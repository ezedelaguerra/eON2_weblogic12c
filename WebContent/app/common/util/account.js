
Class('App.account', 'linb.Com',{
    Instance:{
		customAppend:function(){
		    var self=this, prop = this.properties;
		    if(prop.fromRegion)
		        self.dlgaccount.setFromRegion(prop.fromRegion);
		
		    if(!self.dlgaccount.get(0).renderId)
		        self.dlgaccount.render();
		        self.dlgaccount.show(self.parent, true);
	    },
        autoDestroy:true,
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
                .host(host,"dlgaccount")
                .setLeft(390)
                .setTop(90)
                .setWidth(320)
                .setHeight(250)
                .setTabindex(0)
                .setResizer(false)
                .setCaption("Account")
                .setMinBtn(false)
                .setMaxBtn(false)
                .setPinBtn(false)
            );
            
            host.dlgaccount.append((new linb.UI.Group)
                .host(host,"grpaccount")
                .setLeft(10)
                .setTop(10)
                .setWidth(290)
                .setHeight(190)
                .setTabindex(0)
                .setCaption("$app.caption.userdetails")
            );
            
            host.grpaccount.append((new linb.UI.Label)
                .host(host,"lbluserid")
                .setLeft(10)
                .setTop(20)
                .setWidth(100)
                .setTabindex(0)
                .setCaption("$app.caption.lblUserName")
                .setFontSize("12px")
                .setFontWeight("bold")
            );
            
            host.grpaccount.append((new linb.UI.Label)
                .host(host,"lblusername")
                .setLeft(10)
                .setTop(65)
                .setWidth(100)
                .setTabindex(0)
                .setCaption("$app.caption.name : ")
                .setFontSize("12px")
                .setFontWeight("bold")
            );
            
            host.grpaccount.append((new linb.UI.Label)
                .host(host,"lblpassword")
                .setLeft(10)
                .setTop(80)
                .setWidth(100)
                .setTabindex(0)
                .setCaption("Password : ")
                .setFontSize("12px")
                .setFontWeight("bold")
                .setVisibility("hidden")
            );
            
            host.grpaccount.append((new linb.UI.Label)
                .host(host,"lbllastlogindt")
                .setLeft(10)
                .setTop(110)
                .setWidth(100)
                .setTabindex(0)
                .setCaption("$app.caption.lastloginDate  : ")
                .setFontSize("12px")
                .setFontWeight("bold")
            );
            
            host.grpaccount.append((new linb.UI.Label)
                .host(host,"lbllastlogindtinfo")
                .setLeft(120)
                .setTop(110)
                .setWidth(150)
                .setTabindex(0)
                .setCaption("lbllastlogindtinfo")
                .setHAlign("left")
            );
            
            host.grpaccount.append((new linb.UI.Label)
                .host(host,"lblpasswordinfo")
                .setLeft(120)
                .setTop(80)
                .setWidth(100)
                .setTabindex(0)
                .setCaption("lblpasswordinfo")
                .setHAlign("left")
                .setVisibility("hidden")
            );
            
            host.grpaccount.append((new linb.UI.Label)
                .host(host,"lblnameinfo")
                .setLeft(120)
                .setTop(65)
                .setWidth(100)
                .setTabindex(0)
                .setCaption("lblnameinfo")
                .setHAlign("left")
            );
            
            host.grpaccount.append((new linb.UI.Label)
                .host(host,"lblusernameinfo")
                .setLeft(120)
                .setTop(20)
                .setWidth(100)
                .setTabindex(0)
                .setCaption("lblusernameinfo")
                .setHAlign("left")
            );
            
            host.grpaccount.append((new linb.UI.Label)
                .host(host,"lbluseridinfo")
                .setLeft(120)
                .setTop(20)
                .setWidth(100)
                .setTabindex(0)
                .setCaption("lbluseridinfo")
                .setHAlign("left")
                .setVisibility("hidden")
            );
            
            host.grpaccount.append((new linb.UI.Button)
                .host(host,"btnok")
                .setLeft(200)
                .setTop(140)
                .setWidth(70)
                .setCaption("Ok")
                .onClick("_btnok_onclick")
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
            account=page;
            account.getPassword();
        }, 
        _onready:function () {
        	account = this;
        	account.getPassword();
        }, 
        _btnok_onclick:function (profile, e, src, value) {
            this.destroy();
        },
        getPassword: function() {
        	var currpass;
        	currpass = account.lblpasswordinfo.getCaption();
        	return currpass;
        }
    }
});