document.addEventListener("DOMContentLoaded", function() {
    var searchOptions = {}; 

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

    searchInput.addEventListener("keyup", function(event) {
        if (event.key === "Enter") {
            var searchTerm = searchInput.value.trim();
            if (searchTerm !== "Search..") {
                searchOptions = { ...searchOptions, location: searchTerm }; 
            } else {
                delete searchOptions.location; 
            }
            fetchFavoriteListings(listingsContainer, loader,flexContainer, searchOptions); 
        }
    });

    fetchFavoriteListings();

    function fetchFavoriteListings() {
        var apiKey = getCookie('apikey');
        var xmlReq = new XMLHttpRequest();
        xmlReq.onreadystatechange = function() {
            if (xmlReq.readyState === XMLHttpRequest.DONE) {
                if (xmlReq.status === 200) {
                    var response = JSON.parse(xmlReq.responseText);
                    if (response.status === "Success") {
                        var favorites = response.data;
                        populateFavoriteListings(favorites, listingsContainer, loader, flexContainer);
                        attachFavoriteButtonListeners(); 
                    } else {
                        console.error("Error retrieving favorite listings:", response.message);
                    }
                } else {
                    console.error("Error retrieving favorite listings. HTTP status:", xmlReq.status);
                }
            }
        };
    
        var requestBody = {
            "type": "GetAllFavouriteListings",
            "apikey": apiKey,
            "fuzzy": true,
        };

        if (searchOptions && Object.keys(searchOptions).length > 0) {
            requestBody.search = searchOptions;
        }
    
        xmlReq.open("POST", "https://wheatley.cs.up.ac.za/u23536013/api.php", true);
        xmlReq.setRequestHeader("Content-Type", "application/json");
        xmlReq.send(JSON.stringify(requestBody));
    }


    function attachFavoriteButtonListeners() {
        var favoriteButtons = document.querySelectorAll('.favoritebtn');
        favoriteButtons.forEach(function(button) {
            button.addEventListener('click', function() {
                var listingId = parseInt(button.dataset.listingId); 
                removeFromFavorites(listingId); 
            });
        });
    }

    function removeFromFavorites(listingId) {
        var apiKey = getCookie('apikey');
        var xmlReq = new XMLHttpRequest();
        xmlReq.onreadystatechange = function() {
            if (xmlReq.readyState === XMLHttpRequest.DONE) {
                if (xmlReq.status === 200) {
                    var response = JSON.parse(xmlReq.responseText);
                    if (response.status === "Success") {
                        fetchFavoriteListings();
                    } else {
                        console.error("Error removing from favorites:", response.message);
                    }
                } else {
                    console.error("Error removing from favorites. HTTP status:", xmlReq.status);
                }
            }
        };

        var requestBody = {
            "type": "RemoveFromFavorites",
            "apikey": apiKey,
            "listing_id": listingId
        };

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

    function populateFavoriteListings(listings, listingsContainer, loader,flexContainer) {
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
                                        <img src="${listing.images.data[0]}" alt="${listing.title}">
                                        <h2>Price: R${listing.price}</h2>
                                        <p>Rent/Buy: ${listing.type}</p>
                                        <p>Location: ${listing.location}</p>
                                        <p>Bedrooms: ${listing.bedrooms}</p>
                                        <p>Bathrooms: ${listing.bathrooms}</p>
                                        <p>${listing.title}</p>
                                        <button class="favoritebtn" data-listing-id="${listing.id}">&#x1F494; Remove from Favorites</button>
                                        <a href="https://wheatley.cs.up.ac.za/u23536013/COS216/PA4/view.php?listing_id=${listing.id}">&#8505; More info</a>
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
    };
});

