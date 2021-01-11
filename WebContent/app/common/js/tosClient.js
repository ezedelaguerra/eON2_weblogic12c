Class('App.tosClient', 'linb.Com',{
    Instance:{
        autoDestroy:true, 

        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Panel)
                .host(host,"panel3")
                .setZIndex(1)
                .setCaption("$app.caption.TOS_TITLE")
                .setCustomStyle({"PANEL":"overflow:auto;"})
            );
            
            host.panel3.append((new linb.UI.Pane)
                .host(host,"pane21")
                .setDock("center")
                .setDockFloat(true)
                .setLeft(130)
                .setTop(510)
                .setWidth(740)
                .setHeight(40)
                .setBottom(10)
                .setPosition("relative")
            );
            
            host.pane21.append((new linb.UI.Button)
                .host(host,"BTN_AGREE")                
                .setDomId("BTN_AGREE")
                .setLeft(390)
                .setTop(20)
                .setWidth(150)
                .setCaption("$app.caption.BTN_TOS_AGREE")
                .onClick("BTN_onclick")
            );
            
            host.pane21.append((new linb.UI.Button)
                .host(host,"BTN_DISAGREE")
                .setDomId("BTN_DISAGREE")
                .setLeft(590)
                .setTop(20)
                .setWidth(150)
                .setCaption("$app.caption.BTN_TOS_DISAGREE")
                .onClick("BTN_onclick")
            );
            
            host.panel3.append((new linb.UI.Div)
                .host(host,"DIVCONTENT")
                .setDomId("DIVCONTENT")
                .setDock("center")
                .setLeft(10)
                .setTop(20)
                .setWidth(750)
                .setHeight(490)
                .setCustomStyle({"KEY":"background-color:#FFF;overflow:auto"})

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
        _onready:function (com,threadid){
          //  this.loadTOS(this);
        }, 
        BTN_onclick:function(profile, e, src, value)
        {
            var agree = '0';
            if(profile.alias!=null && profile.alias=="BTN_AGREE")
            {
                agree = '1';
            }
            this.BTN_AGREE.setDisabled(true);
            this.BTN_DISAGREE.setDisabled(true);
            
            this.agreementSubmit(agree);
        }, 


       agreementSubmit:function(isAgree)
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

                               window.location = "/eON/afterLogin.do?TOS=true";
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