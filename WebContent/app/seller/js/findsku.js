
Class('App.findsku', 'linb.Com',{
    Instance:{
	 	
		//busy icon when dialog box is displayed so user can't click outside the dialog box
		customAppend:function(){
	    var self=this, prop = this.properties;
	    
	    if(prop.fromRegion)
	        self.dlgFindSku.setFromRegion(prop.fromRegion);
	
	    if(!self.dlgFindSku.get(0).renderId)
	        self.dlgFindSku.render();
	    	self.dlgFindSku.show(self.parent, true);
		}, 
		
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
                .host(host,"dlgFindSku")
                .setLeft(400)
                .setTop(80)
                .setWidth(250)
                .setHeight(170)
                .setCaption("Find SKU")
                .setMinBtn(false)
                .setMaxBtn(false)
                .setPinBtn(false)
                .setResizer(false)
            );
            
            host.dlgFindSku.append((new linb.UI.Group)
                .host(host,"grpEnterSearchName")
                .setLeft(10)
                .setTop(10)
                .setWidth(220)
                .setHeight(100)
                .setCaption("Enter SKU Name")
            );
            
            host.grpEnterSearchName.append((new linb.UI.Input)
                .host(host,"txtSearchName")
                .setLeft(10)
                .setTop(10)
                .setWidth(190)
                .setHeight(23)
                .onChange("_txtsearchname_onchange")
            );
            
            host.grpEnterSearchName.append((new linb.UI.Button)
                .host(host,"btnFind")
                .setLeft(130)
                .setTop(45)
                .setWidth(70)
                .setCaption("Find")
                .setImage("../../common/img/search.gif")
                .onClick("_btnfind_onclick")
            );
            
            host.grpEnterSearchName.append((new linb.UI.Input)
                    .host(host,"hdnSearchName")
//                    .setLeft(110)
//                    .setTop(50)
                    .setVisibility("hidden")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events:{"onReady":"_onready","onDestroy":"_onDestroy"}, 
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        },
        _onready:function(page, threadid){
        	findSKU = this;
        	findSKU.hdnSearchName.value = "";
        },
        
        //start:jr
        _onDestroy : function () {
        	linb.UI.getFromDom('btnFindSku').setDisabled(false); 
        },
        //end:jr
        _txtsearchname_onchange:function (profile, oldValue, newValue) {
        	findSKU.hdnSearchName.value = newValue; 
        },
        _btnfind_onclick:function (profile, e, src, value){
            var obj = new Object();
          
//        	alert(masterList.categoriesTabPMF.getUIValue());
            obj.searchName = findSKU.hdnSearchName.value;
            obj.categoryId = g_orderSheetParam.categoryId;
            obj.btnClicked = "findSKU";
//            alert("call ajax");
            var url = "/eON/pmf/pmfSetSession.json";
//            var id = Math.random() * url.length;
            linb.Ajax(url, obj, //+ "?id=" + id, obj, 
                function (response) {
            	validateResponse(response);
                	var o = _.unserialize(response);
//                	alert("response: " + response);
//                	window.open('mastersheetlist.html', '_newWindow', 'left=10,top=100,width=1000,height=590,toolbar=no,resizable=0,status=0,location=no,menubar=no,scrollbars=0');
                	findSKU.fireEvent('onFindSku',["ok"]);
                }, 
                function(msg) {
                        linb.message("失敗： " + msg);
                    }, null, {
                        method : 'POST'
                    }
            ).start();
            
        	findSKU.hdnSearchName.value = "";
        	findSKU.dlgFindSku.hide();
        	linb.UI.getFromDom('btnFindSku').setDisabled(false);
        }
        
        
        
    }
});