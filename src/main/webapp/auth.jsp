<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css">

  <!-- Compiled and minified JavaScript -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script>
</head>
<style>

</style>
<body>
    <div class="container">
        <div class="row">
            <form class="col s12 m8 l4 offset-m2 offset-l4">
              <div class="card">
          
                <div class="card-action teal lighten-1 white-text">
                  <h3>Форма авторизации</h3>
                </div>
          
                <div class="card-content">
                  <div class="form-field">
                    <label for="user_name">Имя пользователя</label>
                    <input type="text" id="user_name" name="user_name">
                  </div><br>
          
                  <div class="form-field">
                    <label for="password">Пароль</label>
                    <input type="password" id="password" name="password">
                  </div><br>
                  ${error ? "": error}
                  <div class="form-field">
                    <!-- <button type="submit" class="btn-large waves-effect waves-dark" onclick="/app?create=kirusha&password=kirusha">Войти</button> -->
                    <input type="submit" class="btn-large waves-effect waves-dark" style="width:100%;" name="sign_up" value="РЕГИСТРИЯ" />
                  </div><br>
                  <div class="form-field">
                    <!-- <button type="submit" class="btn-large waves-effect waves-dark" onclick="/app?create=kirusha&password=kirusha">Войти</button> -->
                    <input type="submit" class="btn-large waves-effect waves-dark" style="width:100%;" name="sign_in" value="Войти" />
                  </div><br>
                </div>
          
              </div>
            </form>
          </div>
      </div>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.1/js/materialize.min.js"></script>
</body>

</html>
