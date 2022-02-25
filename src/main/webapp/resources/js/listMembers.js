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


	// 1. 비활성화, 활성화
	let deactivationBtns = document.querySelectorAll(".deactivation");
	let activationBtns = document.querySelectorAll(".activation");
	
	for(let btn of deactivationBtns) {
		btn.addEventListener("click", activationOrDeactivation);
	}
	
	for(let btn of activationBtns) {
		btn.addEventListener("click", activationOrDeactivation);
	}
	
	
	// 2. 파트너 승격, 파트너 강등
	let elevationBtns = document.querySelectorAll(".elevation");
	let demotionBtns = document.querySelectorAll(".demotion");
	
	console.log(elevationBtns);
	
	for(let btn of elevationBtns) {
		btn.addEventListener("click", elevationOrDemotion);
	}
	
	for(let btn of demotionBtns) {
		btn.addEventListener("click", elevationOrDemotion);
	}


});




function search(willBeNextPage) {
	
	var searchForm = document.getElementById("searchForm");
	var page = document.getElementById("page");
	
	page.value = willBeNextPage;
	
	searchForm.submit();
	
}

function activationOrDeactivation(event) {
	
	const whatToDo = event.target.value;
	
	console.dir(event);
	console.log(whatToDo);
	
	var newInput = document.createElement("input");
	newInput.setAttribute("type","button");
	newInput.setAttribute("class","btn btn-secondary btn-sm elevation");
	newInput.setAttribute("id", "newInput");
	
	let user_state = event.target.parentElement.parentElement.childNodes[7].innerText;
	console.dir(user_state);
	
	console.dir(event.target.parentElement.parentElement.childNodes[11].children[0]);
	
	
	if(whatToDo === "활성화 하기") {
		
		const thisUserId = event.target.parentElement.parentElement.lastElementChild.lastElementChild.value;
		console.log(thisUserId);
		
		event.target.parentElement.parentElement.childNodes[11].children[0].innerHTML = '<input type="button" class="btn btn-secondary btn-sm elevation" value="파트너 승격">';
		
		
		$.ajax({
			url : "/manager/activation"
			, method : "POST"
			, data : {"user_id" : thisUserId}
			, success : function(data){
				if(data > 0) {
					event.target.value = "비활성화 하기";
					event.target.parentElement.parentElement.childNodes[7].innerText = "활성화 계정";
					if(event.target.parentElement.parentElement.childNodes[7].innerText == "활성화 계정") {
						event.target.parentElement.parentElement.childNodes[11].children[0].value = "파트너 승격";
						event.target.parentElement.parentElement.childNodes[11].children[0].style.visibility = "visible";
					} else if(event.target.parentElement.parentElement.childNodes[7].innerText == "비활성화 계정") {
						event.target.parentElement.parentElement.childNodes[11].children[0].value = "";
						event.target.parentElement.parentElement.childNodes[11].children[0].style.visibility = "hidden";
					} else {
						event.target.parentElement.parentElement.childNodes[11].children[0].value = "파트너 강등";
						event.target.parentElement.parentElement.childNodes[11].children[0].style.visibility = "visible";
					}
				} 
				
				let elevationBtns = document.querySelectorAll(".elevation");
				console.log(elevationBtns);
				for(let btn of elevationBtns) {
					btn.addEventListener("click", specialElevationOrDemotion);
				}
				
			}
		});
	
	} else if (whatToDo === "비활성화 하기") {
		
		const thisUserId = event.target.parentElement.parentElement.lastElementChild.lastElementChild.value;
		console.log(thisUserId);
		
		// event.target.parentElement.parentElement.childNodes[11].innerHTML = '<input type="button" class="btn btn-secondary btn-sm elevation">';
		
		$.ajax({
			url : "/manager/deactivation"
			, method : "POST"
			, data : {"user_id" : thisUserId}
			, success : function(data){
				if(data > 0) {
					event.target.value = "활성화 하기";
					event.target.parentElement.parentElement.childNodes[7].innerText = "탈퇴 계정";
					if(event.target.parentElement.parentElement.childNodes[7].innerText == "탈퇴 계정") {
						event.target.parentElement.parentElement.childNodes[11].children[0].value = "";
						event.target.parentElement.parentElement.childNodes[11].children[0].style.visibility = "hidden";
					} else if(event.target.parentElement.parentElement.childNodes[7].innerText == "활성화 계정") {
						event.target.parentElement.parentElement.childNodes[11].children[0].value = "파트너 승격";
						event.target.parentElement.parentElement.childNodes[11].children[0].style.visibility = "visible";
					} else {
						event.target.parentElement.parentElement.childNodes[11].children[0].value = "파트너 강등";
						event.target.parentElement.parentElement.childNodes[11].children[0].style.visibility = "visible";
					}
				} 
			}
		});
		
	}

}



function elevationOrDemotion(event) {
	
	const whatToDo = event.target.value;
	
	console.dir(event);
	console.log(whatToDo);

	
	let user_state = event.target.parentElement.parentElement.childNodes[7].innerText;
	console.dir(user_state);
	
	console.dir(event.target.parentElement.parentElement.childNodes[11].children[0]);
	
	
	if(whatToDo === "파트너 승격") {
		
		const thisUserId = event.target.parentElement.parentElement.lastElementChild.lastElementChild.value;
		console.log(thisUserId);
		
		$.ajax({
			url : "/manager/elevation"
			, method : "POST"
			, data : {"user_id" : thisUserId}
			, success : function(data){
				if(data > 0) {
					event.target.value = "파트너 강등";
					event.target.parentElement.parentElement.childNodes[7].innerText = "파트너 계정";
				} 
			}
		});
		
	}
	
	
	if(whatToDo === "파트너 강등") {
		
		const thisUserId = event.target.parentElement.parentElement.lastElementChild.lastElementChild.value;
		console.log(thisUserId);
		
		$.ajax({
			url : "/manager/demotion"
			, method : "POST"
			, data : {"user_id" : thisUserId}
			, success : function(data){
				if(data > 0) {
					event.target.value = "파트너 승격";
					event.target.parentElement.parentElement.childNodes[7].innerText = "일반 계정";
				} 
			}
		});
		
	}
	
	
} 


function specialElevationOrDemotion(event) {
	
	const whatToDo = event.target.value;
	
	console.dir(event);
	console.log(whatToDo);
	
	
	let user_state = event.target.parentElement.parentElement.parentElement.children[3].innerText;
	console.dir(user_state);
	
	
	
	if(whatToDo === "파트너 승격") {
		
		const thisUserId = event.target.parentElement.parentElement.parentElement.children[1].innerText;
		console.log(thisUserId);
		
		$.ajax({
			url : "/manager/elevation"
			, method : "POST"
			, data : {"user_id" : thisUserId}
			, success : function(data){
				if(data > 0) {
					event.target.value = "파트너 강등";
					event.target.parentElement.parentElement.parentElement.children[3].innerText = "파트너 계정";
				} 
			}
		});
		
	}
	
	
	if(whatToDo === "파트너 강등") {
		
		const thisUserId = event.target.parentElement.parentElement.parentElement.children[1].innerText;
		console.log(thisUserId);
		
		$.ajax({
			url : "/manager/demotion"
			, method : "POST"
			, data : {"user_id" : thisUserId}
			, success : function(data){
				if(data > 0) {
					event.target.value = "파트너 승격";
					event.target.parentElement.parentElement.parentElement.children[3].innerText = "일반 계정";
				} 
			}
		});
		
	}
	
}



