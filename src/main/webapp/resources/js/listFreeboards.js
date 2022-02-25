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
    
    
    
    $("#writeButton").on("click", function(){
		location.href = "/freeboard/writeFreeboardForm";
	});
	
	
	$(".forClick").on("click", function(){
		
		let user_id = ($(this).find("td:eq(1)").text());
		let fb_boardnum = ($(this).find("td:eq(5)").text());
		let logined_id = $("#hiddenLoginedId").val();
		let user_state = $("#hiddenUserState").val();

		/*
		console.log(user_id);
		console.log(fb_boardnum); 
		console.log(logined_id);
		console.log(user_state);		
		*/
		
		$.ajax({
			url : "/freeboard/readFreeboard"
			, method : "POST"
			, data : {"fb_boardnum" : fb_boardnum}
			, success : function(data){
				
				showReple(fb_boardnum);
				
				if(user_id == logined_id || user_state == 3){
					$("#modal_updateButton").css({"display" : "inline-block"});
					$("#modal_DeleteButton").css({"display" : "inline-block"});
				} else {
					$("#modal_updateButton").css({"display" : "none"});
					$("#modal_DeleteButton").css({"display" : "none"});
				}
				
				$("#exampleModalLabel").html("<span id='title'>" + data.fb_title + "</span>");
				$("#innerContent1").html("<pre id='context'>" + data.fb_content + "</pre>");
				$("#innerContent1").append("<div id='indateAndHits'><span id='indate'>" + data.fb_regdate + "</span><br>조회수： <span id='hits'> " + data.fb_hitcount + "</span></div>");
				$("#innerContent3").html("<span id='num'>" + data.fb_boardnum + "</span>");
				$("#modalTrigger").trigger("click");		
			}
		});
			
		
		$("#upperClose").off("click").on("click", function(){
			
			let hits = $("#hits").text();
			let fb_boardnum = $("#num").text();
			let td = $(".forClick").find("td:eq(6)");
		
			let selectedTd;
			
			console.log(td);
		
			for(var i = 0; i < td.length; i++) {
				console.log(td[i]);
				var temp = $(td[i]).children().val();
				console.log("이건 제이쿼리 객체에서 빼 내온 hidden input 값 : " + temp);
				if(temp == fb_boardnum) {
					selectedTd = $(td[i]);
				}
			}
				
			console.log(selectedTd);
	
			$("#innerContent1").text("");
			$("#innerContent2").text("");
			$("#innerContent3").text("");
			$(selectedTd).parent().find("td:eq(3)").text(hits);
	
		});	
		
		$("#lowerClose").off("click").on("click", function(){
			let hits = $("#hits").text();
			let fb_boardnum = $("#num").text();
			let td = $(".forClick").find("td:eq(6)");
		
			let selectedTd;
			
			console.log("배열 형태의 td : " + td);
		
			for(var i = 0; i < td.length; i++) {
				console.log(td[i]);
				var temp = $(td[i]).children().val();
				console.log("배열 안의 값을 하나씩 떼어낸 td : " + temp);
				if(temp == fb_boardnum) {
					selectedTd = $(td[i]);
				}
			}
				
			console.log(selectedTd);
	
			$("#innerContent1").text("");
			$("#innerContent2").text("");
			$("#innerContent3").text("");
			$(selectedTd).parent().find("td:eq(3)").text(hits);
			
		});	
		
		
		$("#modal_writeRepleButton").off("click").on("click", function(){
			
			$("#writeReplyButton").off("click").on("click", function(){
				
				let fb_boardnum = $("#innerContent3").text();
				let fb_reple_content = $("#fb_reple_content").val();
			
				
				$.ajax({
					url : "/freeboardReple/insertReple"
					, method : "POST"
					, data : {"fb_boardnum" : fb_boardnum, 
							  "fb_reple_content" : fb_reple_content,
							  "user_id" : logined_id}
					, success : function(data){
						if(!(data < 1)) {
							
							showReple(fb_boardnum);
							$("#fb_reple_content").val("");
							$("#lowerClose4").trigger("click");
					
						} else {
							alert("댓글작성에 실패했습니다!");
							$("#fb_reple_content").val("");
							$("#lowerClose4").trigger("click");
						}
					}
				});
				
			});	
				
		});
		
		
		
		$("#modal_updateButton").off("click").on("click", function(){
			let placeholder_title = $('#exampleModalLabel>#title').text();
			let placeholder_context = $('#context').text();
			let fb_boardnum = $("#innerContent3").text();
			let fb_regdate = $("#indate").text();
			let fb_hitcount = $("#hits").text();
			$("#update_title").attr("placeholder", placeholder_title);
			$("#update_context").attr("placeholder", placeholder_context);
			$("#update_num").val(fb_boardnum);
			$("#update_indate").val(fb_regdate);
			$("#update_hits").val(fb_hitcount);
		});	
		
		
		$("#updateButton").off("click").on("click", function(){
			
			let fb_boardnum = $("#update_num").val();
			let fb_title = $("#update_title").val();
			let fb_content = $("#update_context").val();
			let fb_regdate = $("#update_indate").val();
			let fb_hitcount = $("#update_hits").val();
			let td = $(".forClick").find("td:eq(6)");
			let selectedTd;
			
			// 자바스크립트 날짜 객체
			
			let today = new Date();   

			let year = today.getFullYear();
			let month = ('0' + (today.getMonth() + 1)).slice(-2);
			let day = ('0' + today.getDate()).slice(-2);

			let dateString = year + '-' + month  + '-' + day;
			
			if(fb_title.trim().length < 1) {
				alert("게시글 제목은 필수 항목입니다！");	
				return;
			}
	
			if(fb_content.trim().length < 1) {
				alert("게시글 내용은 필수 항목입니다!");
				return;	
			}
			
			
			$.ajax({
			url : "/freeboard/updateFreeboard"
			, method : "POST"
			, data : {"fb_boardnum" : fb_boardnum, 
					  "fb_title" : fb_title,
					  "fb_content" : fb_content}
			, success : function(data){
					if(!(data < 1)) {
						$("#exampleModalLabel").html("<span id='title'>" + fb_title + "</span>");
						$("#innerContent1").html("<pre id='context'>" + fb_content + "</pre><div id='indateAndHits'><span id='indate'>등록일 : " + fb_regdate + "</span><br>수정일 : "+ dateString + "<br>조회수： <span id='hits'> " + fb_hitcount + "</span></div>");
						for(var i = 0; i < td.length; i++) {
							console.log(td[i]);
							var temp = $(td[i]).children().val();
							console.log("배열 안의 값을 하나씩 떼어낸 td : " + temp);
							if(temp == fb_boardnum) {
							selectedTd = $(td[i]);
							}
						}
				
						console.log(selectedTd);
						
						$("#update_num").val("");
						$("#update_title").val("");
						$("#update_context").val("");
						$("#update_indate").val("");
						$("#update_hits").val("");
						
						$(selectedTd).parent().find("td:eq(2)").html("<span id='title'>" + fb_title + "</span>");
						$(selectedTd).parent().find("td:eq(4)").html(dateString);
						
						$("#lowerClose2").trigger("click");
						
					} else {
						$("#update_num").val("");
						$("#update_title").val("");
						$("#update_context").val("");
						$("#update_indate").val("");
						$("#update_hits").val("");
						alert("게시글 수정에 실패했습니다. 나중에 다시 시도해주세요!");
						$("#lowerClose2").trigger("click");
					}
				}
			});
			
		});
		
		
		
		$("#modal_DeleteButton").off("click").on("click", function(){
			
			$("#deleteButton").off("click").on("click", function(){
				let fb_boardnum = $("#innerContent3").text();
				let td = $(".forClick").find("td:eq(6)");
				let selectedTd;
				
				$.ajax({
				url : "/freeboard/deleteFreeboard"
				, method : "POST"
				, data : {"fb_boardnum" : fb_boardnum}
				, success : function(data){
						if(!(data < 1)) {
							
							for(var i = 0; i < td.length; i++) {
								console.log(td[i]);
								var temp = $(td[i]).children().val();
								console.log("배열 안의 값을 하나씩 떼어낸 td : " + temp);
								if(temp == fb_boardnum) {
								selectedTd = $(td[i]);
								}
							}
					
							console.log(selectedTd);
							
							$(selectedTd).parent().hide();
							$("#lowerClose3").trigger("click");
							$("#lowerClose").trigger("click");
							
						} else {
							alert("삭제에 실패했습니다. 나중에 한 번 더 시도해주세요!");
							$("#lowerClose3").trigger("click");
							$("#lowerClose").trigger("click");
						}
					}
				});
	
			});	
			
		});	
		
			
	});
		
	

});



