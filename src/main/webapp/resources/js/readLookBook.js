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
	
	countReple();
	
	
	$("#upperClose").off("click").on("click", function(){
			
		countReple();

	});	
	
	
	$("#lowerClose").off("click").on("click", function(){
		
		countReple();
		
	});	
	
	const backwardDiv = document.querySelector("#backwardDiv");
	backwardDiv.addEventListener("mouseover", mouseover);
	backwardDiv.addEventListener("click", goingBack);
	
	const lb_boardnum = $("#hiddenBoardnum").val();
	console.log(lb_boardnum);
	
	
	$.ajax({
		url : "/lookbook/readTag"
		, method : "POST"
		, data : {"lb_boardnum" : lb_boardnum}
		, success : function(data){
			
			for(let i = 0; i < data.length; i++) {
				$("#TagTd").append('<input type="button" style="margin-right:15px;" class="btn btn-light" value="' + data[i].tag + '">');
			}
				
		}
	});
	
	
	$.ajax({
		url : "/lookbook/readLink"
		, method : "POST"
		, data : {"lb_boardnum" : lb_boardnum}
		, success : function(data){
			
			for(let i = 0; i < data.length; i++) {
				$("#LinkTd").append('<input type="button" style="margin-right:15px;" class="btn btn-light" value="' + data[i].link_add + '">');
			}
			
			$("#LinkTd .btn-light").off().on("click", function(e){
				
				console.log($(this).val());
				
				if($(this).val().includes("musinsa")) {
					updateLinkHits();
				}
				
				window.open(e.target.value);
			});
				
		}
	});
	
	
	$.ajax({
		url : "/lookbook/readPicture"
		, method : "POST"
		, data : {"lb_boardnum" : lb_boardnum}
		, success : function(data){
			
			for(let i = 1; i < data.length; i++) {
				$(".carousel-indicators").append('<button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="' + i +  '"aria-label="Slide ' + (i + 1) + '"></button>');
			}
			
			console.log(data[0].savedfile);
			
			for(let i = 0; i < 1; i++) {
				$("#active").append('<img src="/lookbook/display?name=' + data[i].savedfile + '"class="d-block w-50" alt="...">');
			}
			
			for(let i = 1; i < data.length; i++) {
				
				$(".carousel-inner").append('<div class="carousel-item carouselDiv"><img src="/lookbook/display?name=' + data[i].savedfile + '"class="d-block w-50" alt="..."><div class="carousel-caption d-none d-md-block"></div></div>');
			
			}
			
		}	
	});
	
	
	
	$("#readLookBookReplyBtn").off().on("click", function(){
		
		let user_id = $("#hiddenUserId").val();
		let lb_boardnum = $("#hiddenBoardnum").val();
		let logined_id = $("#hiddenLoginedId").val();
		let user_state = $("#hiddenUserState").val();
		
		console.log(user_id);
		console.log(lb_boardnum);
		console.log(logined_id);
		console.log(user_state);
		
		showReple(lb_boardnum);
		
		
		$("#modalTrigger").trigger("click");	
	});
	
	
	$("#modal_writeRepleButton").off("click").on("click", function(){
			
		$("#writeReplyButton").off("click").on("click", function(){
			
			let lb_boardnum = $("#hiddenBoardnum").val();
			let lb_reple_content = $("#lb_reple_content").val();
			let logined_id = $("#hiddenLoginedId").val();
		
			console.log(lb_boardnum);
			console.log(lb_reple_content);
			
			$.ajax({
				url : "/lookbookReple/insertLbReple"
				, method : "POST"
				, data : {"lb_boardnum" : lb_boardnum, 
						  "lb_reple_content" : lb_reple_content,
						  "user_id" : logined_id}
				, success : function(data){
					if(!(data < 1)) {
						
						showReple(lb_boardnum);
						
						$("#lb_reple_content").val("");
						$("#lowerClose4").trigger("click");
				
					} else {
						alert("댓글작성에 실패했습니다!");
						$("#lb_reple_content").val("");
						$("#lowerClose4").trigger("click");
					}
				}
			});
			
		});	
		
		$("#lowerClose4").off("click").on("click", function(){
			$("#lb_reple_content").val("");					
		});
			
	});
	
	
	$("#deleteLookBookBtn").on("click", function(){
		
		let lb_boardnum = $("#hiddenBoardnum").val();
		
		let checking = confirm("정말로 삭제하시겠습니까?");
		
		if(checking) {
			window.location.href = "/lookbook/deleteLookBook?lb_boardnum=" + lb_boardnum;
		}

 	});
	
	
});




