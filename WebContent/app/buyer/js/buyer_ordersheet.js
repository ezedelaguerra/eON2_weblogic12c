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
// * 20120608		Rhoda	v11			Redmine 68 - Concurrency
// * 20120913		Lele	chrome		Redmine 880 - Chrome compatibility
// --%>
var g_orderSheetParam; // should only be changed in 'onSearch' fire event
// should only be changed when selecting a date from date panel
var g_selectedDate;
var g_allDatesView; //should be change to false 'onSearch' if buyer admin; true when changing buyer combo box
var g_datesViewBuyerID;

Class('App.ordersheet', 'linb.Com',{
	autoDestroy:true,
    Instance:{
        iniComponents:function(){
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
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
                .setLeft(150)
                .setWidth(840)
                .setHeight(40)
                .setRight(12)
                .setVisibility("hidden")
            );
            
            host.paneSelectedDates.append((new linb.UI.ComboInput)
                .host(host,"cboBuyers")
                .setDirtyMark(false)
	            .setInputReadonly(true)
                .setLeft(110)
                .setTop(5)
                .setWidth(105)
                .onChange("_cboBuyers_onchange")
                .beforeUIValueSet("_cbobuyers_beforeuivalueset")
                .setVisibility("hidden")
            );

            host.paneSelectedDates.append((new linb.UI.ComboInput)
                .host(host,"cboSellers")
                .setVisibility("hidden")
            );
            
            host.paneSelectedDates.append((new linb.UI.Link)
                .host(host,"linkPrevious")
                .setLeft(230)
                .setTop(10)
                .setCaption("<b><<<</b>")
                .onClick("_linkprevious_onclick")
            );
            
            host.paneSelectedDates.append((new linb.UI.Link)
                .host(host,"linkNext")
                .setLeft(260)
                .setTop(10)
                .setCaption("<b>>>></b>")
                .onClick("_linknext_onclick")
            );
            
            host.paneSelectedDates.append((new linb.UI.Div)
                .host(host,"datePanel")
                .setTop(10)
                .setWidth(550)
                .setHeight(26)
                .setRight(0)
            );
            
            host.paneSelectedDates.append((new linb.UI.CheckBox)
                .host(host,"chkboxalldates")
                .setDirtyMark(false)
                .setLeft(105)
                .setTop(5)
                .setWidth(110)
                .setCaption("$app.caption.alldates")
                .onChecked("_chkboxalldates_onchecked")
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
                .setVisibility("hidden")
            );
            
            host.blockOptionButtons.append((new linb.UI.Button)
                .host(host,"btnAddSKUGroup")
                .setLeft(70)
                .setTop(4)
                .setWidth(130)
                .setShadow(true)
                .setCaption("$app.caption.addskugroup")
                .setImage("../../common/img/tool_addgroup.gif")
                .setHAlign("left")
                .onClick("_btnaddskugroup_onclick")
                .setVisibility("hidden")
            );
            
            host.blockOptionButtons.append((new linb.UI.Button)
                .host(host,"btnSave")
                .setLeft(210)
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
                .setLeft(280)
                .setTop(4)
                .setWidth(60)
                .setShadow(true)
                .setCaption("$app.caption.publish")
                .setImage("../../common/img/publish.gif")
                .onClick("_btnPublish_onclick")
                .setVisibility("hidden")
            );
            
            host.blockOptionButtons.append((new linb.UI.Button)
                .host(host,"btnLock")
                .setLeft(280)
                .setTop(4)
                .setWidth(60)
                .setShadow(true)
                .setCaption("$app.caption.lock")
                .setImage("../../common/img/lock.gif")
                .onClick("_btnLock_onclick")
                .setDisabled(true)
            );
            
            host.blockOptionButtons.append((new linb.UI.Button)
                .host(host,"btnUnlock")
                .setLeft(350)
                .setTop(4)
                .setWidth(90)
                .setShadow(true)
                .setCaption("$app.caption.unlock")
                .setImage("../../common/img/unlock.gif")
                .onClick("_btnUnlock_onclick")
                .setDisabled(true)
            );
            
            host.blockOptionButtons.append((new linb.UI.Button)
                .host(host,"btnDownloadExcel")
                .setLeft(450)
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
            
            host.categoriesTab.append((new linb.UI.Pane)
				.host(host,"paneSigmaGrid")
				.setDock("fill")
			);            
                       
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events: {
            "onReady" : "_onready", "onRender":"_onRender"
        },
        iniResource: function(com, threadid) {
        },
        iniExComs: function(com, hreadid) {
        },
        _onRender:function(page, threadid){
        	orderSheet = page;
        	buyerMenu.menubar.setItems(getMenuItems());
        	if(g_clientWidth > orderSheet.paneMain.getWidth()){
        		orderSheet.paneMain.setWidth(g_clientWidth);
        	}			
			if(g_clientHeight > orderSheet.paneMain.getHeight() + 60){
        		orderSheet.paneMain.setHeight(g_clientHeight - 60);
        	}
        },
        _ondestroy:function (com) {        	
        	if(currentContent._excom1) {
        		currentContent._excom1.destroy();
        	}
        },
        _onready: function() {
        	orderSheet = this;
        	disableBuyerPreferenceButtons("1");
        }, 

        _btnsearch_onclick:function (profile, e, src, value) {
        	if (orderSheet.confirmNavigation()) {
	        	var ns=this;
	        	linb.ComFactory.newCom(
	        			'App.filter', 
	        			function(){
	        				orderSheet._excom1 = this;
	        				orderSheet._excom1.hiddenSheetTypeId.setCaption(10000);
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
        	obj.selectedBuyerCompany = g_orderSheetParam.selectedBuyerCompany;
            obj.selectedBuyerID = g_orderSheetParam.selectedBuyerID;
        	obj.selectedSellerCompany = g_orderSheetParam.selectedSellerCompany;
            obj.selectedSellerID = g_orderSheetParam.selectedSellerID;
            obj.selectedDate = g_orderSheetParam.selectedDate;
            obj.categoryId = g_orderSheetParam.categoryId;
            obj.sheetTypeId = g_orderSheetParam.sheetTypeId;
            obj.allDatesView = g_orderSheetParam.allDatesView;
            obj.checkDBOrder = g_orderSheetParam.checkDBOrder;
            
            /* reset the visibility toggle */
            if (g_userRole == "ROLE_BUYER_ADMIN"){
	            orderSheet.visToggle.setUIValue(false);
	            orderSheet.visToggle.setDisabled(true);
        		orderSheet.chkboxalldates.setVisibility("hidden");
        		orderSheet.cboBuyers.setVisibility("visible");
            }
            orderSheet.linkPrevious.setVisibility('hidden');
            orderSheet.linkNext.setVisibility('hidden');
            orderSheet.categoriesTab.setUIValue(g_orderSheetParam.categoryId);
            
            orderSheet.lblRoundOffSheet.setCaption("loadBuyerOrder");

			obj.datesViewBuyerID = '0';
            if (g_orderSheetParam.allDatesView) {
            	obj.datesViewBuyerID = g_orderSheetParam.datesViewBuyerID;
            	if (g_userRole == "ROLE_BUYER_ADMIN"){
	            	orderSheet.visToggle.setVisibility("hidden");
	            	orderSheet.labelVisToggle.setVisibility("hidden");
            		orderSheet.chkboxalldates.setVisibility("hidden");
            	}else{
            		obj.datesViewBuyerID = header.lbluseridinfo.getCaption();
            		orderSheet.cboBuyers.setValue(header.lblusernameinfo.getCaption(), true);
            		orderSheet.chkboxalldates.setVisibility("visible");
            	}
            	orderSheet.chkboxalldates.setValue(true);
            }
            else {
            	if (g_userRole == "ROLE_BUYER"){
            		orderSheet.cboBuyers.setValue(header.lblusernameinfo.getCaption(), true);
            		orderSheet.chkboxalldates.setVisibility("visible");
            		orderSheet.visToggle.setVisibility("hidden");
            		orderSheet.labelVisToggle.setVisibility("hidden");
            	}
            	if (g_userRole == "ROLE_BUYER_ADMIN"){
            		orderSheet.cboBuyers.setValue("All", true);
            	}
            	orderSheet.chkboxalldates.setValue(false);
            }
            
            /*set date panel*/
        	orderSheet.setDatePanel(true);
        	
            linb.Ajax(
    	        "/eON/buyerorder/setParameter.json",
    	        obj,
    	        function (response) {
    	        	validateResponse(response);
    	        	var paneSigmaGridHtml = orderSheet.paneSigmaGrid.getHtml();    
    	        	if (paneSigmaGridHtml == "") {
    	        		orderSheet.categoriesTab.setValue(g_orderSheetParam.categoryId);
    	        		orderSheet.categoriesTab.append(orderSheet.paneSigmaGrid, g_orderSheetParam.categoryId);
    	        		if (g_userRole == "ROLE_BUYER"){
    	        			orderSheet.paneSigmaGrid.setHtml("<iframe id='dataTableIframe' name='dataTableIframe' frameborder=0 marginheight=0 marginwidth=0 scrolling='no' width=100% height=99% src='../jsp/buyer_orderTable.jsp'></iframe>");
    	        		}
    	        		if (g_userRole == "ROLE_BUYER_ADMIN"){
    	        			orderSheet.paneSigmaGrid.setHtml("<iframe id='dataTableIframe' name='dataTableIframe' frameborder=0 marginheight=0 marginwidth=0 scrolling='no' width=100% height=99% src='../jsp/buyerAdmin_orderTable.jsp'></iframe>");
    	        		}
    	            } else {
    	            	// FORDELETION 20120913: Lele - Redmine 880
    	            	//orderSheet.loadDataTable();
    	            	orderSheet.categoriesTab.setUIValue(orderSheet.categoriesTab.getUIValue()+'');    	            	
    	            	orderSheet.categoriesTab.append(orderSheet.paneSigmaGrid, orderSheet.categoriesTab.getUIValue()+'');
    	            	
    	            	// ENHANCEMENT START 20120913: Lele - Redmine 880
    	            	if (g_userRole == "ROLE_BUYER"){
    	        			orderSheet.paneSigmaGrid.setHtml("<iframe id='dataTableIframe' name='dataTableIframe' frameborder=0 marginheight=0 marginwidth=0 scrolling='no' width=100% height=99% src='../jsp/buyer_orderTable.jsp'></iframe>");
    	        		}
    	        		if (g_userRole == "ROLE_BUYER_ADMIN"){
    	        			orderSheet.paneSigmaGrid.setHtml("<iframe id='dataTableIframe' name='dataTableIframe' frameborder=0 marginheight=0 marginwidth=0 scrolling='no' width=100% height=99% src='../jsp/buyerAdmin_orderTable.jsp'></iframe>");
    	        		}
    	            	orderSheet.paneSigmaGrid.refresh();
    	            	// ENHANCEMENT END 20120913:
    	            }  
    	           	//enable edit menu
    	           	buyerMenu.menubar.updateItem('mnuEdit', {disabled:false});
    	           	buyerMenu.menubar.updateItem('subuprefRoundoff', {disabled:false});
    	            buyerMenu.menubar.updateItem('subuprefUnitOrder', {disabled:false});
    	            buyerMenu.menubar.updateItem('utilDownloadExcel', {disabled:false});
    	            buyerMenu.menubar.updateItem('mnuOrdersheet', {disabled:true});
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
           	buyerMenu.menubar.updateItem('roundoff1', {disabled:false});
           	buyerMenu.menubar.updateItem('roundoff2', {disabled:false});
           	buyerMenu.menubar.updateItem('roundoff3', {disabled:false});
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
        _chkboxalldates_onchecked:function (profile, e, value) {
        	if (orderSheet.confirmNavigation()) {
	        	var id;
	        	var items = orderSheet.cboBuyers.getItems();
	    		var thisObj = items[0];
	    		id = thisObj.id;
	    		
	        	g_orderSheetParam.datesViewBuyerID = id;
	        	g_orderSheetParam.checkDBOrder = false;
	        	g_orderSheetParam.allDatesView = e;
	        	orderSheet.loadOrderSheet();
        	}
        	else {
        		var checked = orderSheet.chkboxalldates.getUIValue();
        		orderSheet.chkboxalldates.setUIValue(!checked);
        	}
        },
        _cboBuyers_onchange:function (profile, oldValue, newValue) {
        	if (orderSheet.confirmNavigation()) {
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
        	}
        	
        },
        computeForPrices : function (profitVisibility, profitInfo) {

       	 	var extra_space = "&nbsp;&nbsp;";
        	var text = 
        		linb.Locale[linb.getLang()].app.caption['totalpricewotax'] + extra_space +
				toJapaneseCurrency(profitInfo.priceWithoutTax) + extra_space + extra_space +
				linb.Locale[linb.getLang()].app.caption['totalpricewtax'] + extra_space + 
				toJapaneseCurrency(profitInfo.priceWithTax) + extra_space + extra_space;
        	
        	if (profitVisibility.totalSellingPrice == "1") {
        		text = text + linb.Locale[linb.getLang()].app.caption['totalSellingPrice'] + extra_space +
				toJapaneseCurrency(profitInfo.sellingPrice) + extra_space + extra_space;
        	}
        	
        	if (profitVisibility.totalProfit == "1") {
        		text = text + linb.Locale[linb.getLang()].app.caption['totalProfit'] + extra_space +
				toJapaneseCurrency(profitInfo.profit) + extra_space + extra_space;
        	}
        	
        	if (profitVisibility.totalProfitPercent == "1") {
        		text = text + linb.Locale[linb.getLang()].app.caption['totalProfitPercent'] + extra_space +
				toPercentage(profitInfo.profitPercentage) + extra_space + extra_space;
        	}
        	
        	return text; 
        },
        getCategoryId : function () {
        	return g_orderSheetParam.categoryId;
        },
        _visToggle_onchecked : function (profile, e, src) {
        	orderSheet.toggleVisibilities(e);
        },
        simpleLoadByCategory : function (categoryId) {
        	var url = "/eON/order/setCategoryId.json";
        	var id = Math.random()*url.length;
        	linb.Ajax(
    	        url + "?id=" + id + "&categoryId=" + categoryId,
    	        null,
    	        function (response) {
    	        	validateResponse(response);
    	        	orderSheet.responseCheck(response);
    	        	if(orderSheet.hasNoError(response)) {
    	        		orderSheet.loadDataTable();
    	        	}
    	        }, 
    	        function(msg) {
    	                linb.message("失敗： " + msg);
    	        }, 
    	        null, 
    	        {
    	        	method : 'GET',
    	        	retry : 0
    	        }
    	    ).start();
        },
        loadDataTable : function () {
			var f = document.getElementById('dataTableIframe');
			f.contentWindow.location.reload();
		},
		addRow : function () {
			var f = document.getElementById('dataTableIframe');
			f.contentWindow.addRow();
		},
		toggleVisibilities : function (checked) {
			var f = document.getElementById('dataTableIframe');
			if (f == null) {
				alert(checked);
				return;
			}
			f.contentWindow.refreshing();
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
			if (g_userRole == "ROLE_BUYER"){
				if (g_orderSheetParam.endDate != null && g_orderSheetParam.endDate != "" 
					&& g_orderSheetParam.startDate != g_orderSheetParam.endDate)
					g_orderSheetParam.allDatesView = true;
				else g_orderSheetParam.allDatesView = false;
			}else{
				g_orderSheetParam.allDatesView = false;
			}
			g_orderSheetParam.checkDBOrder = true;
			orderSheet.loadOrderSheet();
			orderSheet.disableActionButtons();
			orderSheet.paneSelectedDates.reBoxing().show();
			orderSheet.paneOptionbuttons.reBoxing().show();
			//Start - Chrome Fix - Buttons
			orderSheet.blockOptionButtons.setVisibility('visible');
			orderSheet.blockOptionButtons.reBoxing().show();
			//End - Chrome Fix - Buttons
			orderSheet.panelMainSheet.reBoxing().show();
			orderSheet.cboBuyers.setItems(g_orderSheetParam.buyerCombo, true);
			orderSheet.cboSellers.setItems(g_orderSheetParam.sellerCombo, true);
			if (g_userRole == "ROLE_BUYER_ADMIN"){
				orderSheet
				.btnAddSKU.setVisibility("visible");
	        	orderSheet.setEnableBAButton();
			}
        },

        _btnaddsku_onclick:function (p,item,src) {
        	orderSheet.addRow();
        },
        roundOffPriceWTax: function(price){
        	var priceRounded = 0 ;
    		if (buyerMenu.lblRoundOffStatus.getCaption() == "lower"){
    			priceRounded = Math.floor(price);
    		} else if (buyerMenu.lblRoundOffStatus.getCaption() == "higher"){
    			priceRounded = Math.ceil(price);
    		} else {
    			priceRounded = Math.round(price);
    		}
    		return priceRounded;
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
	        				// reload table
				        	g_orderSheetParam.checkDBOrder = false;
				        	orderSheet.loadOrderSheet();
	    					}
	    				}});
	    				var items = orderSheet.cboSellers.getItems();
	    				orderSheet._excom1.cbiSellername.setItems(items, true);
	    				orderSheet._excom1.cbiSellername.setValue(items[0].caption, true);
			        	var g_sellerId = orderSheet._excom1.cbiSellername.getUIValue();
	    				this.show(null, orderSheet.paneSubCom);
	    			}
	        	);
        	}
        },
        _btnSave_onclick : function (p,item,src) {
        	this.btnSave.setDisabled(true);
        	orderSheet.saveOrder();
        },
		saveOrder : function () {
			var f = document.getElementById('dataTableIframe');
			f.contentWindow.onSave();
		},
		//ENHANCEMENT START 20120608: Rhoda Redmine 68
		alertConcurrencyMsg : function(concurrencyResp, isSellerFinalized){
			var msg = "";
			if (isSellerFinalized == 'true'){
				var alertMsg = linb.Locale[linb.getLang()].app.caption['ALERT_CONCURRENCY_UNLOCKED_ORDER'];
				//alert(alertMsg);
				msg = alertMsg;
			}else{
				var alertMsg = linb.Locale[linb.getLang()].app.caption['ALERT_CONCURRENCY'];
				//alert(alertMsg + concurrencyResp.concurrencyMsg);
				msg = alertMsg+concurrencyResp.concurrencyMsg;
			}
			//alert(alertMsg + concurrencyMsg);
			if(concurrencyResp.action=='save'){
				msg = concurrencyResp.concurrencyMsg;
			}
			linb.ComFactory.newCom(
        			'App.alertPopUp', 
        			function(){
        				this.alertDialog.showModal();
        				this.loadAlertDisplay(this,msg);
        			}
            	);
		},
		//ENHANCEMENT END 20120608: Rhoda Redmine 68
        _btnLock_onclick: function (p,item,src) {
        	if (orderSheet.confirmNavigation()) {
	        	orderSheet.lockOrder();
        	}
        },
		lockOrder: function () {
			var f = document.getElementById('dataTableIframe');
			f.contentWindow.onLock();
		},
        _btnUnlock_onclick: function (p,item,src) {
        	if (orderSheet.confirmNavigation()) {
	        	orderSheet.unLockOrder();
        	}
        },
        _btnDownloadExcel_onclick : function (p,item,src) {
        	var ns=this;
    		linb.ComFactory.newCom(
    			'App.downloadExcel', 
    			function(){
    				orderSheet._excom1 = this;
    				this.setEvents({'onDownloadExcel' : function(paramObj) { 
    					buyerMenu.downloadExcel(paramObj);
    				}});
    				ns.$cached=this;
    				this.show(null, orderSheet.paneSubCom);
    			});    		
        }, 
		unLockOrder : function () {
			var f = document.getElementById('dataTableIframe');
			f.contentWindow.onUnlock();
		},
		_btnPublish_onclick: function (p,item,src) {
        	if (orderSheet.confirmNavigation()) {
				var f = document.getElementById('dataTableIframe');
				f.contentWindow.onPublish();
        	}
		},
        downloadExcel : function (paramObj) {
        	var f = document.getElementById('dataTableIframe');
			f.contentWindow.submitTempForm(_.serialize(paramObj));
        },setEnableBAButton : function(){
    		if(enablePublish =="true"){
        		this.btnPublish.setVisibility("visible");
	        	this.btnPublish.setDisabled(true);
        	}else{
        		this.btnPublish.setVisibility("hidden");
        	}
        },
        disableActionButtons : function () {
        	this.setVisibilityForActionButtons();
        	if (g_userRole=="ROLE_BUYER_ADMIN"){
	        	this.btnAddSKU.setVisibility("visible");
	        	this.btnAddSKU.setDisabled(true);
        	}
        	this.btnSave.setDisabled(true);
        	this.btnLock.setDisabled(true);
        	this.btnUnlock.setDisabled(true);
        	//Download Excel Button Always Enabled
          	this.btnDownloadExcel.setDisabled(false);
        },
        setVisibilityForActionButtons : function () {
        	this.btnSave.setVisibility('visible');
        	this.btnLock.setVisibility('hidden');
        	this.btnUnlock.setVisibility('visible');
        	this.btnDownloadExcel.setVisibility('visible');
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
        refreshment : function () {
        	g_orderSheetParam.checkDBOrder = true;
        	orderSheet.loadOrderSheet();
        }

     }
});