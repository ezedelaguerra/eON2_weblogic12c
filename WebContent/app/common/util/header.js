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
// * 20120913		Lele	chrome		Redmine 880 - Chrome compatibility
// --%>
var g_clientWidth;   // global variable for browser's current width
var g_clientHeight;  // global variable for browser's current heigth

Class('App.header', 'linb.Com', {
    Instance: {
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Pane)
                .host(host,"paneHeader")
                .setLeft(0)
                .setTop(0)
                .setWidth(1080)
                .setHeight(30)
            );
            
            host.paneHeader.append((new linb.UI.Block)
                .host(host,"blockHeader")
                .setDock("fill")
                .setDockFloat(true)
                .setPosition("relative")
            );
            
            host.blockHeader.append((new linb.UI.Label)
                .host(host,"lbllastlogindtinfo")
                .setLeft(120)
                .setTop(110)
                .setWidth(150)
                .setTabindex(0)
                .setVisibility("hidden")
                .setCaption("")
                .setHAlign("left")
            );
            
            host.blockHeader.append((new linb.UI.Label)
                .host(host,"lblpasswordinfo")
                .setLeft(120)
                .setTop(80)
                .setWidth(100)
                .setTabindex(0)
                .setVisibility("hidden")
                .setCaption("")
                .setHAlign("left")
            );
            
            host.blockHeader.append((new linb.UI.Label)
                .host(host,"lblusernameinfo")
                .setLeft(120)
                .setTop(50)
                .setWidth(100)
                .setTabindex(0)
                .setVisibility("hidden")
                .setCaption("")
                .setHAlign("left")
            );
            
            host.blockHeader.append((new linb.UI.Label)
                .host(host,"lbluseridinfo")
                .setLeft(120)
                .setTop(20)
                .setWidth(100)
                .setTabindex(0)
                .setVisibility("hidden")
                .setCaption("")
                .setHAlign("left")
            );
            
            host.blockHeader.append((new linb.UI.Label)
                .host(host,"lblCompanyId")
                .setLeft(120)
                .setTop(20)
                .setWidth(100)
                .setTabindex(0)
                .setVisibility("hidden")
                .setCaption("")
                .setHAlign("left")
            );
            
            host.blockHeader.append((new linb.UI.Label)
                .host(host,"lblUserId")
                .setLeft(0)
                .setTop(0)
                .setWidth(100)
                .setTabindex(0)
                .setVisibility("hidden")
                .setCaption("")
                .setHAlign("left")
            );
            
            host.blockHeader.append((new linb.UI.Label)
                .host(host,"lblRole")
                .setLeft(120)
                .setTop(20)
                .setWidth(100)
                .setTabindex(0)
                .setVisibility("hidden")
                .setCaption("")
                .setHAlign("left")
            );
            
            host.blockHeader.append((new linb.UI.Div)
                .host(host,"div1")
                .setLeft(15)
                .setTop(2)
                .setWidth(40)
                .setHeight(20)
                .setHtml("<img src='../../common/img/logo.jpg' width='23px' height='23px'>")
            );
            
            host.blockHeader.append((new linb.UI.Label)
                .host(host,"label1")
                .setLeft(60)
                .setTop(10)
                .setWidth(170)
                .setHeight(12)
//                .setCaption("$app.caption.welcome")
                .setCaption("Welcome")
                .setHAlign("left")
                .setFontSize("12px")
                .setFontWeight("bold")
            );
            
            host.blockHeader.append((new linb.UI.Label)
                .host(host,"lbluser")
                .setLeft(125)
                .setTop(10)
                .setWidth(280)
                .setHeight(12)
                .setCaption("lbluser")
                .setHAlign("left")
                .setFontSize("12px")
                .setFontWeight("bold")
            );
            
            host.blockHeader.append((new linb.UI.Div)
                .host(host,"divRealMarket")
                .setTop(1)
                .setWidth(80)
                .setHeight(10)
                .setRight(320)
                .setHtml("<a href='http://www.hontoichiba.com/' target='_blank'/><img src='../../common/img/logo_honto_toumei_small.gif' style='height: 25px; width: 75px;'></a>")
            );
            
            
           /* host.blockHeader.append((new linb.UI.Image)
                .host(host,"imgToumei")
                .setTop(5)
                .setWidth(60)
                .setHeight(20)
                .setRight(323)
                .setSrc("../../common/img/logo_honto_toumei_small.gif")
                .onClick("_lnkRealMarket_onclick")
            );
            
            host.blockHeader.append((new linb.UI.Link)
                .host(host,"lnkToumei")
                .setTop(5)
                .setWidth(70)
                .setHeight(20)
                .setRight(323)
                .setCaption("")
                .onClick("_lnkRealMarket_onclick")
            );*/
            
//            host.blockHeader.append((new linb.UI.Link)
//                .host(host,"lnkcontactus")
//                .setTop(10)
//                .setWidth(70)
//                .setRight(150)
//                .setCaption("$app.caption.contactus")
//                .setCustomStyle({"KEY":"cursor:pointer"})
//                .onClick("_lnkcontactus_onclick")
//            );
/*            host.blockHeader.append((new linb.UI.Link)
                    .host(host,"lnkTos")
                    .setDomId("lnkTos")
                    .setTop(10)
                    .setHeight(16)
                    .setRight(200)
                    .setCaption("$app.caption.LINK_TOS")
                    .setCustomStyle({"KEY":"cursor:pointer"})
                    .onClick("_lnkTos_onclick")
                );*/
            
            host.blockHeader.append((new linb.UI.Pane)
                    .host(host,"pane7")
                    .setTop(0)
                    .setHeight(16)
                    .setWidth(140)
                    .setRight(160)
                );
                
                host.pane7.append((new linb.UI.Label)
                    .host(host,"tosNewLabel")
                    .setDomId("tosNewLabel")
                    .setDock("left")
                    .setVisibility("hidden")
                    .setTop(15)
                    .setCaption("$app.caption.LABEL_NEW_TOS")
                    .setHAlign("left")
                    .setFontSize("10px")
                    .setCustomStyle({"KEY":"color:red;padding-top:5px;"})
                );
                
                host.pane7.append((new linb.UI.Link)
                    .host(host,"lnkTos")
                    .setDomId("lnkTos")
	                .setTop(10)
	                .setHeight(16)
                    .setLeft(30)
                    .setCustomStyle({"KEY":"cursor:pointer"})
                    .setCaption("$app.caption.LINK_TOS")
                    .onClick("_lnkTos_onclick")
                );
            
            host.blockHeader.append((new linb.UI.Link)
                .host(host,"lnkSeika")
                .setTop(10)
                .setHeight(16)
                .setRight(125)
                .setCaption("$app.caption.seikaLink")
                .setCustomStyle({"KEY":"cursor:pointer"})
                .onClick("_lnkseika_onclick")
            );
            
            host.blockHeader.append((new linb.UI.Link)
                .host(host,"lnkhome")
                .setTop(10)
                .setHeight(16)
                .setRight(80)
                .setCaption("$app.caption.home")
                .setCustomStyle({"KEY":"cursor:pointer"})
                .onClick("_lnkhome_onclick")
            );
            
            host.blockHeader.append((new linb.UI.Link)
                .host(host,"lnkLogout")
                .setTop(10)
                .setHeight(16)
                .setRight(10)
                .setCaption("$app.caption.logout")
                .setCustomStyle({"KEY":"cursor:pointer"})
                .onClick("_lnklogout_onclick")
            );
            
            //start:jr
		    host.blockHeader.append((new linb.UI.Label)
			        .host(host,"hdnUsername")
			        .setLeft(120)
			        .setTop(20)
			        .setWidth(100)
			        .setTabindex(0)
			        .setVisibility("hidden")
			        .setCaption("")
			        .setHAlign("left")
			);
		    
		  //start:jr
		    host.blockHeader.append((new linb.UI.Label)
			        .host(host,"hdnRole")
			        .setLeft(120)
			        .setTop(20)
			        .setWidth(100)
			        .setTabindex(0)
			        .setVisibility("hidden")
			        .setCaption("")
			        .setHAlign("left")
			);
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events: {
            "onReady" : "_onready", "onRender":"_onRender"
        }, 
        iniResource: function(com, threadid) {
        }, 
        iniExComs: function(com, hreadid) {
        }, 
        _onRender:function(page, threadid){
            header=page;
            //initialize the values of global width and height
            g_clientWidth =  document.documentElement.clientWidth ? document.documentElement.clientWidth : document.body.clientWidth;
            g_clientHeight = document.documentElement.clientHeight ? document.documentElement.clientHeight : document.body.clientHeight;
            if(g_clientWidth > header.paneHeader.getWidth()){
            	header.paneHeader.setWidth(g_clientWidth);
            }
            
            // ENHANCEMENT START 20120913: Lele - Redmine 880
            var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
            
            if (is_chrome) {
            	//header.paneHeader.setDock("fill");
            }
            // ENHANCEMENT END 20120913:
            
        }, 
        _onready: function() {
            Class("Component");
            var ns = this;
            header = this;
            //alert("Header");
            var url = "/eON/login.json";
            var obj = new Object;
            linb.Ajax(url,obj,function(response) {

                var obj = _.unserialize(response);
                //alert(response);
                if (obj.fail) {
                    var field = new Object();
                    field = obj.field;
                    linb.alert(field.userName);
                } else if (obj.message != null) {
                    var field = new Object();
                    field = obj.field;
                    linb.alert(obj.message);
                } else {
                    var user = new Object();
                    user = obj.response;
                    var role = new Object();
                    role = user.role;
                    var company = new Object();
                    company = user.company;
                    ns.lbluser.setCaption(user.userName +" "+ role.roleName);
                    ns.lbluseridinfo.setCaption(user.userId);
                    ns.lblusernameinfo.setCaption(user.name);
                    ns.lblpasswordinfo.setCaption(user.password);
                    ns.lbllastlogindtinfo.setCaption(user.dateLastLoginStr);
                    ns.lblCompanyId.setCaption(company.companyId);
                    ns.lblUserId.setCaption(user.userId);
					//start:jr
                    ns.hdnUsername.setCaption(user.userName);
                    ns.hdnRole.setCaption(role.roleName);
                    g_roleType = role.roleName;
                    
                    //global variables
                    unPublishedAllocation = user.viewUnpublisheAlloc;
                    unFinalizedBilling = user.viewUnfinalizeBilling;
                    enablePublish = user.enableBAPublish;
                    autoPublishAlloc = user.autoPublishAlloc;
                    displayAllocQty = user.preference.displayAllocQty;
                	unfinalizeReceived = user.preference.unfinalizeReceived;
                	if(unPublishedAllocation == "true"){
                		userPrefShowUnpublishAlloc.value = unPublishedAllocation;
                	}
            	
	            	if(unFinalizedBilling == "true"){
	            		userPrefShowUnfinalizeBilling.value = unFinalizedBilling;	            		
	            	}
	            	
	            	if(enablePublish == "true"){
	            		userPrefEnableBAPublishOrder.value = enablePublish;	            		
	            	}
	            	
                    //end:jr
                }

                /* ENHANCEMENT START 20120831 Rhoda - Redmine 102 */
                linb.ComFactory.newCom('App.menubar', function() {
            		mainSPA._excom2 = this; // use this to reference this component
            		//this.show(null, ns.paneMenu);
            		this.show(linb( [ document.body ]));
            	});
                linb.ComFactory.newCom('App.home', function() {
            		currentContent = this;
            		//ns._excom3 = this; // use this to reference this component
            		//this.show(null, ns.paneBody);
            		this.show(linb( [ document.body ]));
            	}); 
                /* ENHANCEMENT END 20120831 Rhoda - Redmine 102 */
            }, function(msg, type, id) {
                linb.alert("失敗： " + msg);
            }, null, {
                method : 'POST',
                    retry : 0
            }).start();
            
            this.displayTOSState();
        },
        displayTOSState: function()
        {
        	label = this.tosNewLabel;
             var obj = null;
             linb.Ajax(
                     "/eON/TOSState.json",
                     obj,
                     function (response) {
                         validateResponse(response);


                             var infoMessage = retrieveInfoMessage(response);
                             if (infoMessage != ""){
                                 alert(infoMessage);
                             } else {

                                 obj = _.unserialize(response);

                                 if(obj.isTOSNew=='NEW')
                                	 {
                                	 label.setVisibility("visible");

                                	 }
                             }



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
        _lnkseika_onclick : function (profile, e, src, value) {
        	window.open('https://meta2.freshremix.com/seika_info/index.html', '', 'width=900,height=930,scrollbars=yes');
        },
//        _lnkaccount_onclick:function(profile, e, src, value){   
//        	 this.setProperties({
//                 fromRegion:linb.use(src).cssRegion(true)
//             });
//            linb.ComFactory.newCom('App.account', function() {
//                var comaccount = this;
//                comaccount.lbluseridinfo.setCaption(header.lbluseridinfo.getCaption());
//                comaccount.lblusernameinfo.setCaption(header.hdnUsername.getCaption());//user.name
//                comaccount.lblnameinfo.setCaption(header.lblusernameinfo.getCaption());//user.username
//                comaccount.lblpasswordinfo.setCaption(header.lblpasswordinfo.getCaption());
//                var loginDt = header.lbllastlogindtinfo.getCaption();
//                //alert(loginDt);
//                if(loginDt != null && loginDt != ""){
//                    comaccount.lbllastlogindtinfo.setCaption(loginDt);
//                }
//                this.show(linb( [ document.body ]));
//            });
//            return;
//        }, 
//        _lnkcontactus_onclick: function(profile, e, src, value) {
//        	this.setProperties({
//                fromRegion:linb.use(src).cssRegion(true)
//            });
//            var host = this;
//            linb.ComFactory.newCom('App.contactus', function() {
//                this.show(linb( [ document.body ]));
//            });
//        }, 
        _lnkhome_onclick: function(profile, e) {
        	/** check first if there are unsaved changes from util.js */
        	if(checkUnsavedChanges()){ 
        		return;
        	}
        	mainSPA.clearTheSession();
            window.open("index.jsp", "_self");
        }, 
        _lnklogout_onclick: function(profile, e) {
        	/** check first if there are unsaved changes from util.js */
        	if(checkUnsavedChanges()){ 
        		return;
        	}
            window.open("/eON/j_spring_security_logout", "_self");
        }, 
        _lnkRealMarket_onclick: function(profile, e) {
        	/** check first if there are unsaved changes from util.js */
        	if(checkUnsavedChanges()){ 
        		return;
        	}
            window.open('http://www.hontoichiba.com');
        },
        _lnkTos_onclick: function(profile, e) {
        	/** check first if there are unsaved changes from util.js */
        	if(checkUnsavedChanges()){ 
        		return;
        	}
        	linb.ComFactory.newCom(
        			'App.tosPopUp', 
        			function(){
        				this.tosDialog.showModal();
        			}
            	);
        }
    }
});
