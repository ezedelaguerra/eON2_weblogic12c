var g_sheetTypeId; //global var for page Ids

Class('App.menubar', 'linb.Com', {
    Instance: {
        iniComponents:function(){
		    var host=this, children=[], append=function(child){children.push(child.get(0))};
		
		    append((new linb.UI.Pane)
                .host(host,"paneMenu")
                //.setDock("top")
                .setLeft(0)
                .setTop(31)
                .setWidth(1080)
				.setHeight(28)
            );

			host.paneMenu
				.append((new linb.UI.MenuBar)
				.host(host, "menubar")
				.setHandler(false)
				.onMenuSelected("_menubar_buyer_onmenuselected")
				.setAutoShowTime(0)
			);
 
            host.paneMenu.append((new linb.UI.Label)
                .host(host,"lblTitle")
                .setTop(5)
                .setWidth(400)
                .setLeft(300)
                .setCaption("<font size='3px;'>$app.caption.homepage</font>")
                .setHAlign("center")
                .setFontSize("14")
                .setFontWeight("bold")
            );

			host.paneMenu.append((new linb.UI.Label)
                .host(host,"lblHiddenSheetTypeId")
                .setTop(5)
                .setWidth(400)
                .setLeft(300)
                .setCaption("")
                .setHAlign("center")
                .setVisibility("hidden")
            );

//			host.paneMenu.append((new linb.UI.Label)
//				.host(host,"lblTelephone")
//				.setTop(5)
//				.setWidth(250)
//				.setRight(10)
//				.setCaption("<b>Fresh Remix Corp. Tel No. 81-3-5822-1566</b>"));
			
			host.paneMenu.append((new linb.UI.Button)
                .host(host,"lblRoundOffStatus")
                .setLeft(190)
                .setTop(60)
                .setWidth(100)
                .setVisibility("hidden")
            );
			
			host.paneMenu.append((new linb.UI.Button)
                .host(host,"lblSheetCode")
                .setLeft(190)
                .setTop(60)
                .setWidth(100)
                .setVisibility("hidden")
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
        	buyerMenu = page;
        	buyerMenu.menubar.setItems(getMenuItems('home'));
        	if(g_clientWidth > buyerMenu.paneMenu.getWidth()){
        		buyerMenu.paneMenu.setWidth(g_clientWidth);
            }
            if(g_clientHeight > buyerMenu.paneMenu.getHeight() + 60){
            	buyerMenu.paneMenu.setHeight(g_clientHeight - 60);
        	}
        },
        _onready: function() {
        	buyerMenu = this;
        	var g_sheetTypeId = 0;
        	buyerMenu.menubar.setItems(getMenuItems("home"));
        	
        },
        _menubar_buyer_onmenuselected:function (profile, popProfile, item, src) {
        	
        	/**  
        	 * check first if there are unsaved changes
        	 * from util.js
        	 * */        	
        	if(checkUnsavedChanges()){ 
        		return;
        	}
        	
//        	if (buyerAdminFilter != null)
//        		buyerAdminFilter.destroy();
  
        	//start:jr
        	if (item.id=="subuprefEnableBAPublishOrder") { 
        		if(item.value == true){
        			orderSheet.btnPublish.setVisibility("visible");
        			//orderSheet.btnPublish.setDisabled(false);
        		}else{
        			orderSheet.btnPublish.setVisibility("hidden");
//        			orderSheet.btnPublish.setDisabled(true);
        		}
        		
        		buyerMenu.savePreference(item.value, "0");
    			return;
        	}
        	//end:jr
        	        	
        	/* added by jovs */
        	if (item.id=="utilDownloadCSV") { 
        		linb.ComFactory.newCom('App.downloadcsv' ,
    				function(){
        				var ns = this;
    					this.show(linb([document.body]));
    				});
    			return;
        	}
        	/* added by jovs */
        	
        	if (item.id == "utilDownloadExcel") {
        		var sheetTypeId = this.lblHiddenSheetTypeId.getCaption();
        		linb.ComFactory.newCom(
        			'App.downloadExcel', 
        			function(){
        				downloadExcel.lblHiddenSheetTypeId.setCaption(sheetTypeId);
        				this.setEvents({'onDownloadExcel' : function(paramObj) { 
        					buyerMenu.downloadExcel(paramObj);
        				}});
        				this.show(null, buyerMenu.paneSubCom);
        			});
        		return;
        	}
        	
        	if(item.id=="subuprefShowcols"){
        		buyerMenu.showHideColumns();
        		return;
        	} else if(item.id =="fileChangePassword"){
          		 var host=this;
                   linb.ComFactory.newCom('App.changepassword' ,function(){
                       var ns = this;
                       ns.lblUserId.setCaption(header.lbluseridinfo.getCaption());
                       ns.iptUsername.setValue(header.hdnUsername.getCaption());
                       ns.iptUsername.setDisabled(true);
                       ns.show(linb([document.body]));
                   });   
                   
                   return;
           	} else if (item.id=="fileLogoff") {
        		window.open("/eON/j_spring_security_logout", "_self");
        		return;
        	} else if (item.id=="editRefresh") {
        		//if comment sheet
        		var sheetTypeId = this.lblHiddenSheetTypeId.getCaption();
        		if(sheetTypeId == 99999){
        			this.lblTitle.setCaption("<font size='3px;'>$app.caption.comments</font>");
        		}
        		else if(sheetTypeId == 10000 || sheetTypeId == 10004 || sheetTypeId == 10006) {
        			if (currentContent != null)
        				currentContent.refreshment();
        			return;
        		}
        		else {
	        		var f = document.getElementById('dataTableIframe');
	        		if (f != null)
	    			f.contentWindow.location.reload(true);
	        		return;
        		}
        	} else if (item.id=="helpAbout") {
        		linb.ComFactory.newCom('App.aboutEON', function() {
                	this.show(linb( [ document.body ]));
                });
        		return;
        	} else if (item.id=="subuprefUnitOrder"){
         		linb.ComFactory.newCom('App.orderunit', function() {
                 	this.show(linb( [ document.body ]));
                 });
         		return;
         	} else if (item.id=="utilUploadFile"){
        		linb.ComFactory.newCom('App.uploadcsv', function() {
                	this.show(linb( [ document.body ]));
                });
        		return;
        	} //start:jr
        	else if (item.id=="fileUserDetails") {
        		linb.ComFactory.newCom('App.userdetails', function() {
			        this.show(linb( [ document.body ]));
	            });	
        		return;
        	}
        	//end:jr
        	else if (item.id=="roundoff1"){
         		this.lblRoundOffStatus.setCaption("default");
         		buyerMenu.roundOffPriceWTax();
     			return;
         	} else if (item.id=="roundoff2"){
         		this.lblRoundOffStatus.setCaption("lower");
         		buyerMenu.roundOffPriceWTax();
     			return;
         	} else if (item.id=="roundoff3"){
         		this.lblRoundOffStatus.setCaption("higher");
         		buyerMenu.roundOffPriceWTax();
     			return;
         	} else if (item.id=="mnuHome") {
        		mainSPA.clearTheSession();
        		this.lblTitle.setCaption("<font size='3px;'>$app.caption.homepage</font>");
        		sheetTypeId = 99996;
        		buyerMenu.disableMenus();
        	} else if (item.id=="mnuOrdersheet") {
        		this.lblTitle.setCaption("<font size='3px;'>$app.caption.buyerordersheet</font>");
        		sheetTypeId = 10000;
        		buyerMenu.disableMenus();
        	} else if(item.id=="mnuBillingsheet"){
        		this.lblTitle.setCaption("<font size='3px;'>$app.caption.buyerbillingsheet</font>");
        		sheetTypeId = 10006;
        		buyerMenu.disableMenus();
        	} else if (item.id=="mnuReceivedsheet") {
        		this.lblTitle.setCaption("<font size='3px;'>$app.caption.receivedsheet</font>");
        		sheetTypeId = 10004;
        		buyerMenu.disableMenus();
        	} else if (item.id=="mnuComments") {
        		buyerMenu.menubar.updateItem('utilDownloadCSV', {disabled:true});
        		buyerMenu.menubar.updateItem('utilUploadFile', {disabled:true});
        		this.lblTitle.setCaption("<font size='3px;'>$app.caption.comments</font>");
        		sheetTypeId = 99999;
        		mainSPA.clearTheSession();
        		this.menubar.updateItem('mnuEdit', {disabled:false});
        	} else if (item.id=="mnuUserpreference") {
        		mainSPA.clearTheSession();
        		this.lblTitle.setCaption("<font size='3px;'>$app.caption.userpreference</font>");
        		sheetTypeId = 99998;
        		this.menubar.updateItem('mnuEdit', {disabled:false});
        	}
        	
        	buyerMenu.menubar.updateItem('utilDownloadExcel', {disabled:true});
        	this.lblHiddenSheetTypeId.setCaption(sheetTypeId);
        	this.lblHiddenSheetTypeId.refresh();
        	this.lblTitle.refresh();
        	mainSPA._switchContent(sheetTypeId);
        },
        roundOffPriceWTax: function(){
        	var price;
        	var price1;
			if (g_orderSheetParam.sheetTypeId == 10000 || buyerMenu.lblSheetCode.getCaption() == "10000"){ //buyer order
				if (orderSheet.lblRoundOffSheet.getCaption() == "loadBuyerOrder"){
					price = buyerMenu.roundPrice(orderSheet.hdntotpricewtax.getUIValue());
					orderSheet.totpricewtax.setValue(buyerMenu.roundPrice(price));
					
					price1 = orderSheet.hdnGtPriceWTax.getUIValue();
					orderSheet.gtPriceWTax.setValue(buyerMenu.roundPrice(price1));
				}
			} else if (g_orderSheetParam.sheetTypeId == 10006 || buyerMenu.lblSheetCode.getCaption() == "10006"){//buyer billing
				if (billingSheet.lblRoundOffSheet.getCaption() == "loadBuyerBilling"){
					price = buyerMenu.roundPrice(billingSheet.hdntotpricewtax.getUIValue());
					billingSheet.totpricewtax.setValue(buyerMenu.roundPrice(price));
					
					price1 = billingSheet.hdnGtPriceWTax.getUIValue();
					billingSheet.gtPriceWTax.setValue(buyerMenu.roundPrice(price1));
				}
			} else if (g_orderSheetParam.sheetTypeId == 10004 || buyerMenu.lblSheetCode.getCaption() == "10004"){//buyer received 
				if (receivedSheet.lblRoundOffSheet.getCaption() == "loadReceived"){
					price = buyerMenu.roundPrice(receivedSheet.hdntotpricewtax.getUIValue());
					receivedSheet.totpricewtax.setValue(buyerMenu.roundPrice(price));
					
					price1 = receivedSheet.hdnGtPriceWTax.getUIValue();
					receivedSheet.gtPriceWTax.setValue(buyerMenu.roundPrice(price1));
				}
			}
        },
        roundPrice: function(price){
        	var priceRounded = 0 ;

    		if (buyerMenu.lblRoundOffStatus.getCaption() == "lower"){
    			priceRounded = Math.floor(price);
//    			alert("priceRounded FLOOR: " + priceRounded);
    		} else if (buyerMenu.lblRoundOffStatus.getCaption() == "higher"){
    			priceRounded = Math.ceil(price);
//    			alert("priceRounded CEIL: " + priceRounded);
    		} else {
    			priceRounded = Math.round(price);
//    			alert("priceRounded ROUND: " + priceRounded);
    		}
    		
    		if(isNaN(priceRounded)){
    			priceRounded = 0; 
    		}
    		
    		return toJapaneseCurrency(priceRounded);
        },
        showHideColumns: function(){
        	var sigmaSheet = 'dataTableIframe';
        	var f = document.getElementById(sigmaSheet);
			f.contentWindow.showDialogHideColumns();
        },
        disableMenus: function(){
            buyerMenu.menubar.updateItem('subuprefRoundoff', {disabled:true});
            buyerMenu.menubar.updateItem('roundoff1', {disabled:true});
            buyerMenu.menubar.updateItem('roundoff2', {disabled:true});
            buyerMenu.menubar.updateItem('roundoff3', {disabled:true});
            buyerMenu.menubar.updateItem('subuprefShowcols', {disabled:true});
        },savePreference : function (value,isAllocation) {
        	var url = "/eON/saveUserPreference.json";

        	linb.Ajax(
    	        url + "?value="+value+"&chkBox="+ isAllocation,
    	        null,
    	        function (response) {
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
        downloadExcel : function (paramObj) {
        	var f = document.getElementById('dataTableIframe');
        	f.contentWindow.submitTempForm(_.serialize(paramObj));
        }
    }
});
