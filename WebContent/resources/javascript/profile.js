function func() {
	var user= [{"DESCRIPTION":"woohoo","STALKERS":"0","POPULARITY":"1.0","USERNAME":"Barry","NICKNAME":"otherBarry","PIC":"http://assets.sbnation.com/assets/1311490/barry.jpg","USER_ID":"3"}];
		var messages= [{"OWNER":"100","MID":"1","STAMP":"2015-02-17 15:18:02.486","REPLYTO":"0","TIMES_REPUBLISHED":"0","POPULARITY":"42.0","REPUBLISH_OF":"0","TEXT":"this is test<a href=ghj>@someone</a> i dont know what an href looks like"},{"OWNER":"7","MID":"101","STAMP":"2015-02-17 15:25:48.818","REPLYTO":"0","TIMES_REPUBLISHED":"0","POPULARITY":"42.0","REPUBLISH_OF":"0","TEXT":"this is another test <a href=http://youtu.be/dQw4w9WgXcQ>@hrefs</a>  i dont know what an href looks like"}];
		
		var nickname= document.getElementsByClassName('nickname');
		var avatar= document.getElementsByClassName('avatar');
		var timestamp= document.getElementsByClassName('timestamp');
		var textcontent= document.getElementsByClassName('textcontent');
		var follow= document.getElementsByClassName('follow');
		
		document.getElementById('mainavatar').src= user[0].PIC;
		document.getElementById("mainnickname").innerHTML = user[0].NICKNAME;
		document.getElementById("mainusername").innerHTML = "@"+user[0].USERNAME;
	    document.getElementById("description").innerHTML = "<b>About Me:</b><br>" + user[0].DESCRIPTION;
	    document.getElementById("followers").innerHTML = user[0].STALKERS;
	    document.getElementById("rank").innerHTML = "Hipster Rank: #" +user[0].USER_ID;
	    document.getElementById("followersurl").href = "/users/"+ user[0].USERNAME+"/followers";
	    document.getElementById("followingurl").href = "/users/"+ user[0].USERNAME+"/following";
	        
	    
	    
	    //cloning the message template
	    
	    var parent= document.getElementById('messagebar');
	       
		for(var i = 0; i < messages.length-1; i++) {
			var div = document.getElementById('usermessages'),
			clone = div.cloneNode(true);
			clone.class = "usermessages";
			parent.appendChild(clone);     
		   }
		
		//populating the new empty messages with actual user data
		
		for(i = 0; i < messages.length; i++) {
	        nickname[i].innerHTML= "@" + user[0].NICKNAME+" ";
	        nickname[i].href= "/" + user[0].USERNAME;
	        avatar[i].src= user[0].PIC;
	        textcontent[i].innerHTML= messages[i].TEXT;
	        timestamp[i].innerHTML= timeago(messages[i].STAMP);
	        follow[i].href="/"+user[0].USERNAME+"/follow";

	    }
		
		$(document).ready(function(){
			$(".replymessage").click(function(e){
	        	e.preventDefault();
	        	$(this).next('.newreply').toggle("slow");
	        	
	        });
	    });
	 
	    $(document).ready(function(){
	        $(".rebuplishmessage").click(function(e){
	        	e.preventDefault();
	        	$(this).next('.newrepublish').toggle("slow");
	        	
	        });

	    });
		
		 
	    
	}
	
	function timeago(stamp) {
		var now=new Date(stamp.replace(' ','T')+'Z');
		
	    var secs = (((new Date()).getTime() / 1000) - now.getTime()/1000);
	    Math.floor(secs);
	    var minutes = secs / 60;
	    secs = Math.floor(secs % 60);
	    if (minutes < 1) {
	        return secs + (secs > 1 ? ' seconds ago' : ' second ago');
	    }
	    var hours = minutes / 60;
	    minutes = Math.floor(minutes % 60);
	    if (hours < 1) {
	        return minutes + (minutes > 1 ? ' minutes ago' : ' minute ago');
	    }
	    var days = hours / 24;
	    hours = Math.floor(hours % 24);
	    if (days < 1) {
	        return hours + (hours > 1 ? ' hours ago' : ' hour ago');
	    }
	    return stamp.substring(0, stamp.length - 7);;

	}