<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link href="../../common/css/styles.css" rel="Stylesheet" type="text/css"/>
<style>
/*css for inquiry*/

.inq-font-head-frc
{
	background-color:orange;
	font-size:12px;
	font-family:Verdana;
	font-weight:bold;
}
.inq-font-head
{
	font-size:16px;
	font-family:Verdana;
	border-collapse:collapse;
}
.inq-font-head-label
{
	background-color:#FAE3AA;
	font-size:12px;
	font-family:Verdana;
	border-collapse:collapse;
	font-weight:bold;
}

.inq-font-label
{
	font-size:11px;
	font-family:Verdana;
}
</style>
<title>eON Inquiry</title>
<script type="text/javascript">
		
	function checkFields() {

		if (!document.getElementById('chkInqEON').checked  
			&& !document.getElementById('chkInqNSystem').checked
			&& !document.getElementById('chkInqOthers').checked) 
		{
				alert('Please provide inquiry items.');
				return false;
		}
		if (!document.getElementById('chkDtlWishApply').checked 
			&& !document.getElementById('chkDtlReqDocs').checked
			&& !document.getElementById('chkDtlWishDtlExp').checked
			&& !document.getElementById('chkDtlOthers').checked) 
		{
				alert('Please provide inquiry details.');
				return false;
		}
		if (document.getElementById('txtSurname').value==""){
			alert('Please provide your surname.');
			document.getElementById('txtSurname').focus();
			return false;
		}
		if (document.getElementById('txtFirstname').value==""){
			alert('Please provide your firstname.');
			document.getElementById('txtFirstname').focus();
			return false;
		}
		if (document.getElementById('txtFurSurname').value==""){
			alert('Please provide your Furigana surname.');
			document.getElementById('txtFurSurname').focus();
			return false;
		}
		if (document.getElementById('txtFurFirstname').value==""){
			alert('Please provide your Furigana firstname.');
			document.getElementById('txtFurFirstname').focus();
			return false;
		}
		if (document.getElementById('txtCompName').value==""){
			alert('Please provide your Company name.');
			document.getElementById('txtCompName').focus();
			return false;
		}
		if (document.getElementById('txtStoreName').value==""){
			alert('Please provide your Store name.');
			document.getElementById('txtStoreName').focus();
			return false;
		}
		if (document.getElementById('txtDeptName').value==""){
			alert('Please provide your Department name.');
			document.getElementById('txtDeptName').focus();
			return false;
		}
		var filterNo = /^([0-9]{10,11})+$/;
		var telno = document.getElementById('txttelno');
		if (!filterNo.test(telno.value)) {
			alert('Please provide a valid telephone number. Example: 81358221566');
			telno.focus();
			return false;
		}
		var mobileno = document.getElementById('txtmobno');
		if (!filterNo.test(mobileno.value)) {
			alert('Please provide a valid mobile number. Example: 81358221566');
			mobileno.focus();
			return false;
		}
		var email = document.getElementById('txtEmail');
		var filterEmail = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (!filterEmail.test(email.value)) {
			alert('Please provide a valid email address. Example: email@freshremix.co.jp');
			email.focus();
			return false;
		}
		if (document.getElementById('selAddress').value==""){
			alert('Please provide your Address.');
			document.getElementById('selAddress').focus();
			return false;
		}
		//filterZipCd=/^\d{7}$/;
		filterZipCd=/^([0-9]{1,7})+$/;
		var zipcode = document.getElementById('txtZipCode');
		if (!filterZipCd.test(zipcode.value)) {
			alert('Please provide a valid zipcode. Allows maximum 7 digits.');
			zipcode.focus();
			return false;
		}
		document.getElementById('responseMsg').style.display="block";
		return true;
	}
</script>
</head>
<body>
<!--<form id="inquiry" action="/app/eon/login/inquiry.do" method="post" onsubmit="return checkFields();window.close();" >-->
<form id="inquiry" action="/eON/inquiry.do" method="post"
	onsubmit="return checkFields();">
