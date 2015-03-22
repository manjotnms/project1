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
			$("#enterDoNo").click(function(){
	     		var doNo =  $('#doNo').val();
	     		var transName = <%=request.getAttribute("transName")%>
	     		var transType = <%=request.getAttribute("transType")%>
	     		var otherValue = <%=request.getAttribute("otherValue")%>;
	     		var URL='';
	     		//alert("Transaction: "+transName);
	     		//alert("Do_No: "+doNo);
	     		if(transName=='1' && transType=='1'){
	     			URL = '${pageContext.request.contextPath}/doNoAvail.spr?doNo='+doNo;
	     		}
	     		else if(transName=='1' && transType=='2'){
	     			URL = '${pageContext.request.contextPath}/doNoAvailForIntern.spr?doNo='+doNo;
	     		}
	     	
	        	$.ajax({
	           		url :URL ,
	           		success : function(data) {
	            		if($.trim(data)=='false'){
	            			alert("Do Number Not Exist...........");
	            			$('#doNo').focus();
	            		}else{
	            			//alert("TransValue: "+transName+':'+transType+':'+doNo+':');
	            			$('#transaction').val(transName+':'+transType+':'+doNo+':');
            				$('#doNumber').submit();
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
    <div class="container">
        <div class="row text-center ">
            <div class="col-md-12">
                <br /><br />
                <h2>Step 1: Verify the D.O Number</h2>
                 <br />
            </div>
        </div>
         <div class="row ">
               
                  <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                        <strong>Enter the D.O Number here : </strong>
                        <strong><a href="backToHomeFrontOffice.spr">Back To HOME</a></strong>  
                            </div>
                            <div class="panel-body">
                                <form role="form" method="get" id="doNumber" action="frontInOne.spr">
                                       <br />
                                     <div class="form-group input-group">
                                            <span class="input-group-addon"><i class="fa fa-tag"  ></i></span>
                                            <input type="text" class="form-control" name="doNo" placeholder="Do No" id="doNo" />
                                            <input type="text" name="transaction" id="transaction" hidden="true"/>
                                     </div>                                   
                                     <a href="#" class="btn btn-primary" id="enterDoNo">SUBMIT</a>
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
