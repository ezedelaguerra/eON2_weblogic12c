var g_orderSheetParam; 

Class('App.akadensheet', 'linb.Com', {
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
                .setDisabled(true)
                .setBorder(true)
                .setLeft(30)
                .setTop(5)
                .setWidth(105)
//                .onChange("_cboBuyers_onchange")
//                .beforeUIValueSet("_cbobuyers_beforeuivalueset")
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
            
            host.paneSelectedDates.append((new linb.UI.Input)
                .host(host,"iptBuyers")
                .setLeft(30)
                .setTop(5)
                .setWidth(90)
                .setDirtyMark(false)
                .setDisabled(true)
                .setBorder(true)
                .onHotKeypress("_iptbuyers_onhotkeypress")
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
                .setWidth(80)
                .setShadow(true)
                .setCaption("$app.caption.addsku")
                .setImage("../../common/img/tool_add.gif")
                .onClick("_btnaddsku_onclick")
            );
            
            host.blockOptionButtons.append((new linb.UI.Button)
                .host(host,"btnImportAllocation")
                .setLeft(100)
                .setTop(4)
                .setWidth(90)
                .setShadow(true)
                .setCaption("$app.caption.importalloc")
                .setImage("../../common/img/import.gif")
                .setHAlign("left")
                .onClick("_btnimportallocation_onclick")
            );
            
            host.blockOptionButtons.append((new linb.UI.Button)
                .host(host,"btnSave")
                .setLeft(200)
                .setTop(5)
                .setWidth(90)
                .setShadow(true)
                .setCaption("$app.caption.save")
                .setImage("../../common/img/save.gif")
                .onClick("_btnsave_onclick")
            );
            
            host.blockOptionButtons.append((new linb.UI.Button)
                .host(host,"btnDownloadExcel")
                .setLeft(300)
                .setTop(4)
                .setWidth(90)
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
                //.setItems([{"id":"1", "caption":"$app.caption.fruits", "image":"../../common/img/fruits.gif"}, {"id":"2", "caption":"$app.caption.vegetables", "image":"../../common/img/veggie.gif"}, {"id":"3", "caption":"$app.caption.fish", "image":"../../common/img/fish.gif"}])
                .setZIndex(0)
                .onItemSelected("_categoriestab_onitemselected")
                .beforeUIValueSet("_categoriestab_beforeuivalueset")
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
                .setLeft(890)
                .setTop(2)
                .setWidth(80)
                .setDisabled(true)
                .setBorder(true)
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
		},
        events: {
            "onReady" : "_onready", "onRender":"_onRender"
        },
        iniResource: function(com, threadid) {
        },
        iniExComs: function(com, hreadid) {
        },
        _onRender:function(page, threadid){
        	akadenSheet = page;
        	sellerMenu.menubar_seller.setItems(getMenuItems());
         	if(g_clientWidth > akadenSheet.paneMain.getWidth()){
         		akadenSheet.paneMain.setWidth(g_clientWidth);
        	}			
    		if(g_clientHeight > akadenSheet.paneMain.getHeight() + 60){
    			akadenSheet.paneMain.setHeight(g_clientHeight - 60);
        	}
        },
        _onready: function() {
        	akadenSheet = this;
        	sellerMenu.menubar_seller.setItems(getMenuItems());
        	disableSellerPreferenceButtons("0");
        },
        _btnsearch_onclick:function (profile, e, src, value) {
        	if (akadenSheet.confirmNavigation()) {
	        	var ns=this;
	        	linb.ComFactory.newCom(
		    			'App.filter', 
		    			function(){
		    				akadenSheet._excom1 = this;
		    				akadenSheet._excom1.hiddenSheetTypeId.setCaption(10020);
		    				akadenSheet._excom1.hiddenDealingPatternId.setCaption(1);
		    				akadenSheet._excom1.groupDate.setCaption("Billing date");
		    				akadenSheet._excom1.hiddenFilterMode.setCaption(1);
		    				this.setEvents({'onSearch' : function(result) { 
		    					akadenSheet.onSearchEvent(result);        					
		    				}});
		    				ns.$cached=this;
		    				this.show(null, akadenSheet.paneSubCom);
		    			}
		        	);
        	}
        },
        loadOrderSheet : function () {
        	var obj = new Object();
        	//akadenSheet.paneSigmaGrid.setHtml("");
        	obj.startDate = g_orderSheetParam.startDate;
            obj.endDate = g_orderSheetParam.endDate;
            obj.selectedSellerID = g_orderSheetParam.selectedSellerID;
        	obj.selectedBuyerCompany = g_orderSheetParam.selectedBuyerCompany;
            obj.selectedBuyerID = g_orderSheetParam.selectedBuyerID;
            obj.selectedDate = g_orderSheetParam.selectedDate;
            obj.categoryId = g_orderSheetParam.categoryId;
            obj.sheetTypeId = g_orderSheetParam.sheetTypeId;
            obj.checkDBOrder = g_orderSheetParam.checkDBOrder;
            obj.datesViewBuyerID = g_orderSheetParam.datesViewBuyerID;
            
            akadenSheet.lblRoundOffSheet.setCaption("loadBilling");
            
            akadenSheet.linkPrevious.setVisibility('hidden');
            akadenSheet.linkNext.setVisibility('hidden');
            akadenSheet.categoriesTab.setUIValue(g_orderSheetParam.categoryId);
            /*set date panel*/
        	akadenSheet.setDatePanel(true);
            
            linb.Ajax(
    	        "/eON/akaden/setAkadenParameter.json",
    	        obj,
    	        function (response) {
    	        	validateResponse(response);
    	        	var paneSigmaGridHtml = akadenSheet.paneSigmaGrid.getHtml();                	
    	        	if (paneSigmaGridHtml == "") {
    	        		akadenSheet.categoriesTab.setValue(g_orderSheetParam.categoryId);
    	        		akadenSheet.categoriesTab.append(akadenSheet.paneSigmaGrid, g_orderSheetParam.categoryId);
    	        		akadenSheet.paneSigmaGrid.setHtml("<iframe id='dataTableIframe' name='dataTableIframe' frameborder=0 marginheight=0 marginwidth=0 scrolling=0 width=100% height=99% src='../jsp/akadenGrid.jsp'></iframe>");
    	            } else {
    	            	akadenSheet.categoriesTab.setUIValue(akadenSheet.categoriesTab.getUIValue()+'');
    	            	akadenSheet.categoriesTab.append(akadenSheet.paneSigmaGrid, akadenSheet.categoriesTab.getUIValue()+'');
    	            	akadenSheet.loadDataTable();
    	            }
    	        	akadenSheet.computeGTPrice();
    	           	//enable edit menu
    	        	sellerMenu.menubar_seller.updateItem('mnuEdit', {disabled:false});
    	        	sellerMenu.menubar_seller.updateItem('subuprefRoundoff', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('roundoff1', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('roundoff2', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('roundoff3', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('subuprefUnitOrder', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('utilPMF', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('utilDownloadExcel', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('mnuAkadensheet', {disabled:true});
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
        	g_categoryId = item.id; // this is for import allocation data
        	this.categoriesTab.append(this.paneSigmaGrid, item.id);
        	g_orderSheetParam.categoryId = item.id;
        	g_orderSheetParam.checkDBOrder = false;
        	akadenSheet.loadOrderSheet();
        },
        _btnaddsku_onclick:function (p,item,src) {
        	akadenSheet.addRow();
        },
        _cboBuyers_onchange:function (profile, oldValue, newValue) {
        	var id;
        	var items = akadenSheet.cboBuyers.getItems();
        	for (var i=0; i<items.length; i++) {
    			var thisObj = items[i];
    			if (newValue == thisObj.caption) {
    				id = thisObj.id;
    				break;
    			}
    		}
        	akadenSheet.iptBuyers.setUIValue(newValue);
        	g_orderSheetParam.datesViewBuyerID = id;
        	g_orderSheetParam.checkDBOrder = false;
        	akadenSheet.loadOrderSheet();
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
		toggleVisibilities : function (checked) {
			var f = document.getElementById('dataTableIframe');
			if (f == null) {
				return;
			}
			f.contentWindow.toggleVisibilities(checked);
        },
        _linkprevious_onclick:function (profile, e) {
        	if (akadenSheet.confirmNavigation()) {
	        	var newDates = getNewStartEndDate(g_orderSheetParam, 'previous');
	        	g_orderSheetParam.startDate = newDates.startDate;
	        	g_orderSheetParam.endDate = newDates.endDate;
	        	g_orderSheetParam.selectedDate = newDates.selectedDate;
	        	g_orderSheetParam.checkDBOrder = true;
	            
	        	akadenSheet.loadOrderSheet();
        	}
        },
        _linknext_onclick:function (profile, e) {
        	if (akadenSheet.confirmNavigation()) {
	        	var newDates = getNewStartEndDate(g_orderSheetParam, 'next');
	        	g_orderSheetParam.startDate = newDates.startDate;
	        	g_orderSheetParam.endDate = newDates.endDate;
	        	g_orderSheetParam.selectedDate = newDates.selectedDate;
	        	g_orderSheetParam.checkDBOrder = true;
	            
	        	akadenSheet.loadOrderSheet();
        	}
        },
        setDatePanel : function (disabled) {
        	akadenSheet.datePanel.setHtml(setDatePanel(g_orderSheetParam, disabled, akadenSheet), true);
        },
        computeForPrices : function (totalPriceWOTax, totalPriceWTax) {
        	if (totalPriceWOTax == "undefined" || totalPriceWOTax == "NaN" || totalPriceWOTax == null)
        		totalPriceWOTax = 0;
        	//var totalPriceWTax = totalPriceWOTax * 1.05;
        	var price = akadenSheet.roundOffPriceWTax(totalPriceWTax);
        	totalPriceWOTax = akadenSheet.roundOffPriceWTax(totalPriceWOTax);
        	if (totalPriceWTax == "NaN") totalPriceWTax = 0;
        	if (price == 'NaN') price = 0;
        	akadenSheet.totpricewtax.setCaption(toJapaneseCurrency(price));
    		akadenSheet.totpricewotax.setCaption(toJapaneseCurrency(totalPriceWOTax)); 
        	akadenSheet.hdntotpricewtax.setValue(totalPriceWTax); // default computation
        },
        updatePrices : function (oldValue,value,price) {
        	var _price = akadenSheet.totpricewotax.getCaption();
        	_price = _price.replaceAll("&yen;", "");
        	_price = _price.replaceAll(",", "");
        	var subtrahen = Math.round(oldValue * price);
        	_price = _price - subtrahen;
        	var addent = Math.round(value * price);
        	_price = _price + addent;
        	akadenSheet.totpricewotax.setCaption(toJapaneseCurrency(_price));
        	
        	_price = akadenSheet.gtPriceWOTax.getCaption();
        	_price = _price.replaceAll("&yen;", "");
        	_price = _price.replaceAll(",", "");
        	_price = _price - subtrahen;
        	_price = _price + addent;
        	akadenSheet.gtPriceWOTax.setCaption(toJapaneseCurrency(_price));
        	
        	var _price = akadenSheet.totpricewtax.getCaption();
        	_price = _price.replaceAll("&yen;", "");
        	_price = _price.replaceAll(",", "");
        	var subtrahen = Math.round(oldValue * Math.round(price * 1.05));
        	_price = _price - subtrahen;
        	var addent = Math.round(value * Math.round(price * 1.05));
        	_price = _price + addent;
        	akadenSheet.totpricewtax.setCaption(toJapaneseCurrency(_price));
        	
        	_price = akadenSheet.gtPriceWTax.getCaption();
        	_price = _price.replaceAll("&yen;", "");
        	_price = _price.replaceAll(",", "");
        	_price = _price - subtrahen;
        	_price = _price + addent;
        	akadenSheet.gtPriceWTax.setCaption(toJapaneseCurrency(_price));
        }, 
        roundOffPriceWTax: function(price){
        	var priceRounded = 0 ;
    		if (sellerMenu.lblRoundOffStatus.getCaption() == "lower"){
    			priceRounded = Math.floor(price);
    		} else if (sellerMenu.lblRoundOffStatus.getCaption() == "higher"){
    			priceRounded = Math.ceil(price);
    		} else {
    			priceRounded = Math.round(price);
    		}
    		return priceRounded;
        },
        computeGTPrice : function () {
        	var url = "/eON/akaden/computeGTPrice.json";
        	var id = Math.random()*url.length;
        	linb.Ajax(
    	        url + "?id="+id,
    	        null,
    	        function (response) {
    	        	validateResponse(response);
    	        	akadenSheet.responseCheck(response);
    	        	if(akadenSheet.hasNoError(response)) {
    	        		var obj = _.unserialize(response);
    	        		var price = akadenSheet.roundOffPriceWTax(obj.gtPriceWTax);
        	        	var priceWOTax = akadenSheet.roundOffPriceWTax(obj.gtPriceWOTax);
    	        		if (obj.gtPriceWOTax != null) {
	        	        	akadenSheet.gtPriceWOTax.setCaption(toJapaneseCurrency(priceWOTax));
	        	        	akadenSheet.gtPriceWTax.setCaption(toJapaneseCurrency(price));
	        	        	akadenSheet.hdnGtPriceWTax.setValue(toJapaneseCurrency(obj.gtPriceWTax));
    	        		} else {
    	        			akadenSheet.gtPriceWOTax.setCaption(toJapaneseCurrency(0));
    	        			akadenSheet.gtPriceWTax.setCaption(toJapaneseCurrency(0));
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
        onSearchEvent : function (result) {
        	//set categorytabs
        	akadenSheet.paneSigmaGrid.setHtml("");
        	akadenSheet.categoriesTab.setItems(categoryTabs);
        	
        	g_orderSheetParam = result;
			g_orderSheetParam.selectedDate = g_orderSheetParam.startDate;
			g_orderSheetParam.checkDBOrder = true;
			
			akadenSheet.loadOrderSheet();
			akadenSheet.paneSelectedDates.reBoxing().show();
			akadenSheet.paneOptionbuttons.reBoxing().show();
			akadenSheet.panelMainSheet.reBoxing().show();
			var localBuyer = [];
			for (var i=0; i<g_orderSheetParam.buyerCombo.length;i++) {
				localBuyer[0] = g_orderSheetParam.buyerCombo[i];
			}
			g_orderSheetParam.buyerCombo = localBuyer;
			akadenSheet.cboBuyers.setItems(g_orderSheetParam.buyerCombo, true);
			akadenSheet.iptBuyers.setUIValue(localBuyer[0].caption);
            akadenSheet.totpricewtax.setCaption("");
        	akadenSheet.totpricewotax.setCaption("");
        },
        _btnimportallocation_onclick:function (profile, e, src, value) {
        	if (akadenSheet.confirmNavigation()) {
	        	var host=this;
	            linb.ComFactory.newCom('App.importallocdata' ,function(){
	            this.show(linb([document.body]));});
        	}
        },
        movetoakaden: function(rec) {
        	var f = document.getElementById('dataTableIframe');
        },
        refreshTheGridNow: function() {
        	var f = document.getElementById('dataTableIframe');
        	f.contentWindow.refreshMyGridNow();
        },
        _btnsave_onclick:function (profile, e, src, value) {
        	this.btnSave.setDisabled(true);
        	var f = document.getElementById('dataTableIframe');
        	f.contentWindow.onSave();
        },
        _btnDownloadExcel_onclick : function (profile, e, src, value) {
        	var ns=this;
     		linb.ComFactory.newCom(
     			'App.downloadExcel', 
     			function(){
     				akadenSheet._excom1 = this;
     				this.setEvents({'onDownloadExcel' : function(paramObj) { 
     					sellerMenu.downloadExcel(paramObj);
     				}});
     				ns.$cached=this;
     				this.show(null, akadenSheet.paneSubCom);
     			}); 
        },
        movetosheet: function(rec) {
            var f = document.getElementById('dataTableIframe');
            f.contentWindow.transfernow(rec);
        
        }, 
	    _iptbuyers_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            return false;
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
        	if (akadenSheet.checkForDirtyTable()) {
//        		var ans = confirm(linb.Locale[linb.getLang()].app.caption['confirmNavigation']);
//        		if (ans) return true;
//        		else return false;
        		alert(linb.Locale[linb.getLang()].app.caption['alertUnsavedChanges']);
        		return false;
        	}
        	return true;
        },
        _categoriestab_beforeuivalueset : function (profile, oldValue, newValue) {
        	return akadenSheet.confirmNavigation();
        },
        _cbobuyers_beforeuivalueset : function (profile, oldValue, newValue) {
        	return akadenSheet.confirmNavigation();
        },
        refreshment : function () {
         	g_orderSheetParam.checkDBOrder = true;
         	akadenSheet.loadOrderSheet();
         }
	}
});
