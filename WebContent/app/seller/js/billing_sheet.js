var g_orderSheetParam; // should only be changed in 'onSearch' fire event
// should only be changed when selecting a date from date panel
//var g_selectedDate;
//var g_allDatesView; //should be change to false 'onSearch'; true when changing buyer combo box
//var g_datesViewBuyerID;

Class('App.billingSheet', 'linb.Com', {
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
                .setDisabled(true)
                .onClick("_btnaddskugroup_onclick")
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
            
//            host.blockOptionButtons.append((new linb.UI.Button)
//                .host(host,"btnFinalize")
//                .setLeft(310)
//                .setTop(4)
//                .setWidth(90)
//                .setShadow(true)
//                .setCaption("$app.caption.finalize")
//                .setImage("../../common/img/finalize.gif")
//            );
            host.blockOptionButtons.append((new linb.UI.Button)
                .host(host,"btnFinalize")
                .setLeft(285)
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
                .setLeft(350)
                .setTop(4)
                .setWidth(70)
                .setShadow(true)
                .setCaption("$app.caption.unfinalize")
                //.setImage("../../common/img/unfinalize.gif")
                .onClick("_btnUnfinalize_onclick")
                .setDisabled(true)
            );           
//            host.blockOptionButtons.append((new linb.UI.Button)
//                .host(host,"btnAkaden")
//                .setLeft(310)
//                .setTop(4)
//                .setWidth(90)
//                .setShadow(true)
//                .setCaption("$app.caption.akadensheet")
//                .setImage("../../common/img/akaden.gif")
//                .onClick("_btnAkaden_onclick")
//            );
            
            host.blockOptionButtons.append((new linb.UI.Button)
                .host(host,"btnDownloadExcel")
                .setLeft(425)
                .setTop(4)
                .setWidth(90)
                .setShadow(true)
                .setCaption("$app.caption.downloadexcel")
                .setImage("../../common/img/excel.gif")
                .onClick("_btnDownloadExcel_onclick")
            );

//            host.blockOptionButtons.append((new linb.UI.Button)
//                .host(host,"btnProdMasterFile")
//                .setLeft(510)
//                .setTop(4)
//                .setWidth(90)
//                .setShadow(true)
//                .setCaption("$app.caption.prodmasterfile")
//                .setImage("../../common/img/file.gif")
//                .onClick("_btnprodmasterfile_onclick")
//            );
            
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
                .setDisabled(true)
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
                .setTop(2)
                .setRight(10)
                .setZIndex(1002)
                .setWidth(110)
                .setBorder(false)
                .setCustomStyle({"KEY":"background-color:white;color:gray;"})
            );
            
            host.panelMainSheet.append((new linb.UI.Input)
                .host(host,"hdntotpricewtax")
                .setLeft(650)
                .setTop(2)
                .setWidth(100)
                .setDisabled(true)
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
            "onReady" : "_onready", "onRender":"_onRender"
        },
        iniResource: function(com, threadid) {
        },
        iniExComs: function(com, hreadid) {
        },
        _onRender:function(page, threadid){
        	orderSheet = page;
        	if(g_clientWidth > orderSheet.paneMain.getWidth()){
        		orderSheet.paneMain.setWidth(g_clientWidth);
        	}			
			if(g_clientHeight > orderSheet.paneMain.getHeight() + 60){
        		orderSheet.paneMain.setHeight(g_clientHeight - 60);
        	}
        },
        _onready: function() {
        	orderSheet = this;
        	sellerMenu.menubar_seller.setItems(getMenuItems());
        	disableSellerPreferenceButtons("2");
        },
        _btnsearch_onclick:function (profile, e, src, value) {
        	if (orderSheet.confirmNavigation()) {
	        	 var ns=this;
	        	 linb.ComFactory.newCom(
		        			'App.filter', 
		        			function(){
		        				orderSheet._excom1 = this;
		        				if (g_userRole == "ROLE_SELLER"){
		        					orderSheet._excom1.hiddenSheetTypeId.setCaption(10005);
		                    	}
		                    	if (g_userRole == "ROLE_SELLER_ADMIN"){
		                    		orderSheet._excom1.hiddenSheetTypeId.setCaption(10011);
		                    	}
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
        	//orderSheet.paneSigmaGrid.setHtml("");
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
            
            orderSheet.lblRoundOffSheet.setCaption("loadBilling");
            orderSheet.lblSelectedDate.setCaption(g_orderSheetParam.selectedDate);
            
            /* reset the visibility toggle */
            //orderSheet.btnDownloadExcel.setDisabled(true);
            //orderSheet..setUIValue(false);
            //orderSheet.labelVisToggle.setVisibility("visible");
            //orderSheet.visToggle.setVisibility("visible");
            //orderSheet.visToggle.setDisabled(true);
            orderSheet.linkPrevious.setVisibility('hidden');
            orderSheet.linkNext.setVisibility('hidden');
            orderSheet.categoriesTab.setUIValue(g_orderSheetParam.categoryId);
            
            obj.datesViewBuyerID = '0';
            if (g_orderSheetParam.allDatesView) {
            	obj.datesViewBuyerID = g_orderSheetParam.datesViewBuyerID;
            	//orderSheet.visToggle.setVisibility("hidden");
            	//orderSheet.labelVisToggle.setVisibility("hidden");
            }
            else orderSheet.cboBuyers.setValue("All", true);
            
            /*set date panel*/
        	orderSheet.setDatePanel(true);
            
            linb.Ajax(
    	        "/eON/billing/setParameter.json",
    	        obj,
    	        function (response) {
    	        	validateResponse(response);
    	        	//orderSheet.responseCheck(response);
    	        	validateResponse(response);
    	        	var paneSigmaGridHtml = orderSheet.paneSigmaGrid.getHtml();                	
    	        	if (paneSigmaGridHtml == "") {
    	        		orderSheet.categoriesTab.setValue(g_orderSheetParam.categoryId);
    	        		orderSheet.categoriesTab.append(orderSheet.paneSigmaGrid, g_orderSheetParam.categoryId);
    	        		orderSheet.paneSigmaGrid.setHtml("<iframe id='dataTableIframe' name='dataTableIframe' frameborder=0 marginheight=0 marginwidth=0 scrolling='no' width=100% height=99% src='../jsp/billingTable.jsp'></iframe>");
    	            } else {
    	            	orderSheet.categoriesTab.setUIValue(orderSheet.categoriesTab.getUIValue()+'');
    	            	orderSheet.categoriesTab.append(orderSheet.paneSigmaGrid, orderSheet.categoriesTab.getUIValue()+'');
    	            	orderSheet.loadDataTable();
    	            }
    	        	orderSheet.computeGTPrice();
    	        	sellerMenu.menubar_seller.updateItem('mnuEdit', {disabled:false}); 
    	        	sellerMenu.menubar_seller.updateItem('subuprefRoundoff', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('roundoff1', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('roundoff2', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('roundoff3', {disabled:false});
//    	            sellerMenu.menubar_seller.updateItem('subuprefShowcols', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('subuprefUnitOrder', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('utilPMF', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('utilDownloadExcel', {disabled:false});
    	            sellerMenu.menubar_seller.updateItem('mnuBillingsheet', {disabled:true});
    	        }, 
    	        function(msg) {
    	                linb.message("失敗： setParameter.json " + msg);
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
        	this.categoriesTab.append(this.paneSigmaGrid, item.id);
        	g_orderSheetParam.categoryId = item.id;
        	g_orderSheetParam.checkDBOrder = false;
        	orderSheet.loadOrderSheet();
        	//orderSheet.simpleLoadByCategory(item.id);
        }, 
        _btnprodmasterfile_onclick:function (profile, e, src, value) {
//            var host=this;
            linb.ComFactory.newCom('App.productmasterlist' ,function(){
            	this.show(linb([document.body]));
//            	this.setEvents({'onSelectPMF' : function(result) { 
//            		alert("from PMF " + result.pmfName);
//            		orderSheet.loadMasterList(result);
//    			}});
            });
        },
        _btnaddsku_onclick:function (p,item,src) {
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
        _cboBuyers_onchange:function (profile, oldValue, newValue) {
        	//alert(oldValue);
        	//alert(newValue);
        	//alert(_.serialize(profile));
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
//        loadMasterList : function (result) {
//        	alert (result.pmfName);
//        	linb.ComFactory.newCom(
//    			'App.masterlist', 
//    			function(){
//    				orderSheet.pmf=this;
//    				orderSheet.pmf.dlgMasterSheetList.setCaption("Product Master File " + result.pmfName);
//    				this.show(null, orderSheet.pmf.paneMain);
//    			}
//        	);
//        },
//        computeForTotalPrices : function () {
//        	var url = "/eON/billing/computeTotalPrice.json";
//        	var id = Math.random()*url.length;
//        	linb.Ajax(
//    	        url + "?id="+id,
//    	        null,
//    	        function (response) {
//    	        	orderSheet.responseCheck(response);
//    	        	if(orderSheet.hasNoError(response)) {
//    	        		var obj = _.unserialize(response);
//    	        		var price = orderSheet.roundOffPriceWTax(obj.totalPriceWTax);
//        	        	orderSheet.totpricewtax.setValue(price);
//        	        	orderSheet.totpricewotax.setValue(obj.totalPriceWOTax);
//        	        	orderSheet.hdntotpricewtax.setValue(obj.totalPriceWTax);
//    	        	}
//		    	}, 
//		    	function(msg) {
//		    		linb.message("失敗： computeTotalPrice.json " + msg);
//    	        }, 
//    	        null, 
//    	        {
//    	        	method : 'GET',
//    	        	retry : 0
//    	        }
//    	    ).start();
//        },,
        computeForPrices : function (totalPriceWOTax, totalPriceWTax) {
        	//var totalPriceWTax = totalPriceWOTax * 1.05;
        	var price = orderSheet.roundOffPriceWTax(totalPriceWTax);
        	totalPriceWOTax = orderSheet.roundOffPriceWTax(totalPriceWOTax);
    		orderSheet.totpricewtax.setCaption(toJapaneseCurrency(price));
        	orderSheet.totpricewotax.setCaption(toJapaneseCurrency(totalPriceWOTax));
    		orderSheet.hdntotpricewtax.setValue(totalPriceWTax); // default computation
        },
        updatePrices : function (oldValue,value,price) {
        	var _price = orderSheet.totpricewotax.getCaption();
        	_price = _price.replaceAll("&yen;", "");
        	_price = _price.replaceAll(",", "");
        	var subtrahen = Math.round(oldValue * price);
        	_price = _price - subtrahen;
        	var addent = Math.round(value * price);
        	_price = _price + addent;
        	orderSheet.totpricewotax.setCaption(toJapaneseCurrency(_price));
        	
        	_price = orderSheet.gtPriceWOTax.getCaption();
        	_price = _price.replaceAll("&yen;", "");
        	_price = _price.replaceAll(",", "");
        	_price = _price - subtrahen;
        	_price = _price + addent;
        	orderSheet.gtPriceWOTax.setCaption(toJapaneseCurrency(_price));

        	var _price = orderSheet.totpricewtax.getCaption();
        	_price = _price.replaceAll("&yen;", "");
        	_price = _price.replaceAll(",", "");
        	var subtrahen = Math.round(oldValue * Math.round(price * 1.05));
        	_price = _price - subtrahen;
        	var addent = Math.round(value * Math.round(price * 1.05));
        	_price = _price + addent;
        	orderSheet.totpricewtax.setCaption(toJapaneseCurrency(_price));
        	
        	_price = orderSheet.gtPriceWTax.getCaption();
        	_price = _price.replaceAll("&yen;", "");
        	_price = _price.replaceAll(",", "");
        	_price = _price - subtrahen;
        	_price = _price + addent;
        	orderSheet.gtPriceWTax.setCaption(toJapaneseCurrency(_price));
        }, 
        getCategoryId : function () {
        	return g_orderSheetParam.categoryId;
        },
        _visToggle_onchecked : function (profile, e, src) {
        	orderSheet.toggleVisibilities(e);
        },
        simpleLoadByCategory : function (categoryId) {
        	var url = "/eON/billing/setCategoryId.json";
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
    	                linb.message("失敗： setCategoryId.json " + msg);
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
        computeGTPrice : function () {
        	var url = "/eON/billing/computeGTPrice.json";
        	var id = Math.random()*url.length;
        	linb.Ajax(
    	        url + "?id="+id,
    	        null,
    	        function (response) {
    	        	validateResponse(response);
    	        	orderSheet.responseCheck(response);
    	        	if(orderSheet.hasNoError(response)) {
    	        		var obj = _.unserialize(response);
    	        		var price = orderSheet.roundOffPriceWTax(obj.gtPriceWTax);
    	        		var priceWOTax = orderSheet.roundOffPriceWTax(obj.gtPriceWOTax);
    	        		if (obj.gtPriceWOTax != null) {
	    	        		orderSheet.gtPriceWOTax.setCaption(toJapaneseCurrency(priceWOTax));
	        	        	orderSheet.gtPriceWTax.setCaption(toJapaneseCurrency(price));
	        	        	orderSheet.hdnGtPriceWTax.setValue(obj.gtPriceWTax);
    	        		} else {
    	        			orderSheet.gtPriceWOTax.setCaption(toJapaneseCurrency(0));
	        	        	orderSheet.gtPriceWTax.setCaption(toJapaneseCurrency(0));
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
//        computeGTPrice : function () {
//        	var url = "/eON/billing/computeGTPrice.json";
//        	var id = Math.random()*url.length;
//        	linb.Ajax(
//    	        url + "?id="+id,
//    	        null,
//    	        function (response) {
//    	        	orderSheet.responseCheck(response);
//    	        	if(orderSheet.hasNoError(response)) {
//    	        		var obj = _.unserialize(response);
//    	        		var price = orderSheet.roundOffPriceWTax(obj.gtPriceWTax);
//        	        	orderSheet.gtPriceWOTax.setValue(obj.gtPriceWOTax);
//        	        	orderSheet.gtPriceWTax.setValue(price);
//        	        	orderSheet.hdnGtPriceWTax.setValue(obj.gtPriceWTax);
//    	        	}
//		    	}, 
//		    	function(msg) {
//		    		linb.message("失敗： computeGTPrice.json " + msg);
//    	        }, 
//    	        null, 
//    	        {
//    	        	method : 'GET',
//    	        	retry : 0
//    	        }
//    	    ).start();
//        },
        onSearchEvent : function (result) {
        	//set categorytabs
        	orderSheet.paneSigmaGrid.setHtml("");
        	orderSheet.categoriesTab.setItems(categoryTabs);
        	
        	g_orderSheetParam = result;
			g_orderSheetParam.selectedDate = g_orderSheetParam.startDate;
			g_orderSheetParam.allDatesView = false;
			g_orderSheetParam.checkDBOrder = true;
			/**/
			orderSheet.loadOrderSheet();
			/**/
			orderSheet.paneSelectedDates.reBoxing().show();
			orderSheet.paneOptionbuttons.reBoxing().show();
			orderSheet.panelMainSheet.reBoxing().show();
			orderSheet.cboBuyers.setItems(g_orderSheetParam.buyerCombo, true);
            orderSheet.totpricewtax.setCaption("");
        	orderSheet.totpricewotax.setCaption("");
        	orderSheet.gtPriceWTax.setCaption("");
        	orderSheet.gtPriceWOTax.setCaption("");
        },
        movetosheet: function(rec) {
            var f = document.getElementById('dataTableIframe');
            f.contentWindow.transfernow(rec);
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
        downloadExcel : function (paramObj) {
        	var f = document.getElementById('dataTableIframe');
    		f.contentWindow.submitTempForm(_.serialize(paramObj));
        },
        _btnSave_onclick : function (p,item,src) {
        	this.btnSave.setDisabled(true);
        	orderSheet.saveOrder();
        },
    	saveOrder : function () {
    		var f = document.getElementById('dataTableIframe');
    		f.contentWindow.onSave();
    	},
        _btnFinalize_onclick : function (p,item,src) {
        	if (orderSheet.confirmNavigation()) {
	    		var f = document.getElementById('dataTableIframe');
	    		f.contentWindow.onFinalize();
        	}
        },
        _btnUnfinalize_onclick : function (p,item,src) {
        	if (orderSheet.confirmNavigation()) {
	    		var f = document.getElementById('dataTableIframe');
	    		f.contentWindow.onUnfinalize();
        	}
        },
        _btnAkaden_onclick:function (profile, e, src, value) {
        	alert("click");
            linb.ComFactory.newCom('App.akadensheet' ,function(){
            	this.show(linb([document.body]));
            	alert("create com");
            	this.setEvents({'onSave' : function() { 
            		alert("from akaden");
        			var f = document.getElementById('dataTableIframe');
        			f.contentWindow.location.reload(true);
    			}});
            });
        },
        disableActionButtons : function () {
        	this.btnAddSKU.setDisabled(true);
        	this.btnAddSKUGroup.setDisabled(true);
        	this.btnSave.setDisabled(true);
        	this.btnFinalize.setDisabled(true);
        	this.btnUnfinalize.setDisabled(true);
        },
        disableMenus : function () {
        	sellerMenu.menubar_seller.updateItem('utilPMF', {disabled:true});
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
//        		var ans = confirm(linb.Locale[linb.getLang()].app.caption['confirmNavigation']);
//        		if (ans) return true;
//        		else return false;
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
        },
        enableMenus : function () {
        	sellerMenu.menubar_seller.updateItem('utilPMF', {disabled:false});
        }
    }
});
