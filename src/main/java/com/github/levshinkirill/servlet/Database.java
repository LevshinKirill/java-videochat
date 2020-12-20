package com.github.levshinkirill.servlet;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    static public Connection getDataBase() {
        String DB_URL = "jdbc:postgresql://127.0.0.1:5432/java_videochat";
        String USER = "postgres";
        String PASS = "1234";
        System.out.println("Testing connection to PostgreSQL JDBC");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return null;
        }
        System.out.println("PostgreSQL JDBC Driver successfully connected");
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return null;
        }
        if (connection != null) {
            System.out.println("You successfully connected to database now");
            return  connection;
        } else {
            System.out.println("Failed to make connection to database");
            return null;
        }
    }

    static public ArrayList<DbUser> getUsers() throws SQLException {
        Connection connection = getDataBase();
        String sql = "select * from users";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        ArrayList<DbUser> users = new ArrayList<DbUser>();
        while (result.next()){
            String id = result.getString("user_id");
            String userName = result.getString("user_name");
            String password = result.getString("user_password");
            users.add(new DbUser(id,userName,password));
        }
        return users;
    }

    static  public boolean createUser(String id,String name,String password) throws SQLException {
        Connection connection = getDataBase();
        String sql = "insert into users (user_id,user_name,user_password) values (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,id);
        statement.setString(2,name);
        statement.setString(3,password);
        int rows = statement.executeUpdate();
        if(rows > 0) {
            return true;
        } else {
            return false;
        }
    }

    static  public ArrayList<DbRoom> getRooms() throws SQLException {
        Connection connection = getDataBase();
        String sql = "select rooms.room_id,rooms.room_name,users.user_name from users join rooms on rooms.user_id = users.user_id";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        ArrayList<DbRoom> rooms = new ArrayList<DbRoom>();
        while (result.next()){
            String roomId = result.getString("room_id");
            String roomName = result.getString("room_name");
            String userName = result.getString("user_name");
            rooms.add(new DbRoom(roomId,roomName,userName));
        }
        return rooms;
    }

    static public boolean createRoom(String id, String name, String user_id) throws SQLException{
        Connection connection = getDataBase();
        String sql = "insert into rooms (room_id,room_name,user_id) values (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,id);
        statement.setString(2,name);
        statement.setString(3,user_id);
        int rows = statement.executeUpdate();
        if(rows > 0) {
            return true;
        } else {
            return false;
        }
    }

    static public boolean deleteRoom(String id) throws SQLException {
        Connection connection = getDataBase();
        String sql = "delete from rooms where room_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,id);
        int rows = statement.executeUpdate();
        if(rows > 0) {
            return true;
        } else {
            return false;
        }
    }
}
