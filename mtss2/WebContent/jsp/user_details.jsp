<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
 	$(document).ready(function() {
     	$('a.change_status').click(function(event) {
                var url = $(this).attr('href');
                alert("Remove User "+url);
                if (confirm("Do You Want To Delete This Record......?")) {
                	$(location).attr('href',url);
                }
                return false;
            });
        });
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body style="text-align: center;">
	<table border=1>
		<tr>
			<td>User_Name</td>
			<td>User_Type</td>
			<td>Edit_User</td>
			<td>Delete_User</td>
		</tr>
		<c:forEach items="${userList}" var="user">
				<tr>
					<td>${user.userName}</td>
					<td>${user.userType}</td>
					<td><a href="#">Edit</a></td>
					<td><a href="removeUser.spr?userName=${user.userName}" class="change_status">Delete</a></td>
				</tr>
			</c:forEach>
	</table>
</body>
</html>