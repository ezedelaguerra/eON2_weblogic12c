<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error</title>
</head>
<body>Error doing the request. <a href="login.jsp">Click here to login again.</a></body>
<% request.getSession().invalidate(); %>
</html>