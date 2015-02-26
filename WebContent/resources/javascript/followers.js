function func() {
		var user= [{"DESCRIPTION":"woohoo","STALKERS":"0","POPULARITY":"1.0","USERNAME":"Barry","NICKNAME":"otherBarry","PIC":"http://assets.sbnation.com/assets/1311490/barry.jpg","USER_ID":"3"}];
		var followers= [{"DESCRIPTION":"long,duck,long.","STALKERS":"0","POPULARITY":"1.0","USERNAME":"user","NICKNAME":"nick","PIC":"http://38.media.tumblr.com/avatar_65a3dde32c04_128.png","USER_ID":"4"},{"DESCRIPTION":"long, duck,long.","STALKERS":"0","POPULARITY":"1.0","USERNAME":"user2","NICKNAME":"nick2","USER_ID":"5"}];
		
		
		$(document).ready(function(){
	        $(".Ffollow").attr("href", "http://www.google.com/");
	    });
		
		
		document.getElementById('mainavatar').src= user[0].PIC;
		document.getElementById("mainnickname").innerHTML = user[0].NICKNAME;
		document.getElementById("mainusername").innerHTML = "@"+user[0].USERNAME;
	    document.getElementById("description").innerHTML = "<b>About Me:</b><br>" + user[0].DESCRIPTION;
	    document.getElementById("followers").innerHTML = user[0].STALKERS;
	    document.getElementById("rank").innerHTML = "Hipster Rank: #" +user[0].USER_ID;
	    document.getElementById("followersurl").href = "/users/"+ user[0].USERNAME+"/followers";
	    document.getElementById("followingurl").href = "/users/"+ user[0].USERNAME+"/following";
	    
	    
	    //generating followers divs and appending to main list
	    var parent= document.getElementById('followerslist');
	       
		for(var i = 0; i<followers.length-1; i++) {
			var div = document.getElementById('Fusers'),
			clone = div.cloneNode(true);
			parent.appendChild(clone);     
		   }
		
		
		//updating the list with actual followers data
		var fusername= document.getElementsByClassName('Fusername');
		var favatar= document.getElementsByClassName('Favatar');
		var ffollow= document.getElementsByClassName('Ffollow');
		
		for(var i=0; i<followers.length; i++){
			
			fusername[i].innerHTML= "@"+followers[i].USERNAME;
			fusername[i].href= "/user/"+followers[i].USERNAME;
			favatar[i].src= followers[i].PIC;
			ffollow[i].href="/user/"+followers[i].USERNAME+"/follow";
		}
		
		
	    
	}