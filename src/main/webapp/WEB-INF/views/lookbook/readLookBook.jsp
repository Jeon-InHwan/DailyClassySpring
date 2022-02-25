<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Daily Classy Look Book</title>

<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.bundle.js"></script>
<script type="text/javascript" src="/resources/js/readLookBook.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/readLookBook.css">
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.css">

</head>

<body>
	
<div id="picture"></div>

<input type="hidden" id="hiddenKakao" value="${sessionScope.kakao}">
	
<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<div class="container-fluid">
		<a class="navbar-brand" href="${pageContext.request.contextPath}/member/afterLogin">Daily Classy</a>
	    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	      <span class="navbar-toggler-icon"></span>
	    </button>
	    <div class="collapse navbar-collapse" id="navbarSupportedContent">
	      <ul class="navbar-nav">
	        <li class="nav-item dropdown" id="firstLi">
	          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
	            회원메뉴
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown" id="firstUl">
            <li><a class="dropdown-item" href="/member/updateMemberForm">회원정보 수정</a></li>
           	<li><a class="dropdown-item" href="/member/withdrawalMemberForm">회원탈퇴</a></li>
          	<li><a class="dropdown-item" href="/member/checkMyInfo">포인트 조회 / 파트너 신청</a></li>
          	<c:if test="${sessionScope.user_state == 3}">
	  			<li><a class="dropdown-item" href="/manager/listMembers">회원 관리</a></li>
	  		</c:if>
          </ul>
        <li class="nav-item dropdown" id="secondLi">
        	<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown"aria-expanded="false" >
	            룩북 게시판
            </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown" id="SecondUl">
	            <li><a class="dropdown-item" href="/lookbook/listLookBooks">룩북 게시판 이동</a></li>
	            <li><a class="dropdown-item" href="/lookbook/writeLookBookForm">룩북 게시판 작성</a></li>
            </ul>
	        </li>
	        <li class="nav-item dropdown" id="thridLi">
	          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown"aria-expanded="false" >
	            자유 게시판
	          </a>
	          <ul class="dropdown-menu" aria-labelledby="navbarDropdown" id="ThirdUl">
	            <li><a class="dropdown-item" href="/freeboard/listFreeboards">자유 게시판 이동</a></li>
	            <li><a class="dropdown-item" href="/freeboard/writeFreeboardForm">자유 게시판 작성</a></li>
	          </ul>
	        </li>
      </ul>
    </div>
  </div>
</nav>


<div class="dropdown" id="dropdownId">
	<c:choose>
	  	<c:when test="${sessionScope.user_state == 0}">
	  		<input type="button" class="btn btn-secondary dropdown-toggle" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false" value="${sessionScope.loginNm}(${sessionScope.loginId})님">
	  	</c:when>
	  	<c:when test="${sessionScope.user_state == 1}">
	  		<input type="button" class="btn btn-secondary dropdown-toggle" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false" value="${sessionScope.loginNm}(${sessionScope.loginId})님">
	  		<span class="position-absolute top-0 start-0 translate-middle badge rounded-pill bg-danger">Partner</span>
	  	</c:when>
	  	<c:otherwise>
	  		<button type="button" class="btn btn-secondary dropdown-toggle" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
	  			${sessionScope.loginNm}<span class="position-absolute top-0 start-0 translate-middle badge rounded-pill bg-danger">Manager</span>
	  		</button>
	  	</c:otherwise>
  	</c:choose>
	<ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1" id="userUl">
	  <li><a class="dropdown-item" href="/member/updateMemberForm">회원정보 수정</a></li>
	  <li><a class="dropdown-item" href="/member/logout">로그아웃</a></li>
	</ul>
</div>

<button class="btn btn-light" id = "clock"></button>

