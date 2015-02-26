function func() {
		var user=  [{"DESCRIPTION":"long,duck,long.","STALKERS":"0","POPULARITY":"1.0","USERNAME":"user","NICKNAME":"nick","PIC":"http://38.media.tumblr.com/avatar_65a3dde32c04_128.png","USER_ID":"4"}];
		//var messages= [{"NICKNAME":"wee","OWNER":"100","MID":"1","STAMP":"2015-02-17 15:18:02.486","REPLYTO":"0","TIMES_REPUBLISHED":"0","POPULARITY":"42.0","REPUBLISH_OF":"0","TEXT":"this is test<a href=ghj>@someone</a> i dont know what an href looks like"},{"NICKNAME":"woo","OWNER":"7","MID":"101","STAMP":"2015-02-17 15:25:48.818","REPLYTO":"0","TIMES_REPUBLISHED":"0","POPULARITY":"42.0","REPUBLISH_OF":"0","TEXT":"this is another test <a href=http://youtu.be/dQw4w9WgXcQ>@hrefs</a>  i dont know what an href looks like"}];
		
		document.getElementById('avatar').src= user[0].PIC;
		document.getElementById("nickname").innerHTML = "@"+user[0].NICKNAME;
	     
	    
	    var parent= document.getElementById('messagebar');
	       
		for(var i = 0; i < messages.length-1; i++) {
			var div = document.getElementById('usermessages'),
			clone = div.cloneNode(true);
			clone.class = "usermessages";
			parent.appendChild(clone);     
		   }
		
		for(i = 0; i < messages.length; i++) {
	        nickname[i].innerHTML= "@" + messages[i].NICKNAME+" ";
	        avatar[i].src= user[0].PIC;
	        textcontent[i].innerHTML= messages[i].TEXT;
	        timestamp[i].innerHTML= timeago(messages[i].STAMP);

	    }
		
		$(document).ready(function(){
	        $(".postnewmessage").click(function(){
	            $(".newpost").toggle("slow");
	        });

	    });
		
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
	    
}
	    