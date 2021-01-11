
Class('App.aboutEON', 'linb.Com',{
    Instance:{
	
		customAppend:function(){
		    var self=this, prop = this.properties;
		    
		    if(prop.fromRegion)
		        self.dialogAboutEon.setFromRegion(prop.fromRegion);
		
		    if(!self.dialogAboutEon.get(0).renderId)
		        self.dialogAboutEon.render();
		
		    self.dialogAboutEon.show(self.parent, true);
		}, 
	
	
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
                .host(host,"dialogAboutEon")
                .setLeft(0)
                .setTop(0)
                .setHeight(250)
                .setResizer(false)
                .setCaption("About eON ...")
                .setMinBtn(false)
                .setMaxBtn(false)
                .setCloseBtn(false)
                .setPinBtn(false)
            );
            
            host.dialogAboutEon.append((new linb.UI.Label)
                .host(host,"label5")
                .setLeft(20)
                .setTop(30)
                .setWidth(30)
                .setCaption("eON")
            );
            
            host.dialogAboutEon.append((new linb.UI.Label)
                .host(host,"label6")
                .setLeft(20)
                .setTop(70)
                .setWidth(80)
                .setCaption("Version 1.0.0")
                .setHAlign("left")
            );
            
            host.dialogAboutEon.append((new linb.UI.Label)
                .host(host,"label7")
                .setLeft(20)
                .setTop(90)
                .setWidth(100)
                .setCaption("Buil Id: 20100528")
            );
            
            host.dialogAboutEon.append((new linb.UI.Label)
                .host(host,"label8")
                .setLeft(20)
                .setTop(130)
                .setWidth(250)
                .setCaption("(c) Copyright FRC 2010. All Rights Reserved.")
            );
            
            host.dialogAboutEon.append((new linb.UI.Button)
                .host(host,"btnOk")
                .setLeft(200)
                .setTop(160)
                .setWidth(80)
                .setCaption("OK")
                .onClick("_btnok_onclick")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        }, 
        events:{}, 
        iniResource:function(com, threadid){
        }, 
        iniExComs:function(com, hreadid){
        }, 
        _btnok_onclick:function (profile, e, src, value) {
//            window.close();
        	this.dialogAboutEon.hide();
        }
    }
});