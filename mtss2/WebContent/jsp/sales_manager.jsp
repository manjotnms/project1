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
		for ( var i = 1; i <=5; i++ ) {
			$("#d"+i).toggle();
		}
		
  		$("#a1").click(function(){
    		$("#d1").toggle();
  		});
  		$("#a2").click(function(){
    		$("#d2").toggle();
  		});
  		$("#a3").click(function(){
    		$("#d3").toggle();
  		});
  		$("#a4").click(function(){
    		$("#d4").toggle();
  		});
  		$("#a5").click(function(){
    		$("#d5").toggle();
  		});
  		
	});
  </script>
  
</head>
<body>
  <section class="container">
    <div class="login">
      <h1>Sale Detail</h1>
      <form:form action="saveSales.spr" method="post" modelattribute="sales">
     	 <a href="#" id="a1" >Show 1 Div</a><br/>
     	 <div id="d1">
	        <p>Way Bridge ID:<input type="text" name="wayBridgeId" placeholder="Way Bridge ID"></p>
	        <p>Purchaser Name:<input type="text" name="purchaserName" placeholder="Purchaser Name"></p>
	        <p>Destination:<input type="text" name="destination" placeholder="Destination"></p>
			<p>State Code:<input type="text" name="stateCode" placeholder="State Code"></p>
			<p>Grade:<input type="text" name="grade" placeholder="Grade"></p>
			<p>Do_No<input type="text" name="doNo" placeholder="Do No"></p>
			<p>Do_Date<input type="text" name="doDate" placeholder="Do Date"></p>
			<p>Appl_No<input type="text" name="applNo" placeholder="Appl No"></p>
			<p>Appl_Date<input type="text" name="applDate" placeholder="Appl Date"></p>
			<p>DO_Qty<input type="text" name="doQty" placeholder="Do Qty"></p>
		</div>
		
		<a href="#" id="a2">Show 2 Div</a><br/>
		<div id="d2">
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
		<a href="#" id="a3">Show 3 Div</a><br/>
		<div id="d3">
			<p><input type="text" name="qtyBalance" placeholder="Qty Balance"></p>
	        <p><input type="text" name="textType" placeholder="textType"></p>
			<p><input type="text" name="custCd" placeholder="Cust Cd"></p>
			<p><input type="text" name="excRegNo" placeholder="Exc Reg No"></p>
			<p><input type="text" name="range" placeholder="Range"></p>
			<p><input type="text" name="division" placeholder="Division"></p>
			<p><input type="text" name="commission" placeholder="commission"></p>
			<p><input type="text" name="vattinNo" placeholder="Vattin No"></p>
			<p><input type="text" name="cstNo" placeholder="Cst No"></p> 
		</div>
		
		<a href="#" id="a4">Show 4 Div</a><br/>
		<div id="d4">
			<p><input type="text" name="basicRate" placeholder="Basic Rate"></p>
			<p><input type="text" name="pan" placeholder="Pan"></p>
			<p><input type="text" name="doStartDate" placeholder="Do Start Date"></p>
			<p><input type="text" name="doEndDate" placeholder="Do End Date"></p>
		</div>
		
		<a href="#" id="a5">Show 5 Div</a><br/>
		<div id="d5">
			<p><input type="text" name="taxPercent" placeholder="Text Percent"></p>
			<p><input type="text" name="royalty" placeholder="Royalty"></p>
	        <p><input type="text" name="sed" placeholder="Sed"></p>
			<p><input type="text" name="cleanEngyCess" placeholder="Clean Engy Cess"></p>
			<p><input type="text" name="weighMeBt" placeholder="Weigh Me Bt"></p>
			<p><input type="text" name="slc" placeholder="Slc"></p>
			<p><input type="text" name="wrc" placeholder="Wrc"></p>
			<p><input type="text" name="bazarFee" placeholder="Bazar Fee"></p>
			<p><input type="text" name="centExcRate" placeholder="Cent Exc Rate"></p>
			<p><input type="text" name="eduCessRate" placeholder="Edu Cess Rate"></p>
	        <p><input type="text" name="highEduRate" placeholder="High Edu Rate"></p>
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
