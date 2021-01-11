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
 * 20120731		Rhoda	v11			Redmine 68 - Order Sheet Concurrency
 * 20120913		Lele	chrome		Redmine 880 - Chrome compatibility
 * 20120913		Mel 	v14.01		Redmine 1110 - Change the position of each strings in cells/文字列の位置の変更
 
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
<meta http-equiv="X-UA-Compatible" content="chrome=1" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
<%-- <meta http-equiv="Content-Language" content="en-us" /> --%>
<%-- 
<link rel="stylesheet" type="text/css" href="../../sigmagrid/lib/css/styles.css" />
<link rel="stylesheet" type="text/css" href="../../sigmagrid/lib/grid/gt_grid.css" />
<link rel="stylesheet" type="text/css" href="../../sigmagrid/lib/grid/skin/vista/skinstyle.css" />

<script type="text/javascript" src="../../sigmagrid/lib/grid/gt_msg_en.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../sigmagrid/lib/src/gt_grid_all.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../../runtime/jsLinb/js/linb-all.js"></script>
--%>

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
		    {name : 'lockflag'},
		    {name : 'sellerId', type : 'int'},
		    //{name : 'myselect'},
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
			//{name : 'price1', type: 'float' },
			//{name : 'price2', type: 'float' },
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
			{name : 'B_skuComment'},
			{name : 'skumaxlimit', type: 'float'},
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
			{name : 'rowqty', type: 'float'}
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
			{name : 'profitInfo'}
  </c:otherwise>
