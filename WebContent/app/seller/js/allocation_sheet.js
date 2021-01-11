//<%--
// * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
// * <BR>
// * <B>Description:</B><BR>
// * Describe class or interface.<BR>
// * <BR>
// * <B>Known Bugs:</B>
// * none<BR>
// * <BR>
// * <B>History:</B>
// * <PRE><!-- Do not use tabs in the history table! Do not extend table width! -->
// * date       	name	version		changes
// * ------------------------------------------------------------------------------
// * 20120731		Rhoda	v11			Redmine 354 - Unfinalize Order and Finalize Alloc concurrency
// * 20120913		lele	chrome		Redmine 880 - Chrome compatibility
// --%>
var g_orderSheetParam; // should only be changed in 'onSearch' fire event

Class('App.allocationSheet', 'linb.Com', {
	autoDestroy:true,
 Instance: {
     iniComponents:function(){
		    var host=this, children=[], append=function(child){children.push(child.get(0));};
		
		    append((new linb.UI.Pane)
             .host(host,"paneMain")
             .setLeft(0)
             .setTop(59)
             .setWidth(1080)
             .setHeight(520)
         );
         
         host.paneMain.append((new linb.UI.Block)
             .host(host,"blockMain")
             .setDock("fill")
         );
         
         host.blockMain.append((new linb.UI.Pane)
             .host(host,"paneUpperLayer")
             .setDock("top")
             .setHeight(35)
         );
         
         host.paneUpperLayer.append((new linb.UI.Block)
             .host(host,"blockUpperLayer")
             .setDock("fill")
         );
         
         host.blockUpperLayer.append((new linb.UI.Pane)
             .host(host,"paneSelectedDates")
             .setLeft(210)
             .setWidth(780)
             .setHeight(40)
             .setRight(12)
             .setVisibility("hidden")
         );
         
         host.paneSelectedDates.append((new linb.UI.ComboInput)
             .host(host,"cboBuyers")
             .setDirtyMark(false)
	         .setInputReadonly(true)
             .setLeft(30)
             .setTop(5)
             .setWidth(105)
             .onChange("_cboBuyers_onchange")
             .beforeUIValueSet("_cbobuyers_beforeuivalueset")
         );
         
         host.paneSelectedDates.append((new linb.UI.Link)
             .host(host,"linkPrevious")
             .setLeft(150)
             .setTop(10)
             .setCaption("<b><<<</b>")
             .onClick("_linkprevious_onclick")
         );
         
         host.paneSelectedDates.append((new linb.UI.Link)
             .host(host,"linkNext")
             .setLeft(185)
             .setTop(10)
             .setCaption("<b>>>></b>")
             .onClick("_linknext_onclick")
         );
         
         host.paneSelectedDates.append((new linb.UI.Div)
             .host(host,"datePanel")
             .setTop(10)
             .setWidth(550)
             .setHeight(26)
             .setRight(10)
         );
         
         host.blockUpperLayer.append((new linb.UI.Button)
             .host(host,"btnSearch")
             .setLeft(10)
             .setTop(5)
             .setWidth(80)
             .setHeight(25)
             .setCaption("$app.caption.filterPage")
             .onClick("_btnsearch_onclick")
         );
         
         host.blockMain.append((new linb.UI.Pane)
             .host(host,"paneOptionbuttons")
             .setDock("top")
             .setHeight(36)
             .setVisibility("hidden")
         );
         
         host.paneOptionbuttons.append((new linb.UI.Block)
             .host(host,"blockOptionButtons")
             .setDock("fill")
         );
         
         host.blockOptionButtons.append((new linb.UI.Button)
             .host(host,"btnAddSKU")
             .setLeft(10)
             .setTop(4)
             .setWidth(70)
             .setShadow(true)
             .setCaption("$app.caption.addsku")
             .setImage("../../common/img/tool_add.gif")
             .onClick("_btnaddsku_onclick")
             .setDisabled(true)
         );
         
         host.blockOptionButtons.append((new linb.UI.Button)
             .host(host,"btnAddSKUGroup")
             .setLeft(85)
             .setTop(4)
             .setWidth(130)
             .setShadow(true)
             .setCaption("$app.caption.addskugroup")
             .setImage("../../common/img/tool_addgroup.gif")
             .setHAlign("left")
             .onClick("_btnaddskugroup_onclick")
             .setDisabled(true)
         );
         
         host.blockOptionButtons.append((new linb.UI.Button)
             .host(host,"btnSave")
             .setLeft(220)
             .setTop(4)
             .setWidth(60)
             .setShadow(true)
             .setCaption("$app.caption.save")
             .setImage("../../common/img/save.gif")
             .setHAlign("left")
             .onClick("_btnSave_onclick")
             .setDisabled(true)
         );
         
         host.blockOptionButtons.append((new linb.UI.Button)
             .host(host,"btnPublish")
             .setLeft(285)
             .setTop(4)
             .setWidth(60)
             .setShadow(true)
             .setCaption("$app.caption.publish")
             .setImage("../../common/img/publish.gif")
             .onClick("_btnPublish_onclick")
             .setDisabled(true)
         );
         
         host.blockOptionButtons.append((new linb.UI.Button)
             .host(host,"btnUnpublish")
             .setLeft(350)
             .setTop(4)
             .setWidth(50)
             .setShadow(true)
             .setCaption("$app.caption.unpublish")
             .onClick("_btnUnpublish_onclick")
             .setDisabled(true)
         );
         
         host.blockOptionButtons.append((new linb.UI.Button)
             .host(host,"btnFinalize")
             .setLeft(405)
             .setTop(4)
             .setWidth(60)
             .setShadow(true)
             .setCaption("$app.caption.finalize")
             .setImage("../../common/img/finalize.gif")
             .onClick("_btnFinalize_onclick")
             .setDisabled(true)
         );
         
         host.blockOptionButtons.append((new linb.UI.Button)
             .host(host,"btnUnfinalize")
             .setLeft(470)
             .setTop(4)
             .setWidth(70)
             .setShadow(true)
             .setCaption("$app.caption.unfinalize")
             .onClick("_btnUnfinalize_onclick")
             .setDisabled(true)
         );
         
         host.blockOptionButtons.append((new linb.UI.Button)
             .host(host,"btnDownloadExcel")
             .setLeft(545)
             .setTop(4)
             .setWidth(70)
             .setShadow(true)
             .setCaption("$app.caption.downloadexcel")
             .setImage("../../common/img/excel.gif")
             .onClick("_btnDownloadExcel_onclick")
         );
         
         host.blockMain.append((new linb.UI.Pane)
             .host(host,"tmpPane")
             .setVisibility("hidden")
             .setDock("fill")
         );
         
         host.tmpPane.append((new linb.UI.Block)
             .host(host,"panelMainSheet")
             .setDock("fill")
         );
         
         host.panelMainSheet.append((new linb.UI.Tabs)
             .host(host,"categoriesTab")
             .setZIndex(0)
             .onItemSelected("_categoriestab_onitemselected")
             .beforeUIValueSet("_categoriestab_beforeuivalueset")
         );
         
         host.categoriesTab.append((new linb.UI.Pane)
				.host(host,"paneSigmaGrid")
				.setDock("fill")
			);
		    
		    return children;
		},
     events: {
         "onReady" : "_onready", "onRender":"_onRender"
     },
     iniResource: function(com, threadid) {
     },
     iniExComs: function(com, hreadid) {
     },
     _onRender:function(page, threadid){
     	allocSheet = page;
     	sellerMenu.menubar_seller.setItems(getMenuItems());
     	if(g_clientWidth > allocSheet.paneMain.getWidth()){
     		allocSheet.paneMain.setWidth(g_clientWidth);
    	}			
		if(g_clientHeight > allocSheet.paneMain.getHeight() + 60){
			allocSheet.paneMain.setHeight(g_clientHeight - 60);
    	}
     },
     _onready: function() {
     	allocSheet = this;
     	disableSellerPreferenceButtons("1");
     },
     _btnsearch_onclick:function (profile, e, src, value) {
    	 if (allocSheet.confirmNavigation()) {
	    	 var ns=this;
	    	 linb.ComFactory.newCom(
		     			'App.filter', 
		     			function(){
		     				allocSheet._excom1 = this;
		     				allocSheet._excom1.hiddenSheetTypeId.setCaption(10003);
		     				allocSheet._excom1.hiddenDealingPatternId.setCaption(1);
		     				this.setEvents({'onSearch' : function(result) { 
		     					allocSheet.onSearchEvent(result);
		     				}});
		     				ns.$cached=this;
		     				this.show(null, allocSheet.paneSubCom);
		     			}
		     	);
    	 }
     },
     loadOrderSheet : function () {
    	 var obj = new Object();
    	 obj.startDate = g_orderSheetParam.startDate;
         obj.endDate = g_orderSheetParam.endDate;
         obj.selectedSellerID = g_orderSheetParam.selectedSellerID;
         obj.selectedBuyerCompany = g_orderSheetParam.selectedBuyerCompany;
         obj.selectedBuyerID = g_orderSheetParam.selectedBuyerID;
         obj.selectedDate = g_orderSheetParam.selectedDate;
         obj.categoryId = g_orderSheetParam.categoryId;
         obj.sheetTypeId = g_orderSheetParam.sheetTypeId;
         obj.allDatesView = g_orderSheetParam.allDatesView;
         obj.checkDBOrder = g_orderSheetParam.checkDBOrder;
         
         /* reset the visibility toggle */
         allocSheet.linkPrevious.setVisibility('hidden');
         allocSheet.linkNext.setVisibility('hidden');
         allocSheet.categoriesTab.setUIValue(g_orderSheetParam.categoryId);
         
         obj.datesViewBuyerID = '0';
         if (g_orderSheetParam.allDatesView) {
         	obj.datesViewBuyerID = g_orderSheetParam.datesViewBuyerID;
         }
         else {
        	 allocSheet.cboBuyers.setValue("All", true);
         } 
         	
         /*set date panel*/
     	allocSheet.setDatePanel(true);
         
         linb.Ajax(
 	        "/eON/allocation/setParameter.json",
 	        obj,
 	        function (response) {
 	        	validateResponse(response);
 	        	var paneSigmaGridHtml = allocSheet.paneSigmaGrid.getHtml();                	
 	        	if (paneSigmaGridHtml == "") {
 	        		allocSheet.categoriesTab.setValue(g_orderSheetParam.categoryId);
 	        		allocSheet.categoriesTab.append(allocSheet.paneSigmaGrid, g_orderSheetParam.categoryId);
 	        		allocSheet.paneSigmaGrid.setHtml("<iframe id='dataTableIframe' name='dataTableIframe' frameborder=0 marginheight=0 marginwidth=0 scrolling='no' width=100% height=99% src='../jsp/allocationTable.jsp'></iframe>");
 	            } else {
 	            	allocSheet.categoriesTab.setUIValue(allocSheet.categoriesTab.getUIValue()+'');
 	            	allocSheet.categoriesTab.append(allocSheet.paneSigmaGrid, allocSheet.categoriesTab.getUIValue()+'');
 	            	// ENHANCEMENT START 20120913: Lele - Redmine 880
 	            	//allocSheet.loadDataTable();
 	            	allocSheet.paneSigmaGrid.setHtml("<iframe id='dataTableIframe' name='dataTableIframe' frameborder=0 marginheight=0 marginwidth=0 scrolling='no' width=100% height=99% src='../jsp/allocationTable.jsp'></iframe>");
 	            	allocSheet.paneSigmaGrid.refresh();
 	            	// ENHANCEMENT END 20120913:
 	            }
 	           	//enable edit
 	        	sellerMenu.menubar_seller.updateItem('mnuEdit', {disabled:false});    
 	            sellerMenu.menubar_seller.updateItem('subuprefUnitOrder', {disabled:false});
	            sellerMenu.menubar_seller.updateItem('utilPMF', {disabled:true});
	            sellerMenu.menubar_seller.updateItem('utilDownloadExcel', {disabled:false});
	            sellerMenu.menubar_seller.updateItem('mnuAllocationsheet', {disabled:true});
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
     },
     responseCheck : function (jsonResponse) {
     	var obj = _.unserialize(jsonResponse);
     	if (obj.fail == 'true') {
     		linb.pop("responseCheck", obj.globals, "OK");
     	}
     }, 
     hasNoError : function (jsonResponse) {
     	var obj = _.unserialize(jsonResponse);
     	if (obj.fail == 'true') {
     		return false;
     	}
     	return true;
     },
     _categoriestab_onitemselected:function (profile, item, src) {
         // reload sigmaTable
         // FORDELETION 20120913: Lele - Redmine 880
     	//this.categoriesTab.append(this.paneSigmaGrid, item.id);
     	g_orderSheetParam.categoryId = item.id;
     	g_orderSheetParam.checkDBOrder = false;
     	allocSheet.loadOrderSheet();
     }, 
     _btnaddsku_onclick : function (p,item,src) {
     	allocSheet.addRow();
     }, 
     _btnaddskugroup_onclick:function (profile, e, src, value) {
    	 if (allocSheet.confirmNavigation()) {
	     	linb.ComFactory.newCom(
	 			'App.addSKUGroup', 
	 			function(){
	 				allocSheet._excom1 = this;
	 				allocSheet._excom1.hiddenCategoryValue.setCaption(allocSheet.categoriesTab.getUIValue());
	 				this.setEvents({'onAddSKUGroup' : function(result) {
	 					if (result == 'success') {
	    					alert('登録されました。');
		 					// reload table
				        	g_orderSheetParam.checkDBOrder = false;
				        	allocSheet.loadOrderSheet();
	 					}
	 				}});
	 				linb.Ajax(
					        "/eON/skuGroup/getSellers.json",
					        null,
					        function (response) {
					        	validateResponse(response);
					        	allocSheet._excom1.responseCheck(response);
					        	var o = _.unserialize(response);
					        	var items = o.response;
					        	allocSheet._excom1.cbiSellername.setItems(items, true);
					        	allocSheet._excom1.cbiSellername.setUIValue(items[0].caption);
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
	 				
	 				if (g_userRole == "ROLE_SELLER"){
						allocSheet._excom1.cbiSellername.setDisabled(true);
						allocSheet._excom1.cbiSellername.setVisibility('hidden');
						allocSheet._excom1.lblSkuGroupSellerName.setVisibility('hidden');
						allocSheet._excom1.inptSKUGroupName.setTop(40);
						allocSheet._excom1.lblSkuGroupName.setTop(40);
					}
	 				
	 				this.show(null, allocSheet.paneSubCom);
	 			}
	     	);
    	 }
     },
     _btnSave_onclick : function (p,item,src) {
    	this.btnSave.setDisabled(true);
     	allocSheet.saveOrder();
     },
     _btnPublish_onclick : function (p,item,src) {
    	 if (allocSheet.confirmNavigation()) {
			allocSheet.disableActionButtons();
			allocSheet.publishOrder();
    	 }
     },
     _btnUnpublish_onclick : function (p,item,src) {
    	 if (allocSheet.confirmNavigation()) {
	     	allocSheet.disableActionButtons();
	     	allocSheet.unpublishOrder();
    	 }
     },
     _btnFinalize_onclick : function (p,item,src) {
    	 if (allocSheet.confirmNavigation()) {
	     	//allocSheet.disableActionButtons();
	     	allocSheet.finalizeOrder();
    	 }
     },
     _btnUnfinalize_onclick : function (p,item,src) {
    	 if (allocSheet.confirmNavigation()) {
	     	//allocSheet.disableActionButtons();
	     	allocSheet.unfinalizeOrder();
    	 }
     },
     _btnDownloadExcel_onclick : function (p,item,src) {
     	var ns=this;
 		linb.ComFactory.newCom(
 			'App.downloadExcel', 
 			function(){
 				allocSheet._excom1 = this;
 				this.setEvents({'onDownloadExcel' : function(paramObj) { 
 					sellerMenu.downloadExcel(paramObj);
 				}});
 				ns.$cached=this;
 				this.show(null, allocSheet.paneSubCom);
 			});    		
     }, 
     _cboBuyers_onchange:function (profile, oldValue, newValue) {
     	var id;
     	var items = allocSheet.cboBuyers.getItems();
     	for (var i=0; i<items.length; i++) {
 			var thisObj = items[i];
 			if (newValue == thisObj.caption) {
 				id = thisObj.id;
 				break;
 			}
 		}
     	
     	g_orderSheetParam.datesViewBuyerID = id;
     	g_orderSheetParam.checkDBOrder = false;
     	g_orderSheetParam.allDatesView = true;
     	if (id == '0') g_orderSheetParam.allDatesView = false;
     	
     	allocSheet.loadOrderSheet();
     },
     computeForPrices : function (totalPriceWOTax, totalPriceWTax) {
    	 
    	 var extra_space = "&nbsp;&nbsp;";
    	 
    	 
    	 return linb.Locale[linb.getLang()].app.caption['totalpricewotax'] + extra_space +
     			toJapaneseCurrency(totalPriceWOTax) + extra_space + extra_space + 
     			linb.Locale[linb.getLang()].app.caption['totalpricewtax'] + extra_space + 
     			toJapaneseCurrency(totalPriceWTax) + extra_space + extra_space;
     },
     getCategoryId : function () {
     	return g_orderSheetParam.categoryId;
     },
     loadDataTable : function () {
		var f = document.getElementById('dataTableIframe');
		f.contentWindow.location.reload();
     },
     	addRow : function () {
		var f = document.getElementById('dataTableIframe');
		f.contentWindow.addRow();
     },
     	saveOrder : function () {
		var f = document.getElementById('dataTableIframe');
		f.contentWindow.onSave();
     },
     	//ENHANCEMENT START 20120727: Rhoda Redmine 354
     	alertConcurrencyMsgFinalize:function(concurrencyResp){
    	if (concurrencyResp.isReceivedFinalized == "true"){
      		var alertMsg = linb.Locale[linb.getLang()].app.caption['ALERT_CONCURRENCY_IS_RECVD_FIN'];
      		alert(alertMsg);
      	}else if (concurrencyResp.isAllocFinalized == "true"){
     		var alertMsg = linb.Locale[linb.getLang()].app.caption['ALERT_CONCURRENCY_IS_ALLOC_FIN'];
     		alert(alertMsg);
     	}else{
     		var alertMsg = linb.Locale[linb.getLang()].app.caption['ALERT_CONCURRENCY_FINALIZE'];
			alert(alertMsg);
     	}
     },
     alertConcurrencyMsgUnPublish:function(concurrencyResp){
     	if (concurrencyResp.isReceivedFinalized == "true"){
       		var alertMsg = linb.Locale[linb.getLang()].app.caption['ALERT_CONCURRENCY_IS_RECVD_FIN'];
       		alert(alertMsg + '\n' + concurrencyResp.concurrencyMsg);
       	}else if (concurrencyResp.isAllocFinalized == "true"){
      		var alertMsg = linb.Locale[linb.getLang()].app.caption['ALERT_CONCURRENCY_IS_ALLOC_FIN'];
       		alert(alertMsg + '\n' + concurrencyResp.concurrencyMsg);
      	}else{
      		var alertMsg = linb.Locale[linb.getLang()].app.caption['ALERT_CONCURRENCY_UNPUBLISH'];
       		alert(alertMsg + '\n' + concurrencyResp.concurrencyMsg);
      	}
      },
     alertConcurrencyMsgUnFinalize:function(concurrencyResp){
     	if (concurrencyResp.isReceivedFinalized == "true"){
       		var alertMsg = linb.Locale[linb.getLang()].app.caption['ALERT_CONCURRENCY_IS_RECVD_FIN'];
       		alert(alertMsg + '\n' + concurrencyResp.concurrencyMsg);
       	}else if (concurrencyResp.isAllocUnFinalized == "true"){
      		var alertMsg = linb.Locale[linb.getLang()].app.caption['ALERT_CONCURRENCY_IS_ALLOC_UNFIN'];
      		alert(alertMsg + '\n' +concurrencyResp.concurrencyMsg);
      	}else{
      		var alertMsg = linb.Locale[linb.getLang()].app.caption['ALERT_CONCURRENCY_UNFINALIZE'];
 			alert(alertMsg + '\n' +concurrencyResp.concurrencyMsg);
      	}
      },
     	alertConcurrencyMsgSave:function(concurrencyResp){
    	if (concurrencyResp.isSellerOrderUnFinalized == "true"){
    		var alertMsg = linb.Locale[linb.getLang()].app.caption['ALERT_CONCURRENCY_IS_ORDER_UNFIN'];
    		alert(alertMsg);
    	}else if (concurrencyResp.isReceivedFinalized == "true"){
    		var alertMsg = linb.Locale[linb.getLang()].app.caption['ALERT_CONCURRENCY_IS_RECVD_FIN'];
    		alert(alertMsg);
    	}else if (concurrencyResp.isAllocFinalized == "true"){
    		var alertMsg = linb.Locale[linb.getLang()].app.caption['ALERT_CONCURRENCY_IS_ALLOC_FIN'];
    		alert(alertMsg);
    	}
		//ENHANCEMENT END 20120727: Rhoda Redmine 354
	 },
     	publishOrder : function () {
		var f = document.getElementById('dataTableIframe');
		f.contentWindow.onPublish();
     },
     	unpublishOrder : function () {
		var f = document.getElementById('dataTableIframe');
		f.contentWindow.onUnpublish();
     },
     	finalizeOrder : function () {
		var f = document.getElementById('dataTableIframe');
		f.contentWindow.onFinalize();
     },
     	unfinalizeOrder : function () {
		var f = document.getElementById('dataTableIframe');
		f.contentWindow.onUnfinalize();
     },
     _linkprevious_onclick:function (profile, e) {
    	 if (allocSheet.confirmNavigation()) {
	     	var newDates = getNewStartEndDate(g_orderSheetParam, 'previous');
	     	g_orderSheetParam.startDate = newDates.startDate;
	     	g_orderSheetParam.endDate = newDates.endDate;
	     	g_orderSheetParam.selectedDate = newDates.selectedDate;
	     	g_orderSheetParam.checkDBOrder = true;
	         
	     	allocSheet.loadOrderSheet();
    	 }
     },
     _linknext_onclick:function (profile, e) {
    	 if (allocSheet.confirmNavigation()) {
	     	var newDates = getNewStartEndDate(g_orderSheetParam, 'next');
	     	g_orderSheetParam.startDate = newDates.startDate;
	     	g_orderSheetParam.endDate = newDates.endDate;
	     	g_orderSheetParam.selectedDate = newDates.selectedDate;
	     	g_orderSheetParam.checkDBOrder = true;
	         
	     	allocSheet.loadOrderSheet();
    	 }
     },
     setDatePanel : function (disabled) {
     	allocSheet.datePanel.setHtml(setDatePanel(g_orderSheetParam, disabled, allocSheet), true);
     },
     onSearchEvent : function (result) {
    	 
    	//set categorytabs
    	allocSheet.paneSigmaGrid.setHtml("");
    	allocSheet.categoriesTab.setItems(categoryTabs);
    
     	g_orderSheetParam = result;
		g_orderSheetParam.selectedDate = g_orderSheetParam.startDate;
		g_orderSheetParam.allDatesView = false;
		g_orderSheetParam.checkDBOrder = true;
		allocSheet.disableActionButtons();
		allocSheet.loadOrderSheet();
		allocSheet.paneSelectedDates.reBoxing().show();
		allocSheet.paneOptionbuttons.reBoxing().show();
		//Start - Chrome Fix - Buttons
		allocSheet.blockOptionButtons.setVisibility('visible');
		allocSheet.blockOptionButtons.reBoxing().show();
		//End - Chrome Fix - Buttons
		allocSheet.panelMainSheet.reBoxing().show();
		allocSheet.cboBuyers.setItems(g_orderSheetParam.buyerCombo, true);
     },
     movetosheet: function(rec) {
         var f = document.getElementById('dataTableIframe');
         f.contentWindow.transfernow(rec);
     },
     downloadExcel : function (paramObj) {
     	var f = document.getElementById('dataTableIframe');
			f.contentWindow.submitTempForm(_.serialize(paramObj));
     },
     disableActionButtons : function () {
    	this.setVisibilityForActionButtons();
     	this.btnAddSKU.setDisabled(true);
     	this.btnAddSKUGroup.setDisabled(true);
     	this.btnSave.setDisabled(true);
     	this.btnPublish.setDisabled(true);
     	this.btnUnpublish.setDisabled(true);
     	this.btnFinalize.setDisabled(true);
     	this.btnUnfinalize.setDisabled(true);
      	//Download Excel Button Always Enabled
      	this.btnDownloadExcel.setDisabled(false);
     },
     setVisibilityForActionButtons : function () {
      	this.btnAddSKU.setVisibility('visible');
      	this.btnAddSKUGroup.setVisibility('visible');
      	this.btnSave.setVisibility('visible');
      	this.btnPublish.setVisibility('visible');
      	this.btnUnpublish.setVisibility('visible');
      	this.btnFinalize.setVisibility('visible');
      	this.btnUnfinalize.setVisibility('visible');
      	this.btnDownloadExcel.setVisibility('visible');
      	this.cboBuyers.setVisibility('visible');
      },
     
     checkForDirtyTable : function () {
     	var f = document.getElementById('dataTableIframe');
     	if (f != null) {
				var ret = f.contentWindow.isDirty();
				return ret;
     	}
     	return false;
     },
     confirmNavigation : function () {
     	if (allocSheet.checkForDirtyTable()) {
     		alert(linb.Locale[linb.getLang()].app.caption['alertUnsavedChanges']);
    		return false;
     	}
     	return true;
     },
     _categoriestab_beforeuivalueset : function (profile, oldValue, newValue) {
    	 return allocSheet.confirmNavigation();
     },
     _cbobuyers_beforeuivalueset : function (profile, oldValue, newValue) {
    	 return allocSheet.confirmNavigation();
     },
     disableMenus : function () {
     	sellerMenu.menubar_seller.updateItem('utilPMF', {disabled:true});
     },
     refreshment : function () {
     	g_orderSheetParam.checkDBOrder = true;
     	allocSheet.loadOrderSheet();
     },
     enableMenus : function () {
     	sellerMenu.menubar_seller.updateItem('utilPMF', {disabled:true});
     }
 }
});