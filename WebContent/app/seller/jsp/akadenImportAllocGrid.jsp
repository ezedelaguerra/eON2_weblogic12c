<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<c:set var="oneBuyerId" value="${oneBuyerId}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" -->
<html>
<head>
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />

<%--<link href="highlight/style.css" rel="stylesheet" type="text/css" />--%>
<link rel="stylesheet" type="text/css" href="../../sigmagrid2.4e/grid/css/styles.css" />
<link rel="stylesheet" type="text/css" href="../../sigmagrid2.4e/grid/gt_grid.css" />
<link rel="stylesheet" type="text/css" href="../../sigmagrid2.4e/grid/skin/vista/skinstyle.css" />
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
<script type="text/javascript" src="../../common/util/util.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../common/util/sigma_util.js?version=<c:out value="${initParam.version}"/>"></script>

<jsp:include page="../../common/util/disable_right_click.jsp" />

<script type="text/javascript">
var g_reload = ""; // use to check if grid need to reload.
var grid_demo_id = "myallocgrid1" ;

	var _dataset= {
		fields :[
			{name : 'skuId', type : 'int'},	
			{name : 'sellerId', type : 'int'},		    	    
		    {name : 'myselect'},
		    {name : 'companyname'},
			{name : 'sellername'},
			{name : 'groupname'},
			{name : 'marketname'},
			{name : 'skuname'},
			{name : 'home'},
			{name : 'grade'},
			{name : 'clazzname'},
			{name : 'price1', type: 'float' },
			{name : 'price2', type: 'float' },
			{name : 'pricewotax', type: 'float' },
			{name : 'pricewtax', type: 'float' },
			{name : 'packageqty', type: 'float'},
			{name : 'packagetype'},
			{name : 'unitorder'},
			<c:forEach var="company" items="${companyList}"  varStatus="status">
				<c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
					{name: 'Q_${user.userId}'},
				</c:forEach>
			</c:forEach>
			{name : 'rowqty', type: 'float'}
		],
		recordType : 'object'
	}

	var _columns = [
		{id: 'skuId', header:"SKU Id", editable : true, hidden : true},
		{id: 'sellerId', header:"Seller Id", width : 40, editable : false, hidden : true},
		{id: 'myselect', header:"Select", align:"center", width : 25, renderer : deleteCheckBoxRenderer},
		{id: 'companyname' , header: "Companyname" , align:"center" , width :100 , editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.companyName}")},
     	{id: 'sellername' , header: "Sellername" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.sellerName}"), editor : {type: "select", options : ${sellerNameList}}
     	<c:choose>
     		<c:when test="${isAdmin eq true}">
     		,afterEdit : function(value, oldValue, record, row, col, grid) {
     	        var item = getPoolItem(value);
     	        if(item){
     	            updateRow(row, item);
     	        }
     	       	myallocgrid.showWaiting();
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
     	            	myallocgrid.hideWaiting();
     	            }
     	        });
     	    }
     	    </c:when>
     	</c:choose>
     	},
     	{id: 'groupname' , header: "Groupname" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.groupName}"), editor : {type: "select", 
			
			validator : function(value,record,colObj,grid) {
				if (value == "0") {
					return "Please select groupname.";
				}
			}
			
	    <c:choose>
	    	<c:when test="${isAdmin eq true}">
	    	        ,onEditorInit: function(value, record, row, column, grid) {
	    		        var i=0;
	    	            var dom = this.valueDom;
	    	            var item = getPoolItem(record.sellername);
	    	            var options = item ? item["groupname"]:[];
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
    	{id: 'marketname' , header: "Marketname" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.marketCondition}"), editor : {type: "text"}, toolTip : true, toolTipWidth : 100},
    	{id: 'skuname' , header: "Skuname" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.skuName}"), editor : {type: "text"}},
    	{id: 'home' , header: "Home" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.areaProduction}"), editor : {type: "text"}},
    	{id: 'grade' , header: "Grade" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.clazz1}"), editor : {type: "text"}},
    	{id: 'clazzname' , header: "Classname" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.clazz2}"), editor : {type: "text"} },
    	{id: 'price1' , header: "Price1" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.price1}"), editor : {type: "text"}},
    	{id: 'price2' , header: "Price2" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.price2}"), editor : {type: "text"}},
    	{id: 'pricewotax' , header: "Price W/O Tax" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.priceWOTax}"), editor : {type: "text"}},
    	{id: 'pricewtax' , header: "Price W/ Tax" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.priceWTax}"), renderer: pricewtaxRenderer },
    	{id: 'packageqty' , header: "Quantity" , align:"center" , width :60, hidden : toSigmaBoolean("${user.preference.hideColumn.packageQty}"), editor : {type: "text"}},
    	{id: 'packagetype' , header: "Type" , align:"center" , width :60, hidden : toSigmaBoolean("${user.preference.hideColumn.packageType}"), editor : {type: "text"}},
    	{id: 'unitorder' , header: "Unit Order" , align:"center" , width :80, hidden : toSigmaBoolean("${user.preference.hideColumn.uom}"), editor : {type: "select" ,options : ${orderUnitList} ,defaultText : '0' }, renderer : unitorderRenderer},
        <c:forEach var="company" items="${companyList}"  varStatus="status">
     		<c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
     			{id: 'Q_${user.userId}' , header: "${user.userName}" , align:"right" , width :35 , editor : {type: "text", maxLength: 9}},
     		</c:forEach>
        </c:forEach>

	    {id: 'rowqty' , header: "Row Qty" , align:"center" , width :50 , renderer: rowqtyRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.rowQty}")}
    ];

	var gridOption={
		id : grid_demo_id,
		encoding:'UTF-8',
		loadURL : '<c:url value="/akaden/loadImportedDataFromAllocation.json" />',
		saveURL : '<c:url value="/akaden/setAkadenSessionForAllocation.json" />',
		width: "99.5%",  //"100%", // 700,
		height: "435px",  //"100%", // 330,
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
		allowHide : true,
		skin : "default",
		beforeEdit: function(value, record, col, grid) {
			return false;
		},
		customRowAttribute : function(record,rn,grid) {
			return 'style="height:30px;"'; 
		},
		loadResponseHandler : function(response, requestParameter){
			var obj = _.unserialize(response.text);
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
			        myallocgrid.showWaiting();
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
			            	myallocgrid.hideWaiting();
			            }
			        });
				}
			}

			var confirmMsg = obj.confirmMsg;
			if (confirmMsg != null && confirmMsg != "") 
				alert(confirmMsg);
			
		},
		saveResponseHandler : function(response, requestParameter) {
			var result = responseCheck(response.text);
			if (!result) {
				alert('選択した割り当てデータをインポートしました');
				//alert ('Selected allocation data(s) are now imported.');
				window.parent.akadenSheet.refreshTheGridNow();
				window.parent.akadenSheet.computeGTPrice();
			}
			else {
				myallocgrid.refresh();
				alert('転送に失敗しました　いくつかのデータは既にインポート済みです');
				//alert ('Failed to transferred. Some data(s) are already imported.');	
				window.parent.akadenSheet.refreshTheGridNow();			
			}
			
		}
		};
		var myallocgrid = new Sigma.Grid(gridOption);
		Sigma.Util.onLoad(Sigma.Grid.render(myallocgrid));

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
			var render = " ";
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

		function setSelectOptions(dom, options ,value){
			  dom.options.length = 0;
			  $.each(options, function(i, item){
			    dom.options[dom.options.length] = new Option(item, item, item==value, item==value);
			  });
			}

		function cacheSellerSKUGroupInfo() {
			myallocgrid.showWaiting();
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
	                myallocgrid.hideWaiting();
	            }
	        });
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
		        var col = myallocgrid.getColumn(i);
		        if(col){
		            if(Sigma.$type(item)==="array"){
		                //do nothing because this is for options
		            }else{
		                record = myallocgrid.getRecordByRow(row);
		                myallocgrid.setCellValue(col.id, record, item);
		            }
		        }
		    });
		}		

		function pricewtaxRenderer (value , record, columnObj, grid, colNo, rowNo) {
			var tmp = window.parent.akadenSheet.roundOffPriceWTax(value);
			if (value ==  "undefined") return "";
			if (tmp > 0) return addCommas(tmp);
			else return "";
		}

		function rowqtyRenderer(value, record, columnObj, grid, colNo, rowNo){
			var name = record[columnObj.fieldIndex];
			
			if (isNaN(name))
				name = 0;
			
			if (columnObj.id == "rowqty") {
				return "<div class='higlightedRowQty'>" + addCommas(name) + "</div>";
			}
		}

	function deleteCheckBoxRenderer (value , record, columnObj, grid, colNo, rowNo) {
		return "<input type=checkbox id='deletemoko' onclick=deleteRecord();></input>";
	}

	function deleteRecord() {	
		// add waiting and delay
		myallocgrid.showWaiting();
		setTimeout('myallocgrid.deleteRow()',100);
		setTimeout('myallocgrid.hideWaiting()',175);
	}
	
	function onImport() {
		var isCheckDelete = myallocgrid.getDeletedRecords();
		if( isCheckDelete.length > 0 ) {
			myallocgrid.showWaiting();
			myallocgrid.addParameters({action:"save", option : _columns});
			myallocgrid.save();
			myallocgrid.hideWaiting();
			//return true;
		}
		else {
			alert('転送に失敗しました　チェックボックスをチェックして、赤伝シートにデータをインポートしてください');
			//alert('Failed to transferred. Please check the checkbox to import the data into akaden sheet.');
		}		
	}
	
	function responseCheck (jsonResponse) {
		var obj = _.unserialize(jsonResponse);
		if (obj.exists == 1) 
			return true;
		else
			return false;
	}
	
	function transfer() {
		var recs = myallocgrid.getSelectedRecords();
		window.parent.akadenSheet.movetoakaden(recs);
	}

