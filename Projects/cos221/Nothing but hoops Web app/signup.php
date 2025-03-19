<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/auth.css" />
    <title>Signup</title>
  </head>
  <body>
    <div class="container">
      <div class="form">
        <h2> Signup</h2>
        <form id="signupUser">
          <label for="fname">First Name:</label><br />
          <input type="text" id="fname" fname="fname" /><br />
          <label for="lname">Last Name:</label><br />
          <input type="text" id="lname" lname="lname" /><br />
          <label for="email">Email:</label><br />
          <input type="email" id="email" name="email" /><br />
          <label for="dob">Date of Birth:</label><br />
          <input type="date" id="dob" name="dob" /><br />
          <label for="password">Password:</label><br />
          <input type="password" id="password" name="password" /><br /><br />
          <button type="submit" id = "registerButton">Signup</button>
        </form>
        <p style="color:white">Have an account already? <span style="display:inline; width:30%; background-color:white; color:black;"><a href="login.php">Login</a></span></p>
        <div id = "registerMessage">
          <!-- Register message here -->
        </div>
      </div>
    </div>
  <script src="Register.js"></script>
</body>
</html>
