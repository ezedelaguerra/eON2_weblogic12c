
Class('App.category', 'linb.Com',{
    Instance:{
        autoDestroy:true, 

        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Panel)
                .host(host,"pnlCategory")
                .setZIndex(1)
                .setCaption("Category Maintenance")
            );
            
            host.pnlCategory.append((new linb.UI.Group)
                .host(host,"grpCategory")
                .setLeft(151)
                .setTop(90)
                .setWidth(320)
                .setHeight(340)
                .setCaption("")
                .setToggleBtn(false)
            );
            
            host.grpCategory.append((new linb.UI.Input)
                .host(host,"iptCategory")
                .setLeft(30)
                .setTop(20)
                .setWidth(140)
                .setHeight(23)
                .setDisabled(true)
            );
            
            host.grpCategory.append((new linb.UI.Button)
                .host(host,"btnAddNew")
                .setLeft(171)
                .setTop(20)
                .setCaption("Add Category")
                .onClick("_btnaddnew_onclick")
            );
            
            host.grpCategory.append((new linb.UI.Label)
                .host(host,"label5")
                .setLeft(30)
                .setTop(60)
                .setCaption("<b>Available categories</b>")
            );
            
            host.grpCategory.append((new linb.UI.List)
                .host(host,"lstCategories")
                .setLeft(30)
                .setTop(80)
                .setWidth(270)
                .setHeight(180)
                .setValue("a")
            );
            
            host.grpCategory.append((new linb.UI.Button)
                .host(host,"btnSave")
                .setLeft(30)
                .setTop(280)
                .setWidth(60)
                .setCaption("Save")
                .onClick("_btnsave_onclick")
            );
            
            host.grpCategory.append((new linb.UI.Button)
                .host(host,"btnDelete")
                .setLeft(100)
                .setTop(280)
                .setWidth(60)
                .setCaption("Delete")
                .setDisabled(true)
                .onClick("_btndelete_onclick")
            );
            
            
            
            host.grpCategory.append((new linb.UI.Button)
                .host(host,"btnCancel")
                .setLeft(170)
                .setTop(280)
                .setWidth(60)
                .setCaption("Cancel")
                .onClick("_btncancel_onclick")
            );
            
            host.grpCategory.append((new linb.UI.Button)
                .host(host,"btnClose")
                .setLeft(240)
                .setTop(280)
                .setWidth(60)
                .setCaption("Close")
                .onClick("_btnclose_onclick")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events:{"onReady":"_onready","onRender":"_onrender"}, 
        _onrender:function() {
        	categories = this;
        }, 
        _onready:function(page, threadid){
            categories=page;
            this.loadExistingCategories();
        },
        customAppend:function(parent,subId,left,top){
            return false;
        }, 
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        }, 
        _btnaddnew_onclick:function (profile, e, src, value) {
        	categories.iptCategory.setDisabled(false);
        	categories.iptCategory.setZIndex(1);
        }, 
        _btndelete_onclick:function (profile, e, src, value) {
            alert('delete me');
        }, 
        _btnsave_onclick:function (profile, e, src, value) {
        	var categoryName = categories.iptCategory.getUIValue();
            categories.saveNewCategory(categoryName);
        }, 
        _btncancel_onclick:function (profile, e, src, value) {
            alert('cancel me');
        }, 
        _btnclose_onclick:function (profile, e, src, value) {
            categories.hide();
        },
        loadExistingCategories: function() {
        	var ns = this;
            var url = "/eON/loadadmincategories.json";
            var param = new Object();
            param.companyFlagType = "1"; // NO take effect. Just to fill the parameter
            linb.Ajax(url,param,
                function(response) {
            		validateResponse(response);
                    var obj = null;
                    obj = _.unserialize(response);
                    var categoryitems=[];
                    for(var i=0; i<obj.category.length;i++) {
                        categoryitems.push({"id":obj.category[i].categoryId, "caption":obj.category[i].description});
                    }
                    categories.lstCategories.setItems(categoryitems);
                    categories.lstCategories.refresh();
            },
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
        },
        saveNewCategory: function(categoryName) {
        	if (categories.checkCategoryIfExist(categoryName)){
        		alert('Category name exist.');
        		return;
        	}        		
        	var ns = this;
        	var url = "/eON/saveadmincategories.json";
            var obj = new Object();
            obj.description = categoryName;
            linb.Ajax(url,obj,
                function(response){
            		validateResponse(response);
                	categories.loadExistingCategories();                    
            },
            function(msg){
                linb.message("失敗： " + msg);
            },
            null,
            {method:'POST',retry:0}).start();
        },
        checkCategoryIfExist: function(categoryName) {
        	var items = [];
        	items = categories.lstCategories.getItems();
        	for(var i = 0; i<items.length; i++) {
        		if (categoryName.toLowerCase() == items[i].caption.toLowerCase())
        			return true;
        	}
        	return false;
        }
    }
});