/*
 	Copyright Freshremix Asia Software Corporation
	date		name		version	changes
	==============================================
	20120913	lele		chrome	Redmine 880 - Chrome compatibility
 * */
 
Class('App.orderunit', 'linb.Com',{
    Instance:{
		autoDestroy:true, 
		iniComponents:function(){
	        // [[code created by jsLinb UI Builder
	        var host=this, children=[], append=function(child){children.push(child.get(0))};
	        
	        append((new linb.UI.Dialog)
	            .host(host,"dlgOrderUnit")
	            .setLeft(360)
	            .setTop(150)
	            .setHeight(330)
	            .setCaption("$app.caption.unitorder")
	            .setMinBtn(false)
	            .setMaxBtn(false)
	            .setPinBtn(false)
	        );
            
            host.dlgOrderUnit.append((new linb.UI.Group)
                .host(host,"grpOrderUnitAdd")
                .setLeft(10)
                .setTop(10)
                .setWidth(270)
                .setHeight(70)
                .setCaption("$app.caption.uomAdd")
            );
            
            host.grpOrderUnitAdd.append((new linb.UI.Input)
                .host(host,"iptOrderUnit")
                .setLeft(10)
                .setTop(10)
                .setWidth(180)
                .setMaxlength("15")
            );
            
            host.grpOrderUnitAdd.append((new linb.UI.Button)
                .host(host,"btnOrderUnitSave")
                .setLeft(200)
                .setTop(10)
                .setWidth(60)
                .setCaption("$app.caption.save")
                .onClick("_btnorderunitsave_onclick")
            );
            
            host.dlgOrderUnit.append((new linb.UI.Group)
                .host(host,"grpOrderUnitList")
                .setLeft(10)
                .setTop(90)
                .setWidth(270)
                .setHeight(190)
                .setCaption("$app.caption.uomList")
            );
            
            host.grpOrderUnitList.append((new linb.UI.TreeGrid)
                .host(host,"tgOrderUnit")
                .setDock("none")
                .setLeft(10)
                .setTop(10)
                .setWidth(250)
                .setHeight(120)
                .setAltRowsBg(true)
                .setRowHandler(false)
                .setHeader([{"id":"col1", "width":240, "type":"label", "caption":"$app.caption.uomUnitName", "headerStyle":"font-weight:bold", "cellStyle":"text-align:center"}])
                .onRowSelected("_tgorderunit_onrowselected")
            );
            
            host.grpOrderUnitList.append((new linb.UI.Button)
                .host(host,"btnOrderUnitCancel")
                .setLeft(180)
                .setTop(140)
                .setWidth(70)
                .setCaption("$app.caption.cancel")
                .onClick("_btnorderunitcancel_onclick")
            );
            
            host.grpOrderUnitList.append((new linb.UI.Button)
                .host(host,"btnOrderUnitDelete")
                .setLeft(100)
                .setTop(140)
                .setWidth(70)
                .setDisabled(true)
                .setCaption("$app.caption.deletes")
                .onClick("_btnorderunitdelete_onclick")
            );
            
            host.grpOrderUnitAdd.append((new linb.UI.Input)
                .host(host,"hdnOrderUnit")
                .setLeft(10)
                .setTop(10)
                .setWidth(180)
                .setVisibility("hidden")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events:{"onReady" : "_onready", "onRender":"_onRender"}, 
        iniResource:function(com, threadid){
        }, 
        customAppend:function(parent,subId,left,top){
            return false;
        }, 
        iniExComs:function(com, hreadid){
        },
        _onRender:function(page, threadid){
        	unitOrderMenu = page;
        	
        	// ENHANCEMENT START 20120913: Lele - Redmine 880
        	var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
            
            if (is_chrome) {
            	unitOrderMenu.dlgOrderUnit.setWidth(320);
            	unitOrderMenu.grpOrderUnitAdd.setWidth(280);
            	unitOrderMenu.grpOrderUnitList.setHeight(200);
            	unitOrderMenu.grpOrderUnitList.setWidth(280);
            	unitOrderMenu.tgOrderUnit.setWidth(260);
            }
            // ENHANCEMENT END 20120913:
        },
        _onready: function() {
        	unitOrderMenu = this;
        	unitOrderMenu.loadOrderUnit();
        }, 
        _btnorderunitdelete_onclick:function (profile, e, src, value) {
        	var deleteConfirm = confirm(linb.Locale[g_LangKey].app.caption['confirmDeleteUom']);
        	if (deleteConfirm){
        		var url = "/eON/orderUnit/deleteOrderUnit.json";
        		var obj = new Object();
        		obj.orderUnitId = unitOrderMenu.hdnOrderUnit.value;

        		linb.Ajax(url,obj, function(response){
        			validateResponse(response);
        			var o = _.unserialize(response);
        			if (o.status == "1"){
                    	alert(linb.Locale[g_LangKey].app.caption['unitorderinuse']);
                    } else {
            			unitOrderMenu.loadOrderUnit();
            			orderSheet.loadOrderSheet();
                    }
        		},
        		function(msg){
        			linb.message("失敗： " + msg);
        		},
        		null,
        		{method:'POST',retry:0}).start();
//      		this.dlgOrderUnit.hide();
        	}

        	unitOrderMenu.btnOrderUnitDelete.setDisabled(true);
        }, 
        _btnorderunitcancel_onclick:function (profile, e, src, value) {
            this.dlgOrderUnit.destroy();
        },
        _btnorderunitsave_onclick:function(profile, e, src, value){
        	var url = "/eON/orderUnit/saveOrderUnit.json";
        	var obj = new Object();
        	obj.orderUnitName = unitOrderMenu.iptOrderUnit.getUIValue().replace(/^[\s\u3000]+|[\s\u3000]+$/g, '');
        	if (obj.orderUnitName != ""){
	        	linb.Ajax(url,obj,
	                function(response){
	            		validateResponse(response);
	                    var o = _.unserialize(response);
	                    if (o.status == "1"){
	                    	alert(linb.Locale[g_LangKey].app.caption['unitorderexistsalert']);
	                    } else {
	                    	unitOrderMenu.loadOrderUnit();
	                    	orderSheet.loadOrderSheet();
	                    }
	            },
	            function(msg){
	                linb.message("失敗： " + msg);
	            },
	            null,
	            {method:'POST',retry:0}).start();
            
        	unitOrderMenu.btnOrderUnitDelete.setDisabled(true);
        	}else{
        		alert(linb.Locale[g_LangKey].app.caption['unitordernamerequiredalert']);
        	}
        },
        _tgorderunit_onrowselected:function(profile, row, src){
//        	alert(row.cells[0].value);//name
//        	alert(row.cells[1].value);//id
        	unitOrderMenu.hdnOrderUnit.value = row.cells[1].value;
        	unitOrderMenu.btnOrderUnitDelete.setDisabled(false);
        },
        loadOrderUnit:function(){
        	var url = "/eON/orderUnit/loadOrderUnit.json";
            linb.Ajax(url,null,
                function(response){
            		validateResponse(response);
                    var o = _.unserialize(response);
                    unitOrderMenu.tgOrderUnit.setRows(o.orderUnitList);
            },
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
        }
    }
});