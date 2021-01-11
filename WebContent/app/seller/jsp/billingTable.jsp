<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<c:set var="allDatesView" value="${orderSheetParam.allDatesView}" />
<c:set var="oneBuyerId" value="${oneBuyerId}"/>
<c:set var="showA" value="true" />
<c:if test="${fn:contains(orderSheetParam.selectedBuyerID, ';') || allDatesView eq true}">
  <c:set var="oneBuyerId" value="${orderSheetParam.datesViewBuyerID}"/>
  <c:set var="showA" value="false" />
</c:if>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" -->
<html>
<head>
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />

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
<script type="text/javascript" src="../../common/util/StringUtil.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../common/util/util.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../common/util/sigma_util.js?version=<c:out value="${initParam.version}"/>"></script>
<jsp:include page="../../common/util/disable_right_click.jsp" />
<script type="text/javascript">
var enabled = false;

var grid_demo_id = "myGrid1" ;
var _dataset= {
		fields :[
			{name : 'isNewSKU', type : 'int'},
		 	{name : 'typeflag', type : 'int'},
			{name : 'akadenSkuId', type : 'int'},
		    {name : 'myselect'},
			{name : 'sellerId', type : 'int'},
		    {name : 'skuId', type : 'int'},
			{name : 'lockflag'},
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
			{name : 'price1', type: 'float' },
			{name : 'price2', type: 'float' },
			{name : 'pricewotax', type: 'float' },
			{name : 'pricewtax', type: 'float' },
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
        	{name : 'Q_${user.userId}', type: 'float'},
	  </c:forEach>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <c:forEach var="dateKey" items="${dateList}" varStatus="status">
            {name : 'Q_${dateKey}', type: 'float'},
    </c:forEach>
  </c:otherwise>
</c:choose>
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
			{name : 'rowqty', type: 'float'}
			],
			recordType : 'object'
		}
	
		var _columns = [
			{id: 'isNewSKU', header:"isNewSKU", width : 40, editable : false, hidden : true},
			{id: 'typeflag', header:"TypeFlag", width : 40, editable : false, hidden : true},
			{id: 'akadenSkuId', header:"AkadenSKUId", width : 40, editable : false, hidden : true},
         	{id: 'myselect', header:"Select", align:"center", width : 25, renderer : deleteCheckBoxRenderer},
			{id: 'sellerId', header:"Seller Id", width : 40, editable : false, hidden : true},
     		{id: 'skuId', header:"SKUId", width : 40, editable : false, hidden : true},
    		{id: 'lockflag', header:"JSON", width : 200, editable : false, hidden : true},
         	{id: 'companyname' , header: "Companyname" , align:"center" , width :100 , editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.companyName}")},
         	{id: 'sellername' , header: "Sellername" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.sellerName}"), editor : {type: "select", options : ${sellerNameList}}
         	<c:choose>
         		<c:when test="${isAdmin eq true}">
         		,afterEdit : function(value, oldValue, record, row, col, grid) {
             		//alert('Sellername');
         	        var item = getPoolItem(value);
         	        if(item){
         	            updateRow(row, item);
         	        }
         	       	mygridtable.showWaiting();
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
         	            	mygridtable.hideWaiting();
         	            }
         	        });
         	    }
         	    </c:when>
         	</c:choose>
         	},
         	{id: 'groupname' , header: "Groupname" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.groupName}"),  
             	editor : {type: "select", 
                
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
    		{id: 'marketname' , header: "Marketname" , align:"center" , width :100 , hidden : toSigmaBoolean("${user.preference.hideColumn.marketCondition}"), editor : {type: "text", maxLength: 42}, toolTip : true, toolTipWidth : 100 },
    		{id: 'column01', header: "Column 01", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column01}")},
        	{id: 'column02', header: "Column 02", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column02}")},
        	{id: 'column03', header: "Column 03", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column03}")},
        	{id: 'column04', header: "Column 04", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column04}")},
        	{id: 'column05', header: "Column 05", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column05}")},
   	    	{id: 'skuname' , header: "Skuname" , align:"center" , width :100 , hidden : toSigmaBoolean("${user.preference.hideColumn.skuName}"), editor : {type: "text", maxLength: 42, validRule : ['R']}},
   	    	{id: 'home' , header: "Home" , align:"center" , width :100 , hidden : toSigmaBoolean("${user.preference.hideColumn.areaProduction}"), editor : {type: "text", maxLength: 42}},
   	    	{id: 'grade' , header: "Grade" , align:"center" , width :100 , hidden : toSigmaBoolean("${user.preference.hideColumn.clazz1}"), editor : {type: "text", maxLength: 42}},
   	    	{id: 'clazzname' , header: "Classname" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.clazz2}"), editor : {type: "text", maxLength: 42}},
   	    	{id: 'price1' , header: "Price1" , align:"center" , width :100 , hidden : toSigmaBoolean("${user.preference.hideColumn.price1}"), editor : {type: "text", maxLength: 9, validRule : ['N'], validator : numberValidator}, renderer : zeroRenderer},
   	    	{id: 'price2' , header: "Price2" , align:"center" , width :100 , hidden : toSigmaBoolean("${user.preference.hideColumn.price2}"), editor : {type: "text", maxLength: 9, validRule : ['N'], validator : numberValidator}, renderer : zeroRenderer},
   	    	{id: 'pricewotax' , header: "Price W/O Tax" , align:"center" , width :100 , hidden : toSigmaBoolean("${user.preference.hideColumn.priceWOTax}"), editor : {type: "text", maxLength: 9, validRule : ['N'], validator : numberValidator}, renderer : zeroRenderer},
   	    	{id: 'pricewtax' , header: "Price W/ Tax" , align:"center" , width :100, renderer : pricewtaxRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.priceWTax}")},
   	    	{id: 'packageqty' , header: "Quantity" , align:"center" , width :60 , hidden : toSigmaBoolean("${user.preference.hideColumn.packageQty}"), editor : {type: "text", maxLength: 9, validator : pkgQtyValidator}, renderer : zeroRenderer},
   	    	{id: 'packagetype' , header: "Type" , align:"center" , width :60 , hidden : toSigmaBoolean("${user.preference.hideColumn.packageType}"), editor : {type: "text", maxLength: 42}},
   	    	{id: 'unitorder' , header: "Unit Order" , align:"center" , width :80 , hidden : toSigmaBoolean("${user.preference.hideColumn.uom}"), editor : {type: "select" ,options : ${orderUnitList} ,defaultText : '0', validator : unitOrderValidator }, renderer : unitorderRenderer},
   	<c:if test="${showA eq true}">
   	    	{id: 'comments' , header: "Comments" , align:"center" , width :100,  toolTip : true , toolTipWidth : 100, hidden : toSigmaBoolean("${user.preference.hideColumn.skuComments}"), editor : {type:"textarea",width:"100px",height:"150px"}},
   	</c:if>         	
   	    <c:choose>
   	     <c:when test="${allDatesView eq false}">
   	       <c:forEach var="company" items="${companyList}"  varStatus="status">
   	   	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
   	   		<%-- {id: 'Q_${user.userId}' , header: "${user.userName}" , align:"right" , width :35 , renderer:render_quantity }, --%>
   	   		{id:'Q_${user.userId}', header:"${user.userName}" ,align:"right", width:35, editor:{type:"text", maxLength:13, validator : quantity_validator}, renderer : quantityRenderer, afterEdit : function(value, oldValue, record, row, col, grid) { if (oldValue != value) updatePrices(oldValue,value, record['pricewotax']); }},
   	   	  </c:forEach>
   	       </c:forEach>
   	     </c:when>
   	     <c:otherwise>
   	       <c:forEach var="dateKey" items="${dateList}" varStatus="status">
   	    	  <c:set var="dateCol" value="${dateMap[dateKey]}" />
   	    		<%-- {id: 'Q_${dateKey}' , header: "${dateCol}" , align:"right" , width :35 , renderer:render_quantity }, --%>
   	    		{id:'Q_${dateKey}', header:"${dateCol}", align:"right", width:35, editor:{type:"text", maxLength:13, validator : quantity_validator}, renderer : quantityRenderer, afterEdit : function(value, oldValue, record, row, col, grid) { if (oldValue != value) updatePrices(oldValue,value, record['pricewotax']); }},
   	       </c:forEach>
   	     </c:otherwise>
   	   </c:choose>
		   	{id: 'column06', header: "Column 06", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column06}")},
			{id: 'column07', header: "Column 07", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column07}")},
			{id: 'column08', header: "Column 08", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column08}")},
			{id: 'column09', header: "Column 09", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column09}")},
			{id: 'column10', header: "Column 10", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column10}")},
			{id: 'column11', header: "Column 11", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column11}")},
			{id: 'column12', header: "Column 12", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column12}")},
			{id: 'column13', header: "Column 13", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column13}")},
			{id: 'column14', header: "Column 14", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column14}")},
			{id: 'column15', header: "Column 15", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column15}")},
			{id: 'column16', header: "Column 16", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column16}")},
			{id: 'column17', header: "Column 17", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column17}")},
			{id: 'column18', header: "Column 18", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column18}")},
			{id: 'column19', header: "Column 19", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column19}")},
			{id: 'column20', header: "Column 20", align: "center", width : 100, editor : {type: "text", maxLength: 64}, hidden : toSigmaBoolean("${user.preference.hideColumn.column20}")},
		    {id: 'rowqty' , header: "Row Qty" , align:"center" , width :50 , renderer:my_renderer, hidden : toSigmaBoolean("${user.preference.hideColumn.rowQty}")}
         ];

	//dynamic height for sigma grid based on browser's height
	var sigmaHeight = window.parent.g_clientHeight - 203;
	if(sigmaHeight < 380) sigmaHeight = 380;    

	var gridOption={
		id : grid_demo_id,
		encoding:'UTF-8',
		loadURL : '<c:url value="/billing/loadOrder.json" />',
		saveURL : '<c:url value="/billing/saveOrder.json" />',
		width: "99.5%",  //"100%", // 700,
		height: sigmaHeight + "px", //"360px",  //"100%", // 330,
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
		skin : "default",
		
		beforeEdit: function(value, record, col, grid) {
			var rowNo = (grid.activeRow).rowIndex;
		    var lockflagObj = _.unserialize(mygridtable.getColumnValue("lockflag", rowNo));
		    var insertedRows = mygridtable.getInsertedRecords();
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

			if (col.id == 'skumaxlimit') if(lockflagObj.skumaxlimit == '0') return true;   
			
		    if (!col.id.startsWith("Q")) {
				if (lockflagObj.sku == '1')
					return false;
		    }

		    if (1==0) {}
		    <c:choose>
		      <c:when test="${allDatesView eq false}">
		        <c:forEach var="company" items="${companyList}"  varStatus="status">
		    	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
		    	  		else if (col.id == 'Q_${user.userId}') {
		    	  			if (lockflagObj.Q_${user.userId} == '1') return false;
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
		afterEdit: function(value, oldValue, record, row, col, grid){
			//if (value != oldValue) {
			//	row = mygridtable.getRowByRecord(record);
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
	      return !processed;	
	  	},
		customRowAttribute : function(record,rn,grid){
	  		var lockflagObj = _.unserialize(record.lockflag);
			if (lockflagObj.sku == '1') {
				if (record.typeflag==2)
					return 'style="height:30px; color:#ff0000"; background-color:#bfbfbf;';
				else
					return 'style="height:30px; background-color:#bfbfbf;"';
			} else {
				if (record.typeflag==2)
					return 'style="height:30px; color:#ff0000"';
				else
					return 'style="height:30px;"';
			}
		},
	<%--<c:choose>
		<c:when test="${isAdmin eq false}">
			defaultRecord : { sellerId : "${user.userId}", companyname: "${user.company.companyName}", sellername : "${user.shortName}", groupname : "0", unitorder : "0",
		</c:when>
		<c:otherwise>
			defaultRecord : { groupname : "0", unitorder : "0",
		</c:otherwise>
	</c:choose>--%>
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
		
				marketname : "", skuname : "", home : "", grade : "", clazzname : "", 
				price1 : "", price2 : "", pricewotax : "", packageqty : "", packagetype : "", 
				skumaxlimit : "", rowqty : "",
				column01 : "", column02 : "", column03 : "", column04 : "", column05 : "", 
				column06 : "", column07 : "", column08 : "", column09 : "", column10 : "", 
				column11 : "", column12 : "", column13 : "", column14 : "", column15 : "", 
				column16 : "", column17 : "", column18 : "", column19 : "", column20 : "" },

		loadResponseHandler : function(response, requestParameter){
			var obj = _.unserialize(response.text);
			window.parent.orderSheet.computeForPrices(obj.totalPriceWOTaxOnDisplay, obj.totalPriceWTaxOnDisplay);
			window.parent.orderSheet.cboBuyers.setDisabled(false);
			window.parent.orderSheet.linkPrevious.setVisibility('visible');
			window.parent.orderSheet.linkNext.setVisibility('visible');
			window.parent.orderSheet.setDatePanel(false);
			window.parent.orderSheet.btnSave.setDisabled(true);
			//alert(response.text);
			// enable disable action buttons
			var flags = _.unserialize(obj.buttonFlags);
			if (flags != null) {
				if (flags.btnFinalize == 1) { // finalized
					window.parent.orderSheet.btnAddSKU.setDisabled(true);
					window.parent.orderSheet.btnAddSKUGroup.setDisabled(true);
					window.parent.orderSheet.btnSave.setDisabled(true);
					window.parent.orderSheet.btnFinalize.setDisabled(true);
					window.parent.orderSheet.btnUnfinalize.setDisabled(false);
					window.parent.orderSheet.disableMenus();
				} else { 
					window.parent.orderSheet.btnAddSKU.setDisabled(false);
					window.parent.orderSheet.btnAddSKUGroup.setDisabled(false);
					window.parent.orderSheet.btnSave.setDisabled(false);
					window.parent.orderSheet.btnFinalize.setDisabled(false);
					window.parent.orderSheet.btnUnfinalize.setDisabled(true);
					window.parent.orderSheet.enableMenus();
				}

				if (flags.btnSave == 1 && flags.btnFinalize == 0){
					window.parent.orderSheet.btnAddSKU.setDisabled(true);
					window.parent.orderSheet.btnAddSKUGroup.setDisabled(true);
					window.parent.orderSheet.btnSave.setDisabled(true);
					window.parent.orderSheet.btnFinalize.setDisabled(true);
					window.parent.orderSheet.btnUnfinalize.setDisabled(true);
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
			        mygridtable.showWaiting();
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
			            	mygridtable.hideWaiting();
			            }
			        });
				}
			}
		},
		saveResponseHandler : function(response, requestParameter) {
			//alert("response.text: " + response.text);
			var result = validateResponseOnIframe(response.text);
			if (result) {
				alert(mygridtable.getMsg('SAVED'));
			}
			window.parent.orderSheet.computeGTPrice();
		},
		onmouseover : function(event,grid){
			var ts= grid.getEventTargets(event);
			if (ts.column && ts.column.toolTip) {
				grid.showCellToolTip(ts.cell,ts.column.toolTipWidth);
			}else{
				grid.hideCellToolTip();
			}
		},
		onmouseout : function(event,grid){
			grid.hideCellToolTip();
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
		}
	};

	var mygridtable = new Sigma.Grid(gridOption);
	Sigma.Util.onLoad(Sigma.Grid.render(mygridtable));

	function my_renderer(value, record, columnObj, grid, colNo, rowNo){
		var name = record[columnObj.fieldIndex];

		if (isNaN(name))
			name = 0;
		
		if (columnObj.id == "rowqty") {
			return "<div class='higlightedRowQty'>" + name + "</div>";
		}
		else if (columnObj.id == "pricewtax") {
			return "<div class='higlightedPriceWTax'>" + name + "</div>";
		}
		else {
			return name;
		}
	}

	function quantityRenderer (value , record, columnObj, grid, colNo, rowNo) {
		return addCommas(value);
	}

	function showDialogHideColumns() {
		dlg = Sigma.ad2("show",{gridId:"myGrid1",checkValid:function($){
				return $.hidden;
		},checkFn:"hide",uncheckFn:"show",canCheck:function($){
	        return !$.frozen;
		}});
		dlg.show();
		dlg.setTitle("Hide Columns");
	}
	function quantity_validator(value, record, colObj, grid) {
		value = value + "";
		value = value.trim();
		var tmp = "${unitorderRenderer}";
		var obj = _.unserialize(tmp);
		//var toProcess = (!isNaN(value) && value>=0 && value<=999999999.999);
		var toProcess = (!isNaN(value) && value<=999999999.999);
		var isValidDecimalInput = validateQuantityScale(getOrderUnitById(obj,record["unitorder"]),value);
		if (value == '' || (toProcess == true && isValidDecimalInput ==true)) {
			return true;
		}
		else {//invalid
			return grid.getMsg('INVALID_INPUT');
		}
	}
	function scale(value){
		  if(String(value).indexOf(".") > 0){
			  //if (String(value).indexOf(".") < String(value).length - 4) {
				  return false;
			  //}  
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

	
	/*function quantity_validator(value, record, colObj, grid) {*/
		/*
		value = value.trim();
		if (value == '' || (!isNaN(value) && value>=0 && value<=999999999.999)) {
			return true;
		}
		else {//invalid
			return grid.getMsg('INVALID_NUMBER');
		}*/
		
		/*if (value == '' || (!isNaN(value) && value>0 && value<=999999.999)) {
			// check for sku max limit
			var totalQty = 0;
			<c:forEach var="company" items="${companyList}"  varStatus="status">
			  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
			  		var buyer = 'Q_${user.userId}';
			  		if (buyer == colObj.id) {
				  		totalQty = parseFloat(totalQty) + parseFloat(value);
			  		} else {
				  		if (isNaN(record.Q_${user.userId})) {
				  			totalQty = parseFloat(totalQty) + parseFloat(record.Q_${user.userId});
				  		}
			  		}
			  </c:forEach>
		    </c:forEach>
		    var skumaxlimit = parseFloat(record.skumaxlimit);
		    if (skumaxlimit > 0 && totalQty > skumaxlimit)
			    return "Exceeded the total number of quantities allowable to the SKU.";
		    return true;
		}
		else {
			return "invalid input";
		}
	}*/

	/*function validateQuantityLimit (record, colId, value) {
		if (value != '' && parseInt(value) > 0) {
			var errMsg = "Exceeded the total number of quantities allowable to the SKU.";
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
				mygridtable.showWaiting();
		        $.ajax({
		            url: url,
		            data: "",
		            dataType: 'json',
		            async : false,
		            success: function(response){
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
		                mygridtable.hideWaiting();
		            }
		        });
		        if (eps == 1) return errMsg;
			} else {
				return errMsg;
			}
		}
	}

	function validateQuantityLimitUI (record, colId, value) {
		var totalQty = 0;
		var quantityKeys = [];
		var ctr = 0;
		<c:forEach var="company" items="${companyList}"  varStatus="status">
		  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
		  		var key = "Q_${user.userId}";
		  		quantityKeys[ctr] = key;
		  		ctr++;
		  </c:forEach>
	    </c:forEach>

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
	}	*/
	function render_quantity(value, record, columnObj, grid, colNo, rowNo) {
		var render;
		var disabled = "";
		var allRows = mygridtable.getInsertedRecords();
		var color = 'transparent';
		if (allRows.length > 0) {
			disabled = "disabled";
			color = '#FAAFBA';
		}
	    
		if (value == -999) {
			render = "<span id='span-" + columnObj.id + "-" + rowNo + "' class='gt-editor-q' style='background-color:#CFECEC;'></span>";
		}
		else {
			render = "<input type='text' style='background-color: " + color + ";'maxLength='10' onmouseover=\"this.style.cursor='arrow'\" class='gt-editor-q' id='tBox-" + columnObj.id + "-" + rowNo + "' value='"+value+"' " + disabled + " onfocus='focusMe(this)' onblur='blurMe(this)' onkeyup='enterPress(this, event)'/>";
		}
		
		return render;
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

	function deleteRecord(id) {	
		// add waiting and delay
		mygridtable.showWaiting();
		setTimeout('mygridtable.deleteRow()',100);
		setTimeout('mygridtable.hideWaiting()',175);
	}

	function pricewtaxRenderer (value , record, columnObj, grid, colNo, rowNo) {
		var tmp = window.parent.orderSheet.roundOffPriceWTax(value);
		if (value ==  "undefined") return "";
		if (tmp > 0) return addCommas(tmp);
		else return "";
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
		var tmp = parseFloat(value);
		if (value ==  "undefined") return "";
		if (tmp > 0) return value;
		else return "";
	}	
	
	function cBoxMethod(cBoxObj) {
		var allRows = mygridtable.getAllRows();
		var cBoxId = cBoxObj.id;
		var arr = cBoxId.split("-");
		var colId = arr[1];
		var rowNo = arr[2];
		var visFlag = 1;
		if (cBoxObj.checked) visFlag = 1;
		else visFlag = 0;

		if (rowNo < allRows.length)
			mygridtable.setColumnValue(colId, rowNo, visFlag);
		else {
			cBoxObj.checked = true;
		}
	}
		
	function onSave() {
		/*var responseInsert = mygridtable.getInsertedRecords();
		var responseUpdated = mygridtable.getUpdatedRecords();
		var responseDelete = mygridtable.getDeletedRecords();
		if(responseInsert.length > 0 || responseUpdated.length > 0 || responseDelete.length > 0)
			proceedSaving(); */
		mygridtable.addParameters({action:"save", option : _columns});
		var ret = validateSKUGroupsAndOrderUnits();

		if (ret != false) {
			/*ret = mygridtable.save();
			if (ret != false)
				window.parent.orderSheet.disableActionButtons();

			if (ret != null) // error on input
				alert("Fill up all the required fields correctly");*/
			ret = mygridtable.save();

			if (ret != null) // error on input
				alert(mygridtable.getMsg('REQUIRED_FIELDS_NOT_POPULATED'));
		}
		else {
			alert(mygridtable.getMsg('REQUIRED_FIELDS_NOT_POPULATED'));
		}	
	}

	function onFinalize() {
		var msg = mygridtable.getMsg('CONFIRM_FINALIZE');
		var answer = confirm(msg);
		if (answer) {
			mygridtable.showWaiting();
			linb.Ajax(
				"/eON/billing/finalize.json",
	            null,
	            function (response) {
					validateResponse(response);
					if (validateResponseOnIframe(response)) {
						window.parent.orderSheet.disableActionButtons();
						mygridtable.reload(false,true);
						alert(mygridtable.getMsg('FINALIZED'));
					}
	            	mygridtable.hideWaiting();
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
		var msg = mygridtable.getMsg('ORDER_CONFIRM_UNFINALIZE');
		var answer = confirm(msg);
		if (answer) {
			mygridtable.showWaiting();
			linb.Ajax(
				"/eON/billing/unfinalize.json",
	            null,
	            function (response) {
					validateResponse(response);
		            if (validateResponseOnIframe(response)) {
		            	window.parent.orderSheet.disableActionButtons();
		            	alert(mygridtable.getMsg('UNFINALIZED'));
						mygridtable.reload(false,true);
					}
					mygridtable.hideWaiting();
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

	function validateResponseOnIframe(jsonResponse) {
		var obj = _.unserialize(jsonResponse);
		//alert("jsonResponse: " + jsonResponse);
		var error = obj.globals;
		if (obj.fail == 'true') {
			//alert(error[0]);
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

		var allRows = mygridtable.getAllRows();
		var id = obj.id;
		var arr = id.split("-");
		var colId = arr[1];
		var rowNo = arr[2];
		var value = obj.value;
		//alert(colId);
		//alert(rowNo);
		var origValue = mygridtable.getColumnValue(colId, rowNo);
		//if (origValue == null && rowNo != 0)
		//	rowNo = rowNo - 1;
		//origValue = mygridtable.getColumnValue(colId, rowNo);
		if (value == '' || (!isNaN(value) && value>0 && value<=999999.999)) {
			if (value != origValue) {
				obj.style.backgroundColor = 'orange';
				obj.style.backgroundRepeat  = 'no-repeat';
				obj.style.backgroundPosition = 'right top';
				obj.style.backgroundImage = 'url(../../sigmagrid/lib/grid/skin/default/images/cell_updated.gif)';
				//mygridtable.setColumnValue(colId, rowNo, value);
			}
			//ok
		}
		else {
			alert(mygrid.getMsg('INVALID_NUMBER'));
			value = origValue;
			obj.value = origValue;
			//mygridtable.setColumnValue(colId, rowNo, origValue);
		}
		//alert(colId);
		//alert(rowNo);
		//alert(origValue);
		//alert(mygridtable.isInsertRow(mygridtable.activeRow));
		mygridtable.setColumnValue(colId, rowNo, value);
		
	}

	function enterPress(obj, e) {
		var KeyID = (window.event) ? event.keyCode : e.keyCode;
		if (KeyID==13 || KeyID==9 || KeyID==27 ) {
			obj.blur();
		}
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
	        var col = mygridtable.getColumn(i);
	        if(col){
	            if(Sigma.$type(item)==="array"){
	                //do nothing because this is for options
	            }else{
	                record = mygridtable.getRecordByRow(row);
	                mygridtable.setCellValue(col.id, record, item);
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
		mygridtable.showWaiting();
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
                mygridtable.hideWaiting();
            }
        });
	}
	
	function proceedSaving() {
		mygridtable.showWaiting();
		mygridtable.addParameters({action:"save", option : _columns});
		mygridtable.save();
		mygridtable.hideWaiting();
	}
	
	function addRow() {
		//var defaultRec = ["1","2","3","4"]; 
		mygridtable.insert("", true, 0);
		mygridtable.bodyDiv.scrollTop = 0;
	}
	
	function transfernow(obj) {
		for(var i=0;i<obj.length;i++){
			//mygridtable.add(obj[i]);
			mygridtable.insert(obj[i], true, 0);
			mygridtable.bodyDiv.scrollTop = 0;
		}
	}
	
	function responseCheck (jsonResponse) {
		var obj = _.unserialize(jsonResponse);
		if (obj.fail == 'true') {
			linb.pop("responseCheck", obj.globals, "OK");
		}
	}

	function refreshMyGridNow() {
		window.location.reload();
	}

	function submitTempForm(param) {
		//var answer = confirm("Proceed with excel download?")
		//if (!answer) return;
		var sheetTypeId = 10005;
		<c:if test="${isAdmin eq true}">
			sheetTypeId = 10011;
		</c:if>
		var paramObj = _.unserialize(param);
		paramObj.sheetTypeId = sheetTypeId;

		var param = _.serialize(paramObj);
		var theForm = document.tempForm;
		//alert(param);
		theForm.action = "/eON/downloadExcel.json?_dxls_json=" + param;
		theForm.submit();
	}

	function validateSKUGroupsAndOrderUnits () {

		var records = mygridtable.getInsertedRecords();
		var i=0;
		for (i=0; i<records.length; i++) {
			if (records[i].groupname == '0' || records[i].unitorder == '0' || records[i].packageqty == '')
				return false;
		}
		
		return true;
	}

	function isDirty () {
		return mygridtable.isDirty();
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

		var res = validateOrderUnitUI(grid, record['sellerId'], record['skuId'], orderUnit, quantityKeyValue, "0");

		if (res == true) {
			var url = "/eON/billing/validateOrderUnit.json";
			res = validateOrderUnitBE(grid, record['sellerId'], record['skuId'], orderUnit, quantityKeyValue, url);
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

	function updatePrices(oldValue,value,price) {
		window.parent.orderSheet.updatePrices(oldValue,value,price);
	}
</script>
</head>
<body>
<table id="myHead1" style="display: none">
	<tr style="background-color: #99CCFF; text-align: center;">
		<td rowspan="2" columnId='lockflag'>Lock Flag</td>
		<td rowspan="2" columnId='isNewSKU'></td>
		<td rowspan="2" columnId='typeflag'></td>
		<td rowspan="2" columnId='akadenSkuId'></td>
		<td rowspan="2" columnId='sellerId'></td>
		<td rowspan="2" columnId='skuId'></td>
		<td rowspan="2" columnId='myselect'></td>
		<td rowspan="2" columnId='companyname'><c:out value="${eONHeader.skuColumnMap.companyname.headerSheet}" /></td>
		<td rowspan="2" columnId='sellername'><c:out value="${eONHeader.skuColumnMap.sellername.headerSheet}" /></td>
		<td rowspan="2" columnId='groupname'><span class="requiredColumn">＊</span><c:out value="${eONHeader.skuColumnMap.groupname.headerSheet}" /></td>
		<td rowspan="2" columnId='marketname'><c:out value="${eONHeader.skuColumnMap.marketcondition.headerSheet}" /></td>
		<td rowspan="2" columnId='column01'><c:out value="${eONHeader.skuColumnMap.column01.headerSheet}" /></td>
		<td rowspan="2" columnId='column02'><c:out value="${eONHeader.skuColumnMap.column02.headerSheet}" /></td>
		<td rowspan="2" columnId='column03'><c:out value="${eONHeader.skuColumnMap.column03.headerSheet}" /></td>
		<td rowspan="2" columnId='column04'><c:out value="${eONHeader.skuColumnMap.column04.headerSheet}" /></td>
		<td rowspan="2" columnId='column05'><c:out value="${eONHeader.skuColumnMap.column05.headerSheet}" /></td>
		<td rowspan="2" columnId='skuname'><span class="requiredColumn">＊</span><c:out value="${eONHeader.skuColumnMap.skuname.headerSheet}" /></td>
		<td rowspan="2" columnId='home'><c:out value="${eONHeader.skuColumnMap.areaofproduction.headerSheet}" /></td>
		<td rowspan="2" columnId='grade'><c:out value="${eONHeader.skuColumnMap.class1.headerSheet}" /></td>
		<td rowspan="2" columnId='clazzname'><c:out value="${eONHeader.skuColumnMap.class2.headerSheet}" /></td>
		<td rowspan="2" columnId='price1'><c:out value="${eONHeader.skuColumnMap.price1.headerSheet}" /></td>
		<td rowspan="2" columnId='price2'><c:out value="${eONHeader.skuColumnMap.price2.headerSheet}" /></td>
		<td rowspan="2" columnId='pricewotax'><c:out value="${eONHeader.skuColumnMap.pricewotax.headerSheet}" /></td>
		<td rowspan="2" columnId='pricewtax'><c:out value="${eONHeader.skuColumnMap.pricewtax.headerSheet}" /></td>
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
		<td rowspan="2" columnId='rowqty'>総数</td>
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
<div id="bigbox" style="margin: 0px; display: ! none;">
<div id="gridbox"
	style="border: 0px solid #cccccc; background-color: #f3f3f3; padding: 0px; height: 355px; width: 100%;"></div>
</div>
<form name="tempForm" method="post">
</form>
</body>
</html>