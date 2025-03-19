document.addEventListener("DOMContentLoaded", function() {
  const profilePicture = "img/profile_icon.webp";
  const userId = getCookie("user_id");

  getProfiles(userId, profilePicture);
});

let otherProfiles = [];

function getCookie(name) {
  const nameEQ = name + "=";
  const ca = document.cookie.split(';');
  for (let i = 0; i < ca.length; i++) {
      let c = ca[i].trim();
      if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length);
  }
  return null;
}

function setCookie(name, value, days) {
  const d = new Date();
  d.setTime(d.getTime() + (days * 24 * 60 * 60 * 1000));
  const expires = "expires=" + d.toUTCString();
  document.cookie = name + "=" + value + ";" + expires + ";path=/";
}

function renderProfiles(userId, profilePicture) {
  const profileList = document.getElementById("profileList");
  profileList.innerHTML = "";

  otherProfiles.forEach(profile => {
      const profileItem = document.createElement("div");
      profileItem.classList.add("profile-item");
      profileItem.innerHTML = `
          <img src="${profilePicture}" alt="Profile Picture">
          <div class="profile-details">
              <h2>${profile.Profile_Name}</h2>
              <p>${profile.Child_Profile ? "Child Profile" : "Adult Profile"}</p>
              <button class="delete-profile-btn" data-profile-id="${profile.Profile_ID}">Delete</button>
          </div>
      `;
      
      profileItem.onclick = function() {
          setCookie("Profile_ID", profile.Profile_ID, 7);
          setCookie("isChild",profile.Child_Profile,7);
          window.location.href = "https://wheatley.cs.up.ac.za/u23536013/cos221/Movies.php";
      };

      profileList.appendChild(profileItem);
  });

  if (otherProfiles.length < 4) {
      const addProfileButton = document.createElement("div");
      addProfileButton.classList.add("add-profile");
      addProfileButton.onclick = function() {
          window.location.href = "https://wheatley.cs.up.ac.za/u23536013/cos221/addProfile.html";
      };
      addProfileButton.innerHTML = `<span style="font-size: 24px">+</span>`;
      profileList.appendChild(addProfileButton);
  }

  document.querySelectorAll(".delete-profile-btn").forEach(button => {
      button.addEventListener("click", function(event) {
          event.stopPropagation();
          const profileID = event.target.getAttribute("data-profile-id");
          deleteProfile(profileID);
          location.reload();
      });
  });
}

function addProfile() {
  const userId = getCookie("user_id");
  const profileName = prompt("Enter profile name:");
  if (profileName) {
      const isChildProfile = confirm("Is this a child profile?");
      const languageID = prompt("Enter the language ID for the new profile");
      const newProfile = {
          Profile_Name: profileName,
          User_ID: userId,
          Child_Profile: isChildProfile ? 1 : 0,
          Language_ID: languageID
      };
      addProfileRequest(newProfile);
  }
}

function editProfile(profileID) {
  const profileIndex = otherProfiles.findIndex(profile => profile.Profile_ID === profileID);
  if (profileIndex !== -1) {
      const newProfileName = prompt("Enter new profile name:", otherProfiles[profileIndex].Profile_Name);
      const isChildProfile = confirm("Is this a child profile?");
      otherProfiles[profileIndex].Profile_Name = newProfileName;
      otherProfiles[profileIndex].Child_Profile = isChildProfile ? 1 : 0;
      updateProfileRequest(profileID, newProfileName, isChildProfile);
  }
}

function deleteProfile(profileID) {
  if (confirm("Are you sure you want to delete this profile?")) {
      removeProfileRequest(profileID);
  }
}

function getProfiles(userId, profilePicture) {
  const url = "https://wheatley.cs.up.ac.za/u23536013/cos221/api.php";
  const requestBody = {
      type: "getProfiles",
      userID: userId
  };

  sendRequest(url, requestBody, (response) => {
      if (response.status === 200) {
          otherProfiles = response.data;
          renderProfiles(userId, profilePicture);
      } else {
          console.error("Error retrieving profile:", response.message);
      }
  });
}

function updateProfileRequest(id, name, child) {
  const url = "https://wheatley.cs.up.ac.za/u23536013/cos221/api.php";
  const requestBody = {
      type: "updateProfile",
      profileID: id,
      profileName: name,
      childProfile: child
  };

  sendRequest(url, requestBody, (response) => {
      if (response.status === 200) {
          renderProfiles(getCookie("user_id"), "img/profile_icon.webp");
      } else {
          console.error("Error updating profile:", response.message);
      }
  });
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

  sendRequest(url, requestBody, (response) => {
      if (response.status === 200) {
          renderProfiles(getCookie("user_id"), "img/profile_icon.webp");
      } else {
          console.error("Error adding profile:", response.message);
      }
  });
}

function removeProfileRequest(profileID) {
  const url = "https://wheatley.cs.up.ac.za/u23536013/cos221/api.php";
  const requestBody = {
      type: "removeProfile",
      profileID: profileID
  };

  sendRequest(url, requestBody, (response) => {
      if (response.status === 200) {
          const profileIndex = otherProfiles.findIndex(profile => profile.Profile_ID === profileID);
          if (profileIndex !== -1) {
              otherProfiles.splice(profileIndex, 1);
              renderProfiles(getCookie("user_id"), "img/profile_icon.webp");
          }
      } else {
          console.error("Error removing profile:", response.message);
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