function showReple(fb_boardnum) {
	
	
	$.ajax({
		url : "/freeboardReple/selectAllReple"
		, method : "POST"
		, data : {"fb_boardnum" : fb_boardnum}
		, success :function(data){
				
			let result = '';
			if(data.length == 0){
				// data = [] 일 때 아래의 코드가 실행된다!!
				result += '<div id="repleWrapper" class="modal-footer">';
				result += '<div id="repleDiv"> ';
				result += '<table class="table table-dark table-hover" id="repleTable">';
				result += '<tr>';
				result += '<th>ID</th>';
				result += '<th>Context</th>';
				result += '<th>등록일</th>';
				result += '</tr>';
				result += '<tr>';
				result += '<td colspan="3">아직 등록된 댓글이 없습니다!</td>';
				result += '</tr>';
				result += '</table>';
				result += '</div>';
				result += '</div>';
			} else {
				result += '<div id="repleWrapper" class="modal-footer">';
				result += '<div id="repleDiv"> ';
				result += '<table class="table table-dark table-hover" id="repleTable">';0
				result += '<tr>';
				result += '<th>ID</th>';
				result += '<th>Context</th>';
				result += '<th>등록일</th>';
				result += '</tr>';
				$.each(data, function(index, item){
				result += '<tr class="forClickRepleTr" data-num="' + item.fb_reple_num + '" data-id="' + item.user_id + '"  data-text="' + item.fb_reple_content +  '">';
				result += 	'<td>' + item.user_id + '</td>';
				result += 	'<td id="preTd"><pre class="eachRepletext" >' + item.fb_reple_content + '</pre></td>';
				result += 	'<td>' + item.fb_reple_regdate + '</td>';
				result += '</tr>';
				});
				result += '</table>';
				result += '<div id="informationDiv"><span id="informationForDeleteAndUpdateReple">댓글 수정／댓글 삭제는 클릭하세요!</span></div>';
				result += '<input type="button" id="hiddenButtonForToggle5" data-bs-target="#exampleModalToggle5" data-bs-toggle="modal">'
				result += '</div>';
				result += '</div>';
			}
			
			$("#innerContent4").html(result);
			$(".eachRepletext").attr({"data-bs-toggle" : "tooltip",
				         			  "data-bs-placement" : "top",
				         			  "title" : "댓글 수정／댓글 삭제는 클릭하세요!"});
			$("#freeboard_numforToggle5").val(fb_boardnum);
			$(".forClickRepleTr").on("click", updateAndRemoveReple);
		}

				
	});
	
}



