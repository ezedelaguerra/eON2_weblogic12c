
Class('App.contactus', 'linb.Com',{
    Instance:{
		customAppend:function(){
	    var self=this, prop = this.properties;
		    if(prop.fromRegion)
		        self.dialogContacts.setFromRegion(prop.fromRegion);
		
		    if(!self.dialogContacts.get(0).renderId)
		        self.dialogContacts.render();
		        self.dialogContacts.show(self.parent, true);
		},
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
                .host(host,"dialogContacts")
                .setLeft(390)
                .setTop(90)
                .setWidth(320)
                .setHeight(330)
                .setResizer(false)
                .setCaption("Contact Us")
                .setMinBtn(false)
                .setMaxBtn(false)
                .setPinBtn(false)
            );
            
            host.dialogContacts.append((new linb.UI.Group)
                .host(host,"group73")
                .setLeft(10)
                .setTop(10)
                .setWidth(290)
                .setHeight(270)
                .setCaption("FRC Contact details")
            );
            
            host.group73.append((new linb.UI.Label)
                .host(host,"label168")
                .setLeft(10)
                .setTop(20)
                .setCaption("<b>FRC Phone Number :</b>")
            );
            
            host.group73.append((new linb.UI.Label)
                .host(host,"label169")
                .setLeft(10)
                .setTop(100)
                .setCaption("<b>Email Address :</b>")
                .setHAlign("left")
            );
            
            host.group73.append((new linb.UI.Label)
                .host(host,"label170")
                .setLeft(10)
                .setTop(140)
                .setCaption("<b>Office Address :</b>")
                .setHAlign("left")
            );
            
            host.group73.append((new linb.UI.Label)
                .host(host,"label171")
                .setLeft(30)
                .setTop(40)
                .setWidth(210)
                .setCaption("81-03-5822-1566")
                .setHAlign("left")
            );
            
            host.group73.append((new linb.UI.Label)
                .host(host,"label172")
                .setLeft(30)
                .setTop(120)
                .setWidth(240)
                .setCaption("wating for details")
                .setHAlign("left")
            );
            
            host.group73.append((new linb.UI.Label)
                .host(host,"label173")
                .setLeft(30)
                .setTop(160)
                .setWidth(240)
                .setCaption("Ryukakusan Bldg. 4F. 2-5-12 Higashi-<br/>kanda Chiyoda-ku, Tokyo 101-0031 Japan")
                .setHAlign("left")
            );
            
            host.group73.append((new linb.UI.Label)
                .host(host,"label440")
                .setLeft(10)
                .setTop(60)
                .setCaption("<b>FAX Number</b>")
                .setHAlign("left")
            );
            
            host.group73.append((new linb.UI.Label)
                .host(host,"label441")
                .setLeft(30)
                .setTop(80)
                .setWidth(240)
                .setCaption("81-03-5822-1351")
                .setHAlign("left")
            );
            
            host.group73.append((new linb.UI.Button)
                .host(host,"button444")
                .setLeft(200)
                .setTop(220)
                .setWidth(70)
                .setCaption("Ok")
                .onClick("_button444_onclick")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events:{}, 
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        }, 
        _button312_onclick:function (profile, e, src, value) {
            this.dialogContacts.hide();
        }, 
        _button444_onclick:function (profile, e, src, value) {
            this.dialogContacts.hide();
        }
    }
});