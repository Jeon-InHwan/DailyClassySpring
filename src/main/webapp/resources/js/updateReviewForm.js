function loadJQuery() {
    var oScript = document.createElement("script");
    oScript.type = "text/javascript";
    oScript.charset = "utf-8";		  
    oScript.src = "/resources/js/jquery-3.6.0.js";	
    document.getElementsByTagName("head")[0].appendChild(oScript);
}


$(function(){
		
	var images = ['/resources/images/movie_01.jpg',
				  '/resources/images/movie_02.jpg',
	              '/resources/images/movie_03.jpg',
	              '/resources/images/movie_04.jpg',
	              '/resources/images/movie_05.jpg',
	              '/resources/images/movie_06.jpg',
	              '/resources/images/movie_07.jpg',
	              '/resources/images/movie_08.jpg',
	              '/resources/images/movie_09.jpg',
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

});


function updateReviewCheck() {
	
	
	const review_title = document.querySelector("#review_title").value;
	const review_text = document.querySelector("#review_text").value;
	const updateReview = document.querySelector("#updateReview");
	
	
	if(review_title.trim().length < 1) {
		alert("리뷰 제목은 필수 입력값입니다!");
		return;
	}
	
	if(review_title.trim().length > 50) {
		alert("리뷰 제목이 너무 깁니다!");
		return;
	}
	
	if(review_text.trim().length < 1) {
		alert("리뷰 내용은 필수 입력값입니다!");
		return;
	}
	
	if(review_text.trim().length > 1000) {
		alert("리뷰 내용이 너무 깁니다!");
		return;
	}
	
	
	updateReview.submit();
	
}




