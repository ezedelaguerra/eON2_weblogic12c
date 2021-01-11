var g_LangKey;

Class('App.changepassword', 'linb.Com',{
    Instance:{
        autoDestroy:true, 

        customAppend:function(){
		    var self=this, prop = this.properties;
		    
		    if(prop.fromRegion)
		        self.dlgChangePassword.setFromRegion(prop.fromRegion);
		
		    if(!self.dlgChangePassword.get(0).renderId)
		        self.dlgChangePassword.render();
		
		    self.dlgChangePassword.show(self.parent, true);
		}, 
	
		
//		lblPasswordMaintenance :'Password Maintenance',
//		lblChangePassword : 'Change Password',
//		lblUserName : 'Username :',
//		lblNewPassword :'New Password :',
//		lblConfirmPassword:'Confirm Password :'
		
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
                .host(host,"dlgChangePassword")
                .setLeft(490)
                .setTop(190)
                .setWidth(320)
                .setHeight(310)
                .setCaption("$app.caption.lblPasswordMaintenance")
                .setMinBtn(false)
                .setMaxBtn(false)
                .setResizer(false)
                .setPinBtn(false)
            );
            
            host.dlgChangePassword.append((new linb.UI.Group)
                .host(host,"grpChangePassword")
                .setLeft(10)
                .setTop(10)
                .setWidth(290)
                .setHeight(250)
                .setCaption("$app.caption.lblChangePassword")
                .setToggleBtn(false)
            );
            
            host.grpChangePassword.append((new linb.UI.Label)
                .host(host,"label71")
                .setLeft(20)
                .setTop(20)
                .setWidth(80)
                .setCaption("$app.caption.searhRecipientUserNames")
                .setHAlign("left")
            );
            
            host.grpChangePassword.append((new linb.UI.Label)
                .host(host,"label76")
                .setLeft(20)
                .setTop(70)
                .setWidth(100)
                .setCaption("$app.caption.lblNewPassword")
                .setHAlign("left")
            );
            
            host.grpChangePassword.append((new linb.UI.Label)
                .host(host,"label77")
                .setLeft(20)
                .setTop(120)
                .setWidth(190)
                .setCaption("$app.caption.lblConfirmPassword")
                .setHAlign("left")
            );
            
            host.grpChangePassword.append((new linb.UI.Label)
                .host(host,"lblUserId")
                .setLeft(190)
                .setTop(20)
                .setWidth(80)
                .setVisibility("hidden")
                .setCaption("")
            );
            
            host.grpChangePassword.append((new linb.UI.Input)
                .host(host,"iptUsername")
                .setLeft(20)
                .setTop(40)
                .setWidth(250)
                .setTabindex(10)
                .setDisabled(true)
                .setValueFormat("[^.*]")
                .onHotKeypress("_iptusername_onhotkeypress")
            );
            
            host.grpChangePassword.append((new linb.UI.Input)
                .host(host,"iptNewPword")
                .setLeft(20)
                .setTop(90)
                .setWidth(250)
                .setDirtyMark(false)
                .setType("password")
                .setTabindex(11)
                .setValueFormat("[^.*]")
                .setMaxlength(21)
                .onHotKeypress("_iptnewpword_onhotkeypress")
            );
            
            host.grpChangePassword.append((new linb.UI.Input)
                .host(host,"iptConfirmPword")
                .setLeft(20)
                .setTop(140)
                .setWidth(250)
                .setDirtyMark(false)
                .setType("password")
                .setTabindex(12)
                .setValueFormat("[^.*]")
                .setMaxlength(21)
                .onHotKeypress("_iptconfirmpword_onhotkeypress")
            );
            
            host.grpChangePassword.append((new linb.UI.Button)
                .host(host,"btnUpdate")
                .setLeft(100)
                .setTop(190)
                .setWidth(80)
                .setTabindex(13)
                .setCaption("$app.caption.save") // original caption is "update"
                .onClick("_btnupdate_onclick")
            );
            
            host.grpChangePassword.append((new linb.UI.Button)
                .host(host,"btnCancel")
                .setLeft(190)
                .setTop(190)
                .setWidth(80)
                .setTabindex(14)
               .setCaption("$app.caption.cancel")
                .onClick("_btncancel_onclick")
            );
            
            host.dlgChangePassword.append((new linb.UI.Block)
                .host(host,"blkChangePassword")
                .setLeft(30)
                .setTop(50)
                .setWidth(250)
                .setHeight(140)
                .setVisibility("hidden")
            );
            
            host.blkChangePassword.append((new linb.UI.Group)
                .host(host,"group14")
                .setLeft(10)
                .setTop(10)
                .setWidth(230)
                .setHeight(110)
                .setCaption("$app.caption.lblEnterCurrentPassword")
                .setToggleBtn(false)
            );
            
            host.group14.append((new linb.UI.Input)
                .host(host,"iptCurrentPassword")
                .setDomId("currpass")
                .setType("password")
                .setDirtyMark(false)
                .setMaxlength(21)
                .setLeft(20)
                .setTop(20)
                .setWidth(190)
                .onHotKeypress("_iptcurrentpassword_onhotkeypress")
            );
            
            host.group14.append((new linb.UI.Button)
                .host(host,"btnOk")
                .setLeft(80)
                .setTop(60)
                .setWidth(80)
                .setCaption("Ok")
                .onClick("_btnok_onclick")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events:{"onReady":"_onready","onRender":"_onrender"}, 
        _onrender:function() {
        }, 
        _onready: function() {
            CP = this;
            g_LangKey = linb.getLang();
        }, 
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        }, 
        _btnupdate_onclick:function (profile, e, src, value) {
        	CP.enterTheCurrentPassword();
        }, 
        _btncancel_onclick:function (profile, e, src, value) {
            this.destroy();
        }, 
        _iptusername_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            if (CP.iptUsername.getUIValue().length > 21 && key!= "backspace"){
                key=null;
                return false;
            }
        }, 
        _iptnewpword_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            if (CP.iptNewPword.getUIValue().length > 21 && key!= "backspace"){
                key=null;
                return false;
            };
        }, 
        _iptconfirmpword_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            if (CP.iptConfirmPword.getUIValue().length > 21 && key!= "backspace"){
                key=null;
                return false;
            };
            if(key=='enter') 
            	CP.enterTheCurrentPassword();
        }, 
       
        _btnok_onclick:function (profile, e, src, value) {
        	CP.changeNowThePassword();
        },
        _iptcurrentpassword_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            if(key=='enter')
            	CP.changeNowThePassword();
        },
        changeNowThePassword: function() {
        	var currpass;
        	if (g_userRole == "ROLE_ADMIN" || g_userRole == "role_admin")
        		currpass = _SPA.lblpasswordinfo.getCaption();
        	else
        		currpass = header.lblpasswordinfo.getCaption();
        	
            if(this.iptCurrentPassword.getUIValue() == currpass){
                var url = "/eON/updatepassword.json";
                var obj = new Object();
                obj.userName = this.iptUsername.getUIValue();
                obj.userId = this.lblUserId.getCaption();
                obj.password=this.iptNewPword.getUIValue();
                linb.Ajax(url,obj,
                    function(response){
                		validateResponse(response);
                        var res = _.unserialize(response);
                        if (parseInt(res.status)>0) {
                        	alert(linb.Locale[g_LangKey].app.caption['alertUpdatePasswordSuccess']);
                            CP.destroy();
                        }
                        else {
                            alert(linb.Locale[g_LangKey].app.caption['alertErrorOnUpdatePassword']);
                        }
                },
                function(msg){
                    linb.message("失敗： " + msg);
                },
                null,
                {method:'POST',retry:0}).start();
            }
            else {
                alert(linb.Locale[g_LangKey].app.caption['alertPasswordNotMatched']);
                this.blkChangePassword.hide();
            }
        },
        enterTheCurrentPassword: function() {
        	var reqFields = new Array();
        	reqFields[0] = this.iptConfirmPword;
        	reqFields[1] = this.iptNewPword;
        	reqFields[2] = this.iptUsername;
            if(checkIfFieldIsValid(reqFields, "", "", 0)){
               if(this.iptConfirmPword.getUIValue() == this.iptNewPword.getUIValue()) {
                   this.blkChangePassword.reBoxing().show();
                   var currid = linb.Dom.byId('currpass');
                   linb(currid).nextFocus();
                }
                else {
                	 alert(linb.Locale[g_LangKey].app.caption['alertErrorOnUpdatePassword']);
                }
            }
        }
    }
});