
Class('App.productmasterlist', 'linb.Com',{
    Instance:{
	
		//start:jr
	 	customAppend:function(){
		    var self=this, prop = this.properties;
		    
		    if(prop.fromRegion)
		        self.dialogMasterFileList.setFromRegion(prop.fromRegion);
		
		    if(!self.dialogMasterFileList.get(0).renderId)
		        self.dialogMasterFileList.render();
		    	self.dialogMasterFileList.show(self.parent, true);
		}, 
	    //end:jr
	
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0));};

            append((new linb.UI.Dialog)
                .host(host,"dialogMasterFileList")
                .setLeft(350)
                .setTop(41)
                .setHeight(310)
                .setWidth(310)
                .setResizer(false)
                .setCaption("$app.caption.pmflist")
                .setMinBtn(false)
                .setMaxBtn(false)
                .setCloseBtn(false)
                .setPinBtn(false)
            );

            host.dialogMasterFileList.append((new linb.UI.Group)
                .host(host,"group39")
                .setLeft(10)
                .setTop(10)
                .setWidth(270)
                .setHeight(250)
                .setZIndex(0)
                .setCaption("$app.caption.listofpmf")
            );

            host.group39.append((new linb.UI.RadioBox)
                .host(host,"rboPmfList")
                .setLeft(10)
                .setTop(10)
                .setWidth(250)
                .setHeight(180)
                .setZIndex(1003)
                .setValue("")
                .onItemSelected("_rbopmflist_onitemselected")
                .setCustomStyle({"ITEM":"display:block", "KEY":"background:#transparent;scrollbars:0"})
                .setPosition("absolute")
            );

            host.group39.append((new linb.UI.Button)
                .host(host,"btnSelect")
                .setLeft(100)
                .setTop(200)
                .setWidth(70)
                .setCaption("$app.caption.select")
                .onClick("_btnselect_onclick")
            );

            host.group39.append((new linb.UI.Button)
                .host(host,"btnCancel")
                .setLeft(180)
                .setTop(200)
                .setWidth(70)
                .setCaption("$app.caption.cancel")
                .onClick("_btncancel_onclick")
            );

            host.group39.append((new linb.UI.Button)
                .host(host,"btnNew")
                .setLeft(20)
                .setTop(200)
                .setWidth(70)
                .setCaption("$app.caption.createpmf")
                .onClick("_btnnew_onclick")
            );
            
            host.group39.append((new linb.UI.Input)
                .host(host,"hdnPmfId")
                .setLeft(90)
                .setTop(50)
                .setVisibility("hidden")
            );
            
            host.group39.append((new linb.UI.Input)
                    .host(host,"hdnPmfName")
                    .setLeft(110)
                    .setTop(50)
                    .setVisibility("hidden")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        },
        events:{"onReady":"_onready", "onRender" : "_onRender"},
        iniResource:function(com, threadid){
        },
        iniExComs:function(com, hreadid){
        },
        _onRender:function(page, threadid){
        	SPA = page;
        },
        _onready:function(){
            SPA = this;
        	var url = "/eON/pmf/pmfList.json";
            linb.Ajax(
                url,
                null,
                function (response) {
                	validateResponse(response);
                	var o = _.unserialize(response);
                	for(var i=0;i<o.response.length;i++){
                		var val = [{id : o.response[i].pmfId ,caption : o.response[i].pmfName}];
                		SPA.rboPmfList.insertItems(val,null,false);
                		
                		//for Seller Admins, Create PMF button should be disabled
                		if (mainSPA._excom1.lblRole.getCaption() == 2){ 
                			SPA.btnNew.setDisabled(true);
                		}
                	}
                }, 
                function(msg) {
                        linb.message("失敗： " + msg);
                    }, null, {
                        method : 'POST',
        	        	retry : 0
                    }
            ).start();
            
        	if (mainSPA.lblSheet.getCaption() == 10001) {
        		sheetName = 'orderSheet';
        	} else if (mainSPA.lblSheet.getCaption() == 10003) {
        		sheetName = 'allocSheet';
        	}else if (mainSPA.lblSheet.getCaption() == 10020) {
        		sheetName = 'akadenSheet';
        	}else if (mainSPA.lblSheet.getCaption() == 10005) {
        		sheetName = 'orderSheet';
        	}
        },
        _btnselect_onclick:function (profile, e, src, value) {
            if(this.hdnPmfId.value=="" || this.hdnPmfId.value==null){
            	var pmfAlert = linb.Locale['ja'].app.caption['pmfnoselectalert'];
            	alert(pmfAlert);
            } else {
	        	var obj = new Object();
	            obj.pmfName = this.hdnPmfName.value;
	            obj.pmfId = this.hdnPmfId.value;
	            obj.searchName = "";
	            obj.btnClicked = "pmfSelect";
	            obj.categoryId = g_orderSheetParam.categoryId;
	            var url = "/eON/pmf/pmfSetSession.json";
	            
	            linb.Ajax(
	                url,
	                obj,
	                function (response) {
	                	validateResponse(response);
	                	var o = _.unserialize(response);
//	                	alert(o.response);
	                	linb.ComFactory.newCom('App.masterlist' ,
                    		function(){
	                			//masterlist = this;
	                			//masterlist.hdnPmfNameSelect.setValue(SPA.hdnPmfName.value);
                    			this.show(null, sheetName.paneSubCom);
                    		}
                        );
                	}, 
	                function(msg) {
	                        linb.message("失敗： " + msg);
	                    }, null, {
	                        method : 'POST'
	                    }
	            ).start();
	
//	        	window.open('../html/mastersheetlist.html', '_newWindow', 'left=10,top=100,width=1000,height=590,toolbar=no,resizable=0,status=0,location=no,menubar=no,scrollbars=0');
//	            this.destroy();
	            this.dialogMasterFileList.destroy();
            }
        },
        _btncancel_onclick:function (profile, e, src, value) {
        	this.dialogMasterFileList.destroy();
        },
        _btnnew_onclick:function (profile, e, src, value) {
//        	this.dlgNewPmf.show();
        	linb.ComFactory.newCom('App.newpmf' ,
        		function(){
        			this.show(null, sheetName.paneSubCom);
        		}
            );
        	this.dialogMasterFileList.destroy();
        }, 
        _rbopmflist_onitemselected:function (profile, item, src) {
            this.hdnPmfId.value = item.id;
            this.hdnPmfName.value = item.caption;
        }
    }
});