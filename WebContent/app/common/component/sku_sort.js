var g_defaultColItems;

Class('App.skuSort', 'linb.Com',{
	autoDestroy:true, 
    Instance:{
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Panel)
                .host(host,"skuSortPanel")
                .setDock("none")
                .setLeft(180)
                .setTop(59)
                .setWidth(820)
                .setHeight(520)
                .setZIndex(1)
                .setCaption("<center><b>$app.caption.skusortwindow</b></center>")
            );
            
            host.skuSortPanel.append((new linb.UI.List)
                .host(host,"defaultColList")
                .setDirtyMark(false)
                .setLeft(21)
                .setTop(41)
                .setWidth(170)
                .setHeight(350)
                .setSelMode("multi")
            );
            
            host.skuSortPanel.append((new linb.UI.List)
                .host(host,"sortColList")
                .setDirtyMark(false)
                .setLeft(271)
                .setTop(41)
                .setWidth(170)
                .setHeight(350)
                .setSelMode("multi")
            );
            
            host.skuSortPanel.append((new linb.UI.Button)
                .host(host,"saveBtn")
                .setLeft(21)
                .setTop(411)
                .setWidth(80)
                .setCaption("$app.caption.skusortsave")
                .onClick("_savebtn_onclick")
            );
            
            host.skuSortPanel.append((new linb.UI.Button)
                .host(host,"cancelBtn")
                .setLeft(111)
                .setTop(411)
                .setWidth(80)
                .setCaption("$app.caption.skusortcancel")
                .onClick("_cancelbtn_onclick")
            );
            
            host.skuSortPanel.append((new linb.UI.Button)
                .host(host,"rightBtn")
                .setLeft(201)
                .setTop(191)
                .setWidth(60)
                .setCaption(">>>")
                .setValue(true)
                .onClick("_rightbtn_onclick")
            );
            
            host.skuSortPanel.append((new linb.UI.Button)
                .host(host,"leftBtn")
                .setLeft(201)
                .setTop(221)
                .setWidth(60)
                .setCaption("<<<")
                .setValue(true)
                .onClick("_leftbtn_onclick")
            );
            
            host.skuSortPanel.append((new linb.UI.Button)
                .host(host,"upBtn")
                .setLeft(451)
                .setTop(191)
                .setWidth(40)
                .setCaption("↑")
                .setVAlign("center")
                .onClick("_upbtn_onclick")
            );
            
            host.skuSortPanel.append((new linb.UI.Button)
                .host(host,"downBtn")
                .setLeft(451)
                .setTop(221)
                .setWidth(40)
                .setCaption("↓")
                .setVAlign("center")
                .onClick("_downbtn_onclick")
            );
            
            host.skuSortPanel.append((new linb.UI.Label)
                .host(host,"defaultColLbl")
                .setLeft(21)
                .setTop(21)
                .setWidth(170)
                .setBorder(true)
                .setCaption("$app.caption.skusortdefaultcol")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.skuSortPanel.append((new linb.UI.Label)
                .host(host,"sortColLbl")
                .setLeft(271)
                .setTop(21)
                .setWidth(170)
                .setBorder(true)
                .setCaption("$app.caption.skusortsortcol")
                .setHAlign("center")
                .setVAlign("middle")
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
            //alert("render");
            this.initialize();
        }, 
        _onready:function() {
            //alert("ready");
            skuSort = this;
        }, 
        _onbeforecreated:function(com, threadid) {
            //alert("beforecreated");
        }, 
        customAppend:function(parent,subId,left,top){
            return false;
        }, 
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        }, 

        _cancelbtn_onclick:function (profile, e, src, value) {
        	g_currentId = 0;
            this.destroy();
        }, 
        _savebtn_onclick:function (profile, e, src, value) {
            skuSort.saveSKUSorting();
            //this.destroy();
        },
        initialize:function() {
            var url = "/eON/initSKUSort.json";
            linb.Ajax(
                url,
                null,
                function (response) {
                    //alert(response);
                    validateResponse(response);
                    var obj = new Object();
                    var items = new Array();
                    obj = _.unserialize(response);
                    
                    for(var i=0; i<obj.allDefCols.length; i++){
                        items.push({"id":obj.allDefCols[i].skuColumnId,
                            "caption":obj.allDefCols[i].headerSheet});
                    }
                    g_defaultColItems = items;
                    
                    items = new Array();
                    for(var i=0; i<obj.defCols.length; i++){
                        items.push({"id":obj.defCols[i].skuColumnId,
                            "caption":obj.defCols[i].headerSheet});
                    }
                    skuSort.defaultColList.setItems(items);
                    
                    items = new Array();
                    for(var i=0; i<obj.sortCols.length; i++){
                        items.push({"id":obj.sortCols[i].skuColumnId,
                            "caption":obj.sortCols[i].headerSheet});
                    }
                    skuSort.sortColList.setItems(items);

                },
                function(msg) {
                        linb.message("失敗： " + msg);
                    }, null, {
                        method : 'GET',
                        retry : 0
                    }
            ).start();
        },
        _rightbtn_onclick:function (profile, e, src, value) {
        	if (skuSort.defaultColList.getItems().length == 0 ||
        			skuSort.defaultColList.getUIValue()	== null ||
        			skuSort.defaultColList.getUIValue().length == 0)
        		return;
        	var leftSelects = skuSort.defaultColList.getUIValue().split(";");
        	var toRightItems = new Array();
        	var leftItems = skuSort.defaultColList.getItems();
        	for(var i=0; i<leftItems.length; i++){
                if(_.arr.indexOf(leftSelects, leftItems[i].id) >= 0) {
                    toRightItems.push(leftItems[i]);
                }
            }
        	skuSort.sortColList.insertItems(toRightItems, null, false);
        	skuSort.defaultColList.removeItems(leftSelects);
        	skuSort.defaultColList.refresh();
        	skuSort.sortColList.refresh();
        },
        _leftbtn_onclick:function (profile, e, src, value) {
        	if (skuSort.sortColList.getItems().length == 0 ||
        			skuSort.sortColList.getUIValue() == null ||
        			skuSort.sortColList.getUIValue().length == 0)
        		return;
        	var rightSelects = skuSort.sortColList.getUIValue().split(";");
        	var leftItems = skuSort.defaultColList.getItems();
        	var leftIds = new Array();
        	for(var i=0; i<leftItems.length; i++){
        		_.arr.insertAny(leftIds, leftItems[i].id);
            }
        	_.arr.insertAny(leftIds, rightSelects);
        	var newLeftItems = new Array();
        	for(var i=0; i<g_defaultColItems.length; i++) {
        		if(_.arr.indexOf(leftIds, g_defaultColItems[i].id) >= 0) {
        			newLeftItems.push(g_defaultColItems[i]);
        		}
        	}
        	skuSort.sortColList.removeItems(rightSelects);
        	skuSort.defaultColList.setItems(newLeftItems);
        	skuSort.defaultColList.refresh();
        	skuSort.sortColList.refresh();
        },
        _upbtn_onclick:function (profile, e, src, value) {
        	this.sortUpDown("up");
        },
        _downbtn_onclick:function (profile, e, src, value) {
            this.sortUpDown("down");
        },
        sortUpDown:function(direction) {
        	if (skuSort.sortColList.getItems().length == 0 ||
        			skuSort.sortColList.getUIValue() == null ||
        			skuSort.sortColList.getUIValue().length == 0)
        		return;
        	var uiVal = skuSort.sortColList.getUIValue();
        	var rightSelects = skuSort.sortColList.getUIValue().split(";");
        	var rightItems = skuSort.sortColList.getItems();
        	var rightIds = new Array();
        	var rightSelIds = new Array();
        	for(var i=0; i<rightItems.length; i++){
        		_.arr.insertAny(rightIds, rightItems[i].id);
        		if(_.arr.indexOf(rightSelects, rightItems[i].id) >= 0) {
        			_.arr.insertAny(rightSelIds, rightItems[i].id);
        		}
            }
        	//alert(_.serialize(rightIds));
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
        	//alert(_.serialize(rightIds));
        	if (dontMove) return;
        	var newRightItems = new Array();
        	for(var i=0; i<rightIds.length; i++) {
        		var thisId = rightIds[i];
        		for(var j=0; j<rightItems.length; j++) {
        			if(thisId == rightItems[j].id)
        				newRightItems.push(rightItems[j]);
        		}	
        	}
        	skuSort.sortColList.setItems(newRightItems);
        	skuSort.sortColList.refresh();
        	skuSort.sortColList.setUIValue(uiVal);
        },
        saveSKUSorting:function() {
        	var rightItems = skuSort.sortColList.getItems();
        	var skuSortParam = new Object();
        	var skuColIds = new Array();
        	var sortOrder = new Array();
        	for (var i=0; i<rightItems.length; i++) {
        		skuColIds.push(parseInt(rightItems[i].id));
        		sortOrder.push(i+1);
        	}
        	skuSortParam.skuColIds = skuColIds;
        	skuSortParam.sortOrder = sortOrder;
        	
        	var url = "/eON/saveSKUSort.json";
            linb.Ajax(
                url,
                skuSortParam,
                function (response) {
                    //alert(response);
                    validateResponse(response);
                    alert("登録されました。");
                    skuSort.sortColList.refresh();
                    skuSort.defaultColList.refresh();
                },
                function(msg) {
                        linb.message("失敗： " + msg);
                    }, null, {
                        method : 'POST',
                        retry : 0
                    }
            ).start();
        }
    }
});