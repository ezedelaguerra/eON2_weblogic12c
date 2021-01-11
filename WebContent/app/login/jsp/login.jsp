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

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>

<html>
<head>
<title>eON Application Multilingual version (Main)</title>
<link rel="stylesheet" href="../../common/css/styles.css"
	type="text/css"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.popup_message {
	position: absolute;
	left: 0px;
	top: 0px;
	visibility: hidden;
	font-size: 9pt;
	color: #000000;
	background-color: #9f9;
	layer-background-color: light-green;
	border: 1px solid #000000;
	padding: 5;
	text-align: left;
}
</style>

	<!--<script type="text/javascript" src="../js/forgotpassword.js?version=<c:out value="${initParam.version}"/>"></script>-->
	<script type="text/javascript" src="../../../runtime/jsLinb/js/linb-all.js"></script>
    <script type="text/javascript" src="../../../runtime/jsLinb/js/adv-all.js"></script>
    <script type="text/javascript" src="../../../runtime/jsLinb/js/Coder.js"></script>
    <script type="text/javascript" src="../../../runtime/jsLinb/Locale/en.js?version=<c:out value="${initParam.version}"/>"></script>
    <script type="text/javascript" src="../../../runtime/jsLinb/Locale/ja.js?version=<c:out value="${initParam.version}"/>"></script>

    <script type="text/javascript" src="../../sigmagrid2.4e/grid/gt_grid_all.js?version=<c:out value="${initParam.version}"/>"></script>
    <script type="text/javascript" src="../../sigmagrid2.4e/jquery/js/jquery-1.4.2.min.js?version=<c:out value="${initParam.version}"/>"></script>
    <script type="text/javascript" src="../../sigmagrid2.4e/jquery/js/jquery-ui-1.8.custom.min.js?version=<c:out value="${initParam.version}"/>"></script>
    <script type="text/javascript" src="../../common/Locale/en.js?version=<c:out value="${initParam.version}"/>"></script>
    <script type="text/javascript" src="../../common/Locale/ja.js?version=<c:out value="${initParam.version}"/>"></script>
    <script type="text/javascript" src="../../common/util/util.js"/>"></script>
        
<script type="text/javascript">
linb.setLang("${pageContext.request.locale.language}", function(){});

