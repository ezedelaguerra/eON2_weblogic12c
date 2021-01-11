<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<script type="text/javascript" src="../../common/util/util.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../common/util/StringUtil.js?version=<c:out value="${initParam.version}"/>"></script>
<script type="text/javascript" src="../../common/util/sigma_util.js?version=<c:out value="${initParam.version}"/>"></script>
<jsp:include page="../../common/util/disable_right_click.jsp" />
<script type="text/javascript">
var grid_demo_id = "myGrid1" ;
	
	var dsOption= {
		fields :[
		    {name : 'companyName'},
			{name : 'sellerName'},
			{name : 'skuGroup'},
			{name : 'market'},
			{name : 'skuName'},
			{name : 'sellerProdCode'},
			{name : 'buyerProdCode'},
			{name : 'location'},
			{name : 'grade'},
			{name : 'pmfClass'},
			{name : 'price1', type: 'double' },
			{name : 'price2', type: 'double' },
			{name : 'priceNoTax', type: 'double' },
			{name : 'pricewTax', type: 'double' },
			{name : 'pkgQuantity', type: 'integer'},
			{name : 'pkgType'},
			{name : 'unitOrder'},
			{name : 'commentA'},
			{name : 'commentB'},
			{name : 'skuId', type: 'integer'},
			{name : 'pmfId', type: 'integer'},
			{name : 'sellerId', type: 'integer'}
		],
		recordType : 'object'
	}

	var colsOption = [
    	{id: 'myselect', header:"Select", align:"center", width : 25, renderer : deleteCheckBoxRenderer},
    	{id: 'companyName' , header: "Company Name" , align:"center" , width :100 , editable : false},
    	{id: 'sellerName' , header: "Seller Name" , align:"center" , width :100},
    	{id: 'skuGroup' , header: "Group Name" , align:"center" , width :100, editor : {type: "select", 
			
			validator : function(value,record,colObj,grid) {
				if (value == "0") {
					alert(grid.getMsg('INVALID_INPUT'));
					return false;
				}
			}, options : ${skuGroupList}, defaultText : '0'}, renderer : groupnameRenderer },
    	{id: 'market' , header: "Market" , align:"center" , width :100 , editor : {type: "text", maxLength: 42}},
    	{id: 'skuName' , header: "Brand Name" , align:"center" , width :100 , editor : {type: "text", maxLength: 42, 
			validator : function(value,record,colObj,grid) {
				if (value == "") {
					alert(grid.getMsg('INVALID_INPUT'));
					return false;
				}
			}}},
    	{id: 'sellerProdCode' , header: "Seller Product Code" , align:"center" , width :150 , editor : {type: "text", maxLength: 32}},
    	{id: 'buyerProdCode' , header: "Buyer Product Code" , align:"center" , width :150 , editor : {type: "text", maxLength: 32}},
    	{id: 'location' , header: "Home" , align:"center" , width :100 , editor : {type: "text", maxLength: 42}},
    	{id: 'grade' , header: "Grade" , align:"center" , width :100 , editor : {type: "text", maxLength: 42}},
    	{id: 'pmfClass' , header: "Class" , align:"center" , width :100, editor : {type: "text", maxLength: 42} },
    	{id: 'price1' , header: "Price1" , align:"center" , width :100 , editor : {type: "text", maxLength: 9,validRule:['N'], validator : numberValidator}, renderer : zeroRenderer},
    	{id: 'price2' , header: "Price2" , align:"center" , width :100 , editor : {type: "text", maxLength: 9,validRule:['N'], validator : numberValidator}, renderer : zeroRenderer},
    	{id: 'priceNoTax' , header: "Price w/o Tax" , align:"center" , width :100 , editor : {type: "text",validRule:['N'], maxLength: 9, validator : numberValidator}, renderer : zeroRenderer},
    	{id: 'pricewTax' , header: "Price w/ Tax" , align:"center" , width :100 , renderer : pricewtaxRenderer},
    	{id: 'pkgQuantity' , header: "Quantity" , align:"center" , width :60 , editor : {type: "text", maxLength: 9, validator : pkgQtyValidator}, renderer : zeroRenderer},
    	{id: 'pkgType' , header: "Type" , align:"center" , width :60 , editor : {type: "text", maxLength: 42}},
    	{id: 'unitOrder' , header: "Unit Order" , align:"center" , width :80 , editor : {type: "select" ,options : ${orderUnitList} ,defaultText : '0', 
			validator : function(value,record,colObj,grid) {
				if (value == 0) {
					alert(grid.getMsg('INVALID_INPUT'));
					return false;
				}
			}}, renderer : unitorderRenderer},
    	{id: 'commentA' , header: "Comment A" , align:"center" , width :100 , editor : {type: "text",maxLength: 64}},
    	{id: 'commentB' , header: "Comment B" , align:"center" , width :100 , editor : {type: "text",maxLength: 64}},
    	{id: 'skuId' , header: "SKU Id" , align:"center" , width :100 , editor : {type: "text"}, hidden : true},
    	{id: 'pmfId' , header: "PMF Id" , align:"center" , width :100 , editor : {type: "text"}, hidden : true},
    	{id: 'sellerId' , header: "Seller Id" , align:"center" , width :100 , editor : {type: "text"}, hidden : true}
    ];

	var gridOption={
		id : grid_demo_id,
		encoding:'UTF-8',
		loadURL : '<c:url value="/pmf/pmfSkuList.json" />',
		saveURL : '<c:url value="/pmf/pmfSkuSave.json" />',
		width: "100%",  //"100%", // 700,
		height: "440",  //"100%", // 330,
		container : 'gridbox', 
		replaceContainer : true, 
		customHead : 'myHead1',
		dataset : dsOption ,
		columns : colsOption,
		pageSize : 120 ,
		pageSizeList : [40, 80, 120, 160, 200],
		toolbarContent : 'nav | goto | pagesize state',
		language : 'ja',
		showGridMenu : false,
		allowCustomSkin : true,
		allowFreeze : false,
		allowGroup : true,
		allowHide : true,
		skin : "default",
		customRowAttribute : function(record,rn,grid){
			return 'style="height:30px;"';
		},
		<c:choose>
			<c:when test="${isAdmin eq false}">
				defaultRecord : {myselect : "", companyName: "${user.company.shortName}", sellerName : "${user.shortName}", skuGroup : "0", 
			</c:when>
			<c:otherwise>
				defaultRecord : {myselect : "", companyName: "${pmfCompanyName}", sellerName : "${pmfSellerName}", skuGroup : "0", 
			</c:otherwise>
		</c:choose>
 		market : "", skuName : "", sellerProdCode : "", buyerProdCode: "", 
 		location : "", grade : "", pmfClass : "", price1 : "", price2 : "", priceNoTax : "", 
 		pkgQuantity : "", pkgType : "", unitOrder : "99", commentA : "",  commentB : "", 
 		skuId : "", pmfId : "${pmfId}"},
							

		loadResponseHandler : function(response, requestParameter) {
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
			        mygrid.showWaiting();
			        $.ajax({
			            url: "/eON/pmf/pmfSellerSelect.json",
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

		onInvalidInput : function(value,oldValue,activeRecord, cell, activeColumn, validResult, grid){
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
		
		saveResponseHandler : function(response, requestParameter) {
			//alert("response.text: " + response.text);
			var result = validateResponseOnIframe(response.text);
			if (result) {
				alert(mygrid.getMsg('SAVED'));
			}
			//window.parent.orderSheet.computeGTPrice();
		}
	};

	var mygrid = new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
	    
	function showDialogHideColumns() {
		dlg = Sigma.ad2("show",{gridId:"myGrid1",checkValid:function($){
				return $.frozen;
		},checkFn:"show",uncheckFn:"hide",canCheck:function($){
	        return !$.hidden;
		}});
		dlg.show();
		dlg.setTitle("Hide Columns");
	}

	function addRow() {
		mygrid.insert("", true, 0);
		mygrid.bodyDiv.scrollTop = 0;
	}

	function updateTable(){
		mygrid.showWaiting();
		mygrid.addParameters({action:"save", option : colsOption});
		var ret = validateSKUGroupsAndOrderUnits();

		if (ret != false) {
			ret = mygrid.save();
			//if (ret != false)
			//	window.parent.orderSheet.disableActionButtons();

			if (ret != null) // error on input
				alert(mygrid.getMsg('REQUIRED_FIELDS_NOT_POPULATED'));
		}
		else {
			alert(mygrid.getMsg('REQUIRED_FIELDS_NOT_POPULATED'));
		}
		mygrid.hideWaiting();
	}
	
	function validateSKUGroupsAndOrderUnits () {

		var records = mygrid.getInsertedRecords();
		var i=0;
		for (i=0; i<records.length; i++) {
			if (records[i].skuGroup == '0' || records[i].unitOrder == '0' || records[i].pkgQty == '')
				return false;
		}
		
		return true;
	}
	
	function transfer(sheetName) {
		var recs = mygrid.getDeletedRecords();
	    var arrRecs = [];
	    for(var i=0;i<recs.length;i++){
		    var obj = new Object();
		    obj.sellerId = recs[i].sellerId;
		    obj.companyname = recs[i].companyName;
			obj.skuId = recs[i].skuId;
	        obj.sellername = recs[i].sellerName;
	        obj.groupname = recs[i].skuGroup;
	        obj.marketname = recs[i].market;
	        obj.skuname = recs[i].skuName;
	        obj.home = recs[i].location;
	        obj.grade = recs[i].grade;
	        obj.clazzname = recs[i].pmfClass;
	        obj.price1 = recs[i].price1;
	        obj.price2 = recs[i].price2;
	        obj.pricewotax = recs[i].priceNoTax;
	        obj.pricewtax = recs[i].pricewTax;
	        obj.packageqty = recs[i].pkgQuantity;
	        obj.packagetype = recs[i].pkgType;
	        obj.unitorder = recs[i].unitOrder;
	        <c:choose>
	    		<c:when test="${isAdmin eq false}">
	        		obj.lockflag = " {'allQuantities' : 1, 'sellername' : 1, addSKU : 1}";
	        	</c:when>
	        	<c:otherwise>
	        		obj.lockflag = " {'adminAddSKU' : 1, addSKU : 1}";
	        	</c:otherwise>
	        </c:choose>
	        
	        arrRecs[i] = obj;
	    }
   
		if (window.parent.mainSPA.lblSheet.getCaption() == 10001) {
    		window.parent.orderSheet.movetosheet(arrRecs);
    	} else if (window.parent.mainSPA.lblSheet.getCaption() == 10003) {
    		window.parent.allocSheet.movetosheet(arrRecs);
    	}else if (window.parent.mainSPA.lblSheet.getCaption() == 10020) {
    		window.parent.akadenSheet.movetosheet(arrRecs);
    	}else if (window.parent.mainSPA.lblSheet.getCaption() == 10005) {
    		window.parent.orderSheet.movetosheet(arrRecs);
    	}
    }
    
	function deleteCheckBoxRenderer (value , record, columnObj, grid, colNo, rowNo) {
		var disabled = "";
		return "<input type=checkbox id='test' " + disabled + rowNo + " onclick=deleteRecord(this.id);></input>";
	}

	function deleteRecord(id) {	
		//var elem = document.getElementById(id);
		//if(elem.checked == true) {	
		//	var ans = confirm('Are you sure to delete this record?','Confirm');
		//	if (ans) 
				setTimeout('mygrid.deleteRow()',100);	
		//}		
	}
	
	function groupnameRenderer (value , record, columnObj, grid, colNo, rowNo) {
        var render = "--商品グループ--";
        var user = "S_" + '${pmfUser}';

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

	function zeroRenderer (value , record, columnObj, grid, colNo, rowNo) {
		var tmp = parseFloat(value);
		if (value ==  "undefined") return "";
		if (tmp > 0) return addCommas(value);
		else return "";
	}

	function pricewtaxRenderer (value , record, columnObj, grid, colNo, rowNo) {
		var tmp = parseFloat(value);
		if (value ==  "undefined") return "";
		if (tmp > 0) return addCommas(value);
		else return "";
	}

	function validateResponseOnIframe(jsonResponse) {
		var obj = _.unserialize(jsonResponse);
		//alert("jsonResponse: " + jsonResponse);
		var error = obj.globals;
		if (obj.fail == 'true') {
			alert(error[0]);
			return false;
		}
		return true;
	}	
	  
	function numberValidator(value, record, colObj, grid){
		if (value != "" && checkNumeric(value) == false){
			alert(grid.getMsg('INVALID_INPUT'));
			return false;
	  	}
		if(negativeValidator(value) == false){
			alert(grid.getMsg('INVALID_INPUT'));
			return false;
	  	}
	  	if(scale(value) == false){
			alert(grid.getMsg('INVALID_INPUT'));
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

	function scale(value){
		if(String(value).indexOf(".") > 0){
			//if (String(value).indexOf(".") < String(value).length - 4) {
				return false;
		  	//}  
	  	}
	  	return true;
  	}	

	function isDirty () {
		return mygrid.isDirty();
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

	function checkNumeric(value){
		var anum=/(^\d+$)/;
	    if (anum.test(value)){
	    	return true;
	    }
	    return false;
	}
</script>
</head>
<body>
<table id="myHead1" style="display: none">
	<tr style="background-color: #99CCFF; text-align: center;">
		<td rowspan="2" id='myselect'></td>
		<td rowspan="2" id='companyname'>会社名</td>
		<td rowspan="2" id='sellerName'>売主担当者</td>
		<td rowspan="2" id='skuGroup'><span class="requiredColumn">＊</span>商品グループ</td>
		<td rowspan="2" id='market'>市況</td>
		<td rowspan="2" id='skuName'><span class="requiredColumn">＊</span>商品名</td>
		<td rowspan="2" id='sellerProdCode'>売主商品コード</td>
		<td rowspan="2" id='buyerProdCode'>買主商品コード</td>
		<td rowspan="2" id='location'>産地</td>
		<td rowspan="2" id='grade'>等級</td>
		<td rowspan="2" id='pmfClass'>階級</td>
		<td rowspan="2" id='price1'>価格１</td>
		<td rowspan="2" id='price2'>価格2</td>
		<td rowspan="2" id='priceNoTax'>店着税抜</td>		
    	<td rowspan="2" id='pricewTax'>店着税込</td>
		<td rowspan="2" id='pkgQuantity'><span class="requiredColumn">＊</span>入数</td>
		<td rowspan="2" id='pkgType'>入数単位</td>
		<td rowspan="2" id='unitOrder'><span class="requiredColumn">＊</span>発注単位</td>
		<td rowspan="2" id='commentA'>コメント１</td>
		<td rowspan="2" id='commentB'>コメント２</td>
		<td rowspan="2" id='skuId'>SKU Id</td>
		<td rowspan="2" id='pmfId'>PMF Id</td>
		<td rowspan="2" id='sellerId'>Seller Id</td>
	</tr>
</table>
<div id="bigbox" style="margin: 0px; display: ! none;">
<div id="gridbox"
	style="border: 0px solid cccccc; background-color: f3f3f3; padding: 0px; height: 440; width: 100%;"></div>
<br />

</div>
</body>
</html>