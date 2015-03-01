(function(){
	var app = angular.module('user', [ 'ngSanitize']);
	
	
	app.controller('UserController',['$http', function($http){	

		var user= this;
		user.details=[];
		user.messages=[];
		
		$http.post('user','nick').success(function(data){
			user.details= data[0];
		});
		
		$http.post('Discover','nick,popularity').success(function(data){
			user.messages= data;
		});
		
	}]);

	app.controller('FollowersController',function(){
		var followers= this;
		followers.details=[];
		followers.messages=[];
		
		$http.post('user','nick').success(function(data){
			followers.details= data[0];
		});
		
		$http.post('Discover','nick,popularity').success(function(data){
			followers.messages= data;
		});
	
	});
	
	app.controller('FollowingController',function(){
		var followeing= this;
		followeing.details=[];
		followeing.messages=[];
		
		$http.post('user','nick').success(function(data){
			followeing.details= data[0];
		});
		
		$http.post('Discover','nick,popularity').success(function(data){
			followeing.messages= data;
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
	
	var followers= [{"DESCRIPTION":"woohoo","STALKERS":"0","POPULARITY":"1.0","USERNAME":"Barry","NICKNAME":"otherBarry","PIC":"http://assets.sbnation.com/assets/1311490/barry.jpg","USER_ID":"3"},{"DESCRIPTION":"woohoo","STALKERS":"0","POPULARITY":"1.0","USERNAME":"Barry","NICKNAME":"notBarry","PIC":"http://assets.sbnation.com/assets/1311490/barry.jpg","USER_ID":"4"}];
	
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
	