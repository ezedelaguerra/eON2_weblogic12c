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
 * 20120802 	Jovs 	v11			Redmine 908 - Reset to zero index the value of group name once the column of seller name change
 * 20120731		Rhoda	v11			Redmine 354 - Unfinalize Order and Finalize Alloc concurrency
 * 20120913		Lele	chrome		Redmine 880 - Chrome compatibility
 * 20120919		Rhoda	v12			Redmine 1070 - Seller can unfinalize the Order Sheet after Seller finalized Allocation Sheet
 * 20121116		Melissa v14			Redmine 1109 - the announcement is different after unpublish
 
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:set var="allDatesView" value="${orderSheetParam.allDatesView}" />
<c:set var="oneBuyerId" value="${oneBuyerId}"/>


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

<%--<script src="highlight/jssc3.js" type="text/javascript"></script>--%>
<script type="text/javascript" src="../../sigmagrid2.4e/grid/gt_msg_en.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../sigmagrid2.4e/grid/gt_msg_ja.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../sigmagrid2.4e/src/gt_const.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../sigmagrid2.4e/grid/gt_grid_all.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../sigmagrid2.4e/grid/flashchart/fusioncharts/FusionCharts.js?version=<c:out value="${initParam.version}"/>"></script>
<%-- ENHANCEMENT 20120913: Lele - Redmine 880 --%>
<script type="text/javascript" src="../../sigmagrid2.4e/grid/patch/gt_ie9.js?version=<c:out value="${initParam.version}"/>"></script>

<link type="text/css" href="../../sigmagrid2.4e/jquery/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="../../sigmagrid2.4e/jquery/js/jquery-1.4.2.min.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../sigmagrid2.4e/jquery/js/jquery-ui-1.8.custom.min.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../../runtime/jsLinb/js/linb-all.js"></script>
<script type="text/javascript" src="../../common/util/StringUtil.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../common/util/util.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../common/util/sigma_util.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../common/Locale/en.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../common/Locale/ja.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../common/util/taxUtil.js?version=<c:out value="${initParam.version}"/>"></script>

<jsp:include page="../../common/util/disable_right_click.jsp" />
<script type="text/javascript">
    linb.setLang("${pageContext.request.locale.language}", function(){});
    var TAX_RATE = <c:out value="${TAX_RATE}" />;
	var visToggled = false;
	var visFlag = false;
	var grid_demo_id = "myGrid1" ;
	var _dataset= {
		fields :[
		    {name : 'skuId', type : 'int'},
		    {name : 'lockflag'},
		    {name : 'sellerId', type : 'int'},
		    {name : 'myselect'},
		    {name : 'companyname'},
			{name : 'sellername'},
			{name : 'groupname'},
			{name : 'marketname'},
			{name : 'column01', type : 'int'},
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
			{name : 'price1', type: 'float' },
			{name : 'price2', type: 'float' },
			{name : 'pricewotax', type: 'float' },
			{name : 'pricewtax', type: 'float' },
			{name : 'column10'},
			{name : 'column05'},
			{name : 'column11'},
			{name : 'packageqty', type: 'float'},
			{name : 'packagetype'},
			{name : 'unitorder'},
<c:choose>
  <c:when test="${allDatesView eq false}">
  			{name : 'visall', type: 'boolean'},
    <c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
        	{name : 'Q_${user.userId}', type: 'float'},
        	{name : 'V_${user.userId}', type: 'boolean'},
	  </c:forEach>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <c:forEach var="dateKey" items="${dateList}" varStatus="status">
            {name : 'Q_${dateKey}', type: 'float'},
    </c:forEach>
  </c:otherwise>
</c:choose>
			{name : 'skumaxlimit', type: 'float'},
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
			{name : 'profitInfo'},
			{name : 'externalSkuId'}
		],
		recordType : 'json'
	}
	
	var _columns = [
		{id: 'skuId', header:"SKU Id", width : 40, editable : false, hidden : true},
		{id: 'lockflag', header:"JSON", width : 200, editable : false, hidden : true},
		{id: 'sellerId', header:"Seller Id", width : 40, editable : false, hidden : true},
    	{id: 'myselect', header:"Select", align:"center", width : 25, renderer : deleteCheckBoxRenderer},
    	{id: 'companyname' , header: "Companyname" , align:"left" , width : "${user.preference.widthColumn.companyName}", editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.companyName}")},

    	{id: 'sellername' , header: "Sellername" , align:"left" , width : "${user.preference.widthColumn.sellerName}", hidden : toSigmaBoolean("${user.preference.hideColumn.sellerName}"),  
         editor : {type: "select", options : ${sellerNameList}}

<c:choose>
	<c:when test="${isAdmin eq true}">
	,afterEdit : function(value, oldValue, record, row, col, grid) {
		/* ENHANCEMENT START 20120802 JOVSAB - Redmine 908 */
		if (value != oldValue)
			record.groupname = "0";
		mygrid.refreshRow(row,  record);
		/* ENHANCEMENT END 20120802 JOVSAB */
        var item = getPoolItem(value);
        if(item){
            updateRow(row, item);
        }
        mygrid.showWaiting();
        $.ajax({
            url: "/eON/order/sellerSelect.json",
            data: "sellerName=" + encodeURIComponent(value),
            dataType: 'json',
            async : false,
            success: function(ret){
        		validateSigmaResponse(ret);
       	 		NOPOOL[value] = ret;
                updateRow(row,ret);
            },
            complete:function(XMLHttpRequest, textStatus){
                mygrid.hideWaiting();
            }
        });
    }
    </c:when>
</c:choose>
			},
		
        {id: 'groupname' , header: "Groupname" , align:"left" , width : "${user.preference.widthColumn.groupName}", hidden : toSigmaBoolean("${user.preference.hideColumn.groupName}"),  
		 editor : {type: "select", 
			validator : function(value,record,colObj,grid) {
				if (value == "0") {
					return grid.getMsg('INVALID_INPUT');
				}
			}
<%-- FORDELETION START 20120913: Lele - Redmine 880			
<c:choose>
	<c:when test="${isAdmin eq true}">
 	 FORDELETION END 20120913: --%>
	        ,onEditorInit: function(value, record, row, column, grid) {
		        var i=0;
	            var dom = this.valueDom;
	            var item = getPoolItem(record.sellername);
	            var options = item ? item["groupname"]:[];
	            //alert("options: " + _.serialize(options));
	            setSelectOptions(dom, options, value);
	        }
<%-- FORDELETION START 20120913: Lele - Redmine 880
	</c:when>
	<c:otherwise>
		, options : ${skuGroupList}
	</c:otherwise>
</c:choose>
	 FORDELETION END 20120913: --%>
			, defaultText : '0'},
			renderer : groupnameRenderer
		 },
    	{id: 'marketname' , header: "Marketname" , align:"left" , width : "${user.preference.widthColumn.marketCondition}", hidden : toSigmaBoolean("${user.preference.hideColumn.marketCondition}"), editor : {type: "text", maxLength: 42}, toolTip : true, toolTipWidth : 100 },
    	{id: 'column01', header: "Column 01", align: "right", width : "${user.preference.widthColumn.column01}", editor : {type: "text", maxLength: 9, validRule : ['N'], validator : numberValidator}, hidden : toSigmaBoolean("${user.preference.hideColumn.column01}")},
    	{id: 'column02', header: "Column 02", align: "left", width : "${user.preference.widthColumn.column02}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column02}")},
    	{id: 'column03', header: "Column 03", align: "left", width : "${user.preference.widthColumn.column03}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column03}")},
    	{id: 'column04', header: "Column 04", align: "left", width : "${user.preference.widthColumn.column04}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column04}")},
    	{id: 'column06', header: "Column 06", align: "left", width : "${user.preference.widthColumn.column06}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column06}")},
		{id: 'column07', header: "Column 07", align: "left", width : "${user.preference.widthColumn.column07}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column07}")},
		{id: 'column08', header: "Column 08", align: "left", width : "${user.preference.widthColumn.column08}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column08}")},
		{id: 'column09', header: "Column 09", align: "left", width : "${user.preference.widthColumn.column09}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column09}")},
    	{id: 'skuname' , header: "Skuname" , align:"left" , width : "${user.preference.widthColumn.skuName}", hidden : toSigmaBoolean("${user.preference.hideColumn.skuName}"), editor : {type: "text", maxLength: 42, validRule : ['R']}},
    	{id: 'home' , header: "Home" , align:"left" , width : "${user.preference.widthColumn.areaProduction}", hidden : toSigmaBoolean("${user.preference.hideColumn.areaProduction}"), editor : {type: "text", maxLength: 42}},
    	{id: 'grade' , header: "Grade" , align:"left" , width : "${user.preference.widthColumn.clazz1}", hidden : toSigmaBoolean("${user.preference.hideColumn.clazz1}"), editor : {type: "text", maxLength: 42}},
    	{id: 'clazzname' , header: "Classname" , align:"left" , width : "${user.preference.widthColumn.clazz2}", hidden : toSigmaBoolean("${user.preference.hideColumn.clazz2}"), editor : {type: "text", maxLength: 42}},
    	{id: 'price1' , header: "Price1" , align:"right" , width : "${user.preference.widthColumn.price1}", hidden : toSigmaBoolean("${user.preference.hideColumn.price1}"), editor : {type: "text", maxLength: 9, validRule : ['N'], validator : numberValidator}, renderer : zeroRenderer},
    	{id: 'price2' , header: "Price2" , align:"right" , width : "${user.preference.widthColumn.price2}", hidden : toSigmaBoolean("${user.preference.hideColumn.price2}"), editor : {type: "text", maxLength: 9, validRule : ['N'], validator : numberValidator}, renderer : zeroRenderer},
    	{id: 'pricewotax' , header: "Price W/O Tax" , align:"right" , width : "${user.preference.widthColumn.priceWOTax}", hidden : toSigmaBoolean("${user.preference.hideColumn.priceWOTax}"), editor : {type: "text", maxLength: 9, validRule : ['N'], validator : numberValidator}, renderer : zeroRenderer, afterEdit : autoCalculateFromPrice},
    	{id: 'pricewtax' , header: "Price W/ Tax" , align:"right" , width : "${user.preference.widthColumn.priceWTax}", renderer : pricewtaxRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.priceWTax}")},
    	{id: 'column10', header: "Column 10", align: "left", width : "${user.preference.widthColumn.column10}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column10}")},
		{id: 'column05', header: "Column 05", align: "left", width : "${user.preference.widthColumn.column05}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column05}")},
		{id: 'column11', header: "Column 11", align: "left", width : "${user.preference.widthColumn.column11}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column11}")},
    	{id: 'packageqty' , header: "Quantity" , align:"right" , width : "${user.preference.widthColumn.packageQty}", hidden : toSigmaBoolean("${user.preference.hideColumn.packageQty}"), editor : {type: "text", maxLength: 9, validator : pkgQtyValidator}, renderer : zeroRenderer},
    	{id: 'packagetype' , header: "Type" , align:"left" , width : "${user.preference.widthColumn.packageType}", hidden : toSigmaBoolean("${user.preference.hideColumn.packageType}"), editor : {type: "text", maxLength: 42}},
    	{id: 'unitorder' , header: "Unit Order" , align:"left" , width : "${user.preference.widthColumn.uom}", hidden : toSigmaBoolean("${user.preference.hideColumn.uom}"), editor : {type: "select" ,options : ${orderUnitList} ,defaultText : '0', validator : unitOrderValidator }, renderer : unitorderRenderer},