</c:choose>
		],
		recordType : 'json'
	}
	
	var _columns = [
		{id: 'skuId', header:"SKU Id", width : 40, editable : false, hidden : true},
		{id: 'skuBaId', header:"SKU Id", width : 40, editable : false, hidden : true},
		{id: 'lockflag', header:"JSON", width : 200, editable : false, hidden : true},
		{id: 'sellerId', header:"Seller Id", width : 40, editable : false, hidden : true},
    	//{id: 'myselect', header:"Select", align:"center", width : 25, hideable : false, editable : false, renderer : deleteCheckBoxRenderer },
    	{id: 'companyname' , header: "Companyname" , align:"left" , width : "${user.preference.widthColumn.companyName}", editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.companyName}")},

    	{id: 'sellername' , header: "Sellername" , align:"left" , width : "${user.preference.widthColumn.sellerName}", editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.sellerName}"), 
        	editor : {type: "select", options : ${sellerNameList}}

<c:choose>
	<c:when test="${isAdmin eq true}">
	,afterEdit : function(value, oldValue, record, row, col, grid) {
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
		
        {id: 'groupname' , header: "Groupname" , align:"left" , width : "${user.preference.widthColumn.groupName}", editable : false, 
			hidden : toSigmaBoolean("${user.preference.hideColumn.groupName}"), 
		    editor : {type: "select", 
				validator : function(value,record,colObj,grid) {
					if (value == "0") {
						return grid.getMsg('INVALID_INPUT');
					}
				}
			
<c:choose>
	<c:when test="${isAdmin eq true}">
	        ,onEditorInit: function(value, record, row, column, grid) {
		        var i=0;
	            var dom = this.valueDom;
	            var item = getPoolItem(record.sellername);
	            var options = item ? item["groupname"]:[];
	            //alert("options: " + _.serialize(options));
	            setSelectOptions(dom, options, value);
	        }
	</c:when>
	<c:otherwise>
		, options : ${skuGroupList}
	</c:otherwise>
</c:choose>
			, defaultText : '0'},
			renderer : groupnameRenderer
		 },
    	{id: 'marketname' , header: "Marketname" , align:"left" , width : "${user.preference.widthColumn.marketCondition}", editable : false, editor : {type: "text", maxLength: 42}, toolTip : true, toolTipWidth : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.marketCondition}")},
    	{id: 'column01', header: "Column 01", align: "right", width : "${user.preference.widthColumn.column01}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column01}")},
    	{id: 'column02', header: "Column 02", align:"left", width : "${user.preference.widthColumn.column02}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column02}")},
    	{id: 'column03', header: "Column 03", align:"left", width : "${user.preference.widthColumn.column03}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column03}")},
    	{id: 'column04', header: "Column 04", align:"left", width : "${user.preference.widthColumn.column04}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column04}")},
    	{id: 'column06', header: "Column 06", align:"left", width : "${user.preference.widthColumn.column06}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column06}")},
    	{id: 'column07', header: "Column 07", align:"left", width : "${user.preference.widthColumn.column07}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column07}")},
    	{id: 'column08', header: "Column 08", align:"left", width : "${user.preference.widthColumn.column08}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column08}")},
    	{id: 'column09', header: "Column 09", align:"left", width : "${user.preference.widthColumn.column09}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column09}")},
    	{id: 'skuname' , header: "Skuname" , align:"left" , width : "${user.preference.widthColumn.skuName}", editable : false, editor : {type: "text", maxLength: 42, validRule : ['R']}, hidden : toSigmaBoolean("${user.preference.hideColumn.skuName}")},
    	{id: 'home' , header: "Home" , align:"left" , width : "${user.preference.widthColumn.areaProduction}", editable : false, editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.areaProduction}")},
    	{id: 'grade' , header: "Grade" , align:"left" , width : "${user.preference.widthColumn.clazz1}", editable : false, editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.clazz1}")},
    	{id: 'clazzname' , header: "Classname" , align:"left" , width : "${user.preference.widthColumn.clazz2}", editable : false, editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.clazz2}")},
    	//{id: 'price1' , header: "Price1" , align:"center" , width :100 , editable : false, editor : {type: "text", maxLength: 9}},
    	//{id: 'price2' , header: "Price2" , align:"center" , width :100 , editable : false, editor : {type: "text", maxLength: 9, validRule : ['N']}, renderer : zeroRenderer},
    	{id: 'pricewotax' , header: "Price W/O Tax" , align:"right" , width : "${user.preference.widthColumn.priceWOTax}", editable : false, editor : {type: "text", maxLength: 9, validRule : ['N'], validator : numberValidator}, renderer : zeroRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.priceWOTax}")},
    	{id: 'pricewtax' , header: "Price W/ Tax" , align:"right" , editable : false, width : "${user.preference.widthColumn.priceWTax}", renderer : pricewtaxRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.priceWTax}")},
    	{id: 'B_purchasePrice' , header: "Piece Price" , align:"right" , width : "${user.preference.widthColumn.purchasePrice}", editable : false, editor : {type: "text", maxLength: 9, validRule : ['N'], validator : numberValidator}, renderer : buyerPriceRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.purchasePrice}")},
    	{id: 'column10', header: "Column 10", align:"left", width : "${user.preference.widthColumn.column10}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column10}")},
    	{id: 'column05', header: "Column 05", align:"left", width : "${user.preference.widthColumn.column05}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column05}")},
    	{id: 'column11', header: "Column 11", align:"left", width : "${user.preference.widthColumn.column11}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column11}")},
    	{id: 'B_sellingPrice' , header: "Selling Price" , align:"right" , editable : false, width : "${user.preference.widthColumn.sellingPrice}", editor : {type: "text", maxLength: 9, validRule : ['N'], validator : numberValidator}, renderer : buyerPriceRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.sellingPrice}"), afterEdit : autoCalculateFromSellPrice},
    	{id: 'B_sellingUom' , header: "Selling UOM" , align:"left" , width : "${user.preference.widthColumn.sellingUom}", editable : false, editor : {type: "select" ,options : ${sellingUomList} ,defaultText : '0' }, renderer : sellingUomRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.sellingUom}")},
    	{id: 'B_profitPercentage' , header: "Profit Percentage" , align:"right" , editable : false, width : "${user.preference.widthColumn.profitPercentage}", renderer : percentRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.profitPercentage}")},
    	{id: 'packageqty' , header: "Quantity" , align:"right" , width : "${user.preference.widthColumn.packageQty}", editor : {type: "text", maxLength: 9, validator : pkgQtyValidator}, renderer : zeroRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.packageQty}"), afterEdit : autoCalculateFromPckQty},
    	{id: 'packagetype' , header: "Type" , align:"left" , width : "${user.preference.widthColumn.packageType}", editable : false, editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.packageType}")},
    	{id: 'unitorder' , header: "Unit Order" , align:"left" , width : "${user.preference.widthColumn.uom}", editable : false, editor : {type: "select" ,options : ${orderUnitList} ,defaultText : '0' }, renderer : unitorderRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.uom}")},
