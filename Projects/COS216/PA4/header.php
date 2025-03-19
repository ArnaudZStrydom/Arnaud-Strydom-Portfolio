
<!DOCTYPE html>
<html>
<head>
    <title>HOOP</title>
    <link rel="icon" type="image/x-icon" href="img/HEAVEN HAVEN (3).png">
    <link rel="stylesheet" href="css/header.css">
</head>
<body>

<div class="navbar">
  <img class="logo" src="img/HEAVEN_HAVEN__2_-removebg-preview.png" alt="Logo">;
  <a href="https://wheatley.cs.up.ac.za/u23536013/index.html">Home</a>
  <a href="https://wheatley.cs.up.ac.za/u23536013/COS216/PA4/index2.php">Listings</a>
  <a href="https://wheatley.cs.up.ac.za/u23536013/COS216/PA4/agents.php">Agents</a>
  <a href="https://wheatley.cs.up.ac.za/u23536013/COS216/PA4/calculator.php">Calculators</a>
  <a href="https://wheatley.cs.up.ac.za/u23536013/COS216/PA4/favourites.php">Favourites</a>
  <input id="searchInput" type="text" placeholder="Search..">
  <?php
 
 session_start();
 require_once('config.php');
 
 if (isset($_COOKIE['user_id'])) {
     $loggedIn = true; 
     $user_id = $_COOKIE['user_id'];
     $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
     $stmt = $mysqli->prepare("SELECT name, surname FROM users WHERE id = ?");
     $stmt->bind_param("i", $user_id);
     $stmt->execute();
     $result = $stmt->get_result();
 
     if ($result->num_rows == 1) {
         $row = $result->fetch_assoc();
         $user_name = $row['name'] . " " . $row['surname']; 
     } else {
         $user_name = "User";
     }
     $stmt->close(); // Close the statement
     $mysqli->close(); // Close the connection
 } else {
     $loggedIn = false;
     $user_name = "Guest";
 }
  if ($loggedIn) {
    echo '<p>' . $user_name . '</p>';
    echo '<a href="https://wheatley.cs.up.ac.za/u23536013/COS216/PA4/Logout.php">Logout</a>'; 
    
  } else {
      
      echo '<a href="https://wheatley.cs.up.ac.za/u23536013/COS216/PA4/Login.php">Login</a>'; 
      echo '<a href="https://wheatley.cs.up.ac.za/u23536013/COS216/PA4/Register.php">Register</a>'; 
  }
  ?>
  

</div>

