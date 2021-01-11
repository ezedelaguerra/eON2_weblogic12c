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
		    var host=this, children=[], append=function(child){children.push(child.get(0));};
		
		    append((new linb.UI.Dialog)
		    	.host(host,"panelFilter")
		    	.setDock("none")
		    	.setLeft(93)
		    	.setTop(50)
		    	.setWidth(910)
		    	.setHeight(570)
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
                .setHeight(490)
                .setToggleBtn(false)
                .setCaption("")
            );
		    
		    host.groupDealingPattern.append((new linb.UI.Label)
                .host(host,"labelSeller")
                .setLeft(0)
                .setTop(20)
                .setWidth(80)
                .setCaption("$app.caption.sellers :")
            );
		    
		    host.groupDealingPattern.append((new linb.UI.List)
                .host(host,"lstSellersCompany")
                .setLeft(80)
                .setTop(20)
                .setWidth(200)
                .setHeight(200)
                .setSelMode("multi")
                .setDirtyMark(false)
                .onItemSelected("_lstsellerscompany_onitemselected")
            );
		    
		    host.groupDealingPattern.append((new linb.UI.List)
                .host(host,"lstSellersCompanyId")
                .setLeft(290)
                .setTop(20)
                .setWidth(200)
                .setHeight(200)
                .setSelMode("multi")
                .setDirtyMark(false)
                .onItemSelected("_lstsellerscompanyid_onitemselected")
            );
		    
		    host.groupDealingPattern.append((new linb.UI.Label)
                .host(host,"labelBuyer")
                .setLeft(0)
                .setTop(220)
                .setWidth(80)
                .setCaption("$app.caption.buyers :")
            );
		    
		    host.groupDealingPattern.append((new linb.UI.List)
                .host(host,"lstBuyersCompany")
                .setLeft(80)
                .setTop(220)
                .setWidth(200)
                .setHeight(200)
                .setSelMode("multi")
                .setDirtyMark(false)
                .onItemSelected("_lstbuyerscompany_onitemselected")
            );
		    
		    host.groupDealingPattern.append((new linb.UI.List)
                .host(host,"lstBuyersCompanyId")
                .setLeft(290)
                .setTop(220)
                .setWidth(200)
                .setHeight(200)
                .setSelMode("multi")
                .setDirtyMark(false)
                .onChange("_categoriesTabOnRender")
                .onItemSelected("_lstbuyerscompanyid_onitemselected")
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
            
            host.groupDealingPattern.append((new linb.UI.Label)
                .host(host,"labelMark1")
                .setLeft(260)
                .setTop(430)
                .setWidth(200)
                .setCaption("$app.caption.filterSellerPubMark")
                .setHAlign("left")
            );
            
            host.groupDealingPattern.append((new linb.UI.Label)
                .host(host,"labelMark2")
                .setLeft(260)
                .setTop(450)
                .setWidth(240)
                .setCaption("$app.caption.filterSellerFinMark")
                .setHAlign("left")
            );
            
            host.panelFilter.append((new linb.UI.Button)
                .host(host,"btnSearchMode")
                .setLeft(690)
                .setTop(510)
                .setWidth(80)
                .setCaption("$app.caption.search")
                .onClick("_btnsearchmode_onclick")
            );
            
            host.panelFilter.append((new linb.UI.Button)
                .host(host,"btnCancelMode")
                .setLeft(780)
                .setTop(510)
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
//              .setValue(linb.Date.add(new Date, 'd',1)) 
//              .onRender("_datepickerSearchDate_onrender")
                /* DELETION END 20120726 */
                .afterUIValueSet("_datepickersearchdate_aftervalueupdated")
                .beforeClickEffect("_datepickersearchdate_beforeclickeffect")
                .setEvents({
                    items:{
                        onMouseover:function(profile,e,src){
                            
                			//here, just use linb.Template to build a html string
                            var item=profile.getItem(src),
                            tpl=new linb.Template({"":"<div style='text-align:center;border:solid 1px;background:#fff;'><h4>{title}</h4><img src='{src}'></div><p>{desc}</p>"},item),
                            html=tpl.toHtml();
                            linb.Tips.show(linb.Event.getPos(e),html);
                        }
                	}
                })
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
//                .setValue(linb.Date.add(new Date, 'd',1)) 
//                .onRender("_inptDateFrom_onRender")
                /* DELETION END 20120726 */ 
                .onChange("_inptDateFrom_onChange")
                .onFocus("_inptDateFrom_onFocus")
                // ENHANCEMENT 20120913: Lele - Redmine 880
                .setCustomStyle({'INPUT':'background-color:#e6e273'})
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
                .setDirtyMark(false)
                .setCustomStyle({"KEY":"background:transparent;"})
                .setValue("1")
                // ENHANCEMENT 20120913: Lele - Redmine 880
                .setVisibility("hidden")
            );
            
            host.panelFilter.append((new linb.UI.Pane)
                .host(host,"hiddenPane")
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
		}, 
        customAppend:function(parent,subId,left,top){
            (parent||linb('body')).append(this.panelFilter, subId);
            this.panelFilter.setDisplay('');
            
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
        	sellerAdminFilter = page;
        	sellerAdminFilter.hiddenPane.hide();
        	/* ENHANCEMENT START 20120726 JOVSAB - Calling the function of getting the server date */
        	sellerAdminFilter.getDateServerValue();
            /* ENHANCEMENT START 20120726 */
        	/* DELETION START 20120726 JOVSAB - Redmine 286*/
        	// sellerAdminFilter.populateSellerCompany();
        	/* DELETION END 20120726 */
        	
        	// ENHANCEMENT START 20120913: Lele - Redmine 880
        	var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
            
            if (is_chrome) {
            	sellerAdminFilter.panelFilter.setWidth(930);
            	sellerAdminFilter.groupDealingPattern.setWidth(520);
            }
            // ENHANCEMENT END 20120913:
        },
        _onready: function() {
        	sellerAdminFilter = this;
        	g_LangKey = linb.getLang();
        }, 
        _btncancelmode_onclick:function (profile, e, src, value) {
//        	sellerAdminFilter.clearLists();
//        	this.panelFilter.setDisplay('none');
        	this.destroy();
        }, 
        _btnsearchmode_onclick:function (profile, e, src, value) {
        	sellerAdminFilter.searchBuyerCompany();
	    },  
	    _inptDateTo_onChange: function (profile) {
	        var hasError = false;
	        var newdateresult = this.inptDateTo.getUIValue();
	        var dateFrom = this.inptDateFrom.getUIValue();
	
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
	            sellerAdminFilter.lstBuyersCompany.setItems(empty, true);
	            sellerAdminFilter.lstBuyersCompanyId.setItems(empty, true);
	            sellerAdminFilter.lstSellersCompany.setItems(empty, true);
	            sellerAdminFilter.lstSellersCompanyId.setItems(empty, true);
	            return;
	        }else{
	             // query for company
            	sellerAdminFilter.populateSellerCompany();
	         }	
	    },  _inptDateFrom_onChange: function (profile) {
	        var hasError = false;
	        var newdateresult = this.inptDateTo.getUIValue();
	        var dateFrom = this.inptDateFrom.getUIValue();
	
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
	            sellerAdminFilter.lstBuyersCompany.setItems(empty, true);
	            sellerAdminFilter.lstBuyersCompanyId.setItems(empty, true);
	            sellerAdminFilter.lstSellersCompany.setItems(empty, true);
	            sellerAdminFilter.lstSellersCompanyId.setItems(empty, true);
	            return;
	        }else{
	             // query for company
            	sellerAdminFilter.populateSellerCompany();
	         }
	    }, 
