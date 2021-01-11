/*
 	Copyright Freshremix Asia Software Corporation
	date		name		version	changes
	==============================================
	20120913	lele		chrome	Redmine 880 - Chrome compatibility
 * */
var g_unreadMsg = 0; // global variable for unread messages

Class('App.home', 'linb.Com', {
    Instance: {
        autoDestroy:true, 
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Pane)
                .host(host,"paneBody")
                .setLeft(0)
                .setTop(59)
                .setWidth(1080)
                .setHeight(520)
            );
            
            host.paneBody.append((new linb.UI.Block)
                .host(host,"blockButtons")
                .setDock("top")
                .setHeight(43)
            );
            
            host.blockButtons.append((new linb.UI.Button)
                .host(host,"btnOrderSheet")
                .setLeft(20)
                .setTop(5)
                .setWidth(80)
                .setHeight(28)
                .setShadow(true)
//                .setCaption("<font color=\"#0000ee\">$app.caption.ordersheet</font>")
                .setCaption("$app.caption.ordersheet")
                .setImage("../../common/img/seller_order_sheet.GIF")
                .onClick("_btnordersheet_onclick")
            );
            
            host.blockButtons.append((new linb.UI.Button)
                .host(host,"btnAllocationSheet")
                .setLeft(110)
                .setTop(5)
                .setWidth(85)
                .setHeight(28)
                .setShadow(true)
//                .setCaption("<font color=\"#0000ee\">$app.caption.allocationsheet</font>")
                .setCaption("$app.caption.allocationsheet")
                .setImage("../../common/img/seller_allocation_sheet.GIF")
                .onClick("_btnallocationsheet_onclick")
            );
            
            host.blockButtons.append((new linb.UI.Button)
                .host(host,"btnBillingSheet")
                .setLeft(205)
                .setTop(5)
                .setWidth(80)
                .setHeight(28)
                .setShadow(true)
//                .setCaption("<font color=\"#0000ee\">$app.caption.billingsheet</font>")
                .setCaption("$app.caption.billingsheet")
                .setImage("../../common/img/seller_billing_sheet.GIF")
                .onClick("_btnbillingsheet_onclick")
                .setDisabled(true)
            );
            
            host.blockButtons.append((new linb.UI.Button)
                .host(host,"btnAkadenSheet")
                .setLeft(295)
                .setTop(5)
                .setWidth(80)
                .setHeight(28)
                .setShadow(true)
//                .setCaption("<font color=\"#0000ee\">$app.caption.akadensheet</font>")
                .setCaption("$app.caption.akadensheet")
                .setImage("../../common/img/akaden.gif")
                .onClick("_btnakadensheet_onclick")
                .setDisabled(true)
            );
            
            host.blockButtons.append((new linb.UI.Button)
                .host(host,"btnComments")
                .setLeft(385)
                .setTop(5)
                .setWidth(115)
                .setHeight(28)
                .setShadow(true)
//                .setCaption("<font color=\"#0000ee\">$app.caption.comments</font>")
                .setCaption("$app.caption.comments")
                .setImage("../../common/img/envelope_close.GIF")
                .onClick("_btncomments_onclick")
            );
            
            host.blockButtons.append((new linb.UI.Button)
                .host(host,"btnUserPreference")
                .setLeft(510)
                .setTop(5)
                .setWidth(115)
                .setHeight(28)
                .setShadow(true)
//                .setCaption("<font color=\"#0000ee\">$app.caption.userpreference</font>")
                .setCaption("$app.caption.userpreference")
                .setImage("../../common/img/user_preferences.gif")
                .onClick("_btnuserpreference_onclick")
            );
            
            host.paneBody.append((new linb.UI.Block)
                .host(host,"blockContents")
                .setDock("fill")
            );
            
            host.blockContents.append((new linb.UI.Dialog)
                .host(host,"dialogAnnoncement")
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
                .setPinBtn(false)
            );
            
            host.dialogAnnoncement.append((new linb.UI.Group)
                .host(host,"group73")
                .setLeft(10)
                .setTop(0)
                .setWidth(490)
                .setHeight(250)
                .setCaption("")
            );
            
            host.group73.append((new linb.UI.TextEditor)
                .host(host,"txtEditorAnnouncement")
                .setLeft(20)
                .setTop(55)
                .setWidth(450)
                .setHeight(135)
            );
            
            host.group73.append((new linb.UI.Label)
                .host(host,"lblDate")
                .setLeft(20)
                .setTop(30)
                .setWidth(450)
                .setCaption("")
                .setHAlign("left")
            );
            
            host.group73.append((new linb.UI.Label)
                .host(host,"lblFrom")
                .setLeft(19)
                .setTop(10)
                .setWidth(452)
                .setCaption("")
                .setHAlign("left")
            );
            
            host.group73.append((new linb.UI.Button)
                .host(host,"button405")
                .setLeft(400)
                .setTop(200)
                .setWidth(70)
                .setCaption("Close")
                .onClick("_button405_onclick")
            );
            
            host.blockContents.append((new linb.UI.Dialog)
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
                .setPinBtn(false)
            );
            
            host.dialogArchive.append((new linb.UI.Group)
                .host(host,"group86")
                .setLeft(10)
                .setTop(10)
                .setWidth(630)
                .setHeight(310)
                .setCaption("")
            );
            
            host.group86.append((new linb.UI.Label)
                .host(host,"label515")
                .setLeft(15)
                .setTop(10)
                .setWidth(70)
                .setCaption("<b>Search By :</b>")
                .setHAlign("left")
            );
            
            host.group86.append((new linb.UI.ComboInput)
                .host(host,"comboinput116")
                .setLeft(80)
                .setTop(10)
                .setItems([{"id":"b", "caption":"Date"}, {"id":"c", "caption":"Title"}])
            );
            
            host.group86.append((new linb.UI.Input)
                .host(host,"input335")
                .setLeft(200)
                .setTop(10)
            );
            
            host.group86.append((new linb.UI.Pane)
                .host(host,"pane248")
                .setLeft(20)
                .setTop(40)
                .setWidth(590)
                .setHeight(210)
            );
            
            host.pane248.append((new linb.UI.TreeGrid)
                .host(host,"treegrid102")
                .setRowNumbered(true)
                .setHeader([{"id":"col1", "width":80, "type":"label", "caption":"Date Created"}, {"id":"col2", "width":400, "type":"label", "caption":"Annoucements"}])
                .setRows([{"cells":[{"value":"2009/09/11"}, {"value":"New product announcement 1"}], "id":"g"}, {"cells":[{"value":"2009/09/12"}, {"value":"New product announcement 2"}], "id":"i"}, {"cells":[{"value":"2009/09/13"}, {"value":"New product announcement 3"}], "id":"n"}, {"cells":[{"value":"2009/09/15"}, {"value":"New product announcement 4"}], "id":"o"}, {"cells":[{"value":"2009/09/15"}, {"value":"New product announcement 5"}], "id":"p"}])
                .onRowSelected("_treegrid102_onrowselected")
                .afterRowActive("_treegrid72_afterrowactive")
                .setCustomStyle({"KEY":"cursor:pointer"})
            );
            
            host.group86.append((new linb.UI.Button)
                .host(host,"button416")
                .setLeft(520)
                .setTop(260)
                .setWidth(90)
                .setCaption("Close")
                .onClick("_button416_onclick")
            );
            
            host.group86.append((new linb.UI.Button)
                .host(host,"button423")
                .setLeft(330)
                .setTop(11)
                .setWidth(70)
                .setHeight(20)
                .setCaption("Search")
            );
            
            host.blockContents.append((new linb.UI.Pane)
                .host(host,"pane156")
                .setLeft(30)
                .setTop(70)
                .setWidth(220)
                .setHeight(210)
            );
            
            host.blockContents.append((new linb.UI.Tabs)
                .host(host,"tabs3")
                .setItems([{"id":"a", "caption":"$app.caption.announcementsection", "image":"../../common/img/announcement.gif"}])
                .setDock("none")
                .setLeft(520)
                .setTop(40)
                .setWidth(480)
                .setHeight(390)
                .setRight(20)
                .setValue("a")
            );
            
            host.tabs3.append((new linb.UI.Group)
                .host(host,"groupAnnouncements")
                .setLeft(10)
                .setTop(10)
                .setWidth(460)
                .setHeight(340)
                .setCaption("")
                .setToggleBtn(false)
            , 'a');
            
            host.groupAnnouncements.append((new linb.UI.Pane)
                .host(host,"paneTitle")
                .setWidth(460)
                .setHeight(460)
            );
            
            host.blockContents.append((new linb.UI.Link)
                .host(host,"link38")
                .setLeft(900)
                .setTop(40)
                .setWidth(100)
                .setVisibility("hidden")
                .setCaption("<i><u>$app.caption.archive</u></i>")
            );
            
            host.blockContents.append((new linb.UI.Tabs)
                .host(host,"tabs7")
                .setItems([{"id":"a", "caption":"$app.caption.alertsection", "image":"../../common/img/alert.gif"}])
                .setDock("none")
                .setLeft(20)
                .setTop(40)
                .setWidth(480)
                .setHeight(390)
                .setValue("a")
            );
            
            host.tabs7.append((new linb.UI.Group)
                .host(host,"groupAlerts")
                .setLeft(10)
                .setTop(10)
                .setWidth(460)
                .setHeight(340)
                .setCaption("<b>$app.caption.inbox<b>")
                .setToggleBtn(false)
                .setCustomStyle({"KEY":"cursor:pointer"})
            , 'a');
            
            // FORDELETION START 20120913: Lele - Redmine 880
//            host.groupAlerts.append((new linb.UI.Pane)
//                .host(host,"pane129")
//                .setLeft(10)
//                .setTop(0)
//                .setWidth(350)
//                .setHeight(110)
//            );
			// FORDELETION END 20120913:
            
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
            
            host.blockContents.append((new linb.UI.Link)
                .host(host,"linkShortcut")
                .setLeft(880)
                .setTop(450)
                .setWidth(110)
                .setHeight(15)
                .setCaption("<u>$app.caption.createshortcut</u>")
                .onClick("_linkshortcut_onclick")
                .setCustomStyle({"KEY":"cursor:pointer"})
            );
            
            host.blockContents.append((new linb.UI.Link)
                .host(host,"linkAddFavorite")
                .setLeft(750)
                .setTop(450)
                .setWidth(100)
                .setCaption("<u>$app.caption.addtofavorite</u>")
                .onClick("_linkaddfavorite_onclick")
                .setCustomStyle({"KEY":"cursor:pointer"})
            );
            /*
            host.blockContents.append((new linb.UI.PageBar)
                .host(host,"pagebarinbox")
                .setLeft(30)
                .setTop(440)
                .setWidth(230)
                .onClick("_pagebarinbox_onclick")
            );
            */
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
            sellerHome = page;
            sellerHome.initializeEmailAlerts();
//            sellerHome.getUnreadMessages();
            if(g_clientWidth > sellerHome.paneBody.getWidth()){
            	sellerHome.paneBody.setWidth(g_clientWidth);
            }
            if(g_clientHeight > sellerHome.paneBody.getHeight() + 60){
            	sellerHome.paneBody.setHeight(g_clientHeight - 60);
        	}
            
            // ENHANCEMENT START 20120913: Lele - Redmine 880
            var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
            
            if (is_chrome) {
            	sellerHome.groupAnnouncements.setWidth(450);
            	sellerHome.groupAlerts.setWidth(445);
            	//sellerHome.paneTitle.setDock("fill");
            }
            // ENHANCEMENT END 20120913:
            
        }, 
        _onready: function() {
        	sellerHome = this;
        	//this.paneTitle.setHtml("<iframe frameborder=0 marginheight=0 marginwidth=0 scrolling=0 width=100% height=100% src='https://meta2.freshremix.com/eon/eon_message.html' </iframe>");
        	sellerHome.loadUserAnnouncement();
        	disableSellerPreferenceButtons("0");
        	sellerHome.sellerMenuDisable();
        },customAppend:function(parent,subId,left,top){
            return false;
        },
        initializeEmailAlerts: function () {
        	//sellerHome=this;
            var url = "/eON/comments/homepageDisplay.json";
            linb.Ajax(
                url,
                null,
                function (response) {
                	validateResponse(response);
                    var result = _.unserialize(response);                	
                    sellerHome.inboxGrid.setRows(result.inboxItems);
//                	sellerHome._rows = result.inboxItems; 
//                	alert(sellerHome._rows.length);
//                    sellerHome.setPageCount(5);
                },
                function(msg) { linb.message("失敗： " + msg); },
                null,
                { method : 'GET', retry : 0 }
            ).start();
        }, 
