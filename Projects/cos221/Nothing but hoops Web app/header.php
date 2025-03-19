<!DOCTYPE html>
<html>
<head>
    <title>HOOP</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/x-icon" href="img/hoop_logo.png">
    <link rel="stylesheet" href="css/header.css">
</head>
<body>

<div class="navbar">
  <img class="logo" src="img/hoop_logo.png" alt="Logo">
  <a href="https://wheatley.cs.up.ac.za/u23536013/cos221/Movies.php">Movies</a>
  <a href="https://wheatley.cs.up.ac.za/u23536013/cos221/Series.php">Series</a>
  <a href="https://wheatley.cs.up.ac.za/u23536013/cos221/Recomendations.php">Recomendations</a>
  <input id="searchInput" type="text" placeholder="Search..">
  <?php
  session_start();
  require_once('config.php');
  
  if (isset($_COOKIE['Profile_ID'])) {
      $loggedIn = true; 
      $Profile_ID = $_COOKIE['Profile_ID'];
      $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

      // Check for profile name
      $stmt = $mysqli->prepare("SELECT Profile_Name, User_ID FROM profile_account WHERE Profile_ID = ?");
      $stmt->bind_param("i", $Profile_ID);
      $stmt->execute();
      $result = $stmt->get_result();

      if ($result->num_rows == 1) {
          $row = $result->fetch_assoc();
          $user_name = $row['Profile_Name'];
          $User_ID = $row['User_ID'];
      } else {
          $user_name = "User";
      }
      $stmt->close(); // Close the statement

      // Check for admin privileges
      $stmt = $mysqli->prepare("SELECT Admin_privileges FROM user_account WHERE User_ID = ?");
      $stmt->bind_param("i", $User_ID);
      $stmt->execute();
      $result = $stmt->get_result();

      if ($result->num_rows == 1) {
          $row = $result->fetch_assoc();
          $isAdmin = $row['Admin_privileges'];
      } else {
          $isAdmin = false;
      }
      $stmt->close(); // Close the statement

      $mysqli->close(); // Close the connection
  } else {
      $loggedIn = false;
      $user_name = "Guest";
      $isAdmin = false;
  }

  if ($loggedIn) {
      echo '<p>' . $user_name . '</p>';
      echo '<a href="https://wheatley.cs.up.ac.za/u23536013/cos221/Logout.php">Logout</a>'; 
      echo '<a href="https://wheatley.cs.up.ac.za/u23536013/cos221/accounts.html">Switch Profile</a>'; 
      echo '<a href="https://wheatley.cs.up.ac.za/u23536013/cos221/editProfile.html">Edit Profile</a>';
      
      // Display Admin tab if the user is an admin
      if ($isAdmin) {
          echo '<a href="https://wheatley.cs.up.ac.za/u23536013/cos221/admin.php">Admin</a>';
      }
  } else {
      echo '<a href="https://wheatley.cs.up.ac.za/u23536013/cos221/login.php">Login</a>'; 
      echo '<a href="https://wheatley.cs.up.ac.za/u23536013/cos221/signup.php">Register</a>'; 
  }
  ?>
</div>

</body>
</html>

