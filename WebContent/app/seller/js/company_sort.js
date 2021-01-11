var g_isEdit=0;//toggle for edit
Class('App.companySort', 'linb.Com',{
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
                .setCaption("<center><b>$app.caption.sortbuyer</b></center>")
            );

            host.companySortPanel.append((new linb.UI.Group)
                .host(host,"companySortgrp")
                .setLeft(10)
                .setTop(20)
                .setWidth(400)
                .setHeight(450)
                .setCaption("")
            );

            /*host.companySortgrp.append((new linb.UI.Tabs)
                .host(host,"categoriesTab")
//                .onRender("initialize")
                .setDock("none")
                .setLeft(20)
                .setTop(10)
                .setWidth(330)
                .setHeight(375)
                .onItemSelected("_categoriestab_onitemselected")
                .beforeUIValueSet("_categoriestab_beforeuivalueset")
            );*/
//            host.companySortgrp.append((new linb.UI.Tabs)
//                    .host(host,"categoriestab")
//                    .setItems([{"id":"a", "caption":"$app.fruits", "image":"img/demo.gif"}, {"id":"b", "caption":"$app.vegetables", "image":"img/demo.gif"}])
//                    .setDock("none")
//                    .setLeft(20)
//                    .setTop(40)
//                    .setWidth(330)
//                    .setHeight(250)
//                    .setValue("a")
//                );
            
            /*host.categoriestab.append((new linb.UI.TreeGrid)
                    .host(host,"skugrouptgd")
                    .setDock("top")
                    .setTop(10)
                    .setHeight(220)
                    .setSelMode("single")
                    .setAltRowsBg(true)
                    .setRowHandler(false)
                    .setEditable(true)
                    .setColResizer(false)
                    .setColSortable(false)
                    .setHeader([{"id":"col1", "width":80, "type":"checkbox", "caption":"$app.caption.select", "editable":"true"}, {"id":"col2", "width":240, "type":"text", "caption":"$app.caption.name", "cellStyle":"text-align:center;"}])
                    .setRows([{"cells":[{"value":false}, {"value":"Fruit D"}], "id":"d"}, {"cells":[{"value":false}, {"value":"Fruit C"}], "id":"c"}, {"cells":[{"value":false}, {"value":"Fruit B"}], "id":"b"}, {"cells":[{"value":true}, {"value":"Fruit A"}], "id":"a"}])
                    .setValue("col1")
                , 'a');*/
            
//            host.categoriesTab.append((new linb.UI.Pane)
//    				.host(host,"paneSigmaGrid")
//    				.setDock("fill")
//    			);
            host.companySortgrp.append((new linb.UI.List)
                    .host(host,"companylist")
                    .setDirtyMark(false)
                    //.setDock("top")
                    .setTop(10)
                    .setHeight(345)
                    .setWidth(300)
                    .setLeft(20)
                    //.setTop(41)
                    //.setWidth(170)
                    //.setHeight(350)
                    //.setItems([{"cells":[{"value":false}, {"value":"Fruit D"}], "id":"d"}, {"cells":[{"value":false}, {"value":"Fruit C"}], "id":"c"}, {"cells":[{"value":false}, {"value":"Fruit B"}], "id":"b"}, {"cells":[{"value":true}, {"value":"Fruit A"}], "id":"a"}])
                    .setSelMode("multi")
                );
//            
//            host.companySortgrp.append((new linb.UI.Button)
//                .host(host,"button92")
//                .setLeft(30)
//                .setTop(300)
//                .setWidth(100)
//                .setCaption("$app.caption.deletes")
//            );

//            host.companySortgrp.append((new linb.UI.Button)
//                .host(host,"button201")
//                .setLeft(250)
//                .setTop(300)
//                .setWidth(100)
//                .setCaption("$app.caption.alignsequence")
//                .onClick("_button201_onclick")
//            );
            
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
                .host(host,"lnkSortBuyers")
                .setLeft(220)
                .setTop(375)
                .setCaption("$app.caption.sortbuyers")
                .onClick("_lnkSortBuyers_onclick")
                .setCustomStyle({"KEY":"cursor:pointer"})
            );
            
