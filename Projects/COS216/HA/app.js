const socket = new io();

socket.on('connect', () => {
    console.log('Connected to server');
});

socket.on('forceDisconnect', () => {
    console.log('Disconnected by server');
    socket.disconnect();
    alert('Server has disconnected you. The application will now close.');
    Server.close();
    window.location.reload();
});

socket.on('auctionStarting', (data) => {
    console.log(`Auction starting: ${data.auctionId}`);
});

socket.on('highestBidUpdated', (data) => {
    console.log(`Highest bid updated for auction ${data.auctionId}: ${data.bidAmount}`);
});

document.addEventListener('DOMContentLoaded', () => {

    const loginButton = document.getElementById("loginButton");
    const createAuctionButton = document.getElementById("create-btn"); 
    const createAuctionchooser = document.getElementById("create-auction-btn"); 
    const viewbtn = document.getElementById("join-auction-btn"); 
    var listingsContainer = document.querySelector(".alllistings");


    loginButton.addEventListener("click", function(event) {
        event.preventDefault();
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        handleLogin(email, password);
    });
    createAuctionchooser.addEventListener("click",function(event){
        document.getElementById("auction-options").style.display = "none";
                    
        document.getElementById("create-auction-section").style.display = "block";
    });
    createAuctionButton.addEventListener("click", function(event) {
        event.preventDefault();
        
        const auctionName = document.getElementById("auctionName").value;
        const startingPrice = parseFloat(document.getElementById("startingPrice").value);
        const startDate = document.getElementById("startDate").value;
        const endDate = document.getElementById("endDate").value;
        const listingID = parseInt(document.getElementById("listingID").value);

        const auctionData = {
            "type": "CreateAuction",
            "auction_name": auctionName,
            "starting_price": startingPrice,
            "start_date": startDate,
            "end_date": endDate,
            "listing_id": listingID
        };
        
       
        createAuction(auctionData);
    });

    viewbtn.addEventListener("click", function(event) {
        fetchListings(listingsContainer,email, password);
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
                    
                    document.getElementById("login-section").style.display = "none";
                    
                    document.getElementById("auction-options").style.display = "block";
                    socket.emit('userConnected', email);
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

    xmlReq.open("POST", "/login", true);
    xmlReq.setRequestHeader("Content-Type", "application/json");
    xmlReq.send(JSON.stringify(requestBody));
}

function createAuction(auctionData) {
    var xmlReq = new XMLHttpRequest();

    xmlReq.onreadystatechange = function() {
        if (xmlReq.readyState === XMLHttpRequest.DONE) {
            if (xmlReq.status === 200) {
                var response = JSON.parse(xmlReq.responseText);
                if (response.status === "Success") {
                    document.getElementById("loginMessage").textContent = "Created auction successful!";
                    
                    document.getElementById("auction-options").style.display = "none";
                    
                    document.getElementById("create-auction-section").style.display = "block";
                } else {
                    console.error("creation failed:", response.status);
                    document.getElementById("loginMessage").textContent = "Creation failed.";
                }
            } else {
                console.error("Creation request failed. HTTP status:", xmlReq.status);
                document.getElementById("loginMessage").textContent = "Login failed. Creation failed";
            }
        }
    };

    xmlReq.open("POST", "/create-auction", true);
    xmlReq.setRequestHeader("Content-Type", "application/json");
    xmlReq.send(JSON.stringify(auctionData));

}



const joinAuctionButton = document.getElementById('join-auction-btn');
joinAuctionButton.addEventListener('click', function(event){

});


function fetchListings(listingsContainer,email, password) {
    var xmlReq = new XMLHttpRequest();
    xmlReq.onreadystatechange = function() {
        if (xmlReq.readyState === XMLHttpRequest.DONE) {
            if (xmlReq.status === 200) {
                var response = JSON.parse(xmlReq.responseText);
                if (response.status === "Success") {
                    var listings = response.data;
                    populateListingsWithImages(listings, listingsContainer);
                } else {
                    console.error("Error retrieving listings:", response.message);
                }
            } else {
                console.error("Error retrieving listings. HTTP status:", xmlReq.status);
            }
        }
    };

    var requestBody = {
    };

    xmlReq.open("POST", "/getAuctionListings", true);
    xmlReq.setRequestHeader("Content-Type", "application/json");
    xmlReq.send(JSON.stringify(requestBody));
}



function populateListingsWithImages(listings, listingsContainer, ) {
    document.getElementById("auction-options").style.display = "none";
                    
    document.getElementById("auction-details").style.display = "block";
    listingsContainer.innerHTML = "";

    if (listings.length === 0) {
        listingsContainer.innerHTML = "<p>No results found.</p>";
        listingsContainer.style.display = "flex";
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
                                        <button class="place-bid-btn" data-listing-id=${listing.id} data-auction-id=${listing.auction_id}>Join Auction</button>
                                    <div>
                                </div>
                            `;
                            listingsContainer.innerHTML += listingHTML;
                            index++;

                            populateNextListing(); 
                
         
                

                }
    }
    populateNextListing();
    attachBidButtonListeners();
};

function attachBidButtonListeners() {
    var bidbtns = document.querySelectorAll('.place-bid-btn');
    bidbtns.forEach(function(button) {
        button.addEventListener('click', function() {
            var listingId = parseInt(button.dataset.listingId);
            var auctionId = button.dataset.auctionId;
            fetchListingDetails(listingId,auctionId);
        });
    });
}

var listingDetailsContainer = document.querySelector(".allinfo");

    function fetchListingDetails(listingId,auctionId) {
        listingDetailsContainer.style.display = "none";


        var xmlReq = new XMLHttpRequest();
        xmlReq.onreadystatechange = function() {
            if (xmlReq.readyState === XMLHttpRequest.DONE) {
                if (xmlReq.status === 200) {
                    var response = JSON.parse(xmlReq.responseText);
                    if (response.status === "Success") {
                        var listing = response.data[0]; 
                        populateListingDetails(listing,auctionId);
                    } else {
                        console.error("Error fetching listing details:", response.message);
                    }
                } else {
                    console.error("Error fetching listing details. HTTP status:", xmlReq.status);
                }
            }
        };

        var requestBody = {
            "listing_id": parseInt(listingId),
            "auction_id": auctionId
        };

        xmlReq.open("POST", "/ViewBid", true);
        xmlReq.setRequestHeader("Content-Type", "application/json");
        xmlReq.send(JSON.stringify(requestBody));
    }

    async function populateListingDetails(listing, auctionId) {
        document.getElementById("auction-details").style.display = "none";
        document.getElementById("auction-view-details").style.display = "block";
        var highestbidcontainer = document.getElementById("highestbidprice");
        listingDetailsContainer.style.display = "flex";
    
        const highestBidPrice = await getHighestBidPrice(auctionId);
        console.log("The highest bid price is:", highestBidPrice);
    
        fetchListingImages(listing.id, listing);
    
        const numBathrooms = listing.bathrooms;
        const numRooms = listing.bedrooms;
        const numParkingSpaces = listing.parking_spaces;
        var numAmenities = 0;
    
        if (listing.amenities.length === 0) {
            numAmenities = 4;
        } else {
            for (let i = 0; i < listing.amenities.length; i++) {
                if (listing.amenities[i] === ',') {
                    numAmenities++;
                }
            }
        }
    
        const price = listing.price;
        const type = listing.type;
    
        const weights = {
            bathroomsWeight: 0.2,
            roomsWeight: 0.2,
            parkingSpacesWeight: 0.15,
            amenitiesWeight: 0.25,
            priceWeight: 0.2
        };
    
        var normalizedPrice = price;
    
        if (type === 'sale') {
            if (price < 1000000) {
                normalizedPrice = price / 1000000;
            } else {
                normalizedPrice = price / 10000000;
            }
        } else {
            normalizedPrice = price / 1000;
        }
    
        const bathroomsScore = numBathrooms * 20;
        const roomsScore = numRooms * 15;
        const parkingSpacesScore = numParkingSpaces * 10;
        const amenitiesScore = numAmenities * 20;
        const priceScore = normalizedPrice * 10;
    
        const weightedBathroomsScore = bathroomsScore * weights.bathroomsWeight;
        const weightedRoomsScore = roomsScore * weights.roomsWeight;
        const weightedParkingSpacesScore = parkingSpacesScore * weights.parkingSpacesWeight;
        const weightedAmenitiesScore = amenitiesScore * weights.amenitiesWeight;
        const weightedPriceScore = priceScore * weights.priceWeight;
    
        const propertyScore = ((weightedBathroomsScore + weightedRoomsScore + weightedParkingSpacesScore + weightedAmenitiesScore + weightedPriceScore) / 10).toFixed(2);
    
        highestbidcontainer.innerHTML = `
        <h2>R${highestBidPrice}</h2>
        <div class="mb-3">
            <label for="BidPrice" class="form-label">Bid Ammount:</label>
            <input type="number" class="form-control" id="startingPrice" name="startingPrice" step="0.01" required>
            <p>Please place a bid higher than the current highest bid</p>
            <button type="submit"  id="newbid-btn" class="btn btn-primary" data-listing-id=${listing.id} data-auction-id=${auctionId}>Place Bid</button>
        </div>
        `;
        var detailsContainer = document.querySelector(".biddetails");
        detailsContainer.innerHTML = `
            <h2>${listing.title}</h2>
            <p>Rent/Buy: ${listing.type}</p>
            <p>Location: ${listing.location}</p>
            <p>Bedrooms: ${listing.bedrooms}</p>
            <p>Bathrooms: ${listing.bathrooms}</p>
            <p>Description: ${listing.description}</p>
            <p>Amenities: ${listing.amenities}</p>
            <p>Garages: ${listing.parking_spaces}</p>
            <p>Property Score: ${propertyScore} &#11088;</p>
        `;

        attachnewBidButtonListeners();
    }

    function attachnewBidButtonListeners() {
        var newbidbtns = document.getElementById("newbid-btn");
       
        newbidbtns.addEventListener('click', function() {
                var listingId = parseInt(button.dataset.listingId);
                var auctionId = button.dataset.auctionId;
                updatebid(listingId,auctionId);
            });
      
    }

    function fetchListingImages(listingId,listing) {
        
        populateImageCarousel(listing.images.data,listing);
                    
            
        };



    function populateImageCarousel(images,listing) {
        var imageCarousel = document.getElementById("HouseView-container");
        var listingHTML=`
        <div class="mySlides active">
            <img src="${images[0]}" alt="${listing.title}">
        </div>
    `;
        imageCarousel.innerHTML += listingHTML ;
        for(let i = 1 ; i < images.length - 1;i++) {
        var listingHTML=`
            <div class="mySlides">
                <img src="${images[i]}" alt="${listing.title}">
            </div>
        `;
            imageCarousel.innerHTML += listingHTML ;
        }

        imageCarousel.innerHTML+=`
            <a class="prevbtn" onclick="plusSlides(-1)">&#8592</a>
            <a class="nxtbtn" onclick="plusSlides(1)">&#8594</a>
        `;
        
        var listingHTML=`
            <footer class="footer1">
                <div id = "footer" style="text-align:center">
            
                </div>
            </footer>
        `;

        imageCarousel.innerHTML+=listingHTML;

        var footer = document.getElementById("footer");
        for(let i = 0 ; i < images.length;i++) {
            var listingHTML=`
            <span class="bullets" onclick="currentSlide(${i+1})"></span> 
            `;
            footer.innerHTML += listingHTML ;
        }

        footer.innerHTML +=`
        <div class="numbertext">1 / ${images.length}</div>
        `;

        showSlides(slideIndex);
    }


var slideIndex = 1;

function plusSlides(n)
{
  showSlides(slideIndex += n);
}

function currentSlide(n) {
  showSlides(slideIndex = n);
}

function showSlides(n) {
  var i;
  var slides = document.getElementsByClassName("mySlides");
  var bullets = document.getElementsByClassName("bullets");
  var numbertext = document.getElementsByClassName("numbertext")[0];

  if (n > slides.length) {
    slideIndex = 1;
  }    
  if (n < 1) {
    slideIndex = slides.length;
  }
  for (i = 0; i < slides.length; i++) {
      slides[i].style.display = "none";  
  }
  for (i = 0; i < bullets.length; i++) {
      bullets[i].className = bullets[i].className.replace(" active", "");
  }
  slides[slideIndex-1].style.display = "block";  
  bullets[slideIndex-1].className += " active";
  numbertext.innerHTML = slideIndex + " / " + slides.length;
}

var listingDetailsContainer = document.querySelector(".allinfo");

function fetchHighestBid(auctionId) {
    return new Promise((resolve, reject) => {
        var xmlReq = new XMLHttpRequest();
        xmlReq.onreadystatechange = function() {
            if (xmlReq.readyState === XMLHttpRequest.DONE) {
                if (xmlReq.status === 200) {
                    var response = JSON.parse(xmlReq.responseText);
                    if (response.status === "Success") {
                        var auction = response.data; 
                        resolve(auction.highest_bid);
                    } else {
                        console.error("Error fetching listing details:", response.message);
                        reject(new Error("Error fetching listing details: " + response.message));
                    }
                } else {
                    console.error("Error fetching listing details. HTTP status:", xmlReq.status);
                    reject(new Error("HTTP status: " + xmlReq.status));
                }
            }
        };

        var requestBody = {
            "auction_id": auctionId
        };

        xmlReq.open("POST", "/highestbid", true);
        xmlReq.setRequestHeader("Content-Type", "application/json");
        xmlReq.send(JSON.stringify(requestBody));
    });
}

async function getHighestBidPrice(auctionId) {
    try {
        const highestBidPrice = await fetchHighestBid(auctionId);
        console.log("Highest bid:", highestBidPrice);
        return highestBidPrice;
    } catch (error) {
        console.error("Failed to fetch highest bid:", error);
    }
}