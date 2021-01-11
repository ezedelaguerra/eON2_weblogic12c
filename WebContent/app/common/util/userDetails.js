var g_LangKey;
Class('App.userdetails', 'linb.Com',{
    Instance:{
		customAppend:function(){
		    var self=this, prop = this.properties;
		    
		    if(prop.fromRegion)
		        self.pnlEditUserDetails.setFromRegion(prop.fromRegion);
		
		    if(!self.pnlEditUserDetails.get(0).renderId)
		        self.pnlEditUserDetails.render();
		
		    self.pnlEditUserDetails.show(self.parent, true);
		}, 
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};

            append((new linb.DataBinder)
                .host(host,"bindnewuserdetails")
                .setName("bindnewuserdetails")
            );
            
            append((new linb.DataBinder)
                .host(host,"binduserdetails")
                .setName("binduserdetails")
            );

            append((new linb.UI.Dialog)
                .host(host,"pnlEditUserDetails")
                .setDock("none")
                .setLeft(330)
                .setTop(5)
                .setWidth(430)
                .setHeight(580)
                .setZIndex(1)
                .setCaption("$app.caption.userdetails")
                .setCloseBtn(true)
                .setPinBtn(false)
            );

            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"lblfirstname")
                .setLeft(20)
                .setTop(25)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.firstname :")
            );

            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"lblshortname")
                .setLeft(20)
                .setTop(55)
                .setWidth(120)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.shortname :")
            );

            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"lbladdress1")
                .setLeft(10)
                .setTop(170)
                .setWidth(130)
                .setCaption("$app.caption.address1 :")
            );

            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"lblcomments")
                .setLeft(30)
                .setTop(440)
                .setWidth(110)
                .setCaption("$app.caption.comments :")
            );

            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"lblemailadd")
                .setLeft(30)
                .setTop(350)
                .setWidth(110)
                .setCaption("$app.caption.emailaddress :")
            );

            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"lbltelno")
                .setLeft(30)
                .setTop(262)
                .setWidth(110)
                .setCaption("$app.caption.telephonenumber :")
            );

            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"lblcategory")
                .setLeft(30)
                .setTop(81)
                .setWidth(110)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.category :")
                .setVisibility("hidden")
            );

            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"lblmobno")
                .setLeft(30)
                .setTop(320)
                .setWidth(110)
                .setCaption("$app.caption.mobilenumber :")
            );

            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"lbladdress2")
                .setLeft(10)
                .setTop(201)
                .setWidth(130)
                .setCaption("$app.caption.address2 :")
            );

            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"lbladdress3")
                .setLeft(0)
                .setTop(232)
                .setWidth(140)
                .setCaption("$app.caption.address3 :")
            );

            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"lblfaxno")
                .setLeft(30)
                .setTop(291)
                .setWidth(110)
                .setCaption("$app.caption.faxnumber :")
            );

            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"lblmobemailadd")
                .setLeft(0)
                .setTop(380)
                .setWidth(140)
                .setCaption("$app.caption.mobileemailnumber :")
            );

            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditName")
                .setDataBinder("binduserdetails")
                .setDataField("compeditname")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(20)
                .setWidth(271)
                .setTabindex(31)
                .setMaxlength(64)
                //.beforeKeypress("_ipteditname_onkeypress")
                //.onChange("_ipteditname_onchange")
            );

            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditShortName")
                .setDataBinder("binduserdetails")
                .setDataField("compeditshortname")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(50)
                .setWidth(271)
                .setTabindex(32)
                .setMaxlength(5)
                .onHotKeypress("_ipteditshortname_onhotkeypress")
                .onBlur("_ipteditshortname_onblur")
            );

            host.pnlEditUserDetails.append((new linb.UI.List)
                .host(host,"lstEditCategory")
                .setDataBinder("binduserdetails")
                .setDataField("compeditcategory")
                .setDirtyMark(false)
                .setDisabled(true)
                .setLeft(140)
                .setTop(81)
                .setWidth(271)
                .setHeight(75)
                .setTabindex(34)
                .setVisibility("hidden")
            );

            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditAddress1")
                .setDataBinder("binduserdetails")
                .setDataField("compeditaddress1")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(165)
                .setWidth(271)
                .setTabindex(36)
                .setMaxlength(100)
                .onHotKeypress("_ipteditaddress1_onhotkeypress")
                .onBlur("_ipteditaddress1_onblur")
            );

            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditAddress2")
                .setDataBinder("binduserdetails")
                .setDataField("compeditaddress2")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(195)
                .setWidth(271)
                .setTabindex(37)
                .setMaxlength(100)
                .onHotKeypress("_ipteditaddress2_onhotkeypress")
                .onBlur("_ipteditaddress2_onblur")
            );

            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditAddress3")
                .setDataBinder("binduserdetails")
                .setDataField("compeditaddress3")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(225)
                .setWidth(271)
                .setTabindex(38)
                .setMaxlength(100)
                .onHotKeypress("_ipteditaddress3_onhotkeypress")
                .onBlur("_ipteditaddress3_onblur")
            );

            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditTelNo")
                .setDataBinder("binduserdetails")
                .setDataField("compedittelno")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(255)
                .setWidth(271)
                .setTabindex(39)
                .setMaxlength(20)
                .onHotKeypress("_iptedittelno_onhotkeypress")
                .onBlur("_iptedittelno_onblur")
                .setMask('11-1111-1111', true)
            );

            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditFaxNo")
                .setDataBinder("binduserdetails")
                .setDataField("compeditfaxno")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(285)
                .setWidth(271)
                .setTabindex(40)
                .setMaxlength(20)
                .onHotKeypress("_ipteditfaxno_onhotkeypress")
                .onBlur("_ipteditfaxno_onblur")
                .setMask('11-1111-1111')
            );

            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditMobileNo")
                .setDataBinder("binduserdetails")
                .setDataField("compeditmobileno")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(315)
                .setWidth(271)
                .setTabindex(41)
                .setMaxlength(20)
                .onHotKeypress("_ipteditmobileno_onhotkeypress")
                .onBlur("_ipteditmobileno_onblur")
                .setMask('11-1111-1111')
            );

            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditEmailAdd")
                .setDataBinder("binduserdetails")
                .setDataField("compeditemailaddress")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(345)
                .setWidth(271)
                .setTabindex(42)
                .setMaxlength(50)
                .setValueFormat("^[\\w\\.=-]+@[\\w\\.-]+\\.[\\w\\.-]{2,4}$")
                .onHotKeypress("_ipteditemailadd_onhotkeypress")
                .onBlur("_ipteditemailadd_onblur")
            );

            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditMobEmailAdd")
                .setDataBinder("binduserdetails")
                .setDataField("compeditmobemailaddress")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(375)
                .setWidth(271)
                .setTabindex(43)
                .setMaxlength(50)
                .onHotKeypress("_ipteditmobemailadd_onhotkeypress")
                .onBlur("_ipteditmobemailadd_onblur")
            );

            host.pnlEditUserDetails.append((new linb.UI.TextEditor)
                .host(host,"txtEditorEditComments")
                .setDataBinder("binduserdetails")
                .setDataField("compeditcomments")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(435)
                .setWidth(271)
                .setHeight(50)
                .setTabindex(44)
                .onHotKeypress("_txteditoreditcomments_onhotkeypress")
            );

            host.pnlEditUserDetails.append((new linb.UI.CheckBox)
                .host(host,"chkEditBMS")
                .setDataBinder("binduserdetails")
                .setDataField("compeditbms")
                .setDisabled(true)
                .setLeft(120)
                .setTop(405)
                .setWidth(271)
                .setTabindex(45)
                .setCaption("Use BMS")
            );

            host.pnlEditUserDetails.append((new linb.UI.Button)
                .host(host,"btnSaveUserDetials")
                .setLeft(221)
                .setTop(500)
                .setWidth(80)
                .setTabindex(49)
                .setCaption("$app.caption.save")
                .onClick("_btnsaveuserdetails_onclick")
            );

            host.pnlEditUserDetails.append((new linb.UI.Button)
                .host(host,"btnCancelEditUser")
                .setLeft(321)
                .setTop(500)
                .setWidth(80)
                .setTabindex(50)
                .setCaption("$app.caption.cancel")
                .onClick("_btncanceledituser_onclick")
            );

            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events:{"onReady":"_onReady","onRender":"_onRender"}, 
		_onRender : function(page, threadid) {
			userDetails = page;
		},
        _onReady:function(page, threadid){
			userDetails = this;
			g_LangKey = linb.getLang();
			var url = "/eON/usersinfo.json";
            var obj = new Object();
            obj.userId = header.lbluseridinfo.getCaption();
            linb.Ajax(url,obj,
                function(response){
            	validateResponse(response);
            	userDetails.binduserdetails.resetValue("");
                //this._hidePanelDetails("edit");
                var obj = _.unserialize(response);
                userDetails.iptEditName.setValue(obj.userinfo.name);
                userDetails.iptEditShortName.setValue(obj.userinfo.shortName);
                userDetails.iptEditAddress1.setValue(obj.userinfo.address1);
                userDetails.iptEditAddress2.setValue(obj.userinfo.address2);
                userDetails.iptEditAddress3.setValue(obj.userinfo.address3);
                userDetails.iptEditTelNo.setValue(obj.userinfo.telNumber);
                userDetails.iptEditFaxNo.setValue(obj.userinfo.faxNumber);
                userDetails.iptEditMobileNo.setValue(obj.userinfo.mobileNumber);
                userDetails.iptEditEmailAdd.setValue(obj.userinfo.pcEmail);
                userDetails.iptEditMobEmailAdd.setValue(obj.userinfo.mobileEmail);
                userDetails.txtEditorEditComments.setValue(obj.userinfo.comments);
                userDetails.chkEditBMS.setValue(obj.userinfo.useBms);
                if (g_userRole == "ROLE_SELLER"){
                	userDetails.lblcategory.setVisibility("visible");
                	userDetails.lstEditCategory.setVisibility("visible");
	                var items = [];
	                for(var i=0; i<obj.userinfo.categoryList.length; i++){
	                    items.push({"id":obj.userinfo.categoryList[i].categoryId, "caption":obj.userinfo.categoryList[i].categoryAvailable});
	                }
	                userDetails.lstEditCategory.setItems(items);
                }else{
                	userDetails.lbladdress1.setTop(81);
                    userDetails.iptEditAddress1.setTop(81);
                	userDetails.lbladdress2.setTop(112);
                    userDetails.iptEditAddress2.setTop(112);
                	userDetails.lbladdress3.setTop(143);
                    userDetails.iptEditAddress3.setTop(143);
                	userDetails.lbltelno.setTop(174);
                    userDetails.iptEditTelNo.setTop(174);
                	userDetails.lblfaxno.setTop(205);
                    userDetails.iptEditFaxNo.setTop(205);
                	userDetails.lblmobno.setTop(236);
                    userDetails.iptEditMobileNo.setTop(236);
                	userDetails.lblemailadd.setTop(267);
                    userDetails.iptEditEmailAdd.setTop(267);
                	userDetails.lblmobemailadd.setTop(298);
                    userDetails.iptEditMobEmailAdd.setTop(298);
                    userDetails.chkEditBMS.setTop(329);
                	userDetails.lblcomments.setTop(360);
                    userDetails.txtEditorEditComments.setTop(360);
                    userDetails.btnSaveUserDetials.setTop(430);
                    userDetails.btnCancelEditUser.setTop(430);
                    userDetails.pnlEditUserDetails.setHeight(500);
                }
            },
            function(msg){
                    linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
        }, 
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        }, 
        _btncanceledituser_onclick:function (profile, e, src, value) {
            //this.pnlEditUserDetails.hide();
            this.destroy();
        }, 
        _btnsaveuserdetails_onclick:function (profile, e, src, value) {
        	//var role = this.cbiEditRole.getUIValue(), categoryList="";
        	//if(role=="SELLER" || role=="BUYER")
        		//categoryList = this.lstEditCategory.getItems();
        	//else
        		//categoryList = "temporaryList";
            var reqFields = new Array();
            reqFields[0] = this.iptEditName;
            reqFields[1] = this.iptEditShortName;
 
            if (checkIfFieldIsValid(reqFields, this.lstEditCategory, "", 1)) {
            	
            	var contactNoFields = new Array();
            	contactNoFields[0] = this.iptEditTelNo;
            	contactNoFields[1] = this.iptEditFaxNo;
            	contactNoFields[2] = this.iptEditMobileNo;
                if(!validateContactNumberFields(contactNoFields)) {
                	//alert(linb.Locale[g_LangKey].app.caption['alertinvalidcontactnumber']);
                	return;
            }
                  
	        var url = "/eON/userDetails.json";
                //var url = "/eON/updateuserbyid.json";
            var obj = new Object();
            obj.userId = header.lbluseridinfo.getCaption();
            obj.name=this.iptEditName.getUIValue();
            obj.shortName=this.iptEditShortName.getUIValue();
            obj.address1=this.iptEditAddress1.getUIValue();
            obj.address2=this.iptEditAddress2.getUIValue();
            obj.address3=this.iptEditAddress3.getUIValue();
            obj.mobileNumber=this.iptEditMobileNo.getUIValue();
            obj.telNumber=this.iptEditTelNo.getUIValue();
            obj.faxNumber=this.iptEditFaxNo.getUIValue();
            obj.mobileEmail=this.iptEditMobEmailAdd.getUIValue();
            obj.pcEmail=this.iptEditEmailAdd.getUIValue();
            obj.comments=this.txtEditorEditComments.getUIValue();
            
            if (this.iptEditEmailAdd.getUIValue().length > 0 && checkEmail(this.iptEditEmailAdd) == false ) {
                alert(linb.Locale[g_LangKey].app.caption['alertinvalidemailadd']);
                return;
            }
            if (this.iptEditMobEmailAdd.getUIValue().length > 0 && checkEmail(this.iptEditMobEmailAdd) == false ) {
                alert(linb.Locale[g_LangKey].app.caption['alertinvalidmobileemailadd']);
                return;
            }
            
            linb.Ajax(url,obj,
            function(response){
                var res = _.unserialize(response);
                validateResponse(response);
                if (parseInt(res.status)>0) {
                    alert(linb.Locale[g_LangKey].app.caption['alertuserupdated']);
                }
                else {
                    alert(linb.Locale[g_LangKey].app.caption['alerterrorupdatingrecord']);
                }
            },
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
            } else {
                alert(linb.Locale[g_LangKey].app.caption['alertrequiredfields']);
            }
        },
        _iptedittelno_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            var unicode=e.charCode? e.charCode : e.keyCode;
            if (unicode==43 || unicode==45)
                return true;
            if ((unicode<48 || unicode>57) && (key!= "backspace"))
                return false;
        },
        _ipteditfaxno_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            var unicode=e.charCode? e.charCode : e.keyCode;
            if (unicode==43 || unicode==45)
                return true;
            if ((unicode<48 || unicode>57) && (key!= "backspace"))
                return false;
        },
        _ipteditmobileno_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            var unicode=e.charCode? e.charCode : e.keyCode;
            if (unicode==43 || unicode==45)
                return true;
            if ((unicode<48 || unicode>57) && (key!= "backspace"))
                return false;
        },
        _txteditoreditcomments_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            if (userDetails.txtEditorEditComments.getUIValue().length >= 1000 && key!= "backspace"){
                key=null;
                return false;
            }
        },
        _ipteditemailadd_onblur:function (profile) {
            var oldValue = userDetails.iptEditEmailAdd.getUIValue();
            if(oldValue.length>0) {
            var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
                if(reg.test(oldValue) == false) {
                    alert(linb.Locale[g_LangKey].app.caption['alertinvalidemailadd']);
                    return false;
                }
            }
        }, 
        _ipteditmobemailadd_onblur:function (profile) {
            var oldValue = userDetails.iptEditMobEmailAdd.getUIValue();
            if(oldValue.length>0) {
            var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
                if(reg.test(oldValue) == false) {
                    alert(linb.Locale[g_LangKey].app.caption['alertinvalidmobileemailadd']);
                    return false;
                }
            }
        }, 
        _iptedittelno_onblur:function (profile) {
            var telNo = userDetails.iptEditTelNo.getUIValue();
            if(telNo.replace(/-|_/g,"").length<10) {
                alert(linb.Locale[g_LangKey].app.caption['alertinvalidtelno']);
                return false;
            }
        }, 
        _ipteditfaxno_onblur:function (profile) {
            var faxNo = userDetails.iptEditFaxNo.getUIValue();
            if(faxNo.replace(/-|_/g,"").length<10) {
                alert(linb.Locale[g_LangKey].app.caption['alertinvalidfaxno']);
                return false;
            }
        }, 
        _ipteditmobileno_onblur:function (profile) {
            var mobNo = userDetails.iptEditMobileNo.getUIValue();
            if(mobNo.replace(/-|_/g,"").length<10) {
                alert(linb.Locale[g_LangKey].app.caption['alertinvalidmobileno']);
                return false;
            }
        }
        /*,
        _ipteditname_onkeypress:function(profile, caret, key, control, shift, alt) { 
        	alert(key.length);
        	if(key.length==1){ 
        		if(control && (key=='c'||key=='x'||key=='a')) return true; 
        		var len=userDetails.ipteditname.getUIValue().length - (caret[1]-caret[0]); 
        		if (len >= 10) return false; 
        	} 
        },
        _ipteditname_onchange:function (profile, oldValue, newValue) { 
        	if (newValue.length > 10) profile.boxing().setUIValue(newValue.substr(0,10),true); 
        }*/
    }
});