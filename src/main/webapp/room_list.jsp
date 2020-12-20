<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>JSP Application</title>
    
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

    <!-- Compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
            
</head>
<body>
    <nav>
        <div class="nav-wrapper blue">
          <a href="/app" class="brand-logo">Общяшка</a>
          <ul class="right ">
            <li><a href="/app?log_out">Выйти</a></li>
          </ul>
        </div>
      </nav>
    <div class="container">
        <h1 class="center">Список комнат</h1>
        <br />
        <form action="/app">
            <div class="row">
                <div class="col m11 s8">
                    <input type="text" class="form-control"name="create_room" placeholder="Имя комнаты">
                </div>
                <div class="col m1 s3">
                    <button type="submit" class="btn btn-success mb-2">Создать комнату</button>
                </div>
            </div>
        </form>
        <br />
        <div class="row">
            <c:forEach items="${rooms}" var="room" >
                <div class="col s12 m4">
                    <div class="card">
                        <div class="card-image">
                            <img src="https://365psd.com/images/istock/previews/1063/106340965-chat-icon-vector-blue.jpg">
                            <a class="btn-floating halfway-fab waves-effect orange btn-large" href="/app?room=${room.id}"><i class="material-icons">input</i></a>
                        </div>
                        <div class="card-content">
                            <span class="card-title"><c:out value="${room.name}" /></span>
                            <span class="card-title"><strong>Создал:<c:out value="${room.userName}" /></strong></span>
                        </div>
                        <div class="card-action">
                            <a href="/app?delete_room=${room.id}" class="right-align red-text">Удалить комнату</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
          </div>
    </div>
</body>
</html>