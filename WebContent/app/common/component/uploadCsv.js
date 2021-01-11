
Class('App.uploadcsv', 'linb.Com',{
    Instance:{
        customAppend:function(){
		    var self=this, prop = this.properties;
		    
		    if(prop.fromRegion)
		        self.dlgUploadCsv.setFromRegion(prop.fromRegion);
		
		    if(!self.dlgUploadCsv.get(0).renderId)
		        self.dlgUploadCsv.render();
		
		    self.dlgUploadCsv.show(self.parent, true);
		}, 
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
	            .host(host,"dlgUploadCsv")
                .setLeft(0)
                .setTop(0)
                .setWidth(1000)
                .setHeight(580)
	            .setCaption("商品登録アップロード")
	            .setPinBtn(false)
                .setMinBtn(false)
                .setMaxBtn(false)
                .setCloseBtn(false)
                .setResizer(false)
                .setMovable(false)
	        );
            
            host.dlgUploadCsv.append((new linb.UI.Label)
                .host(host,"label1")
                .setLeft(40)
                .setTop(50)
                .setCaption("$app.caption.exceldategroup：")
            );
            
            host.dlgUploadCsv.append((new linb.UI.Input)
                .host(host,"iptUploadDate")
                .setLeft(160)
                .setTop(50)
                .setWidth(170)
                .setReadonly(true)
            );
            
            host.dlgUploadCsv.append((new linb.UI.Label)
                .host(host,"label4")
                .setLeft(40)
                .setTop(80)
                .setCaption("$app.caption.skuuploadfile：")
            );
            
            host.dlgUploadCsv.append((new linb.UI.ComboInput)
                .host(host,"cboBrowse")
                .setLeft(160)
                .setTop(80)
                .setWidth(250)
                .setType("upload")
            );
            
            host.dlgUploadCsv.append((new linb.UI.Button)
                .host(host,"btnUpload")
                .setLeft(40)
                .setTop(110)
                .setCaption("$app.caption.uploadfile")
                .onClick("_btnupload_onclick")
            );
            
            host.dlgUploadCsv.append((new linb.UI.Button)
                .host(host,"btnUploadCancel")
                .setLeft(170)
                .setTop(110)
                .setCaption("$app.caption.skusortcancel")
                .onClick("_btnuploadcancel_onclick")
            );
            
            host.dlgUploadCsv.append((new linb.UI.TreeGrid)
                .host(host,"tgUpload")
                .setDock("none")
                .setLeft(30)
                .setTop(140)
                .setWidth(906)
                .setHeight(300)
                .setAltRowsBg(true)
                .setRowHandler(false)
                .setColResizer(false)
                .setColSortable(false)
                .setHeader([
                 {"id":"rowNum", "width":200, "cellStyle":"text-align:center", "type":"label", "caption":"<b>$app.caption.skuuploadrownum</b>"},
                 {"id":"colNum", "width":200, "cellStyle":"text-align:center", "type":"label", "caption":"<b>$app.caption.skuuploadcolnum</b>"}, 
                 {"id":"errorMsg", "width":500, "type":"label", "caption":"<b>$app.caption.skuuploaderrormsg</b>"} 
                 ])
                 .setVisibility("hidden")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        },
		events:{"onReady":"_onready","onRender":"_onrender"}, 
		_onready:function() {
		    uploadCsv = this;
		    var deliveryDate = orderSheet.lblSelectedDate.getCaption();
		    var dateFormatted = deliveryDate.substring(0,4) + "/" + deliveryDate.substring(4,6) + "/" + deliveryDate.substring(6,8); 
		    uploadCsv.iptUploadDate.setValue(dateFormatted);
		}, 
		_btnuploadcancel_onclick: function(profile, e, src, value){
			this.dlgUploadCsv.hide();
		    sellerMenu.menubar_seller.setVisibility("visible");
		},
		_btnupload_onclick: function(profile, e, src, value){
			if (this.cboBrowse.getUIValue().length == 0){
				var uploadRequiredAlert = linb.Locale['ja'].app.caption['skuuploadfilenamerequired'];
        		alert(uploadRequiredAlert);
			} else {
				var url = "/eON/upload/validateCsv.json";
	        	var obj = new Object();
	        	obj.fileName = this.cboBrowse.getUIValue();
	            linb.IAjax(url, {file:this.cboBrowse.getUploadObj()},{method:'post'},
	                function (response) {
	                	var o = _.unserialize(response);
	                	if(o == false){
	                		uploadCsv._uploadCsv();
	                	}			
	                }, 
	                function(msg) {
	                        linb.message("失敗： " + msg);
	                    }, null, {
	                        method : 'GET'
	                    }
	            ).start();
			}
        },
        
        _uploadCsv:function(){
        	var url = "/eON/upload/uploadCsv.json";
        	var obj = new Object();
        	obj.fileName = this.cboBrowse.getUIValue();
            linb.Ajax(url, obj,
                function (response) {
            	validateResponse(response);
            	var o = _.unserialize(response);
                	//var o = _.unserialize(response);
                	if(o.errorFlag == 1){
                		uploadCsv.tgUpload.setVisibility("visible");
                    	uploadCsv.tgUpload.setRows(o.uploadError);
                	} 
                    	
                    else {
                    	uploadCsv.dlgUploadCsv.hide();
            		    sellerMenu.menubar_seller.setVisibility("visible");
                		var uploadSuccess = linb.Locale['ja'].app.caption['skuuploaded'];
                		alert(uploadSuccess);
                    	orderSheet.loadOrderSheet();
                	}
                }, 
                function(msg) {
                        linb.message("失敗： " + msg);
                    }, null, {
                        method : 'GET'
                    }
            ).start();
        }
    }



//linb.request(url, 
//{file:this.cboBrowse.getUploadObj()}, 
//function (response) {
//	var o = _.unserialize(response);
//	if(o.errorFlag == 1){
//		uploadCsv.tgUpload.setVisibility("visible");
//    	uploadCsv.tgUpload.setRows(o.uploadError);
//	} else {
//    	uploadCsv.dlgUploadCsv.hide();
//	    sellerMenu.menubar_seller.setVisibility("visible");
//		var uploadSuccess = linb.Locale['ja'].app.caption['skuuploaded'];
//		alert(uploadSuccess);
//    	orderSheet.loadOrderSheet();
//	}
//}, 
//function(msg) {
//	linb.message("失敗： " + msg);
//	}, null, {
//		method:'POST'
//	}
//).start();

//linb.request(url, 
//{file:this.cboBrowse.getUploadObj()}, 
//function(){}, 
//function(msg) {
//linb.message("失敗： " + msg);
//}, null, {
//	method:'POST'
//}
//).start();
















});