document.addEventListener("DOMContentLoaded", function() {
    var searchOptions = {}; 
    var sortOptions = {}; 

    var searchInput = document.getElementById("searchInput");
    var listingsContainer = document.querySelector(".alllistings");
    var flexContainer = document.getElementById("flex-container"); 
    var userid = getCookie("user_id");
    if (!userid) {
        listingsContainer.style.display = "none";
        flexContainer.style.display = "none";
        
    
        var loginMessageContainer = document.createElement("div");
        loginMessageContainer.classList.add("login-message-container");
    
        var loginMessage = document.createElement("h2");
        loginMessage.textContent = "Please log in to view listings.";
    
        loginMessageContainer.appendChild(loginMessage);
        document.body.appendChild(loginMessageContainer);
    
        return;
    }
    


    searchInput.addEventListener("keyup", function(event) {
        if (event.key === "Enter") {
            var searchTerm = searchInput.value.trim();
            if (searchTerm !== "Search..") {
                searchOptions = { ...searchOptions, Title_Name: searchTerm }; 
            } else {
                delete searchOptions.location; 
            }
            fetchListings(listingsContainer,flexContainer, searchOptions, sortOptions); 
        }
    });

    var dropdowns = document.querySelectorAll('.dropdown');

    dropdowns.forEach(function(dropdown) {
        var button = dropdown.querySelector('.dropbtn');
        var items = dropdown.querySelectorAll('.dropdowncont .dropdown-item');

        button.addEventListener('click', function() {
            dropdown.classList.toggle('active');
        });

        items.forEach(function(item) {
            item.addEventListener('click', function() {
                var filterType = item.getAttribute('data-filter');
                var filterValue = item.getAttribute('data-value');
                
                if (filterType === "Genre_Name" || filterType === "Language_Name") {
                    searchOptions = { ...searchOptions, [filterType]: filterValue }; 
                } else if (filterType === "Review_Rating") {
                    searchOptions = { ...searchOptions, [filterType]: parseInt(filterValue) }; 
                } else if (filterType === "sort") {
                    sortOptions = { [filterType]: filterValue }; 
                }
                
                fetchListings(listingsContainer,flexContainer, searchOptions, sortOptions); 
                dropdown.classList.remove('active');
            });
        });
    });



    var clearFiltersButton = document.getElementById('cleardropdown');
    clearFiltersButton.addEventListener('click', function() {
        searchOptions = {}; 
        sortOptions = {}; 
        fetchListings(listingsContainer,flexContainer, searchOptions, sortOptions); 
    });

    fetchListings(listingsContainer,flexContainer, searchOptions, sortOptions); 

});

function fetchListings(listingsContainer,flexContainer, searchOptions = {}, sortOptions = {}) {
    
    listingsContainer.style.display = "none";
    flexContainer.style.display = "none";
    var xmlReq = new XMLHttpRequest();
    xmlReq.onreadystatechange = function() {
        if (xmlReq.readyState === XMLHttpRequest.DONE) {
            if (xmlReq.status === 200) {
                var response = JSON.parse(xmlReq.responseText);
                if (response.status === "Success") {
                    var Series = response.data;
                    populateListingsWithImages(Series, listingsContainer,flexContainer);
                } else {
                    console.error("Error retrieving listings:", response.message);
                }
            } else {
                console.error("Error retrieving listings. HTTP status:", xmlReq.status);
            }
        }
    };

    var apiKey = getCookie('apikey');
    var isChild = getCookie('isChild');
    var requestBody = {
        "type": "GetSeries",
        "return": "*",
        "limit": 35,
        "fuzzy": true,
        "isChild":isChild

        };

    if (searchOptions && Object.keys(searchOptions).length > 0) {
        requestBody.search = searchOptions;
    }

    if (sortOptions.sort  && Object.keys(sortOptions).length > 0) {
        requestBody.order = 'ASC';
        requestBody.sort = sortOptions.sort;
    }

    xmlReq.open("POST", "https://wheatley.cs.up.ac.za/u23536013/cos221/api.php", true);
    xmlReq.setRequestHeader("Content-Type", "application/json");
    xmlReq.send(JSON.stringify(requestBody));
}

function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

function populateListingsWithImages(Series, listingsContainer,flexContainer) {
    
    listingsContainer.style.display = "none";
    flexContainer.style.display = "none";

    listingsContainer.innerHTML = "";

    if (Series.length === 0) {
        listingsContainer.innerHTML = "<p>No results found.</p>";
        
        listingsContainer.style.display = "flex";
        flexContainer.style.display = "flex";
        return; 
    }

    var index = 0;
    function populateNextListing() {
        while (index < Series.length) {
            var Serie = Series[index];
                            var listingHTML = `
                                <div class="singlelisting">
                                    <div class = "details">
                                        <img src="${Serie.Image}" alt="${Serie.Title_name}">
                                        <a href="https://wheatley.cs.up.ac.za/u23536013/cos221/view.php?Title_ID=${Serie.Title_ID}">&#8505; More info</a>
                                    <div>
                                </div>
                            `;
                            listingsContainer.innerHTML += listingHTML;
                            index++;

                            populateNextListing(); 
                
         
                

                }

                if (index === Series.length) {
                    listingsContainer.style.display = "flex";
                    flexContainer.style.display = "flex";
                }         
    }
    populateNextListing()
   
};