<table style="width: 500;" cellspacing="5" cellpadding="6"
	align="center">
	<tr>
		<td>
		<c:choose>
			<c:when test="${not empty response}">
				<font id="responseMsg"
					style="font-family: verdana; color: #ff0033; font-size: 14pt;">
				${response}
				</font>
			</c:when>
			<c:otherwise>
			<font id="responseMsg"
						style="display: none; font-family: verdana; color: #ff0033; font-size: 14pt;">Sending email...</font>
			</c:otherwise>
		</c:choose>	
		</td>
	</tr>
	<tr>
		<td colspan="5" class="inq-font-head" nowrap>&nbsp;Inquiry
		Request For Documents</td>
	</tr>
	<tr>
		<td colspan="5" class="inq-font-head-frc" nowrap>&nbsp;Fresh
		Remix Corporation</td>
	</tr>
	<tr>
		<td colspan="5" class="inq-font-head-label" nowrap>
		&nbsp;Contents of inquiry</td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap>&nbsp;<font color="red">*</font>Inquiry Items</td>
		<td class="inq-font-label" nowrap><input id="chkInqEON"
			name="chkInqEON" type="checkbox" value="1" <c:if test='${indObj.inquireEon eq "1"}'>checked</c:if> > eON</td>
		<td class="inq-font-label" nowrap><input id="chkInqNSystem"
			name="chkInqNSystem" type="checkbox" value="1" <c:if test="${indObj.inquireNsystem eq '1'}">checked</c:if> > N-System</td>
		<td class="inq-font-label" nowrap><input id="chkInqOthers"
			name="chkInqOthers" type="checkbox" value="1" <c:if test="${indObj.inquireOthers eq '1'}">checked</c:if> > Others</td>
		<td></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap>&nbsp;<font color="red">*</font>Details Of Inquiry</td>
		<td class="inq-font-label" nowrap><input id="chkDtlWishApply"
			name="chkDtlWishApply" type="checkbox" value="1" <c:if test="${indObj.applyDetails eq '1'}">checked</c:if> > Wish to apply</td>
		<td class="inq-font-label" nowrap><input id="chkDtlReqDocs"
			name="chkDtlReqDocs" type="checkbox" value="1" <c:if test="${indObj.docsDetails eq '1'}">checked</c:if> > Request for docs</td>
		<td class="inq-font-label" nowrap><input id="chkDtlWishDtlExp"
			name="chkDtlWishDtlExp" type="checkbox" value="1" <c:if test="${indObj.explainDetails eq '1'}">checked</c:if> > Wish for
		detailed explanation</td>
		<td class="inq-font-label" nowrap><input id="chkDtlOthers"
			name="chkDtlOthers" type="checkbox" value="1" <c:if test="${indObj.otherDetails eq '1'}">checked</c:if>/> Others</td>
	</tr>
	<tr>
		<td colspan="5" class="inq-font-head-label" nowrap>&nbsp;Contact
		details of customer</td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>Name(Surname/Firstname)</td>
		<td class="inq-font-label" nowrap><input id="txtSurname"
			name="txtSurname" type="text" class="inq-font-label" value="${inqObj.surname}" maxlength="50"/></td>
		<td class="inq-font-label" nowrap><input id="txtFirstname"
			name="txtFirstname" type="text" class="inq-font-label" value="${inqObj.firstname}" maxlength="50"/></td>
		<td class="inq-font-label" nowrap></td>
		<td class="inq-font-label" nowrap></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>Furigana(Surname/Firstname)</td>
		<td class="inq-font-label" nowrap><input id="txtFurSurname"
			name="txtFurSurname" type="text" class="inq-font-label" value="${inqObj.furiganaSurname}" maxlength="50"/></td>
		<td class="inq-font-label" nowrap><input id="txtFurFirstname"
			name="txtFurFirstname" type="text" class="inq-font-label" value="${inqObj.furiganaFirstname}" maxlength="50"/></td>
		<td class="inq-font-label" nowrap>&nbsp;</td>
		<td class="inq-font-label" nowrap>&nbsp;</td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>Company name</td>
		<td class="inq-font-label" nowrap colspan="4"><input
			name="txtCompName" id="txtCompName" size="48" type="text"
			class="inq-font-label" value="${inqObj.companyName}" maxlength="50"/></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>Store name</td>
		<td class="inq-font-label" nowrap colspan="4"><input
			name="txtStoreName" id="txtStoreName" size="48" type="text"
			class="inq-font-label" value="${inqObj.storeName}" maxlength="50"/></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>Department name</td>
		<td class="inq-font-label" nowrap colspan="4"><input
			name="txtDeptName" id="txtDeptName" size="48" type="text"
			class="inq-font-label" value="${inqObj.deptName}" maxlength="50"/></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>Telephone No</td>
		<td class="inq-font-label" nowrap colspan="4"><input
			name="txttelno" id="txttelno" size="15" type="text"
			class="inq-font-label" value="${inqObj.contactNo}" maxlength="11"/></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>Mobile No</td>
		<td class="inq-font-label" nowrap colspan="4"><input
			name="txtmobno" id="txtmobno" size="15" type="text"
			class="inq-font-label" value="${inqObj.mobileNo}" maxlength="11"/></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>Email</td>
		<td class="inq-font-label" nowrap colspan="4"><input
			name="txtEmail" id="txtEmail" size="48" type="text"
			class="inq-font-label" value="${inqObj.email}" maxlength="50"/></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>Address</td>
		<td class="inq-font-label" nowrap colspan="4"><select
			class="inq-font-label" id="selAddress" name="selAddress">
			<c:if test="${not empty indObj.address}">
			<option>${inqObj.address}</option>
			</c:if>
			<option value="">Please select</option>
			<option value="Tokyo">Tokyo</option>
			<option value="Kobe">Kobe</option>
			<option value="Nagoya">Nagoya</option>
		</select> Prefecture</td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>Zipcode</td>
		<td class="inq-font-label" nowrap colspan="4">
		<input name="txtZipCode" id="txtZipCode" size="10" type="text" value="${inqObj.zipcode}" maxlength="7"/> 
		<span style="font-size: 9px; color: Red;" >Please input by half-pitch
		characters, hyphen is not needed (Ex.) 1230000</span></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap height="60">&nbsp;</td>
		<td class="inq-font-label" nowrap colspan="4"><input
			id="btnSubmit" type="submit" value="Submit" style="width: 61px;" />&nbsp;&nbsp;&nbsp;
		<input id="btnClose" type="button" value="Close" style="width: 61px"
			onclick="window.close();" /></td>
	</tr>
</table>
</form>
</body>
</html>