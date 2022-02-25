function loadJQuery() {
    var oScript = document.createElement("script");
    oScript.type = "text/javascript";
    oScript.charset = "utf-8";		  
    oScript.src = "/resources/js/jquery-3.6.0.js";	
    document.getElementsByTagName("head")[0].appendChild(oScript);
};


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
    
    
    const kakaoButton = document.querySelector(".flex");
	kakaoButton.addEventListener("mouseover", mouseover);
    
    
    var kakaoInfo = '${kakaoInfo}';

      if(kakaoInfo != ""){
    	  
          var data = JSON.parse(kakaoInfo);

          alert("카카오로그인 성공 \n accessToken : " + data['accessToken']);
          alert(
          "user : \n" + "email : "
          + data['email']  
          + "\n nickname : " 
          + data['nickname']);
      }
      
	Kakao.init('5ee3e8ada31f6326196bcf67516d7c5d');
    

});


function doLogin() {
	const user_id = document.getElementById("user_id").value;
	const user_pw = document.getElementById("user_pw").value;
	const loginForm = document.getElementById("loginForm");
	
	
	if(user_id.trim().length < 5 || user_id.trim().length > 12) {
		alert("ID는 5글자 이상, 12글자 이하로 입력해주세요!");
		return;
	}
	
	if(user_pw.trim().length < 5 || user_pw.trim().length > 12) {
		alert("Password는 5글자 이상, 12글자 이하로 입력해주세요!");
		return;
	}
	
	
	loginForm.submit();

}


function mouseover() {
	const kakaoButton = document.querySelector(".flex");
	kakaoButton.classList.add("forMouseover");
}

function kakaoLogin() {
      
      $.ajax({
          url: '/login/getKakaoAuthUrl',
          type: 'get',
          async: false,
          dataType: 'text',
          success: function (res) {
              location.href = res;
              //console.log(res);
          },
          error: function(e) {
        	  console.log(e);
          }
      });
	      
} 



