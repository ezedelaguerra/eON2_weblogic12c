<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
				alert('正しいお問い合わせ項目を入力してください。');
				return false;
		}
		if (!document.getElementById('chkDtlWishApply').checked 
			&& !document.getElementById('chkDtlReqDocs').checked
			&& !document.getElementById('chkDtlWishDtlExp').checked
			&& !document.getElementById('chkDtlOthers').checked) 
		{
				alert('正しいお問い合わせ項目詳細を入力してください。');
				return false;
		}
		if (document.getElementById('txtSurname').value==""){
			alert('正しいお名前（姓）を入力してください。');
			document.getElementById('txtSurname').focus();
			return false;
		}
		if (document.getElementById('txtFirstname').value==""){
			alert('正しいお名前（名）を入力してください。');
			document.getElementById('txtFirstname').focus();
			return false;
		}
		if (document.getElementById('txtFurSurname').value==""){
			alert('正しいふりがな（せい）を入力してください。');
			document.getElementById('txtFurSurname').focus();
			return false;
		}
		if (document.getElementById('txtFurFirstname').value==""){
			alert('正しいふりがな（めい）を入力してください。');
			document.getElementById('txtFurFirstname').focus();
			return false;
		}
		if (document.getElementById('txtCompName').value==""){
			alert('正しい会社名または屋号を入力してください。');
			document.getElementById('txtCompName').focus();
			return false;
		}
		if (document.getElementById('txtStoreName').value==""){
			alert('正しい店名を入力してください。');
			document.getElementById('txtStoreName').focus();
			return false;
		}
		if (document.getElementById('txtDeptName').value==""){
			alert('正しい部署または部門名を入力してください。');
			document.getElementById('txtDeptName').focus();
			return false;
		}
		var filterNo = /^([0-9]{10,11})+$/;
		var telno = document.getElementById('txttelno');
		if (!filterNo.test(telno.value)) {
			alert('正しい電話番号を入力してください。 例: 81358221566');
			telno.focus();
			return false;
		}
		var mobileno = document.getElementById('txtmobno');
		if (!filterNo.test(mobileno.value)) {
			alert('正しい携帯番号を入力してください。 例: 81358221566');
			mobileno.focus();
			return false;
		}
		var email = document.getElementById('txtEmail');
		var filterEmail = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (!filterEmail.test(email.value)) {
			alert('正しいメールアドレスを入力してください。 例: email@freshremix.co.jp');
			email.focus();
			return false;
		}
		filterZipCd=/^([0-9]{1,7})+$/;
		var zipcode = document.getElementById('txtZipCode');
		if (!filterZipCd.test(zipcode.value)) {
			alert('正しい郵便番号を入力してください。 最大の7ケタまで。');
			zipcode.focus();
			return false;
		}
		document.getElementById('responseMsg').style.display="block";
		return true;
	}
	function setup() {
		var onChange = document.getElementById('hidden');
		alert(onChange.value);
	}
	function getAddress() {
		if (!checkFields()) return;
		var onChange = document.getElementById('hidden');
		onChange.value = "true";

		document.forms[0].submit();
	}

	function resetOnchangeFlag() {
		var onChange = document.getElementById('hidden');
		onChange.value = "false";
	}

	function enterPress() {
		if (window.event && window.event.keyCode == 13){  
			getAddress();
			
		}
	}

</script>
</head>
<body>
<!--<form id="inquiry" action="/app/eon/login/inquiry.do" method="post" onsubmit="return checkFields();window.close();" >-->
<form id="inquiry" action="/eON/inquiry.do" method="post" 
	onsubmit="return checkFields();">
