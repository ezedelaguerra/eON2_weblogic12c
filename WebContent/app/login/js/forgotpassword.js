
Class('App.forgotpassword', 'linb.Com',{
    Instance:{
        autoDestroy:true, 

        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Block)
                .host(host,"block1")
                .setLeft(20)
                .setTop(10)
                .setWidth(450)
                .setHeight(230)
                .setTabindex(0)
                //.setCaption("Forgot Password")
            );
            
            host.block1.append((new linb.UI.Label)
                .host(host,"lblUsername")
                .setLeft(30)
                .setTop(80)
                .setWidth(80)
                .setTabindex(0)
                .setCaption("$app.caption.userId")
            );
            
            host.block1.append((new linb.UI.Label)
                .host(host,"lblEmail")
                .setLeft(10)
                .setTop(113)
                .setWidth(100)
                .setTabindex(0)
                .setCaption("$app.caption.emailaddress")
            );
            
            host.block1.append((new linb.UI.Label)
                .host(host,"lblNote")
                .setLeft(30)
                .setTop(10)
                .setWidth(370)
                .setHeight(50)
                .setTabindex(0)
                .setCaption("$app.caption.forgotPasswordNote")
            );
            
            host.block1.append((new linb.UI.Div)
                .host(host,"divErrUser")
                .setLeft(340)
                .setTop(70)
                .setWidth(100)
                .setHeight(20)
                .setTabindex(0)
            );
            
            host.block1.append((new linb.UI.Div)
                .host(host,"divErrEmail")
                .setLeft(340)
                .setTop(110)
                .setWidth(100)
                .setHeight(20)
                .setZIndex(0)
                .setTabindex(0)
            );
            
            host.block1.append((new linb.UI.Input)
                .host(host,"iptUsername")
                .setLeft(110)
                .setTop(79)
                .setWidth(230)
                .setTipsErr("$app.caption.invalidUserName")
                .setValueFormat("^[^ ]+$")
                .setTipsBinder("divErrUser")
                .setMaxlength(32)
                .setDirtyMark(false)
            );
            
            host.block1.append((new linb.UI.Div)
                .host(host,"divMessage")
                .setLeft(60)
                .setTop(180)
                .setWidth(320)
                .setHeight(20)
                .setVisibility("hidden")
                .setHtml("<font>$app.caption.sendingEmail</font>")
            );
            
            host.block1.append((new linb.UI.Input)
                .host(host,"iptEmail")
                .setLeft(110)
                .setTop(110)
                .setWidth(230)
                .setTabindex(2)
                .setTipsErr("$app.caption.invalidFormat")
                .setValueFormat("^[\\w\\.=-]+@[\\w\\.-]+\\.[\\w\\.-]{2,4}$")
                .setTipsBinder("divErrEmail")
                .setMaxlength(50)
                .setDirtyMark(false)
            );
            
            host.block1.append((new linb.UI.Button)
                .host(host,"btnSend")
                .setLeft(100)
                .setTop(150)
                .setWidth(100)
                .setHeight(20)
                .setZIndex(1002)
                .setTabindex(3)
                .setCaption("$app.caption.send")
                .onClick("_button5_onclick")
            );
            
            host.block1.append((new linb.UI.Button)
                .host(host,"btnCancel")
                .setLeft(230)
                .setTop(150)
                .setWidth(100)
                .setHeight(20)
                .setTabindex(4)
                .setCaption("$app.caption.cancel")
                .onClick("_btncancel_onclick")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events:{"onReady" : "_onready", "onRender":"_onRender"},
        _onRender:function(page, threadid){
        	SPA = page;
        },
        _onready: function() {
            SPA = this;
        }, 
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        },
        _button5_onclick:function (profile, e, src, value) {
            SPA.divMessage.setVisibility("visible");
            //SPA.divMessage.setHtml("<font>$app.caption.sendingEmail</font>");

            var url = "/eON/forgotPassword.json";
            var obj = new Object();
            obj.userName=SPA.iptUsername.getUIValue();
            errUserName=linb.Locale['ja'].app.caption['requiredUsername'];
            errEmail=linb.Locale['ja'].app.caption['requiredEmail'];
            if(obj.userName==""){
            	//alert(errUserName);
            	SPA.divMessage.setHtml(errUserName);
            	return;
            }
            obj.pcEmail=SPA.iptEmail.getUIValue();
            if(obj.pcEmail==""){
            	//alert(errEmail);
            	SPA.divMessage.setHtml(errEmail);
            	return;
            }
            sending = linb.Locale['ja'].app.caption['sendingEmail'];
            SPA.divMessage.setHtml(sending);
             linb.Ajax(url,obj,
             function(response){
               var obj = _.unserialize(response);
//               alert(response);
               if (obj.fail) {
                   var field = new Object();
                   field = obj.field;
//                   alert(field.userName);
                   SPA.divMessage.setHtml(field.userName);
               }
               else if (obj.message != null) {
                   var field = new Object();
                   field = obj.field;
                   SPA.divMessage.setHtml(obj.message);
               }
               else {
                   SPA.divMessage.setHtml(obj.response);
//                   window.close();
               }
             },
             function(msg, type, id){
            	 var onFailMsg = linb.Locale['ja'].app.caption['onFail'];
                 linb.message(onFailMsg + msg);
             },
             null,
             {method:'POST'}).start();
//             window.close();
        }, 
        _btncancel_onclick:function (profile, e, src, value) {
            window.close();
        	//this.destroy();
        }
    }
});