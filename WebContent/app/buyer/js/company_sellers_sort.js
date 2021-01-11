var g_isEdit=0;//toggle for edit
Class('App.companySellerSort', 'linb.Com',{
    autoDestroy:true,
    Instance:{    
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};

            append((new linb.UI.Panel)
                .host(host,"companySortPanel")
                .setDock("none")
                .setLeft(180)
                .setTop(59)
                .setWidth(820)
                .setHeight(520)
                .setZIndex(1)
                .setCaption("<center><b>$app.caption.sortseller</b></center>")
            );

            host.companySortPanel.append((new linb.UI.Group)
                .host(host,"companySortgrp")
                .setLeft(10)
                .setTop(20)
                .setWidth(400)
                .setHeight(450)
                .setCaption("")
            );

            host.companySortgrp.append((new linb.UI.List)
                    .host(host,"companylist")
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
                .setLeft(50)
                .setTop(375)
                .setWidth(70)
                .setCaption("$app.caption.save")
                .onClick("_btnsave_onclick")
            );

            host.companySortgrp.append((new linb.UI.Button)
                .host(host,"_btnclose")
                .setLeft(130)
                .setTop(375)
                .setWidth(70)
                .setCaption("$app.caption.close")
                .onClick("_btnclose_onclick")
            );

            host.companySortgrp.append((new linb.UI.Link)
                .host(host,"lnkSortSellers")
                .setLeft(220)
                .setTop(375)
                .setCaption("$app.caption.sortsellers")
                .onClick("_lnkSortSellers_onclick")
                .setCustomStyle({"KEY":"cursor:pointer"})
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
        	companySort = page;
        	g_isEdit=0;
            this.loadCompanySort();
        },
        _onready:function() {
            companySort = this;
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
        	if (companySort.confirmNavigation()) {
        	g_currentId = 0;
            this.destroy();
        	}
        },
        _btnsave_onclick:function (profile, e, src, value) {
        	if (companySort.checkForDirtyTable()) {
        		companySort.saveCompanySorting();
        	}
        },
        _upbtn_onclick:function (profile, e, src, value) {
        	g_isEdit = 1;
        	this.sortUpDown("up");
        },
        _downbtn_onclick:function (profile, e, src, value) {
        	g_isEdit = 1;
            this.sortUpDown("down");
        },
        sortUpDown:function(direction) {
        	if (companySort.companylist.getItems().length == 0 ||
        			companySort.companylist.getUIValue() == null ||
        			companySort.companylist.getUIValue().length == 0)
        		return;
        	var uiVal = companySort.companylist.getUIValue();
        	var rightSelects = companySort.companylist.getUIValue().split(";");
        	var rightItems = companySort.companylist.getItems();
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
        	companySort.companylist.setItems(newRightItems);
        	companySort.companylist.refresh();
        	companySort.companylist.setUIValue(uiVal);
        },
        _categoriestab_onitemselected:function (profile, item, src) {
        	g_categoryId = item.id;
        	this.categoriesTab.append(this.companylist, item.id);
        	companySort.loadCompanySort(item.id);
		},
		loadCompanySort: function () {
			var obj = new Object();
	        linb.Ajax(
    	        "/eON/initCompanySellersSort.json",
    	        obj,
    	        function (response) {
                    validateResponse(response);
                    var obj = new Object();
                    var items = new Array();
                    obj = _.unserialize(response);
                    for(var i=0; i<obj.companySorts.length; i++){
                    	var company = obj.companySorts[i].company;
                        items.push({"id":company.companyId,
                            "caption":company.companyName});
                    }
                    companySort.companylist.setItems(items);
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
        saveCompanySorting:function() {
        	var rightItems = companySort.companylist.getItems();
        	var companySortList = new Array();
        	for (var i=0; i<rightItems.length; i++) {
        		var companySorts = new Object();
        		companySorts.companyId = parseInt(rightItems[i].id);
        		companySorts.sorting = i+1;
        		companySortList.push(companySorts);
        	}
        	
        	var url = "/eON/saveCompanySellersSort.json?param=" + _.serialize(companySortList);
            linb.Ajax(
                url,
                null,
                function (response) {
                    validateResponse(response);
                    alert(linb.Locale[linb.getLang()].app.caption['alertsaved']);
                	g_isEdit = 0;
                    companySort.companylist.refresh();
                },
                function(msg) {
                        linb.message("失敗： " + msg);
                    }, null, {
                        method : 'POST',
                        retry : 0
                    }
            ).start();
        },
        _lnkSortSellers_onclick: function(profile, e) {
        	if (companySort.confirmNavigation()) {
        		companySort.loadCompanySort();
	            var host = this;
	            linb.ComFactory.newCom('App.sellersSort', function() {
	            	g_innerPref = this;
	            	this.setEvents({'onCloseCompanySort' : function() {
	            		g_innerPref = null;
					}});
	            	var items = companySort.companylist.getItems();
		        	this.cbiCompanyname.setItems(items, true);
		        	this.cbiCompanyname.setUIValue(items[0].caption);
	                this.show(linb( [ document.body ]));
	            });
        	}
        },
        checkForDirtyTable : function () {
        	if(companySort.companylist.isDirtied() && g_isEdit == 1)
        		return true;
        	else
        		return false;
        },
        confirmNavigation : function () {
        	if (companySort.checkForDirtyTable()) {
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