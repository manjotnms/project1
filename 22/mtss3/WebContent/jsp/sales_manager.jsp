<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en"> <!--<![endif]-->
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Sale Detail</title>
  <link rel="stylesheet" href="css/style.css">
  <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
  
  
  <!--Date Picker-->
  <!------------ Including  jQuery Date UI with CSS -------------->

  
  <script type="text/javascript" src="assets/js/latestJS.js"></script>
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
  <script>
  $(document).ready(function(){	
  	$('#myForm').submit(function() {
	    // get all the inputs into an array.
	    alert("FormLength:"+document.getElementById("myForm").length);
	    var $inputs = $('#myForm :input');

	    // not sure if you wanted this, but I thought I'd add it.
	    // get an associative array of just the values.
	    var values = {};
	    var taxes = '';
	    $inputs.each(function() {
	        taxes = taxes+this.name+':'+$(this).val()+';';
	    });
	    
	    var start = taxes.indexOf("start");
	    var end =   taxes.indexOf("end");
	    taxes = taxes.substring(start+7,end);
	    /* alert("TAX1"+taxes); */
	    document.getElementById('taxesDetail').value = taxes;
	    /* alert("value poped"); */
	});
  });
  </script>
  <script type="text/javascript">
  	$(document).ready(function(){
  		$("#addTax").on('click', function() {
  		   var taxName = prompt("Enter New Tax Name:");
  		   /* alert("Tax Name: "+taxName); */
  		   window.location = "addNewTaxName.spr?taxName="+taxName;
  		});
  		
  		$("#doNo").blur(function(){
     		var doNo =  $('#doNo').val();
     		alert("Do_No: "+doNo);
        	$.ajax({
           		url : '${pageContext.request.contextPath}/doNoAvail.spr?doNo='+doNo,
           		success : function(data) {
            		if(data=="true"){
            			alert("Do Number Already Exist...........");
            			$('#doNo').focus();
            		}
            	},
            	error: function(xhr, status, error) {
            		  var err = eval("(" + xhr.responseText + ")");
            		  alert(err.Message);
            	}
        	});
        });
  	});
  </script>
  
