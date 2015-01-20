<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en"> <!--<![endif]-->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Sale Detail</title>
  <link rel="stylesheet" href="css/style.css">
  <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
  
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script>
	$(document).ready(function(){
		$("div").toggle;
  		$("#buttonfirstDiv").click(function(){
    		$("#firstDiv").toggle();
  		});
  		$("#buttonDivSecond").click(function(){
    		$("#secondDiv").toggle();
  		});
  		$("#buttonDivThird").click(function(){
    		$("#thirdDiv").toggle();
  		});
  		$("#buttonDivFour").click(function(){
    		$("#fourthDiv").toggle();
  		});
  		$("#buttonDivFive").click(function(){
    		$("#fivethDiv").toggle();
  		});
  		
	});
  </script>
  
</head>
<body>
  <section class="container">
    <div class="login">
      <h1>Sale Detail</h1>
      <form:form method="post" action="saveSales" modelattribute="sales">
      <p id="buttonDivFirst">Show First Div</p>
      <div id="firstDiv">
        <p><input type="text" name="wayBrideId" placeholder="Way Bride ID"></p>
        <p><input type="text" name="purchaserName" placeholder="Purchaser Name"></p>
        <p><input type="text" name="destination" placeholder="Destination"></p>
		<p><input type="text" name="stateCode" placeholder="State Code"></p>
		<p><input type="text" name="grade" placeholder="Grade"></p>
		<p><input type="text" name="doNo" placeholder="Do No"></p>
		<p><input type="text" name="doDate" placeholder="Do Date"></p>
		<p><input type="text" name="applNo" placeholder="Appl No"></p>
		<p><input type="text" name="applDate" placeholder="Appl Date"></p>
		<p><input type="text" name="doQty" placeholder="Do Qty"></p>
	</div>
	<p id="buttonDivSecond">Show First Div</p>
	<div id="secondDiv">
		<p><input type="text" name="draftNo1" placeholder="Draft No1"></p>
        <p><input type="text" name="draftDt1" placeholder="Draft Dt1"></p>
        <p><input type="text" name="draftAmt1" placeholder="Draft Amt1"></p>
		<p><input type="text" name="bank1" placeholder="Bank1"></p>
		<p><input type="text" name="draftNo2" placeholder="Draft No2"></p>
        <p><input type="text" name="draftDt2" placeholder="Draft Dt2"></p>
        <p><input type="text" name="draftAmt2" placeholder="Draft Amt2"></p>
		<p><input type="text" name="bank2" placeholder="Bank2"></p>
		<p><input type="text" name="draftNo3" placeholder="Draft No3"></p>
        <p><input type="text" name="draftDt3" placeholder="Draft Dt3"></p>
        <p><input type="text" name="draftAmt3" placeholder="Draft Amt3"></p>
		<p><input type="text" name="bank3" placeholder="Bank3"></p>
	</div>
	<p id="buttonDivThird">Show First Div</p>
	<div id="thirdDiv">
		<p><input type="text" name="qtyBalance" placeholder="Qty Balance"></p>
        <p><input type="text" name="textType" placeholder="textType"></p>
        <p><input type="text" name="textPercent" placeholder="Text Percent"></p>
		<p><input type="text" name="custCd" placeholder="Cust Cd"></p>
		<p><input type="text" name="excRegNo" placeholder="Exc Reg No"></p>
		<p><input type="text" name="range" placeholder="Range"></p>
		<p><input type="text" name="division" placeholder="Division"></p>
		<p><input type="text" name="commission" placeholder="commission"></p>
		<p><input type="text" name="vattinNo" placeholder="Vattin No"></p>
		<p><input type="text" name="cstNo" placeholder="Cst No"></p>
	</div>
	<p id="buttonDivFour">Show First Div</p>
	<div id="fourthDiv">
		<p><input type="text" name="basicRate" placeholder="Basic Rate"></p>
        <p><input type="text" name="royalty" placeholder="Royalty"></p>
        <p><input type="text" name="sed" placeholder="Sed"></p>
		<p><input type="text" name="cleanEngyCess" placeholder="Clean Engy Cess"></p>
		<p><input type="text" name="weighMeBt" placeholder="Weigh Me Bt"></p>
		<p><input type="text" name="slc" placeholder="Slc"></p>
		<p><input type="text" name="wrc" placeholder="Wrc"></p>
		<p><input type="text" name="bazarFee" placeholder="Bazar Fee"></p>
		<p><input type="text" name="pan" placeholder="Pan"></p>
		<p><input type="text" name="centExcRate" placeholder="Cent Exc Rate"></p>
	</div>
	<p id="buttonDivFive">Show First Div</p>
	<div id="fivethDiv">
		<p><input type="text" name="eduCessRate" placeholder="Edu Cess Rate"></p>
        <p><input type="text" name="highEduRate" placeholder="High Edu Rate"></p>
        <p><input type="text" name="doStartDate" placeholder="Do Start Date"></p>
		<p><input type="text" name="doEndDate" placeholder="Do End Date"></p>
		<p><input type="text" name="roadCess" placeholder="Road Cess"></p>
		<p><input type="text" name="ambhCess" placeholder="Ambh Cess"></p>
	</div>
        <p class="submit"><input type="submit" name="enter Sale Detail" value="Submit"></p>
      </form:form>
    </div>

    <!-- <div class="login-help">
      <p>Forgot your password? <a href="index.html">Click here to reset it</a>.</p>
    </div> -->
  </section>

 <!--  <section class="about">
    <p class="about-links">
      <a href="http://www.cssflow.com/snippets/login-form" target="_parent">View Article</a>
      <a href="http://www.cssflow.com/snippets/login-form.zip" target="_parent">Download</a>
    </p>
    <p class="about-author">
      &copy; 2012&ndash;2013 <a href="http://thibaut.me" target="_blank">Thibaut Courouble</a> -
      <a href="http://www.cssflow.com/mit-license" target="_blank">MIT License</a><br>
      Original PSD by <a href="http://www.premiumpixels.com/freebies/clean-simple-login-form-psd/" target="_blank">Orman Clark</a>
  </section> -->
</body>
</html>
