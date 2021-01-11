/*
 	Copyright Freshremix Asia Software Corporation
	date		name		version	changes
	==============================================
	20120913	lele		chrome	Redmine 880 - Chrome compatibility
 * */

var g_receivedSheetParam; // should only be changed in 'onSearch' fire event

Class('App.receivedsheet', 'linb.Com',{
	autoDestroy:true,
    Instance:{
        iniComponents:function(){
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
		    append((new linb.UI.Pane)
                .host(host,"paneMain")
                //.setDock("fill")
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
            
            host.paneSelectedDates.append((new linb.UI.CheckBox)
                .host(host,"chkboxSelectAllDates")
                .setDirtyMark(false)
                .setLeft(105)
                .setTop(5)
                .setWidth(110)
                .setCaption("$app.caption.alldates")
                .onChecked("_chkboxalldates_onchecked")
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
        
            host.blockUpperLayer.append((new linb.UI.Button)
                .host(host,"btnSearch")
                .setLeft(10)
                .setTop(5)
                .setWidth(80)
                .setHeight(25)
//                .setCaption("$app.caption.search")
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
                .setWidth(50)
                .setShadow(true)
                .setCaption("$app.caption.addsku")
                .setImage("../../common/img/tool_add.gif")
                .onClick("_btnaddsku_onclick")
                .setDisabled(true)
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
                .setDisabled(true)
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
                .setVisibility("hidden")
            );
            
            host.blockOptionButtons.append((new linb.UI.Button)
                .host(host,"btnDownloadExcel")
                .setLeft(350)
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
        	receivedSheet = page;
        	buyerMenu.menubar.setItems(getMenuItems());
        	if(g_clientWidth > receivedSheet.paneMain.getWidth()){
        		receivedSheet.paneMain.setWidth(g_clientWidth);
        	}			
			if(g_clientHeight > receivedSheet.paneMain.getHeight() + 60){
				receivedSheet.paneMain.setHeight(g_clientHeight - 60);
        	}
			if(unfinalizeReceived=="1"){
				receivedSheet.btnDownloadExcel.setLeft(450);
				receivedSheet.btnUnlock.setVisibility("visible");
			}
        },
        _onready: function() {
        	receivedSheet = this;
        	disableBuyerPreferenceButtons("0");
        }, 
        _btnsearch_onclick:function (profile, e, src, value) {
        	if (receivedSheet.confirmNavigation()) {
	        	var ns=this;
	        	linb.ComFactory.newCom(
	        			'App.filter', 
	        			function(){
	        				receivedSheet._excom1 = this;
	        				if (g_userRole == "ROLE_BUYER"){
	        					receivedSheet._excom1.hiddenSheetTypeId.setCaption(10004);
	                    	}
	                    	if (g_userRole == "ROLE_BUYER_ADMIN"){
	                    		receivedSheet._excom1.hiddenSheetTypeId.setCaption(10012);
	                    	}
	        				receivedSheet._excom1.hiddenDealingPatternId.setCaption(1);
	        				this.setEvents({'onSearch' : function(result) { 
	        					//alert('here'+_.serialize(result));
	        					receivedSheet.onSearchEvent(result);
	        				}});
	        				ns.$cached=this;
	        				this.show(null, receivedSheet.paneSubCom);
	        				//this.show(linb([document.body]));
	        				//orderSheet.show(orderSheet._excom1.show(null, orderSheet.paneSubCom),true,93,100);
	        			}
		        	);
        	}
        },
        loadOrderSheet : function () {
        	var obj = new Object();
        	obj.startDate = g_orderSheetParam.startDate;
            obj.endDate = g_orderSheetParam.endDate;
        	obj.selectedSellerCompany = g_orderSheetParam.selectedSellerCompany;
            obj.selectedSellerID = g_orderSheetParam.selectedSellerID;
            obj.selectedBuyerCompany = g_orderSheetParam.selectedBuyerCompany;
            obj.selectedBuyerID = g_orderSheetParam.selectedBuyerID;
            obj.selectedDate = g_orderSheetParam.selectedDate;
            obj.categoryId = g_orderSheetParam.categoryId;
            obj.sheetTypeId = g_orderSheetParam.sheetTypeId;
            obj.allDatesView = g_orderSheetParam.allDatesView;
            obj.checkDBOrder = g_orderSheetParam.checkDBOrder;
            
            receivedSheet.linkPrevious.setVisibility('hidden');
            receivedSheet.linkNext.setVisibility('hidden');
            receivedSheet.categoriesTab.setUIValue(g_orderSheetParam.categoryId);
            receivedSheet.chkboxSelectAllDates.setVisibility("hidden");
            
            receivedSheet.lblRoundOffSheet.setCaption("loadReceived");
        	
            obj.datesViewBuyerID = '0';
            if (g_orderSheetParam.allDatesView) {
            	obj.datesViewBuyerID = g_orderSheetParam.datesViewBuyerID;
            	receivedSheet.chkboxSelectAllDates.setValue(true);
            
            }else {
            	receivedSheet.chkboxSelectAllDates.setUIValue(false);
            	if (g_userRole == "ROLE_BUYER"){
            		receivedSheet.cboBuyers.setValue(header.lblusernameinfo.getCaption(), true);
            	}
            	if (g_userRole == "ROLE_BUYER_ADMIN"){
            		receivedSheet.cboBuyers.setVisibility("visible");
            		receivedSheet.cboBuyers.setValue("All", true);
            	}
            }
            
            /*set date panel*/
        	receivedSheet.setDatePanel(true);
        	
            linb.Ajax(
    	        "/eON/buyerreceived/setParameter.json",
    	        obj,
    	        function (response) {
    	        	validateResponse(response);
    	        	//alert(response);
    	        	receivedSheet.responseCheck(response);
    	        	validateResponse(response);
    	        	var paneSigmaGridHtml = receivedSheet.paneSigmaGrid.getHtml();                	
    	        	if (paneSigmaGridHtml == "") {
    	        		receivedSheet.categoriesTab.setValue(g_orderSheetParam.categoryId);
    	        		receivedSheet.categoriesTab.append(receivedSheet.paneSigmaGrid, g_orderSheetParam.categoryId);
    	        		receivedSheet.paneSigmaGrid.setHtml("<iframe id='dataTableIframe' name='dataTableIframe' frameborder=0 marginheight=0 marginwidth=0 scrolling='no' width=100% height=99% src='../jsp/buyer_receivedTable.jsp'></iframe>");
    	            } else {
    	            	receivedSheet.categoriesTab.setUIValue(receivedSheet.categoriesTab.getUIValue()+'');
    	            	receivedSheet.categoriesTab.append(receivedSheet.paneSigmaGrid, receivedSheet.categoriesTab.getUIValue()+'');
    	            	// ENHANCEMENT START 20120913: Lele - Redmine 880
    	            	//receivedSheet.loadDataTable();
    	            	receivedSheet.paneSigmaGrid.setHtml("<iframe id='dataTableIframe' name='dataTableIframe' frameborder=0 marginheight=0 marginwidth=0 scrolling='no' width=100% height=99% src='../jsp/buyer_receivedTable.jsp'></iframe>");
    	            	receivedSheet.paneSigmaGrid.refresh();
    	            	// ENHANCEMENT END 20120913:
    	            }
    	           	//enable edit menu
    	           	buyerMenu.menubar.updateItem('mnuEdit', {disabled:false});   	        
    	           	buyerMenu.menubar.updateItem('subuprefRoundoff', {disabled:false});
//    	            buyerMenu.menubar.updateItem('subuprefShowcols', {disabled:false});
    	            buyerMenu.menubar.updateItem('subuprefUnitOrder', {disabled:false});
    	            buyerMenu.menubar.updateItem('utilDownloadExcel', {disabled:false});
    	            buyerMenu.menubar.updateItem('mnuReceivedsheet', {disabled:true});
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
        	this.categoriesTab.append(this.paneSigmaGrid, item.id);
        	g_orderSheetParam.categoryId = item.id;
        	g_orderSheetParam.checkDBOrder = false;
        	receivedSheet.loadOrderSheet();
        },
        _chkboxalldates_onchecked:function (profile, e, value) {
        	if (receivedSheet.confirmNavigation()) {
	        	var id;
	        	var items = receivedSheet.cboBuyers.getItems();
	    		var thisObj = items[0];
	    		id = thisObj.id;
	    		
	        	g_orderSheetParam.datesViewBuyerID = id;
	        	g_orderSheetParam.checkDBOrder = false;
	        	g_orderSheetParam.allDatesView = e;
	        	
	        	receivedSheet.loadOrderSheet();
        	}
        	else {
        		var checked = receivedSheet.chkboxSelectAllDates.getUIValue();
        		receivedSheet.chkboxSelectAllDates.setUIValue(!checked);
        	}
        },
        _cboBuyers_onchange:function (profile, oldValue, newValue) {
        	if (receivedSheet.confirmNavigation()) {
	        	var id;
	        	var items = receivedSheet.cboBuyers.getItems();
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
	        	
	        	receivedSheet.loadOrderSheet();
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
        loadDataTable : function () {
			var f = document.getElementById('dataTableIframe');
			f.contentWindow.location.reload();
		},
		addRow : function () {
			var f = document.getElementById('dataTableIframe');
			f.contentWindow.addRow();
		},
        _linkprevious_onclick:function (profile, e) {
			if (receivedSheet.confirmNavigation()) {
	        	var newDates = getNewStartEndDate(g_orderSheetParam, 'previous');
	        	g_orderSheetParam.startDate = newDates.startDate;
	        	g_orderSheetParam.endDate = newDates.endDate;
	        	g_orderSheetParam.selectedDate = newDates.selectedDate;
	        	g_orderSheetParam.checkDBOrder = true;
	            
	        	receivedSheet.loadOrderSheet();
			}
        },
        _linknext_onclick:function (profile, e) {
        	if (receivedSheet.confirmNavigation()) {
	        	var newDates = getNewStartEndDate(g_orderSheetParam, 'next');
	        	g_orderSheetParam.startDate = newDates.startDate;
	        	g_orderSheetParam.endDate = newDates.endDate;
	        	g_orderSheetParam.selectedDate = newDates.selectedDate;
	        	g_orderSheetParam.checkDBOrder = true;
	            
	        	receivedSheet.loadOrderSheet();
        	}
        },
        setDatePanel : function (disabled) {
        	receivedSheet.datePanel.setHtml(setDatePanel(g_orderSheetParam, disabled, receivedSheet), true);
        },
        onSearchEvent : function (result) {
        	
        	//set categorytabs
        	receivedSheet.paneSigmaGrid.setHtml("");
        	receivedSheet.categoriesTab.setItems(categoryTabs);
        	
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
			/**/
			receivedSheet.loadOrderSheet();
			receivedSheet.disableActionButtons();
			/**/
			receivedSheet.paneSelectedDates.reBoxing().show();
			receivedSheet.paneOptionbuttons.reBoxing().show();
			//Start - Chrome Fix - Buttons
			receivedSheet.blockOptionButtons.setVisibility('visible');
			receivedSheet.blockOptionButtons.reBoxing().show();
			//End - Chrome Fix - Buttons
			receivedSheet.panelMainSheet.reBoxing().show();
			receivedSheet.cboBuyers.setItems(g_orderSheetParam.buyerCombo, true);
        },
        downloadExcel : function (paramObj) {
        	var f = document.getElementById('dataTableIframe');
			f.contentWindow.submitTempForm(_.serialize(paramObj));
        },
        enableAfterSigmaLoad: function () {
        	//receivedSheet.btnDownloadExcel.setDisabled(false);
        	receivedSheet.cboBuyers.setDisabled(false);
			receivedSheet.linkPrevious.setVisibility('visible');
			receivedSheet.linkNext.setVisibility('visible');
			receivedSheet.setDatePanel(false);
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
        disableActionButtons : function () {
        	this.setVisibilityForActionButtons();
        	this.btnAddSKU.setDisabled(true);
        	this.btnAddSKUGroup.setDisabled(true);
        	this.btnSave.setDisabled(true);
        	this.btnLock.setDisabled(true);
        	this.btnUnlock.setDisabled(true);
        	//Download Excel Button Always Enabled
          	this.btnDownloadExcel.setDisabled(false);
        },
        setVisibilityForActionButtons : function () {
        	//this.btnAddSKU.setVisibility('visible');
        	//this.btnAddSKUGroup.setVisibility('visible');
        	this.btnSave.setVisibility('visible');
        	this.btnLock.setVisibility('hidden');
        	//this.btnUnlock.setVisibility('visible');
        	this.btnDownloadExcel.setVisibility('visible');
        },
        _btnSave_onclick : function (p,item,src) {
        	this.btnSave.setDisabled(true);
        	//receivedSheet.disableActionButtons();
        	receivedSheet.saveOrder();
        },
		saveOrder : function () {
			var f = document.getElementById('dataTableIframe');
			f.contentWindow.onSave();
		},
     	alertConcurrencyMsg:function(concurrencyMsg){
			var alertMsg = linb.Locale[linb.getLang()].app.caption['ALERT_CONCURRENCY_FINALIZE_RECEIVED'];
			alert(alertMsg);
		},
        _btnLock_onclick: function (p,item,src) {
			if (receivedSheet.confirmNavigation()) {
				//receivedSheet.disableActionButtons();
				receivedSheet.lockOrder();
			}
        },
		lockOrder: function () {
			var f = document.getElementById('dataTableIframe');
			f.contentWindow.onLock();
		},
        _btnUnlock_onclick: function (p,item,src) {
			if (receivedSheet.confirmNavigation()) {
				//receivedSheet.disableActionButtons();
				receivedSheet.unLockOrder();
			}
        },
        _btnDownloadExcel_onclick : function (p,item,src) {
        	var ns=this;
    		linb.ComFactory.newCom(
    			'App.downloadExcel', 
    			function(){
    				receivedSheet._excom1 = this;
    				this.setEvents({'onDownloadExcel' : function(paramObj) { 
    					buyerMenu.downloadExcel(paramObj);
    				}});
    				ns.$cached=this;
    				this.show(null, receivedSheet.paneSubCom);
    			});    		
        },
		unLockOrder : function () {
			var f = document.getElementById('dataTableIframe');
			f.contentWindow.onUnlock();
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
        	if (receivedSheet.checkForDirtyTable()) {
        		alert(linb.Locale[linb.getLang()].app.caption['alertUnsavedChanges']);
        		return false;
        	}
        	return true;
        },
        _categoriestab_beforeuivalueset : function (profile, oldValue, newValue) {
        	return receivedSheet.confirmNavigation();
        },
        _cbobuyers_beforeuivalueset : function (profile, oldValue, newValue) {
        	return receivedSheet.confirmNavigation();
        },
        refreshment : function () {
        	g_orderSheetParam.checkDBOrder = true;
        	receivedSheet.loadOrderSheet();
        }
     }
});