</head>
<body>
  <section class="container">
    <div class="login">
      <h1>Sale Detail:</h1>
      <form:form action="saveSales.spr" method="post" modelattribute="sales" id="myForm">
     	<a href="#" id="a1" >Gernal Detail</a><br/>
     	 <div id="d1">
     	 	<p>Do_No<input type="text" name="doNo" id="doNo" placeholder="Do No"></p>
	        <p>Way Bridge ID:<input type="text" name="wayBridgeId" placeholder="Way Bridge ID"></p>
	        <p>Purchaser Name:<input type="text" name="purchaserName" placeholder="Purchaser Name"></p>
	        <p>Destination:<input type="text" name="destination" placeholder="Destination"></p>
			<p>State Code:<input type="text" name="stateCode" placeholder="State Code"></p>
			<p>Grade:<input type="text" name="grade" placeholder="Grade"></p>
			<p>Do_Date<input type="text" name="doDate" placeholder="Do Date"></p>
			<p>Appl_No<input type="text" name="applNo" placeholder="Appl No"></p>
			<p>Appl_Date<input type="text" name="applDate" placeholder="Appl Date"></p>
			<p>DO_Qty<input type="text" name="doQty" placeholder="Do Qty"></p>
		</div>
		
		<a href="#" id="a2">Banking Detail</a><br/>
		<div id="d2">
			<p>Draft No1<input type="text" name="draftNo1" placeholder="Draft No1"></p>
	        <p>Draft Dt1<input type="text" name="draftDt1" placeholder="Draft Dt1"></p>
	        <p>Draft Amt1<input type="text" name="draftAmt1" placeholder="Draft Amt1"></p>
			<p>Bank1<input type="text" name="bank1" placeholder="Bank1"></p>
			<p>Draft No2<input type="text" name="draftNo2" placeholder="Draft No2"></p>
	        <p>Draft Dt2<input type="text" name="draftDt2" placeholder="Draft Dt2"></p>
	        <p>Draft Amt2<input type="text" name="draftAmt2" placeholder="Draft Amt2"></p>
			<p>Bank2<input type="text" name="bank2" placeholder="Bank2"></p>
			<p>Draft No3<input type="text" name="draftNo3" placeholder="Draft No3"></p>
	        <p>Draft Dt3<input type="text" name="draftDt3" placeholder="Draft Dt3"></p>
	        <p>Draft Amt3<input type="text" name="draftAmt3" placeholder="Draft Amt3"></p>
			<p>Bank3<input type="text" name="bank3" placeholder="Bank3"></p>
		</div>
		<a href="#" id="a3">Show 3 Div</a><br/>
		<div id="d3">
			<p>Qty Balance<input type="text" name="qtyBalance" placeholder="Qty Balance"></p>
	        <p>Text Type<input type="text" name="textType" placeholder="textType"></p>
			<p>Cust Cd<input type="text" name="custCd" placeholder="Cust Cd"></p>
			<p>Exc Reg No<input type="text" name="excRegNo" placeholder="Exc Reg No"></p>
			<p>Range<input type="text" name="range" placeholder="Range"></p>
			<p>Division<input type="text" name="division" placeholder="Division"></p>
			<p>commission<input type="text" name="commission" placeholder="commission"></p>
			<p>Vattin No<input type="text" name="vattinNo" placeholder="Vattin No"></p>
			<p>Cst No<input type="text" name="cstNo" placeholder="Cst No"></p> 
		</div>
		
		<a href="#" id="a4">Show 4 Div</a><br/>
		<div id="d4">
			<p>Basic Rate<input type="text" name="basicRate" placeholder="Basic Rate"></p>
			<p>Pan<input type="text" name="pan" placeholder="Pan"></p>
			<p>Do Start Date<input type="text" name="doStartDate" placeholder="Do Start Date" id="doStartDate"></p>
			<p>Do End Date<input type="text" name="doEndDate" placeholder="Do End Date"></p>
		</div> 
		
		<a href="#" id="a5">Taxes Detail</a><br/>
		<div id="d5">
			<p><input type="hidden" name="start"/></p>
			<p>Text Percent<input type="text" name="taxPercent" placeholder="Text Percent"></p>
			<p>Royalty<input type="text" name="royalty" placeholder="Royalty"></p>
	        <p>Sed<input type="text" name="sed" placeholder="Sed"></p>
			<p>Clean Engy Cess<input type="text" name="cleanEngyCess" placeholder="Clean Engy Cess"></p>
			<p>Weigh Me Bt<input type="text" name="weighMeBt" placeholder="Weigh Me Bt"></p>
			<p>SLC<input type="text" name="slc" placeholder="Slc"></p>
			<p>WRC<input type="text" name="wrc" placeholder="Wrc"></p>
			<p>Bazar Fee<input type="text" name="bazarFee" placeholder="Bazar Fee"></p>
			<p>Cent Exc Rate<input type="text" name="centExcRate" placeholder="Cent Exc Rate"></p>
			<p>Edu Cess Rate<input type="text" name="eduCessRate" placeholder="Edu Cess Rate"></p>
	        <p>High Edu Rate<input type="text" name="highEduRate" placeholder="High Edu Rate"></p>
			<p>Road Cess<input type="text" name="roadCess" placeholder="Road Cess"></p>
			<p>Ambh Cess<input type="text" name="ambhCess" placeholder="Ambh Cess"></p>
			<%-- <c:forEach items="${taxesList}" var="taxName">
				<p>${taxName}<input type="text" name="${taxName}" placeholder="${taxName}"/></p>
			</c:forEach> --%>
			<p><input type="hidden" name="end"/></p>
			<p><input type="hidden" name="taxesDetail" id="taxesDetail"></p>
		</div>
        <p class="submit"><input type="submit" name="enter Sale Detail" value="Submit"></p>
        <p><a href="#" id="addTax">ADD NEW TAX</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="deleteTaxRequest.spr" id="deleteTax">DELETE TAX</a></p>
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
