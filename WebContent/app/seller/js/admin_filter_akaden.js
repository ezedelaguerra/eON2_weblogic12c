var g_LangKey;

Class('App.admin_filter_akaden', 'linb.Com', {
	autoDestroy:true,
    Instance: {
        iniComponents:function(){
		var host=this, children=[], append=function(child){children.push(child.get(0));};
		
	    append((new linb.UI.Dialog)
	    	.host(host,"panelFilter")
	    	.setLeft(93)
	    	.setTop(50)
	    	.setWidth(910)
	    	.setHeight(540)
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
            .setHeight(455)
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
            .setHeight(220)
            .setSelMode("multi")
            .setDirtyMark(false)
            .onItemSelected("_lstsellerscompany_onitemselected")
        );
	    
	    host.groupDealingPattern.append((new linb.UI.List)
            .host(host,"lstSellersCompanyId")
            .setLeft(290)
            .setTop(20)
            .setWidth(200)
            .setHeight(220)
            .setSelMode("multi")
            .setDirtyMark(false)
            .onItemSelected("_lstsellerscompanyid_onitemselected")
        );
	    
	    host.groupDealingPattern.append((new linb.UI.Label)
            .host(host,"labelBuyer")
            .setLeft(0)
            .setTop(210)
            .setWidth(80)
            .setCaption("$app.caption.buyers :")
        );
	    
	    host.groupDealingPattern.append((new linb.UI.List)
            .host(host,"lstBuyersCompany")
            .setLeft(80)
            .setTop(210)
            .setWidth(200)
            .setHeight(220)
            .setSelMode("multi")
            .setDirtyMark(false)
            .onItemSelected("_lstbuyerscompany_onitemselected")
        );
	    
	    host.groupDealingPattern.append((new linb.UI.List)
            .host(host,"lstBuyersCompanyId")
            .setLeft(290)
            .setTop(210)
            .setWidth(200)
            .setHeight(220)
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
        
        host.panelFilter.append((new linb.UI.Button)
            .host(host,"btnSearchMode")
            .setLeft(690)
            .setTop(470)
            .setWidth(80)
            .setCaption("$app.caption.search")
            .onClick("_btnsearchmode_onclick")
        );
        
        host.panelFilter.append((new linb.UI.Button)
            .host(host,"btnCancelMode")
            .setLeft(780)
            .setTop(470)
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
            .setValue(linb.Date.add(new Date, 'd',1)) 
            .afterUIValueSet("_datepickersearchdate_aftervalueupdated")
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
            .setDirtyMark(false)
            .setCustomStyle({"KEY":"background:transparent;"})
            .setValue("1")
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
        
	    return children;
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
        	sellerAdminFilterAkaden = page;
        	sellerAdminFilterAkaden.hiddenPane.hide();
        	sellerAdminFilterAkaden.populateSellerCompany();
        },
        _onready: function() {
        	sellerAdminFilterAkaden = this;
        	g_LangKey = linb.getLang();
        }, 
        _btncancelmode_onclick:function (profile, e, src, value) {
        	sellerAdminFilterAkaden.clearLists();
        	sellerAdminFilterAkaden.panelFilter.destroy();
        }, 
        _btnsearchmode_onclick:function (profile, e, src, value) {
        	sellerAdminFilterAkaden.searchBuyerCompany();
	    },  
	    _inptDateTo_onChange: function (profile) {
	        var hasError = false;
	        var newdateresult = this.inptDateTo.getUIValue();
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
	            sellerAdminFilterAkaden.lstBuyersCompany.setItems(empty, true);
	            sellerAdminFilterAkaden.lstBuyersCompanyId.setItems(empty, true);
	            sellerAdminFilterAkaden.lstSellersCompany.setItems(empty, true);
	            sellerAdminFilterAkaden.lstSellersCompanyId.setItems(empty, true);
	            return;
	        }else{
	             // query for company
            	sellerAdminFilterAkaden.populateSellerCompany();
	         }	
	    },  _inptDateFrom_onChange: function (profile) {
	        var hasError = false;
	        var newdateresult = this.inptDateTo.getUIValue();
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
	            sellerAdminFilterAkaden.lstBuyersCompany.setItems(empty, true);
	            sellerAdminFilterAkaden.lstBuyersCompanyId.setItems(empty, true);
	            sellerAdminFilterAkaden.lstSellersCompany.setItems(empty, true);
	            sellerAdminFilterAkaden.lstSellersCompanyId.setItems(empty, true);
	            return;
	        }else{
	             // query for company
            	sellerAdminFilterAkaden.populateSellerCompany();
	         }
	    },  _categoriesTabOnRender: function() {
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
        _datepickersearchdate_aftervalueupdated:function (profile, oldValue, newValue) {
        	var hasError = false;
            var d = newValue;
            var newdateresult = sellerAdminFilterAkaden.formatDate(d);
            var option = this.rdDateRange.getUIValue();
            if (this.rbDateOption.getUIValue() == 'W') {
	        	var to_d = linb.Date.add(d,'d',6);
	        	this.inptDateFrom.setUIValue(newdateresult);
	        	this.inptDateTo.setUIValue(sellerAdminFilterAkaden.formatDate(to_d));
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
            
            if (hasError == false) {
            	sellerAdminFilterAkaden.populateSellerCompany();
            }
        }, 
        _rbdateoption_onitemselected:function (profile, item, src) {
        	var tmp = this.rdDateRange.getItems();
        	if (this.rbDateOption.getUIValue() == 'W') {
	        	var d = this.datepickerSearchDate.getUIValue();
	        	var dateFrom = sellerAdminFilterAkaden.formatDate(d);
	            this.inptDateFrom.setUIValue(dateFrom);
	            
	        	d = linb.Date.add(new Date(dateFrom),'d',6);
	        	this.inptDateTo.setUIValue(sellerAdminFilterAkaden.formatDate(d));
	        	this.inptDateTo.setDisabled(true);
	        	tmp[1].disabled = true;
	        	
	        	sellerAdminFilterAkaden.populateSellerCompany();
        	} else {
        		this.inptDateTo.setDisabled(false);
        		this.inptDateTo.setUIValue("");
        		tmp[1].disabled = false;
        	}
        	this.rdDateRange.setItems(tmp);
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
        allToUserIds : function (linbComp) {
        	var tmpValue = linbComp.getUIValue();
        	var tmp = "";
        	if (tmpValue == "0" || tmpValue.startsWith("0;")) {
        		var items = linbComp.getItems();
        		for (var i=1; i<items.length; i++) {
        			tmp = tmp + items[i].id + ";";
        		}
        	} else {
        		tmp = tmpValue;
        	}
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
        	this.rdDateRange.setValue(2);
        	this.rdDateRange.refresh();
        },_inptDateFrom_onFocus : function (profile) {
        	this.rdDateRange.setValue(1);
        	this.rdDateRange.refresh();
        },
        populateSellerCompany : function () {
        	var url = "/eON/adminFilter/getCompany.json";
        	var id = Math.random() * url.length;
        	sellerAdminFilterAkaden.disableControls();
        	linb.Ajax(
                url + "?id=" + id,
                null,
                function (response) {
                	validateResponse(response);
                	var o = _.unserialize(response);
                	var items = o.response;
                	sellerAdminFilterAkaden.lstSellersCompany.setItems(items, true);
                	sellerAdminFilterAkaden.lstSellersCompany.refresh();
                	sellerAdminFilterAkaden.enableControls();
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
        },
        _categoriesTabOnRender: function() {
        	var url = "/eON/getCategoryTabs.json";
        	var objArr =sellerAdminFilterAkaden.allToUserIds(sellerAdminFilterAkaden.lstSellersCompanyId, 
        			sellerAdminFilterAkaden.lstSellersCompanyId.getUIValue());;
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
        _lstsellerscompany_onitemselected:function (profile, item, src) {
            var ids = "";
            var items = this.lstSellersCompany.getItems();
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
            
            var tmp = sellerAdminFilterAkaden.lstSellersCompany.getUIValue();
            if (tmp != "") {
	        	var objDateFrom = new Date(sellerAdminFilterAkaden.inptDateFrom.getUIValue());
	        	var objDateTo = new Date(sellerAdminFilterAkaden.inptDateTo.getUIValue());
	        	
	        	var url = "/eON/adminFilter/getUser.json?dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo);
	        	url = url + "&company=" + tmp;
	        	var id = Math.random() * url.length;
	        	sellerAdminFilterAkaden.disableControls();
	            linb.Ajax(
	                url+"&id="+id,
	                null,
	                function (response) {
	                	validateResponse(response);
	                	var o = _.unserialize(response);
	                	var items = o.response;
	                	sellerAdminFilterAkaden.lstSellersCompanyId.setItems(items, true);
	                	sellerAdminFilterAkaden.lstSellersCompanyId.removeItems(['0']);
	                	sellerAdminFilterAkaden.lstSellersCompanyId.refresh();
	                	sellerAdminFilterAkaden.lstBuyersCompanyId.setItems(null, true);
	                	sellerAdminFilterAkaden.lstBuyersCompanyId.refresh();
	                	sellerAdminFilterAkaden.lstBuyersCompany.setItems(null, true);
	                	sellerAdminFilterAkaden.lstBuyersCompany.refresh();
	                	sellerAdminFilterAkaden.enableControls();
	                }, 
	                function(msg) {
	                        linb.message("onFail: " + msg);
	                    }, null, {
	                        method : 'GET',
	                        retry : 0
	                    }
	            ).start();
            } else {
            	sellerAdminFilterAkaden.lstSellersCompanyId.setItems(null, true);
            	sellerAdminFilterAkaden.lstSellersCompanyId.refresh();
            	sellerAdminFilterAkaden.lstBuyersCompanyId.setItems(null, true);
            	sellerAdminFilterAkaden.lstBuyersCompanyId.refresh();
            	sellerAdminFilterAkaden.lstBuyersCompany.setItems(null, true);
            	sellerAdminFilterAkaden.lstBuyersCompany.refresh();
            }
        }, 
        _lstsellerscompanyid_onitemselected:function (profile, item, src) {
        	sellerAdminFilterAkaden.lstSellersCompanyId.setSelMode("single");
            var ids = "";
            var items = this.lstSellersCompanyId.getItems();
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
            
            var tmp = sellerAdminFilterAkaden.lstSellersCompanyId.getUIValue();
            if (tmp != "") {
	        	var objDateFrom = new Date(sellerAdminFilterAkaden.inptDateFrom.getUIValue());
	        	var objDateTo = new Date(sellerAdminFilterAkaden.inptDateTo.getUIValue());
	        	
	        	var url = "/eON/filter/getCompany.json?dealingPatternId=1";
	        	url = url + "&userCompanyId=" +tmp;
	        	url = url + "&dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo);
	        	var id = Math.random() * url.length;
	        	sellerAdminFilterAkaden.disableControls();
	            linb.Ajax(
	                url+"&id="+id,
	                null,
	                function (response) {
	                	validateResponse(response);
	                	var o = _.unserialize(response);
	                	var items = o.response;
	                	sellerAdminFilterAkaden.lstBuyersCompany.setItems(items, true);
	                	sellerAdminFilterAkaden.lstBuyersCompany.refresh();
	                	sellerAdminFilterAkaden.lstBuyersCompanyId.setItems(null, true);
	                	sellerAdminFilterAkaden.lstBuyersCompanyId.refresh();
	                	sellerAdminFilterAkaden.enableControls();
	                }, 
	                function(msg) {
	                        linb.message("onFail: " + msg);
	                    }, null, {
	                        method : 'GET',
	                        retry : 0
	                    }
	            ).start();
            } else {
            	sellerAdminFilterAkaden.lstBuyersCompany.setItems(null, true);
            	sellerAdminFilterAkaden.lstBuyersCompany.refresh();
            	sellerAdminFilterAkaden.lstBuyersCompanyId.setItems(null, true);
            	sellerAdminFilterAkaden.lstBuyersCompanyId.refresh();
            }
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
            
            var buyerComp = sellerAdminFilterAkaden.lstBuyersCompany.getUIValue();
            if (buyerComp != "") {
	        	var sellerIds = sellerAdminFilterAkaden.allToUserIds(this.lstSellersCompanyId);
	        	var objDateFrom = new Date(sellerAdminFilterAkaden.inptDateFrom.getUIValue());
	        	var objDateTo = new Date(sellerAdminFilterAkaden.inptDateTo.getUIValue());
	        	
	        	var url = "/eON/filter/getUser.json?companies="+buyerComp+"&sellerIds="+sellerIds;
	        	url = url + "&dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo);
	        	var id = Math.random() * url.length;
	        	sellerAdminFilterAkaden.disableControls();
	            linb.Ajax(
	                url+"&id="+id,
	                null,
	                function (response) {
	                	validateResponse(response);
	                	var o = _.unserialize(response);
	                	var items = o.response;
	                	sellerAdminFilterAkaden.lstBuyersCompanyId.setItems(items, true);
	                	sellerAdminFilterAkaden.lstBuyersCompanyId.removeItems(['0']);
	                	sellerAdminFilterAkaden.lstBuyersCompanyId.refresh();
	                	sellerAdminFilterAkaden.enableControls();
	                }, 
	                function(msg) {
	                        linb.message("onFail: " + msg);
	                    }, null, {
	                        method : 'GET',
	                        retry : 0
	                    }
	            ).start();
            } else {
            	sellerAdminFilterAkaden.lstBuyersCompanyId.setItems(null, true);
            	sellerAdminFilterAkaden.lstBuyersCompanyId.refresh();
            }
        }, 
        _lstbuyerscompanyid_onitemselected:function (profile, item, src) {
        	sellerAdminFilterAkaden.lstBuyersCompanyId.setSelMode("single");
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
        clearLists: function() {
        	sellerAdminFilterAkaden.lstSellersCompanyId.setItems([]);
    	    sellerAdminFilterAkaden.lstSellersCompany.setItems([]);
    	    sellerAdminFilterAkaden.lstBuyersCompanyId.setItems([]);
    	    sellerAdminFilterAkaden.lstBuyersCompany.setItems([]);
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
        		var objDateFrom = new Date(sellerAdminFilterAkaden.inptDateFrom.getUIValue());
        		var objDateTo = new Date(sellerAdminFilterAkaden.inptDateTo.getUIValue());
        		var obj = new Object();
        		obj.startDate = toYYYYMMDD(objDateFrom);
        	    obj.endDate = toYYYYMMDD(objDateTo);
        	    obj.selectedSellerID = sellerAdminFilterAkaden.allToUserIds(sellerAdminFilterAkaden.lstSellersCompanyId, sellerAdminFilterAkaden.lstSellersCompanyId.getUIValue());
        	    obj.selectedBuyerCompany = sellerAdminFilterAkaden.allToUserIds(sellerAdminFilterAkaden.lstBuyersCompany, sellerAdminFilterAkaden.lstBuyersCompany.getUIValue());
        	    obj.selectedBuyerID = sellerAdminFilterAkaden.allToUserIds(sellerAdminFilterAkaden.lstBuyersCompanyId, sellerAdminFilterAkaden.lstBuyersCompanyId.getUIValue());
        	    obj.selectedDate = toYYYYMMDD(objDateFrom);
        	    obj.categoryId = g_categoryId; // default to fruits
        	    obj.sheetTypeId = sellerAdminFilterAkaden.hiddenSheetTypeId.getCaption(); 
        	    obj.buyerCombo = sellerAdminFilterAkaden.setBuyerCombo(sellerAdminFilterAkaden.lstBuyersCompanyId);
        	    //by Rhoda for add sku group seller combo- start
        	    obj.sellerCombo = sellerAdminFilterAkaden.setSellerCombo(sellerAdminFilterAkaden.lstSellersCompanyId);
        	    //by Rhoda for add sku group seller combo- start
        	    
        	    this.fireEvent('onSearchAkaden',[obj]);
        	    sellerAdminFilterAkaden.clearLists();
        	    sellerAdminFilterAkaden.panelFilter.destroy();
        	}
        },
        _lstbuyerscompanyid_onrender: function(profile) {
        	var ns = this, uictrl = profile.boxing();
            var behaviors = profile.box.getBehavior(),
                evOnItem = behaviors.ITEM;
            evOnItem.beforeKeydown=function(profile, e, src){
                if(linb.Event.getKey(e)[0]=='enter'){
                	sellerAdminFilterAkaden.searchBuyerCompany();
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
        }
    }
});