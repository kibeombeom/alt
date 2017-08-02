<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri= "http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<style>
		body{padding-top:50px}body>.navbar{-webkit-transition:background-color .3s ease-in;transition:background-color .3s ease-in}@media (min-width:768px){body>.navbar-transparent{background-color:transparent}body>.navbar-transparent .navbar-nav>.open>a{background-color:transparent!important}}#home{padding-top:0}#home .navbar-brand{padding:13.5px 15px 12.5px}#home .navbar-brand>img{display:inline;margin:0 10px;height:100%}#banner{min-height:300px;border-bottom:none}.table-of-contents{margin-top:1em}.page-header h1{font-size:4em}.bs-docs-section{margin-top:6em}.bs-docs-section h1{padding-top:100px}.bs-component{position:relative}.bs-component .modal{position:relative;top:auto;right:auto;left:auto;bottom:auto;z-index:1;display:block}.bs-component .modal-dialog{width:90%}.bs-component .popover{position:relative;display:inline-block;width:220px;margin:20px}#source-button{position:absolute;top:0;right:0;z-index:100;font-weight:700}.nav-tabs{margin-bottom:15px}.progress{margin-bottom:10px}footer{margin:5em 0}footer li{float:left;margin-right:1.5em;margin-bottom:1.5em}footer p{clear:left;margin-bottom:0}.splash{padding:9em 0 2em;background-color:#141d27;background-image:url(../img/bg.jpg);background-size:cover;background-attachment:fixed;color:#fff;text-align:center}.splash .logo{width:160px}.splash h1{font-size:3em}.splash #social{margin:2em 0}.splash .alert{margin:2em 0}.section-tout{padding:4em 0 3em;border-bottom:1px solid rgba(0,0,0,.05);background-color:#eaf1f1}.section-tout .fa{margin-right:.5em}.section-tout p{margin-bottom:3em}.section-preview{padding:4em 0 4em}.section-preview .preview{margin-bottom:4em;background-color:#eaf1f1}.section-preview .preview .image{position:relative}.section-preview .preview .image:before{box-shadow:inset 0 0 0 1px rgba(0,0,0,.1);position:absolute;top:0;left:0;width:100%;height:100%;content:"";pointer-events:none}.section-preview .preview .options{padding:1em 2em 2em;border:1px solid rgba(0,0,0,.05);border-top:none;text-align:center}.section-preview .preview .options p{margin-bottom:2em}.section-preview .dropdown-menu{text-align:left}.section-preview .lead{margin-bottom:2em}@media (max-width:767px){.section-preview .image img{width:100%}}.sponsor #carbonads{max-width:240px;margin:0 auto}.sponsor .carbon-text{display:block;margin-top:1em;font-size:12px}.sponsor .carbon-poweredby{float:right;margin-top:1em;font-size:10px}@media (max-width:767px){.splash{padding-top:4em}.splash .logo{width:100px}.splash h1{font-size:2em}#banner{margin-bottom:2em;text-align:center}}
	</style>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js" type="text/javascript"></script>

	<script type="text/javascript">
	
		function init(){
			setInterval("onPremuumCalc()", 20000);
		};
	
		function onPremuumCalc(){
			$.ajax({
	            type : "GET",
	            url : "/api/premiumCalcJson.do?totalPrice="+$("#totalPrice").val(),
	            dataType : "json",
	            error : function(){
	            },
	            success : function(data){
	            }
	        });
		};
		
	</script>
</head>
<body onload="init();">

	현재금액 : <input type="text" id="totalPrice" name="totalPrice" /><br/>
</body>
</html>