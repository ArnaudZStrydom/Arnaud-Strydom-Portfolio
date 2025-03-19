document.addEventListener("DOMContentLoaded", function() {
    var searchOptions = {}; 
    var sortOptions = {}; 

    var searchInput = document.getElementById("searchInput");
    var loader = document.querySelector(".loader");
    var listingsContainer = document.querySelector(".alllistings");
    var flexContainer = document.getElementById("flex-container"); 
    fetchListings(listingsContainer, loader,flexContainer); 

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
                if (response.status === "success") {
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

    var requestBody = {
        "studentnum": "u23536013",
        "apikey": "06a1f50b0dd8a2299328d39e8fb30526",
        "type": "GetAllListings",
        "limit": 35,
        "return": '*',
    };

    if (searchOptions && Object.keys(searchOptions).length > 0) {
        requestBody.search = searchOptions;
    }

    if (sortOptions.sort  && Object.keys(sortOptions).length > 0) {
        requestBody.order = 'ASC';
        requestBody.sort = sortOptions.sort;
    }

    xmlReq.open("POST", "https://wheatley.cs.up.ac.za/api/", true);
    xmlReq.setRequestHeader("Content-Type", "application/json");
    xmlReq.send(JSON.stringify(requestBody));
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
        if (index < listings.length) {
            var listing = listings[index];
            var xmlReq = new XMLHttpRequest();
            xmlReq.onreadystatechange = function() {
                if (xmlReq.readyState === XMLHttpRequest.DONE) {
                    if (xmlReq.status === 200) {
                        var response = JSON.parse(xmlReq.responseText);
                        if (response.status === "success") {
                            var images = response.data;
                            var listingHTML = `
                                <div class="singlelisting">
                                    <img src="${images[0]}" alt="${listing.title}">
                                    <h2>Price: R${listing.price}</h2>
                                    <p>Rent/Buy: ${listing.type}</p>
                                    <p>Location: ${listing.location}</p>
                                    <p>Bedrooms: ${listing.bedrooms}</p>
                                    <p>Bathrooms: ${listing.bathrooms}</p>
                                    <p>${listing.title}</p>
                                    <button class="favoritebtn">&#10084; Add to Favorites</button>
                                    <a href="https://wheatley.cs.up.ac.za/u23536013/COS216/PA2/view.html?listing_id=${listing.id}">&#8505; More info</a>
                                </div>
                            `;
                            listingsContainer.innerHTML += listingHTML;
                            index++;
                            populateNextListing(); 
                        } else {
                            console.error("Error retrieving images for listing:", listing.title);
                            index++;
                            populateNextListing(); 
                        }
                    } else {
                        console.error("Error retrieving images for listing:", listing.title, "HTTP status:", xmlReq.status);
                        index++;
                        populateNextListing(); 
                    }
                }
                
                
                if (index === listings.length) {
                    loader.style.display = "none";
                    listingsContainer.style.display = "flex";
                    flexContainer.style.display = "flex";
                }
            };
            xmlReq.open("GET", `https://wheatley.cs.up.ac.za/api/getimage?listing_id=${listing.id}`, true);
            xmlReq.send();
        }
    }

    populateNextListing(); 
}
