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
	<!--  <script type="text/javascript">	
		$(document).ready(function(event){
			//$("#enterEPCNo").bind('click', false);
			$("#enterEPCNo").hide();
			$("#searchEPCNo").click(function(){
				//$("#searchEPCNo").bind('click', false);
				$("#searchEPCNo").hide();
				$.ajax({
           			url : '${pageContext.request.contextPath}/EPCNumber.spr',
           			success : function(data) {
           				//alert("EPC Length: "+data.length +"Value "+data);
           				if(data.length==0){
           					//alert("EPCNo :"+data);
           					//$("#searchEPCNo").unbind('click', false);
           					$("#searchEPCNo").show();
           					alert("Sorry! EPC Number Not Found ...Click It Again For Search EPC Number");
           				}
           				else{
           					//alert("Success "+data);
           					$("#searchEPCNo").hide();
           					$("#enterEPCNo").show();
           					$("#epcNumber").val(data);
           				}
            		},
            		error: function(xhr, status, error) {
            		  var err = eval("(" + xhr.responseText + ")");
            		  alert(err.Message);
            		}
				});
        	});
			
			$("#enterEPCNo").click(function(){
				$('#epcNoForm').submit();	
	        });		
		});	
	</script>-->
	<script type="text/javascript">	
		$(document).ready(function(event){
  			$('#enterEPCNo').click(function(e){
     			var epcNo =  $('#epcNumber').val();
     			
     			//Check valid EPC number	
    			/* if(!validate(epcNo)){
    				event.preventDefault();
    				return false;
    			}  */			
     			//alert("EPC_NO: "+epcNo);
     			
        		$.ajax({
           			url : '${pageContext.request.contextPath}/epcAvailStatus.spr?epcNo='+epcNo,
           			async: 'false',
           			success : function(data) {
            			if(data=="false"){
            				alert("EPC Number Is Invalis Please Try Another EPC No...........");
            				return false;
            			}
            			else
            			{
            				var trans = $('#transaction').val();
            				$('#transaction').val(trans+epcNo+':');
            				$('#epcNoForm').submit();
            			}
            		},
            		error: function(xhr, status, error) {
            		  alert("Error Messages");
            		  console.log( 'something went wrong', status, error );

            		  var err = eval("(" + xhr.responseText + ")");
            		  alert(err.Message);
            		},
        		});

        	});
  		});
	</script>
	
</head>
<body>
    <div class="container">
        <div class="row text-center ">
            <div class="col-md-12">
                <br /><br />
                <h2>Step 2: Search for RFID Tag</h2>
                 <br />
            </div>
        </div>
         <div class="row ">
               
                  <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                        <strong>Click here to search :  </strong>
                        <strong><a href="backToHomeFrontOffice.spr">Back To HOME</a></strong>  
                            </div>
                            <div class="panel-body">
                                <form role="form" method="get" id="epcNoForm" action="frontInTwo.spr">
                                       <br />
                                     <div class="form-group input-group">
                                            <span class="input-group-addon"><i class="fa fa-tag"  ></i></span>
                                            <input type="text" class="form-control" placeholder="EPC Number" name="epcNumber" id="epcNumber" />
                                            <input type="text" name="transaction" id="transaction" value="${transaction}" hidden="true"/>
                                            <input type="text" name="frontOfOut" id="frontOfOut" value="${frontOfOut}" hidden="true">
                                     </div>
                                     <a href="#" class="btn btn-primary" id="searchEPCNo">SEARCH</a>                                   
                                     <a href="#" class="btn btn-primary" id="enterEPCNo">SUBMIT</a>
                                    <hr /> 
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