<input type="hidden" id="hiddenUserState" value="${sessionScope.user_state}">
<input type="hidden" id="hiddenLoginedID" value="${sessionScope.loginId}">
<input type="hidden" id="hiddenBoardnum" value="${lookbook.lb_boardnum}">
<input type="hidden" id="hiddenUserId" value="${lookbook.user_id}">


<div id="tableWrapper">

	<div id="tableDiv"> 
		
		<div id="backwardDiv">
			<img alt="" src="/resources/images/backward.png">
			<span id ="backwardSpan">뒤로가기</span>
		</div>
		
		<table class="table table-dark table-hover" id="lookbookTable">
			<tr>
				<th>ID</th>
				<td class="tdClass">
					${lookbook.user_id}
				</td>
			</tr>
			<tr>
				<th>LookBook Title</th>
				<td class="tdClass">
					${lookbook.lb_title}
				</td>
			</tr>
			<tr>
				<th>Content</th>
				<td class="tdClass">
<pre id="preForText">
${lookbook.lb_content}
</pre>
				</td>
			</tr>
			<tr>
				<th>Tag</th>
				<td id="TagTd">
					
				</td>
			</tr>
			<tr>
				<th>Picture</th>
				<td id="picTd">
					<div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
						<div class="carousel-indicators">
							<button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
						</div>
					<div class="carousel-inner">
						<div class="carousel-item active carouselDiv" id="active">
					    	
					    	<div class="carousel-caption d-none d-md-block">
					     	</div>
					    </div>
						 <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
						   <span class="carousel-control-prev-icon" aria-hidden="true"></span>
						   <span class="visually-hidden">Previous</span>
						 </button>
						 <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
						   <span class="carousel-control-next-icon" aria-hidden="true"></span>
						   <span class="visually-hidden">Next</span>
						 </button>	 
						 </div>
					  </div>
					</td>
				</tr>
			<tr>
				<th>조회수</th>
				<td class="tdClass">
					${lookbook.lb_hitcount}
				</td>
			</tr>
			<tr>
				<th>추천수</th>
				<td class="tdClass" id="recommendTd">
					${lookbook.lb_likes}
				</td>
			</tr>
			<tr>
				<th>댓글수</th>
				<td class="tdClass" id="countRepleTd"></td>
			</tr>
			<tr>
				<th>등록일</th>
				<td class="tdClass">
					${lookbook.lb_regdate}
				</td>
			</tr>
			<tr>
				<th>구매링크</th>
				<td id="LinkTd" class="tdClass">
					
				</td>
			</tr>
		</table>
	</div>
	
</div>

<div id="buttonDiv">
	<input type="button" id="readLookBookReplyBtn" class="btn btn-secondary btn-lg" value="댓글 보기">
	<input type="button" id="recommendThisLookBook" class="btn btn-secondary btn-lg" value="추천하기" onclick="recommendThisLookBook();">
	<input type="button" id="searchByTagBtn" class="btn btn-secondary btn-lg" value="태그로 무신사 검색" onclick="searchByTag();">
</div>


<c:if test="${sessionScope.loginId == lookbook.user_id || sessionScope.user_state == 3}">
	<div id="writerDiv">
		<input type="button" id="deleteLookBookBtn" class="btn btn-danger" value="게시글 삭제">
	</div>
</c:if>
	



<!-- Button trigger modal -->
<input type="button" id="modalTrigger" value="Launch Modal" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">

<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg">
    <div class="modal-content" id="forTitleUpperColor">
      <div class="modal-header" id="forTitleColor">
        <h5 class="modal-title" id="exampleModalLabel">댓글</h5>
        <button type="button" class="btn-close btn-close" data-bs-dismiss="modal" aria-label="Close" id="upperClose"></button>
      </div>
      <div class="modal-body" id="innerContent1">
	  	
      </div>
      <div class="modal-body" id="innerContent3">
        
      </div>
      <div class="modal-body" id="innerContent4">
      	
      </div>	
      <div class="modal-footer">
        <input type="hidden" id="hiddenLoginedId" value="${sessionScope.loginId}">
        <input type="button" class="btn btn-primary btn-close-white" value="댓글 작성" id="modal_writeRepleButton" data-bs-target="#exampleModalToggle4" data-bs-toggle="modal" data-bs-dismiss="modal">
        <input type="button" class="btn btn-secondary btn-close-white" data-bs-dismiss="modal" id="lowerClose" value="Close">      
      </div>
    </div>
  </div>
