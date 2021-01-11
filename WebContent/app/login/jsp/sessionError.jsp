<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error</title>
</head>
<body >
<center style="valign:middle">
<br><br>
<div style="width:50%; font-size:12px; ">
<b style="color:red; font-size:15px">エラー：別のセッションで接続されています。</b>
<br><br>
<p>同じブラウザでEONを使用している可能性があるか、または別のセッションが正常にログアウトされていませんでした。</p>
<center>
<div style="width:50%; text-align:left;">
下記を確認してください。

	<ol >
		<li>別のユーザーIDでログインできるように現在のユーザーIDにてログアウトしてください</li>
		<li>Internet Explorerをご利用の場合は再度立ち上げなおしてください</li>
		<li>このエラーが続く場合はシステム管理者に連絡してください。</li>
	</ol>
</div>
</center>
</div>
</center>
</body>
</html>