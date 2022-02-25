<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LookBook List</title>

<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.bundle.js"></script>
<script type="text/javascript" src="/resources/js/listLookBooks.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/listLookBooks.css">
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.css">

</head>

<body>
	
<div id="picture"></div>

<input type="hidden" id="hiddenLoginedId" value="${sessionScope.loginId}">
<input type="hidden" id="hiddenUserState" value="${sessionScope.user_state}">
	
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


<div id="tableWrapper">

	<div id="searchDiv">
		<form id="searchForm" action="/lookbook/listLookBooks" method="GET">
			<select class="form-select form-select-sm" aria-label=".form-select-sm example" id="searchItem" name="searchItem">
			  <option value="lb_title" ${searchItem == 'lb_title' ? 'selected' : ''} >TITLE</option>
			  <option value="user_id" ${searchItem == 'user_id' ? 'selected' : ''}>WRITER ID</option>
			  <option value="lb_content" ${searchItem == 'lb_content' ? 'selected' : ''}>TEXT</option>
			</select>
			<input type="text" id="searchWord" name="searchWord" value="${searchWord}">
			<input type="hidden" name="page" id="page" value="">
			<input type="button" class="btn btn-secondary btn-sm" id="searchButton" value="검색" onclick="search(1)"><br>
		</form>
	</div>

	<div id="tableDiv"> 
		<table class="table table-dark table-hover" id="lookbookTable">
			<tr>
				<th>NO</th>
				<th>ID</th>
				<th>LookBook Title</th>
				<th>조회수</th>
				<th>추천수</th>
				<th>등록일</th>
			</tr>
			<c:set var="num" value="${navi.totalRecordsCount - ((navi.currentPage-1)*10)}"/>
			<c:forEach var="look" items="${list}" varStatus="stat">
				<tr class="forClick">
					<td>${num}</td>
					<c:choose>
						<c:when test="${look.user_id == '3'}">
							<td><span class="badge bg-primary">Manager</span></td>
						</c:when>
						<c:otherwise>
							<td><span class="badge bg-secondary">${look.user_id}</span></td>
						</c:otherwise>
					</c:choose>
					<td>${look.lb_title}</td>
					<td>${look.lb_hitcount}</td>
					<td>${look.lb_likes}</td>
					<td>${look.lb_regdate}</td>
					<td class="hiddenTd">${look.lb_boardnum}</td>
					<td class="hiddenTd"><input type="hidden" value="${look.lb_boardnum}"></td>
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
	<input type="button" id="toWriteLookBookFormButton" class="btn btn-secondary btn-lg" value="LookBook 작성 페이지로 이동" onclick="toWriteLookBookForm();">
</div>


	
</body>
</html>