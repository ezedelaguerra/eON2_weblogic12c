var g_isEdit=0;//toggle for edit
Class('App.setLockButtonStatusMaintenance', 'linb.Com',{
    autoDestroy:true,
    Instance:{    
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};

            append((new linb.UI.Panel)
                .host(host,"setLockButtonPanel")
                .setDock("none")
                .setLeft(180)
                .setTop(59)
                .setWidth(820)
                .setHeight(520)
                .setZIndex(1)
                .setCaption("<center><b>$app.caption.setlockbuttoncaption</b></center>")
            );

            host.setLockButtonPanel.append((new linb.UI.Group)
                .host(host,"grpColumn")
                .setLeft(10)
                .setTop(20)
                .setWidth(500)
                .setHeight(450)
                .setCaption("")
                .setToggleBtn(false)
            );
            
            host.grpColumn.append((new linb.UI.Label)
                .host(host,"lblStatus")
                .setLeft(20)
                .setTop(10)
                .setWidth(180)
                .setHAlign("left")
                .setCaption("<b>$app.caption.lockbuttonstatus</b>")
            );
            
            host.grpColumn.append((new linb.UI.RadioBox)
                .host(host,"rbLockButtonStatus")
                .setItems([{"id":"0", "caption":"$app.caption.enable"}, {"id":"1", "caption":"$app.caption.disable"}])
                .setLeft(30)
                .setTop(40)
                .setWidth(150)
                .setHeight(200)
                .setValue("1")
                .setDirtyMark(false)
                .onItemSelected("_rbLockButtonStatus_onitemselected")
                .setCustomStyle({"ITEMS":"overflow:visible", "KEY":"background:transparent;"})
                .onItemSelected("_rbLockButtonStatus_onitemselected")
            );
   
            host.grpColumn.append((new linb.UI.Button)
                .host(host,"btnSave")
                .setLeft(180)
                .setTop(375)
                .setWidth(70)
                .setCaption("$app.caption.save")
                .onClick("_btnSave_onclick")
            );
            
            host.grpColumn.append((new linb.UI.Button)
                .host(host,"btnClose")
                .setLeft(260)
                .setTop(375)
                .setWidth(70)
                .setCaption("$app.caption.close")
                .onClick("_btnClose_onclick")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        },
        events:{
            "onReady" : "_onready", "onRender":"_onrender", "onDestroy":"_ondestroy"
        },
        _ondestroy:function (com) {
            g_sortWindow = null;
        },
        _onrender:function(page, threadid){
        	lockButtonStatus = page;
        	this.loadProfitPreference();
        },
        _onready:function() {
        	lockButtonStatus = this;
        }, 
        _rbLockButtonStatus_onitemselected:function (profile, item, src) {
        	g_isEdit = 1;
        },
        _rbSellingPriceOption_onitemselected:function (profile, item, src) {
        	g_isEdit = 1;
        },
        _chkTotalSellingPrice_onchecked:function (profile, item, src) {
        	g_isEdit = 1;
        },
        _chkTotalProfit_onchecked:function (profile, item, src) {
        	g_isEdit = 1;
        },
        _chkTotalProfitPercent_onchecked:function (profile, item, src) {
        	g_isEdit = 1;
        },
        _btnClose_onclick:function (profile, e, src, value) {

        	if (lockButtonStatus.confirmNavigation()) {
	    		g_currentId = 0;
	            this.destroy();
        	}
        },
        _btnSave_onclick:function (profile, e, src, value) {
	    	var lockBtnStatusObj = new Object();
	    	lockBtnStatusObj.lockButtonStatus = lockButtonStatus.rbLockButtonStatus.getUIValue();
	    	
	    	linb.Ajax(
		        "/eON/userPref/saveLockButtonStatus.json",
		        lockBtnStatusObj,
		        function (response) {
	                validateResponse(response);
                    alert(linb.Locale[linb.getLang()].app.caption['alertsaved']);
                	g_isEdit = 0;
		        }, 
		        function(msg) {
		            linb.message("失敗： " + msg);
		        }, 
		        null, 
		        {
		        	method : 'POST',
		        	retry : 0
		        }
		    ).start(); 
        },
        loadProfitPreference : function () {
			var obj = new Object();
	        linb.Ajax(
    	        "/eON/userPref/loadLockButtonStatus.json",
    	        obj,
    	        function (response) {
    	        	//alert(response);
                    validateResponse(response);
                    var obj = new Object();
                    var items = new Array();
                    obj = _.unserialize(response);
                    lockBtnStatusObj = obj.lockBtnStatusObj;
                    lockButtonStatus.rbLockButtonStatus.setUIValue(lockBtnStatusObj.lockButtonStatus);
    	        }, 
    	        function(msg) {
    	                linb.message("失敗： " + msg);
    	        }, 
    	        null, 
    	        {
    	        	method : 'POST',
    	        	retry : 0
    	        }
    	    ).start(); 
        	
        },
        checkForDirtyTable : function () {
        	if(g_isEdit == 1)
        		return true;
        	else
        		return false;
        },
        confirmNavigation : function () {
        	if (lockButtonStatus.checkForDirtyTable()) {
        		var ans = confirm(linb.Locale[linb.getLang()].app.caption['confirmNavigation']);
        		if (ans) {
        			return true;
        		}
        		else return false;
        	}
        	return true;
        }
    }
});