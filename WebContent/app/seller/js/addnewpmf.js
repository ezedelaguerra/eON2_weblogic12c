
Class('App.newpmf', 'linb.Com',{
    Instance:{
	
		//start:jr
	 	customAppend:function(){
		    var self=this, prop = this.properties;
		    
		    if(prop.fromRegion)
		        self.dlgNewPmf.setFromRegion(prop.fromRegion);
		
		    if(!self.dlgNewPmf.get(0).renderId)
		        self.dlgNewPmf.render();
		    	self.dlgNewPmf.show(self.parent, true);
		}, 
	    //end:jr
	
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0));};

            append((new linb.UI.Dialog)
                .host(host,"dlgNewPmf")
                .setLeft(350)
                .setTop(41)
                .setWidth(370)
                .setHeight(190)
                .setCaption("$app.caption.newpmf")
                .setMinBtn(false)
                .setMaxBtn(false)
                .setCloseBtn(false)
                .setPinBtn(false)
                .setResizer(false)
            );
            
            host.dlgNewPmf.append((new linb.UI.Group)
                .host(host,"grpNewPmf")
                .setLeft(2)
                .setTop(10)
                .setWidth(350)
                .setHeight(130)
                .setCaption("$app.caption.createpmf")
            );
            
            host.grpNewPmf.append((new linb.UI.Label)
                .host(host,"lblNewPmfSellerName")
                .setLeft(0)
                .setTop(20)
                .setWidth(100)
                .setCaption("$app.caption.sellername")
                .setVAlign("middle")
            );
            
            host.grpNewPmf.append((new linb.UI.ComboInput)
                .host(host,"cbiSellername")
                .setLeft(160)
                .setTop(20)
                .setWidth(180)
                .onChange("_cbisellername_onchange")
            );
            
            host.grpNewPmf.append((new linb.UI.Label)
                .host(host,"lblNewPmfName")
                .setLeft(0)
                .setTop(50)
                .setWidth(100)
                .setCaption("$app.caption.pmfname")
                .setVAlign("middle")
            );
            
            host.grpNewPmf.append((new linb.UI.Input)
                .host(host,"iptNewPmfName")
                .setLeft(160)
                .setTop(50)
                .setWidth(180)
            );
            
            host.grpNewPmf.append((new linb.UI.Button)
                .host(host,"btnNewPmfSave")
                .setLeft(60)
                .setTop(80)
                .setWidth(100)
                .setCaption("$app.caption.createpmf")
                .onClick("_btnnewpmfsave_onclick")
            );
            
            host.grpNewPmf.append((new linb.UI.Button)
                .host(host,"btnNewPmfCancel")
                .setLeft(190)
                .setTop(80)
                .setWidth(100)
                .setCaption("$app.caption.cancel")
                .onClick("_btnnewpmfcancel_onclick")
            );
            
            host.grpNewPmf.append((new linb.UI.Button)
                .host(host,"lblRoleName")
                .setLeft(190)
                .setTop(60)
                .setWidth(100)
                .setVisibility("hidden")
            );

            return children;
            // ]]code created by jsLinb UI Builder
        },
        events:{"onReady":"_onready", "onRender":"_onRender"},
        iniResource:function(com, threadid){
        },
        iniExComs:function(com, hreadid){
        },
        _onRender:function(page, threadid){
        	newPmf = page;
        	var obj = new Object();
	        	linb.Ajax(
			        "/eON/skuGroup/getSellers.json",
			        null,
			        function (response) {
			        	validateResponse(response);
			        	newPmf.responseCheck(response);
			        	// set seller name in sku group window
			        	var o = _.unserialize(response);
			        	var items = o.response;
	//		        	var nuv=[];
	//		        	nuv.push(items);
			        	newPmf.cbiSellername.setItems(items, true);
			        	var g_sellerId = newPmf.cbiSellername.setUIValue(items[0].caption);
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
	        	
	        	if (g_userRole == "ROLE_SELLER"){
	        		newPmf.cbiSellername.setDisabled(true);
				}
        },
        _onready:function(page, threadid){
        	newPmf = this;
        }, 
        _btnnewpmfcancel_onclick:function (profile, e, src, value) {
        	this.dlgNewPmf.destroy();
        }, 
        _btnnewpmfsave_onclick:function (profile, e, src, value) {
        	var pmfNameTrim = this.iptNewPmfName.getUIValue().trim();
        	
        	if (this.iptNewPmfName.getUIValue()=="" || this.iptNewPmfName.getUIValue()==null || pmfNameTrim.length == 0){
        		var pmfAlert = linb.Locale['ja'].app.caption['pmfnamerequiredalert'];
        		alert(pmfAlert);
        	} else {
        		var obj = new Object();
        		obj.btnClicked = "newPmfSave";
        		obj.pmfName = this.iptNewPmfName.getUIValue();
        		obj.pmfUserId = g_sellerId;
        		obj.searchName = "";
        		var url = "/eON/pmf/pmfNewPmf.json";
        		linb.Ajax(
    				url,
    				obj,
    				function (response) {
    					validateResponse(response);
    					var o = _.unserialize(response);
    					if (obj.pmfName != o.response){
    						alert(o.response);
    					} else {
    						linb.ComFactory.newCom('App.masterlist' ,
	                    		function(){
	                    			this.show(null, sheetName.paneSubCom);
	                    		}
	                        );
    					}
    				}, 
    				function(msg) {
    					linb.message("失敗： " + msg);
    				}, null, {
    					method : 'GET'
    				}
        		).start();
        		
        		this.dlgNewPmf.destroy();
        	}
       },
       responseCheck : function (jsonResponse) {
    	   var obj = _.unserialize(jsonResponse);
    	   if (obj.fail == 'true') {
    		   linb.pop("responseCheck", obj.globals, "OK");
       	   }
       },
       _cbisellername_onchange:function (profile, oldValue, newValue) {
    	   var id;
    	   var items = newPmf.cbiSellername.getItems();
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