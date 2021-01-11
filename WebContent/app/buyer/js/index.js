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
var currentContent;
Class('App','linb.Com',{
	Instance : {
		langKey : 'app',

		iniComponents : function() {
			// [[code created by jsLinb UI Builder
			var host = this, children = [], append = function(child) {
				children.push(child.get(0))
			};
			
			/*//pane for header
			append((new linb.UI.Pane)
				.host(host, "paneHeader")
                .setLeft(0)
                .setTop(0)
                .setWidth(1258)
                .setHeight(51));

			//pane for menu
			append((new linb.UI.Pane)
				.host(host, "paneMenu")
				//.setDock("top")
                .setLeft(0)
                .setTop(52)
                .setWidth(1040)
				.setHeight(29));

			//pane for mainbody
			append((new linb.UI.Pane)
				.host(host, "paneBody")
				//.setDock("fill")
                .setLeft(0)
                .setTop(81)
                .setWidth(1040)
                .setHeight(600));*/

			return children;
			// ]]code created by jsLinb UI Builder
		},
		events : {
			"onReady" : "_onready",
			"onRender" : "_onRender", 
			"onDestroy":"_ondestroy"
		},
		iniResource : function(com, threadid) {
		},
		iniExComs : function(com, hreadid) {
		},
        _ondestroy:function (com) {
        	if(currentContent._excom1) currentContent._excom1.destroy();
        },
		_onRender : function(page, threadid) {
			mainSPA = page;
		},
		_onready : function() {
			linb.UI.setTheme('vista');
			Class("Component");
			var ns = this;
			mainSPA = this;
			linb.ComFactory.newCom('App.header', function() {
        		mainSPA._excom1 = this; // use this to reference this component
				//alert('new Com header');
				var header = this;
				//ns.paneHeader.append(header.paneHeader,false);
				//this.show(null, ns.paneHeader);
				this.show(linb( [ document.body ]));
				/* DELETE START 20120831 Rhoda - Redmine 102 */
//				linb.ComFactory.newCom('App.menubar', function() {
//	        		mainSPA._excom2 = this; // use this to reference this component
//					//alert('new Com menu');
//					var buyerMenu = this;
//					//ns.paneMenu.append(menu.paneMenu,false);
//					//this.show(null, ns.paneMenu);
//					this.show(linb( [ document.body ]));
//				});
//				linb.ComFactory.newCom('App.home', function() {
//					//alert('new Com body');
//					currentContent = this;
//					//ns.paneBody.append(currentContent.paneHome,false);
//					//this.show(null, ns.paneBody);
//					this.show(linb( [ document.body ]));
//				});			
				/* DELETE END 20120831 Rhoda - Redmine 102 */
			});
			
		}, 
		_switchContent : function (targetContent) {
        	var comName;
        	// destroy component
        	currentContent.destroy();
        	// load target content
        	if (targetContent == 10000) {
        		comName = 'App.ordersheet';
//        	} else if (targetContent == 10003) {
//        		comName = 'App.allocationSheet';
        	} else if (targetContent == 10006){
        		comName = 'App.buyerBilling';
        	} else if (targetContent == 10004) {
        		comName = 'App.receivedsheet';
        	} else if (targetContent == 99999) {
        		comName = 'App.comments';
        	} else if (targetContent == 99998) {
        		comName = 'App.userpreference';
        	} else if (targetContent == 99996) {
        		comName = 'App.home';
        	}
        	

    		if(comName == 'App.home'){
            	linb.ComFactory.newCom('App.menubar', function() {
            		mainSPA._excom2 = this;
            		this.show(linb( [ document.body ]));
            	});
    		}
        	linb.ComFactory.newCom(comName, function() {
	    		
    			currentContent = this;
    			this.show(linb( [ document.body ]));
    			
        		if ((comName != 'App.comments' && comName != 'App.userpreference' && comName != 'App.home')) {
	        		linb.ComFactory.newCom(
	        			'App.filter', 
	        			function(){
	        				currentContent._excom1 = this;
	        				filterPage = this;
	        				filterPage.hiddenSheetTypeId.setCaption(targetContent);
	        				filterPage.hiddenDealingPatternId.setCaption(1);
	        				this.setEvents({'onSearch' : function(result) { 
	        					currentContent.onSearchEvent(result);
	        				}});
	        				currentContent.$cached=this;
	        				this.show(null, currentContent.paneSubCom);
	        			}
	            	);
        		}
//        		else {
//        			if (filterPage != null) filterPage.destroy();
//        		}
        	});
		},
		// for download: this will clear the session once home link pressed.
        clearTheSession: function() {
        	var url = "/eON/downloadClearSession.json";
        	linb.Ajax(url,null, function(response){;},function(msg){
                linb.message("失敗： " + msg);
            },null,{method:'POST',retry:0}).start();
        }
	}
});
