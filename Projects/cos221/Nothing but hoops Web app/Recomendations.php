<?php
include 'header.php';
?>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/stylesPA1.css">
</head>
<div id="flex-container">

  <div class="dropdown" id="filterbyGenre">
    <button class="dropbtn">Filter by Genre</button>
    <div class="dropdowncont">
      <span data-filter="Genre_Name" data-value="action" class="dropdown-item">action</span>
      <span data-filter="Genre_Name" data-value="animation" class="dropdown-item">animation</span>
      <span data-filter="Genre_Name" data-value="comedy" class="dropdown-item">comedy</span>
      <span data-filter="Genre_Name" data-value="crime" class="dropdown-item">crime</span>
      <span data-filter="Genre_Name" data-value="documentation" class="dropdown-item">documentation</span>
      <span data-filter="Genre_Name" data-value="drama" class="dropdown-item">drama</span>
      <span data-filter="Genre_Name" data-value="european" class="dropdown-item">european</span>
      <span data-filter="Genre_Name" data-value="family" class="dropdown-item">family</span>
      <span data-filter="Genre_Name" data-value="fantasy" class="dropdown-item">fantasy</span>
      <span data-filter="Genre_Name" data-value="history" class="dropdown-item">history</span>
      <span data-filter="Genre_Name" data-value="horror" class="dropdown-item">horror</span>
      <span data-filter="Genre_Name" data-value="music" class="dropdown-item">music</span>
      <span data-filter="Genre_Name" data-value="reality" class="dropdown-item">reality</span>
      <span data-filter="Genre_Name" data-value="romance" class="dropdown-item">romance</span>
      <span data-filter="Genre_Name" data-value="scifi" class="dropdown-item">scifi</span>
      <span data-filter="Genre_Name" data-value="sport" class="dropdown-item">sport</span>
      <span data-filter="Genre_Name" data-value="thriller" class="dropdown-item">thriller</span>
      <span data-filter="Genre_Name" data-value="war" class="dropdown-item">war</span>
      <span data-filter="Genre_Name" data-value="western" class="dropdown-item">western</span>
    </div>
  </div>
  <div class="dropdown" id="filterbyrating">
    <button class="dropbtn">Filter by rating</button>
    <div class="dropdowncont">
      <span data-filter="Review_Rating" data-value="1" class="dropdown-item">1</span>
      <span data-filter="Review_Rating" data-value="2" class="dropdown-item">2</span>
      <span data-filter="Review_Rating" data-value="3" class="dropdown-item">3</span>
      <span data-filter="Review_Rating" data-value="4" class="dropdown-item">4</span>
      <span data-filter="Review_Rating" data-value="5" class="dropdown-item">5</span>
      
    </div>
  </div>

  <div class="dropdown" id="filterlanguage">
    <button class="dropbtn">Filter by language</button>
    <div class="dropdowncont">
      <span data-filter="Language_Name" data-value="English" class="dropdown-item">English</span>
      <span data-filter="Language_Name" data-value="Spanish" class="dropdown-item">Spanish</span>
      <span data-filter="Language_Name" data-value="German" class="dropdown-item">German</span>
      <span data-filter="Language_Name" data-value="Afrikaans" class="dropdown-item">Afrikaans</span>
      <span data-filter="Language_Name" data-value="Arabic" class="dropdown-item">Arabic</span>
      <span data-filter="Language_Name" data-value="Portugese" class="dropdown-item">Portugese</span>
      <span data-filter="Language_Name" data-value="Dutch" class="dropdown-item">Dutch</span>
      <span data-filter="Language_Name" data-value="Zulu" class="dropdown-item">Zulu</span>
      <span data-filter="Language_Name" data-value="isiXhosa" class="dropdown-item">isiXhosa</span>
    </div>
  </div>
  <div class="dropdown" id="sortByDropdown">
    <button class="dropbtn">Sort by</button>
    <div class="dropdowncont">
      <span data-filter="sort" data-value="Release_Date" class="dropdown-item">Release Date</span>
      <span data-filter="sort" data-value="Title_Name" class="dropdown-item">Title</span>
      <span data-filter="sort" data-value="Review_Rating" class="dropdown-item">Rating</span>

    </div>
  </div>
  <div class="dropdown" id="cleardropdown">
    <button class="dropbtn">Clear filters</button>
  </div>
</div>

<div class="alllistings">
    <!-- Recomendation Listings will be dynamically added here -->
</div>


<script src="Recomendations.js"></script>

</body>
</html>

<?php
include 'footer.php';
?>