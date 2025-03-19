//I chose to use asynchronous calls because they prevent UI blocking 
//and enhance responsiveness by allowing other code to run in the background while waiting for the asynchronous operation to finish.
//Asynchronous operations are perfect for applications that need to be responsive and scalable since they can handle multiple tasks at once.
document.addEventListener("DOMContentLoaded", function() {
    var loader = document.querySelector(".loader");
    var listingDetailsContainer = document.querySelector(".allinfo");


    var urlParams = new URLSearchParams(window.location.search);
    var listingId = urlParams.get('listing_id');

    fetchListingDetails(listingId);

    function fetchListingDetails(listingId) {
        loader.style.display = "block";
        listingDetailsContainer.style.display = "none";


        var xmlReq = new XMLHttpRequest();
        xmlReq.onreadystatechange = function() {
            if (xmlReq.readyState === XMLHttpRequest.DONE) {
                if (xmlReq.status === 200) {
                    var response = JSON.parse(xmlReq.responseText);
                    if (response.status === "Success") {
                        var listing = response.data[0]; 
                        populateListingDetails(listing);
                    } else {
                        console.error("Error fetching listing details:", response.message);
                    }
                } else {
                    console.error("Error fetching listing details. HTTP status:", xmlReq.status);
                }
            }
        };

        var requestBody = {
            "apikey": "7jyY5bkDmyqDdzOOVLxgcQxY5Oa",
            "type": "GetAllListings",
            "limit": 1,
            "search": {
                "id": parseInt(listingId)
            },
            "return": "*"
        };

        xmlReq.open("POST", "/viewAuction", true);
        xmlReq.setRequestHeader("Content-Type", "application/json");
        xmlReq.send(JSON.stringify(requestBody));
    }

    function populateListingDetails(listing) {
       
        loader.style.display = "none";
        listingDetailsContainer.style.display = "flex";

       
        fetchListingImages(listing.id,listing);
        
        const numBathrooms = listing.bathrooms;
        const numRooms = listing.bedrooms;
        const numParkingSpaces = listing.parking_spaces;
        var numAmenities = 0;
        var numberamen = 0 ;
        if(listing.amenities.length === 0){
            numAmenities = 4 ;
        }else{
            for(let i = 0 ; i < listing.amenities.length ; i++) {
                if(listing.amenities[i] === ','){
                    numAmenities++;
                }
                
            }
        }
        
        const price = listing.price; 
        const type = listing.type ;
        
        
        const weights = {
            bathroomsWeight: 0.2,
            roomsWeight: 0.2,
            parkingSpacesWeight: 0.15,
            amenitiesWeight: 0.25,
            priceWeight: 0.2
        };
        
        var normalizedPrice = price;
        
        if(type === 'sale'){
           if(price < 1000000){
                normalizedPrice = price/1000000 ;
           }else{
                normalizedPrice = price/10000000 ;
           }
        }
        else{
             normalizedPrice = price / 1000 ;
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
        
        
        const propertyScore = ((weightedBathroomsScore + weightedRoomsScore + weightedParkingSpacesScore + weightedAmenitiesScore + weightedPriceScore)/10).toFixed(2);


        var detailsContainer = document.querySelector(".details");
        detailsContainer.innerHTML = `
            <h2>${listing.title}</h2>
            <p>Price: R${listing.price}</p>
            <p>Rent/Buy: ${listing.type}</p>
            <p>Location: ${listing.location}</p>
            <p>Bedrooms: ${listing.bedrooms}</p>
            <p>Bathrooms: ${listing.bathrooms}</p>
            <p>Description: ${listing.description}</p>
            <p>amenities: ${listing.amenities}</p>
            <p>Garages: ${listing.parking_spaces}</p>
            <p>property Score: ${propertyScore} &#11088;</p>
            
        `;
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
});

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

