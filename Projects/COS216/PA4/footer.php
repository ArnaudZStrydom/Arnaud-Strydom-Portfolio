<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Footer Styling</title>
    <link rel="stylesheet" href="css/footer.css">
</head>
<body>
    <footer>
    <button id="themeToggle">Toggle theme Mode</button>
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <h3>About Us</h3>
                <p>We are a group of experts in real estate we have combined our shared knoledge to create a place for all your housing needs. </p>
            </div>
            <div class="col-md-3">
                <h3>Quick Links</h3>
                <ul>
                    <li><a href="https://wheatley.cs.up.ac.za/u23536013/index.php">Home</a></li>
                    <li> <a href="https://wheatley.cs.up.ac.za/u23536013/COS216/PA4/index2.php">Listings</a></li>
                    <li><a href="https://wheatley.cs.up.ac.za/u23536013/COS216/PA4/agents.php">Agents</a></li>
                    <li><a href="https://wheatley.cs.up.ac.za/u23536013/COS216/PA4/calculator.php">Calculators</a></li>
                    <li><a href="https://wheatley.cs.up.ac.za/u23536013/COS216/PA4/favourites.php">Favourites</a> </li>
                </ul>
            </div>
            <div class="col-md-3">
                <h3>Contact Us</h3>
                <ul>
                    <li><a href="u23536013@tuks.co.za"> Email: u23536013@tuks.co.za</li>
                    <li><a href="+27 62 562 6624">Phone: +27 62 562 6624</li>
                    <li>Address: University of Pretoria</li>
                </ul>
            </div>
        </div>
    </div>
    <div class="footer-bottom">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <p>&copy; <?php echo date("Y"); ?> University of Pretoria. All rights reserved.</p>
                </div>
            </div>
        </div>
    </div>
</footer>
<script src="footer.js"></script>
</body>
</html>