</script>
</head>
<body>
<table id="myHead1" style="display: none">
	<tr style="background-color: #99CCFF; text-align: center;">
		<td rowspan="2" columnId='skuId'></td>
		<td rowspan="2" columnId='sellerId'></td>
		<td rowspan="2" columnId='myselect'></td>
		<td rowspan="2" columnId='companyname'>会社名</td>
		<td rowspan="2" columnId='sellername'>売主担当者</td>
		<td rowspan="2" columnId='groupname'><span class="requiredColumn">＊</span>商品グループ</td>
		<td rowspan="2" columnId='marketname'>市況</td>
		<td rowspan="2" columnId='skuname'><span class="requiredColumn">＊</span>商品名</td>
		<td rowspan="2" columnId='home'>産地</td>
		<td rowspan="2" columnId='grade'>等級</td>
		<td rowspan="2" columnId='clazzname'>階級</td>
		<td rowspan="2" columnId='price1'>価格１</td>
		<td rowspan="2" columnId='price2'>価格2</td>
		<td rowspan="2" columnId='pricewotax'>店着税抜</td>
		<td rowspan="2" columnId='pricewtax'>店着税込</td>
		<td rowspan="2" columnId='packageqty'><span class="requiredColumn">＊</span>入数</td>
		<td rowspan="2" columnId='packagetype'>入数単位</td>
		<td rowspan="2" columnId='unitorder'>発注単位</td>
	    <c:forEach var="company" items="${companyList}"  varStatus="status">
		  <c:set var="userList" value="${companyMap[company.companyId]}" />
			<td colspan="${fn:length(userList)}" align="center">${company.verticalName}</td>
	    </c:forEach>
		<td rowspan="2" columnId='rowqty'>総数</td>
	</tr>
	<tr style="background-color: #99CCFF">
    <c:forEach var="company" items="${companyList}"  varStatus="status">
	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
        <td id='Q_${user.userId}' align="center">${user.verticalName}</td>
	  </c:forEach>
    </c:forEach>	 
	</tr>
</table>
<div id="bigbox" style="margin: 0px; display: ! none;">
<div id="gridbox"
	style="border: 0px solid #cccccc; background-color: #f3f3f3; padding: 0px; height: 440px; width: 100%;"></div>

</div>
</body>
</html>