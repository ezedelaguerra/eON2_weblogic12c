
Class('App.alertPopUp', 'linb.Com',{
    Instance:{
        autoDestroy:true, 

        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
                .host(host,"alertDialog")
                .setDomId("alertDialog")
                .setLeft(60)
                .setTop(30)
                .setWidth(610)
                .setHeight(310)
                .setResizer(false)
                .setCaption("Alert")
                .setMinBtn(false)
                .setMaxBtn(false)
                .setPinBtn(false)
                .setCustomStyle({"PANEL":"overflow:auto;"})
            );
            
            host.alertDialog.append((new linb.UI.Button)
                .host(host,"BTN_AGREE")
                .setDomId("BTN_AGREE")
                .setLeft(440)
                .setTop(220)
                .setWidth(140)
                .setVisibility("visible")
                .setCaption("OK")
                .onClick("BTN_onclick")
            );
            
            host.alertDialog.append((new linb.UI.Div)
                .host(host,"DIVCONTENT")
                .setDomId("DIVCONTENT")
                .setDock("center")
                .setLeft(20)
                .setTop(10)
                .setWidth(550)
                .setHeight(190)
                .setCustomStyle({"KEY":"background-color:#FFF;overflow:auto"})
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
         events:{"onReady":"_onready"}, 
        customAppend:function(parent,subId,left,top){
            return false;
        }, 
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        }, 
        _onready:function (com,threadid){
            // this.loadAlertDisplay(this);
        }, 
        BTN_onclick:function(profile, e, src, value)
        {
            this.destroy();
        }, 

        loadAlertDisplay: function(ui, content)
        {
            ui.DIVCONTENT.setHtml(content);
        }


    }


});