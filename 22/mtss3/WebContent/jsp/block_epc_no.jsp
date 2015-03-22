<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Block EPC Number</title>
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
  		$('#blockEPC').click(function(e){
     		var epcNo =  $('#epcNo').val();
     		//alert("EPC_NO: "+epcNo);
        	$.ajax({
           		url : '${pageContext.request.contextPath}/epcAvail.spr?epcNo='+epcNo,
           		async: 'false',
           		success : function(data) {
            		if(data=="false"){
            			alert("EPC Number Not Exist...........");
            			return false;
            		}
            		else
            		{
            			$('#blockEPCForm').submit();
            		}
            	},
            	error: function(xhr, status, error) {
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
        <div class="row text-center  ">
            <div class="col-md-12">
                <br /><br />
                <h2> Block EPC Number</h2>
               
<!--                 <h5>( Register yourself to get access )</h5> -->
                 <br />
            </div>
        </div>
         <div class="row">
               
                <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                        <strong>Enter EPC Number</strong>  
                            </div>
                            <div class="panel-body">
                                <form:form action="enterEPCNo.spr" method="post" modelattribute="epc" id="blockEPCForm"><br/>
										<div class="form-group input-group">
                                            <span class="input-group-addon"><i class="fa fa-tag"  ></i></span>
                                            <input type="text" class="form-control" name="epcNo" placeholder="EPC No." id="epcNo"/>
                                        </div>
                                    <a href="#" class="btn btn-success " id="blockEPC">Enter</a>
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
 