function showReple(lb_boardnum) {
	
	
	$.ajax({
		url : "/lookbookReple/selectAllLbReple"
		, method : "POST"
		, data : {"lb_boardnum" : lb_boardnum}
		, success :function(data){
				
			let result = '';
			if(data.length == 0){
				// data = [] 일 때 아래의 코드가 실행된다!!
				result += '<div id="repleWrapper" class="modal-footer">';
				result += '<div id="repleDiv"> ';
				result += '<table class="table table-light table-hover" id="repleTable">';
				result += '<tr>';
				result += '<th>ID</th>';
				result += '<th>Context</th>';
				result += '<th>등록일</th>';
				result += '</tr>';
				result += '<tr>';
				result += '<td colspan="3" id="noReple">아직 등록된 댓글이 없습니다!</td>';
				result += '</tr>';
				result += '</table>';
				result += '</div>';
				result += '</div>';
			} else {
				result += '<div id="repleWrapper" class="modal-footer">';
				result += '<div id="repleDiv"> ';
				result += '<table class="table table-light table-hover" id="repleTable">';0
				result += '<tr>';
				result += '<th>ID</th>';
				result += '<th>Context</th>';
				result += '<th>등록일</th>';
				result += '</tr>';
				$.each(data, function(index, item){
				result += '<tr class="forClickRepleTr" data-num="' + item.lb_reple_num + '" data-id="' + item.user_id + '"  data-text="' + item.lb_reple_content +  '">';
				result += 	'<td>' + item.user_id + '</td>';
				result += 	'<td id="preTd"><pre class="eachRepletext" >' + item.lb_reple_content + '</pre></td>';
				result += 	'<td>' + item.lb_reple_regdate + '</td>';
				result += '</tr>';
				});
				result += '</table>';
				result += '<div id="informationDiv"><span id="informationForDeleteAndUpdateReple">댓글 수정／댓글 삭제는 클릭하세요!</span></div>';
				result += '<input type="button" id="hiddenButtonForToggle5" data-bs-target="#exampleModalToggle5" data-bs-toggle="modal">'
				result += '</div>';
				result += '</div>';
			}
			
			$("#innerContent1").html(result);
			$(".eachRepletext").attr({"data-bs-toggle" : "tooltip",
				         			  "data-bs-placement" : "top",
				         			  "title" : "댓글 수정／댓글 삭제는 클릭하세요!"});
			$("#lookBookBoard_numforToggle5").val(lb_boardnum);
		    $(".forClickRepleTr").on("click", updateAndRemoveReple);
		}

				
	});
	
}