<c:choose>
  <c:when test="${allDatesView eq false}">
  {id: 'visall', header: "visall" , align:"center" , width :35 , hidden: true, renderer:render_checkbox_all},
    <c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
		<%-- {id: 'Q_${user.userId}' , header: "${user.userName}" , align:"right" , width :35 , renderer:render_quantity }, --%>
		{id:'Q_${user.userId}', header:"${user.userName}" ,align:"right", width:35, editor:{type:"text", maxLength:13, validator : quantity_validator, pattern:"[0-9]*"}, renderer : quantityRenderer, afterEdit : autoCalculateFromQuantity },
	  </c:forEach>
    </c:forEach>
    <c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
	    {id: 'V_${user.userId}' , header: "${user.userName}" , align:"center" , width :35 , hidden: true, renderer:render_checkbox},
	  </c:forEach>
    </c:forEach>    
  </c:when>
  <c:otherwise>
    <c:forEach var="dateKey" items="${dateList}" varStatus="status">
 	  <c:set var="dateCol" value="${dateMap[dateKey]}" />
 		<%-- {id: 'Q_${dateKey}' , header: "${dateCol}" , align:"right" , width :35 , renderer:render_quantity }, --%>
 		{id:'Q_${dateKey}', header:"${dateCol}", align:"right", width:35, editor:{type:"text", maxLength:13, validator:quantity_validator_weekly, pattern:"[0-9]*"}, renderer : quantityRenderer, afterEdit : autoCalculateFromQuantity },
    </c:forEach>
  </c:otherwise>
</c:choose>
	{id: 'B_skuComment' , header: "SKU Comments" , align:"left" , width : "${user.preference.widthColumn.skuComment}",  toolTip : true , toolTipWidth : 100, editable : false, editor : {type:"textarea",width:"100px",height:"150px"}, hidden : toSigmaBoolean("${user.preference.hideColumn.skuComment}"), renderer : commentRenderer},
	{id: 'skumaxlimit', header: "SKU Max Limit", width : "${user.preference.widthColumn.skuMaxLimit}", editable : false, hidden : true},
<c:choose>
  <c:when test="${allDatesView eq false}">
	{id: 'column12', header: "Column 12", align:"left", width : "${user.preference.widthColumn.column12}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column12}")},
	{id: 'column13', header: "Column 13", align:"left", width : "${user.preference.widthColumn.column13}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column13}")},
	{id: 'column14', header: "Column 14", align:"left", width : "${user.preference.widthColumn.column14}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column14}")},
	{id: 'column15', header: "Column 15", align:"left", width : "${user.preference.widthColumn.column15}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column15}")},
	{id: 'column16', header: "Column 16", align:"left", width : "${user.preference.widthColumn.column16}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column16}")},
	{id: 'column17', header: "Column 17", align:"left", width : "${user.preference.widthColumn.column17}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column17}")},
	{id: 'column18', header: "Column 18", align:"left", width : "${user.preference.widthColumn.column18}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column18}")},
	{id: 'column19', header: "Column 19", align:"left", width : "${user.preference.widthColumn.column19}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column19}")},
	{id: 'column20', header: "Column 20", align:"left", width : "${user.preference.widthColumn.column20}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column20}")},
	{id: 'rowqty' , header: "Row Qty" , align:"right" , width : "${user.preference.widthColumn.rowQty}", renderer:rowqtyRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.rowQty}")},
	{id: 'profitInfo', header:"Profit Info", width : 200, editable : false, hidden : true}
  </c:when>
  <c:otherwise>
	{id: 'rowqty' , header: "Row Qty" , align:"right" , width : "${user.preference.widthColumn.rowQty}", renderer:rowqtyRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.rowQty}")},
	{id: 'column12', header: "Column 12", align: "left", width : "${user.preference.widthColumn.column12}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column12}")},
	{id: 'column13', header: "Column 13", align: "left", width : "${user.preference.widthColumn.column13}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column13}")},
	{id: 'column14', header: "Column 14", align: "left", width : "${user.preference.widthColumn.column14}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column14}")},
	{id: 'column15', header: "Column 15", align: "left", width : "${user.preference.widthColumn.column15}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column15}")},
	{id: 'column16', header: "Column 16", align: "left", width : "${user.preference.widthColumn.column16}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column16}")},
	{id: 'column17', header: "Column 17", align: "left", width : "${user.preference.widthColumn.column17}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column17}")},
	{id: 'column18', header: "Column 18", align: "left", width : "${user.preference.widthColumn.column18}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column18}")},
	{id: 'column19', header: "Column 19", align: "left", width : "${user.preference.widthColumn.column19}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column19}")},
	{id: 'column20', header: "Column 20", align: "left", width : "${user.preference.widthColumn.column20}", editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column20}")},
	{id: 'profitInfo', header:"Profit Info", width : 200, editable : false, hidden : true}
  </c:otherwise>
