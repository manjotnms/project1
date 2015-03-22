<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script src="js/jquery-2.0.2.min.js"></script>
	<script type="text/javascript" src="js/divRotationOutOperation.js"></script>
	<style>
		#s2, #s3, #s4 {
			display:none;
		}
		#errorsepcNumberForm, #errorschalanInvoiceNumberForm {
			color:red;
		}
		#s3{
			color: green;
		}
	</style>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Out Module</title>
</head>
<body>
	<h1> Welcome to OUT Module </h1><h3><a href="home.jsp">Back To HOME</a></h3>
	
	<div id="s1">
		<h3>Step 1: Search for RFID Tag</h3>
		<p>Click here to search : </p> 
		<form action="OutOperations" id="epcNumberForm" onsubmit="validateepcNumberForm()" >
			<table>
				<tr><td><input type="hidden" name="action" id="actionepcNumberForm" value="epcNumberForm" /></td></tr>
				<tr><td><input type="text" name="epcNumber" id="epcNumber" /></td></tr>
				<tr>
					<td>
						<a onclick="login('s2','epcNumberForm')">
							<input type="button" value="SUBMIT"  onclick="validateepcNumberForm()">
						</a>
						<input type="button" value="CANCEL">
					</td>
				</tr>
			</table>
		</form>
		<div id="errorsepcNumberForm">
				
		</div>
	</div>
	
	<div id="s2">
		<h3>Step 2: (Chalan) Please fill the chalan/invoice no & click Ok: </h3>
		<form action="OutOperations" id="chalanInvoiceNumberForm">
			<table>
				<tr><td><input type="hidden" name="action" id="actionchalanInvoiceNumberForm" value="chalanInvoiceNumberForm" /></td></tr>
				<tr><td>Chalan Number :</td><td><input type="text" name="chalanNumber" id="chalanNumber" /></td></tr>
				<tr><td>Invoice Number :</td><td><input type="text" name="invoiceNumber" id="invoiceNumber" /></td></tr>
				<tr>
					<td>
						<a onclick="login('s3','chalanInvoiceNumberForm')">
							<input type="button" value="SUBMIT" onclick="validatechalanInvoiceNumberForm()">
						</a>
						<input type="button" value="CANCEL">
					</td>
				</tr>
			</table>
		</form>
		<div id="errorschalanInvoiceNumberForm">
				
		</div>
	</div>
	
	<div id="s3">
		<h3>Operation Successfully performed. </h3>
		<p>Step's Finished: Print the chalan and Invoice.</p>
		<a href="javascript:void(0)" > Click here to download Invoice </a> </br>
		<a href="javascript:void(0)" > Click here to download Chalan </a>
	</div>

</body>
</html>