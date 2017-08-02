<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri= "http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular.min.js"></script>
	
	<script type="text/javascript">
		var mainApp = angular.module('mainApp', []);
		
		mainApp.controller('mainController', ['$scope', '$http', '$interval', function($scope, $http, $interval) {
			var interval;
			
			getData($scope, $http);
			interval = $interval(function(){
				getData($scope, $http);
			},10000);
		}]);
		
		function getData($scope, $http){
			$http({
				method : 'GET',
				url : '/api/main.json'
			}).success(function(data, status, headers, config){
				$scope.tickerData = data.dataList;
				
				$scope.coinone = data.coinoneBalance.normalWallets;
				$scope.coinoneXrp = data.coinoneBalance.xrp;
				$scope.coinoneKrw = data.coinoneBalance.krw;
				
				$scope.poloniexXrp = data.poloniexBalance.XRP;
				$scope.poloniexUsdt = data.poloniexBalance.USDT;
			}).error(function(data, status, headers, config){
			});
		}
		
	</script>
</head>
<body>

	<!-- angular Test -->
	<div ng-app="mainApp">
		<div ng-controller="mainController">
			<table border="0">
				<tr>
					<td>거래소</td>
					<td>코인</td>
					<td>현재가</td>
					<td>잔고</td>
				</tr>
				<tr>
					<td>코인원</td>
					<td>XRP</td>
					<td>{{tickerData.body}}</td>
					<td>{{coinoneKrw.balance}}</td>
				</tr>
				<tr>
					<td>폴로닉스</td>
					<td>XRP</td>
					<td>{{coinoneKrw.balance}}</td>
					<td>{{poloniexXrp}}</td>
				</tr>
			</table>
			<!-- <li ng-repeat="wallet in coinone">
				{{$index}}
				{{wallet.label}}
				{{wallet.balance}}
			</li> -->
		</div>
	</div>
	
</body>
</html>