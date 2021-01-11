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
 * 20120724		Lele	v11			Redmine 851 - Change row's color when selected
 * 20120724		Lele	v11			Redmine 420 - Change of display
 * 20120913		Lele	chrome		Redmine 880 - Chrome compatibility
 * 20120913		Mel 	v14.01		Redmine 1110 - Change the position of each strings in cells/文字列の位置の変更
 
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:set var="user" value="${user}" />
<c:set var="allDatesView" value="${orderSheetParam.allDatesView}" />

<c:set var="showA" value="true" />
<c:set var="oneBuyerId" value="${orderSheetParam.selectedBuyerID}"/>
<c:if test="${fn:contains(orderSheetParam.selectedBuyerID, ';') || allDatesView eq true}">
  <c:set var="oneBuyerId" value="${orderSheetParam.datesViewBuyerID}"/>
  <c:set var="showA" value="false" />
</c:if>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" -->
<html>
<head>
<%-- ENHANCEMENT 20120913: Lele - Redmine 880 --%>
<meta http-equiv="X-UA-Compatible" content="chrome=1"/>
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
 
<%--<link href="highlight/style.css" rel="stylesheet" type="text/css" />--%>
<%-- ENHANCEMENT START 20120913: Lele - Redmine 880 --%>
<link rel="stylesheet" type="text/css" href="../../sigmagrid2.4e/grid/css/styles.css?version=<c:out value="${initParam.version}"/>" />
<link rel="stylesheet" type="text/css" href="../../sigmagrid2.4e/grid/gt_grid.css?version=<c:out value="${initParam.version}"/>" />
<link rel="stylesheet" type="text/css" href="../../sigmagrid2.4e/grid/skin/vista/skinstyle.css?version=<c:out value="${initParam.version}"/>" />
<%-- ENHANCEMENT END 20120913: --%>
<script type="text/javascript" src="../../sigmagrid2.4e/grid/gt_msg_en.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../sigmagrid2.4e/grid/gt_msg_ja.js?version=<c:out value="${initParam.version}"/>"></script>
 
<%--<script src="highlight/jssc3.js" type="text/javascript"></script>--%>
<script type="text/javascript" src="../../sigmagrid2.4e/src/gt_const.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../sigmagrid2.4e/grid/gt_grid_all.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../sigmagrid2.4e/grid/flashchart/fusioncharts/FusionCharts.js?version=<c:out value="${initParam.version}"/>"></script>
 
<link type="text/css" href="../../sigmagrid2.4e/jquery/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="../../sigmagrid2.4e/jquery/js/jquery-1.4.2.min.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../sigmagrid2.4e/jquery/js/jquery-ui-1.8.custom.min.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../../runtime/jsLinb/js/linb-all.js"></script>
<script type="text/javascript" src="../../common/util/StringUtil.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../common/util/util.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../common/util/sigma_util.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../common/util/taxUtil.js?version=<c:out value="${initParam.version}"/>"></script>

