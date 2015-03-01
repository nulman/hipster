function getName(VarSearch) {

	 var url = window.location.search.substring(1);
	    var VariableArray = url.split('&');
	    for(var i = 0; i < VariableArray.length; i++){
	        var KeyValuePair = VariableArray[i].split('=');
	        if(KeyValuePair[0] == VarSearch){
	            return KeyValuePair[1];
	        }
	    }	    
}

(function(){
	
//main module
	var app = angular.module('user', [ 'ngSanitize']);
	
	//MainController- used in the Main page- queries for the current user's details and latest 10 posts from users i follow
	app.controller('MainController',['$http', function($http){	

		var user= this;
		user.details=[];
		user.messages=[];
		
		//var name= getName("nickname");
		var name= 'nick';
		
		$http.post('user',name).success(function(data){
			user.details= data[0];
		});
		
		$http.post('Discover','stalkee,latest').success(function(data){
			user.messages= data;
		});
		
	}]);
	
	//DiscoverController- used in the Discover page- queries for the top 10 posts of all users
	app.controller('DiscoverController',['$http', function($http){	

		var user= this;
		user.details=[];
		user.messages=[];
		
		//var name= getName("nickname");
		var name= 'nick';
		
		$http.post('user',name).success(function(data){
			user.details= data[0];
		});
		
		$http.post('Discover','all,popularity').success(function(data){
			user.messages= data;
		});
		
	}]);
	
	//UserController- used in User pages- queries for the user details and latest 10 posts
	app.controller('UserController',['$http', function($http){	

		var user= this;
		user.details=[];
		user.messages=[];
		
		//var name= getName("nickname");
		var name= 'nick';
		
		$http.post('user',name).success(function(data){
			user.details= data[0];
		});
		
		$http.post('Discover','nick,latest').success(function(data){
			user.messages= data;
		});
		
	}]);
	
	//FollowersController- used in /followers/user pages, queries for the top 10 followers
	app.controller('FollowersController',function(){
		var followers= this;
		followers.followers=[];
		
		//var name= getName("nickname");
		var name= 'nick';
		
		$http.post('followers',name).success(function(data){
			followers.followers= data;
		});
	
	});
	
	//FollowingController- used in /following/user pages, queries for the top 10 users who User is following
	app.controller('FollowingController',function(){
		var following= this;
		following.following=[];
		
		//var name= getName("nickname");
		var name= 'nick';
		
		$http.post('following',name).success(function(data){
			following.following= data;
		});
	
	});
	
	app.filter('timeago', function() {
		  return function(stamp) {
			  
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

		  };
		});
	
	$(document).ready(function(){
		$(".replymessage").click(function(e){
	    	e.preventDefault();
	    	$(this).next('.newreply').toggle("slow");
	    	$('.rebuplishmessage').toggle();
	    	
	    });
	});

	$(document).ready(function(){
	    $(".rebuplishmessage").click(function(e){
	    	e.preventDefault();
	    	$(this).next('.newrepublish').toggle("slow");
	    	
	    });

	});


	
})();
	