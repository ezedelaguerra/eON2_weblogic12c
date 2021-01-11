
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
                .setHeight(540)
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
                .setHeight(440)
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
                .setType("datepicker")
                .onChange("cbiDateFrom_onchange")
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
                .setType("datepicker")
                .onChange("_cbidateto_onchange")
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
                .onHotKeypress("_cbisheettype_onhotkeypress")
            );

            host.grpDownloadCSV.append((new linb.UI.List)
                .host(host,"lstSellers")
                .setLeft(100)
                .setTop(230)
                .setWidth(160)
                .setHeight(120)
                .setSelMode("multi")
                .setValue("")
                .onItemSelected("_lstsellers_onitemselected")
                .onChange("_lstsellers_onchange")
            );

            host.grpDownloadCSV.append((new linb.UI.Label)
                .host(host,"lblUsersList")
                .setLeft(10)
                .setTop(100)
                .setWidth(90)
                .setCaption("$app.caption.buyers :")
            );

            host.grpDownloadCSV.append((new linb.UI.Label)
                .host(host,"lblCategory")
                .setLeft(10)
                .setTop(360)
                .setWidth(90)
                .setCaption("$app.caption.csvcategory :")
            );

            host.grpDownloadCSV.append((new linb.UI.ComboInput)
                .host(host,"cbiCategory")
                .setLeft(100)
                .setTop(360)
                .setWidth(160)
                .setValue(linb.Locale[linb.getLang()].app.caption['all'])
                .onHotKeypress("_cbicategory_onhotkeypress")
            );

            host.grpDownloadCSV.append((new linb.UI.CheckBox)
                .host(host,"chkBoxQty")
                .setLeft(90)
                .setTop(390)
                .setWidth(160)
                .setCaption("$app.caption.csvhasquantity")
                .onChecked("_chkboxqty_onchecked")
            );

            host.grpDownloadCSV.append((new linb.UI.Input)
                .host(host,"iptDateFrom")
                .setLeft(100)
                .setTop(10)
                .setWidth(145)
                .onHotKeypress("_iptdatefrom_onhotkeypress")
            );

            host.grpDownloadCSV.append((new linb.UI.Input)
                .host(host,"iptDateTo")
                .setLeft(100)
                .setTop(40)
                .setWidth(145)
                .onHotKeypress("_iptdateto_onhotkeypress")
            );

            host.grpDownloadCSV.append((new linb.UI.List)
                .host(host,"lstBuyers")
                .setLeft(100)
                .setTop(100)
                .setWidth(160)
                .setHeight(120)
                .setSelMode("multi")
                .setValue("")
                .onItemSelected("_lstbuyers_onitemselected")
                .onChange("_lstbuyers_onchange")
            );

            host.grpDownloadCSV.append((new linb.UI.Label)
                .host(host,"label147")
                .setLeft(10)
                .setTop(230)
                .setWidth(90)
                .setCaption("$app.caption.sellers :")
            );

            host.dlgDownloadCSV.append((new linb.UI.Button)
                .host(host,"btnSearch")
                .setLeft(120)
                .setTop(460)
                .setWidth(70)
                .setCaption("$app.caption.csvdownload")
                .onClick("_btnsearch_onclick")
            );

            host.dlgDownloadCSV.append((new linb.UI.Button)
                .host(host,"btnCancel")
                .setLeft(200)
                .setTop(460)
                .setWidth(70)
                .setCaption("$app.caption.cancel")
                .onClick("_btncancel_onclick")
            );

            host.dlgDownloadCSV.append((new linb.UI.Label)
                .host(host,"lblHasQty")
                .setLeft(10)
                .setTop(320)
                .setWidth(20)
                .setVisibility("hidden")
                .setCaption("")
            );

            host.dlgDownloadCSV.append((new linb.UI.Label)
                .host(host,"lblUserType")
                .setLeft(50)
                .setTop(320)
                .setWidth(30)
                .setVisibility("hidden")
                .setCaption("")
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
            downloadcsv_admin_buyer = page;
        },
        _onready: function() {
            downloadcsv_admin_buyer = this;
            this.loadCSVUISettings();
            this.loadCSVUISheets();
        },
        _btncancel_onclick:function (profile, e, src, value) {
            downloadcsv_admin_buyer.destroy();
        },
        _btnsearch_onclick:function (profile, e, src, value) {
            if (!downloadcsv_admin_buyer.isErrorFieldRequired()) {
                if (this.checkValidDates()) {
                    var url = "/eON/downloadCSV.json";
                    var obj = new Object();
                    obj.startDate = downloadcsv_admin_buyer.iptDateFrom.getUIValue();
                    obj.endDate = downloadcsv_admin_buyer.iptDateTo.getUIValue();
                    obj.sheetTypeId = downloadcsv_admin_buyer._getSheetTypeId();
                    obj.usersList = downloadcsv_admin_buyer.getSellersList(); 
                    obj.buyersList = downloadcsv_admin_buyer.getBuyersList();
                    obj.categoryId = downloadcsv_admin_buyer._getCategoryId();
                    obj.hasQty = downloadcsv_admin_buyer.lblHasQty.getCaption();
                    submitTheForm(url, "_json", _.serialize(obj), linb.Locale[linb.getLang()].app.caption['confirmDownload']);
                }
            }
        },
        _chkboxqty_onchecked:function (profile, e, value) {
            this.lblHasQty.setCaption(e);
        },
        getUserDealingPattern: function() {
        },
        getSellersList:function() {
            var obj= [], items, uv;
            uv = this.lstSellers.getUIValue().split(";");
            if(uv.length > 0) {
                items = this.lstSellers.getItems();
                for(var i=1;i<items.length;i++){
                    if(_.arr.indexOf(uv,items[i].id)>=0) {
                        obj.push(items[i].id);
                    }
                }
            }
            return obj;
        },
        getBuyersList:function() {
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
            var sellerItems = this.lstSellers.getItems();
            var buyerItems = this.lstBuyers.getItems();
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
            if (sellerItems.length < 1) {
            	msg = msg + "[" + linb.Locale[linb.getLang()].app.caption['sellers'] + "]\r\n";
                error = 1;
            }
            if(sellerItems.length > 0) {
                var obj = this.getSellersList();
                if (obj.length < 1) {
                	msg = msg + "[" + linb.Locale[linb.getLang()].app.caption['sellers'] + "]\r\n";
                    error = 1;
                }
            }            
            if (buyerItems.length < 1) {
            	msg = msg + "[" + linb.Locale[linb.getLang()].app.caption['buyers'] + "]\r\n";
                error = 1;
            }
            if(buyerItems.length > 0) {
                var obj = this.getBuyersList();
                if (obj.length < 1) {
                	msg = msg + "[" + linb.Locale[linb.getLang()].app.caption['buyers'] + "]\r\n";
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
        loadSellerParameters: function (dfrom, dto) {
            var url = "/eON/csvUsersList.json";
            var obj = new Object();
            obj.startDate = dfrom;
            obj.endDate = dto;
            linb.Ajax(url,obj,
                function(response){
            		validateResponse(response);
                    var usersList=[], buyerList=[];
                    var obj = _.unserialize(response);
                    
                    // Set sellers list
                    usersList.push({"id":"0", "caption":"ALL"});
                    for(var i=0; i<obj.buyersList.length; i++) {
                    	usersList.push({"id":obj.buyersList[i].userId, "caption":obj.buyersList[i].name});
                    }
                    downloadcsv_admin_buyer.lstBuyers.setItems(usersList);
                    downloadcsv_admin_buyer.lstBuyers.refresh();
                    
                    // Set buyers list
//                    buyerList.push({"id":"0", "caption":"ALL"});
//                    for(var i=0; i<obj.buyersList.length; i++) {
//                    	buyerList.push({"id":obj.buyersList[i].userId, "caption":obj.buyersList[i].name});
//                    }
//                    downloadcsv_admin_buyer.lstBuyers.setItems(buyerList);
//                    downloadcsv_admin_buyer.lstBuyers.refresh();
            },
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
        },
        loadCSVUISettings: function () {
            var today=new Date(), currdate;
            currdate = linb.Date.format(today,"yyyy/mm/dd");
            var url = "/eON/downloadcsvParams.json";
            linb.Ajax(url,null,
                function(response){
            		validateResponse(response);
                    var obj = _.unserialize(response);
                    if (obj.success == 1) {
                        var usrLists=[], byrList=[];
                        downloadcsv_admin_buyer.iptDateFrom.setValue(obj.startdate, true);
                        downloadcsv_admin_buyer.iptDateTo.setValue(obj.enddate, true);
                        var categoryName = downloadcsv_admin_buyer.getCategoryName(obj.categoryName);
                        downloadcsv_admin_buyer.cbiCategory.setValue(categoryName, true);
                        var defaultsheet = downloadcsv_admin_buyer.getSheetTypeName(obj.defaultsheet);
                        downloadcsv_admin_buyer.cbiSheetType.setValue(defaultsheet, true);
                        
                        byrList.push({"id":"0", "caption":"ALL"});
                        for(var i=0; i<obj.buyersList.length; i++) {
                        	byrList.push({"id":obj.buyersList[i].userId, "caption":obj.buyersList[i].name});
                        }
                        
                        usrLists.push({"id":"0", "caption":"ALL"});
                        for(var i=0; i<obj.usersList.length; i++) {
                            usrLists.push({"id":obj.usersList[i].userId, "caption":obj.usersList[i].name});
                        }
                        
                        downloadcsv_admin_buyer.lstBuyers.setItems(byrList, true);
                        downloadcsv_admin_buyer.lstSellers.setItems(usrLists, true);
                        downloadcsv_admin_buyer.lstBuyers.setValue(obj.selectedBuyerIds, true);                        
                        downloadcsv_admin_buyer.lstSellers.setUIValue(obj.selectedIds);
                    } else {
                        downloadcsv_admin_buyer.iptDateFrom.setValue(currdate);
                    }
            },
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
        },
        loadCSVUISheets: function () {
        	var date = new Date();
        	var obj = new Object();
            var url = "/eON/downloadcsvSheetsList.json";
            obj.startdate = linb.Date.format(date, "yyyy/mm/dd");
            linb.Ajax(url,obj,
                function(response){
            		validateResponse(response);
                    var obj = _.unserialize(response);
                    if (obj.sheetsList.length > 0) {
                        var sheetsList=[];
                        for(var i=0; i<obj.sheetsList.length; i++) {
                        	var sheetTypeId = obj.sheetsList[i].sheetTypeId;
                        	var caption = downloadcsv_admin_buyer.getSheetTypeName(sheetTypeId);
                            sheetsList.push({"id":obj.sheetsList[i].sheetTypeId, "caption":caption});
                        }
                        downloadcsv_admin_buyer.cbiSheetType.setItems(sheetsList);
                        downloadcsv_admin_buyer.cbiSheetType.refresh();
                    }

                    if (obj.categoryList.length > 0) {
                        var categoryList=[];
                        if (obj.categoryList.length > 1)
                    		var caption = linb.Locale[linb.getLang()].app.caption['all'];
                            categoryList.push({"id":"0", "caption":caption});
                        for(var i=0; i<obj.categoryList.length; i++) {
                        	var categoryId = obj.categoryList[i].categoryId;
                        	caption = downloadcsv_admin_buyer.getCategoryName(categoryId);
                            categoryList.push({"id":obj.categoryList[i].categoryId, "caption":caption});
                        }
                        downloadcsv_admin_buyer.cbiCategory.setItems(categoryList);
                        downloadcsv_admin_buyer.cbiCategory.refresh();
                    }
            },
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
        },
        cbiDateFrom_onchange:function (profile, oldValue, newValue) {
            var dfrom, dfromlen, dto, dto, currdate, today=new Date();
            dfrom = linb.Date.format(newValue,"yyyy/mm/dd");
            currdate = linb.Date.format(today,"yyyy/mm/dd");
            this.iptDateFrom.setUIValue(dfrom);
            dfromlen = this.iptDateFrom.getUIValue().length;
            dto = this.iptDateTo.getUIValue();
            if (dto.length < 1) {
                this.iptDateTo.setUIValue(dfrom);
            }
            if (dfrom > this.iptDateTo.getUIValue()) {
            	alert(linb.Locale[linb.getLang()].app.caption['alertdatefromnotgreaterthandateto']);
                this.iptDateFrom.setUIValue(currdate);
                this.iptDateTo.setUIValue(currdate);
                return;
            }
            this.lstBuyers.setItems([]);
            downloadcsv_admin_buyer.loadSellerParameters(dfrom, this.iptDateTo.getUIValue());
        },
        _cbidateto_onchange:function (profile, oldValue, newValue) {
            var dto, dateFrom, currdate, today=new Date();
            dto = linb.Date.format(newValue,"yyyy/mm/dd");
            currdate = linb.Date.format(today,"yyyy/mm/dd");
            this.iptDateTo.setUIValue(dto);
            dateFrom = this.iptDateFrom.getUIValue();
            if (dateFrom.length < 1) {
            	alert(linb.Locale[linb.getLang()].app.caption['alertdatefromshouldnotempty']);
                return;
            }
            if (dateFrom > dto) {
            	alert(linb.Locale[linb.getLang()].app.caption['alertdatefromnotgreaterthandateto']);
                this.iptDateFrom.setUIValue(currdate);
                this.iptDateTo.setUIValue(currdate);
                return;
            }
            this.lstBuyers.setItems([]);
            downloadcsv_admin_buyer.loadSellerParameters(dateFrom, dto);
        },
        _getSheetTypeId: function() {
            var uv = this.cbiSheetType.getUIValue().split(";"), sheetId;
            var items = this.cbiSheetType.getItems();
            for(var i=0;i<items.length;i++){
                if(_.arr.indexOf(uv,items[i].caption)>=0) {
                    sheetId = items[i].id;
                }
            }
            return sheetId;
        },
        _getCategoryId: function() {
            var categoryId = [];
            if (this.cbiCategory.getUIValue().toLowerCase() == linb.Locale[linb.getLang()].app.caption['all']) {
                var items = this.cbiCategory.getItems();
                for(var i=1;i<items.length;i++){
                    categoryId.push(items[i].id);
                }
            }else{
                var uv = this.cbiCategory.getUIValue().split(";");
                var items = this.cbiCategory.getItems();
                for(var i=0;i<items.length;i++){
                    if(_.arr.indexOf(uv,items[i].caption)>=0) {
                        categoryId.push(items[i].id);
                    }
                }
            }
            return categoryId;
        },
        checkValidDates: function() {
            var dfrom, dfromlen, dto, dto, currdate, today=new Date();
            dfrom = this.iptDateFrom.getUIValue();
            dto = this.iptDateTo.getUIValue();
            if (dfrom > dto) {
            	alert(linb.Locale[linb.getLang()].app.caption['alertdatefromnotgreaterthandateto']);
                this.iptDateFrom.setUIValue(dfrom);
                this.iptDateTo.setUIValue(dfrom);
                return false;
            }
            return true;
        },
        _lstsellers_onitemselected:function (profile, item, src) {
            var ids = "";
            var items = this.lstSellers.getItems();
            for(var i=0;i<items.length;i++){
                ids = ids + ";" + items[i].id;
            }
            if(item.id=='0'){
                var value = profile.boxing().getUIValue();
                if(_.arr.indexOf(value.split(';'),item.id)!=-1){
                    profile.boxing().setUIValue(ids);
                }else{
                    profile.boxing().setUIValue('');
                }
            }
        },
        _lstsellers_onchange:function (profile, oldValue, newValue) {        	
        }, 
        _lstbuyers_onchange:function (profile, oldValue, newValue) {
        	var url = "/eON/csvBuyerAdminSellers.json";
            var obj = new Object();
            obj.startDate = downloadcsv_admin_buyer.iptDateFrom.getUIValue();
            obj.endDate = downloadcsv_admin_buyer.iptDateTo.getUIValue();
            obj.buyerIds = downloadcsv_admin_buyer.allToUserIds(this.lstBuyers, newValue);
            linb.Ajax(url,obj,
                function(response){
	            	validateResponse(response);
	            	var o = _.unserialize(response);
	            	var items = o.response;
	            	downloadcsv_admin_buyer.lstSellers.setItems(items, true);
	            	downloadcsv_admin_buyer.lstSellers.refresh();
            },
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
        },
        allToUserIds : function (linbComp, newValue) {
        	var tmp = "";
        	if (newValue == "0" || newValue.startsWith("0;")) {
        		var items = linbComp.getItems();
        		for (var i=1; i<items.length; i++) {
        			tmp = tmp + items[i].id + ";";
        		}
        	} else {
        		tmp = newValue;
        	}
        	return tmp;
        },
        _lstbuyers_onitemselected:function (profile, item, src) {
            var ids = "";
            var items = this.lstBuyers.getItems();
            for(var i=0;i<items.length;i++){
                ids = ids + ";" + items[i].id;
            }
            if(item.id=='0'){
                var value = profile.boxing().getUIValue();
                if(_.arr.indexOf(value.split(';'),item.id)!=-1){
                    profile.boxing().setUIValue(ids);
                }else{
                    profile.boxing().setUIValue('');
                }
            }
        },
        _iptdateto_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            return false;
        },
        _iptdatefrom_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            return false;
        },
        _cbisheettype_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            return false;
        },
        _cbicategory_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            return false;
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
        getCategoryName:function (categoryId){
        	switch (categoryId) {
	            case "1": caption = linb.Locale[linb.getLang()].app.caption['fruits'];break; 
	            case "2": caption = linb.Locale[linb.getLang()].app.caption['vegetables'];break; 
	            case "3": caption = linb.Locale[linb.getLang()].app.caption['fish'];break; 
        	}
        	return caption;
        }
    }
});