<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Step One</title>
<script src="js/jquery-2.0.2.min.js"></script>
<script type="text/javascript">
	function showTypeChooser() {
		displayOut();
		//alert('called'+ document.getElementById("MaterialChooser").value);
		if ( document.getElementById("MaterialChooser").value == 1 ) {
			//alert('called > 1');
			document.getElementById("div_Other").style.display = 'none';
			document.getElementById("div_TypeChooser").style.display = 'block';
		} else if(document.getElementById("MaterialChooser").value == 2) {
			//alert('called .. ');
			document.getElementById("div_TypeChooser").style.display = 'none';
			document.getElementById("div_Other").style.display = 'block';
		} else {
			document.getElementById("div_TypeChooser").style.display = 'none';
			document.getElementById("div_Other").style.display = 'none';
		}
		
	}
	function valueOtherDisplay(){

		// If 
		if(document.getElementById("otherValue").value == "")
			document.getElementById("displayOutOther").innerHTML = "<span style='color:red;'>-- Please Fill Other Value -- </span>";
		else
			document.getElementById("displayOutOther").innerHTML = "Product: "+document.getElementById("otherValue").value;
			
	}
	function displayOut(){
		var str = "<span style='color:green;'>Material: ";
		var end = "</span>";
		if(document.getElementById("MaterialChooser").value == 1) {
			document.getElementById("displayOut").innerHTML = str+ "COAL" + end;
		} else if(document.getElementById("MaterialChooser").value == 2) {
			document.getElementById("displayOut").innerHTML = str+ "Other Goods" + end;
		} else {
			document.getElementById("displayOut").innerHTML = "<span style='color:red;'>-- Please Select Material -- </span>";
		}
	}
	
	function displayOutType() {
		var str = "<span style='color:green;'>Type: ";
		var end = "</span>";
		//alert(document.getElementById("TypeChooser").value);
		if(document.getElementById("TypeChooserC").checked) {
			//alert('C');
			document.getElementById("displayOutType").innerHTML = str+ "Customer" + end;
		} else if(document.getElementById("TypeChooserI").checked) {
			//alert('I');
			document.getElementById("displayOutType").innerHTML = str+ "Internal" + end;
		} else {
			document.getElementById("displayOutType").innerHTML = "<span style='color:red;'>-- Please Select Type -- </span>";
		} 
	}
</script>
<style type="text/css">
#div_MaterialChooser, #displayOut {
	float: left;
	width: 50%;
}
#div_radioTypeChooser, #displayOutType {
	float: left;
	width: 50%;
}
#displayOutOther, #div_OtherName {
	float: left;
	width: 50%;	
}
#div_TypeChooser{
	width: 100%;
}
#mainContainer{
	width: 1000px;
	margin: auto;
}
.customButton{
	width: 150px;
	height: 44px;
	font-size: 25px;
}
#div_submitBtn{
	margin-left: 350px;
	margin-top: 20px;
}
</style>
</head>
<body>
	<div id="mainContainer" >
		<h1> Welcome to IN Module</h1><h3><a href="backToHomeFrontOffice.spr">Back To HOME</a></h3>
		<hr>
		<form action="frontIn.spr">
			<div id="div_MaterialChooser">
				<label>Select Material:</label>
				<select name="MaterialChooser" id="MaterialChooser" onchange="showTypeChooser()">
					<option value="0">- Please select -</option>
					<option value="1">COAL</option>
					<option value="2">Other Goods</option>
				</select>
			</div>
			<div id="displayOut" style="font-size: 30px;">
				<span style='color:red;'>-- Please Select Material -- </span>
			</div>
			<hr>
			<div id="div_TypeChooser" style="display: none;">
				<div id="div_radioTypeChooser">
					<table>
					    <tr><td><label>Select Type of Transaction:</label></td>
							<!-- <select id="TypeChooser">
								<option value="0">- Please select -</option>
								<option value="1">Customer</option>
								<option value="2">Internal</option>
						    </select> -->
							<td><input type="radio" id="TypeChooserC" name="TypeChooser" value="1" onclick="displayOutType()" > <label>Customer</label></td>
							<td><input type="radio" id="TypeChooserI" name="TypeChooser" value="2" onclick="displayOutType()" > <label>Internal</label></td>
						</tr>
					</table>
				</div>
				<div id="displayOutType" style="font-size: 30px;">
					<span style='color:red;'>-- Please Select Type -- </span>
				</div>
				<hr>
			</div>
			<div id="div_Other" style="display: none;">
				<div id="div_OtherName">
					<table>
						<tr> 
							<td><label>Enter Other Product's Name:</label></td> 
							<td><input type="text" name="otherValue" id="otherValue"  value="" onkeyup="valueOtherDisplay()"></td>
						</tr>
					</table>
				</div>
				<div id="displayOutOther" style="font-size: 30px; color: green;">
					<span style='color:red;'>-- Please Select Type -- </span>
				</div>
				<hr>
			</div>
			<div id="div_submitBtn">
				<input type="submit" value="Submit" class="customButton">
			</div>
		</form>
	</div>
</body>
</html>