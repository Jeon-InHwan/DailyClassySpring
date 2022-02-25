function loadJQuery() {
    var oScript = document.createElement("script");
    oScript.type = "text/javascript";
    oScript.charset = "utf-8";		  
    oScript.src = "/resources/js/jquery-3.6.0.js";	
    document.getElementsByTagName("head")[0].appendChild(oScript);
}

// 파일 현재 필드 숫자 totalCount랑 비교값
let fileCount = 0;

// 해당 숫자를 수정하여 전체 업로드 갯수 지정
let totalCount = 10;

// 파일 고유넘버
let fileNum = 0;

// 첨부파일 배열
let content_files = new Array();


let filesArr;


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
	
		
	$("#tagInputBtn").on({
		click: clickEvent,
	});	
		
	function clickEvent(){
		
		let tag = $("#tagInput").val();
		
		if(tag.length < 1) {
			alert("tag를 입력해주세요!");
			return;
		}
		
		$("#tagDiv").append('<input type="button" class="btn btn-light" value=' + '"#' + tag + '"' + '</input>' + '<input type="button" class="forDelete" value="❌">');
		$(".forDelete").css({
			"display": "inline",
			"cursor": "pointer",
			"background": "none",
			"border": "none"
		});
		
		$("#tagInput").val("");
		
		$(".forDelete").off().on("click", function(){
			$(this).prev().remove();
			$(this).remove();
		});
		
		
	};
	
	
	$("#tagInput").on("keypress", function(e){
		if (e.which == 13){
			clickEvent();
		} else {
			return;
		}
	});
	
	
	$("#linkInputBtn").on({
		click: linkClickEvent,
	});	
		
	function linkClickEvent(){
		
		let link = $("#linkInput").val();
		
		if(link.length < 1) {
			alert("link를 입력해주세요!");
			return;
		}
		
		$("#linkDiv").append('<input type="button" class="btn btn-light" value=' + '"' + link + '"' + '</input>' + '<input type="button" class="forLinkDelete" value="❌">');
		$(".forLinkDelete").css({
			"display": "inline",
			"cursor": "pointer",
			"background": "none",
			"border": "none"
		});
		
		$("#linkInput").val("");
		
		$(".forLinkDelete").off().on("click", function(){
			$(this).prev().remove();
			$(this).remove();
		});
		
		
	};
	
		$("#linkInput").on("keypress", function(e){
			if (e.which == 13){
				linkClickEvent();
			} else {
				return;
		}
		
	});
	
	
	
	$("#input_file").on("change", fileCheck);
	
		
	function fileCheck(e) {
	    
		let files = e.target.files;
	    
	    // 파일 배열 담기
	    filesArr = Array.prototype.slice.call(files);
	    
	    // 파일 개수 확인 및 제한
	    if (fileCount + filesArr.length > totalCount) {
			$.alert('파일은 최대 ' + totalCount + '개까지 업로드 할 수 있습니다!');
	      return;
	    } else {
	    	fileCount = fileCount + filesArr.length;
		}
    
    
	// 각각의 파일 배열담기 및 기타
    filesArr.forEach(function (f) {
      
		let reader = new FileReader();
		reader.onload = function (e) {
		content_files.push(f);
        
        $('#articlefileChange').append(
       		'<div id="file' + fileNum + '" onclick="fileDelete(\'file' + fileNum + '\');">'
       		+ '<font style="font-size:12px">' + f.name + '</font>'  
       		+ '<img src="/resources/images/icon_minus.png" style="width:20px; margin-left:10px; height:auto; vertical-align: middle; cursor: pointer;"/>' 
       		+ '<div/>'
		);
		
        fileNum ++;
      
      };
      
      reader.readAsDataURL(f);
    
    });
	    
	    console.log(content_files);
	    
	    //초기화
	    $("#input_file").val("");
	
	}

	
	


});


// 파일 부분 삭제 함수
function fileDelete(fileNum){
    let no = fileNum.replace(/[^0-9]/g, "");
    content_files[no].is_delete = true;
	$('#' + fileNum).remove();
	fileCount --;
    console.log(content_files);
}


function writeLookBook() {
	
	const lb_title = document.querySelector("#lb_title").value;
	const lb_content = document.querySelector("#lb_content").value;
	let tagBtn = $("#tagDiv").find('.btn-light');
	let linkBtn = $("#linkDiv").find('.btn-light');
    
    if(filesArr == null) {
		alert("룩북 사진 파일 업로드는 필수입니다!");
		return;
	}
	
	let tagTemp = []; 
	let link_addTemp = [];
	
	$.each(tagBtn, function(index){
		tagTemp[index] = $(this).val();
	});
	
	for (var i = 0; i < tagTemp.length; i++) {
    	console.log(tagTemp[i]);
	}   
	
	$.each(linkBtn, function(index){
		link_addTemp[index] = $(this).val();
	});
	
	for (var i = 0; i < link_addTemp.length; i++) {
    	console.log(link_addTemp[i]);
	}    
	
	
	let form = $("form")[0];        
 	let formData = new FormData(form);
		
	for (var x = 0; x < content_files.length; x++) {
		
		// 삭제 안한것만 담아 준다. 
		if(!content_files[x].is_delete){
			 formData.append("article_file", content_files[x]);
		}
		
	}
	
	var lookbook = {   
	    "link_add"    	: link_addTemp,
	    "tag"     		: tagTemp,
	    "lb_title"  	: lb_title,
	    "lb_content" 	: lb_content
	}
	
	console.log(lookbook);
	
	formData.append("lookbook", new Blob([ JSON.stringify(lookbook) ], {type : "application/json"}));
	
	if(lb_title.trim().length < 1) {
		alert("룩북 제목은 필수 입력값입니다!");
		return;
	}
	
	if(lb_title.trim().length > 50) {
		alert("룩북 제목이 너무 깁니다!");
		return;
	}
	
	if(lb_content.trim().length < 1) {
		alert("룩북 내용은 필수 입력값입니다!");
		return;
	}
	
	if(lb_content.trim().length > 1000) {
		alert("룩북 내용이 너무 깁니다!");
		return;
	}
	
	if(tagBtn.length < 1) {
		alert("태그 작성을 완료해주세요!");
		return;
	}
	
	
	
	$.ajax({
		url : "/lookbook/writeLookBook"
		, method : "POST"
		, enctype: "multipart/form-data"
		, data : formData
		, processData: false
		, contentType: false
		, success : function(data){
			if(data < 1) {
				alert("룩북 작성에 실패했습니다(1)!");
				return;
			} else {
				console.log(data);
				alert("파일업로드 성공");
				window.location.href = "/lookbook/listLookBooks";
			}	
		}
			, error: function (xhr, status, error) {
				alert("서버오류로 지연되고있습니다. 잠시 후 다시 시도해주시기 바랍니다.");
				return false;
		  	}		
		
	});
	
	
	
	
	

}