//        getUnreadMessages : function () {
//            var url = "/eON/comments/countUnreadMsg.json";
//            linb.Ajax(
//                url,
//                null,
//                function (response) {
//                    var result = _.unserialize(response);
//                    g_unreadMsg = result.count;
//                    sellerHome.renderUnreadMsg();
//                },
//                function(msg) { linb.message("失敗： " + msg); },
//                null,
//                { method : 'GET', retry : 0 }
//            ).start();
//        },
//        renderUnreadMsg : function () {
//            var cap;
//            if (g_unreadMsg == 0) cap = "<font color=red><b>$app.caption.comments</b></font>";
//            else cap = "<font color=red><b>$app.caption.comments</b>(" + g_unreadMsg + ")</font>";
//            sellerHome.btnComments.setCaption(cap);
//            sellerHome.btnComments.refresh();
//        },
        _linkaddfavorite_onclick:function (profile, e) {
            url = window.parent.location;
            //alert(url);
            title= 'eON';
            if (window.sidebar) {
                window.sidebar.addPanel(title, url,"");
            }
            else if( document.all ){
                window.external.AddFavorite( url, title);
            }
            else {
                return true;
            }
        }, 
        _btnordersheet_onclick: function (profile, e, src, value) {
            sellerMenu.lblTitle.setCaption("<font size='3px;'>$app.caption.ordersheet</font>");
            sellerMenu.lblTitle.refresh();
            sellerMenu.lblSheetCode.setCaption("10001");
            sellerMenu.lblHiddenSheetTypeId.setCaption("10001");
            sellerMenu.lblHiddenSheetTypeId.refresh();
            mainSPA._switchContent(10001);
        }, 
        _btnallocationsheet_onclick: function (profile, e, src, value) {
            sellerMenu.lblTitle.setCaption("<font size='3px;'>$app.caption.allocationsheet</font>");
            sellerMenu.lblTitle.refresh();
            sellerMenu.lblSheetCode.setCaption("10003");
            sellerMenu.lblHiddenSheetTypeId.setCaption("10003");
            sellerMenu.lblHiddenSheetTypeId.refresh();
            mainSPA._switchContent(10003);
        }, 
        _btnbillingsheet_onclick: function (profile, e, src, value) {
            sellerMenu.lblTitle.setCaption("<font size='3px;'>$app.caption.billingsheet</font>");
            sellerMenu.lblTitle.refresh();
            sellerMenu.lblSheetCode.setCaption("10005");
            sellerMenu.lblHiddenSheetTypeId.setCaption("10005");
            sellerMenu.lblHiddenSheetTypeId.refresh();
            mainSPA._switchContent(10005);
        }, 
        _linkshortcut_onclick:function (profile, e) {
            var browserName=navigator.appName;
            if (browserName=="Microsoft Internet Explorer") {
                document.location.href = '../../login/jsp/eonShortcutLink.jsp';
            }
            else {
                document.location = '../../login/jsp/eonShortcutLink.jsp';
                    //"http://localhost:8083/sigma-visual-2.2/VisualJS/projects/eON.url"
                    //"http://localhost:8083/sigma-visual-2.2/VisualJS/projects/login/eON.url"
            }
        }, 
        _button416_onclick:function (profile, e, src, value) {
            this.dialogArchive.hide();
        }, 
        _treegrid72_afterrowactive:function (profile, row) {
            this.lblFrom.setCaption("<b>From :</b> Administrator");
            this.lblDate.setCaption("<b>Date Created :</b> " + row.cells[0].value);
            this.txtEditorAnnouncement.setValue(row.cells[1].value);

        }, 
        _treegrid102_onrowselected:function (profile, row, src) {
            this.dialogAnnoncement.reBoxing().topZindex(true).show();
        }, 
        _button405_onclick:function (profile, e, src, value) {
            this.dialogAnnoncement.hide();
        }, 
        _btnakadensheet_onclick:function(profile, e, src, value) {
            sellerMenu.lblTitle.setCaption("<font size='3px;'>$app.caption.akadensheet</font>");
            sellerMenu.lblTitle.refresh();
            sellerMenu.lblHiddenSheetTypeId.setCaption(10020);
            sellerMenu.lblHiddenSheetTypeId.refresh();
            sellerMenu.lblSheetCode.setCaption("10020");
            mainSPA._switchContent(10020);
        }, 
        _btncomments_onclick: function (profile, e, src, value) {
            sellerMenu.lblTitle.setCaption("<font size='3px;'>$app.caption.comments</font>");
            sellerMenu.lblTitle.refresh();
            sellerMenu.lblHiddenSheetTypeId.setCaption(99999);
            sellerMenu.lblHiddenSheetTypeId.refresh();
            sellerMenu.menubar_seller.updateItem('mnuEdit', {disabled:false});
            sellerMenu.menubar_seller.updateItem('utilDownloadCSV', {disabled:true});
            sellerMenu.menubar_seller.updateItem('utilUploadFile', {disabled:true});
            mainSPA._switchContent(99999);
        }, 
        _btnuserpreference_onclick: function (profile, e, src, value) {
            sellerMenu.lblTitle.setCaption("<font size='3px;'>$app.caption.userpreference</font>");
            sellerMenu.lblTitle.refresh();
            sellerMenu.lblSheetCode.setCaption("99997");
            mainSPA._switchContent(99997);
        }, 
        _inboxGrid_onDblclickRow: function (profile, row, e, src) {
            sellerMenu.lblTitle.setCaption("<font size='3px;'>$app.caption.comments</font>");
            sellerMenu.lblTitle.refresh();
            sellerMenu.lblHiddenSheetTypeId.setCaption(99999);
            sellerMenu.lblHiddenSheetTypeId.refresh();
            sellerMenu.menubar_seller.updateItem('mnuEdit', {disabled:false});
            sellerMenu.menubar_seller.updateItem('utilDownloadCSV', {disabled:true});
            sellerMenu.menubar_seller.updateItem('utilUploadFile', {disabled:true});
            mainSPA._switchContent(99999);
        },
        sellerMenuDisable : function (profile, row, e, src){
            sellerMenu.menubar_seller.updateItem('mnuHome', {disabled:true});
            sellerMenu.menubar_seller.updateItem('mnuOrdersheet', {disabled:false});
            sellerMenu.menubar_seller.updateItem('mnuAllocationsheet', {disabled:false});
            sellerMenu.menubar_seller.updateItem('mnuAkadensheet', {disabled:true});
            sellerMenu.menubar_seller.updateItem('mnuBillingsheet', {disabled:true});
            sellerMenu.menubar_seller.updateItem('mnuComments', {disabled:false});
            sellerMenu.menubar_seller.updateItem('mnuUserpreference', {disabled:false});
            sellerMenu.menubar_seller.updateItem('subuprefUnitOrder', {disabled:true});
            sellerMenu.menubar_seller.updateItem('mnuEdit', {disabled:true});
            sellerMenu.menubar_seller.updateItem('utilUploadFile', {disabled:true});
            sellerMenu.menubar_seller.updateItem('utilPMF', {disabled:true});
            sellerMenu.menubar_seller.updateItem('utilDownloadCSV', {disabled:false});
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
                                 sellerHome.paneTitle.setHtml(obj.announcement);
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
        	sellerHome.setTg(page);
        }, 
        setTg:function(index){
            var rows=sellerHome._rows.slice((index-1)*sellerHome.count,index*sellerHome.count);
            sellerHome.inboxGrid.setRows(rows);
        }, 
        setPageCount:function(count){
        	alert(sellerHome._rows.length);
        	sellerHome.count=count;
            var page=parseInt((sellerHome._rows.length+(sellerHome.count-1))/sellerHome.count);
            sellerHome.pagebarinbox.setValue([1,1,page].join(':')).setPage(1);
            sellerHome.setTg(1);
        }
        */
    }
});
