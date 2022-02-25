<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Freeboard</title>

<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.bundle.js"></script>
<script type="text/javascript" src="/resources/js/listFreeboards.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/listFreeboards.css">
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.css">


</head>
<body>
	
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


<div id="picture"></div>

<button class="btn btn-light" id = "clock"></button>

<input type="hidden" id="hiddenLoginedId" value="${sessionScope.loginId}">
<input type="hidden" id="hiddenUserState" value="${sessionScope.user_state}">

 
<div id="tableWrapper">

	<div id="searchDiv">
		<form id="searchForm" action="/freeboard/listFreeboards" method="GET">
			<select class="form-select form-select-sm" aria-label=".form-select-sm example" id="searchItem" name="searchItem">
			  <option value="fb_title" ${searchItem == 'fb_title' ? 'selected' : ''} >TITLE</option>
			  <option value="user_id" ${searchItem == 'user_id' ? 'selected' : ''}>WRITER</option>
			  <option value="fb_content" ${searchItem == 'fb_content' ? 'selected' : ''}>CONTENT</option>
			</select>
			<input type="text" name="searchWord" value="${searchWord}">
			<input type="hidden" name="page" id="page" value="">
			<input type="button" class="btn btn-secondary btn-sm" value="검색" onclick="search(1)"><br>
		</form>
	</div>

	<div id="tableDiv"> 
		<table class="table table-dark table-hover" id="freeBoardTable">
			<tr>
				<th>NO</th>
				<th>ID</th>
				<th>TITLE</th>
				<th>조회수</th>
				<th>등록일</th>
			</tr>
			<c:set var="num" value="${navi.totalRecordsCount - ((navi.currentPage-1)*10)}"/>
			<c:forEach var="free" items="${list}" varStatus="stat">
				<tr class="forClick">
					<td>${num}</td>
					<c:choose>
						<c:when test="${free.user_id == '3'}">
							<td><span class="badge bg-primary">Manager</span></td>
						</c:when>
						<c:otherwise>
							<td><span class="badge bg-secondary">${free.user_id}</span></td>
						</c:otherwise>
					</c:choose>
					<td>${free.fb_title}</td>
					<td>${free.fb_hitcount}</td>
					<td>${free.fb_regdate}</td>
					<td class="hidden_td">${free.fb_boardnum}</td>
					<td class="hidden_td"><input type="hidden" value="${free.fb_boardnum}"></td>
				</tr>
			<c:set var="num" value="${num-1}"></c:set>	
			</c:forEach>
		</table>
	</div>
</div>


<div id = "pagenationDiv">
	<nav aria-label="Page navigation example" id="pagenationNaV">
	  <ul class="pagination" id="pagination">
	    <li class="page-item">
	      <a class="page-link" href="javascript:search(${navi.currentPage - 1});" aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	      </a>
	    </li>
	    <c:forEach var="num" begin="${navi.startPageGroup}" end="${navi.endPageGroup}">
			<c:choose>
				<c:when test="${navi.currentPage == num}">
					<li class="page-item"><a id="hilightCurrent" class="page-link" href="javascript:search(${num});">${num}</a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" href="javascript:search(${num});">${num}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	    <li class="page-item">
	      <a class="page-link" href="javascript:search(${navi.currentPage + 1});" aria-label="Next">
	        <span aria-hidden="true">&raquo;</span>
	      </a>
	    </li>
	  </ul>
	</nav>
