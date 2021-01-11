<%@ page language="java" contentType="InternetShortcut; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
response.setContentType("application/InternetShortcut");
response.setHeader("Content-Disposition","attachment; filename=" + "eON.url" );
%>
[DEFAULT]
BASEURL=https://<%= request.getServerName()%><%= request.getContextPath()%>
[InternetShortcut]
URL=https://<%= request.getServerName()%><%= request.getContextPath()%>
Modified=104656CBC580C70184
HotKey=0
IDList=
[{000214A0-0000-0000-C000-000000000046}]
Prop3=19,2
