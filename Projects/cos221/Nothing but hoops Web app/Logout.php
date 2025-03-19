<?php
// Clear the cookies

setcookie("user_id", "", time() - 3600, "/");
header("Location: login.php");
exit;
?>