//duplicate code
//	    _categoriesTabOnRender: function() {
//	        var url = "/eON/getCategoryTabs.json";
//	        var objArr =[];
//	        linb.Ajax(
//	            url + "?obj="+objArr,
//	            null,
//	            function (response) {
//	            	validateResponse(response);
//	            var categoryObject = _.unserialize(response);
//	            var obj = _.unserialize(response);
//	            categoryIndex = obj.index;
//	            categoryTabs =[];
//	            for(var i=0; i<categoryObject.categories.length; i++){
//	                var image =obj.categories[i].image.replaceAll("%","/");
//	                categoryTabs.push({"id":obj.categories[i].id,
//	                    "caption":obj.categories[i].caption,"image":image});
//	            }
//	            //orderSheet.categoriesTab.setItems(categoryTabs);
//	            },
//	            function(msg) {
//	                linb.message("失敗： " + msg);
//	            },
//	            null,
//	            {
//	                method : 'GET',
//	                retry : 0
//	            }
//	        ).start();
//	    }, 
        _datepickersearchdate_aftervalueupdated:function (profile, oldValue, newValue) {
	    	
	    	var hasError = false;
            var d = newValue;
            var newdateresult = sellerAdminFilter.formatDate(d);
            var option = this.rdDateRange.getUIValue();
            if (this.rbDateOption.getUIValue() == 'W') {
	        	var to_d = linb.Date.add(d,'d',6);
	        	this.inptDateFrom.setUIValue(newdateresult);
	        	this.inptDateTo.setUIValue(sellerAdminFilter.formatDate(to_d));
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
	                    } else {
	            		this.inptDateTo.setUIValue(newdateresult);
	            }
	            	}
	            }
            }
            var month = newValue.getMonth() + 1;
            var year = newValue.getFullYear();
            sellerAdminFilter._onChangeSellerAction(month, year, true);
        }, 
        _rbdateoption_onitemselected:function (profile, item, src) {
        	var tmp = this.rdDateRange.getItems();
        	if (this.rbDateOption.getUIValue() == 'W') {
	        	var d = this.datepickerSearchDate.getUIValue();
	        	var dateFrom = sellerAdminFilter.formatDate(d);
	            this.inptDateFrom.setUIValue(dateFrom);
	            
	        	d = linb.Date.add(new Date(dateFrom),'d',6);
	        	this.inptDateTo.setUIValue(sellerAdminFilter.formatDate(d));
	        	this.inptDateTo.setDisabled(true);
	        	tmp[1].disabled = true;
	        	
	        	sellerAdminFilter.populateSellerCompany();
        	} else {
        		this.inptDateTo.setDisabled(false);
        		this.inptDateTo.setUIValue("");
        		tmp[1].disabled = false;
        	}
        	this.rdDateRange.setItems(tmp);
        },
        allToUserIds : function (linbComp, newValue) {
            var tmp = "";
            var buyIds = newValue.split(';');
            //alert("buyIds:[" + _.serialize(buyIds) + "]");
        	var items = linbComp.getItems();
        	//alert("items:[" + _.serialize(items) + "]");
        	for (var i=1; i<items.length; i++) {
        		for ( var j = 0; j < buyIds.length; j++ ) {
        			if (items[i].id == buyIds[j]) {                    
            			//add to sorted Ids
        				tmp = tmp + items[i].id + ";";
        				break;
        			}
        		}
            }
            //alert("tmp:[" + tmp + ']');
            return tmp;
        },
        allToUserIds : function (linbComp) {
        	var isNotAll = sellerAdminFilter.hiddenFilterMode.getCaption();
        	var tmpValue = linbComp.getUIValue();
        	var buyIds = tmpValue.split(';');
        	//alert("buyIds:[" + _.serialize(buyIds) + "]");
        	var tmp = "";
//        	if (tmpValue == "0" || tmpValue.startsWith("0;")) {
//        		var items = linbComp.getItems();
//        		for (var i=1; i<items.length; i++) {
//        			tmp = tmp + items[i].id + ";";
//        		}
//        	} else {
//        		tmp = tmpValue;
//        	}

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
        	//alert("tmp:[" + tmp + ']');
        	return tmp;
        },
        setBuyerCombo : function (comboObj) {
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
        setSellerCombo : function (comboObj) {
        	var list = [];
        	var idx = 0;
        	var obj = new Object();
        	var values = comboObj.getUIValue().split(';');
        	var items = comboObj.getItems();
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
        formatDate : function (dateObj) {
        	var _year = linb.Date.get(dateObj,"y");
        	var _month = pad((linb.Date.get(dateObj,"m")+1),2);
        	var _day = pad(linb.Date.get(dateObj,"d"),2);
        	return  _year + "/" + _month + "/" + _day;
        },
        _inptDateTo_onFocus : function (profile) {
        	// ENHANCEMENT START 20120913: Lele - Redmine 880
        	this.inptDateFrom.setCustomStyle({'INPUT':'background-color:white'});
        	this.inptDateTo.setCustomStyle({'INPUT':'background-color:#e6e273'});
        	//this.inptDateFrom.refresh();
        	//this.inptDateTo.refresh();
        	// ENHANCEMENT END 20120913:
        	this.rdDateRange.setValue(2);
        	this.rdDateRange.refresh();
        },_inptDateFrom_onFocus : function (profile) {
        	// ENHANCEMENT START 20120913: Lele - Redmine 880
        	this.inptDateFrom.setCustomStyle({'INPUT':'background-color:#e6e273'});
        	this.inptDateTo.setCustomStyle({'INPUT':'background-color:white'});
        	//this.inptDateFrom.refresh();
        	//this.inptDateTo.refresh();
        	// ENHANCEMENT END 20120913:
        	this.rdDateRange.setValue(1);
        	this.rdDateRange.refresh();
        },
        populateSellerCompany : function () {
        	var url = "/eON/adminFilter/getCompany.json";
        	var id = Math.random() * url.length;
        	sellerAdminFilter.disableControls();
        	linb.Ajax(
                url + "?id=" + id,
                null,
                function (response) {
                	validateResponse(response);
                	var o = _.unserialize(response);
                	var items = o.response;
                	sellerAdminFilter.lstSellersCompany.setItems(items, true);
                	sellerAdminFilter.lstSellersCompany.refresh();
                	sellerAdminFilter.enableControls();
                }, 
                function(msg) {
                        linb.message("失敗： " + msg);
                    }, null, {
                        method : 'GET',
                        retry : 0
                    }
            ).start();
        	// reset value of ...
            this.lstSellersCompanyId.setItems(null, true);
            this.lstSellersCompanyId.refresh();
            this.lstBuyersCompanyId.setItems(null, true);
            this.lstBuyersCompanyId.refresh();
            this.lstBuyersCompany.setItems(null, true);
            this.lstBuyersCompany.refresh();
        },_categoriesTabOnRender: function() {
        	
        },
        _lstsellerscompany_onitemselected:function (profile, item, src) {
        	
        	var ids = "";
            var items = this.lstSellersCompany.getItems();
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
            
            var tmp = sellerAdminFilter.lstSellersCompany.getUIValue();
            if (tmp != "") {
		    	var objDateFrom = new Date(sellerAdminFilter.inptDateFrom.getUIValue());
		    	var objDateTo = new Date(sellerAdminFilter.inptDateTo.getUIValue());
		    	
		    	var url = "/eON/adminFilter/getUser.json?dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo);
		    	url = url + "&company=" + tmp;
		    	url = url + "&sheetTypeId=" + sellerAdminFilter.hiddenSheetTypeId.getCaption();
		    	var id = Math.random() * url.length;
		    	sellerAdminFilter.disableControls();
		        linb.Ajax(
		            url+"&id="+id,
		            null,
		            function (response) {
		            	//sellerAdminFilter.responseCheck(response);
		            	validateResponse(response);
		            	var o = _.unserialize(response);
		            	var items = o.response;
		            	sellerAdminFilter.lstSellersCompanyId.setItems(items, true);
		            	if (sellerAdminFilter.hiddenFilterMode.getCaption()==1)
		            		sellerAdminFilter.lstSellersCompanyId.removeItems(['0']);
		            	sellerAdminFilter.lstSellersCompanyId.refresh();
		            	sellerAdminFilter.lstBuyersCompanyId.setItems(null, true);
		            	sellerAdminFilter.lstBuyersCompanyId.refresh();
		            	sellerAdminFilter.lstBuyersCompany.setItems(null, true);
		            	sellerAdminFilter.lstBuyersCompany.refresh();
		            	sellerAdminFilter.enableControls();
		            }, 
		            function(msg) {
		                    linb.message("onFail: " + msg);
		                }, null, {
		                    method : 'GET',
		                    retry : 0
		                }
		        ).start();
            } else {
            	sellerAdminFilter.lstSellersCompanyId.setItems(null, true);
            	sellerAdminFilter.lstSellersCompanyId.refresh();
            	sellerAdminFilter.lstBuyersCompanyId.setItems(null, true);
            	sellerAdminFilter.lstBuyersCompanyId.refresh();
            	sellerAdminFilter.lstBuyersCompany.setItems(null, true);
            	sellerAdminFilter.lstBuyersCompany.refresh();
            }
        }, 
        _lstsellerscompanyid_onitemselected:function (profile, item, src) {
        	if (sellerAdminFilter.hiddenFilterMode.getCaption()==1)
        		sellerAdminFilter.lstSellersCompanyId.setSelMode("single");
            var ids = "";
            var items = this.lstSellersCompanyId.getItems();
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
            
            var tmp = sellerAdminFilter.lstSellersCompanyId.getUIValue();
            if (tmp != "") {
	        	var objDateFrom = new Date(sellerAdminFilter.inptDateFrom.getUIValue());
	        	var objDateTo = new Date(sellerAdminFilter.inptDateTo.getUIValue());
	        	
	        	var url = "/eON/filter/getCompany.json?dealingPatternId=1";
	        	url = url + "&userCompanyId=" +tmp;
	        	url = url + "&dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo);
	        	var id = Math.random() * url.length;
	        	sellerAdminFilter.disableControls();
	            linb.Ajax(
	                url+"&id="+id,
	                null,
	                function (response) {
	                	//sellerAdminFilter.responseCheck(response);
	                	validateResponse(response);
	                	var o = _.unserialize(response);
	                	var items = o.response;
	                	sellerAdminFilter.lstBuyersCompany.setItems(items, true);
	                	sellerAdminFilter.lstBuyersCompany.refresh();
	                	sellerAdminFilter.lstBuyersCompanyId.setItems(null, true);
	                	sellerAdminFilter.lstBuyersCompanyId.refresh();
	                	sellerAdminFilter.enableControls();
	                }, 
	                function(msg) {
	                        linb.message("onFail: " + msg);
	                    }, null, {
	                        method : 'GET',
	                        retry : 0
	                    }
	            ).start();
            } else {
            	sellerAdminFilter.lstBuyersCompany.setItems(null, true);
            	sellerAdminFilter.lstBuyersCompany.refresh();
            	sellerAdminFilter.lstBuyersCompanyId.setItems(null, true);
            	sellerAdminFilter.lstBuyersCompanyId.refresh();
            }
        }, 
        _lstbuyerscompany_onitemselected:function (profile, item, src) {
        sellerAdminFilter.clearHighlightsInDatePicker();
            var ids = "";
            var items = this.lstBuyersCompany.getItems();
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
            
            var buyerComp = sellerAdminFilter.lstBuyersCompany.getUIValue();
            if (buyerComp != "" && buyerComp != ";0") {
	        	var sellerIds = sellerAdminFilter.allToUserIds(this.lstSellersCompanyId);
	        	var objDateFrom = new Date(sellerAdminFilter.inptDateFrom.getUIValue());
	        	var objDateTo = new Date(sellerAdminFilter.inptDateTo.getUIValue());
	        	
	        	var url = "/eON/filter/getUser.json?companies="+buyerComp+"&sellerIds="+sellerIds;
	        	url = url + "&dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo);
	        	url = url + "&sheetTypeId=" + sellerAdminFilter.hiddenSheetTypeId.getCaption();
	        	var id = Math.random() * url.length;
	        	sellerAdminFilter.disableControls();
	            linb.Ajax(
	                url+"&id="+id,
	                null,
	                function (response) {
	                	//sellerAdminFilter.responseCheck(response);
	                	validateResponse(response);
	                	var o = _.unserialize(response);
	                	var items = o.response;
	                	sellerAdminFilter.lstBuyersCompanyId.setItems(items, true);
	                	if (sellerAdminFilter.hiddenFilterMode.getCaption()==1)
	                		sellerAdminFilter.lstBuyersCompanyId.removeItems(['0']);
	                	sellerAdminFilter.lstBuyersCompanyId.refresh();
	                	sellerAdminFilter.enableControls();
	                }, 
	                function(msg) {
	                        linb.message("onFail: " + msg);
	                    }, null, {
	                        method : 'GET',
	                        retry : 0
	                    }
	            ).start();
            } else {
            	sellerAdminFilter.lstBuyersCompanyId.setItems(null, true);
            	sellerAdminFilter.lstBuyersCompanyId.refresh();
            }
        }, 
        _lstbuyerscompanyid_onitemselected:function (profile, item, src) {
        	if (sellerAdminFilter.hiddenFilterMode.getCaption()==1)
        		sellerAdminFilter.lstBuyersCompanyId.setSelMode("single");
            var ids = "";
            var items = this.lstBuyersCompanyId.getItems();
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
            //moved here from _categoriesTabOnRender to prevent double call
        	var url = "/eON/getCategoryTabs.json";
        	var objArr =sellerAdminFilter.allToUserIds(sellerAdminFilter.lstSellersCompanyId, 
        			sellerAdminFilter.lstSellersCompanyId.getUIValue());
        	if (objArr == "") {
        		return;
        	} 
            sellerAdminFilter.disableControls();
        	linb.Ajax(
    	        url + "?obj="+objArr,
    	        null,
    	        function (response) {
    	        	validateResponse(response);
        		var categoryObject = _.unserialize(response);
        		var obj = _.unserialize(response);
        		categoryIndex = obj.index; 
        		categoryTabs=[];
                for(var i=0; i<categoryObject.categories.length; i++){
                	var image =obj.categories[i].image.replaceAll("%","/"); 		
                	categoryTabs.push({"id":obj.categories[i].id, 
                		"caption":obj.categories[i].caption,"image":image});
                }
            	sellerAdminFilter.enableControls();
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
            var month = sellerAdminFilter.datepickerSearchDate._nodes[0].$month;
            var year = sellerAdminFilter.datepickerSearchDate._nodes[0].$year;
            sellerAdminFilter._onChangeSellerAction(month, year, true);
            sellerAdminFilter._setStartEndDeliveryDate();
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
                    sellerAdminFilter.datepickerSearchDate.setValue(linb.Date.parse(obj.response));
                    sellerAdminFilter.inptDateFrom.setValue(obj.response);
                    // Call method to populate seller company
                    sellerAdminFilter.populateSellerCompany();
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
        	sellerAdminFilter.lstSellersCompanyId.setItems([]);
    	    sellerAdminFilter.lstSellersCompany.setItems([]);
    	    sellerAdminFilter.lstBuyersCompanyId.setItems([]);
    	    sellerAdminFilter.lstBuyersCompany.setItems([]);
    	    /* DELETION START 20120726 JOVSAB - Not needed call the function */
//    	    sellerAdminFilter.datepickerSearchDate.setValue(linb.Date.add(new Date, 'd',1));
//    	    sellerAdminFilter.inptDateFrom.setValue(this.formatDate(linb.Date.add(new Date, 'd',1)), true);
//    	    sellerAdminFilter.inptDateTo.setValue("", true);
    	    /* DELETION END 20120726 JOVSAB */
    	    sellerAdminFilter.rbDateOption.setValue("D",true);
        },
        searchBuyerCompany: function() {               
        	var hasErrors = false;

            var dateFromValue = this.inptDateFrom.getUIValue();
            var dateFrom = new Date(dateFromValue);
            var dateToValue = this.inptDateTo.getUIValue();
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
        		var objDateFrom = new Date(sellerAdminFilter.inptDateFrom.getUIValue());
        		
        		// ENHANCEMENT START 20120913: Lele - Redmine 880
        		if (!sellerAdminFilter.inptDateTo.getUIValue()) {
        			sellerAdminFilter.inptDateTo.setValue(sellerAdminFilter.inptDateFrom.getUIValue());
                }
                // ENHANCEMENT END 20120913:
        		
        		var objDateTo = new Date(sellerAdminFilter.inptDateTo.getUIValue());
        		var obj = new Object();
        		obj.startDate = toYYYYMMDD(objDateFrom);
        	    obj.endDate = toYYYYMMDD(objDateTo);
        	    obj.selectedSellerID = sellerAdminFilter.allToUserIds(sellerAdminFilter.lstSellersCompanyId, sellerAdminFilter.lstSellersCompanyId.getUIValue());
        	    obj.selectedBuyerCompany = sellerAdminFilter.allToUserIds(sellerAdminFilter.lstBuyersCompany, sellerAdminFilter.lstBuyersCompany.getUIValue());
        	    obj.selectedBuyerID = sellerAdminFilter.allToUserIds(sellerAdminFilter.lstBuyersCompanyId, sellerAdminFilter.lstBuyersCompanyId.getUIValue());
        	    obj.selectedDate = toYYYYMMDD(objDateFrom);
        	    obj.categoryId = categoryIndex; // default to fruits
        	    obj.sheetTypeId = sellerAdminFilter.hiddenSheetTypeId.getCaption();
        	    
        	    // Remove filter marks
                var items = sellerAdminFilter.lstBuyersCompanyId.getItems();
                var newArray = [];
                var i = 0;
                for (i=0; i<items.length; i++) {
                	newArray.push({"id" : items[i].id, "caption" : items[i].noMarkCaption});
                }
                sellerAdminFilter.lstBuyersCompanyId.setItems(newArray,true);
                obj.buyerCombo = sellerAdminFilter.setBuyerCombo(sellerAdminFilter.lstBuyersCompanyId);
                // end of removal
        	    
        	    //by Rhoda for add sku group seller combo- start
        	    obj.sellerCombo = sellerAdminFilter.setSellerCombo(sellerAdminFilter.lstSellersCompanyId);
        	    //by Rhoda for add sku group seller combo- start
        	    
        	    this.fireEvent('onSearch',[obj]);
//        		this.destroy();
        	    sellerAdminFilter.clearLists();
//        	    this.panelFilter.setDisplay('none');
        	    this.destroy();
        	}
        },
        _lstbuyerscompanyid_onrender: function(profile) {
        	var ns = this, uictrl = profile.boxing();
            var behaviors = profile.box.getBehavior(),
                evOnItem = behaviors.ITEM;
            evOnItem.beforeKeydown=function(profile, e, src){
                if(linb.Event.getKey(e)[0]=='enter'){
                	sellerAdminFilter.searchBuyerCompany();
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
        	this.lstSellersCompany.setDisabled(true);
        	this.lstSellersCompanyId.setDisabled(true);
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
        	this.lstSellersCompany.setDisabled(false);
        	this.lstSellersCompanyId.setDisabled(false);
        	this.lstBuyersCompany.setDisabled(false);
        	this.lstBuyersCompanyId.setDisabled(false);
        	this.btnSearchMode.setDisabled(false);
        	this.btnCancelMode.setDisabled(false);
        },

        _onChangeSellerAction: function(month, year, isSameMonthYear) {
        	sellerAdminFilter.clearHighlightsInDatePicker();

            var url = "/eON/getSellerPublishedDate.json";
            var objDateFrom = new Date();
            var objDateTo = new Date();

            objDateFrom.setDate(objDateFrom.getDate() + 1);
            objDateTo.setDate(objDateTo.getDate() + 1);

            var selectedBuyerIds = sellerAdminFilter.lstBuyersCompanyId.getUIValue().split(';');
            var selectedSellerIds = sellerAdminFilter.lstSellersCompanyId.getUIValue().split(';');
            strArr = "?dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo)
            +"&selectedBuyers="+selectedBuyerIds+"&selectedSellers="+selectedSellerIds;

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
                    	if(sellerAdminFilter.datepickerSearchDate.getSubNode('TD', i).html() == "1"){
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
                                	if(sellerAdminFilter.datepickerSearchDate.getSubNode('TD', dateHighlighted).attr('class').
                                			indexOf("TD_CC-checked") == -1){
                                		sellerAdminFilter.datepickerSearchDate.getSubNode('TD', dateHighlighted).css('background-color', '#80f442');
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
            var selectedBuyerIds = sellerAdminFilter.lstBuyersCompanyId.getUIValue().split(';');
            var selectedSellerIds = sellerAdminFilter.lstSellersCompanyId.getUIValue().split(';');
            strArr = "?dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo)
            +"&selectedBuyers="+selectedBuyerIds+"&selectedSellers="+selectedSellerIds;

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
                        	yyyymmddDateStr = sellerAdminFilter.inptDateFrom.getUIValue();
                        }

                        sellerAdminFilter.inptDateTo.setValue(yyyymmddDateStr);
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
        				if (sellerAdminFilter.datepickerSearchDate.getSubNode('TD', i).attr('class').
        	                            			indexOf("td-free") == -1){ //Weekdays
        							sellerAdminFilter.datepickerSearchDate.getSubNode('TD', i).attr('style' , 'background-color : #EFF8FF , color : #000000');
        				} else {
        							sellerAdminFilter.datepickerSearchDate.getSubNode('TD', i).attr('style' , 'background-color : #F9F7D1, color : #000000');
        				}

        				if(sellerAdminFilter.datepickerSearchDate.getSubNode('TD', i).attr('class').
        	                            			indexOf("TD_CC-checked") != -1){
        							sellerAdminFilter.datepickerSearchDate.getSubNode('TD', i).attr('style' , 'background-color : #316AC5');
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
        			sellerAdminFilter._onChangeSellerAction(month, year, true);
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
                		sellerAdminFilter.clearHighlightsInDatePicker();
                	} else if (e.currentTarget.className == nextMonth){
                		sellerAdminFilter.clearHighlightsInDatePicker();
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
        				sellerAdminFilter.newValue = newValue;
        				sellerAdminFilter.oldValue = oldValue;
        				setTimeout(function(){
        					sellerAdminFilter._changeCalendarEventListener(sellerAdminFilter.oldValue, sellerAdminFilter.newValue, 1);
        				},350)

                	} else if(e.currentTarget.className == previousMonth){
                		sellerAdminFilter.clearHighlightsInDatePicker();
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
                		sellerAdminFilter.newValue = newValue;
        				sellerAdminFilter.oldValue = oldValue;
        				setTimeout(function(){
        					sellerAdminFilter._changeCalendarEventListener(sellerAdminFilter.oldValue, sellerAdminFilter.newValue, 0);
        				},350)
                	}

                	return true;
                },

        _datepickersearchdate_aftervalueupdated:function (profile, oldValue, newValue) {
                	var month = newValue.getMonth() + 1;
                	var year = newValue.getFullYear();

                	var hasError = false;
                    var d = newValue;
                    var newdateresult = sellerAdminFilter.formatDate(d);
                    var option = sellerAdminFilter.rdDateRange.getUIValue();
                    if (sellerAdminFilter.rbDateOption.getUIValue() == 'W') {
        	        	var to_d = linb.Date.add(d,'d',6);
        	        	sellerAdminFilter.inptDateFrom.setUIValue(newdateresult);
        	        	sellerAdminFilter.inptDateTo.setUIValue(sellerAdminFilter.formatDate(to_d));
                    } else {
        	            if (option == "1") {
        	            	var dateToValue = sellerAdminFilter.inptDateTo.getUIValue();
        	            	var dateFrom = new Date(newdateresult);
        	            	var dateTo = new Date(sellerAdminFilter.inptDateTo.getUIValue());

        	            	if (dateToValue != "" && isDate(dateToValue) == false) {
        						//alert("Date To: invalid date");
        						//hasError = true;
                        		sellerAdminFilter.inptDateFrom.setUIValue(newdateresult);
                        		sellerAdminFilter.inptDateTo.setUIValue("");
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
                            		sellerAdminFilter.inptDateFrom.setUIValue(newdateresult);
                            	}
                            }
        	            }
        	            else {
        	            	var dateFrom = sellerAdminFilter.inptDateFrom.getUIValue();

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
        	                    	sellerAdminFilter.inptDateTo.setUIValue(newdateresult);
        	                    }
        	            	}
        	            }
                    }
                    sellerAdminFilter._onChangeSellerAction(month, year, true);
                }

    }
});