</div>


<div class="modal fade" id="exampleModalToggle4" aria-hidden="true" aria-labelledby="exampleModalToggleLabel4" tabindex="-1">
  <div class="modal-dialog modal-dialog-centered modal-xl">
    <div class="modal-content" id="forTitleUpperColor4">
      <div class="modal-header" id="forTitleColor4">
        <h5 class="modal-title" id="exampleModalToggleLabel4">댓글 작성</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close" id="upperClose4"></button>
      </div>
      <div class="modal-body">
      	<input type="hidden" name="lb_boardnum" id="writeReple_lookbookboardnum">
		<input type="hidden" value="${sessionScope.loginId}" name="user_id" id="user_id">
		<div id="modal_writeRepleWrapper">
			<div id="modal_writeRepleDiv"> 
				<table class="table table-light table-hover" id="modal_writeRepleTable">
					<tr>
						<th id="writeRepleTableIdTh">ID</th>
						<td id="writeRepleId">${sessionScope.loginId}</td>
					</tr>
					<tr>
						<th id="writeRepleTableContentTh">댓글</th>
						<td id="updateContentTd">
							<div class="form-floating">
		  						<textarea class="form-control" name="lb_reple_content"  id="lb_reple_content" wrap="hard"></textarea>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
      </div>
      <div class="modal-footer">
     	 <input type="button" class="btn btn-secondary btn-close-white" id="writeReplyButton" value="댓글 작성">
     	 <input type="button" class="btn btn-secondary btn-close-white" data-bs-dismiss="modal" id="lowerClose4" value="Close">
      </div>
    </div>
  </div>
</div>	


<div class="modal fade" id="exampleModalToggle5" aria-hidden="true" aria-labelledby="exampleModalToggleLabel5" tabindex="-1">
  <div class="modal-dialog modal-dialog-centered modal-xl">
    <div class="modal-content" id="forTitleUpperColor5">
      <div class="modal-header" id="forTitleColor5">
        <h5 class="modal-title" id="exampleModalToggleLabel5">댓글 수정 ／ 댓글 삭제</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close" id="upperClose5"></button>
      </div>
      <div class="modal-body" id="forDeleteBody">
      	<div id="modal_updateRepleWrapper">
			<div id="modal_updateRepleDiv"> 
				<table class="table table-light table-hover" id="modal_updateRepleTable">
					<tr>
						<th id="updateRepleTableIdTh">ID</th>
						<td id="updateRepleId">${sessionScope.loginId}</td>
					</tr>
					<tr>
						<th id="updateRepleTableContentTh">댓글</th>
						<td id="updateRepleContentTd">
							<div class="form-floating">
		  						<textarea class="form-control" name = "lb_reple_content"  id="lb_repletext_update" wrap="hard"></textarea>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
      	<input type="hidden" value="${sessionScope.loginId}" id="loginIdforToggle5">
      	<input type="hidden" id="lookBookBoard_numforToggle5" value="${lookbook.lb_boardnum}">
      </div>
      <div class="modal-footer">
     	 <input type="button" class="btn btn-secondary btn-close-white" id="updateRepleButton" value="수정">
     	 <input type="button" class="btn btn-secondary btn-close-white" id="deleteRepleButton" value="삭제">
     	 <input type="button" class="btn btn-secondary btn-close-white" data-bs-dismiss="modal" id="lowerClose5" value="Close">
      </div>
    </div>
  </div>
</div>


	
</body>
</html>