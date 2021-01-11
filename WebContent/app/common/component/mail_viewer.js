/*
 	Copyright Freshremix Asia Software Corporation
	date		name		version	changes
	==============================================
	20120725	lele		v11		Redmine 131 - Change of display in address bar of Comments
	
 * */

//var g_emailParam = [];

Class('App.DlgViewMail', 'linb.Com',{
    Instance:{
        //base Class for linb.Com
        base:["linb.UI"], 
        //requried class for the App
        required:["linb.UI.Dialog", "linb.UI.Input", "linb.UI.Button", "linb.UI.Div"], 
        //prepare data
        customAppend:function(){
            var self=this, prop = this.properties;
            
            if(prop.fromRegion)
                self.mailViewerDialog.setFromRegion(prop.fromRegion);

            if(!self.mailViewerDialog.get(0).renderId)
                self.mailViewerDialog.render();

            self.inpFrom.resetValue(prop.col1);
            self.txtMessage.resetValue(prop.col2);
            self.inpHiddenSubject.resetValue(prop.col3);
            // ENHANCEMENT 20120725: Lele - Redmine 131
            self.inptHiddenId.resetValue(prop.col4);
            //asy
            self.mailViewerDialog.show(self.parent, true);
        }, 
        iniComponents:function() {
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
                .host(host,"mailViewerDialog")
                .setLeft(200)
                .setTop(100)
                .setWidth(550)
                .setHeight(390)
                .setCaption("$app.caption.inbox")
                .onHotKeydown("_mailViewerDialog_onhotkey")
                .beforeClose("_mailViewerDialog_beforeclose")
                .setMinBtn(false)
                .setMaxBtn(false)
                .setResizer(false)
                .setPinBtn(false)
            );
            
            host.mailViewerDialog.append((new linb.UI.Group)
                .host(host,"viewItemGroup")
                .setLeft(10)
                .setTop(0)
                .setWidth(520)
                .setHeight(340)
                .setCaption("$app.caption.messageBody")
            );
            
            host.viewItemGroup.append((new linb.UI.Label)
                .host(host,"lblFrom")
                .setLeft(10)
                .setTop(23)
                .setWidth(80)
                .setCaption("$app.caption.from")
            );
            
            host.viewItemGroup.append((new linb.UI.Input)
                .host(host,"inpFrom")
                .setLeft(90)
                .setTop(20)
                .setWidth(380)
                .setDisabled(true)
            );
            
            host.viewItemGroup.append((new linb.UI.Input)
                .host(host,"txtMessage")
                .setLeft(90)
                .setTop(43)
                .setWidth(378)
                .setHeight(225)
                .setMultiLines(true)
                .setCustomStyle({"KEY":"background:#ffffff;border:solid 1px #888"})
                .setReadonly(true)
            );
            
            host.viewItemGroup.append((new linb.UI.Label)
                .host(host,"lblMessage")
                .setLeft(10)
                .setTop(45)
                .setWidth(80)
                .setCaption("$app.caption.messageBody")
            );
            
            host.viewItemGroup.append((new linb.UI.Button)
                .host(host,"btnReply")
                .setLeft(310)
                .setTop(280)
                .setWidth(70)
                .setCaption("$app.caption.reply")
                .onClick("_btnreply_onclick")
            );
            
            host.viewItemGroup.append((new linb.UI.Button)
                .host(host,"btnCancel")
                .setLeft(390)
                .setTop(280)
                .setWidth(70)
                .setCaption("$app.caption.cancel")
                .onClick("_btncancel_onclick")
            );
            
            host.viewItemGroup.append((new linb.UI.Input)
                .host(host,"inpHiddenSubject")
                .setLeft(10)
                .setTop(45)
                .setWidth(80)
                .setVisibility("hidden")
            );
            
            host.mailViewerDialog.append((new linb.UI.Group)
                .host(host,"editItemGroup")
                .setLeft(10)
                .setTop(10)
                .setWidth(520)
                .setHeight(330)
                .setCaption("$app.caption.edit")
                .setVisibility("hidden")
            );
            
            host.editItemGroup.append((new linb.UI.Label)
                .host(host,"eLblTo")
                .setLeft(10)
                .setTop(14)
                .setWidth(90)
                .setCaption("$app.caption.emailTo2")
            );
            
            host.editItemGroup.append((new linb.UI.Input)
                .host(host,"eInpTo")
                .setLeft(100)
                .setTop(10)
                .setWidth(370)
                .setDisabled(true)
            );
            
            // FORDELETION START 20120725: Lele - Redmine 131
//            host.editItemGroup.append((new linb.UI.Input)
//                .host(host,"inptHiddenIds")
//                .setLeft(100)
//                .setTop(10)
//                .setWidth(370)
//                .setVisibility("hidden")
//            );
            // FORDELETION END 20120725:
            
            // ENHANCEMENT START 20120725: Lele - Redmine 131
            host.editItemGroup.append((new linb.UI.Input)
                .host(host,"inptHiddenEmail")
                .setVisibility("hidden")
            );
            host.editItemGroup.append((new linb.UI.Input)
                .host(host,"inptHiddenId")
                .setVisibility("hidden")
            );
            // ENHANCEMENT END 20120725:
            
            host.editItemGroup.append((new linb.UI.Button)
                .host(host,"eBtnSearchRecipients")
                .setLeft(470)
                .setTop(11)
                .setWidth(30)
                .setHeight(20)
                .setCaption("")
                .setImage("../../common/img/search.gif")
                .onClick("_ebtnSearchRecipients_onclick")
            );
            
            host.editItemGroup.append((new linb.UI.Label)
                .host(host,"eLblSubject")
                .setLeft(10)
                .setTop(35)
                .setWidth(90)
                .setCaption("$app.caption.subject")
            );
            
            host.editItemGroup.append((new linb.UI.Input)
                .host(host,"eInpSubject")
                .setLeft(100)
                .setTop(33)
                .setWidth(370)
                .setMaxlength(4000)
            );
            
            host.editItemGroup.append((new linb.UI.Label)
                .host(host,"eLblMessage")
                .setLeft(10)
                .setTop(58)
                .setWidth(90)
                .setCaption("$app.caption.messageBody")
            );
            
            host.editItemGroup.append((new linb.UI.Input)
                .host(host,"eTxtMessage")
                .setLeft(100)
                .setTop(57)
                .setMultiLines(true)
                .setWidth(368)
                .setHeight(200)
                .setCustomStyle({"KEY":"background:#ffffff;border:solid 1px #888"})
            );
            
            host.editItemGroup.append((new linb.UI.Button)
                .host(host,"eBtnSend")
                .setLeft(310)
                .setTop(270)
                .setWidth(70)
                .setCaption("$app.caption.send")
                .onClick("_ebtnSend_onclick")
            );
            
            host.editItemGroup.append((new linb.UI.Button)
                .host(host,"eBtnCancel")
                .setLeft(390)
                .setTop(270)
                .setWidth(70)
                .setCaption("$app.caption.cancel")
                .onClick("_ebtnCancel_onclick")
            );
            
            host.editItemGroup.append((new linb.UI.Input)
                    .host(host,"hdnIsReply")
                    .setLeft(100)
                    .setTop(10)
                    .setWidth(370)
                    .setVisibility("hidden")
                    .setValue("0")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events: {
            "onReady" : "_onready", "onRender":"_onRender", "onDestroy":"_onDestroy"
        },
        _onDestroy : function () {
        	
        },
        _onRender:function(page, threadid) {
        	mailViewer = page;
        	var tab = comments.tabComments.getUIValue();
        	if(tab == 'b') {
        		mailViewer.btnReply.setDisabled(true);
        		this.lblFrom.setCaption(linb.Locale[linb.getLang()].app.caption['emailTo']);
        		this.mailViewerDialog.setCaption(linb.Locale[linb.getLang()].app.caption['outbox']);
        	}
        },
        _btnreply_onclick : function (profile, e, value) {
            this.viewItemGroup.setVisibility("hidden");
            this.editItemGroup.setVisibility("visible");
            this.hdnIsReply.setValue("1");
            
            this.eInpTo.setUIValue(this.inpFrom.getUIValue());
            this.eInpSubject.setUIValue(this.constructReplySubject(this.inpHiddenSubject.getUIValue()));
            var msg = this.constructReplyMessage(this.txtMessage.getUIValue());
            this.eTxtMessage.setUIValue(msg);
        }, 
        constructReplyMessage : function (content) {
        	var newContent = "\n\n========================\nFrom: " + this.inpFrom.getUIValue() + "\n";
        	newContent = newContent + "Subject: " + this.eInpSubject.getUIValue() + "\n";
        	newContent = newContent + content;
        	return newContent;
        },
        constructReplySubject : function (subject) {
        	if (subject.startsWith("RE:"))
        		return subject;
        	else
        		return "RE: " + subject;
        },
        _ebtnSend_onclick : function (profile, e, value) {
        	
        	var inpt = this.eInpTo.getUIValue();
        	if (inpt == '') {
        		alert("There must be at least one name in Recipient box.");
        		return
        	}
        	var url = "/eON/comments/sendEmail.json";
        	var param = new Object();
        	param.toAddress = this.eInpTo.getUIValue();
        	// ENHANCEMENT START 20120725: Lele - Redmine 131
        	param.toEmail = this.inptHiddenEmail.getUIValue();
        	param.toId = this.inptHiddenId.getUIValue();
        	// ENHANCEMENT END 20120725: 
        	param.subject = this.eInpSubject.getUIValue();
        	param.message = this.eTxtMessage.getUIValue();
        	param.replyStatus = this.hdnIsReply.getValue();
        	linb.Ajax(
    	        url,
    	        param,
    	        function (response) {
    	        	var result = _.unserialize(response);
    	        	validateResponse(response);
    	        	if (result.fail != 'true') {
    	        		mailViewer.mailViewerDialog.close();
    	        	}
    	        }, 
		    	function(msg) { linb.message("失敗： " + msg); }, 
    	        null, 
    	        { method : 'POST', retry : 0, reqType : 'form' }
        	).start();
        },
        _btncancel_onclick : function (profile, e, value) {
        	this.mailViewerDialog.close();
        },
        _ebtnCancel_onclick : function (profile, e, value) {
        	this.mailViewerDialog.close();
        },
        _mailViewerDialog_beforeclose:function (profile) {
        	comments.enableActionButtons();
            profile.boxing().hide();
            return false;
        }, 
        _mailViewerDialog_onhotkey:function(profile, key, control, shift, alt) {
            if(key=='esc')
                profile.boxing().close();
        },
        _ebtnSearchRecipients_onclick : function (profile, e, value) {
        	var self=this;
            linb.ComFactory.newCom('App.DlgSearchRecipient' ,function(){
                this.$parent=self;
                this.setEvents('onRecipientSelect', self._onRecipientSelect);
                this.show(linb([document.body]));
            });
        }, _onRecipientSelect : function (resultParameter) {
        	// FORDELETION START 20120725: Lele - Redmine 131
//        	var names = this.eInpTo.getUIValue();
//        	var ending = names.charAt(names.length-1);
//        	if (ending != "" && ending != ";")
//        		names = names + "; ";
//        	for (var i=0; i<resultParameter.length; i++) {
//        		names += resultParameter[i].caption;
//        		if (i != resultParameter.length -1) {
//        			names += "; ";
//        		}
//        	}
//        	this.eInpTo.setUIValue(names);
        	// FORDELETION END 20120725:
        	
        	// ENHANCEMENT START 20120725: Lele - Redmine 131
        	var names = this.eInpTo.getUIValue();
        	var emails = this.inptHiddenEmail.getUIValue();
        	var ids = this.inptHiddenId.getUIValue();
        	var ending = names.charAt(names.length-1);
        	if (ending != "" && ending != ";") {
        		names = names + "; ";
        		emails = emails + "; ";
        		ids = ids + "; ";
        	}
        	for (var i=0; i<resultParameter.length; i++) {
        		names += resultParameter[i].caption + "<" + resultParameter[i].companyName + ">";
        		emails += resultParameter[i].emails[0];
        		ids += resultParameter[i].id;
        		if (i != resultParameter.length -1) {
        			names += "; ";
        			emails += "; ";
        			ids += "; ";
        		}
        	}
        	this.eInpTo.setUIValue(names);
        	this.inptHiddenEmail.setUIValue(emails);
        	this.inptHiddenId.setUIValue(ids);
        	// ENHANCEMENT END 20120725: 
        }
    }
});