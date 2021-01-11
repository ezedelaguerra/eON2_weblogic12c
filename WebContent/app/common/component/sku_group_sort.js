var g_isEdit=0;//toggle for edit
Class('App.skuGrpSort', 'linb.Com',{
    autoDestroy:true,
    Instance:{    
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};

            append((new linb.UI.Panel)
                .host(host,"skuGroupSortPanel")
                .setDock("none")
                .setLeft(180)
                .setTop(59)
                .setWidth(820)
                .setHeight(520)
                .setZIndex(1)
                .setCaption("<center><b>$app.caption.settingordercommoditygroup</b></center>")
            );

            host.skuGroupSortPanel.append((new linb.UI.Group)
                .host(host,"group17")
                .setLeft(10)
                .setTop(20)
                .setWidth(400)
                .setHeight(450)
                .setCaption("")
            );

            host.group17.append((new linb.UI.Tabs)
                .host(host,"categoriesTab")
//                .onRender("initialize")
                .setDock("none")
                .setLeft(20)
                .setTop(10)
                .setWidth(330)
                .setHeight(375)
                .onItemSelected("_categoriestab_onitemselected")
                .beforeUIValueSet("_categoriestab_beforeuivalueset")
            );
//            host.group17.append((new linb.UI.Tabs)
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
            host.categoriesTab.append((new linb.UI.List)
                    .host(host,"skugrouptlist")
                    .setDirtyMark(false)
                    .setDock("top")
                    .setTop(10)
                    .setHeight(340)
                    .setWidth(100)
                    //.setLeft(271)
                    //.setTop(41)
                    //.setWidth(170)
                    //.setHeight(350)
                    //.setItems([{"cells":[{"value":false}, {"value":"Fruit D"}], "id":"d"}, {"cells":[{"value":false}, {"value":"Fruit C"}], "id":"c"}, {"cells":[{"value":false}, {"value":"Fruit B"}], "id":"b"}, {"cells":[{"value":true}, {"value":"Fruit A"}], "id":"a"}])
                    .setSelMode("multi")
                );
//            
//            host.group17.append((new linb.UI.Button)
//                .host(host,"button92")
//                .setLeft(30)
//                .setTop(300)
//                .setWidth(100)
//                .setCaption("$app.caption.deletes")
//            );

//            host.group17.append((new linb.UI.Button)
//                .host(host,"button201")
//                .setLeft(250)
//                .setTop(300)
//                .setWidth(100)
//                .setCaption("$app.caption.alignsequence")
//                .onClick("_button201_onclick")
//            );
            
            host.group17.append((new linb.UI.Button)
                .host(host,"btnUp")
                .setTips("Up")
                .setLeft(360)
                .setTop(70)
                .setWidth(20)
                .setHeight(30)
                .setCaption("↑")
                .onClick("_upbtn_onclick")
            );

            host.group17.append((new linb.UI.Button)
                .host(host,"btnDowm")
                .setTips("Down")
                .setLeft(360)
                .setTop(110)
                .setWidth(20)
                .setHeight(30)
                .setCaption("↓")
                .onClick("_downbtn_onclick")
            );
            host.group17.append((new linb.UI.Button)
                .host(host,"btnsave")
                .setLeft(190)
                .setTop(395)
                .setWidth(70)
                .setCaption("$app.caption.save")
                .onClick("_btnsave_onclick")
            );

            host.group17.append((new linb.UI.Button)
                .host(host,"_btnclose")
                .setLeft(270)
                .setTop(395)
                .setWidth(70)
                .setCaption("$app.caption.close")
                .onClick("_btnclose_onclick")
            );
//            host.skuGroupSortPanel.append((new linb.UI.Group)
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
        	skuGroupSort = page;
        	g_isEdit=0;
            this.initialize();
        },
        _onready:function() {
            //alert("ready");
            skuGroupSort = this;
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
        	if (skuGroupSort.confirmNavigation()) {
	        	this.fireEvent('onCloseSKUGroupSort');
	            this.destroy();
        	}
        },
        _btnsave_onclick:function (profile, e, src, value) {
            skuGroupSort.saveSKUGrpSorting();
            //this.destroy();
        },
        saveSKUGrpSorting:function() {
        	//alert('Saving...');
        	var rightItems = skuGroupSort.skugrouptlist.getItems();
        	var SKUGroupSortParam = new Object();
        	var skuGrpColIds = new Array();
        	var sortOrder = new Array();
        	for (var i=0; i<rightItems.length; i++) {
        		skuGrpColIds.push(parseInt(rightItems[i].id));
        		sortOrder.push(i+1);
        	}
        	SKUGroupSortParam.skuGroupIds = skuGrpColIds;
        	SKUGroupSortParam.sortOrder = sortOrder;
        	SKUGroupSortParam.skuCategoryId = g_categoryId;
        	
        	var url = "/eON/saveSKUGroupSort.json";
            linb.Ajax(
                url,
                SKUGroupSortParam,
                function (response) {
                    //alert(response);
                    validateResponse(response);
                    alert("登録されました。");
                    g_isEdit=0;
                    skuGroupSort.skugrouptlist.refresh();
                },
                function(msg) {
                        linb.message("失敗： " + msg);
                    }, null, {
                        method : 'POST',
                        retry : 0
                    }
            ).start();
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
                    skuGroupSort.categoriesTab.setItems(items);
                    //alert('obj.index = '+obj.index);
                    //g_categoryId = obj.index;
                    skuGroupSort.categoriesTab.setValue(g_categoryId);
                    skuGroupSort.loadSkuGroup();
                    //skuGroupSort.tabs4.setValue(0);
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
        	//alert('up');
        	g_isEdit=1;
        	this.sortUpDown("up");
        },
        _downbtn_onclick:function (profile, e, src, value) {
        	//alert('up');
        	g_isEdit=1;
            this.sortUpDown("down");
        },
        sortUpDown:function(direction) {
        	//alert('length ' +skuGroupSort.skugrouptlist.getItems().length);
        	//alert('value '+skuGroupSort.skugrouptlist.getUIValue());
        	//alert('vallength '+skuGroupSort.skugrouptlist.getUIValue().length);
        	if (skuGroupSort.skugrouptlist.getItems().length == 0 ||
        			skuGroupSort.skugrouptlist.getUIValue() == null ||
        			skuGroupSort.skugrouptlist.getUIValue().length == 0)
        		return;
        	var uiVal = skuGroupSort.skugrouptlist.getUIValue();
        	var rightSelects = skuGroupSort.skugrouptlist.getUIValue().split(";");
        	var rightItems = skuGroupSort.skugrouptlist.getItems();
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
        	skuGroupSort.skugrouptlist.setItems(newRightItems);
        	skuGroupSort.skugrouptlist.refresh();
        	skuGroupSort.skugrouptlist.setUIValue(uiVal);
        },
        _categoriestab_onitemselected:function (profile, item, src) {
        	//alert(item.id);
        	g_categoryId = item.id;
        	this.categoriesTab.append(this.skugrouptlist, item.id);
        	skuGroupSort.loadSkuGroup(item.id);
		},
		loadSkuGroup: function () {
			var obj = new Object();
	        obj.categoryId = g_categoryId;
	        this.categoriesTab.append(this.skugrouptlist, g_categoryId);
	        linb.Ajax(
    	        "/eON/initSKUGroupSort.json",
    	        obj,
    	        function (response) {
    	        	//alert(response);
                    validateResponse(response);
                    var obj = new Object();
                    var items = new Array();
                    obj = _.unserialize(response);
                    for(var i=0; i<obj.skuGroupSorts.length; i++){
                        items.push({"id":obj.skuGroupSorts[i].skuGroupId,
                            "caption":obj.skuGroupSorts[i].skuGroupName});
                    }
                    skuGroupSort.skugrouptlist.setItems(items);
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
        checkForDirtyTable : function () {
    		//alert('editSort '+skuGroupSort.skugrouptlist.isDirtied());
        	//if(skuGroupSort.skugrouptlist.isDirtied())g_isEdit == 1
			if(g_isEdit == 1)
        		return true;
        	else
        		return false;
        },
        confirmNavigation : function () {
        	if (skuGroupSort.checkForDirtyTable()) {
        		var ans = confirm(linb.Locale[linb.getLang()].app.caption['confirmNavigation']);
        		if (ans) {
        			g_isEdit=0;
        			return true;
        		}
        		else return false;
        	}
        	return true;
        },
        _categoriestab_beforeuivalueset : function (profile, oldValue, newValue) {
        	if (skuGroupSort.checkForDirtyTable()) {
        		var ans = confirm(linb.Locale[linb.getLang()].app.caption['confirmNavigation']);
        		if (ans) {
        			g_isEdit=0;
        			skuGroupSort.skugrouptlist.refresh();
        			return true;
        		}
        		else return false;
        	}
        	return true;
        }
    }
});