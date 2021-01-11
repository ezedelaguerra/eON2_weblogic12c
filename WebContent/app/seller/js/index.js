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
// should only be changed when selecting in menu bar
var currentContent;
Class('App', 'linb.Com', {
	Instance:{
    	iniComponents:function() {

        	var host=this, children=[], append=function(child){children.push(child.get(0))};
        	
        	/*append((new linb.UI.Pane)
                .host(host,"paneHeader")
                .setDock("top")
                .setHeight(50)
            );
        	
        	append((new linb.UI.Pane)
                .host(host,"paneMenu")
                .setDock("top")
                .setHeight(28)
            );
        	
        	append((new linb.UI.Pane)
                .host(host,"paneBody")
                .setDock("fill")
            );*/
        	append((new linb.UI.Label)
                    .host(host,"lblSheet")
                    .setLeft(190)
                    .setTop(60)
                    .setWidth(100)
                    .setVisibility("hidden")
                );
        	return children;
        	
		},
        events:{"onReady":"_onready", "onDestroy":"_ondestroy", "onRender":"_onRender"},
        iniResource:function(com, threadid) {
        },
        iniExComs:function(com, hreadid) {
        },
        _ondestroy:function (com) {
        	if(currentContent._excom1) currentContent._excom1.destroy();
        },
        _onRender:function(page, threadid) {
            mainSPA = page;
        },
        _onready : function() {
        	linb.UI.setTheme('vista');
        	var ns = this;
        	linb.ComFactory.newCom('App.header', function() {
        		mainSPA._excom1 = this; // use this to reference this component
                //this.show(null, ns.paneHeader);
        		this.show(linb( [ document.body ]));
        		/* DELETE START 20120831 Rhoda - Redmine 102 */
//	        	linb.ComFactory.newCom('App.menubar', function() {
//	        		mainSPA._excom2 = this; // use this to reference this component
//	        		//this.show(null, ns.paneMenu);
//	        		this.show(linb( [ document.body ]));
//	        	});
//	        	linb.ComFactory.newCom('App.home', function() {
//	        		currentContent = this;
//	        		//ns._excom3 = this; // use this to reference this component
//	        		//this.show(null, ns.paneBody);
//	        		this.show(linb( [ document.body ]));
//	        	}); 
				/* DELETE END 20120831 Rhoda - Redmine 102 */
        	});
        }, _switchContent : function (targetContent) {
        	var comName, grpTitleName = "$app.caption.deliveryDate", akadenSheet_filter=0;
        	// destroy component
        	currentContent.destroy();
        	// load target content
        	if (targetContent == 10001) {
        		comName = 'App.orderSheet';
        	} else if (targetContent == 10003) {
        		comName = 'App.allocationSheet';
        	} else if (targetContent == 10020) {
        		comName = 'App.akadensheet';
        		akadenSheet_filter = 1;
        		grpTitleName = "Billing Date";
        	} else if (targetContent == 10005) {
        		comName = 'App.billingSheet';
        	} else if (targetContent == 99999) {
        		comName = 'App.comments';
        	} else if (targetContent == 99997) {
        		comName = 'App.userpreference';
        	} else if (targetContent == 99995) {
        		comName = 'App.home';
        	}
        	
        	mainSPA.lblSheet.setCaption(targetContent);
        	linb.ComFactory.newCom(comName, function() {

        		if(comName == 'App.home'){
                	linb.ComFactory.newCom('App.menubar', function() {
                		mainSPA._excom2 = this;
                		this.show(linb( [ document.body ]));
                	});
        		}
        		
    			currentContent = this;
        		this.show(null, mainSPA.paneBody);
        		if ((comName != 'App.comments' && comName != 'App.userpreference' && comName != 'App.home')) {
	        		linb.ComFactory.newCom(
	        			'App.filter', 
	        			function(){
	        				currentContent._excom1 = this;
	        				filterPage = this;
	        				filterPage.hiddenSheetTypeId.setCaption(targetContent);
	        				filterPage.hiddenDealingPatternId.setCaption(1);
	        				filterPage.hiddenFilterMode.setCaption(akadenSheet_filter);
	        				filterPage.groupDate.setCaption(grpTitleName);	        				
	        				this.setEvents({'onSearch' : function(result) { 
	        					currentContent.onSearchEvent(result);
	        				}});
	        				currentContent.$cached=this;
	        				this.show(null, currentContent.paneSubCom);
	        			}
	            	);
        		}
        		//else {
        			//if (filterPage != null) filterPage.destroy();
        		//}
        	});
        },
        // for csv download: this will clear the session once home link is clicked.
        clearTheSession: function() {
        	var url = "/eON/downloadClearSession.json";
        	linb.Ajax(url,null, function(response){;},function(msg){
                linb.message("失敗： " + msg);
            },null,{method:'POST',retry:0}).start();
        }
	}
});

/*
ns._excom1.destroy();
delete ns._excom1;
*/