</c:choose>
    ];
    
	//dynamic height for sigma grid based on browser's height
    var sigmaHeight = window.parent.g_clientHeight - 195;
    if(sigmaHeight < 388) sigmaHeight = 388;

	var gridOption={
		id : grid_demo_id,
		encoding:'UTF-8',
		loadURL : '<c:url value="/buyerorder/loadOrder.json" />',
		saveURL : '<c:url value="/buyerorder/saveOrder.json" />',
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
		sellingPrice : toProfitVisibility("${user.preference.profitPreference.totalSellingPrice}"),
		profit : toProfitVisibility("${user.preference.profitPreference.totalProfit}"),
		profitPercentage : toProfitVisibility("${user.preference.profitPreference.totalProfitPercent}"),
		skin : "default",
		customRowAttribute : function(record,rn,grid){
			var lockflagObj = _.unserialize(record.lockflag);
			if (lockflagObj.sku == '1' || lockflagObj.proposed == '1') return 'style="background-color:#eee9e9"';
			else return 'style="height:30px;"';
							 },

<c:choose>
	<c:when test="${isAdmin eq false}">
		defaultRecord : { sellerId : "${user.userId}", companyname: "${user.company.shortName}", sellername : "${user.shortName}", groupname : "0", unitorder : "0", lockflag : " {'allQuantities' : 1, 'sellername' : 1}",
	</c:when>
	<c:otherwise>
		defaultRecord : { skuId : "0", groupname : "0", unitorder : "0", lockflag : " {'adminAddSKU' : 1}",
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
marketname : "", skuname : "", home : "", grade : "", clazzname : "", pricewotax : "", packageqty : "", packagetype : "", rowqty : "" },

		loadResponseHandler : function(response, requestParameter) {

			var obj = _.unserialize(response.text);
			
			// Get Totals
			mygrid.setTotals(obj.totals);
			mygrid.setProfitVisibility(obj.profitVisibility);
			
			$("#priceInfo").html(
					window.
					parent.
					orderSheet.
					computeForPrices(obj.profitVisibility, obj.totals));

			// Get Grand Totals
			mygrid.setGrandTotals(obj.grandTotals);
			
			window.parent.orderSheet.visToggle.setDisabled(false);
			window.parent.orderSheet.cboBuyers.setDisabled(false);
			window.parent.orderSheet.linkPrevious.setVisibility('visible');
			window.parent.orderSheet.linkNext.setVisibility('visible');
			window.parent.orderSheet.setDatePanel(false);
			//window.parent.orderSheet.btnDownloadExcel.setDisabled(false);

			// enable disable action buttons
			window.parent.orderSheet.disableActionButtons();
			var flags = _.unserialize(obj.buttonFlags);
			//alert(flags.btnLock);
			if (flags != null) {
				if (flags.btnLock == 1 && flags.btnFinalize != 1) { // locked
					window.parent.orderSheet.btnLock.setDisabled(true);
					window.parent.orderSheet.btnUnlock.setDisabled(false);
					window.parent.orderSheet.btnSave.setDisabled(true);
					//window.parent.orderSheet.btnFinalize.setDisabled(false);
				} else {
					if (flags.btnFinalize != 1){
						window.parent.orderSheet.btnLock.setDisabled(false);
						window.parent.orderSheet.btnSave.setDisabled(false);
					}else {
						window.parent.orderSheet.btnLock.setDisabled(true);
						window.parent.orderSheet.btnSave.setDisabled(true);
					}
					window.parent.orderSheet.btnUnlock.setDisabled(true);
					//window.parent.orderSheet.btnFinalize.setDisabled(true);
				}
				
				if (flags.btnFinalize == 1) { // finalized
					//window.parent.orderSheet.btnAddSKU.setDisabled(true);
					//window.parent.orderSheet.btnAddSKUGroup.setDisabled(true);
					window.parent.orderSheet.btnSave.setDisabled(true);
					window.parent.orderSheet.btnLock.setDisabled(true);
					window.parent.orderSheet.btnUnlock.setDisabled(true);
				} //else { 
					//window.parent.orderSheet.btnAddSKU.setDisabled(false);
					//window.parent.orderSheet.btnAddSKUGroup.setDisabled(false);
					//window.parent.orderSheet.btnSave.setDisabled(false);
				//}

				if (flags.btnSave == 1) { // not saved
					window.parent.orderSheet.btnSave.setDisabled(true);
					window.parent.orderSheet.btnLock.setDisabled(true);
					window.parent.orderSheet.btnUnlock.setDisabled(true);
				}
			}
			
			var confirmMsg = obj.confirmMsg;
			if (confirmMsg != null && confirmMsg != "") 
				alert(confirmMsg);
			
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
			if (result) {
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
		    
		    if (lockflagObj == null) {
				return;
			}
		    if (col.id == 'sellername' && lockflagObj.sellername == '1') return false;

		    <c:choose>
			    <c:when test="${isAdmin eq true}">
			    	if (lockflagObj.adminAddSKU == '1' && col.id != 'sellername') return false;
				</c:when>
			</c:choose>
		    
		    if (!col.id.startsWith("Q")) {
				if (lockflagObj.sku == '1' || lockflagObj.proposed == '1')
					return false;
		    }

		    if (1==0) {}
		    <c:choose>
		      <c:when test="${allDatesView eq false}">
		        <c:forEach var="company" items="${companyList}"  varStatus="status">
		    	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
		    	  		else if (col.id == 'Q_${user.userId}') {
		    	  			var obj = document.getElementById("cBox-V_${user.userId}-" + existRowStart);
		    	  			if (obj == null) return false;
		    	  			if (lockflagObj.Q_${user.userId} == '1' || !obj.checked) return false;
		    		  		if (lockflagObj.allQuantities == '1' || lockflagObj.Q_${user.userId} == '1') return false;
		    	  		}
		    	  </c:forEach>
		        </c:forEach>
		      </c:when>
		      <c:otherwise>
		        <c:forEach var="dateKey" items="${dateList}" varStatus="status">
		    	  <c:set var="dateCol" value="${dateMap[dateKey]}" />
		    	  		else if (col.id == 'Q_${dateKey}') {
		    		  		if (lockflagObj.allQuantities == '1' || lockflagObj.Q_${dateKey} == '1') return false;
		    	  		}
		        </c:forEach>
		      </c:otherwise>
		    </c:choose>
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

	function quantityRenderer (value , record, columnObj, grid, colNo, rowNo) {
		return addCommas(value);
	}

	function commentRenderer (value , record, columnObj, grid, colNo, rowNo) {
		return "<div title='" + value + "'>" + value + "<div>";
	}

	function skuMaxLimit_validator(value, record, colObj, grid) {
		value = value.trim();
		var toProcess = (!isNaN(value) && value>0 && value<=999999999.999);
		if (value == '' || toProcess == true) {
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
		value = value.trim();
		var tmp = "${unitorderRenderer}";
		var obj = _.unserialize(tmp);
		var toProcess = (!isNaN(value) && value>=0 && value<=999999999.999);
		var isValidDecimalInput = validateQuantityScale(getOrderUnitById(obj,record["unitorder"]),value);
		if (value == '' || (toProcess == true && isValidDecimalInput ==true)) {
			if (record.skumaxlimit == "")
				return;
			// check for sku max limit
			var tmp = validateQuantityLimit(record, colObj.id, value);
			return tmp;
		}
		else {//invalid
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
	
	function validateQuantityLimit (record, colId, value) {
		if (value != '' && parseInt(value) > 0) {
			var errMsg = Sigma.$msg(mygrid.getMsg('BUYER_SKU_MAX_LIMIT_EXCEEDED'), record.skumaxlimit);
			var cond1 = validateQuantityLimitUI (record, colId, value);
			//alert("cond1: " + cond1);
			//if (cond1 != false) {
				var skumaxlimit;
			    if (colId == 'skumaxlimit') skumaxlimit = value;
			    else skumaxlimit = record.skumaxlimit;
			    
				var url = "/eON/order/quantityLimit.json?sellerId=" + record.sellerId;
				url = url + "&skuId=" + record.skuId;
				url = url + "&skuMaxLimit=" + skumaxlimit;
				url = url + "&cond1=" + cond1;
				url = url + "&value=" + value;
				
		        var eps = 0;
		        var availableQty = 0;
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
							availableQty = response.availableQty;
						}
		            },
		            complete:function(XMLHttpRequest, textStatus){
		                mygrid.hideWaiting();
		            }
		        });
		        if (eps == 1)return Sigma.$msg(mygrid.getMsg('BUYER_SKU_MAX_LIMIT_EXCEEDED'), availableQty);
		        //if (eps == 1) return errMsg;
			//} else {
				//return errMsg;
			//}
		}
	}

	function validateQuantityLimitUI (record, colId, value) {
		var totalQty = 0;
		var otherTotalQty = 0;
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
	    //if (skumaxlimit > 0 && skumaxlimit < (totalQty)) {
	    	//return false; 
	    //} else {
	    	quantityKeys[ctr] = parseFloat(totalQty);
	    	//ctr++;
    		//uantityKeys[ctr] = parseFloat(otherTotalQty);
		    return quantityKeys;
	    //}
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

		return "<input type=checkbox id='cBox-" + columnObj.id + "-" + rowNo + "' " + checked + " " + disabled + " onclick=cBoxAllMethod(this);></input>";
	}

	function deleteCheckBoxRenderer (value , record, columnObj, grid, colNo, rowNo) {
		var disabled = "";
		var lockflagObj = _.unserialize(mygrid.getColumnValue("lockflag", rowNo));
		if (lockflagObj != null) {
			if (lockflagObj.sku == '1')
				disabled = "disabled";
		}
		return "<input type=checkbox id='test' " + disabled + " onclick=setTimeout('mygrid.deleteRow()',100);></input>";
	}

	function pricewtaxRenderer (value , record, columnObj, grid, colNo, rowNo) {
		var tmp = window.parent.orderSheet.roundOffPriceWTax(value);
		if (value ==  "undefined") return "";
		if (tmp > 0) return addCommas(tmp);
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
	
	//first
	function cBoxAllMethod(cBoxAll) {
		var cBoxAllId = cBoxAll.id;
		var arr = cBoxAllId.split("-");
		var colId = arr[1];
		var rowNo = arr[2];
		var cBoxId = new Array();
		var visId = new Array();
		var cBoxObj;
	<c:forEach var="user" items="${buyerMap}" varStatus="status">
		cBoxId[${status.count-1}] = "cBox-V_${user.value.userId}-" + rowNo;
		visId[${status.count-1}] = "V_${user.value.userId}";
	</c:forEach>

		for(i=0; i<cBoxId.length; i++) {
			cBoxObj = document.getElementById(cBoxId[i]);
			if (cBoxObj != null) {
				cBoxObj.checked = cBoxAll.checked;
				visFlag = 1;
				if (!cBoxObj.checked) visFlag = 0;
				mygrid.setColumnValue(visId[i], rowNo, visFlag);
			}
		}		
	}

	function cBoxMethod(cBoxObj) {
		//var thisGrid = Sigma.$grid(grid_demo_id);
		var buyerCount = ${fn:length(buyerMap)};
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
		//var test = mygrid.getColumnValue(Q_buyer, rowNo);
		//var textObj = document.getElementById("tBox-" + Q_buyer + "-" + rowNo);
		//alert(textObj);
		
		var lockflag = mygrid.getColumnValue("lockflag", rowNo);
		//alert(test);
		//var obj = _.unserialize(test);

		//alert(obj.Q_4);
		//alert(_.serialize(obj));
		
		//alert(cBoxObj.checked);
		//alert(colId);
		//alert(rowNo);
		//alert(textObj.id);
		//alert(textObj.readOnly);
		//alert(textObj.style.backgroundColor);

		//style='background-color:#FAAFBA
		
		//var quote = "\"";
		//var re = new RegExp(quote + Q_buyer + "\":\"" + "[0-9]"+ quote, "g");
		
		if (cBoxObj.checked) {
			visFlag = 1;
			//alert(lockflag.indexOf(Q_buyer));
			//alert(lockflag.lastIndexOf(Q_buyer));
			//lockflag = lockflag.replace(re, quote + Q_buyer + "\":\"" + "0" + quote);
			//textObj.style.backgroundColor = "transparent";
			//textObj.disabled = false;
			//mygrid.setColumnValue("lockflag", rowNo, visFlag);
		}
		else {
			visFlag = 0;
			//lockflag = lockflag.replace(re, quote + Q_buyer + "\":\"" + "1" + quote);
			//textObj.value = "";
			//alert(textObj.id);
			//textObj.style.backgroundColor = "#FAAFBA"; //redish
			//textObj.disabled = true;
			//mygrid.setColumnValue("lockflag", rowNo, visFlag);
		}
		//alert(lockflag);
		//mygrid.setColumnValue("lockflag", rowNo, lockflag);

		//alert(allRows.length);
		//if (rowNo < allRows.length)
			mygrid.setColumnValue(colId, rowNo, visFlag);
		//else {
		//	cBoxObj.checked = true;
		//}
		//alert(thisGrid.getUpdatedRecords());
		//alert(cBoxObj.checked);
		var cBoxId = new Array();
		var chkCtr = 0;
	<c:forEach var="user" items="${buyerMap}" varStatus="status">
		cBoxId[${status.count-1}] = "cBox-V_${user.value.userId}-" + rowNo;
		cBoxObj = document.getElementById("cBox-V_${user.value.userId}-" + rowNo);
		if (cBoxObj.checked) chkCtr++;
	</c:forEach>

		var cBoxAllObj = document.getElementById("cBox-visall-" + rowNo);
		if (chkCtr == buyerCount)
			cBoxAllObj.checked = true;
		else
			cBoxAllObj.checked = false;
	
	}
		
	function onSave() {
		mygrid.addParameters({action:"save", option : _columns});
		var ret = validateSKUGroupsAndOrderUnits();

		if (ret != false) {
			/*ret = mygrid.save();
			if (ret != false)
				window.parent.orderSheet.disableActionButtons();

			if (ret != null) // error on input
				alert("Fill up all the required fields correctly");*/
			ret = mygrid.save();

			if (ret != null) // error on input
				alert(mygrid.getMsg('REQUIRED_FIELDS_NOT_POPULATED'));
		}
		else {
			//alert("Fill up all the required fields correctly");
			alert(mygrid.getMsg('REQUIRED_FIELDS_NOT_POPULATED'));
		}
		if (ret == false || ret == "error") {
			window.parent.orderSheet.btnSave.setDisabled(false);
		}
	}
	
	function onPublish() {
		mygrid.showWaiting();
		linb.Ajax(
			"/eON/buyerorder/publish.json",
            null,
            function (response) {
				if (validateResponseOnIframe(response)) {
					validateResponse(response);
					window.parent.orderSheet.disableActionButtons();
	            	mygrid.reload(false,true);
	            	//alert("Order Published");
	            	alert(mygrid.getMsg('PUBLISHED'));
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
		linb.Ajax(
			"/eON/seller/unpublish.json",
            null,
            function (response) {
	            if (validateResponseOnIframe(response)) {
	            	validateResponse(response);
	            	window.parent.orderSheet.disableActionButtons();
	            	mygrid.reload(false,true);
	            	//alert("Order Unpublished");
	            	alert(mygrid.getMsg('UNPUBLISHED'));
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

	function onLock() {

		var msg = mygrid.getMsg('CONFIRM_LOCK');
		var isLocked = confirm(msg);

		if(isLocked == false){
			return false;
		}

		mygrid.showWaiting();
		linb.Ajax(
			"/eON/buyerorder/lockUnlock.json?lock=Y",
            null,
            function (response) {
            	//linb.message("Order Locked");
            	//mygrid.reload(false,true);
            	//mygrid.hideWaiting();
            	//window.location.reload();
	            if (validateResponseOnIframe(response)) {
	            	validateResponse(response);
	            	window.parent.orderSheet.disableActionButtons();
					alert(mygrid.getMsg('LOCKED'));
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

	function onUnlock() {
		var msg = mygrid.getMsg('CONFIRM_UNLOCK');
		var isUnlock = confirm(msg);

		if(isUnlock == false){
			return false;
		}
		
		mygrid.showWaiting();				
		linb.Ajax(
			"/eON/buyerorder/lockUnlock.json?lock=N",
            null,
            function (response) {
            	//linb.message("Order Unlocked");
            	//mygrid.reload(false,true);
            	//mygrid.hideWaiting();
            	//window.location.reload();
	            if (validateResponseOnIframe(response)) {
	            	validateResponse(response);
	            	window.parent.orderSheet.disableActionButtons();
					alert(mygrid.getMsg('UNLOCKED'));
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

	function addRow() {
		mygrid.insert("", true, 0);
		mygrid.bodyDiv.scrollTop = 0;
	}
	
	function validateResponseOnIframe(jsonResponse) {
		var obj = _.unserialize(jsonResponse);
		var error = obj.globals;
		if (obj.fail == 'true') {
			//alert(error[0]);
            if (obj.failMessage && obj.failMessage.trim()!=""){
                alert(obj.failMessage);
            }
			return false;
		}
		
		if (obj.isOrderSheetModified && obj.isOrderSheetModified != null && obj.isOrderSheetModified !="") {
			alert(obj.isOrderSheetModified);
			return false;
		}
		
		var concurrencyResp = obj.concurrencyResp;
		if (concurrencyResp.concurrencyMsg != null && concurrencyResp.concurrencyMsg.trim() != ""){
			//ENHANCEMENT START 20120727: Rhoda Redmine 68
			window.parent.orderSheet.alertConcurrencyMsg(concurrencyResp, obj.isSellerFinalized);
			mygrid.reload(false,true);
			//ENHANCEMENT END 20120727: Rhoda Redmine 68
			return false;
		}
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
	        mygrid.add(obj[i]);
	    }
    }

	function submitTempForm(param) {
		//var answer = confirm("Proceed with excel download?")
		//if (!answer) return;
		var sheetTypeId = 10000;
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

	function validateSKUGroupsAndOrderUnits () {
		var records = mygrid.getInsertedRecords();
		var i=0;
		for (i=0; i<records.length; i++) {
			if (records[i].groupname == '0' || records[i].unitorder == '0' || records[i].packageqty == '')
				return false;
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
		if(negativeValidator(value) == false){
			return grid.getMsg('INVALID_INPUT');
	 	}
	 	if(scale(value) == false){
	  		return grid.getMsg('INVALID_INPUT');
	 	}
	
	 	return true;
	}

	function isDirty () {
		return mygrid.isDirty();
	}

	function scale(value){
		if(String(value).indexOf(".") > 0){
			 // if (String(value).indexOf(".") < String(value).length - 4) {
				  return false;
			 // }  
		}
		return true;
	}	

	function pkgQtyValidator(value, record, colObj, grid){
		if (value == ""){
			alert(grid.getMsg('INVALID_INPUT'));
			return false;
		}
		if (checkNumeric(value) == false){
			alert(grid.getMsg('INVALID_INPUT'));
			return false;
	  	}
		if(negativeValidator(value) == false){
			alert(grid.getMsg('INVALID_INPUT'));
			return false;
	  	}
		if(String(value).indexOf(".") > 0){
			alert(grid.getMsg('INVALID_INPUT'));
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

		grid.setCellValue("pricewtax", record, calculatePriceWithTax(value ,'ROUND'));
		
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
		var profitVisibility = grid.getProfitVisibility();

		$("#priceInfo").html(
				window.
				parent.
				orderSheet.
				computeForPrices(profitVisibility, curProfitInfoT));
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
		<td rowspan="2" columnId='lockflag'>Lock Flag</td>
		<td rowspan="2" columnId='sellerId'></td>
<!--		<td rowspan="2" columnId='myselect'></td>-->
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
<!--		<td rowspan="2" columnId='price1'>価格１</td>-->
<!--		<td rowspan="2" columnId='price2'>価格2</td>-->
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
	<td rowspan="2" columnId='B_skuComment'><c:out value="${eONHeader.skuColumnMap.B_skuComment.headerSheet}" /></td>
	<td rowspan="2" columnId='skumaxlimit'>受注上限</td>
	
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
    <c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
        <td id='V_${user.userId}' style="display:none" align="center">${user.verticalName}</td>
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