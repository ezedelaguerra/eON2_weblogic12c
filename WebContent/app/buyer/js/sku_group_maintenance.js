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
                .setTop(20)
                //.setWidth(365)
                .setHeight(342)
                .onItemSelected("_categoriestab_onitemselected")
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
            
        	this.grpSKUGroup.setWidth(600);
        	this.categoriesTab.setWidth(450);
    		this.skugrouptgd.setWidth(510);
            /*this.skugrouptgd.setHeader([{"id":"delete", "width":80, "type":"checkbox", "caption":"$app.caption.select", "editable":"true"}, 
                                        {"id":"id", "type":"label", "hidden" : true},
                                        {"id":"origSkuGroupId", "width":150, "type":"label", "hidden" : true}, 
                                        {"id":"sellerId", "width":150, "type":"label", "hidden" : true},
                                        {"id":"sellerName", "width":150, "type":"label", "caption":"$app.caption.sellername", "editable":"false", "cellStyle":"text-align:center;"},
                                        {"id":"categoryId", "width":150, "type":"label", "hidden" : true},
                                        {"id":"skuGroupName", "width":260, "type":"input", "caption":"$app.caption.name", "cellStyle":"text-align:center;"}
                                        ]);*/
            this.skugrouptgd.setHeader([{"id":"delete", "width":80, "type":"checkbox", "caption":"$app.caption.select", "editable":"true", "hidden":true}, 
                                        {"id":"id", "type":"label", "hidden" : true},
                                        {"id":"origSkuGroupId", "width":150, "type":"label", "hidden" : true}, 
                                        {"id":"sellerId", "width":150, "type":"label", "hidden" : true},
                                        {"id":"sellerName", "width":150, "type":"label", "caption":"$app.caption.sellername", "editable":false, "cellStyle":"text-align:center;"},
                                        {"id":"categoryId", "width":150, "type":"label", "hidden" : true},
                                        {"id":"skuGroupName", "width":260, "type":"input", "caption":"$app.caption.name", "cellStyle":"text-align:center;", "editable":false}
                                        ]);
            
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
        _btnClose_onclick:function (profile, e, src, value) {
        	g_currentId = 0;
            this.destroy();
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
                	//alert(response);
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
    }
});