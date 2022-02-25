<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>

<head>

<title>Daily Classy</title>

<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.bundle.js"></script>
<script type="text/javascript" src="/resources/js/index.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/index.css">
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.css">

</head>

</head>
<body>

	<div id="picture"></div>
	
	
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
	  <div class="container-fluid">
	    <a class="navbar-brand" href="${pageContext.request.contextPath}/">Daily Classy</a>
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
	            <li><a class="dropdown-item" href="/member/joinForm">회원가입</a></li>
	          </ul>
	        </li>
	        <li class="nav-item dropdown" id="secondLi">
	        	<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown"aria-expanded="false" >
	            룩북 게시판
	          </a>
	          <ul class="dropdown-menu" aria-labelledby="navbarDropdown" id="SecondUl">
	            <li><a class="dropdown-item" href="#">룩북 게시판 이동</a></li>
	            <li><a class="dropdown-item" href="#">룩북 게시판 작성</a></li>
	          </ul>
	        </li>
	        <li class="nav-item dropdown" id="thridLi">
	          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown"aria-expanded="false" >
	            자유 게시판
	          </a>
	          <ul class="dropdown-menu" aria-labelledby="navbarDropdown" id="ThirdUl">
	            <li><a class="dropdown-item" href="#">자유 게시판 이동</a></li>
	            <li><a class="dropdown-item" href="#">자유 게시판 작성</a></li>
	          </ul>
	        </li>
	      </ul>
	    </div>
	  </div>
	</nav>
	
	
	
	<div id="outWrapper">
		<form action="/member/login" id="loginForm" method="POST">
			<div class="input-group mb-3">
	 		 	<span class="input-group-text" id="idSpan">ID</span>
	  			<input type="text" class="form-control" name="user_id" id="user_id"placeholder="ID를 입력해주세요!" aria-label="user_id" aria-describedby="idSpan">
	  		</div>
	  		<div class="input-group mb-3">	
	  			<span class="input-group-text" id="passwordSpan">Password</span>
	  			<input type="password" class="form-control" name="user_pw" id="user_pw" placeholder="Password를 입력해주세요!" aria-label="user_pw" aria-describedby="passwordSpan">
			</div>
		</form>
	</div>	
	
	<div id="loginButtonDiv">
		<input type="button" class="btn btn-secondary btn-lg" id = "loginButton" value="로그인" onclick="doLogin();">
	</div>
	
	<div id="joinButtonDiv">
		<input type="button" class="btn btn-secondary btn-lg" id = "joinButton" value="회원가입" onclick = "location.href = '/member/joinForm'">
		<img src="/resources/images/kakao.png" alt="Kakao Login" onclick="kakaoLogin()" class="flex">
	</div>
	

</body>
</html>
