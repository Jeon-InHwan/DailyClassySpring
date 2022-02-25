<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Daily Classy 회원가입 </title>

<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.bundle.js"></script>
<script type="text/javascript" src="/resources/js/joinForm.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/joinForm.css">
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.css">

</head>
<body>

<div id="picture"></div>


<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">Daily Classy</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
  </div>
</nav>


<div id="outWrapper">
	<form action="/member/join" id="joinForm" method="POST">
		<div class="input-group mb-3">
 		 	<span class="input-group-text" id="idSpan">ID</span>
  			<input type="text" class="form-control" id="user_id" name="user_id" placeholder="ID는 5글자 이상, 12글자 이하로 입력해주세요!" aria-label="user_id" aria-describedby="idSpan">
  			<input type="button" id="idDoubleCheckButton" class="btn btn-secondary" value="ID중복체크" disabled="disabled">
  		</div>
  		<div class="input-group mb-3">	
  			<span class="input-group-text" id="passwordSpan">Password</span>
  			<input type="password" class="form-control" id="user_pw" name="user_pw" placeholder="Password는 5글자 이상, 12글자 이하로 입력해주세요!" aria-label="user_pwd" aria-describedby="passwordSpan">
		</div>
		<div class="input-group mb-3">	
  			<span class="input-group-text" id="nameSpan">Name</span>
  			<input type="text" class="form-control" id="user_name" name="user_name" placeholder="공백없이 이름을 입력해주세요!" aria-label="user_pwd" aria-describedby="nameSpan">
		</div>
	</form>
</div>	

<div id="joinButtonDiv">
	<input type="button" class="btn btn-secondary btn-lg" id = "joinButton" value="회원가입!" onclick="formCheck();">
</div>




</body>
</html>