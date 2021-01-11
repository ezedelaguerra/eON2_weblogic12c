
Class('App.announcements', 'linb.Com',{
    Instance:{
        autoDestroy:true, 


        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Panel)
                .host(host,"panelAnnouncements")
                .setZIndex(1)
                .setCaption("$app.caption.headerAnnouncements")
                .setCustomStyle({"PANEL":"overflow:auto;"})
            );

            host.panelAnnouncements.append((new linb.UI.Group)
                .host(host,"grpAnnouncements")
                .setLeft(20)
                .setTop(20)
                .setWidth(490)
                .setHeight(550)
                .setCaption("$app.caption.panelAnnouncements")
                .setToggleBtn(false)
            );

            host.panelAnnouncements.append((new linb.UI.RichEditor)
                .host(host,"txtAnnouncements")
                .setLeft(40)
                .setTop(50)
                .setWidth(450)
                .setHeight(460)
            );

            host.panelAnnouncements.append((new linb.UI.Button)
                .host(host,"btnAnnouncementSubmit")
                .setDomId("btnAnnouncementSubmit")
                .setLeft(40)
                .setTop(520)
                .setWidth(200)
                .setTabindex(4)
                .setCaption("$app.caption.BTN_TOS_SUBMIT")
                .onClick("btnAnnouncementSubmit_onclick")
            );

            host.panelAnnouncements.append((new linb.UI.Group)
                .host(host,"grpUserAnnouncements")
                .setLeft(520)
                .setTop(20)
                .setWidth(490)
                .setHeight(550)
                .setCaption("$app.caption.userPanelAnnouncements")
                .setToggleBtn(false)
            );

            host.panelAnnouncements.append((new linb.UI.RichEditor)
                .host(host,"txtUserAnnouncements")
                .setLeft(540)
                .setTop(50)
                .setWidth(450)
                .setHeight(460)
            );

            host.panelAnnouncements.append((new linb.UI.Button)
                .host(host,"btnUserAnnouncementSubmit")
                .setDomId("btnUserAnnouncementSubmit")
                .setLeft(540)
                .setTop(520)
                .setWidth(200)
                .setTabindex(4)
                .setCaption("$app.caption.BTN_TOS_SUBMIT")
                .onClick("btnUserAnnouncementSubmit_onclick")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events:{"onReady":"_onready", "onRender":"_onRender"},
        customAppend:function(parent,subId,left,top){
            return false;
        },
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        },
        btnAnnouncementSubmit_onclick:function(profile, e, src, value)
        {

            var obj = new Object();
            obj.announcementStr=this.txtAnnouncements.getUIValue();
            obj.announcementType="generalAnnouncement"
            var ui = this;
            linb.Ajax(
                    "/eON/saveAnnouncement.json",
                    obj,
                    function (response) {
                        validateResponse(response);


                            var infoMessage = retrieveInfoMessage(response);
                            if (infoMessage != ""){
                                alert(infoMessage);
                            } else {
                                obj = _.unserialize(response);
                                var alertMsg  = linb.Locale[linb.getLang()].app.caption['generalAnnouncementSaved'];
                                alert(alertMsg);

                            }



                    },
                    function(msg) {
                        linb.message(msg);
                    },
                    null,
                    {
                        method : 'POST',
                        retry : 0
                    }
                ).start();
        },
        btnUserAnnouncementSubmit_onclick:function(profile, e, src, value)
        {

            var obj = new Object();
            obj.announcementStr=this.txtUserAnnouncements.getUIValue();
            obj.announcementType="userAnnouncement"
            var ui = this;
            linb.Ajax(
                    "/eON/saveAnnouncement.json",
                    obj,
                    function (response) {
                        validateResponse(response);


                            var infoMessage = retrieveInfoMessage(response);
                            if (infoMessage != ""){
                                alert(infoMessage);
                            } else {
                                obj = _.unserialize(response);
                                var alertMsg  = linb.Locale[linb.getLang()].app.caption['userAnnouncementSaved'];
                                alert(alertMsg);

                            }



                    },
                    function(msg) {
                        linb.message(msg);
                    },
                    null,
                    {
                        method : 'POST',
                        retry : 0
                    }
                ).start();
        },
        _onready:function (com,threadid){
            announcementPage = this;
            this.loadGeneralAnnouncement(this);
            this.loadUserAnnouncement(this);
        },
        _onRender:function (com,threadid){
            announcementPage = this;
            this.loadGeneralAnnouncement(this);
            this.loadUserAnnouncement(this);
        },
        loadGeneralAnnouncement: function(ui)
        {
            var obj = new Object();
            obj.announcementType="generalAnnouncement"
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
                                 announcementPage.txtAnnouncements.setValue(obj.announcement);
                                 announcementPage.txtAnnouncements.setUIValue(obj.announcement);
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

        loadUserAnnouncement: function(ui)
        {
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
                                 announcementPage.txtUserAnnouncements.setValue(obj.announcement);
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
    }


});