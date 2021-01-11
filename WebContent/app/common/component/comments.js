/*
 	Copyright Freshremix Asia Software Corporation
	date		name		version	changes
	==============================================
	20120725	lele		v11		Redmine 131 - Change of display in address bar of Comments
	
 * */

var g_idList = [];
var g_delStat;
var g_unreadMsg = 0; // global variable for unread messages

Class('App.comments', 'linb.Com', {
    autoDestroy:true, 
    Instance: {
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Pane)
                .host(host,"commentsPane")
                .setLeft(0)
                .setTop(59)
                .setWidth(1080)
                .setHeight(520)
            );
            
            host.commentsPane.append((new linb.UI.Block)
                .host(host,"btnsBlock")
                .setDock("top")
                .setHeight(50)
            );
            
            host.btnsBlock.append((new linb.UI.Button)
                .host(host,"btnNew")
                .setLeft(10)
                .setTop(15)
                .setWidth(110)
//                .setCaption("$app.caption.newmessage")
                .setCaption("$app.caption.eonnewmessage")
                .setImage("../../common/img/new.gif")
                .onClick("_btnnew_onclick")
            );
            
            host.btnsBlock.append((new linb.UI.Button)
                .host(host,"btnDelete")
                .setDisabled(true)
                .setLeft(130)
                .setTop(15)
                .setWidth(70)
                .setCaption("$app.caption.deletes")
                .setImage("../../common/img/delete.gif")
                .onClick("_btnDelete_onclick")
            );
            
            host.commentsPane.append((new linb.UI.Panel)
                .host(host,"inboxOutboxPanel")
                .setCaption("")
            );
            
            host.inboxOutboxPanel.append((new linb.UI.Tabs)
                .host(host,"tabComments")
                .setItems([{"id":"a", "caption":"<b>$app.caption.inbox</b>", "image":"../../common/img/inbox.gif"}, {"id":"b", "caption":"<b>$app.caption.outbox</b>", "image":"../../common/img/outbox.gif"}])
                .setDock("top")
                .setHeight(441)
                .setValue("a")
                .onItemSelected("_tabcomments_onitemselected")
            );
            
            host.tabComments.append((new linb.UI.TreeGrid)
                .host(host,"outboxGrid")
                .setSelMode("single")
                .setAltRowsBg(true)
                .setRowHandler(false)
                .setHeader([{"id":"recipientsAddress", "width":150, "type":"label", "caption":"<b>$app.caption.emailTo</b>"}, {"id":"subject", "width":650, "type":"label", "caption":"<b>$app.caption.messages</b>"}, {"id":"address", "width":220, "type":"label", "hidden":true, "caption":"<b>$app.caption.address</b>"}, {"id":"date", "width":150, "type":"label", "caption":"<b>$app.caption.datesent</b>"}, {"id":"id", "type":"label", "hidden":true, "width":80, "caption":"id"}, {"id":"message", "type":"label", "hidden":true, "width":80, "caption":"message"}])
                .setRows({})
                .onRowSelected("_outboxGrid_onRowSelected")
                .onDblclickRow("_outboxGrid_onDblclickRow")
                .setCustomStyle({"KEY":"cursor:pointer"})
            , 'b');
            
            host.tabComments.append((new linb.UI.TreeGrid)
                .host(host,"inboxGrid")
                .setDock("top")
                .setHeight(360)
                .setSelMode("single")
                .setAltRowsBg(true)
                .setRowHandler(false)
                .setHeader([{"id":"sender", "width":150, "type":"label", "caption":"<b>$app.caption.sender</b>"}, {"id":"subject", "width":650, "type":"label", "caption":"<b>$app.caption.messages</b>"}, {"id":"address", "width":220, "type":"label", "hidden":true, "caption":"<b>$app.caption.address</b>"}, {"id":"date", "width":150, "type":"label", "caption":"<b>$app.caption.datesent</b>"}, {"id":"id", "type":"label", "hidden":true, "width":80, "caption":"id"}, {"id":"message", "type":"label", "hidden":true, "width":80, "caption":"message"}, {"id":"sender_id", "type":"label", "hidden":true, "width":80, "caption":"sender_id"}, {"id":"recipient_id", "type":"label", "hidden":true, "width":80, "caption":"recipient_id"}])
                .onRowSelected("_inboxGrid_onRowSelected")
                .onDblclickRow("_inboxGrid_onDblclickRow")
                .setCustomStyle({"KEY":"cursor:pointer;"})
            , 'a');
            
            host.commentsPane.append((new linb.UI.PageBar)
                .host(host,"pagebar")
                .setLeft(20)
                .setTop(480)
                .onClick("_pagebar_onclick")
            );
            
            host.commentsPane.append((new linb.UI.Label)
                .host(host,"lblMessageCounter")
                .setTop(485)
                .setWidth(200)
                .setRight(20)
                .setCaption("lblMessageCounter")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events: {
            "onReady" : "_onready", "onRender":"_onRender", "onDestroy":"_onDestroy"
        }, 
        iniResource: function(com, threadid) {
        }, 
        iniExComs: function(com, hreadid) {
        }, 
        _onDestroy: function () {

        }, 
        _onRender:function(page, threadid){
            comments = page;
            if(g_clientWidth > comments.commentsPane.getWidth()){
            	comments.commentsPane.setWidth(g_clientWidth);
            }
            if(g_clientHeight > comments.commentsPane.getHeight() + 60){
            	comments.commentsPane.setHeight(g_clientHeight - 60);
        	}
        }, 
        _onready: function() {
            comments = this;
            g_idList = [];
            if (g_userRole == "ROLE_SELLER" || g_userRole == "ROLE_SELLER_ADMIN"){
            	sellerMenu.menubar_seller.setItems(getMenuItems());
                comments.sellerSheetsMenuDisable();
            }
            if (g_userRole == "ROLE_BUYER" || g_userRole == "ROLE_BUYER_ADMIN"){
        		buyerMenu.menubar.setItems(getMenuItems());
                comments.buyerSheetsMenuDisable();
            }
        }, 
        initialize: function () {
            var url = "/eON/comments/init.json";
            linb.Ajax(
                url,
                null,
                function (response) {
                	validateResponse(response);
                    var result = _.unserialize(response);
                    comments._rows = result.inboxItems;
                    comments.setPageCount(15, 'a');
                },
                function(msg) { linb.message("失敗： " + msg); },
                null,
                { method : 'GET', retry : 0 }
            ).start();
        }, 
        getUnreadMessages : function () {
            var url = "/eON/comments/countUnreadMsg.json";
            linb.Ajax(
                url,
                null,
                function (response) {
                	validateResponse(response);
                    var result = _.unserialize(response);
                    g_unreadMsg = result.count;
                    comments.renderUnreadMsg();
                },
                function(msg) { linb.message("失敗： " + msg); },
                null,
                { method : 'GET', retry : 0 }
            ).start();
        }, 
        renderUnreadMsg: function () {
            var cap;
            if (g_unreadMsg == 0) cap = "<b>$app.caption.inbox</b>";
            else cap = "<b>$app.caption.inbox(" + g_unreadMsg + ")</b>";
            comments.tabComments.updateItem("a", {"caption":cap, "image":"../../common/img/inbox.gif"});
            comments.tabComments.setValue("a");
        }, 
        reloadMessages : function (selectedTab) {
        	comments.getUnreadMessages();
            var url = "/eON/comments/init.json";
            var param = new Object();
            param.selectedTab = selectedTab;
            selectedTab == 'a' ? comments.inboxGrid.busy() : comments.outboxGrid.busy();
            linb.Ajax(
                url,
                null,
                function (response) {
                	validateResponse(response);
                    var result = _.unserialize(response);
                    if (selectedTab == 'a') {
                    	comments.inboxGrid.setRows(result.inboxItems);
                        comments._rows = result.inboxItems;
                        comments.setPageCount(15, selectedTab);
                    }
                    else {
                        comments.outboxGrid.setRows(result.outboxItems);
                        comments._rows = result.outboxItems;
                        comments.setPageCount(15, selectedTab);
                    }
                    selectedTab=='a'?comments.inboxGrid.free():comments.outboxGrid.free();
                },
                function(msg) { linb.message("失敗： " + msg); },
                null,
                { method : 'GET', retry : 0 }
            ).start();
        }, 
        _btnnew_onclick: function (profile, e, src, value) {
            comments.disableActionButtons();
            var self=this;
            linb.ComFactory.newCom('App.DlgViewMail' ,function(){
                this.$parent=self;
                this
                .setProperties({
                    fromRegion:linb.use(src).cssRegion(true)
                })
                .setEvents('onSendNew', self._changeRow);
                this.viewItemGroup.setVisibility("hidden");
                this.editItemGroup.setVisibility("visible");
                this.editItemGroup.setCaption("$app.caption.newMail");
                this.show(linb([document.body]));
            });
        }, 
        _btnDelete_onclick: function (profile, e, src, value) {
            var res = confirm(linb.Locale[linb.getLang()].app.caption['confirmDelete']);
            if (res) {
                var url = "/eON/comments/delete.json";
                var param = new Object();
                param.selectedTab = this.tabComments.getUIValue();
                if (param.selectedTab == 'a') param.selectedId = this.inboxGrid.getActiveRow().cells[4].value;
                else param.selectedId = this.outboxGrid.getActiveRow().cells[4].value;
                //alert("param: " + param.selectedId);
                linb.Ajax(
                    url,
                    param,
                    function (response) {
                    	validateResponse(response);
                        comments.reloadMessages(param.selectedTab);
                        comments.btnDelete.setDisabled(true);
                    },
                    function(msg) { linb.message("失敗： " + msg); },
                    null,
                    { method : 'GET', retry : 0, reqType : 'form' }
                ).start();
            }
        },        _outboxGrid_onRowSelected: function (profile, row, src) {
            this.btnDelete.setDisabled(false);
            var exists = false;
            var id = row.cells[4].value;
            for (var i=0; i<g_idList.length; i++) {
                if (id == g_idList[i]) {
                    exists = true;
                    g_idList[i] = null;
                    break;
                }
            }
            if (exists == false) {
                g_idList.push(id);
            }
        },        _inboxGrid_onRowSelected: function (profile, row, src) {
            this.btnDelete.setDisabled(false);
        }, 
        _inboxGrid_onDblclickRow: function (profile, row, e, src) {
            var self=this;
            linb.ComFactory.newCom('App.DlgViewMail' ,function(){
                this.$parent=self;
                this
                .setProperties({
                    fromRegion:linb.use(src).cssRegion(true),
                    col1:row.cells[0].value, //sender
                    col2:row.cells[5].value, //message
                    // ENHANCEMENT START 20120725: Lele - Redmine 131
                    //col3:row.cells[1].value //subject
                    col3:row.cells[1].value, //subject
                    col4:row.cells[6].value //sender id
                    // ENHANCEMENT END 20120725:
                });
                this.show(linb([document.body]));
            });
            this._changeRow(row);
        }, 
        _outboxGrid_onDblclickRow: function (profile, row, e, src) {
            var self=this;
            linb.ComFactory.newCom('App.DlgViewMail' ,function(){
                this.$parent=self;
                this
                .setProperties({
                    fromRegion:linb.use(src).cssRegion(true),
                    col1:row.cells[0].value, //recipients
                    col2:row.cells[5].value, //message
                    col3:row.cells[1].value //subject
                });
                this.show(linb([document.body]));
            });
        }, 
        _changeRow: function (row) {
            var url = "/eON/comments/updateOpenStatus.json";
            var param = new Object();
            param.selectedId = this.inboxGrid.getActiveRow().cells[4].value;
            linb.Ajax(
                url,
                param,
                function (response) {
                	validateResponse(response);
                    comments.inboxGrid.updateCell(row.cells[0],{cellStyle:"font-weight:normal;color:black;"});
                    comments.inboxGrid.updateCell(row.cells[1],{cellStyle:"font-weight:normal;color:black;"});
                    comments.inboxGrid.updateCell(row.cells[2],{cellStyle:"font-weight:normal;color:black;"});
                    comments.inboxGrid.updateCell(row.cells[3],{cellStyle:"font-weight:normal;color:black;"});
                },
                function(msg) { linb.message("失敗： " + msg); },
                null,
                { method : 'GET', retry : 0, reqType : 'form' }
            ).start();
        }, 
        disableActionButtons: function () {
            g_delStat = this.btnDelete.getDisabled();
            comments.btnNew.setDisabled(true);
            comments.btnDelete.setDisabled(true);
        }, 
        enableActionButtons: function () {
            comments.btnNew.setDisabled(false);
            if (g_delStat) comments.btnDelete.setDisabled(true);
            else comments.btnDelete.setDisabled(false);
        }, 
        sellerSheetsMenuDisable: function (profile, row, e, src){
            sellerMenu.menubar_seller.updateItem('mnuHome', {disabled:false});
            sellerMenu.menubar_seller.updateItem('mnuOrdersheet', {disabled:false});
            sellerMenu.menubar_seller.updateItem('mnuAllocationsheet', {disabled:false});
            sellerMenu.menubar_seller.updateItem('mnuAkadensheet', {disabled:true});
            sellerMenu.menubar_seller.updateItem('mnuBillingsheet', {disabled:true});
            sellerMenu.menubar_seller.updateItem('mnuComments', {disabled:true});
            sellerMenu.menubar_seller.updateItem('mnuUserpreference', {disabled:false});
        }, 
        buyerSheetsMenuDisable: function (profile, row, e, src){
            buyerMenu.menubar.updateItem('mnuHome', {disabled:false});
            buyerMenu.menubar.updateItem('mnuOrdersheet', {disabled:false});
            buyerMenu.menubar.updateItem('mnuReceivedsheet', {disabled:false});
            buyerMenu.menubar.updateItem('mnuBillingsheet', {disabled:true});
            buyerMenu.menubar.updateItem('mnuComments', {disabled:true});
            buyerMenu.menubar.updateItem('mnuUserpreference', {disabled:false});
        }, 
        _pagebar_onclick:function (profile, page) {
            var tabIndex = this.tabComments.getUIValue();
            profile.boxing().setPage(page);
            comments.setTg(page, tabIndex);
        }, 
        setTg:function(index, tabIndex){
            var rows;
            if(tabIndex=='a') {
                rows=comments._rows.slice((index-1)*comments.count,index*comments.count);
                comments.inboxGrid.setRows(rows);
            }
            else {
                rows=comments._rows.slice((index-1)*comments.count,index*comments.count);
                comments.outboxGrid.setRows(rows);
            }
            comments.lblMessageCounter.setCaption('<i>' + rows.length + ' record(s) out of ' + comments._rows.length + '</i>');
        }, 
        setPageCount:function(count, tabIndex){
            comments.count=count;
            var page=parseInt((comments._rows.length+(comments.count-1))/comments.count);
            comments.pagebar.setValue([1,1,page].join(':')).setPage(1);
            comments.setTg(1, tabIndex);
        }, 
        _tabcomments_onitemselected:function (profile, item, src) {
        	comments.inboxGrid.refresh();
        	comments.outboxGrid.refresh();
        	comments.btnDelete.setDisabled(true);
            comments.reloadMessages(item.id);
        }
    }
});