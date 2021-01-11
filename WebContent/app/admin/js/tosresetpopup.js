
Class('App.tosreset', 'linb.Com',{
    Instance:{
        autoDestroy:true, 

        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
                .host(host,"TOSReset_DIALOG")
                .setDomId("TOSReset_DIALOG")
                .setLeft(400)
                .setTop(200)
                .setWidth(440)
                .setHeight(200)
                .setResizer(false)
                .setCaption("")
                .setMinBtn(false)
                .setMaxBtn(false)
                .setPinBtn(false)
            );
            
            host.TOSReset_DIALOG.append((new linb.UI.Pane)
                .host(host,"pane6")
                .setLeft(0)
                .setTop(0)
                .setWidth(420)
                .setHeight(150)
            );
            
            host.pane6.append((new linb.UI.Label)
                .host(host,"label1")
                .setLeft(10)
                .setTop(10)
                .setWidth(380)
                .setCaption("$app.caption.CONFIRM_TOS_RESET")
                .setHAlign("left")
            );
            
            host.pane6.append((new linb.UI.Label)
                    .host(host,"label29")
                    .setLeft(10)
                    .setTop(40)
                    .setCaption("$app.caption.userId")
                    .setHAlign("left")
                    .setVAlign("middle")
            );
                
            host.pane6.append((new linb.UI.Input)
                    .host(host,"TXT_USERNAME")
                    .setDomId("TXT_USERNAME")
                    .setLeft(200)
                    .setTop(40)
                    .setWidth(210)
                    .setTabindex(1)
            );
            
            
            host.pane6.append((new linb.UI.Label)
                .host(host,"label2")
                .setLeft(10)
                .setTop(70)
                .setWidth(170)
                .setCaption("$app.caption.password")
                .setHAlign("left")
                .setVAlign("middle")
            );
            
            host.pane6.append((new linb.UI.Input)
                .host(host,"TXT_PASS")
                .setDomId("TXT_PASS")
                .setLeft(200)
                .setTop(70)
                .setWidth(210)
                .setType("password")
                .setTabindex(2)
            );
            
            host.pane6.append((new linb.UI.Button)
                .host(host,"BTNSUBMIT")
                .setDomId("BTNSUBMIT")
                .setLeft(70)
                .setTop(110)
                .setCaption("$app.caption.BTN_TOS_SUBMIT")
                .onClick("BTNSUBMIT_onclick")
                .setTabindex(3)
            );
            
            host.pane6.append((new linb.UI.Button)
                .host(host,"button22")
                .setLeft(220)
                .setTop(110)
                .setCaption("$app.caption.cancel")
                .onClick("ClosePopUP")
                .setTabindex(4)
            );
            
           
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events:{}, 
        customAppend:function(parent,subId,left,top){
            return false;
        }, 
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        }, 
        BTNSUBMIT_onclick:function(profile, e, src, value)
        {
        	var ui = this;
        	
        	this.BTNSUBMIT.setDisabled(true);
             var obj = new Object();
             obj.username = this.TXT_USERNAME.getUIValue();
             obj.password = this.TXT_PASS.getUIValue();
             ui.destroy();
             linb.Ajax(
                     "/eON/TOSreset.json",
                     obj,
                     function (response) {
                         validateResponse(response);


                             var infoMessage = retrieveInfoMessage(response);
                             if (infoMessage != ""){
                            	 alert(infoMessage);
                             } else {

                                 var alertMsg  = linb.Locale[linb.getLang()].app.caption['ALERT_TOS_RESET_SUCCESSFUL'];
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
        ClosePopUP:function(profile, e, src, value)
        {
            this.destroy();
        }
    }
});