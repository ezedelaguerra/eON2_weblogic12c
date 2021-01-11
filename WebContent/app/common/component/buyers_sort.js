var g_companyId;
var g_isEdit=0;//toggle for edit
Class('App.buyersSort', 'linb.Com',{
    autoDestroy:true,
    Instance:{    
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};

            append((new linb.UI.Panel)
                .host(host,"buyerSortPanel")
                .setDock("none")
                .setLeft(180)
                .setTop(59)
                .setWidth(820)
                .setHeight(520)
                .setZIndex(1)
                .setCaption("<center><b>$app.caption.sortbuyer</b></center>")
            );

            host.buyerSortPanel.append((new linb.UI.Group)
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
            
//            host.companyGrp.append((new linb.UI.Button)
//                .host(host,"btnCompanySearch")
//                .setLeft(290)
//                .setTop(1)
//                .setWidth(60)
//                .setTabindex(1003)
//                .setCaption("$app.caption.search")
//                .onClick("_btncompanysearch_onclick")
//            );
            host.buyerSortPanel.append((new linb.UI.Group)
                .host(host,"companySortgrp")
                .setLeft(40)
                .setTop(60)
                .setWidth(400)
                .setHeight(420)
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
                    .host(host,"buyerlist")
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
        	buyersSort = page;
        	g_isEdit=0;
        },
        _onready:function() {
            //alert("ready");
        	buyersSort = this;
        	//var companyId = buyersSort.cbiCompanyname.getUIValue();
        	//var items = buyersSort.cbiCompanyname.getItems();
        	//alert(_.serialize(items));
        	//var companyId = items[0].id;
        	//alert(companyId);
            //this.loadBuyers(companyId);
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
        	if (buyersSort.confirmNavigation()) {
        		if(g_userRole != "ROLE_BUYER_ADMIN")
        			this.fireEvent('onCloseCompanySort');
        		else g_currentId = 0;
        		this.destroy();
        	}
        },
        _btnsave_onclick:function (profile, e, src, value) {
            buyersSort.saveBuyerSorting();
            //this.destroy();
        },
        saveBuyerSorting:function() {
        	//alert('Saving...');
        	var rightItems = buyersSort.buyerlist.getItems();
        	var buyerSortList = new Array();
        	for (var i=0; i<rightItems.length; i++) {
        		var buyerSorts = new Object();
        		//buyerSorts.companyId = g_companyId;
        		buyerSorts.buyerId = rightItems[i].id;
        		buyerSorts.sorting = i+1;
        		buyerSortList.push(buyerSorts);
        	}
        	
        	var url = "/eON/saveBuyersSort.json?param=" + _.serialize(buyerSortList);
            linb.Ajax(
                url,
                buyerSortList,
                function (response) {
                    //alert(response);
                    validateResponse(response);
                    alert("登録されました。");
                    g_isEdit=0;
                    buyersSort.buyerlist.refresh();
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
        	//alert('length ' +buyersSort.companylist.getItems().length);
        	//alert('value '+buyersSort.companylist.getUIValue());
        	//alert('vallength '+buyersSort.companylist.getUIValue().length);
        	if (buyersSort.buyerlist.getItems().length == 0 ||
        			buyersSort.buyerlist.getUIValue() == null ||
        			buyersSort.buyerlist.getUIValue().length == 0)
        		return;
        	var uiVal = buyersSort.buyerlist.getUIValue();
        	var rightSelects = buyersSort.buyerlist.getUIValue().split(";");
        	var rightItems = buyersSort.buyerlist.getItems();
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
        	buyersSort.buyerlist.setItems(newRightItems);
        	buyersSort.buyerlist.refresh();
        	buyersSort.buyerlist.setUIValue(uiVal);
        },
		loadBuyers: function (companyId) {
			var obj = new Object();
	        obj.companyId = companyId;
	        //this.categoriesTab.append(this.companylist, g_categoryId);
	        linb.Ajax(
    	        "/eON/initBuyersSort.json",
    	        obj,
    	        function (response) {
    	        	//alert(response);
                    validateResponse(response);
                    var obj = new Object();
                    var items = new Array();
                    obj = _.unserialize(response);
                    for(var i=0; i<obj.buyersSorts.length; i++){
                    	var buyer = obj.buyersSorts[i].buyer;
                        items.push({"id":buyer.userId,
                            "caption":buyer.name});
                    }
                    buyersSort.buyerlist.setItems(items);
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
        	var items = buyersSort.cbiCompanyname.getItems();
        	for (var i=0; i<items.length; i++) {
    			var thisObj = items[i];
    			if (newValue == thisObj.caption) {
    				id = thisObj.id;
    				buyersSort.cbiCompanyname.setValue(thisObj.caption);
    				break;
    			}
    		}
        	g_companyId = id;
        	buyersSort.loadBuyers(id);
        	
        },
        checkForDirtyTable : function () {
    		//alert('editSort '+skuGroupSort.skugrouptlist.isDirtied());
        	//if(buyersSort.buyerlist.isDirtied())
        	if(g_isEdit == 1)
        		return true;
        	else
        		return false;
        },
        confirmNavigation : function () {
        	if (buyersSort.checkForDirtyTable()) {
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