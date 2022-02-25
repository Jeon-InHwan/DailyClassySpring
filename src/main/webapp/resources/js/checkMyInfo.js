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
	
	
	// 사용자가 작성한 LookBook 게시판 글 수 조회
	$.ajax({
		url : "/lookbook/countLookBook"
		, method : "POST"
		, success : function(data){

			$("#lookbookCountTd").text(data);
				
		}
	});


});


function requestCheck() {
	
	
	// 사용자가 작성한 LookBook 게시판 글 수 조회
	$.ajax({
		url : "/lookbook/countLookBook"
		, method : "POST"
		, success : function(data){
			
			console.log(data);
			
			if(data >= 5) {
				
				// 사용자가 작성한 LookBook 게시판 글 수 조회
				$.ajax({
					url : "/member/getLinkHits"
					, method : "POST"
					, success : function(data){
						
						console.log(data);
			
						if(data >= 100) {
							
							let check;
							check = confirm("파트너 신청을 진행하시겠습니까?");
							
							if(check) {
								
								// 사용자의 user_state 업데이트
								$.ajax({
									url : "/member/updateUserState"
									, method : "POST"
									, success : function(data){
										
										if(data > 0) {
											window.location.href = "/member/logout";	
										} else {
											alert("파트너 신청에 실패했습니다!");
										}
										
									} 
									, error : function(error) {
					        			alert("파트너 신청에 실패했습니다!");
					    			}
								});
							
							} 
						} else {
							alert("파트너 신청 자격을 충족해주세요! (클릭 포인트)");
						}
					}
							
				});
				
			} else {
				alert("파트너 신청 자격을 충족해주세요!(LookBook 게시판 글 수)");
			}
				
		}
	});
	
	
}








