
Class('App.tosPopUp', 'linb.Com',{
    Instance:{
        autoDestroy:true, 

        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
                .host(host,"tosDialog")
                .setDomId("tosDialog")
                .setLeft(100)
                .setTop(30)
                .setWidth(800)
                .setHeight(540)
                .setResizer(false)
                .setCaption("$app.caption.TOS_TITLE")
                .setMinBtn(false)
                .setMaxBtn(false)
                .setPinBtn(false)
                .setCustomStyle({"PANEL":"overflow:auto;"})
                
            );
            
            host.tosDialog.append((new linb.UI.Pane)
                .host(host,"btnPane")
                .setDock("center")
                .setLeft(10)
                .setTop(440)
                .setWidth(690)
                .setHeight(50)
            );
            
            host.btnPane.append((new linb.UI.Button)
                .host(host,"BTN_AGREE")
                .setDomId("BTN_AGREE")
                .setLeft(540)
                .setTop(10)
                .setWidth(140)
                .setVisibility("hidden")
                .setCaption("$app.caption.BTN_TOS_AGREE")
                .onClick("BTN_onclick")
            );
            
           
            
            host.tosDialog.append((new linb.UI.Div)
                .host(host,"DIVCONTENT")
                .setDomId("DIVCONTENT")
                .setDock("center")
                .setLeft(20)
                .setTop(20)
                .setWidth(760)
                .setHeight(420)
                .setCustomStyle({"KEY":"background-color:#FFF;overflow:auto"})
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 

        events:{"onReady":"_onready"}, 
        customAppend:function(parent,subId,left,top){
            return false;
        }, 
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        }, 
        _onready:function (com,threadid){
             this.loadTOSDisplay(this);
        }, 
        BTN_onclick:function(profile, e, src, value)
        {
            var agree = '0';
            if(profile.alias!=null && profile.alias=="BTN_AGREE")
            {
                agree = '1';
            }
            this.BTN_AGREE.setDisabled(true);
            this.agreementSubmit(this, agree);
        }, 

        loadTOSDisplay: function(ui)
        {
             var obj = null;
             linb.Ajax(
                     "/eON/TOSPopUpDisplay.json",
                     obj,
                     function (response) {
                         validateResponse(response);


                             var infoMessage = retrieveInfoMessage(response);
                             if (infoMessage != ""){
                                 alert(infoMessage);
                             } else {

                                 obj = _.unserialize(response);
                                 ui.setupTOSDisplay(obj.tos, obj.displayBtn);
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

        setupTOSDisplay:function(tos, toDisplay)
        {
            this.DIVCONTENT.setHtml(tos.content);
            if(toDisplay=='true')
            {
                this.BTN_AGREE.setVisibility("visible");
            }
        }, 
        agreementSubmit:function(ui, isAgree)
        {
            var obj = new Object();
            obj.isAgree = isAgree;

            linb.Ajax(
                    "/eON/TOSAgreementResponse.json",
                    obj,
                    function (response) {
                        validateResponse(response);


                            var infoMessage = retrieveInfoMessage(response);
                            if (infoMessage != ""){
                                alert(infoMessage);
                            } else {

                                ui.destroy();
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
     }



});