document.addEventListener("DOMContentLoaded", function() {
    var loginButton = document.getElementById("loginButton");

    loginButton.addEventListener("click", function(event) {
        event.preventDefault();

        var email = document.getElementById("email").value;
        var password = document.getElementById("password").value;

        handleLogin(email, password);
    });
});

function handleLogin(email, password) {
    var xmlReq = new XMLHttpRequest();

    xmlReq.onreadystatechange = function() {
        if (xmlReq.readyState === XMLHttpRequest.DONE) {
            if (xmlReq.status === 200) {
                var response = JSON.parse(xmlReq.responseText);
                if (response.status === "Success") {
                    document.getElementById("loginMessage").textContent = "Login successful!";
                    window.location.href = "https://wheatley.cs.up.ac.za/u23536013/cos221/accounts.html";
                    loginButton.href = "https://wheatley.cs.up.ac.za/u23536013/cos221/Movies.php";
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