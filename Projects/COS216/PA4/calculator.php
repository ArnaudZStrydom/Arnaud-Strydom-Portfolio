<?php
include 'header.php';
?>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/calculator.css">
</head>
    <div id="flex-container">
        
    </div>

    <br>


    <div class="row">
        <div class="column" style="background-color:rgb(223, 196, 75);">
            <div id="det1"  class="details">
                <h2>Calculate Down Payment</h2>
                <p>Sale Price:</p>
                <label for="salePrice">R</label>
                <input type="number" id="salePrice" name="salePrice" value="500000">
                <p>Down Payment Percentage:</p>
                <label for="downPaymentPercentage">%</label>
                <input type="number" id="downPaymentPercentage" name="downPaymentPercentage" value="20">
                <br><br><br>
                <button id="calcbtn1" class="calcbtn" >Calculate</button>
                <div id="downPaymentResults">
                    
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="column" style="background-color:rgb(223, 196, 75);">
            <div id="det2" class="details">
                <h2>Calculate Monthly Mortgage Payment</h2>
                <p>Loan Amount:</p>
                <label for="loanAmount">R</label>
                <input type="number" id="loanAmount" name="loanAmount" value="500000">
                <p>Interest Rate:</p>
                <label for="interestRate">%</label>
                <input type="number" id="interestRate" name="interestRate" value="3">
                <p>Loan Term:</p>
                <input type="number" id="loanTerm" name="loanTerm" value="30">
                <label for="loanTerm">Years</label>
                <p>Number of Payments:</p>
                <input type="number" id="numPayments" name="numPayments" value="12">
                <br><br><br>
                <button id="calcbtn2" class="calcbtn">Calculate</button>
                <div id="mortgagePaymentResults">

                </div>
            </div>
        </div>
    </div>

    <script src="calc.js"></script>

</body>

</html>

<?php
include 'footer.php';
?>

