
<<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/auth.css">
<title>Login</title>
</head>
<body>
  <div class="container">
    <div class="form">
      <h2 class="userType"> Login</h2>
      <form id="loginUser">
        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email"><br>
        <label for="password">Password:</label><br>
        <input type="password" id="password" name="password"><br><br>
        <button type="submit" id="loginButton">Login</button>
      </form>
      <p style="color:white">Have no account yet? <span style="display:inline; width:30%; background-color:white; color:black;"><a href="signup.html">Signup</a></span></p>
    </div>
    <p id = "loginMessage"> </p>
  </div>
  <script src="Login.js"></script>
</body>
</html>

<?php
include 'footer.php';
?>