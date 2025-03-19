<?php
    $data = file_get_contents("data.json");
    $data = json_decode($data);
    echo "<table border='1'>";
    echo "<tr><th>Firstname</th><th>Lastname</th><th>About</th><th>Hobbies</th></tr>";
    foreach($data as $person){
        echo "<tr>";
        echo "<td>".$person->firstname."</td>";
        echo "<td>".$person->lastname."</td>";
        echo "<td>".$person->about."</td>";
        echo "<td>";
        foreach($person->hobbies as $hobby){
            echo $hobby.", ";
        }
        //OR you can use implode to join the array into a string. implode(", ", $person->hobbies);
        echo "</td>";
        echo "</tr>";
    }
    echo "</table>";

?>