<c:choose>
  <c:when test="${allDatesView eq false}">
  {id: 'visall', header: "visall" , align:"center" , width :36 , hidden: true, renderer:render_checkbox_all},
    <c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
		<%-- {id: 'Q_${user.userId}' , header: "${user.userName}" , align:"right" , width :35 , renderer:render_quantity }, --%>
		{id:'Q_${user.userId}', header:"${user.userName}" ,align:"right", width:36, editor:{type:"text", maxLength:13, validator : quantity_validator}, renderer : quantityRenderer, afterEdit : autoCalculateFromQuantity },
	  </c:forEach>
    </c:forEach>
    <c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
	    {id: 'V_${user.userId}' , header: "${user.userName}" , align:"center" , width :36 , hidden: true, renderer : render_checkbox},
	  </c:forEach>
    </c:forEach>    
  </c:when>
  <c:otherwise>
    <c:forEach var="dateKey" items="${dateList}" varStatus="status">
 	  <c:set var="dateCol" value="${dateMap[dateKey]}" />
 		<%-- {id: 'Q_${dateKey}' , header: "${dateCol}" , align:"right" , width :35 , renderer:render_quantity }, --%>
 		{id:'Q_${dateKey}', header:"${dateCol}", align:"right", width:36, editor:{type:"text", maxLength:13, validator: quantity_validator_weekly}, renderer : quantityRenderer, afterEdit : autoCalculateFromQuantity },
    </c:forEach>
  </c:otherwise>
</c:choose>

<c:choose>
<c:when test="${allDatesView eq false}">
	{id: 'skumaxlimit' , header: "SKU Max Limit" , align:"right" , width : "${user.preference.widthColumn.skuMaxLimit}", hidden : toSigmaBoolean("${user.preference.hideColumn.skuMaxLimit}"), editor : {type: "text", maxLength: 13, validRule : ['N'], validator : skuMaxLimit_validator}, renderer : zeroRenderer},
</c:when>
<c:otherwise>
	{id: 'skumaxlimit' , header: "SKU Max Limit" , align:"right" , width : "${user.preference.widthColumn.skuMaxLimit}", hidden : true},
