
Class('App.addnewsku', 'linb.Com',{
    Instance:{
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Panel)
                .host(host,"panelAddSKU")
                .setDock("none")
                .setLeft(190)
                .setTop(30)
                .setWidth(470)
                .setHeight(500)
                .setZIndex(1)
                .setVisibility("visible")
                .setCaption("Add SKU Info")
            );
            
            host.panelAddSKU.append((new linb.UI.Group)
                .host(host,"group5")
                .setLeft(20)
                .setTop(10)
                .setWidth(420)
                .setHeight(90)
                .setCaption("Add SKU Info")
            );
            
            host.group5.append((new linb.UI.Label)
                .host(host,"label12")
                .setLeft(10)
                .setTop(10)
                .setCaption("Column Name:")
            );
            
            host.group5.append((new linb.UI.Input)
                .host(host,"input12")
                .setLeft(140)
                .setTop(40)
                .setWidth(140)
            );
            
            host.group5.append((new linb.UI.Label)
                .host(host,"label16")
                .setLeft(10)
                .setTop(40)
                .setCaption("Column Number:")
            );
            
            host.group5.append((new linb.UI.ComboInput)
                .host(host,"cbiColumnName")
                .setLeft(140)
                .setTop(10)
                .setWidth(260)
                .setItems([{"id":"a", "caption":"Sellername"}, {"id":"b", "caption":"Groupname"}, {"id":"c", "caption":"Marketname"}, {"id":"d", "caption":"Brandname"}, {"id":"e", "caption":"LeadTime"}])
                .setValue(null)
            );
            
            host.panelAddSKU.append((new linb.UI.Group)
                .host(host,"group6")
                .setLeft(20)
                .setTop(110)
                .setWidth(420)
                .setHeight(110)
                .setCaption("Value")
            );
            
            host.group6.append((new linb.UI.Input)
                .host(host,"input10")
                .setLeft(140)
                .setTop(10)
                .setValue("10")
            );
            
            host.group6.append((new linb.UI.CheckBox)
                .host(host,"checkbox1")
                .setLeft(20)
                .setTop(10)
                .setWidth(110)
                .setCaption("Default Value")
            );
            
            host.group6.append((new linb.UI.ComboInput)
                .host(host,"comboinput12")
                .setLeft(140)
                .setTop(35)
                .setItems([{"id":"a", "caption":"item a", "image":"img/demo.gif"}, {"id":"b", "caption":"item b", "image":"img/demo.gif"}, {"id":"c", "caption":"item c", "image":"img/demo.gif"}, {"id":"d", "caption":"item d", "image":"img/demo.gif"}])
                .setValue("a")
            );
            
            host.group6.append((new linb.UI.Label)
                .host(host,"label17")
                .setLeft(270)
                .setTop(35)
                .setWidth(20)
                .setCaption("<font size=4>+</font>")
            );
            
            host.group6.append((new linb.UI.Input)
                .host(host,"input13")
                .setLeft(300)
                .setTop(35)
                .setWidth(100)
            );
            
            host.group6.append((new linb.UI.CheckBox)
                .host(host,"checkbox2")
                .setLeft(20)
                .setTop(35)
                .setCaption("Price + N")
            );
            
            host.group6.append((new linb.UI.ComboInput)
                .host(host,"comboinput13")
                .setLeft(140)
                .setTop(60)
                .setItems([{"id":"a", "caption":"item a", "image":"img/demo.gif"}, {"id":"b", "caption":"item b", "image":"img/demo.gif"}, {"id":"c", "caption":"item c", "image":"img/demo.gif"}, {"id":"d", "caption":"item d", "image":"img/demo.gif"}])
                .setValue("a")
            );
            
            host.group6.append((new linb.UI.Label)
                .host(host,"label18")
                .setLeft(270)
                .setTop(65)
                .setWidth(20)
                .setCaption("<b>X</b>")
            );
            
            host.group6.append((new linb.UI.Input)
                .host(host,"input14")
                .setLeft(300)
                .setTop(60)
                .setWidth(100)
            );
            
            host.group6.append((new linb.UI.CheckBox)
                .host(host,"checkbox3")
                .setLeft(20)
                .setTop(60)
                .setWidth(100)
                .setCaption("Price * N")
            );
            
            host.panelAddSKU.append((new linb.UI.Group)
                .host(host,"group7")
                .setLeft(20)
                .setTop(230)
                .setWidth(420)
                .setHeight(190)
                .setCaption("Visibility")
            );
            
            host.group7.append((new linb.UI.TreeGrid)
                .host(host,"treegrid25")
                .setDock("none")
                .setLeft(30)
                .setTop(10)
                .setWidth(370)
                .setHeight(150)
                .setAltRowsBg(true)
                .setEditable(true)
                .setRowHandler(false)
                .setRowResizer(false)
                .setHeader([{"id":"col1", "width":200, "type":"label", "caption":"User", "cellStyle":"text-align:center;"}, {"id":"col2", "width":60, "type":"checkbox", "caption":"View"}, {"id":"col3", "width":60, "type":"checkbox", "caption":"Edit"}])
                .setRows([{"cells":[{"value":"Seller"}, {"value":false}, {"value":false}], "id":"a"}, {"cells":[{"value":"Seller Admin"}, {"value":false}, {"value":false}], "id":"b"}, {"cells":[{"value":"Buyer"}, {"value":false}, {"value":false}], "sub":[["sub1", "sub2", "sub3", "sub4"]], "id":"c"}, {"cells":[{"value":"Buyer Admin"}, {"value":false}, {"value":false}], "sub":[["sub1", "sub2", "sub3", "sub4"]], "id":"d"}, {"cells":[{"value":"Chouai"}, {"value":false}, {"value":false}], "sub":[["sub1", "sub2", "sub3", "sub4"]], "id":"e"}])
            );
            
            host.panelAddSKU.append((new linb.UI.Button)
                .host(host,"btnAdd")
                .setLeft(260)
                .setTop(430)
                .setWidth(80)
                .setCaption("ADD")
                .onClick("_btnadd_onclick")
            );
            
            host.panelAddSKU.append((new linb.UI.Button)
                .host(host,"btnCancel")
                .setLeft(350)
                .setTop(430)
                .setWidth(70)
                .setCaption("CANCEL")
                .onClick("_btncancel_onclick")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events:{}, 
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        }, 
        _btnadd_onclick:function (profile, e, src, value) {
            this.panelAddSKU.hide();
        }, 
        _btncancel_onclick:function (profile, e, src, value) {
            this.panelAddSKU.hide();
        }
    }
});