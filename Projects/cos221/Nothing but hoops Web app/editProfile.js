document.addEventListener("DOMContentLoaded", function() {
    const profileID = getCookie("Profile_ID");
    const editProfileForm = document.getElementById("editProfileForm");
    const languageIDInput = document.getElementById("language");
    const dropdownItems = document.querySelectorAll(".dropdown-item");
    const dropdownBtn = document.querySelector(".dropbtn");

    const profilePicture = "img/profile_icon.webp";
    const userId = getCookie("user_id");

    getProfiles(userId, profilePicture, function() {
        populateForm(profileID);
    });

    dropdownItems.forEach(item => {
        item.addEventListener("click", function() {
            const selectedLanguage = item.getAttribute('data-value');
            dropdownBtn.textContent = selectedLanguage;
            languageIDInput.value = selectedLanguage;
        });
    });

    editProfileForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const profileName = document.getElementById("profileName").value;
        const isChildProfile = document.getElementById("isChildProfile").checked ? 1 : 0;
        const languageID = languageIDInput.value;

        updateProfileRequest(profileID, profileName, isChildProfile, languageID);
    });
});

function getCookie(name) {
    const nameEQ = name + "=";
    const ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i].trim();
        if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length);
    }
    return null;
}

function updateProfileRequest(id, name, child, language) {
    const url = "https://wheatley.cs.up.ac.za/u23536013/cos221/api.php";
    const requestBody = {
        type: "updateProfile",
        profileID: id,
        profileName: name,
        childProfile: child,
        language: language
    };

    sendRequest(url, requestBody, function(response) {
        if (response.status === 200) {
            window.location.href = "https://wheatley.cs.up.ac.za/u23536013/cos221/accounts.html"; // Redirect to main page
        } else {
            console.error("Error updating profile:", response.message);
        }
    });
}

function sendRequest(url, requestBody, callback) {
    const xmlReq = new XMLHttpRequest();
    xmlReq.open("POST", url, true);
    xmlReq.setRequestHeader("Content-Type", "application/json");

    xmlReq.onreadystatechange = function() {
        if (xmlReq.readyState === XMLHttpRequest.DONE) {
            if (xmlReq.status === 200) {
                const response = JSON.parse(xmlReq.responseText);
                callback(response);
            } else {
                console.error("Error. HTTP status:", xmlReq.status);
            }
        }
    };

    xmlReq.onerror = function() {
        console.error("Request error");
    };

    xmlReq.send(JSON.stringify(requestBody));
}

let otherProfiles = [];

function getProfiles(userId, profilePicture, callback) {
    const url = "https://wheatley.cs.up.ac.za/u23536013/cos221/api.php";
    const requestBody = {
        type: "getProfiles",
        userID: userId
    };

    sendRequest(url, requestBody, function(response) {
        if (response.status === 200) {
            otherProfiles = response.data;
            callback();
        } else {
            console.error("Error retrieving profile:", response.message);
        }
    });
}

function populateForm(profileID) {
    otherProfiles.forEach(profile => {
        if (profileID == profile.Profile_ID) {
            document.getElementById("profileID").value = profile.Profile_ID;
            document.getElementById("profileName").value = profile.Profile_Name;
            document.getElementById("isChildProfile").checked = profile.Child_Profile == 1;
        }
    });
}

  
  
  