function updateAndRemoveReple() {
	
	var lb_boardnum = $("#lookbook_numforToggle5").val();
	var lb_reple_num = $(this).attr("data-num");	
	var user_id = $(this).attr("data-id");	
	var lb_reple_content = $(this).attr("data-text");	
	var loggedin_user_id = $("#loginIdforToggle5").val();
	let user_state = $("#hiddenUserState").val();
	
	console.log(lb_boardnum);
	console.log(lb_reple_num);
	console.log(user_id);
	console.log(lb_reple_content);
	console.log(loggedin_user_id);
	console.log(user_state);
	
	
	
	
	if(user_id == loggedin_user_id || user_state == 3) {
		$("#lb_repletext_update").val(lb_reple_content);
		$("#hiddenButtonForToggle5").trigger("click");
	} else {
		return;
	}
	
	
	
	// updateAndRemoveReple() 실행 후 modal의 버튼
	$("#updateRepleButton").off("click").on("click", function(){
		
		var lb_boardnum = $("#lookBookBoard_numforToggle5").val();
		
		let updateConfirm = confirm("정말로 수정하시겠습니까?");
		
		if(updateConfirm){
			
			let lookbook_repletext = $("#lb_repletext_update").val();
		
			$.ajax({
				url : "/lookbookReple/updateLbReple"
				, method : "POST"
				, data : {"lb_reple_num" : lb_reple_num, 
						  "lb_reple_content" : lookbook_repletext}
				, success : function(data){
			
						if(!(data < 1)) {
									
							$("#lb_repletext_update").val("");
							$("#lowerClose5").trigger("click");
							showReple(lb_boardnum);
							
						} else {
							alert("댓글 수정에 실패했습니다. 나중에 다시 한 번 시도해주세요!");
							$("#lb_repletext_update").val("");
							$("#lowerClose4").trigger("click");
						}
						
				}
			});
			
			
		}
				
		
		
	});
	
	
	
	// updateAndRemoveReple() 실행 후 modal의 버튼
	$("#deleteRepleButton").off("click").on("click", function(){
		
		var lb_boardnum = $("#lookBookBoard_numforToggle5").val();
		
		let deleteConfirm = confirm("정말로 삭제하겠습니까?");
		
		if(deleteConfirm){
		
			$.ajax({
			url : "/lookbookReple/deleteLbReple"
			, method : "POST"
			, data : {"lb_reple_num" : lb_reple_num}
			, success : function(data){
		
				if(!(data < 1)) {
							
					$("#lb_repletext_update").val("");
					$("#lowerClose5").trigger("click");
					showReple(lb_boardnum);
					
				} else {
					alert("댓글 삭제에 실패했습니다. 나중에 다시 한 번 시도해주세요!");
					$("#lb_repletext_update").val("");
					$("#lowerClose4").trigger("click");
				}
				
					
			}
		});
		
		
	}
		
		
	});
	
	
	


}



function goingBack() {
	
	let kakao = $("#hiddenKakao").val();
	
	if(kakao == "kakao") {
		window.location.href = "/lookbook/listLookBooks";
	}
	
	else {
		history.back();	
	}
	
}

function mouseover() {
	const backwardDiv = document.querySelector("#backwardDiv");
	backwardDiv.classList.add("forMouseover");
}

function searchByTag() {
	let link = "https://search.musinsa.com/search/musinsa/integration?type=&q=";
	
	let tags;
	tags = $("#TagTd").find(".btn-light");
		
	for(let i = 0; i < 1; i++) {
		link += tags[i].value.substring(1);
	}
	
	for(let i = 1; i < tags.length; i++) {
		link += "+" + tags[i].value.substring(1) ;
	}
	
	console.log(link);
	
	updateLinkHits();
	
	window.open(link);
}

function recommendThisLookBook() {
	
	const lb_boardnum = $("#lookBookBoard_numforToggle5").val();
	
	console.log(lb_boardnum);
	
	$.ajax({
		url : "/lookbook/recommendThis"
		, method : "POST"
		, data : {"lb_boardnum" : lb_boardnum}
		, success : function(data){
	
			if(!(data < 1)) {
						
				let original = $("#recommendTd").text();
				$("#recommendTd").text(parseInt(original) + 1);
				
			} else {
				alert("게시글 추천에 실패했습니다. 나중에 다시 한 번 시도해주세요!");
				return;
			}
			
		}
	});
	
}


function countReple() {
	
	const lb_boardnum = $("#lookBookBoard_numforToggle5").val();
	console.log("countReple lb_boardnum :" + lb_boardnum);
	
	$.ajax({
		url : "/lookbookReple/countReple"
		, method : "POST"
		, data : {"lb_boardnum" : lb_boardnum}
		, success : function(data){			
			$("#countRepleTd").text(data);
		}
	});
	
}


function updateLinkHits() {
	
	let user_id = $("#hiddenUserId").val();
	
	$.ajax({
		url : "/member/updateLinkHits"
		, method : "POST"
		, data : {"user_id" : user_id}
		, success : function(data){			
			console.log("updateLinkHits : " + data);
		}
	});
	
}
