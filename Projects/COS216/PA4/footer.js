document.addEventListener("DOMContentLoaded", function() {
  
    const themeToggle = document.getElementById("themeToggle");
    const singlelisting = document.querySelectorAll(".singlelisting");
    const details = document.querySelectorAll(".details");
    const singleAgents = document.querySelectorAll(".singleagent");
    const detailsH2Elements = document.querySelectorAll(".singleagent");
  
    themeToggle.addEventListener("click", function() {
        const singlelisting = document.querySelectorAll(".singlelisting");
        const details = document.querySelectorAll(".details");
        const singleAgents = document.querySelectorAll(".singleagent");
        const detailsH2Elements = document.querySelectorAll('.details h2');
      document.body.classList.toggle("dark-mode");
      
      const isDarkMode = document.body.classList.contains("dark-mode");
      saveThemePreference(isDarkMode);
  
      singlelisting.forEach(listing => {
        listing.classList.toggle("dark-mode", isDarkMode);
      });
  
      details.forEach(detail => {
        detail.classList.toggle("dark-mode", isDarkMode);
      });

      detailsH2Elements.forEach(detailh2 => {
        detailh2.classList.toggle("dark-mode", isDarkMode);
      });
  
      singleAgents.forEach(agent => {
        agent.classList.toggle("dark-mode", isDarkMode);
      });
    });
  
    function applyThemePreference() {
      const isDarkMode = getCookie("darkMode") === "true";
      const themeToggle = document.getElementById("themeToggle");
      const singlelisting = document.querySelectorAll(".singlelisting");
      const details = document.querySelectorAll(".details");
      const singleAgents = document.querySelectorAll(".singleagent");
      const detailsH2Elements = document.querySelectorAll(".singleagent");
      if (isDarkMode) {
        document.body.classList.add("dark-mode");
        singlelisting.forEach(listing => {
          listing.classList.add("dar-kmode");
        });
        details.forEach(detail => {
          detail.classList.add("dark-mode");
        });
        detailsH2Elements.forEach(detailh2 => {
            detailh2.classList.add("dark-mode");
          });
        singleAgents.forEach(agent => {
          agent.classList.add("dark-mode");
        });
      } else {
        document.body.classList.remove("dark-mode");
        singlelisting.forEach(listing => {
          listing.classList.remove("dark-mode");
        });
        detailsH2Elements.forEach(detailh2 => {
            detailh2.classList.remove("dark-mode");
        });
        details.forEach(detail => {
            detail.classList.remove("dark-mode");
          });
        singleAgents.forEach(agent => {
          agent.classList.remove("dark-mode");
        });
      }
    }
  
    applyThemePreference();
  });
  
  function saveThemePreference(isDarkMode) {
    document.cookie = `darkMode=${isDarkMode}; expires=Fri, 31 Dec 9999 23:59:59 GMT`;
  }
  
  function getCookie(name) {
    const cookieString = document.cookie;
    const cookies = cookieString.split("; ");
    for (const cookie of cookies) {
      const [cookieName, cookieValue] = cookie.split("=");
      if (cookieName === name) {
        return cookieValue;
      }
    }
    return "";
  }