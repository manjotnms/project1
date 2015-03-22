var errorIn = 0;
var errorInValue = '';
var submitStatusInOp = 0;
function login(ok, formName)
{   
	if(errorIn < 1 ) {
				//$("#"+formName).submit();
				// Submit this form.
	 			var postData = $("#"+formName).serializeArray();
			    var formURL = $("#"+formName).attr("action");
			    $.ajax(
			    {
			        url : formURL,
			        type: "POST",
			        data : postData,
			        success:function(data, textStatus, jqXHR) 
			        {
			            //alert(data);
			        	//data: return data from server
			        	if(data=="Ok") {
				            document.getElementById("s1").style.display='none';
				        	document.getElementById("s2").style.display='none';
				        	document.getElementById("s3").style.display='none';
				        	document.getElementById("s4").style.display='none';
				        	document.getElementById("s5").style.display='none';
				        	
				        	document.getElementById(ok).style.display='block';
			        	} else {
			        		document.getElementById("errors"+formName).innerHTML = "There is some problem. NOT Submitted.";
			        	}
			        },
			        error: function(jqXHR, textStatus, errorThrown) 
			        {
			        	alert("errorThrown:"+errorThrown);
			        	alert("textStatus:"+textStatus);
			        	alert("jqXHR:"+errorThrown);
			            document.getElementById("errors"+formName).innerHTML = errorThrown;
			        	//if fails      
			        }
			    });
			    //e.preventDefault(); //STOP default action
			    //e.unbind(); //unbind. to stop multiple form submit.
	} else {
		document.getElementById("errors"+formName).innerHTML = errorInValue;
		errorIn = 0;
		errorInValue = '';
	}
}

// 1st :doNumberForm

function validatedoNumberForm() {
	//alert("Valid epcNumberForm : "+$('#epcNumber').val());
	//alert($('#actionepcNumberForm'));
	//alert($('#actionepcNumberForm').val());
	if($('#actiondoNumberForm').val()==undefined || $('#actiondoNumberForm').val() =="" ) {
		errorIn = 1;
		errorInValue = 'There is some problem. NOT Submitted.';
		return;
	} else if ($('#doNumber').val()==undefined || $('#doNumber').val() ==""){
		errorIn = 1;
		errorInValue = 'D.O Number is not valid. Please enter again !';
		return;
	} else {
		//alert('All ok.');
	}
}

// 2nd
function validateepcNumberForm() {
	//alert("Valid epcNumberForm : "+$('#epcNumber').val());
	//alert($('#actionepcNumberForm'));
	//alert($('#actionepcNumberForm').val());
	if($('#actionepcNumberForm').val()==undefined || $('#actionepcNumberForm').val() =="" ) {
		errorIn = 1;
		errorInValue = 'There is some problem. NOT Submitted.';
		return;
	} else if ($('#epcNumber').val()==undefined || $('#epcNumber').val() ==""){
		errorIn = 1;
		errorInValue = 'Epc Number is not valid. Please search again !';
		return;
	} else {
		//alert('All ok.');
	}
}

// 3rd
function validateimeiNumberForm() {
	//alert("Valid epcNumberForm : "+$('#epcNumber').val());
	//alert($('#actionepcNumberForm'));
	//alert($('#actionepcNumberForm').val());
	if($('#actionimeiNumberForm').val()==undefined || $('#actionimeiNumberForm').val() =="" ) {
		errorIn = 1;
		errorInValue = 'There is some problem. NOT Submitted.';
		return;
	} else if ($('#imeiNumber').val()==undefined || $('#imeiNumber').val() ==""){
		errorIn = 1;
		errorInValue = 'IMEI Number is not valid. Please select again !';
		return;
	} else {
		//alert('All ok.');
	}
}

// 4th
function validateotherDetailsForm() {
	//alert("Valid epcNumberForm : "+$('#epcNumber').val());
	//alert($('#actionepcNumberForm'));
	//alert($('#actionepcNumberForm').val());
	if($('#actionotherDetailsForm').val()==undefined || $('#actionotherDetailsForm').val() =="" ) {
		errorIn = 1;
		errorInValue = 'There is some problem. NOT Submitted.';
		return;
	} else if ($('#vechNumber').val()==undefined || $('#vechNumber').val() ==""){
		errorIn = 1;
		errorInValue = 'Vehichle Number is not valid. Please enter again !';
		return;
	} else if ($('#rlw').val()==undefined || $('#rlw').val() ==""){
		errorIn = 1;
		errorInValue = 'RLW is not valid. Please enter again !';
		return;
	} else {
		//alert('All ok.');
	}
}

