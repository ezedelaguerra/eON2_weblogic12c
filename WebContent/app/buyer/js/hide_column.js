Class('App.hideColumnMaintenance', 'linb.Com',{
    autoDestroy:true,
    Instance:{    
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};

            append((new linb.UI.Panel)
                .host(host,"hideColMaintenance")
                .setDock("none")
                .setLeft(180)
                .setTop(59)
                .setWidth(820)
                .setHeight(520)
                .setZIndex(1)
                .setCaption("<center><b>$app.caption.hidecolumnmaintenance</b></center>")
            );

            host.hideColMaintenance.append((new linb.UI.Group)
                .host(host,"grpColumn")
                .setLeft(10)
                .setTop(20)
                .setWidth(790)
                .setHeight(450)
                .setCaption("")
                .setToggleBtn(false)
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"companyName")
                .setDirtyMark(false)
                .setLeft(10)
                .setTop(10)
                .setWidth(200)
                .setCaption("$app.caption.companyname")
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"sellerName")
                .setDirtyMark(false)
                .setLeft(10)
                .setTop(40)
                .setWidth(200)
                .setCaption("$app.caption.sellername")
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"groupName")
                .setDirtyMark(false)
                .setLeft(10)
                .setTop(70)
                .setWidth(200)
                .setCaption("$app.caption.groupname")
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"marketCondition")
                .setDirtyMark(false)
                .setLeft(10)
                .setTop(100)
                .setWidth(200)
                .setCaption("$app.caption.marketcondition")
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column01")
                .setDirtyMark(false)
                .setLeft(10)
                .setTop(130)
                .setWidth(145)
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column02")
                .setDirtyMark(false)
                .setLeft(10)
                .setTop(160)
                .setWidth(145)
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column03")
                .setDirtyMark(false)
                .setLeft(10)
                .setTop(190)
                .setWidth(145)
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column04")
                .setDirtyMark(false)
                .setLeft(10)
                .setTop(220)
                .setWidth(145)
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column06")
                .setDirtyMark(false)
                .setLeft(10)
                .setTop(250)
                .setWidth(145)
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column07")
                .setDirtyMark(false)
                .setLeft(10)
                .setTop(280)
                .setWidth(145)
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column08")
                .setDirtyMark(false)
                .setLeft(10)
                .setTop(310)
                .setWidth(145)
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column09")
                .setDirtyMark(false)
                .setLeft(10)
                .setTop(340)
                .setWidth(145)
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"skuName")
                .setDirtyMark(false)
                .setLeft(10)
                .setTop(370)
                .setWidth(200)
                .setCaption("$app.caption.skuname")
            );
            
            // next column
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"areaProduction")
                .setDirtyMark(false)
                .setLeft(195)
                .setTop(10)
                .setWidth(200)
                .setCaption("$app.caption.areaproduction")
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"clazz1")
                .setDirtyMark(false)
                .setLeft(195)
                .setTop(40)
                .setWidth(200)
                .setCaption("$app.caption.class1")
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"clazz2")
                .setDirtyMark(false)
                .setLeft(195)
                .setTop(70)
                .setWidth(200)
                .setCaption("$app.caption.class2")
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"pricewotax")
                .setDirtyMark(false)
                .setLeft(195)
                .setTop(100)
                .setWidth(200)
                .setCaption("$app.caption.pricewotax")
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"pricewtax")
                .setDirtyMark(false)
                .setLeft(195)
                .setTop(130)
                .setWidth(200)
                .setCaption("$app.caption.pricewtax")
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"purchasePrice")
                .setDirtyMark(false)
                .setLeft(195)
                .setTop(160)
                .setWidth(200)
                .setCaption("$app.caption.purchaseprice")
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column10")
                .setDirtyMark(false)
                .setLeft(195)
                .setTop(190)
                .setWidth(145)
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column05")
                .setDirtyMark(false)
                .setLeft(195)
                .setTop(220)
                .setWidth(145)
            );            
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column11")
                .setDirtyMark(false)
                .setLeft(195)
                .setTop(250)
                .setWidth(145)
            );            
            
            host.grpColumn.append((new linb.UI.CheckBox)
            	.host(host,"sellingPrice")
            	.setDirtyMark(false)
            	.setLeft(195)
            	.setTop(280)
            	.setWidth(200)
            	.setCaption("$app.caption.sellingprice")
            );
	      
            host.grpColumn.append((new linb.UI.CheckBox)
            	.host(host,"sellingUom")
            	.setDirtyMark(false)
            	.setLeft(195)
            	.setTop(310)
            	.setWidth(200)
            	.setCaption("$app.caption.sellinguom")
            );
      
            host.grpColumn.append((new linb.UI.CheckBox)
	    	    .host(host,"profitPercentage")
	          	.setDirtyMark(false)
	          	.setLeft(195)
	          	.setTop(340)
	          	.setWidth(200)
	          	.setCaption("$app.caption.profitPercentage")
	      	);
            
            host.grpColumn.append((new linb.UI.CheckBox)
	    	    .host(host,"packageQty")
	          	.setDirtyMark(false)
	          	.setLeft(195)
	          	.setTop(370)
	          	.setWidth(200)
	          	.setCaption("$app.caption.packageqty")
	      	);
            
            // next column
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"packageType")
                .setDirtyMark(false)
                .setLeft(380)
                .setTop(10)
                .setWidth(200)
                .setCaption("$app.caption.packagetype")
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"uom")
                .setDirtyMark(false)
                .setLeft(380)
                .setTop(40)
                .setWidth(200)
                .setCaption("$app.caption.uom")
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"skuComment")
                .setDirtyMark(false)
                .setLeft(380)
                .setTop(70)
                .setWidth(200)
                .setCaption("$app.caption.skucomment")
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"rowQty")
                .setDirtyMark(false)
                .setLeft(380)
                .setTop(100)
                .setWidth(200)
                .setCaption("$app.caption.rowqty")
            );

            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column12")
                .setDirtyMark(false)
                .setLeft(380)
                .setTop(130)
                .setWidth(145)
            );   
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column13")
                .setDirtyMark(false)
                .setLeft(380)
                .setTop(160)
                .setWidth(145)
            );   
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column14")
                .setDirtyMark(false)
                .setLeft(380)
                .setTop(190)
                .setWidth(145)
            );   
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column15")
                .setDirtyMark(false)
                .setLeft(380)
                .setTop(220)
                .setWidth(145)
            );   
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column16")
                .setDirtyMark(false)
                .setLeft(380)
                .setTop(250)
                .setWidth(145)
            );   
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column17")
                .setDirtyMark(false)
                .setLeft(380)
                .setTop(280)
                .setWidth(145)
            );   
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column18")
                .setDirtyMark(false)
                .setLeft(380)
                .setTop(310)
                .setWidth(145)
            );   
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column19")
                .setDirtyMark(false)
                .setLeft(380)
                .setTop(340)
                .setWidth(145)
            );   
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"column20")
                .setDirtyMark(false)
                .setLeft(380)
                .setTop(370)
                .setWidth(145)
            );   
            
            // next column 575 (left)
            
            host.grpColumn.append((new linb.UI.Button)
                .host(host,"btnSave")
                .setLeft(285)
                .setTop(400)
                .setWidth(70)
                .setCaption("$app.caption.save")
                .onClick("_btnSave_onclick")
            );
            
            host.grpColumn.append((new linb.UI.Button)
                .host(host,"btnClose")
                .setLeft(405)
                .setTop(400)
                .setWidth(70)
                .setCaption("$app.caption.close")
                .onClick("_btnClose_onclick")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        },
        events:{
            "onReady" : "_onready", "onRender":"_onrender", "onDestroy":"_ondestroy"
        },
        _ondestroy:function (com) {
            g_sortWindow = null;
        },
        _onrender:function(page, threadid){
        	hideColMaint = page;
        	this.loadUserPreference();
        },
        _onready:function() {
        	hideColMaint = this;
        },
        _btnClose_onclick:function (profile, e, src, value) {
    		g_currentId = 0;
            this.destroy();
        },
        _btnSave_onclick:function (profile, e, src, value) {
        	
        	this.btnSave.setDisabled(true);
        	this.btnClose.setDisabled(true);
        	
        	var param = new Object();
        	param.companyName = this.companyName.getUIValue() == true ? "1" : "0";
        	param.sellerName = this.sellerName.getUIValue() == true ? "1" : "0";
        	param.groupName = this.groupName.getUIValue() == true ? "1" : "0";
        	param.marketCondition = this.marketCondition.getUIValue() == true ? "1" : "0";
        	param.skuName = this.skuName.getUIValue() == true ? "1" : "0";
        	param.areaProduction = this.areaProduction.getUIValue() == true ? "1" : "0";
        	param.clazz1 = this.clazz1.getUIValue() == true ? "1" : "0";
        	param.clazz2 = this.clazz2.getUIValue() == true ? "1" : "0";
        	param.priceWOTax = this.pricewotax.getUIValue() == true ? "1" : "0";
        	param.priceWTax = this.pricewtax.getUIValue() == true ? "1" : "0";
        	param.purchasePrice = this.purchasePrice.getUIValue() == true ? "1" : "0";
        	param.sellingPrice = this.sellingPrice.getUIValue() == true ? "1" : "0";
        	param.sellingUom = this.sellingUom.getUIValue() == true ? "1" : "0";
        	param.profitPercentage = this.profitPercentage.getUIValue() == true ? "1" : "0";
        	param.packageQty = this.packageQty.getUIValue() == true ? "1" : "0";
        	param.packageType = this.packageType.getUIValue() == true ? "1" : "0";
        	param.uom = this.uom.getUIValue() == true ? "1" : "0";
        	param.skuComment = this.skuComment.getUIValue() == true ? "1" : "0";
        	param.rowQty = this.rowQty.getUIValue() == true ? "1" : "0";
        	param.column01 = this.column01.getUIValue() == true ? "1" : "0";
        	param.column02 = this.column02.getUIValue() == true ? "1" : "0";
        	param.column03 = this.column03.getUIValue() == true ? "1" : "0";
        	param.column04 = this.column04.getUIValue() == true ? "1" : "0";
        	param.column05 = this.column05.getUIValue() == true ? "1" : "0";
        	param.column06 = this.column06.getUIValue() == true ? "1" : "0";
        	param.column07 = this.column07.getUIValue() == true ? "1" : "0";
        	param.column08 = this.column08.getUIValue() == true ? "1" : "0";
        	param.column09 = this.column09.getUIValue() == true ? "1" : "0";
        	param.column10 = this.column10.getUIValue() == true ? "1" : "0";
        	param.column11 = this.column11.getUIValue() == true ? "1" : "0";
        	param.column12 = this.column12.getUIValue() == true ? "1" : "0";
        	param.column13 = this.column13.getUIValue() == true ? "1" : "0";
        	param.column14 = this.column14.getUIValue() == true ? "1" : "0";
        	param.column15 = this.column15.getUIValue() == true ? "1" : "0";
        	param.column16 = this.column16.getUIValue() == true ? "1" : "0";
        	param.column17 = this.column17.getUIValue() == true ? "1" : "0";
        	param.column18 = this.column18.getUIValue() == true ? "1" : "0";
        	param.column19 = this.column19.getUIValue() == true ? "1" : "0";
        	param.column20 = this.column20.getUIValue() == true ? "1" : "0";
        	
        	linb.Ajax(
    	        "/eON/userPref/saveHideColumn.json",
    	        param,
    	        function (response) {
                    validateResponse(response);
                    alert(linb.Locale[linb.getLang()].app.caption['columnsettingsaved']);
                    hideColMaint.btnSave.setDisabled(false);
                    hideColMaint.btnClose.setDisabled(false);
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
        loadUserPreference : function () {
        	this.btnSave.setDisabled(true);
        	this.btnClose.setDisabled(true);
        	linb.Ajax(
        	        "/eON/userPref/loadHideColumn.json",
        	        null,
        	        function (response) {
                        validateResponse(response);
                        var res = _.unserialize(response);
                        hideColMaint.renderColumns(res.hideColumn);
                        hideColMaint.renderColumnNames(res.nameMap);
                        hideColMaint.btnSave.setDisabled(false);
                        hideColMaint.btnClose.setDisabled(false);
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
        renderColumns : function (hc) {
        	this.companyName.setUIValue(hc.companyName == "1" ? true : false);
        	this.sellerName.setUIValue(hc.sellerName == "1" ? true : false);
        	this.groupName.setUIValue(hc.groupName == "1" ? true : false);
        	this.marketCondition.setUIValue(hc.marketCondition == "1" ? true : false);
        	this.skuName.setUIValue(hc.skuName == "1" ? true : false);
        	this.areaProduction.setUIValue(hc.areaProduction == "1" ? true : false);
        	this.clazz1.setUIValue(hc.clazz1 == "1" ? true : false);
        	this.clazz2.setUIValue(hc.clazz2 == "1" ? true : false);
        	this.pricewotax.setUIValue(hc.priceWOTax == "1" ? true : false);
        	this.pricewtax.setUIValue(hc.priceWTax == "1" ? true : false);
        	this.purchasePrice.setUIValue(hc.purchasePrice == "1" ? true : false);
        	this.sellingPrice.setUIValue(hc.sellingPrice == "1" ? true : false);
        	this.sellingUom.setUIValue(hc.sellingUom == "1" ? true : false);
        	this.profitPercentage.setUIValue(hc.profitPercentage == "1" ? true : false);
        	this.packageQty.setUIValue(hc.packageQty == "1" ? true : false);
        	this.packageType.setUIValue(hc.packageType == "1" ? true : false);
        	this.uom.setUIValue(hc.uom == "1" ? true : false);
        	this.skuComment.setUIValue(hc.skuComment == "1" ? true : false);
        	this.rowQty.setUIValue(hc.rowQty == "1" ? true : false);
        	this.column01.setUIValue(hc.column01 == "1" ? true : false);
        	this.column02.setUIValue(hc.column02 == "1" ? true : false);
        	this.column03.setUIValue(hc.column03 == "1" ? true : false);
        	this.column04.setUIValue(hc.column04 == "1" ? true : false);
        	this.column05.setUIValue(hc.column05 == "1" ? true : false);
        	this.column06.setUIValue(hc.column06 == "1" ? true : false);
        	this.column07.setUIValue(hc.column07 == "1" ? true : false);
        	this.column08.setUIValue(hc.column08 == "1" ? true : false);
        	this.column09.setUIValue(hc.column09 == "1" ? true : false);
        	this.column10.setUIValue(hc.column10 == "1" ? true : false);
        	this.column11.setUIValue(hc.column11 == "1" ? true : false);
        	this.column12.setUIValue(hc.column12 == "1" ? true : false);
        	this.column13.setUIValue(hc.column13 == "1" ? true : false);
        	this.column14.setUIValue(hc.column14 == "1" ? true : false);
        	this.column15.setUIValue(hc.column15 == "1" ? true : false);
        	this.column16.setUIValue(hc.column16 == "1" ? true : false);
        	this.column17.setUIValue(hc.column17 == "1" ? true : false);
        	this.column18.setUIValue(hc.column18 == "1" ? true : false);
        	this.column19.setUIValue(hc.column19 == "1" ? true : false);
        	this.column20.setUIValue(hc.column20 == "1" ? true : false);
        },
        renderColumnNames : function (nameMap) {
        	this.column01.setCaption(nameMap.column01);
        	this.column02.setCaption(nameMap.column02);
        	this.column03.setCaption(nameMap.column03);
        	this.column04.setCaption(nameMap.column04);
        	this.column05.setCaption(nameMap.column05);
        	this.column06.setCaption(nameMap.column06);
        	this.column07.setCaption(nameMap.column07);
        	this.column08.setCaption(nameMap.column08);
        	this.column09.setCaption(nameMap.column09);
        	this.column10.setCaption(nameMap.column10);
        	this.column11.setCaption(nameMap.column11);
        	this.column12.setCaption(nameMap.column12);
        	this.column13.setCaption(nameMap.column13);
        	this.column14.setCaption(nameMap.column14);
        	this.column15.setCaption(nameMap.column15);
        	this.column16.setCaption(nameMap.column16);
        	this.column17.setCaption(nameMap.column17);
        	this.column18.setCaption(nameMap.column18);
        	this.column19.setCaption(nameMap.column19);
        	this.column20.setCaption(nameMap.column20);
        }
    }
});