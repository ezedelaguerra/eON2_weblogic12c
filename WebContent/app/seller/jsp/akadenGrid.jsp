<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<c:set var="allDatesView" value="${akadenSheetParam.allDatesView}" />
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
			{name : 'lockflag'},
		 	{name : 'typeflag', type : 'int'},
		    {name : 'myselect'},
			{name : 'akadenSkuId', type : 'int'},
			{name : 'sellerId', type : 'int'},
		    {name : 'skuId', type : 'int'},
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
			{name : 'comments'},
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
			{id: 'isNewSKU', header:"isNewSKU", width : 40, editable : false, hidden : true},
			{id: 'lockflag', header:"JSON", width : 200, editable : false, hidden : true},
			{id: 'typeflag', header:"TypeFlag", width : 40, editable : false, hidden : true},
         	{id: 'myselect', header:"Select", align:"center", width : 25, renderer : deleteCheckBoxRenderer},
			{id: 'akadenSkuId', header:"AkadenSKUId", width : 40, editable : false, hidden : true},
			{id: 'sellerId', header:"Seller Id", width : 40, editable : false, hidden : true},
     		{id: 'skuId', header:"SKUId", width : 40, editable : false, hidden : true},
         	{id: 'companyname' , header: "Companyname" , align:"center" , width :100 , editable : false, hidden : toSigmaBoolean("${user.preference.hideColumn.companyName}")},
         	{id: 'sellername' , header: "Sellername" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.sellerName}"), editor : {type: "select", options : ${sellerNameList}}
         	<c:choose>
			<c:when test="${isAdmin eq true}">
				,afterEdit : function(value, oldValue, record, row, col, grid) {
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
         	{id: 'groupname' , header: "Groupname" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.groupName}"), editor : {type: "select", 
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
    		{id: 'marketname' , header: "Marketname" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.marketCondition}"), editor : {type: "text", maxLength: 42}, toolTip : true, toolTipWidth : 100 },
   	    	{id: 'skuname' , header: "Skuname" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.skuName}"), editor : {type: "text", maxLength: 42, validRule : ['R']}},
   	    	{id: 'home' , header: "Home" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.areaProduction}"), editor : {type: "text", maxLength: 42}},
   	    	{id: 'grade' , header: "Grade" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.clazz1}"), editor : {type: "text", maxLength: 42}},
   	    	{id: 'clazzname' , header: "Classname" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.clazz2}"), editor : {type: "text", maxLength: 42}},
   	    	{id: 'price1' , header: "Price1" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.price1}"), editor : {type: "text", maxLength: 9, validator : numberValidator}, renderer : zeroRenderer},
   	    	{id: 'price2' , header: "Price2" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.price2}"), editor : {type: "text", maxLength: 9, validator : numberValidator}, renderer : zeroRenderer},
   	    	{id: 'pricewotax' , header: "Price W/O Tax" , align:"center" , width :100, hidden : toSigmaBoolean("${user.preference.hideColumn.priceWOTax}"), editor : {type: "text", maxLength: 9, validator : numberValidator}, renderer : zeroRenderer},
   	    	{id: 'pricewtax' , header: "Price W/ Tax" , align:"center" , width :100, renderer : pricewtaxRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.priceWTax}")},
   	    	{id: 'packageqty' , header: "Quantity" , align:"center" , width :60 , editor : {type: "text", maxLength: 9, validator : pkgQtyValidator}, renderer : zeroRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.packageQty}")},
   	    	{id: 'packagetype' , header: "Type" , align:"center" , width :60 , editor : {type: "text", maxLength: 42}, hidden : toSigmaBoolean("${user.preference.hideColumn.packageType}")},
   	    	{id: 'unitorder' , header: "Unit Order" , align:"center" , width :80 , editor : {type: "select" ,options : ${orderUnitList} ,defaultText : '0', validator : unitOrderValidator }, renderer : unitorderRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.uom}")},
         	{id: 'comments' , header: "Comments" , align:"center" , width :100,  toolTip : true , toolTipWidth : 100, editor : {type:"textarea",width:"100px",height:"150px"}, hidden : toSigmaBoolean("${user.preference.hideColumn.skuComment}")},
         	<c:forEach var="company" items="${companyList}"  varStatus="status">
	     	  <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
	     	 	{id:'Q_${user.userId}', header:"${user.userName}" ,align:"right", width:35, editor:{type:"text", maxLength:13, validator : quantity_validator}, renderer : quantityRenderer, afterEdit : function(value, oldValue, record, row, col, grid) { if (oldValue != value) updatePrices(oldValue,value, record['pricewotax']); }},
	     	  </c:forEach>
	        </c:forEach>
		    {id: 'rowqty' , header: "Row Qty" , align:"center" , width :50 , renderer: rowqtyRenderer, hidden : toSigmaBoolean("${user.preference.hideColumn.rowQty}")}
         ];

	//dynamic height for sigma grid based on browser's height
	var sigmaHeight = window.parent.g_clientHeight - 203;
	if(sigmaHeight < 380) sigmaHeight = 380;    

	var gridOption={
		id : grid_demo_id,
		encoding:'UTF-8',
		loadURL : '<c:url value="/akaden/loadAkaden.json" />',
		saveURL : '<c:url value="/akaden/saveAkaden.json" />',
		width: "99.5%",  //"100%", // 700,
		height: sigmaHeight + "px",//"360px",  //"100%", // 330,
		container : 'gridbox', 
		replaceContainer : true, 
		customHead : 'myHead1',
		dataset : _dataset ,
		columns : _columns,
		pageSize : 12 ,
		pageSizeList : [40, 80, 120, 160, 200],
		toolbarContent : 'nav | goto | pagesize state',
		language : 'ja',
		allowHide : true,
		skin : "default",
		beforeEdit: function(value, record, col, grid) {
			if (record.typeflag==1 || record.typeflag==2) 
				return false;
			
			var rowNo = (grid.activeRow).rowIndex;
		    var lockflagObj = _.unserialize(mygridtable.getColumnValue("lockflag", rowNo));
	
		    if (lockflagObj == null) {
				return;
			}
		    if (col.id == 'sellername' && lockflagObj.sellername == '1') return false;

		    <c:choose>
			    <c:when test="${isAdmin eq true}">
			    	if (lockflagObj.adminAddSKU == '1' && col.id != 'sellername') return false;
				</c:when>
			</c:choose>
		        
		    if (col.id == 'groupname' || col.id == 'marketname' ||
				col.id == 'skuname'   || col.id == 'home' ||
				col.id == 'grade' 	  || col.id == 'clazzname' ||
				col.id == 'price1' 	  || col.id == 'price2' ||
				col.id == 'pricewotax' || col.id == 'pricewtax' ||
				col.id == 'packageqty' || col.id == 'packagetype' ||
				col.id == 'unitorder' || col.id == 'skumaxlimit' ||
				col.id == 'sellername' ) {
				if (lockflagObj.sku == '1')
					return false;
		    }

		    if (1==0) {}
		    <c:forEach var="dateKey" items="${dateList}" varStatus="status">
	    	  <c:set var="dateCol" value="${dateMap[dateKey]}" />
	    	  		else if (col.id == 'Q_${dateKey}') {
	    		  		if (lockflagObj.allQuantities == '1' || lockflagObj.Q_${dateKey} == '1') return false;
	    	  		}
	        </c:forEach>
		},
		afterEdit: function(value, oldValue, record, row, col, grid){
			//if (value != oldValue) {
			//	row = mygridtable.getRowByRecord(record);
			//	row.style.backgroundColor = 'orange';
		    //}
			window.parent.akadenSheet.btnSave.setDisabled(false);
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
			if (record.typeflag==2)
				return 'style="height:30px; color:#ff0000"';
			else
				return 'style="height:30px;"';
		},
		<c:choose>
		<c:when test="${isAdmin eq false}">
			defaultRecord : { sellerId : "${user.userId}", companyname: "${user.company.shortName}", sellername : "${user.shortName}", groupname : "--商品グループ--", unitorder : "${defualtOrderUnitId}", lockflag : " {'sellername' : 1}",
		</c:when>
		<c:otherwise>
			defaultRecord : { groupname : "--商品グループ--", unitorder : "${defualtOrderUnitId}", lockflag : " {'adminAddSKU' : \"1\"}",
		</c:otherwise>
	</c:choose>

	<c:forEach var="company" items="${companyList}"  varStatus="status">
	    <c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
			"Q_${user.userId}" : "",
		</c:forEach>
    </c:forEach>
		
	marketname : "", skuname : "", home : "", grade : "", clazzname : "", price1 : "", price2 : "", pricewotax : "", packageqty : "", packagetype : "", skumaxlimit : "", comments : "", rowqty : "" },

		loadResponseHandler : function(response, requestParameter){
			var obj = _.unserialize(response.text);
			window.parent.akadenSheet.computeForPrices(obj.totalPriceWOTaxOnDisplay, obj.totalPriceWTaxOnDisplay);
			window.parent.akadenSheet.cboBuyers.setDisabled(false);
			window.parent.akadenSheet.linkPrevious.setVisibility('visible');
			window.parent.akadenSheet.linkNext.setVisibility('visible');
			window.parent.akadenSheet.setDatePanel(false);
			window.parent.akadenSheet.btnSave.setDisabled(false);
			
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
			            url: "/eON/akaden/sellerSelect.json",
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
			window.parent.akadenSheet.computeGTPrice();
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

	function pricewtaxRenderer (value , record, columnObj, grid, colNo, rowNo) {
		var tmp = window.parent.akadenSheet.roundOffPriceWTax(value);
		if (value ==  "undefined") return "";
		if (tmp > 0) return addCommas(tmp);
		else return "";
	}
	
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
		return "<input type=checkbox id='chkId" + rowNo + "' onclick=deleteRecord(this.id);></input>";
	}

	function deleteRecord(id) {	
		// add waiting and delay
		mygridtable.showWaiting();
		setTimeout('mygridtable.deleteRow()',100);
		setTimeout('mygridtable.hideWaiting()',175);		
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
		mygridtable.addParameters({action:"save", option : _columns});
		var ret = validateSKUGroupsAndOrderUnits();

		if (ret != false) {
			ret = mygridtable.save();

			if (ret != null) // error on input
				alert(mygridtable.getMsg('REQUIRED_FIELDS_NOT_POPULATED'));
		}
		else {
			alert(mygridtable.getMsg('REQUIRED_FIELDS_NOT_POPULATED'));
		}	
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
            success: function(ret) {
				validateSigmaResponse(ret);
	   	 		NOPOOL[value] = ret;
	            updateRow(row,ret);
            },
            complete:function(XMLHttpRequest, textStatus){
                mygridtable.hideWaiting();
            }
        });
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
	
	function isDirty () {
		return mygridtable.isDirty();
	}
	
	function refreshMyGridNow() {
		//window.location.reload();
		mygridtable.reload(false, true);
	}

	function submitTempForm(param) {
		//var answer = confirm("Proceed with excel download?")
		//if (!answer) return;
		var sheetTypeId = 10020;
		<c:if test="${isAdmin eq true}">
			sheetTypeId = 10021;
		</c:if>
		var paramObj = _.unserialize(param);
		paramObj.sheetTypeId = sheetTypeId;

		var param = _.serialize(paramObj);
		var theForm = document.tempForm;
		//alert(param);
		theForm.action = "/eON/downloadExcel.json?_dxls_json=" + param;
		theForm.submit();
	}

	function unitOrderValidator (value, record, colObj, grid) {
		if (value == 0)
			return grid.getMsg('INVALID_INPUT');

		var quantityKeyValue = [];
		var ctr = 0;

		<c:forEach var="company" items="${companyList}"  varStatus="status">
   	  		<c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
   	 			var key = "Q_${user.userId}";
		  		quantityKeyValue[ctr] = key + "," + record[key];
		  		ctr++;
   	  		</c:forEach>
      	</c:forEach>
      	
		var tmp = "${unitorderRenderer}";
		var obj = _.unserialize(tmp);
		var orderUnit = getOrderUnitById(obj,value);
		var res = validateOrderUnitUI(grid, record['sellerId'], record['akadenSkuId'], orderUnit, quantityKeyValue, "0");
				
		return res;
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

	function zeroRenderer (value , record, columnObj, grid, colNo, rowNo) {
		if (isNaN(value)) {
			record[columnObj.id] = "";
			return "";
		} else {
			return addCommas(value);
		}
	}

	function updatePrices(oldValue,value,price) {
		window.parent.akadenSheet.updatePrices(oldValue,value,price);
	}
</script>
</head>
<body>
<table id="myHead1" style="display: none">
	<tr style="background-color: #99CCFF; text-align: center;">
		<td rowspan="2" columnId='isNewSKU'></td>
		<td rowspan="2" columnId='lockflag'>Lock Flag</td>
		<td rowspan="2" columnId='typeflag'></td>
		<td rowspan="2" columnId='akadenSkuId'></td>
		<td rowspan="2" columnId='sellerId'></td>
		<td rowspan="2" columnId='skuId'></td>
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
		<td rowspan="2" columnId='unitorder'><span class="requiredColumn">＊</span>発注単位</td>
		<td rowspan="2" columnId='comments'>コメント一覧</td>
		<c:forEach var="company" items="${companyList}"  varStatus="status">
		  <c:set var="userList" value="${companyMap[company.companyId]}" />
			<td colspan="${fn:length(userList)}" align="center">${company.verticalName}</td>
	    </c:forEach>
		<td rowspan="2" columnId='rowqty'>総数</td>
	</tr>
	<tr style="background-color: #99CCFF">
	<c:forEach var="company" items="${companyList}"  varStatus="status">
		<c:forEach var="user" items="${companyMap[company.companyId]}"  varStatus="status">
		     <td id='Q_${user.userId}' align="center">${user.verticalName}</td></c:forEach>
	</c:forEach>
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