/*
 	Copyright Freshremix Asia Software Corporation
	date		name		version	changes
	==============================================
	20120725	lele		v11		Redmine 131 - Change of display in address bar of Comments
	
 * */

Class('App.DlgSearchRecipient', 'linb.Com',{
    Instance:{
        //base Class for linb.Com
        base:["linb.UI"], 
        //requried class for the App
        required:["linb.UI.Dialog", "linb.UI.Input", "linb.UI.Button", "linb.UI.Div"], 
        //prepare data
        customAppend:function(){
            var self=this, prop = this.properties;
            
            if(prop.fromRegion)
                self.searchRecipientDialog.setFromRegion(prop.fromRegion);

            if(!self.searchRecipientDialog.get(0).renderId)
                self.searchRecipientDialog.render();

            //asy
            self.searchRecipientDialog.show(self.parent, true);
        }, 
        iniComponents:function() {
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
                .host(host,"searchRecipientDialog")
                .setLeft(310)
                .setTop(130)
                .setWidth(320)
                .setHeight(340)
                .setCaption("$app.caption.searhRecipientWindow")
                .setMinBtn(false)
                .setMaxBtn(false)
                .setResizer(false)
                .setCloseBtn(false)
                .setPinBtn(false)
            );
            
            host.searchRecipientDialog.append((new linb.UI.Group)
                .host(host,"nameGroup")
                .setLeft(10)
                .setTop(0)
                .setWidth(290)
                .setHeight(260)
                .setCaption("$app.caption.searhRecipientGroup")
            );
            
            host.nameGroup.append((new linb.UI.List)
                .host(host,"lstCompany")
                .setLeft(20)
                .setTop(30)
                .setHeight(200)
                .setDirtyMark(false)
                .setSelMode("multi")
                .onChange("_lstCompany_onchange")
                .onItemSelected("_lstCompany_onitemselected")
            );
            
            host.nameGroup.append((new linb.UI.List)
                .host(host,"lstUser")
                .setLeft(150)
                .setTop(30)
                .setHeight(200)
                .setSelMode("multi")
            );
            
            host.nameGroup.append((new linb.UI.Label)
                .host(host,"lblCompNames")
                .setLeft(20)
                .setTop(10)
                .setCaption("$app.caption.searhRecipientCompanyNames")
                .setHAlign("left")
            );
            
            host.nameGroup.append((new linb.UI.Label)
                .host(host,"lblUsernames")
                .setLeft(150)
                .setTop(10)
                .setCaption("$app.caption.searhRecipientUserNames")
                .setHAlign("left")
            );
            
            host.searchRecipientDialog.append((new linb.UI.Button)
                .host(host,"btnSelect")
                .setLeft(130)
                .setTop(270)
                .setWidth(70)
                .setCaption("$app.caption.searhRecipientSelect")
                .onClick("_btnSelect_onclick")
            );
            
            host.searchRecipientDialog.append((new linb.UI.Button)
                .host(host,"btnCancel")
                .setLeft(210)
                .setTop(270)
                .setWidth(70)
                .setCaption("$app.caption.cancel")
                .onClick("_btnCancel_onclick")
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
        	searchRecipient = page;
        },
        _onready: function() {
        	Class("Component");
        	searchRecipient = this;
        	var url = "/eON/comments/getCompanies.json";
        	linb.Ajax(
    	        url,
    	        null,
    	        function (response) {
    	        	validateResponse(response);
    	        	var result = _.unserialize(response);
    	        	searchRecipient.lstCompany.setItems(result.companies,true);
    	        }, 
		    	function(msg) { linb.message("失敗： " + msg); }, 
    	        null, 
    	        { method : 'GET', retry : 0 }
        	).start();
        },
        _btnCancel_onclick : function (profile, e, value) {
        	this.searchRecipientDialog.close();
        },
        _searchRecipientDialog_beforeclose:function (profile) {
            profile.boxing().hide();
            return false;
        }, 
        _searchRecipientDialog_onhotkey:function(profile, key, control, shift, alt) {
            if(key=='esc')
                profile.boxing().close();
        },
        _btnSelect_onclick : function (profile, e, value) {
        	//var emailAdds = this.getEmailAddress(this.lstUser.getUIValue());
        	var names = this.getNames(this.lstUser.getUIValue());
        	_.tryF(this.events.onRecipientSelect, [names], this.$parent);
        	searchRecipient.searchRecipientDialog.close();
        }, 
        _lstCompany_onchange : function (profile, oldValue, newValue) {
        	
        },
        allToBuyerIds : function (linbComp, newValue) {
        	var tmp = "";
        	if (newValue == "0" || newValue.startsWith("0;")) {
        		var items = linbComp.getItems();
        		for (var i=1; i<items.length; i++) {
        			tmp = tmp + items[i].id + ";";
        		}
        	} else {
        		tmp = newValue;
        	}
        	return tmp;
        },
        getEmailAddress : function (selectedId) {
        	var items = this.lstUser.getItems();
        	var selected_arr = selectedId.split(";");
        	var emailAdds = [];
        	for (var i=0; i<items.length; i++) {
    			var id = items[i].id;
    			for (var j=0; j<selected_arr.length; j++) {
    				var selId = selected_arr[j];
    				if (selId == items[i].id) {
    					if (items[i].pcEmailAddress != null)
    						emailAdds.push(items[i].pcEmailAddress);
        				if (items[i].mobileAddress != null) {
        					emailAdds.push(items[i].mobileAddress);
        				}
    				}
    			}
    		}
        	return emailAdds;
        },
        getNames : function (selectedId) {
        	var items = this.lstUser.getItems();
        	var selected_arr = selectedId.split(";");
        	var name = [];
        	for (var i=0; i<items.length; i++) {
    			var id = items[i].id;
    			for (var j=0; j<selected_arr.length; j++) {
    				var selId = selected_arr[j];
    				if (selId == items[i].id) {
    					var obj = new Object();
    					obj.caption = items[i].caption;
    					obj.id = items[i].id;
    					
    					var emailAdds = [];
    					if (items[i].pcEmailAddress != null)
    						emailAdds.push(items[i].pcEmailAddress);
        				if (items[i].mobileAddress != null) {
        					emailAdds.push(items[i].mobileAddress);
        				}
        				obj.emails = emailAdds;
        				
        				// ENHANCEMENT START 20120725: Lele - Redmine 131
        				obj.companyId = items[i].companyId;
        				obj.companyName = items[i].companyName;
        				// ENHANCEMENT END 20120725:
        				
    					name.push(obj);
    				}
    			}
    		}
        	return name;
        },
        _lstCompany_onitemselected : function (profile, item, src) {
        	var ids = "";
            var items = this.lstCompany.getItems();
            var value = profile.boxing().getUIValue();
            for(var i=0;i<items.length;i++){
                ids = ids + ";" + items[i].id;
            }
            if(item.id=='0'){                
                if(_.arr.indexOf(value.split(';'),item.id)!=-1){
                    profile.boxing().setUIValue(ids);
                }else{
                    profile.boxing().setUIValue('');
                }
            }else {
            	if(_.arr.indexOf(value.split(';'),'0')!=-1){
                	ids = ids.replace(';0', '');
                	ids = ids.replace(';'+item.id, '');
                    profile.boxing().setUIValue(ids);
                }else{
                	value = "0" + value;
                	var all = ids;
                	ids = ids.replaceAll(';', '');
                	value = value.replaceAll(';', '');
                	if(ids == value)
                	profile.boxing().setUIValue(all);
                }
            }
            
            //var companyId = searchRecipient.allToBuyerIds(this.lstCompany, newValue);
            var companyId = this.lstCompany.getUIValue();
        	if (companyId != "") {
	        	var url = "/eON/comments/getUsers.json";
	        	url = url + "?companyId=" + companyId;
	        	linb.Ajax(
	    	        url,
	    	        null,
	    	        function (response) {
	    	        	validateResponse(response);
	    	        	var result = _.unserialize(response);
	    	        	searchRecipient.lstUser.setItems(result.companies,true);
	    	        }, 
			    	function(msg) { linb.message("失敗： " + msg); }, 
	    	        null, 
	    	        { method : 'GET', retry : 0 }
	        	).start();
        	} else {
        		this.lstUser.setItems(null,true);
        	}
        }
    }
});