var g_isEdit=0;//toggle for edit
Class('App.profitPreference', 'linb.Com',{
    autoDestroy:true,
    Instance:{    
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};

            append((new linb.UI.Panel)
                .host(host,"profitPreference")
                .setDock("none")
                .setLeft(180)
                .setTop(59)
                .setWidth(820)
                .setHeight(520)
                .setZIndex(1)
                .setCaption("<center><b>$app.caption.profitPref</b></center>")
            );

            host.profitPreference.append((new linb.UI.Group)
                .host(host,"grpColumn")
                .setLeft(10)
                .setTop(20)
                .setWidth(500)
                .setHeight(450)
                .setCaption("")
                .setToggleBtn(false)
            );
            
            host.grpColumn.append((new linb.UI.Label)
                .host(host,"lblPriceTaxOption")
                .setLeft(20)
                .setTop(10)
                .setWidth(180)
                .setHAlign("left")
                .setCaption("<b>$app.caption.priceTaxOption</b>")
            );
            
            host.grpColumn.append((new linb.UI.RadioBox)
                .host(host,"rbPriceTaxOption")
                .setItems([{"id":"0", "caption":"$app.caption.pricewotax"}, {"id":"1", "caption":"$app.caption.pricewtax"}])
                .setLeft(30)
                .setTop(40)
                .setWidth(150)
                .setHeight(200)
                .setValue("1")
                .setDirtyMark(false)
                .onItemSelected("_rbPriceTaxOption_onitemselected")
                .setCustomStyle({"ITEMS":"overflow:visible", "KEY":"background:transparent;"})
                .onItemSelected("_rbPriceTaxOption_onitemselected")
            );
            
            host.grpColumn.append((new linb.UI.Label)
                .host(host,"lblDisplayTotalsOpt")
                .setLeft(20)
                .setTop(130)
                .setWidth(150)
                .setHAlign("left")
                .setCaption("<b>$app.caption.displayTotalsOption</b>")
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"chkTotalSellingPrice")
                .setLeft(30)
                .setTop(160)
                .setWidth(200)
                .setDirtyMark(false)
                .setCaption("$app.caption.totalSellingPrice")
                .onChecked("_chkTotalSellingPrice_onchecked")
            );
            
            host.grpColumn.append((new linb.UI.RadioBox)
                    .host(host,"rbSellingPriceOption")
                    .setItems([{"id":"1", "caption":"$app.caption.withPackageQtyLbl"}, {"id":"0", "caption":"$app.caption.withoutPackageQtyLbl"}])
                    .setLeft(170)
                    .setTop(160)
                    .setWidth(320)
                    .setHeight(200)
                    .setValue("1")
                    .setDirtyMark(false)
                    .onItemSelected("_rbSellingPriceOption_onitemselected")
                    .setCustomStyle({"ITEMS":"display: inline-block", "KEY":"background:transparent;"})
                    .onItemSelected("_rbSellingPriceOption_onitemselected")
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"chkTotalProfit")
                .setLeft(30)
                .setTop(190)
                .setWidth(200)
                .setDirtyMark(false)
                .setCaption("$app.caption.totalProfit")
                .onChecked("_chkTotalProfit_onchecked")
            );
            
            host.grpColumn.append((new linb.UI.CheckBox)
                .host(host,"chkTotalProfitPercent")
                .setLeft(30)
                .setTop(220)
                .setWidth(200)
                .setDirtyMark(false)
                .setCaption("$app.caption.totalProfitPercent")
                .onChecked("_chkTotalProfitPercent_onchecked")
            );
   
            host.grpColumn.append((new linb.UI.Button)
                .host(host,"btnSave")
                .setLeft(180)
                .setTop(375)
                .setWidth(70)
                .setCaption("$app.caption.save")
                .onClick("_btnSave_onclick")
            );
            
            host.grpColumn.append((new linb.UI.Button)
                .host(host,"btnClose")
                .setLeft(260)
                .setTop(375)
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
        	userProfitPref = page;
        	this.loadProfitPreference();
        },
        _onready:function() {
        	userProfitPref = this;
        }, 
        _rbPriceTaxOption_onitemselected:function (profile, item, src) {
        	g_isEdit = 1;
        },
        _rbSellingPriceOption_onitemselected:function (profile, item, src) {
        	g_isEdit = 1;
        },
        _chkTotalSellingPrice_onchecked:function (profile, item, src) {
        	g_isEdit = 1;
        },
        _chkTotalProfit_onchecked:function (profile, item, src) {
        	g_isEdit = 1;
        },
        _chkTotalProfitPercent_onchecked:function (profile, item, src) {
        	g_isEdit = 1;
        },
        _btnClose_onclick:function (profile, e, src, value) {

        	if (userProfitPref.confirmNavigation()) {
	    		g_currentId = 0;
	            this.destroy();
        	}
        },
        _btnSave_onclick:function (profile, e, src, value) {
	    	var profitPref = new Object();
	    	profitPref.priceTaxOption = userProfitPref.rbPriceTaxOption.getUIValue();
	    	profitPref.totalSellingPrice = userProfitPref.chkTotalSellingPrice.getUIValue() == true ? "1" : "0";
	    	profitPref.totalProfitPercent = userProfitPref.chkTotalProfitPercent.getUIValue() == true ? "1" : "0";
	    	profitPref.totalProfit = userProfitPref.chkTotalProfit.getUIValue() == true ? "1" : "0";
	    	profitPref.withPackageQuantity = userProfitPref.rbSellingPriceOption.getUIValue();
	    	
	    	linb.Ajax(
		        "/eON/userPref/saveProfitPreference.json",
		        profitPref,
		        function (response) {
	                validateResponse(response);
                    alert(linb.Locale[linb.getLang()].app.caption['alertsaved']);
                	g_isEdit = 0;
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
        loadProfitPreference : function () {
			var obj = new Object();
	        linb.Ajax(
    	        "/eON/userPref/loadProfitPreference.json",
    	        obj,
    	        function (response) {
    	        	//alert(response);
                    validateResponse(response);
                    var obj = new Object();
                    var items = new Array();
                    obj = _.unserialize(response);
                    profitPref = obj.profitPref;
                    userProfitPref.rbPriceTaxOption.setUIValue(profitPref.priceTaxOption);
                    userProfitPref.chkTotalSellingPrice.setUIValue(profitPref.totalSellingPrice == "1" ? true : false);
                    userProfitPref.chkTotalProfit.setUIValue(profitPref.totalProfit == "1" ? true : false);
                    userProfitPref.chkTotalProfitPercent.setUIValue(profitPref.totalProfitPercent == "1" ? true : false);
                    userProfitPref.rbSellingPriceOption.setUIValue(profitPref.withPackageQuantity);
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
        	if(g_isEdit == 1)
        		return true;
        	else
        		return false;
        },
        confirmNavigation : function () {
        	if (userProfitPref.checkForDirtyTable()) {
        		var ans = confirm(linb.Locale[linb.getLang()].app.caption['confirmNavigation']);
        		if (ans) {
        			return true;
        		}
        		else return false;
        	}
        	return true;
        }
    }
});