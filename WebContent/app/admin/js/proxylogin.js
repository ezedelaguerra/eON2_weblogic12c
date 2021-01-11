
Class('App.proxyLogin', 'linb.Com',{
    Instance:{
        autoDestroy:true, 


        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Panel)
                .host(host,"panelTos")
                .setZIndex(1)
                .setCaption("$app.caption.PROXYLGN_TITLE")
                .setCustomStyle({"PANEL":"overflow:auto;"})
            );
            
            host.panelTos.append((new linb.UI.Group)
                .host(host,"group2")
                .setLeft(30)
                .setTop(30)
                .setWidth(560)
                .setHeight(70)
                .setCaption("")
                .setToggleBtn(false)
            );
            
            host.group2.append((new linb.UI.Label)
                .host(host,"label2")
                .setLeft(10)
                .setTop(10)
                .setCaption("$app.caption.userId")
                .setHAlign("left")
                .setVAlign("middle")
            );
            
            host.group2.append((new linb.UI.Input)
                .host(host,"TXT_USERNAME")
                .setDomId("TXT_USERNAME")
                .setLeft(180)
                .setTop(10)
                .setWidth(200)
            );
            
            host.group2.append((new linb.UI.Button)
                .host(host,"btnLogin")
                .setDomId("btnLogin")
                .setLeft(400)
                .setTop(10)
                .setCaption("$app.caption.LOGIN_BTN")
                .onClick("btnLoginSubmit_onclick")
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
        btnLoginSubmit_onclick:function(profile, e, src, value)
        {

            var obj = new Object();
            obj.username=this.TXT_USERNAME.getUIValue();
           
            var ui = this;
            linb.Ajax(
                    "/eON/ProxyLogin.json",
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
                        linb.message(msg);
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