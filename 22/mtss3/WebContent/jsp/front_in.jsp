<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>In Module</title>
	<script src="js/jquery-2.0.2.min.js"></script>
	<script type="text/javascript" src="js/divRotationInOperation.js"></script>
	<style type="text/css">
		#s2, #s3, #s4, #s5 {
			display:none;
		}
		#errorsdoNumberForm, #errorsepcNumberForm, #errorsimeiNumberForm, #errorsotherDetailsForm {
			color:red;
		}
		#s5{
			color: green;
		}
	</style>
</head>
<body>
	<h1> Welcome to IN Module</h1><h3><a href="backToHomeFrontOffice.spr">Back To HOME</a></h3>
	<div id="s1">
		<h3>Step 1: Verify the D.O Number</h3>
		<p>Enter the D.O Number here : </p> 
		<form action="InOperations" id="doNumberForm" >
			<table>
				<tr><td><input type="hidden" name="action" id="actiondoNumberForm" value="doNumberForm" /></td></tr>
				<tr><td><input type="text" name="doNumber" id="doNumber" /></td></tr>
				<tr>
					<td>
						<a onclick="login('s2','doNumberForm')">
							<input type="button" value="SUBMIT" onclick="validatedoNumberForm()" >
						</a>
						<input type="button" onclick="pageRefresh()" value="CANCEL">
					</td>
				</tr>
			</table>
			<div id="errorsdoNumberForm">
				
			</div>
		</form>
	</div>
	
	<div id="s2">
		<h3>Step 2: Search for RFID Tag</h3>
		<p>Click here to search : </p>
		<form action="InOperations" id="epcNumberForm" >
			<table>
				<tr><td><input type="hidden" name="action" id="actionepcNumberForm" value="epcNumberForm" /></td></tr>
				<tr>
					<td><input type="text" name="epcNumber" id="epcNumber" /></td>
					<td>
						<a onclick="login('s3','epcNumberForm')">
							<input type="button" value="SUBMIT"  onclick="validateepcNumberForm()">
						</a>
						<input type="button" onclick="pageRefresh()" value="CANCEL">
					</td>
				</tr>
			</table>
		</form>
		<div id="errorsepcNumberForm">
				
		</div>
	</div>
	
	<div id="s3">
		<h3>Step 3: Search for GPS Device</h3>
		<p>Please select the GPS device from below list : </p>
		<form action="InOperations" id="imeiNumberForm" >
		<table>
				<tr><td><input type="hidden" name="action" id="actionimeiNumberForm" value="imeiNumberForm" /></td></tr>
				<tr>
					<td>
						<select name="imeiNumber" id="imeiNumber" style="width:150px;" >
							<option value="">--Please select--</option>
							<option value="1">IMEI 1</option>
							<option value="2">IMEI 2</option>
							<option value="3">IMEI 3</option>
							<option value="4">IMEI 4</option>
						</select>
					</td>
					<td>
						<a onclick="login('s4','imeiNumberForm')">
							<input type="button" value="SUBMIT"  onclick="validateimeiNumberForm()">
						</a>
						<input type="button" onclick="pageRefresh()" value="CANCEL">
					</td>
				</tr>
		</table>
		</form>
		<div id="errorsimeiNumberForm">
				
		</div>
	</div>
	
	<div id="s4">
		<h3>Step 4: Please enter below details:</h3>
		<form action="InOperations" id="otherDetailsForm" >
			<table>
				<tr><td><input type="hidden" name="action" id="actionotherDetailsForm" value="otherDetailsForm" /></td></tr>
				<tr><td>Vehichle No :</td><td><input type="text" name="vechNumber" id="vechNumber" /></td></tr>
				<tr><td>RLW :</td><td><input type="text" name="rlw" id="rlw" /></td></tr>		 
				<tr>
					<td>
						<a onclick="login('s5','otherDetailsForm')">
							<input type="button" value="SUBMIT"  onclick="validateotherDetailsForm()">
						</a>
						<input type="button" onclick="pageRefresh()" value="CANCEL">
					</td>
				</tr>
			</table>
		</form>
		<div id="errorsotherDetailsForm">
				
		</div>
	</div>
	
	<div id="s5">
		<h3>Step's Finished : Successfully Entered.</h3>
		<h4>Dispatch the Rfid card and GPS device to driver.</h4>
	</div>

</body>
</html>