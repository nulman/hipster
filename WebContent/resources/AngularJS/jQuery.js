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