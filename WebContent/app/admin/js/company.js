
Class('App.company', 'linb.Com',{
    Initialize:function(){
       // global vars or functions here
       window.g_LangKey = "";
       window.g_saveMode = "";
       window.removedItems = [];
       window.g_userId = "";
       window.g_dpflag = "";
       window.g_removeMode = "";
       window.g_userItem = [];
    }, 
    Instance:{
        autoDestroy:true,        
        events:{"onReady":"_onReady","onRender":"_onrender"},        
        _onrender:function() {
            setFocus(this.iptSearchCompName);
        }, 
        _onReady:function(page, threadid){ 
            SPA=this;
            g_LangKey = linb.getLang();
            this.grpCompanyDetails.setToggle(false);
            setFocus(this.iptSearchCompName);            
        },        
        customAppend:function(parent,subId,left,top){
            return false;
        },        
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        },        
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.DataBinder)
                .host(host,"bindcompany")
                .setName("bindcompany")
            );
            
            append((new linb.DataBinder)
                .host(host,"binduserdetails")
                .setName("binduserdetails")
            );
            
            append((new linb.DataBinder)
                .host(host,"bindnewuserdetails")
                .setName("bindnewuserdetails")
            );
            
            append((new linb.DataBinder)
                .host(host,"bindAdminNewUsers")
                .setName("bindAdminNewUsers")
            );
            
            append((new linb.UI.Panel)
                .host(host,"panelCompany")
                .setZIndex(1)
                .setVisibility("visible")
                .setCaption("$app.caption.companymaintenance")
                .setCustomStyle({"PANEL":"overflow:auto;"})
            );
            
            host.panelCompany.append((new linb.UI.Group)
                .host(host,"grpCompanyDetails")
                .setLeft(20)
                .setTop(150)
                .setWidth(430)
                .setHeight(730)
                .setVisibility("hidden")
                .setCaption("<font size='1px' color='#123000'>$app.caption.mandatoryfields (<font color='red'>*</font>)</font>")
                .setToggleBtn(false)
                .setToggle(false)
            );
            
            host.grpCompanyDetails.append((new linb.UI.Label)
                .host(host,"lblUserId")
                .setLeft(280)
                .setTop(460)
                .setWidth(70)
                .setCaption("")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Label)
                .host(host,"lblCompanyId")
                .setLeft(220)
                .setTop(460)
                .setWidth(80)
                .setCaption("")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Label)
                .host(host,"lblemailAdd")
                .setLeft(50)
                .setTop(280)
                .setWidth(100)
                .setCaption("<b>$app.caption.emailaddress :</b>")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Label)
                .host(host,"lblAddress3")
                .setLeft(0)
                .setTop(190)
                .setWidth(150)
                .setCaption("<b>$app.caption.address3 :</b>")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Label)
                .host(host,"lblTelNo")
                .setLeft(50)
                .setTop(220)
                .setWidth(100)
                .setCaption("<b>$app.caption.telephonenumber :</b>")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Label)
                .host(host,"lblUsersList")
                .setLeft(10)
                .setTop(490)
                .setCaption("<u><b>$app.caption.userslist :</b></u>")
                .setHAlign("left")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Label)
                .host(host,"lblShortname")
                .setLeft(60)
                .setTop(40)
                .setWidth(90)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;<b>$app.caption.shortname :</b>")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Label)
                .host(host,"lblAddress1")
                .setLeft(30)
                .setTop(130)
                .setCaption("<b>$app.caption.address1 :</b>")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Label)
                .host(host,"lblComptype")
                .setLeft(30)
                .setTop(70)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;<b>$app.caption.companytype :</b>")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Label)
                .host(host,"lblFaxno")
                .setLeft(50)
                .setTop(250)
                .setWidth(100)
                .setCaption("<b>$app.caption.faxnumber :</b>")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Label)
                .host(host,"lblCompName")
                .setLeft(30)
                .setTop(15)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;<b>$app.caption.companyname :</b>")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Label)
                .host(host,"lblAddress2")
                .setLeft(30)
                .setTop(160)
                .setCaption("<b>$app.caption.address2 :</b>")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Label)
                .host(host,"lblComments")
                .setLeft(30)
                .setTop(330)
                .setCaption("<b>$app.caption.comments :</b>")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Label)
                .host(host,"lblContactPerson")
                .setLeft(30)
                .setTop(100)
                .setCaption("<b>$app.caption.contactperson :</b>")
            );
            
            host.grpCompanyDetails.append((new linb.UI.TreeGrid)
                .host(host,"tgUsersList")
                .setDataBinder("bindcompany")
                .setDataField("compuserslist")
                .setDock("none")
                .setLeft(10)
                .setTop(510)
                .setWidth(400)
                .setHeight(160)
                .setRowHandler(false)
                .setHeader([{"id":"colUsername", "width":140, "type":"label", "caption":"$app.caption.userid", "cellStyle":"text-align:center;", "headerStyle":"font-weight:bold"}, {"id":"colRole", "width":135, "type":"label", "caption":"$app.caption.role", "cellStyle":"text-align:center;", "headerStyle":"font-weight:bold"}, {"id":"colDetail", "width":100, "type":"button", "cellStyle":"background-color:#E6E6FA;text-align:center;", "caption":"$app.caption.details", "headerStyle":"font-weight:bold"}])
                .setValue("")
                .onClickCell("_tguserslist_onclickcell")
                .setCustomStyle({"KEY":"cursor:pointer"})
            );
            
            host.grpCompanyDetails.append((new linb.UI.Input)
                .host(host,"iptCompName")
                .setDataBinder("bindcompany")
                .setDataField("companyname")
                .setDirtyMark(false)
                .setLeft(150)
                .setTop(10)
                .setWidth(260)
                .setTabindex(401)
                .setBorder(true)
                .setTipsErr("Required Company name.")
                .setMaxlength(50)
            );
            
            host.grpCompanyDetails.append((new linb.UI.Input)
                .host(host,"iptShortName")
                .setDataBinder("bindcompany")
                .setDataField("compshortname")
                .setDirtyMark(false)
                .setLeft(150)
                .setTop(40)
                .setWidth(260)
                .setTabindex(402)
                .setBorder(true)
                .setTipsErr("Required Short Name")
                .setMaxlength(5)
            );
            
            host.grpCompanyDetails.append((new linb.UI.ComboInput)
                .host(host,"cbiCompanyType")
                .setDataBinder("bindcompany")
                .setDataField("comptype")
                .setDirtyMark(false)
                .setLeft(150)
                .setTop(70)
                .setWidth(260)
                .setHeight(23)
                .setTabindex(403)
                .setBorder(true)
                .setTipsErr("Required Company Type")
                .setDynCheck(true)
                .setCachePopWnd(false)
                .setItems([{"id":"1", "caption":"Seller"}, {"id":"3", "caption":"Buyer"}])
                .setCaption("")
                .onHotKeypress("_cbicompanytype_onhotkeypress")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Input)
                .host(host,"iptContactPerson")
                .setDataBinder("bindcompany")
                .setDataField("compcontactperson")
                .setDirtyMark(false)
                .setLeft(150)
                .setTop(100)
                .setWidth(260)
                .setTabindex(404)
                .setBorder(true)
                .setMaxlength(50)
            );
            
            host.grpCompanyDetails.append((new linb.UI.Input)
                .host(host,"iptAddress1")
                .setDataBinder("bindcompany")
                .setDataField("compaddress1")
                .setDirtyMark(false)
                .setLeft(150)
                .setTop(130)
                .setWidth(260)
                .setTabindex(405)
                .setBorder(true)
                .setMaxlength(100)
            );
            
            host.grpCompanyDetails.append((new linb.UI.Input)
                .host(host,"iptAddress2")
                .setDataBinder("bindcompany")
                .setDataField("compaddress2")
                .setDirtyMark(false)
                .setLeft(150)
                .setTop(160)
                .setWidth(260)
                .setTabindex(406)
                .setBorder(true)
                .setMaxlength(100)
            );
            
            host.grpCompanyDetails.append((new linb.UI.Input)
                .host(host,"iptAddress3")
                .setDataBinder("bindcompany")
                .setDataField("compaddress3")
                .setDirtyMark(false)
                .setLeft(150)
                .setTop(190)
                .setWidth(260)
                .setTabindex(407)
                .setBorder(true)
                .setMaxlength(100)
            );
            
            host.grpCompanyDetails.append((new linb.UI.Input)
                .host(host,"iptTelNo")
                .setDataBinder("bindcompany")
                .setDataField("comptelno")
                .setDirtyMark(false)
                .setLeft(150)
                .setTop(220)
                .setWidth(260)
                .setTabindex(408)
                .setBorder(true)
                .setMask("11-1111-1111")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Input)
                .host(host,"iptFaxNo")
                .setDataBinder("bindcompany")
                .setDataField("compfaxno")
                .setDirtyMark(false)
                .setLeft(150)
                .setTop(250)
                .setWidth(260)
                .setTabindex(409)
                .setBorder(true)
                .setMask("11-1111-1111")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Input)
                .host(host,"iptEmailAdd")
                .setDataBinder("bindcompany")
                .setDataField("compemailaddress")
                .setDirtyMark(false)
                .setLeft(150)
                .setTop(280)
                .setWidth(260)
                .setTabindex(410)
                .setBorder(true)
                .setMaxlength(50)
                .onBlur("_iptemailadd_onblur")
            );
            
            host.grpCompanyDetails.append((new linb.UI.CheckBox)
                .host(host,"chkSOX")
                .setDataBinder("bindcompany")
                .setDataField("compsox")
                .setDirtyMark(false)
                .setDisabled(true)
                .setLeft(120)
                .setTop(305)
                .setTabindex(411)
                .setVisibility("hidden")
                .setCaption("<font color='#000000'>SOX</font>")
            );
            
            host.grpCompanyDetails.append((new linb.UI.TextEditor)
                .host(host,"txtEditorComment")
                .setDataBinder("bindcompany")
                .setDataField("compcomments")
                .setDirtyMark(false)
                .setLeft(150)
                .setTop(330)
                .setWidth(260)
                .setHeight(90)
                .setTabindex(412)
                .setBorder(true)
                .setValue("")
                .onHotKeypress("_txteditorcomment_onhotkeypress")
            );
            
            host.grpCompanyDetails.append((new linb.UI.CheckBox)
                .host(host,"chkIncludeDetails")
                .setLeft(10)
                .setTop(430)
                .setWidth(170)
                .setTabindex(413)
                .setVisibility("hidden")
                .setCaption("$app.caption.includeuserdetails")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Button)
                .host(host,"btnDownloadCompInfo")
                .setDisabled(true)
                .setLeft(20)
                .setTop(460)
                .setWidth(190)
                .setTabindex(414)
                .setCaption("$app.caption.downloadcompanyinfo")
                .setImagePos("left")
                .onClick("_btndownloadcompInfo_onclick")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Button)
                .host(host,"btnDealingPattern")
                .setLeft(220)
                .setTop(460)
                .setWidth(190)
                .setTabindex(415)
                .setCaption("$app.caption.companydealingpattern")
                .onClick("_btndealingpattern_onclick")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Button)
                .host(host,"btnAddCompUser")
                .setLeft(120)
                .setTop(680)
                .setWidth(80)
                .setTabindex(416)
                .setCaption("$app.caption.adduser")
                .onClick("_btnaddcompuser_onclick")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Button)
                .host(host,"btnAdministrator")
                .setLeft(10)
                .setTop(680)
                .setWidth(110)
                .setTabindex(417)
                .setCaption("$app.caption.newadmininstrator")
                .onClick("_btnadministrator_onclick")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Button)
                .host(host,"btnSaveCompUser")
                .setLeft(270)
                .setTop(680)
                .setWidth(70)
                .setTabindex(418)
                .setCaption("$app.caption.save")
                .onClick("_btnsavecompuser_onclick")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Button)
                .host(host,"btnCancelCompUser")
                .setLeft(340)
                .setTop(680)
                .setWidth(70)
                .setTabindex(419)
                .setCaption("$app.caption.cancel")
                .onClick("_btncancelcompuser_onclick")
            );
            
            host.grpCompanyDetails.append((new linb.UI.Button)
                .host(host,"btnDeleteCompUser")
                .setDomId("btnDeleteCompUser")
                .setDisabled(true)
                .setLeft(200)
                .setTop(680)
                .setWidth(70)
                .setTabindex(420)
                .setCaption("$app.caption.deletes")
                .onClick("_btndeletecompuser_onclick")
            );
            
            host.panelCompany.append((new linb.UI.Group)
                .host(host,"pnlNewUserDetails")
                .setLeft(471)
                .setTop(20)
                .setWidth(430)
                .setHeight(773)
                .setVisibility("hidden")
                .setCaption("$app.caption.newuserdetails")
                .setToggleBtn(false)
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"label326")
                .setLeft(30)
                .setTop(361)
                .setWidth(110)
                .setCaption("$app.caption.faxnumber :")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"label325")
                .setLeft(0)
                .setTop(301)
                .setWidth(140)
                .setCaption("$app.caption.address3 :")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"label105")
                .setLeft(20)
                .setTop(616)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.userid :")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"label106")
                .setLeft(20)
                .setTop(646)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.password :")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"label231")
                .setLeft(40)
                .setTop(110)
                .setWidth(100)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.category :")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"label248")
                .setLeft(30)
                .setTop(246)
                .setWidth(110)
                .setCaption("$app.caption.address1 :")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"label324")
                .setLeft(20)
                .setTop(276)
                .setCaption("$app.caption.address2 :")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"label102")
                .setLeft(40)
                .setTop(391)
                .setWidth(100)
                .setCaption("$app.caption.mobilenumber :")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"label103")
                .setLeft(30)
                .setTop(421)
                .setWidth(110)
                .setCaption("$app.caption.emailaddress :")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"label104")
                .setLeft(40)
                .setTop(481)
                .setWidth(100)
                .setCaption("$app.caption.comments :")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"label327")
                .setLeft(10)
                .setTop(456)
                .setWidth(130)
                .setCaption("$app.caption.mobileemailnumber :")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"label246")
                .setLeft(50)
                .setTop(85)
                .setWidth(90)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.role :")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"label245")
                .setLeft(20)
                .setTop(25)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.firstname :")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.CheckBox)
                .host(host,"tosNewUserChk")
                .setDomId("tosNewUserChk")
                .setDataBinder("bindnewuserdetails")
                .setLeft(380)
                .setTop(580)
                .setWidth(30)
                .setCaption(" ")
            );

            host.pnlNewUserDetails.append((new linb.UI.CheckBox)
                .host(host,"newCalendarHighlightChk")
                .setDomId("newCalendarHighlightChk")
                .setDataBinder("binduserdetails")
                .setDirtyMark(false)
                .setLeft(210)
                .setTop(580)
                .setDisabled(true)
                .setWidth(40)
                .setCaption("")
                .setHAlign("right")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"label293")
                .setLeft(30)
                .setTop(331)
                .setWidth(110)
                .setCaption("$app.caption.telephonenumber :")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"label239")
                .setLeft(240)
                .setWidth(150)
                .setTop(580)
                .setCaption("$app.caption.TOS_TITLE_USR_DTL")
                .setVAlign("middle")
            );

            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"newLabelCalendarHighlight")
                .setLeft(70)
                .setWidth(150)
                .setTop(580)
                .setCaption("$app.caption.calendarHighlight")
                .setVAlign("middle")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Label)
                .host(host,"label247")
                .setLeft(30)
                .setTop(55)
                .setWidth(110)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.shortname :")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.CheckBox)
                .host(host,"chkNewUnfinalizeReceived")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewfinrec")
                .setDirtyMark(false)
                .setLeft(130)
                .setTop(220)
                .setWidth(271)
                .setTabindex(45)
                .setVisibility("hidden")
                .setCaption("$app.caption.unfinalizedReceived")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptNewFirstName")
                .setDomId("iptNewFirstName")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewfirstname")
                .setLeft(140)
                .setTop(20)
                .setWidth(260)
                .setTabindex(51)
                .setBorder(true)
                .setMaxlength(64)
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptNewShortName")
                .setDomId("iptNewShortName")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewshortname")
                .setLeft(140)
                .setTop(50)
                .setWidth(260)
                .setTabindex(52)
                .setBorder(true)
                .setMaxlength(5)
            );
            
            host.pnlNewUserDetails.append((new linb.UI.ComboInput)
                .host(host,"cbiNewRole")
                .setDomId("cbiNewRole")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewrole")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(81)
                .setWidth(260)
                .setZIndex(0)
                .setTabindex(53)
                .setBorder(true)
                .setDynCheck(true)
                .setCachePopWnd(false)
                .setCaption("")
                .afterUIValueSet("_cbinewrole_afteruivalueset")
                .onHotKeypress("_cbinewrole_onhotkeypress")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.List)
                .host(host,"lstNewCategory")
                .setDomId("lstNewCategory")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewcategory")
                .setDisabled(true)
                .setLeft(140)
                .setTop(110)
                .setWidth(260)
                .setHeight(101)
                .setTabindex(54)
                .setSelMode("multi")
                .setValue("")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptNewAddress1")
                .setDomId("iptNewAddress1")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewaddress1")
                .setLeft(140)
                .setTop(241)
                .setWidth(260)
                .setTabindex(55)
                .setBorder(true)
                .setMaxlength(100)
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptNewAddress2")
                .setDomId("iptNewAddress2")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewaddress2")
                .setLeft(140)
                .setTop(271)
                .setWidth(260)
                .setTabindex(56)
                .setBorder(true)
                .setMaxlength(100)
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptNewAddress3")
                .setDomId("iptNewAddress3")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewaddress3")
                .setLeft(140)
                .setTop(301)
                .setWidth(260)
                .setTabindex(57)
                .setBorder(true)
                .setMaxlength(100)
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptNewTelNo")
                .setDomId("iptNewTelNo")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewtelno")
                .setLeft(140)
                .setTop(331)
                .setWidth(260)
                .setTabindex(58)
                .setBorder(true)
                .setMask("11-1111-1111")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptNewFaxNo")
                .setDomId("iptNewFaxNo")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewfaxno")
                .setLeft(140)
                .setTop(361)
                .setWidth(260)
                .setTabindex(59)
                .setBorder(true)
                .setMask("11-1111-1111")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptNewMobileNo")
                .setDomId("iptNewMobileNo")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewmobileno")
                .setLeft(140)
                .setTop(391)
                .setWidth(260)
                .setTabindex(60)
                .setBorder(true)
                .setMask("111-1111-1111")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptNewEmailAdd")
                .setDomId("iptNewEmailAdd")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewemailadd")
                .setLeft(140)
                .setTop(421)
                .setWidth(260)
                .setTabindex(61)
                .setBorder(true)
                .setMaxlength(50)
                .onBlur("_iptnewemailadd_onblur")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptNewMobEmailAdd")
                .setDomId("iptNewMobEmailAdd")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewmobemailadd")
                .setLeft(140)
                .setTop(451)
                .setWidth(260)
                .setTabindex(62)
                .setBorder(true)
                .setMaxlength(50)
                .onBlur("_iptnewmobemailadd_onblur")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.TextEditor)
                .host(host,"txtEditorNewComments")
                .setDomId("txtEditorNewComments")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewcomments")
                .setLeft(140)
                .setTop(481)
                .setWidth(260)
                .setHeight(90)
                .setTabindex(63)
                .setBorder(true)
                .setValue("")
                .onHotKeypress("_txteditornewcomments_onhotkeypress")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.CheckBox)
                .host(host,"chkNewBMS")
                .setDomId("chkNewBMS")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewuserbms")
                .setDisabled(true)
                .setLeft(120)
                .setTop(581)
                .setWidth(191)
                .setTabindex(64)
                .setVisibility("hidden")
                .setCaption("$app.caption.userbms")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptNewUserId")
                .setDomId("iptNewUserId")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewuserid")
                .setLeft(140)
                .setTop(611)
                .setWidth(260)
                .setTabindex(65)
                .setBorder(true)
                .setMaxlength(21)
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptNewPassword")
                .setDomId("iptNewPassword")
                .setDataBinder("bindnewuserdetails")
                .setDataField("compnewpassword")
                .setLeft(140)
                .setTop(641)
                .setWidth(260)
                .setTabindex(66)
                .setBorder(true)
                .setType("password")
                .setMaxlength(21)
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Button)
                .host(host,"btnNewDealingPattern")
                .setLeft(41)
                .setTop(701)
                .setWidth(180)
                .setTabindex(67)
                .setCaption("<b>$app.caption.userdealingpattern</b>")
                .onClick("_btnnewdealingpattern_onclick")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Button)
                .host(host,"btnSaveNewRole")
                .setDomId("btnSaveNewRole")
                .setLeft(231)
                .setTop(701)
                .setWidth(80)
                .setTabindex(68)
                .setCaption("$app.caption.save")
                .onClick("_btnsavenewrole_onclick")
            );
            
            host.pnlNewUserDetails.append((new linb.UI.Button)
                .host(host,"btnCancelNewRole")
                .setLeft(321)
                .setTop(701)
                .setWidth(80)
                .setTabindex(69)
                .setCaption("$app.caption.cancel")
                .onClick("_btncancelnewrole_onclick")
            );
            
            host.panelCompany.append((new linb.UI.Group)
                .host(host,"pnlEditUserDetails")
                .setLeft(471)
                .setTop(20)
                .setWidth(445)
                .setHeight(770)
                .setVisibility("hidden")
                .setCaption("$app.caption.userdetails")
                .setToggleBtn(false)
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"label164")
                .setLeft(0)
                .setTop(311)
                .setWidth(140)
                .setCaption("$app.caption.address3 :")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"label163")
                .setLeft(30)
                .setTop(281)
                .setWidth(110)
                .setCaption("$app.caption.address2 :")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"label248")
                .setLeft(40)
                .setTop(491)
                .setWidth(100)
                .setCaption("$app.caption.comments :")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"label249")
                .setLeft(40)
                .setTop(431)
                .setWidth(100)
                .setCaption("$app.caption.emailaddress :")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"label250")
                .setLeft(40)
                .setTop(341)
                .setWidth(100)
                .setCaption("$app.caption.telephonenumber :")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"label230")
                .setLeft(40)
                .setTop(110)
                .setWidth(100)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.category :")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"label571")
                .setLeft(40)
                .setTop(401)
                .setWidth(100)
                .setCaption("$app.caption.mobilenumber :")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"lbleditshortname")
                .setLeft(40)
                .setTop(55)
                .setWidth(100)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.shortname :")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"label248")
                .setLeft(30)
                .setTop(251)
                .setWidth(110)
                .setCaption("$app.caption.address1 :")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"label246")
                .setLeft(60)
                .setTop(656)
                .setWidth(80)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.password :")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"label247")
                .setLeft(40)
                .setTop(626)
                .setWidth(100)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.userid :")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"label165")
                .setLeft(40)
                .setTop(371)
                .setWidth(100)
                .setCaption("$app.caption.faxnumber :")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"label166")
                .setLeft(10)
                .setTop(461)
                .setWidth(130)
                .setCaption("$app.caption.mobileemailnumber :")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Button)
                .host(host,"btnEditRemove")
                .setTips("Remove Category")
                .setLeft(281)
                .setTop(221)
                .setCaption("$app.caption.remove")
                .onClick("_btneditremove_onclick")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"label160")
                .setLeft(235)
                .setWidth(150)
                .setTop(590)
                .setCaption("$app.caption.TOS_TITLE_USR_DTL")
                .setVAlign("middle")
            );

            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"labelCalendarHighlight")
                .setLeft(75)
                .setWidth(150)
                .setTop(590)
                .setCaption("$app.caption.calendarHighlight")
                .setVAlign("middle")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Button)
                .host(host,"downBtn")
                .setLeft(410)
                .setTop(165)
                .setWidth(20)
                .setHeight(30)
                .setCaption("↓")
                .setVAlign("center")
                .onClick("_downbtn_onclick")
            )
            
            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"label246")
                .setLeft(50)
                .setTop(85)
                .setWidth(90)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.role :")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.CheckBox)
                .host(host,"tosChk")
                .setDomId("tosChk")
                .setDataBinder("binduserdetails")
                .setDirtyMark(false)
                .setLeft(370)
                .setTop(590)
                .setWidth(40)
                .setCaption("")
                .setHAlign("right")
            );

            host.pnlEditUserDetails.append((new linb.UI.CheckBox)
                .host(host,"calendarHighlightChk")
                .setDomId("calendarHighlightChk")
                .setDataBinder("binduserdetails")
                .setDirtyMark(false)
                .setLeft(210)
                .setTop(590)
                .setDisabled(true)
                .setWidth(40)
                .setCaption("")
                .setHAlign("right")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Label)
                .host(host,"label245")
                .setLeft(20)
                .setTop(25)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.firstname :")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Button)
                .host(host,"upBtn")
                .setLeft(410)
                .setTop(125)
                .setWidth(20)
                .setHeight(30)
                .setCaption("↑")
                .setVAlign("center")
                .onClick("_upbtn_onclick")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditName")
                .setDomId("iptEditName")
                .setDataBinder("binduserdetails")
                .setDataField("compeditname")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(20)
                .setWidth(260)
                .setTabindex(31)
                .setBorder(true)
                .setMaxlength(64)
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditShortName")
                .setDomId("iptEditShortName")
                .setDataBinder("binduserdetails")
                .setDataField("compeditshortname")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(50)
                .setWidth(260)
                .setTabindex(32)
                .setBorder(true)
                .setMaxlength(5)
            );
            
            host.pnlEditUserDetails.append((new linb.UI.ComboInput)
                .host(host,"cbiEditRole")
                .setDomId("cbiEditRole")
                .setDataBinder("binduserdetails")
                .setDataField("compeditrole")
                .setDirtyMark(false)
                .setDisabled(true)
                .setLeft(140)
                .setTop(80)
                .setWidth(260)
                .setHeight(23)
                .setTabindex(33)
                .setBorder(true)
                .setCaption("")
                .onHotKeypress("_cbieditrole_onhotkeypress")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.List)
                .host(host,"lstEditCategory")
                .setDomId("lstEditCategory")
                .setDataBinder("binduserdetails")
                .setDataField("compeditcategory")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(110)
                .setWidth(260)
                .setHeight(101)
                .setTabindex(34)
                .setValue("")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Button)
                .host(host,"btnEditCategory")
                .setLeft(131)
                .setTop(221)
                .setWidth(130)
                .setTabindex(35)
                .setCaption("$app.caption.category")
                .onClick("_btneditcategory_onclick")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditAddress1")
                .setDomId("iptEditAddress1")
                .setDataBinder("binduserdetails")
                .setDataField("compeditaddress1")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(251)
                .setWidth(260)
                .setTabindex(36)
                .setBorder(true)
                .setMaxlength(100)
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditAddress2")
                .setDomId("iptEditAddress2")
                .setDataBinder("binduserdetails")
                .setDataField("compeditaddress2")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(281)
                .setWidth(260)
                .setTabindex(37)
                .setBorder(true)
                .setMaxlength(100)
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditAddress3")
                .setDomId("iptEditAddress3")
                .setDataBinder("binduserdetails")
                .setDataField("compeditaddress3")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(311)
                .setWidth(260)
                .setTabindex(38)
                .setBorder(true)
                .setMaxlength(100)
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditTelNo")
                .setDomId("iptEditTelNo")
                .setDataBinder("binduserdetails")
                .setDataField("compedittelno")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(341)
                .setWidth(260)
                .setTabindex(39)
                .setBorder(true)
                .setMask("11-1111-1111")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditFaxNo")
                .setDomId("iptEditFaxNo")
                .setDataBinder("binduserdetails")
                .setDataField("compeditfaxno")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(371)
                .setWidth(260)
                .setTabindex(40)
                .setBorder(true)
                .setMask("11-1111-1111")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditMobileNo")
                .setDomId("iptEditMobileNo")
                .setDataBinder("binduserdetails")
                .setDataField("compeditmobileno")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(401)
                .setWidth(260)
                .setTabindex(41)
                .setBorder(true)
                .setMask("111-1111-1111")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditEmailAdd")
                .setDomId("iptEditEmailAdd")
                .setDataBinder("binduserdetails")
                .setDataField("compeditemailaddress")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(431)
                .setWidth(260)
                .setTabindex(42)
                .setBorder(true)
                .setValueFormat("^[\\w\\.=-]+@[\\w\\.-]+\\.[\\w\\.-]{2,4}$")
                .setMaxlength(50)
                .onBlur("_ipteditemailadd_onblur")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditMobEmailAdd")
                .setDomId("iptEditMobEmailAdd")
                .setDataBinder("binduserdetails")
                .setDataField("compeditmobemailaddress")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(461)
                .setWidth(260)
                .setTabindex(43)
                .setBorder(true)
                .setMaxlength(50)
                .onBlur("_ipteditmobemailadd_onblur")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.TextEditor)
                .host(host,"txtEditorEditComments")
                .setDomId("txtEditorEditComments")
                .setDataBinder("binduserdetails")
                .setDataField("compeditcomments")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(491)
                .setWidth(260)
                .setHeight(90)
                .setTabindex(44)
                .setBorder(true)
                .setValue("")
                .onHotKeypress("_txteditoreditcomments_onhotkeypress")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.CheckBox)
                .host(host,"chkEditBMS")
                .setDomId("chkEditBMS")
                .setDataBinder("binduserdetails")
                .setDataField("compeditbms")
                .setDisabled(true)
                .setLeft(220)
                .setTop(590)
                .setWidth(171)
                .setTabindex(45)
                .setVisibility("hidden")
                .setCaption("$app.caption.userbms")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.CheckBox)
                .host(host,"chkEditUnfinalizeReceived")
                .setDomId("chkEditUnfinalizeReceived")
                .setDataBinder("binduserdetails")
                .setDataField("compeditfinrec")
                .setDirtyMark(false)
                .setLeft(130)
                .setTop(225)
                .setWidth(271)
                .setTabindex(45)
                .setVisibility("hidden")
                .setCaption("$app.caption.unfinalizedReceived")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditUserName")
                .setDomId("iptEditUserName")
                .setDataBinder("binduserdetails")
                .setDataField("compedituserid")
                .setDirtyMark(false)
                .setDisabled(true)
                .setLeft(140)
                .setTop(621)
                .setWidth(260)
                .setTabindex(46)
                .setBorder(true)
                .setMaxlength(21)
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Input)
                .host(host,"iptEditPassword")
                .setDomId("iptEditPassword")
                .setDataBinder("binduserdetails")
                .setDataField("compeditpassword")
                .setDirtyMark(false)
                .setLeft(140)
                .setTop(651)
                .setWidth(260)
                .setTabindex(47)
                .setBorder(true)
                .setType("password")
                .setMaxlength(21)
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Button)
                .host(host,"btnEditDealingPattern")
                .setDomId("btnEditDealingPattern")
                .setLeft(31)
                .setTop(701)
                .setWidth(170)
                .setTabindex(48)
                .setCaption("<b>$app.caption.userdealingpattern</b>")
                .onClick("_btneditdealingpattern_onclick")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Button)
                .host(host,"btnSaveEditRole")
                .setDomId("btnSaveEditRole")
                .setLeft(221)
                .setTop(701)
                .setWidth(80)
                .setTabindex(49)
                .setCaption("$app.caption.save")
                .onClick("_btnsaveeditrole_onclick")
            );
            
            host.pnlEditUserDetails.append((new linb.UI.Button)
                .host(host,"btnCancelEditRole")
                .setLeft(321)
                .setTop(701)
                .setWidth(80)
                .setTabindex(50)
                .setCaption("$app.caption.cancel")
                .onClick("_btncanceleditrole_onclick")
            );
            
            host.panelCompany.append((new linb.UI.Dialog)
                .host(host,"dialogSearchCompany")
                .setLeft(151)
                .setTop(51)
                .setHeight(310)
                .setZIndex(2)
                .setVisibility("hidden")
                .setResizer(false)
                .setCaption("$app.caption.search")
                .setMovable(false)
                .setMinBtn(false)
                .setMaxBtn(false)
                .setCloseBtn(false)
                .setPinBtn(false)
            );
            
            host.dialogSearchCompany.append((new linb.UI.Group)
                .host(host,"group118")
                .setLeft(10)
                .setTop(10)
                .setWidth(270)
                .setHeight(250)
                .setCaption("$app.caption.searchcompany")
                .setToggleBtn(false)
            );
            
            host.group118.append((new linb.UI.Input)
                .host(host,"iptDlgSearchCompName")
                .setLeft(10)
                .setTop(10)
                .setWidth(250)
                .setHeight(23)
                .setZIndex(1002)
                .setMaxlength(50)
                .onHotKeyup("_iptdlgsearchcompname_onhotkeyup")
                .onHotKeypress("_iptdlgsearchcompname_onhotkeypress")
                .onHotKeydown("_iptdlgsearchcompname_onhotkeydown")
                .setCustomStyle({"KEY":"background:#transparent;border: 1px"})
            );
            
            host.group118.append((new linb.UI.TreeGrid)
                .host(host,"tgSearchCompanyName")
                .setDomId("companySearchResultTreeGrid")
                .setDock("none")
                .setLeft(10)
                .setTop(34)
                .setWidth(250)
                .setHeight(160)
                .setAltRowsBg(true)
                .setRowHandler(false)
                .setHeader([{"id":"col1", "width":245, "type":"label", "caption":"$app.caption.companyname", "headerStyle":"font-weight:bold"}])
                .onRowSelected("_tgsearchcompanyname_onrowselected")
                .onDblclickRow("_tgsearchcompanyname_ondblclickrow")
                .setCustomStyle({"KEY":"cursor:pointer"})
            );
            
            host.group118.append((new linb.UI.Button)
                .host(host,"btnSearchCompOk")
                .setDomId("popupSearchButton")
                .setDisabled(true)
                .setLeft(100)
                .setTop(205)
                .setWidth(70)
                .setCaption("$app.caption.search")
                .onClick("_btnsearchcompok_onclick")
            );
            
            host.group118.append((new linb.UI.Button)
                .host(host,"btnSearchCompCancel")
                .setLeft(180)
                .setTop(205)
                .setWidth(70)
                .setCaption("$app.caption.cancel")
                .onClick("_btnsearchcompcancel_onclick")
            );
            
            host.group118.append((new linb.UI.Input)
                .host(host,"lblSearchUserId")
                .setDisabled(true)
                .setLeft(200)
                .setTop(10)
                .setWidth(60)
                .setHeight(23)
                .setMaxlength(21)
            );
            
            host.panelCompany.append((new linb.UI.Dialog)
                .host(host,"panelDealingPattern")
                .setLeft(16)
                .setTop(61)
                .setWidth(711)
                .setHeight(470)
                .setZIndex(4)
                .setVisibility("hidden")
                .setResizer(false)
                .setCaption("$app.caption.companydealingpattern")
                .setMovable(false)
                .setMinBtn(false)
                .setMaxBtn(false)
                .setCloseBtn(false)
                .setPinBtn(false)
                .setCustomStyle({"PANEL":"overflow:auto"})
            );
            
            host.panelDealingPattern.append((new linb.UI.Group)
                .host(host,"grpDealingPattern")
                .setLeft(20)
                .setTop(10)
                .setWidth(650)
                .setHeight(400)
                .setCaption("")
                .setToggleBtn(false)
            );
            
            host.grpDealingPattern.append((new linb.UI.List)
                .host(host,"lstAvailableCompDealingPattern")
                .setLeft(40)
                .setTop(70)
                .setWidth(240)
                .setHeight(260)
                .setSelMode("multi")
                .setValue("")
            );
            
            host.grpDealingPattern.append((new linb.UI.List)
                .host(host,"lstSelectedCompDealingPattern")
                .setLeft(380)
                .setTop(70)
                .setWidth(240)
                .setHeight(260)
                .onItemSelected("_lstselectedcompdealingpattern_onitemselected")
            );
            
            host.grpDealingPattern.append((new linb.UI.Button)
                .host(host,"btnTransferAvailableCompanies")
                .setLeft(290)
                .setTop(160)
                .setWidth(80)
                .setCaption(">>>")
                .onClick("_btntransferavailablecompanies_onclick")
            );
            
            host.grpDealingPattern.append((new linb.UI.Button)
                .host(host,"btnCompDealingPatternCancel")
                .setLeft(550)
                .setTop(350)
                .setWidth(70)
                .setCaption("$app.caption.cancel")
                .onClick("_btncompdealingpatterncancel_onclick")
            );
            
            host.grpDealingPattern.append((new linb.UI.Label)
                .host(host,"label216")
                .setLeft(40)
                .setTop(50)
                .setCaption("<b>$app.caption.availablecompany</b>")
                .setHAlign("left")
            );
            
            host.grpDealingPattern.append((new linb.UI.Label)
                .host(host,"label217")
                .setLeft(380)
                .setTop(50)
                .setWidth(180)
                .setCaption("<b>$app.caption.selectedcompany</b>")
                .setHAlign("left")
            );
            
            host.grpDealingPattern.append((new linb.UI.Button)
                .host(host,"btnSearchCompAvailable")
                .setLeft(210)
                .setTop(11)
                .setWidth(70)
                .setHeight(21)
                .setCaption("$app.caption.search")
                .onClick("_btnsearchcompavailable_onclick")
            );
            
            host.grpDealingPattern.append((new linb.UI.Button)
                .host(host,"btnCompDealingPatternOk")
                .setLeft(470)
                .setTop(350)
                .setWidth(70)
                .setCaption("$app.caption.save")
                .onClick("_btncompdealingpatternok_onclick")
            );
            
            host.grpDealingPattern.append((new linb.UI.Button)
                .host(host,"btnCompDealingPatternRemove")
                .setLeft(390)
                .setTop(350)
                .setWidth(70)
                .setCaption("$app.caption.remove")
                .onClick("_btncompdealingpatternremove_onclick")
            );
            
            host.grpDealingPattern.append((new linb.UI.Input)
                .host(host,"iptSearchCompDealingPattern")
                .setLeft(40)
                .setTop(10)
                .setWidth(170)
                .setHeight(23)
                .setBorder(true)
                .onHotKeypress("_iptsearchcompdealingpattern_onhotkeypress")
            );
            
            host.panelCompany.append((new linb.UI.Dialog)
                .host(host,"pnlUserDealPattern")
                .setLeft(151)
                .setTop(181)
                .setWidth(700)
                .setHeight(520)
                .setZIndex(1004)
                .setVisibility("hidden")
                .setResizer(false)
                .setCaption("$app.caption.userdealingpattern")
                .setMovable(false)
                .setMinBtn(false)
                .setMaxBtn(false)
                .setCloseBtn(false)
                .setPinBtn(false)
                .setCustomStyle({"PANEL":"overflow:auto"})
            );
            
            host.pnlUserDealPattern.append((new linb.UI.Group)
                .host(host,"grpUserDealingPattern")
                .setLeft(20)
                .setTop(10)
                .setWidth(650)
                .setHeight(460)
                .setCaption("")
                .setToggleBtn(false)
            );
            
            host.grpUserDealingPattern.append((new linb.UI.List)
                .host(host,"lstAvailableUDP")
                .setDirtyMark(false)
                .setLeft(30)
                .setTop(80)
                .setWidth(240)
                .setHeight(300)
                .setSelMode("multi")
                .setValue("")
            );
            
            host.grpUserDealingPattern.append((new linb.UI.Button)
                .host(host,"btnTransferUDP")
                .setLeft(290)
                .setTop(190)
                .setWidth(80)
                .setCaption(">>>")
                .onClick("_btntransferudp_onclick")
            );
            
            host.grpUserDealingPattern.append((new linb.UI.List)
                .host(host,"lstSelectedUDP")
                .setDirtyMark(false)
                .setLeft(390)
                .setTop(80)
                .setWidth(240)
                .setHeight(300)
                .setValue("")
                .onItemSelected("_lstselectedudp_onitemselected")
            );
            
            host.grpUserDealingPattern.append((new linb.UI.Label)
                .host(host,"label72")
                .setLeft(390)
                .setTop(0)
                .setCaption("<b><u>$app.caption.expirydate :</b></u>")
                .setHAlign("left")
            );
            
            host.grpUserDealingPattern.append((new linb.UI.Label)
                .host(host,"label73")
                .setLeft(370)
                .setTop(30)
                .setWidth(60)
                .setCaption("<i>$app.caption.expiryfrom :</i>")
                .setHAlign("center")
            );
            
            host.grpUserDealingPattern.append((new linb.UI.Label)
                .host(host,"label74")
                .setLeft(500)
                .setTop(30)
                .setWidth(60)
                .setCaption("<i>$app.caption.expiryto :</i>")
                .setHAlign("center")
            );
            
            host.grpUserDealingPattern.append((new linb.UI.Button)
                .host(host,"btnSaveUDP")
                .setLeft(480)
                .setTop(400)
                .setWidth(70)
                .setCaption("$app.caption.save")
                .onClick("_btnsaveudp_onclick")
            );
            
            host.grpUserDealingPattern.append((new linb.UI.Button)
                .host(host,"btnCancelUDP")
                .setLeft(560)
                .setTop(400)
                .setWidth(70)
                .setCaption("$app.caption.cancel")
                .onClick("_bntuserdealingcancel_onclick")
            );
            
            host.grpUserDealingPattern.append((new linb.UI.Input)
                .host(host,"iptSearchUserNameUDP")
                .setDomId("iptSearchUserNameUDP")
                .setLeft(30)
                .setTop(30)
                .setWidth(160)
                .setHeight(24)
                .setBorder(true)
                .onHotKeypress("_iptsearchusernameudp_onhotkeypress")
            );
            
            host.grpUserDealingPattern.append((new linb.UI.Button)
                .host(host,"btnSearchUDP")
                .setLeft(190)
                .setTop(31)
                .setWidth(80)
                .setCaption("$app.caption.search")
                .onClick("_btnsearchudp_onclick")
            );
            
            host.grpUserDealingPattern.append((new linb.UI.Dialog)
                .host(host,"dlgUDPSetDate")
                .setLeft(210)
                .setTop(40)
                .setWidth(250)
                .setHeight(280)
                .setVisibility("hidden")
                .setResizer(false)
                .setCaption("$app.caption.expirydate")
                .setMovable(false)
                .setMinBtn(false)
                .setMaxBtn(false)
                .setCloseBtn(false)
                .setPinBtn(false)
            );
            
            host.dlgUDPSetDate.append((new linb.UI.Group)
                .host(host,"group11")
                .setLeft(10)
                .setTop(10)
                .setWidth(220)
                .setHeight(220)
                .setCaption("")
                .setToggleBtn(false)
            );
            
            host.group11.append((new linb.UI.Label)
                .host(host,"label141")
                .setLeft(10)
                .setTop(40)
                .setWidth(50)
                .setCaption("$app.caption.expiryto :")
                .setHAlign("left")
            );
            
            host.group11.append((new linb.UI.List)
                .host(host,"lstDESUserId")
                .setLeft(10)
                .setTop(90)
                .setWidth(40)
                .setHeight(70)
                .setVisibility("hidden")
            );
            
            host.group11.append((new linb.UI.List)
                .host(host,"lstDESUser")
                .setLeft(70)
                .setTop(70)
                .setWidth(140)
                .setHeight(90)
                .setSelMode("none")
            );
            
            host.group11.append((new linb.UI.Label)
                .host(host,"label142")
                .setLeft(10)
                .setTop(70)
                .setWidth(60)
                .setCaption("$app.caption.users :")
                .setHAlign("left")
            );
            
            host.group11.append((new linb.UI.Button)
                .host(host,"btnDESSet")
                .setLeft(50)
                .setTop(170)
                .setWidth(70)
                .setCaption("$app.caption.set")
                .onClick("_btndesset_onclick")
            );
            
            host.group11.append((new linb.UI.Button)
                .host(host,"btnDESCancel")
                .setLeft(130)
                .setTop(170)
                .setWidth(60)
                .setCaption("$app.caption.cancel")
                .onClick("_btndescancel_onclick")
            );
            
            host.group11.append((new linb.UI.Label)
                .host(host,"label140")
                .setLeft(10)
                .setTop(10)
                .setWidth(60)
                .setCaption("$app.caption.expiryfrom :")
                .setHAlign("left")
            );
            
            host.group11.append((new linb.UI.ComboInput)
                .host(host,"cbiDESDateFrom")
                .setDirtyMark(false)
                .setLeft(70)
                .setTop(10)
                .setWidth(140)
                .setZIndex(1002)
                .setType("datepicker")
                .setCaption("")
                .afterUIValueSet("_cbidesdatefrom_afteruivalueset")
            );
            
            host.group11.append((new linb.UI.ComboInput)
                .host(host,"cbiDESDateTo")
                .setDirtyMark(false)
                .setLeft(70)
                .setTop(40)
                .setWidth(140)
                .setZIndex(1004)
                .setType("datepicker")
                .setCaption("")
                .afterUIValueSet("_cbidesdateto_afteruivalueset")
            );
            
            host.group11.append((new linb.UI.Input)
                .host(host,"iptDESFrom")
                .setLeft(70)
                .setTop(10)
                .setWidth(125)
                .setZIndex(1006)
                .setBorder(true)
                .onHotKeypress("_iptdesfrom_onhotkeypress")
            );
            
            host.group11.append((new linb.UI.Input)
                .host(host,"iptDESTo")
                .setLeft(70)
                .setTop(40)
                .setWidth(125)
                .setZIndex(1008)
                .setBorder(true)
                .onHotKeypress("_iptdesto_onhotkeypress")
            );
            
            host.group11.append((new linb.UI.Label)
                .host(host,"lblDESEditudp")
                .setLeft(10)
                .setTop(120)
                .setWidth(30)
                .setVisibility("hidden")
                .setCaption("")
            );
            
            host.grpUserDealingPattern.append((new linb.UI.Label)
                .host(host,"label143")
                .setLeft(30)
                .setTop(60)
                .setWidth(240)
                .setCaption("$app.caption.availableuserid")
                .setHAlign("left")
            );
            
            host.grpUserDealingPattern.append((new linb.UI.Label)
                .host(host,"label144")
                .setLeft(390)
                .setTop(60)
                .setWidth(240)
                .setCaption("$app.caption.selecteduserid")
                .setHAlign("left")
            );
            
            host.grpUserDealingPattern.append((new linb.UI.Input)
                .host(host,"iptExpiryFromUDP")
                .setDirtyMark(false)
                .setLeft(430)
                .setTop(30)
                .setWidth(70)
                .setBorder(true)
                .onHotKeypress("_iptexpiryfromudp_onhotkeypress")
            );
            
            host.grpUserDealingPattern.append((new linb.UI.Input)
                .host(host,"iptExpiryToUDP")
                .setDirtyMark(false)
                .setLeft(560)
                .setTop(30)
                .setWidth(70)
                .setBorder(true)
                .onChange("_iptexpirytoudp_onchange")
            );
            
            host.grpUserDealingPattern.append((new linb.UI.Label)
                .host(host,"lblSaveUDPWhenRemove")
                .setLeft(160)
                .setTop(380)
                .setWidth(110)
                .setVisibility("hidden")
                .setCaption("")
            );
            
            host.grpUserDealingPattern.append((new linb.UI.List)
                .host(host,"lstRemovedUDP")
                .setDirtyMark(false)
                .setLeft(390)
                .setTop(80)
                .setWidth(240)
                .setHeight(300)
                .setVisibility("hidden")
                .setValue("")
            );
            
            host.panelCompany.append((new linb.UI.Dialog)
                .host(host,"dialogNewUserCategory")
                .setLeft(361)
                .setTop(151)
                .setWidth(250)
                .setHeight(220)
                .setZIndex(4)
                .setVisibility("hidden")
                .setResizer(false)
                .setCaption("$app.caption.category")
                .setMovable(false)
                .setMinBtn(false)
                .setMaxBtn(false)
                .setCloseBtn(false)
                .setPinBtn(false)
            );
            
            host.dialogNewUserCategory.append((new linb.UI.Label)
                .host(host,"label1147")
                .setLeft(10)
                .setTop(10)
                .setWidth(220)
                .setCaption("<b>$app.caption.listofcategories :</b>")
                .setHAlign("left")
            );
            
            host.dialogNewUserCategory.append((new linb.UI.List)
                .host(host,"lstCategoryList")
                .setLeft(10)
                .setTop(30)
                .setWidth(220)
                .setHeight(110)
                .setValue("")
            );
            
            host.dialogNewUserCategory.append((new linb.UI.Button)
                .host(host,"btnSelectListOfCategory")
                .setLeft(80)
                .setTop(150)
                .setWidth(70)
                .setCaption("$app.caption.select")
                .onClick("_btnselectlistofcategory_onclick")
            );
            
            host.dialogNewUserCategory.append((new linb.UI.Button)
                .host(host,"btnCloseCategory")
                .setLeft(160)
                .setTop(150)
                .setWidth(70)
                .setCaption("$app.caption.close")
                .onClick("_btnclosecategory_onclick")
            );
            
            host.panelCompany.append((new linb.UI.Group)
                .host(host,"pnlAdminNewUserDetails")
                .setLeft(471)
                .setTop(20)
                .setWidth(430)
                .setHeight(770)
                .setZIndex(4)
                .setVisibility("hidden")
                .setCaption("$app.caption.adminuserdetails")
                .setToggleBtn(false)
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Label)
                .host(host,"label1425")
                .setLeft(10)
                .setTop(436)
                .setWidth(130)
                .setCaption("$app.caption.mobileemailnumber :")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Label)
                .host(host,"label1424")
                .setLeft(30)
                .setTop(341)
                .setWidth(110)
                .setCaption("$app.caption.faxnumber :")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Label)
                .host(host,"label1413")
                .setLeft(30)
                .setTop(55)
                .setWidth(110)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.shortname :")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Label)
                .host(host,"label1414")
                .setLeft(30)
                .setTop(226)
                .setWidth(110)
                .setCaption("$app.caption.address1 :")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Label)
                .host(host,"label1415")
                .setLeft(40)
                .setTop(371)
                .setWidth(100)
                .setCaption("$app.caption.mobilenumber :")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Label)
                .host(host,"label1416")
                .setLeft(30)
                .setTop(401)
                .setWidth(110)
                .setCaption("$app.caption.emailaddress :")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Label)
                .host(host,"label1417")
                .setLeft(40)
                .setTop(461)
                .setWidth(100)
                .setCaption("$app.caption.comments :")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Label)
                .host(host,"label1418")
                .setLeft(20)
                .setTop(616)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.userid :")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Label)
                .host(host,"label1419")
                .setLeft(20)
                .setTop(646)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.password :")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Label)
                .host(host,"label1412")
                .setLeft(50)
                .setTop(85)
                .setWidth(90)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.role :")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Label)
                .host(host,"label1411")
                .setLeft(20)
                .setTop(25)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.firstname :")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Label)
                .host(host,"label1420")
                .setLeft(40)
                .setTop(110)
                .setWidth(100)
                .setCaption("<font color='red'>*</font>&nbsp;&nbsp;$app.caption.category :")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Label)
                .host(host,"label1421")
                .setLeft(30)
                .setTop(311)
                .setWidth(110)
                .setCaption("$app.caption.telephonenumber :")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Label)
                .host(host,"label1422")
                .setLeft(20)
                .setTop(256)
                .setCaption("$app.caption.address2 :")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Label)
                .host(host,"label1423")
                .setLeft(0)
                .setTop(281)
                .setWidth(140)
                .setCaption("$app.caption.address3 :")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.List)
                .host(host,"lstAdminCategory")
                .setDataBinder("bindAdminNewUsers")
                .setDataField("admnewcategory")
                .setDisabled(true)
                .setLeft(140)
                .setTop(110)
                .setWidth(260)
                .setHeight(101)
                .setTabindex(54)
                .setSelMode("multi")
                .setValue("")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.CheckBox)
                .host(host,"chkAdmNewBMS")
                .setDataBinder("bindAdminNewUsers")
                .setDataField("admnewuserbms")
                .setDisabled(true)
                .setLeft(120)
                .setTop(561)
                .setWidth(191)
                .setTabindex(64)
                .setVisibility("hidden")
                .setCaption("$app.caption.userbms")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptAdminFirstname")
                .setDomId("iptAdminFirstname")
                .setDataBinder("bindAdminNewUsers")
                .setDataField("adminfirstname")
                .setLeft(140)
                .setTop(21)
                .setWidth(260)
                .setTabindex(301)
                .setBorder(true)
                .setMaxlength(64)
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptAdminShortname")
                .setDomId("iptAdminShortname")
                .setDataBinder("bindAdminNewUsers")
                .setDataField("admnewshortname")
                .setLeft(140)
                .setTop(51)
                .setWidth(260)
                .setTabindex(302)
                .setBorder(true)
                .setMaxlength(5)
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.ComboInput)
                .host(host,"cbiAdminRole")
                .setDomId("cbiAdminRole")
                .setLeft(140)
                .setTop(81)
                .setWidth(260)
                .setHeight(24)
                .setTabindex(303)
                .setBorder(true)
                .setItems([{"id":"5", "caption":"Administrator"}])
                .onHotKeypress("_cbiadminrole_onhotkeypress")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptAdminAddress1")
                .setDataBinder("bindAdminNewUsers")
                .setDataField("admnewaddress1")
                .setLeft(140)
                .setTop(221)
                .setWidth(260)
                .setTabindex(304)
                .setBorder(true)
                .setMaxlength(100)
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptAdminAddress2")
                .setDataBinder("bindAdminNewUsers")
                .setDataField("admnewaddress2")
                .setLeft(140)
                .setTop(251)
                .setWidth(260)
                .setTabindex(305)
                .setBorder(true)
                .setMaxlength(100)
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptAdminAddress3")
                .setDataBinder("bindAdminNewUsers")
                .setDataField("admnewaddress3")
                .setLeft(140)
                .setTop(281)
                .setWidth(260)
                .setTabindex(306)
                .setBorder(true)
                .setMaxlength(100)
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptAdminTelNo")
                .setDataBinder("bindAdminNewUsers")
                .setDataField("admnewtelno")
                .setLeft(140)
                .setTop(311)
                .setWidth(260)
                .setTabindex(307)
                .setBorder(true)
                .setMask("11-1111-1111")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptAdminFaxNo")
                .setDataBinder("bindAdminNewUsers")
                .setDataField("admnewfaxno")
                .setLeft(140)
                .setTop(341)
                .setWidth(260)
                .setTabindex(308)
                .setBorder(true)
                .setMask("11-1111-1111")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptAdminMobileNo")
                .setDataBinder("bindAdminNewUsers")
                .setDataField("admnewmobileno")
                .setLeft(140)
                .setTop(371)
                .setWidth(260)
                .setTabindex(309)
                .setBorder(true)
                .setMask("111-1111-1111")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptAdminEmailAdd")
                .setDataBinder("bindAdminNewUsers")
                .setDataField("admnewemailadd")
                .setLeft(140)
                .setTop(401)
                .setWidth(260)
                .setTabindex(340)
                .setBorder(true)
                .setMaxlength(50)
                .onBlur("_iptadminemailadd_onblur")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptAdminMobEmailAdd")
                .setDataBinder("bindAdminNewUsers")
                .setDataField("admnewmobemailadd")
                .setLeft(140)
                .setTop(431)
                .setWidth(260)
                .setTabindex(341)
                .setBorder(true)
                .setMaxlength(50)
                .onBlur("_iptadminmobemailadd_onblur")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.TextEditor)
                .host(host,"txtAdminComments")
                .setDataBinder("bindAdminNewUsers")
                .setDataField("admnewcomments")
                .setLeft(140)
                .setTop(461)
                .setWidth(260)
                .setHeight(90)
                .setTabindex(342)
                .setBorder(true)
                .setValue("")
                .onHotKeypress("_txtadmincomments_onhotkeypress")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptAdminUsername")
                .setDomId("iptAdminUsername")
                .setDataBinder("bindAdminNewUsers")
                .setDataField("admnewusername")
                .setLeft(140)
                .setTop(611)
                .setWidth(260)
                .setTabindex(343)
                .setBorder(true)
                .setMaxlength(21)
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Input)
                .host(host,"iptAdminPassword")
                .setDomId("iptAdminPassword")
                .setDataBinder("bindAdminNewUsers")
                .setDataField("admnewpassword")
                .setLeft(140)
                .setTop(641)
                .setWidth(260)
                .setTabindex(344)
                .setBorder(true)
                .setType("password")
                .setMaxlength(21)
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Button)
                .host(host,"btnAdmNewSave")
                .setDomId("btnAdmNewSave")
                .setLeft(211)
                .setTop(701)
                .setWidth(80)
                .setTabindex(345)
                .setCaption("$app.caption.save")
                .onClick("_btnadmsave_onclick")
            );
            
            host.pnlAdminNewUserDetails.append((new linb.UI.Button)
                .host(host,"btnAdmNewCancel")
                .setLeft(311)
                .setTop(701)
                .setWidth(80)
                .setTabindex(346)
                .setCaption("$app.caption.cancel")
                .onClick("_btnadmnewcancel_onclick")
            );
            
            host.panelCompany.append((new linb.UI.Group)
                .host(host,"usrDwnldGRP")
                .setLeft(21)
                .setTop(80)
                .setWidth(431)
                .setHeight(50)
                .setCaption("")
                .setToggleBtn(false)
                .setToggle(false)
            );
            
            host.usrDwnldGRP.append((new linb.UI.Label)
                .host(host,"lblUsrDwnld")
                .setDomId("lblUsrDwnld")
                .setLeft(30)
                .setTop(5)
                .setWidth(260)
                .setCaption("<b>$app.caption.LBL_USERDOWNLOAD :</b>")
                .setHAlign("left")
                .setVAlign("middle")
            );
            
            host.usrDwnldGRP.append((new linb.UI.Button)
                .host(host,"tosUsrDwnld")
                .setDomId("tosUsrDwnld")
                .setLeft(290)
                .setTop(5)
                .setCaption("$app.caption.BTN_USERDOWNLOAD")
                .onClick("_tosUsrDwnld_onclick")

            );
            
            host.panelCompany.append((new linb.UI.Group)
                .host(host,"group3")
                .setLeft(21)
                .setTop(11)
                .setWidth(431)
                .setHeight(50)
                .setTabindex(1000)
                .setCaption("")
                .setToggleBtn(false)
            );
            
            host.group3.append((new linb.UI.Input)
                .host(host,"iptSearchCompName")
                .setDomId("iptSearchCompName")
                .setDataBinder("bindcompany")
                .setDataField("searchcompname")
                .setLeft(130)
                .setTop(0)
                .setWidth(160)
                .setHeight(24)
                .setTabindex(0)
                .setBorder(true)
                .setMaxlength(50)
                .onChange("_iptsearchcompname_onchange")
            );
            
            host.group3.append((new linb.UI.Label)
                .host(host,"label42")
                .setLeft(10)
                .setTop(5)
                .setWidth(110)
                .setTabindex(1001)
                .setCaption("<b>$app.caption.companyname :</b>")
            );
            
            host.group3.append((new linb.UI.Button)
                .host(host,"btnCompanySearch")
                .setDomId("mainSearchButton")
                .setLeft(290)
                .setTop(1)
                .setWidth(60)
                .setTabindex(1003)
                .setCaption("$app.caption.search")
                .onClick("_btncompanysearch_onclick")
            );
            
            host.group3.append((new linb.UI.Button)
                .host(host,"btnAddNewCompany")
                .setLeft(351)
                .setTop(1)
                .setWidth(60)
                .setTabindex(1004)
                .setCaption("$app.caption.addnewcompany")
                .onClick("_btnaddnewcompany_onclick")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        },        
        _searchcompanyname:function(compname) {
            var ns = this;
            var url = "/eON/autosearchnames.json";
            var obj = new Object();
            obj.companyName=compname;
            linb.Ajax(url,obj,
                function(response){
                    validateResponse(response);
                    var o = _.unserialize(response);
                    SPA.tgSearchCompanyName.setRows(o.searcResults);
            },
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
        },        
        _searchnamecdpattern:function(compname) {
            if(compname.length>0) {
                this.iptSearchCompName.setValue("");
                var ns = this, items=[];
                var url = "/eON/searchcdpattern.json";
                var o=new Object();
                o.companyType=this.cbiCompanyType.getUIValue();
                o.companyId=this.lblCompanyId.getCaption();
                o.companyName=compname;
                linb.Ajax(url,o,
                function(response){
                validateResponse(response);
                var obj = _.unserialize(response);
                var items=[];
                for(var i=0; i<obj.result.length; i++){
                items.push({"id":obj.result[i].companyId, "caption":obj.result[i].companyName});
                }
                SPA.lstAvailableCompDealingPattern.setItems(items);
                },
                function(msg){
                linb.message("失敗： " + msg);
                },
                null,
                {method:'POST',retry:0}).start();
            }
            else {
                this._loadCDP(2);
            }
        },        
        _showUsers:function() {
            var pge=this;
            var url = "/eON/getallusers.json";
            var param = new Object();
            param.companyId=this.lblCompanyId.getCaption();
            linb.Ajax(url,param,
            function(response){
                validateResponse(response);
                SPA._fillusers(response);
            },
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
        },        
        _fillusers:function(resp) {
            var obj=_.unserialize(resp);
            this.tgUsersList.setRows(obj.users);
        },        
        _btncompanysearch_onclick:function (profile, e, src, value) {
            var newValue = this.iptSearchCompName.getUIValue();
            if (this.iptSearchCompName.getUIValue() == "") {
                var ns = this, items=[];
                var url = "/eON/getCompanyNames.json";
                var o=new Object();
                o.isDealingPattern = 0;




                linb.Ajax(url,o,
                function(response){
                        validateResponse(response);
                        var obj = _.unserialize(response);
                        SPA.tgSearchCompanyName.setRows(obj.companyNames);
                },
                function(msg){
                    linb.message("失敗： " + msg);
                },
                null,
                {method:'POST',retry:0}).start();
                this.iptSearchCompName.setValue("");
                this.dialogSearchCompany.show(this.panelCompany.reBoxing(),true,150,80);
                setFocus(this.iptDlgSearchCompName);
                this.iptDlgSearchCompName.setValue("");
                this.btnSearchCompOk.setDisabled(true);
                this.tgSearchCompanyName.refresh();
            } else {
                    var ns = this;
                    var url = "/eON/autosearchnames.json";
                    var obj = new Object();
                    obj.companyName=newValue;
                    linb.Ajax(url,obj,
                        function(response) {
                            validateResponse(response);
                            var o = _.unserialize(response);
                            //SPA.dialogSearchCompany.reBoxing().show();
                            SPA.tgSearchCompanyName.setRows(o.searcResults);
                    },
                    function(msg) {
                        linb.message("失敗： " + msg);
                    },
                    null,
                    {method:'POST',retry:0}).start();
                    this.dialogSearchCompany.show(this.panelCompany.reBoxing(),true,150,80);
                    setFocus(this.iptDlgSearchCompName);
                    this.tgSearchCompanyName.refresh();
                    SPA.iptDlgSearchCompName.setValue(newValue);
                    //this._search_companyname(newValue);
            }
        },        
        _btnsearchcompok_onclick:function (profile, e, src, value) {
            this._search_companyname(this.lblSearchUserId.getUIValue());
            this._hidePanelDetails('search');
            this.dialogSearchCompany.hide();
            this.btnAddCompUser.setDisabled(false);
            this.btnAdministrator.setDisabled(false);
        },        
        _search_companyname:function(compname) {
            var ns = this;
            var url = "/eON/companyinfo.json";
            var obj = new Object();
            obj.companyId=compname;
            linb.Ajax(url,obj,
                function(response){
                validateResponse(response);
                SPA.btnDealingPattern.setDisabled(false);
                SPA._fillsearchedcompany(response);
            },
            function(msg){
                    linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
            this.dialogSearchCompany.hide();
        },        
        _fillsearchedcompany:function(resp) {
            var obj = _.unserialize(resp);
            var objlength = 0;
            objlength = obj.companyInfo.companyName;
            this.bindcompany.resetValue("");
            SPA.lblCompanyId.setCaption(obj.companyInfo.companyId);
            SPA.iptCompName.setValue(obj.companyInfo.companyName);
            SPA.iptShortName.setValue(obj.companyInfo.shortName);
            SPA.cbiCompanyType.setValue(obj.companyInfo.companyType.description);
            this.cbiCompanyType.setDisabled(true);
            SPA.iptContactPerson.setValue(obj.companyInfo.contactPerson);
            SPA.iptAddress1.setValue(obj.companyInfo.address1);
            SPA.iptAddress2.setValue(obj.companyInfo.address2);
            SPA.iptAddress3.setValue(obj.companyInfo.address3);
            SPA.iptTelNo.setValue(obj.companyInfo.telNumber);
            SPA.iptFaxNo.setValue(obj.companyInfo.faxNumber);
            SPA.iptEmailAdd.setValue(obj.companyInfo.emailAdd);
            SPA.chkSOX.setValue(obj.companyInfo.soxFlag==0?false:true);
            SPA.txtEditorComment.setValue(obj.companyInfo.comments);
            SPA.tgUsersList.setRows(obj.userslist);
            this.grpCompanyDetails.reBoxing().show();
            this.grpCompanyDetails.setToggle(true);
            SPA.clearFailedCompanyBackgroundColor();
            g_saveMode = "update";
        },        
        _btnsearchcompcancel_onclick:function (profile, e, src, value) {
            this.dialogSearchCompany.hide();
        },        
        _tguserslist_onclickcell:function (profile, cell, e, src) {
            var ns = this;
            var url = "/eON/usersinfo.json";
            var obj = new Object();
            var _cellid = cell._row.cells[3].value;
            obj.userId = _cellid;
            linb.Ajax(url,obj,
                function(response){
                validateResponse(response);
                SPA._fillsearchusersinfo(response);
            },
            function(msg){
                    linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
            this.lblUserId.setCaption(cell._row.cells[3].value);
        },        
        _fillsearchusersinfo:function(resp) {
            this.binduserdetails.resetValue("");
            this._hidePanelDetails("edit");
            var obj = _.unserialize(resp);
            this.iptEditName.setValue(obj.userinfo.name);
            this.iptEditShortName.setValue(obj.userinfo.shortName);
            this.iptEditAddress1.setValue(obj.userinfo.address1);
            this.iptEditAddress2.setValue(obj.userinfo.address2);
            this.iptEditAddress3.setValue(obj.userinfo.address3);
            this.iptEditTelNo.setValue(obj.userinfo.telNumber);
            this.iptEditFaxNo.setValue(obj.userinfo.faxNumber);
            this.iptEditMobileNo.setValue(obj.userinfo.mobileNumber);
            this.iptEditEmailAdd.setValue(obj.userinfo.pcEmail);
            this.iptEditMobEmailAdd.setValue(obj.userinfo.mobileEmail);
            this.txtEditorEditComments.setValue(obj.userinfo.comments);
            this.chkEditBMS.setValue(obj.userinfo.useBms==0?false:true);
            this.tosChk.setValue(obj.userinfo.tosFlag=='1'?true:false);
            this.calendarHighlightChk.setValue(obj.userinfo.enableCalendarHighlight=='1'?true:false);
            this.iptEditUserName.setValue(obj.userinfo.userName);
            this.iptEditPassword.setValue(obj.userinfo.password);
            var items = [];
            for(var i=0; i<obj.userinfo.categoryList.length; i++){
                items.push({"id":obj.userinfo.categoryList[i].categoryId, "caption":obj.userinfo.categoryList[i].categoryAvailable});
            }
            this.lstEditCategory.setItems(items);
            var arr=[];
            arr.push({"id":obj.userinfo.rolesList[0].roleId, "caption":obj.userinfo.rolesList[0].roleName});
            var rolename = obj.userinfo.rolesList[0].roleName;
            if (rolename.toLowerCase()=="seller") {
                this.btnEditCategory.setDisabled(false);
                this.btnEditCategory.setVisibility("visible");
                this.btnEditRemove.setVisibility("visible");
                this.calendarHighlightChk.setDisabled(false);
            } else if(rolename.toLowerCase()=="seller_admin"){
                this.calendarHighlightChk.setDisabled(false);
            } else {
                this.calendarHighlightChk.setDisabled(true);
                this.btnEditCategory.setVisibility("hidden");
                this.btnEditRemove.setVisibility("hidden");
            }
            this.cbiEditRole.setUIValue(obj.userinfo.rolesList[0].roleName, true);
            this.cbiEditRole.setItems(arr, true);
            this.btnNewDealingPattern.setDisabled(false);
            this.chkEditUnfinalizeReceived.setValue(obj.userinfo.unfinalizeReceived==0?false:true);
        },        
        _btndownloadcompInfo_onclick: function (profile, e, src, value) {
            if (!downloadcsv.isErrorFieldRequired()) {
                var url = "/eON/downloadcompanyinfo.json";
                var obj = new Object();
                obj.companyId = SPA.lblCompanyId.getCaption();
                obj.include = SPA.chkIncludeDetails.getValue();
                submitTheForm(url, "_json", _.serialize(obj), "Are you sure to download company information?");
            }
        },        
        _btndealingpattern_onclick:function (profile, e, src, value) {
            this._loadCDP(1);
            setFocus(this.iptSearchCompDealingPattern);
        },        
        _loadCDP:function(mode) {
            var ns = this, items=[];
            var url = "/eON/getCompanyNamesById.json";
            var o=new Object();
            o.isDealingPattern = 1;
            o.descr=this.cbiCompanyType.getUIValue();
            o.flagType=this.cbiCompanyType.getUIValue()=="buyer"?"0":"1";
            o.companyId=this.lblCompanyId.getCaption();
            linb.Ajax(url,o,
                function(response){
                    validateResponse(response);
                    var availItems=[];
                    var selItems=[];
                    var obj = _.unserialize(response);




                    if(obj.companyNames ==null){
                        return;
                    }




                    if(obj.companyNames.length>0) {
                        for(var i=0; i<obj.companyNames.length; i++) {
                            availItems.push({"id":obj.companyNames[i].cells[1], "caption":obj.companyNames[i].cells[0]});
                        }
                        if(mode==1) {
                            if (obj.selectedCDPattern.length > 0) {
                                for(var i=0; i<obj.selectedCDPattern.length; i++) {
                                    selItems.push({"id":obj.selectedCDPattern[i].companyId, "caption":obj.selectedCDPattern[i].companyName});
                                }
                                SPA.btnCompDealingPatternRemove.setDisabled(true);
                                SPA.btnCompDealingPatternOk.setDisabled(false);
                            }else{
                                SPA.btnCompDealingPatternRemove.setDisabled(true);
                                SPA.btnCompDealingPatternOk.setDisabled(true);
                            }
                            SPA.lstSelectedCompDealingPattern.setItems(selItems);
                        }
                        SPA.lstAvailableCompDealingPattern.setItems(availItems);
                    }
                },
            function(msg){
                    linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
            this.panelDealingPattern.show(this.panelCompany.reBoxing(),true,0,0);
        },        
        _btneditdealingpattern_onclick:function (profile, e, src, value) {
            var ns = this, items=[];
            var url = "/eON/usersdpattern.json";
            var o=new Object();
            o.isDealingPattern = 1;
            o.companyId=this.lblCompanyId.getCaption();
            o.userId=this.lblUserId.getCaption();
            o.companyType=this.cbiCompanyType.getUIValue();
            o.roleId=this.cbiEditRole.getItems()[0].id;
            o.roleName = this.cbiEditRole.getUIValue();
            o.newRole = 0;
            linb.Ajax(url,o,
                function(response){
                    validateResponse(response);
                    var availItems=[],selItems=[];
                    var selItems=[];
                    var obj = _.unserialize(response);
                    for(var i=0; i<obj.usersDPList.length; i++) {
                        availItems.push({"id":obj.usersDPList[i].userId, "caption":obj.usersDPList[i].userName});
                    }
                    SPA.lstAvailableUDP.setItems(availItems);
                    for(var i=0; i<obj.userSelDP.length; i++) {
                        selItems.push({"id":obj.userSelDP[i].userId, "caption":obj.userSelDP[i].userName, "datefrom":obj.userSelDP[i].expiryDateFrom, "dateto":obj.userSelDP[i].expiryDateTo, "userdpid" : obj.userSelDP[i].userDPId});
                    }
                    if (selItems.length>0) { // Do this so to save when removing UDP
                        SPA.lblSaveUDPWhenRemove.setCaption("1");
//                        SPA.btnRemoveUDP.setDisabled(true);
                        SPA.btnSaveUDP.setDisabled(false);
                    } else {
                        SPA.lblSaveUDPWhenRemove.setCaption("0");
//                        SPA.btnRemoveUDP.setDisabled(true);
                        SPA.btnSaveUDP.setDisabled(true);
                    }
                    
                    // set retrieved users in global var
                    g_userItem = selItems;
                    
                    SPA.lstSelectedUDP.setItems(selItems);
                    SPA.lstSelectedUDP.refresh();
                    // ENHANCEMENT START 20120531: Rhoda Redmine 865
                    SPA.iptExpiryFromUDP.setDisabled(true);
                    SPA.iptExpiryToUDP.setDisabled(true);
                    // ENHANCEMENT END 20120531: Rhoda Redmine 865
            },
            function(msg){
                    linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
            this.pnlUserDealPattern.show(this.panelCompany.reBoxing(),true,0,0);
            g_dpflag = 0; // set the dealing pattern flag to 1 as new
            setFocus(this.iptSearchUserNameUDP);
            g_removeMode=0;
        },        
        _bntuserdealingcancel_onclick:function (profile, e, src, value) {
            this.iptExpiryFromUDP.resetValue("");
            this.iptExpiryToUDP.resetValue("");
            this.pnlUserDealPattern.hide();
        },        
        _btnaddcompuser_onclick:function (profile, e, src, value) {
            this.bindnewuserdetails.resetValue("");
            this._hidePanelDetails("new");
            this.lblUserId.setCaption("");
            SPA.btnNewDealingPattern.setDisabled(true);
            this._getRoles();
        },        
        _btnnewdealingpattern_onclick:function (profile, e, src, value) {
            var ns = this, items=[];
            var url = "/eON/usersdpattern.json";
            var o=new Object();
            o.isDealingPattern = 1;
            o.companyId=this.lblCompanyId.getCaption();
            o.userId=this.lblUserId.getCaption();
            o.companyType=this.cbiCompanyType.getUIValue();
            o.roleId=this.cbiNewRole.getItems()[0].id;
            o.roleName = this.cbiNewRole.getUIValue();
            o.newRole = 1;
            linb.Ajax(url,o,
                function(response){
                    validateResponse(response);
                    var availItems=[],selItems=[];
                    var selItems=[];
                    var obj = _.unserialize(response);
                    for(var i=0; i<obj.usersDPList.length; i++) {
                        availItems.push({"id":obj.usersDPList[i].userId, "caption":obj.usersDPList[i].userName});
                    }
                    SPA.lstAvailableUDP.setItems(availItems);
                    for(var i=0; i<obj.userSelDP.length; i++) {
                        selItems.push({"id":obj.userSelDP[i].userId, "caption":obj.userSelDP[i].userName, "datefrom":obj.userSelDP[i].expiryDateFrom, "dateto":obj.userSelDP[i].expiryDateTo});
                    }
                    if (selItems.length>0) { // Do this so to save when removing UDP
                        SPA.lblSaveUDPWhenRemove.setCaption("1");
//                        SPA.btnRemoveUDP.setDisabled(true);
                        SPA.btnSaveUDP.setDisabled(false);
                    } else {
                        SPA.lblSaveUDPWhenRemove.setCaption("0");
//                        SPA.btnRemoveUDP.setDisabled(true);
                        SPA.btnSaveUDP.setDisabled(true);
                    }
                    SPA.lstSelectedUDP.setItems(selItems);
                    SPA.lstSelectedUDP.refresh();
            },
            function(msg){
                    linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
            this.pnlUserDealPattern.show(this.panelCompany.reBoxing(),true,0,0);
            g_dpflag = 1; // set the dealing pattern flag to 1 as new
            setFocus(this.iptSearchUserNameUDP);
            g_removeMode=0; //this is used for removing dealing pattern
        },        
        _btnsavecompuser_onclick:function (profile, e, src, value) {
            var ns = this;
            var obj = new Object();
            obj.companyId=this.lblCompanyId.getCaption();
            obj.companyName=this.iptCompName.getUIValue();
            obj.shortName=this.iptShortName.getUIValue();
            obj.description=this.cbiCompanyType.getUIValue();
            obj.contactPerson=this.iptContactPerson.getUIValue();
            obj.address1=this.iptAddress1.getUIValue();
            obj.address2=this.iptAddress2.getUIValue();
            obj.address3=this.iptAddress3.getUIValue();
            obj.telNumber=this.iptTelNo.getUIValue();
            obj.faxNumber=this.iptFaxNo.getUIValue();
            obj.emailAdd=this.iptEmailAdd.getUIValue();
            obj.soxFlag=this.chkSOX.getUIValue();
            obj.comments=this.txtEditorComment.getUIValue();




            var reqFields = new Array();
            reqFields[0] = this.iptCompName;
            reqFields[1] = this.iptShortName;
            reqFields[2] = this.cbiCompanyType;
            
            if (checkIfFieldIsValid(reqFields, "", this.cbiCompanyType.getUIValue(), 0)) {
                if (!checkEmail(this.iptEmailAdd)) {
                    //alert(linb.Locale[g_LangKey].app.caption['alertinvalidemailadd']);
                    return;
                }
                var contactNoFields = new Array();
                contactNoFields[0] = this.iptTelNo;
                contactNoFields[1] = this.iptFaxNo;
                if(!validateContactNumberFields(contactNoFields)) {
                    alert(linb.Locale[g_LangKey].app.caption['alertinvalidcontactnumber']);
                    return;
                }
                
                switch(g_saveMode) {
                case "new" :
                    var url = "/eON/savecompanyinfo.json";
                    linb.Ajax(url,obj,
                        function(response){
                            validateResponse(response);
                            var res = _.unserialize(response);
                            if (parseInt(res.status)>0) {
                                alert(linb.Locale[g_LangKey].app.caption['alertnewcompanysaved']);
                                SPA._hidePanelDetails();
                            }
                            else if (res.status=="-1") {
                                alert(linb.Locale[g_LangKey].app.caption['alertcompanyexist']);
                            }
                            else {
                                alert(linb.Locale[g_LangKey].app.caption['alerterrorsaving']);
                            }
                    },
                    function(msg){
                        linb.message("失敗： " + msg);
                    },
                    null,
                    {method:'POST',retry:0}).start();
                    this.btnDealingPattern.setDisabled(false);
                    break;
                default :
                    var url = "/eON/updatecompanyinfo.json";
                    linb.Ajax(url,obj,
                        function(response){
                            validateResponse(response);
                            var res = _.unserialize(response);
                            if (parseInt(res.status)>0) {
                                alert(linb.Locale[g_LangKey].app.caption['alertcompanyupdated']);
                                //SPA.bindcompany.resetValue("");
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
                }
            }
            else {
                alert(linb.Locale[g_LangKey].app.caption['alertrequiredfields']);
            }
        },        
        _btnsavenewrole_onclick:function (profile, e, src, value) {
            var obj=new Object(), categoryList="", flagtype=0;
            var uv = this.cbiNewRole.getUIValue().split(";"), vmatch=0;
            var items = this.cbiNewRole.getItems();
            for(var i=0;i<items.length;i++){
                if(items[i].caption==uv)
                    vmatch=1;
            }
            var reqFields = new Array();
            reqFields[0] = this.iptNewFirstName;
            reqFields[1] = this.iptNewShortName;
            reqFields[2] = this.cbiNewRole;            
            reqFields[3] = this.iptNewUserId; 
            reqFields[4] = this.iptNewPassword;
            
            if (this.cbiCompanyType.getUIValue()=="seller") {
                categoryList = this.lstNewCategory.getItems();
                flagtype=1;
            }
            
            if (checkIfFieldIsValid(reqFields, this.lstNewCategory, this.cbiCompanyType.getUIValue(), flagtype)) {
           
                var contactNoFields = new Array();
                contactNoFields[0] = this.iptNewTelNo;
                contactNoFields[1] = this.iptNewFaxNo;
                contactNoFields[1] = this.iptNewMobileNo;
                if(!validateContactNumberFields(contactNoFields)) {
                    alert(linb.Locale[g_LangKey].app.caption['alertinvalidcontactnumber']);
                    return;
                }
                
               //start:jr
                //check if length is not zero
                /*
                if(utilTrim(this.iptNewUserId.getUIValue()).length < 1 ||utilTrim(this.iptNewPassword.getUIValue()).length < 1 ){
                    alert(linb.Locale[g_LangKey].app.caption['alertrequiredfields']);
                    return;
                }
                */
                //end:jr




                if (vmatch==1) {
                    var ns = this;
                    var url = "/eON/saveusersinfo.json";
                    var obj = ns._getUserObjects();
                    linb.Ajax(url,obj,
                    function(response){
                        validateResponse(response);
                        var res = _.unserialize(response);
                        if (parseInt(res.userid)>0) {
                            alert(linb.Locale[g_LangKey].app.caption['alertnewusersaved']);
                            SPA.btnNewDealingPattern.setDisabled(false);
                            SPA._showUsers();
                        }
                        else if(res.userid==-1) {
                            alert(linb.Locale[g_LangKey].app.caption['alertuserexist']);
                        }
                        else {
                            alert(linb.Locale[g_LangKey].app.caption['alerterrorsaving']);
                        }
                        SPA.lblUserId.setCaption(res.userid);
                    },
                    function(msg){
                    linb.message("失敗： " + msg);
                    },
                    null,
                    {method:'POST',retry:0}).start();
                }else {
                    alert(linb.Locale[g_LangKey].app.caption['alertinvalidrole']);
                }
            }else {
                alert(linb.Locale[g_LangKey].app.caption['alertrequiredfields']);
            }
        },        
        _getUserObjects:function() {
            var obj=new Object();
            var uv = this.cbiNewRole.getUIValue().split(";"),roleId;
            var items = this.cbiNewRole.getItems();
            for(var i=0;i<items.length;i++){
                if(items[i].caption==uv){
                    roleId=items[i].id;
                }
            }
            obj.roleId=roleId;
            obj.userId=this.lblUserId.getCaption();
            obj.userName=this.iptNewUserId.getUIValue();
            obj.password=this.iptNewPassword.getUIValue();
            obj.name=this.iptNewFirstName.getUIValue();
            obj.shortName=this.iptNewShortName.getUIValue();
            obj.companyId=this.lblCompanyId.getCaption();
            obj.address1=this.iptNewAddress1.getUIValue();
            obj.address2=this.iptNewAddress2.getUIValue();
            obj.address3=this.iptNewAddress3.getUIValue();
            obj.mobileNumber=this.iptNewMobileNo.getUIValue();
            obj.telNumber=this.iptNewTelNo.getUIValue();
            obj.faxNumber=this.iptNewFaxNo.getUIValue();
            obj.mobileEmail=this.iptNewMobEmailAdd.getUIValue();
            obj.pcEmail=this.iptNewEmailAdd.getUIValue();
            obj.comments=this.txtEditorNewComments.getUIValue();
            obj.useBms=this.chkNewBMS.getUIValue();
            obj.tosFlag = (this.tosNewUserChk.getUIValue() ?'1':'0');
            obj.enableCalendarHighlight = (this.newCalendarHighlightChk.getUIValue() ?'1':'0');
            //if(roleId==1)unfinalizeReceived
                obj.categories = this._getCategoryList("new");
            obj.unfinalizeReceived=this.chkNewUnfinalizeReceived.getUIValue();
            return obj;
        },        
        _btnaddnewcompany_onclick:function (profile, e, src, value) {
            this._clearfields();
            //this.lblSaveMode.setCaption('new');
            setFocus(this.iptCompName);
            g_saveMode = "new";
            this.lblCompanyId.setCaption("");
            this.lblUserId.setCaption("");
            this.btnDealingPattern.setDisabled(true);
            this.btnAddCompUser.setDisabled(true);
            this.btnAdministrator.setDisabled(true);
            this.cbiCompanyType.setDisabled(false);
        },        
        _clearfields:function() {
            this.bindcompany.resetValue("");
            this.bindnewuserdetails.resetValue("");
            this.binduserdetails.resetValue("");
            this.tgUsersList.setRows([]);
            this.lstEditCategory.setItems([]);
            this.lstNewCategory.setItems([]);
            this._hidePanelDetails("");
            this.grpCompanyDetails.reBoxing().show();
            this.grpCompanyDetails.setToggle(true);
            setFocus(this.iptCompName);
        },        
        _tgsearchcompanyname_onrowselected:function (profile, row, src) {
            this.iptDlgSearchCompName.setValue(row.cells[0].value);
            this.lblSearchUserId.setValue(row.cells[1].value);
            this.btnSearchCompOk.setDisabled(false);
        },        
        _tgsearchcompanyname_ondblclickrow:function (profile, row, e, src) {
            this._search_companyname(row.cells[1].value);
            this._hidePanelDetails('search');
            this.dialogSearchCompany.hide();
            this.btnAddCompUser.setDisabled(false);
            this.btnAdministrator.setDisabled(false);
        },        
        _btndeletecompuser_onclick:function (profile, e, src, value) {
            if (this.lblUserId.getCaption().length>0) {
                var answer = confirm(linb.Locale[g_LangKey].app.caption['confirmdeleteuser']);
                if (!answer) 
                    return;
                var ns = this;
                var url = "/eON/deleteuserbyid.json";
                var obj = new Object();
                obj.userId = this.lblUserId.getCaption();
                obj.companyId = this.lblCompanyId.getCaption();
                linb.Ajax(url,obj,
                    function(response){
                        validateResponse(response);
                        var obj = _.unserialize(response);
                        if(parseInt(obj.status)>0){
                            SPA.binduserdetails.resetValue("");
                            SPA.pnlEditUserDetails.hide();
                            SPA.pnlNewUserDetails.hide();
                            SPA.tgUsersList.setRows(obj.userslist);
                            alert(linb.Locale[g_LangKey].app.caption['alertuserdeleted']);
                        }
                        else {
                            alert(linb.Locale[g_LangKey].app.caption['alertdeletionfailed']);
                        }
                },
                function(msg){
                    alert("Deletion of user is in progress. Please verify later.");
                    //linb.message("失敗： " + msg);
                },
                null,
                {method:'POST',retry:0}).start();
            }
            else {
                alert(linb.Locale[g_LangKey].app.caption['alertnoselecteduser']);
            }
        },        
        _btnsaveeditrole_onclick:function (profile, e, src, value) {
            var role = this.cbiEditRole.getUIValue(), categoryList="", flagtype=0;
           
            var reqFields = new Array();
            reqFields[0] = this.iptEditName;
            reqFields[1] = this.iptEditShortName;
            reqFields[2] = this.cbiEditRole;
            reqFields[3] = this.iptEditUserName; 
            reqFields[4] = this.iptEditPassword;    
            
            if(role=="SELLER") {
                categoryList = this.lstEditCategory;
                flagtype = 1;
            }            
            
            if (checkIfFieldIsValid(reqFields, categoryList, this.cbiCompanyType.getUIValue(), flagtype)) {
           
            var contactNoFields = new Array();
            contactNoFields[0] = this.iptEditTelNo;
            contactNoFields[1] = this.iptEditFaxNo;
            contactNoFields[2] = this.iptEditMobileNo;
            if(!validateContactNumberFields(contactNoFields)) {
                alert(linb.Locale[g_LangKey].app.caption['alertinvalidcontactnumber']);
                return;
            }
                
            //start:jr
            //check if length is not zero
            /*
            if(utilTrim(this.iptEditUserName.getUIValue()).length < 1 ||utilTrim(this.iptEditPassword.getUIValue()).length < 1 ){
                alert(linb.Locale[g_LangKey].app.caption['alertrequiredfields']);
                return;
            }
            */
            //end:jr




            var obj = new Object();
            obj.roleId=this.cbiEditRole.getItems()[0].id;
            obj.userId=this.lblUserId.getCaption();
            obj.userName=this.iptEditUserName.getUIValue();
            obj.password=this.iptEditPassword.getUIValue();
            obj.name=this.iptEditName.getUIValue();
            obj.shortName=this.iptEditShortName.getUIValue();
            obj.companyId=this.lblCompanyId.getCaption();
            obj.address1=this.iptEditAddress1.getUIValue();
            obj.address2=this.iptEditAddress2.getUIValue();
            obj.address3=this.iptEditAddress3.getUIValue();
            obj.mobileNumber=this.iptEditMobileNo.getUIValue();
            obj.telNumber=this.iptEditTelNo.getUIValue();
            obj.faxNumber=this.iptEditFaxNo.getUIValue();
            obj.mobileEmail=this.iptEditMobEmailAdd.getUIValue();
            obj.pcEmail=this.iptEditEmailAdd.getUIValue();
            obj.comments=this.txtEditorEditComments.getUIValue();
            obj.useBms=this.chkEditBMS.getUIValue();
            obj.tosFlag = (this.tosChk.getUIValue() ?'1':'0');
            obj.enableCalendarHighlight = (this.calendarHighlightChk.getUIValue() ?'1':'0');
            obj.unfinalizeReceived=this.chkEditUnfinalizeReceived.getUIValue();
            //if(role=="SELLER")
                obj.categories=this._getCategoryList('edit');
            var ns = this;
            var url = "/eON/updateuserbyid.json";
            linb.Ajax(url,obj,
                function(response){
                    validateResponse(response);
                    var res = _.unserialize(response);
                    if (parseInt(res.status)>0) {
                        alert(linb.Locale[g_LangKey].app.caption['alertuserupdated']);
                        SPA._showUsers();
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
            }
            else {
                alert(linb.Locale[g_LangKey].app.caption['alertrequiredfields']);
            }
        }, 
        _getCategoryList:function(type) {
            var items, uv, tmp="";
            if (type=="new") {
                uv = this.lstNewCategory.getUIValue().split(";");
                items = this.lstNewCategory.getItems();
                for(var i=0;i<items.length;i++){
                    if(_.arr.indexOf(uv,items[i].id)>=0) {
                        tmp = tmp + items[i].id + ";";
                    }
                }
            }else{
                items = this.lstEditCategory.getItems();
                for(var i=0;i<items.length;i++){
                    tmp = tmp + items[i].id + ";";
                }
            }
            return tmp;
        },        
        _btneditremove_onclick:function (profile, e, src, value) {
            var uv = this.lstEditCategory.getUIValue().split(";"), nuv= [];
            var items = this.lstEditCategory.getItems();
            for(var i=0;i<items.length;i++){
                if(_.arr.indexOf(uv,items[i].id)>=0){
                    this.lstEditCategory.removeItems(items[i].id);
                }
            }




            this.lstEditCategory.refresh();
        },        
        _btnselectlistofcategory_onclick:function (profile, e, src, value) {
            var uv = this.lstCategoryList.getUIValue().split(";"), nuv= [], items, isExist;
            var items = this.lstCategoryList.getItems();
            isExist = this._checkCategoryIfExists(this.lstCategoryList.getUIValue());
            if(isExist) {
                //alert('Duplicate entry.');
                alert(linb.Locale[g_LangKey].app.caption['alertduplicateentry']);
            }else{
                for(var i=0;i<items.length;i++){
                    if(_.arr.indexOf(uv,items[i].id)>=0){
                        nuv.push(items[i]);
                    }
                }
                this.lstEditCategory.insertItems(nuv,false);
                this.lstCategoryList.refresh();
                this.dialogNewUserCategory.hide();
            }
        },        
        _checkCategoryIfExists:function(id) {
            var select = this.lstEditCategory.getItems();
            for(var x=0;x<select.length;x++) {
                //alert(select[x].id);
                //alert(id);jr
                if (id==select[x].id) {
                    return true;
                }
            }
            return false;
        },        
        _btneditcategory_onclick:function (profile, e, src, value) {
            this._getRoles();
            this.dialogNewUserCategory.show(this.panelCompany.reBoxing(),true, 350,100);
        },        
        _btncompdealingpatterncancel_onclick:function (profile, e, src, value) {
            this.lstSelectedCompDealingPattern.setItems([]);
            this.panelDealingPattern.hide();
        },        
        _btncompdealingpatternok_onclick:function (profile, e, src, value) {
            if (this.lstSelectedCompDealingPattern.getItems().length<1) {
                alert(linb.Locale[g_LangKey].app.caption['alertnodpselected']);
            }
            else {
            var ns = this;
            var url = "/eON/savecompdpattern.json";
            var obj = new Object(), selected = [];
            obj.companyId=this.lblCompanyId.getCaption();
            obj.roleType=this.cbiCompanyType.getUIValue();
            var items = this.lstSelectedCompDealingPattern.getItems();
            for(var i=0;i<items.length;i++){
                selected.push(items[i].id);
            }
            obj.selectedCompId=selected;
            linb.Ajax(url,obj,
                function(response){
                    validateResponse(response);
                    var res = _.unserialize(response);
                    if (parseInt(res.status)>0) {
                        alert(linb.Locale[g_LangKey].app.caption['alertcompanydpsaved']);
                    }
                    else {
                        alert(linb.Locale[g_LangKey].app.caption['alerterrorsavingdp']);
                    }
            },
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
            if (this.lstSelectedCompDealingPattern.getItems().length < 1) {
                this.btnCompDealingPatternOk.setDisabled(true);
                this.btnCompDealingPatternRemove.setDisabled(true);
            }
            //this.panelDealingPattern.hide();
        }
        },        
        _btntransferavailablecompanies_onclick:function (profile, e, src, value) {
            var uv = this.lstAvailableCompDealingPattern.getUIValue().split(";"), nuv= [];
            var items = this.lstAvailableCompDealingPattern.getItems();
            var ret=false;
            for(var i=0;i<items.length;i++){
                if(_.arr.indexOf(uv,items[i].id)>=0) {
                    ret = this._checkitems(items[i].caption);
                    if(ret==true) {
                        alert(linb.Locale[g_LangKey].app.caption['alertduplicateentry']);
                    }
                    else {
                        nuv.push(items[i]);
                    }
                }
                this.btnCompDealingPatternOk.setDisabled(false);
                this.btnCompDealingPatternRemove.setDisabled(true);
            }
            this.lstSelectedCompDealingPattern.insertItems(nuv,null,false);
            this.lstAvailableCompDealingPattern.refresh();
        },        
        _checkitems:function (id) {
            var select = this.lstSelectedCompDealingPattern.getItems();
            for(var x=0;x<select.length;x++) {
                if (id==select[x].caption) {
                    return true;
                }
            }
        },        
        _btncompdealingpatternremove_onclick:function (profile, e, src, value) {
            var ns = this;
            var url = "/eON/removeCDP.json";
            var obj = new Object();
            obj.companyId=this.lblCompanyId.getCaption();
            obj.selectedCompanyId=this.lstSelectedCompDealingPattern.getUIValue();
            obj.companyType=this.cbiCompanyType.getUIValue();
            linb.Ajax(url,obj,
                function(response){
                    validateResponse(response);
                    var res = _.unserialize(response);
                    if (res.result == "true") {
                        var uv = SPA.lstSelectedCompDealingPattern.getUIValue().split(";"), nuv= [];
                        var items = SPA.lstSelectedCompDealingPattern.getItems();
                        for(var i=0;i<items.length;i++){
                            if(_.arr.indexOf(uv,items[i].id)>=0){
                                SPA.lstSelectedCompDealingPattern.removeItems(items[i].id);
                            }
                        }
                    }
                    else {
                        alert(linb.Locale[g_LangKey].app.caption['alertcannotremovedp']);
                    }
                    SPA.lstSelectedCompDealingPattern.refresh();
                    if (SPA.lstSelectedCompDealingPattern.getItems().length < 1) {
                        SPA.btnCompDealingPatternOk.setDisabled(true);
                        SPA.btnCompDealingPatternRemove.setDisabled(true);
                    }
            },
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start(); 
        },        
        _btncancelcompuser_onclick:function (profile, e, src, value) {
            this._hidePanelDetails("");
        },        
        _getRoles:function() {
            var ns = this;
            var url = "/eON/loadrolesandcategory.json";
            var param = new Object();
            param.companyFlagType = this.cbiCompanyType.getUIValue().toLowerCase()=="buyer"?"0":"1";
            linb.Ajax(url,param,
                function(response){
                    validateResponse(response);
                    var obj=null;
                    obj = _.unserialize(response);
                    var rolesitems=[];
                    for(var i=0; i<obj.roles.length;i++) {
                        rolesitems.push({"id":obj.roles[i].roleId, "caption":obj.roles[i].roleName});
                    }
                    SPA.cbiNewRole.setItems(rolesitems);
                    var categoryitems=[];
                    for(var i=0; i<obj.category.length;i++) {
                        categoryitems.push({"id":obj.category[i].categoryId, "caption":obj.category[i].description});
                    }
                    if(SPA.cbiCompanyType.getUIValue().toLowerCase()!="buyer") {
                        /* jr fix */
                        SPA.lstNewCategory.setItems(categoryitems);
                        SPA.lstCategoryList.setItems(categoryitems);
                    }else{
                        SPA.lstNewCategory.setItems([]);
                        SPA.lstCategoryList.setItems([]);
                    }
            },
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
        },        
        _btnadministrator_onclick:function (profile, e, src, value) {
            this._hidePanelDetails("admin");
        },        
        _hidePanelDetails:function(name) {
            switch(name)
            {
                case "admin" :
                    this.pnlAdminNewUserDetails.reBoxing().show();
                    this.pnlNewUserDetails.hide();
                    this.pnlEditUserDetails.hide();
                    this.clearFailedAdminBackgroundColor();
                    setFocus(this.iptAdminFirstname);
                    //SPA.setAdminRoleId();
                    break;
                case "new" :
                    this.pnlNewUserDetails.reBoxing().show();
                    this.pnlAdminNewUserDetails.hide();
                    this.pnlEditUserDetails.hide();
                    setFocus(this.iptNewFirstName);
                    this.clearFailedUserBackgroundColor("new");
                    if (this.cbiCompanyType.getUIValue() == "Buyer"){
                        this.chkNewUnfinalizeReceived.setVisibility("visible");
                    }else{
                        this.chkNewUnfinalizeReceived.setVisibility("hidden");
                    }
                    break;
                case "edit" :
                    this.pnlEditUserDetails.reBoxing().show();
                    this.pnlAdminNewUserDetails.hide();
                    this.pnlNewUserDetails.hide();
                    setFocus(this.iptEditName);
                    this.clearFailedUserBackgroundColor("edit");
                    if (this.cbiCompanyType.getUIValue() == "Buyer"){
                        this.chkEditUnfinalizeReceived.setVisibility("visible");
                    }else{
                        this.chkEditUnfinalizeReceived.setVisibility("hidden");
                    }
                    break;
                case "search" :
                    this.pnlEditUserDetails.hide();
                    this.pnlAdminNewUserDetails.hide();
                    this.pnlNewUserDetails.hide();
                    this.binduserdetails.resetValue("");
                    this.bindnewuserdetails.resetValue("");
                    this.bindAdminNewUsers.resetValue("");
                    this.grpCompanyDetails.reBoxing().show();
                    this.clearFailedCompanyBackgroundColor();
                    setFocus(this.iptCompName);
                    break;
                default :
                    this.pnlEditUserDetails.hide();
                    this.pnlAdminNewUserDetails.hide();
                    this.pnlNewUserDetails.hide();
                    this.grpCompanyDetails.hide();
                    this.bindcompany.resetValue("");
                    this.binduserdetails.resetValue("");
                    this.bindnewuserdetails.resetValue("");
                    this.bindAdminNewUsers.resetValue("");
                    this.grpCompanyDetails.setToggle(false);
                    this.clearFailedCompanyBackgroundColor();
                    setFocus(this.iptCompName);
                    break;
            }




        },        
        _btncancelnewrole_onclick:function (profile, e, src, value) {
            this.bindnewuserdetails.resetValue("");
            SPA.pnlNewUserDetails.hide();
        },        
        _btncanceleditrole_onclick:function (profile, e, src, value) {
            this.binduserdetails.resetValue("");
            this.pnlEditUserDetails.hide();
        },        
        _btnadmnewcancel_onclick:function (profile, e, src, value) {
            this.bindAdminNewUsers.resetValue("");
            this.pnlAdminNewUserDetails.hide();
        },        
        _btnadmsave_onclick:function (profile, e, src, value) {
            var reqFields = new Array();
            reqFields[0] = this.iptAdminFirstname;
            reqFields[1] = this.iptAdminShortname;
            reqFields[2] = this.cbiAdminRole;
            reqFields[3] = this.iptAdminUsername;
            reqFields[4] = this.iptAdminPassword; 
            
            if (checkIfFieldIsValid(reqFields, "", this.cbiCompanyType.getUIValue(), 0)) {
           
                var contactNoFields = new Array();
                contactNoFields[0] = this.iptAdminTelNo;
                contactNoFields[1] = this.iptAdminFaxNo;
                contactNoFields[2] = this.iptAdminMobileNo;
                if(!validateContactNumberFields(contactNoFields)) {
                    alert(linb.Locale[g_LangKey].app.caption['alertinvalidcontactnumber']);
                    return;
                }
                
                var ns = this;
                var url = "/eON/saveusersinfo.json";
                var obj = new Object();// = ns._getAdminUserObjects;
                obj.companyId=this.lblCompanyId.getCaption();
                obj.name=this.iptAdminFirstname.getUIValue();
                obj.shortName=this.iptAdminShortname.getUIValue();
                obj.roleId=this.cbiAdminRole.getItems()[0].id;
                obj.address1=this.iptAdminAddress1.getUIValue();
                obj.address2=this.iptAdminAddress2.getUIValue();
                obj.address3=this.iptAdminAddress3.getUIValue();
                obj.telNumber=this.iptAdminTelNo.getUIValue();
                obj.faxNumber=this.iptAdminFaxNo.getUIValue();
                obj.mobileNumber=this.iptAdminMobileNo.getUIValue();
                obj.pcEmail=this.iptAdminEmailAdd.getUIValue();
                obj.mobileEmail=this.iptAdminMobEmailAdd.getUIValue();
                obj.comments=this.txtAdminComments.getUIValue();
                obj.userName=this.iptAdminUsername.getUIValue();
                obj.password=this.iptAdminPassword.getUIValue();
                linb.Ajax(url,obj,
                function(response){
                    validateResponse(response);
                    var res = _.unserialize(response);
                    if (parseInt(res.status)>0) {
                        alert(linb.Locale[g_LangKey].app.caption['alertnewadminsaved']);
                        SPA.pnlAdminNewUserDetails.hide();
                        SPA.bindAdminNewUsers.resetValue("");
                        SPA._showUsers();
                    }
                    else if(res.status==-1) {
                        alert(linb.Locale[g_LangKey].app.caption['alertrecordexist']);
                    }
                    else {
                        alert(linb.Locale[g_LangKey].app.caption['alerterrorsaving']);
                    }
                },
                function(msg){
                linb.message("失敗： " + msg);
                },
                null,
                {method:'POST',retry:0,reqType:'json'}).start();
            }
            else {
                alert(linb.Locale[g_LangKey].app.caption['alertrequiredfields']);
            }
        },        
        _getAdminUserObjects:function() {
            var obj = new Object();
            obj.name=this.iptAdminFirstname.getUIValue();
            obj.shortName=this.iptAdminShortname.getUIValue();
            obj.roleId=5; //this.iptAdminRole.getUIValue();
            obj.address1=this.iptAdminAddress1.getUIValue();
            obj.address2=this.iptAdminAddress2.getUIValue();
            obj.address3=this.iptAdminAddress3.getUIValue();
            obj.telNumber=this.iptAdminTelNo.getUIValue();
            obj.faxNumber=this.iptAdminFaxNo.getUIValue();
            obj.mobileNumber=this.iptAdminMobileNo.getUIValue();
            obj.pcEmail=this.iptAdminEmailAdd.getUIValue();
            obj.mobileEmail=this.iptAdminMobEmailAdd.getUIValue();
            obj.comments=this.txtAdminComments.getUIValue();
            obj.userName=this.iptAdminUsername.getUIValue();
            obj.password=this.iptAdminPassword.getUIValue();
            return obj;
        },        
        _btntransferudp_onclick:function (profile, e, src, value) {
            var uv = this.lstAvailableUDP.getUIValue().split(";"), nuv= [], d = new Date;
            var items = this.lstAvailableUDP.getItems();
            var ret=false;
            var isDuplicate = false;
            var userItems=[];
            var userItemIds=[];
            for(var i=0;i<items.length;i++){
                if(_.arr.indexOf(uv,items[i].id)>=0) {
                    ret = this._checkudpitems(items[i].caption);
                    if(ret==true) {
                        isDuplicate = true;
                    }
                    userItemIds.push(items[i].id);
                    //userItems.push(items[i].caption);
                    userItems.push({"id":items[i].id, "caption":items[i].caption});
                    //this.lblDESUserId.setCaption(items[i].id);
                    //this.lstDESUser.setUIValue(items[i].caption); --jr
                    this.iptDESFrom.setUIValue(linb.Date.format(d,"yyyymmdd"));
                    this.iptDESTo.setUIValue("");
                    this.lblDESEditudp.setCaption("1");








                }
            }




            if(isDuplicate==true) {
                alert(linb.Locale[g_LangKey].app.caption['alertduplicateentry']);
            }else{
                this.lstDESUserId.setItems(userItems);
                this.lstDESUser.setItems(userItems);
                this.dlgUDPSetDate.show(this.pnlUserDealPattern.reBoxing(),true,200,200);
                this.lstAvailableUDP.refresh();
            }








        },        
        _checkudpitems:function (id) {
            var select = this.lstSelectedUDP.getItems();
            for(var x=0;x<select.length;x++) {
                if (id==select[x].caption) {
                    return true;
                }
            }
        },        
//        _btnremoveudp_onclick:function (profile, e, src, value) {
//            var uv = this.lstSelectedUDP.getUIValue().split(";"), nuv= [];
//            var items = this.lstSelectedUDP.getItems();
//            for(var i=0;i<items.length;i++){
//                if(_.arr.indexOf(uv,items[i].id)>=0) {
//                    //removedItems.push([items[i].id,items[i].datefrom,items[i].dateto]);
//                    //this.lstSelectedUDP.removeItems(items[i].id);
//                    var roleId = this.cbiEditRole.getItems()[0].id;
//                    if (roleId == "1" || roleId == "3")
//                        this.validateRemoveUser(roleId, this.lblUserId.getCaption(), items[i].id, items[i].datefrom, items[i].dateto);
//                    else {
//                        removedItems.push([items[i].id,items[i].datefrom,items[i].dateto]);
//                        this.lstSelectedUDP.removeItems(items[i].id);
//                    }
//                }
//            }
//            this.lstRemovedUDP.setItems(removedItems);
//            if(this.lstSelectedUDP.getItems().length > 0) {
//                this.btnSaveUDP.setDisabled(false);
//                this.btnRemoveUDP.setDisabled(true);
//            }else{
//                this.btnSaveUDP.setDisabled(false);
//                this.btnRemoveUDP.setDisabled(true);
//            }
//            g_removeMode=1; //this is used for removing user dealing pattern
//        }, 
        _btnsaveudp_onclick:function (profile, e, src, value) {
       
            this._iptexpirytoudp_onchange(null, null, this.iptExpiryToUDP.getUIValue());
       
            if(this.lstSelectedUDP.getItems().length<1 && g_removeMode==0) {
                alert(linb.Locale[g_LangKey].app.caption['alertnoselecteduserdp']);
            }
            else {
                var ns = this, item1, item2;
                var url = "/eON/saveuserdpattern.json";
                var obj = new Object(), selected = [];
                obj.roleId=g_dpflag==1?this.cbiNewRole.getItems()[0].id:this.cbiEditRole.getItems()[0].id;
                obj.userId=this.lblUserId.getCaption();
                obj.companyId=this.lblCompanyId.getCaption();
                obj.roleName=this.cbiNewRole.getUIValue();
                obj.newRole = g_dpflag;
                obj.userList = g_userItem;
                var items = this.lstSelectedUDP.getItems();
                for(var i=0;i<items.length;i++){
                    selected.push([items[i].id,items[i].datefrom,items[i].dateto]);
                }




                var removedItems = this.lstRemovedUDP.getItems(), removed =[];
                obj.selectedUserId=selected;
                //added by jr
                obj.removedUserId = removedItems;




                linb.Ajax(url,obj,
                    function(res){
                        validateResponse(res);
                        var o = _.unserialize(res);
                        if (parseInt(o.status)>0) {
                            alert(linb.Locale[g_LangKey].app.caption['alertuserdpsaved']);
                        }
                        else {
                            alert(linb.Locale[g_LangKey].app.caption['alerterrorsavinguserdp']);
                            if (o.dpMessage)
                                alert(o.dpMessage);
                        }




                        removedItems =[];
                        SPA.lstRemovedUDP.setItems(removedItems);
                },
                function(msg){
                    linb.message("失敗： " + msg);
                },
                null,
                {method:'POST',retry:0}).start();
                //this.lstSelectedUDP.refresh();
                if (this.lstSelectedUDP.getItems().length<1)
                    this.btnSaveUDP.setDisabled(true);
                else
                    this.btnSaveUDP.setDisabled(false);
            }
        },        
        _iptsearchcompname_onchange:function (profile, oldValue, newValue) {
            var ns = this;
            var url = "/eON/autosearchnames.json";
            var obj = new Object();
            obj.companyName=newValue;
            linb.Ajax(url,obj,
                function(response){
                    validateResponse(response);
                    var o = _.unserialize(response);
                    SPA.iptDlgSearchCompName.setValue(newValue);
                    SPA.tgSearchCompanyName.setRows(o.searcResults);
                    SPA.tgSearchCompanyName.refresh();
            },
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
            SPA.dialogSearchCompany.show(this.panelCompany.reBoxing(),true,150,80);
            setFocus(this.iptDlgSearchCompName);
        }, 
        _ipttelno_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            var unicode=e.charCode? e.charCode : e.keyCode;
            if (unicode==43 || unicode==45)
                return true;
            if ((unicode<48 || unicode>57) && (key!= "backspace"))
                return false;
        },        
        _iptfaxno_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            var unicode=e.charCode? e.charCode : e.keyCode;
            if (unicode==43 || unicode==45)
                return true;
            if ((unicode<48 || unicode>57) && (key!= "backspace"))
                return false;
        }, 
        _txteditorcomment_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            if (SPA.txtEditorComment.getUIValue().length >= 1000 && key!= "backspace"){
                key=null;
                return false;
            }
        },        
        _iptdlgsearchcompname_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            if (SPA.iptDlgSearchCompName.getUIValue().length<1)
                SPA.btnSearchCompOk.setDisabled(true);
            else
                SPA.btnSearchCompOk.setDisabled(false);
            SPA._searchcompanyname(this.iptDlgSearchCompName.getUIValue());
        }, 
        _iptnewtelno_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            var unicode=e.charCode? e.charCode : e.keyCode;
            if (unicode==43 || unicode==45)
                return true;
            if ((unicode<48 || unicode>57) && (key!= "backspace"))
                return false;
    },        
        _iptnewfaxno_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            var unicode=e.charCode? e.charCode : e.keyCode;
            if (unicode==43 || unicode==45)
                return true;
            if ((unicode<48 || unicode>57) && (key!= "backspace"))
                return false;
        },        
        _iptnewmobileno_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            var unicode=e.charCode? e.charCode : e.keyCode;
            if (unicode==43 || unicode==45)
                return true;
            if ((unicode<48 || unicode>57) && (key!= "backspace"))
                return false;
        },        
        _txteditornewcomments_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            if (SPA.txtEditorNewComments.getUIValue().length >= 1000 && key!= "backspace"){
                key=null;
                return false;
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
            if (SPA.txtEditorEditComments.getUIValue().length >= 1000 && key!= "backspace"){
                key=null;
                return false;
            }
        },        
        _btnsearchcompavailable_onclick:function (profile, e, src, value) {
            this._searchnamecdpattern(this.iptSearchCompDealingPattern.getUIValue());
        },        
        _iptsearchcompdealingpattern_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            SPA._searchnamecdpattern(this.iptSearchCompDealingPattern.getUIValue());
        },        
//        _iptadmintelno_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
//            var unicode=e.charCode? e.charCode : e.keyCode;
//            if (unicode==43 || unicode==45)
//                return true;
//            if ((unicode<48 || unicode>57) && (key!= "backspace"))
//                return false;
//        }, 
//        _iptadminfaxno_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
//            var unicode=e.charCode? e.charCode : e.keyCode;
//            if (unicode==43 || unicode==45)
//                return true;
//            if ((unicode<48 || unicode>57) && (key!= "backspace"))
//                return false;
//        }, 
//        _iptadminmobileno_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
//            var unicode=e.charCode? e.charCode : e.keyCode;
//            if (unicode==43 || unicode==45)
//                return true;
//            if ((unicode<48 || unicode>57) && (key!= "backspace"))
//                return false;
//        }, 
        _txtadmincomments_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            if (SPA.txtAdminComments.getUIValue().length >= 1000 && key!= "backspace"){
                key=null;
                return false;
            };
        },        
        _btndescancel_onclick:function (profile, e, src, value) {
            this.dlgUDPSetDate.hide();
        },        
        _iptdesuser_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            return false;
        },        
        _btndesset_onclick:function (profile, e, src, value) {
            var date = linb.Date,d = new Date(),today;
            today = date.format(d,'yyyymmdd');
            var dfrom = this.iptDESFrom.getUIValue();
            var dto = this.iptDESTo.getUIValue();
            if(dfrom < today)
                alert(linb.Locale[g_LangKey].app.caption['alerterrordatefromto']);
            else if (dto=="")
                this._updateUDPItems(this.lblDESEditudp.getCaption());
            else if (dfrom > dto)
                alert(linb.Locale[g_LangKey].app.caption['alerterrordatetofrom']);
            else
                this._updateUDPItems(this.lblDESEditudp.getCaption());
            
            // ENHANCEMENT START 20120906: Lele - Redmine 882
            this.iptExpiryFromUDP.setUIValue("");
            this.iptExpiryToUDP.setUIValue("");
            // ENHANCEMENT END 20120906
        },        




        _lstselectedudp_onitemselected:function (profile, item, src) {
            var ctr;
            for (ctr in g_userItem) {
                if (item.id == g_userItem[ctr].id) {
                    this.iptExpiryFromUDP.resetValue(g_userItem[ctr].datefrom);
                    this.iptExpiryToUDP.resetValue(g_userItem[ctr].dateto);
                }
            }
            // ENHANCEMENT START 20120531: Rhoda Redmine 865
            this.iptExpiryFromUDP.setDisabled(false);
            this.iptExpiryToUDP.setDisabled(false);
            // ENHANCEMENT END 20120531: Rhoda Redmine 865
        },        
        _cbidesdateto_afteruivalueset:function (profile, oldValue, newValue) {
            this.iptDESTo.setUIValue(linb.Date.format(newValue,"yyyymmdd"));
        },        
        _cbidesdatefrom_afteruivalueset:function (profile, oldValue, newValue) {
            this.iptDESFrom.setUIValue(linb.Date.format(newValue,"yyyymmdd"));
        },        
        _lstselectedudp_ondblclick:function (profile, item, src) {
            var uv = this.lstSelectedUDP.getUIValue().split(";"), nuv= [];
            var items = this.lstSelectedUDP.getItems();
            var ret=false;
            var userItems=[];
            //start:jr
            for(var i=0;i<items.length;i++){
                if(_.arr.indexOf(uv,items[i].id)>=0) {
                    //this.lblDESUserId.setCaption(items[i].id);
                    //this.lstDESUser.setUIValue(items[i].caption);
                    userItems.push({"id":items[i].id, "caption":items[i].caption});
                    this.iptDESFrom.setUIValue(items[i].datefrom);
                    this.iptDESTo.setUIValue(items[i].dateto);
                    this.lblDESEditudp.setCaption("2");
                }
            }




            this.lstDESUser.setItems(userItems);
            this.dlgUDPSetDate.show(this.pnlUserDealPattern.reBoxing(),true,200,200);
        },        
        _updateUDPItems:function(o) {
            //jr edit




            var nuv=[], userid, enablebutton = false;
            var userIds = this.lstDESUserId.getItems();
            var userItemsIds = this.lstDESUser.getItems();
            //edit jr
            var uv = this.lstRemovedUDP.getUIValue().split(";"), nuv= [];
            var items = this.lstRemovedUDP.getItems();
            //end edit jr
            for(var i=0;i<userItemsIds.length;i++){
                var obj = new Object();
                obj.userid=userItemsIds[i].id ;
                obj.username=userItemsIds[i].caption;
                obj.datefrom=this.iptDESFrom.getUIValue();
                obj.dateto=this.iptDESTo.getUIValue();




                if(o=='1'){
                    nuv.push({"id":obj.userid, "caption":obj.username, "datefrom":obj.datefrom, "dateto":this.iptDESTo.getUIValue()});
                    /* ENHANCEMENT 20120802 RHODA - Redmine # 882  */
                    g_userItem.push({"id":obj.userid, "caption":obj.username, "datefrom":obj.datefrom, "dateto":this.iptDESTo.getUIValue()});
                }else{
                    this.lstSelectedUDP.updateItem(obj.userid,{"datefrom":this.iptDESFrom.getUIValue(), "dateto":this.iptDESTo.getUIValue()});
                }
                
                //edit jr
                this.lstRemovedUDP.removeItems(userItemsIds[i].id);
                //end edit
                
                enablebutton = true;




            }
            if (enablebutton == true) {
//                SPA.btnRemoveUDP.setDisabled(true);
                SPA.btnSaveUDP.setDisabled(false);
                /* DELETE START 20120802 RHODA - Redmine # 882  */
                /* ENHANCEMENT START 20120802 JOVSAB - Redmine # 882  */
                //this.iptExpiryFromUDP.setUIValue(this.iptDESFrom.getUIValue());
                //this.iptExpiryToUDP.setUIValue(this.iptDESTo.getUIValue());
                /* ENHANCEMENT END 20120802 */
                /* DELETE END 20120802 RHODA*/
            }else{
//                SPA.btnRemoveUDP.setDisabled(true);
                SPA.btnSaveUDP.setDisabled(true);
            }
           
            this.lstSelectedUDP.insertItems(nuv,null,false);
            this.lstSelectedUDP.refresh();
            this.lstDESUser.refresh();
            this.dlgUDPSetDate.hide();
        },        
        _iptdesfrom_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            return false;
        },        
        _iptdesto_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            return false;
        },        
        _iptexpiryfromudp_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            return false;
        },        
        _iptexpirytoudp_onchange: function (profile, oldValue, newValue) {
            // On change of expiry input, update global var g_userItem;
            var id = this.lstSelectedUDP.getUIValue();
            var ctr;
            for (ctr in g_userItem) {
                if (id == g_userItem[ctr].id) {
                    g_userItem[ctr].dateto = newValue;
                    g_userItem[ctr].updated = true;
                }
            }
            // ENHANCEMENT START 20120907: Rhoda - Redmine 882
            SPA.lstSelectedUDP.setItems(g_userItem);
            SPA.lstSelectedUDP.refresh();
            // ENHANCEMENT END 20120907: Lele - Redmine 882
        }, 
        _btnsearchudp_onclick:function (profile, e, src, value) {
            var ns = this, items=[];
            var url = "/eON/autosearchuserdp.json";
            var o=new Object();
            o.companyId=this.lblCompanyId.getCaption();
            o.userId=this.lblUserId.getCaption();
            o.userName=this.iptSearchUserNameUDP.getUIValue();
            o.companyType=this.cbiCompanyType.getUIValue();
            o.roleName=this.cbiEditRole.getUIValue();
            o.roleId=this.cbiEditRole.getItems()[0].id;
            linb.Ajax(url,o,
            function(response){
                validateResponse(response);
                var obj = _.unserialize(response);
                var items=[];
                for(var i=0; i<obj.result.length; i++){
                    items.push({"id":obj.result[i].userId, "caption":obj.result[i].userName});
                }
                SPA.lstAvailableUDP.setItems(items);
            },
            function(msg){
            linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
        },        
        _iptsearchusernameudp_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            var ns = this, items=[];
            var url = "/eON/autosearchuserdp.json";
            var o=new Object();
            o.companyId=this.lblCompanyId.getCaption();
            o.userId=this.lblUserId.getCaption();
            o.userName=this.iptSearchUserNameUDP.getUIValue();
            o.companyType=this.cbiCompanyType.getUIValue();
            o.roleId=this.cbiEditRole.getItems()[0].id;
            o.roleName=this.cbiEditRole.getUIValue();
            linb.Ajax(url,o,
            function(response){
                validateResponse(response);
                var obj = _.unserialize(response);
                var items=[];
                for(var i=0; i<obj.result.length; i++){
                    items.push({"id":obj.result[i].userId, "caption":obj.result[i].userName});
                }
                SPA.lstAvailableUDP.setItems(items);
            },
            function(msg){
            linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
        },        
        _cbinewrole_afteruivalueset:function (profile, oldValue, newValue) {
            if (newValue.toLowerCase()=="seller") {
                this.lstNewCategory.setDisabled(false);
                this.lstNewCategory.refresh();
                this.newCalendarHighlightChk.setDisabled(false);
            } else if(newValue.toLowerCase()=="seller_admin"){
                this.newCalendarHighlightChk.setDisabled(false);
            } else {
                this.newCalendarHighlightChk.setDisabled(true);
                this.lstNewCategory.setDisabled(true);
                this.lstNewCategory.refresh();
            }
        },        
        _iptdlgsearchcompname_onhotkeyup:function (profile, key, control, shift, alt, e, src) {
            if (SPA.iptDlgSearchCompName.getUIValue().length<1)
                SPA.btnSearchCompOk.setDisabled(true);
            else
                SPA.btnSearchCompOk.setDisabled(false);
            SPA._searchcompanyname(this.iptDlgSearchCompName.getUIValue());
        },        
        _iptdlgsearchcompname_onhotkeydown:function (profile, key, control, shift, alt, e, src) {
            if (SPA.iptDlgSearchCompName.getUIValue().length<1)
                SPA.btnSearchCompOk.setDisabled(true);
            else
                SPA.btnSearchCompOk.setDisabled(false);
            SPA._searchcompanyname(this.iptDlgSearchCompName.getUIValue());
        },        
        _iptcompname_onhotkeyup:function (profile, key, control, shift, alt, e, src) {
        }, 
        _cbicompanytype_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            return false;
        }, 
        _iptemailadd_onblur:function (profile) {
            var oldValue = SPA.iptEmailAdd.getUIValue();
            if(oldValue.length>0) {
            var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
                if(reg.test(oldValue) == false) {
                    alert(linb.Locale[g_LangKey].app.caption['alertinvalidemailadd']);
                    return false;
                }
            }
        }, 
        _iptnewmobemailadd_onblur:function (profile) {
            var oldValue = SPA.iptNewMobEmailAdd.getUIValue();
            if(oldValue.length>0) {
            var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
                if(reg.test(oldValue) == false) {
                    alert(linb.Locale[g_LangKey].app.caption['alertinvalidmobileemailadd']);
                    return false;
                }
            }
        },        
        _iptnewemailadd_onblur:function (profile) {
            var oldValue = SPA.iptNewEmailAdd.getUIValue();
            if(oldValue.length>0) {
            var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
                if(reg.test(oldValue) == false) {
                    alert(linb.Locale[g_LangKey].app.caption['alertinvalidemailadd']);
                    return false;
                }
            }
        },        
        _cbinewrole_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            return false;
        }, 
        _cbieditrole_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            return false;
        },        
        _ipteditemailadd_onblur:function (profile) {
            var oldValue = SPA.iptEditEmailAdd.getUIValue();
            if(oldValue.length>0) {
            var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
                if(reg.test(oldValue) == false) {
                    alert(linb.Locale[g_LangKey].app.caption['alertinvalidemailadd']);
                    return false;
                }
            }
        },        
        _ipteditmobemailadd_onblur:function (profile) {
            var oldValue = SPA.iptEditMobEmailAdd.getUIValue();
            if(oldValue.length>0) {
            var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
                if(reg.test(oldValue) == false) {
                    alert(linb.Locale[g_LangKey].app.caption['alertinvalidmobileemailadd']);
                    return false;
                }
            }
        },        
        _cbiadminrole_onhotkeypress:function (profile, key, control, shift, alt, e, src) {
            return false;
        }, 
        _iptadminemailadd_onblur:function (profile) {
            var oldValue = SPA.iptAdminEmailAdd.getUIValue();
            if(oldValue.length>0) {
            var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
                if(reg.test(oldValue) == false) {
                    alert(linb.Locale[g_LangKey].app.caption['alertinvalidemailadd']);
                    return false;
                }
            }
        },        
        _iptadminmobemailadd_onblur:function (profile) {
            var oldValue = SPA.iptAdminMobEmailAdd.getUIValue();
            if(oldValue.length>0) {
            var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
                if(reg.test(oldValue) == false) {
                    alert(linb.Locale[g_LangKey].app.caption['alertinvalidmobileemailadd']);
                    return false;
                }
            }
        },        
        _btnclosecategory_onclick:function (profile, e, src, value) {
            this.dialogNewUserCategory.hide();
        }, 
       setAdminRoleId: function() {
           var items = [];
           if(this.cbiCompanyType.getUIValue()=="Seller")
               items.push({"id":"2", "caption":"Seller Admin"});
           else if (this.cbiCompanyType.getUIValue()=="Buyer")
               items.push({"id":"4", "caption":"Buyer Admin"});
           else
               items.push({"id":"0", "caption":"Error"});
           this.cbiAdminRole.setItems(items);
//           this.cbiAdminRole.setValue(items[0].caption);
       },         
       _iptFaxNo_beforekeypress:function (profile, caret, key, ctrl, shift, alt, e, src) {
           if(key.length==1){
               if(ctrl && (key=='c'||key=='x'||key=='a'))
                   return true;




               var len=profile.boxing().getUIValue().length - (caret[1]-caret[0]);
               if (len >= 20)
                   return false;
           }
       }, 
       _lstselectedcompdealingpattern_onitemselected:function (profile, item, src) {
           var value = profile.boxing().getUIValue();
           if(_.arr.indexOf(value.split(';'),item.id)!=-1)
               this.btnCompDealingPatternRemove.setDisabled(false);
           else
               this.btnCompDealingPatternRemove.setDisabled(true);               
       },              
       _upbtn_onclick:function (profile, e, src, value) {
           this.sortUpDown("up");
       }, 
       _downbtn_onclick:function (profile, e, src, value) {
           this.sortUpDown("down");
       }, 
       clearFailedCompanyBackgroundColor: function() {
            var allFields = new Array();
           allFields[0] = SPA.iptCompName;
           allFields[1] = SPA.iptShortName;
           allFields[2] = SPA.cbiCompanyType;
           allFields[3] = SPA.iptTelNo;
           allFields[4] = SPA.iptFaxNo;
           clearBackGroundColor(allFields); 
       },              
       clearFailedUserBackgroundColor: function(mode) {
             var allFields = new Array();
           allFields[0] = mode=="edit"?SPA.iptEditName:SPA.iptNewFirstName;
           allFields[1] = mode=="edit"?SPA.iptEditShortName:SPA.iptNewShortName;
           allFields[2] = mode=="edit"?SPA.iptEditTelNo:SPA.iptNewTelNo;
           allFields[3] = mode=="edit"?SPA.iptEditFaxNo:SPA.iptNewFaxNo;
           allFields[4] = mode=="edit"?SPA.iptEditMobileNo:SPA.iptNewMobileNo;
           allFields[5] = mode=="edit"?SPA.iptEditMobEmailAdd:SPA.iptNewMobEmailAdd;
           allFields[6] = mode=="edit"?SPA.iptEditUserName:SPA.iptNewUserId;
           allFields[7] = mode=="edit"?SPA.iptEditPassword:SPA.iptNewPassword;
           allFields[8] = mode=="edit"?SPA.lstEditCategory:SPA.lstNewCategory;
           allFields[9] = mode=="edit"?SPA.cbiEditRole:SPA.cbiNewRole;
           clearBackGroundColor(allFields); 
        }, 
        clearFailedAdminBackgroundColor: function() {
            var allFields = new Array();
            allFields[0] = this.iptAdminFirstname;
            allFields[1] = this.iptAdminShortname;
            allFields[2] = this.cbiAdminRole;
            allFields[3] = this.iptAdminUsername;
            allFields[4] = this.iptAdminPassword; 
            clearBackGroundColor(allFields);
        }, 
        validateRemoveUser: function (roleId, userId1, userId2, dateFrom, dateTo) {
            var url = "/eON/validateRemoveUDP.json";
            url = url + "?roleId=" + roleId;
            url = url + "&userId1=" + userId1;
            url = url + "&userId2=" + userId2;
            url = url + "&dateFrom=" + dateFrom;
            url = url + "&dateTo=" + dateTo;
            linb.Ajax(url, null,
            function(response){
                validateResponse(response);
                var obj = _.unserialize(response);
                if (obj.result == "false") {
                    alert(linb.Locale[g_LangKey].app.caption['alerterrorremovingudp']);
//                    SPA.btnRemoveUDP.setDisabled(false);
                }
                else
                    SPA.removeUser(userId2, dateFrom, dateTo);
            },
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
        }, 
        removeUser: function (userId, dateFrom, dateTo) {
            removedItems.push([userId,dateFrom,dateTo]);
            this.lstSelectedUDP.removeItems(userId);
        }, 
        sortUpDown: function(direction) {
            if (this.lstEditCategory.getItems().length == 0 ||
                    this.lstEditCategory.getUIValue() == null ||
                    this.lstEditCategory.getUIValue().length == 0)
                return;
            var uiVal = this.lstEditCategory.getUIValue();
            var rightSelects = this.lstEditCategory.getUIValue().split(";");
            var rightItems = this.lstEditCategory.getItems();
            var rightIds = new Array();
            var rightSelIds = new Array();
            for(var i=0; i<rightItems.length; i++){
                _.arr.insertAny(rightIds, rightItems[i].id);
                if(_.arr.indexOf(rightSelects, rightItems[i].id) >= 0) {
                    _.arr.insertAny(rightSelIds, rightItems[i].id);
                }
            }




            var dontMove = false;
            if (direction == "up") {
                for(var i=0; i<rightSelIds.length; i++) {
                    var thisId = rightSelIds[i];
                    var curPos = _.arr.indexOf(rightIds, thisId);
                    var nexPos = curPos - 1;
                    if (nexPos < 0) {
                        dontMove = true;
                        break;
                    }
            
                    _.arr.removeFrom(rightIds, curPos);
                    _.arr.insertAny(rightIds, thisId, nexPos);
                }
            }
            else {
                for(var i=rightSelIds.length-1; i>=0; i--) {
                    var thisId = rightSelIds[i];
                    var curPos = _.arr.indexOf(rightIds, thisId);
                    var nexPos = curPos + 1;
                    if (nexPos > rightIds.length-1) {
                        dontMove = true;
                        break;
                    }
                    _.arr.removeFrom(rightIds, curPos);
                    _.arr.insertAny(rightIds, thisId, nexPos);
                }
            }
       
            if (dontMove) return;
            var newRightItems = new Array();
            for(var i=0; i<rightIds.length; i++) {
                var thisId = rightIds[i];
                for(var j=0; j<rightItems.length; j++) {
                    if(thisId == rightItems[j].id)
                        newRightItems.push(rightItems[j]);
                }
            }
            this.lstEditCategory.setItems(newRightItems);
            this.lstEditCategory.refresh();
            this.lstEditCategory.setUIValue(uiVal);
        },
		_tosUsrDwnld_onclick : function()
		{
            var url = "/eON/DownloadUserDetails.json";

            submitTheForm(url, "_json",null , linb.Locale[linb.getLang()].app.caption['confirmDownload']);

           
		}
    }
});