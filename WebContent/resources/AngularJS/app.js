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

function getLast() {

	 var url = window.location.href;
	 window.alert(href.substr(url.lastIndexOf('/') + 1));
}

function checkvalid(nick,follows){
	return (follows.indexOf(nick) > -1);
}


function showrandom() {
    var quotes = ["you're special because you like sepia tones, thick rim glasses and soy milk", 
                  "does following other people make you less or more hipster?", 
                  "does it come in plaid?",
                  "do you also drink cofffee before it's cool?  cause that's dangerous",
                  "try a sepia filter.  everything looks better with a sepia filter",
                  "social networks?  so mainstream..",
                  "republish? really? you can't make your own special commentary about the monarch butterfly's mating habits?",
                  "this will look great with a bit of blur and some deep inspirational quote in helvetica"];
    var quote = quotes[Math.floor(Math.random() * quotes.length)];
    document.getElementById("quote").innerHTML = quote;
  };

(function(){
	
//main module
	var app = angular.module('Hipster', [ 'ngSanitize']);
	
	//UserController- used in all pages- queries for the current user's details
	app.controller('UserController',['$http','$scope','$rootScope', function($http,$scope,$rootScope){	

		var user= this;
		user.details=[];

		
		//fetching the current user's information from the server
		$http.post('user','me').success(function(data){
			user.details= data[0];
			$rootScope.userdetails= data[0];
		});

	}]);

	
	//MainController- used in the Main page- queries for the latest 10 posts from users i follow
	app.controller('MainController',['$scope','$interval','$http', function($scope,$interval,$http){	

		var main= this;
		main.messages=[];
		main.showreply= [false];
		main.showrepublish= [false];
			
				
		$http.post('Discover','me,latest,0').success(function(data){
			main.messages= data;
		});
		
		$scope.refresh= function(){
			
			stop= $interval(function(){
			$http.post('Discover','me,latest,0').success(function(data){
				main.messages= data;
			});
			}, 2000);
		};
		
		$scope.pause = function() {
	          
	        $interval.cancel(stop);
	        stop = undefined;
	           
	        };
	        
	        
	    $scope.togglereply= function($index){
	    	if(main.showreply[$index]==false){
	    		$scope.pause();
	    		main.showreply[$index]=true;
	    	}else{
	    		$scope.refresh();
	    		main.showreply[$index]=false;
	    	}
	    };
	    
	    $scope.togglerepublish= function($index){
	    	if(main.showrepublish[$index]==false){
	    		$scope.pause();
	    		main.showrepublish[$index]=true;
	    	}else{
	    		$scope.refresh();
	    		main.showrepublish[$index]=false;
	    	}
	    };
		
	}]);
	
	//DiscoverController- used in the Discover page- queries for the top 10 posts of all users
	app.controller('DiscoverController',['$scope','$interval','$log','$http', function($scope,$interval,$log,$http){	

		var discover= this;
		discover.messages1=[];
		discover.messages2=[];
		discover.showreply= [false];
		discover.showrepublish= [false];
		
		$scope.inchecbox= false;

		$http.post('Discover','all,popularity,0').success(function(data){
			discover.messages= data;
		});
		
		$scope.refresh= function(){
			
			stop= $interval(function(){
			$http.post('Discover','me,popularity,0').success(function(data){
				discover.messages1= data;
			});
				
			$http.post('Discover','all,popularity,0').success(function(data){
				discover.messages2= data;
			});
			}, 2000);
	
		};
		
		

		///
		$scope.pause = function() {
	          
	        $interval.cancel(stop);
	        stop = undefined;
	           
	        };
	        
	    $scope.toggle= function(){
			$scope.inchecbox= !$scope.inchecbox;
			};

	        
	        
	    $scope.togglereply= function($index){
	    	if(discover.showreply[$index]==false){
	    		$scope.pause();
	    		discover.showreply[$index]=true;
	    	}else{
	    		$scope.refresh();
	    		discover.showreply[$index]=false;
	    	}
	    };
	    
	    $scope.togglerepublish= function($index){
	    	if(discover.showrepublish[$index]==false){
	    		$scope.pause();
	    		discover.showrepublish[$index]=true;
	    	}else{
	    		$scope.refresh();
	    		discover.showrepublish[$index]=false;
	    	}
	    };
		
	}]);
	

	//TopicController- used in topic pages, queries for the latest 10 messages who contain a specific topic
	app.controller('TopicController',['$scope','$interval','$http', function($scope,$interval,$http){	

		var topic= this;
		topic.messages=[];
		topic.showreply= [false];
		topic.showrepublish= [false];
		
		var searchtopic= getName("topic");

		$http.post('topic',searchtopic).success(function(data){
			topic.messages= data;
		});
		
		$scope.refresh= function(){
			
			stop= $interval(function(){
			$http.post('topic',searchtopic).success(function(data){
				topic.messages= data;
			});
			}, 2000);
		};
		
		$scope.pause = function() {
	          
	        $interval.cancel(stop);
	        stop = undefined;
	           
	        };
	        
	        
	    $scope.togglereply= function($index){
	    	if(topic.showreply[$index]==false){
	    		$scope.pause();
	    		topic.showreply[$index]=true;
	    	}else{
	    		$scope.refresh();
	    		topic.showreply[$index]=false;
	    	}
	    };
	    
	    $scope.togglerepublish= function($index){
	    	if(topic.showrepublish[$index]==false){
	    		$scope.pause();
	    		topic.showrepublish[$index]=true;
	    	}else{
	    		$scope.refresh();
	    		topic.showrepublish[$index]=false;
	    	}
	    };
		
	}]);
	

	
	//ProfileController- used in user profile pages- queries for the user details and latest 10 posts
	app.controller('ProfileController',['$scope','$interval','$http', function($scope,$interval,$http){	

		var profile= this;
		profile.details=[];
		profile.messages=[];
		profile.showreply= [false];
		profile.showrepublish= [false];
		
		var name= getName("nickname");
		//var name= getLast();
		
		$http.post('user',name).success(function(data){
			profile.details= data[0];
		});
		
		var request= name+",latest,0";
		
		$http.post('Discover',request).success(function(data){
			profile.messages= data;
		});
		
		$scope.refresh= function(){
			
			stop= $interval(function(){
				$http.post('Discover',request).success(function(data){
					profile.messages= data;
				});
			}, 2000);
		};
		
		$scope.pause = function() {
	          
	        $interval.cancel(stop);
	        stop = undefined;
	           
	        };
	        
	        
	    $scope.togglereply= function($index){
	    	if(profile.showreply[$index]==false){
	    		$scope.pause();
	    		profile.showreply[$index]=true;
	    	}else{
	    		$scope.refresh();
	    		profile.showreply[$index]=false;
	    	}
	    };
	    
	    $scope.togglerepublish= function($index){
	    	if(profile.showrepublish[$index]==false){
	    		$scope.pause();
	    		profile.showrepublish[$index]=true;
	    	}else{
	    		$scope.refresh();
	    		profile.showrepublish[$index]=false;
	    	}
	    };
	    
	    $scope.reset= function(){
	    	$scope.replytxt = "@somthing";
	    }
		
		
	}]);
	
	//FormController
	app.controller('FormController',function(){
		
		profile.showreply= [false];
		profile.showrepublish= [false];
		
		$scope.togglereply= function($index){
	    	if(profile.showreply[$index]==false){
	    		$scope.pause();
	    		profile.showreply[$index]=true;
	    	}else{
	    		$scope.refresh();
	    		profile.showreply[$index]=false;
	    	}
	    };
	    
	    $scope.togglerepublish= function($index){
	    	if(profile.showrepublish[$index]==false){
	    		$scope.pause();
	    		profile.showrepublish[$index]=true;
	    	}else{
	    		$scope.refresh();
	    		profile.showrepublish[$index]=false;
	    	}
	    };
	    
	    $scope.reset= function(){
	    	$scope.user = angular.copy($scope.master);
	    }
	});
	
	
	//FollowersController- used in /followers/user pages, queries for the user's details and the top 10 followers
	app.controller('FollowersController',['$scope','$http', function($scope,$http){
		
		var followers= this;
		followers.followers=[];
		
		var name= getName("nickname");
		
		$http.post('followers',name).success(function(data){
			followers.followers= data;
		});
		
	
	}]);
	
	//FollowingController- used in /following/user pages, queries for the user's details and the top 10 users who User is following
	app.controller('FollowingController',['$scope','$http', function($scope,$http){
		
		var following= this;
		following.followings=[];
		
		var name= getName("nickname");
		var string= name+",1";
		
		$http.post('following',string).success(function(data){
			following.followings= data;
		});
		
	
	}]);
	
	
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
			    return stamp.substring(0, stamp.length - 3);;

		  };
		});
	
	


	
})();
	