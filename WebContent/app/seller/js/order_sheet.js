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
// should only be changed when selecting a date from date panel
//var g_selectedDate;
//var g_allDatesView; //should be change to false 'onSearch'; true when changing buyer combo box
//var g_datesViewBuyerID;

Class('App.orderSheet', 'linb.Com', {
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
            
            host.panelMainSheet.append((new linb.UI.CheckBox)
                .host(host,"visToggle")
                .setDirtyMark(false)
                .setRight(120)
                .setTop(2)
                .setWidth(35)
                .setCaption("")
                .onChecked("_visToggle_onchecked")
                .setDisabled(true)
                .setVisibility("hidden")
            );
            
            host.panelMainSheet.append((new linb.UI.Label)
                .host(host,"labelVisToggle")
                .setRight(25)
                .setTop(5)
                .setWidth(100)
                .setHAlign("left")
                .setCaption("$app.caption.visibilitytoggle")
                .setVisibility("hidden")
            );
                              
            host.blockUpperLayer.append((new linb.UI.Button)
                .host(host,"lblRoundOffSheet")
                .setLeft(190)
                .setTop(60)
                .setWidth(100)
                .setVisibility("hidden")
                .setCaption("sheet")
            );
            
            host.blockUpperLayer.append((new linb.UI.Button)
                .host(host,"lblSelectedDate")
                .setLeft(190)
                .setTop(60)
                .setWidth(100)
                .setVisibility("hidden")
                .setCaption("date")
            );
                        
            host.categoriesTab.append((new linb.UI.Pane)
				.host(host,"paneSigmaGrid")
				.setDock("fill")
			);
		    
		    return children;
		},
        events: {
            "onReady" : "_onready", "onRender":"_onRender", "onDestroy":"_ondestroy"
        },
        _ondestroy:function (com) {        	
        	if(currentContent._excom1) {
        		currentContent._excom1.destroy();
        	}
        },
        iniResource: function(com, threadid) {
        },
        iniExComs: function(com, hreadid) {
        },
        _onRender:function(page, threadid){
        	orderSheet = page;
        	sellerMenu.menubar_seller.setItems(getMenuItems());
        	if(g_clientWidth > orderSheet.paneMain.getWidth()){
        		orderSheet.paneMain.setWidth(g_clientWidth);
        	}			
			if(g_clientHeight > orderSheet.paneMain.getHeight() + 60){
        		orderSheet.paneMain.setHeight(g_clientHeight - 60);
        	}
        },
        _onready: function() {
        	orderSheet = this;
        	//disable buttons
        	disableSellerPreferenceButtons("0");
        },
        _btnsearch_onclick:function (profile, e, src, value) {
        	if (orderSheet.confirmNavigation()) {
	        	var ns=this;
	        	linb.ComFactory.newCom(
		    			'App.filter', 
		    			function(){
		    				orderSheet._excom1 = this;
		    				orderSheet._excom1.hiddenSheetTypeId.setCaption(10001);
		    				orderSheet._excom1.hiddenDealingPatternId.setCaption(1);
		    				this.setEvents({'onSearch' : function(result) { 
		    					orderSheet.onSearchEvent(result);
		    				}});
		    				ns.$cached=this;
		    				this.show(null, orderSheet.paneSubCom);
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
            
            orderSheet.lblRoundOffSheet.setCaption("loadOrder");
            orderSheet.lblSelectedDate.setCaption(g_orderSheetParam.selectedDate);
            
            /* reset the visibility toggle */
            orderSheet.visToggle.setUIValue(false);
            orderSheet.visToggle.setDisabled(true);
            orderSheet.linkPrevious.setVisibility('hidden');
            orderSheet.linkNext.setVisibility('hidden');
            orderSheet.categoriesTab.setUIValue(g_orderSheetParam.categoryId);
            
            obj.datesViewBuyerID = '0';
            if (g_orderSheetParam.allDatesView) {
            	obj.datesViewBuyerID = g_orderSheetParam.datesViewBuyerID;
            	orderSheet.visToggle.setVisibility("hidden");
            	orderSheet.labelVisToggle.setVisibility("hidden");
            }
            else{
            	//start:jr
            	//sets the value "ALL" when buyerIds are more than 1
            	//if(g_orderSheetParam.buyerCombo.length>1){
            		orderSheet.cboBuyers.setValue("All", true);
            	//}else{
            		//orderSheet.cboBuyers.setValue(g_orderSheetParam.buyerCombo[0].caption, true);
            	//}
            	//end:jr
            } 
            	
            
            
            /*set date panel*/
        	orderSheet.setDatePanel(true);
            
            linb.Ajax(
    	        "/eON/order/setParameter.json",
    	        obj,
    	        function (response) {
    	        	validateResponse(response);
    	        	var paneSigmaGridHtml = orderSheet.paneSigmaGrid.getHtml();                	
    	        	if (paneSigmaGridHtml == "") {
    	        		orderSheet.categoriesTab.setValue(g_orderSheetParam.categoryId);
    	        		orderSheet.categoriesTab.append(orderSheet.paneSigmaGrid, g_orderSheetParam.categoryId);
    	        		orderSheet.paneSigmaGrid.setHtml("<iframe id='dataTableIframe' name='dataTableIframe' frameborder=0 marginheight=0 marginwidth=0 scrolling='no' width=100% height=99% src='../jsp/sigmaTable.jsp'></iframe>");
    	            } else {
    	            	orderSheet.categoriesTab.setUIValue(orderSheet.categoriesTab.getUIValue()+'');
    	            	orderSheet.categoriesTab.append(orderSheet.paneSigmaGrid, orderSheet.categoriesTab.getUIValue()+'');
    	            	// ENHANCEMENT START 20120913: Lele - Redmine 880
    	            	//orderSheet.loadDataTable();
    	            	orderSheet.paneSigmaGrid.setHtml("<iframe id='dataTableIframe' name='dataTableIframe' frameborder=0 marginheight=0 marginwidth=0 scrolling='no' width=100% height=99% src='../jsp/sigmaTable.jsp'></iframe>");
    	            	orderSheet.paneSigmaGrid.refresh();
    	            	// ENHANCEMENT END 20120913:
    	            }
    	           	//enable edit
    	        	sellerMenu.menubar_seller.updateItem('mnuEdit', {disabled:false});    
    	            sellerMenu.menubar_seller.updateItem('subuprefRoundoff', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('roundoff1', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('roundoff2', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('roundoff3', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('subuprefUnitOrder', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('utilUploadFile', {disabled:true});
    	            sellerMenu.menubar_seller.updateItem('utilPMF', {disabled:true});
    	            sellerMenu.menubar_seller.updateItem('utilDownloadExcel', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('mnuOrdersheet', {disabled:true});
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
        	orderSheet.loadOrderSheet();
        }, 
        _btnaddsku_onclick : function (p,item,src) {
        	orderSheet.addRow();
        }, 
        _btnaddskugroup_onclick:function (profile, e, src, value) {
        	if (orderSheet.confirmNavigation()) {
	        	linb.ComFactory.newCom(
	    			'App.addSKUGroup', 
	    			function(){
	    				orderSheet._excom1 = this;
	    				orderSheet._excom1.hiddenCategoryValue.setCaption(orderSheet.categoriesTab.getUIValue());
	    				this.setEvents({'onAddSKUGroup' : function(result) {
	    					if (result == 'success') {
		    					alert('登録されました。');
		        				// reload table
					        	g_orderSheetParam.checkDBOrder = false;
					        	orderSheet.loadOrderSheet();
	    					}
	    				}});
	
						linb.Ajax(
					        "/eON/skuGroup/getSellers.json",
					        null,
					        function (response) {
					        	validateResponse(response);
					        	orderSheet._excom1.responseCheck(response);
					        	var o = _.unserialize(response);
					        	var items = o.response;
					        	orderSheet._excom1.cbiSellername.setItems(items, true);
					        	orderSheet._excom1.cbiSellername.setUIValue(items[0].caption);
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
	    					orderSheet._excom1.cbiSellername.setDisabled(true);
	    					orderSheet._excom1.cbiSellername.setVisibility('hidden');
	    					orderSheet._excom1.lblSkuGroupSellerName.setVisibility('hidden');
	    					orderSheet._excom1.inptSKUGroupName.setTop(40);
	    					orderSheet._excom1.lblSkuGroupName.setTop(40);
	    				} 
	    					
	    				this.show(null, orderSheet.paneSubCom);
	    			}
	        	);
        	}
        },
        _btnSave_onclick : function (p,item,src) {
        	this.btnSave.setDisabled(true);
        	orderSheet.saveOrder();
        },
        _btnPublish_onclick : function (p,item,src) {
        	if (orderSheet.confirmNavigation()) {
	        	orderSheet.disableActionButtons();
	        	orderSheet.publishOrder();
	        	g_orderSheetParam.checkDBOrder = true;
	        	orderSheet.loadOrderSheet();
        	}
        },
        _btnUnpublish_onclick : function (p,item,src) {
        	if (orderSheet.confirmNavigation()) {
	        	orderSheet.disableActionButtons();
	        	orderSheet.unpublishOrder();
	        	g_orderSheetParam.checkDBOrder = true;
	        	orderSheet.loadOrderSheet();
        	}
        },
        _btnFinalize_onclick : function (p,item,src) {
        	if (orderSheet.confirmNavigation()) {
	        	//orderSheet.disableActionButtons();
	        	orderSheet.finalizeOrder();
        	}
        },
        _btnUnfinalize_onclick : function (p,item,src) {
        	//orderSheet.disableActionButtons();
        	orderSheet.unfinalizeOrder();
        },
        _btnDownloadExcel_onclick : function (p,item,src) {
        	var ns=this;
    		linb.ComFactory.newCom(
    			'App.downloadExcel', 
    			function(){
    				orderSheet._excom1 = this;
    				this.setEvents({'onDownloadExcel' : function(paramObj) { 
    					sellerMenu.downloadExcel(paramObj);
    				}});
    				ns.$cached=this;
    				this.show(null, orderSheet.paneSubCom);
    			});    		
        }, 
        _btnDownloadPDF_onclick : function (p,item,src) {
            var ns=this;
            linb.ComFactory.newCom(
                'App.downloadPDF', 
                function(){
                    orderSheet._excom1 = this;
                    this.setEvents({'onDownloadPDF' : function(paramObj) { 
                        sellerMenu.downloadExcel(paramObj);
                    }});
                    ns.$cached=this;
                    this.show(null, orderSheet.paneSubCom);
                });         
        }, 
        _cboBuyers_onchange:function (profile, oldValue, newValue) {
        	var id;
        	var items = orderSheet.cboBuyers.getItems();
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
        	
        	orderSheet.loadOrderSheet();
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
        _visToggle_onchecked : function (profile, e, src) {
        	orderSheet.toggleVisibilities(e);
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
		//ENHANCEMENT START 20120727: Rhoda Redmine 354
		alertConcurrencyMsg:function(concurrencyObj){
			var concurrencyMsg = concurrencyObj.concurrencyMsg;
			var msgCode = 'ALERT_CONCURRENCY';
			if (concurrencyObj.isAllocFinalized == 'true') {
				msgCode = 'ALERT_CONCURRENCY_UNFINALIZE_ORDER';
			} 
			var alertMsg = concurrencyMsg
			//alert(alertMsg + concurrencyMsg);
			if(concurrencyObj.action!='save'){
				alertMsg = linb.Locale[linb.getLang()].app.caption[msgCode];
			}
			linb.ComFactory.newCom(
        			'App.alertPopUp', 
        			function(){
        				this.alertDialog.showModal();
        				this.loadAlertDisplay(this,alertMsg);
        			}
            	);
		},
		//ENHANCEMENT END 20120727: Rhoda Redmine 354
		toggleVisibilities : function (checked) {
			var f = document.getElementById('dataTableIframe');
			if (f == null) {
				alert(checked);
				return;
			}
			//f.contentWindow.refreshing();
			f.contentWindow.toggleVisibilities(checked);
        },
        _linkprevious_onclick:function (profile, e) {
        	if (orderSheet.confirmNavigation()) {
	        	var newDates = getNewStartEndDate(g_orderSheetParam, 'previous');
	        	g_orderSheetParam.startDate = newDates.startDate;
	        	g_orderSheetParam.endDate = newDates.endDate;
	        	g_orderSheetParam.selectedDate = newDates.selectedDate;
	        	g_orderSheetParam.checkDBOrder = true;
	            
	        	orderSheet.loadOrderSheet();
        	}
        },
        _linknext_onclick:function (profile, e) {
        	if (orderSheet.confirmNavigation()) {
	        	var newDates = getNewStartEndDate(g_orderSheetParam, 'next');
	        	g_orderSheetParam.startDate = newDates.startDate;
	        	g_orderSheetParam.endDate = newDates.endDate;
	        	g_orderSheetParam.selectedDate = newDates.selectedDate;
	        	g_orderSheetParam.checkDBOrder = true;
	            
	        	orderSheet.loadOrderSheet();
        	}
        },
        setDatePanel : function (disabled) {
        	orderSheet.datePanel.setHtml(setDatePanel(g_orderSheetParam, disabled, orderSheet), true);
        },
        onSearchEvent : function (result) {
        	//set categorytabs
        	orderSheet.paneSigmaGrid.setHtml("");
        	orderSheet.categoriesTab.setItems(categoryTabs);
        	
        	g_orderSheetParam = result;
			g_orderSheetParam.selectedDate = g_orderSheetParam.startDate;
			g_orderSheetParam.allDatesView = false;
			g_orderSheetParam.checkDBOrder = true;
			orderSheet.disableActionButtons();
			orderSheet.loadOrderSheet();
			orderSheet.paneSelectedDates.reBoxing().show();
			orderSheet.paneOptionbuttons.reBoxing().show();
			//Start - Chrome Fix - Buttons
			orderSheet.blockOptionButtons.setVisibility('visible');
			orderSheet.blockOptionButtons.reBoxing().show();
			//End - Chrome Fix - Buttons
			orderSheet.panelMainSheet.reBoxing().show();
			orderSheet.cboBuyers.setItems(g_orderSheetParam.buyerCombo, true);
        },
        movetosheet: function(rec) {
            var f = document.getElementById('dataTableIframe');
            f.contentWindow.transfernow(rec);
        },
        downloadExcel : function (paramObj) {
        	var f = document.getElementById('dataTableIframe');
			f.contentWindow.submitTempForm(_.serialize(paramObj));
        },
        downloadPDF : function (paramObj) {
            var f = document.getElementById('dataTableIframe');
            f.contentWindow.submitPDFTempForm(_.serialize(paramObj));
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
        
        setVisibilityForActionButtons : function(){
        	//Start - Chrome Fix - Buttons
        	this.btnAddSKU.setVisibility('visible');
        	this.btnAddSKUGroup.setVisibility('visible');
        	this.btnSave.setVisibility('visible');
        	this.btnPublish.setVisibility('visible');
        	this.btnUnpublish.setVisibility('visible');
        	this.btnFinalize.setVisibility('visible');
        	this.btnUnfinalize.setVisibility('visible');
        	this.btnDownloadExcel.setVisibility('visible');
        	this.cboBuyers.setVisibility('visible');
        	//End - Chrome Fix - Buttons
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
        	if (orderSheet.checkForDirtyTable()) {
        		alert(linb.Locale[linb.getLang()].app.caption['alertUnsavedChanges']);
        		return false;
        	}
        	return true;
        },
        _categoriestab_beforeuivalueset : function (profile, oldValue, newValue) {
        	return orderSheet.confirmNavigation();
        },
        _cbobuyers_beforeuivalueset : function (profile, oldValue, newValue) {
        	return orderSheet.confirmNavigation();
        },
        disableMenus : function () {
        	sellerMenu.menubar_seller.updateItem('utilUploadFile', {disabled:true});
			sellerMenu.menubar_seller.updateItem('utilPMF', {disabled:true});
        },
        refreshment : function () {
        	g_orderSheetParam.checkDBOrder = true;
        	orderSheet.loadOrderSheet();
        },
        enableMenus : function () {
        	sellerMenu.menubar_seller.updateItem('utilUploadFile', {disabled:true});
			sellerMenu.menubar_seller.updateItem('utilPMF', {disabled:true});
        }
    }
});
