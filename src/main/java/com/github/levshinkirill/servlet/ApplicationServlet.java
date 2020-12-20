package com.github.levshinkirill.servlet;
import java.io.IOException;
import java.sql.*;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.github.levshinkirill.Room;

@WebServlet(urlPatterns = "/app")
public class ApplicationServlet extends HttpServlet {

    private static final long serialVersionUID = 3987695371953543306L;

    private ConcurrentMap<String, Set<Session>> rooms = Room.INSTANCE.map();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        DbUser currentUser = (DbUser) session.getAttribute("current_user");
        if (req.getParameterMap().containsKey("sign_up")) {
            if(req.getParameter("user_name").length()<3) {
                req.setAttribute("error", "<div class='center red-text'>ИМЯ ПОЛЬЗОВАТЕЛЯ ДОЛЖНО СОДЕРЖАТЬ БОЛЬШЕ 3 СИМВОЛОВ </div> </br>");
                getServletContext().getRequestDispatcher("/auth.jsp").forward(req, resp);
            }
            if(req.getParameter("password").length()<8) {
                req.setAttribute("error", "<div class='center red-text'>ПАРОЛЬ ДОЛЖЕН БЫТЬ БОЛЬШЕ 8 СИМВОЛОВ</div> </br>");
                getServletContext().getRequestDispatcher("/auth.jsp").forward(req, resp);
            }
            try {
                for(DbUser user : Database.getUsers()) {
                    if(user.getName().equals(req.getParameter("user_name"))) {
                        req.setAttribute("error", "<div class='center red-text'>ПОЛЬЗОВАТЕЛЬ С ТАКИМ ИМЕНЕМ УЖЕ СУЩЕСТВУЕТ</div> </br>");
                        getServletContext().getRequestDispatcher("/auth.jsp").forward(req, resp);
                    }
                }
                String uuid = UUID.randomUUID().toString().replace("-", "");
                DbUser user = new DbUser(uuid,req.getParameter("user_name"),req.getParameter("password"));
                Database.createUser(uuid,req.getParameter("user_name"),req.getParameter("password"));
                session.setAttribute("current_user", user);
                resp.sendRedirect("/app");
            } catch (Exception e) {
                req.setAttribute("error", "SERVER ERROR");
                getServletContext().getRequestDispatcher("/auth.jsp").forward(req, resp);
            }
        }
        if(req.getParameterMap().containsKey("sign_in")) {
            try {
                for(DbUser user : Database.getUsers()) {
                    if(user.getName().equals(req.getParameter("user_name"))) {
                        if(user.getPassword().equals(req.getParameter("password")) ) {
                            session.setAttribute("current_user", user);
                            resp.sendRedirect("/app");
                        }
                    }
                }
                req.setAttribute("error", "<div class='center red-text'>ПОЛЬЗОВАТЕЛЬ С ТАКИМ ИМЕНЕМ И ПАРОЛЕМ НЕ СУЩЕСТВУЕТ</div> </br>");
                getServletContext().getRequestDispatcher("/auth.jsp").forward(req, resp);
            } catch (Exception e) {
                req.setAttribute("error", "server error");
                getServletContext().getRequestDispatcher("/auth.jsp").forward(req, resp);
            }
        } else
        if(currentUser==null) {
            getServletContext().getRequestDispatcher("/auth.jsp").forward(req, resp);
        } else
        if (req.getParameterMap().containsKey("room")) {
            String room = req.getParameter("room");
            req.setAttribute("room", req.getContextPath() + "/server/" + req.getParameter("r"));
            req.setAttribute("user_name", currentUser.getName());
            req.setAttribute("rooms", rooms);
            req.setAttribute("init_call", rooms.containsKey(room));
            getServletContext().getRequestDispatcher("/room.jsp").forward(req, resp);
        }
        if (req.getParameterMap().containsKey("create_room")) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            try {
                Database.createRoom(uuid,req.getParameter("create_room"),currentUser.getId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            resp.sendRedirect("/app");
        }
        if (req.getParameterMap().containsKey("delete_room")) {
            try {
                Database.deleteRoom(req.getParameter("delete_room"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            resp.sendRedirect("/app");
        }
        if (req.getParameterMap().containsKey("log_out")) {
            session.invalidate();
            getServletContext().getRequestDispatcher("/auth.jsp").forward(req, resp);
        } else {
            try {
                req.setAttribute("rooms", Database.getRooms());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            getServletContext().getRequestDispatcher("/room_list.jsp").forward(req, resp);
        }
    }
}
