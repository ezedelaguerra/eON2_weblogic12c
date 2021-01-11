
Class('App', 'linb.Com',{
    Instance:{
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};

            append((new linb.UI.Pane)
                .host(host,"pane1")
                .setDock("top")
                .setHeight(50)
            );

            host.pane1.append((new linb.UI.Block)
                .host(host,"block1")
                .setDock("top")
                .setDockFloat(true)
                .setHeight(50)
                .setPosition("relative")
            );

            host.block1.append((new linb.UI.Div)
                .host(host,"div1")
                .setLeft(15)
                .setTop(7)
                .setWidth(40)
                .setHeight(31)
                .setHtml("<img src='../../common/img/logo.jpg' width='35' height='35'>")
            );

            host.block1.append((new linb.UI.Link)
                .host(host,"lnkhome")
                .setTop(28)
                .setHeight(16)
                .setRight(80)
                .setCaption("$app.home")
                .onClick("_lnkhome_onclick")
            );

            host.block1.append((new linb.UI.Link)
                .host(host,"lnklogout")
                .setTop(28)
                .setHeight(16)
                .setRight(12)
                .setCaption("$app.logout")
                .onClick("_lnklogout_onclick")
            );

            host.block1.append((new linb.UI.Label)
                .host(host,"lblwelcome")
                .setLeft(60)
                .setTop(20)
                .setWidth(170)
                .setHeight(12)
                .setCaption("$app.welcome")
                .setHAlign("left")
                .setFontSize("12px")
                .setFontWeight("bold")
//                .setVisibility("hidden")
            );
            
            host.block1.append((new linb.UI.Label)
                    .host(host,"lbluser")
                    .setLeft(125)
                    .setTop(20)
                    .setWidth(280)
                    .setHeight(12)
                    .setHAlign("left")
	                .setFontSize("12px")
	                .setFontWeight("bold")
                );
            

            host.block1.append((new linb.UI.Div)
                .host(host,"div1")
                .setTop(20)
                .setWidth(70)
                .setHeight(20)
                .setRight(260)
                .setHtml("<a href=\"http://www.hontoichiba.com/frcscripts/reqapp.dll?APPNAME=frc&PRGNAME=ho_index&ARGUMENTS=-A2\"><img src='../../common/img/logo_honto_toumei_small.gif' width='60' height='20'></a>")
            );

            host.block1.append((new linb.UI.Link)
                .host(host,"lnkaccount")
                .setTop(28)
                .setHeight(16)
                .setRight(128)
                .setCaption("$app.account")
                .onClick("_lnkaccount_onclick")
            );

            host.block1.append((new linb.UI.Link)
                .host(host,"lnkContactUs")
                .setTop(27)
                .setWidth(70)
                .setRight(180)
                .setCaption("$app.contactus")
                .onClick("_lnkContactUs_onclick")
            );

            append((new linb.UI.Pane)
                .host(host,"pane55")
                .setDock("top")
                .setHeight(28)
            );

            host.pane55.append((new linb.UI.Label)
                .host(host,"label17")
                .setTop(5)
                .setWidth(220)
                .setRight(400)
                .setCaption("<font size='3px;'>$app.homepage</font>")
                .setHAlign("center")
                .setFontSize("14")
                .setFontWeight("bold")
            );

            host.pane55.append((new linb.UI.MenuBar)
                .host(host,"menubar2")
                .setItems([{"id":"mnuFile", "caption":"<font color=\"#014e00\" size=\"3px\">$app.file</font>", "sub":[{"id":"fileUser", "caption":"<font color=\"#0000ee\">$app.userpreference</font>", "image":"img/user.gif", "sub":[{"id":"subuprefScreenset", "caption":"<font color=\"#0000ee\">Screen Settings</font>", "image":"../../common/img/demo.gif", "sub":[{"id":"screenSaveset", "caption":"<font color=\"#0000ee\">Save settings</font>", "image":"../../common/img/demo.gif"}, {"type":"split"}, {"id":"screenSaveset", "caption":"<font color=\"#0000ee\">Reset settings</font>", "image":"../../common/img/demo.gif"}]}, {"id":"subuprefPricewtax", "caption":"<font color=\"#0000ee\">Price with Tax column</font>", "image":"../../common/img/demo.gif", "sub":[{"id":"pricewEnable", "caption":"<font color=\"#0000ee\">Enable</font>", "image":"../../common/img/demo.gif"}, {"type":"split"}, {"id":"pricewDisable", "caption":"<font color=\"#0000ee\">Disable</font>", "image":"../../common/img/demo.gif"}]}, {"id":"subuprefPricewotax", "caption":"<font color=\"#0000ee\">Price without Tax column</font>", "image":"../../common/img/demo.gif", "sub":[{"id":"pricewoEnable", "caption":"<font color=\"#0000ee\">Enable</font>", "image":"../../common/img/demo.gif"}, {"type":"split"}, {"id":"pricewoDisable", "caption":"<font color=\"#0000ee\">Disable</font>", "image":"../../common/img/demo.gif"}]}, {"id":"subuprefShowcols", "caption":"<font color=\"#0000ee\">Show/Hide columns</font>", "image":"../../common/img/demo.gif", "sub":[{"id":"showcol1", "caption":"<font color=\"#0000ee\">Column 1</font>", "type":"checkbox"}, {"type":"split"}, {"id":"showcol2", "caption":"<font color=\"#0000ee\">Column 2</font>", "type":"checkbox"}, {"type":"split"}, {"id":"showcol3", "caption":"<font color=\"#0000ee\">Column 3</font>", "type":"checkbox"}, {"type":"split"}, {"id":"showcol4", "caption":"<font color=\"#0000ee\">Column 4</font>", "type":"checkbox"}]}, {"id":"subuprefSellprodcode", "caption":"<font color=\"#0000ee\">Seller Product code</font>", "image":"../../common/img/demo.gif", "sub":[{"id":"prodcode1", "caption":"<font color=\"#0000ee\">Product code 1</font>", "type":"checkbox", "value":true}, {"type":"split"}, {"id":"prodcode2", "caption":"<font color=\"#0000ee\">Product code 2</font>", "type":"checkbox"}, {"type":"split"}, {"id":"prodcode3", "caption":"<font color=\"#0000ee\">Product code 3</font>", "type":"checkbox"}, {"type":"split"}, {"id":"prodcode4", "caption":"<font color=\"#0000ee\">Product code 4</font>", "type":"checkbox"}]}, {"id":"subuprefRoundoff", "caption":"<font color=\"#0000ee\">Round-off</font>", "image":"../../common/img/demo.gif", "sub":[{"id":"roundoff1", "caption":"<font color=\"#0000ee\">0-4 Round off lower, 5-9 Round off higher</font>", "type":"checkbox"}, {"type":"split"}, {"id":"roundoff3", "caption":"<font color=\"#0000ee\">Round off lower always</font>", "type":"checkbox"}, {"type":"split"}, {"id":"roundoff4", "caption":"<font color=\"#0000ee\">Round off higher always</font>", "type":"checkbox"}]}, {"id":"subuprefBMS", "caption":"<font color=\"#0000ee\">BMS</font>", "type":"checkbox"}, {"id":"subuprefRecAlerts", "caption":"<font color=\"#0000ee\">Receive Notification</font>", "image":"../../common/img/demo.gif", "sub":[{"id":"subRecAlertsEnable", "caption":"<font color=\"#0000ee\">Enable</font>"}, {"type":"split"}, {"id":"subRecAlertsDisable", "caption":"<font color=\"#0000ee\">Disable</font>"}]}, {"id":"subuprefUnitOrder", "caption":"<font color=\"#0000ee\">Create Unit Order</font>", "image":"../../common/img/demo.gif"}]}, {"type":"split"}, {"id":"fileChangePassword", "caption":"<font color=\"#0000ee\">$app.changepassword</font>", "image":"../../common/img/key.gif"}, {"type":"split"}, {"id":"fileUserDetails", "caption":"<font color=\"#0000ee\">$app.userdetails</font>", "image":"../../common/img/userdetails.gif"}, {"type":"split"}, {"id":"fileLogoff", "caption":"<font color=\"#0000ee\">$app.logout</font>", "image":"../../common/img/logout.gif"}]}, {"id":"mnuSheets", "caption":"<font color=\"#014e00\" size=\"3px\">$app.sheets</font>", "sub":[{"id":"mnuOrdersheet", "caption":"<font color=\"#0000ee\">$app.ordersheet</font>", "image":"../../common/img/seller_order_sheet.gif"}, {"type":"split"}, {"id":"mnuAllocationsheet", "caption":"<font color=\"#0000ee\">$app.allocationsheet</font>", "image":"../../common/img/seller_allocation_sheet.gif"}, {"type":"split"}, {"id":"mnubillingsheet", "caption":"<font color=\"#0000ee\">$app.billingsheet</font>", "image":"../../common/img/seller_billing_sheet.gif"}, {"type":"split"}, {"id":"mnuUserpreference", "caption":"<font color=\"#0000ee\">$app.userpreference</font>", "image":"../../common/img/user_preferences.gif"}, {"type":"split"}, {"id":"mnuComments", "caption":"<font color=\"#0000ee\">$app.comments</font>", "image":"../../common/img/envelope_close.gif"}, {"type":"split"}, {"id":"mnuAkadensheet", "caption":"<font color=\"#0000ee\">$app.akadensheet</font>", "image":"../../common/img/akaden.gif"}]}, {"id":"mnuUtility", "sub":[{"id":"utilPrint", "caption":"<font color=\"#0000ee\">$app.print</font>", "image":"../../common/img/print.gif"}], "caption":"<font color=\"#014e00\" size=\"3px\">$app.utility</font>"}, {"id":"mnuHelp", "sub":[{"id":"helpHelp", "caption":"<font color=\"#0000ee\">$app.onlinehelp</font>", "image":"../../common/img/help.gif"}, {"type":"split"}, {"id":"helpAbout", "caption":"<font color=\"#0000ee\">$app.abouteon</font>", "image":"../../common/img/demo.gif"}, {"type":"split"}, {"id":"helpThemes", "caption":"<font color=\"#0000ee\">$app.themes</font>", "image":"../../common/img/demo.gif", "sub":[{"id":"themesDefault", "caption":"<font color=\"#0000ee\">Default</font>"}, {"id":"themesAqua", "caption":"<font color=\"#0000ee\">Aqua</font>"}, {"id":"themesVista", "caption":"<font color=\"#0000ee\">Vista</font>"}, {"id":"themesPlain", "caption":"<font color=\"#0000ee\">White</font>"}, {"id":"themesPink", "caption":"<font color=\"#0000ee\">Pink</font>"}, {"id":"themesYellow", "caption":"<font color=\"#0000ee\">Yellow</font>"}, {"id":"themesGold", "caption":"<font color=\"#0000ee\">Gold</font>"}, {"id":"themesGreen", "caption":"<font color=\"#0000ee\">Green</font>"}, {"id":"themesBeige", "caption":"<font color=\"#0000ee\">Beige</font>"}, {"id":"themesCyan", "caption":"<font color=\"#0000ee\">Cyan</font>"}, {"id":"themesSalmon", "caption":"<font color=\"#0000ee\">Salmon</font>"}]}], "caption":"<font color=\"#014e00\" size=\"3px\">$app.help</font>"}])
                .onMenuSelected("_menubar2_onmenuselected")
            );

            host.pane55.append((new linb.UI.Label)
                .host(host,"label187")
                .setTop(5)
                .setWidth(140)
                .setRight(12)
                .setCaption("<b>Tel No. 81-3-5822-1566</b>")
            );

            append((new linb.UI.Pane)
                .host(host,"pane443")
                .setDock("fill")
            );

            host.pane443.append((new linb.UI.Block)
                .host(host,"block196")
                .setDock("fill")
            );

            host.block196.append((new linb.UI.Dialog)
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

            host.block196.append((new linb.UI.Dialog)
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
            );

            host.group86.append((new linb.UI.PageBar)
                .host(host,"pagebar1")
                .setLeft(20)
                .setTop(260)
                .setValue("1:5:10")
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

            host.block196.append((new linb.UI.Pane)
                .host(host,"pane156")
                .setLeft(30)
                .setTop(70)
                .setWidth(220)
                .setHeight(210)
            );

            host.block196.append((new linb.UI.Tabs)
                .host(host,"tabs3")
                .setItems([{"id":"a", "caption":"$app.announcementsection", "image":"../../common/img/announcement.gif"}])
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
                .setDock("fill")
            );

            host.block196.append((new linb.UI.Link)
                .host(host,"link38")
                .setLeft(900)
                .setTop(40)
                .setWidth(100)
                .setCaption("<i><u>$app.archive</u></i>")
//                .onClick("_link38_onclick")
            );

            host.block196.append((new linb.UI.Tabs)
                .host(host,"tabs7")
                .setItems([{"id":"a", "caption":"$app.alertsection", "image":"../../common/img/alert.gif"}])
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
                .setCaption("")
                .setToggleBtn(false)
            , 'a');

            host.groupAlerts.append((new linb.UI.Pane)
                .host(host,"pane129")
                .setLeft(10)
                .setTop(0)
                .setWidth(350)
                .setHeight(110)
            );

            host.groupAlerts.append((new linb.UI.TreeGrid)
                .host(host,"treegrid71")
                .setDock("none")
                .setLeft(10)
                .setTop(10)
                .setWidth(440)
                .setHeight(310)
                .setAnimCollapse(true)
                .setRowHandler(false)
                .setColHidable(true)
                .setHeader([{"id":"coldate", "width":80, "type":"label", "caption":"<b>$app.orderdate</b>"}, {"id":"colsellername", "width":100, "type":"label", "caption":"<b>$app.buyername</b>"}, {"id":"colmessage", "width":250, "type":"label", "caption":"<b>$app.messages</b>"}])
//                .setRows([{"cells":[{"value":"2009/09/15"}, {"value":"Buyer_Frasc 1"}, {"value":"Alert Message 1"}], "id":"a", "cellStyle":"text-align:center;"}, {"cells":[{"value":"2009/09/15"}, {"value":"Buyer_Frasc 2"}, {"value":"Alert Message 2"}], "id":"b", "cellStyle":"text-align:center;"}])
//                .onRowSelected("_treegrid71_onrowselected")
            );

            host.block196.append((new linb.UI.Link)
                .host(host,"linkShortcut")
                .setLeft(880)
                .setTop(450)
                .setWidth(110)
                .setHeight(15)
                .setCaption("<u>$app.createshortcut</u>")
                .onClick("_linkshortcut_onclick")
            );

            host.block196.append((new linb.UI.Link)
                .host(host,"linkAddFavorite")
                .setLeft(750)
                .setTop(450)
                .setWidth(100)
                .setCaption("<u>$app.addtofavorite</u>")
                .onClick("_linkaddfavorite_onclick")
            );

            host.pane443.append((new linb.UI.Block)
                .host(host,"block29")
                .setDock("top")
                .setHeight(43)
            );

            host.block29.append((new linb.UI.Button)
                .host(host,"btnOrderSheet")
                .setLeft(20)
                .setTop(5)
                .setWidth(80)
                .setHeight(28)
                .setShadow(true)
                .setCaption("$app.ordersheet")
                .setImage("../../common/img/seller_order_sheet.gif")
                .onClick("_btnordersheet_onclick")
            );

            host.block29.append((new linb.UI.Button)
                .host(host,"btnAllocationSheet")
                .setLeft(110)
                .setTop(5)
                .setWidth(80)
                .setHeight(28)
                .setShadow(true)
                .setCaption("$app.allocationsheet")
                .setImage("../../common/img/seller_allocation_sheet.gif")
                .onClick("_btnallocationsheet_onclick")
            );

            host.block29.append((new linb.UI.Button)
                .host(host,"btnBillingSheet")
                .setLeft(200)
                .setTop(5)
                .setWidth(80)
                .setHeight(28)
                .setShadow(true)
                .setCaption("$app.billingsheet")
                .setImage("../../common/img/seller_billing_sheet.gif")
                .onClick("_btnbillingsheet_onclick")
            );

            host.block29.append((new linb.UI.Button)
                .host(host,"btnAkadenSheet")
                .setLeft(290)
                .setTop(5)
                .setWidth(80)
                .setHeight(28)
                .setShadow(true)
                .setCaption("$app.akadensheet")
                .setImage("../../common/img/akaden.gif")
                .onClick("_btnakadensheet_onclick")
            );

            host.block29.append((new linb.UI.Button)
                .host(host,"btnComments")
                .setLeft(380)
                .setTop(5)
                .setWidth(90)
                .setHeight(28)
                .setShadow(true)
                .setCaption("<b><font color='red'>Comments(1)</font></b>")
                .onClick("_btncomments_onclick")
            );

            host.block29.append((new linb.UI.Button)
                .host(host,"btnUserPreference")
                .setLeft(480)
                .setTop(5)
                .setWidth(90)
                .setHeight(28)
                .setShadow(true)
                .setCaption("$app.userpreference")
                .setImage("../../common/img/user_preferences.gif")
                .onClick("_btnuserpreference_onclick")
            );

            host.block1.append((new linb.UI.Label)
                .host(host,"lbllastlogindtinfo")
                .setLeft(120)
                .setTop(110)
                .setWidth(150)
                .setTabindex(0)
                .setHAlign("left")
                .setVisibility("hidden")
                .setCaption("")
            );

            host.block1.append((new linb.UI.Label)
                .host(host,"lblpasswordinfo")
                .setLeft(120)
                .setTop(80)
                .setWidth(100)
                .setTabindex(0)
                .setHAlign("left")
                .setVisibility("hidden")
            );

            host.block1.append((new linb.UI.Label)
                .host(host,"lblusernameinfo")
                .setLeft(120)
                .setTop(50)
                .setWidth(100)
                .setTabindex(0)
                .setHAlign("left")
                .setVisibility("hidden")
            );

            host.block1.append((new linb.UI.Label)
                .host(host,"lbluseridinfo")
                .setLeft(120)
                .setTop(20)
                .setWidth(100)
                .setTabindex(0)
                .setHAlign("left")
                .setVisibility("hidden")
            );

            return children;
            // ]]code created by jsLinb UI Builder
        },
        events:{"onReady":"_onready", "onRender":"_onRender"},
        iniResource:function(com, threadid){
        },
        iniExComs:function(com, hreadid){
        },
        _onRender:function(page, threadid){
            SPA=page;
            linb.Ajax('data/data.js',null,this._ajax1_onrequestok).start();
        },
        _onready:function () {
            Class("Component");
            var ns=this;
            SPA=this;
            //this.btnComments.setCaption("<font color=red><blink>$app.comments</blink></font>");
            this.paneTitle.setHtml("<iframe frameborder=0 marginheight=0 marginwidth=0 scrolling=0 width=100% height=100% src='https://meta2.freshremix.com/eon/eon_message_general.html' </iframe>");


            var url = "/eON/login.json";
            var obj = new Object;
            linb.Ajax(url, obj, function(response) {
    	
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
					SPA.lbluser.setCaption(user.userName +" "+ role.roleName);
					SPA.lbluseridinfo.setCaption(user.userName);
					SPA.lblusernameinfo.setCaption(user.name);
					SPA.lblpasswordinfo.setCaption(user.password);
					//alert(user.dateLastLoginStr);
					//alert(user.dateLastLoginStr == null);
					if(user.dateLastLoginStr != null && user.dateLastLoginStr != ""){
						SPA.lbllastlogindtinfo.setCaption(user.dateLastLoginStr);
					}
				}
			}, function(msg, type, id) {
				linb.alert("onFail: " + msg);
			}, null, {
				method : 'POST'
			})
			.start();
        },
//        _ajax1_onrequestok:function (response, rspType, threadId) {
//            var obj = _.unserialize(response);
//            SPA.treegrid71.setHeader(obj.header).setRows(obj.rows);
//        },
        _lnkaccount_onclick:function(profile,e){
            //window.open('https://meta.freshremix.com/seika_info/index.html');

            linb.ComFactory.newCom('App.account', function() {
            	var comaccount = this;
            	comaccount.lbluseridinfo.setCaption(SPA.lbluseridinfo.getCaption());
            	comaccount.lblusernameinfo.setCaption(SPA.lblusernameinfo.getCaption());
            	comaccount.lblpasswordinfo.setCaption(SPA.lblpasswordinfo.getCaption());
            	var loginDt = SPA.lbllastlogindtinfo.getCaption();
            	//alert(loginDt);
            	if(loginDt != null && loginDt != ""){
            		comaccount.lbllastlogindtinfo.setCaption(loginDt);
            	}
				this.show(linb( [ document.body ]));
            });
        },
        _pagebar2_onclick:function(profile,page){
            profile.boxing().setPage(page);
        },
        _treegrid72_afterrowactive:function (profile, row) {
            this.lblFrom.setCaption("<b>From :</b> Administrator");
            this.lblDate.setCaption("<b>Date Created :</b> " + row.cells[0].value);
            this.txtEditorAnnouncement.setValue(row.cells[1].value);

        },
        _lnkhome_onclick:function (profile, e) {
            window.open("index.html","_self");
        },
        _button405_onclick:function (profile, e, src, value) {
            this.dialogAnnoncement.hide();
        },
        _treegrid72_onrowselected:function (profile, row, src) {
            this.dialogAnnoncement.reBoxing().show();
        },
        _treegrid71_onrowselected:function (profile, row, src) {
            window.open("seller_ordersheet.html","_self");
        },
        _treegrid102_onrowselected:function (profile, row, src) {
            this.dialogAnnoncement.reBoxing().topZindex(true).show();
        },
        _treegrid102_afterrowactive:function (profile, row) {
            this.lblFrom.setCaption("<b>From :</b> Administrator");
            this.lblDate.setCaption("<b>Date Created :</b> " + row.cells[0].value);
            this.txtEditorAnnouncement.setValue(row.cells[1].value);

        },
        _link90_onclick:function (profile, e) {
            this.dialogArchive.reBoxing().show();
        },
        _button416_onclick:function (profile, e, src, value) {
            this.dialogArchive.hide();
        },
        _lnkContactUs_onclick: function(profile, e) {
            var host = this;
            linb.ComFactory.newCom('App.contactus', function() {
                this.show(linb( [ document.body ]));
            });
        },
        _link94_onclick:function (profile, e) {
            window.open("seller_Comments.html", "_self");
        },
        _menubar2_onmenuselected:function (profile, popProfile, item, src) {
            if (item.id=="subuprefUnitOrder") {
                var host=this;
                linb.ComFactory.newCom('App.unitorder' ,function(){this.show(linb([document.body]));});
            }
            else if (item.id=="subRecAlertsEnable"){
                var host=this;
                linb.ComFactory.newCom('App.receivedalerts' ,function(){this.show(linb([document.body]));});
            }
            else if (item.id=="fileChangePassword") {
                var host=this;
                linb.ComFactory.newCom('App.changepassword' ,function(){this.show(linb([document.body]));});
            }
            else if (item.id=="fileUserDetails") {
                var host=this;
                linb.ComFactory.newCom('App.userdetails' ,function(){this.show(linb([document.body]));});
            }
            else if (item.id=="mnuOrdersheet") {
                window.open("seller_ordersheet.html","_self","location=0,status=0,scrollbars=0,menubar=0,resizable=0");
            }
            else if (item.id=="mnuAllocationsheet") {
                window.open("seller_allocationsheet.html", "_self");
            }
            else if (item.id=="mnubillingsheet") {
                window.open("seller_billingsheet.html", "_self");
            }
            else if (item.id=="mnuComments") {
                window.open("seller_comments.html", "_self");
            }
            else if (item.id=="mnuAkadensheet") {
                window.open("seller_akaden.html", "_self");
            }
            else if (item.id=="mnuUserpreference") {
                window.open("seller_userpreference.html", "_self");
            }
            else if (item.id=="themesAqua") {
                linb.UI.setTheme('aqua');
            }
            else if  (item.id=="themesVista") {
                linb.UI.setTheme('vista');
            }
            else if  (item.id=="themesPink") {
                linb.UI.setTheme('pink');
            }
            else if  (item.id=="themesYellow") {
                linb.UI.setTheme('yellow');
            }
            else if  (item.id=="themesGold") {
                linb.UI.setTheme('gold');
            }
            else if  (item.id=="themesGreen") {
                linb.UI.setTheme('green');
            }
            else if  (item.id=="themesPlain") {
                linb.UI.setTheme('plain');
            }
            else if  (item.id=="themesBeige") {
                linb.UI.setTheme('beige');
            }
            else if  (item.id=="themesCyan") {
                linb.UI.setTheme('cyan');
            }
            else if  (item.id=="themesSalmon") {
                linb.UI.setTheme('salmon');
            }
            else {
                linb.UI.setTheme('default');
            }
        },
        _btnordersheet_onclick:function (profile, e, src, value) {
            window.open("seller_ordersheet.html", "_self");
        },
        _btnallocationsheet_onclick:function (profile, e, src, value) {
            window.open("seller_allocationsheet.html", "_self");
        },
        _btnbillingsheet_onclick:function (profile, e, src, value) {
            window.open("seller_billingsheet.html", "_self");
        },
        _btnakadensheet_onclick:function (profile, e, src, value) {
            window.open("seller_akaden.html", "_self");
        },
        _btncomments_onclick:function (profile, e, src, value) {
            window.open("seller_comments.html", "_self");
        },
        _btnuserpreference_onclick:function (profile, e, src, value) {
           window.open("seller_userpreference.html","_self");
        },
        _link38_onclick:function (profile, e) {
            this.dialogArchive.reBoxing().show();
        },
        _linkaddfavorite_onclick:function (profile, e) {
            url = 'http://localhost:8080/eON';
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
        _linkshortcut_onclick:function (profile, e) {
        	document.location.href = "http://localhost:8083/sigma-visual-2.2/VisualJS/projects/login/eON.url";
        },
        _lnklogout_onclick:function (profile, e) {
        	window.open("/eON/j_spring_security_logout", "_self");
        }
     }
});
