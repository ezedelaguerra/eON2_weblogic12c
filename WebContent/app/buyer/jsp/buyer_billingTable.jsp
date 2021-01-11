 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<c:set var="user" value="${user}" />
<c:set var="allDatesView" value="${orderSheetParam.allDatesView}" />
<%--<c:set var="allDatesView" value="false" />--%>
<c:set var="OneBuyerId" value="2" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" -->
<html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
<title>Add nested header to grid - Sigma Ajax data grid control sample</title>
<%--
<meta http-equiv="Content-Language" content="en-us" />
<link rel="stylesheet" type="text/css" href="../../sigmagrid/lib/css/styles.css" />
<link rel="stylesheet" type="text/css" href="../../sigmagrid/lib/grid/gt_grid.css" />
<link rel="stylesheet" type="text/css" href="../../sigmagrid/lib/grid/skin/vista/skinstyle.css" />
<script type="text/javascript" src="../../sigmagrid/lib/grid/gt_msg_en.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../sigmagrid/lib/grid/gt_grid_all.js?version=<c:out value="${initParam.version}"/>"></script>

<script type="text/javascript" src="../../../runtime/jsLinb/js/linb-all.js"></script>
--%>

<%--<link href="highlight/style.css" rel="stylesheet" type="text/css" />--%>
<link rel="stylesheet" type="text/css" href="../../sigmagrid2.4e/grid/css/styles.css" />
<link rel="stylesheet" type="text/css" href="../../sigmagrid2.4e/grid/gt_grid.css" />
<link rel="stylesheet" type="text/css" href="../../sigmagrid2.4e/grid/skin/vista/skinstyle.css" />

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
<script type="text/javascript" src="../../sigmagrid2.4e/grid/gt_msg_en.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../sigmagrid2.4e/grid/gt_msg_ja.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../common/util/StringUtil.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../common/util/util.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../common/util/sigma_util.js?version=<c:out value="${initParam.version}"/>"></script>
<jsp:include page="../../common/util/disable_right_click.jsp" />
<script type="text/javascript">