function updateAndRemoveReple() {
	
	var fb_boardnum = $("#freeboard_numforToggle5").val();
	var fb_reple_num = $(this).attr("data-num");	
	var user_id = $(this).attr("data-id");	
	var fb_reple_content = $(this).attr("data-text");	
	var loggedin_user_id = $("#loginIdforToggle5").val();
	let user_state = $("#hiddenUserState").val();
	
	if(user_id == loggedin_user_id || user_state == 3) {
		$("#freeboard_repletext_update").val(fb_reple_content);
		$("#hiddenButtonForToggle5").trigger("click");
	} else {
		return;
	}
	
	
	
	// updateAndRemoveReple() 실행 후 modal의 버튼
	$("#updateRepleButton").off("click").on("click", function(){
		
		
		let updateConfirm = confirm("정말로 수정하시겠습니까?");
		
		if(updateConfirm){
			
			let freeboard_repletext = $("#freeboard_repletext_update").val();
		
			$.ajax({
				url : "/freeboardReple/updateReple"
				, method : "POST"
				, data : {"fb_reple_num" : fb_reple_num, 
						  "fb_reple_content" : freeboard_repletext}
				, success : function(data){
			
						if(!(data < 1)) {
									
							$("#freeboard_repletext_update").val("");
							$("#lowerClose5").trigger("click");
							showReple(fb_boardnum);
							
						} else {
							alert("댓글 수정에 실패했습니다. 나중에 다시 한 번 시도해주세요!");
							$("#freeboard_repletext_update").val("");
							$("#lowerClose4").trigger("click");
						}
						
				}
			});
			
			
		}
				
		
		
	});
	
	
	
	// updateAndRemoveReple() 실행 후 modal의 버튼
	$("#deleteRepleButton").off("click").on("click", function(){
		
		
		let deleteConfirm = confirm("정말로 삭제하겠습니까?");
		
		if(deleteConfirm){
		
			$.ajax({
			url : "/freeboardReple/deleteReple"
			, method : "POST"
			, data : {"fb_reple_num" : fb_reple_num}
			, success : function(data){
		
				if(!(data < 1)) {
							
					$("#freeboard_repletext_update").val("");
					$("#lowerClose5").trigger("click");
					showReple(fb_boardnum);
					
				} else {
					alert("댓글 삭제에 실패했습니다. 나중에 다시 한 번 시도해주세요!");
					$("#freeboard_repletext_update").val("");
					$("#lowerClose4").trigger("click");
				}
				
					
			}
		});
		
		
	}
		
		
	});


}


function search(willBeNextPage) {
	
	var searchForm = document.getElementById("searchForm");
	var page = document.getElementById("page");
	
	page.value = willBeNextPage;
	
	searchForm.submit();
	
}



