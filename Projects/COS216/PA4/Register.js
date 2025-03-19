document.addEventListener("DOMContentLoaded", function() {
    var registerButton = document.getElementById("registerButton");
    var registerMessage = document.getElementById("registerMessage");

    registerButton.addEventListener("click", function(event) {
        event.preventDefault();

        var name = document.getElementById("name").value;
        var surname = document.getElementById("surname").value;
        var email = document.getElementById("email").value;
        var password = document.getElementById("password").value;
        var cellphone = document.getElementById("cellphone").value;

        var requestBody = {
            "type": "Register",
            "name": name,
            "surname": surname,
            "email": email,
            "password": password,
            "cellphone": cellphone
        };

        // Send POST request to api.php
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "https://wheatley.cs.up.ac.za/u23536013/api.php", true);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    var response = JSON.parse(xhr.responseText);
                    if (response.status === "Success") {
                        
                        handleLogin(email, password);
                    } else {
                        
                        registerMessage.textContent = "Registration failed: " + response.message;
                    }
                } else {
                    
                    registerMessage.textContent = "Registration request failed. HTTP status: " + xhr.status;
                }
            }
        };

        xhr.send(JSON.stringify(requestBody));
    });

    function handleLogin(email, password) {
        var xmlReq = new XMLHttpRequest();
    
        xmlReq.onreadystatechange = function() {
            if (xmlReq.readyState === XMLHttpRequest.DONE) {
                if (xmlReq.status === 200) {
                    var response = JSON.parse(xmlReq.responseText);
                    if (response.status === "Success") {
                        window.location.href = "https://wheatley.cs.up.ac.za/u23536013/COS216/PA4/index2.php";
                    } else {
                        console.error("Login failed:", response.status);
                        document.getElementById("loginMessage").textContent = "Login failed. INCORRECT PASSWORD OR EMAIL!";
                    }
                } else {
                    console.error("Login request failed. HTTP status:", xmlReq.status);
                    document.getElementById("loginMessage").textContent = "Login failed. INCORRECT PASSWORD OR EMAIL!";
                }
            }
        };
    
        var requestBody = {
            "type": "Login",
            "email": email,
            "password": password
        };
    
        xmlReq.open("POST", "https://wheatley.cs.up.ac.za/u23536013/api.php", true);
        xmlReq.setRequestHeader("Content-Type", "application/json");
    
        xmlReq.send(JSON.stringify(requestBody));
    }
});