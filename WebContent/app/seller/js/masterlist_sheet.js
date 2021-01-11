Class('App.masterlist', 'linb.Com',{
    Instance:{
	
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
                .host(host,"dlgMasterSheetList")
//            append((new linb.UI.Panel)
//                .host(host,"paneMasterSheetList")
                .setZIndex(1)
                .setLeft(0)
                .setTop(0)
                .setWidth(1000)
                .setHeight(580)
                .setCaption("$app.caption.pmflist")
                .setPinBtn(false)
                .setMinBtn(false)
                .setMaxBtn(false)
                .setCloseBtn(false)
                .setResizer(false)
                .setMovable(false)
            );
            
                        
//            host.paneMasterSheetList.append((new linb.UI.Tabs)
//            host.dlgMasterSheetList.append((new linb.UI.Tabs)
//                .host(host,"categoriesTabPMF")
//                .setItems([{"id":"1", "caption":"$app.caption.fruits", "image":"../../common/img/fruits.gif"}, 
//                           {"id":"2", "caption":"$app.caption.vegetables", "image":"../../common/img/veggie.gif"}, 
//                           {"id":"3", "caption":"$app.caption.fish", "image":"../../common/img/fish.gif"}])
//                .setZIndex(0)
//                .onItemSelected("_categoriestabpmf_onitemselected")
//            );
            
//            host.categoriesTabPMF.append((new linb.UI.Pane)
            host.dlgMasterSheetList.append((new linb.UI.Pane)
    				.host(host,"paneSigmaGridPMF")
    				.setDock("fill")
    		);
                       
//            host.paneMasterSheetList.append((new linb.UI.Pane)
            host.dlgMasterSheetList.append((new linb.UI.Pane)
                .host(host,"pane443")
                .setDock("top")
                .setHeight(36)
            );
            
            host.pane443.append((new linb.UI.Block)
                .host(host,"block80")
                .setDock("fill")
            );
            
            host.block80.append((new linb.UI.Button)
                .host(host,"btnCancel")
                .setTop(5)
                .setWidth(85)
                .setRight(20)
                .setCaption("$app.caption.skusortcancel")
                .setImage("../../common/img/cancel.gif")
                .onClick("_btncancel_onclick")
            );
            
            host.block80.append((new linb.UI.Button)
                .host(host,"btnCopy")
                .setTop(5)
                .setWidth(70)
                .setRight(115)
                .setCaption("$app.caption.copy")
                .setImage("../../common/img/Upload.gif")
                .onClick("_btncopy_onclick")
            );
            
            host.block80.append((new linb.UI.Button)
                .host(host,"btnAddSKUPMF")
                .setLeft(10)
                .setTop(5)
                .setWidth(80)
                .setCaption("$app.caption.addsku")
                .setImage("../../common/img/tool_add.gif")
                .onClick("_btnaddskupmf_onclick")
           );
            
            host.block80.append((new linb.UI.Button)
                .host(host,"btnSave")
                .setLeft(240)
                .setTop(5)
                .setWidth(70)
                .setCaption("$app.caption.save")
                .setImage("../../common/img/save.gif")
                .onClick("_btnsave_onclick")
            );
            
            host.block80.append((new linb.UI.Button)
                .host(host,"btnExcel")
                .setLeft(320)
                .setTop(5)
                .setWidth(90)
                .setCaption("$app.caption.downloadcsv")
                .setImage("../../common/img/excel.gif")
                .setVisibility("hidden")
            );
            
            host.block80.append((new linb.UI.Button)
                .host(host,"btnCSV")
                .setLeft(420)
                .setTop(5)
                .setWidth(90)
                .setCaption("$app.caption.downloadcsv")
                .setImage("../../common/img/csv.gif")
                .setVisibility("hidden")
            );
            
            host.block80.append((new linb.UI.Button)
                .host(host,"btnSkuGroup")
                .setLeft(100)
                .setTop(5)
                .setWidth(130)
                .setCaption("$app.caption.addskugroup")
                .setImage("../../common/img/tool_addgroup.gif")
                .onClick("_btnskugroup_onclick")
            );
            
            host.block80.append((new linb.UI.Button)
                .host(host,"btnUpload")
                .setLeft(520)
                .setTop(5)
                .setWidth(90)
                .setCaption("$app.caption.uploadfile")
                .setImage("../../common/img/Upload.gif")
                .setVisibility("hidden")
            );
            
            host.block80.append((new linb.UI.Button)
                .host(host,"btnFindSku")
                .setDomId("btnFindSku")
                .setLeft(320)//620
                .setTop(5)
                .setWidth(90)
                .setCaption("$app.caption.findsku")
                .setImage("../../common/img/search.gif")
                .onClick("_btnfindsku_onclick")
            );
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events:{"onReady":"_onready", "onRender":"_onRender"},
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        }, 
        _onRender:function(page, threadid){
        	masterList = page;
//        	masterList.loadMasterFileList(1);
        	masterList.loadMasterFileList();
        },
        _onready:function(page, threadid){
        	masterList = this;
        	if (mainSPA.lblSheet.getCaption() == 10001) {
        		sheetName = 'orderSheet';
        	} else if (mainSPA.lblSheet.getCaption() == 10003) {
        		sheetName = 'allocSheet';
        	}else if (mainSPA.lblSheet.getCaption() == 10020) {
        		sheetName = 'akadenSheet';
        	}else if (mainSPA.lblSheet.getCaption() == 10005) {
        		sheetName = 'orderSheet';
        	}
        }, 
        _btncancel_onclick:function (profile, e, src, value) {
        	if (masterList.confirmNavigation()) {
        		var url = "/eON/pmf/pmfCancelNewPmf.json";
        		var obj = new Object();
        		obj.btnClicked = "pmfCancel";
        		//added by jr
        		//enable button
        		//linb.UI.getFromDom('btnProdMasterFile').setDisabled(false);
        		//end jr
        		linb.Ajax(
        				url,
        				null,
        				function (response) {
        					var o = _.unserialize(response);
//      					alert(o.response)
        				}, 
        				function(msg) {
        					linb.message("失敗： " + msg);
        				}, null, {
        					method : 'GET'
        				}
        		).start();
        		masterList.dlgMasterSheetList.destroy();
        	}
        }, 
        _btncopy_onclick:function (profile, e, src, value) {
        	if (masterList.confirmNavigation()) {
        		var f = document.getElementById('dataTableIframePMF');             
        		f.contentWindow.transfer(sheetName);
        		this.dlgMasterSheetList.destroy();
        	}
        }, 
        _btnskugroup_onclick:function (profile, e, src, value) {
        	if (masterList.confirmNavigation()) {
        		linb.ComFactory.newCom(
        				'App.addSKUGroup', 
        				function(){
        					//start:jr
        					//this will set the mouse icon to busy status
        					this
        					.setProperties({
        						fromRegion:linb.use(src).cssRegion(true)
        					});
        					//end:jr
        					masterList._excom1 = this;
//      					masterList._excom1.hiddenCategoryValue.setCaption(masterList.categoriesTabPMF.getUIValue());
        					masterList._excom1.hiddenCategoryValue.setCaption(g_orderSheetParam.categoryId);
        					this.setEvents({'onAddSKUGroup' : function(result) {
        						if (result == 'success') {
//      							masterList.loadMasterFileList(masterList.categoriesTabPMF.getUIValue());
        							masterList.loadMasterFileList();
        							masterList.loadDataTable();
        							masterList.loadMainSheet();
        						}
        					}});
//      					this.show(null, masterList.paneMasterSheetList);
        					if (g_userRole == "ROLE_SELLER"){
        						linb.Ajax(
        								"/eON/skuGroup/getSellers.json",
        								null,
        								function (response) {
        									validateResponse(response);
        									masterList._excom1.responseCheck(response);
        									var o = _.unserialize(response);
        									var items = o.response;
        									masterList._excom1.cbiSellername.setItems(items, true);
        									masterList._excom1.cbiSellername.setUIValue(items[0].caption);
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
        						masterList._excom1.cbiSellername.setDisabled(true);
        						masterList._excom1.cbiSellername.setVisibility('hidden');
        						masterList._excom1.lblSkuGroupSellerName.setVisibility('hidden');
        						masterList._excom1.inptSKUGroupName.setTop(40);
        						masterList._excom1.lblSkuGroupName.setTop(40);
        					}	

        					this.show(null, masterList.dlgMasterSheetList);
        				}
        		);
        	}
        }, 
        _btnaddskupmf_onclick:function (profile, e, src, value) {
        	var f = document.getElementById('dataTableIframePMF');
			f.contentWindow.addRow();
        }, 
//        _categoriestabpmf_onitemselected:function (profile, item, src) {
//        	masterList.categoriesTabPMF.append(masterList.paneSigmaGridPMF, item.id);
//        	masterList.loadMasterFileList(item.id);
//        },
        _btnsave_onclick:function (profile, e, src, value) {
        	masterList.updateTable();
//        	masterList.loadDataTable();
        },
        _btnfindsku_onclick:function (profile, e, src, value) {
        	if (masterList.confirmNavigation()) {
        		var host=this;
        		//start:jr
        		linb.UI.getFromDom('btnFindSku').setDisabled(true);
        		//end:jr
//      		linb.ComFactory.newCom('App.findsku' ,
//      		function(){
////    		this.show(null, masterList.paneMasterSheetList);
//      		this.show(null, masterList.dlgMasterSheetList);
//      		}
//      		);
        		linb.ComFactory.newCom(
        				'App.findsku', 
        				function(){
        					this.setEvents({'onFindSku' : function(result) {
//      						alert("onFindSKU");
        						// reload table
        						masterList.loadDataTable();
        					}});
        					this.show(null, masterList.dlgMasterSheetList);
        				}
        		);
        	}
        },
        updateTable:function () {
			var f = document.getElementById('dataTableIframePMF');
			f.contentWindow.updateTable();
		},
		loadDataTable:function () {
			var f = document.getElementById('dataTableIframePMF');
			f.contentWindow.location.reload(true);
		},
		loadMasterFileList:function(){
           	var url = "/eON/pmf/pmfSetSession.json";
            var obj = new Object();
            obj.categoryId = g_orderSheetParam.categoryId;
            obj.searchName = "";
            obj.btnClicked = "masterList";
            linb.Ajax(url, obj,
                function (response) {
            	validateResponse(response);
                	var o = _.unserialize(response);
//                	alert("response: "+o.response);
//        			masterList.paneMasterSheetList.setCaption("Product Master File " + o.response);
                	masterList.dlgMasterSheetList.setCaption("商品マスター" + o.response);
//        			var paneSigmaGridHtml = masterList.paneSigmaGridPMF.getHtml();                	
//                	if (paneSigmaGridHtml == "") {
//                		masterList.categoriesTabPMF.setValue("1");
//                        masterList.categoriesTabPMF.append(masterList.paneSigmaGridPMF, '1');
                        masterList.paneSigmaGridPMF.setHtml("<iframe id='dataTableIframePMF' name='dataTableIframe' frameborder=0 marginheight=0 marginwidth=0 scrolling=0 width=100% height=100% src='../jsp/pmfSigmaTable.jsp'> </iframe>");
                        //masterList.paneSigmaGridPMF.setHtml("1");
//                    } else {
//                    	masterList.categoriesTabPMF.setUIValue(masterList.categoriesTabPMF.getUIValue()+'');
//                    	masterList.categoriesTabPMF.append(masterList.paneSigmaGridPMF, masterList.categoriesTabPMF.getUIValue()+'');
//                    	masterList.loadDataTable();	
//                    }
                }, 
                function(msg) {
                        linb.message("失敗： " + msg);
                    }, null, {
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
        loadMainSheet : function(){
        	if (g_orderSheetParam.sheetTypeId == 10001){ //order
        		orderSheet.loadOrderSheet();
        	} else if (g_orderSheetParam.sheetTypeId == 10003){ //alloc
        		allocSheet.loadOrderSheet();
        	} else if (g_orderSheetParam.sheetTypeId == 10005){ //billing
        		orderSheet.loadOrderSheet();
        	} else if (g_orderSheetParam.sheetTypeId == 10020){ //akaden
        		akadenSheet.loadOrderSheet();
        	}
        },
        checkForDirtyTable : function () {
        	var f = document.getElementById('dataTableIframePMF');
        	if (f != null) {
				var ret = f.contentWindow.isDirty();
				return ret;
        	}
        	return false;
        },
        confirmNavigation : function () {
        	if (masterList.checkForDirtyTable()) {
        		var ans = confirm(linb.Locale[linb.getLang()].app.caption['confirmNavigation']);
        		if (ans) return true;
        		else return false;
        	}
        	return true;
        }
    }
});

