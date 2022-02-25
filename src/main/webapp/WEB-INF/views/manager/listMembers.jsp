<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[관리자] 회원목록</title>

<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.bundle.js"></script>
<script type="text/javascript" src="/resources/js/listMembers.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/listMembers.css">
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

<button class="btn btn btn-light" id="clock"></button>



<div id="tableWrapper">
	
	
	<div id="searchDiv">
		<form id="searchForm" action="/manager/listMembers" method="GET">
			<select class="form-select form-select-sm" aria-label=".form-select-sm example" id="searchItem" name="searchItem">
			  <option value="user_id" ${searchItem == 'user_id' ? 'selected' : ''} >ID</option>
			  <option value="user_name" ${searchItem == 'user_name' ? 'selected' : ''}>이름</option>
			</select>
			<select class="form-select form-select-sm" aria-label=".form-select-sm example" id="searchStatus" name="searchStatus">
			  <option value="all" ${searchStatus == 'all' ? 'selected' : ''} >모든 계정</option>
			  <option value="-1" ${searchStatus == '-1' ? 'selected' : ''} >비활성화 계정</option>
			  <option value="0" ${searchStatus == '0' ? 'selected' : ''} >일반 사용자 계정</option>
			  <option value="1" ${searchStatus == '1' ? 'selected' : ''}>파트너 사용자 계정</option>
			</select>
			<input type="text" name="searchWord" value="${searchWord}">
			<input type="hidden" name="page" id="page" value="">
			<input type="button" class="btn btn-secondary btn-sm" id="searchButton" value="검색" onclick="search(1)"><br>
		</form>
	</div>
	
	
	<div id="tableDiv"> 
		<table class="table table-dark table-hover" id="memberTable">
			<tr>
				<th>NO</th>
				<th>ID</th>
				<th>이름</th>
				<th>계정 상태</th>
				<th>계정 활성화/비활성화</th>
				<th>파트너 승격/강등</th>
				<th>가입일</th>
			</tr>
			<c:set var="num" value="${navi.totalRecordsCount - ((navi.currentPage-1)*10)}"/>
			<c:forEach var="member" items="${list}" varStatus="stat">
				<tr class="forClick">
					<td >${num}</td>
					<td id="user_id">${member.user_id}</td>
					<td id="user_name">${member.user_name}</td>
					<td id="user_state">
						<c:choose>
							<c:when test="${member.user_state == 0}">
								일반 계정
							</c:when>
							<c:when test="${member.user_state == 1}">
								파트너 계정
							</c:when>
							<c:when test="${member.user_state == -1}">
								탈퇴 계정
							</c:when>
						</c:choose>
					</td>
					<td id="user_state_btn1">
						<c:choose>
							<c:when test="${member.user_state == 0 || member.user_state == 1}">
								<input type="button" class="btn btn-secondary btn-sm deactivation" value="비활성화 하기">
							</c:when>
							<c:when test="${member.user_state == -1}">
								<input type="button" class="btn btn-secondary btn-sm activation" value="활성화 하기">
							</c:when>
						</c:choose>		
					</td>
					<td id="user_state_btn2">
						<c:choose>
							<c:when test="${member.user_state == -1}">
								<div class="stateDiv"></div>
							</c:when>
							<c:when test="${member.user_state == 0}">
								<input type="button" class="btn btn-secondary btn-sm elevation" value="파트너 승격">
							</c:when>
							<c:otherwise>
								<input type="button" class="btn btn-secondary btn-sm demotion" value="파트너 강등">
							</c:otherwise>
						</c:choose>		
					</td>
					<td id="user_regdate">${member.user_regdate}</td>
					<td class="hiddenTd"><input type="hidden" value="${member.user_id}"></td>
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


	
</body>
</html>