<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
 	$(document).ready(function() {
     	$('#newUser').click(function(event) {
     		    var password = $("#password").val();
     		    var confirmPassword = $("#confirmPassword").val();
               	if(password!=confirmPassword){
               		alert("Password And Confirm Password Not Match......");
                	return false;
               	}
               	else
               		return true;
            });
        });
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<b>${userMessage}</b>
	<form:form action="addNewUser.spr" method="post" modelattribute="user">
		<p>Name:<input type="text" name="name" placeholder="Name"></p>
        <p>Username:<input type="text" name="userName" placeholder="Username or Email"></p>
        <p>Password:<input type="password" name="password" id="password" placeholder="Password"></p>
        <p>Confirm Password<input type="password" name="confirmPassword" id="confirmPassword" placeholder="Confirm Password"></p>
		<p>User Type:<select name="userType">
									<option value="operator">Operator</option>
									<option value="sales">Sales</option>
									<option value="gm">G.M</option>
								  </select></p>
        <p class="submit"><input type="submit" name="commit" value="Create User" id="newUser"></p>
      </form:form>
</body>
</html>