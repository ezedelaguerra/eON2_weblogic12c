<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> <%-- xmlns="http://www.w3.org/1999/xhtml" --%>
	<head>
	    <meta http-equiv="content-type" content="application/x-javascript;text/html; charset=utf-8" />
	    <meta http-equiv="Content-Style-Type" content="text/css" />
	    <meta name="keywords" content="javascript framework, RIA, SPA, client SOA, linb, jsLinb, RAD, IDE, Web IDE, widgets, javascript OOP, opensource, open-source, Ajax, cross-browser, prototype, web2.0, platform-independent, language-independent" />
	    <meta name="description" content="Web application created by Visual JS, powered by Sigma Visual framework" />
	    <meta name="copyright" content="copyright@www.sigmawidgets.com" />
	    <meta http-equiv="imagetoolbar" content="no" />
	    <meta http-equiv="CACHE-CONTROL" content="NO-CACHE"/>
	    <meta http-equiv="Pragma" content="NO-CACHE"/>
	    <meta http-equiv="Expires" content="-1"/>
	    <link href="app/common/css/styles.css" rel="stylesheet" type="text/css" />
	    <title>eON Application Multilingual version (Main)</title>
	</head>
 <body style="height:100%;overflow:hidden;">
        <script type="text/javascript" src="runtime/jsLinb/js/linb-all.js"></script>
        <script type="text/javascript" src="runtime/jsLinb/js/adv-all.js"></script>
        <script type="text/javascript" src="app/common/Locale/en.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="app/common/Locale/ja.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="app/common/js/tosClient.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="app/common/util/util.js?version=<c:out value="${initParam.version}"/>"></script>
	
		<div id="tmp" style="visibility: hidden;"><c:out value="${tos.content}" escapeXml="false"/></div>

	 	<script type="text/javascript">
	 		
	        linb.setAppLangKey("app");
	        linb.setLang("${pageContext.request.locale.language}", function(){               
	        });
            linb.Com.load('App.tosClient', function(){linb('loading').remove();linb('DIVCONTENT').html(document.getElementById('tmp').innerHTML)});
        </script>	
</body>
</html>