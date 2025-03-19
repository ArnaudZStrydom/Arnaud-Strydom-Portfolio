function myFunction1(){
    document.getElementById("mybox").style.backgroundColor="red";
}

function myFunction2(){
    document.getElementById("mybox").style.backgroundColor="blue";
}

document.getElementById("mybox").addEventListener("mouseenter",myFunction1);
document.getElementById("mybox").addEventListener("mouseleave",myFunction2);



function lastClicked(click){
    if(document.getElementById("lastpressed").innerHTML.length > 20){
        document.getElementById("lastpressed").innerHTML = "";
        document.getElementById("hiddenimg").style.display="inline";
        setTimeout(() => {
            document.getElementById("hiddenimg").style.display="none";
        }, 2000);
    }
    else{
        document.getElementById("lastpressed").innerHTML += click.key;
    }
}


document.body.addEventListener("keypress",lastClicked);