var grid_demo_id = "myGrid1" ;
	
	var _dataset= {
		fields :[
			{name : 'companyname'},
			{name : 'sellername'},
			{name : 'groupname'},
			{name : 'marketname'},
			{name : 'column01'},
			{name : 'column02'},
			{name : 'column03'},
			{name : 'column04'},
			{name : 'column05'},
			{name : 'skuname'},
			{name : 'home'},
			{name : 'grade'},
			{name : 'clazzname'},
			{name : 'price2', type: 'float' },
			{name : 'pricewotax', type: 'float' },
			{name : 'pricewtax', type: 'float' },
			{name : 'B_purchasePrice', type: 'float' },
			{name : 'B_sellingPrice', type: 'float' },
			{name : 'B_sellingUom'},
			{name : 'packageqty', type: 'float'},
			{name : 'packagetype'},
			{name : 'unitorder'},
			<c:if test="${showA eq true}">
			{name : 'comments'},
			</c:if>		


			<c:choose>			
				<c:when test="${allDatesView eq false}">
			  			{name : 'visall', type: 'boolean'},
			    <c:forEach var="company" items="${companyList}"  varStatus="status">
				  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
			        	{name : 'Q_${user.userId}'},
				  </c:forEach>
			    </c:forEach>
			  </c:when>
			  <c:otherwise>
			    <c:forEach var="dateKey" items="${dateList}" varStatus="status">
			            {name : 'Q_${dateKey}', type: 'float'},
			    </c:forEach>
			  </c:otherwise>
			</c:choose>				
			<c:forEach var="company" items="${companyList}"  varStatus="status">
				  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
					{name: 'Q_${user.userId}'},
				  </c:forEach>
			 </c:forEach>
					{name : 'B_skuComments'},
					{name : 'column06'},
					{name : 'column07'},
					{name : 'column08'},
					{name : 'column09'},
					{name : 'column10'},
					{name : 'column11'},
					{name : 'column12'},
					{name : 'column13'},
					{name : 'column14'},
					{name : 'column15'},
					{name : 'column16'},
					{name : 'column17'},
					{name : 'column18'},
					{name : 'column19'},
					{name : 'column20'},
			 <c:if test="${user.role.roleId eq 4 || allDatesView eq true}"> <%-- buyer admin --%>
				{name : 'rowqty', type: 'float'},
			</c:if>
			{name : 'lockflag'}
			],
			recordType : 'object'
	}
	
	var _columns = [
        {id: 'companyname' , header: "Companyname" , align:"center" , width :100 , editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.companyName}")},
        {id: 'sellername' , header: "Sellername" , align:"center" , width :100, editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.sellerName}")},
        {id: 'groupname' , header: "Groupname" , align:"center" , width :100, editable : false , editor : {type: "select", options : ${skuGroupList}}, renderer : groupnameRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.groupName}")},
        {id: 'marketname' , header: "Marketname" , align:"center" , width :100 , editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.marketCondition}")},
        {id: 'column01', header: "Column 01", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column01}")},
    	{id: 'column02', header: "Column 02", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column02}")},
    	{id: 'column03', header: "Column 03", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column03}")},
    	{id: 'column04', header: "Column 04", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column04}")},
    	{id: 'column05', header: "Column 05", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column05}")},
	    {id: 'skuname' , header: "Skuname" , align:"center" , width :100 , editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.skuName}")},
  	    {id: 'home' , header: "Home" , align:"center" , width :100 , editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.areaProduction}")},
  	    {id: 'grade' , header: "Grade" , align:"center" , width :100 , editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.clazz1}")},
  	    {id: 'clazzname' , header: "Classname" , align:"center" , width :100, editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.clazz2}")},
  	    <c:choose>
  	  		<c:when test="${user.role.roleId eq 4}">
  	  			{id: 'price2' , header: "Price2" , align:"center" , width :100 , editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.price2}")},
  	  		</c:when>
  	  		<c:otherwise>
  	  			{id: 'price2' , header: "Price2" , align:"center" , width :100 , editable : false, hidden : true},
  	  		</c:otherwise>
  	    </c:choose>
  	    {id: 'pricewotax' , header: "Price W/O Tax" , align:"center" , width :100 , editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.priceWOTax}")},
  	    {id: 'pricewtax' , header: "Price W/ Tax" , align:"center" , width :100, editable : false, renderer : pricewtaxRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.priceWTax}")},
    	{id: 'B_purchasePrice' , header: "Piece Price" , align:"center" , width :60 , editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.purchasePrice}")},
    	{id: 'B_sellingPrice' , header: "Selling Price" , align:"center" , editable : false, width :60, hidden : toSigmaBoolean("${user.preference.hideColumn.sellingPrice}")},
    	{id: 'B_sellingUom' , header: "Selling UOM" , align:"center" , width :60 , editable : false, renderer : sellingUomRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.sellingUom}")},
    	{id: 'packageqty' , header: "Quantity" , align:"center" , width :60 , editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.packageQty}")},
  	    {id: 'packagetype' , header: "Type" , align:"center" , width :60 , editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.packageType}")},
  	    {id: 'unitorder' , header: "Unit Order" , align:"center" , width :80 , editable : false , editor : {type: "select" ,options : ${orderUnitList}}, renderer : unitorderRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.uom}")},
	  	<c:if test="${showA eq true}">
	  	    	{id: 'comments' , header: "Comments" , align:"center" , width :100, editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.skuComment}")},
	  	</c:if>   
	  	       	
   	    <c:choose>
   	     <c:when test="${allDatesView eq false}">
   	       <c:forEach var="company" items="${companyList}"  varStatus="status">
   	   	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
   	   		<%-- {id: 'Q_${user.userId}' , header: "${user.userName}" , align:"right" , width :35 , renderer:render_quantity }, --%>
   	   	{id:'Q_${user.userId}', header:"${user.userName}" ,align:"right", width:35, editable : false},
   	   	  </c:forEach>
   	       </c:forEach>
   	     </c:when>
   	     <c:otherwise>
   	       <c:forEach var="dateKey" items="${dateList}" varStatus="status">
   	    	  <c:set var="dateCol" value="${dateMap[dateKey]}" />
   	    		<%-- {id: 'Q_${dateKey}' , header: "${dateCol}" , align:"right" , width :35 , renderer:render_quantity }, --%>
   	    		{id:'Q_${dateKey}', header:"${dateCol}", align:"right", width:35, editable : false},
   	       </c:forEach>
   	     </c:otherwise>
   	   </c:choose>	
        
   		{id: 'B_skuComments' , header: "SKU Comments" , align:"center" , width :100,  toolTip : true , toolTipWidth : 100, editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.skuComment}"), renderer : commentRenderer},
   		{id: 'column06', header: "Column 06", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column06}")},
		{id: 'column07', header: "Column 07", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column07}")},
		{id: 'column08', header: "Column 08", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column08}")},
		{id: 'column09', header: "Column 09", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column09}")},
		{id: 'column10', header: "Column 10", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column10}")},
		{id: 'column11', header: "Column 11", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column11}")},
		{id: 'column12', header: "Column 12", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column12}")},
		{id: 'column13', header: "Column 13", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column13}")},
		{id: 'column14', header: "Column 14", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column14}")},
		{id: 'column15', header: "Column 15", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column15}")},
		{id: 'column16', header: "Column 16", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column16}")},
		{id: 'column17', header: "Column 17", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column17}")},
		{id: 'column18', header: "Column 18", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column18}")},
		{id: 'column19', header: "Column 19", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column19}")},
		{id: 'column20', header: "Column 20", align: "center", width : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.column20}")},
    	<c:if test="${user.role.roleId eq 4 || allDatesView eq true}"> <%-- buyer admin --%>
	    {id: 'rowqty' , header: "Row Qty" , align:"center" , width :50 , editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.rowQty}")},
	    </c:if>
	    {id:'lockflag', header:"lockflag", align:"center", width:300, hidden:true}
    ];

	//dynamic height for sigma grid based on browser's height
    var sigmaHeight = window.parent.g_clientHeight - 203;
    if(sigmaHeight < 380) sigmaHeight = 380;

	var gridOption={
			id : grid_demo_id,
			loadURL : '<c:url value="/buyerbilling/loadOrder.json" />',
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
			skin : "default",
			customRowAttribute : function(record,rn,grid){
			if (record.typeflag==2)
				return 'style="height:30px; color:#ff0000; background-color:#eee9e9"';
			else
				return 'style="height:30px; background-color:#bfbfbf"';
			},

		loadResponseHandler : function(response, requestParameter){
			var obj = _.unserialize(response.text);
			window.parent.billingSheet.computeForTotalPrices(obj.totalPriceWOTaxOnDisplay, obj.totalPriceWTaxOnDisplay);
			//window.parent.orderSheet.visToggle.setDisabled(false);
			window.parent.billingSheet.cboBuyers.setDisabled(false);
			window.parent.billingSheet.linkPrevious.setVisibility('visible');
			window.parent.billingSheet.linkNext.setVisibility('visible');
			window.parent.billingSheet.setDatePanel(false);

			var confirmMsg = obj.confirmMsg;
			if (confirmMsg != null && confirmMsg != "") 
				alert(confirmMsg);			
		},

		beforeEdit : function(value,record,col,grid) {
			return false;
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
		else if (columnObj.id == "pricewtax") {
			return "<div class='higlightedPriceWTax'>" + name + "</div>";
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

	function render_checkbox(value , record, columnObj, grid, colNo, rowNo){
		//alert(value);
		//alert(columnObj.width);
		//alert(columnObj.id);
		//alert(record.Q_2);
		//var data = grid.getRecord(record);
		//data['Q_2'] = 555;
		//alert(data['Q_2']);
		
		//grid.setColumnValue("Q_2", rowNo, "343");
		//alert(grid.getUpdatedRecords());
		//alert(columnObj.value);
		//return "<input type=checkbox id='cBox_" + columnObj.id + rowNo + "' checked='" + value +
		//"' onclick=cBoxMethod(this, '" + columnObj.id + "','" + rowNo + "');></input>";
		return "<input type=checkbox id='cBox-" + columnObj.id + "-" + rowNo + "' checked='" + value +
			"' onclick=cBoxMethod(this);></input>";			
	}

	function commentRenderer (value , record, columnObj, grid, colNo, rowNo) {
		return "<div title='" + value + "'>" + value + "<div>";
	}
	
	function cBoxMethod(cBoxObj) {
		//var thisGrid = Sigma.$grid(grid_demo_id);
		var allRows = mygrid.getAllRows();
		//alert(_.serialize(allRows));
		var cBoxId = cBoxObj.id;
		var arr = cBoxId.split("-");
		var colId = arr[1];
		var rowNo = arr[2];
		var visFlag = 1;
		//alert(cBoxObj.checked);
		//alert(colId);
		//alert(rowNo);
		if (cBoxObj.checked) visFlag = 1;
		else visFlag = 0;

		//alert(allRows.length);
		if (rowNo < allRows.length)
			mygrid.setColumnValue(colId, rowNo, visFlag);
		else {
			cBoxObj.checked = true;
		}
		//alert(thisGrid.getUpdatedRecords());
		//alert(cBoxObj.checked);
	}

	function reloadTable() {
		mygrid.reload();
	}

	function responseCheck (jsonResponse) {
		var obj = _.unserialize(jsonResponse);
		if (obj.fail == 'true') {
			linb.pop("responseCheck", obj.globals, "OK");
		}
	}

	function submitTempForm(param) {
		//var answer = confirm("Proceed with excel download?")
		//if (!answer) return;
		var sheetTypeId = 10006;
		<c:if test="${user.role.roleId eq 4}"> <%-- buyer admin --%>
			sheetTypeId = 10013;
		</c:if>
		var paramObj = _.unserialize(param);
		paramObj.sheetTypeId = sheetTypeId;

		var param = _.serialize(paramObj);
		var theForm = document.tempForm;
		//alert(param);
		theForm.action = "/eON/downloadExcel.json?_dxls_json=" + param;
		theForm.submit();
	}

	function pricewtaxRenderer (value , record, columnObj, grid, colNo, rowNo) {
		var tmp = window.parent.billingSheet.roundOffPriceWTax(value);
		if (value ==  "undefined") return "";
		if (tmp > 0) return addCommas(tmp);
		else return "";
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

	function groupnameRenderer (value , record, columnObj, grid, colNo, rowNo) {
		var user = "S_" + record.sellerId;
		var tmp = "${skuGroupRenderer}";
		var map = _.unserialize(tmp);
		var render = "";
		if (map[user] != null) {
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

	function isDirty () {
		return mygrid.isDirty();
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

</script>
</head>
<body>
<table id="myHead1" style="display: none">
	<tr style="background-color: #99CCFF; text-align: center;">
		<td rowspan="2" columnId='companyname'><c:out value="${eONHeader.skuColumnMap.companyname.headerSheet}" /></td>
		<td rowspan="2" columnId='sellername'><c:out value="${eONHeader.skuColumnMap.sellername.headerSheet}" /></td>
		<td rowspan="2" columnId='groupname'><c:out value="${eONHeader.skuColumnMap.groupname.headerSheet}" /></td>
		<td rowspan="2" columnId='marketname'><c:out value="${eONHeader.skuColumnMap.marketcondition.headerSheet}" /></td>
		<td rowspan="2" columnId='column01'><c:out value="${eONHeader.skuColumnMap.column01.headerSheet}" /></td>
		<td rowspan="2" columnId='column02'><c:out value="${eONHeader.skuColumnMap.column02.headerSheet}" /></td>
		<td rowspan="2" columnId='column03'><c:out value="${eONHeader.skuColumnMap.column03.headerSheet}" /></td>
		<td rowspan="2" columnId='column04'><c:out value="${eONHeader.skuColumnMap.column04.headerSheet}" /></td>
		<td rowspan="2" columnId='column05'><c:out value="${eONHeader.skuColumnMap.column05.headerSheet}" /></td>
		<td rowspan="2" columnId='skuname'><c:out value="${eONHeader.skuColumnMap.skuname.headerSheet}" /></td>
		<td rowspan="2" columnId='home'><c:out value="${eONHeader.skuColumnMap.areaofproduction.headerSheet}" /></td>
		<td rowspan="2" columnId='grade'><c:out value="${eONHeader.skuColumnMap.class1.headerSheet}" /></td>
		<td rowspan="2" columnId='clazzname'><c:out value="${eONHeader.skuColumnMap.class2.headerSheet}" /></td>
		<td rowspan="2" columnId='price2'><c:out value="${eONHeader.skuColumnMap.price2.headerSheet}" /></td>
		<td rowspan="2" columnId='pricewotax'><c:out value="${eONHeader.skuColumnMap.pricewotax.headerSheet}" /></td>
		<td rowspan="2" columnId='pricewtax'><c:out value="${eONHeader.skuColumnMap.pricewtax.headerSheet}" /></td>
		<td rowspan="2" columnId='B_purchasePrice'><c:out value="${eONHeader.skuColumnMap.purchasePrice.headerSheet}" /></td>
		<td rowspan="2" columnId='B_sellingPrice'><c:out value="${eONHeader.skuColumnMap.sellingPrice.headerSheet}" /></td>
		<td rowspan="2" columnId='B_sellingUom'><c:out value="${eONHeader.skuColumnMap.sellingUom.headerSheet}" /></td>
		<td rowspan="2" columnId='packageqty'><span class="requiredColumn">＊</span><c:out value="${eONHeader.skuColumnMap.packageqty.headerSheet}" /></td>
		<td rowspan="2" columnId='packagetype'><c:out value="${eONHeader.skuColumnMap.packagetype.headerSheet}" /></td>
		<td rowspan="2" columnId='unitorder'><c:out value="${eONHeader.skuColumnMap.uom.headerSheet}" /></td>
		<c:if test="${showA eq true}">
			<td rowspan="2" columnId='comments'>Comments</td>
		</c:if>
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
		<td rowspan="2" columnId='B_skuComments'><c:out value="${eONHeader.skuColumnMap.skuComment.headerSheet}" /></td>
		<td rowspan="2" columnId='column06'><c:out value="${eONHeader.skuColumnMap.column06.headerSheet}" /></td>
		<td rowspan="2" columnId='column07'><c:out value="${eONHeader.skuColumnMap.column07.headerSheet}" /></td>
		<td rowspan="2" columnId='column08'><c:out value="${eONHeader.skuColumnMap.column08.headerSheet}" /></td>
		<td rowspan="2" columnId='column09'><c:out value="${eONHeader.skuColumnMap.column09.headerSheet}" /></td>
		<td rowspan="2" columnId='column10'><c:out value="${eONHeader.skuColumnMap.column10.headerSheet}" /></td>
		<td rowspan="2" columnId='column11'><c:out value="${eONHeader.skuColumnMap.column11.headerSheet}" /></td>
		<td rowspan="2" columnId='column12'><c:out value="${eONHeader.skuColumnMap.column12.headerSheet}" /></td>
		<td rowspan="2" columnId='column13'><c:out value="${eONHeader.skuColumnMap.column13.headerSheet}" /></td>
		<td rowspan="2" columnId='column14'><c:out value="${eONHeader.skuColumnMap.column14.headerSheet}" /></td>
		<td rowspan="2" columnId='column15'><c:out value="${eONHeader.skuColumnMap.column15.headerSheet}" /></td>
		<td rowspan="2" columnId='column16'><c:out value="${eONHeader.skuColumnMap.column16.headerSheet}" /></td>
		<td rowspan="2" columnId='column17'><c:out value="${eONHeader.skuColumnMap.column17.headerSheet}" /></td>
		<td rowspan="2" columnId='column18'><c:out value="${eONHeader.skuColumnMap.column18.headerSheet}" /></td>
		<td rowspan="2" columnId='column19'><c:out value="${eONHeader.skuColumnMap.column19.headerSheet}" /></td>
		<td rowspan="2" columnId='column20'><c:out value="${eONHeader.skuColumnMap.column20.headerSheet}" /></td>
	    <c:if test="${user.role.roleId eq 4 || allDatesView eq true}"> <%-- buyer admin --%>
		<td rowspan="2" columnId='rowqty'>総数</td>
		</c:if>
		<td rowspan="2" columnId='lockflag'>lockflag</td>
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
</table>
<div id="bigbox" style="margin: 0px; display: ! none;">
<div id="gridbox"
	style="border: 0px solid #cccccc; background-color: #f3f3f3; padding: 0px; height: 440px; width: 100%;"></div>
<br />
<table border=0 width="100%">
	<tr><%--
		<td>
			<c:if test="${lockButtonEnabled eq true}"> 
				<input type="button" style="width: 80px;" value="Lock" onclick="onLock();" /> &nbsp;
			</c:if>
			<c:if test="${unlockButtonEnabled eq true}"> 
				<input type="button" style="width: 80px;" value="Unlock" onclick="onUnlock();" />
			</c:if>
		</td>
		 
		<td align="right">
			<input type="button" style="width: 100px;" value=" Hide Columns" onclick="showDialogHideColumns();" />
		</td>
		--%>
	</tr>
</table>
</div>
<form name="tempForm" method="post">
</form> 
</body>
</html>