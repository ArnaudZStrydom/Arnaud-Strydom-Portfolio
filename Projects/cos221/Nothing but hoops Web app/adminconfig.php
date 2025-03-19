<?php
define('DB_SERVER', 'wheatley.cs.up.ac.za');
define('DB_USERNAME', 'u23536013'); 
define('DB_PASSWORD', 'CB5XXQPCTLSTGWLHVKFOWOET6R6W2UXC'); 
define('DB_DATABASE', 'u23536013_final_prac_5_database');

$mysqli = new mysqli(DB_SERVER, DB_USERNAME, DB_PASSWORD, DB_DATABASE);

if ($mysqli->connect_error) {
    die("Connection failed: " . $mysqli->connect_error);
}
