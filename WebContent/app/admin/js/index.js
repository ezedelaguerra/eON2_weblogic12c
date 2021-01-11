
Class('App', 'linb.Com',{
    Instance:{
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.PopMenu)
                .host(host,"popmnuMatchingFee")
                .setItems([{"id":"popmenuDownload", "caption":"Download Billing", "image":"../../common/img/demo.gif"}, {"type":"split"}, {"id":"popmenuBilling", "caption":"Billing Master", "image":"../../common/img/demo.gif"}, {"type":"split"}, {"id":"popmenuTariff", "caption":"Tariff Master", "image":"../../common/img/demo.gif"}])
            );
            
            append((new linb.UI.Pane)
                .host(host,"paneHeader")
                .setDock("top")
                .setHeight(51)
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
                .host(host,"lblRole")
                .setLeft(120)
                .setTop(20)
                .setWidth(100)
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
            
            host.blockHeader.append((new linb.UI.Div)
                .host(host,"div1")
                .setLeft(15)
                .setTop(7)
                .setWidth(40)
                .setHeight(31)
                .setHtml("<img src='../../common/img/logo.jpg' width='35' height='35'>")
            );
            
            host.blockHeader.append((new linb.UI.Label)
                .host(host,"label1")
                .setLeft(60)
                .setTop(28)
                .setWidth(170)
                .setHeight(12)
                .setCaption("Welcome")
                .setHAlign("left")
                .setFontSize("12px")
                .setFontWeight("bold")
            );
            
            host.blockHeader.append((new linb.UI.Label)
                .host(host,"lbluser")
                .setLeft(125)
                .setTop(28)
                .setWidth(280)
                .setHeight(12)
                .setCaption("lbluser")
                .setHAlign("left")
                .setFontSize("12px")
                .setFontWeight("bold")
            );
            
            host.blockHeader.append((new linb.UI.Div)
                .host(host,"divRealMarket")
                .setTop(10)
                .setWidth(70)
                .setHeight(20)
                .setRight(270)
                .setHtml("<a href='http://www.hontoichiba.com/' target='_blank'/><img src='../../common/img/logo_honto_toumei_small.gif' style='height: 30px; width: 80px;'></a>")
            );
            
            host.blockHeader.append((new linb.UI.Link)
                .host(host,"lnkSeika")
                .setTop(25)
                .setHeight(16)
                .setRight(125)
                .setCaption("$app.caption.seikaLink")
                .onClick("_lnkseika_onclick")
                .setCustomStyle({"KEY":"cursor:pointer"})
            );
            
            host.blockHeader.append((new linb.UI.Link)
                .host(host,"lnkhome")
                .setTop(25)
                .setHeight(16)
                .setRight(80)
                .setCaption("$app.caption.home")
                .onClick("_lnkhome_onclick")
                .setCustomStyle({"KEY":"cursor:pointer"})
            );
            
            host.blockHeader.append((new linb.UI.Link)
                .host(host,"lnkLogout")
                .setTop(25)
                .setHeight(16)
                .setRight(10)
                .setCaption("$app.caption.logout")
                .onClick("_lnklogout_onclick")
                .setCustomStyle({"KEY":"cursor:pointer"})
            );
            
            append((new linb.UI.Pane)
                .host(host,"paneMenuBar")
                .setDock("top")
                .setHeight(28)
            );
            
            host.paneMenuBar.append((new linb.UI.MenuBar)
                .host(host,"menubar9")
                .setItems([{"id":"mnuFile", "sub":[{"id":"fileHistoryLogFile", "image":"../../common/img/history.gif", "caption":"$app.caption.historylogfile", "disabled":true}, {"type":"split"}, {"id":"fileChangePassword", "image":"../../common/img/key.gif", "caption":"$app.caption.changepassword"}], "caption":"$app.caption.file"}, {"id":"mnuUtility", "sub":[{"id":"utilPrint", "image":"../../common/img/Print.gif", "caption":"$app.caption.print", "disabled":true}], "caption":"$app.caption.utility"}])
                .onMenuSelected("_menubar9_onmenuselected")
            );
            
            append((new linb.UI.Layout)
                .host(host,"layoutMain")
                .setItems([{"id":"before", "pos":"before", "locked":true, "size":160, "min":50, "max":400, "hide":false, "cmd":true, "folded":false, "hidden":false}, {"id":"main", "min":10}])
                .setType("horizontal")
                .setCustomStyle({"KEY":"border:0;"})
            );
            
            host.layoutMain.append((new linb.UI.Block)
                .host(host,"blockLeftMenubutton")
                .setDock("fill")
            , 'before');
            
            host.blockLeftMenubutton.append((new linb.UI.Div)
                .host(host,"div284")
                .setLeft(15)
                .setTop(20)
                .setWidth(125)
                .setHeight(20)
                .setHtml("<center><b>$app.caption.maintenance</b></center>")
            );
            
            host.blockLeftMenubutton.append((new linb.UI.Button)
                .host(host,"btnRolesMenu")
                .setDisabled(true)
                .setLeft(10)
                .setTop(60)
                .setWidth(130)
                .setShadow(true)
                .setCaption("$app.caption.menubtnroles")
                .onClick("_btnrolesmenu_onclick")
            );
            
            host.blockLeftMenubutton.append((new linb.UI.Button)
                .host(host,"btnCompanyMenu")
                .setLeft(10)
                .setTop(100)
                .setWidth(130)
                .setShadow(true)
                .setCaption("$app.caption.menubtncompany")
                .onClick("_btncompanymenu_onclick")
            );
            
            host.blockLeftMenubutton.append((new linb.UI.Button)
                .host(host,"btnAnnouncementMenu")
                .setDisabled(false)
                .setLeft(10)
                .setTop(180)
                .setWidth(130)
                .setShadow(true)
                .setCaption("$app.caption.announcements")
                .onClick("_btnannouncementmenu_onclick")
            );
            
            host.blockLeftMenubutton.append((new linb.UI.Button)
                .host(host,"btnTOS")
                .setLeft(10)
                .setTop(220)
                .setWidth(130)
                .setShadow(true)
                .setCaption("$app.caption.menubtntos")
                .onClick("_btntos_onclick")
            );
            
            host.blockLeftMenubutton.append((new linb.UI.Button)
                .host(host,"btnMatchingFeeMenu")
                .setDisabled(true)
                .setLeft(10)
                .setTop(140)
                .setWidth(130)
                .setShadow(true)
                .setCaption("$app.caption.menubtnmatchingfee")
                .onClick("_btnmatchingfeemenu_onclick")
            );
            
            host.blockLeftMenubutton.append((new linb.UI.Label)
                .host(host,"lblUserId")
                .setLeft(80)
                .setTop(0)
                .setVisibility("hidden")
                .setCaption("")
            );
            
            host.blockLeftMenubutton.append((new linb.UI.Button)
                .host(host,"proxyBtn")
                .setDomId("proxyBtn")
                .setLeft(10)
                .setTop(260)
                .setWidth(130)
                .setCaption("$app.caption.menubtnproxy")
                .onClick("_btnproxy_onclick")
            );
            
            host.layoutMain.append((new linb.UI.Pane)
                .host(host,"paneMainDisplay")
                .setDock("fill")
            , 'main');
            
            
            host.blockLeftMenubutton.append((new linb.UI.Button)
                    .host(host,"activityLogsBtn")
                    .setDomId("activityLogsBtn")
                    .setLeft(10)
                    .setTop(300)
                    .setWidth(130)
                    .setCaption("$app.caption.operationLogLabel")
                    .onClick("_btnactivitylogs_onclick")
                );
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events:{"onReady":"_onready","onRender":"_onrender"}, 
        _onRender:function(page, threadid){
            this._getLoggedUser();
            _SPA = page;
        }, 
        _onready: function() {
            linb.UI.setTheme('vista');
            _SPA = this;
            this._getLoggedUser();
            this.menubar9.refresh();
        }, 
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        }, 
        _btncompanymenu_onclick:function (profile, e, src, value) {
            var host=this;
            linb.ComFactory.getCom('App.company',function(){
                var ns=this;
                host.paneMainDisplay.append(ns.panelCompany,false);
            });
        }, 
        _btnannouncementmenu_onclick:function (profile, e, src, value) {
            var host=this;
            linb.ComFactory.getCom('App.announcements',function(){
                var ns=this;
                host.paneMainDisplay.append(ns.panelAnnouncements,false);
                ns._onRender();
                ns.txtAnnouncements.refresh();
                ns.txtUserAnnouncements.refresh();
            });
        },
        /******************************************************************************/
        /**** Terms of Service Button onClick
        /******************************************************************************/

        _btntos_onclick:function (profile, e, src, value) {
            var host=this;
            linb.ComFactory.getCom('App.tos',function(){
                var ns=this;
                host.paneMainDisplay.append(ns.panelTos,false);

            });
        }, 
        
        _btnproxy_onclick:function (profile, e, src, value) {
            var host=this;
            linb.ComFactory.getCom('App.proxyLogin',function(){
                var ns=this;
                host.paneMainDisplay.append(ns.panelTos,false);

            });
        }, 

//        _btncategory_onclick:function (profile, e, src, value) {
//            var host=this;
//            linb.ComFactory.getCom('App.category',function(){
//            var ns=this;
//            host.paneMainDisplay.append(ns.pnlCategory,false);
//            });
//        },
        _btnmatchingfeemenu_onclick:function (profile, e, src, value) {
             this.popmnuMatchingFee.pop(profile.getRoot());
             this.popmnuMatchingFee.$target = profile;
        }, 
        _btnactivitylogs_onclick:function (profile, e, src, value) {
            var host=this;
            linb.ComFactory.getCom('App.activitylogs',function(){
                var ns=this;
                host.paneMainDisplay.append(ns.panelCompany,false);
            });
        }, 
//        _lnkaccount_onclick: function(profile, e, src) {
//            this.setProperties({
//                fromRegion:linb.use(src).cssRegion(true)
//            });
//            linb.ComFactory.newCom('App.account', function() {
//                var comaccount = this;
//                comaccount.lbluseridinfo.setCaption(_SPA.lbluseridinfo.getCaption());
//                comaccount.lblusernameinfo.setCaption(_SPA.lbluseridinfo.getCaption());//user.username
//                comaccount.lblnameinfo.setCaption(_SPA.lblusernameinfo.getCaption());//user.name
//                comaccount.lblpasswordinfo.setCaption(_SPA.lblpasswordinfo.getCaption());
//                var loginDt = _SPA.lbllastlogindtinfo.getCaption();
//                //alert(loginDt);
//                if(loginDt != null && loginDt != ""){
//                    comaccount.lbllastlogindtinfo.setCaption(loginDt);
//                }
//                this.show(linb( [ document.body ]));
//            });
//        },
        _lnkseika_onclick: function (profile, e, src, value) {
            window.open('https://meta2.freshremix.com/seika_info/index.html', '', 'width=900,height=930,scrollbars=yes');
        }, 
        _lnkhome_onclick:function (profile, e) {
            //window.open("index.jsp", "_self");
            window.location.reload(true);
        }, 
//        _lnkcontactus_onclick:function (profile, e, src) {
//            this.setProperties({
//                fromRegion:linb.use(src).cssRegion(true)
//            });
//            var host = this;
//            linb.ComFactory.newCom('App.contactus', function() {
//                this.show(linb( [ document.body ]));
//            });
//        },
        _lnklogout_onclick:function (profile, e) {
            window.open("/eON/j_spring_security_logout", "_self");
        }, 
        _lnkRealMarket_onclick: function(profile, e) {
            window.open('http://www.hontoichiba.com');
        }, 
        _menubar9_onmenuselected:function (profile, popProfile, item, src) {
            this.setProperties({
                fromRegion:linb.use(src).cssRegion(true)
            });
            switch(item.id) {
                case "fileChangePassword" :
                    var host=this;
                    linb.ComFactory.newCom('App.changepassword' ,function(){
                        var ns = this;
                        ns.lblUserId.setCaption(_SPA.lblUserId.getCaption());
                        ns.iptUsername.setValue(_SPA.lbluseridinfo.getCaption());
                        ns.show(linb([document.body]));
                    });
                    break;

                case "fileLogoff"  :
                    window.open("/eON/j_spring_security_logout", "_self");
                    break;
                case "helpAbout" :
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
                    break;
            }
        }, 
        _getLoggedUser:function() {
            var url = "/eON/login.json";
            var obj = new Object;
            linb.Ajax(url, obj, function(response) {
                validateResponse(response);
                var obj = _.unserialize(response);
                /*
                console.log(obj);
                console.log(obj.response.userId);
                console.log(obj.response.userName);
                console.log(obj.response.role.roleName);
                console.log(obj.response.name);
                console.log(obj.response.password);
                console.log(obj.response.dateLastLoginStr);
                */
                if (obj.fail) {
                    var field = new Object();
                    field = obj.field;
                    linb.alert(field.userName);
                } else if (obj.message != null) {
                    var field = new Object();
                    field = obj.field;
                    linb.alert(obj.message);
                } else {
                    _SPA.lbluser.setCaption(obj.response.userName +" "+ obj.response.role.roleName);
                    _SPA.lbluseridinfo.setCaption(obj.response.userName);
                    _SPA.lblusernameinfo.setCaption(obj.response.name);
                    _SPA.lblpasswordinfo.setCaption(obj.response.password);
                    _SPA.lbllastlogindtinfo.setCaption(obj.response.dateLastLoginStr);
                    _SPA.lbluser.setCaption(obj.response.userName + " " + obj.response.role.roleName);
                    _SPA.lblUserId.setCaption(obj.response.userId);
                }
            }, function(msg, type, id) {
                linb.alert("失敗： " + msg);
            }, null, {
                method : 'POST'
            }).start();
        }
     }
});