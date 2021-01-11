/*
 	Copyright Freshremix Asia Software Corporation
	date		name		version	changes
	==============================================
	20120913	lele		chrome	Redmine 880 - Chrome compatibility
 * */

Class('App.downloadExcel', 'linb.Com',{
	autoDestroy:true,
	    Instance:{
		customAppend:function(){
	    var self=this, prop = this.properties;
	    
	    if(prop.fromRegion)
	        self.downloadExcelPanel.setFromRegion(prop.fromRegion);
	
	    if(!self.downloadExcelPanel.get(0).renderId)
	        self.downloadExcelPanel.render();
	    	self.downloadExcelPanel.show(self.parent, true);
		},
        iniComponents:function(){
            // [[code created by jsLinb UI Builder
            var host=this, children=[], append=function(child){children.push(child.get(0))};
            
            append((new linb.UI.Dialog)
                .host(host,"downloadExcelPanel")
                .setDock("none")
                .setLeft(235)
                .setTop(10)
                .setWidth(520)
                .setHeight(560)
                .setMinBtn(false)
                .setMaxBtn(false)
                .setCloseBtn(false)
                .setPinBtn(false)
                .setCaption("<center><b>$app.caption.downloadexcelwindow</b></center>")
            );
            
            host.downloadExcelPanel.append((new linb.UI.Label)
                .host(host,"sunHidden")
                .setVisibility("hidden")
                .setCaption("$app.caption.sunday")
            );
            
            host.downloadExcelPanel.append((new linb.UI.Label)
                .host(host,"monHidden")
                .setVisibility("hidden")
                .setCaption("$app.caption.monday")
            );
            
            host.downloadExcelPanel.append((new linb.UI.Label)
                .host(host,"tueHidden")
                .setVisibility("hidden")
                .setCaption("$app.caption.tuesday")
            );
            
            host.downloadExcelPanel.append((new linb.UI.Label)
                .host(host,"wedHidden")
                .setVisibility("hidden")
                .setCaption("$app.caption.wednesday")
            );
            
            host.downloadExcelPanel.append((new linb.UI.Label)
                .host(host,"thuHidden")
                .setVisibility("hidden")
                .setCaption("$app.caption.thursday")
            );
            
            host.downloadExcelPanel.append((new linb.UI.Label)
                .host(host,"friHidden")
                .setVisibility("hidden")
                .setCaption("$app.caption.friday")
            );
            
            host.downloadExcelPanel.append((new linb.UI.Label)
                .host(host,"satHidden")
                .setVisibility("hidden")
                .setCaption("$app.caption.saturday")
            );
            
            host.downloadExcelPanel.append((new linb.UI.Group)
                .host(host,"dateGroup")
                .setLeft(21)
                .setTop(11)
                .setWidth(472)
                .setHeight(115)
                .setCaption("$app.caption.exceldategroup")
                .setToggleBtn(false)
                .setCustomStyle({"CAPTION":"font-weight:bold"})
            );
            
            host.dateGroup.append((new linb.UI.Label)
                .host(host,"date1Lbl1")
                .setLeft(25)
                .setTop(42)
                .setWidth(60)
                .setHeight(15)
                .setZIndex(2)
                .setCaption("1/1")
                .setHAlign("center")
                .setVAlign("middle")
                .setFontWeight("bold")
            );
            
            host.dateGroup.append((new linb.UI.Label)
                .host(host,"date1Lbl2")
                .setLeft(25)
                .setTop(55)
                .setWidth(60)
                .setHeight(15)
                .setZIndex(2)
                .setCaption("mon")
                .setHAlign("center")
                .setVAlign("middle")
                .setFontWeight("bold")
            );
            
            host.dateGroup.append((new linb.UI.Label)
                .host(host,"date2Lbl1")
                .setLeft(85)
                .setTop(42)
                .setWidth(60)
                .setHeight(15)
                .setZIndex(2)
                .setCaption("1/2")
                .setHAlign("center")
                .setVAlign("middle")
                .setFontWeight("bold")
            );
            
            host.dateGroup.append((new linb.UI.Label)
                .host(host,"date2Lbl2")
                .setLeft(85)
                .setTop(55)
                .setWidth(60)
                .setHeight(15)
                .setZIndex(2)
                .setCaption("tue")
                .setHAlign("center")
                .setVAlign("middle")
                .setFontWeight("bold")
            );
            
            host.dateGroup.append((new linb.UI.Label)
                .host(host,"date3Lbl1")
                .setLeft(145)
                .setTop(42)
                .setWidth(60)
                .setHeight(15)
                .setZIndex(2)
                .setCaption("1/3")
                .setHAlign("center")
                .setVAlign("middle")
                .setFontWeight("bold")
            );
            
            host.dateGroup.append((new linb.UI.Label)
                .host(host,"date3Lbl2")
                .setLeft(145)
                .setTop(55)
                .setWidth(60)
                .setHeight(15)
                .setZIndex(2)
                .setCaption("wed")
                .setHAlign("center")
                .setVAlign("middle")
                .setFontWeight("bold")
            );
            
            host.dateGroup.append((new linb.UI.Label)
                .host(host,"date4Lbl1")
                .setLeft(205)
                .setTop(42)
                .setWidth(60)
                .setHeight(15)
                .setZIndex(2)
                .setCaption("1/4")
                .setHAlign("center")
                .setVAlign("middle")
                .setFontWeight("bold")
            );
            
            host.dateGroup.append((new linb.UI.Label)
                .host(host,"date4Lbl2")
                .setLeft(205)
                .setTop(55)
                .setWidth(60)
                .setHeight(15)
                .setZIndex(2)
                .setCaption("thu")
                .setHAlign("center")
                .setVAlign("middle")
                .setFontWeight("bold")
            );
            
            host.dateGroup.append((new linb.UI.Label)
                .host(host,"date5Lbl1")
                .setLeft(265)
                .setTop(42)
                .setWidth(60)
                .setHeight(15)
                .setZIndex(2)
                .setCaption("1/5")
                .setHAlign("center")
                .setVAlign("middle")
                .setFontWeight("bold")
            );
            
            host.dateGroup.append((new linb.UI.Label)
                .host(host,"date5Lbl2")
                .setLeft(265)
                .setTop(55)
                .setWidth(60)
                .setHeight(15)
                .setZIndex(2)
                .setCaption("fri")
                .setHAlign("center")
                .setVAlign("middle")
                .setFontWeight("bold")
            );
            
            host.dateGroup.append((new linb.UI.Label)
                .host(host,"date6Lbl1")
                .setLeft(325)
                .setTop(42)
                .setWidth(60)
                .setHeight(15)
                .setZIndex(2)
                .setCaption("1/6")
                .setHAlign("center")
                .setVAlign("middle")
                .setFontWeight("bold")
            );
            
            host.dateGroup.append((new linb.UI.Label)
                .host(host,"date6Lbl2")
                .setLeft(325)
                .setTop(55)
                .setWidth(60)
                .setHeight(15)
                .setZIndex(2)
                .setCaption("sat")
                .setHAlign("center")
                .setVAlign("middle")
                .setFontWeight("bold")
            );
            
            host.dateGroup.append((new linb.UI.Label)
                .host(host,"date7Lbl1")
                .setLeft(385)
                .setTop(42)
                .setWidth(60)
                .setHeight(15)
                .setZIndex(2)
                .setCaption("1/7")
                .setHAlign("center")
                .setVAlign("middle")
                .setFontWeight("bold")
            );
            
            host.dateGroup.append((new linb.UI.Label)
                .host(host,"date7Lbl2")
                .setLeft(385)
                .setTop(55)
                .setWidth(60)
                .setHeight(15)
                .setZIndex(2)
                .setCaption("sun")
                .setHAlign("center")
                .setVAlign("middle")
                .setFontWeight("bold")
            );
            
            host.dateGroup.append((new linb.UI.CheckBox)
                .host(host,"allDatesBox")
                .setDirtyMark(false)
                .setLeft(40)
                .setTop(0)
                .setWidth(90)
                .setHeight(30)
                .setBorder(true)
                .setCaption("$app.caption.excelalldates")
                .onChecked("_alldatesbox_onchecked")
            );
            
            host.dateGroup.append((new linb.UI.CheckBox)
                .host(host,"date1Box")
                .setDirtyMark(false)
                .setLeft(40)
                .setTop(70)
                .setWidth(30)
                .setZIndex(2)
                .setCaption("")
                .setUIValue(true)
                .onChecked("_date1box_onchecked")
            );
            
            host.dateGroup.append((new linb.UI.CheckBox)
                .host(host,"date2Box")
                .setDirtyMark(false)
                .setLeft(100)
                .setTop(70)
                .setWidth(30)
                .setZIndex(2)
                .setCaption("")
                .onChecked("_date2box_onchecked")
            );
            
            host.dateGroup.append((new linb.UI.CheckBox)
                .host(host,"date3Box")
                .setDirtyMark(false)
                .setLeft(160)
                .setTop(70)
                .setWidth(30)
                .setZIndex(2)
                .setCaption("")
                .onChecked("_date3box_onchecked")
            );
            
            host.dateGroup.append((new linb.UI.CheckBox)
                .host(host,"date4Box")
                .setDirtyMark(false)
                .setLeft(220)
                .setTop(70)
                .setWidth(30)
                .setZIndex(2)
                .setCaption("")
                .onChecked("_date4box_onchecked")
            );
            
            host.dateGroup.append((new linb.UI.CheckBox)
                .host(host,"date5Box")
                .setDirtyMark(false)
                .setLeft(280)
                .setTop(70)
                .setWidth(30)
                .setZIndex(2)
                .setCaption("")
                .onChecked("_date5box_onchecked")
            );
            
            host.dateGroup.append((new linb.UI.CheckBox)
                .host(host,"date6Box")
                .setDirtyMark(false)
                .setLeft(340)
                .setTop(70)
                .setWidth(30)
                .setZIndex(2)
                .setCaption("")
                .onChecked("_date6box_onchecked")
            );
            
            host.dateGroup.append((new linb.UI.CheckBox)
                .host(host,"date7Box")
                .setDirtyMark(false)
                .setLeft(400)
                .setTop(70)
                .setWidth(30)
                .setZIndex(2)
                .setCaption("")
                .onChecked("_date7box_onchecked")
            );
            
            host.dateGroup.append((new linb.UI.Block)
                .host(host,"block1-1")
                .setLeft(25)
                .setTop(40)
                .setWidth(60)
                .setHeight(33)
                .setBorder(true)
                .setBackground("lightblue")
            );
            
            host.dateGroup.append((new linb.UI.Block)
                .host(host,"block2-1")
                .setLeft(85)
                .setTop(40)
                .setWidth(60)
                .setHeight(33)
                .setBorder(true)
                .setBackground("lightblue")
            );
            
            host.dateGroup.append((new linb.UI.Block)
                .host(host,"block3-1")
                .setLeft(145)
                .setTop(40)
                .setWidth(60)
                .setHeight(33)
                .setBorder(true)
                .setBackground("lightblue")
            );
            
            host.dateGroup.append((new linb.UI.Block)
                .host(host,"block4-1")
                .setLeft(205)
                .setTop(40)
                .setWidth(60)
                .setHeight(33)
                .setBorder(true)
                .setBackground("lightblue")
            );
            
            host.dateGroup.append((new linb.UI.Block)
                .host(host,"block5-1")
                .setLeft(265)
                .setTop(40)
                .setWidth(60)
                .setHeight(33)
                .setBorder(true)
                .setBackground("lightblue")
            );
            
            host.dateGroup.append((new linb.UI.Block)
                .host(host,"block6-1")
                .setLeft(325)
                .setTop(40)
                .setWidth(60)
                .setHeight(33)
                .setBorder(true)
                .setBackground("lightblue")
            );
            
            host.dateGroup.append((new linb.UI.Block)
                .host(host,"block7-1")
                .setLeft(385)
                .setTop(40)
                .setWidth(60)
                .setHeight(33)
                .setBorder(true)
                .setBackground("lightblue")
            );
            
            host.dateGroup.append((new linb.UI.Block)
                .host(host,"block1-2")
                .setLeft(25)
                .setTop(70)
                .setWidth(60)
                .setHeight(23)
                .setBorder(true)
                .setBackground("white")
            );
            
            host.dateGroup.append((new linb.UI.Block)
                .host(host,"block2-2")
                .setLeft(85)
                .setTop(70)
                .setWidth(60)
                .setHeight(23)
                .setBorder(true)
                .setBackground("white")
            );
            
            host.dateGroup.append((new linb.UI.Block)
                .host(host,"block3-2")
                .setLeft(145)
                .setTop(70)
                .setWidth(60)
                .setHeight(23)
                .setBorder(true)
                .setBackground("white")
            );
            
            host.dateGroup.append((new linb.UI.Block)
                .host(host,"block4-2")
                .setLeft(205)
                .setTop(70)
                .setWidth(60)
                .setHeight(23)
                .setBorder(true)
                .setBackground("white")
            );
            
            host.dateGroup.append((new linb.UI.Block)
                .host(host,"block5-2")
                .setLeft(265)
                .setTop(70)
                .setWidth(60)
                .setHeight(23)
                .setBorder(true)
                .setBackground("white")
            );
            
            host.dateGroup.append((new linb.UI.Block)
                .host(host,"block6-2")
                .setLeft(325)
                .setTop(70)
                .setWidth(60)
                .setHeight(23)
                .setBorder(true)
                .setBackground("white")
            );
            
            host.dateGroup.append((new linb.UI.Block)
                .host(host,"block7-2")
                .setLeft(385)
                .setTop(70)
                .setWidth(60)
                .setHeight(23)
                .setBorder(true)
                .setBackground("white")
            );
            
            host.downloadExcelPanel.append((new linb.UI.Group)
                .host(host,"SheetOptionGroup")
                .setLeft(21)
                .setTop(131)
                .setWidth(472)
                .setHeight(105)
                .setCaption("$app.caption.excelsheetgroup")
                .setToggleBtn(false)
                .setCustomStyle({"CAPTION":"font-weight: bold"})
            );
            
            host.SheetOptionGroup.append((new linb.UI.RadioBox)
                .host(host,"dateOptBox")
                .setDirtyMark(false)
                .setItems([{"id":"1", "caption":"$app.caption.excelonepersheet"}, {"id":"9", "caption":"$app.caption.excelallpersheet"}])
                .setLeft(20)
                .setTop(20)
                .setWidth(130)
                .setHeight(50)
                .setZIndex(2)
                .setValue("1")
                .setCustomStyle({"ITEM":"display:block", "KEY":"background:#transparent;scrollbars:0", "CAPTION":"font-size:12px;"})
            );
            
            host.SheetOptionGroup.append((new linb.UI.RadioBox)
                .host(host,"sellerOptBox")
                .setDirtyMark(false)
                .setItems([{"id":"1", "caption":"$app.caption.excelonepersheetseller"}, {"id":"9", "caption":"$app.caption.excelallpersheet"}])
                .setLeft(170)
                .setTop(20)
                .setWidth(130)
                .setHeight(50)
                .setZIndex(2)
                .setValue("1")
                .setCustomStyle({"ITEM":"display:block", "KEY":"background:#transparent;scrollbars:0", "CAPTION":"font-size:12px;"})
            );
            
            host.SheetOptionGroup.append((new linb.UI.RadioBox)
                .host(host,"buyerOptBox")
                .setDirtyMark(false)
                .setItems([{"id":"1", "caption":"$app.caption.excelonepersheetbuyer"}, {"id":"9", "caption":"$app.caption.excelallpersheet"}])
                .setLeft(320)
                .setTop(20)
                .setWidth(130)
                .setHeight(50)
                .setZIndex(2)
                .setValue("1")
                .setCustomStyle({"ITEM":"display:block", "KEY":"background:#transparent;scrollbars:0", "CAPTION":"font-size:12px;"})
            );
            
            host.SheetOptionGroup.append((new linb.UI.Label)
                .host(host,"dateBoxOptLbl")
                .setLeft(30)
                .setTop(5)
                .setWidth(100)
                .setZIndex(2)
                .setCaption("$app.caption.exceldateopt")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.SheetOptionGroup.append((new linb.UI.Label)
                .host(host,"sellerBoxOptLbl")
                .setLeft(180)
                .setTop(5)
                .setWidth(100)
                .setZIndex(2)
                .setCaption("$app.caption.excelselleropt")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.SheetOptionGroup.append((new linb.UI.Label)
                .host(host,"buyerBoxOptLbl")
                .setLeft(330)
                .setTop(5)
                .setWidth(100)
                .setZIndex(2)
                .setCaption("$app.caption.excelbuyeropt")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.SheetOptionGroup.append((new linb.UI.Block)
                .host(host,"dateBoxBlock")
                .setLeft(10)
                .setTop(0)
                .setWidth(150)
                .setHeight(80)
                .setBorderType("ridge")
            );
            
            host.SheetOptionGroup.append((new linb.UI.Block)
                .host(host,"sellerBoxBlock")
                .setLeft(160)
                .setTop(0)
                .setWidth(150)
                .setHeight(80)
                .setBorderType("ridge")
            );
            
            host.SheetOptionGroup.append((new linb.UI.Block)
                .host(host,"buyerBoxBlock")
                .setLeft(310)
                .setTop(0)
                .setWidth(150)
                .setHeight(80)
                .setBorderType("ridge")
            );
            
            host.downloadExcelPanel.append((new linb.UI.CheckBox)
                .host(host,"hasQtyBox")
                .setDirtyMark(false)
                .setLeft(21)
                .setTop(241)
                .setWidth(150)
                .setCaption("$app.caption.excelhasqty")
                .setDisabled(false)
            );
            
            host.downloadExcelPanel.append((new linb.UI.CheckBox)
                .host(host,"priceBox")
                .setDirtyMark(false)
                .setLeft(21)
                .setTop(261)
                .setCaption("$app.caption.priceDisplay")
                .setValue(true)
                .onChecked("_pricebox_onchecked")
            );
            
            host.downloadExcelPanel.append((new linb.UI.Group)
                .host(host,"priceGroup")
                .setLeft(21)
                .setTop(281)
                .setWidth(472)
                .setHeight(200)
                .setCaption("$app.caption.priceCompLabel00")
                .setToggleBtn(false)
                .setCustomStyle({"CAPTION":"font-weight: bold"})
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock1")
                .setLeft(130)
                .setTop(0)
                .setWidth(315)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Label)
                .host(host,"priceLbl1")
                .setLeft(220)
                .setTop(0)
                .setWidth(135)
                .setHeight(25)
                .setZIndex(2)
                .setCaption("$app.caption.priceCompLabel01")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock2-1")
                .setLeft(130)
                .setTop(25)
                .setWidth(210)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Label)
                .host(host,"priceLbl2-1")
                .setLeft(130)
                .setTop(25)
                .setWidth(210)
                .setHeight(25)
                .setZIndex(2)
                .setCaption("$app.caption.priceCompLabel02")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock2-2")
                .setLeft(340)
                .setTop(25)
                .setWidth(105)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock3-1")
                .setLeft(130)
                .setTop(50)
                .setWidth(105)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Label)
                .host(host,"priceLbl3-1")
                .setLeft(115)
                .setTop(50)
                .setWidth(135)
                .setHeight(25)
                .setZIndex(2)
                .setCaption("$app.caption.priceCompLabel03")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock3-2")
                .setLeft(235)
                .setTop(50)
                .setWidth(210)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock4-1")
                .setLeft(20)
                .setTop(75)
                .setWidth(110)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock4-2")
                .setLeft(130)
                .setTop(75)
                .setWidth(105)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Label)
                .host(host,"priceLbl4-2")
                .setLeft(115)
                .setTop(75)
                .setWidth(135)
                .setHeight(25)
                .setZIndex(2)
                .setCaption("$app.caption.priceCompLabel06")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock4-3")
                .setLeft(235)
                .setTop(75)
                .setWidth(105)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Label)
                .host(host,"priceLbl4-3")
                .setLeft(235)
                .setTop(75)
                .setWidth(105)
                .setHeight(25)
                .setZIndex(2)
                .setCaption("$app.caption.priceCompLabel07")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock4-4")
                .setLeft(340)
                .setTop(75)
                .setWidth(105)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Label)
                .host(host,"priceLbl4-4")
                .setLeft(340)
                .setTop(75)
                .setWidth(105)
                .setHeight(25)
                .setZIndex(2)
                .setCaption("$app.caption.priceCompLabel10")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock5-1")
                .setLeft(20)
                .setTop(100)
                .setWidth(110)
                .setHeight(75)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock5-2")
                .setLeft(130)
                .setTop(100)
                .setWidth(105)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Label)
                .host(host,"priceLbl5-2")
                .setLeft(115)
                .setTop(100)
                .setWidth(135)
                .setHeight(25)
                .setZIndex(2)
                .setCaption("$app.caption.priceCompLabel08")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock5-3")
                .setLeft(235)
                .setTop(100)
                .setWidth(105)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Label)
                .host(host,"priceLbl5-3")
                .setLeft(235)
                .setTop(100)
                .setWidth(105)
                .setHeight(25)
                .setZIndex(2)
                .setCaption("$app.caption.priceCompLabel07")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock5-4")
                .setLeft(340)
                .setTop(100)
                .setWidth(105)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Label)
                .host(host,"priceLbl5-4")
                .setLeft(340)
                .setTop(100)
                .setWidth(105)
                .setHeight(25)
                .setZIndex(2)
                .setCaption("$app.caption.priceCompLabel10")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock6-1")
                .setLeft(130)
                .setTop(125)
                .setWidth(105)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Label)
                .host(host,"priceLbl6-1")
                .setLeft(115)
                .setTop(125)
                .setWidth(135)
                .setHeight(25)
                .setZIndex(2)
                .setCaption("$app.caption.priceCompLabel06")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock6-2")
                .setLeft(235)
                .setTop(125)
                .setWidth(105)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Label)
                .host(host,"priceLbl6-2")
                .setLeft(235)
                .setTop(125)
                .setWidth(105)
                .setHeight(25)
                .setZIndex(2)
                .setCaption("$app.caption.priceCompLabel07")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock6-3")
                .setLeft(340)
                .setTop(125)
                .setWidth(105)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Label)
                .host(host,"priceLbl6-3")
                .setLeft(320)
                .setTop(125)
                .setWidth(150)
                .setHeight(25)
                .setZIndex(2)
                .setCaption("$app.caption.priceCompLabel11")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock7-1")
                .setLeft(130)
                .setTop(150)
                .setWidth(105)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Label)
                .host(host,"priceLbl7-1")
                .setLeft(115)
                .setTop(150)
                .setWidth(135)
                .setHeight(25)
                .setZIndex(2)
                .setCaption("$app.caption.priceCompLabel06")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock7-2")
                .setLeft(235)
                .setTop(150)
                .setWidth(105)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Label)
                .host(host,"priceLbl7-2")
                .setLeft(210)
                .setTop(150)
                .setWidth(150)
                .setHeight(25)
                .setZIndex(2)
                .setCaption("$app.caption.priceCompLabel09")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.priceGroup.append((new linb.UI.Block)
                .host(host,"priceBlock7-3")
                .setLeft(340)
                .setTop(150)
                .setWidth(105)
                .setHeight(25)
                .setBorderType("ridge")
            );
            
            host.priceGroup.append((new linb.UI.Label)
                .host(host,"priceLbl7-3")
                .setLeft(340)
                .setTop(150)
                .setWidth(105)
                .setHeight(25)
                .setZIndex(2)
                .setCaption("$app.caption.priceCompLabel10")
                .setHAlign("center")
                .setVAlign("middle")
            );
            
            host.priceGroup.append((new linb.UI.RadioBox)
                .host(host,"priceComputationBox")
                .setDirtyMark(false)
                .setItems([{"id":"1", "caption":"$app.caption.priceCompLabel04"},
                           {"id":"2", "caption":"$app.caption.priceCompLabel05"}, 
                           {"id":"3", "caption":"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"},
                           {"id":"4", "caption":"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"}])
                .setLeft(20)
                .setTop(75)
                .setWidth(100)
                .setHeight(105)
                .setZIndex(2)
                .setValue("1")
                .setCustomStyle({"ITEM":"display:block", "KEY":"background:#transparent;scrollbars:0"})
            );
            
            host.downloadExcelPanel.append((new linb.UI.Button)
                .host(host,"submitBtn")
                .setLeft(151)
                .setTop(491)
                .setWidth(80)
                .setCaption("$app.caption.excelsubmit")
                .setDisabled(true)
                .onClick("_submitbtn_onclick")
            );
                
            host.downloadExcelPanel.append((new linb.UI.Button)
                .host(host,"cancelBtn")
                .setLeft(281)
                .setTop(491)
                .setWidth(80)
                .setCaption("$app.caption.excelcancel")
                .onClick("_cancelbtn_onclick")
            );
            
            host.downloadExcelPanel.append((new linb.UI.Label)
                .host(host,"lblHiddenSheetTypeId")
                .setTop(1)
                .setWidth(1)
                .setLeft(1)
                .setCaption("")
                .setHAlign("center")
                .setVisibility("hidden")
            );
            
            return children;
            // ]]code created by jsLinb UI Builder
        },
        events:{
        	"onReady" : "_onready", "onRender":"_onrender", "onDestroy":"_ondestroy",
        	"beforeCreated":"_onbeforecreated"
        },
        _ondestroy:function (com) {
        },
        _onrender:function(page, threadid){
        	downloadExcel.loadExcelSettings(this);
        	//alert(downloadExcel.lblHiddenSheetTypeId.getCaption());
        	var sheetTypeId = downloadExcel.lblHiddenSheetTypeId.getCaption();
        	if (sheetTypeId == 10020 || sheetTypeId == 10006 || sheetTypeId == 10005) {
        		downloadExcel.hasQtyBox.setUIValue(false);
        		downloadExcel.hasQtyBox.setDisabled(true);
        	}
        	//alert("render");
        	
        	// ENHANCEMENT 20120913: Lele - Redmine 880
        	var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
            
            if (is_chrome) {
            	downloadExcel.downloadExcelPanel.setWidth(540);
            	downloadExcel.dateGroup.setWidth(490);
            	downloadExcel.SheetOptionGroup.setWidth(490);
            	downloadExcel.priceGroup.setWidth(490);
            }
          
            // ENHANCEMENT END 20120913:
        },
        _onready:function() {
        	//alert("ready");
        	downloadExcel = this;
        	
        }, 
        _onbeforecreated:function(com, threadid) {
        	//alert("beforecreated");
        },
        iniResource:function(com, threadid){
        	//alert("iniresource");
        }, 
        iniExComs:function(com, hreadid){
        },
        _alldatesbox_onchecked:function (profile, e, value) {
        	if (e) {
        		downloadExcel.date1Box.setUIValue(true);
        		downloadExcel.date2Box.setUIValue(true);
        		downloadExcel.date3Box.setUIValue(true);
        		downloadExcel.date4Box.setUIValue(true);
        		downloadExcel.date5Box.setUIValue(true);
        		downloadExcel.date6Box.setUIValue(true);
        		downloadExcel.date7Box.setUIValue(true);
        		
        		//downloadExcel.allDatesBox.setDisabled(true);
        	}
        	else {
        		downloadExcel.date1Box.setUIValue(true);
        		downloadExcel.date2Box.setUIValue(false);
        		downloadExcel.date3Box.setUIValue(false);
        		downloadExcel.date4Box.setUIValue(false);
        		downloadExcel.date5Box.setUIValue(false);
        		downloadExcel.date6Box.setUIValue(false);
        		downloadExcel.date7Box.setUIValue(false);
        	}
        },
        _date1box_onchecked:function (profile, e, value) {
        	var checkCtr = downloadExcel.dateBoxValidate();
        	if (checkCtr == 0) downloadExcel.date1Box.setUIValue(true);
        },
        _date2box_onchecked:function (profile, e, value) {
        	var checkCtr = downloadExcel.dateBoxValidate();
        	if (checkCtr == 0) downloadExcel.date2Box.setUIValue(true);
        },
        _date3box_onchecked:function (profile, e, value) {
        	var checkCtr = downloadExcel.dateBoxValidate();
        	if (checkCtr == 0) downloadExcel.date3Box.setUIValue(true);
        },
        _date4box_onchecked:function (profile, e, value) {
        	var checkCtr = downloadExcel.dateBoxValidate();
        	if (checkCtr == 0) downloadExcel.date4Box.setUIValue(true);
        },
        _date5box_onchecked:function (profile, e, value) {
        	var checkCtr = downloadExcel.dateBoxValidate();
        	if (checkCtr == 0) downloadExcel.date5Box.setUIValue(true);
        },
        _date6box_onchecked:function (profile, e, value) {
        	var checkCtr = downloadExcel.dateBoxValidate();
        	if (checkCtr == 0) downloadExcel.date6Box.setUIValue(true);
        },
        _date7box_onchecked:function (profile, e, value) {
        	var checkCtr = downloadExcel.dateBoxValidate();
        	if (checkCtr == 0) downloadExcel.date7Box.setUIValue(true);
        },
        dateBoxValidate:function () {
        	var checkCtr = 0;
        	
        	if (downloadExcel.date1Box.getUIValue())
        		checkCtr++;
        	if (downloadExcel.date2Box.getUIValue())
        		checkCtr++;
        	if (downloadExcel.date3Box.getUIValue())
        		checkCtr++;
        	if (downloadExcel.date4Box.getUIValue())
        		checkCtr++;
        	if (downloadExcel.date5Box.getUIValue())
        		checkCtr++;
        	if (downloadExcel.date6Box.getUIValue())
        		checkCtr++;
        	if (downloadExcel.date7Box.getUIValue())
        		checkCtr++;
        	
        	if (checkCtr == 7) {
        		downloadExcel.allDatesBox.setUIValue(true);
        		//downloadExcel.allDatesBox.setDisabled(true);
        	}
        	else {
        		downloadExcel.allDatesBox.setUIValue(false);
        		//downloadExcel.allDatesBox.setDisabled(false);
        	}
        	
        	return checkCtr;
        },
        _pricebox_onchecked:function (profile, e, value) {
        	if (e) {
        		downloadExcel.priceComputationBox.setUIValue('1');
        		downloadExcel.priceComputationBox.setDisabled(false);
        	}
        	else {
        		downloadExcel.priceComputationBox.setUIValue('0');
        		downloadExcel.priceComputationBox.setDisabled(true);
        	}
        },
        _cancelbtn_onclick:function (profile, e, src, value) {
        	this.destroy();
        },
        _submitbtn_onclick:function (profile, e, src, value) {
        	downloadExcel.setDownloadExcelParams();
        	
        	this.destroy();
        },
        loadExcelSettings:function(ui) {
        	var url = "/eON/loadExcelSetting.json";
        	linb.Ajax(
                url,
                null,
                function (response) {
                	//alert(response);
                	validateResponse(response);
                	var obj = _.unserialize(response);
                	var dateList = obj.dateList;
                	var dateMDList = obj.dateMDList;
                	var dayIndexList = obj.dayIndexList;
                	var dayList = obj.dayList;
                	var dxls = obj.dxls;
                	
                	//alert(_.serialize(dxls));
                	
                	/* setup date labels */
                	downloadExcel.setDateLabels(dateList, dateMDList, dayList, dayIndexList);
                	//
                	
                	/* setup ui values */
                	downloadExcel.setUIValues(dxls);
                	ui.submitBtn.setDisabled(false);
                	//
                }, 
                function(msg) {
                        linb.message("失敗： " + msg);
                    }, null, {
                        method : 'GET',
                        retry : 0
                    }
            ).start();
        },
        setDateLabels:function(dateList, dateMDList, dayList, dayIndexList) {
        	/* date */
        	downloadExcel.date1Lbl1.setCaption(dateMDList[0]);
        	downloadExcel.date2Lbl1.setCaption(dateMDList[1]);
        	downloadExcel.date3Lbl1.setCaption(dateMDList[2]);
        	downloadExcel.date4Lbl1.setCaption(dateMDList[3]);
        	downloadExcel.date5Lbl1.setCaption(dateMDList[4]);
        	downloadExcel.date6Lbl1.setCaption(dateMDList[5]);
        	downloadExcel.date7Lbl1.setCaption(dateMDList[6]);
        	//
        	
        	/* day */
        	/*
        	dayArr = new Array(
        			"$app.caption.sunday",
        			"$app.caption.monday",
        			"$app.caption.tuesday",
        			"$app.caption.wednesday",
        			"$app.caption.thursday",
        			"$app.caption.friday",
        			"$app.caption.saturday"
            	);*/
        	
        	dayArr = new Array(
        			"日","月","火","水","木","金","土"
            );
        	
        	downloadExcel.date1Lbl2.setCaption(dayArr[dayIndexList[0]]);
        	downloadExcel.date2Lbl2.setCaption(dayArr[dayIndexList[1]]);
        	downloadExcel.date3Lbl2.setCaption(dayArr[dayIndexList[2]]);
        	downloadExcel.date4Lbl2.setCaption(dayArr[dayIndexList[3]]);
        	downloadExcel.date5Lbl2.setCaption(dayArr[dayIndexList[4]]);
        	downloadExcel.date6Lbl2.setCaption(dayArr[dayIndexList[5]]);
        	downloadExcel.date7Lbl2.setCaption(dayArr[dayIndexList[6]]);
        	
        	/*
        	downloadExcel.date1Lbl2.setCaption(dayList[0]);
        	downloadExcel.date2Lbl2.setCaption(dayList[1]);
        	downloadExcel.date3Lbl2.setCaption(dayList[2]);
        	downloadExcel.date4Lbl2.setCaption(dayList[3]);
        	downloadExcel.date5Lbl2.setCaption(dayList[4]);
        	downloadExcel.date6Lbl2.setCaption(dayList[5]);
        	downloadExcel.date7Lbl2.setCaption(dayList[6]);*/
        	//
        },
        setDownloadExcelParams:function() {
        	var paramObj = new Object();
        	
        	/* date */
        	var dateArr = new Array('0','0','0','0','0','0','0');
        	if (downloadExcel.date1Box.getUIValue())
        		dateArr[0] = '1';
        	if (downloadExcel.date2Box.getUIValue())
        		dateArr[1] = '1';
        	if (downloadExcel.date3Box.getUIValue())
        		dateArr[2] = '1';
        	if (downloadExcel.date4Box.getUIValue())
        		dateArr[3] = '1';
        	if (downloadExcel.date5Box.getUIValue())
        		dateArr[4] = '1';
        	if (downloadExcel.date6Box.getUIValue())
        		dateArr[5] = '1';
        	if (downloadExcel.date7Box.getUIValue())
        		dateArr[6] = '1';
        	paramObj.dateFlag = dateArr;
        	//
        	
        	/* sheet options */
        	paramObj.dateOpt = downloadExcel.dateOptBox.getUIValue(); 
        	paramObj.sellerOpt = downloadExcel.sellerOptBox.getUIValue();
        	paramObj.buyerOpt = downloadExcel.buyerOptBox.getUIValue();
        	//
        	
        	/* has quantity */
        	var hasQty = '0';
        	if (downloadExcel.hasQtyBox.getUIValue())
        		hasQty = '1';
        	paramObj.hasQty = hasQty;
        	//
        	
        	/* price computation */
        	paramObj.priceComputeOpt = downloadExcel.priceComputationBox.getUIValue();
        	//
        	
        	/* return to sheet JS then submit from from sigma table */
        	this.fireEvent('onDownloadExcel', [paramObj]);
        },
        setUIValues:function(dxls) {
        	var dateFlag = dxls.dateFlag;
        	var dateOpt = dxls.dateOpt;
        	var sellerOpt = dxls.sellerOpt;
        	var buyerOpt = dxls.buyerOpt;
        	var hasQty = dxls.hasQty;
        	var priceComputeOpt = dxls.priceComputeOpt;
        	
        	/* date */
        	if (dateFlag[0] == '1')
        		downloadExcel.date1Box.setUIValue(true);
        	if (dateFlag[1] == '1')
        		downloadExcel.date2Box.setUIValue(true);
        	if (dateFlag[2] == '1')
        		downloadExcel.date3Box.setUIValue(true);
        	if (dateFlag[3] == '1')
        		downloadExcel.date4Box.setUIValue(true);
        	if (dateFlag[4] == '1')
        		downloadExcel.date5Box.setUIValue(true);
        	if (dateFlag[5] == '1')
        		downloadExcel.date6Box.setUIValue(true);
        	if (dateFlag[6] == '1')
        		downloadExcel.date7Box.setUIValue(true);
        	downloadExcel.dateBoxValidate();
        	//
        	
        	/* sheet options */
        	downloadExcel.dateOptBox.setUIValue(dateOpt);
        	downloadExcel.sellerOptBox.setUIValue(sellerOpt);
    		downloadExcel.buyerOptBox.setUIValue(buyerOpt);
    		if (g_userRole == "ROLE_SELLER") {
    			downloadExcel.sellerOptBox.setUIValue("1");
    			downloadExcel.sellerOptBox.setDisabled(true);
    		}
    		if (g_userRole == "ROLE_BUYER") {
    			downloadExcel.buyerOptBox.setUIValue("1");
    			downloadExcel.buyerOptBox.setDisabled(true);
    		}
        	//
        	
        	/* has quantity */
        	if (hasQty == '1')
        		downloadExcel.hasQtyBox.setUIValue(true);
        	//
        	
        	/* price computation */
        	if (priceComputeOpt == '0') {
        		downloadExcel.priceBox.setUIValue(false);
        		downloadExcel.priceComputationBox.setDisabled(true);
        	}
        	downloadExcel.priceComputationBox.setUIValue(priceComputeOpt);
        	//	
        }
    }
});