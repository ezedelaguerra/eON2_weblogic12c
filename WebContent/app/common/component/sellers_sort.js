var g_companyId;
var g_isEdit=0;//toggle for edit
Class('App.sellersSort', 'linb.Com',{
    autoDestroy:true,
    Instance:{    
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};

            append((new linb.UI.Panel)
                .host(host,"sellerSortPanel")
                .setDock("none")
                .setLeft(180)
                .setTop(59)
                .setWidth(820)
                .setHeight(520)
                .setZIndex(1)
                .setCaption("<center><b>$app.caption.sortseller</b></center>")
            );

            host.sellerSortPanel.append((new linb.UI.Group)
                .host(host,"companyGrp")
                .setLeft(21)
                .setTop(11)
                .setWidth(431)
                .setHeight(50)
                .setTabindex(1000)
                .setCaption("")
                .setToggleBtn(false)
            );
            
            host.companyGrp.append((new linb.UI.ComboInput)
                .host(host,"cbiCompanyname")
                .setLeft(70)
                .setTop(1)
                .setWidth(300)
                .onChange("_cbiCompanyname_onchange")
            );
            
            host.companyGrp.append((new linb.UI.Label)
                .host(host,"label42")
                .setLeft(10)
                .setTop(5)
                .setWidth(60)
                .setTabindex(1001)
                .setCaption("<b>$app.caption.companyname :</b>")
            );
            
            host.sellerSortPanel.append((new linb.UI.Group)
                .host(host,"companySortgrp")
                .setLeft(40)
                .setTop(60)
                .setWidth(400)
                .setHeight(420)
                .setCaption("")
            );

           
            host.companySortgrp.append((new linb.UI.List)
                    .host(host,"sellerlist")
                    .setDirtyMark(false)
                    .setTop(10)
                    .setHeight(345)
                    .setWidth(300)
                    .setLeft(20)
                    .setSelMode("multi")
                );
            
            host.companySortgrp.append((new linb.UI.Button)
                .host(host,"btnUp")
                .setTips("Up")
                .setLeft(360)
                .setTop(70)
                .setWidth(20)
                .setHeight(30)
                .setCaption("↑")
                .onClick("_upbtn_onclick")
            );

            host.companySortgrp.append((new linb.UI.Button)
                .host(host,"btnDowm")
                .setTips("Down")
                .setLeft(360)
                .setTop(110)
                .setWidth(20)
                .setHeight(30)
                .setCaption("↓")
                .onClick("_downbtn_onclick")
            );
            host.companySortgrp.append((new linb.UI.Button)
                .host(host,"btnsave")
                .setLeft(190)
                .setTop(370)
                .setWidth(70)
                .setCaption("$app.caption.save")
                .onClick("_btnsave_onclick")
            );

            host.companySortgrp.append((new linb.UI.Button)
                .host(host,"_btnclose")
                .setLeft(270)
                .setTop(370)
                .setWidth(70)
                .setCaption("$app.caption.close")
                .onClick("_btnclose_onclick")
            );

            return children;
            // ]]code created by jsLinb UI Builder
        },
        events:{
            "onReady" : "_onready", "onRender":"_onrender", "onDestroy":"_ondestroy",
            "beforeCreated":"_onbeforecreated"
        },
        _ondestroy:function (com) {
            g_sortWindow = null;
        },
        _onrender:function(page, threadid){
        	sellersSort = page;
        	g_isEdit=0;
        },
        _onready:function() {
        	sellersSort = this;
        },
        _onbeforecreated:function(com, threadid) {
        },
        customAppend:function(parent,subId,left,top){
            return false;
        },
        iniResource:function(com, threadid){
        },
        iniExComs:function(com, hreadid){
        },

        _btnclose_onclick:function (profile, e, src, value) {
        	if (sellersSort.confirmNavigation()) {
        		if(g_userRole != "ROLE_SELLER_ADMIN")
        			this.fireEvent('onCloseCompanySort');
        		else g_currentId = 0;
        		this.destroy();
        	}
        },
        _btnsave_onclick:function (profile, e, src, value) {
            sellersSort.saveSellerSorting();
        },
        saveSellerSorting:function() {
        	var rightItems = sellersSort.sellerlist.getItems();
        	var sellerSortList = new Array();
        	for (var i=0; i<rightItems.length; i++) {
        		var sellerSorts = new Object();
        		sellerSorts.sellerId = rightItems[i].id;
        		sellerSorts.sorting = i+1;
        		sellerSortList.push(sellerSorts);
        	}
        	
        	var url = "/eON/saveSellersSort.json?param=" + _.serialize(sellerSortList);
            linb.Ajax(
                url,
                sellerSortList,
                function (response) {
                    validateResponse(response);
                    alert(linb.Locale[linb.getLang()].app.caption['alertsaved']);
                    g_isEdit=0;
                    sellersSort.sellerlist.refresh();
                },
                function(msg) {
                        linb.message("失敗： " + msg);
                    }, null, {
                        method : 'POST',
                        retry : 0
                    }
            ).start();
        },
        _upbtn_onclick:function (profile, e, src, value) {
        	g_isEdit=1;
        	this.sortUpDown("up");
        },
        _downbtn_onclick:function (profile, e, src, value) {
        	g_isEdit=1;
            this.sortUpDown("down");
        },
        sortUpDown:function(direction) {
        	if (sellersSort.sellerlist.getItems().length == 0 ||
        			sellersSort.sellerlist.getUIValue() == null ||
        			sellersSort.sellerlist.getUIValue().length == 0)
        		return;
        	var uiVal = sellersSort.sellerlist.getUIValue();
        	var rightSelects = sellersSort.sellerlist.getUIValue().split(";");
        	var rightItems = sellersSort.sellerlist.getItems();
        	var rightIds = new Array();
        	var rightSelIds = new Array();
        	for(var i=0; i<rightItems.length; i++){
        		_.arr.insertAny(rightIds, rightItems[i].id);
        		if(_.arr.indexOf(rightSelects, rightItems[i].id) >= 0) {
        			_.arr.insertAny(rightSelIds, rightItems[i].id);
        		}
            }
        	var dontMove = false;
        	if (direction == "up") {
	        	for(var i=0; i<rightSelIds.length; i++) {
	        		var thisId = rightSelIds[i];
	        		var curPos = _.arr.indexOf(rightIds, thisId);
	        		var nexPos = curPos - 1;
	        		if (nexPos < 0) {
	        			dontMove = true;
	        			break;
	        		}
	        		
	        		_.arr.removeFrom(rightIds, curPos);
	        		_.arr.insertAny(rightIds, thisId, nexPos);
	        	}
        	}
        	else {
        		for(var i=rightSelIds.length-1; i>=0; i--) {
	        		var thisId = rightSelIds[i];
	        		var curPos = _.arr.indexOf(rightIds, thisId);
	        		var nexPos = curPos + 1;
	        		if (nexPos > rightIds.length-1) {
	        			dontMove = true;
	        			break;
	        		}
	        		_.arr.removeFrom(rightIds, curPos);
	        		_.arr.insertAny(rightIds, thisId, nexPos);
	        	}
        	}
        	if (dontMove) return;
        	var newRightItems = new Array();
        	for(var i=0; i<rightIds.length; i++) {
        		var thisId = rightIds[i];
        		for(var j=0; j<rightItems.length; j++) {
        			if(thisId == rightItems[j].id)
        				newRightItems.push(rightItems[j]);
        		}	
        	}
        	sellersSort.sellerlist.setItems(newRightItems);
        	sellersSort.sellerlist.refresh();
        	sellersSort.sellerlist.setUIValue(uiVal);
        },
		loadSellers: function (companyId) {
			var obj = new Object();
	        obj.companyId = companyId;
	        linb.Ajax(
    	        "/eON/initSellersSort.json",
    	        obj,
    	        function (response) {
                    validateResponse(response);
                    var obj = new Object();
                    var items = new Array();
                    obj = _.unserialize(response);
                    for(var i=0; i<obj.sellersSorts.length; i++){
                    	var seller = obj.sellersSorts[i].seller;
                        items.push({"id":seller.userId,
                            "caption":seller.name});
                    }
                    sellersSort.sellerlist.setItems(items);
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
		},
        _cbiCompanyname_onchange:function (profile, oldValue, newValue) {
        	var id;
        	var items = sellersSort.cbiCompanyname.getItems();
        	for (var i=0; i<items.length; i++) {
    			var thisObj = items[i];
    			if (newValue == thisObj.caption) {
    				id = thisObj.id;
    				sellersSort.cbiCompanyname.setValue(thisObj.caption);
    				break;
    			}
    		}
        	g_companyId = id;
        	sellersSort.loadSellers(id);
        	
        },
        checkForDirtyTable : function () {
        	if(g_isEdit == 1)
        		return true;
        	else
        		return false;
        },
        confirmNavigation : function () {
        	if (sellersSort.checkForDirtyTable()) {
        		var ans = confirm(linb.Locale[linb.getLang()].app.caption['confirmNavigation']);
        		if (ans) {
        			g_isEdit=0;
        			return true;
        		}
        		else return false;
        	}
        	return true;
        }
    }
});