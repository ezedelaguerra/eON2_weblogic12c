/*
 	Copyright Freshremix Asia Software Corporation
	date		name		version	changes
	==============================================
	20120726	jovsab		v11		Redmine 286 - get calendar date from the server
	20120913	lele		chrome	Redmine 880 - Chrome compatibility
 * */
var g_LangKey;

Class('App.filter', 'linb.Com', {
	autoDestroy:true,
    Instance: {
        iniComponents:function(){
		    var host=this, children=[], append=function(child){children.push(child.get(0));};
		
		    append((new linb.UI.Dialog)
		    	.host(host,"panelFilter")
		    	.setDock("none")
		    	.setLeft(90)
		    	.setTop(100)
		    	.setWidth(910)
		    	// ENHANCEMENT 20120913: Lele - Redmine 880
		    	.setHeight(400)
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
                .setHeight(310)
                .setToggleBtn(false)
                .setCaption("")
            );
		    
		    host.groupDealingPattern.append((new linb.UI.Label)
                .host(host,"labelBuyer")
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
	                .setValue("")
	                .setDirtyMark(false)
	                .onItemSelected("_lstSellersCompany_onitemselected")
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
                .host(host,"lstSellersCompanyId")
                .setLeft(290)
                .setTop(20)
                .setWidth(200)
                .setHeight(220)
                .setSelMode("multi")
                .setValue("")
                .setDirtyMark(false)
                .onChange("_categoriesTabOnRender")
                .onItemSelected("_lstsellerscompanyid_onitemselected")                
                .onRender("_lstsellerscompanyid_onrender")
            );
            
            host.groupDealingPattern.append((new linb.UI.Label)
                .host(host,"labelMark1")
                .setLeft(290)
                .setTop(250)
                .setWidth(200)
                .setCaption("$app.caption.filterSellerPubMark")
                .setHAlign("left")
            );
            
            host.groupDealingPattern.append((new linb.UI.Label)
                .host(host,"labelMark2")
                .setLeft(290)
                .setTop(270)
                .setWidth(200)
                .setCaption("$app.caption.filterBuyerFinMark")
                .setHAlign("left")
            );
            
            host.panelFilter.append((new linb.UI.Button)
                .host(host,"btnSearchMode")
                .setLeft(690)
                .setTop(325)
                .setWidth(80)
                .setCaption("$app.caption.search")
                .onClick("_btnsearchmode_onclick")
            );
            
            host.panelFilter.append((new linb.UI.Button)
                .host(host,"btnCancelMode")
                .setLeft(780)
                .setTop(325)
                .setWidth(80)
                .setCaption("$app.caption.cancel")
                .onClick("_btncancelmode_onclick")
            );
            
            host.panelFilter.append((new linb.UI.Group)
                .host(host,"groupDate")
                .setLeft(10)
                .setTop(1)
                .setWidth(365)
                .setHeight(310)
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
                //.onRender("_inptDateFrom_onRender")
                /* DELETION END 20120726 */ 
                .onFocus("_inptDateFrom_onFocus")
                .onChange("_inptDateFrom_onChange")
                // ENHANCEMENT 20120913: Lele - Redmine 880
                .setCustomStyle({'INPUT':'background-color:#e6e273'})
            );
            
            host.groupDate.append((new linb.UI.Input)
                .host(host,"inptDateTo")
                .setLeft(230)
                .setTop(168)
                .setWidth(100)
                .setDirtyMark(false)
                .onFocus("_inptDateTo_onFocus")
                .onChange("_inptDateTo_onChange")
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
		    
		    return children;
		},
	    clearLists: function() {
	    	buyerFilter.lstSellersCompany.setItems([]);
	        buyerFilter.lstSellersCompanyId.setItems([]);
	        /* DELETION START 20120726 JOVSAB - Not needed call the function */
//    	    buyerFilter.datepickerSearchDate.setValue(linb.Date.add(new Date, 'd',1));
//    	    buyerFilter.inptDateFrom.setValue(this.formatDate(linb.Date.add(new Date, 'd',1)), true);
//	        buyerFilter.inptDateTo.setValue("", true);
	        /* DELETION END 20120726 JOVSAB */
	        buyerFilter.rbDateOption.setValue("D",true);
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
        	buyerFilter = page;
        	buyerFilter.hiddenPane.hide();
        	/* ENHANCEMENT START 20120726: JOVSAB - Calling the function of getting the server date */
        	buyerFilter.getDateServerValue();
            /* ENHANCEMENT END 20120726: */
        	/* DELETION START 20120726 JOVSAB - Redmine 286*/
        	// buyerFilter.populateSellerCompany();
        	/* DELETION END 20120726 */
        },
        _onready: function() {
        	buyerFilter = this;
            g_LangKey = linb.getLang();
        }, 
        _btncancelmode_onclick:function (profile, e, src, value) {
//        	buyerFilter.panelFilter.setDisplay('none');
//        	buyerFilter.clearLists();        	
//        	buyerFilter.populateSellerCompany();
        	this.destroy();
        }, 
        _btnsearchmode_onclick:function (profile, e, src, value) {
        	buyerFilter.searchBuyerCompany();
        },  
        _lstSellersCompany_onitemselected:function (profile, item, src) {
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
            
            var tmp = this.lstSellersCompany.getUIValue();
            if (tmp != "" && tmp != ";0") {
	        	var objDateFrom = new Date(buyerFilter.inptDateFrom.getUIValue());
	        	var objDateTo = new Date(buyerFilter.inptDateTo.getUIValue());
	        	
	        	var url = "/eON/filter/getUser.json?dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo);
	        	url = url + "&dealingPatternId="+this.hiddenDealingPatternId.getCaption();
	        	url = url + "&companies="+tmp;
	        	url = url + "&sheetTypeId=" + buyerFilter.hiddenSheetTypeId.getCaption();
	        	var id = Math.random() * url.length;
	        	buyerFilter.disableControls();
	            linb.Ajax(
	                url+"&id="+id,
	                null,
	                function (response) {
	                	//buyerFilter.responseCheck(response);
	                	validateResponse(response);
	                	var o = _.unserialize(response);
	                	var items = o.response;
	                	buyerFilter.lstSellersCompanyId.setItems(items, true);
	                	buyerFilter.lstSellersCompanyId.refresh();
	                	buyerFilter.enableControls();
	                }, 
	                function(msg) {
	                        linb.message("onFail: " + msg);
	                    }, null, {
	                        method : 'GET',
	                        retry : 0
	                    }
	            ).start();
            } else {
            	buyerFilter.lstSellersCompanyId.setItems(null, true);
            }
        },
        _datepickerSearchDate_onrender:function (profile) {
            profile.getSubNode('TODAY').css('display','none');
            profile.getSubNode('BODY').first(2).css('display','none');
            profile.getSubNode('W',true).css('display','none');
            linb(profile.getSubNode('H',true).get(0)).css('display','none');
        },
        /* ENHANCEMENT START: JOVSAB - This code is for getting the server date and return to calendar */
        getDateServerValue: function () {
        	var url = "/eON/getServerDate.json";  
            linb.Ajax(
                url,
                null,
                function (response) {
                	var obj = _.unserialize(response);
                    buyerFilter.datepickerSearchDate.setValue(linb.Date.parse(obj.response));
                    buyerFilter.inptDateFrom.setValue(obj.response);
                    // Call method to populate seller company
                    buyerFilter.populateSellerCompany();
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
        /* ENHANCEMENT END: JOVSAB */
        _lstsellerscompanyid_onitemselected:function (profile, item, src) {
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
        }, 
        _datepickersearchdate_aftervalueupdated:function (profile, oldValue, newValue) {
        	var hasError = false;
            var d = newValue;
            var newdateresult = buyerFilter.formatDate(d);
            var option = this.rdDateRange.getUIValue();
            if (this.rbDateOption.getUIValue() == 'W') {
	        	var to_d = linb.Date.add(d,'d',6);
	        	this.inptDateFrom.setUIValue(newdateresult);
	        	this.inptDateTo.setUIValue(buyerFilter.formatDate(to_d));
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
	                    }
	                    else {
	                    	this.inptDateTo.setUIValue(newdateresult);
	                    }
	            	}
	            }
            }
            //not needed because input fields for dates already calls populateSellerCompany
//            if (hasError == false) {
//            	buyerFilter.populateSellerCompany();
//            }
        }, 
        _rbdateoption_onitemselected:function (profile, item, src) {
        	var tmp = this.rdDateRange.getItems();
        	if (this.rbDateOption.getUIValue() == 'W') {
	        	var d = this.datepickerSearchDate.getUIValue();
	        	var dateFrom = buyerFilter.formatDate(d);
	            this.inptDateFrom.setUIValue(dateFrom);
	            
	        	d = linb.Date.add(new Date(dateFrom),'d',6);
	        	this.inptDateTo.setUIValue(buyerFilter.formatDate(d));
	        	this.inptDateTo.setDisabled(true);
	        	tmp[1].disabled = true;
	        	
	        	buyerFilter.populateSellerCompany();
        	} else {
        		this.inptDateTo.setDisabled(false);
        		this.inptDateTo.setUIValue("");
                tmp[1].disabled = false;
        	}
        	this.rdDateRange.setItems(tmp);
        },
        allToSellerIds : function (linbComp, newValue) {
        	var tmp = "";
        	if (newValue == "0" || newValue.startsWith("0;")) {
        		var items = linbComp.getItems();
        		for (var i=1; i<items.length; i++) {
        			tmp = tmp + items[i].id + ";";
        		}
        	} else {
        		tmp = newValue;
        	}
        	//alert('tmp' + tmp);
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
        },_inptDateTo_onChange : function (profile) {
        	var hasError = false;
            var newdateresult = this.inptDateTo.getUIValue();
            var option = this.rdDateRange.getUIValue();
        	var dateFrom = this.inptDateFrom.getUIValue();
        	
        	if (dateFrom == "" || isDate(dateFrom) == false) {
            	// alert("Date From: should not be empty");
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
        		buyerFilter.lstSellersCompany.setItems(empty, true);
        		buyerFilter.lstSellersCompanyId.setItems(empty, true);
        		return;
        	}else{
        		buyerFilter.populateSellerCompany();
            }
        },_inptDateFrom_onChange : function (profile) {
        	var hasError = false;
            var newdateresult = this.inptDateTo.getUIValue();
            var option = this.rdDateRange.getUIValue();
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
        		buyerFilter.lstSellersCompany.setItems(empty, true);
        		buyerFilter.lstSellersCompanyId.setItems(empty, true);
        		return;
        	}else{
        		buyerFilter.populateSellerCompany();
            }	 
        },_categoriesTabOnRender: function() {
        	var url = "/eON/getCategoryTabs.json";
        	var strArr = buyerFilter.allToSellerIds(buyerFilter.lstSellersCompanyId, buyerFilter.lstSellersCompanyId.getUIValue());
         	if (strArr == "" || strArr == ";0" || strArr.indexOf(";0;") !== -1) {
         		return;
         	}
        	buyerFilter.disableControls();
        	linb.Ajax(
    	        url + "?obj="+strArr,
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
             	buyerFilter.enableControls();
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
        }, populateSellerCompany : function () {
        	// query for company 
        	var objDateFrom = new Date(buyerFilter.inptDateFrom.getUIValue());
        	var objDateTo = new Date(buyerFilter.inptDateTo.getUIValue());
         	var url = "/eON/filter/getCompany.json?dateFrom="+toYYYYMMDD(objDateFrom)+"&dateTo="+toYYYYMMDD(objDateTo);
         	url = url + "&dealingPatternId="+this.hiddenDealingPatternId.getCaption();
         	url = url + "&userCompanyId=" + header.lblCompanyId.getCaption();
         	
         	var id = Math.random() * url.length;
         	buyerFilter.disableControls();
         	linb.Ajax(
                 url + "&id=" + id,
                 null,
                 function (response) {
                 	//sellerFilter.responseCheck(response);
                 	validateResponse(response);
                 	var o = _.unserialize(response);
                 	var items = o.response;
                 	buyerFilter.lstSellersCompany.setItems(items, true);
                 	buyerFilter.lstSellersCompany.refresh();
                 	buyerFilter.enableControls();
                 }, 
                 function(msg) {
                         linb.message("失敗： " + msg);
                     }, null, {
                         method : 'GET',
                         retry : 0
                     }
             ).start();
             
         	 // reset value of user level combo box
             this.lstSellersCompanyId.setItems(null, true);
             this.lstSellersCompanyId.refresh();
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
        	
        	if (this.lstSellersCompanyId.getUIValue() == '') {
        		hasErrors = true;
        		alert(linb.Locale[g_LangKey].app.caption['errorNoSelectedSellers']);
        		return;
        	}
        	
        	if (hasErrors == false) {
        		// create parameter object
        		var objDateFrom = new Date(buyerFilter.inptDateFrom.getUIValue());
        		
        		// ENHANCEMENT START 20120913: Lele - Redmine 880
        		if (!buyerFilter.inptDateTo.getUIValue()) {
        			buyerFilter.inptDateTo.setValue(buyerFilter.inptDateFrom.getUIValue());
                }
                // ENHANCEMENT END 20120913:
        		
        		var objDateTo = new Date(buyerFilter.inptDateTo.getUIValue());
        		var obj = new Object();
        		obj.startDate = toYYYYMMDD(objDateFrom);
        	    obj.endDate = toYYYYMMDD(objDateTo);
        	    obj.selectedSellerCompany = buyerFilter.allToSellerIds(buyerFilter.lstSellersCompany, buyerFilter.lstSellersCompany.getUIValue());
        	    obj.selectedSellerID = buyerFilter.allToSellerIds(buyerFilter.lstSellersCompanyId, buyerFilter.lstSellersCompanyId.getUIValue());
        	    obj.selectedDate = toYYYYMMDD(objDateFrom);
        	    obj.categoryId = categoryIndex; // default to fruits
        	    obj.sheetTypeId = buyerFilter.hiddenSheetTypeId.getCaption(); 
        	    var user = new Object();
        	    user.id = header.lbluseridinfo.getCaption();
        	    user.caption = header.lblusernameinfo.getCaption();
        	    //alert(_.serialize(user));
        	    var list = [];
        	    list[0] = user;
        	    obj.buyerCombo = list;
        	    this.fireEvent('onSearch',[obj]);
        		//this.destroy();        	    
//        	    this.panelFilter.setDisplay('none');
        	    buyerFilter.clearLists();
        	    this.destroy();
        	}
        },
        _lstsellerscompanyid_onrender: function(profile) {
        	var ns = this, uictrl = profile.boxing();
            var behaviors = profile.box.getBehavior(),
                evOnItem = behaviors.ITEM;
            evOnItem.beforeKeydown=function(profile, e, src){
                if(linb.Event.getKey(e)[0]=='enter'){
                	buyerFilter.searchBuyerCompany();
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
        	this.lstSellersCompany.setDisabled(true);
        	this.rdDateRange.setDisabled(true);
        	this.btnSearchMode.setDisabled(true);
        	this.btnCancelMode.setDisabled(true);
        },
        
        enableControls : function () {
        	this.datepickerSearchDate.setDisabled(false);
        	this.inptDateFrom.setDisabled(false);
        	this.inptDateTo.setDisabled(false);
        	this.lstSellersCompany.setDisabled(false);
        	this.rdDateRange.setDisabled(false);
        	this.btnSearchMode.setDisabled(false);
        	this.btnCancelMode.setDisabled(false);
        }
    }
});