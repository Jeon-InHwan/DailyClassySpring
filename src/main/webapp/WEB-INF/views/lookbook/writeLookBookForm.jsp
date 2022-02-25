<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LookBook 작성</title>

<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.bundle.js"></script>
<script type="text/javascript" src="/resources/js/writeLookBookForm.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/writeLookBookForm.css">
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.css">

</head>
<body>

<div id="picture"></div>

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

<!-- enctype="multipart/form-data" -->

<form action="/lookbook/writeLookBook" id="writeLookBook"  method=POST>
	<div id="tableWrapper">
		<div id="tableDiv"> 
			<table class="table table-dark table-hover" id="lookbookTable">
				<tr>
					<th id="idTh">ID</th>
					<td>${sessionScope.loginId}</td>
				</tr>
				<tr>
					<th>LookBook Title</th>
					<td>
						<div class="input-group mb-3">	
  							<input type="text" class="form-control" name="lb_title" id="lb_title" placeholder="" aria-label="lb_title" aria-describedby="basic-addon1">
  						</div>
					</td>
				</tr>
				<tr>
					<th>Content</th>
					<td id="contentTd">
						<div class="form-floating">
  							<textarea class="form-control" name = "lb_content"  id="lb_content"></textarea>
						</div>
					</td>
				</tr>
				<tr>
					<th>사진 첨부파일</th>
					<td id="fileTd">
						<div class="form-floating">
  							<input id="input_file" multiple="multiple" type="file">
  							<span style="font-size:10px; color: gray;">※ 첨부파일은 최대 10개까지 등록이 가능합니다!</span>
						</div>
						<div class="data_file_txt" id="data_file_txt" style="margin:40px;">
							<span>첨부 파일</span>
							<br />
							<div id="articlefileChange">
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<th><div id="tagThDiv">태그 입력</div></th>
					<td id="tagTd">
						<input type="text" id="tagInput"><br>
						<input type="button" id="tagInputBtn" class="btn btn-secondary" value="태그 입력">
					</td>
				</tr>
				<tr>
					<th>등록된 태그</th>
					<td id="inputedTd">
						<div id="tagDiv"></div>
					</td>
				</tr>
				<tr>
					<th>구매처 링크</th>
					<td id="buyLinkTd">
						<input type="text" id="linkInput"><br>
						<input type="button" id="linkInputBtn" class="btn btn-secondary" value="구매처 링크 입력">
					</td>
				</tr>
				<tr>
					<th>등록된 구입처 링크</th>
					<td id="inputedLinkTd">
						<div id="linkDiv"></div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>

	

<div id="buttonDiv">
	<input type="button" id="writeButton" class="btn btn-secondary btn-lg" value="LookBook 작성" onclick="writeLookBook();">
</div>



</body>
</html>