<%--
 * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
 * <BR>
 * <B>Description:</B><BR>
 * Describe class or interface.<BR>
 * <BR>
 * <B>Known Bugs:</B>
 * none<BR>
 * <BR>
 * <B>History:</B>
 * <PRE><!-- Do not use tabs in the history table! Do not extend table width! -->
 * date       	name	version		changes
 * ------------------------------------------------------------------------------
 * 20120913		Lele	chrome		Redmine 880 - Chrome compatibility
 --%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> <%-- xmlns="http://www.w3.org/1999/xhtml" --%>
	<head>
		<%-- ENHANCEMENT 20120913: Lele - Redmine 880 --%>
		<meta http-equiv="X-UA-Compatible" content="chrome=1"/>
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
	    
	   <jsp:include page="../../common/util/disable_right_click.jsp" />
	  <%--  <script language='Javascript'>  
		 function mycontextmenu() {           
	     return false;   
	     } 
		document.oncontextmenu = mycontextmenu;
		</script>--%>
	</head>
	<%-- ENHANCEMENT START 20120913: Lele - Redmine 880 --%>
	<%-- <body style="height:100%;overflow:hidden;" > --%>
    <body style="height:100%;overflow:auto;" scroll="yes">
    <%-- ENHANCEMENT END 20120913: --%>
        <div id='loading'><img src="../../../runtime/loading.gif" alt="Loading..." /></div>
        <script type="text/javascript" src="../../../runtime/jsLinb/js/linb-all.js"></script>
        <script type="text/javascript" src="../../../runtime/jsLinb/js/adv-all.js"></script>
        <script type="text/javascript" src="../../../runtime/jsLinb/js/Coder.js"></script>
        <script type="text/javascript" src="../../../runtime/jsLinb/Locale/en.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../../runtime/jsLinb/Locale/ja.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/Locale/en.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/Locale/ja.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../js/index.js?version=<c:out value="${initParam.version}"/>"></script>
        <!-- load header first to initialize user preference to be used by menu.js -->
        <script type="text/javascript" src="../../common/util/header.js?version=<c:out value="${initParam.version}"/>"></script>   
        <script type="text/javascript" src="../js/menu.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../js/home.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../js/order_sheet.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../js/allocation_sheet.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../js/billing_sheet.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../js/productmasterlist.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../js/masterlist_sheet.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../js/findsku.js?version=<c:out value="${initParam.version}"/>"></script>        
        <script type="text/javascript" src="../js/akadensheet.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../js/importallocdata.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../js/addnewpmf.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../js/user_preference.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../js/sku_group_maintenance.js?version=<c:out value="${initParam.version}"/>"></script>   
        <script type="text/javascript" src="../js/company_sort.js?version=<c:out value="${initParam.version}"/>"></script>  
        <script type="text/javascript" src="../js/hide_column.js?version=<c:out value="${initParam.version}"/>"></script>
<!--        <script type="text/javascript" src="../js/buyers_sort.js"></script>  -->
        <script type="text/javascript" src="../js/filter_akaden.js?version=<c:out value="${initParam.version}"/>"></script>        
        <script type="text/javascript" src="../../common/util/contactus.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/util/account.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/util/date_panel.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/util/util.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/util/menuItems_util.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/util/StringUtil.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/util/userDetails.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/component/add_sku_group.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/util/changepassword.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/component/comments.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/component/mail_viewer.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/component/search_recipient.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/component/aboutEON.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/component/unitoforder.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/component/download_excel.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/component/uploadCsv.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/component/sku_sort.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/component/sku_group_sort.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/component/buyers_sort.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/component/sellers_sort.js?version=<c:out value="${initParam.version}"/>"></script>
        <script type="text/javascript" src="../../common/js/tosPopUp.js?version=<c:out value="${initParam.version}"/>"></script>   
        <script type="text/javascript" src="../../common/js/alertPopUp.js?version=<c:out value="${initParam.version}"/>"></script>   
        
        
        <script type="text/javascript">
        	var g_roleType = "";
        	var g_categoryId = 1;
        	var unPublishedAllocation ="false";
        	var unFinalizedBilling ="false";
        	var autoPublishAlloc = "false";
        	var displayAllocQty = "0";
        	var unfinalizeReceived = "0";
        	var categoryIndex = 0;
        	var categoryTabs =[];
        </script>
        <sec:authorize ifAllGranted="ROLE_SELLER">        
	        <script type="text/javascript" src="../js/filter.js?version=<c:out value="${initParam.version}"/>"></script>
	        <script type="text/javascript" src="../js/filter_akaden.js?version=<c:out value="${initParam.version}"/>"></script>
        	<script type="text/javascript" src="../js/download_csv.js?version=<c:out value="${initParam.version}"/>"></script>
	        <script type="text/javascript">
	        var g_userRole = "ROLE_SELLER";
	        </script>
		</sec:authorize>
		<sec:authorize ifAllGranted="ROLE_SELLER_ADMIN">
			<script type="text/javascript" src="../js/admin_filter.js?version=<c:out value="${initParam.version}"/>"></script>
			<script type="text/javascript" src="../js/admin_filter_akaden.js?version=<c:out value="${initParam.version}"/>"></script>
			<script type="text/javascript" src="../js/download_csv_admin.js?version=<c:out value="${initParam.version}"/>"></script>
			<script type="text/javascript">
			  var g_userRole = "ROLE_SELLER_ADMIN";
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
				
				var theForm = document.myForm;
				//alert(param);
				var urlQuery = url + "?" + key + "=" + params;
				theForm.action = urlQuery;
				theForm.submit();
			}
			function submitTempForm(param) {
				var answer = confirm("Proceed with excel download?");
				if (!answer) return;
				
				var theForm = document.myForm;
				//alert(param);
				theForm.action = "/eON/downloadExcel.json?_dxls_json=" + param;
				theForm.submit();				
			}

		</script>
		
		 
		<form name="myForm" method="post">
		</form>
		
	</body>
</html>