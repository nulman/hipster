//chooses a random phrase from the array to display at the top bar on click
function showrandom1() {
    var quotes = ["you're special because you like sepia tones, thick rim glasses and soy milk", 
                  "does following other people make you less or more hipster?", 
                  "does it come in plaid?",
                  "do you also drink cofffee before it's cool?  cause that's dangerous",
                  "try a sepia filter.  everything looks better with a sepia filter",
                  "social networks?  so mainstream..",
                  "republish is so 1954. which is great.",
                  "this will look great with a bit of blur and some deep inspirational quote in helvetica"];
    var quote = quotes[Math.floor(Math.random() * quotes.length)];
    document.getElementById("quote").innerHTML = quote;
  };
 
  //clears form after submission
  function resetform() {
	  document.getElementById("newpost").reset();
	  return false;
	  };
	  
//changes 'follow' icon to OK after following
 function followOk() {
	document.getElementById("folsuccess").className = "glyphicon glyphicon-ok";
	return true;
};


 //word counter for the login page description
$(document).ready(function() {
    $("#logindescription").on('keydown', function(e) {
    	var words = $.trim(this.value).length ? this.value.match(/\S+/g).length : 0;
    	
        if (words <= 50) {
            $('#wordsleft').text(50-words)
        }else{
            if (e.which !== 8) e.preventDefault();
        }
    });
 }); 

