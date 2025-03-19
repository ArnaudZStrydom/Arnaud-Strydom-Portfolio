document.addEventListener("DOMContentLoaded", function() {
    var registerButton = document.getElementById("registerButton");
    var registerMessage = document.getElementById("registerMessage");
    var dob = document.getElementById('dob').value;

    registerButton.addEventListener("click", function(event) {
        event.preventDefault();
        const name = document.getElementById('fname').value;
        const surname = document.getElementById('lname').value;
        const email = document.getElementById('email').value;
        const dob = document.getElementById('dob').value;
        const password = document.getElementById('password').value;
        
        // Perform validation here if needed
        if (!fname || !lname || !email || !dob || !password) {
          document.getElementById('registerMessage').innerText = 'Please fill out all fields.';
          return;
        }
        var requestBody = {
            "type": "Register",
            "name": name,
            "surname": surname,
            "email": email,
            "password": password,
            "DOB": dob,
        };

        // Send POST request to api.php
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "https://wheatley.cs.up.ac.za/u23536013/cos221/api.php", true);
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
                        window.location.href = "https://wheatley.cs.up.ac.za/u23536013/cos221/Movies.php";
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
    
        xmlReq.open("POST", "https://wheatley.cs.up.ac.za/u23536013/cos221/api.php", true);
        xmlReq.setRequestHeader("Content-Type", "application/json");
    
        xmlReq.send(JSON.stringify(requestBody));
    }
});