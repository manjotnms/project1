<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
      <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>EPC Number Detail</title>
	<!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
     <!-- MORRIS CHART STYLES-->
   
        <!-- CUSTOM STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
     <!-- TABLE STYLES-->
    <link href="assets/js/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
    
<script type="text/javascript" src="assets/js/latestJS.js"></script>
<script type="text/javascript">
 	$(document).ready(function() {
     	$('a.change_status').click(function(event) {
                var url = $(this).attr('href');
                //alert("Remove User "+url);
                if (confirm("Do You Want To Delete This Record......?")) {
                	$(location).attr('href',url);
                }
                return false;
        });
     });
</script>
    
</head>
<body>
    <div id="wrapper">
        <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html">Binary admin</a> 
            </div>
  <div style="color: white;
padding: 15px 50px 5px 50px;
float: right;
font-size: 16px;"> Last access : 30 May 2014 &nbsp; <a href="logout.spr" class="btn btn-danger square-btn-adjust">Logout</a> </div>
        </nav>   
           <!-- /. NAV TOP  -->
                <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
				<li class="text-center">
                    <img src="assets/img/find_user.png" class="user-image img-responsive"/>
					</li>
	
                     <li>
                        <a href="createUser.spr"><i class="fa fa-laptop fa-3x"></i>Create New User</a>
                    </li>
                    <li>
                        <a href="viewUserList.spr"><i class="fa fa-desktop fa-3x"></i>View User List</a>
                    </li>
                    <li>
                        <a href="changePasswordRequest.spr"><i class="fa fa-bolt fa-3x"></i>Change Password</a>
                    </li>
                                   
                    <li>
                        <a href="#"><i class="fa fa-sitemap fa-3x"></i>Update EPC/GPS Records<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                        
                        <!-- Production Vehicle Detail -->
                        	<li>
                                <a href="enterPermaVehRequest.spr">Enter Production Vehicle Detail</a>
                            </li>
                            
                            <!-- Block EPC Number -->
                            <li>
                                <a href="blockEPCNoRequest.spr">Block EPC Number</a>
                            </li>
                            
                             <!-- EPP/GPS Detail -->
                            <li>
                                <a href="#">EPC/GPS Detil<span class="fa arrow"></span></a>
                                <ul class="nav nav-third-level">
                                    <li>
                                        <a href="getEPCDetail.spr">EPC's Detail</a>
                                    </li>
                                    <li>
                                        <a href="getGPSDetail.spr">GPS's Detail</a>
                                    </li>
                                 </ul>
                             </li>
                            
                             <!-- Enter Temporary EPC/GPS Detail -->
                            
                            <li>
                                <a href="#">Enter Temporary EPC/GPS<span class="fa arrow"></span></a>
                                <ul class="nav nav-third-level">
                                    <li>
                                        <a href="enterEPCNoRequest.spr">Enter EPC</a>
                                    </li>
                                    <li>
                                        <a href="enterGPSNoRequest.spr">Enter GPS</a>
                                    </li>
                                 </ul>
                             </li>
                             <!-- Lost EPC/GPS Detail -->
                             <li>
                               <a href="#">Lost EPC/GPS<span class="fa arrow"></span></a>
                               <ul class="nav nav-third-level">
                                   <li>
                                       <a href="lostEPCRequest.spr">Lost EPC</a>
                                   </li>
                                   <li>
                                       <a href="lostGPSRequest.spr">Lost GPS</a>
                                   </li>
                                </ul>
                             </li>
                        </ul>
                      </li>  
                </ul>
               
            </div>
            
        </nav>  
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                     <h2>User Detail</h2>         
                    </div>
                </div>
                 <!-- /. ROW  -->
                 <hr />
               
            <div class="row">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                             Advanced Tables
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>EPC_No</th>
                                            <th>Date_Of_Issue</th>
                                            <th>Available_Status</th>
                                            <th>Is_Use</th>
                                            <th>Edit Detail</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                     <c:forEach items="${epcList}" var="epc">
                                        <tr class="odd gradeX">
                                            <td>${epc.epcNo}</td>
                                            <td>${epc.dateOfIssue}</td>
                                            <td>${epc.avail}</td>
                                            <td>${epc.inUse}</td>
                                            <td class="center"><a href="deleteEPC.spr?epcNo=${epc.epcNo}" class="change_status">Delete</a></td>
                                       </tr>
                                     </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            
                        </div>
                    </div>
                    <!--End Advanced Tables -->
                </div>
            </div>
        </div>
    </div>
	<!-- /. PAGE INNER  -->
</div>
         <!-- /. PAGE WRAPPER  -->
     <!-- /. WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="assets/js/jquery-1.10.2.js"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="assets/js/bootstrap.min.js"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="assets/js/jquery.metisMenu.js"></script>
     <!-- DATA TABLE SCRIPTS -->
    <script src="assets/js/dataTables/jquery.dataTables.js"></script>
    <script src="assets/js/dataTables/dataTables.bootstrap.js"></script>
        <script>
            $(document).ready(function () {
                $('#dataTables-example').dataTable();
            });
    </script>
         <!-- CUSTOM SCRIPTS -->
    <script src="assets/js/custom.js"></script>
    
   
</body>
</html>
