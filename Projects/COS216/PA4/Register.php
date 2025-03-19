<?php
include 'header.php';
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link rel="stylesheet" href="css/stylesPA1.css"> 
</head>
<body>
    <div class="flex-container">
        <h2>Register</h2>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required><br><br>
        <label for="surname">Surname:</label>
        <input type="text" id="surname" name="surname" required><br><br>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <label for="cellphone">Cellphone:</label>
        <input type="text" id="cellphone" name="cellphone" required><br><br>
        <a id="registerButton" href="#" ><button type="button">Register</button></a>
        <p id="registerMessage"></p> 
    </div>
    <script src="Register.js"></script>
</body>
</html>