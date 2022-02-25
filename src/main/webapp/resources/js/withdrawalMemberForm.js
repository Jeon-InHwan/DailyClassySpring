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


function withdrawalCheck() {
	
	const user_pw = document.getElementById("user_pw").value;
	const withdrawalForm = document.getElementById("withdrawalForm");
	const user_id = document.getElementById("user_id");
	
	if(user_pw.trim().length < 5 || user_pw.trim().length > 12) {
		alert("Password는 5글자 이상, 12글자 이하로 입력해주세요!");
		return;
	}
	
	const withdrawalConfirm = confirm("정말로 회원탈퇴 하시겠습니까?");
	
	
	if(withdrawalConfirm) {
		user_id.removeAttribute("disabled");
		withdrawalForm.submit();
	}
	
}




