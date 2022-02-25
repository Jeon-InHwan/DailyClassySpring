<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Daily Classy 회원정보 확인</title>

<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.bundle.js"></script>
<script type="text/javascript" src="/resources/js/checkMyInfo.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/checkMyInfo.css">
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.css">

</head>
<body>

<div id="picture"></div>

<input type="hidden" id="hiddenLoginedID" value="${sessionScope.loginId}">


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


<button class="btn btn-light" id="clock"></button>


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


<div id="outWrapper">
	<table class="table table-light table-hover" id="lookbookTable">
		<tr>
			<th>ID</th>
			<td id="idTd">${member.user_id}</td>
		</tr>
		<tr>
			<th>Name</th>
			<td id="nameTd">${member.user_name}</td>
		</tr>
		<tr>
			<th>회원 등급</th>
			<td id="memberRankTd">
				<c:choose>
				  	<c:when test="${member.user_state == 0}">
				  		일반 회원
				  	</c:when>
				  	<c:when test="${member.user_state == 1}">
				  		파트너 회원
				  	</c:when>
				  	<c:otherwise>
				  		관리자
				  	</c:otherwise>
			  	</c:choose>
			</td>
		</tr>
		<tr>
			<th>가입일</th>
			<td id="regdateTd">${member.user_regdate}</td>
		</tr>
		<tr>
			<th>작성한 LookBook 게시글 수</th>
			<td id="lookbookCountTd"></td>
		</tr>
		<tr>
			<th>링크/태그 클릭 포인트</th>
			<td id="link_hitsTd">${member.link_hits  * 10} Point</td>
		</tr>	
	
	</table>
	
	<c:if test="${sessionScope.user_state == 0}">	
		<span id="explanationSpan">&nbsp;&nbsp;작성한 게시글 5개 이상, 링크/태그 클릭 포인트 1000점 이상 시 파트너 신청 가능!&nbsp;&nbsp;</span>
	</c:if>
	
</div>	



<c:if test="${sessionScope.user_state == 0}">
	<div id="buttonDiv">
		<input type="button" class="btn btn-secondary btn-lg" id="requestChangeUser_state" value="파트너 신청!" onclick="requestCheck();">
	</div>
</c:if>

</body>
</html>