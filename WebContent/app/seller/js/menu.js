//<%--
// * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
// * <BR>
// * <B>Description:</B><BR>
// * Describe class or interface.<BR>
// * <BR>
// * <B>Known Bugs:</B>
// * none<BR>
// * <BR>
// * <B>History:</B>
// * <PRE><!-- Do not use tabs in the history table! Do not extend table width! -->
// * date       	name	version		changes
// * ------------------------------------------------------------------------------
// * 20120831		Rhoda	v11			Redmine 102 - Enable the seller admin to have the only quantity display in the user preference menu
// *										(menu checkbox for Display Alloc Qty)
// --%>
 var g_sheetTypeId; //global var for page Ids
var g_isFilterActive = null;

Class('App.menubar', 'linb.Com', {
    Instance: {
        iniComponents:function(){
		    var host=this, children=[], append=function(child){children.push(child.get(0))};
		
		    append((new linb.UI.Pane)
                .host(host,"paneMenu")
                .setLeft(0)
                .setTop(31)
                .setWidth(1080)
				.setHeight(28)
            );
            
            host.paneMenu.append((new linb.UI.MenuBar)
                .host(host,"menubar_seller")
                .setHandler(false)
                .onMenuSelected("_menubar_seller_onmenuselected")
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
        	sellerMenu = page;
        	if(g_clientWidth > sellerMenu.paneMenu.getWidth()){
        		sellerMenu.paneMenu.setWidth(g_clientWidth);
        	}
        },
        _onready: function() {
        	sellerMenu = this;
        	var g_sheetTypeId = 0;
        	sellerMenu.menubar_seller.setItems(getMenuItems("home"));
        },
        _menubar_seller_onmenuselected:function (profile, popProfile, item, src) {
        	
        	/**  
        	 * check first if there are unsaved changes
        	 * from util.js
        	 * */        	
        	if(checkUnsavedChanges()){ 
        		return;
        	}
        	
        	if(item.id=="subuprefShowUnpublishAllocation"){
        	    sellerMenu.savePreference(item.value,"0");
        		return;
        	}
        	
        	if(item.id=="subuprefShowUnfinalizeBilling"){
        		sellerMenu.savePreference(item.value,"1");
        		return;
        	}
        	
        	if(item.id=="subuprefAutoPublishAllocation"){
        	    sellerMenu.savePreference(item.value,"2");
        		return;
        	} 
        	
        	if(item.id=="subuprefDisplayAllocQty"){
        	    sellerMenu.savePreference(item.value,"3");
        		return;
        	}        	
        	
        	if (item.id=="utilDownloadCSV") { 
        		this.setProperties({
                    fromRegion:linb.use(src).cssRegion(true)
                });
        		linb.ComFactory.newCom('App.downloadcsv' ,
    				function(){
        				var ns = this;
    					this.show(linb([document.body]));
    				});
    			return;
        	}
        	
        	if (item.id == "utilDownloadExcel") {
        		var sheetTypeId = this.lblHiddenSheetTypeId.getCaption();
        		this.setProperties({
                    fromRegion:linb.use(src).cssRegion(true)
                });
        		linb.ComFactory.newCom(
        			'App.downloadExcel', 
        			function(){
        				downloadExcel.lblHiddenSheetTypeId.setCaption(sheetTypeId);
        				this.setEvents({'onDownloadExcel' : function(paramObj) { 
        					sellerMenu.downloadExcel(paramObj);
        				}});
        				this.show(null, sellerMenu.paneSubCom);
        			});
        		return;
        	}
        	
        	if (item.id == "utilPMF") {
        		//start:jr
            	//this will set the mouse icon to busy status
            	this.setProperties({
                    fromRegion:linb.use(src).cssRegion(true)
                });
            	//end:jr
            	//linb.UI.getFromDom('btnProdMasterFile').setDisabled(true);
                linb.ComFactory.newCom('App.productmasterlist' ,function(){
                	this.show(linb([document.body]));
                });
                return;
        	}
        	
        	if(item.id =="fileChangePassword"){
                 linb.ComFactory.newCom('App.changepassword' ,function(){
             		 this.setProperties({
                         fromRegion:linb.use(src).cssRegion(true)
                     });
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
        		var g_sheetTypeId = this.lblHiddenSheetTypeId.getCaption();
        		if(g_sheetTypeId == 99999){
        			this.lblTitle.setCaption("<font size='3px;'>$app.caption.comments</font>");
        		}
        		else if(g_sheetTypeId == 10001 || g_sheetTypeId == 10003 || g_sheetTypeId == 10020 || g_sheetTypeId == 10005) {
        			if (currentContent != null)
        				currentContent.refreshment();
        			return;
        		}
        		else {
        			//if Akaden sheet
	        		var f = document.getElementById('dataTableIframe');
	        		if (f != null)
	    			f.contentWindow.location.reload(true);
	        		return;
        		}
        	} else if (item.id=="utilRefresh") {
				mainSPA._switchContent(this.lblHiddenSheetTypeId.getCaption());
				return;
        	} else if (item.id=="helpAbout") {
        		linb.ComFactory.newCom('App.aboutEON', function() {
        			//start:jr
        			//sets mouse icon to busy
        			this
                    .setProperties({
                        fromRegion:linb.use(src).cssRegion(true)
                    });
                    //end:jr
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
        			this
                    .setProperties({
                        fromRegion:linb.use(src).cssRegion(true)
                    });
                	this.show(linb( [ document.body ]));
                });
        		return;
        	} else if (item.id=="roundoff1"){
        		this.lblRoundOffStatus.setCaption("default");
        		sellerMenu.roundOffPriceWTax();
    			return;
        	} else if (item.id=="roundoff2"){
        		this.lblRoundOffStatus.setCaption("lower");
        		sellerMenu.roundOffPriceWTax();
    			return;
        	} else if (item.id=="roundoff3"){
        		this.lblRoundOffStatus.setCaption("higher");
        		sellerMenu.roundOffPriceWTax();
    			return;
        	} else if (item.id=="fileUserDetails") {
        		this.setProperties({
                    fromRegion:linb.use(src).cssRegion(true)
                });
        		linb.ComFactory.newCom('App.userdetails', function() {
			        this.show(linb( [ document.body ]));
	            });	
        		return;
        	} else if (item.id=="mnuHome") {
        		mainSPA.clearTheSession();
        		this.lblTitle.setCaption("<font size='3px;'>$app.caption.homepage</font>");
        		g_sheetTypeId = 99995;
        		sellerMenu.disableMenus();
        	} else if (item.id=="mnuOrdersheet") {
        		this.lblTitle.setCaption("<font size='3px;'>$app.caption.ordersheet</font>");
        		g_sheetTypeId = 10001;
        		sellerMenu.disableMenus();
        	} else if (item.id=="mnuAllocationsheet") {
        		this.lblTitle.setCaption("<font size='3px;'>$app.caption.allocationsheet</font>");
        		g_sheetTypeId = 10003;
        		sellerMenu.disableMenus();
        	} else if (item.id=="mnuAkadensheet") {
        		this.lblTitle.setCaption("<font size='3px;'>$app.caption.akadensheet</font>");
        		g_sheetTypeId = 10020;
        		sellerMenu.disableMenus();
        	} else if (item.id=="mnuBillingsheet") {
        		this.lblTitle.setCaption("<font size='3px;'>$app.caption.billingsheet</font>");
        		g_sheetTypeId = 10005;
        		sellerMenu.disableMenus();
        	} else if (item.id=="mnuComments") {
        		sellerMenu.menubar_seller.updateItem('utilDownloadCSV', {disabled:true});
            	sellerMenu.menubar_seller.updateItem('utilUploadFile', {disabled:true});
        		this.lblTitle.setCaption("<font size='3px;'>$app.caption.comments</font>");
        		g_sheetTypeId = 99999;
        		mainSPA.clearTheSession();
        		this.menubar_seller.updateItem('mnuEdit', {disabled:false});
        	} else if (item.id=="mnuUserpreference") {
        		mainSPA.clearTheSession();
        		this.lblTitle.setCaption("<font size='3px;'>$app.caption.userpreference</font>");
        		g_sheetTypeId = 99997;
        	} 
        	
        	sellerMenu.menubar_seller.updateItem('utilDownloadExcel', {disabled:true});
        	this.lblHiddenSheetTypeId.setCaption(g_sheetTypeId);
        	this.lblHiddenSheetTypeId.refresh();
        	this.lblTitle.refresh();
        	mainSPA._switchContent(g_sheetTypeId);
        },
        disableMenus: function(){
        	g_isOneDayDownload = "true"; // flag to true if download csv is for 1 day
        	sellerMenu.menubar_seller.updateItem('subuprefRoundoff', {disabled:true});
        },
        downloadExcel : function (paramObj) {
        	var f = document.getElementById('dataTableIframe');
        	f.contentWindow.submitTempForm(_.serialize(paramObj));
        },savePreference : function (value,isAllocation) {
        	var url = "/eON/saveUserPreference.json";
        
        	linb.Ajax(
    	        url + "?value="+value+"&chkBox="+ isAllocation,
    	        null,
    	        function (response) {
    	        	validateResponse(response);
    	        	var user = _.unserialize(response);
    	        	//global varible used in menu_utils.js
    	        	autoPublishAlloc = user.response.autoPublishAlloc;
    	        	/* ENHANCEMENT 20120831 Rhoda - Redmine 102 */
                    displayAllocQty = user.response.preference.displayAllocQty;
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
        }
    }
});
