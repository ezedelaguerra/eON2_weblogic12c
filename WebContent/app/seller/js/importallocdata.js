var g_allocParam;
Class('App.importallocdata', 'linb.Com',{
    Instance:{
            customAppend:function(){

            var self=this, prop = this.properties;

            if(prop.fromRegion) {
                self.pnlImportAllocation.setFromRegion(prop.fromRegion);
            }

            if(!self.pnlImportAllocation.get(0).renderId) {
                self.pnlImportAllocation.render();
            }

            self.pnlImportAllocation.show(self.parent, true);
        }, 
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
                .host(host,"pnlImportAllocation")
                .setLeft(60)
                .setTop(30)
                .setWidth(830)
                .setHeight(450)
                .setShadow(false)
                .setResizer(false)
                .setCaption("$app.caption.importalloc")
                .setMinBtn(false)
                .setMaxBtn(false)
                .setCloseBtn(false)
                .setPinBtn(false)
            );
            
            host.pnlImportAllocation.append((new linb.UI.Group)
                .host(host,"grpAllocGrid")
                .setLeft(10)
                .setTop(11)
                .setWidth(790)
                .setHeight(359)
                .setCaption("")
                .setToggleBtn(false)
            );
            
            host.grpAllocGrid.append((new linb.UI.Pane)
                .host(host,"paneSigmaGrid")
                .setLeft(10)
                .setTop(0)
                .setWidth(770)
                .setHeight(340)
            );
            
            host.pnlImportAllocation.append((new linb.UI.Button)
                .host(host,"btnImport")
                .setLeft(630)
                .setTop(380)
                .setWidth(80)
                .setCaption("$app.caption.imports")
                .onClick("_btnimport_onclick")
            );
            
            host.pnlImportAllocation.append((new linb.UI.Button)
                .host(host,"btnCancel")
                .setLeft(720)
                .setTop(380)
                .setWidth(80)
                .setCaption("$app.caption.cancel")
                .onClick("_btncancel_onclick")
            );
            
            host.pnlImportAllocation.append((new linb.UI.Button)
                .host(host,"btnSearch")
                .setLeft(530)
                .setTop(380)
                .setWidth(90)
                .setCaption("$app.caption.search")
                .onClick("_btnsearch_onclick")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events:
        {
            "onReady":"_onready","onRender":"_onrender"
        }, 
        _onRender:function(page, threadid){
            importallocdata = page;
        }, 
        _onready: function() {
            importallocdata = this;
        }, 
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        }, 
        _btnsearch_onclick:function (profile, e, src, value) {
        	var filter;
        	if(g_userRole=="ROLE_SELLER")
            	filter = "App.filter_akaden";
            else
            	filter = "App.admin_filter_akaden";
            this.setProperties({
                fromRegion:linb.use(src).cssRegion(true)
            });            
            var ns=this;
                linb.ComFactory.newCom(
                	filter,
                    function(){
                        importallocdata._excom2 = this;
                        importallocdata._excom2.hiddenSheetTypeId.setCaption(10003);
                        importallocdata._excom2.hiddenDealingPatternId.setCaption(1);
                        importallocdata._excom2.groupDate.setCaption("$app.caption.deliveryDate"); 
                        this.setEvents({'onSearchAkaden' : function(result) {
                            importallocdata.loadImportedAllocationData(result);
                        }});
                        this.show(null, importallocdata.paneSubCom);
                    }
                );
        }, 
        onSearchEvent: function (result) {
            importallocdata.loadImportedAllocationData(result);
        }, 
        _btncancel_onclick:function (profile, e, src, value) {
            this.pnlImportAllocation.destroy();
        }, 
        loadImportedAllocationData:function(result) {
            g_allocParam = result;
            var obj = new Object();
            obj.startDate = g_allocParam.startDate;
            obj.endDate = g_allocParam.endDate;
            obj.selectedSellerID = g_orderSheetParam.selectedSellerID;
            obj.selectedBuyerCompany = g_allocParam.selectedBuyerCompany;
            obj.selectedBuyerID = g_allocParam.selectedBuyerID;
            obj.selectedDate = g_allocParam.selectedDate;
            obj.categoryId = g_categoryId; //g_allocParam.categoryId;
            obj.sheetTypeId = g_allocParam.sheetTypeId;
            obj.allDatesView = g_allocParam.allDatesView;
            obj.checkDBOrder = g_allocParam.checkDBOrder;
            obj.datesViewBuyerID = g_orderSheetParam.datesViewBuyerID;
            linb.Ajax(
                "/eON/akaden/setAkadenAllocParameter.json",
                obj,
                function (response) {
                	validateResponse(response);
                    var paneSigmaGridHtml = importallocdata.paneSigmaGrid.getHtml();
                    if (paneSigmaGridHtml == "") {
                        importallocdata.paneSigmaGrid.setHtml("<iframe id='importAllocIframe' name='importAllocIframe' frameborder=0 marginheight=0 marginwidth=0 scrolling=0 width=100% height=99% src='../jsp/akadenImportAllocGrid.jsp'></iframe>");
                    } else {
                        importallocdata.loadAllocationTable();
                    }
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
        loadAllocationTable: function () {
            var f = document.getElementById('importAllocIframe');
            f.contentWindow.location.reload();
        }, 
        _btnimport_onclick: function(profile, e, src, value) {
            var paneSigmaGridHtml = importallocdata.paneSigmaGrid.getHtml();
            if(paneSigmaGridHtml == "") {
            	alert(linb.Locale[g_LangKey].app.caption['alertfailedtotransfer']);
            }
            else {
                var f = document.getElementById('importAllocIframe');
                f.contentWindow.onImport();
            }
        }
    }
});