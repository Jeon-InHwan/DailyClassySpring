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

});


function writeFreeboardCheck() {
	
	
	const fb_title = document.querySelector("#fb_title").value;
	const fb_content = document.querySelector("#fb_content").value;
	const writeFreeboard = document.querySelector("#writeFreeboard");
	
	
	if(fb_title.trim().length < 1) {
		alert("리뷰 제목은 필수 입력값입니다!");
		return;
	}
	
	if(fb_title.trim().length > 50) {
		alert("리뷰 제목이 너무 깁니다!");
		return;
	}
	
	if(fb_content.trim().length < 1) {
		alert("리뷰 내용은 필수 입력값입니다!");
		return;
	}
	
	if(fb_content.trim().length > 1000) {
		alert("리뷰 내용이 너무 깁니다!");
		return;
	}
	
	
	writeFreeboard.submit();
	
}




