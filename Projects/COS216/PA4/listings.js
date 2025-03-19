document.addEventListener("DOMContentLoaded", function() {
    var searchOptions = {}; 
    var sortOptions = {}; 

    var searchInput = document.getElementById("searchInput");
    var loader = document.querySelector(".loader");
    var listingsContainer = document.querySelector(".alllistings");
    var flexContainer = document.getElementById("flex-container"); 
    var apiKey = getCookie("apikey");
    if (!apiKey) {
        listingsContainer.style.display = "none";
        flexContainer.style.display = "none";
        loader.style.display = "none";
    
        var loginMessageContainer = document.createElement("div");
        loginMessageContainer.classList.add("login-message-container");
    
        var loginMessage = document.createElement("p");
        loginMessage.textContent = "Please log in to view listings.";
    
        loginMessageContainer.appendChild(loginMessage);
        document.body.appendChild(loginMessageContainer);
    
        return;
    }

    handleLoadPreferences(apiKey);


    function handleLoadPreferences(apiKey) {
        var xmlReq = new XMLHttpRequest();
        var url = "https://wheatley.cs.up.ac.za/u23536013/api.php";

        xmlReq.open("POST", url, true);
        xmlReq.setRequestHeader("Content-Type", "application/json");

        xmlReq.onreadystatechange = function() {
            if (xmlReq.readyState === XMLHttpRequest.DONE) {
                if (xmlReq.status === 200) {
                    var response = JSON.parse(xmlReq.responseText);
                    if (response.status === "Success") {
                        var listings = response.data;
                        populateListingsWithImages(listings, listingsContainer, loader,flexContainer);
                    } else {
                        console.error("Error retrieving listings:", response.message);
                    }
                } else {
                    console.error("Error retrieving listings. HTTP status:", xmlReq.status);
                }
            }
        };

        xmlReq.onerror = function () {
            console.error("Error occurred while loading preferences.");
        };

        var requestBody = {
            "type": "LoadPreferences",
            "apikey": apiKey
        };

        xmlReq.send(JSON.stringify(requestBody));
    }
    


    searchInput.addEventListener("keyup", function(event) {
        if (event.key === "Enter") {
            var searchTerm = searchInput.value.trim();
            if (searchTerm !== "Search..") {
                searchOptions = { ...searchOptions, location: searchTerm }; 
            } else {
                delete searchOptions.location; 
            }
            fetchListings(listingsContainer, loader,flexContainer, searchOptions, sortOptions); 
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
                
                if (filterType === "type") {
                    searchOptions = { ...searchOptions, [filterType]: filterValue }; 
                } else if (filterType === "bedrooms" || filterType === "bathrooms" || filterType === "parking_spaces") {
                    searchOptions = { ...searchOptions, [filterType]: parseInt(filterValue) }; 
                } else if (filterType === "sort") {
                    sortOptions = { [filterType]: filterValue }; 
                }
                
                fetchListings(listingsContainer, loader,flexContainer, searchOptions, sortOptions); 
                dropdown.classList.remove('active');
            });
        });
    });



    var clearFiltersButton = document.getElementById('cleardropdown');
    clearFiltersButton.addEventListener('click', function() {
        searchOptions = {}; 
        sortOptions = {}; 
        fetchListings(listingsContainer, loader,flexContainer, searchOptions, sortOptions); 
    });

    var savePreferenceButton = document.getElementById("savePreferenceButton");
    savePreferenceButton.addEventListener("click", function() {
        var apiKey = getCookie('apikey');
        var preferences = {
            "type": "SavePreferences",
            "apikey": apiKey,
            "filters": searchOptions,
            "sorting_option": sortOptions
        };
        savePreferences(preferences);
    });

    function savePreferences(preferences) {
        var xhr = new XMLHttpRequest();
        var url = "https://wheatley.cs.up.ac.za/u23536013/api.php";
        var apiKey = getCookie("apikey");
    
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");

    
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    console.log("Preferences saved successfully.");
                } else {
                    console.error("Failed to save preferences.");
                }
            }
        };
    
        xhr.onerror = function () {
            console.error("Error occurred while saving preferences.");
        };
    
        xhr.send(JSON.stringify(preferences));
    }
});