var obj = new Object();
obj.announcementType="generalAnnouncement"
linb.Ajax("/eON/getAnnouncement.json",obj,
        function (response) {
                validateResponse(response);
                var infoMessage = retrieveInfoMessage(response);
                if (infoMessage != ""){
                    alert(infoMessage);
                } else {
                    obj = _.unserialize(response);
                    document.getElementById("generalAnnouncementDiv").innerHTML = obj.announcement;
                }
        },
        function(msg) {
            linb.message("失敗： " + msg);
        },
        null,
        {
            method : 'POST',
            retry : 0
        }
    ).start();

		removeFrames();
		var isSaveClicked = 0;
		var isNewInput = 0;
		function getCookie(c_name)
		{
			var aCookie = document.cookie.split("; ");
			//alert(aCookie.length);
			for (var i=0; i < aCookie.length; i++)
			{
			  // a name/value pair (a crumb) is separated by an equal sign
			  //alert(i);
			  var aCrumb = aCookie[i].split("=");
			  //alert('c_name = ' + c_name);
			  //alert('aCrumb[0] = ' + aCrumb[0]);
			  if (c_name == aCrumb[0]) {
				  //alert(aCrumb[1]);
			    return unescape(aCrumb[1]);
			  }
			}
			// a cookie with the requested name does not exist
			return null;
		}
		
		function setCookie()
		{
			//alert(document.f._spring_security_remember_me.checked);
			var c_name = 'j_username';
			var p_name = 'j_password';
			var value = document.f.j_username.value;
			var expiredays = 365;
			var exdate=new Date();
			exdate.setDate(exdate.getDate()+expiredays);
			//alert(value);
			document.cookie=c_name+ "=" +escape(value)+
			((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
			document.cookie=p_name+ "=" +escape(document.f.j_password.value)+
			((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
		}

		function ifSaveNotChecked(){
			isSaveClicked = '1';
			/*if(!document.f._spring_security_remember_me.checked){
				  document.cookie = c_name + "=; expires=Fri, 31 Dec 1999 23:59:59 GMT;";
				  document.cookie = p_name + "=; expires=Fri, 31 Dec 1999 23:59:59 GMT;";
				  //document.f.j_password.value="";
			}*/
		}

		function toggleNewInput(){
			isNewInput = '1';
		}		
		function checkCookie()
		{
			var username=getCookie('j_username');
			//alert("username "+username);
			if (username!=null && username!="" && username!='""')
			  {
			  //alert('Welcome again '+username+'!');
			  document.f.j_username.value = username;
			  document.f.j_password.value='dummypassword';
			  document.f._spring_security_remember_me.checked=true;
			  }
		}
		function isSaveIdPassword()
		{
			if(checkFields()){
				var frmObj = document.getElementById("f");
				if(frmObj._spring_security_remember_me.checked){
					if(isSaveClicked=='0' && isNewInput=='0'){
						var password=getCookie('j_password');
						//alert("username "+username);
						if (password!=null && password!="" && password!='""')
						  {
						  //alert('Welcome again '+username+'!');
						  document.f.j_password.value=password;
						  }
						//frmObj.action="/eON/saveIdPassword.do";
						//frmObj.submit();
						//return false;
					}else{
						setCookie();
					}
					return true;
				}else{
					if(isSaveClicked=='1'){
						var password=getCookie('j_password');
						if (password!=null && password!="" && password!='""')
						  {
						  document.f.j_password.value=password;
						  }
						var c_name = 'j_username';
						var p_name = 'j_password';
					  	document.cookie = c_name + "=; expires=Fri, 31 Dec 1999 23:59:59 GMT;";
					  	document.cookie = p_name + "=; expires=Fri, 31 Dec 1999 23:59:59 GMT;";
					}
					return true;
				}
			}else return false;
		}
        function checkFields() {
          	if (document.getElementById('errorMsg') != null)
        		document.getElementById('errorMsg').style.display="none";
        	document.getElementById('requiredMsg').style.display="none";
            var username = document.getElementById('j_username').value;
            var password = document.getElementById('j_password').value;
    		
            if ((username == '') || (password == '')) {
                document.getElementById('requiredMsg').style.display="block";
                return false;
            }
            
            return true;
        }
 		       
        function popforgotPassWindow() {
        	var browserName=navigator.appName;
			if (browserName=="Microsoft Internet Explorer") {
				window.showModalDialog("../html/ForgotPassword.html",'Window','dialogWidth:30;dialogHeight:16;');
			}
			else {
				mywindow = window.open ("../html/ForgotPassword.html",  "Forgot Password", "location=0,status=0,scrollbars=0,menubar=0,resizable=0,width=480,height=260");
				mywindow.moveTo(250,300);
			}
			/*linb.ComFactory.newCom('App.forgotpassword', function() {
                var comaccount = this;
                this.iptUsername.setValue(document.getElementById('j_username').value);
                this.show(linb( [ document.body ]));
            });*/
        }
		
		function popLoginHelpWindow() {
		
			var browserName=navigator.appName;
			if (browserName=="Microsoft Internet Explorer") {
				//window.showModalDialog("../html/loginHelp.html",'Login Help','dialogWidth:15;dialogHeight:10;');
				window.open("../html/loginHelp.html",'_blank','top=250, left=300, height=150, width=340, status=no, menubar=no, resizable=no, scrollbars=yes, toolbar=no, location=no, directories=no');
			}
			else {
				mywindow = window.open ("../html/loginHelp.html",  "Login Help", "location=0,status=0,scrollbars=0,menubar=0,resizable=0,width=340,height=150");
				mywindow.moveTo(250,300);
			}
		
			//document.location.href="loginHelp.html";
        }
		
		function popInquiry() {
			var browserName=navigator.appName;
			if (browserName=="Microsoft Internet Explorer") {
				//window.showModalDialog("inquiry.jsp",'Login Help','dialogWidth:50;dialogHeight:40;');
				window.open("inquiry.jsp",'_blank','top=50, left=100, height=650, width=850, status=no, menubar=no, resizable=yes, scrollbars=yes, toolbar=no, location=no, directories=no');
			} else {
				mywindow = window.open ("inquiry.jsp",  "Login Help", "location=0,status=0,scrollbars=0,menubar=0,resizable=0,width=840,height=640");
				mywindow.moveTo(50,50);
			}
        }
	    var IE = 0,NN = 0,N6 = 0;
	    if(document.all) IE = true;
	    else if(document.layers) NN = true;
	    else if(document.getElementById) N6 = true;


	    function show_popup(Msg,mX,mY,nX,nY){
	        var pX = 0,pY = 0;
	        var sX = -10,sY = 30;
	        if(IE){
	            MyMsg = document.all(Msg).style;
	            MyMsg.visibility = "visible";
	        }
	        if(NN){
	            MyMsg = document.layers[Msg];
	            MyMsg.visibility = "show";
	        }
	        if(N6){
	            MyMsg = document.getElementById(Msg).style;
	            MyMsg.visibility = "visible";
	        }
	        if(IE){
	            pX = document.body.scrollLeft;
	            pY = document.body.scrollTop;
	            MyMsg.left = mX + pX + sX;
	            MyMsg.top = mY + pY + sY;
	        }
	        if(NN || N6){
	            MyMsg.left = nX+ sX;
	            MyMsg.top = nY + sY;
	        }
	    }

	    function hide_popup(Msg){
	        if(IE) document.all(Msg).style.visibility = "hidden";
	        if(NN) document.layers[Msg].visibility = "hide";
	        if(N6) document.getElementById(Msg).style.visibility = "hidden";
	    }

	    function checkCapsLock(e, obj){
	         var kc, sk;
	         var posX = findPosX(obj);
	         var posY = findPosY(obj);

	         kc = e.keyCode?e.keyCode:e.which;
	         sk = e.shiftKey?e.shiftKey:((kc == 16)?true:false);
	         if(((kc >= 65 && kc <= 90) && !sk)||((kc >= 97 && kc <= 122) && sk))
	          show_popup('msg3', posX + 150, posY - 10,event.pageX,event.pageY);
	         else
	          hide_popup('msg3');
	    }

	  function findPosX(obj)
	  {
	    var curleft = 0;
	    if(obj.offsetParent)
	        while(1)
	        {
	          curleft += obj.offsetLeft;
	          if(!obj.offsetParent)
	            break;
	          obj = obj.offsetParent;
	        }
	    else if(obj.x)
	        curleft += obj.x;
	    return curleft;
	  }

	  function findPosY(obj)
	  {
	    var curtop = 0;
	    if(obj.offsetParent)
	        while(1)
	        {
	          curtop += obj.offsetTop;
	          if(!obj.offsetParent)
	            break;
	          obj = obj.offsetParent;
	        }
	    else if(obj.y)
	        curtop += obj.y;
	    return curtop;
	  }

	  function getUrlParams() {
		    var params = {};
		    window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi,
		function (str, key, value) {
		    params[key] = value;
		});
		    return params;
		}
	  function validateOnLoad(){
		  var params = getUrlParams();

		if (params.errorMsg && params.errorMsg.length > 0) {
			  alert(linb.Locale[linb.getLang()].app.caption[unescape(params.errorMsg)]);
		}
		
		if (document.f.autoLogin.value == "1") {
			document.f.autoLogin.value == "";
			document.f.j_username.value = '${user.userName}';
			document.f.j_password.value = '${user.password}';
			var frmObj = document.getElementById("f");
			frmObj.submit();

		} else {
			var errorMsg = '<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />';
			if (errorMsg == '' || errorMsg == null) {
				checkCookie();
			} else {
				//ifSaveNotChecked();
				var password = getCookie('j_password');
				if (password != null && password != "" && password != '""') {
					document.f.j_password.value = password;
				}
				var c_name = 'j_username';
				var p_name = 'j_password';
				document.cookie = c_name
						+ "=; expires=Fri, 31 Dec 1999 23:59:59 GMT;";
				document.cookie = p_name
						+ "=; expires=Fri, 31 Dec 1999 23:59:59 GMT;";
			}
		}
	}
	function popUp(url) {
		sealWin = window
				.open(
						url,
						"win",
						'toolbar=0,location=0,directories=0,status=1,menubar=1,scrollbars=1,resizable=1,width=500,height=450');
		self.name = "mainWin";
	}

	function shortcut() {
		var browserName = navigator.appName;
		if (browserName == "Microsoft Internet Explorer") {
			document.location.href = 'eonShortcutLink.jsp';
		} else {
			document.location = 'eonShortcutLink.jsp';
			//"http://localhost:8083/sigma-visual-2.2/VisualJS/projects/eON.url"
			//"http://localhost:8083/sigma-visual-2.2/VisualJS/projects/login/eON.url"
		}
	}

	function favorite() {
		url = window.parent.location;
		//alert(url);
		title = 'eON';
		if (window.sidebar) {
			window.sidebar.addPanel(title, url, "");
		}
		// ENHANCEMENT START 20120913: Lele - Redmine 880
		else if (window.chrome) {
			alert('Press ctrl+D to bookmark (Command+D for macs) after you click Ok');
		}
		// ENHANCEMENT END 20120913:
		else {
			window.external.AddFavorite(url, title);
		}
	}

	function removeFrames() {
		if (window.location != window.parent.location) {
			window.parent.location.reload();
		}
	}
</script>

</head>
<body onload="validateOnLoad();">
<form id='f' name='f' action='/eON/j_spring_security_check' method='post'
	onsubmit="return isSaveIdPassword();">

<input type="hidden" id="autoLogin" name="autoLogin" value='${autologin}'/>

<div id="loginDiv"><br />
<table border="1" align="center" cellpadding="1" cellpadding="1"
	height="450">
	<tr>
		<td>
		<table border="0" width="900px" align="center" cellpadding="1">
			<tr>
				<td style="background-color: #000000;" colspan="6" valign="bottom">
</td>
			</tr>
			<tr class="backcolor-welcome">
				<td colspan="6" valign="middle"
					style="text-align: center; border-width: 1px; border-color: Silver; height: 50px;">
				<span
					style="font-family: Verdana; font-size: 26px; font-weight: bold">eONへようこそ</span></td>
			</tr>
			<tr>
				<td colspan="6" valign="middle" height="40"></td>
			</tr>
			<tr>
				<td rowspan="8" align="center" valign="top">
				<table>
					<tr>
						<td align="center">
							<a href="http://www.hontoichiba.com/" target="_blank"/> 
							<img src="../../common/img/logo_honto_toumei_170.gif" border="0"
								style="height: 50px; width: 100px;" alt="">
							</a>
							<br/><br/>
							<a href="http://store.shopping.yahoo.co.jp/yaonet/index.html" target="_blank">
							<img src="../../common/img/yaonet.gif"
								border="0" style="height: 50px; width: 100px;">
							</a>
							<br/><br/>
							




						
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td><img alt="" src="../../common/img/pixel.gif"
					style="width: 1px; height: 20px" /></td>
				<td align="center">
				
				<c:choose>
					<c:when
						test="${SPRING_SECURITY_LAST_EXCEPTION.message eq 'Bad credentials' || autologin == '0' }">
						<font id="errorMsg"
							style="font-family: verdana; color: #ff0033; font-size: 8pt;">
						ユーザ名とパスワードが正しくありません。 </font>
					</c:when>
					<c:otherwise>
						<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
							<font id="errorMsg"
								style="font-family: verdana; color: #ff0033; font-size: 8pt;">
							Your login attempt was not successful, try again.<br />
							<br />
							Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.
							</font>
						</c:if>
					</c:otherwise>
				</c:choose> <font id="requiredMsg"
					style="display: none; font-family: verdana; color: #ff0033; font-size: 8pt;">ユーザ名とパスワードを入力してください。</font>

				</td>
				<td></td>
				<td></td>
				<td rowspan="8" valign="top" width="400">
				<div id="generalAnnouncementDiv" style="width:310px;height:300px;overflow-y:auto;padding:10px;-moz-box-shadow:inset 0 0 5px #000000;-webkit-box-shadow:inset 0 0 5px #000000;box-shadow:inset 0 0 5px #000000;"></div>
				<%--<iframe
					src="https://meta2.freshremix.com/eon/eon_message_general.html"
					width="310" height="300">
				<p>Your browser does not support iframes.</p>
				</iframe>--%>
			</tr>
			<%-- ENHANCEMENT START 20120913: Lele - Redmine 880 --%>
			<tr style="height: 30px;">
				<td align="right" nowrap>ユーザーID&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td align="left" style="font-family: verdana; font-size: 12px;">
				<input type="text"
				      id="j_username" name="j_username" 
				      value = "<c:if test='${SPRING_SECURITY_LAST_EXCEPTION.message eq "Bad credentials"}'><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>"
				      style="text-align: left; width: 162px;height: 20px;" maxlength="21" onchange="toggleNewInput()"/>
				</td>
				<td></td>
			</tr>
                <tr style="height: 30px;">
                      <td align="right" nowrap>パスワード &nbsp;&nbsp;&nbsp;&nbsp;</td>
                      <td align="left" style="font-family: verdana; font-size: 12px;">
                      <input type="password" 
                            id="j_password" name="j_password"  
                            value = "<c:if test='${SPRING_SECURITY_LAST_EXCEPTION.message eq "Bad credentials"}'><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>"
                            style="text-align: left; width: 162px;height: 20px;" maxlength="21" onchange="toggleNewInput()"
                            onKeyPress="return checkCapsLock(event, this)" onMouseOut="hide_popup('msg3')" /></td>
                      <td></td>
                </tr>
                <tr style="height: 30px;">
                      <td colspan="3" align="center" valign="bottom">
                      <table>
                            <tr>
                                  <td><input type="submit" value="ログイン" style="width: 80px" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                  </td>
                                  <td class="font-label">
                                  <input type="checkbox" id="_spring_security_remember_me" name="_spring_security_remember_me" onclick="ifSaveNotChecked();"/>ID、パスワードの保存</td>
                            </tr>
                      </table>
                      </td>
                </tr>
                <tr style="height: 80px;">
                <%-- ENHANCEMENT END 20120913: --%>
				<td><img alt="" src="../../common/img/pixel.gif"
					style="width: 1px; height: 10px" /></td>
				<td colspan="2" align="center">
				<table align="center">
					<tr>
						<td align="left">
						<img alt="" src="../../common/img/list.gif"
							style="width: 14px; height: 14px" />&nbsp;
							<a href="#" onClick="favorite();" class="link-label"> 
							<!--<font size="2">Add to Favorite</font></a><br />-->
							<font size="2">お気に入りに追加</font></a><br />
						<img alt="" src="../../common/img/list.gif"
							style="width: 14px; height: 14px" />&nbsp;&nbsp;<a href="#"
							onclick="shortcut();" class="link-label">
							<!--<font size="2">Shortcut</font></a><br />-->
							<font size="2">ショートカットの作成</font></a><br />
							<%--
						<img alt="" src="../../common/img/list.gif"
							style="width: 14px; height: 14px;" />&nbsp;&nbsp;<a href="#"
							onclick="popforgotPassWindow();" class="link-label">
							<font size="2">パスワードを忘れた方はこちら</font></a><br />
							 --%>
						<img alt="" src="../../common/img/list.gif"
							style="width: 14px; height: 14px" />&nbsp;&nbsp;<a href="#"
							onclick="popInquiry();" class="link-label">
							<font size="2">IDをお持ちでない方はこちら</font></a></td>
					</tr>
				</table>
				</td>
				<td></td>
			</tr>
			<tr>
				<td><img alt="" src="../../common/img/pixel.gif"
					style="width: 1px; height: 10px" /></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td><img alt="" src="../../common/img/pixel.gif"
					style="width: 1px; height: 10px" /></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td height="20"></td>
			</tr>
			<tr>
				<table align="center" valign="center" height="60px" >
					<tr>
						<td align="right" class="font-label"><img alt=""
							src="../../common/img/telephone.gif"
							style="width: 40px; height: 40px" /></td>
						<td	style="font-size: 11px; font-family: verdana; text-align: left;"
							align="left">お電話によるお問合せはこちらから<br />
						月曜日～金曜日（祝祭日除く） 9時～17時<br />
						03-5822-1352</td>
<!--						<td width="50%"></td>-->
					</tr>
				</table>
				<!--                            </td>-->
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<span class="popup_message" id="msg3">CapsLockキーがオン <br>
CapsLockキーがオンになっていると、パスワードが誤って入力される可能性があります。 <br>
CapsLockキーを押してオフにしてから、パスワードを入力してください</span></form>
</body>
</html>