</c:otherwise>
</c:choose>
		{id: 'rowqty' , header: "Row Qty" , align:"right" , width : "${user.preference.widthColumn.rowQty}", renderer:rowqtyRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.rowQty}")},
		{id: 'column12', header: "Column 12", align: "left", width : "${user.preference.widthColumn.column12}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column12}")},
		{id: 'column13', header: "Column 13", align: "left", width : "${user.preference.widthColumn.column13}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column13}")},
		{id: 'column14', header: "Column 14", align: "left", width : "${user.preference.widthColumn.column14}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column14}")},
		{id: 'column15', header: "Column 15", align: "left", width : "${user.preference.widthColumn.column15}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column15}")},
		{id: 'column16', header: "Column 16", align: "left", width : "${user.preference.widthColumn.column16}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column16}")},
		{id: 'column17', header: "Column 17", align: "left", width : "${user.preference.widthColumn.column17}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column17}")},
		{id: 'column18', header: "Column 18", align: "left", width : "${user.preference.widthColumn.column18}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column18}")},
		{id: 'column19', header: "Column 19", align: "left", width : "${user.preference.widthColumn.column19}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column19}")},
		{id: 'column20', header: "Column 20", align: "left", width : "${user.preference.widthColumn.column20}", editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.column20}")},
		{id: 'profitInfo', header:"Profit Info", width : 200, editable : false, hidden : true},
		{id: 'externalSkuId', header:"External SKU ID", width : 200, editable : false, hidden : true}
    ];
    //dynamic height for sigma grid based on browser's height
    var sigmaHeight = window.parent.g_clientHeight - 195;
    if(sigmaHeight < 388) sigmaHeight = 388;
    
	var gridOption={
		id : grid_demo_id,
		encoding:'UTF-8',
		loadURL : '<c:url value="/order/loadOrder.json" />',
		saveURL : '<c:url value="/order/saveOrder.json" />',
		width: "99.5%",  //"100%", // 700,
		height: sigmaHeight + "px", //"359px",  //"100%", // 330, //300
		container : 'gridbox', 
		replaceContainer : true, 
		customHead : 'myHead1',
		dataset : _dataset ,
		columns : _columns,
		pageSize : 120,
		pageSizeList : [40, 80, 120, 160, 200],
		toolbarContent : 'nav | goto | pagesize state',
		language : 'ja',
		allowCustomSkin : true,
		allowFreeze : false,
		allowGroup : true,
		allowHide : true,
		// ENHANCEMENT 20120724: Lele - Redmine 851
		autoSelectFirstRow : false,
		// ENHANCEMENT 20120724: Lele - Redmine 420
		recountAfterSave : false,
		sellingPrice : false,
		profit : false,
		profitPercentage : false,
		skin : "default",
		customRowAttribute : function(record,rn,grid) {
			var lockflagObj = _.unserialize(record.lockflag);
			if (lockflagObj.sku == '1' || lockflagObj.proposed == '1') return 'style="background-color:#eee9e9"';
			else return 'style="height:30px;"';
		},

<c:choose>
	<c:when test="${isAdmin eq false}">
		defaultRecord : { skuId : "0", sellerId : "${user.userId}", companyname: "${user.company.shortName}", sellername : "${user.shortName}", groupname : "0", unitorder : "${defualtOrderUnitId}", lockflag : " {'allQuantities' : 0, 'sellername' : 1, addSKU : 1}",
	</c:when>
	<c:otherwise>
		defaultRecord : { skuId : "0", groupname : "0", unitorder : "${defualtOrderUnitId}", lockflag : " {'adminAddSKU' : 1, addSKU : 1}",
	</c:otherwise>
</c:choose>

<c:choose>
<c:when test="${allDatesView eq false}">
		"visall" : "1",
  <c:forEach var="company" items="${companyList}"  varStatus="status">
    <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
		"Q_${user.userId}" : "", "V_${user.userId}" : "1",
	</c:forEach>
  </c:forEach>
</c:when>
<c:otherwise>
  <c:forEach var="dateKey" items="${dateList}" varStatus="status">
		"Q_${dateKey}" : "",
  </c:forEach>
</c:otherwise>
</c:choose>
		skumaxlimit : "", marketname : "", skuname : "", home : "", grade : "", 
		clazzname : "", price1 : "", price2 : "", pricewotax : "", packageqty : "", 
		packagetype : "", skumaxlimit : "", rowqty : "",
		column01 : "1", column02 : "", column03 : "", column04 : "", column05 : "", 
		column06 : "", column07 : "", column08 : "", column09 : "", column10 : "", 
		column11 : "", column12 : "", column13 : "", column14 : "", column15 : "", 
		column16 : "", column17 : "", column18 : "", column19 : "", column20 : "" },

		loadResponseHandler : function(response, requestParameter) {
			var obj = _.unserialize(response.text);

			// Get Totals
			mygrid.setTotals(obj.totals);
			$("#priceInfo").html(
					window.
					parent.
					orderSheet.
					computeForPrices(obj.totals.priceWithoutTax, obj.totals.priceWithTax));

			// Get Grand Totals
			mygrid.setGrandTotals(obj.grandTotals);

			//window.parent.orderSheet.visToggle.setDisabled(false);
			window.parent.orderSheet.cboBuyers.setDisabled(false);
			window.parent.orderSheet.linkPrevious.setVisibility('visible');
			window.parent.orderSheet.linkNext.setVisibility('visible');
			window.parent.orderSheet.setDatePanel(false);
			//window.parent.orderSheet.btnDownloadExcel.setDisabled(false);

			// enable disable action buttons
			window.parent.orderSheet.disableActionButtons();
			var flags = _.unserialize(obj.buttonFlags);
			if (flags != null) {
				window.parent.orderSheet.enableMenus();
				if (flags.btnSave == 0) {
					window.parent.orderSheet.btnAddSKU.setDisabled(false);
					window.parent.orderSheet.btnAddSKUGroup.setDisabled(false);
					window.parent.orderSheet.btnSave.setDisabled(false);
				}
				if (flags.btnPublish == 0)
					window.parent.orderSheet.btnPublish.setDisabled(false);
				if (flags.btnFinalize == 0)
					window.parent.orderSheet.btnFinalize.setDisabled(false);
				if (flags.btnUnpublish == 0)
					window.parent.orderSheet.btnUnpublish.setDisabled(false);
				if (flags.btnUnfinalize == 0){
					window.parent.orderSheet.btnUnfinalize.setDisabled(false);
					window.parent.orderSheet.btnAddSKU.setDisabled(true);
					window.parent.orderSheet.btnAddSKUGroup.setDisabled(true);
					window.parent.orderSheet.disableMenus();
				}else{
					window.parent.orderSheet.btnUnfinalize.setDisabled(true);
				}
				//window.parent.sellerMenu.menubar_seller.refresh();
			}

			var sellerSet = obj.sellernameSet;
			if (sellerSet != null) {
				var i=0;
				for (i=0; i<sellerSet.length; i++) {
					var value = sellerSet[i];
					var item = getPoolItem(value);
			        if(item){
			            //updateRow(row, item);
			            return;
			        }
			        mygrid.showWaiting();
			        $.ajax({
			            url: "/eON/order/sellerSelect.json",
			            data: "sellerName=" + encodeURIComponent(value),
			            dataType: 'json',
			            async : false,
			            success: function(ret){
			        		validateSigmaResponse(ret);
			       	 		NOPOOL[value] = ret;
			                //updateRow(row,ret);
			            },
			            complete:function(XMLHttpRequest, textStatus){
			                mygrid.hideWaiting();
			            }
			        });
				}
			}
		},

		saveResponseHandler : function(response, requestParameter) {
			//alert("response.text: " + response.text);
			var result = validateResponseOnIframe(response.text);
			if (result == true) {
				alert(mygrid.getMsg('SAVED'));
			}
		},

		beforeEdit : function(value,record,col,grid) {
			//row = grid.getRowByRecord(record);
			//alert("colValue: " + grid.getColumnValue(col.id, row.rowIndex));
			var rowNo = (grid.activeRow).rowIndex;
		    var lockflagObj = _.unserialize(mygrid.getColumnValue("lockflag", rowNo));
		    var insertedRows = mygrid.getInsertedRecords();
		    var existRowStart = rowNo - insertedRows.length;

		    if (1==0) {}
		    <c:choose>
		      <c:when test="${allDatesView eq false}">
		        <c:forEach var="company" items="${companyList}"  varStatus="status">
		    	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
		    	  		else if (col.id == 'Q_${user.userId}') {
		    	  			if (lockflagObj == null) return false;
		    	  			var obj = document.getElementById("cBox-V_${user.userId}-" + existRowStart);
		    	  			//if (obj == null) return false;
		    	  			//if (lockflagObj.Q_${user.userId} == '1' || !obj.checked) return false;
		    	  			if (lockflagObj.Q_${user.userId} == '1') return false;
		    	  			if (obj != null && !obj.checked) return false;
		    		  		if (lockflagObj.allQuantities == '1' || lockflagObj.Q_${user.userId} == '1') return false;
		    	  		}
		    	  </c:forEach>
		        </c:forEach>
		      </c:when>
		      <c:otherwise>
		        <c:forEach var="dateKey" items="${dateList}" varStatus="status">
		    	  <c:set var="dateCol" value="${dateMap[dateKey]}" />
		    	  		else if (col.id == 'Q_${dateKey}') {
		    	  			if (lockflagObj == null) return false;
		    		  		if (lockflagObj.allQuantities == '1' || lockflagObj.Q_${dateKey} == '1') return false;
		    	  		}
		        </c:forEach>
		      </c:otherwise>
		    </c:choose>
			
		    if (lockflagObj == null) {
				return;
			}
		    if (col.id == 'sellername' && lockflagObj.sellername == '1') return false;

		    <c:choose>
			    <c:when test="${isAdmin eq true}">
			    	if (lockflagObj.adminAddSKU == '1' && col.id != 'sellername') return false;
				</c:when>
			</c:choose>
			
		    if ((!col.id.startsWith("Q") && !col.id.startsWith("V")) || (col.id == 'skumaxlimit')) {
				if (lockflagObj.sku == '1' || lockflagObj.proposed == '1')
					return false;
		    }
		},

		onInvalidInput : function(value,oldValue,activeRecord, cell, activeColumn, validResult, grid){
		    alert(validResult);
		    /*edited by jovsab 2010.08.18*/
		    /*
	      var dom = this.activeEditor.valueDom;
		    $(dom).animate({
	         backgroundColor: "red"
	         }, 3000, function() {
	         // Animation complete.
	         dom.style.backgroundColor = "";
	      });
		      */
	      return false;
		},

		afterEdit: function(value, oldValue, record, row, col, grid){
			//if (value != oldValue) {
			//	row = mygrid.getRowByRecord(record);
			//	row.style.backgroundColor = 'orange';
		    //}
			
			var dom = grid.activeEditor.valueDom;
			if(dom){
				$(dom).stop();
				$(dom).clearQueue();
				dom.style.backgroundColor = "";
			}
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

	function rowqtyRenderer(value, record, columnObj, grid, colNo, rowNo){
		var name = record[columnObj.fieldIndex];

		if (isNaN(name))
			name = 0;
		
		if (columnObj.id == "rowqty") {
			return "<div class='higlightedRowQty'>" + addCommas(name) + "</div>";
		}
	}

	function quantityRenderer (value , record, columnObj, grid, colNo, rowNo) {
		return addCommas(value);
	}

	function skuMaxLimit_validator(value, record, colObj, grid) {
		value = value + "";
		value = value.trim();
		var tmp = "${unitorderRenderer}";
		var obj = _.unserialize(tmp);
		var toProcess = (!isNaN(value) && value>0 && value<=999999999.999);
		var isValidDecimalInput = validateQuantityScale(getOrderUnitById(obj,record["unitorder"]), value);
		if (value == '' || (toProcess == true && isValidDecimalInput ==true)) {
			if (toProcess == true) {
				var tmp = validateQuantityLimit(record, colObj.id, value);
				return tmp;
			}
		}
		else {
			return grid.getMsg('INVALID_INPUT');
		}
	}

	function quantity_validator(value, record, colObj, grid) {
		value = value + "";
		value = value.trim();
		var tmp = "${unitorderRenderer}";
		var obj = _.unserialize(tmp);
		var toProcess = (!isNaN(value) && value>=0 && value<=999999999.999);
		var isValidDecimalInput = validateQuantityScale(getOrderUnitById(obj,record["unitorder"]), value);
		if (value == '' || (toProcess == true && isValidDecimalInput ==true)) {
			if (record.skumaxlimit == "")
				return;
			// check for sku max limit
			var tmp = validateQuantityLimit(record, colObj.id, value);
			return tmp;
		}
		else {//invalid
			if (visToggled) toggleVisibilities2(!visFlag);
			return grid.getMsg('INVALID_INPUT');
		}
	}

	function quantity_validator_weekly (value, record, colObj, grid) {
		value = value + "";
		value = value.trim();
		var tmp = "${unitorderRenderer}";
		var obj = _.unserialize(tmp);
		var toProcess = (!isNaN(value) && value>=0 && value<=999999999.999);
		var isValidDecimalInput = validateQuantityScale(getOrderUnitById(obj,record["unitorder"]), value);
		var error_msg = "";
		if (toProcess == true && isValidDecimalInput ==true) {
			// check for sku max limit
			var url = "/eON/order/weeklyQuantityLimit.json";
			url = url + "?sellerId=" + record.sellerId;
			url = url + "&skuId=" + record.skuId;
			url = url + "&quantity=" + value;
			url = url + "&dateFieldId=" + colObj.id;

			// do not validate when: weekly view and newly added sku (sku id = 0)
			if (record.skuId == 0)
				return;
			
			mygrid.showWaiting();
	        $.ajax({
	            url: url,
	            data: "",
	            dataType: 'json',
	            async : false,
	            success: function(response){
	            	if (response.isValid == "false") {
		            	error_msg = Sigma.$msg(mygrid.getMsg('SKU_MAX_LIMIT_EXCEEDED'), response.availableQty);
	            	}
	            },
	            complete:function(XMLHttpRequest, textStatus){
	                mygrid.hideWaiting();
	            }
	        });
		} else {
			return grid.getMsg('INVALID_INPUT');
		}

		if (error_msg != "")
			return error_msg;
	}

	function nagativeValidator (value, record, colObj, grid) {
		if (isNaN(value) || parseFloat(value) < 0) {
			return grid.getMsg('INVALID_INPUT');
		}
	}

	function validateQuantityLimit (record, colId, value) {
		if (value != '' && parseInt(value) > 0) {
			var errMsg = Sigma.$msg(mygrid.getMsg('SKU_MAX_LIMIT_EXCEEDED'), record.skumaxlimit);
			var cond1 = validateQuantityLimitUI (record, colId, value);
			//alert("cond1: " + cond1);
			if (cond1 != false) {
				var skumaxlimit;
			    if (colId == 'skumaxlimit') skumaxlimit = value;
			    else skumaxlimit = record.skumaxlimit;
			    
				var url = "/eON/order/quantityLimit.json?sellerId=" + record.sellerId;
				url = url + "&skuId=" + record.skuId;
				url = url + "&skuMaxLimit=" + skumaxlimit;
				url = url + "&cond1=" + cond1;
				
		        var eps = 0;
				mygrid.showWaiting();
		        $.ajax({
		            url: url,
		            data: "",
		            dataType: 'json',
		            async : false,
		            success: function(response){
		        		validateSigmaResponse(response);
		        		var cond2 = response.isValid;
		        		//alert("cond2: " + cond2);
		        		if (cond2 == 'true') {
							return true;
						}
						else {
							eps = 1;
						}
		            },
		            complete:function(XMLHttpRequest, textStatus){
		                mygrid.hideWaiting();
		            }
		        });
		        if (eps == 1) return errMsg;
			} else {
				if (colId == 'skumaxlimit'){
					return Sigma.$msg(mygrid.getMsg('SKU_LIMIT_LESS_THAN_TOTAL_QTY'), getAvailableQuantity(record, colId, value));
				}else{
					return Sigma.$msg(mygrid.getMsg('SKU_MAX_LIMIT_EXCEEDED'), getAvailableQuantity(record, colId, value));
				}
			}
		}
	}

	function validateQuantityLimitUI (record, colId, value) {
		var totalQty = 0;
		var quantityKeys = [];
		var ctr = 0;

		<c:choose>
				<c:when test="${allDatesView eq true}">
					<c:forEach var="dateKey" items="${dateList}" varStatus="status">
					        var key = "Q_${dateKey}";
					  		quantityKeys[ctr] = key;
					  		ctr++;
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:forEach var="company" items="${companyList}"  varStatus="status">
					  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
					  		var key = "Q_${user.userId}";
					  		quantityKeys[ctr] = key;
					  		ctr++;
					  </c:forEach>
				    </c:forEach>
				</c:otherwise>
		</c:choose>
	    var i=0;
	    //alert("record: " + _.serialize(record));
	    for (i=0;i<quantityKeys.length;i++) {
		    var key = quantityKeys[i];
		    var _value;
		    if (colId ==  key) _value = value;
		    else _value = record[key];
		    if (!isNaN(_value) && _value>0 && _value<=999999999.999) {
	  			totalQty = parseFloat(totalQty) + parseFloat(_value);
	  		}
	    }

	    var skumaxlimit;
	    if (colId == 'skumaxlimit') skumaxlimit = value;
	    else skumaxlimit = parseFloat(record.skumaxlimit);
	    //alert("skumaxlimit: " + skumaxlimit);
	  	// invalid
	    if (skumaxlimit > 0 && skumaxlimit < (totalQty)) {
	    	return false; 
	    } else {
	    	quantityKeys[ctr] = parseFloat(totalQty);
		    return quantityKeys;
	    }
	}

	function getAvailableQuantity (record, colId, value) {
		var totalQty = 0;
		var quantityKeys = [];
		var ctr = 0;

		<c:choose>
				<c:when test="${allDatesView eq true}">
					<c:forEach var="dateKey" items="${dateList}" varStatus="status">
					        var key = "Q_${dateKey}";
					  		quantityKeys[ctr] = key;
					  		ctr++;
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:forEach var="company" items="${companyList}"  varStatus="status">
					  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
					  		var key = "Q_${user.userId}";
					  		quantityKeys[ctr] = key;
					  		ctr++;
					  </c:forEach>
				    </c:forEach>
				</c:otherwise>
		</c:choose>
	    var i=0;
	    var enteredQty=0;
	    for (i=0;i<quantityKeys.length;i++) {
		    var key = quantityKeys[i];
		    var _value;
		    if (colId ==  key) {
			    _value = value;
			    enteredQty = value;
		    }
		    else {
			    _value = record[key];
		    }
		    if (!isNaN(_value) && _value>0 && _value<=999999999.999) {
	  			totalQty = parseFloat(totalQty) + parseFloat(_value);
	  		}
	    }

	    var skumaxlimit;
	    if (colId == 'skumaxlimit'){
		    return parseFloat(totalQty);
	    } else {
		    skumaxlimit = parseFloat(record.skumaxlimit);
		    return Math.abs(parseFloat(totalQty) - parseFloat(enteredQty) - parseFloat(skumaxlimit));
	    }

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

		//<c:if test="${editableStatus ne ''}">
		//	disabled = "disabled";
		//</c:if>
		
		if (value == -999) {
			render = "<span id='span-" + columnObj.id + "-" + rowNo + "' class='gt-editor-q' style='background-color:#CFECEC;'></span>";
		}
		else {
			render = "<input type='text' style='background-color: " + color + ";'maxLength='10' onmouseover=\"this.style.cursor='arrow'\" class='gt-editor-q' id='tBox-" + columnObj.id + "-" + rowNo + "' value='"+value+"' " + disabled + " onfocus='focusMe(this)' onblur='blurMe(this)' onkeyup='enterPress(this, event)'/>";
		}
		
		return render;
	}

	function render_checkbox(value , record, columnObj, grid, colNo, rowNo){
		//var thisGrid = Sigma.$grid(grid_demo_id);
		var allRows = mygrid.getInsertedRecords();
		//alert(value);
		//alert(columnObj.width);
		//alert(columnObj.id);
		//alert(record.Q_2);
		//var data = grid.getRecord(record);
		//data['Q_2'] = 555;
		//alert(data['V_2']);
		
		//grid.setColumnValue("Q_2", rowNo, "343");
		//alert(grid.getUpdatedRecords());
		//alert(columnObj.value);
		//return "<input type=checkbox id='cBox_" + columnObj.id + rowNo + "' checked='" + value +
		//"' onclick=cBoxMethod(this, '" + columnObj.id + "','" + rowNo + "');></input>";
		disabled = "";

		//alert(mygrid.getColumnValue("lockflag", rowNo));
		//alert(columnObj.id);
		//alert(rowNo);
		//alert(allRows.length);
		//if (rowNo < allRows.length) disabled = "";
		if (allRows.length > 0) disabled = "disabled";
		else {
			var lockflagObj = _.unserialize(mygrid.getColumnValue("lockflag", rowNo));
			var colId = columnObj.id;
			<c:if test="${allDatesView eq false}">
				<c:forEach var="company" items="${companyList}"  varStatus="status">
				  	<c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
		  			if (colId == 'V_${user.userId}') {
			  			if (lockflagObj.V_${user.userId} == '1') disabled = "disabled";	;
		  			}
	  				</c:forEach>
	  			</c:forEach>
	  		</c:if>
		}
		checked = "";
		if (value == "1") checked = "checked";

		return "<input type=checkbox id='cBox-" + columnObj.id + "-" + rowNo + "' " + checked + " " + disabled + " onclick=cBoxMethod(this);></input>";
	}

	function render_checkbox_all(value , record, columnObj, grid, colNo, rowNo) {
		var allRows = mygrid.getInsertedRecords();
		disabled = "";

		if (allRows.length > 0) disabled = "disabled";
		else {
			var lockflagObj = _.unserialize(mygrid.getColumnValue("lockflag", rowNo));
			var colId = columnObj.id;
			if (lockflagObj.sku == '1') disabled = "disabled";

			<c:if test="${allDatesView eq false}">
				<c:forEach var="company" items="${companyList}"  varStatus="status">
			  		<c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
			  			if (lockflagObj.V_${user.userId} == '1') disabled = "disabled";
					</c:forEach>
				</c:forEach>
			</c:if>
		}
		checked = "";
		if (value == "1") checked = "checked";

		disabled = "";
		return "<input type=checkbox id='cBox-" + columnObj.id + "-" + rowNo + "' " + checked + " " + disabled + " onclick=cBoxAllMethod(this);></input>";
	}

	function deleteCheckBoxRenderer (value , record, columnObj, grid, colNo, rowNo) {

		var lockflagObj = _.unserialize(record["lockflag"]);
		if (lockflagObj != null) {
			if (lockflagObj.sku == '1')
				return "<input type=checkbox id='chkId" + rowNo + "' disabled></input>";
			else
				return "<input type=checkbox id='chkId" + rowNo + "' onclick=deleteRecord(this.id," + rowNo + ");></input>";
		}
	}

	function deleteRecord(id, rowNo) {
		mygrid.showWaiting();
		setTimeout('mygrid.deleteRowWithNoQty(' + rowNo + ')',100);
		setTimeout('mygrid.hideWaiting()',175);
	}

	function pricewtaxRenderer (value , record, columnObj, grid, colNo, rowNo) {
		if (value ==  "undefined") return "";
		if (value > 0) return addCommas(value);
		else return "";
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

	function zeroRenderer (value , record, columnObj, grid, colNo, rowNo) {
		if (isNaN(value)) {
			record[columnObj.id] = "";
			return "";
		} else {
			return addCommas(value);
		}
	}
	
	//first
	function cBoxAllMethod(cBoxAll) {
		var cBoxAllId = cBoxAll.id;
		var arr = cBoxAllId.split("-");
		var colId = arr[1];
		var rowNo = arr[2];
		var cBoxId = new Array();
		var visId = new Array();
		var qId = new Array();
		var cBoxObj;
		var lockedBuyer = new Array();
		var lockflag = mygrid.getColumnValue("lockflag", rowNo);
		var lockflagObj = _.unserialize(lockflag);
	<c:forEach var="user" items="${buyerMap}" varStatus="status">
		cBoxId[${status.count-1}] = "cBox-V_${user.value.userId}-" + rowNo;
		visId[${status.count-1}] = "V_${user.value.userId}";
		qId[${status.count-1}] = "Q_${user.value.userId}";
		lockedBuyer[${status.count-1}] = lockflagObj.V_${user.value.userId};
	</c:forEach>
		
		var ctrQ = 0;
		for(i=0; i<cBoxId.length; i++) {
			var Q_buyer = qId[i];
			if (!cBoxAll.checked) {
				var qValue = mygrid.getColumnValue(Q_buyer, rowNo);
				if (qValue != null && qValue != "") {
					ctrQ++;
				}
			}
		}

		if (ctrQ > 0) {
			// ENHANCEMENT START: LELE
			/*
			var msg = mygrid.getMsg('CONFIRM_INVISIBLE');
			var answer = confirm(msg);
			if (!answer) {
				cBoxAll.checked = !cBoxAll.checked;
				return;
			}
			*/
			alert(mygrid.getMsg('ERROR_INVISIBLE'));
			cBoxAll.checked = !cBoxAll.checked;
			return;
			// ENHANCEMENT END
		}

		for(i=0; i<cBoxId.length; i++) {
			if (lockedBuyer[i] != null && lockedBuyer[i] != 0) continue; 
			cBoxObj = document.getElementById(cBoxId[i]);
			if (cBoxObj != null) {
				cBoxObj.checked = cBoxAll.checked;
				visFlag = 1;
				if (!cBoxObj.checked) {
					visFlag = 0;
					var oldQValue = mygrid.getColumnValue(qId[i], rowNo);
					if (oldQValue != "") {
						mygrid.setColumnValue(qId[i], rowNo, "");
						makeDirty(qId[i], rowNo);
					}
				}
				var oldVValue = mygrid.getColumnValue(visId[i], rowNo);
				if (oldVValue != visFlag) {
					mygrid.setColumnValue(visId[i], rowNo, visFlag);
					makeDirty(visId[i], rowNo);
				}
			}
		}
		
	}

	function cBoxMethod(cBoxObj) {
		//var thisGrid = Sigma.$grid(grid_demo_id);
		//var buyerCount = ${fn:length(buyerMap)};
		var allRows = mygrid.getAllRows();
		//alert(_.serialize(allRows));
		var cBoxId = cBoxObj.id;
		var arr = cBoxId.split("-");
		var colId = arr[1];
		var rowNo = arr[2];
		var visFlag = 1;

		arr = colId.split("_");
		var buyerId = arr[1];
		var Q_buyer = "Q_" + buyerId;
		var V_buyer = "V_" + buyerId;
		var qValue = mygrid.getColumnValue(Q_buyer, rowNo);
		var vValue = mygrid.getColumnValue(V_buyer, rowNo);
		
		if (cBoxObj.checked) {
			visFlag = 1;
		}
		else {
			visFlag = 0;
			// ENHANCEMENT START LELE: Do not allow to uncheck if buyer has quantity
			/*
			if (qValue != null && qValue != "") {
				var msg = mygrid.getMsg('CONFIRM_INVISIBLE');
				var answer = confirm(msg);
				if (answer) {
					mygrid.setColumnValue(Q_buyer, rowNo, "");
					makeDirty(Q_buyer, rowNo);
				}
				else {
					cBoxObj.checked = true;
					return;
				}
			}
			*/
			if (qValue != null && qValue != "") {
				alert(mygrid.getMsg('ERROR_INVISIBLE'));
				cBoxObj.checked = true;
				return;
			}
			// ENHANCEMENT END:
		}
		mygrid.setColumnValue(colId, rowNo, visFlag);
		makeDirty(colId, rowNo);

		var lockedBuyer;
		var lockflag = mygrid.getColumnValue("lockflag", rowNo);
		var lockflagObj = _.unserialize(lockflag);
		var cBoxId = new Array();
		var allChecked = true;
	<c:forEach var="user" items="${buyerMap}" varStatus="status">
		lockedBuyer = lockflagObj.V_${user.value.userId};
		cBoxObj = document.getElementById("cBox-V_${user.value.userId}-" + rowNo);
		if (!cBoxObj.checked && lockedBuyer == 0) allChecked = false;
	</c:forEach>

		var cBoxAllObj = document.getElementById("cBox-visall-" + rowNo);
		cBoxAllObj.checked = allChecked;
		
	}

	function makeDirty(colId, rowNo) {
		var record;
		if ( Sigma.U.isNumber(rowNo)){
			record = mygrid.dataset.getRecord(rowNo);
		}
		var row = mygrid.getRowByRecord(record);
		var col =  mygrid.columnMap[colId];
		var cell = mygrid.getCell(row, col);
		
		mygrid.dirty(cell);
	}
		
	function onSave() {
		var targetDeliveryDate = "${deliveryDate}";
		var targetBuyerIds = "${orderSheetParam.selectedBuyerID}";
		var targetSellerIds = "${orderSheetParam.selectedSellerID}";
		var param = {'targetDeliveryDate' : targetDeliveryDate, 'targetBuyerIds' : targetBuyerIds, 'targetSellerIds' : targetSellerIds};
		mygrid.setParameters(param);
		var ret = validateSKUGroupsAndOrderUnits();

		if (ret != false) {

			ret = validateOrder();
			
			if (ret == true) {
				ret = mygrid.save();
				//if (ret != null)  { // error on input
				//	alert(mygrid.getMsg('REQUIRED_FIELDS_NOT_POPULATED'));
				//}
				// ENHANCEMENT START 20120802: Lele - Redmine 908
				if (ret == false) {
					alert(mygrid.getMsg('REQUIRED_FIELDS_NOT_POPULATED'));
				}
				// ENHANCEMENT END 20120802: Lele 
			}
		}
		else {
			alert(mygrid.getMsg('REQUIRED_FIELDS_NOT_POPULATED'));
		}
		if (ret == false || ret == "error") {
			window.parent.orderSheet.btnSave.setDisabled(false);
		}
	}

	function validateOrder() {
		var isValid = false;
		var deletedRec = mygrid.getDeletedRecords();
		var skuIds = "";

		for (var i=0; i<deletedRec.length; i++) {
			skuIds = skuIds + deletedRec[i]["skuId"];
			if (i < deletedRec.length - 1) skuIds = skuIds + ",";
		}

		if (skuIds == "") {
			isValid = true;
		}
		else {
			mygrid.showWaiting();
			$.ajax({
	            url: "/eON/order/validateOrder.json?skuIds=" + skuIds,
	            data: "",
	            dataType: 'json',
	            async : false,
	            success: function(response){
					validateSigmaResponse(response);
	            	if (response.error != "false") {
		            	isValid = false; //response.error;
		            	alert(response.error);
	            	}
	            	else  isValid = true;
	            },
	            complete:function(XMLHttpRequest, textStatus){
	                mygrid.hideWaiting();
	            }
	        });
		}
		return isValid;
	}
	
	function onPublish() {
		mygrid.showWaiting();
		var targetDeliveryDate = "${deliveryDate}";
		var targetBuyerIds = "${orderSheetParam.selectedBuyerID}";
		var targetSellerIds = "${orderSheetParam.selectedSellerID}";
		var param = {targetDeliveryDate : targetDeliveryDate, targetBuyerIds : targetBuyerIds, targetSellerIds : targetSellerIds};
		linb.Ajax(
			"/eON/seller/publish.json",
            param,
            function (response) {
				validateResponse(response);
				if (validateResponseOnIframe(response)) {
					window.parent.orderSheet.disableActionButtons();
	            	alert(mygrid.getMsg('PUBLISHED'));
	            	window.parent.orderSheet.loadDataTable();
				}
				mygrid.hideWaiting();
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

	function onUnpublish() {
		mygrid.showWaiting();
		var targetDeliveryDate = "${deliveryDate}";
		var targetBuyerIds = "${orderSheetParam.selectedBuyerID}";
		var targetSellerIds = "${orderSheetParam.selectedSellerID}";
		var param = {targetDeliveryDate : targetDeliveryDate, targetBuyerIds : targetBuyerIds, targetSellerIds : targetSellerIds};
		linb.Ajax(
			"/eON/seller/unpublish.json",
            param,
            function (response) {
				validateResponse(response);
	            if (validateResponseOnIframe(response)) {
	            	var o = _.unserialize(response);
	            	window.parent.orderSheet.disableActionButtons();
	            	if(o.hasQuantities == 0){
		            	alert(mygrid.getMsg('UNPUBLISHED'));
		            	//window.parent.orderSheet.loadDataTable();
	            	}else{
	            		alert(linb.Locale[linb.getLang()].app.caption['UNPUBLISHED_NOT_ALLOWED']);
	            		//mygrid.reload(false,true);
	            	}
	            }
            	mygrid.hideWaiting();
            }, 
            function(msg) {
				linb.message("失敗： " + msg);
            }, 
            null, 
            {
				asy : false,
            	method : 'POST',
            	retry : 0
            }
        ).start();
	}

	function onFinalize() {
		var targetDeliveryDate = "${deliveryDate}";
		var targetBuyerIds = "${orderSheetParam.selectedBuyerID}";
		var targetSellerIds = "${orderSheetParam.selectedSellerID}";
		var param = {targetDeliveryDate : targetDeliveryDate, targetBuyerIds : targetBuyerIds, targetSellerIds : targetSellerIds};
		var msg = mygrid.getMsg('CONFIRM_FINALIZE');
		var answer = confirm(msg);
		if (answer) {
			mygrid.showWaiting();
			linb.Ajax(
				"/eON/seller/finalize.json",
	            param,
	            function (response) {
					validateResponse(response);
					if (validateResponseOnIframe(response)) {
						window.parent.orderSheet.disableActionButtons();
						var infoMessage = retrieveInfoMessage(response);
		            	if (infoMessage != ""){
	                        alert(infoMessage);
		            	} else {
	                        alert(mygrid.getMsg('FINALIZED'));
		            	}
						mygrid.reload(false,true);
					}
	            	mygrid.hideWaiting();
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
	}

	function onUnfinalize() {
		var targetDeliveryDate = "${deliveryDate}";
		var targetBuyerIds = "${orderSheetParam.selectedBuyerID}";
		var targetSellerIds = "${orderSheetParam.selectedSellerID}";
		var param = {targetDeliveryDate : targetDeliveryDate, targetBuyerIds : targetBuyerIds, targetSellerIds : targetSellerIds};
		var msg = mygrid.getMsg('ORDER_CONFIRM_UNFINALIZE');
		var answer = confirm(msg);
		if (answer) {
			mygrid.showWaiting();
			linb.Ajax(
				"/eON/seller/unfinalize.json",
	            param,
	            function (response) {
					validateResponse(response);
		            if (validateResponseOnIframe(response)) {
		            	window.parent.orderSheet.disableActionButtons();
						alert(mygrid.getMsg('UNFINALIZED'));
						//DELETE 20120919: Rhoda Redmine 1070
						//mygrid.reload(false,true);
					}
					//ENHANCEMENT 20120919: Rhoda Redmine 1070
		            mygrid.reload(false,true);
					mygrid.hideWaiting();
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
	}

	function toggleVisibilities(checked) {
		var Q = new Array(); 
		var V = new Array();
		var visAll = mygrid.getColumn("visall");
<c:forEach var="user" items="${buyerMap}" varStatus="status">
       	Q[${status.count-1}] = mygrid.getColumn("Q_${user.value.userId}");
       	V[${status.count-1}] = mygrid.getColumn("V_${user.value.userId}");
</c:forEach>
	
		if (checked) {
			visAll.show();
			for(i=0; i<Q.length; i++) {
				Q[i].hide();
				V[i].show();
			}
		}
		else {
			visAll.hide();
			for(i=0; i<Q.length; i++) {
				V[i].hide();
				Q[i].show();
			}
		}
		
	}

	function toggleVisibilities2(checked, force) {
		var objBox = document.getElementById("visToggle");
		if (objBox == null) return;
		objBox.checked = checked;
		if (mygrid.getInsertedRecords().length > 0) {
			objBox.checked = false;
			return;
		}
		var Q = new Array(); 
		var V = new Array();
		var visAll = mygrid.getColumn("visall");
<c:forEach var="user" items="${buyerMap}" varStatus="status">
       	Q[${status.count-1}] = mygrid.getColumn("Q_${user.value.userId}");
       	V[${status.count-1}] = mygrid.getColumn("V_${user.value.userId}");
</c:forEach>
	
		if (checked) {
			visAll.show();
			for(i=0; i<Q.length; i++) {
				Q[i].hide();
				V[i].show();
			}
		}
		else {
			visAll.hide();
			for(i=0; i<Q.length; i++) {
				V[i].hide();
				Q[i].show();
			}
		}
		if (visToggled || force) visToggled = false;
		else visToggled = true;
		visFlag = checked;
		
	}

	function addRow() {
		/* hide visibility columns */
		toggleVisibilities2(false, true);
		/**/
		mygrid.insert("", true, 0);
		mygrid.bodyDiv.scrollTop = 0;
	}
	
	
	
	function validateResponseOnIframe(jsonResponse) {
		var obj = _.unserialize(jsonResponse);
		//alert("jsonResponse: " + jsonResponse);
		var error = obj.globals;
		if (obj.fail == 'true') {
			//alert(error[0]);
			if (obj.failMessage && obj.failMessage.trim()!=""){
				alert(obj.failMessage);
			}
			return false;
		}
		//ENHANCEMENT START 20120731: Rhoda Redmine 68
		var concurrencyResp = obj.concurrencyResp;
		if (concurrencyResp) {
	        if (concurrencyResp.concurrencyMsg != null && concurrencyResp.concurrencyMsg.trim() != ""){
	            window.parent.orderSheet.alertConcurrencyMsg(concurrencyResp);
	            return false;
	        }
		}
		//ENHANCEMENT END 20120731: Rhoda Redmine 68
		return true;
	}

	var g_storedBGColor;

	function focusMe(obj) {
		g_storedBGColor = obj.style.backgroundColor;
		//alert('focus:' + g_storedBGColor);
		obj.style.textAlign = 'left';
		obj.value = obj.value;
		obj.style.border = '1px solid #558899';
		obj.style.backgroundColor = 'white';
		obj.style.font = 'normal 11px arial, verdana, tahoma, helvetica, sans-serif';
		obj.select();
	}

	function blurMe(obj) {
		obj.style.textAlign = 'right';
		obj.value = obj.value;
		obj.style.border = '0px';
		//alert('blur:' + g_storedBGColor);
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
		//alert(colId);
		//alert(rowNo);
		var origValue = mygrid.getColumnValue(colId, rowNo);
		//if (origValue == null && rowNo != 0)
		//	rowNo = rowNo - 1;
		//origValue = mygrid.getColumnValue(colId, rowNo);
		if (value == '' || (!isNaN(value) && value>0 && value<=999999.999)) {
			if (value != origValue) {
				obj.style.backgroundColor = 'orange';
				obj.style.backgroundRepeat  = 'no-repeat';
				obj.style.backgroundPosition = 'right top';
				obj.style.backgroundImage = 'url(../../sigmagrid/lib/grid/skin/default/images/cell_updated.gif)';
				//mygrid.setColumnValue(colId, rowNo, value);
			}
			//ok
		}
		else {
			alert(mygrid.getMsg('INVALID_NUMBER'));
			value = origValue;
			obj.value = origValue;
			//mygrid.setColumnValue(colId, rowNo, origValue);
		}
		//alert(colId);
		//alert(rowNo);
		//alert(origValue);
		//alert(mygrid.isInsertRow(mygrid.activeRow));
		mygrid.setColumnValue(colId, rowNo, value);
		
	}

	function enterPress(obj, e) {
		var KeyID = (window.event) ? event.keyCode : e.keyCode;
		if (KeyID==13 || KeyID==9 || KeyID==27 ) {
			obj.blur();
		}
	}

	function transfernow(obj) {
	    for(var i=0;i<obj.length;i++){
	        //mygrid.add(obj[i]);
	    	mygrid.insert(obj[i], true, 0);
			mygrid.bodyDiv.scrollTop = 0;
	    }
    }

	function submitTempForm(param) {
		//var answer = confirm("Proceed with excel download?")
		//if (!answer) return;
		var sheetTypeId = 10001;
		<c:if test="${isAdmin eq true}">
			sheetTypeId = 10009;
		</c:if>
		var paramObj = _.unserialize(param);
		paramObj.sheetTypeId = sheetTypeId;

		var param = _.serialize(paramObj);
		var theForm = document.tempForm;
		//alert(param);
		theForm.action = "/eON/downloadExcel.json?_dxls_json=" + param;
		theForm.submit();
	}

	var NOPOOL = {};

	function getPoolItem(no){
	    var ret = null;
	    for(var i in NOPOOL){
	        if(i===no){
	            ret = NOPOOL[i];
	            break;
	        }
	    }
	    return ret;
	}

	function updateRow(row, ret){
	    $.each(ret,function(i,item){
	        var col = mygrid.getColumn(i);
	        if(col){
	            if(Sigma.$type(item)==="array"){
	                //do nothing because this is for options
	            }else{
	                record = mygrid.getRecordByRow(row);
	                mygrid.setCellValue(col.id, record, item);
	            }
	        }
	    });
	}

	function setSelectOptions(dom, options ,value){
	  dom.options.length = 0;
	  $.each(options, function(i, item){
		dom.options[dom.options.length] = new Option(item.caption, item.id, false, item.id==value);
	  });
	}

	function cacheSellerSKUGroupInfo() {
		mygrid.showWaiting();
		$.ajax({
            url: "/eON/order/sellerSelect.json",
            data: "sellerName=" + encodeURIComponent(record.sellername),
            dataType: 'json',
            async : false,
            success: function(ret){
				validateSigmaResponse(ret);
	   	 		NOPOOL[value] = ret;
	            updateRow(row,ret);
            },
            complete:function(XMLHttpRequest, textStatus){
                mygrid.hideWaiting();
            }
        });
	}

	function validateSKUGroupsAndOrderUnits () {

		var records = mygrid.getInsertedRecords();
		var i=0;
		for (i=0; i<records.length; i++) {
			if (records[i].groupname == '0' || records[i].unitorder == '0' || records[i].packageqty == '')
				return false;
		}
		
		return true;
	}
	
	function isDirty () {
		return mygrid.isDirty();
	}

	function refreshing() {
		mygrid.refresh();
	}

	function scale(value){
	  if(String(value).indexOf(".") > 0){
		  //if (String(value).indexOf(".") < String(value).length - 4) {
      	return false;
		 // }  
	  }
	  return true;
  	}

 	function negativeValidator (value) {
		if (isNaN(value) || parseFloat(value) < 0) {
			return false;
		}
		return true;
 	}
	  
  	function numberValidator(value, record, colObj, grid){
		if (value != "" && checkNumeric(value) == false){
			return grid.getMsg('INVALID_INPUT');
	  	}
		if(negativeValidator(value) == false){
			return grid.getMsg('INVALID_INPUT');
	  	}
	  	if(scale(value) == false){
			return grid.getMsg('INVALID_INPUT');
	  	}

	  	return true;
  	}

  	function unitOrderValidator (value, record, colObj, grid) {
		if (value == 0)
			return grid.getMsg('INVALID_INPUT');

		var quantityKeyValue = [];
		var ctr = 0;

		<c:choose>
			<c:when test="${allDatesView eq true}">
				<c:forEach var="dateKey" items="${dateList}" varStatus="status">
			        var key = "Q_${dateKey}";
			        quantityKeyValue[ctr] = key + "," + record[key];
			  		ctr++;
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:forEach var="company" items="${companyList}"  varStatus="status">
			  		<c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
			  			var key = "Q_${user.userId}";
			  			quantityKeyValue[ctr] = key + "," + record[key];
			  			ctr++;
			  		</c:forEach>
		    	</c:forEach>
			</c:otherwise>
		</c:choose>

		var tmp = "${unitorderRenderer}";
		var obj = _.unserialize(tmp);
		var orderUnit = getOrderUnitById(obj,value);

		var res = validateOrderUnitUI(mygrid, record['sellerId'], record['skuId'], orderUnit, quantityKeyValue, record['skumaxlimit']);

		if (res == true) {
			var url = "/eON/order/validateOrderUnit.json";
			res = validateOrderUnitBE(mygrid, record['sellerId'], record['skuId'], orderUnit, quantityKeyValue, url);
		}
		return res;
  	}

	function pkgQtyValidator(value, record, colObj, grid){
		if (value == ""){
			return grid.getMsg('INVALID_INPUT');
		}
		if (checkNumeric(value) == false){
			return grid.getMsg('INVALID_INPUT');
	  	}
		if(negativeValidator(value) == false){
			return grid.getMsg('INVALID_INPUT');
	  	}
		if(scale(value) == false){
			return grid.getMsg('INVALID_INPUT');
	  	}
	  	return true;
	}

	function checkNumeric(value){
		var anum=/(^\d+$)/;
	    if (anum.test(value)){
	    	return true;
	    }
	    return false;
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
		
		grid.setCellValue("pricewtax", record, calculatePriceWithTax(value, 'ROUND'));
		//autoCalculatePrice(value, oldValue, record, row, col, grid);
		//renderTotals(grid);
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
	<c:choose>
		<c:when test="${allDatesView eq false}">
			<table id="TB" border=0 width="100%">
				<tr>
					<td width="86%" align="right">
						<p id="priceInfo">
						</p>
					</td>
					<td>
						<c:if test="${allDatesView eq false}">
							<input type="checkbox" name="visToggle" id="visToggle" onclick="toggleVisibilities2(this.checked)">&nbsp;&nbsp;店舗案内設定</input>
						</c:if>
					</td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
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
		</c:otherwise>
	</c:choose>
</div>

<table id="myHead1" style="display: none">
	<tr style="background-color: #99CCFF; text-align: center;">
		<td rowspan="2" columnId='skuId'></td>
		<td rowspan="2" columnId='lockflag'>Lock Flag</td>
		<td rowspan="2" columnId='sellerId'></td>
		<td rowspan="2" columnId='myselect'></td>
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
		<td rowspan="2" columnId='price1'><c:out value="${eONHeader.skuColumnMap.price1.headerSheet}" /></td>
		<td rowspan="2" columnId='price2'><c:out value="${eONHeader.skuColumnMap.price2.headerSheet}" /></td>
		<td rowspan="2" columnId='pricewotax'><c:out value="${eONHeader.skuColumnMap.pricewotax.headerSheet}" /></td>
		<td rowspan="2" columnId='pricewtax'><c:out value="${eONHeader.skuColumnMap.pricewtax.headerSheet}" /></td>
		<td rowspan="2" columnId='column10'><c:out value="${eONHeader.skuColumnMap.column10.headerSheet}" /></td>
		<td rowspan="2" columnId='column05'><c:out value="${eONHeader.skuColumnMap.column05.headerSheet}" /></td>
		<td rowspan="2" columnId='column11'><c:out value="${eONHeader.skuColumnMap.column11.headerSheet}" /></td>
		<td rowspan="2" columnId='packageqty'><span class="requiredColumn">＊</span><c:out value="${eONHeader.skuColumnMap.packageqty.headerSheet}" /></td>
		<td rowspan="2" columnId='packagetype'><c:out value="${eONHeader.skuColumnMap.packagetype.headerSheet}" /></td>
		<td rowspan="2" columnId='unitorder'><span class="requiredColumn">＊</span><c:out value="${eONHeader.skuColumnMap.uom.headerSheet}" /></td>
		
<c:choose>
  <c:when test="${allDatesView eq false}">
  		<td rowspan="2" columnId='visall'>全<br/>表<br/>示</td>
    <c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:set var="userList" value="${companyMap[company.companyId]}" />
		<td colspan="${fn:length(userList)}" align="center">${company.verticalName}</td>
    </c:forEach>
    <%--
    <c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:set var="userList" value="${companyMap[company.companyId]}" />
		<td colspan="${fn:length(userList)}" align="center">${company.verticalName}</td>
    </c:forEach> --%>
  </c:when>
  <c:otherwise>
		<td colspan="${fn:length(dateList)}" align="center">${buyerMap[oneBuyerId].verticalName}</td>
  </c:otherwise>
</c:choose>

		<td rowspan="2" columnId='skumaxlimit'>受注上限</td>
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
		<td rowspan="2" columnId='profitInfo'>Profit Info</td>
		<td rowspan="2" columnId='externalSkuId'>External SKU ID</td>
	</tr>
	<tr style="background-color: #99CCFF">
<c:choose>
  <c:when test="${allDatesView eq false}">
    <c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
        <td id='Q_${user.userId}' align="center" ${user.verticalNameWithMark}>${user.verticalName}</td>
	  </c:forEach>
    </c:forEach>
    <c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
        <td id='V_${user.userId}' style="display:none" align="center">${user.verticalName}</td>
	  </c:forEach>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <c:forEach var="dateKey" items="${dateList}" varStatus="status">
	  <c:set var="dateCol" value="${dateMap[dateKey]}" />
		<td id='Q_${dateKey}' align="center" ${dateMarkMap[dateKey]}>${dateCol}</td>
    </c:forEach>
  </c:otherwise>
</c:choose>
	</tr>
</table>
<form name="tempForm" method="post">
</form>
</body>
</html>