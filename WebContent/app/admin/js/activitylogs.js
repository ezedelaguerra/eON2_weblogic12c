
Class('App.activitylogs', 'linb.Com',{
    Initialize:function(){
       // global vars or functions here
       window.g_LangKey = "";
       window.g_saveMode = "";
       window.removedItems = [];
       window.g_userId = "";
       window.g_dpflag = "";
       window.g_removeMode = "";
       window.g_userItem = [];
    }, 
    Instance:{
        autoDestroy:true,           
        customAppend:function(parent,subId,left,top){
            return false;
        },        
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        },        
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Panel)
                .host(host,"panelCompany")
                .setZIndex(1)
                .setVisibility("visible") 
                .setCaption("$app.caption.operationLogLabel") //Label Translation
                .setCustomStyle({"PANEL":"overflow:auto;"})
            );
            
            host.panelCompany.append((new linb.UI.Group)
                .host(host,"activityLogPane")
                .setLeft(21)
                .setTop(11)
                .setWidth(1170)
                .setHeight(500)
                .setTabindex(1000)
                .setCaption("$app.caption.search") //Label Translation
                .setToggleBtn(false)
            );
            
            //First Line in Search Field
            //Labels
            host.activityLogPane.append((new linb.UI.Label)
                    .host(host,"userIdLbl")
                    .setLeft(100)
                    .setTop(30)
                    .setWidth(130)
                    .setBorder(true)
                    .setCaption("$app.caption.userid") //Label Translation
                    .setHAlign("center")
                    .setVAlign("middle")
                );
                       
            host.activityLogPane.append((new linb.UI.Label)
                    .host(host,"usernameLbl")
                    .setLeft(240)
                    .setTop(30)
                    .setWidth(130)
                    .setBorder(true)
                    .setCaption("$app.caption.username") //Label Translation
                    .setHAlign("center")
                    .setVAlign("middle")
                );
            
            host.activityLogPane.append((new linb.UI.Label)
                    .host(host,"sellerIdLbl")
                    .setLeft(380)
                    .setTop(30)
                    .setWidth(130)
                    .setBorder(true)
                    .setCaption("$app.caption.targetSellerIdLabel") //Label Translation
                    .setHAlign("center")
                    .setVAlign("middle")
                );
            
            host.activityLogPane.append((new linb.UI.Label)
                    .host(host,"buyerIdLbl")
                    .setLeft(520)
                    .setTop(30)
                    .setWidth(130)
                    .setBorder(true)
                    .setCaption("$app.caption.targetBuyerIdLabel") //Label Translation
                    .setHAlign("center")
                    .setVAlign("middle")
                );
            
            host.activityLogPane.append((new linb.UI.Label)
                    .host(host,"actionIdLbl")
                    .setLeft(660)
                    .setTop(30)
                    .setWidth(130)
                    .setBorder(true)
                    .setCaption("$app.caption.operationLabel") //Label Translation
                    .setHAlign("center")
                    .setVAlign("middle")
                );
            
            //First Line in Search Field
            //Inputs
            host.activityLogPane.append((new linb.UI.Input)
                    .host(host,"userIdInput")
                    .setDirtyMark(false)
                    .setLeft(100)
                    .setTop(50)
                    .setWidth(130)
                    .setType('number')
                    .afterUIValueSet(function (profile,oldValue,newValue,force,tag){
                    	if(newValue < 0){
                    		this.userIdInput.setUIValue(0);
                    		return false;
                    	} else {
                    		return true;
                    	}
                    })
                );
            
            host.activityLogPane.append((new linb.UI.Input)
                .host(host,"usernameInput")
                .setDirtyMark(false)
                .setLeft(240)
                .setTop(50)
                .setWidth(130)
            );
            
            host.activityLogPane.append((new linb.UI.Input)
                    .host(host,"sellerIdInput")
                    .setDirtyMark(false)
                    .setLeft(380)
                    .setTop(50)
                    .setWidth(130)
                    .setType('number')
                );
            
            host.activityLogPane.append((new linb.UI.Input)
                    .host(host,"buyerIdInput")
                    .setDirtyMark(false)
                    .setLeft(520)
                    .setTop(50)
                    .setWidth(130)
                    .setType('number')
                );
            
            host.activityLogPane.append((new linb.UI.ComboInput)
                    .host(host,"actionCombo")
                    .setDirtyMark(false)
                    .setInputReadonly(true)
                    .setLeft(660)
                    .setTop(50)
                    .setWidth(130)
                    .setType("listbox")
                    .setItems(this._getActionItems())
                );
            

            //Second Line in Search Field
            //Labels
            host.activityLogPane.append((new linb.UI.Label)
                    .host(host,"sheetLbl")
                    .setLeft(100)
                    .setTop(80)
                    .setWidth(130)
                    .setBorder(true)
                    .setCaption("$app.caption.sheettype") //Label Translation
                    .setHAlign("center")
                    .setVAlign("middle")
                );
            
            host.activityLogPane.append((new linb.UI.Label)
                    .host(host,"dateFromLbl")
                    .setLeft(240)
                    .setTop(80)
                    .setWidth(130)
                    .setBorder(true)
                    .setCaption("$app.caption.operationDateFromLabel") //Label Translation
                    .setHAlign("center")
                    .setVAlign("middle")
                );
            
            host.activityLogPane.append((new linb.UI.Label)
                    .host(host,"dateToLbl")
                    .setLeft(380)
                    .setTop(80)
                    .setWidth(130)
                    .setBorder(true)
                    .setCaption("$app.caption.operationDateToLabel") //Label Translation
                    .setHAlign("center")
                    .setVAlign("middle")
                );
            
            host.activityLogPane.append((new linb.UI.Label)
                    .host(host,"deliveryDateLbl")
                    .setLeft(520)
                    .setTop(80)
                    .setWidth(130)
                    .setBorder(true)
                    .setCaption("$app.caption.deliveryDate") //Label Translation
                    .setHAlign("center")
                    .setVAlign("middle")
                );
            
            
            //Second Line in Search Field
            //Inputs
            host.activityLogPane.append((new linb.UI.ComboInput)
                    .host(host,"sheetCombo")
                    .setDirtyMark(false)
                    .setInputReadonly(true)
                    .setLeft(100)
                    .setTop(100)
                    .setWidth(130) 
                    .setType("listbox")
                    .setItems(this._getSheetItems())
                );
            
            host.activityLogPane.append((new linb.UI.ComboInput)
                    .host(host,"cbiDateFrom")
                    .setDirtyMark(false)
                    .setLeft(240)
                    .setTop(100)
                    .setWidth(130)          
                    .setType("datepicker")
                    .onRender("_cbiDateFrom_onrender")
                    .onChange("_cbiDateFrom_onchange")
                );
            
            host.activityLogPane.append((new linb.UI.ComboInput)
                    .host(host,"cbiDateTo")
                    .setDirtyMark(false)
                    .setLeft(380)
                    .setTop(100)
                    .setWidth(130)               
                    .setType("datepicker")
                    .onRender("_cbiDateTo_onrender")
                    .onChange("_cbiDateTo_onchange")
                );
            
            host.activityLogPane.append((new linb.UI.ComboInput)
                    .host(host,"cbiDeliveryDate")
                    .setDirtyMark(false)
                    .setLeft(520)
                    .setTop(100)
                    .setWidth(130)               
                    .setType("datepicker")
                    .onRender("_cbiDeliveryDate_onrender")
                    .onChange("_cbiDeliveryDate_onchange")
                );
            
            host.activityLogPane.append((new linb.UI.Input)
                    .host(host,"iptDateFrom")
                    .setLeft(240)
                    .setTop(100)
                    .setWidth(115)               
                    .setDirtyMark(false)
                    .setBorder(true)
                    .onChange("_iptdatefrom_onChange")
                );
                
            host.activityLogPane.append((new linb.UI.Input)
                    .host(host,"iptDateTo")
                    .setLeft(380)
                    .setTop(100)
                    .setWidth(115)               
                    .setDirtyMark(false)
                    .setBorder(true)
                    .onChange("_iptdateto_onChange")                
            );
            
            host.activityLogPane.append((new linb.UI.Input)
                    .host(host,"iptDeliveryDate")
                    .setLeft(520)
                    .setTop(100)
                    .setWidth(115)               
                    .setDirtyMark(false)
                    .setBorder(true)             
            );

            host.activityLogPane.append((new linb.UI.TreeGrid)
                    .host(host,"activityListGrid")
                    .setDock("none")
                    .setLeft(100)
                    .setTop(150)
                    .setWidth(980)
                    .setHeight(270)
                    .setEditable(true)
                    .setRowHandler(false)
                    .setColSortable(false)
                    
                );
            
            host.activityLogPane.append((new linb.UI.Button)
                    .host(host,"btnSearch")
                    .setTop(435)
                    .setLeft(100)
                    .setCaption("$app.caption.search") //Label Translation
                    .setCustomStyle({
                        "KEY":{	
                            "font-weight":"bold"
                        }
                    })
                    .onClick("_btnSearchOnClick")
                );
            
            host.activityLogPane.append((new linb.UI.Button)
                    .host(host,"btnClear")
                    .setTop(435)
                    .setLeft(240)
                    .setCaption("$app.caption.clearLabel") //Label Translation
                    .onClick("_btnClearOnClick")
                    .setCustomStyle({
                        "KEY":{
                            "font-weight":"bold"
                        }
                    })
                );
            
          
            host.activityLogPane.append((new linb.UI.PageBar)
                    .host(host,"pageBar")
                    .setTop(435)
                    .setLeft(400)
                    .setCaption("$app.caption.page") //Label Translation
                    .setValue("1:1:1")
                    .setPrevMark("<<")
                    .setNextMark(">>")
                    .onClick("_pageBar_onClick")
                    
                );
            
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events: {
        	"onRender":"_onRender", "onReady" : "_onready"
        }, 
        _onRender:function(page, threadid){
        	
        },
        _onready: function() {
        	activityLogs = this;
        	this.activityListGrid.setHeader([{"id":"userId","type":"label","width":"63","caption":"$app.caption.userid"}, //Label Translation
        	                                 {"id":"username","type":"label","width":"80","caption":"$app.caption.username"}, //Label Translation
        	                                 {"id":"activityDate","type":"label","width":"70","caption":"$app.caption.exceldategroup"}, //Label Translation
        	                                 {"id":"timestamp","type":"label","width":"170","caption":"$app.caption.timestampLabel"}, //Label Translation
        	                                 {"id":"targetSellerId","type":"label","width":"100","caption":"$app.caption.targetSellerIdLabel"}, //Label Translation
        	                                 {"id":"targetBuyerId","type":"label","width":"200","caption":"$app.caption.targetBuyerIdLabel"}, //Label Translation
        	                                 {"id":"deliveryDate","type":"label","width":"90","caption":"$app.caption.deliveryDate"}, //Label Translation
        	                                 {"id":"sheetName","type":"label","width":"87","caption":"$app.caption.sheettype"}, //Label Translation
        	                                 {"id":"action","type":"label","width":"100","caption":"$app.caption.operationLabel"}, //Label Translation
        	                                 ]);
        	var today = new Date();
        	var YYYYMMDD = toYYYYMMDD(today);
        	var yyyy = YYYYMMDD.substr(0,4);
            var mm = YYYYMMDD.substr(4,2);
            var dd = YYYYMMDD.substr(6,2);
            var todayDate = yyyy + "/" + mm + "/" + dd
        	
            this.iptDateFrom.setValue(todayDate);
            this.iptDateTo.setValue(todayDate);
            this.iptDeliveryDate.setValue(todayDate);
        },
        
        
        _getActionItems : function (){
        	//Label Translation
        	var sheetItems = [{"id" : "","caption" : "$app.caption.all" , value : ""},//Label Translation
        	                  {"id" : "SAVED","caption" : "$app.caption.save" , value : "SAVED"}, //Label Translation
        	                 {"id" : "PUBLISHED","caption" : "$app.caption.publish" , value : "PUBLISHED"}, //Label Translation
        	                 {"id" : "UNPUBLISHED","caption" : "$app.caption.unpublish" , value : "UNPUBLISHED"}, //Label Translation
        	                 {"id" : "FINALIZED","caption" : "$app.caption.finalize" , value : "FINALIZED"}, //Label Translation
        	                 {"id" : "UNFINALIZED","caption" : "$app.caption.unfinalize" , value : "UNFINALIZED"} //Label Translation
        	                 ];
        	
        	return sheetItems;
        },
        
        _getSheetItems : function (){
        	//Label Translation
        	var sheetItems = [{"id" : "","caption" : "$app.caption.all" , value : ""},//Label Translation
        	                  {"id" : "ORDER","caption" : "$app.caption.ordersheet" , value : "ORDER"},//Label Translation
        	                 {"id" : "ALLOCATION","caption" : "$app.caption.allocationsheet" , value : "ALLOCATION"},//Label Translation
        	                 ];
        	
        	return sheetItems;
        },
        
        _cbiDateFrom_onchange:function (profile, oldValue, newValue) {
        	
        	// Just set the input date
        	var new_date = linb.Date.format(newValue,"yyyy/mm/dd");
        	this.iptDateFrom.setUIValue(new_date);
        	//this.cbiDateFrom.collapse();
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
        		}
        	}
        	
        	if (hasError == true) {
        		var empty=[];
        		this.iptDateFrom.setUIValue("");
        		this.iptDateFrom.setValue("");
        		//downloadcsv.lstBuyers.setItems(empty, true);
        	} else {
        		//downloadcsv.loadBuyerList(dateFrom, dateTo);
        	}
        	
        },
        _cbiDateTo_onchange:function (profile, oldValue, newValue) {
        	
        	// Just set the input date
        	var new_date = linb.Date.format(newValue,"yyyy/mm/dd");
        	this.iptDateTo.setUIValue(new_date);
        	//this.cbiDateTo.collapse();
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
        		}
        	}
        	
        	if (hasError == true) {
        		var empty=[];
        		this.iptDateTo.setUIValue("");
        		this.iptDateTo.setValue("");
        		//downloadcsv.lstBuyers.setItems(empty, true);
        	} else {
        		//downloadcsv.loadBuyerList(dateFrom, dateTo);
        	}
        	
        },
        
        _cbiDeliveryDate_onchange:function (profile, oldValue, newValue) {
        	// Just set the input date
        	var new_date = linb.Date.format(newValue,"yyyy/mm/dd");
        	this.iptDeliveryDate.setUIValue(new_date);
        }, 
        
        _cbiDateFrom_onrender:function (profile) {
            //profile.boxing().setValue(linb.Date.add(new Date, 'd',1));
            profile.getSubNode('TODAY').css('display','none');
            profile.getSubNode('BODY').first(2).css('display','none');
            profile.getSubNode('W',true).css('display','none');
            linb(profile.getSubNode('H',true).get(0)).css('display','none');
        },
        
        _cbiDateTo_onrender:function (profile) {
        	profile.getSubNode('TODAY').css('display','none');
            profile.getSubNode('BODY').first(2).css('display','none');
            profile.getSubNode('W',true).css('display','none');
            linb(profile.getSubNode('H',true).get(0)).css('display','none');
        },
        
        _cbiDeliveryDate_onrender:function (profile) {
        	profile.getSubNode('TODAY').css('display','none');
            profile.getSubNode('BODY').first(2).css('display','none');
            profile.getSubNode('W',true).css('display','none');
            linb(profile.getSubNode('H',true).get(0)).css('display','none');
        },
        
        _btnSearchOnClick:function (profile) {
        	var userId = this.userIdInput.getUIValue();
        	var username = this.usernameInput.getUIValue();
        	var sellerId = this.sellerIdInput.getUIValue();
        	var buyerId = this.buyerIdInput.getUIValue();
        	var action = this.actionCombo.getUIValue();
        	var sheetName = this.sheetCombo.getUIValue();
        	var dateFrom = this.iptDateFrom.getUIValue();
        	var dateTo = this.iptDateTo.getUIValue();
        	var deliveryDate = this.iptDeliveryDate.getUIValue();
        	
        	var reqFields1 = new Array();
        	reqFields1[0] = this.iptDateFrom;
        	reqFields1[1] = this.iptDateTo;
        	
        	if(checkIfFieldIsValid(reqFields1, "", "", 0)){
        		activityLogs._setPageBarTotalCount(username,action,sheetName,dateFrom,dateTo);
        		var url = "/eON/getActivityLogList.json?userId="+userId+"&username="+username+"&sellerId="+sellerId+"&buyerId="+buyerId+"&action="+action+
        		"&sheetName="+sheetName+"&dateFrom="+dateFrom+"&dateTo="+dateTo+"&deliveryDate="+deliveryDate+"&endCount=101";
        		activityLogs.activityListGrid.busy();
        		linb.Ajax(
     	                url,
     	                null,
     	                function (response) {
     	                	validateResponse(response);
    	                	var o = _.unserialize(response);
    	                	var rows = o.gridData;
    	                	activityLogs.activityListGrid.setRows(eval(rows)); //IE safe
    	                	activityLogs.activityListGrid.free();
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
        	}
        },
        
        _setPageBarTotalCount:function (username,action,sheetName,dateFrom,dateTo) {
        	var userId = this.userIdInput.getUIValue();
        	var username = this.usernameInput.getUIValue();
        	var sellerId = this.sellerIdInput.getUIValue();
        	var buyerId = this.buyerIdInput.getUIValue();
        	var action = this.actionCombo.getUIValue();
        	var sheetName = this.sheetCombo.getUIValue();
        	var dateFrom = this.iptDateFrom.getUIValue();
        	var dateTo = this.iptDateTo.getUIValue();
        	var deliveryDate = this.iptDeliveryDate.getUIValue();
        	var url = "/eON/getActivityLogCount.json?userId="+userId+"&username="+username+"&sellerId="+sellerId+"&buyerId="+buyerId+"&action="+action+
        		"&sheetName="+sheetName+"&dateFrom="+dateFrom+"&dateTo="+dateTo+"&deliveryDate="+deliveryDate;
        	activityLogs.pageBar.busy();
        	linb.Ajax(
 	                url,
 	                null,
 	                function (response) {
 	                	validateResponse(response);
	                	var o = _.unserialize(response);
	                	var count = o.activityLogCount;
	                	if(count != undefined){
	                		var totalPage = Math.ceil(count/100);
	                		activityLogs.pageBar.setValue("1:1:" + totalPage);
	                		activityLogs.pageBar.free();
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
        
        
        _pageBar_onClick:function (profile, page) {
        	var userId = this.userIdInput.getUIValue();
        	var username = this.usernameInput.getUIValue();
        	var sellerId = this.sellerIdInput.getUIValue();
        	var buyerId = this.buyerIdInput.getUIValue();
        	var action = this.actionCombo.getUIValue();
        	var sheetName = this.sheetCombo.getUIValue();
        	var dateFrom = this.iptDateFrom.getUIValue();
        	var dateTo = this.iptDateTo.getUIValue();
        	var deliveryDate = this.iptDeliveryDate.getUIValue();
        	profile.boxing().setPage(page);
        	var rowEnd = (page * 100) + 1;
        	var url = "/eON/getActivityLogList.json?userId="+userId+"&username="+username+"&sellerId="+sellerId+"&buyerId="+buyerId+"&action="+action+
        		"&sheetName="+sheetName+"&dateFrom="+dateFrom+"&dateTo="+dateTo+"&deliveryDate="+deliveryDate+"&endCount="+rowEnd;
        	activityLogs.activityListGrid.busy();
        	linb.Ajax(
 	                url,
 	                null,
 	                function (response) {
 	                	validateResponse(response);
	                	var o = _.unserialize(response);
	                	var rows = o.gridData;
	                	activityLogs.activityListGrid.setRows(eval(rows)); //IE safe
	                	activityLogs.activityListGrid.free();
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
        
        _btnClearOnClick:function(){
        	var today = new Date();
        	var YYYYMMDD = toYYYYMMDD(today);
        	var yyyy = YYYYMMDD.substr(0,4);
            var mm = YYYYMMDD.substr(4,2);
            var dd = YYYYMMDD.substr(6,2);
            var todayDate = yyyy + "/" + mm + "/" + dd
            
        	this.userIdInput.setUIValue("");
        	this.usernameInput.setUIValue("");
        	this.sellerIdInput.setUIValue("");
        	this.buyerIdInput.setUIValue("");
        	this.actionCombo.setUIValue("");
        	this.sheetCombo.setUIValue("");
        	this.iptDateTo.setUIValue(todayDate);
        	this.iptDateFrom.setUIValue(todayDate);
            this.iptDeliveryDate.setUIValue(todayDate);
            
            this.activityListGrid.setRows([]);
        }
         
    }
});