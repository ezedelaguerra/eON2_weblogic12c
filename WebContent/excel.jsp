<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
response.setContentType("application/csv");
response.setHeader("Content-Disposition","attachment; filename=" + "test.csv" );
%>