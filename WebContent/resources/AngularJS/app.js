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
	var app = angular.module('Hipster', [ 'ngSanitize']);
	
	//UserController- used in all pages- queries for the current user's details
	app.controller('UserController',['$http', function($http){	

		var user= this;
		user.details=[];
		
		//fetching the current user's information from the server
		$http.post('user','me').success(function(data){
			user.details= data[0];
		});
	
	}]);
	
	
	//MainController- used in the Main page- queries for the latest 10 posts from users i follow
	app.controller('MainController',['$scope','$interval','$http', function($scope,$interval,$http){	

		var main= this;
		main.messages=[];
		main.showreply= [];
		
		$http.post('Discover','me,latest,0').success(function(data){
			main.messages= data;
		});
		
		$scope.refresh= function(){
			
			stop= $interval(function(){
			$http.post('Discover','me,latest,0').success(function(data){
				main.messages= data;
			});
			}, 5000);
		};
		
		$scope.pause = function() {
	          
	        $interval.cancel(stop);
	        stop = undefined;
	           
	        };
	        
	        
	    $scope.toggle= function($index){
	    	if(main.showreply==false){
	    		main.showreply[$index]=true;
	    		$scope.pause();
	    	}else{
	    		main.showreply[$index]=false;
	    		$scope.refresh();
	    	}
	    };
		
	}]);
	
	//DiscoverController- used in the Discover page- queries for the top 10 posts of all users
	app.controller('DiscoverController',['$http', function($http){	

		var discover= this;
		discover.messages=[];

		$http.post('Discover','all,popularity,0').success(function(data){
			discover.messages= data;
		});
		
	}]);
	
	//ProfileController- used in user profile pages- queries for the user details and latest 10 posts
	app.controller('ProfileController',['$http', function($http){	

		var profile= this;
		profile.details=[];
		profile.messages=[];
		
		var name= getName("nickname");
		
		$http.post('user',name).success(function(data){
			profile.details= data[0];
		});
		
		var request= name+",latest,0";
		
		$http.post('Discover',request).success(function(data){
			profile.messages= data;
		});
		
	}]);
	
	//FollowersController- used in /followers/user pages, queries for the user's details and the top 10 followers
	app.controller('FollowersController',function(){
		var followers= this;
		followers.followers=[];
		
		var name= getName("nickname");		
		
		$http.post('followers',name).success(function(data){
			followers.followers= data;
		});
	
	});
	
	//FollowingController- used in /following/user pages, queries for the user's details and the top 10 users who User is following
	app.controller('FollowingController',function(){
		var following= this;
		following.following=[];
		
		var name= getName("nickname");
		
		$http.post('following',name).success(function(data){
			following.following= data;
		});
	
	});
	
	//TopicController- used in topic pages, queries for the latest 10 messages who contain a specific topic
	app.controller('TopicController',function(){
		var topic= this;
		topic.topic=[];
		
		var topicname= getName("topic");
		
		$http.post('Discover','all,latest,0').success(function(data){
			following.following= data;
		});
	
	});
	
	
	//custom filter to display time in 'time ago' format
	app.filter('timeago', function() {
		  return function(stamp) {
			  
			  stamp= stamp.substring(0, stamp.length - 4);
			  var t = stamp.split(/[- :]/);

			  var Stamp = new Date(t[0], t[1]-1, t[2], t[3], t[4], t[5]);

			  
			    var secs = (((new Date()).getTime() / 1000) - (Stamp.getTime()/1000));
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
			    return stamp.substring(0, stamp.length - 3);

		  };
		});
	
	


	
})();
	