document.addEventListener("DOMContentLoaded", function() {
    
    var downPaymentBtns = document.getElementById('calcbtn1');
    var mortgagePaymentBtns = document.getElementById('calcbtn2');
    var downPaymentResults = document.getElementById('downPaymentResults');
    var mortgagePaymentResults = document.getElementById('mortgagePaymentResults');

    
    downPaymentBtns.addEventListener('click', calculateDownPayment);
    mortgagePaymentBtns.addEventListener('click', calculateMortgagePayment);

 
    function calculateDownPayment() {
        var salePrice = parseFloat(document.getElementById('salePrice').value);
        var depositPercentage = parseFloat(document.getElementById('downPaymentPercentage').value) / 100;

        var downPayment = salePrice * depositPercentage;


        downPaymentResults.innerHTML = '';

        downPaymentResults.innerHTML += `
            <h2>Down Payment Amount: R${downPayment.toFixed(2)}</h2>
        `;
    }

    
    function calculateMortgagePayment() {
        var P = parseFloat(document.getElementById('loanAmount').value) ;
        var i = parseFloat(document.getElementById('interestRate').value) / 100 / 12;
        var years = parseFloat(document.getElementById('loanTerm').value) ;
        var numpay = parseFloat(document.getElementById('numPayments').value) ;
        var n = numpay * years ;
        var M = P * i * Math.pow(1 + i, n) / (Math.pow(1 + i, n) - 1);

       
        mortgagePaymentResults.innerHTML = '';

        mortgagePaymentResults.innerHTML += `
            <h2>Monthly Mortgage Payment: R${M.toFixed(2)} P/M</h2>
        `;
    }
});