/*
 	Copyright Freshremix Asia Software Corporation
	date		name		version	changes
	==============================================
	20120726	jovsab		v11		Redmine 286 - get calendar date from the server
	20120913	lele		chrome	Redmine 880 - Chrome compatibility
 * */
var g_LangKey;
var g_ServerDate;
var newValue;
var oldValue;

Class('App.filter', 'linb.Com', {
    autoDestroy:true, 
    Instance: {
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
                .host(host,"panelFilter")
                .setDock("none")
                .setLeft(90)
                .setTop(100)
                .setWidth(910)
                .setHeight(375)
                .setZIndex(1)
                .setCaption("<center><b>$app.caption.filterPage</b></center>")
                .setMinBtn(false)
                .setMaxBtn(false)
                .setResizer(false)
                .setPinBtn(false)
                .setCloseBtn(false)
            );
            
            host.panelFilter.append((new linb.UI.Group)
                .host(host,"groupDealingPattern")
                .setLeft(381)
                .setTop(0)
                .setWidth(510)
                .setHeight(290)
                .setCaption("")
                .setToggleBtn(false)
            );
            
            host.groupDealingPattern.append((new linb.UI.Label)
                .host(host,"labelBuyer")
                .setLeft(0)
                .setTop(20)
                .setWidth(80)
                .setCaption("$app.caption.buyers :")
            );
            
            host.groupDealingPattern.append((new linb.UI.List)
                .host(host,"lstBuyersCompany")
                .setLeft(80)
                .setTop(20)
                .setWidth(200)
                .setHeight(220)
                .setSelMode("multi")
                .setValue("")
                .setDirtyMark(false)
                .onItemSelected("_lstbuyerscompany_onitemselected")
                .onRender("_lstbuyerscompanyid_onrender")
            );
            
            host.groupDealingPattern.append((new linb.UI.Label)
                .host(host,"labelAvailableCompanies")
                .setLeft(80)
                .setTop(0)
                .setWidth(200)
                .setCaption("$app.caption.filterAvailableCompanies")
                .setHAlign("left")
            );

            host.groupDealingPattern.append((new linb.UI.Label)
                .host(host,"labelAvailableIDs")
                .setLeft(290)
                .setTop(0)
                .setWidth(200)
                .setCaption("$app.caption.filterAvailableUsers")
                .setHAlign("left")
            );
            
            host.groupDealingPattern.append((new linb.UI.List)
                .host(host,"lstBuyersCompanyId")
                .setLeft(290)
                .setTop(20)
                .setWidth(200)
                .setHeight(220)
                .setSelMode("multi")
                .setValue("")
                .setDirtyMark(false)
                .onChange("_categoriesTabOnRender")
                .onItemSelected("_lstbuyerscompanyid_onitemselected")
            );
            
            host.groupDealingPattern.append((new linb.UI.Label)
                .host(host,"labelMark")
                .setLeft(290)
                .setTop(250)
                .setWidth(200)
                .setCaption("$app.caption.filterSellerLockMark")
                .setHAlign("left")
            );
            
            host.panelFilter.append((new linb.UI.Button)
                .host(host,"btnSearchMode")
                .setLeft(690)
                .setTop(305)
                .setWidth(80)
                .setCaption("$app.caption.search")
                .onClick("_btnsearchmode_onclick")
            );
            
            host.panelFilter.append((new linb.UI.Button)
                .host(host,"btnCancelMode")
                .setLeft(780)
                .setTop(305)
                .setWidth(80)
                .setCaption("$app.caption.cancel")
                .onClick("_btncancelmode_onclick")
            );
            
            host.panelFilter.append((new linb.UI.Group)
                .host(host,"groupDate")
                .setLeft(10)
                .setTop(1)
                .setWidth(365)
                .setHeight(290)
                .setCaption("$app.caption.deliveryDate")
                .setToggleBtn(false)
            );
            
            host.groupDate.append((new linb.UI.DatePicker)
                .host(host,"datepickerSearchDate")
                .setLeft(10)
                .setTop(50)
                .setCloseBtn(false)
                .setCustomStyle({"TODAY":"display:none"}) 
                /* DELETION START 20120726 JOVSAB - Redmine 286*/
//                .setValue(linb.Date.add(new Date, 'd',1)) 
//                .onRender("_datepickerSearchDate_onrender")
                /* DELETION END 20120726 */
                .afterUIValueSet("_datepickersearchdate_aftervalueupdated")
                .beforeClickEffect("_datepickersearchdate_beforeclickeffect")
            );
            
            host.groupDate.append((new linb.UI.RadioBox)
                .host(host,"rbDateOption")
                .setItems([{"id":"D", "caption":"$app.caption.dateRange"}, {"id":"W", "caption":"$app.caption.weekly"}])
                .setLeft(0)
                .setTop(10)
                .setWidth(355)
                .setHeight(30)
                .setValue("D")
                .setDirtyMark(false)
                .onItemSelected("_rbdateoption_onitemselected")
                .setCustomStyle({"ITEMS":"overflow:visible", "KEY":"background:transparent;"})
                .setVisibility("hidden")
            );
            
            host.groupDate.append((new linb.UI.Input)
                .host(host,"inptDateFrom")
                .setLeft(230)
                .setTop(110)
                .setWidth(100)
                .setDirtyMark(false)
                /* DELETION START 20120726 JOVSAB - Redmine 286*/
//                .setValue(sellerFilter.formatDate(linb.Date.add(new Date, 'd',1)))
                /* DELETION END 20120726 */
                .onChange("_inptDateFrom_onChange")
                .onFocus("_inptDateFrom_onFocus")
            );

            host.groupDate.append((new linb.UI.Input)
                .host(host,"inptDateTo")
                .setLeft(230)
                .setTop(168)
                .setWidth(100)
                .setDirtyMark(false)
                .onChange("_inptDateTo_onChange")
                .onFocus("_inptDateTo_onFocus")
            );

            host.groupDate.append((new linb.UI.Label)
                .host(host,"lblDateFrom")
                .setLeft(230)
                .setTop(90)
                .setWidth(100)
                .setCaption("$app.caption.filterFrom")
                .setHAlign("left")
            );

            host.groupDate.append((new linb.UI.Label)
                .host(host,"lblDateTo")
                .setLeft(230)
                .setTop(150)
                .setWidth(100)
                .setCaption("$app.caption.filterTo")
                .setHAlign("left")
            );

            host.groupDate.append((new linb.UI.RadioBox)
                .host(host,"rdDateRange")
                .setItems([{"id":"1", "caption":"<br/>&nbsp;"}, {"id":"2", "caption":""}])
                .setLeft(330)
                .setTop(110)
                .setWidth(25)
                .setHeight(80)
                .setValue("1")
                .setDirtyMark(false)
                .setCustomStyle({"KEY":"background:transparent;"})
                // ENHANCEMENT 20120913: Lele - Redmine 880
                .setVisibility("hidden")
            );

            host.panelFilter.append((new linb.UI.Pane)
                .host(host,"hiddenPane")
                .setWidth(100)
                .setHeight(100)
                .setVisibility(false)
            );

            host.hiddenPane.append((new linb.UI.Label)
                .host(host,"hiddenSheetTypeId")
                .setCaption("")
            );

            host.hiddenPane.append((new linb.UI.Label)
                .host(host,"hiddenDealingPatternId")
                .setCaption("")
            );

            host.hiddenPane.append((new linb.UI.Label)
                .host(host,"hiddenFilterMode")
                .setCaption("")
            );

            return children;
            // ]]code created by jsLinb UI Builder
        },
        customAppend:function(parent,subId,left,top){
            (parent||linb('body')).append(sellerFilter.panelFilter, subId);
            sellerFilter.panelFilter.setDisplay('');

            var self=this, prop = sellerFilter.properties;

		    if(prop.fromRegion)
		        self.panelFilter.setFromRegion(prop.fromRegion);

		    if(!self.panelFilter.get(0).renderId)
		        self.panelFilter.render();
		    	self.panelFilter.show(self.parent, true);
        },
        events: {
            "onReady" : "_onready", "onRender":"_onRender", "onDestroy":"_ondestroy"
        },
        _ondestroy:function (com) {

        },
        iniResource: function(com, threadid) {
        },
        iniExComs: function(com, hreadid) {
        },
        _onRender:function(page, threadid){
            sellerFilter = page;
            sellerFilter.hiddenPane.hide();
            /* ENHANCEMENT START 20120726 JOVSAB - Calling the function of getting the server date */
            sellerFilter.getDateServerValue();
            /* ENHANCEMENT END 20120726 */
            /* DELETION START 20120726 JOVSAB - Redmine 286*/
        	// sellerFilter.populateBuyerCompany();
        	/* DELETION END 20120726 */
        },
        _onready: function() {
            sellerFilter = this;
            g_LangKey = linb.getLang();
        },
        _btncancelmode_onclick:function (profile, e, src, value) {
//        	sellerFilter.panelFilter.setDisplay('none');
        	//sellerFilter.clearLists();
        	//sellerFilter.populateBuyerCompany();
        	sellerFilter.destroy();
        },
        _btnsearchmode_onclick:function (profile, e, src, value) {
            sellerFilter.searchBuyerCompany();
        },
        _rbdateoption_onitemselected:function (profile, item, src) {
            var tmp = sellerFilter.rdDateRange.getItems();
            if (sellerFilter.rbDateOption.getUIValue() == 'W') {
                var d = sellerFilter.datepickerSearchDate.getUIValue();
                var dateFrom = sellerFilter.formatDate(d);
                sellerFilter.inptDateFrom.setUIValue(dateFrom);

                d = linb.Date.add(new Date(dateFrom),'d',6);
                sellerFilter.inptDateTo.setUIValue(sellerFilter.formatDate(d));
                sellerFilter.inptDateTo.setDisabled(true);
                tmp[1].disabled = true;

                sellerFilter.populateBuyerCompany();
            } else {
                sellerFilter.inptDateTo.setDisabled(false);
                sellerFilter.inptDateTo.setUIValue("");
                tmp[1].disabled = false;
            }
            sellerFilter.rdDateRange.setItems(tmp);
        },
//        responseCheck : function (jsonResponse) {
//            var obj = _.unserialize(jsonResponse);
//            if (obj.fail == 'true') {
//                alert("responseCheck", obj.globals, "OK");
//            } else if (obj.isSessionExpired != null && obj.isSessionExpired == 'true') {
//                window.location = "../../login/jsp/login.jsp";
//            }
//        },
        allToBuyerIds: function (linbComp, newValue) {
        	var isNotAll = sellerFilter.hiddenFilterMode.getCaption();
            var tmp = "";
            var buyIds = newValue.split(';');
            //alert("buyIds:[" + _.serialize(buyIds) + "]");
        	var items = linbComp.getItems();
        	//alert("items:[" + _.serialize(items) + "]");
        	for (var i=(isNotAll==1?0:1); i<items.length; i++) {
        		for ( var j = 0; j < buyIds.length; j++ ) {
        			if (items[i].id == buyIds[j]) {
            			//add to sorted Ids
        				tmp = tmp + items[i].id + ";";
        				break;
        			}
        		}
            }
            return tmp;
        },
        setBuyerCombo: function (comboObj) {
            var list = [];
            var idx = 0;
            var obj = new Object();
            var values = comboObj.getUIValue().split(';');
            var items = comboObj.getItems();

            //start:jr
            //this will add the option "All"
            //when values length is > 1
            //if(values.length > 1){
                list[idx++] = items[0];
            //}
            //end:jr
            if (values[0] == "0") {
                for (var j=1; j<items.length; j++) {
                    thisObj = items[j];
                        list[idx++] = thisObj;
                }
            }
            else {
                for (var i=0; i<values.length; i++) {
                    var thisId = values[i];

                    for (var j=1; j<items.length; j++) {
                        thisObj = items[j];
                        if (thisId == thisObj.id) {
                            list[idx++] = thisObj;
                            break;
                        }
                    }
                }
            }

            return list;
        },
        formatDate: function (dateObj) {
            var _year = linb.Date.get(dateObj,"y");
            var _month = pad((linb.Date.get(dateObj,"m")+1),2);
            var _day = pad(linb.Date.get(dateObj,"d"),2);
            return  _year + "/" + _month + "/" + _day;
        },
        _inptDateTo_onFocus: function (profile) {
        	// ENHANCEMENT START 20120913: Lele - Redmine 880
        	sellerFilter.inptDateFrom.setCustomStyle({'INPUT':'background-color:white'});
        	sellerFilter.inptDateTo.setCustomStyle({'INPUT':'background-color:#e6e273'});
        	//sellerFilter.inptDateFrom.refresh();
        	//sellerFilter.inptDateTo.refresh();
        	// ENHANCEMENT END 20120913:
            sellerFilter.rdDateRange.setValue(2);
            sellerFilter.rdDateRange.refresh();
        }, 
        _lstbuyerscompany_onitemselected:function (profile, item, src) {
            sellerFilter.clearHighlightsInDatePicker();
        	if (sellerFilter.hiddenFilterMode.getCaption()==1)
        		sellerFilter.lstBuyersCompanyId.setSelMode("single");
            var ids = "";
            var items = sellerFilter.lstBuyersCompany.getItems();
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
            
            var tmp = sellerFilter.lstBuyersCompany.getUIValue();
            var objDateFrom = new Date(sellerFilter.inptDateFrom.getUIValue());
            var objDateTo = new Date(sellerFilter.inptDateTo.getUIValue());

            if (tmp != "" && tmp != ";0") {
            	
	            var url = "/eON/filter/getUser.json?dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo);
	            url = url + "&dealingPatternId="+sellerFilter.hiddenDealingPatternId.getCaption();
	            url = url + "&companies="+tmp;
	            url = url + "&sheetTypeId=" + sellerFilter.hiddenSheetTypeId.getCaption();
	            var id = Math.random() * url.length;
	            sellerFilter.disableControls();
	            linb.Ajax(
	                url+"&id="+id,
	                null,
	                function (response) {
	                    //sellerFilter.responseCheck(response);
	                    validateResponse(response);
	                    var o = _.unserialize(response);
	                    var items = o.response;
	                    sellerFilter.lstBuyersCompanyId.setItems(items, true);
	                    if (sellerFilter.hiddenFilterMode.getCaption()==1)
	                    	sellerFilter.lstBuyersCompanyId.removeItems(['0']);
	                    sellerFilter.lstBuyersCompanyId.refresh();
	                    sellerFilter.enableControls();
	                },
	                function(msg) {
	                        linb.message("onFail: " + msg);
	                    }, null, {
	                        method : 'GET',
	                        retry : 0
	                    }
	            ).start();
            } else {
            	sellerFilter.lstBuyersCompanyId.setItems(null, true);
            }
        },  _inptDateFrom_onFocus: function (profile) {
        	// ENHANCEMENT START 20120913: Lele - Redmine 880
        	sellerFilter.inptDateFrom.setCustomStyle({'INPUT':'background-color:#e6e273'});
        	sellerFilter.inptDateTo.setCustomStyle({'INPUT':'background-color:white'});
        	//sellerFilter.inptDateFrom.refresh();
        	//sellerFilter.inptDateTo.refresh();
        	// ENHANCEMENT END 20120913:
            sellerFilter.rdDateRange.setValue(1);
            sellerFilter.rdDateRange.refresh();
        },  _inptDateTo_onChange: function (profile) {
            var hasError = false;
            var newdateresult = sellerFilter.inptDateTo.getUIValue();
            var option = sellerFilter.rdDateRange.getUIValue();
            var dateFrom = sellerFilter.inptDateFrom.getUIValue();

            if (dateFrom == "" || isDate(dateFrom) == false) {
            	// alert("Date From: should not be empty");
            	alert(linb.Locale[g_LangKey].app.caption['alertInvalidDateFr']);
                hasError = true;
            }
            if (newdateresult != "" && isDate(newdateresult) == false) {
            	// alert("Date From: should not be empty");
            	alert(linb.Locale[g_LangKey].app.caption['alertInvalidDateTo']);
                hasError = true;
            }

            dateFrom = new Date(dateFrom);
            var dateTo = new Date(newdateresult);
            
            if (dateTo < dateFrom) {
            	// alert("Date To: should be later than Date From:");
                hasError = true;
            }else {

                var diff = linb.Date.diff(dateFrom, dateTo,'d');

                if (diff > 6) {
                	// alert("Error date range");
                    hasError = true;
                } else {
                	sellerFilter.inptDateTo.setUIValue(newdateresult);
                }
            }

            if(hasError == true) {
                var empty=[];
                sellerFilter.lstBuyersCompany.setItems(empty, true);
                sellerFilter.lstBuyersCompanyId.setItems(empty, true);
                return;
            }else{
                sellerFilter.populateBuyerCompany();
            }
        },  _inptDateFrom_onChange: function (profile) {
            var hasError = false;
            var newdateresult = sellerFilter.inptDateTo.getUIValue();
            var option = sellerFilter.rdDateRange.getUIValue();
            var dateFrom = sellerFilter.inptDateFrom.getUIValue();
            
            if (dateFrom == "" || isDate(dateFrom) == false) {
            	// alert("Date From: should not be empty");
            	alert(linb.Locale[g_LangKey].app.caption['alertInvalidDateFr']);
                hasError = true;
            }
            if (newdateresult != "" && isDate(newdateresult) == false) {
            	// alert("Date From: should not be empty");
            	alert(linb.Locale[g_LangKey].app.caption['alertInvalidDateTo']);
                hasError = true;
            }

            dateFrom = new Date(dateFrom);
            var dateTo = new Date(newdateresult);
            
            if (dateTo < dateFrom) {
            	// alert("Date To: should be later than Date From:");
                hasError = true;
            }else {
                var diff = linb.Date.diff(dateFrom, dateTo,'d');

                if (diff > 6) {
                	// alert("Error date range");
                    hasError = true;
                } else {
                	sellerFilter.inptDateTo.setUIValue(newdateresult);
                }
            }

            if(hasError == true) {
                var empty=[];
                sellerFilter.lstBuyersCompany.setItems(empty, true);
                sellerFilter.lstBuyersCompanyId.setItems(empty, true);
                return;
            }else{
                 sellerFilter.populateBuyerCompany();
             }

        },  _categoriesTabOnRender: function() {
            var url = "/eON/getCategoryTabs.json";
            var objArr =[];
            sellerFilter.disableControls();
            linb.Ajax(
                url + "?obj="+objArr,
                null,
                function (response) {
                	validateResponse(response);
                var categoryObject = _.unserialize(response);
                var obj = _.unserialize(response);
                categoryIndex = obj.index;
                g_categoryId = categoryIndex;
                categoryTabs =[];
                for(var i=0; i<categoryObject.categories.length; i++){
                    var image =obj.categories[i].image.replaceAll("%","/");
                    categoryTabs.push({"id":obj.categories[i].id,
                        "caption":obj.categories[i].caption,"image":image});
                }
                sellerFilter.enableControls();
                //orderSheet.categoriesTab.setItems(categoryTabs);
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
        _lstbuyerscompanyid_onitemselected:function (profile, item, src) {
            var ids = "";
            var items = sellerFilter.lstBuyersCompanyId.getItems();
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
        	var month = sellerFilter.datepickerSearchDate._nodes[0].$month;
        	var year = sellerFilter.datepickerSearchDate._nodes[0].$year;
            sellerFilter._onChangeSellerAction(month, year, true);
            sellerFilter._setStartEndDeliveryDate();
        }, 
        _datepickerSearchDate_onrender:function (profile) {
            profile.getSubNode('TODAY').css('display','none');
            profile.getSubNode('BODY').first(2).css('display','none');
            profile.getSubNode('W',true).css('display','none');
            linb(profile.getSubNode('H',true).get(0)).css('display','none');
        },
        /* ENHANCEMENT START 20120726: JOVSAB - This code is for getting the server date and return to calendar */
        getDateServerValue: function () {
        	var url = "/eON/getServerDate.json";  
            linb.Ajax(
                url,
                null,
                function (response) {
                	var obj = _.unserialize(response); 
                	sellerFilter.datepickerSearchDate.setValue(linb.Date.parse(obj.response));
                	sellerFilter.inptDateFrom.setValue(obj.response);
                	// Call method to populate buyer company
                	sellerFilter.populateBuyerCompany();
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
        /* ENHANCEMENT END 20120726: */
        clearLists: function() {
        	sellerFilter.lstBuyersCompany.setItems([]);
            sellerFilter.lstBuyersCompanyId.setItems([]);
        }, populateBuyerCompany : function () {
            // query for company
            var objDateFrom = new Date(sellerFilter.inptDateFrom.getUIValue());
            var objDateTo = new Date(sellerFilter.inptDateTo.getUIValue());
            var url = "/eON/filter/getCompany.json?dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo);
            url = url + "&dealingPatternId="+sellerFilter.hiddenDealingPatternId.getCaption();
            url = url + "&userCompanyId=" + mainSPA._excom1.lblCompanyId.getCaption();
            var id = Math.random() * url.length;
            sellerFilter.disableControls();
            linb.Ajax(
                url + "&id=" + id,
                null,
                function (response) {
                    //sellerFilter.responseCheck(response);
                    validateResponse(response);
                    var o = _.unserialize(response);
                    var items = o.response;
                    sellerFilter.lstBuyersCompany.setItems(items, true);
                    sellerFilter.lstBuyersCompany.refresh();
                    sellerFilter.enableControls();
                },
                function(msg) {
                        linb.message("失敗： " + msg);
                    }, null, {
                        method : 'GET',
                        retry : 0
                    }
            ).start();

            // reset value of user level combo box
            sellerFilter.lstBuyersCompanyId.setItems(null, true);
            sellerFilter.lstBuyersCompanyId.refresh();
        },
        searchBuyerCompany: function() {
        	//orderSheet.paneSigmaGrid.setHtml("");
            var hasErrors = false;

            var option = sellerFilter.rdDateRange.getUIValue();
            var dateFromValue = sellerFilter.inptDateFrom.getUIValue();
            var dateFrom = new Date(dateFromValue);
            var dateToValue = sellerFilter.inptDateTo.getUIValue();
            var dateTo = new Date(dateToValue);
            
            if (dateFromValue == "") {
                //alert("Date From: should not be empty");
            	alert(linb.Locale[g_LangKey].app.caption['alertEmptyDateFr']);
                hasError = true;
                return;
            }
            else if (isDate(dateFromValue) == false) {
            	//alert("Date From: invalid date");
            	alert(linb.Locale[g_LangKey].app.caption['alertInvalidDateFr']);
                hasError = true;
                return;
            }
            
            if (dateToValue != "" && isDate(dateToValue) == false) {
            	//alert("Date To: invalid date");
            	alert(linb.Locale[g_LangKey].app.caption['alertInvalidDateTo']);
                hasError = true;
                return;
            }
            
            if (dateTo < dateFrom) {
                //alert("Date To: should be later than Date From:");
            	alert(linb.Locale[g_LangKey].app.caption['alertLaterDateFr']);
                hasError = true;
                return;
            }else {
                var diff = linb.Date.diff(dateFrom, dateTo,'d');

                if (diff > 6) {
                    hasError = true;
                    //alert("Error date range");
            		alert(linb.Locale[g_LangKey].app.caption['alertDateRangeError']);
                    return;
                } else {
                	sellerFilter.inptDateTo.setUIValue(dateToValue);
                }
            }
            
            if (dateFrom == 'NaN') {
                hasErrors = true;
                alert(linb.Locale[g_LangKey].app.caption['errorValidDate']);
                return;
            }

            if (sellerFilter.lstBuyersCompanyId.getUIValue() == '') {
                hasErrors = true;
                alert(linb.Locale[g_LangKey].app.caption['errorNoSelectedBuyers']);
                return;
            }

            if (hasErrors == false) {
                // create parameter object
                var objDateFrom = new Date(sellerFilter.inptDateFrom.getUIValue());
                
                // ENHANCEMENT START 20120913: Lele - Redmine 880
                if (!sellerFilter.inptDateTo.getUIValue()) {
                	sellerFilter.inptDateTo.setValue(sellerFilter.inptDateFrom.getUIValue());
                }
                // ENHANCEMENT END 20120913:
                
                var objDateTo = new Date(sellerFilter.inptDateTo.getUIValue());
                var obj = new Object();
                obj.startDate = toYYYYMMDD(objDateFrom);
                obj.endDate = toYYYYMMDD(objDateTo);
                obj.selectedBuyerCompany = sellerFilter.allToBuyerIds(sellerFilter.lstBuyersCompany, sellerFilter.lstBuyersCompany.getUIValue());
                obj.selectedBuyerID = sellerFilter.allToBuyerIds(sellerFilter.lstBuyersCompanyId, sellerFilter.lstBuyersCompanyId.getUIValue());
                obj.selectedDate = toYYYYMMDD(objDateFrom);
                obj.categoryId = categoryIndex; // default to fruits
                obj.sheetTypeId = sellerFilter.hiddenSheetTypeId.getCaption();
                
                // Remove filter marks
                var items = sellerFilter.lstBuyersCompanyId.getItems();
                var newArray = [];
                var i = 0;
                for (i=0; i<items.length; i++) {
                	newArray.push({"id" : items[i].id, "caption" : items[i].noMarkCaption});
                }
                sellerFilter.lstBuyersCompanyId.setItems(newArray,true);
                obj.buyerCombo = sellerFilter.setBuyerCombo(sellerFilter.lstBuyersCompanyId);
                // end of removal
                
                sellerFilter.fireEvent('onSearch',[obj]);
//                sellerFilter.destroy();
//                sellerFilter.panelFilter.setDisplay('none');
                sellerFilter.clearLists();      
                sellerFilter.destroy();
            }
        },
        _lstbuyerscompanyid_onrender: function(profile) {
        	var ns = this, uictrl = profile.boxing();
            var behaviors = profile.box.getBehavior(),
                evOnItem = behaviors.ITEM;
            evOnItem.beforeKeydown=function(profile, e, src){
                if(linb.Event.getKey(e)[0]=='enter'){
                	sellerFilter.searchBuyerCompany();
                    //ns.callthisfunction(profile.getItemByDom(src));
                    return false;
                }
            };
            uictrl.setCustomBehavior("ITEM", evOnItem);
        },
        
        disableControls : function () {
        	sellerFilter.lstBuyersCompany.setDisabled(true);
        	sellerFilter.lstBuyersCompanyId.setDisabled(true);
        	sellerFilter.btnSearchMode.setDisabled(true);
        	sellerFilter.btnCancelMode.setDisabled(true);
        	sellerFilter.datepickerSearchDate.setDisabled(true);
        	sellerFilter.inptDateFrom.setDisabled(true);
        	sellerFilter.inptDateTo.setDisabled(true);
        	sellerFilter.rdDateRange.setDisabled(true);
        	sellerFilter.rbDateOption.setDisabled(true);
        },
        
        enableControls : function () {
        	sellerFilter.lstBuyersCompany.setDisabled(false);
        	sellerFilter.lstBuyersCompanyId.setDisabled(false);
        	sellerFilter.btnSearchMode.setDisabled(false);
        	sellerFilter.btnCancelMode.setDisabled(false);
        	sellerFilter.datepickerSearchDate.setDisabled(false);
        	sellerFilter.inptDateFrom.setDisabled(false);
        	sellerFilter.inptDateTo.setDisabled(false);
        	sellerFilter.rdDateRange.setDisabled(false);
        	sellerFilter.rbDateOption.setDisabled(false);
        },
        _onChangeSellerAction: function(month, year, isSameMonthYear) {
        	sellerFilter.clearHighlightsInDatePicker();

            var url = "/eON/getSellerPublishedDate.json";
            var objDateFrom = new Date();
            var objDateTo = new Date();

            objDateFrom.setDate(objDateFrom.getDate() + 1);
            objDateTo.setDate(objDateTo.getDate() + 1);

            var selectedBuyerIds = sellerFilter.lstBuyersCompanyId.getUIValue().split(';');
            var loggedInSellerId = header.lbluseridinfo.getCaption();
	        strArr = "?dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo)
	        +"&selectedBuyers="+selectedBuyerIds+"&selectedSellers="+loggedInSellerId;

	        linb.Ajax(
	            url + strArr,
	            null,
	            function (response) {
	                validateResponse(response);
	                var obj = _.unserialize(response);
	                var orderWithStatus = obj.orderRecordsStatus;
	                var orderRecords = eval(orderWithStatus);

	                var firstDayOfTheMonthIndex = 0;

	                for(var i=0;i<=41;i++){
	                	if(sellerFilter.datepickerSearchDate.getSubNode('TD', i).html() == "1"){
	                		firstDayOfTheMonthIndex = i;
	                		break;
	                	}

	                }

	                if (orderRecords!=null){
	                    for(var i=0; i<orderRecords.length;i++){
	                    	var status = orderRecords[i].status
	                        var yyyymmddDateStr=orderRecords[i].orderDate;
	                        var yyyy = yyyymmddDateStr.substr(0,4);
	                        var mm = yyyymmddDateStr.substr(4,2);
	                        var dd = yyyymmddDateStr.substr(6,2);
	                        var today = new Date();
	                        var dateHighlighted;
	                        if(mm == month){
	                        	dateHighlighted = dd  - 1 + firstDayOfTheMonthIndex;
	                        } else if (mm == month + 1 && (month >= today.getMonth() + 1)){
	                        	var lastDay = new Date(yyyy, month, 0).getDate();
	                        	dateHighlighted = parseInt(dd) + lastDay - 1 + firstDayOfTheMonthIndex;
	                        	if(dateHighlighted > 41){
	                        		dateHighlighted = 41; //Max Date in one calendar. Starts with 0.
	                        	}
	                        }
	                        if(isSameMonthYear && (mm == month || mm == month + 1) && yyyy == year && status == "P"){
		                        if(dateHighlighted != undefined){
		                        	if(sellerFilter.datepickerSearchDate.getSubNode('TD', dateHighlighted).attr('class').
		                        			indexOf("TD_CC-checked") == -1){
		                        		sellerFilter.datepickerSearchDate.getSubNode('TD', dateHighlighted).css('background-color', '#80f442');
		                        	}
		                        }
	                        }
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

        _setStartEndDeliveryDate: function() {
            var url = "/eON/getSellerPublishedDate.json";
            var objDateFrom = new Date();
            var objDateTo = new Date();
            objDateFrom.setDate(objDateFrom.getDate() + 1);
            objDateTo.setDate(objDateTo.getDate() + 1);
            var selectedBuyerIds = sellerFilter.lstBuyersCompanyId.getUIValue().split(';');
            var loggedInSellerId = header.lbluseridinfo.getCaption();
	        strArr = "?dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo)
	        +"&selectedBuyers="+selectedBuyerIds+"&selectedSellers="+loggedInSellerId;

	        linb.Ajax(
	            url + strArr,
	            null,
	            function (response) {
	                    validateResponse(response);
	                    var obj = _.unserialize(response);
	                    var orderWithStatus = obj.orderRecordsStatus;
	                    var orderRecords = eval(orderWithStatus);

	                    var yyyymmddDateStr="";
	                    var yyyy = "";
                        var mm = "";
                        var dd = "";

	                    for(var key in orderRecords){
	                    	if(orderRecords[key].status == "P"){
	                    		yyyymmddDateStr = orderRecords[key].orderDate;
	                    	}
	                    }
	                    if(yyyymmddDateStr != ""){
	                    	var yyyy = yyyymmddDateStr.substr(0,4);
	                        var mm = yyyymmddDateStr.substr(4,2);
	                        var dd = yyyymmddDateStr.substr(6,2);
	                        yyyymmddDateStr = yyyy + "/" + mm + "/" + dd
	                    } else {
	                    	yyyymmddDateStr = sellerFilter.inptDateFrom.getUIValue();
	                    }

	                    sellerFilter.inptDateTo.setValue(yyyymmddDateStr);
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

        clearHighlightsInDatePicker : function (){
        	for(var i=0;i<=41;i++){
				if (sellerFilter.datepickerSearchDate.getSubNode('TD', i).attr('class').
	                            			indexOf("td-free") == -1){ //Weekdays
							sellerFilter.datepickerSearchDate.getSubNode('TD', i).attr('style' , 'background-color : #EFF8FF , color : #000000');
				} else {
							sellerFilter.datepickerSearchDate.getSubNode('TD', i).attr('style' , 'background-color : #F9F7D1, color : #000000');
				}

				if(sellerFilter.datepickerSearchDate.getSubNode('TD', i).attr('class').
	                            			indexOf("TD_CC-checked") != -1){
							sellerFilter.datepickerSearchDate.getSubNode('TD', i).attr('style' , 'background-color : #316AC5');
				}

        	}
        },

        _changeCalendarEventListener:function (oldValue, newValue, eventToggle) {
        	var month = newValue.getMonth() + 1;
        	var year = newValue.getFullYear();
			if(eventToggle == 1){
				month = oldValue.getMonth() + 1;
			} else if(eventToggle == 0) {
				month = oldValue.getMonth() - 1 ;
			}
			sellerFilter._onChangeSellerAction(month, year, true);
        },

        _datepickersearchdate_beforeclickeffect:function(p, i, e, s, t){
        	//console.log("beforeclickeffect");
        	var previousYear = "linb-datepicker-pre2 linb-datepicker-pre2-mouseover";
        	var nextYear = "linb-datepicker-next2 linb-datepicker-next2-mouseover";

        	var previousMonth = "linb-datepicker-pre linb-datepicker-pre-mouseover";
        	var nextMonth = "linb-datepicker-next linb-datepicker-next-mouseover";

        	var oldValue;
        	var newValue;

        	if(e.currentTarget.className == previousYear ||
        			e.currentTarget.className == nextYear){
        		sellerFilter.clearHighlightsInDatePicker();
        	} else if (e.currentTarget.className == nextMonth){
        		sellerFilter.clearHighlightsInDatePicker();
				for(var key in p.$day._nodes){
					if( p.$day._nodes[key] == p.$selnode._nodes[0] ){
						newValue = p.$daymap[key];
						var fullDate = new Date(new Date().getFullYear(), 0, 1);
						fullDate.setMonth(p.$month);
						oldValue = fullDate;
					}
				}

				/*This code was added because the function executes before the UI change which makes the highlight
				 * incorrect. Delay was added to make sure that the UI change first before the execution of the function.
				 */
				sellerFilter.newValue = newValue;
				sellerFilter.oldValue = oldValue;
				setTimeout(function(){
					sellerFilter._changeCalendarEventListener(sellerFilter.oldValue, sellerFilter.newValue, 1);
				},350)

        	} else if(e.currentTarget.className == previousMonth){
        		sellerFilter.clearHighlightsInDatePicker();
        		for(var key in p.$day._nodes){
					if( p.$day._nodes[key] == p.$selnode._nodes[0] ){
						newValue = p.$daymap[key];
						var fullDate = new Date(new Date().getFullYear(), 0, 1);
						fullDate.setMonth(p.$month);
						oldValue = fullDate;
					}
				}
        		/*This code was added because the function executes before the UI change which makes the highlight
				 * incorrect. Delay was added to make sure that the UI change first before the execution of the function.
				 */
        		sellerFilter.newValue = newValue;
				sellerFilter.oldValue = oldValue;
				setTimeout(function(){
					sellerFilter._changeCalendarEventListener(sellerFilter.oldValue, sellerFilter.newValue, 0);
				},350)
        	}

        	return true;
        },

        _datepickersearchdate_aftervalueupdated:function (profile, oldValue, newValue) {
        	var hasError = false;
            var d = newValue;
            var newdateresult = sellerFilter.formatDate(d);
            var option = sellerFilter.rdDateRange.getUIValue();
            if (sellerFilter.rbDateOption.getUIValue() == 'W') {
	        	var to_d = linb.Date.add(d,'d',6);
	        	sellerFilter.inptDateFrom.setUIValue(newdateresult);
	        	sellerFilter.inptDateTo.setUIValue(sellerFilter.formatDate(to_d));
            } else {
	            if (option == "1") {
	            	var dateToValue = sellerFilter.inptDateTo.getUIValue();
	            	var dateFrom = new Date(newdateresult);
	            	var dateTo = new Date(sellerFilter.inptDateTo.getUIValue());

	            	if (dateToValue != "" && isDate(dateToValue) == false) {
						//alert("Date To: invalid date");
						//hasError = true;
                		sellerFilter.inptDateFrom.setUIValue(newdateresult);
                		sellerFilter.inptDateTo.setUIValue("");
                		return;
					}

	            	if (dateTo < dateFrom) {
	            		//alert("Date To: should be later than Date From:");
	            		alert(linb.Locale[g_LangKey].app.caption['alertLaterDateFr']);
	            		hasError = true;
	            	}
	            	else {
                    	var diff = linb.Date.diff(dateFrom, dateTo,'d');

                    	if (diff > 6) {
                            //alert("Error date range");
                    		alert(linb.Locale[g_LangKey].app.caption['alertDateRangeError']);
                            hasError = true;
                        }
                    	else {
                    		sellerFilter.inptDateFrom.setUIValue(newdateresult);
                    	}
                    }
	            }
	            else {
	            	var dateFrom = sellerFilter.inptDateFrom.getUIValue();

	            	if (dateFrom == "") {
                    	//alert("Date From: should not be empty");
	            		alert(linb.Locale[g_LangKey].app.caption['alertEmptyDateFr']);
						hasError = true;
						return;
					}
                    else if (isDate(dateFrom) == false) {
                    	//alert("Date From: invalid date");
                    	alert(linb.Locale[g_LangKey].app.caption['alertInvalidDateFr']);
						hasError = true;
						return;
                    }

	            	dateFrom = new Date(dateFrom);

	            	var dateTo = new Date(newdateresult);
	            	if (dateTo < dateFrom) {
	            		//alert("Date To: should be later than Date From:");
	            		alert(linb.Locale[g_LangKey].app.caption['alertLaterDateFr']);
	            		hasError = true;
	            	}
	            	else {
	            		var diff = linb.Date.diff(dateFrom, dateTo,'d');

	                    if (diff > 6) {
	                    	//alert("Error date range");
                    		alert(linb.Locale[g_LangKey].app.caption['alertDateRangeError']);
	                    	hasError = true;
	                    }
	                    else {
	                    	sellerFilter.inptDateTo.setUIValue(newdateresult);
	                    }
	            	}
	            }
            }
            var month = newValue.getMonth() + 1;
            var year = newValue.getFullYear();
            sellerFilter._onChangeSellerAction(month, year, true);
        }
    }
});

