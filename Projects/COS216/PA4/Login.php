<?php
include 'header.php';
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="css/stylesPA1.css"> 
</head>
<body>
    <div class="flex-container">
        <h2>Login</h2>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <a id="loginButton" href="#" ><button type="button">Login</button></a>
        <a id="Register" href="Register.php" ><button type="button">Register</button></a>
        <p id="loginMessage"></p> 
    </div>
    <script src="Login.js"></script>
</body>
</html>