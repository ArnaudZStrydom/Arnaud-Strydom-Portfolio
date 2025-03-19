document.addEventListener("DOMContentLoaded", function() {
    var loader = document.querySelector(".loader");
    var agentsContainer = document.querySelector(".allagents");
    fetchAgents(agentsContainer, loader); 

    function fetchAgents(agentsContainer, loader) {
        loader.style.display = "block";
        agentsContainer.style.display = "none";
        var xmlReq = new XMLHttpRequest();
        xmlReq.onreadystatechange = function() {
            if (xmlReq.readyState === XMLHttpRequest.DONE) {
                if (xmlReq.status === 200) {
                    var response = JSON.parse(xmlReq.responseText);
                    if (response.status === "success") {
                        populateAgentsWithImages(response.data, agentsContainer, loader); 
                    } else {
                        console.error("Error fetching agents:", response.message);
                    }
                } else {
                    console.error("Error fetching agents. HTTP status:", xmlReq.status);
                }
            }
        };

        var requestBody = {
            "studentnum": "u23536013",
            "apikey": "06a1f50b0dd8a2299328d39e8fb30526",
            "type": "GetAllAgents",
            "limit": 10 
        };

        xmlReq.open("POST", "https://wheatley.cs.up.ac.za/api/", true);
        xmlReq.setRequestHeader("Content-Type", "application/json");
        xmlReq.send(JSON.stringify(requestBody));
    }

    function populateAgentsWithImages(agents, agentsContainer, loader) {
        var index = 0;
        agentsContainer.innerHTML = ""; 

        agents.forEach(function(agent) {
            var xmlReq = new XMLHttpRequest();
            xmlReq.onreadystatechange = function() {
                if (xmlReq.readyState === XMLHttpRequest.DONE) {
                    if (xmlReq.status === 200) {
                        var response = JSON.parse(xmlReq.responseText);
                        if (response.status === "success" && response.data.length > 0) {
                            var images = response.data;
                            var agentHTML = `
                                <div class="singleagent">
                                    <img src="${images}" alt="${agent.name}">
                                    <div class="details">
                                        <h2>${agent.name}</h2>
                                        <p>Description: <br>${agent.description}</p>
                                        <p>Website: <a href="${agent.url}" target="_blank">${agent.url}</a></p>
                                    </div>
                                </div>
                            `;
                            agentsContainer.innerHTML += agentHTML;
                            index++; 
                        } else {
                            console.error("Error retrieving images for agent:", agent.name);
                        }
                    } else {
                        console.error("Error retrieving images for agent:", agent.name, "HTTP status:", xmlReq.status);
                    }
                }
                if (index === agents.length) {
                    loader.style.display = "none";
                    agentsContainer.style.display = "flex";
                }
            };
            xmlReq.open("GET", `https://wheatley.cs.up.ac.za/api/getimage?agency=${agent.name}`, true);
            xmlReq.send();
        });
    }
});