<jsp:include page="../../common/util/disable_right_click.jsp" />
<script type="text/javascript">
var TAX_RATE = <c:out value="${TAX_RATE}" />;
var grid_demo_id = "myGrid1" ;
	
	var _dataset= {
		fields :[
			{name : 'skuId', type : 'int'},
			{name : 'skuBaId', type : 'int'},
<c:if test="${showA eq true}">
		    {name : 'A_${oneBuyerId}'},
</c:if>
		    {name : 'sellerId', type : 'int'},
		    {name : 'companyname'},
			{name : 'sellername'},
			{name : 'groupname'},
			{name : 'marketname'},
			{name : 'column01'},
			{name : 'column02'},
			{name : 'column03'},
			{name : 'column04'},
			{name : 'column06'},
			{name : 'column07'},
			{name : 'column08'},
			{name : 'column09'},
			{name : 'skuname'},
			{name : 'home'},
			{name : 'grade'},
			{name : 'clazzname'},
		<c:if test="${user.role.roleId eq 4}"> <%-- buyer admin --%>
			{name : 'price2', type: 'float' },
		</c:if>
			{name : 'pricewotax', type: 'float' },
			{name : 'pricewtax', type: 'float' },
			{name : 'B_purchasePrice', type: 'float' },
			{name : 'column10'},
			{name : 'column05'},
			{name : 'column11'},
			{name : 'B_sellingPrice', type: 'float' },
			{name : 'B_sellingUom'},
			{name : 'B_profitPercentage', type: 'float' },
			{name : 'packageqty', type: 'float'},
			{name : 'packagetype'},
			{name : 'unitorder'},
			{name : 'unitordername'},
<c:choose>
  <c:when test="${allDatesView eq false}">
    <c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
	  		{name : 'L_${user.userId}'},
        	{name : 'Q_${user.userId}', type: 'float'},
	  </c:forEach>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <c:forEach var="dateKey" items="${dateList}" varStatus="status">
    		{name : 'L_${dateKey}'},
            {name : 'Q_${dateKey}', type: 'float'},
    </c:forEach>
  </c:otherwise>
</c:choose>
			{name : 'B_skuComment'},
<c:choose>
  <c:when test="${allDatesView eq false}">
  			{name : 'column12'},
			{name : 'column13'},
			{name : 'column14'},
			{name : 'column15'},
			{name : 'column16'},
			{name : 'column17'},
			{name : 'column18'},
			{name : 'column19'},
			{name : 'column20'},
			{name : 'rowqty', type: 'float'},
  </c:when>
  <c:otherwise>
  			{name : 'rowqty', type: 'float'},
		  	{name : 'column12'},
			{name : 'column13'},
			{name : 'column14'},
			{name : 'column15'},
			{name : 'column16'},
			{name : 'column17'},
			{name : 'column18'},
			{name : 'column19'},
			{name : 'column20'},
  </c:otherwise>
</c:choose>
			{name : 'lockflag'},
			{name : 'profitInfo'}
		],
		recordType : 'json'
	}
	
	var _columns = [
		{id:'skuId', header:"SKU Id", width:40, editable:false, hidden:true},
		{id:'skuBaId', header:"SKU Id", width:40, editable:false, hidden:true},
<c:choose>
  <c:when test="${allDatesView eq false}">
  	<c:forEach var="company" items="${companyList}"  varStatus="status">
      <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
  	    {id:'L_${user.userId}', header:"${user.userName}", align:"left", width:10, hidden:true},
  	  </c:forEach>
	</c:forEach>
  </c:when>
  <c:otherwise>
    <c:forEach var="dateKey" items="${dateList}" varStatus="status">
	  <c:set var="dateCol" value="${dateMap[dateKey]}" />
		{id:'L_${dateKey}', header:"${dateCol}", align:"left", width:10, hidden:true},
	</c:forEach>
  </c:otherwise>
</c:choose>
<c:if test="${showA eq true}">
	    {id:'A_${oneBuyerId}', header:"approval", align:"left", width:35, renderer:render_checkbox},
</c:if>
	    {id: 'sellerId', header:"Seller Id", width : 40, editable : false, hidden : true},
    	{id: 'companyname', header:"Companyname", align:"left", width : "${user.preference.widthColumn.companyName}", hidden : toSigmaBoolean("${user.preference.hideColumn.companyName}")},
        {id: 'sellername', header:"Sellername", align:"left", width : "${user.preference.widthColumn.sellerName}", hidden : toSigmaBoolean("${user.preference.hideColumn.sellerName}")},
        {id: 'groupname', header:"Groupname", align:"left", width : "${user.preference.widthColumn.groupName}", hidden : toSigmaBoolean("${user.preference.hideColumn.groupName}"), renderer : groupnameRenderer},
    	{id: 'marketname', header:"Marketname", align:"left", width : "${user.preference.widthColumn.marketCondition}", hidden : toSigmaBoolean("${user.preference.hideColumn.marketCondition}")},
    	{id: 'column01', header: "Column 01", align: "right", width : "${user.preference.widthColumn.column01}", hidden : toSigmaBoolean("${user.preference.hideColumn.column01}")},
    	{id: 'column02', header: "Column 02", align:"left", width : "${user.preference.widthColumn.column02}", hidden : toSigmaBoolean("${user.preference.hideColumn.column02}")},
    	{id: 'column03', header: "Column 03", align:"left", width : "${user.preference.widthColumn.column03}", hidden : toSigmaBoolean("${user.preference.hideColumn.column03}")},
    	{id: 'column04', header: "Column 04", align:"left", width : "${user.preference.widthColumn.column04}", hidden : toSigmaBoolean("${user.preference.hideColumn.column04}")},
    	{id: 'column06', header: "Column 06", align:"left", width : "${user.preference.widthColumn.column06}", hidden : toSigmaBoolean("${user.preference.hideColumn.column06}")},
		{id: 'column07', header: "Column 07", align:"left", width : "${user.preference.widthColumn.column07}", hidden : toSigmaBoolean("${user.preference.hideColumn.column07}")},
		{id: 'column08', header: "Column 08", align:"left", width : "${user.preference.widthColumn.column08}", hidden : toSigmaBoolean("${user.preference.hideColumn.column08}")},
		{id: 'column09', header: "Column 09", align:"left", width : "${user.preference.widthColumn.column09}", hidden : toSigmaBoolean("${user.preference.hideColumn.column09}")},
    	{id:'skuname', header:"Skuname", align:"left", width : "${user.preference.widthColumn.skuName}", hidden : toSigmaBoolean("${user.preference.hideColumn.skuName}")},
    	{id:'home', header:"Home", align:"left", width : "${user.preference.widthColumn.areaProduction}", hidden : toSigmaBoolean("${user.preference.hideColumn.areaProduction}")},
    	{id:'grade', header:"Grade", align:"left", width : "${user.preference.widthColumn.clazz1}", hidden : toSigmaBoolean("${user.preference.hideColumn.clazz1}")},
    	{id:'clazzname', header:"Classname", align:"left", width: "${user.preference.widthColumn.clazz2}", hidden : toSigmaBoolean("${user.preference.hideColumn.clazz2}")},
  	<c:if test="${user.role.roleId eq 4}"> <%-- buyer admin --%>
  		{id:'price2', header:"Price2", align:"right", width : "${user.preference.widthColumn.price2}", hidden : toSigmaBoolean("${user.preference.hideColumn.price2}")},
	</c:if>
    	{id:'pricewotax', header:"Price W/O Tax", align:"right", width : "${user.preference.widthColumn.priceWOTax}", hidden : toSigmaBoolean("${user.preference.hideColumn.priceWOTax}"), renderer : zeroRenderer},
    	{id:'pricewtax', header:"Price W/ Tax", align:"right", width : "${user.preference.widthColumn.priceWTax}", renderer : pricewtaxRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.priceWTax}")},
    	
    	{id: 'B_purchasePrice' , header: "Piece Price" , align:"right" , width : "${user.preference.widthColumn.purchasePrice}" 
			    <c:choose>
			    	<c:when test="${user.role.roleId eq 4}"> <%-- buyer admin --%>
			        	, editable : true
			        </c:when>
			        <c:otherwise>
			        	, editable : false
			    	</c:otherwise>
			    </c:choose>
        	, editor : {type: "text", maxLength: 9, validRule : ['N'], validator : numberValidator}, renderer : buyerPriceRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.purchasePrice}")},

       	{id: 'column10', header: "Column 10", align:"left", width : "${user.preference.widthColumn.column10}", hidden : toSigmaBoolean("${user.preference.hideColumn.column10}")},
   		{id: 'column05', header: "Column 05", align:"left", width : "${user.preference.widthColumn.column05}", hidden : toSigmaBoolean("${user.preference.hideColumn.column05}")},
   		{id: 'column11', header: "Column 11", align:"left", width : "${user.preference.widthColumn.column11}", hidden : toSigmaBoolean("${user.preference.hideColumn.column11}")},
    	{id: 'B_sellingPrice' , header: "Selling Price" , align:"right" , width : "${user.preference.widthColumn.sellingPrice}"
        	    <c:choose>
		        	<c:when test="${user.role.roleId eq 4}"> <%-- buyer admin --%>
		            	, editable : true
		            </c:when>
		            <c:otherwise>
		            	, editable : false
		        	</c:otherwise>
		        </c:choose>
            , editor : {type: "text", maxLength: 9, validRule : ['N'], validator : numberValidator}, renderer : buyerPriceRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.sellingPrice}"), afterEdit : autoCalculateFromSellPrice},
            
    	{id: 'B_sellingUom' , header: "Selling UOM" , align:"left" , width : "${user.preference.widthColumn.sellingUom}"
        	    <c:choose>
		        	<c:when test="${user.role.roleId eq 4}"> <%-- buyer admin --%>
		            	, editable : true
		            </c:when>
		            <c:otherwise>
		            	, editable : false
		        	</c:otherwise>
		        </c:choose>
            , editor : {type: "select" ,options : ${sellingUomList} ,defaultText : '0' }, renderer : sellingUomRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.sellingUom}")},

        {id: 'B_profitPercentage' , header: "Profit Percentage" , align:"right" , editable : false, width : "${user.preference.widthColumn.profitPercentage}", renderer : percentRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.profitPercentage}")},
    	{id:'packageqty', header:"Quantity", align:"right", width: "${user.preference.widthColumn.packageQty}", hidden : toSigmaBoolean("${user.preference.hideColumn.packageQty}"), afterEdit : autoCalculateFromPckQty},
    	{id:'packagetype', header:"Type", align:"left", width: "${user.preference.widthColumn.packageType}", hidden : toSigmaBoolean("${user.preference.hideColumn.packageType}")},
    	{id:'unitorder', header:"Unit Order", align:"left", width: "${user.preference.widthColumn.uom}", renderer : unitorderRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.uom}")},
<c:choose>
  <c:when test="${allDatesView eq false}">
  	<c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
		{id:'Q_${user.userId}', header:"${user.userName}" ,align:"right", width:35, editable : false, editor:{type:"text", maxLength:13, validator:quantity_validator}, renderer : quantityRenderer, afterEdit : autoCalculateFromQuantity},
	  </c:forEach>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <c:forEach var="dateKey" items="${dateList}" varStatus="status">
 	  <c:set var="dateCol" value="${dateMap[dateKey]}" />
 		//{id:'Q_${dateKey}', header: "${dateCol}" , align:"right", width:35, renderer:render_quantity},
 		{id:'Q_${dateKey}', header:"${dateCol}", align:"right", width:35, editable : false, editor:{type:"text", maxLength:13, validator:quantity_validator}, renderer : quantityRenderer, afterEdit : autoCalculateFromQuantity},
    </c:forEach>
  </c:otherwise>
</c:choose>
		{id: 'B_skuComment' , header: "SKU Comments" , align:"left" , width : "${user.preference.widthColumn.skuComment}",  toolTip : true , toolTipWidth : 100
	    	    <c:choose>
			    	<c:when test="${user.role.roleId eq 4}"> <%-- buyer admin --%>
			        	, editable : true
			        </c:when>
			        <c:otherwise>
			        	, editable : false
			    	</c:otherwise>
			    </c:choose>
			 , editor : {type:"textarea",width:"100px",height:"75px", validator : comment_validator}, renderer : commentRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.skuComment}")},
		
<c:choose>
  <c:when test="${allDatesView eq false}">
  	{id: 'column12', header: "Column 12", align:"left", width : "${user.preference.widthColumn.column12}", hidden : toSigmaBoolean("${user.preference.hideColumn.column12}")},
	{id: 'column13', header: "Column 13", align:"left", width : "${user.preference.widthColumn.column13}", hidden : toSigmaBoolean("${user.preference.hideColumn.column13}")},
	{id: 'column14', header: "Column 14", align:"left", width : "${user.preference.widthColumn.column14}", hidden : toSigmaBoolean("${user.preference.hideColumn.column14}")},
	{id: 'column15', header: "Column 15", align:"left", width : "${user.preference.widthColumn.column15}", hidden : toSigmaBoolean("${user.preference.hideColumn.column15}")},
	{id: 'column16', header: "Column 16", align:"left", width : "${user.preference.widthColumn.column16}", hidden : toSigmaBoolean("${user.preference.hideColumn.column16}")},
	{id: 'column17', header: "Column 17", align:"left", width : "${user.preference.widthColumn.column17}", hidden : toSigmaBoolean("${user.preference.hideColumn.column17}")},
	{id: 'column18', header: "Column 18", align:"left", width : "${user.preference.widthColumn.column18}", hidden : toSigmaBoolean("${user.preference.hideColumn.column18}")},
	{id: 'column19', header: "Column 19", align:"left", width : "${user.preference.widthColumn.column19}", hidden : toSigmaBoolean("${user.preference.hideColumn.column19}")},
	{id: 'column20', header: "Column 20", align:"left", width : "${user.preference.widthColumn.column20}", hidden : toSigmaBoolean("${user.preference.hideColumn.column20}")},
	{id:'rowqty', header:"Row Qty", align:"right", width : "${user.preference.widthColumn.rowQty}",renderer:rowqtyRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.rowQty}")},
  </c:when>
  <c:otherwise>
  	{id:'rowqty', header:"Row Qty", align:"right", width : "${user.preference.widthColumn.rowQty}",renderer:rowqtyRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.rowQty}")},
  	{id: 'column12', header: "Column 12", align:"left", width : "${user.preference.widthColumn.column12}", hidden : toSigmaBoolean("${user.preference.hideColumn.column12}")},
	{id: 'column13', header: "Column 13", align:"left", width : "${user.preference.widthColumn.column13}", hidden : toSigmaBoolean("${user.preference.hideColumn.column13}")},
	{id: 'column14', header: "Column 14", align:"left", width : "${user.preference.widthColumn.column14}", hidden : toSigmaBoolean("${user.preference.hideColumn.column14}")},
	{id: 'column15', header: "Column 15", align:"left", width : "${user.preference.widthColumn.column15}", hidden : toSigmaBoolean("${user.preference.hideColumn.column15}")},
	{id: 'column16', header: "Column 16", align:"left", width : "${user.preference.widthColumn.column16}", hidden : toSigmaBoolean("${user.preference.hideColumn.column16}")},
	{id: 'column17', header: "Column 17", align:"left", width : "${user.preference.widthColumn.column17}", hidden : toSigmaBoolean("${user.preference.hideColumn.column17}")},
	{id: 'column18', header: "Column 18", align:"left", width : "${user.preference.widthColumn.column18}", hidden : toSigmaBoolean("${user.preference.hideColumn.column18}")},
	{id: 'column19', header: "Column 19", align:"left", width : "${user.preference.widthColumn.column19}", hidden : toSigmaBoolean("${user.preference.hideColumn.column19}")},
	{id: 'column20', header: "Column 20", align:"left", width : "${user.preference.widthColumn.column20}", hidden : toSigmaBoolean("${user.preference.hideColumn.column20}")},
  </c:otherwise>
</c:choose>
    	{id:'lockflag', header:"lockflag", align:"center", width:300, hidden:true},
		{id: 'profitInfo', header:"Profit Info", width : 200, editable : false, hidden : true}
    ];

	//dynamic height for sigma grid based on browser's height
    var sigmaHeight = window.parent.g_clientHeight - 195;
    if(sigmaHeight < 388) sigmaHeight = 388;

	var gridOption={
		id : grid_demo_id,
		loadURL : '<c:url value="/buyerreceived/loadOrder.json" />',
		saveURL : '<c:url value="/buyerreceived/saveOrder.json" />',
		width: "99.5%",  //"100%", // 700,
		height: sigmaHeight + "px", //"359px",  //"100%", // 330,
		container : 'gridbox', 
		replaceContainer : true, 
		customHead : 'myHead1',
		dataset : _dataset ,
		columns : _columns,
		pageSize : 120,
		language : 'ja',
		pageSizeList : [40, 80, 120, 160, 200],
		toolbarContent : 'nav | goto | pagesize state',
		encoding : 'UTF-8',
		allowCustomSkin : true,
		allowFreeze : false,
		allowGroup : true,
		allowHide : true,
		// ENHANCEMENT 20120724: Lele - Redmine 851
		autoSelectFirstRow : false,
		// ENHANCEMENT 20120724: Lele - Redmine 420
		recountAfterSave : false,
		sellingPrice : toProfitVisibility("${user.preference.profitPreference.totalSellingPrice}"),
		profit : toProfitVisibility("${user.preference.profitPreference.totalProfit}"),
		profitPercentage : toProfitVisibility("${user.preference.profitPreference.totalProfitPercent}"),
		skin : "default",
		customRowAttribute : function(record,rn,grid){
			var lockflagObj = _.unserialize(record.lockflag);
			if (lockflagObj.sku == '1') return 'style="background-color:#eee9e9"';
			else return 'style="height:30px;"';
							 },
							 
		loadResponseHandler : function(response, requestParameter) {
									
			var obj = _.unserialize(response.text);
			
			// Get Totals
			mygrid.setTotals(obj.totals);
			mygrid.setProfitVisibility(obj.profitVisibility);
			
			$("#priceInfo").html(
					window.
					parent.
					receivedSheet.
					computeForPrices(obj.profitVisibility, obj.totals));

			// Get Grand Totals
			mygrid.setGrandTotals(obj.grandTotals);

			window.parent.receivedSheet.enableAfterSigmaLoad();
			<c:if test="${user.role.roleId eq 3}"> //buyer
				window.parent.receivedSheet.chkboxSelectAllDates.setVisibility("visible");
			</c:if>

			window.parent.receivedSheet.disableActionButtons();
			
			var flags = _.unserialize(obj.buttonFlags);
			var lockButtonStatus = _.unserialize(obj.lockButtonStatus);
			if(lockButtonStatus == 1){
				var lockStatus = true;
			} else {
				var lockStatus = false;
			}
			
			if (flags != null) {
				if (flags.lockButtonEnabled)
					window.parent.receivedSheet.btnLock.setDisabled(lockStatus);
				
				if (flags.unlockButtonEnabled)
					window.parent.receivedSheet.btnUnlock.setDisabled(lockStatus);
			}
			
			var confirmMsg = obj.confirmMsg;
			if (confirmMsg != null && confirmMsg != "") {
				alert(confirmMsg);
				window.parent.receivedSheet.btnSave.setDisabled(true);
			} else {
				window.parent.receivedSheet.btnSave.setDisabled(false);
			}
		},
		
		saveResponseHandler : function(response, requestParameter) {
			var result = validateResponseOnIframe(response.text);
			if (result) {
				alert(mygrid.getMsg('SAVED'));
			}
		},

		beforeEdit : function(value,  record,  col,  grid) {
			var rowNo = (grid.activeRow).rowIndex;
			var lockflagObj = _.unserialize(mygrid.getColumnValue("lockflag", rowNo));

			if (1==0) {}
<c:choose>
  <c:when test="${allDatesView eq false}">
    <c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
	  		else if (col.id == 'Q_${user.userId}') {
		  		if (lockflagObj.Q_${user.userId} == '1') return false;
	  		}
	  </c:forEach>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <c:forEach var="dateKey" items="${dateList}" varStatus="status">
	  <c:set var="dateCol" value="${dateMap[dateKey]}" />
	  		else if (col.id == 'Q_${dateKey}') {
		  		if (lockflagObj.Q_${dateKey} == '1') return false;
	  		}
    </c:forEach>
  </c:otherwise>
</c:choose>
			else return true;
		},

		onKeyDown : function(event){
		    if(!this.editing){
	         return true; //let 
	      }
	      var me = this;
		    function editCell(_cell){
						if (_cell){
							if(me.endEdit()!==false){
							    Sigma.Grid.handleOverRowCore(event,me,_cell.parentNode);
							    me.initActiveObj_startEdit(event,_cell);	
							}
						}
				}

		 	// ENHANCEMENT 20120724: Lele - Redmine 851
		    mygrid.selectRow(mygrid.activeRow, false);
		    
				var kcode=event.keyCode, oldCell = this.activeCell, newCell = null, processed = false;
				switch(kcode){
	        case Sigma.Const.Key.LEFT:
	          newCell= this._prveEditableCell(oldCell);
	          processed = true;
	          break;
	        case Sigma.Const.Key.RIGHT:
	          newCell= this._nextEditableCell(oldCell);
	          processed = true;
	          break;
	        case Sigma.Const.Key.UP:
	          var newRow= Sigma.U.prevElement(oldCell.parentNode);
	          if (newRow){
	  	          newCell=newRow.cells[Sigma.U.getCellIndex(oldCell)];
	          }
	          processed = true;
	          break;
	        case Sigma.Const.Key.DOWN:
	        // ENHANCEMENT 20120724: Lele - Redmine 851
	        case Sigma.Const.Key.ENTER:
	          var newRow= Sigma.U.nextElement(oldCell.parentNode);
	          if (newRow){
	  	          newCell=newRow.cells[Sigma.U.getCellIndex(oldCell)];
	          }
	          processed = true;
	          break;
	      }
	      if(newCell) {
	        editCell(newCell);
	      } else {
	          if(processed){
	              //we need to stop event
	              Sigma.U.stopEvent(event);
	          }
	      } 

	   	  // ENHANCEMENT 20120724: Lele - Redmine 851
		  mygrid.selectRow(mygrid.activeRow, true);
		    
	      return !processed;	
	   },
	   onInvalidInput : function(value,oldValue,activeRecord, cell, activeColumn, validResult, grid){
			alert(validResult);
			return false;
		},
		afterColumnResize : function (colObj, newWidth, grid) {
			var cw = new Object();
			cw.columnId = colObj.id;
			cw.width = newWidth;
			mygrid.showWaiting();
			$.ajax({
				url: "/eON/userPref/saveColumnWidth.json",
				data: cw,
				dataType: 'json',
				async : false,
				success: function(response) {
				},
	            complete:function(XMLHttpRequest, textStatus){
	            	mygrid.hideWaiting();
				}
			});
	  }
	};

	var mygrid = new Sigma.Grid(gridOption);
	Sigma.Util.onLoad(Sigma.Grid.render(mygrid));
	
	function showDialogHideColumns() {
		dlg = Sigma.ad2("show",{gridId:"myGrid1",checkValid:function($){
				return $.frozen;
		},checkFn:"show",uncheckFn:"hide",canCheck:function($){
	        return !$.hidden;
		}});
		dlg.show();
		dlg.setTitle("Hide Columns");
	}

	function my_renderer(value, record, columnObj, grid, colNo, rowNo){
		var name = record[columnObj.fieldIndex];
		if (columnObj.id == "rowqty") {
			return "<div class='higlightedRowQty'>" + name + "</div>";
		}
		else if (columnObj.id == "compa1") {
			return "<div class='finalize-status'>" + name + "</div>";
		}
		else if (columnObj.id == "compa2") {
			return "<div class='publish-status'>" + name + "</div>";
		}
		else if (columnObj.id == "compa3") {
			return "<div class='unpublish-status'>" + name + "</div>";
		}
		else {
			return name;
		}
	}

	function rowqtyRenderer(value, record, columnObj, grid, colNo, rowNo){
		var name = record[columnObj.fieldIndex];

		if (isNaN(name))
			name = 0;
		
		if (columnObj.id == "rowqty") {
			return "<div class='higlightedRowQty'>" + addCommas(name) + "</div>";
		}
	}

	function quantity_validator(value, record, colObj, grid) {
		value = value.trim();
		//var toProcess = (!isNaN(value) && value>=0 && value<=999999999.999);
		var toProcess = (!isNaN(value) && value<=999999999.999);
		var isValidDecimalInput = validateQuantityScale(record["unitorder"],value);
		if (value == '' || (toProcess == true && isValidDecimalInput ==true)) {
			return true;
		}
		else {//invalid
			return grid.getMsg('INVALID_INPUT');
		}
	}

	function comment_validator(value, record, colObj, grid) {
		value = value + "";
		value = value.trim();
		if (value.length > 50){
			return grid.getMsg('INVALID_INPUT');
		}
		
		return true;

	}

	function commentRenderer (value , record, columnObj, grid, colNo, rowNo) {
		value = value + "";
		var newValue = value.trim();
		if (newValue.length != value.length) {
			grid.update(record, columnObj.id, newValue);
		}
		return "<div title='" + newValue + "'>" + newValue + "<div>";
	}

	function render_quantity(value, record, columnObj, grid, colNo, rowNo) {
		var render;
		var disabled = "";
		var allRows = mygrid.getInsertedRecords();
		var color = 'transparent';
		if (allRows.length > 0) {
			disabled = "disabled";
			color = '#FAAFBA';
		}

		var arr = columnObj.id.split("_");
		var buyerId = arr[1];
		var lockColId = "L_" + buyerId;
		var lockFlag = mygrid.getColumnValue(lockColId, rowNo);
		
		if (value == -999) {
			render = "<span id='span-" + columnObj.id + "-" + rowNo + "' class='gt-editor-q' style='background-color:#CFECEC;'></span>";
		}
		else if (lockFlag == "1") {
			render = "<span id='span-" + columnObj.id + "-" + rowNo + "' class='gt-editor-q'>" + value + "</span>";
		}
		else {
			render = "<input type='text' style='background-color: " + color + ";color:black;' maxLength='10' onmouseover=\"this.style.cursor='arrow'\" class='gt-editor-q' id='tBox-" + columnObj.id + "-" + rowNo + "' value='"+value+"' " + disabled + " onfocus='focusMe(this)' onblur='blurMe(this)' onkeyup='enterPress(this, event)'/>";
		}
		
		return render;
	}

	function quantityRenderer (value , record, columnObj, grid, colNo, rowNo) {
		return addCommas(value);
	}

	function render_checkbox(value , record, columnObj, grid, colNo, rowNo){
		var allRows = mygrid.getInsertedRecords();
		disabled = "";
		if (allRows.length > 0) disabled = "disabled";
		checked = "";
		if (value == "1")
			checked = "checked";

		var arr = columnObj.id.split("_");
		var buyerId = arr[1];
		var lockColId = "L_" + buyerId;
		var lockFlag = mygrid.getColumnValue(lockColId, rowNo);

		if (lockFlag == "1") disabled = "disabled";
		
		return "<input type='checkbox' id='cBox-" + columnObj.id + "-" + rowNo + "' " + checked + " " + disabled + " onclick=cBoxMethod(this);></input>";		
	}

	function cBoxMethod(cBoxObj) {
		var cBoxId = cBoxObj.id;
		var arr = cBoxId.split("-");
		var colId = arr[1];
		var rowNo = arr[2];
		var apprvFlag = 0;
		
		if (cBoxObj.checked) {
			apprvFlag = 1;
		}
		else {
			apprvFlag = 0;
		}
		
		mygrid.setColumnValue(colId, rowNo, apprvFlag);
	}
		
	function onSave() {
		mygrid.addParameters({action:"save", option : _columns});
		mygrid.save();
	}
	
	function onLock() {
		
		var msg = mygrid.getMsg('CONFIRM_APPROVE');
		var isLocked = confirm(msg);

		if(isLocked == false){
			return false;
		}
		
		mygrid.showWaiting();
		linb.Ajax(
			"/eON/buyerreceived/lockUnlock.json?lock=Y",
            null,
            function (response) {
				validateSigmaResponse(response);
				
				mygrid.reload(false,true);
				var obj = _.unserialize(response);
				var concurrencyOrders = obj.concurrencyMsg;
				if (obj.concurrencyMsg != null && obj.concurrencyMsg.trim() != ""){
					//window.parent.receivedSheet.alertConcurrencyMsg(obj.concurrencyMsg);
				}
				else
				{
					alert(mygrid.getMsg('APPROVED'));
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
	}

	function onUnlock() {
		
		var msg = mygrid.getMsg('CONFIRM_UNAPPROVE');
		var isLocked = confirm(msg);

		if(isLocked == false){
			return false;
		}
		
		mygrid.showWaiting();
		linb.Ajax(
			"/eON/buyerreceived/lockUnlock.json?lock=N",
            null,
            function (response) {
				validateSigmaResponse(response);
				alert(mygrid.getMsg('UNAPPROVED'));
				mygrid.reload(false,true);
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
	}
	
	function addRow() {
		mygrid.add();
	}

	function validateResponseOnIframe(jsonResponse) {
		var obj = _.unserialize(jsonResponse);
		var error = obj.globals;
		if (obj.fail == 'true') {
			alert(error[0]);
			return false;
		}
		return true;
	}

	var g_storedBGColor;

	function focusMe(obj) {
		g_storedBGColor = obj.style.backgroundColor;
		obj.style.textAlign = 'left';
		obj.value = obj.value;
		obj.style.border = '1px solid #558899';
		obj.style.backgroundColor = 'white';
		obj.style.font = 'normal 11px arial, verdana, tahoma, helvetica, sans-serif';
		obj.select();
	}

	function blurMe(obj) {
		obj.style.textAlign = 'right';
		obj.style.border = '0px';
		if (g_storedBGColor == 'orange') //stay as orange if oranged
			obj.style.backgroundColor = 'orange';
		else
			obj.style.backgroundColor = 'transparent';
		obj.style.font = 'normal 12px arial, verdana, tahoma, helvetica, sans-serif';

		var allRows = mygrid.getAllRows();
		var id = obj.id;
		var arr = id.split("-");
		var colId = arr[1];
		var rowNo = arr[2];
		var value = obj.value;
		var origValue = mygrid.getColumnValue(colId, rowNo);

		if (value == '' || (!isNaN(value) && value>0 && value<=999999.999)) {
			if (value != origValue) {
				obj.style.backgroundColor = 'orange';
				obj.style.backgroundRepeat  = 'no-repeat';
				obj.style.backgroundPosition = 'right top';
				obj.style.backgroundImage = 'url(../../sigmagrid/lib/grid/skin/default/images/cell_updated.gif)';
			}
			//ok
		}
		else {
			alert(mygrid.getMsg('INVALID_NUMBER'));
			value = origValue;
			obj.value = origValue;
		}

		mygrid.setColumnValue(colId, rowNo, value);
		
	}

	function enterPress(obj, e) {
		var KeyID = (window.event) ? event.keyCode : e.keyCode;
		if (KeyID==13 || KeyID==9 || KeyID==27 ) {
			obj.blur();
		}
	}

	function submitTempForm(param) {
		var sheetTypeId = 10004;
		<c:if test="${user.role.roleId eq 4}">
			sheetTypeId = 10012;
		</c:if>
		var paramObj = _.unserialize(param);
		paramObj.sheetTypeId = sheetTypeId;

		var param = _.serialize(paramObj);
		var theForm = document.tempForm;
		theForm.action = "/eON/downloadExcel.json?_dxls_json=" + param;
		theForm.submit();
	}

	function isDirty () {
		return mygrid.isDirty();
	}

	function pricewtaxRenderer (value , record, columnObj, grid, colNo, rowNo) {
		var tmp = window.parent.receivedSheet.roundOffPriceWTax(value);
		if (value ==  "undefined") return "";
		if (tmp > 0) return addCommas(tmp);
		else return "";
	}

	function autoCalculateFromQuantity (value, oldValue, record, row, col, grid) {
		if (value == oldValue)
			return;
	}

	function autoCalculateFromDate (value, oldValue, record, row, col, grid) {
		if (value == oldValue)
			return;
	}

	function autoCalculateFromPrice (value, oldValue, record, row, col, grid) {
		if (value == oldValue)
			return;
	}

	function updateTotalRowQty () {
		
	}

	function unitorderRenderer (value , record, columnObj, grid, colNo, rowNo) {
		var tmp = "${unitorderRenderer}";
		var map = _.unserialize(tmp);
		for(var i=0;i<map["unitorderRenderer"].length;i++) {
			if(value == map["unitorderRenderer"][i]["id"]) {
				return map["unitorderRenderer"][i]["caption"];
			}
		}
		return " ";
	}
	
	function sellingUomRenderer (value , record, columnObj, grid, colNo, rowNo) {
		var tmp = "${sellingUomRenderer}";
		var map = _.unserialize(tmp);
		for(var i=0;i<map["sellingUomRenderer"].length;i++) {
			if(value == map["sellingUomRenderer"][i]["id"]) {
				return map["sellingUomRenderer"][i]["caption"];
			}
		}
		return " ";
	}
	
 	function negativeValidator (value) {
		if (isNaN(value) || parseFloat(value) < 0) {
			return false;
		}
		return true;
 	}
	  	  
	function numberValidator(value, record, colObj, grid){
	 if(negativeValidator(value) == false){
	  return grid.getMsg('INVALID_INPUT');
	 }
	 if(scale(value) == false){
	  return grid.getMsg('INVALID_INPUT');
	 }
	
	 return true;
	}


	function zeroRenderer (value , record, columnObj, grid, colNo, rowNo) {
		var tmp = parseFloat(value);
		if (value ==  "undefined") return "";
		if (tmp > 0) return addCommas(value);
		else return "";
	}
	
	function buyerPriceRenderer (value , record, columnObj, grid, colNo, rowNo) {
		var tmp = parseFloat(value);
		if (value ==  "undefined") return "";
		if (tmp >= 0) return addCommas(value);
		else return "";
	}
	
	function percentRenderer (value , record, columnObj, grid, colNo, rowNo) {
		if (value == null || value ==  "undefined") {
			return "";
		}else{
			return toPercentage(value);
		}
	}
	
	function groupnameRenderer (value , record, columnObj, grid, colNo, rowNo) {
		var render = "--商品グループ--";
		var user = "S_" + record.sellerId;
		if (user != 'S_undefined') {
			var tmp = "${skuGroupRenderer}";
			var map = _.unserialize(tmp);
			
			for(var i=0;i<map[user].length;i++) {
				if (map[user][i] != null) {
					if(value == map[user][i]["id"]) {
						render = map[user][i]["caption"];
						break;
					}
					else {
						render = value;
					}
				} else {
					render = value;
				}
			}
		}
		return render;
	}

	function scale(value){
	  if(String(value).indexOf(".") > 0){
      	return false;
	  }
	  return true;
  	}

	function autoCalculateFromQuantity (value, oldValue, record, row, col, grid) {
		if (value == oldValue)
			return;

		//autoCalculatePrice(value, oldValue, record, row, col, grid);
		//renderTotals(grid);
	}

	function autoCalculateFromPrice (value, oldValue, record, row, col, grid) {
		if (value == oldValue)
			return;

		grid.setCellValue("pricewtax", record, calculatePriceWithTax(value,'ROUND'));
		//autoCalculatePrice(value, oldValue, record, row, col, grid);
		//renderTotals(grid);
	}

	function autoCalculateFromPckQty (value, oldValue, record, row, col, grid) {
		if (value == oldValue)
			return;

		grid.setCellValue("packageqty", value);
		
		//autoCalculatePrice(value, oldValue, record, row, col, grid);
		//renderTotals(grid);
	}

	function autoCalculateFromSellPrice (value, oldValue, record, row, col, grid) {
		if (value == oldValue)
			return;

		//grid.setCellValue("B_sellingPrice", value);
		
		//autoCalculatePrice(value, oldValue, record, row, col, grid);
		//renderTotals(grid);autoCalculateFromSellPrice
	}
	
	function renderTotals(grid) {

		var curProfitInfoT = grid.getTotals();

		$("#priceInfo").html(
				window.
				parent.
				orderSheet.
				computeForPrices(curProfitInfoT.priceWithoutTax, curProfitInfoT.priceWithTax));
	}
	
</script>
</head>
<body>

<div id="DV" style="background-color:#99CCFF;">
	<table id="TB" border=0 width="96%">
		<tr>
			<td align="right">
				<p id="priceInfo">
				</p>
			</td>
			<td>
			</td>
		</tr>
	</table>
</div>

<table id="myHead1" style="display: none">
	<tr style="background-color: #99CCFF; text-align: center;">
		<td rowspan="2" columnId='skuId'></td>
		<td rowspan="2" columnId='skuBaId'></td>
<c:choose>
  <c:when test="${allDatesView eq false}">
    <c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
        <td rowspan="2" columnId='L_${user.userId}' align="center">${user.verticalName}</td>
	  </c:forEach>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <c:forEach var="dateKey" items="${dateList}" varStatus="status">
	  <c:set var="dateCol" value="${dateMap[dateKey]}" />
		<td rowspan="2" columnId='L_${dateKey}' align="center">${dateCol}</td>
    </c:forEach>
  </c:otherwise>
</c:choose>
<c:if test="${showA eq true}">
		<td rowspan="2" columnId='A_${oneBuyerId}'>検<br/>品</td>
</c:if>
		<td rowspan="2" columnId='sellerId'></td>
		<td rowspan="2" columnId='companyname'><c:out value="${eONHeader.skuColumnMap.companyname.headerSheet}" /></td>
		<td rowspan="2" columnId='sellername'><span class="requiredColumn">＊</span><c:out value="${eONHeader.skuColumnMap.sellername.headerSheet}" /></td>
		<td rowspan="2" columnId='groupname'><span class="requiredColumn">＊</span><c:out value="${eONHeader.skuColumnMap.groupname.headerSheet}" /></td>
		<td rowspan="2" columnId='marketname'><c:out value="${eONHeader.skuColumnMap.marketcondition.headerSheet}" /></td>
		<td rowspan="2" columnId='column01'><c:out value="${eONHeader.skuColumnMap.column01.headerSheet}" /></td>
		<td rowspan="2" columnId='column02'><c:out value="${eONHeader.skuColumnMap.column02.headerSheet}" /></td>
		<td rowspan="2" columnId='column03'><c:out value="${eONHeader.skuColumnMap.column03.headerSheet}" /></td>
		<td rowspan="2" columnId='column04'><c:out value="${eONHeader.skuColumnMap.column04.headerSheet}" /></td>
		<td rowspan="2" columnId='column06'><c:out value="${eONHeader.skuColumnMap.column06.headerSheet}" /></td>
		<td rowspan="2" columnId='column07'><c:out value="${eONHeader.skuColumnMap.column07.headerSheet}" /></td>
		<td rowspan="2" columnId='column08'><c:out value="${eONHeader.skuColumnMap.column08.headerSheet}" /></td>
		<td rowspan="2" columnId='column09'><c:out value="${eONHeader.skuColumnMap.column09.headerSheet}" /></td>
		<td rowspan="2" columnId='skuname'><span class="requiredColumn">＊</span><c:out value="${eONHeader.skuColumnMap.skuname.headerSheet}" /></td>
		<td rowspan="2" columnId='home'><c:out value="${eONHeader.skuColumnMap.areaofproduction.headerSheet}" /></td>
		<td rowspan="2" columnId='grade'><c:out value="${eONHeader.skuColumnMap.class1.headerSheet}" /></td>
		<td rowspan="2" columnId='clazzname'><c:out value="${eONHeader.skuColumnMap.class2.headerSheet}" /></td>
	<c:if test="${user.role.roleId eq 4}">
  		<td rowspan="2" columnId='price2'>価格2</td>
	</c:if>
		<td rowspan="2" columnId='pricewotax'><c:out value="${eONHeader.skuColumnMap.pricewotax.headerSheet}" /></td>
		<td rowspan="2" columnId='pricewtax'><c:out value="${eONHeader.skuColumnMap.pricewtax.headerSheet}" /></td>
		<td rowspan="2" columnId='B_purchasePrice'><c:out value="${eONHeader.skuColumnMap.B_purchasePrice.headerSheet}" /></td>
		<td rowspan="2" columnId='column10'><c:out value="${eONHeader.skuColumnMap.column10.headerSheet}" /></td>
		<td rowspan="2" columnId='column05'><c:out value="${eONHeader.skuColumnMap.column05.headerSheet}" /></td>
		<td rowspan="2" columnId='column11'><c:out value="${eONHeader.skuColumnMap.column11.headerSheet}" /></td>
		<td rowspan="2" columnId='B_sellingPrice'><c:out value="${eONHeader.skuColumnMap.B_sellingPrice.headerSheet}" /></td>
		<td rowspan="2" columnId='B_sellingUom'><c:out value="${eONHeader.skuColumnMap.B_sellingUom.headerSheet}" /></td>
		<td rowspan="2" columnId='B_profitPercentage'><c:out value="${eONHeader.skuColumnMap.B_profitPercentage.headerSheet}" /></td>
		<td rowspan="2" columnId='packageqty'><span class="requiredColumn">＊</span><c:out value="${eONHeader.skuColumnMap.packageqty.headerSheet}" /></td>
		<td rowspan="2" columnId='packagetype'><c:out value="${eONHeader.skuColumnMap.packagetype.headerSheet}" /></td>
		<td rowspan="2" columnId='unitorder'><c:out value="${eONHeader.skuColumnMap.uom.headerSheet}" /></td>
<c:choose>
  <c:when test="${allDatesView eq false}">
    <c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:set var="userList" value="${companyMap[company.companyId]}" />
		<td colspan="${fn:length(userList)}" align="center">${company.verticalName}</td>
    </c:forEach>
  </c:when>
  <c:otherwise>
		<td colspan="${fn:length(dateList)}" align="center">${buyerMap[oneBuyerId].verticalName}</td>
  </c:otherwise>
</c:choose>
<td rowspan="2" columnId='B_skuComment'><c:out value="${eONHeader.skuColumnMap.B_skuComment.headerSheet}" /></td>
<c:choose>
	<c:when test="${allDatesView eq false}">
 		<td rowspan="2" columnId='column12'><c:out value="${eONHeader.skuColumnMap.column12.headerSheet}" /></td>
		<td rowspan="2" columnId='column13'><c:out value="${eONHeader.skuColumnMap.column13.headerSheet}" /></td>
		<td rowspan="2" columnId='column14'><c:out value="${eONHeader.skuColumnMap.column14.headerSheet}" /></td>
		<td rowspan="2" columnId='column15'><c:out value="${eONHeader.skuColumnMap.column15.headerSheet}" /></td>
		<td rowspan="2" columnId='column16'><c:out value="${eONHeader.skuColumnMap.column16.headerSheet}" /></td>
		<td rowspan="2" columnId='column17'><c:out value="${eONHeader.skuColumnMap.column17.headerSheet}" /></td>
		<td rowspan="2" columnId='column18'><c:out value="${eONHeader.skuColumnMap.column18.headerSheet}" /></td>
		<td rowspan="2" columnId='column19'><c:out value="${eONHeader.skuColumnMap.column19.headerSheet}" /></td>
		<td rowspan="2" columnId='column20'><c:out value="${eONHeader.skuColumnMap.column20.headerSheet}" /></td>
		<td rowspan="2" columnId='rowqty'>総数</td>
	</c:when>
	<c:otherwise>
 		<td rowspan="2" columnId='rowqty'>総数</td>
		<td rowspan="2" columnId='column12'><c:out value="${eONHeader.skuColumnMap.column12.headerSheet}" /></td>
		<td rowspan="2" columnId='column13'><c:out value="${eONHeader.skuColumnMap.column13.headerSheet}" /></td>
		<td rowspan="2" columnId='column14'><c:out value="${eONHeader.skuColumnMap.column14.headerSheet}" /></td>
		<td rowspan="2" columnId='column15'><c:out value="${eONHeader.skuColumnMap.column15.headerSheet}" /></td>
		<td rowspan="2" columnId='column16'><c:out value="${eONHeader.skuColumnMap.column16.headerSheet}" /></td>
		<td rowspan="2" columnId='column17'><c:out value="${eONHeader.skuColumnMap.column17.headerSheet}" /></td>
		<td rowspan="2" columnId='column18'><c:out value="${eONHeader.skuColumnMap.column18.headerSheet}" /></td>
		<td rowspan="2" columnId='column19'><c:out value="${eONHeader.skuColumnMap.column19.headerSheet}" /></td>
		<td rowspan="2" columnId='column20'><c:out value="${eONHeader.skuColumnMap.column20.headerSheet}" /></td>
	</c:otherwise>
</c:choose>
		<td rowspan="2" columnId='lockflag'>lockflag</td>
		<td rowspan="2" columnId='profitInfo'>Profit Info</td>
	</tr>
	<tr style="background-color: #99CCFF">
<c:choose>
  <c:when test="${allDatesView eq false}">
    <c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
        <td id='Q_${user.userId}' align="center">${user.verticalName}</td>
	  </c:forEach>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <c:forEach var="dateKey" items="${dateList}" varStatus="status">
	  <c:set var="dateCol" value="${dateMap[dateKey]}" />
		<td id='Q_${dateKey}' align="center">${dateCol}</td>
    </c:forEach>
  </c:otherwise>
</c:choose>
	</tr>
</table>
<form name="tempForm" method="post">
</form> 
</body>
</html>