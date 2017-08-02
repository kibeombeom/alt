<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri= "http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인 페이지</title>

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"> 
	<style> 
		body{
			background:#EAEAEA;
			position:absolute;
			top:23%;
			left:23%;
		}
		.container{ 
			
		} 
		input.ng-invalid { 
			border: 5px solid red; 
		} 
		
	</style> 

	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular.min.js"></script>
	
	<script type="text/javascript">
		var app = angular.module('loginApp', []);
		
		app.controller('loginController', function($scope) {
		  $scope.doLogin = function() {
		    alert("로그인");
		  }
		});
	</script>
</head>
<body ng-app="loginApp">
	<div id="wrap" class="container" ng-controller="loginController">
		<div class="row"> 
				<div class="col-md-4 col-md-offset-3"> 
				<form name="loginForm" class="form-signin" action="/login.do" method="post">
					<h2 class="form-signin-heading">Please sign in</h2>
					<label for="inputId" class="sr-only">아이디</label>
					<p><input type="text" name="userId" id="inputId" class="form-control" placeholder="아이디" required autofocus></p>
					<label for="inputPassword" class="sr-only">비밀번호</label>
					<p><input type="password" name="userPassword" id="inputPassword" class="form-control" placeholder="비밀번호" required></p>
					<p><button type="submit" class="btn btn-lg btn-primary btn-block signup-btn">로그인</button></p>
				</form>
			</div>
		</div>
	</div>
</body>
</html>