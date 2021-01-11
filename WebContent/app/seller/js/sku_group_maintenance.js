Class('App.skuGrpMaintenance', 'linb.Com',{
    autoDestroy:true,
    Instance:{    
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};

            append((new linb.UI.Panel)
                .host(host,"skuGroupMaintenance")
                .setDock("none")
                .setLeft(180)
                .setTop(59)
                .setWidth(820)
                .setHeight(520)
                .setZIndex(1)
                .setCaption("<center><b>$app.caption.skugroupmaintenance</b></center>")
            );

            host.skuGroupMaintenance.append((new linb.UI.Group)
                .host(host,"grpSKUGroup")
                .setLeft(10)
                .setTop(20)
                .setWidth(500)
                .setHeight(450)
                .setCaption("")
                .setToggleBtn(false)
            );

            host.grpSKUGroup.append((new linb.UI.Tabs)
                .host(host,"categoriesTab")
                .setDock("none")
                .setLeft(20)
                .setTop(40)
                //.setWidth(365)
                .setHeight(342)
                .onItemSelected("_categoriestab_onitemselected")
                .beforeUIValueSet("_categoriestab_beforeuivalueset")
            );
            
            host.categoriesTab.append((new linb.UI.TreeGrid)
                    .host(host,"skugrouptgd")
                    .setDock("top")	
                    .setTop(10)
                    .setHeight(310)
                    //.setWidth(360)
                    .setColSortable(false)
                    .setSelMode("single")
                    .setAltRowsBg(true)
                    .setRowHandler(false)
                    .setEditable(true)
//                    .setColResizer(false)
//                    .setColSortable(false)
                    //.setRows([{"cells":[{"value":false}, {"value":"Fruit D"}], "id":"d"}, {"cells":[{"value":false}, {"value":"Fruit C"}], "id":"c"}, {"cells":[{"value":false}, {"value":"Fruit B"}], "id":"b"}, {"cells":[{"value":true}, {"value":"Fruit A"}], "id":"a"}])
                    //.setValue("col1")
            );
            
            host.grpSKUGroup.append((new linb.UI.Button)
                .host(host,"btnAdd")
                .setLeft(30)
                .setTop(10)
                .setWidth(110)
                .setCaption("$app.caption.addcommodity")
                .onClick("_btnAdd_onclick")
            );
//            host.grpSKUGroup.append((new linb.UI.Button)
//                .host(host,"button201")
//                .setLeft(250)
//                .setTop(300)
//                .setWidth(100)
//                .setCaption("$app.caption.alignsequence")
//                .onClick("_button201_onclick")
//            );
            
//            host.grpSKUGroup.append((new linb.UI.Button)
//                .host(host,"btnUp")
//                .setTips("Up")
//                .setLeft(400)
//                .setTop(70)
//                .setWidth(20)
//                .setHeight(30)
//                .setCaption("↑")
//                .onClick("_upbtn_onclick")
//            );
//
//            host.grpSKUGroup.append((new linb.UI.Button)
//                .host(host,"btnDowm")
//                .setTips("Down")
//                .setLeft(400)
//                .setTop(110)
//                .setWidth(20)
//                .setHeight(30)
//                .setCaption("↓")
//                .onClick("_downbtn_onclick")
//            );

            host.grpSKUGroup.append((new linb.UI.Button)
                .host(host,"btnDelete")
                .setLeft(30)
                .setTop(395)
                .setWidth(70)
                .setCaption("$app.caption.deletes")
                .onClick("_btnDelete_onclick")
            );

            host.grpSKUGroup.append((new linb.UI.Button)
                .host(host,"btnSave")
                .setLeft(120)
                .setTop(395)
                .setWidth(70)
                .setCaption("$app.caption.save")
                .onClick("_btnSave_onclick")
            );

            host.grpSKUGroup.append((new linb.UI.Button)
                .host(host,"btnClose")
                .setLeft(200)
                .setTop(395)
                .setWidth(70)
                .setCaption("$app.caption.close")
                .onClick("_btnClose_onclick")
            );
            
            host.grpSKUGroup.append((new linb.UI.Link)
                .host(host,"lnkSortGroup")
                .setLeft(290)
                .setTop(395)
                .setCaption("$app.caption.alignsequence")
                .onClick("_lnkSortGroup_onclick")
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
            //alert("render");
        	skuGroupMaint = page;
            this.initialize();
            if (g_userRole=="ROLE_SELLER"){
            	this.grpSKUGroup.setWidth(450);
            	this.categoriesTab.setWidth(365);
            	this.skugrouptgd.setWidth(360);
	            this.skugrouptgd.setHeader([{"id":"delete", "width":80, "type":"checkbox", "caption":"$app.caption.select", "editable":"true"}, 
	                                        {"id":"id", "type":"label", "hidden" : true},
	                                        {"id":"origSkuGroupId", "width":150, "type":"label", "hidden" : true}, 
	                                        {"id":"sellerId", "width":150, "type":"label", "hidden" : true},
	                                        {"id":"sellerName", "width":150, "type":"label", "hidden" : true},	                                        
	                                        {"id":"categoryId", "width":150, "type":"label", "hidden" : true},
	                                        {"id":"skuGroupName", "width":260, "type":"input", "caption":"$app.caption.name", "cellStyle":"text-align:center;"}
	                                        ]);
            }
            if (g_userRole=="ROLE_SELLER_ADMIN"){
            	this.grpSKUGroup.setWidth(600);
            	this.categoriesTab.setWidth(515);
        		this.skugrouptgd.setWidth(510);
	            this.skugrouptgd.setHeader([{"id":"delete", "width":80, "type":"checkbox", "caption":"$app.caption.select", "editable":"true"}, 
	                                        {"id":"id", "type":"label", "hidden" : true},
	                                        {"id":"origSkuGroupId", "width":150, "type":"label", "hidden" : true}, 
	                                        {"id":"sellerId", "width":150, "type":"label", "hidden" : true},
	                                        {"id":"sellerName", "width":150, "type":"label", "caption":"$app.caption.sellername", "editable":"false", "cellStyle":"text-align:center;"},
	                                        {"id":"categoryId", "width":150, "type":"label", "hidden" : true},
	                                        {"id":"skuGroupName", "width":260, "type":"input", "caption":"$app.caption.name", "cellStyle":"text-align:center;"}
	                                        ]);
            }
        },
        _onready:function() {
            //alert("ready");
            skuGroupMaint = this;
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
        _btnAdd_onclick:function (profile, e, src, value) {
        	if (skuGroupMaint.confirmNavigation()) {
	        	linb.ComFactory.newCom(
	    			'App.addSKUGroup', 
	    			function(){
	    				skuGroupMaint._excom1 = this;
	    				skuGroupMaint._excom1.hiddenCategoryValue.setCaption(skuGroupMaint.categoriesTab.getUIValue());
	    				this.setEvents({'onAddSKUGroup' : function(result) {
	    					if (result == 'success') {
		    					alert('登録されました。');
	    						// reload table
	    						//skuGroupMaint.initialize();
		    					skuGroupMaint.loadSkuGroup();
	    					}
	    				}});
	//    	        	var obj = new Object();
	//		        	linb.Ajax(
	//				        "/eON/skuGroup/getSellers.json",
	//				        null,
	//				        function (response) {
	//				        	skuGroupMaint._excom1.responseCheck(response);
	//				        	// set seller name in sku group window
	//				        	var o = _.unserialize(response);
	//				        	var items = o.response;
	//				        	skuGroupMaint._excom1.cbiSellername.setItems(items, true);
	//				        	skuGroupMaint._excom1.cbiSellername.setUIValue(items[0].caption);
	//				        }, 
	//				        function(msg) {
	//				            linb.message("失敗： " + msg);
	//				        }, 
	//				        null, 
	//				        {
	//				        	method : 'POST',
	//				        	retry : 0
	//				        }
	//				    ).start();        				
	    				if (g_userRole == "ROLE_SELLER")
	    					skuGroupMaint._excom1.cbiSellername.setDisabled(true);
	    				this.show(linb( [ document.body ]));
	    			}
	        	);
        	}
        },
        _btnClose_onclick:function (profile, e, src, value) {
        	if (skuGroupMaint.confirmNavigation()) {
	        	g_currentId = 0;
	            this.destroy();
        	}
        },
        _btnSave_onclick:function (profile, e, src, value) {
            //skuGroupMaint.saveSKUSorting();
            //this.destroy();
        	this.updateSKUGroup();
        },
        _btnDelete_onclick:function (profile, e, src, value) {
            //skuGroupMaint.saveSKUSorting();
            //this.destroy();
        	//alert("delete...");
        	var skuGroupList = [];
        	var rows = skuGroupMaint.skugrouptgd.getRows('data');
            _.arr.each(rows, function(row){
                //_.arr.each(row.cells, function(cell){
                    if(row.cells[0].dirty && row.cells[0].value==true){
                    	//alert(_.serialize(row));
                    	var skuGroup = new Object();
                    	skuGroup.skuGroupId = row.cells[1].value;
                    	skuGroupList.push(skuGroup);
                    }
                //});
            });
            //alert(_.serialize(skuGroupList));
            if (skuGroupList.length != null && skuGroupList.length > 0){
            	//alert('delete');
	            linb.Ajax(
	        	        "/eON/skuGroup/deleteSkuGroup.json?param=" + _.serialize(skuGroupList),
	        	        null,
	        	        function (response) {
	        	        	//alert(response);
	                        validateResponse(response);
	                        var obj = _.unserialize(response);
	                        skuGroupMaint.loadSkuGroup();
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
            }
        },
        
        updateSKUGroup:function() {
        	//var skuGroupList = [];
        	var rows = skuGroupMaint.skugrouptgd.getRows('data');
            _.arr.each(rows, function(row){
                //_.arr.each(row.cells, function(cell){
                	//alert(row.cells[5].dirty);
                    if(row.cells[6].dirty){
                    	//alert(_.serialize(row));
                    	var skuGroup = new Object();
                    	skuGroup.skuGroupId = row.cells[1].value;
                    	skuGroup.origSkuGroupId = row.cells[2].value;
                    	skuGroup.sellerId = row.cells[3].value;
                    	skuGroup.categoryId = row.cells[5].value;
                    	skuGroup.description = row.cells[6].value.trim();
                    	//skuGroupList.push(skuGroup);
                    	
                    	linb.Ajax(
                    	        "/eON/skuGroup/updateSkuGroup.json",
                    	        skuGroup,
                    	        function (response) {
                    	        	//alert(response);
                                    validateResponse(response);
                                    var obj = _.unserialize(response);
                                    skuGroupMaint.loadSkuGroup();
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
                    }
                //});
            });
//            alert("param" + _.serialize(skuGroupList));
//            if (skuGroupList.length != null && skuGroupList.length > 0){
//            	//alert('Update');
//	            linb.Ajax(
//        	        "/eON/skuGroup/updateSkuGroup.json?param=" + _.serialize(skuGroupList),
//        	        skuGroupList,
//        	        function (response) {
//        	        	//alert(response);
//                        validateResponse(response);
//                        var obj = _.unserialize(response);
//                        skuGroupMaint.loadSkuGroup();
//        	        }, 
//        	        function(msg) {
//        	            linb.message("失敗： " + msg);
//        	        }, 
//        	        null, 
//        	        {
//        	        	method : 'POST',
//        	        	retry : 0
//        	        }
//        	    ).start(); 
//            }
        },
        saveSKUSorting:function() {
            //alert("saving...");
        },
        initialize:function() {
            var url = "/eON/loadTab.json";
            linb.Ajax(
                url,
                null,
                function (response) {
                	validateResponse(response);
                	var categoryObject = _.unserialize(response);
            		var obj = _.unserialize(response);
            		var items=[];
                    for(var i=0; i<categoryObject.categories.length; i++){
                    	var image =obj.categories[i].image.replaceAll("%","/");
                    	//alert(image);
                    	items.push({"id":obj.categories[i].id, 
                    		"caption":obj.categories[i].caption,"image":image});
                    }
                    skuGroupMaint.categoriesTab.setItems(items);
                    g_categoryId = obj.index;
                    skuGroupMaint.categoriesTab.setValue(g_categoryId);
                    skuGroupMaint.categoriesTab.append(skuGroupMaint.skugrouptgd, g_categoryId);
                    skuGroupMaint.loadSkuGroup();
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
        _upbtn_onclick:function (profile, e, src, value) {
        	this.sortUpDown("up");
        },
        _downbtn_onclick:function (profile, e, src, value) {
            this.sortUpDown("down");
        },
        sortUpDown:function(direction) {
//        	if (skuGroupMaint.skugrouptgd.getItems().length == 0 ||
//        			skuGroupMaint.skugrouptgd.getUIValue() == null ||
//        			skuGroupMaint.skugrouptgd.getUIValue().length == 0)
//        		return;
        	//var uiVal = skuGroupMaint.skugrouptgd.getUIValue();
        	var rightSelects = skuGroupMaint.skugrouptgd.getUIValue().split(";");
        	var rightItems = skuGroupMaint.skugrouptgd.getItems();
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
        	skuGroupMaint.skugrouptgd.setItems(newRightItems);
        	skuGroupMaint.skugrouptgd.refresh();
        	//skuGroupMaint.skugrouptgd.setUIValue(uiVal);
        },
        _categoriestab_onitemselected:function (profile, item, src) {
        	//alert(item.id);
        	g_categoryId = item.id;
        	this.skugrouptgd.setVisibility="hidden";
        	this.categoriesTab.append(this.skugrouptgd, item.id);
        	skuGroupMaint.loadSkuGroup();
		},
		loadSkuGroup: function () {
			var obj = new Object();
	        obj.categoryId = g_categoryId;
	        linb.Ajax(
    	        "/eON/skuGroup/loadSkuGroup.json",
    	        obj,
    	        function (response) {
    	        	//alert(response);
                    validateResponse(response);
                    var obj = _.unserialize(response);
                    skuGroupMaint.skugrouptgd.setRows(obj.skuGroupList);
                    skuGroupMaint.skugrouptgd.setVisibility="visible";
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
		_lnkSortGroup_onclick: function(profile, e) {
			if (skuGroupMaint.confirmNavigation()) {
	            var host = this;
	            linb.ComFactory.newCom('App.skuGrpSort', function() {
	            	g_innerPref = this;
	            	this.setEvents({'onCloseSKUGroupSort' : function() {
	            		g_innerPref = null;
	            		skuGroupMaint.initialize();
					}});
	                this.show(linb( [ document.body ]));
	            });
			}
        },
        checkForDirtyTable : function () {
        	var rows = skuGroupMaint.skugrouptgd.getRows('data');
        	isDirty = false;
            _.arr.each(rows, function(row){
            	if(row.cells[6].dirty || (row.cells[0].dirty && (row.cells[0].value==true))){
        			isDirty = true;
            	}
            });
            //alert('isDirty '+ isDirty);
            return isDirty;
        },
        confirmNavigation : function () {
        	if (skuGroupMaint.checkForDirtyTable()) {
        		var ans = confirm(linb.Locale[linb.getLang()].app.caption['confirmNavigation']);
        		if (ans) return true;
        		else return false;
        	}
        	return true;
        },
        _categoriestab_beforeuivalueset : function (profile, oldValue, newValue) {
        	if (skuGroupMaint.checkForDirtyTable()) {
        		var ans = confirm(linb.Locale[linb.getLang()].app.caption['confirmNavigation']);
        		if (ans) return true;
        		else return false;
        	}
        	return true;
        }
    }
});