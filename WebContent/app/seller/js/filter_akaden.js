var g_LangKey;

Class('App.filter_akaden', 'linb.Com', {
    autoDestroy:true, 
    Instance: {
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
                .host(host,"panelFilter")
                .setLeft(73)
                .setTop(90)
                .setWidth(917)
                .setHeight(370)
                .setCaption("<center><b>$app.caption.filterPage</b></center>")
                .setMovable(false)
                .setMinBtn(false)
                .setMaxBtn(false)
                .setResizer(false)
                .setPinBtn(false)
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
                .setDirtyMark(false)
                .setLeft(80)
                .setTop(20)
                .setWidth(200)
                .setHeight(220)
                .setSelMode("multi")
                .setValue("")
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
                .setDirtyMark(false)
                .setLeft(290)
                .setTop(20)
                .setWidth(200)
                .setHeight(220)
                .setSelMode("single")
                .setValue("")
                .onChange("_categoriesTabOnRender")
                .onItemSelected("_lstbuyerscompanyid_onitemselected")
            );
            
            host.panelFilter.append((new linb.UI.Button)
                .host(host,"btnSearchMode")
                .setLeft(700)
                .setTop(305)
                .setWidth(80)
                .setCaption("$app.caption.search")
                .onClick("_btnsearchmode_onclick")
            );
            
            host.panelFilter.append((new linb.UI.Button)
                .host(host,"btnCancelMode")
                .setLeft(790)
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
                .setValue(linb.Date.add(new Date, 'd',1)) 
                .afterUIValueSet("_datepickersearchdate_aftervalueupdated")
                .setCustomStyle({"TODAY":"display:none"})
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
                .setValue(this.formatDate(linb.Date.add(new Date, 'd',1)))
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
                .setCustomStyle({"KEY":"background:#aad2fa;#888"})
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
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        customAppend:function(){
            var self=this, prop = this.properties;
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
            akadenFilter = page;
            akadenFilter.hiddenPane.hide();
            akadenFilter.populateBuyerCompany();
        }, 
        _onready: function() {
            akadenFilter = this;
            g_LangKey = linb.getLang();
        }, 
        _btncancelmode_onclick:function (profile, e, src, value) {
            akadenFilter.clearLists();
            akadenFilter.panelFilter.destroy();
        }, 
        _btnsearchmode_onclick:function (profile, e, src, value) {
        	akadenFilter.searchBuyerCompany();            
        }, 
        _datepickersearchdate_aftervalueupdated:function (profile, oldValue, newValue) {
            var hasError = false;
            var d = newValue;
            var newdateresult = akadenFilter.formatDate(d);
            var option = this.rdDateRange.getUIValue();
            if (this.rbDateOption.getUIValue() == 'W') {
                var to_d = linb.Date.add(d,'d',6);
                this.inptDateFrom.setUIValue(newdateresult);
                this.inptDateTo.setUIValue(akadenFilter.formatDate(to_d));
            } else {
                if (option == "1") {
                	var dateToValue = this.inptDateTo.getUIValue();
                    var dateFrom = new Date(newdateresult);
                    var dateTo = new Date(this.inptDateTo.getUIValue());
                    
                    if (dateToValue != "" && isDate(dateToValue) == false) {
						//alert("Date To: invalid date");
						//hasError = true;
                		this.inptDateFrom.setUIValue(newdateresult);
                		this.inptDateTo.setUIValue("");
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
                    		this.inptDateFrom.setUIValue(newdateresult);
                    	}	
                    }
                }
                else {
                	var dateFrom = this.inptDateFrom.getUIValue();

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

                    dateFrom = new Date(dateFrom)    ;

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
                        } else {
                        this.inptDateTo.setUIValue(newdateresult);
                }
                    }
                }
            }

            if (hasError == false) {
                // query for company
                var objDateFrom = new Date(akadenFilter.inptDateFrom.getUIValue());
                var objDateTo = new Date(akadenFilter.inptDateTo.getUIValue());
                var url = "/eON/filter/getCompany.json?dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo);
                url = url + "&dealingPatternId="+this.hiddenDealingPatternId.getCaption();
                url = url + "&userCompanyId=" + mainSPA._excom1.lblCompanyId.getCaption();
                var id = Math.random() * url.length;
                akadenFilter.disableControls();
                linb.Ajax(
                    url + "&id=" + id,
                    null,
                    function (response) {
                        validateResponse(response);
                        var o = _.unserialize(response);
                        var items = o.response;
                        akadenFilter.lstBuyersCompany.setItems(items, true);
                        akadenFilter.lstBuyersCompany.refresh();
                        akadenFilter.enableControls();
                    },
                    function(msg) {
                            linb.message("失敗： " + msg);
                        }, null, {
                            method : 'GET',
                            retry : 0
                        }
                ).start();

                // reset value of user level combo box
                this.lstBuyersCompanyId.setItems(null, true);
                this.lstBuyersCompanyId.refresh();
            }
        }, 
        _rbdateoption_onitemselected:function (profile, item, src) {
        	var tmp = this.rdDateRange.getItems();
            if (this.rbDateOption.getUIValue() == 'W') {
                var d = this.datepickerSearchDate.getUIValue();
                var dateFrom = akadenFilter.formatDate(d);
                this.inptDateFrom.setUIValue(dateFrom);

                d = linb.Date.add(new Date(dateFrom),'d',6);
                this.inptDateTo.setUIValue(akadenFilter.formatDate(d));
                this.inptDateTo.setDisabled(true);
                tmp[1].disabled = true;

                akadenFilter.populateBuyerCompany();
            } else {
                this.inptDateTo.setDisabled(false);
                this.inptDateTo.setUIValue("");
                tmp[1].disabled = false;
            }
            this.rdDateRange.setItems(tmp);
        }, 
        allToBuyerIds: function (linbComp, newValue) {
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
        setBuyerCombo: function (comboObj) {
            var list = [];
            var idx = 0;
            var obj = new Object();
            var values = comboObj.getUIValue().split(';');
            var items = comboObj.getItems();
            list[idx++] = items[0];
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
            this.rdDateRange.setValue(2);
            this.rdDateRange.refresh();
        }, 
        _lstbuyerscompany_onitemselected:function (profile, item, src) {
            var ids = "";
            var items = this.lstBuyersCompany.getItems();
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
            
            var tmp = akadenFilter.lstBuyersCompany.getUIValue();
            if (tmp != "") {
	            var objDateFrom = new Date(akadenFilter.inptDateFrom.getUIValue());
	            var objDateTo = new Date(akadenFilter.inptDateTo.getUIValue());
	
	            var url = "/eON/filter/getUser.json?dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo);
	            url = url + "&dealingPatternId="+this.hiddenDealingPatternId.getCaption();
	            url = url + "&companies="+tmp;
	            var id = Math.random() * url.length;
	            akadenFilter.disableControls();
	            linb.Ajax(
	                url+"&id="+id,
	                null,
	                function (response) {
	                    validateResponse(response);
	                    var o = _.unserialize(response);
	                    var items = o.response;
	                    akadenFilter.lstBuyersCompanyId.setItems(items, true);
	                    akadenFilter.lstBuyersCompanyId.removeItems(['0']);
	                    akadenFilter.lstBuyersCompanyId.refresh();
	                    akadenFilter.enableControls();
	                },
	                function(msg) {
	                        linb.message("onFail: " + msg);
	                    }, null, {
	                        method : 'GET',
	                        retry : 0
	                    }
	            ).start();
            } else {
            	akadenFilter.lstBuyersCompanyId.setItems(null, true);
                akadenFilter.lstBuyersCompanyId.refresh();
            }
        },    
        _inptDateFrom_onFocus: function (profile) {
            this.rdDateRange.setValue(1);
            this.rdDateRange.refresh();
        },    
        _inptDateTo_onChange: function (profile) {
            var hasError = false;
            var newdateresult = this.inptDateTo.getUIValue();
            var option = this.rdDateRange.getUIValue();
            var dateFrom = this.inptDateFrom.getUIValue();

            if (dateFrom == "" || isDate(dateFrom) == false) {
            	alert(linb.Locale[g_LangKey].app.caption['alertInvalidDateFr']);
                hasError = true;
            }
            if (newdateresult != "" && isDate(newdateresult) == false) {
            	alert(linb.Locale[g_LangKey].app.caption['alertInvalidDateTo']);
                hasError = true;
            }

            dateFrom = new Date(dateFrom);
            var dateTo = new Date(newdateresult);
            
            if (dateTo < dateFrom) {
                hasError = true;
            }else {

                var diff = linb.Date.diff(dateFrom, dateTo,'d');

                if (diff > 6) {
                    hasError = true;
                } else {
                    this.inptDateTo.setUIValue(newdateresult);
                }
            }

            if(hasError == true) {
                var empty=[];
                akadenFilter.lstBuyersCompany.setItems(empty, true);
                akadenFilter.lstBuyersCompanyId.setItems(empty, true);
                return;
            }else{
                 // query for company
                var objDateFrom = new Date(akadenFilter.inptDateFrom.getUIValue());
                var objDateTo = new Date(akadenFilter.inptDateTo.getUIValue());
                 var url = "/eON/filter/getCompany.json?dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo);
                 url = url + "&dealingPatternId="+this.hiddenDealingPatternId.getCaption();
                 url = url + "&userCompanyId=" + mainSPA._excom1.lblCompanyId.getCaption();
                 var id = Math.random() * url.length;
                 akadenFilter.disableControls();
                 linb.Ajax(
                     url + "&id=" + id,
                     null,
                     function (response) {
                         validateResponse(response);
                         var o = _.unserialize(response);
                         var items = o.response;
                         akadenFilter.lstBuyersCompany.setItems(items, true);
                         akadenFilter.lstBuyersCompany.refresh();
                         akadenFilter.enableControls();
                     },
                     function(msg) {
                             linb.message("失敗： " + msg);
                         }, null, {
                             method : 'GET',
                             retry : 0
                         }
                 ).start();

                 // reset value of user level combo box
                 this.lstBuyersCompanyId.setItems(null, true);
                 this.lstBuyersCompanyId.refresh();
             }

        },    
        _inptDateFrom_onChange: function (profile) {
            var hasError = false;
            var newdateresult = this.inptDateTo.getUIValue();
            var option = this.rdDateRange.getUIValue();
            var dateFrom = this.inptDateFrom.getUIValue();

            if (dateFrom == "" || isDate(dateFrom) == false) {
            	alert(linb.Locale[g_LangKey].app.caption['alertInvalidDateFr']);
                hasError = true;
            }
            if (newdateresult != "" && isDate(newdateresult) == false) {
            	alert(linb.Locale[g_LangKey].app.caption['alertInvalidDateTo']);}

            dateFrom = new Date(dateFrom);
            var dateTo = new Date(newdateresult);
            
            if (dateTo < dateFrom) {
                hasError = true;
            }else {
                var diff = linb.Date.diff(dateFrom, dateTo,'d');

                if (diff > 6) {
                    hasError = true;
                } else {
                    this.inptDateTo.setUIValue(newdateresult);
                }
            }

            if(hasError == true) {
                var empty=[];
                akadenFilter.lstBuyersCompany.setItems(empty, true);
                akadenFilter.lstBuyersCompanyId.setItems(empty, true);
                return;
            }else{
                 // query for company
                var objDateFrom = new Date(akadenFilter.inptDateFrom.getUIValue());
                var objDateTo = new Date(akadenFilter.inptDateTo.getUIValue());
                 var url = "/eON/filter/getCompany.json?dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo);
                 url = url + "&dealingPatternId="+this.hiddenDealingPatternId.getCaption();
                 url = url + "&userCompanyId=" + mainSPA._excom1.lblCompanyId.getCaption();
                 var id = Math.random() * url.length;
                 akadenFilter.disableControls();
                 linb.Ajax(
                     url + "&id=" + id,
                     null,
                     function (response) {
                         validateResponse(response);
                         var o = _.unserialize(response);
                         var items = o.response;
                         akadenFilter.lstBuyersCompany.setItems(items, true);
                         akadenFilter.lstBuyersCompany.refresh();
                         akadenFilter.enableControls();
                     },
                     function(msg) {
                             linb.message("失敗： " + msg);
                         }, null, {
                             method : 'GET',
                             retry : 0
                         }
                 ).start();

                 // reset value of user level combo box
                 this.lstBuyersCompanyId.setItems(null, true);
                 this.lstBuyersCompanyId.refresh();
             }

        },    
        _categoriesTabOnRender: function() {
            var url = "/eON/getCategoryTabs.json";
            var objArr =[];
            linb.Ajax(
                url + "?obj="+objArr,
                null,
                function (response) {
                	validateResponse(response);
	                var categoryObject = _.unserialize(response);
	                var obj = _.unserialize(response);
	                categoryIndex = obj.index;
	                categoryTabs =[];
	                for(var i=0; i<categoryObject.categories.length; i++){
	                    var image =obj.categories[i].image.replaceAll("%","/");
	                    categoryTabs.push({"id":obj.categories[i].id,
	                        "caption":obj.categories[i].caption,"image":image});
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
        _lstbuyerscompanyid_onitemselected:function (profile, item, src) {
            var ids = "";
            var items = this.lstBuyersCompanyId.getItems();
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
        _datepickerSearchDate_onrender:function (profile) {
            profile.getSubNode('TODAY').css('display','none');
            profile.getSubNode('BODY').first(2).css('display','none');
            profile.getSubNode('W',true).css('display','none');
            linb(profile.getSubNode('H',true).get(0)).css('display','none');
        },
        populateBuyerCompany : function () {
            // query for company
            var objDateFrom = new Date(akadenFilter.inptDateFrom.getUIValue());
            var objDateTo = new Date(akadenFilter.inptDateTo.getUIValue());
            var url = "/eON/filter/getCompany.json?dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo);
            url = url + "&dealingPatternId="+this.hiddenDealingPatternId.getCaption();
            url = url + "&userCompanyId=" + mainSPA._excom1.lblCompanyId.getCaption();
            var id = Math.random() * url.length;
            linb.Ajax(
                url + "&id=" + id,
                null,
                function (response) {
                    validateResponse(response);
                    var o = _.unserialize(response);
                    var items = o.response;
                    akadenFilter.lstBuyersCompany.setItems(items, true);
                    akadenFilter.lstBuyersCompany.refresh();
                },
                function(msg) {
                        linb.message("失敗： " + msg);
                    }, null, {
                        method : 'GET',
                        retry : 0
                    }
            ).start();

            // reset value of user level combo box
            akadenFilter.lstBuyersCompanyId.setItems(null, true);
            akadenFilter.lstBuyersCompanyId.refresh();
        },
        clearLists: function() {
        	akadenFilter.lstBuyersCompany.setItems([]);
            akadenFilter.lstBuyersCompanyId.setItems([]);
            akadenFilter.datepickerSearchDate.setValue(linb.Date.add(new Date, 'd',1));
            akadenFilter.inptDateFrom.setValue(this.formatDate(linb.Date.add(new Date, 'd',1)), true);
            akadenFilter.inptDateTo.setValue("", true);
            akadenFilter.rbDateOption.setValue("D",true);
        },
        searchBuyerCompany: function() {
        	var hasErrors = false;
            var option = this.rdDateRange.getUIValue();
            var dateFromValue = this.inptDateFrom.getUIValue();
            var dateFrom = new Date(dateFromValue);
            var dateToValue = this.inptDateTo.getUIValue();
            var dateTo = new Date(dateToValue);

            if (dateFromValue == "") {
                alert("Date From: should not be empty");
                hasError = true;
                return;
            }
            else if (isDate(dateFromValue) == false) {
            	alert("Date From: invalid date");
                hasError = true;
                return;
            }
            
            if (dateToValue != "" && isDate(dateToValue) == false) {
            	alert("Date To: invalid date");
                hasError = true;
                return;
            }

            if (dateTo < dateFrom) {
                alert("Date To: should be later than Date From:");
                hasError = true;
                return;
            }else {
                var diff = linb.Date.diff(dateFrom, dateTo,'d');

                if (diff > 6) {
                    hasError = true;
                    alert("Error date range");
                    return;
                } else {
                    this.inptDateTo.setUIValue(dateToValue);
                }
            }

            if (dateFrom == 'NaN') {
                hasErrors = true;
                alert(linb.Locale[g_LangKey].app.caption['errorValidDate']);
                return;
            }

            if (this.lstBuyersCompanyId.getUIValue() == '') {
                hasErrors = true;
                alert(linb.Locale[g_LangKey].app.caption['errorNoSelectedBuyers']);
                return;
            }

            if (hasErrors == false) {
                // create parameter object
                var objDateFrom = new Date(akadenFilter.inptDateFrom.getUIValue());
                var objDateTo = new Date(akadenFilter.inptDateTo.getUIValue());
                var obj = new Object();
                obj.startDate = toYYYYMMDD(objDateFrom);
                obj.endDate = toYYYYMMDD(objDateTo);
                obj.selectedBuyerCompany = akadenFilter.allToBuyerIds(akadenFilter.lstBuyersCompany, akadenFilter.lstBuyersCompany.getUIValue());
                obj.selectedBuyerID = akadenFilter.allToBuyerIds(akadenFilter.lstBuyersCompanyId, akadenFilter.lstBuyersCompanyId.getUIValue());
                obj.selectedDate = toYYYYMMDD(objDateFrom);
                obj.categoryId = g_categoryId; // default to fruits
                obj.sheetTypeId = akadenFilter.hiddenSheetTypeId.getCaption();
                obj.buyerCombo = akadenFilter.setBuyerCombo(akadenFilter.lstBuyersCompanyId);

                this.fireEvent('onSearchAkaden',[obj]);
                akadenFilter.clearLists();
                akadenFilter.panelFilter.destroy();
            }
        },
        _lstbuyerscompanyid_onrender: function(profile) {
        	var ns = this, uictrl = profile.boxing();
            var behaviors = profile.box.getBehavior(),
                evOnItem = behaviors.ITEM;
            evOnItem.beforeKeydown=function(profile, e, src){
                if(linb.Event.getKey(e)[0]=='enter'){
                	akadenFilter.searchBuyerCompany();
                    //ns.callthisfunction(profile.getItemByDom(src));
                    return false;
                }
            };
            uictrl.setCustomBehavior("ITEM", evOnItem);
        },
        
        disableControls : function () {
        	this.datepickerSearchDate.setDisabled(true);
        	this.inptDateFrom.setDisabled(true);
        	this.inptDateTo.setDisabled(true);
        	this.rdDateRange.setDisabled(true);
        	this.rbDateOption.setDisabled(true);
        	this.lstBuyersCompany.setDisabled(true);
        	this.lstBuyersCompanyId.setDisabled(true);
        	this.btnSearchMode.setDisabled(true);
        	this.btnCancelMode.setDisabled(true);
        },
        
        enableControls : function () {
        	this.datepickerSearchDate.setDisabled(false);
        	this.inptDateFrom.setDisabled(false);
        	this.inptDateTo.setDisabled(false);
        	this.rdDateRange.setDisabled(false);
        	this.rbDateOption.setDisabled(false);
        	this.lstBuyersCompany.setDisabled(false);
        	this.lstBuyersCompanyId.setDisabled(false);
        	this.btnSearchMode.setDisabled(false);
        	this.btnCancelMode.setDisabled(false);
        }
    }
});

