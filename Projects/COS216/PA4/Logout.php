<?php
// Clear the cookies
setcookie("apikey", "", time() - 3600, "/");
setcookie("user_id", "", time() - 3600, "/");
header("Location: Login.php");
exit;
?>