<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page
	import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter"%>
<%@ page
	import="org.springframework.security.ui.AbstractProcessingFilter"%>
<%@ page import="org.springframework.security.AuthenticationException"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
	String hasError = (String) pageContext.findAttribute("error");
	if(hasError.equals("1")){
		response.sendRedirect("/eON/app/login/jsp/error.jsp");	
	}
%>

<script type="text/javascript">


	function savePassword(){
		var frmObj = document.getElementById("f");
		var password = document.getElementById("password").value;
		var cPassword = document.getElementById("confirmPassword").value;

		if(password !="" && cPassword !=""){

			if(password !=cPassword ){
				alert("password does not match");
				return false;
			}
			
		}else{
			alert("please fill out the required fields");
			return false;
		}
		
		frmObj.action="/eON/updatePassword.do";
		frmObj.submit();
		return true;

	}

	function cancelOperation()
	{
	var fRet;
	fRet = confirm('Are you sure you want to exit?');


	if(fRet ==true){
		window.close();
	}

	} 
    
</script>

<style type="text/css">
	.cp-font-label {
		font-family: verdana;
		font-size: 12px;
	}	
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>パスワード管理</title>
</head>
<body>
<form:form method='post' commandName="user" id="f">
	<form:hidden path="validationId" />
	<table border="1"  bordercolor="#006400;" style="background-color: #AAD2FA; width: 200px; height: 200px;" align="center" cellpadding="5" cellspacing="5">
		<tr>
			<td style="font-family: verdana; font-size: 14px;" align="center" colspan="2">
				パスワード管理
			</td>
		</tr>
		<tr>
			<td align="center">
			
			<table border="0" align="center" cellpadding="3" cellspacing="3">
				<tr>
					<td height="10px"></td>					
				</tr>
				<tr>
					<td class="cp-font-label" align="left" colspan="2">ユーザ名:</td>
				</tr>

				<tr>
					<td colspan="2"><form:input id="username" path="username" readonly="true"
						size="39" /><form:hidden path="userId" /></td>
				</tr>

				<tr>
					<td class="cp-font-label" align="left" colspan="2">パスワード:</td>
				</tr>

				<tr>
					<td colspan="2"><form:password id="password" path="password" size="40" maxlength="21"/></td>
				</tr>

				<tr>
					<td class="cp-font-label" align="left" colspan="2">確認用：新パスワードの再入力:</td>
				</tr>

				<tr>
					<td colspan="2"><form:password id="confirmPassword" path="confirmPassword" size="40" maxlength="21" /></td>
				</tr>
				<tr>
					<td height="5px"></td>					
				</tr>
				<tr>
					<td colspan"2" align="right"><input type="button" value="保存" onclick="savePassword();" />&nbsp;&nbsp;
					<input type="button" value="キャンセル" onclick="cancelOperation()" /></td>
				</tr>
				<tr>
					<td height="5px"></td>					
				</tr>
			</table>
			
	</table>
</form:form>

</body>
</html>