<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome to Daily Classy</title>

<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.bundle.js"></script>
<script type="text/javascript" src="/resources/js/afterLogin.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/afterLogin.css">
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

<button class="btn btn-light" id="clock"></button>


<div id="outWrapper">

	<div class="card" style="width: 18rem;" id="card1">
	  <img src="/resources/images/card01.jpg" class="card-img-top" alt="...">
	  <div class="card-body">
	    <h5 class="card-title">개인정보 수정</h5>
	    <p class="card-text">보안을 위해 주기적으로 <br>비밀번호를 바꿔주세요!</p>
	    <a href="/member/updateMemberForm" class="btn btn-outline-dark">지금 수정하기!</a>
	  </div>
	</div>
	
	<div class="card" style="width: 18rem;" id="card2">
	  <img src="/resources/images/card02.jpg" class="card-img-top" alt="...">
	  <div class="card-body">
	    <h5 class="card-title">Look Book 작성</h5>
	    <p class="card-text">오늘은 어떤 옷을 입으셨나요? <br> 지금, 당신의 패션을 보여주세요!</p>
	    <a href="/lookbook/writeLookBookForm" class="btn btn-outline-dark">룩북 작성하기!</a>
	  </div>
	</div>
	
	
	<div id="tableWrapper">
		<table class="table table-dark table-hover" id="popularTable">
		  <thead>
		    <tr>
		      <th scope="col">#</th>
		      <th scope="col">인기글</th>
		      <th scope="col">추천수</th>
		    </tr>
		  </thead>
		  <tbody>
		  <c:forEach var="pop" items="${list}" varStatus="stat">
		    <tr class="forClick">
		      <th scope="row">${stat.count}</th>
		      <th>${pop.lb_title}</th>
			  <th scope="col">${pop.lb_likes}</th>
			  <th class="hiddenTd">${pop.lb_boardnum}</th>
		    </tr>
		  </c:forEach>
		  </tbody>
		</table>
	</div>

</div>

</body>
</html>