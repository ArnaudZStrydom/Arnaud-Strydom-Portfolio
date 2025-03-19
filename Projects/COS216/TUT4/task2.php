<?php
$JSON_data = file_get_contents("data.json");
$JSON_data = json_decode($JSON_data);

echo "<table border = '1'>";
    echo "<tr><th>First Name</th><th>Last Name</th><th>about</th><th>hobbies</th></tr>";

    foreach($JSON_data as $human){
        echo "<tr>";
        echo "<td>".$human->firstname."</td>";
        echo "<td>".$human->lastname."</td>";
        echo "<td>".$human->about."</td>";
        echo "<td>";


        
        echo implode(",",$human->hobbies);
        
        echo "</td>";
        echo "</tr>";
    }
echo "</table>";
?>