</div>

	<div id="buttonDiv">
		<input type="button" id="writeButton" class="btn btn-secondary btn-lg" value="게시판 작성">
	</div>

	<!-- Button trigger modal -->
	<input type="button" id="modalTrigger" value="Launch Modal" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">

	<!-- Modal -->
	<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg">
	    <div class="modal-content" id="forTitleUpperColor">
	      <div class="modal-header" id="forTitleColor">
	        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
	        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close" id="upperClose"></button>
	      </div>
	      <div class="modal-body" id="innerContent1">
		  	
	      </div>
	      <div class="modal-body" id="innerContent3">
	        
	      </div>
	      <div class="modal-body" id="innerContent4">
	      
	      </div>	
	      <div class="modal-footer">
	      	<input type="button" class="btn btn-primary btn-close-white" value="댓글 작성" id="modal_writeRepleButton" data-bs-target="#exampleModalToggle4" data-bs-toggle="modal" data-bs-dismiss="modal">
	      	<input type="button" class="btn btn-primary btn-close-white" value="게시글 삭제" id="modal_DeleteButton" data-bs-target="#exampleModalToggle3" data-bs-toggle="modal" data-bs-dismiss="modal">
	      	<input type="button" class="btn btn-primary btn-close-white" value="게시글 수정" id="modal_updateButton" data-bs-target="#exampleModalToggle2" data-bs-toggle="modal" data-bs-dismiss="modal">
	        <input type="button" class="btn btn-secondary btn-close-white" data-bs-dismiss="modal" id="lowerClose" value="Close">
	        <input type="hidden" id="hiddenLoginedId" value="${sessionScope.loginId}">
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
	      	<input type="hidden" name="fb_boardnum" id="writeReplr_freeboardnum">
			<input type="hidden" value="${sessionScope.loginId}" name="user_id" id="user_id">
			<div id="modal_writeRepleWrapper">
				<div id="modal_writeRepleDiv"> 
					<table class="table table-dark table-hover" id="modal_writeRepleTable">
						<tr>
							<th id="writeRepleTableIdTh">ID</th>
							<td id="writeRepleId">${sessionScope.loginId}</td>
						</tr>
						<tr>
							<th id="writeRepleTableContentTh">댓글</th>
							<td id="updateContentTd">
								<div class="form-floating">
			  						<textarea class="form-control" name="fb_reple_content"  id="fb_reple_content" wrap="hard"></textarea>
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

	<div class="modal fade" id="exampleModalToggle2" aria-hidden="true" aria-labelledby="exampleModalToggleLabel2" tabindex="-1">
	  <div class="modal-dialog modal-dialog-centered modal-xl">
	    <div class="modal-content" id="forTitleUpperColor2">
	      <div class="modal-header" id="forTitleColor2">
	        <h5 class="modal-title" id="exampleModalToggleLabel2">게시글 수정</h5>
	        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close" id="upperClose2"></button>
	      </div>
	      <div class="modal-body">
	      	<form action="#" id="NotDifined" method=POST>
	      		<input type="hidden" name="fb_boardnum" id="update_num">
				<input type="hidden" value="${sessionScope.loginId}" name="user_id" id="user_id">
				<input type="hidden" id="update_indate">
				<input type="hidden" id="update_hits">
				<div id="modal_updateWrapper">
					<div id="modal_updateDiv"> 
						<table class="table table-dark table-hover" id="modal_writeFormTable">
							<tr>
								<th>ID</th>
								<td id="updateId">${sessionScope.loginId}</td>
							</tr>
							<tr>
								<th>TITLE</th>
								<td>
									<div class="input-group mb-3">	
			  							<input type="text" class="form-control" name="fb_title" id="update_title" aria-label="fb_title" aria-describedby="basic-addon1">
			  						</div>
								</td>
							</tr>
							<tr>
								<th>CONTENT</th>
								<td id="updateContentTd">
									<div class="form-floating">
			  							<textarea class="form-control" name = "fb_content"  id="update_context" wrap="hard"></textarea>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</form>
	      </div>
	      <div class="modal-footer">
	     	 <input type="button" class="btn btn-secondary btn-close-white" id="updateButton" value="게시글 수정">
	     	 <input type="button" class="btn btn-secondary btn-close-white" data-bs-dismiss="modal" id="lowerClose2" value="Close">
	      </div>
	    </div>
	  </div>
	</div>	
	
	
	<div class="modal fade" id="exampleModalToggle3" aria-hidden="true" aria-labelledby="exampleModalToggleLabel3" tabindex="-1">
	  <div class="modal-dialog modal-dialog-centered modal-sm">
	    <div class="modal-content" id="forTitleUpperColor3">
	      <div class="modal-header" id="forTitleColor3">
	        <h5 class="modal-title" id="exampleModalToggleLabel3">게시글 삭제</h5>
	        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close" id="upperClose3"></button>
	      </div>
	      <div class="modal-body" id="forDeleteBody">
	      	정말로 삭제하겠습니까？
	      </div>
	      <div class="modal-footer">
	     	 <input type="button" class="btn btn-secondary btn-close-white" id="deleteButton" value="삭제">
	     	 <input type="button" class="btn btn-secondary btn-close-white" data-bs-dismiss="modal" id="lowerClose3" value="취소">
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
					<table class="table table-dark table-hover" id="modal_updateRepleTable">
						<tr>
							<th id="updateRepleTableIdTh">ID</th>
							<td id="updateRepleId">${sessionScope.loginId}</td>
						</tr>
						<tr>
							<th id="updateRepleTableContentTh">댓글</th>
							<td id="updateRepleContentTd">
								<div class="form-floating">
			  						<textarea class="form-control" name = "fb_reple_content"  id="freeboard_repletext_update" wrap="hard"></textarea>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
	      	<input type="hidden" value="${sessionScope.loginId}" id="loginIdforToggle5">
	      	<input type="hidden" id="freeboard_numforToggle5">
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