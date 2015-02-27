(function(){
	var app = angular.module('user', [ 'ngSanitize']);

	/*
	app.controller('UserController',['$http', function($http){
		this.messages= messages;
		this.details=details;
		
		var user= this;
		user.details=[];
		
		$http.post('/user').success(function(data){
			user.details= data;
		});
	}]);
	*/
	
	app.controller('UserController',function(){
		this.messages= messages;
		this.details= details[0];

	});
	
	app.controller('FollowersController',function(){
		this.followers= followers;
		this.details= details[0];
	
	});
	
	app.controller('FollowingController',function(){
		this.following= followers;
		this.details= details[0];
	
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
	
	
	var details= [{"DESCRIPTION":"woohoo","STALKERS":"0","POPULARITY":"1.0","USERNAME":"Barry","NICKNAME":"otherBarry","PIC":"http://assets.sbnation.com/assets/1311490/barry.jpg","USER_ID":"3"}];
	var messages= [{"OWNER":"1","MID":"1004","STAMP":"2015-02-24 17:53:36.428","REPLYTO":"0","TIMES_REPUBLISHED":"2","POPULARITY":"2.0","REPUBLISH_OF":"0","TEXT":"testing @nick @steve @test #stuff gdfgdfg","NICKNAME":"test","PIC":"http://assets.sbnation.com/assets/1311490/barry.jpg"},
	              {"OWNER":"1","MID":"1101","STAMP":"2015-02-24 17:56:00.535","REPLYTO":"0","TIMES_REPUBLISHED":"0","POPULARITY":"1.5849625007211563","REPUBLISH_OF":"1004","TEXT":"testing @nick @steve @test #stuff gdfgdfg","NICKNAME":"test","PIC":"http://assets.sbnation.com/assets/1311490/barry.jpg"},
	              {"OWNER":"1","MID":"1102","STAMP":"2015-02-24 17:57:12.655","REPLYTO":"0","TIMES_REPUBLISHED":"0","POPULARITY":"1.5849625007211563","REPUBLISH_OF":"1004","TEXT":"testing @nick @steve @test #stuff gdfgdfg","NICKNAME":"test","PIC":"http://assets.sbnation.com/assets/1311490/barry.jpg"},
	              {"OWNER":"1","MID":"1201","STAMP":"2015-02-24 21:12:27.744","REPLYTO":"0","TIMES_REPUBLISHED":"0","POPULARITY":"1.5849625007211563","REPUBLISH_OF":"0","TEXT":"@this @test #that something something #what? @user","NICKNAME":"test","PIC":"http://assets.sbnation.com/assets/1311490/barry.jpg"},
	              {"OWNER":"1","MID":"1301","STAMP":"2015-02-24 21:15:10.755","REPLYTO":"0","TIMES_REPUBLISHED":"0","POPULARITY":"1.5849625007211563","REPUBLISH_OF":"0","TEXT":"@this <a href=\"/Hipster/users/test\">@test</a> <a href=\"/Hipster/topic/that\">#that</a> something something <a href=\"/Hipster/topic/what?\">#what?</a> <a href=\"/Hipster/users/nick\">@nick</a>","NICKNAME":"test","PIC":"http://assets.sbnation.com/assets/1311490/barry.jpg"},
	              {"OWNER":"1","MID":"1401","STAMP":"2015-02-26 19:06:03.546","REPLYTO":"0","TIMES_REPUBLISHED":"0","POPULARITY":"1.5849625007211563","REPUBLISH_OF":"0","TEXT":"blah blah blah","NICKNAME":"test","PIC":"http://assets.sbnation.com/assets/1311490/barry.jpg"},
	              {"OWNER":"1","MID":"1402","STAMP":"2015-02-26 19:06:19.456","REPLYTO":"0","TIMES_REPUBLISHED":"0","POPULARITY":"1.5849625007211563","REPUBLISH_OF":"0","TEXT":"yada yada yada","NICKNAME":"test","PIC":"http://assets.sbnation.com/assets/1311490/barry.jpg"},
	              {"OWNER":"1","MID":"1403","STAMP":"2015-02-26 19:06:25.368","REPLYTO":"0","TIMES_REPUBLISHED":"0","POPULARITY":"1.5849625007211563","REPUBLISH_OF":"0","TEXT":"tractor story","NICKNAME":"test","PIC":"http://assets.sbnation.com/assets/1311490/barry.jpg"}]
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
	