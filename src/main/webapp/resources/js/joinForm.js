function loadJQuery() {
    var oScript = document.createElement("script");
    oScript.type = "text/javascript";
    oScript.charset = "utf-8";		  
    oScript.src = "/resources/js/jquery-3.6.0.js";	
    document.getElementsByTagName("head")[0].appendChild(oScript);
}

let sendOK1 = false;    // true일 때만 전송되도록
let sendOK2 = false; 	// true일 때만 전송되도록
let sendOK3 = false; 	// true일 때만 전송되도록
let sendOK4 = false; 	// true일 때만 전송되도록


$(function(){
	
	$("#user_id").on("keyup", idCheck);
	$("#user_pw").on("keyup", pwCheck);
	$("#user_name").on("keyup", nameCheck);
	$("#idDoubleCheckButton").on("click", idDoubleCheck);
	
		
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

});


function totalCheck(){
	
	let disabledCheck = $("#user_id").prop('disabled');
		
	console.log(disabledCheck);
	
	if(disabledCheck) {
		sendOK4 = true;
	}
	
	
	if(sendOK1 && sendOK2 && sendOK3 && sendOK4) {
		$("#joinButton").attr("value", "회원가입!");
	} 
	
	if(sendOK1 == true && sendOK2 == true && sendOK3 == true && sendOK4 == false) {
		$("#joinButton").attr("value", "ID 중복체크를 실시해주세요!");
	}
	
	console.log(sendOK1, sendOK2, sendOK3, sendOK4);
};




function idCheck() {
	
	let user_id = $("#user_id").val();
	if(user_id.trim().length < 5 || user_id.trim().length > 12){
		$("#joinButton").attr("value", "ID는 5글자 이상, 12글자 이하로 입력해주세요!");
		sendOK1 = false;
		$("#idDoubleCheckButton").attr("disabled", true);
	} else {
		$("#joinButton").attr("value", "ID 중복체크 부탁드립니다!");
		$("#idDoubleCheckButton").attr("disabled", false);
		sendOK1 = true;
	}	
	
	totalCheck();	
}


function pwCheck() {
	
	let user_pw = $("#user_pw").val();
	
	if(user_pw.trim().length < 5 || user_pw.trim().length > 12){
		$("#joinButton").attr("value", "Password는 5글자 이상, 12글자 이하로 입력해주세요!");
		sendOK2 = false;
	} else {
		$("#joinButton").attr("value", "다음 항목을 작성해주세요!");
		sendOK2 = true;
	}
	
	totalCheck();
}


function nameCheck() {
	
	let user_name = $("#user_name").val();
	$("#joinButton").attr("value", "좋습니다! 계속 진행해주세요!");
	if(user_name.trim().length > 0){
		sendOK3 = true;
	} else {
		sendOK3 = false;
	}
	
	totalCheck();
}


function idDoubleCheck() {		


	let user_id = $("#user_id").val();
	let useConfirm;
	
	$.ajax({
	url : '/member/idCheck'
	, method : "POST"
	, data : {"user_id" : user_id}
	, success : function(resp){
		if(resp == 'success'){
			useConfirm = confirm("사용 가능한 ID입니다! 사용하시겠습니까?");
			if(useConfirm) {
				$("#user_id").attr("disabled", true);
				$("#idDoubleCheckButton").attr("disabled", true);
				$("#joinButton").attr("value", "좋습니다! 계속 진행해주세요!");
				totalCheck();
			}
		} else {
			alert("사용 불가한 ID입니다. 다른 ID를 입력해주세요!");
			$("#user_id").val("");
			return;
		  }
		}
	}); 
		
		totalCheck();
}


function formCheck() {
	
		totalCheck();
		
		if($("#joinButton").attr("value") == "회원가입!"){		
			$("#user_id").attr("disabled", false);
			$("#joinForm").submit();
		} else {
			alert("충족되지 않은 조건이 존재합니다!");
			return;
		}
		
}
	
	
	