<input type="hidden" id="hidden"  name="hidden" value="false"/>
<input type="hidden" id="address"  name="address" value="${inqObj.address}"/>
<table style="width: 500;" cellspacing="5" cellpadding="6"
	align="center">
	<tr>
		<td width="100%">
		<c:choose>
			<c:when test="${not empty response}">
				<font id="responseMsg"
					style="font-family: verdana; color: #ff0033; font-size: 14pt;">
				${response}
				</font>
			</c:when>
			<c:otherwise>
			<font id="responseMsg"
						style="display: none; font-family: verdana; color: #ff0033; font-size: 14pt;">メール送信中...</font>
			</c:otherwise>
		</c:choose>	
		</td>
	</tr>
	<tr>
		<td colspan="5" class="inq-font-head" nowrap>&nbsp;お問い合わせ・資料請求</td>
	</tr>
	<tr>
		<td colspan="5" class="inq-font-head-frc" nowrap>&nbsp;フレッシュリミックス株式会社</td>
	</tr>
	<tr>
		<td colspan="5" class="inq-font-head-label" nowrap>
		&nbsp;お問い合わせの内容</td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap>&nbsp;<font color="red">*</font>お問い合わせ項目</td>
		<td class="inq-font-label" nowrap><input id="chkInqEON"
			name="chkInqEON" type="checkbox" value="1" <c:if test='${eon eq "1"}'>checked</c:if> > eON</td>
		<td class="inq-font-label" nowrap><input id="chkInqNSystem"
			name="chkInqNSystem" type="checkbox" value="1" <c:if test="${nsystem eq '1'}">checked</c:if> > N-System</td>
		<td class="inq-font-label" nowrap><input id="chkInqOthers"
			name="chkInqOthers" type="checkbox" value="1" <c:if test="${inqOthers eq '1'}">checked</c:if> > その他</td>
		<td></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap>&nbsp;<font color="red">*</font>お問い合わせ項目詳細</td>
		<td class="inq-font-label" nowrap><input id="chkDtlWishApply"
			name="chkDtlWishApply" type="checkbox" value="1" <c:if test="${apply eq '1'}">checked</c:if> > 申し込み希望</td>
		<td class="inq-font-label" nowrap><input id="chkDtlReqDocs"
			name="chkDtlReqDocs" type="checkbox" value="1" <c:if test="${docs eq '1'}">checked</c:if> > 資料請求</td>
		<td class="inq-font-label" nowrap><input id="chkDtlWishDtlExp"
			name="chkDtlWishDtlExp" type="checkbox" value="1" <c:if test="${exp eq '1'}">checked</c:if> > 詳細説明希望</td>
		<td class="inq-font-label" nowrap><input id="chkDtlOthers"
			name="chkDtlOthers" type="checkbox" value="1" <c:if test="${dtlOthers eq '1'}">checked</c:if>/> その他</td>
	</tr>
	<tr>
		<td colspan="5" class="inq-font-head-label" nowrap>&nbsp;お客さまのご連絡先など</td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>お名前（姓・名）</td>
		<td class="inq-font-label" nowrap><input id="txtSurname"
			name="txtSurname" type="text" class="inq-font-label" value="${inqObj.surname}" maxlength="50"/></td>
		<td class="inq-font-label" nowrap><input id="txtFirstname"
			name="txtFirstname" type="text" class="inq-font-label" value="${inqObj.firstname}" maxlength="50"/></td>
		<td class="inq-font-label" nowrap></td>
		<td class="inq-font-label" nowrap></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>ふりがな（せい・めい）</td>
		<td class="inq-font-label" nowrap><input id="txtFurSurname"
			name="txtFurSurname" type="text" class="inq-font-label" value="${inqObj.furiganaSurname}" maxlength="50"/></td>
		<td class="inq-font-label" nowrap><input id="txtFurFirstname"
			name="txtFurFirstname" type="text" class="inq-font-label" value="${inqObj.furiganaFirstname}" maxlength="50"/></td>
		<td class="inq-font-label" nowrap>&nbsp;</td>
		<td class="inq-font-label" nowrap>&nbsp;</td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>会社名または屋号</td>
		<td class="inq-font-label" nowrap colspan="4"><input
			name="txtCompName" id="txtCompName" size="48" type="text"
			class="inq-font-label" value="${inqObj.companyName}" maxlength="50"/></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>店　名</td>
		<td class="inq-font-label" nowrap colspan="4"><input
			name="txtStoreName" id="txtStoreName" size="48" type="text"
			class="inq-font-label" value="${inqObj.storeName}" maxlength="50"/></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>部署または部門名</td>
		<td class="inq-font-label" nowrap colspan="4"><input
			name="txtDeptName" id="txtDeptName" size="48" type="text"
			class="inq-font-label" value="${inqObj.deptName}" maxlength="50"/></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>電話番号</td>
		<td class="inq-font-label" nowrap colspan="4"><input
			name="txttelno" id="txttelno" size="15" type="text"
			class="inq-font-label" value="${inqObj.contactNo}" maxlength="11"/></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>携帯番号</td>
		<td class="inq-font-label" nowrap colspan="4"><input
			name="txtmobno" id="txtmobno" size="15" type="text"
			class="inq-font-label" value="${inqObj.mobileNo}" maxlength="11"/></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>メールアドレス</td>
		<td class="inq-font-label" nowrap colspan="4"><input
			name="txtEmail" id="txtEmail" size="48" type="text"
			class="inq-font-label" value="${inqObj.email}" maxlength="50"/></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>郵便番号</td>
		<td class="inq-font-label" nowrap colspan="4">
		<input name="txtZipCode" id="txtZipCode" size="10" type="text" value="${inqObj.zipcode}" maxlength="7" onkeydown="enterPress();"
		onchange="getAddress();"/> 
		<span style="font-size: 9px; color: Red;" >※半角で入力してください。-（ハイフン）は必要ありません。例）1230000</span></td>
	</tr>
	<tr>
		<td class="inq-font-label" nowrap><font color="red">*</font>住　所</td>
		<td class="inq-font-label" nowrap colspan="4">
		<input name="selAddress" id="selAddress" size="10" type="text" value="${address}" disabled/>
	</tr>	
	<tr>
		<td class="inq-font-label" nowrap height="60">&nbsp;</td>
		<td class="inq-font-label" nowrap colspan="4"><input
			id="btnSubmit" type="submit" value="送信" style="width: 61px;" />&nbsp;&nbsp;&nbsp;
		<input id="btnClose" type="button" value="閉じる" style="width: 61px"
			onclick="window.close();" /></td>
	</tr>
</table>
</form>
</body>
</html>