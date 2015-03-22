<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Enter GPS Detail</title>
	<!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
   
	<script type="text/javascript" src="assets/js/latestJS.js"></script>
	<script type="text/javascript">	
	$(document).ready(function(){
  		$('#enterGPS').click(function(e){
     		var gpsNo =  $('#gpsNo').val();
     		
     		/* if(!validate(gpsNo)){
     			return false;
     		} */
     		
     		//alert("GPS_NO: "+gpsNo);
        	$.ajax({
           		url : '${pageContext.request.contextPath}/gpsAvail.spr?gpsNo='+gpsNo,
           		async: 'false',
           		success : function(data) {
            		if(data=="true"){
            			alert("IMEI Number Already Exist...........");
            			return false;
            		}
            		else
            		{
            			$('#gpsForm').submit();
            		}
            	},
            	error: function(xhr, status, error) {
            		  var err = eval("(" + xhr.responseText + ")");
            		  alert(err.Message);
            	},
        	});
        });
  	});
	
	function validate(gpsNo) {
		var filter = new RegExp(/^[0-9]{20,20}$/);
		
		if ($.trim(gpsNo).length == 0 || gpsNo=="") {
			alert('IMEI No Is Mandatory');
			return false;
		}
			
		if(!filter.test(gpsNo)) {
			alert('IMEI No Must Be A Number And Must Be Lenght 20 Digit..');
			return false;
		}
	}
	
 </script>
</head>
<body>
    <div class="container">
        <div class="row text-center  ">
            <div class="col-md-12">
                <br /><br />
                <h2> Binary Admin : Register</h2>
               
                <h5>( Register yourself to get access )</h5>
                 <br />
            </div>
        </div>
         <div class="row">
               
                <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                        <strong>  New User ? Register Yourself </strong>  
                            </div>
                            <div class="panel-body">
                                <form:form action="enterGPSNo.spr" method="post" modelattribute="gps" id="gpsForm">
										<div class="form-group input-group">
                                            <span class="input-group-addon"><i class="fa fa-tag"  ></i></span>
                                            <input type="text" class="form-control" name="IMEI" placeholder="IMEI No." id="gpsNo"/>
                                        </div>
                                    <a href="#" class="btn btn-success " name="commit" id="enterGPS">Enter</a>
                                    <hr />
                                </form:form>
                            </div>
                        </div>
                    </div>
        </div>
    </div>


     <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="assets/js/jquery-1.10.2.js"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="assets/js/bootstrap.min.js"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="assets/js/jquery.metisMenu.js"></script>
      <!-- CUSTOM SCRIPTS -->
    <script src="assets/js/custom.js"></script>
   
</body>
</html>
