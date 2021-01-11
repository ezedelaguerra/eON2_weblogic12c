/*
 	Copyright Freshremix Asia Software Corporation
	date		name		version	changes
	==============================================
	20120913	lele		chrome	Redmine 880 - Chrome compatibility
 * */

Class('App.addSKUGroup', 'linb.Com', {
	autodestroy : true, 
    Instance: {
	
		//start:jr
		//set the mouse icon to busy status
		customAppend:function(){
	    var self=this, prop = this.properties;
	    if(prop.fromRegion)
	        self.dialogSKUGroup.setFromRegion(prop.fromRegion);
	    if(!self.dialogSKUGroup.get(0).renderId)
	        self.dialogSKUGroup.render();
	    self.dialogSKUGroup.show(self.parent, true);
		}, 
		//end:jr
	
        iniComponents:function(){
		    var host=this, children=[], append=function(child){children.push(child.get(0));};
		
		    append((new linb.UI.Dialog)
                .host(host,"dialogSKUGroup")
                .setLeft(100)
                .setTop(100)
                .setWidth(350)
                .setHeight(220)
                .setResizer(false)
                .setCaption("$app.caption.addskugroup")
                .setMinBtn(false)
                .setMaxBtn(false)
                .setCloseBtn(false)
                .setPinBtn(false)
            );
		    
		    host.dialogSKUGroup.append((new linb.UI.Group)
                .host(host,"skuGroup")
                .setLeft(10)
                .setTop(10)
                .setWidth(325)
                .setHeight(160)
                .setCaption("$app.caption.additionOfNewSKUGroup")
            );
            
            host.skuGroup.append((new linb.UI.Label)
                .host(host,"lblSkuGroupSellerName")
                .setLeft(10)
                .setTop(20)
                .setWidth(110)
                .setCaption("$app.caption.sellername")
            );
            
            host.skuGroup.append((new linb.UI.ComboInput)
                .host(host,"cbiSellername")
                .setLeft(127)
                .setTop(20)
                .setWidth(183)
                .onChange("_cbiSellername_onchange")
            );
            
            host.skuGroup.append((new linb.UI.Label)
                .host(host,"lblSkuGroupName")
                .setLeft(6)
                .setTop(60)
                .setCaption("<font color ='red'>*</font> $app.caption.groupname")
            );
            
            host.skuGroup.append((new linb.UI.Input)
                .host(host,"inptSKUGroupName")
                .setLeft(127)
                .setTop(60)
                .setWidth(183)
                .setMaxlength("50")
            );
            
            host.skuGroup.append((new linb.UI.Button)
                .host(host,"btnSaveSkuGroup")
                .setLeft(130)
                .setTop(110)
                .setWidth(80)
                .setCaption("$app.caption.save")
                .onClick("_btnSaveSkuGroup_onclick")
            );
            
            host.skuGroup.append((new linb.UI.Button)
                .host(host,"btnCancelSkuGroup")
                .setLeft(220)
                .setTop(110)
                .setWidth(80)
                .setCaption("$app.caption.cancel")
                .onClick("_btnCancelSkuGroup_onclick")
            );
            
            host.skuGroup.append((new linb.UI.Pane)
                .host(host,"hiddenPane")
                .setVisibility(false)
            );
            
            host.hiddenPane.append((new linb.UI.Label)
            	.host(host,"hiddenCategoryValue")
            	.setCaption("")
            );
            
            return children;
		},
        events: {
            "onReady" : "_onready", "onRender":"_onRender"
        },
        iniResource: function(com, threadid) {
        },
        iniExComs: function(com, hreadid) {
        },
        _onRender:function(page, threadid){
        	addSKUGroup = page;
        	addSKUGroup.hiddenPane.hide();
        	var obj = new Object();
//        	if(g_isFromSheet == "N"){
//        		obj.isFromSheet = "N";
//        	}else{
//        		obj.isFromSheet = "Y";
//        	}
	        	linb.Ajax(
			        "/eON/skuGroup/getSellers.json",
			        null,
			        function (response) {
			        	validateResponse(response);
			        	addSKUGroup.responseCheck(response);
			        	// set seller name in sku group window
			        	var o = _.unserialize(response);
			        	var items = o.response;
	//		        	var nuv=[];
	//		        	nuv.push(items);
	//		        	addSKUGroup.cbiSellername.setItems(nuv);
			        	addSKUGroup.cbiSellername.setItems(items, true);
			        	var g_sellerId = addSKUGroup.cbiSellername.setUIValue(items[0].caption);
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
//        	}else{
//        		addSKUGroup.cbiSellername.setItems(g_orderSheetParam.selectedSellerID, true);
//        		var g_sellerId = addSKUGroup.cbiSellername.setUIValue(g_orderSheetParam.selectedSellerID[0].caption);
//        	}

			// ENHANCEMENT START 20120913: Lele - Redmine 880
			var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
            
            if (is_chrome) {
            	addSKUGroup.dialogSKUGroup.setWidth(360);
            	addSKUGroup.skuGroup.setWidth(335);
            }
            // ENHANCEMENT END 20120913:

        },
        _onready: function() {
        	addSKUGroup = this;
        },
        _btnCancelSkuGroup_onclick:function (profile, e, src, value) {
        	this.destroy();
        }, 
        _btnSaveSkuGroup_onclick:function (profile, e, src, value) {
        	var result = 'success';
        	var url = "/eON/skuGroup/saveSkuGroup.json";
            var obj = new Object();
            //obj.sellerId = this.cbiSellername.getUIValue();
            obj.sellerId = g_sellerId;
            obj.categoryId = this.hiddenCategoryValue.getCaption();
            //obj.status = "0"; commented by rhoda for sku grp maint status is remove.
            obj.description = this.inptSKUGroupName.getUIValue().trim(); 
            if(obj.description == ""){
            		alert(linb.Locale[linb.getLang()].app.caption['skugroupdescriptionrequired']);
            		
            	return false;
            }
            linb.Ajax(
                url,
                obj,
                function (response) {
                	validateResponse(response);
                	//addSKUGroup.responseCheck(response);
                	var obj = _.unserialize(response);
                	if (obj.fail == 'true') {
                		alert(obj.globals);
                	} 
                	else {
                		result = 'success';
                		addSKUGroup.fireEvent('onAddSKUGroup', [result]);
                    	addSKUGroup.destroy();
                	}
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
        responseCheck : function (jsonResponse) {
        	var obj = _.unserialize(jsonResponse);
        	if (obj.fail == 'true') {
        		linb.pop("responseCheck", obj.globals, "OK");
        	}
        },
        _cbiSellername_onchange:function (profile, oldValue, newValue) {
        	var id;
        	var items = addSKUGroup.cbiSellername.getItems();
        	for (var i=0; i<items.length; i++) {
    			var thisObj = items[i];
    			if (newValue == thisObj.caption) {
    				id = thisObj.id;
    				break;
    			}
    		}
        	g_sellerId = id;
        	
        }
    }
});