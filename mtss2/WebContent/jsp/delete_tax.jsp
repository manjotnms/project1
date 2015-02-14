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
  <title>Login Form</title>
  <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
  <!-- <link rel="stylesheet" href="/css/style.css" type="text/css"> -->
  <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<body>
  <section class="container">
    <div class="login">
      <h1>Login to Web App</h1>
      <form:form action="deleteTax.spr" method="get">
		<p><select name="taxName">
			<c:forEach items="${taxesList}" var="taxName">
				<option value="${taxName}">${taxName}</option>
			</c:forEach>
	     </select></p>
        <p class="submit"><input type="submit" name="commit" value="Delete Tax"></p>
      </form:form>
    </div>
  </section>
</body>
</html>
