/*
 	Copyright Freshremix Asia Software Corporation
	date		name		version	changes
	==============================================
	20120913	lele		chrome	Redmine 880 - Chrome compatibility
 * */

var g_unreadMsg = 0; // global variable for unread messages

Class('App.home','linb.Com',{
	Instance : {

   		autoDestroy:true, 
		iniComponents : function() {
			// [[code created by jsLinb UI Builder
			var host = this, children = [], append = function(child) {
				children.push(child.get(0))
			};
	
			append((new linb.UI.Pane)
				.host(host, "paneHome")
				//.setDock("fill")
                .setLeft(0)
                .setTop(59)
                .setWidth(1080)
                .setHeight(520));
	
			host.paneHome.append((new linb.UI.Block)
				.host(host,"blkAnnouncement")
				.setDock("fill"));
	
			host.blkAnnouncement.append((new linb.UI.Dialog)
				.host(host,"dialogAnnouncement")
				.setLeft(250)
				.setTop(60)
				.setWidth(520)
				.setZIndex(2)
				.setVisibility("hidden")
				.setShadow(false)
				.setCaption("Announcements")
				.setMovable(false)
				.setMinBtn(false)
				.setMaxBtn(false)
				.setCloseBtn(false)
				.setPinBtn(false));
	
			host.dialogAnnouncement.append((new linb.UI.Group)
				.host(host, "grpAnnouncement")
				.setLeft(10).setTop(0)
				.setWidth(490)
				.setHeight(250)
				.setCaption(""));
	
			host.grpAnnouncement.append((new linb.UI.TextEditor)
				.host(host,"txtEditorAnnouncement")
				.setLeft(20)
				.setTop(55)
				.setWidth(450)
				.setHeight(135));
	
			host.grpAnnouncement.append((new linb.UI.Label)
				.host(host,"lblDate")
				.setLeft(20)
				.setTop(30)
				.setWidth(450)
				.setCaption("")
				.setHAlign("left"));
	
			host.grpAnnouncement.append((new linb.UI.Label)
				.host(host,"lblFrom")
				.setLeft(19)
				.setTop(10)
				.setWidth(452)
				.setCaption("")
				.setHAlign("left"));
	
			host.grpAnnouncement.append((new linb.UI.Button)
				.host(host,"btncloseannouncement")
				.setLeft(400)
				.setTop(200)
				.setWidth(70)
				.setCaption("Close")
				.onClick("_btncloseannouncement_onclick"));
	
			host.blkAnnouncement.append((new linb.UI.Dialog)
				.host(host,"dialogArchive")
				.setLeft(20)
				.setTop(40)
				.setWidth(660)
				.setHeight(370)
				.setZIndex(2)
				.setVisibility("hidden")
				.setResizer(false)
				.setCaption("Archive")
				.setMovable(false)
				.setMinBtn(false)
				.setMaxBtn(false)
				.setCloseBtn(false)
				.setPinBtn(false));
	
			host.dialogArchive.append((new linb.UI.Group)
				.host(host,"grpArchive")
				.setLeft(10)
				.setTop(10)
				.setWidth(630)
				.setHeight(310)
				.setCaption(""));
	
			host.grpArchive.append((new linb.UI.Label)
				.host(host, "lblSearchBy")
				.setLeft(15)
				.setTop(10)
				.setWidth(70)
				.setCaption("<b>Search By :</b>")
				.setHAlign("left"));
	
			host.grpArchive.append((new linb.UI.ComboInput)
				.host(host,"comboinput1")
				.setLeft(80)
				.setTop(10)
				.setItems(
				[ {
					"id" : "b",
					"caption" : "Date"
				}, {
					"id" : "c",
					"caption" : "Title"
				} ]));
	
			host.grpArchive.append((new linb.UI.Input)
				.host(host,"iptArchive")
				.setLeft(200)
				.setTop(10));
	
			host.grpArchive.append((new linb.UI.Pane)
				.host(host, "paneArchive")
				.setLeft(20)
				.setTop(40)
				.setWidth(590)
				.setHeight(210));
	
			host.paneArchive.append((new linb.UI.TreeGrid)
				.host(host,"treegrid102")
				.setRowNumbered(true)
				.setHeader( [ {
				"id" : "col1",
				"width" : 80,
				"type" : "label",
				"caption" : "Date Created"
			}, {
				"id" : "col2",
				"width" : 400,
				"type" : "label",
				"caption" : "Annoucements"
			} ]).setRows( [ {
				"cells" : [ {
					"value" : "2009/09/11"
				}, {
					"value" : "New product announcement 1"
				} ],
				"id" : "g"
			}, {
				"cells" : [ {
					"value" : "2009/09/12"
				}, {
					"value" : "New product announcement 2"
				} ],
				"id" : "i"
			}, {
				"cells" : [ {
					"value" : "2009/09/13"
				}, {
					"value" : "New product announcement 3"
				} ],
				"id" : "n"
			}, {
				"cells" : [ {
					"value" : "2009/09/15"
				}, {
					"value" : "New product announcement 4"
				} ],
				"id" : "o"
			}, {
				"cells" : [ {
					"value" : "2009/09/15"
				}, {
					"value" : "New product announcement 5"
				} ],
				"id" : "p"
			} ]).onRowSelected("_treegrid102_onrowselected")
					.afterRowActive("_treegrid72_afterrowactive"));
	
			host.grpArchive.append((new linb.UI.PageBar)
				.host(host,"pagebar1")
				.setLeft(20)
				.setTop(260)
				.setValue(
				"1:5:10"));
	
			host.grpArchive.append((new linb.UI.Button)
				.host(host,"btnclosearchive")
				.setLeft(520)
				.setTop(260)
				.setWidth(90)
				.setCaption("Close")
				.onClick("_btnclosearchive_onclick"));
	
			host.grpArchive.append((new linb.UI.Button)
				.host(host,"btnsearch")
				.setLeft(330)
				.setTop(11)
				.setWidth(70)
				.setHeight(20)
				.setCaption("Search"));
	
			host.blkAnnouncement.append((new linb.UI.Pane)
				.host(host,"pane156")
				.setLeft(30)
				.setTop(70)
				.setWidth(220)
				.setHeight(210));
	
			host.blkAnnouncement.append((new linb.UI.Tabs).host(host, "tabs3")
				.setItems( [ {
					"id" : "a",
					"caption" : "$app.caption.announcementsection",
					"image" : "../../common/img/announcement.gif"
				} ])
				.setDock("none")
				.setLeft(520)
				.setTop(40)
				.setWidth(480)
				.setHeight(390)
				.setRight(20)
				.setValue("a"));
	
			host.tabs3.append((new linb.UI.Group)
				.host(host,"groupAnnouncements")
				.setLeft(10)
				.setTop(10)
				.setWidth(460)
				.setHeight(340)
				.setCaption("")
				.setToggleBtn(false), 'a');
	
			host.groupAnnouncements.append((new linb.UI.Pane)
				.host(host, "paneTitle")
				.setWidth(460)
                .setHeight(460));
	
			host.blkAnnouncement.append((new linb.UI.Link)
				.host(host,"lnkarchive")
				.setLeft(900)
				.setTop(40)
				.setWidth(100)
				.setCaption("<i><u>$app.caption.archive</u></i>")
				.setVisibility("hidden")
				);
	
			host.blkAnnouncement.append((new linb.UI.Tabs).host(host, "tabs7")
				.setItems( [ {
					"id" : "a",
					"caption" : "$app.caption.alertsection",
					"image" : "../../common/img/alert.gif"
				} ])
				.setDock("none")
				.setLeft(20)
				.setTop(40)
				.setWidth(480)
				.setHeight(390)
				.setValue("a"));
	
			host.tabs7.append((new linb.UI.Group)
				.host(host,"groupAlerts")
                .setLeft(10)
                .setTop(10)
                .setWidth(460)
                .setHeight(340)
                .setCaption("<b>$app.caption.inbox<b>")
                .setToggleBtn(false)
            , 'a');
	
//			host.groupAlerts.append((new linb.UI.Pane)
//				.host(host,"pane129")
//				.setLeft(10)
//				.setTop(0)
//				.setWidth(350)
//				.setHeight(110));
	
			host.groupAlerts.append((new linb.UI.TreeGrid)
                .host(host,"inboxGrid")
                .setSelMode("single")
                .setAltRowsBg(true)
                .setRowHandler(false)
                .setColResizer(false)
                .setHeader([{"id":"sender", "width":120, "type":"label", "caption":"<b>$app.caption.sender</b>"}, {"id":"subject", "width":210, "type":"label", "caption":"<b>$app.caption.messages</b>"}, {"id":"address", "width":100, "type":"label", "hidden":true, "caption":"<b>$app.caption.address</b>"}, {"id":"date", "width":117, "type":"label", "caption":"<b>$app.caption.datesent</b>"}, {"id":"id", "type":"label", "hidden":true, "width":80, "caption":"id"}, {"id":"message", "type":"label", "hidden":true, "width":80, "caption":"message"}, {"id":"sender_id", "type":"label", "hidden":true, "width":80, "caption":"sender_id"}, {"id":"recipient_id", "type":"label", "hidden":true, "width":80, "caption":"recipient_id"}])
                .onDblclickRow("_inboxGrid_onDblclickRow")
                .setCustomStyle({"KEY":"cursor:pointer"})
            );
	
			host.blkAnnouncement.append((new linb.UI.Link)
				.host(host,"linkShortcut")
				.setLeft(880)
				.setTop(450)
				.setWidth(110)
				.setHeight(15)
				.setCaption("<u>$app.caption.createshortcut</u>")
				.onClick("_linkshortcut_onclick")
				.setCustomStyle({"KEY":"cursor:pointer"})
			);
	
			host.blkAnnouncement.append((new linb.UI.Link)
				.host(host,"linkAddFavorite")
				.setLeft(750)
				.setTop(450)
				.setWidth(100)
				.setCaption("<u>$app.caption.addtofavorite</u>")
				.onClick("_linkaddfavorite_onclick")
				.setCustomStyle({"KEY":"cursor:pointer"})
			);
	
			host.paneHome.append((new linb.UI.Block)
				.host(host,"block29")
				.setDock("top")
				.setHeight(43));
	
			host.block29.append((new linb.UI.Button).host(host,
				"btnOrderSheet")
				.setLeft(20)
				.setTop(5)
				.setWidth(80)
				.setHeight(28)
				.setShadow(true)
				.setCaption("<font color=\"#0000ee\">$app.caption.buyerordersheet</font>")
				.setImage("../../common/img/buyer_order_new.gif")
				.onClick("_btnordersheet_onclick"));
	
			host.block29.append((new linb.UI.Button)
				.host(host,"btnReceivedSheet")
				.setLeft(110)
				.setTop(5)
				.setWidth(85)
				.setHeight(28)
				.setShadow(true)
				.setCaption("<font color=\"#0000ee\">$app.caption.receivedsheet</font>")
				.setImage("../../common/img/buyer_received_new.gif")
				.onClick("_btnreceivedsheet_onclick"));
	
			host.block29.append((new linb.UI.Button)
				.host(host,"btnBillingSheet")
				.setLeft(205)
				.setTop(5)
				.setWidth(90)
				.setHeight(28)
				.setShadow(true)
				.setCaption("<font color=\"#0000ee\">$app.caption.buyerbillingsheet</font>")
				.setImage("../../common/img/buyer_billing_new.gif")
				.onClick("_btnbillingsheet_onclick")
                .setDisabled(true));
	
			host.block29.append((new linb.UI.Button)
				.host(host,"btnComments")
				.setLeft(305)
				.setTop(5)
				.setWidth(115)
				.setHeight(28)
				.setShadow(true)
				.setCaption("")
                .setImage("../../common/img/envelope_close.GIF")
                .setCaption("<font color=\"#0000ee\">$app.caption.comments</font>")
				.onClick("_btncomments_onclick"));
	
			host.block29.append((new linb.UI.Button)
				.host(host,"btnUserPreference")
				.setLeft(430)
				.setTop(5)
				.setWidth(115)
				.setHeight(28)
				.setShadow(true)
				.setCaption("<font color=\"#0000ee\">$app.caption.userpreference</font>")
				.setImage("../../common/img/user_preferences.gif")
				.onClick("_btnuserpreference_onclick"));
			/*
			host.blkAnnouncement.append((new linb.UI.PageBar)
                .host(host,"pagebarinbox")
                .setLeft(30)
                .setTop(440)
                .setWidth(230)
                .onClick("_pagebarinbox_onclick"));
			*/

			return children;
			// ]]code created by jsLinb UI Builder
		},
		events : {
			"onReady" : "_onready",
			"onRender" : "_onRender"
		},
		iniResource : function(com, threadid) {
		},
		iniExComs : function(com, hreadid) {
		},
		_onRender : function(page, threadid) {
			buyerHome = page;
			if(g_clientWidth > buyerHome.paneHome.getWidth()){
				buyerHome.paneHome.setWidth(g_clientWidth);
            }
            if(g_clientHeight > buyerHome.paneHome.getHeight() + 60){
            	buyerHome.paneHome.setHeight(g_clientHeight - 60);
        	}
			buyerHome.initializeEmailAlerts();
			
			// ENHANCEMENT START 20120913: Lele - Redmine 880
			var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
            
            if (is_chrome) {
            	buyerHome.groupAnnouncements.setWidth(450);
            	buyerHome.groupAlerts.setWidth(445);
            	//buyerHome.paneTitle.setDock("fill");
            }
            // ENHANCEMENT END 20120913:
            
		},
		_onready : function() {
			Class("Component");
			var ns = this;
			buyerHome = this;
//			buyerHome.btnComments
//			.setCaption("<font color=red><b>$app.caption.comments</b>(1)</font>");
			//buyerHome.paneTitle
			//		.setHtml("<iframe frameborder=0 marginheight=0 marginwidth=0 scrolling=0 width=100% height=100% src='https://meta2.freshremix.com/eon/eon_message.html' </iframe>");
			buyerHome.loadUserAnnouncement();
			disableBuyerPreferenceButtons("0");
			buyerHome.buyerSheetsMenuDisable();
		},
		initializeEmailAlerts : function () {
        	var url = "/eON/comments/homepageDisplay.json";
        	linb.Ajax(
    	        url,
    	        null,
    	        function (response) {
    	        	var result = _.unserialize(response);
    	        	buyerHome.inboxGrid.setRows(result.inboxItems);
//    	        	buyerHome._rows = result.inboxItems; 
//    	        	buyerHome.setPageCount(10);
    	        }, 
		    	function(msg) { linb.message("失敗： " + msg); }, 
    	        null, 
    	        { method : 'GET', retry : 0 }
    	    ).start();
        },
//		getUnreadMessages : function () {
//        	var url = "/eON/comments/countUnreadMsg.json";
//        	linb.Ajax(
//    	        url,
//    	        null,
//    	        function (response) {
//    	        	var result = _.unserialize(response);
//    	        	g_unreadMsg = result.count;
//    	        	buyerHome.renderUnreadMsg();
//    	        }, 
//		    	function(msg) { linb.message("失敗： " + msg); }, 
//    	        null, 
//    	        { method : 'GET', retry : 0 }
//    	    ).start();
//        },
//        renderUnreadMsg : function () {
//        	var cap;
//        	if (g_unreadMsg == 0) cap = "<font color=red><b>$app.caption.comments</b></font>";
//        	else cap = "<font color=red><b>$app.caption.comments</b>(" + g_unreadMsg + ")</font>";
//        	buyerHome.btnComments.setCaption(cap);
//        	buyerHome.btnComments.refresh();
//        },
		_treegrid72_afterrowactive : function(profile, row) {
			this.lblFrom.setCaption("<b>From :</b> Administrator");
			this.lblDate.setCaption("<b>Date Created :</b> "
					+ row.cells[0].value);
			this.txtEditorAnnouncement.setValue(row.cells[1].value);

		},
		_btncloseannouncement_onclick : function(profile, e, src, value) {
			this.dialogAnnouncement.hide();
		},
		_treegrid72_onrowselected : function(profile, row, src) {
			this.dialogAnnouncement.reBoxing().show();
		},
		_treegrid71_onrowselected : function(profile, row, src) {
			window.open("buyer_order.html", "_self");
		},
		_treegrid102_onrowselected : function(profile, row, src) {
			this.dialogAnnouncement.reBoxing().topZindex(true).show();
		},
		_treegrid102_afterrowactive : function(profile, row) {
			this.lblFrom.setCaption("<b>From :</b> Administrator");
			this.lblDate.setCaption("<b>Date Created :</b> "
					+ row.cells[0].value);
			this.txtEditorAnnouncement.setValue(row.cells[1].value);

		},
		_btnclosearchive_onclick : function(profile, e, src, value) {
			this.dialogArchive.hide();
		},
		_btnordersheet_onclick : function(profile, e, src, value) {
			buyerMenu.lblTitle.setCaption("<font size='3px;'>$app.caption.buyerordersheet</font>");
        	buyerMenu.lblTitle.refresh();
        	buyerMenu.lblSheetCode.setCaption("10000");
        	buyerMenu.lblHiddenSheetTypeId.setCaption("10000");
        	buyerMenu.lblHiddenSheetTypeId.refresh();
        	mainSPA._switchContent(10000);
			//window.open("../html/seller_ordersheet.html", "_self");
		},
		_btnallocationsheet_onclick : function(profile, e, src, value) {
			window.open("seller_allocationsheet.html", "_self");
		},
		_btnbillingsheet_onclick : function(profile, e, src, value) {
			buyerMenu.lblTitle.setCaption("<font size='3px;'>$app.caption.buyerbillingsheet</font>");
        	buyerMenu.lblTitle.refresh();
        	buyerMenu.lblSheetCode.setCaption("10006");
        	buyerMenu.lblHiddenSheetTypeId.setCaption("10006");
        	buyerMenu.lblHiddenSheetTypeId.refresh();
        	mainSPA._switchContent(10006);
//			window.open("buyer_billing.html", "_self");
		},
		_btnakadensheet_onclick : function(profile, e, src, value) {
			window.open("seller_akaden.html", "_self");
		},
		_btncomments_onclick : function(profile, e, src, value) {
			buyerMenu.lblTitle.setCaption("<font size='3px;'>$app.caption.comments</font>");
			buyerMenu.lblTitle.refresh();
        	buyerMenu.lblSheetCode.setCaption("99999");
        	buyerMenu.lblHiddenSheetTypeId.setCaption(99999);
        	buyerMenu.lblHiddenSheetTypeId.refresh();
        	buyerMenu.menubar.updateItem('mnuEdit', {disabled:false});
        	buyerMenu.menubar.updateItem('utilDownloadCSV', {disabled:true});
        	buyerMenu.menubar.updateItem('utilUploadFile', {disabled:true});
        	mainSPA._switchContent(99999);
		},
		_btnuserpreference_onclick : function(profile, e, src, value) {
			buyerMenu.lblTitle.setCaption("<font size='3px;'>$app.caption.userpreference</font>");
        	buyerMenu.lblTitle.refresh();
        	buyerMenu.lblSheetCode.setCaption("99998");
        	mainSPA._switchContent(99998);
		},
		_btnreceivedsheet_onclick : function(profile, e, src, value) {
			buyerMenu.lblTitle.setCaption("<font size='3px;'>$app.caption.receivedsheet</font>");
        	buyerMenu.lblTitle.refresh();
        	buyerMenu.lblSheetCode.setCaption("10004");
        	buyerMenu.lblHiddenSheetTypeId.setCaption("10004");
        	buyerMenu.lblHiddenSheetTypeId.refresh();
        	mainSPA._switchContent(10004);
			//window.open("buyer_received.html", "_self");
		},
		_linkaddfavorite_onclick : function(profile, e) {
			url = window.parent.location;
			//alert(url);
			title= 'eON';
			if (window.sidebar) {
				window.sidebar.addPanel(title, url, "");
			} else if (document.all) {
				window.external.AddFavorite(url, title);
			} else {
				return true;
			}
		},
		_linkshortcut_onclick : function(profile, e) {
			var browserName=navigator.appName;
			if (browserName=="Microsoft Internet Explorer") {
				document.location.href = '../../login/jsp/eonShortcutLink.jsp';
			}
			else {
				document.location = '../../login/jsp/eonShortcutLink.jsp';
			}
		},
        _inboxGrid_onDblclickRow : function (profile, row, e, src) {
			buyerMenu.lblTitle.setCaption("<font size='3px;'>$app.caption.comments</font>");
			buyerMenu.lblTitle.refresh();
			buyerMenu.lblHiddenSheetTypeId.setCaption(99999);
			buyerMenu.lblHiddenSheetTypeId.refresh();
			buyerMenu.menubar.updateItem('mnuEdit', {disabled:false});
			buyerMenu.menubar.updateItem('utilDownloadCSV', {disabled:true});
			buyerMenu.menubar.updateItem('utilUploadFile', {disabled:true});
        	mainSPA._switchContent(99999);
        },
        buyerSheetsMenuDisable : function (profile, row, e, src){
        	buyerMenu.menubar.updateItem('mnuHome', {disabled:true});
        	buyerMenu.menubar.updateItem('mnuOrdersheet', {disabled:false});
            buyerMenu.menubar.updateItem('mnuReceivedsheet', {disabled:false});
            buyerMenu.menubar.updateItem('mnuBillingsheet', {disabled:true});
            buyerMenu.menubar.updateItem('mnuComments', {disabled:false});
            buyerMenu.menubar.updateItem('mnuUserpreference', {disabled:false});
            buyerMenu.menubar.updateItem('mnuEdit', {disabled:true});
            buyerMenu.menubar.updateItem('utilDownloadCSV', {disabled:false});
        },
        loadUserAnnouncement: function(ui){
            var obj = new Object();
            obj.announcementType="userAnnouncement"
             linb.Ajax(
                     "/eON/getAnnouncement.json",
                     obj,
                     function (response) {
                         validateResponse(response);
                             var infoMessage = retrieveInfoMessage(response);
                             if (infoMessage != ""){
                                 alert(infoMessage);
                             } else {
                                 obj = _.unserialize(response);
                                 buyerHome.paneTitle.setHtml(obj.announcement);
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
        }
        /*
        , 
        _pagebarinbox_onclick:function (profile, page) {
        	profile.boxing().setPage(page);
        	buyerHome.setTg(page);
        }, 
        setTg:function(index){
            var rows=buyerHome._rows.slice((index-1)*buyerHome.count,index*buyerHome.count);
            buyerHome.inboxGrid.setRows(rows);
        }, 
        setPageCount:function(count){
        	buyerHome.count=count;
            var page=parseInt((buyerHome._rows.length+(buyerHome.count-1))/buyerHome.count);
            buyerHome.pagebarinbox.setValue([1,1,page].join(':')).setPage(1);
            buyerHome.setTg(1);
        }
        */
	}
});
