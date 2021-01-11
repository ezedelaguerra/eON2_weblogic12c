
Class('Specify_Class_Name_Here', 'linb.Com',{
    Instance:{
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};

            append((new linb.UI.Dialog)
                .host(host,"dialog45")
                .setLeft(460)
                .setTop(80)
                .setHeight(250)
                .setCaption("dialog45")
            );

            host.dialog45.append((new linb.UI.Group)
                .host(host,"group247")
                .setLeft(10)
                .setTop(10)
                .setWidth(270)
                .setHeight(190)
                .setCaption("group247")
            );

            return children;
            // ]]code created by jsLinb UI Builder
        },
        events:{},
        iniResource:function(com, threadid){
        },
        iniExComs:function(com, hreadid){
        }
    }
});