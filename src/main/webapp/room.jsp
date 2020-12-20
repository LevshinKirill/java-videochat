<%--
  Created by IntelliJ IDEA.
  User: kirusha
  Date: 8.11.20
  Time: 01:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script>
    <link rel="stylesheet" href="css/room.css">
   </head>
<body>
    <div class="main">
        <div class="row">
            <div class="col l5 m12">
                <div class="video-wrapper">
                    <video id="vid1" class="video"></video>
                </div>
                <div class="video-wrapper">
                    <video id="vid2" class="video"></video>
                </div>
                <a class="waves-effect red btn" href="/app">leave</a>
            </div>
            <div class="col l7 m12">
                    <div class="message-list">
                        <textarea cols="auto" rows="auto" class="message-list-label" id="message-list-label" placeholder="Здесь будут сообщения" readonly></textarea>
                    </div>
                    <div class="message-form">
                        <form onsubmit="pc.sendmessage(event);">
                            <input cols="auto" rows="auto" class="message-form-input" placeholder="Напишите сообщение и нажмите enter" id="message-form-input">
                        </form>
                    </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" >
        var room = "${room}";
        console.log("${room}");
        var initCall = "${init_call}";
        let userName = "${user_name}";

    </script>
<script src="js/app.js" ></script>
</body>

</html>
