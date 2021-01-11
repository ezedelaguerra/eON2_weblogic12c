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
// * 20121213		Melissa	v15			Redmine 1228 - Duplicate data from download menu
// --%>

Class('App.downloadcsv', 'linb.Com',{
    Instance:{
        customAppend:function(){
        var self=this, prop = this.properties;

        if(prop.fromRegion)
            self.dlgDownloadCSV.setFromRegion(prop.fromRegion);

        if(!self.dlgDownloadCSV.get(0).renderId)
            self.dlgDownloadCSV.render();
            self.dlgDownloadCSV.show(self.parent, true);
        }, 
        autoDestroy:true, 
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
                .host(host,"dlgDownloadCSV")
                .setLeft(0)
                .setTop(0)
                .setWidth(310)
                .setHeight(400)
                .setShadow(false)
                .setResizer(false)
                .setCaption("$app.caption.downloadWindow")
                .setMinBtn(false)
                .setMaxBtn(false)
                .setCloseBtn(false)
                .setPinBtn(false)
            );
            
            host.dlgDownloadCSV.append((new linb.UI.Group)
                .host(host,"grpDownloadCSV")
                .setLeft(10)
                .setTop(10)
                .setWidth(280)
                .setHeight(310)
                .setCaption("$app.caption.downloadcsv")
                .setToggleBtn(false)
            );
            
            host.grpDownloadCSV.append((new linb.UI.Label)
                .host(host,"lblDateFrom")
                .setLeft(5)
                .setTop(10)
                .setWidth(95)
                .setCaption("$app.caption.csvstartdate :")
            );
            
            host.grpDownloadCSV.append((new linb.UI.ComboInput)
                .host(host,"cbiDateFrom")
                .setDirtyMark(false)
                .setLeft(100)
                .setTop(10)
                .setWidth(160)               
                .setDirtyMark(false)
                .setType("datepicker")
                .onRender("_cbiDateFrom_onrender")
                .onChange("_cbiDateFrom_onchange")
            );
            
            host.grpDownloadCSV.append((new linb.UI.Label)
                .host(host,"lblDateTo :")
                .setLeft(5)
                .setTop(40)
                .setWidth(95)
                .setCaption("$app.caption.csvenddate :")
            );
            
            host.grpDownloadCSV.append((new linb.UI.ComboInput)
                .host(host,"cbiDateTo")
                .setDirtyMark(false)
                .setLeft(100)
                .setTop(40)
                .setWidth(160)               
                .setDirtyMark(false)
                .setType("datepicker")
                .onRender("_cbiDateTo_onrender")
                .onChange("_cbiDateTo_onchange")
            );
            
            host.grpDownloadCSV.append((new linb.UI.Label)
                .host(host,"lblSheetType")
                .setLeft(10)
                .setTop(70)
                .setWidth(90)
                .setCaption("$app.caption.csvsheettype :")
            );
            
            host.grpDownloadCSV.append((new linb.UI.ComboInput)
                .host(host,"cbiSheetType")
                .setLeft(100)
                .setTop(70)
                .setWidth(160)
                .setType('listbox')
            );
            
            host.grpDownloadCSV.append((new linb.UI.List)
                .host(host,"lstBuyers")
                .setLeft(100)
                .setTop(100)
                .setWidth(160)
                .setHeight(120)
                .setSelMode("multi")
                .setDirtyMark(false)
                .onItemSelected("_lstBuyers_onitemselected")
            );
            
            host.grpDownloadCSV.append((new linb.UI.Label)
                .host(host,"lblBuyers")
                .setLeft(10)
                .setTop(100)
                .setWidth(90)
                .setCaption("$app.caption.buyers :")
            );
            
            host.grpDownloadCSV.append((new linb.UI.Label)
                .host(host,"lblCategory")
                .setLeft(10)
                .setTop(230)
                .setWidth(90)
                .setCaption("$app.caption.csvcategory :")
            );
            
            host.grpDownloadCSV.append((new linb.UI.ComboInput)
                .host(host,"cbiCategory")
                .setLeft(100)
                .setTop(230)
                .setWidth(160)       
                .setDirtyMark(false)
                .setBorder(true)
                .setType('listbox')
            );
            
            host.grpDownloadCSV.append((new linb.UI.CheckBox)
                .host(host,"chkBoxQty")
                .setLeft(90)
                .setTop(260)
                .setWidth(160)                
                .setDirtyMark(false)
                .setCaption("$app.caption.csvhasquantity")
                .onChecked("_chkboxqty_onchecked")
            );
            
            host.grpDownloadCSV.append((new linb.UI.Input)
                .host(host,"iptDateFrom")
                .setLeft(100)
                .setTop(10)
                .setWidth(145)               
                .setDirtyMark(false)
                .setBorder(true)
                .onChange("_iptdatefrom_onChange")
            );
            
            host.grpDownloadCSV.append((new linb.UI.Input)
                .host(host,"iptDateTo")
                .setLeft(100)
                .setTop(40)
                .setWidth(145)               
                .setDirtyMark(false)
                .setBorder(true)
                .onChange("_iptdateto_onChange")                
            );
            
            host.dlgDownloadCSV.append((new linb.UI.Button)
                .host(host,"btnDownload")
                .setLeft(120)
                .setTop(330)
                .setWidth(70)
                .setCaption("$app.caption.csvdownload")
                .onClick("_btndownload_onclick")
            );
            
            host.dlgDownloadCSV.append((new linb.UI.Button)
                .host(host,"btnCancel")
                .setLeft(200)
                .setTop(330)
                .setWidth(70)
                .setCaption("$app.caption.cancel")
                .onClick("_btncancel_onclick")
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
            downloadcsv = page;
        }, 
        _onready: function() {
            downloadcsv = this;
            this.initLoad();
            //this.loadCSVUISettings();
            //this.loadCategoryAndSheets();
        }, 
        _btncancel_onclick:function (profile, e, src, value) {
            downloadcsv.destroy();
        }, 
        _btndownload_onclick:function (profile, e, src, value) {
            if (!downloadcsv.isErrorFieldRequired()) {
            	if (this.checkValidDates()) {
	                var url = "/eON/downloadCSV.json";
	                var DownloadCSVSettings = new Object();
	                var objDateFrom = new Date(downloadcsv.iptDateFrom.getUIValue());
	                var objDateTo = new Date(downloadcsv.iptDateTo.getUIValue());
	                DownloadCSVSettings.startDate = toYYYYMMDD(objDateFrom);
	                DownloadCSVSettings.endDate = toYYYYMMDD(objDateTo);
	                DownloadCSVSettings.sheetTypeId = downloadcsv._getSheetTypeId();
	                DownloadCSVSettings.selectedBuyerIds = downloadcsv.getBuyerList();
	                DownloadCSVSettings.selectedCategoryIds = downloadcsv._getCategoryId();
	                DownloadCSVSettings.hasQty = downloadcsv.chkBoxQty.getUIValue();
	                submitTheForm(url, "_json", _.serialize(DownloadCSVSettings), linb.Locale[linb.getLang()].app.caption['confirmDownload']);
            	}
            }
        }, 
        _chkboxqty_onchecked:function (profile, e, value) {
            if(e){
            	downloadcsv.chkBoxQty.setUIValue(true);
            }else{
            	downloadcsv.chkBoxQty.setUIValue(false);
            }
        }, 
        
        //initialize the downloadcsv window
        initLoad: function () {
        	
        	//1. initialize the list of sheet type 
        	//2. initialize the list of category
        	//3. Start date
        	//4. initialize data from the sheet
        	//   4.1. Start date
        	//   4.2. End date   
        	//   4.3. Sheet Type
        	//   4.4. Seller/Buyer list
        	//   4.5. Category
        	var url = "/eON/downloadCSVInit.json";
            linb.Ajax(url,null,
                function(response){
            	
            		validateResponse(response);
                    var obj = _.unserialize(response);
                    initCSV = obj.downloadCSVSettings;
                  //1. initialize the list of sheet type 
                    if (initCSV.sheetTypeList.length > 0) {
                    	var sheetsList=[];
                        for(var i=0; i<initCSV.sheetTypeList.length; i++) {
                        	var sheetTypeId = initCSV.sheetTypeList[i].sheetTypeId;
                        	var caption = downloadcsv.getSheetTypeName(sheetTypeId);
                            sheetsList.push({"id":sheetTypeId, "caption":caption});
                        }
                        downloadcsv.cbiSheetType.setItems(sheetsList);
                        downloadcsv.cbiSheetType.refresh();

                    }
                    
                  //2. initialize the list of category
                    if (initCSV.categoryList.length > 0) {
                    	var categoryList=[];
                    	if (initCSV.categoryList.length > 1){
                    		var caption = linb.Locale[linb.getLang()].app.caption['all'];
                    		categoryList.push({"id":"0", "caption":caption});
                    	}
                        for(var i=0; i<initCSV.categoryList.length; i++) {
                        	var categoryId = initCSV.categoryList[i];
                        	caption = downloadcsv.getCategoryName(categoryId, initCSV.categoryMaster);
                        	categoryList.push({"id":categoryId, "caption":caption});
                        }
                        downloadcsv.cbiCategory.setItems(categoryList);
                        if (initCSV.categoryList.length == 1)
                    		downloadcsv.cbiCategory.setValue(initCSV.categoryList, true);
                        else downloadcsv.cbiCategory.setValue("0", true);
                        downloadcsv.cbiCategory.refresh();
                    }
                    
                  //3. initialize data from the sheet
                    //   3.1. Start date
                    downloadcsv.iptDateFrom.setValue(initCSV.startDate);
                    
                	//   3.2. End date   
                    downloadcsv.iptDateTo.setValue(initCSV.endDate);

                	//   3.3. Sheet Type
                    downloadcsv.cbiSheetType.setValue(initCSV.sheetTypeId, true);

                	//   3.5. Category
                    if(initCSV.selectedCategoryIds != null && initCSV.selectedCategoryIds.length > 0)
                    	downloadcsv.cbiCategory.setValue(initCSV.selectedCategoryIds[0], true);

                	//   3.4. Seller/Buyer list
                    if(initCSV.buyersList != null && initCSV.buyersList.length > 0){
	                    var allItem=[];
	                    allItem.push({"id":"0", "caption":linb.Locale[linb.getLang()].app.caption['all']});
	                    downloadcsv.lstBuyers.insertItems(allItem);
	                    downloadcsv.lstBuyers.insertItems(initCSV.buyersList);
	                    downloadcsv.lstBuyers.refresh();
	                    var selectedBuyerIds;
	                    var items = initCSV.selectedBuyerIds;
	                    for(var i=0;i<items.length;i++){
	                    	selectedBuyerIds = selectedBuyerIds + ";" + items[i];
	                    }
	                    if(initCSV.buyersList.length == items.length){
	                    	selectedBuyerIds = selectedBuyerIds + ";" + "0";
	                    }	                    
	                    downloadcsv.lstBuyers.setUIValue(selectedBuyerIds);
                    }
            },
        	
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
        },
        getBuyerList:function() {
            var obj= [], items, uv;
            uv = this.lstBuyers.getUIValue().split(";");
            if(uv.length > 0) {
	            items = this.lstBuyers.getItems();
	            for(var i=1;i<items.length;i++){
	            	if(_.arr.indexOf(uv,items[i].id)>=0) {
	                    obj.push(items[i].id);
	                }
	            }
            }
            return obj;
        }, 
        isErrorFieldRequired: function() {
            var msg = "", isnull = null, error = 0, dfrom, dto;
            var items = this.lstBuyers.getItems();
            dfrom = this.iptDateFrom.getUIValue();
            dto = this.iptDateTo.getUIValue();
            if (this.iptDateFrom.getUIValue().length < 1) {
                msg = "[" + linb.Locale[linb.getLang()].app.caption['startdate'] + "]\r\n";
                error = 1;
            }
            if (this.iptDateTo.getUIValue().length < 1) {
                msg = msg + "[" + linb.Locale[linb.getLang()].app.caption['enddate'] + "]\r\n";
                error = 1;
            }
            if (this.cbiSheetType.getUIValue().length < 1) {
                msg = msg + "[" + linb.Locale[linb.getLang()].app.caption['sheettype'] + "]\r\n";
                error = 1;
            }
            if (items.length < 1) {
                msg = msg + "[" + linb.Locale[linb.getLang()].app.caption['users'] + "]\r\n";
                error = 1;
            }
            if(items.length > 0) {
            	var obj = this.getBuyerList();
            	if (obj.length < 1) {
            		msg = msg + "[" + linb.Locale[linb.getLang()].app.caption['users'] + "]\r\n";
            		error = 1;
            	}
            }
            
            if (error == 1) {            	
                alert(linb.Locale[linb.getLang()].app.caption['alertcompleterequiredfields'] + "\r\n\r\n" + msg);
                return true;
            }
            
            if(!isDate(dfrom)) {
            	alert(linb.Locale[linb.getLang()].app.caption['alertinvalidcheckdatefrom']);
            	return true;
            }
            
            if(!isDate(dto)) {
            	alert(linb.Locale[linb.getLang()].app.caption['alertinvalidcheckdateto']);
            	return true;
            }
            
            return false;
        }, 
        loadBuyerList: function (dfrom, dto) {
            var empty=[];
            //downloadcsv.lstBuyers.setItems(empty, true);
            downloadcsv.lstBuyers.clearItems();
            var url = "/eON/downloadCSVLoadBuyerList.json";
            var obj = new Object();
            obj.startDate = downloadcsv.iptDateFrom.getUIValue();
            obj.endDate = downloadcsv.iptDateTo.getUIValue();
            linb.Ajax(url,obj,
                function(response){
            		validateResponse(response);
                    var obj = _.unserialize(response);
                    var allItem=[];
                    downloadCSVSettings = obj.downloadCSVSettings;
                    //create All item
                    allItem.push({"id":"0", "caption":linb.Locale[linb.getLang()].app.caption['all']});
                    downloadcsv.lstBuyers.clearItems();

                    downloadcsv.lstBuyers.insertItems(allItem);
                    downloadcsv.lstBuyers.insertItems(downloadCSVSettings.buyersList);
                    downloadcsv.lstBuyers.refresh();
            },
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
        },
 
        _cbiDateFrom_onchange:function (profile, oldValue, newValue) {
        	
        	// Just set the input date
        	var new_date = linb.Date.format(newValue,"yyyy/mm/dd");
        	this.iptDateFrom.setUIValue(new_date);
        	
        	var date_to = this.iptDateTo.getUIValue();
        	if (date_to.length == 0) {
	        	this.iptDateTo.setUIValue(new_date);
	        	this.cbiDateTo.setUIValue(new Date(new_date));
        	}
        	
        }, 
        _iptdatefrom_onChange: function (profile, oldValue, newValue) {
        	
        	var hasError = false;
        	var dateFrom = newValue;
        	var dateTo = this.iptDateTo.getUIValue();
        	
        	if (dateFrom. length > 0 && isDate(dateFrom) == false) {
        		alert(linb.Locale[linb.getLang()].app.caption['alertInvalidDateFr']);
        		hasError = true;
        	} else {
        		if (isDate(dateTo) == false) {
        			hasError = true;
        		}
        	}
        	
        	if (isDate(dateFrom) && isDate(dateTo)) {
        		var dFrom = new Date(dateFrom);
        		var dTo = new Date(dateTo);
        		
        		if (dTo < dFrom) {
        			alert(linb.Locale[linb.getLang()].app.caption['alertdatefromnotgreaterthandateto']);
        			hasError = true;
        		} else {
        			var diff = linb.Date.diff(dFrom, dTo,'d');
        			if (diff > 45) {
        				alert(linb.Locale[linb.getLang()].app.caption['alertcsvlimitdays']);
        				hasError = true;
        			}
        		}
        	}
        	
        	if (hasError == true) {
        		var empty=[];
        		downloadcsv.lstBuyers.setItems(empty, true);
        	} else {
        		downloadcsv.loadBuyerList(dateFrom, dateTo);
        	}
        	
        },
        _cbiDateTo_onchange:function (profile, oldValue, newValue) {
        	
        	// Just set the input date
        	var new_date = linb.Date.format(newValue,"yyyy/mm/dd");
        	this.iptDateTo.setUIValue(new_date);
        	
        }, 
        _iptdateto_onChange: function (profile, oldValue, newValue) {
        	
        	var hasError = false;
        	var dateTo = newValue;
        	var dateFrom = this.iptDateFrom.getUIValue();
        	
        	if (dateTo. length > 0 && isDate(dateTo) == false) {
        		alert(linb.Locale[linb.getLang()].app.caption['alertInvalidDateTo']);
        		hasError = true;
        	} else {
        		if (isDate(dateFrom) == false) {
        			hasError = true;
        		}
        	}
        	
        	if (isDate(dateFrom) && isDate(dateTo)) {
        		var dFrom = new Date(dateFrom);
        		var dTo = new Date(dateTo);
        		
        		if (dTo < dFrom) {
        			alert(linb.Locale[linb.getLang()].app.caption['alertdatefromnotgreaterthandateto']);
        			hasError = true;
        		} else {
        			var diff = linb.Date.diff(dFrom, dTo,'d');
        			if (diff > 45) {
        				alert(linb.Locale[linb.getLang()].app.caption['alertcsvlimitdays']);
        				hasError = true;
        			}
        		}
        	}
        	
        	if (hasError == true) {
        		var empty=[];
        		downloadcsv.lstBuyers.setItems(empty, true);
        	} else {
        		downloadcsv.loadBuyerList(dateFrom, dateTo);
        	}
        	
        },
        _getSheetTypeId: function() {
            var id = this.cbiSheetType.getUIValue().split(";"), sheetId;
            var items = this.cbiSheetType.getItems();
            for(var i=0;i<items.length;i++){
                if(_.arr.indexOf(id,items[i].id)>=0) {
                    sheetId = items[i].id;
                }
            }
            return sheetId;
        }, 
        _getCategoryId: function() {
            var categoryId = [];
            if (this.cbiCategory.getUIValue() == "0") {
                var items = this.cbiCategory.getItems();
                for(var i=1;i<items.length;i++){
                    categoryId.push(items[i].id);
                }
            }else{
                var id = this.cbiCategory.getUIValue().split(";");
                var items = this.cbiCategory.getItems();
                for(var i=0;i<items.length;i++){
                    if(_.arr.indexOf(id,items[i].id)>=0) {
                        categoryId.push(items[i].id);
                    }
                }
            }
            return categoryId;
        }, 
        checkValidDates: function() {
        	var dfrom, dfromlen, dto, dto, currdate, today=new Date();
        	dfrom = linb.Date.format(this.iptDateFrom.getUIValue(), "yyyy/mm/dd");
            dto = linb.Date.format(this.iptDateTo.getUIValue(), "yyyy/mm/dd");
            if (dfrom > dto) {
            	alert(linb.Locale[linb.getLang()].app.caption['alertdatefromnotgreaterthandateto']);
                this.iptDateFrom.setUIValue(dfrom);
                this.iptDateTo.setUIValue(dfrom);
                return false;
            }
            return true;
        },
        _lstBuyers_onitemselected:function (profile, item, src) {
        	var ids = "";
        	var items = this.lstBuyers.getItems();
            var value = profile.boxing().getUIValue();
            for(var i=0;i<items.length;i++){
                ids = ids + ";" + items[i].id;
            }
            if(item.id=='0'){
                if(_.arr.indexOf(value.split(';'),item.id)!=-1){
                    profile.boxing().setUIValue(ids);
                }else{
                    profile.boxing().setUIValue('');
                }
            }else {
            	if(_.arr.indexOf(value.split(';'),'0')!=-1){
                	ids = ids.replace(';0', '');
                	ids = ids.replace(';'+item.id, '');
                    profile.boxing().setUIValue(ids);
                }else{
                	value = "0" + value;
                	var all = ids;
                	ids = ids.replaceAll(';', '');
                	value = value.replaceAll(';', '');
                	if(ids == value)
                	profile.boxing().setUIValue(all);
                }
            }
        },
        _cbiDateFrom_onrender:function (profile) {
            profile.boxing().setValue(linb.Date.add(new Date, 'd',1));
            profile.getSubNode('TODAY').css('display','none');
            profile.getSubNode('BODY').first(2).css('display','none');
            profile.getSubNode('W',true).css('display','none');
            linb(profile.getSubNode('H',true).get(0)).css('display','none');
        },
        _cbiDateTo_onrender:function (profile) {
        	// Deleted by lele:
        	// Remove the defaulting of End date
//        	  profile.boxing().setValue(linb.Date.add(new Date, 'd',1));
//            profile.getSubNode('TODAY').css('display','none');
//            profile.getSubNode('BODY').first(2).css('display','none');
//            profile.getSubNode('W',true).css('display','none');
        	// End deletion:
//            linb(profile.getSubNode('H',true).get(0)).css('display','none');
        },
        getSheetTypeName:function (sheetTypeId){
        	switch (sheetTypeId) {
	        	case "10000": var caption = linb.Locale[linb.getLang()].app.caption['buyerordersheet'];break; 
	            case "10001": var caption = linb.Locale[linb.getLang()].app.caption['ordersheet'];break; 
	            case "10003": var caption = linb.Locale[linb.getLang()].app.caption['allocationsheet'];break; 
	            case "10004": var caption = linb.Locale[linb.getLang()].app.caption['receivedsheet'];break; 
	            case "10005": var caption = linb.Locale[linb.getLang()].app.caption['billingsheet'];break; 
	            case "10006": var caption = linb.Locale[linb.getLang()].app.caption['buyerbillingsheet'];break; 
	            case "10020": var caption = linb.Locale[linb.getLang()].app.caption['akadensheet'];break;
	        	case "10007": var caption = linb.Locale[linb.getLang()].app.caption['buyerordersheet'];break; 
	            case "10009": var caption = linb.Locale[linb.getLang()].app.caption['ordersheet'];break; 
	            case "10010": var caption = linb.Locale[linb.getLang()].app.caption['allocationsheet'];break; 
	            case "10012": var caption = linb.Locale[linb.getLang()].app.caption['receivedsheet'];break; 
	            case "10011": var caption = linb.Locale[linb.getLang()].app.caption['billingsheet'];break; 
	            case "10013": var caption = linb.Locale[linb.getLang()].app.caption['buyerbillingsheet'];break; 
	            case "10021": var caption = linb.Locale[linb.getLang()].app.caption['akadensheet'];break; 
			}
        	return caption;
        },
        getCategoryName : function (categoryId, categoryMaster){
        	if(linb.getLang() == "en"){
        		return categoryMaster[categoryId].description;
        	}else{
        		return categoryMaster[categoryId].tabName;
        	}
        }
    }
});