//            host.companySortPanel.append((new linb.UI.Group)
//                .host(host,"groupAlignSequence")
//                .setLeft(400)
//                .setTop(20)
//                .setWidth(400)
//                .setHeight(340)
//                .setVisibility("visible")
//                .setCaption("$app.caption.settingordercommoditygroup")
//            );
//
//            host.groupAlignSequence.append((new linb.UI.Tabs)
//                .host(host,"tabs10")
//                .setItems([{"id":"tabFruits", "caption":"$app.fruits", "image":"img/demo.gif"}, {"id":"tabVeggies", "caption":"$app.vegetables", "image":"img/demo.gif"}])
//                .setDock("none")
//                .setLeft(20)
//                .setTop(10)
//                .setWidth(330)
//                .setHeight(250)
//                .setValue("tabFruits")
//            );
//
//            host.tabs10.append((new linb.UI.TreeGrid)
//                .host(host,"treegrid93")
//                .setDock("top")
//                .setHeight(220)
//                .setSelMode("multi")
//                .setAltRowsBg(true)
//                .setRowHandler(false)
//                .setHeader([{"id":"col1", "width":80, "type":"checkbox", "caption":"$app.select", "editable":"true"}, {"id":"col2", "width":240, "type":"label", "caption":"$app.name", "cellStyle":"text-align:center;"}])
//                .setRows([{"cells":[{"value":false}, {"value":"Vegetable A"}], "id":"a"}, {"cells":[{"value":false}, {"value":"Vegetable B"}], "id":"b"}, {"cells":[{"value":false}, {"value":"Vegetable C"}], "id":"c"}, {"cells":[{"value":false}, {"value":"Vegetable D"}], "id":"d"}])
//            , 'tabVeggies');
//
//            host.tabs10.append((new linb.UI.TreeGrid)
//                .host(host,"treegrid26")
//                .setDock("top")
//                .setHeight(220)
//                .setSelMode("multi")
//                .setAltRowsBg(true)
//                .setRowHandler(false)
//                .setHeader([{"id":"col1", "width":80, "type":"checkbox", "caption":"$app.select", "editable":"true"}, {"id":"col2", "width":240, "type":"label", "caption":"$app.name", "cellStyle":"text-align:center;"}])
//                .setRows([{"cells":[{"value":false}, {"value":"Fruit A"}], "id":"a"}, {"cells":[{"value":false}, {"value":"Fruit B"}], "id":"b"}, {"cells":[{"value":false}, {"value":"Fruit C"}], "id":"c"}, {"cells":[{"value":false}, {"value":"Fruit D"}], "id":"d"}])
//            , 'tabFruits');

      



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
        	companySort = page;
        	g_isEdit=0;
            this.loadCompanySort();
        },
        _onready:function() {
            //alert("ready");
            companySort = this;
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

        _btnclose_onclick:function (profile, e, src, value) {
        	if (companySort.confirmNavigation()) {
        	g_currentId = 0;
            this.destroy();
        	}
        },
        _btnsave_onclick:function (profile, e, src, value) {
            companySort.saveCompanySorting();
            //this.destroy();
        },
//        initialize:function() {
//            var url = "/eON/loadTab.json";
//            linb.Ajax(
//                url,
//                null,
//                function (response) {
//                	var categoryObject = _.unserialize(response);
//            		var obj = _.unserialize(response);
//            		var items=[];
//                    for(var i=0; i<categoryObject.categories.length; i++){
//                    	var image =obj.categories[i].image.replaceAll("%","/");
//                    	//alert(image);
//                    	items.push({"id":obj.categories[i].id, 
//                    		"caption":obj.categories[i].caption,"image":image});
//                    }
//                    companySort.categoriesTab.setItems(items);
//                    //alert('obj.index = '+obj.index);
//                    //g_categoryId = obj.index;
//                    companySort.categoriesTab.setValue(g_categoryId);
//                    companySort.loadSkuGroup();
//                    //companySort.tabs4.setValue(0);
//                }, 
//		    	function(msg) {
//		    		linb.message("失敗： " + msg);
//    	        }, 
//    	        null, 
//    	        {
//    	        	method : 'GET',
//    	        	retry : 0
//    	        }
//            ).start();
//        },
        _upbtn_onclick:function (profile, e, src, value) {
        	//alert('up');
        	g_isEdit = 1;
        	this.sortUpDown("up");
        },
        _downbtn_onclick:function (profile, e, src, value) {
        	//alert('up');
        	g_isEdit = 1;
            this.sortUpDown("down");
        },
        sortUpDown:function(direction) {
        	//alert('length ' +companySort.companylist.getItems().length);
        	//alert('value '+companySort.companylist.getUIValue());
        	//alert('vallength '+companySort.companylist.getUIValue().length);
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
        	companySort.companylist.setItems(newRightItems);
        	companySort.companylist.refresh();
        	companySort.companylist.setUIValue(uiVal);
        },
        _categoriestab_onitemselected:function (profile, item, src) {
        	//alert(item.id);
        	g_categoryId = item.id;
        	this.categoriesTab.append(this.companylist, item.id);
        	companySort.loadCompanySort(item.id);
		},
		loadCompanySort: function () {
			var obj = new Object();
	        //obj.categoryId = g_categoryId;
	        //this.categoriesTab.append(this.companylist, g_categoryId);
	        linb.Ajax(
    	        "/eON/initCompanySort.json",
    	        obj,
    	        function (response) {
    	        	//alert(response);
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
        	//alert('Saving...');
        	var rightItems = companySort.companylist.getItems();
        	var companySortList = new Array();
        	for (var i=0; i<rightItems.length; i++) {
        		var companySorts = new Object();
        		companySorts.companyId = parseInt(rightItems[i].id);
        		companySorts.sorting = i+1;
        		companySortList.push(companySorts);
        	}
//        	companySortParam.skuGroupIds = skuGrpColIds;
//        	companySortParam.sortOrder = sortOrder;
//        	companySortParam.skuCategoryId = g_categoryId;
        	
        	var url = "/eON/saveCompanySort.json?param=" + _.serialize(companySortList);
            linb.Ajax(
                url,
                null,
                function (response) {
                    //alert(response);
                    validateResponse(response);
                    alert("登録されました。");
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
		_lnkSortBuyers_onclick: function(profile, e) {
        	if (companySort.confirmNavigation()) {
        		companySort.loadCompanySort();
	            var host = this;
	            linb.ComFactory.newCom('App.buyersSort', function() {
	            	g_innerPref = this;
	            	this.setEvents({'onCloseCompanySort' : function() {
	            		g_innerPref = null;
	            		//companySort.loadCompanySort();
					}});
	            	var items = companySort.companylist.getItems();
		        	this.cbiCompanyname.setItems(items, true);
		        	//alert(items[0].caption);
		        	this.cbiCompanyname.setUIValue(items[0].caption);
		        	//this.loadBuyers(items[0].id);
	                this.show(linb( [ document.body ]));
	            });
        	}
        },
        checkForDirtyTable : function () {
    		//alert('editSort '+skuGroupSort.skugrouptlist.isDirtied());
        	//if(companySort.companylist.isDirtied())
        	if(g_isEdit == 1)
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