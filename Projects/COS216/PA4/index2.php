<?php
include 'header.php';
?>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/stylesPA1.css">
</head>
<div id="flex-container">
  <div class="dropdown" id="rentBuyDropdown">
    <button class="dropbtn">Rent/Buy</button>
    <div class="dropdowncont">
      <span data-filter="type" data-value="rent" class="dropdown-item">Rent</span>
      <span data-filter="type" data-value="sale" class="dropdown-item">Buy</span>
    </div>
  </div>
  <div class="dropdown" id="bedroomsDropdown">
    <button class="dropbtn">Bedrooms</button>
    <div class="dropdowncont">
      <span data-filter="bedrooms" data-value="1" class="dropdown-item">1</span>
      <span data-filter="bedrooms" data-value="2" class="dropdown-item">2</span>
      <span data-filter="bedrooms" data-value="3" class="dropdown-item">3</span>
      <span data-filter="bedrooms" data-value="4" class="dropdown-item">4</span>
      <span data-filter="bedrooms" data-value="5" class="dropdown-item">5</span>
      <span data-filter="bedrooms" data-value="6" class="dropdown-item">6+</span>
    </div>
  </div>
  <div class="dropdown" id="bathroomsDropdown">
    <button class="dropbtn">Bathrooms</button>
    <div class="dropdowncont">
      <span data-filter="bathrooms" data-value="1" class="dropdown-item">1</span>
      <span data-filter="bathrooms" data-value="2" class="dropdown-item">2</span>
      <span data-filter="bathrooms" data-value="3" class="dropdown-item">3</span>
      <span data-filter="bathrooms" data-value="4" class="dropdown-item">4+</span>
    </div>
  </div>
  <div class="dropdown" id="garagesDropdown">
    <button class="dropbtn">Garages</button>
    <div class="dropdowncont">
      <span data-filter="parking_spaces" data-value="1" class="dropdown-item">1</span>
      <span data-filter="parking_spaces" data-value="2" class="dropdown-item">2</span>
      <span data-filter="parking_spaces" data-value="3" class="dropdown-item">3</span>
      <span data-filter="parking_spaces" data-value="4" class="dropdown-item">4+</span>
    </div>
  </div>
  <div class="dropdown" id="sortByDropdown">
    <button class="dropbtn">Sort by</button>
    <div class="dropdowncont">
      <span data-filter="sort" data-value="price" class="dropdown-item">Price</span>
      <span data-filter="sort" data-value="title" class="dropdown-item">Title</span>
    </div>
  </div>
  <div class="dropdown" id="cleardropdown">
    <button class="dropbtn">Clear filters</button>
  </div>
  <div class="dropdown" id="savePreferenceButton">
    <button class="dropbtn">Save prefrence</button>
  </div>
</div>

<div class="loader">
  <img src="img/loader.gif" class="loader" alt="Loading...">
</div>

<div class="alllistings">
    <!-- Listings will be dynamically added here -->
</div>

<script src="listings.js"></script>

</body>
</html>

<?php
include 'footer.php';
?>