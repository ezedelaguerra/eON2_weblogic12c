
Class('App.tos', 'linb.Com',{
    Instance:{
        autoDestroy:true, 


        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Panel)
                .host(host,"panelTos")
                .setZIndex(1)
                .setCaption("$app.caption.tosmaintenance")
                .setCustomStyle({"PANEL":"overflow:auto;"})
            );
            
            host.panelTos.append((new linb.UI.Group)
                .host(host,"grpTos")
                .setLeft(20)
                .setTop(20)
                .setWidth(980)
                .setHeight(650)
                .setCaption("$app.caption.LABEL_TOS_GRP")
                .setToggleBtn(false)
            );
            
            host.grpTos.append((new linb.UI.SLabel)
                .host(host,"slabel1")
                .setLeft(70)
                .setTop(600)
                .setWidth(544)
                .setHeight(30)
                .setCaption("$app.caption.NOTE_TOS_EMAIL")
                .setHAlign("left")
            );
            
            host.panelTos.append((new linb.UI.Input)
                .host(host,"txtContent")
                .setDirtyMark(false)
                .setLeft(40)
                .setTop(50)
                .setWidth(940)
                .setHeight(460)
                .setMultiLines(true)
            );
            
            host.panelTos.append((new linb.UI.Label)
                .host(host,"lblTosEmail")
                .setLeft(40)
                .setTop(550)
                .setWidth(80)
                .setCaption("$app.caption.LABEL_TOS_EMAIL")
                .setHAlign("left")
                .setVAlign("middle")
            );
            
            host.panelTos.append((new linb.UI.Group)
                .host(host,"group2")
                .setLeft(20)
                .setTop(720)
                .setWidth(980)
                .setHeight(100)
                .setCaption("$app.caption.LABEL_TOS_RESET_GRP")
                .setToggleBtn(false)
            );
            
            host.group2.append((new linb.UI.Button)
                .host(host,"RESET_BTN")
                .setDomId("RESET_BTN")
                .setLeft(20)
                .setTop(20)
                .setWidth(320)
                .setCaption("$app.caption.LABEL_TOS_RESET_BTN")
                 .onClick("btnTOSReset_onclick")
            );
            
            host.group2.append((new linb.UI.Label)
                .host(host,"label2")
                .setLeft(20)
                .setTop(50)
                .setWidth(510)
                .setCaption("$app.caption.LABEL_TOS_RESET_WARNING")
                .setHAlign("left")
            );
            
            host.panelTos.append((new linb.UI.Input)
                .host(host,"txtTosEmail")
                .setDirtyMark(false)
                .setLeft(80)
                .setTop(550)
                .setWidth(570)
                .setHeight(80)
                .setTabindex(2)
                .setMultiLines(true)
            );
            
            host.panelTos.append((new linb.UI.Button)
                .host(host,"btnTOSSubmit")
                .setDomId("btnTOSSubmit")
                .setLeft(720)
                .setTop(600)
                .setWidth(200)
                .setTabindex(4)
                .setCaption("$app.caption.BTN_TOS_SUBMIT")
                .onClick("btnTOSSubmit_onclick")
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
        btnTOSReset_onclick:function(profile, e, src, value)
        {
        	linb.ComFactory.newCom(
        			'App.tosreset', 
        			function(){

        				this.TOSReset_DIALOG.showModal();
        			}
            	);
        	
        },
        btnTOSSubmit_onclick:function(profile, e, src, value)
        {

            var obj = new Object();
            obj.content=this.txtContent.getUIValue();
            obj.emailList=this.txtTosEmail.getUIValue();
            obj.version = this.version;
            obj.tosId = this.tosId;
            obj.createdBy = this.createdBy;
            var ui = this;
            linb.Ajax(
                    "/eON/TOSsave.json",
                    obj,
                    function (response) {
                        validateResponse(response);


                            var infoMessage = retrieveInfoMessage(response);
                            if (infoMessage != ""){
                                alert(infoMessage);
                            } else {
                                obj = _.unserialize(response);
                                ui.setTermsOfServiceUI( obj.tos);
                                var alertMsg  = linb.Locale[linb.getLang()].app.caption['ALERT_TOS_SAVED'];
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
            this.loadTOS(this);
        }, 

        loadTOS: function(ui)
        {
             var obj = null;
             linb.Ajax(
                     "/eON/TOSload.json",
                     obj,
                     function (response) {
                         validateResponse(response);


                             var infoMessage = retrieveInfoMessage(response);
                             if (infoMessage != ""){
                                 alert(infoMessage);
                             } else {

                                 obj = _.unserialize(response);
                                 ui.setTermsOfServiceUI( obj.tos);
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

        setTermsOfServiceUI: function( obj)
        {
            if(obj!=null)
            {
                this.txtContent.setValue(obj.content, true);
                this.txtTosEmail.setValue(obj.emailList, true);
                this.version = obj.version;
                this.tosId = obj.tosId;
                this.createdBy = obj.createdBy;
            }
        }
    }


});