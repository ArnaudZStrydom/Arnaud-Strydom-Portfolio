<?php
    $isValid = true; ;
    if($_POST["firstname"] == null){
        echo "First name missing <br>";
        $isValid = false;
    }

    if($_POST["lastname"] == null){
        echo "Last name missing <br>";
        $isValid = false;
    }

    if($_POST["about"] == null){
        echo "About missing <br>";
        $isValid = false;
    }
    if($_POST["hobbies"] == null){
        echo "Hobbies missing <br>";
        $isValid = false;
    }
    if($isValid == false){
        echo "Please fix these errors <br>";
        die();
    }

    $human = new stdClass();
    $human->firstname = $_POST["firstname"];
    $human->lastname = $_POST["lastname"];
    $human->about = $_POST["about"];
    $human->hobbies = explode(",",$_POST["hobbies"]);

    echo "Here is what the JSON equivalent of your form data: <br>";
    echo JSON_encode($human, JSON_PRETTY_PRINT);

?>