<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> <%-- xmlns="http://www.w3.org/1999/xhtml" --%>
	<head>
	    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
	    <meta http-equiv="Content-Style-Type" content="text/css" />
	    <meta name="keywords" content="javascript framework, RIA, SPA, client SOA, linb, jsLinb, RAD, IDE, Web IDE, widgets, javascript OOP, opensource, open-source, Ajax, cross-browser, prototype, web2.0, platform-independent, language-independent" />
	    <meta name="description" content="Web application created by Visual JS, powered by Sigma Visual framework" />
	    <meta name="copyright" content="copyright@www.sigmawidgets.com" />
	    <meta http-equiv="imagetoolbar" content="no" />
	    <meta http-equiv="CACHE-CONTROL" content="NO-CACHE"/>
	    <meta http-equiv="Pragma" content="NO-CACHE"/>
	    <meta http-equiv="Expires" content="-1"/>
	    <link href="../../common/css/styles.css" rel="stylesheet" type="text/css" />
	    <title>eON Application Multilingual version (Main)</title>
	</head>
    <body style="height:100%;overflow:hidden;">
        <script type="text/javascript" src="../../../runtime/jsLinb/js/linb-all.js"></script>
        <script type="text/javascript" src="../../../runtime/jsLinb/js/adv-all.js"></script>
        <script type="text/javascript" src="../../../runtime/jsLinb/Locale/en.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../../runtime/jsLinb/Locale/ja.js?version=<c:out value="${initParam.version}"/>"></script>

        <script type="text/javascript" src="../../common/Locale/en.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/Locale/ja.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../js/index.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../js/company.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../js/announcements.js?version=<c:out value="${initParam.version}"/>"></script>
		<script type="text/javascript" src="../js/tos.js?version=<c:out value="${initParam.version}"/>"></script>
		<script type="text/javascript" src="../js/proxylogin.js?version=<c:out value="${initParam.version}"/>"></script>
		<script type="text/javascript" src="../js/tosresetpopup.js?version=<c:out value="${initParam.version}"/>"></script>
		<script type="text/javascript" src="../js/activitylogs.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/util/StringUtil.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/util/util.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/util/contactus.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/util/account.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/util/changepassword.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/util/header.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/component/aboutEON.js?version=<c:out value="${initParam.version}"/>"></script>
		
		<script type="text/javascript">
	        var g_userRole = "ROLE_ADMIN";
	    </script>
	        
		<sec:authorize ifAllGranted="ROLE_ADMIN">
	        <script type="text/javascript">
	        var g_userRole = "ROLE_ADMIN";
	        </script>
		</sec:authorize>
		
        <script type="text/javascript">
	        linb.setAppLangKey("app");
	        linb.setLang("${pageContext.request.locale.language}", function(){               
	        });
            linb.Com.load('App', function(){linb('loading').remove();});
        </script>		
        <script type="text/javascript">
	        function submitTheForm(url, key, params, confrmsg) {
				var answer = confirm(confrmsg);
				if (!answer) return;
				
				var theForm = document.tempForm;
				//alert(param);
				var urlQuery = url + "?" + key + "=" + params;
				theForm.action = urlQuery;
				theForm.submit();
			}
        </script>        
	 
		<form name="tempForm" method="post">
		</form>
		
</body>
</html>