var g_currentPref;
var g_currentId;
var g_innerPref;

Class('App.userpreference', 'linb.Com',{
	autoDestroy:true,
    Instance:{
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Pane)
                .host(host,"userPrefPane")
                .setLeft(0)
                .setTop(59)
                .setWidth(1080)
                .setHeight(520)
            );
            
            host.userPrefPane.append((new linb.UI.Block)
                .host(host,"leftMenuBlock")
                .setDock("left")
                .setWidth(180)
            );
            
            host.leftMenuBlock.append((new linb.UI.Label)
                .host(host,"leftMenuLbl")
                .setLeft(10)
                .setTop(30)
                .setWidth(160)
                .setCaption("<td nowrap><font size='3px'>$app.caption.sortsettings</font></td>")
                .setHAlign("center")
                .setVAlign("middle")
            );

            
            host.leftMenuBlock.append((new linb.UI.Button)
                .host(host,"buyerSortBtn")
                .setLeft(10)
                .setTop(90)
                .setWidth(160)
                .setCaption("<b>$app.caption.sortbuyer</b>")
                .onClick("_buyerSortBtn_onclick")
                .setVisibility("hidden")
            );
                       
            host.leftMenuBlock.append((new linb.UI.Button)
                .host(host,"skuSortBtn")
                .setLeft(10)
                .setTop(90)
                .setWidth(160)
                .setCaption("<b>$app.caption.sortsku</b>")
                .onClick("_skusortbtn_onclick")
            );
            
            host.leftMenuBlock.append((new linb.UI.Button)
                .host(host,"skuGrpSort")
                .setLeft(10)
                .setTop(130)
                .setWidth(160)
                .setCaption("<b>$app.caption.skugroupmaintenance</b>")
                .onClick("_skuGrpMaintenance_onclick")
            );
            
            host.leftMenuBlock.append((new linb.UI.Button)
                .host(host,"hideColumnMaintenance")
                .setLeft(10)
                .setTop(170)
                .setWidth(160)
                .setCaption("<b>$app.caption.hidecolumnmaintenance</b>")
                .onClick("_hideColumnMaintenance_click")
            );
            
            host.leftMenuBlock.append((new linb.UI.Button)
                .host(host,"sellerSortBtn")
                .setLeft(10)
                .setTop(210)
                .setWidth(160)
                .setCaption("<b>$app.caption.sortseller</b>")
                .onClick("_sellerSortBtn_onclick")
            );
            
            host.leftMenuBlock.append((new linb.UI.Button)
                .host(host,"profitPrefBtn")
                .setLeft(10)
                .setTop(250)
                .setWidth(160)
                .setCaption("<b>$app.caption.profitPref</b>")
                .onClick("_profitPreftBtn_onclick")
            );
            
            host.leftMenuBlock.append((new linb.UI.Button)
                    .host(host,"setLockButtonBtn")
                    .setLeft(10)
                    .setTop(290)
                    .setWidth(160)
                    .setCaption("<b>$app.caption.setlockbuttoncaption</b>")
                    .setVisibility("hidden")
                    .onClick("_setLockBtn_onclick")
                );
            
            host.userPrefPane.append((new linb.UI.Pane)
                .host(host,"rightPane")
                .setDock("fill")
            );
            
            host.rightPane.append((new linb.UI.Block)
                .host(host,"rightBlock")
                .setDock("fill")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events:{
        	"onReady" : "_onready", "onRender":"_onrender", "onDestroy":"_ondestroy",
        	"beforeCreated":"_onbeforecreated"
        },
        _ondestroy:function (com) {
        	g_currentId = null;
        	if(userPreference._excom1) userPreference._excom1.destroy();
        },
        _onrender:function(page, threadid){
        	g_currentId = 0;
        	if(g_clientWidth > page.userPrefPane.getWidth()){
        		page.userPrefPane.setWidth(g_clientWidth);
            }
            if(g_clientHeight > page.userPrefPane.getHeight() + 60){
            	page.userPrefPane.setHeight(g_clientHeight - 60);
        	}
        },
        _onready:function() {
        	userPreference = this;
        	buyerMenu.menubar.setItems(getMenuItems());
        	if (g_userRole == "ROLE_BUYER_ADMIN"){
        		userPreference.buyerSortBtn.setVisibility("visible");
        		userPreference.skuSortBtn.setTop(130);
        		userPreference.skuGrpSort.setTop(170);
        	}
        	userPreference.buyerSheetsMenuDisable();
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
        _buyerSortBtn_onclick:function (profile, e, src, value) {
        	userPreference.changeContent(1002, 'App.buyersSort');
        },
        _skusortbtn_onclick:function (profile, e, src, value) {
        	userPreference.changeContent(1000, 'App.skuSort');
        },
        _skuGrpMaintenance_onclick:function (profile, e, src, value) {
        	userPreference.changeContent(1001, 'App.skuGrpMaintenance');
        },
        _hideColumnMaintenance_click : function (profile, e, src, value) {
        	userPreference.changeContent(1003, 'App.hideColumnMaintenance');
        },
        _sellerSortBtn_onclick:function (profile, e, src, value) {
        	userPreference.changeContent(1004, 'App.companySellerSort');
        },
        _profitPreftBtn_onclick:function (profile, e, src, value) {
        	userPreference.changeContent(1005, 'App.profitPreference');
        },
        _setLockBtn_onclick:function (profile, e, src, value) {
        	userPreference.changeContent(1006, 'App.setLockButtonStatusMaintenance');
        },
        changeContent:function(id, name){
        	if (g_currentId != null && g_currentId == id)
        		return; //dont to anything
        	if (g_currentPref != null)
        		g_currentPref.destroy();
        	if (g_innerPref != null)
        		g_innerPref.destroy();
	        linb.ComFactory.newCom(
    			name, 
    			function(){
    				g_currentPref = this;
    				g_currentId = id;
    				userPreference._excom1 = this;
    				if (id == 1002){
    					linb.Ajax(
    		    	        "/eON/initCompanySort.json",
    		    	        null,
    		    	        function (response) {
    		    	        	//alert(response);
    		                    validateResponse(response);
    		                    var obj = new Object();
    		                    var items = new Array();
    		                    obj = _.unserialize(response);
    		                    for(var i=0; i<obj.companySorts.length; i++){
    		                    	var company = obj.companySorts[i].company;
    		                        items.push({"id":company.companyId,
    		                            "caption":company.companyName});
    		                    }
    		                    userPreference._excom1.cbiCompanyname.setItems(items, true);
    		                    userPreference._excom1.cbiCompanyname.setUIValue(items[0].caption);
    		                    userPreference._excom1.cbiCompanyname.setDisabled(true);
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
    				}
    				
    				this.show(null, userPreference.paneSubCom);
    			}
	        );
        },
        buyerSheetsMenuDisable : function (profile, row, e, src){
        	buyerMenu.menubar.updateItem('mnuHome', {disabled:false});
        	buyerMenu.menubar.updateItem('mnuOrdersheet', {disabled:false});
            buyerMenu.menubar.updateItem('mnuReceivedsheet', {disabled:false});
            //buyerMenu.menubar.updateItem('mnuBillingsheet', {disabled:false});
            buyerMenu.menubar.updateItem('mnuComments', {disabled:false});
            buyerMenu.menubar.updateItem('mnuUserpreference', {disabled:true});
        }
    }
});