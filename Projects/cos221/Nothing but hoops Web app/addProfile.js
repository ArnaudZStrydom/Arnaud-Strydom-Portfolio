document.addEventListener("DOMContentLoaded", function() {
    const addProfileForm = document.getElementById("addProfileForm");
    const languageIDInput = document.getElementById("language");
    const dropdownItems = document.querySelectorAll(".dropdown-item");
    const dropdownBtn = document.querySelector(".dropbtn");
  
    dropdownItems.forEach(item => {
      item.addEventListener("click", function() {
        const selectedLanguage = item.getAttribute('data-value');;
        dropdownBtn.textContent = selectedLanguage;
        languageIDInput.value = selectedLanguage;
      });
    });
  
    addProfileForm.addEventListener("submit", function(event) {
      event.preventDefault();
      const profileName = document.getElementById("profileName").value;
      const isChildProfile = document.getElementById("isChildProfile").checked ? 1 : 0;
      const languageID = languageIDInput.value;
      const userId = getCookie("user_id");
  
      const newProfile = {
        Profile_Name: profileName,
        User_ID: userId,
        Child_Profile: isChildProfile,
        Language_ID: languageID
      };
  
      addProfileRequest(newProfile);
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
  
    function addProfileRequest(newProfile) {
      const url = "https://wheatley.cs.up.ac.za/u23536013/cos221/api.php";
      const requestBody = {
        type: "addProfile",
        userID: newProfile.User_ID,
        profileName: newProfile.Profile_Name,
        childProfile: newProfile.Child_Profile,
        languageID: newProfile.Language_ID
      };
  
      sendRequest(url, requestBody, function(response) {
        if (response.status === 200) {
          window.location.href = "https://wheatley.cs.up.ac.za/u23536013/cos221/accounts.html"; // Redirect to main page
        } else {
          console.error("Error adding profile:", response.message);
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
  });