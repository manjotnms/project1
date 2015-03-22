var error = 0;
var errorValue = '';
var submitStatusOutOp = 0;
function login(ok, formName)
{   
	if(error < 1 ) {
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
			           // alert(data);
			        	//data: return data from server
			        	if(data=="Ok Got it"){
				            document.getElementById("s1").style.display='none';
				        	document.getElementById("s2").style.display='none';
				        	document.getElementById("s3").style.display='none';
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
		document.getElementById("errors"+formName).innerHTML = errorValue;
		error = 0;
	}
}


function validateepcNumberForm() {
	//alert("Valid epcNumberForm : "+$('#epcNumber').val());
	//alert($('#actionepcNumberForm'));
	//alert($('#actionepcNumberForm').val());
	if($('#actionepcNumberForm').val()==undefined || $('#actionepcNumberForm').val() =="" ) {
		error = 1;
		errorValue = 'There is some problem. NOT Submitted.';
		return;
	} else if ($('#epcNumber').val()==undefined || $('#epcNumber').val() ==""){
		error = 1;
		errorValue = 'Epc Number is not valid. Please search again !';
		return;
	} else {
		//alert('All ok.');
	}
}

function validatechalanInvoiceNumberForm() {
	//alert("valid validatechalanInvoiceNumberForm");
	if( $('#actionchalanInvoiceNumberForm').val()==undefined || $('#actionchalanInvoiceNumberForm').val()=="" ) {
		error = 1;
		errorValue = 'There is some problem. NOT Submitted.';
		return;
	} else if( $('#chalanNumber').val()==undefined || $('#chalanNumber').val()=="" ){ 
		error = 1;
		errorValue = 'Please fill a valid Chalan-Number !!';
		return;
	} else if ( $('#invoiceNumber').val()==undefined || $('#invoiceNumber').val()=="" ){
		error = 1;
		errorValue = 'Please fill a valid Invoice-Number !!';
		return;
	} else {
		//alert('All ok.');
	}
}

