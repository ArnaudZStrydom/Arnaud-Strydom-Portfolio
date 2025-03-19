<?php
    $valid=true;
    if($_POST["firstname"]==null){
        echo "First name missing <br>";
        $valid=false;
    }
    if($_POST["lastname"]==null){
        echo "Last name missing <br>";
        $valid=false;
    }
    if($_POST["about"]==null){
        echo "About missing <br>";
        $valid=false;
    }
    if($_POST["hobbies"]==null){
        echo "Hobbies missing <br>";
        $valid=false;
    }

    if(!$valid){
        echo "Please fix these errors";
        die();
    }

    //make the object, you can do it the associtive way aswell
    $person = new stdClass();
    $person->firstname = $_POST["firstname"];
    $person->lastname = $_POST["lastname"];
    $person->about = $_POST["about"];
    $person->hobbies = explode(",",$_POST["hobbies"]);

    
    echo "Here is what the JSON equivalent of your form data: <br>";
    echo JSON_encode($person, JSON_PRETTY_PRINT);

?>