function fetchListings(listingsContainer, loader,flexContainer, searchOptions = {}, sortOptions = {}) {
    loader.style.display = "block";
    listingsContainer.style.display = "none";
    flexContainer.style.display = "none";
    var xmlReq = new XMLHttpRequest();
    xmlReq.onreadystatechange = function() {
        if (xmlReq.readyState === XMLHttpRequest.DONE) {
            if (xmlReq.status === 200) {
                var response = JSON.parse(xmlReq.responseText);
                if (response.status === "Success") {
                    var listings = response.data;
                    populateListingsWithImages(listings, listingsContainer, loader,flexContainer);
                } else {
                    console.error("Error retrieving listings:", response.message);
                }
            } else {
                console.error("Error retrieving listings. HTTP status:", xmlReq.status);
            }
        }
    };

    var apiKey = getCookie('apikey');
    var requestBody = {
        "type": "GetAllListings",
        "apikey": apiKey,
        "return": "*",
        "limit": 35,
        "fuzzy": true,

        };

    if (searchOptions && Object.keys(searchOptions).length > 0) {
        requestBody.search = searchOptions;
    }

    if (sortOptions.sort  && Object.keys(sortOptions).length > 0) {
        requestBody.order = 'ASC';
        requestBody.sort = sortOptions.sort;
    }

    xmlReq.open("POST", "https://wheatley.cs.up.ac.za/u23536013/api.php", true);
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

function populateListingsWithImages(listings, listingsContainer, loader,flexContainer) {
    loader.style.display = "block";
    listingsContainer.style.display = "none";
    flexContainer.style.display = "none";

    listingsContainer.innerHTML = "";

    if (listings.length === 0) {
        listingsContainer.innerHTML = "<p>No results found.</p>";
        loader.style.display = "none";
        listingsContainer.style.display = "flex";
        flexContainer.style.display = "flex";
        return; 
    }

    var index = 0;
    function populateNextListing() {
        while (index < listings.length) {
            var listing = listings[index];
                            var listingHTML = `
                                <div class="singlelisting">
                                    <div class = "details">
                                        <img src="${listing.images.data[0]}" alt="${listing.title}">
                                        <h2>Price: R${listing.price}</h2>
                                        <p>Rent/Buy: ${listing.type}</p>
                                        <p>Location: ${listing.location}</p>
                                        <p>Bedrooms: ${listing.bedrooms}</p>
                                        <p>Bathrooms: ${listing.bathrooms}</p>
                                        <p>${listing.title}</p>
                                        <button class="favoritebtn" data-listing-id="${listing.id}">&#10084; Add to Favorites</button>
                                        <a href="https://wheatley.cs.up.ac.za/u23536013/COS216/PA4/view.php?listing_id=${listing.id}">&#8505; More info</a>
                                    <div>
                                </div>
                            `;
                            listingsContainer.innerHTML += listingHTML;
                            index++;

                            populateNextListing(); 
                
         
                

                }

                if (index === listings.length) {
                    loader.style.display = "none";
                    listingsContainer.style.display = "flex";
                    flexContainer.style.display = "flex";
                }         
    }
    populateNextListing()
    attachFavoriteButtonListeners();
};

function attachFavoriteButtonListeners() {
    var favoriteButtons = document.querySelectorAll('.favoritebtn');
    favoriteButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            var listingId = parseInt(button.dataset.listingId);
            addToFavorites(listingId);
        });
    });
}

function addToFavorites(listingId) {
    
    var apiKey = getCookie('apikey');
    var requestBody = {
        type: 'AddToFavorites',
        apikey: apiKey,
        listingId: listingId
    };

    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'https://wheatley.cs.up.ac.za/u23536013/api.php', true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                console.log('Listing added to favorites successfully.');
             
            } else {
                console.error('Failed to add listing to favorites.');
                
            }
        }
    };

    xhr.onerror = function () {
        console.error('Error occurred while adding listing to favorites.');
    };

    xhr.send(JSON.stringify(requestBody));
}


