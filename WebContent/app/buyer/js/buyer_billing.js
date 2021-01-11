var g_orderSheetParam; // should only be changed in 'onSearch' fire event

Class('App.buyerBilling', 'linb.Com',{
    Instance:{
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
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
                .host(host,"btnDownloadExcel")
                .setLeft(450)
                .setTop(4)
                .setWidth(70)
                .setShadow(true)
                .setCaption("$app.caption.downloadexcel")
                .setImage("../../common/img/excel.gif")
                .onClick("_btnDownloadExcel_onclick")
            );
	        
	        host.blockOptionButtons.append((new linb.UI.Label)
                .host(host,"lblGTPriceWOTax")
                .setRight(340)
                .setTop(5)
                .setWidth(110)
                .setCaption("$app.caption.gtpricewotax")
            );
            
            host.blockOptionButtons.append((new linb.UI.Label)
                .host(host,"gtPriceWOTax")
                .setTop(5)
                .setWidth(110)
                .setRight(230)
                .setBorder(false)
                .setCustomStyle({"KEY":"background-color:white;color:gray;"})
            );
                
            host.blockOptionButtons.append((new linb.UI.Label)
                .host(host,"lblGTPriceWTax")
                .setRight(120)
                .setTop(5)
                .setWidth(110)
                .setCaption("$app.caption.gtpricewtax")
            );
            
            host.blockOptionButtons.append((new linb.UI.Label)
                .host(host,"gtPriceWTax")
                .setRight(10)
                .setTop(5)
                .setWidth(110)
                .setBorder(false)
                .setCustomStyle({"KEY":"background-color:white;color:gray;"})
            );
	           
	        host.blockOptionButtons.append((new linb.UI.Input)
                .host(host,"hdnGtPriceWTax")
                .setLeft(900)
                .setTop(2)
                .setWidth(80)
                .setVisibility("hidden")
	        );
               
	        host.blockMain.append((new linb.UI.Panel)
	        	.host(host,"panelMainSheet")
	        	.setVisibility("hidden")
	        	.setCaption("")
	        );
	            
	        host.panelMainSheet.append((new linb.UI.Tabs)
	            .host(host,"categoriesTab")
	            .setZIndex(0)
	            .onItemSelected("_categoriestab_onitemselected")
	        );
	        
	        host.panelMainSheet.append((new linb.UI.Label)
                .host(host,"labelTotalPriceWithOutTax")
                .setRight(350)
                .setTop(5)
                .setWidth(120)
                .setCaption("$app.caption.totalpricewotax")
            );
            
            host.panelMainSheet.append((new linb.UI.Label)
                .host(host,"totpricewotax")
                .setTop(2)
                .setWidth(110)
                .setRight(240)
                .setBorder(false)
                .setCustomStyle({"KEY":"background-color:white;color:gray;"})
            );
            
            host.panelMainSheet.append((new linb.UI.Label)
                .host(host,"labelTotalPriceWithTax")
                .setRight(120)
                .setTop(5)
                .setWidth(120)
                .setCaption("$app.caption.totalpricewtax")
            );
            
            host.panelMainSheet.append((new linb.UI.Label)
                .host(host,"totpricewtax")
                .setRight(10)
                .setTop(2)
                .setWidth(110)
                .setZIndex(1002)
                .setBorder(false)
                .setCustomStyle({"KEY":"background-color:white;color:gray;"})
            );
	        
	        host.panelMainSheet.append((new linb.UI.Input)
	            .host(host,"hdntotpricewtax")
	            .setLeft(650)
	            .setTop(2)
	            .setWidth(100)
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
        events:{"onReady":"_onready", "onRender":"_onRender"}, 
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, threadid){
        }, 
        _onRender:function(page, threadid){
        	billingSheet = page;
        	billingSheet.setEnableBAButton();
        	buyerMenu.menubar.setItems(getMenuItems());
        	if(g_clientWidth > billingSheet.paneMain.getWidth()){
        		billingSheet.paneMain.setWidth(g_clientWidth);
        	}			
			if(g_clientHeight > billingSheet.paneMain.getHeight() + 60){
				billingSheet.paneMain.setHeight(g_clientHeight - 60);
        	}
        },
        _onready: function() {
        	billingSheet = this;
        	buyerMenu.menubar.setItems(getMenuItems());
        	disableBuyerPreferenceButtons("0");
        }, 
        _categoriestab_onitemselected:function (profile, item, src) {
            // reload sigmaTable
        	billingSheet.categoriesTab.append(billingSheet.paneSigmaGrid, item.id);
        	g_orderSheetParam.categoryId = item.id;
        	g_orderSheetParam.checkDBOrder = false;
        	billingSheet.loadOrderSheet();
        }, 
        
        _btnDownloadExcel_onclick : function (p,item,src) {
        	var ns=this;
    		linb.ComFactory.newCom(
    			'App.downloadExcel', 
    			function(){
    				billingSheet._excom1 = this;
    				this.setEvents({'onDownloadExcel' : function(paramObj) { 
    					buyerMenu.downloadExcel(paramObj);
    				}});
    				ns.$cached=this;
    				this.show(null, billingSheet.paneSubCom);
    			});    		
        }, 
        
        _btnsearch_onclick:function (profile, e, src, value) {
        	var ns=this;
        	linb.ComFactory.newCom(
	    			'App.filter', 
	    			function(){
	    				billingSheet._excom1 = this;
	    				if (g_userRole == "ROLE_BUYER"){
	    					billingSheet._excom1.hiddenSheetTypeId.setCaption(10006);
	                	}
	                	if (g_userRole == "ROLE_BUYER_ADMIN"){
	                		billingSheet._excom1.hiddenSheetTypeId.setCaption(10013);
	                	}
	    				billingSheet._excom1.hiddenDealingPatternId.setCaption(1);
	    				this.setEvents({'onSearch' : function(result) { 
	    					billingSheet.onSearchEvent(result);
	    				}});
	    				ns.$cached=this;
	    				this.show(null, billingSheet.paneSubCom);
	    			}
	        	);
        },
        onSearchEvent : function (result) {
        	//set categorytabs
        	billingSheet.paneSigmaGrid.setHtml("");
        	billingSheet.categoriesTab.setItems(categoryTabs);
        	
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

			billingSheet.loadOrderSheet();
        	
			billingSheet.paneSelectedDates.reBoxing().show();
			billingSheet.paneOptionbuttons.reBoxing().show();
			billingSheet.panelMainSheet.reBoxing().show();
			billingSheet.cboBuyers.setItems(g_orderSheetParam.buyerCombo, true);
			billingSheet.totpricewtax.setCaption("");
			billingSheet.totpricewotax.setCaption("");
			billingSheet.gtPriceWTax.setCaption("");
			billingSheet.gtPriceWOTax.setCaption("");
		},
        _cbobuyers_onchange:function (profile, oldValue, newValue) {
        	//alert(oldValue);
        	//alert(newValue);
        	//alert(_.serialize(profile));
        	var id;
        	var items = billingSheet.cboBuyers.getItems();
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
        	
        	billingSheet.loadOrderSheet();
        	
        },
        loadOrderSheet : function () {
        	var obj = new Object();
        	//billingSheet.paneSigmaGrid.setHtml("");
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
            
            billingSheet.lblRoundOffSheet.setCaption("loadBuyerBilling");
            
            /* reset the visibility toggle */
            //billingSheet.btnDownloadExcel.setDisabled(true);
//            billingSheet.visToggle.setUIValue(false);
//            billingSheet.labelVisToggle.setVisibility("visible");
//            billingSheet.visToggle.setVisibility("visible");
//            billingSheet.visToggle.setDisabled(true);
            billingSheet.linkPrevious.setVisibility('hidden');
            billingSheet.linkNext.setVisibility('hidden');
            billingSheet.categoriesTab.setUIValue(g_orderSheetParam.categoryId);
            billingSheet.chkboxSelectAllDates.setVisibility("hidden");
            
            /*obj.datesViewBuyerID = '0';
            if (g_orderSheetParam.allDatesView) {
            	obj.datesViewBuyerID = g_orderSheetParam.datesViewBuyerID;
            	billingSheet.chkboxSelectAllDates.setValue(true);
//            	billingSheet.visToggle.setVisibility("hidden");
//            	billingSheet.labelVisToggle.setVisibility("hidden");
            } else {
            	billingSheet.chkboxSelectAllDates.setUIValue(false);
            	if (g_userRole == "ROLE_BUYER"){
            		billingSheet.cboBuyers.setValue(header.lblusernameinfo.getCaption(), true);
            	}
            	if (g_userRole == "ROLE_BUYER_ADMIN"){
            		billingSheet.cboBuyers.setValue("All", true);
            	}
            }*/
            
            obj.datesViewBuyerID = '0';
            if (g_orderSheetParam.allDatesView) {
            	obj.datesViewBuyerID = g_orderSheetParam.datesViewBuyerID;
            	if (g_userRole == "ROLE_BUYER_ADMIN"){
            		billingSheet.chkboxSelectAllDates.setVisibility("hidden");
            		billingSheet.cboBuyers.setVisibility("visible");
            	}else{
            		obj.datesViewBuyerID = header.lbluseridinfo.getCaption();
            		billingSheet.cboBuyers.setValue(header.lblusernameinfo.getCaption(), true);
            		billingSheet.chkboxSelectAllDates.setVisibility("visible");
            	}
            	billingSheet.chkboxSelectAllDates.setValue(true);
            }
            else {
            	if (g_userRole == "ROLE_BUYER"){
            		billingSheet.cboBuyers.setValue(header.lblusernameinfo.getCaption(), true);
            		billingSheet.chkboxSelectAllDates.setVisibility("visible");
            	}
            	if (g_userRole == "ROLE_BUYER_ADMIN"){
            		billingSheet.cboBuyers.setVisibility("visible");
            		billingSheet.cboBuyers.setValue("All", true);
            	}
            	billingSheet.chkboxSelectAllDates.setValue(false);
            }            

            /*set date panel*/
            billingSheet.setDatePanel(true);
            
            linb.Ajax(
    	        "/eON/buyerbilling/setParameter.json",
    	        obj,
    	        function (response) {
    	        	//billingSheet.responseCheck(response);
    	        	validateResponse(response);
    	        	var paneSigmaGridHtml = billingSheet.paneSigmaGrid.getHtml();                	
    	        	if (paneSigmaGridHtml == "") {
    	        		billingSheet.categoriesTab.setValue(g_orderSheetParam.categoryId);
    	        		billingSheet.categoriesTab.append(billingSheet.paneSigmaGrid, g_orderSheetParam.categoryId);
    	        		billingSheet.paneSigmaGrid.setHtml("<iframe id='dataTableIframe' name='dataTableIframe' frameborder=0 marginheight=0 marginwidth=0 scrolling='no' width=100% height=99% src='../jsp/buyer_billingTable.jsp'></iframe>");
    	            } else {
    	            	billingSheet.categoriesTab.setUIValue(billingSheet.categoriesTab.getUIValue()+'');
    	            	billingSheet.categoriesTab.append(billingSheet.paneSigmaGrid, billingSheet.categoriesTab.getUIValue()+'');
    	            	billingSheet.loadDataTable();
    	            }
    	        	billingSheet.computeGTPrice();
    	           	buyerMenu.menubar.updateItem('mnuEdit', {disabled:false});
    	            buyerMenu.menubar.updateItem('subuprefRoundoff', {disabled:false});
    	            buyerMenu.menubar.updateItem('roundoff1', {disabled:false});
    	            buyerMenu.menubar.updateItem('roundoff2', {disabled:false});
    	            buyerMenu.menubar.updateItem('roundoff3', {disabled:false});
//    	            buyerMenu.menubar.updateItem('subuprefShowcols', {disabled:false});
    	            buyerMenu.menubar.updateItem('subuprefUnitOrder', {disabled:false});
    	            buyerMenu.menubar.updateItem('utilPMF', {disabled:true});
    	            buyerMenu.menubar.updateItem('utilDownloadExcel', {disabled:false});
    	            buyerMenu.menubar.updateItem('mnuBillingsheet', {disabled:true});
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
        computeForTotalPrices : function (totalPriceWOTax, totalPriceWTax) {
        	//var totalPriceWTax = totalPriceWOTax * 1.05;
        	var price = billingSheet.roundOffPriceWTax(totalPriceWTax);
        	totalPriceWOTax = billingSheet.roundOffPriceWTax(totalPriceWOTax);
        	billingSheet.totpricewtax.setCaption(toJapaneseCurrency(price));
        	billingSheet.totpricewotax.setCaption(toJapaneseCurrency(totalPriceWOTax));
        	billingSheet.hdntotpricewtax.setValue(totalPriceWTax); // default computation

//        	var url = "/eON/billing/computeTotalPrice.json";
//        	var id = Math.random()*url.length;
//        	linb.Ajax(
//    	        url + "?id="+id,
//    	        null,
//    	        function (response) {
//    	        	billingSheet.responseCheck(response);
//    	        	if(billingSheet.hasNoError(response)) {
//    	        		var obj = _.unserialize(response);
//    	        		var price = billingSheet.roundOffPriceWTax(obj.totalPriceWTax);
//    	        		billingSheet.totpricewtax.setValue(price);
//    	        		billingSheet.totpricewotax.setValue(obj.totalPriceWOTax);
//    	        		billingSheet.hdntotpricewtax.setValue(obj.totalPriceWTax);
//    	        	}
//    	        }, 
//    	        function(msg) {
//    	        	linb.message("失敗： " + msg);
//    	        }, 
//    	        null, 
//    	        {
//    	        	method : 'GET',
//    	        	retry : 0
//    	        }
//    	    ).start();
        },
        getCategoryId : function () {
        	return g_orderSheetParam.categoryId;
        },
        computeGTPrice : function () {
        	var url = "/eON/buyerbilling/computeGTPrice.json";
        	var id = Math.random()*url.length;
        	linb.Ajax(
    	        url + "?id="+id,
    	        null,
    	        function (response) {
    	        	validateResponse(response);
    	        	billingSheet.responseCheck(response);
    	        	if(billingSheet.hasNoError(response)) {
    	        		var obj = _.unserialize(response);
    	        		var price = billingSheet.roundOffPriceWTax(obj.gtPriceWTax);
    	        		var priceWOTax = billingSheet.roundOffPriceWTax(obj.gtPriceWOTax);
    	        		if (obj.gtPriceWOTax != null) {
    	        			billingSheet.gtPriceWOTax.setCaption(toJapaneseCurrency(priceWOTax));
    	        			billingSheet.gtPriceWTax.setCaption(toJapaneseCurrency(price));
    	        			billingSheet.hdnGtPriceWTax.setValue(obj.gtPriceWTax);
    	        		} else {
    	        			billingSheet.gtPriceWOTax.setCaption(toJapaneseCurrency(0));
    	        			billingSheet.gtPriceWTax.setCaption(toJapaneseCurrency(0));
    	        		}
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
        setDatePanel : function (disabled) {
        	billingSheet.datePanel.setHtml(setDatePanel(g_orderSheetParam, disabled, billingSheet), true);
        },
        loadDataTable : function () {
			var f = document.getElementById('dataTableIframe');
			f.contentWindow.location.reload();
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
        }, setEnableBAButton : function(){
        	userPrefEnableBAPublishOrder.disabled = true;
        	
        },
        _linkprevious_onclick:function (profile, e) {
        	var newDates = getNewStartEndDate(g_orderSheetParam, 'previous');
        	g_orderSheetParam.startDate = newDates.startDate;
        	g_orderSheetParam.endDate = newDates.endDate;
        	g_orderSheetParam.selectedDate = newDates.selectedDate;
        	g_orderSheetParam.checkDBOrder = true;
            
        	billingSheet.loadOrderSheet();
        },
        _linknext_onclick:function (profile, e) {
        	var newDates = getNewStartEndDate(g_orderSheetParam, 'next');
        	g_orderSheetParam.startDate = newDates.startDate;
        	g_orderSheetParam.endDate = newDates.endDate;
        	g_orderSheetParam.selectedDate = newDates.selectedDate;
        	g_orderSheetParam.checkDBOrder = true;
            
        	billingSheet.loadOrderSheet();
        },
        refreshment : function () {
        	g_orderSheetParam.checkDBOrder = true;
        	billingSheet.loadOrderSheet();
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
        	if (billingSheet.checkForDirtyTable()) {
        		var ans = confirm(linb.Locale[linb.getLang()].app.caption['confirmNavigation']);
        		if (ans) return true;
        		else return false;
        	}
        	return true;
        },
        _chkboxalldates_onchecked:function (profile, e, value) {
        	if (billingSheet.confirmNavigation()) {
	        	var id;
	        	var items = billingSheet.cboBuyers.getItems();
	    		var thisObj = items[0];
	    		id = thisObj.id;
	    		
	        	g_orderSheetParam.datesViewBuyerID = id;
	        	g_orderSheetParam.checkDBOrder = false;
	        	g_orderSheetParam.allDatesView = e;
	        	
	        	billingSheet.loadOrderSheet();
        	}
        	else {
        		var checked = billingSheet.chkboxSelectAllDates.getUIValue();
        		billingSheet.chkboxSelectAllDates.setUIValue(!checked);
        	}
        },
        _cboBuyers_onchange:function (profile, oldValue, newValue) {
        	if (billingSheet.confirmNavigation()) {
	        	var id;
	        	var items = billingSheet.cboBuyers.getItems();
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
	        	
	        	billingSheet.loadOrderSheet();
        	}
        }
     }
});