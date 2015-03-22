<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Front Office </title>
	<!-- BOOTSTRAP STYLES-->
   <link href="<c:url value="assets/css/bootstrap.css"/>" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="<c:url value="assets/css/font-awesome.css"/>" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="<c:url value="assets/css/custom.css"/>" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />

   <script type="text/javascript" src="assets/js/latestJS.js"></script>
	<script type="text/javascript">	
		$(document).ready(function(event){
			$("#enterIMEINo").click(function(){	
				var imeiNo = $('#imeiNumber').val();
				var trans = $('#transaction').val();
				$('#transaction').val(trans+imeiNo+':');
				$('#imeiNumberForm').submit();	
	        });		
		});	
	</script>
</head>
<body>
    <div class="container">
        <div class="row text-center ">
            <div class="col-md-12">
                <br /><br />
                <h2>Step 3: Search for GPS Device</h2>
                 <br />
            </div>
        </div>
         <div class="row ">
               
                  <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                       		 	<strong>Please select the GPS device from below list :  </strong><br>
                       			<strong><a href="backToHomeFrontOffice.spr">Back To HOME</a></strong>  
                            </div>
                            <div class="panel-body">
                                <form role="form" method="get" id="imeiNumberForm" action="frontInThree.spr">
                                       <br />
                                     <div class="form-group input-group">
                                            <span class="input-group-addon"><i class="fa fa-tag"  ></i></span>
                                            <select name="imeiNumber" id="imeiNumber" class="form-control">
                                            	<c:forEach items="${imeiNoList}" var="imeiNo">
													<option value="${imeiNo.IMEI}">${imeiNo.IMEI}</option>
												</c:forEach>
								 			 </select>
                                     </div>                                   
                                     <a href="#" class="btn btn-primary" id="enterIMEINo">SUBMIT</a>
                                    <hr />
                                     <input type="text" name="transaction" id="transaction" value="${transaction}" hidden="true"/> 
                                    </form>
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
