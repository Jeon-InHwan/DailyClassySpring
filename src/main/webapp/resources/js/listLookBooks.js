function loadJQuery() {
    var oScript = document.createElement("script");
    oScript.type = "text/javascript";
    oScript.charset = "utf-8";		  
    oScript.src = "/resources/js/jquery-3.6.0.js";	
    document.getElementsByTagName("head")[0].appendChild(oScript);
}


$(function(){
		
	var images = ['/resources/images/backPic01.jpg',
				  '/resources/images/backPic02.jpg',
	              '/resources/images/backPic03.jpg',
	              '/resources/images/backPic04.jpg',
	              '/resources/images/backPic05.jpg',
	              '/resources/images/backPic06.jpg',
	              '/resources/images/backPic07.jpg',
	              '/resources/images/backPic08.jpg',
	              '/resources/images/backPic09.jpg',
	              '/resources/images/backPic10.jpg'
	     	     ];
  
    var i = 0;

    function changeBackground() {
        $('#picture').css('background-image', function() {
            if (i >= images.length) {
                i=0;
            }
            return 'url(' + images[i++] + ')';      
        });
    }
    
    // Call it on the first time
    changeBackground();
    // Set an interval to continue
    setInterval(changeBackground, 5000);


	const clock = document.querySelector("#clock");

	function getClock() {
	    const date = new Date();
	    const hours = String(date.getHours()).padStart(2, "0");
	    const minutes = String(date.getMinutes()).padStart(2, "0");
	    const seconds = String(date.getSeconds()).padStart(2, "0");
	
	    clock.innerHTML = `${hours}:${minutes}:${seconds}`;
	}

	
	getClock();
	setInterval(getClock, 1000);

	
	$(".forClick").on("click", function(){
		
		let user_id = ($(this).find("td:eq(1)").text());
		let lb_boardnum = ($(this).find("td:eq(6)").text());
		let logined_id = $("#hiddenLoginedId").val();
		let user_state = $("#hiddenUserState").val();
		
		console.log(user_id);
		console.log(lb_boardnum);
		console.log(logined_id);
		console.log(user_state);
		
		window.location.href = "/lookbook/readLookBook?lb_boardnum=" + lb_boardnum;
	
	});
	
	
});


function toWriteLookBookForm() {
	location.href = "/lookbook/writeLookBookForm";
}


function search(willBeNextPage) {
	
	var searchForm = document.getElementById("searchForm");
	var page = document.getElementById("page");
	
	page.value = willBeNextPage;
	
	searchForm.submit();
	
}



window.onpageshow = function(event) {
		
	console.log(event.persisted);
	
	if (event.persisted || window.performance && 
        performance.navigation.type == 2) {
        location.reload();
    }
    
}

