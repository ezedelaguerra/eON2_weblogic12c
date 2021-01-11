<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" import="java.util.*"%>

<sec:authorize ifAllGranted="ROLE_ADMIN">
<% response.sendRedirect("http://" + request.getServerName() + request.getContextPath() + "/app/admin/jsp/index.jsp");%>
</sec:authorize>
<sec:authorize ifAllGranted="ROLE_SELLER">
<% response.sendRedirect("http://" + request.getServerName() + request.getContextPath() + "/app/seller/jsp/index.jsp");%>
</sec:authorize>
<sec:authorize ifAllGranted="ROLE_SELLER_ADMIN">
<% response.sendRedirect("http://" + request.getServerName() + request.getContextPath() + "/app/seller/jsp/index.jsp");%>
</sec:authorize>
<sec:authorize ifAllGranted="ROLE_CHOUAI">
<% response.sendRedirect("../../seller/html/index.html"); %>
</sec:authorize>
<sec:authorize ifAllGranted="ROLE_BUYER">
<% response.sendRedirect("http://" + request.getServerName() + request.getContextPath() + "/app/buyer/jsp/index.jsp");%>
</sec:authorize>
<sec:authorize ifAllGranted="ROLE_BUYER_ADMIN">
<% response.sendRedirect("http://" + request.getServerName() + request.getContextPath() + "/app/buyer/jsp/index.jsp");%>
</sec:authorize>
<sec:authorize ifAllGranted="ROLE_KEN">
<% response.sendRedirect("http://" + request.getServerName() + request.getContextPath() + "/app/admin/jsp/index.jsp");%>
</sec:authorize>