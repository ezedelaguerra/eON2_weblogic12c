/*
 	Copyright Freshremix Asia Software Corporation
	date		name		version	changes
	==============================================
	20120802	jovs		v11		Redmine 102 - Enable the seller admin to have the only quantity display in the user preference menu 
	
 * */
//create by : raquino 
//date created : 05/06/2010 
//FILE - UserPref - Show Unfinalized Billing Sheet to the Buyer SubMenu Item
	var userPrefShowUnfinalizeBilling = new Object();
	userPrefShowUnfinalizeBilling.id = "subuprefShowUnfinalizeBilling";
//	userPrefShowUnfinalizeBilling.caption = "<font color=\"#0000ee\">$app.caption.showUnfinalizeBilling</font>";
	userPrefShowUnfinalizeBilling.caption = "$app.caption.showUnfinalizeBilling";
	userPrefShowUnfinalizeBilling.type = "checkbox";
	
//	//FILE - UserPref - Show Unpublish Allocation to the buyer SubMenu Item
	var userPrefShowUnpublishAlloc = new Object();
	userPrefShowUnpublishAlloc.id = "subuprefShowUnpublishAllocation";
//	userPrefShowUnpublishAlloc.caption = "<font color=\"#0000ee\">$app.caption.showUnpublishAlloc</font>";
	userPrefShowUnpublishAlloc.caption = "$app.caption.showUnpublishAlloc";
	userPrefShowUnpublishAlloc.type = "checkbox";
	
	var userPrefEnableBAPublishOrder = new Object();
	userPrefEnableBAPublishOrder.id = "subuprefEnableBAPublishOrder";
//	userPrefEnableBAPublishOrder.caption = "<font color=\"#0000ee\">$app.caption.enablepublishbutton</font>";
	userPrefEnableBAPublishOrder.caption = "$app.caption.enablepublishbutton";
	userPrefEnableBAPublishOrder.type = "checkbox";
	userPrefEnableBAPublishOrder.disabled = true;
	
/*sets the user(with role seller) 
 * preferences button to disable*/
function disableSellerPreferenceButtons(sheet){
	
//	if(sheet == "0"){
		userPrefShowUnpublishAlloc.disabled = true;
		userPrefShowUnfinalizeBilling.disabled = true;
//	}else if(sheet == "1"){
//		userPrefShowUnpublishAlloc.disabled = false;
//		userPrefShowUnfinalizeBilling.disabled = true;
//	}else if (sheet == "2"){
//		userPrefShowUnpublishAlloc.disabled = true;
//		userPrefShowUnfinalizeBilling.disabled = false;
//	}
	
}
	
/*sets the user(with role buyer) 
 * preferences button to disable*/	
function disableBuyerPreferenceButtons(sheet){
	if(sheet == "0"){
		userPrefEnableBAPublishOrder.disabled = true;
	}else if(sheet == "1"){
		userPrefEnableBAPublishOrder.disabled = false;
	}
}

function getMenuItems(sheet) {
	var menuTypeSplit = new Object();
	menuTypeSplit.type = "split";
	
	//FILE Menu
	var fileMenu = new Object();
	fileMenu.id = "mnuFile";
//	fileMenu.caption = "<font color=\"#014e00\" size=\"3px\">$app.caption.file</font>";
	fileMenu.caption = "$app.caption.file";
	//EDIT Menu
//	var editMenu = new Object();
//	editMenu.id = "mnuEdit";
//	editMenu.disabled = true;
//	editMenu.caption = "<font color=\"#014e00\" size=\"3px\">$app.caption.edit</font>";
//	editMenu.caption = "$app.caption.edit";
	//SHEETS Menu
	var sheetsMenu = new Object();
	sheetsMenu.id = "mnuSheets";
//	sheetsMenu.caption = "<font color=\"#014e00\" size=\"3px\">$app.caption.sheets</font>";
	sheetsMenu.caption = "$app.caption.sheets";
	//UTILITY Menu
	var utilityMenu = new Object();
	utilityMenu.id = "mnuUtility";
//	utilityMenu.caption = "<font color=\"#014e00\" size=\"3px\">$app.caption.utility</font>";
	utilityMenu.caption = "$app.caption.utility";
	//HELP Menu
	var helpMenu = new Object();
	helpMenu.id = "mnuHelp";
//	helpMenu.caption = "<font color=\"#014e00\" size=\"3px\">$app.caption.help</font>";
	helpMenu.caption = "$app.caption.help";
	
	//FILE - UserPref
	var fileUserPref = new Object();
	fileUserPref.id = "fileUser";
//	fileUserPref.caption = "<font color=\"#0000ee\">$app.caption.userpreference</font>";
	fileUserPref.caption = "$app.caption.userpreference";
	fileUserPref.image = "../../common/img/User.gif";
	

	//FILE - UserPref - ScreenSettings SubMenu Item
	var userPrefScreenSet = new Object();
	userPrefScreenSet.id = "subuprefScreenset";
	userPrefScreenSet.disabled = true;
//	userPrefScreenSet.caption = "<font color=\"#0000ee\">$app.caption.screenSet</font>";
	userPrefScreenSet.caption = "$app.caption.screenSet";
	userPrefScreenSet.image = "../../common/img/demo.gif";
	//FILE - UserPref - ScreenSettings - SaveSettings SubMenu Item
	var screenSetSave = new Object();
	screenSetSave.id = "screenSaveset";
	screenSetSave.disabled = true;
//	screenSetSave.caption = "<font color=\"#0000ee\">$app.caption.screenSaveSet</font>";
	screenSetSave.caption = "$app.caption.screenSaveSet";
	screenSetSave.image = "../../common/img/demo.gif";
	//FILE - UserPref - ScreenSettings - ResetSettings SubMenu Item
	var screenSetReset = new Object();
	screenSetReset.id = "screenReset";
	screenSetReset.disabled = true;
//	screenSetReset.caption = "<font color=\"#0000ee\">$app.caption.screenReset</font>";
	screenSetReset.caption = "$app.caption.screenReset";
	screenSetReset.image = "../../common/img/demo.gif";
	
	//FILE - UserPref - Price With Tax SubMenu Item
	var userPrefPriceWTax = new Object(); 
	userPrefPriceWTax.id = "subuprefPricewtax";
	userPrefPriceWTax.disabled = true;
//	userPrefPriceWTax.caption = "<font color=\"#0000ee\">$app.caption.priceWithTaxCol</font>";
	userPrefPriceWTax.caption = "$app.caption.priceWithTaxCol";
	userPrefPriceWTax.image = "../../common/img/demo.gif"; 
	//FILE - UserPref - Price With Tax - Enable SubMenu Item
	var priceWTaxEnable = new Object();
	priceWTaxEnable.disabled = true;
	priceWTaxEnable.id = "pricewEnable";
//	priceWTaxEnable.caption = "<font color=\"#0000ee\">$app.caption.enable</font>";
	priceWTaxEnable.caption = "$app.caption.enable";
	priceWTaxEnable.image = "../../common/img/demo.gif";
	//FILE - UserPref - Price With Tax - Disable SubMenu Item
	var priceWTaxDisable = new Object();
	priceWTaxDisable.id = "pricewDisable";
	priceWTaxDisable.disabled = true;
//	priceWTaxDisable.caption = "<font color=\"#0000ee\">$app.caption.disable</font>";
//	priceWTaxDisable.caption = "<font color=\"#0000ee\">$app.caption.disable</font>";
	priceWTaxDisable.caption = "$app.caption.disable";
	priceWTaxDisable.image = "../../common/img/demo.gif";
	
	//FILE - UserPref - Price Without Tax SubMenu Item
	var userPrefPriceWOTax = new Object(); 
	userPrefPriceWOTax.id = "subuprefPricewotax";
	userPrefPriceWOTax.disabled = true;
//	userPrefPriceWOTax.caption = "<font color=\"#0000ee\">$app.caption.priceWithoutTaxCol</font>";
	userPrefPriceWOTax.caption = "$app.caption.priceWithoutTaxCol";
	userPrefPriceWOTax.image = "../../common/img/demo.gif"; 
	//FILE - UserPref - Price Without Tax - Enable SubMenu Item
	var priceWOTaxEnable = new Object();
	priceWOTaxEnable.id = "pricewoEnable";
	priceWOTaxEnable.disabled = true;
//	priceWOTaxEnable.caption = "<font color=\"#0000ee\">$app.caption.enable</font>";
	priceWOTaxEnable.caption = "$app.caption.enable";
	priceWOTaxEnable.image = "../../common/img/demo.gif";
	//FILE - UserPref - Price Without Tax - Disable SubMenu Item
	var priceWOTaxDisable = new Object();
	priceWOTaxDisable.id = "pricewoDisable";
	priceWOTaxDisable.disabled = true;
//	priceWOTaxDisable.caption = "<font color=\"#0000ee\">$app.caption.disable</font>";
	priceWOTaxDisable.caption = "$app.caption.disable";
	priceWOTaxDisable.image = "../../common/img/demo.gif";
	
	//FILE - UserPref - Show/Hide Columns SubMenu Item
	var userPrefShowHideColumns = new Object();
	userPrefShowHideColumns.id = "subuprefShowcols";
//	userPrefShowHideColumns.caption = "<font color=\"#0000ee\">$app.caption.showHideCol</font>";
	userPrefShowHideColumns.caption = "$app.caption.showHideCol";
	userPrefShowHideColumns.image = "../../common/img/demo.gif";
	userPrefShowHideColumns.disabled = true;
	//TODO by PAM - Show/Hide Columns SubMenu Items
//	var showHideCol1 = new Object();//Sample only
//	showHideCol1.id = "showcol1";
//	showHideCol1.caption = "<font color=\"#0000ee\">TODO by PAM</font>";
//	showHideCol1.type = "checkbox";
	
	//FILE - UserPref - Seller Product Code SubMenu Item
	var userPrefProdCode = new Object();
	userPrefProdCode.id = "subuprefSellprodcode";
	userPrefProdCode.disabled = true;
//	userPrefProdCode.caption = "<font color=\"#0000ee\">$app.caption.sellerprodcode</font>";
	userPrefProdCode.caption = "$app.caption.sellerprodcode";
	userPrefProdCode.image = "../../common/img/demo.gif";
	
	//FILE - UserPref - Round Off SubMenu Item
	var userPrefRoundOff = new Object();
	userPrefRoundOff.id = "subuprefRoundoff";
	userPrefRoundOff.disabled = true;
//	userPrefRoundOff.caption = "<font color=\"#0000ee\">$app.caption.roundOff</font>";
	userPrefRoundOff.caption = "$app.caption.roundOff";
	userPrefRoundOff.image = "../../common/img/demo.gif";
	//FILE - UserPref - Round Off SubMenu - Option1 Item
	var roundOffOpt1 = new Object();
	roundOffOpt1.id = "roundoff1";
	roundOffOpt1.disabled = true;
//	roundOffOpt1.caption = "<font color=\"#0000ee\">$app.caption.rounfOffOpt1</font>";
	roundOffOpt1.caption = "$app.caption.rounfOffOpt1";
	roundOffOpt1.type = "radiobox";
	//FILE - UserPref - Round Off SubMenu - Option2 Item
	var roundOffOpt2 = new Object();
	roundOffOpt2.id = "roundoff2";
	roundOffOpt2.disabled = true;
//	roundOffOpt2.caption = "<font color=\"#0000ee\">$app.caption.roundOffOpt2</font>";
	roundOffOpt2.caption = "$app.caption.roundOffOpt2";
	roundOffOpt2.type = "radiobox";
	//FILE - UserPref - Round Off SubMenu - Option3 Item
	var roundOffOpt3 = new Object();
	roundOffOpt3.id = "roundoff3";
	roundOffOpt3.disabled = true;
//	roundOffOpt3.caption = "<font color=\"#0000ee\">$app.caption.roundOffOpt3</font>";
	roundOffOpt3.caption = "$app.caption.roundOffOpt3";
	roundOffOpt3.type = "radiobox";
	
	//FILE - UserPref - BMS SubMenu Item
	var userPrefBMS = new Object();
	userPrefBMS.id = "subuprefBMS";
	userPrefBMS.disabled = true;
//	userPrefBMS.caption = "<font color=\"#0000ee\">$app.caption.bms</font>";
	userPrefBMS.caption = "$app.caption.bms";
	userPrefBMS.type = "checkbox";
	
	//FILE - UserPref - Receive Notification SubMenu Item
	var userPrefRcvAlert = new Object();
	userPrefRcvAlert.id = "subuprefRecAlerts";
	userPrefRcvAlert.disabled = true;
//	userPrefRcvAlert.caption = "<font color=\"#0000ee\">$app.caption.rcvAlert</font>";	
	userPrefRcvAlert.caption = "$app.caption.rcvAlert";	
	userPrefRcvAlert.image = "../../common/img/demo.gif";
	//FILE - UserPref - Receive Notification SubMenu - Enable Item
	var rcvAlertEnable = new Object();
	rcvAlertEnable.id = "subRcvAlertsEnable";
	rcvAlertEnable.disabled = true;
//	rcvAlertEnable.caption = "<font color=\"#0000ee\">$app.caption.enable</font>";
	rcvAlertEnable.caption = "$app.caption.enable";
	
	//FILE - UserPref - Receive Notification SubMenu - Disable Item
	var rcvAlertDisable = new Object();
	rcvAlertDisable.id = "subRecAlertsDisable";
	rcvAlertDisable.disabled = true;
//	rcvAlertDisable.caption = "<font color=\"#0000ee\">$app.caption.disable</font>";
	rcvAlertDisable.caption = "$app.caption.disable";
	
	//FILE - UserPref - Create Unit Order SubMenu Item
	var userPrefCreateUnitOrder = new Object();
	userPrefCreateUnitOrder.id = "subuprefUnitOrder";
//	userPrefCreateUnitOrder.caption = "<font color=\"#0000ee\">$app.caption.createUnitOrder</font>";
	userPrefCreateUnitOrder.caption = "$app.caption.createUnitOrder";
	userPrefCreateUnitOrder.image = "../../common/img/demo.gif";
	userPrefCreateUnitOrder.disabled = true;
	
//	//FILE - UserPref - Show Unfinalized Billing Sheet to the Buyer SubMenu Item
//	var userPrefShowUnfinalizeBilling = new Object();
//	userPrefShowUnfinalizeBilling.id = "subuprefShowUnfinalizeBilling";
//	userPrefShowUnfinalizeBilling.caption = "<font color=\"#0000ee\">$app.caption.showUnfinalizeBilling</font>";
//	userPrefShowUnfinalizeBilling.type = "checkbox";
//	
////	//FILE - UserPref - Show Unpublish Allocation to the buyer SubMenu Item
//	var userPrefShowUnpublishAlloc = new Object();
//	userPrefShowUnpublishAlloc.id = "subuprefShowUnpublishAllocation";
//	userPrefShowUnpublishAlloc.caption = "<font color=\"#0000ee\">$app.caption.showUnpublishAlloc</font>";
//	userPrefShowUnpublishAlloc.type = "checkbox";

//	alert(unPublishedAllocation);
//	if(unPublishedAllocation == "true"){
//		alert(unPublishedAllocation);
//		userPrefShowUnpublishAlloc.value = unPublishedAllocation;
//	}
//	
//	if(unFinalizedBilling == "true"){
//		alert(unFinalizedBilling);
//		userPrefShowUnfinalizeBilling.value = unFinalizedBilling;
//		
//	}
	
	
//  Auto-Publish: Ogie 120610	
//	FILE - UserPref - Auto-Publish Allocation when finalizing the seller order sheet
	var userPrefAutoPublishAlloc = new Object();
	userPrefAutoPublishAlloc.id = "subuprefAutoPublishAllocation";
	userPrefAutoPublishAlloc.caption = "$app.caption.autoPublishAlloc";
	userPrefAutoPublishAlloc.type = "checkbox";
	if(autoPublishAlloc == "true"){
		userPrefAutoPublishAlloc.value = autoPublishAlloc;
	}	
//  Auto-Publish: Ogie 120610	
	
//  Alloc display qty only: Rhoda 042012
//	FILE - UserPref - Display Allocation Quantity
	var userPrefDisplayAllocQty = new Object();
	userPrefDisplayAllocQty.id = "subuprefDisplayAllocQty";
	userPrefDisplayAllocQty.caption = "$app.caption.displayQtyAlloc";
	userPrefDisplayAllocQty.type = "checkbox";
	if(displayAllocQty == "0"){
		userPrefDisplayAllocQty.value = false;
	}else{
		userPrefDisplayAllocQty.value = true;
	}
//  Alloc display qty only: Rhoda 042012		
	//FILE - History Log
	var fileHistoryLogFile = new Object();
	fileHistoryLogFile.id = "fileHistorylogfile";
//	fileHistoryLogFile.caption = "<font color=\"#0000ee\">$app.caption.historylogfile</font>";
	fileHistoryLogFile.caption = "$app.caption.historylogfile";
	fileHistoryLogFile.image = "../../common/img/history.gif";
	fileHistoryLogFile.disabled = true;
	
	//FILE - Change Password
	var fileChangePassword = new Object();
	fileChangePassword.id = "fileChangePassword";
//	fileChangePassword.caption = "<font color=\"#0000ee\">$app.caption.changepassword</font>";
//	fileChangePassword.caption = "<font color=\"#0000ee\">$app.caption.changepassword</font>";
	fileChangePassword.caption = "$app.caption.changepassword";
	fileChangePassword.image = "../../common/img/key.gif";
	
	//FILE - User Details
//	var fileUserDetails = new Object();
//	fileUserDetails.id = "fileUserDetails";
//	fileUserDetails.caption = "$app.caption.userdetails";
//	fileUserDetails.image = "../../common/img/userdetails.gif"; 
	
	//FILE - LogOff
	var fileLogoff = new Object();
	fileLogoff.id = "fileLogoff";
//	fileLogoff.caption = "<font color=\"#0000ee\">$app.caption.logout</font>";
	fileLogoff.caption = "$app.caption.logout";
	fileLogoff.image = "../../common/img/logout.gif";
	
	//EDIT - Add Columns 
	var editAddColumn = new Object();
	editAddColumn.id = "editAddColumn";
	editAddColumn.disabled = true;
//	editAddColumn.caption =  "<font color=\"#0000ee\">$app.caption.addcolumn</font>";
	editAddColumn.caption =  "$app.caption.addcolumn";
	editAddColumn.image = "../../common/img/addcolumn.gif";
	//EDIT - Publish Orders 
	var editPublishOrders = new Object();
	editPublishOrders.id = "editPublishOrders";
	editPublishOrders.disabled = true;
//	editPublishOrders.caption = "<font color=\"#0000ee\">$app.caption.publishedorders</font>";
	editPublishOrders.caption = "$app.caption.publishedorders";
	editPublishOrders.image = "../../common/img/fileupload.gif";
	//EDIT - Refresh
	var editRefresh = new Object();
	editRefresh.id = "editRefresh";
//	editRefresh.caption = "<font color=\"#0000ee\">$app.caption.refresh</font>";
	editRefresh.caption = "$app.caption.refresh";
	editRefresh.image = "../../common/img/refresh.gif";
	//EDIT - Copy
	var editCopy = new Object();
	editCopy.id = "editCopy";
	editCopy.disabled = true;
//	editCopy.caption = "<font color=\"#0000ee\">$app.caption.copy</font>";
	editCopy.caption = "$app.caption.copy";
	editCopy.image = "../../common/img/copy.gif";
	//EDIT - Filter Display
	var editFilterDisplay = new Object();
	editFilterDisplay.id = "editFilterDisplay";
	editFilterDisplay.disabled = true;
//	editFilterDisplay.caption = "<font color=\"#0000ee\">$app.caption.filterdisplay</font>";
	editFilterDisplay.caption = "$app.caption.filterdisplay";
	editFilterDisplay.image = "../../common/img/filterdisplay.gif";
	//EDIT - Find Sku
	var editFindSku = new Object();
	editFindSku.id = "editFindSKU";
	editFindSku.disabled = true;
//	editFindSku.caption = "<font color=\"#0000ee\">$app.caption.findsku</font>";
	editFindSku.caption = "$app.caption.findsku";
	editFindSku.image = "../../common/img/search.gif";
	
	//SHEETS - 	Home Page
	var homePage = new Object();
	homePage.id = "mnuHome";
	homePage.caption = "$app.caption.home";
	
	//SHEETS - Order Sheet
	var orderSheet = new Object();
	orderSheet.id = "mnuOrdersheet";
	if (g_userRole == "ROLE_SELLER" || g_userRole == "ROLE_SELLER_ADMIN"){
		//orderSheet.caption = "<font color=\"#0000ee\">$app.caption.ordersheet</font>";
		orderSheet.caption = "$app.caption.ordersheet";
		orderSheet.image = "../../common/img/seller_order_sheet.GIF";
	}else if (g_userRole == "ROLE_BUYER" || g_userRole == "ROLE_BUYER_ADMIN"){
		//orderSheet.caption = "<font color=\"#0000ee\">$app.caption.buyerordersheet</font>";
		orderSheet.caption = "$app.caption.buyerordersheet";
		orderSheet.image = "../../common/img/buyer_order_new.gif";
	}
	//SHEETS - Allocation Sheet
	var allocationSheet = new Object();
	allocationSheet.id = "mnuAllocationsheet";
//	allocationSheet.caption = "<font color=\"#0000ee\">$app.caption.allocationsheet</font>";
	allocationSheet.caption = "$app.caption.allocationsheet";
	allocationSheet.image = "../../common/img/seller_allocation_sheet.GIF";
	//{"type":"split"}, {"id":"mnuAllocationsheet", "caption":"<font color=\"#0000ee\">$app.caption.allocationsheet</font>", "image":"../../common/img/seller_allocation_sheet.GIF"}, 
	//SHEETS - Received Sheet
	var receivedSheet = new Object();
	receivedSheet.id = "mnuReceivedsheet";
//	receivedSheet.caption = "<font color=\"#0000ee\">$app.caption.receivedsheet</font>";
//	receivedSheet.caption = "<font color=\"#0000ee\">$app.caption.receivedsheet</font>";
	receivedSheet.caption = "$app.caption.receivedsheet";
	receivedSheet.image = "../../common/img/buyer_received_new.gif";
	//SHEETS - Billing Sheet
	var billingSheet = new Object();
	billingSheet.id = "mnuBillingsheet";
	billingSheet.disabled = true;
	if (g_userRole == "ROLE_SELLER" || g_userRole == "ROLE_SELLER_ADMIN"){
//		billingSheet.caption = "<font color=\"#0000ee\">$app.caption.billingsheet</font>";
		billingSheet.caption = "$app.caption.billingsheet";
		billingSheet.image = "../../common/img/seller_billing_sheet.GIF";
	}else if (g_userRole == "ROLE_BUYER" || g_userRole == "ROLE_BUYER_ADMIN"){
//		billingSheet.caption = "<font color=\"#0000ee\">$app.caption.buyerbillingsheet</font>";
		billingSheet.caption = "$app.caption.buyerbillingsheet";
		billingSheet.image = "../../common/img/buyer_billing_new.gif";
	}
	//SHEETS - User Preference
	var sheetsUserPref = new Object();
	sheetsUserPref.id = "mnuUserpreference";
	//sheetsUserPref.disabled = true;
//	sheetsUserPref.caption = "<font color=\"#0000ee\">$app.caption.userpreference</font>";
	sheetsUserPref.caption = "$app.caption.userpreference";
	sheetsUserPref.image = "../../common/img/user_preferences.gif";
	//SHEETS - Comments Sheet
	var commentSheet = new Object();
	commentSheet.id = "mnuComments";
//	commentSheet.caption = "<font color=\"#0000ee\">$app.caption.comments</font>";
	commentSheet.caption = "$app.caption.comments";
	commentSheet.image = "../../common/img/envelope_close.GIF";
	//SHEETS - Akaden Sheet
	var akadenSheet = new Object();
	akadenSheet.id = "mnuAkadensheet";
	akadenSheet.disabled = true;
//	akadenSheet.caption = "<font color=\"#0000ee\">$app.caption.akadensheet</font>";
	akadenSheet.caption = "$app.caption.akadensheet";
	akadenSheet.image = "../../common/img/akaden.gif";
	
	//UTILITY - Print
	var utilityPrint = new Object();
	utilityPrint.id = "utilPrint";
	utilityPrint.disabled = true;
//	utilityPrint.caption = "<font color=\"#0000ee\">$app.caption.print</font>";
	utilityPrint.caption = "$app.caption.print";
	utilityPrint.image = "../../common/img/Print.gif";
	//{"id":"mnuUtility", "sub":[{"id":"utilPrint", "caption":"<font color=\"#0000ee\">$app.caption.print</font>", "image":"../../common/img/print.gif"}, 
	//UTILITY - Refresh
	var utilityRefresh = new Object();
	utilityRefresh.id = "utilRefresh";
	utilityRefresh.disabled = true;
//	utilityRefresh.caption = "<font color=\"#0000ee\">$app.caption.refresh</font>";
	utilityRefresh.caption = "$app.caption.refresh";
	utilityRefresh.image = "../../common/img/refresh.gif";
	//{"id":"utilRefresh", "caption":"<font color=\"#0000ee\">$app.caption.refresh</font>", "image":"../../common/img/refresh.gif"}], 
	//UTILITY - Download EXCEL
	var utilityDownloadExcel = new Object();
	utilityDownloadExcel.id = "utilDownloadExcel";
	utilityDownloadExcel.disabled = true;
//	utilityDownloadExcel.caption = "<font color=\"#0000ee\">$app.caption.downloadexcel</font>";
	utilityDownloadExcel.caption = "$app.caption.downloadexcel";
	utilityDownloadExcel.image = "../../common/img/excel.gif";
	//UTILITY - Download CSV
	var utilityDownloadCSV = new Object();
	utilityDownloadCSV.id = "utilDownloadCSV";
	utilityDownloadCSV.disabled = false;
//	utilityDownloadCSV.caption = "<font color=\"#0000ee\">$app.caption.downloadcsv</font>";
	utilityDownloadCSV.caption = "$app.caption.downloadcsv";
	utilityDownloadCSV.image = "../../common/img/csv.gif";
	//UTILITY - Download PDF
	var utilityDownloadPDF = new Object();
	utilityDownloadPDF.id = "utilDownloadPDF";
	utilityDownloadPDF.disabled = true;
//	utilityDownloadPDF.caption = "<font color=\"#0000ee\">$app.caption.downloadpdf</font>";
//	utilityDownloadPDF.caption = "<font color=\"#0000ee\">$app.caption.downloadpdf</font>";
	utilityDownloadPDF.caption = "$app.caption.downloadpdf";
	utilityDownloadPDF.image = "../../common/img/PDF.gif";
	//UTILITY - Download PDF
	var utilityUploadFile = new Object();
	utilityUploadFile.id = "utilUploadFile";
	utilityUploadFile.disabled = true;
//	utilityUploadFile.caption = "<font color=\"#0000ee\">$app.caption.uploadfile</font>";
	utilityUploadFile.caption = "$app.caption.uploadfile";
	utilityUploadFile.image = "../../common/img/Upload.gif";
	//UTILITY - Download PMF
	var utilityPMF = new Object();
	utilityPMF.id = "utilPMF";
	utilityPMF.disabled = true;
//	utilityPMF.caption = "<font color=\"#0000ee\">$app.caption.prodmasterfile</font>";
	utilityPMF.caption = "$app.caption.prodmasterfile";
	utilityPMF.image = "../../common/img/file.gif";
	
	//HELP - Online help
	var helpOnline = new Object();
	helpOnline.id = "helpOnline";
	helpOnline.disabled = true;
//	helpOnline.caption = "<font color=\"#0000ee\">$app.caption.onlinehelp</font>";
	helpOnline.caption = "$app.caption.onlinehelp";
	helpOnline.image = "../../common/img/help.gif";
	//{"id":"mnuHelp", "sub":[{"id":"helpHelp", "caption":"<font color=\"#0000ee\">$app.caption.onlinehelp</font>", "image":"../../common/img/help.gif"}, 
	//HELP - About EON
	var helpAboutEon = new Object();
	helpAboutEon.id = "helpAbout";
//	helpAboutEon.caption = "<font color=\"#0000ee\">$app.caption.abouteon</font>";
	helpAboutEon.caption = "$app.caption.abouteon";
	helpAboutEon.image = "../../common/img/demo.gif";
	
	//Screen Settings Menu Set Sub Items
	var userPrefScreenSetSub = [];
	userPrefScreenSetSub[0] = screenSetSave;
	userPrefScreenSetSub[1] = menuTypeSplit;
	userPrefScreenSetSub[2] = screenSetReset;
	userPrefScreenSet.sub = userPrefScreenSetSub;
	
	//Price With Tax Menu Set Sub Items
	var userPrefPriceWTaxSub = [];
	userPrefPriceWTaxSub[0] = priceWTaxEnable;
	userPrefPriceWTaxSub[1] = menuTypeSplit;
	userPrefPriceWTaxSub[2] = priceWTaxDisable;
	userPrefPriceWTax.sub = userPrefPriceWTaxSub;
	
	//Price Without Tax Menu Set Sub Items
	var userPrefPriceWOTaxSub = [];
	userPrefPriceWOTaxSub[0] = priceWOTaxEnable;
	userPrefPriceWOTaxSub[1] = menuTypeSplit;
	userPrefPriceWOTaxSub[2] = priceWOTaxDisable;
	userPrefPriceWOTax.sub = userPrefPriceWOTaxSub;
	
	//TODO by PAM - Show/Hide Columns set sub items
//	var g_sellerShowHideColSub = [];//Sample only
//	g_sellerShowHideColSub[0] = g_sellerShowHideCol1;
//	g_sellerShowHideColumns.sub = g_sellerShowHideColSub;
	
	//TODO - Seller Product Code set items
	//g_sellerProdCode.sub="";
	
	//Round Off set Items
	var userPrefRoundOffSub = [];
	userPrefRoundOffSub[0] = roundOffOpt1;
	userPrefRoundOffSub[1] = menuTypeSplit;
	userPrefRoundOffSub[2] = roundOffOpt2;
	userPrefRoundOffSub[3] = menuTypeSplit;
	userPrefRoundOffSub[4] = roundOffOpt3;
	userPrefRoundOff.sub = userPrefRoundOffSub;
	
	//User Pref Menu Set Sub Items
	var fileUserPrefSub = [];
//	fileUserPrefSub[0] = userPrefScreenSet;
//	fileUserPrefSub[1] = menuTypeSplit;
//	fileUserPrefSub[2] = userPrefPriceWTax;
//	fileUserPrefSub[3] = menuTypeSplit;
//	fileUserPrefSub[4] = userPrefPriceWOTax;
//	fileUserPrefSub[5] = menuTypeSplit;
//	fileUserPrefSub[6] = userPrefShowHideColumns;
//	fileUserPrefSub[7] = menuTypeSplit;
//	fileUserPrefSub[8] = userPrefProdCode;
//	fileUserPrefSub[9] = menuTypeSplit;
//	fileUserPrefSub[0] = userPrefRoundOff;
//	fileUserPrefSub[12] = userPrefBMS;
//	fileUserPrefSub[13] = menuTypeSplit;
//	fileUserPrefSub[14] = userPrefRcvAlert;
//	fileUserPrefSub[15] = menuTypeSplit;
	
	if (g_userRole == "ROLE_SELLER" || g_userRole == "ROLE_SELLER_ADMIN"){
//		fileUserPrefSub[1] = menuTypeSplit;
		fileUserPrefSub[0] = userPrefCreateUnitOrder;
//		fileUserPrefSub[17] = menuTypeSplit;
//		fileUserPrefSub[18] = userPrefShowUnfinalizeBilling;
		/* ENHANCEMENT START 20120802 JOVSAB - Enable the seller admin to have the only quantity display in the user preference menu */
		fileUserPrefSub[1] = userPrefDisplayAllocQty;
		/* ENHANCEMENT END 20120802 JOVSAB */
	}
	if (g_userRole == "ROLE_SELLER"){
		fileUserPrefSub[1] = menuTypeSplit;
		fileUserPrefSub[2] = userPrefAutoPublishAlloc;
		fileUserPrefSub[3] = userPrefDisplayAllocQty;
	}	
	//start jr
	if (g_userRole == "ROLE_BUYER_ADMIN"){
//		fileUserPrefSub[1] = menuTypeSplit;
//		fileUserPrefSub[2] = userPrefEnableBAPublishOrder;
	}
	//end jr
	
	fileUserPref.sub = fileUserPrefSub;
	
	//File Menu Set Sub Items
	var fileSubMenu = [];
	
	if (g_userRole == "ROLE_SELLER" || g_userRole == "ROLE_SELLER_ADMIN"){
		fileSubMenu[0] = fileUserPref;
		fileSubMenu[1] = menuTypeSplit;
//		fileSubMenu[1] = menuTypeSplit;
//		fileSubMenu[2] = fileHistoryLogFile;
		fileSubMenu[2] = fileChangePassword;
		fileSubMenu[3] = menuTypeSplit;
		//fileSubMenu[4] = fileUserDetails;
		//fileSubMenu[5] = menuTypeSplit;
		//fileSubMenu[6] = fileLogoff;
	}
	if (g_userRole == "ROLE_BUYER" || g_userRole == "ROLE_BUYER_ADMIN"){
		fileSubMenu[0] = fileChangePassword;
		fileSubMenu[1] = menuTypeSplit;
		//fileSubMenu[2] = fileUserDetails;
		//fileSubMenu[5] = menuTypeSplit;
		//fileSubMenu[6] = fileLogoff;
	}
	fileMenu.sub = fileSubMenu;
	
	//Edit Menu Set Sub Items
//	var editSubMenu = [];
//	editSubMenu[0] = editAddColumn;
//	editSubMenu[1] = editPublishOrders;
//	editSubMenu[0] = editRefresh;
//	editSubMenu[3] = editCopy;
//	editSubMenu[4] = editFindSku;
//	editMenu.sub = editSubMenu;
	
	//Sheets Menu Set Sub Items
	var sheetsSubMenu = [];
	if (g_userRole == "ROLE_SELLER" || g_userRole == "ROLE_SELLER_ADMIN"){
		sheetsSubMenu[0] = homePage;
		sheetsSubMenu[1] = menuTypeSplit;
		sheetsSubMenu[2] = orderSheet;
		sheetsSubMenu[3] = menuTypeSplit;
		sheetsSubMenu[4] = allocationSheet;
		sheetsSubMenu[5] = menuTypeSplit;
		sheetsSubMenu[6] = billingSheet;
		sheetsSubMenu[7] = menuTypeSplit;
		sheetsSubMenu[8] = akadenSheet;
		sheetsSubMenu[9] = menuTypeSplit;
		sheetsSubMenu[10] = commentSheet;
		sheetsSubMenu[11] = menuTypeSplit;
		sheetsSubMenu[12] = sheetsUserPref;
	}
	if (g_userRole == "ROLE_BUYER" || g_userRole == "ROLE_BUYER_ADMIN"){
		sheetsSubMenu[0] = homePage;
		sheetsSubMenu[1] = menuTypeSplit;
		sheetsSubMenu[2] = orderSheet;
		sheetsSubMenu[3] = menuTypeSplit;
		sheetsSubMenu[4] = receivedSheet;
		sheetsSubMenu[5] = menuTypeSplit;
		sheetsSubMenu[6] = billingSheet;
		sheetsSubMenu[7] = menuTypeSplit;
		sheetsSubMenu[8] = commentSheet;
		sheetsSubMenu[9] = menuTypeSplit;
		sheetsSubMenu[10] = sheetsUserPref;
		//fileSubMenu[9] = userPrefEnableBAPublishOrder;
	}
		
	sheetsMenu.sub = sheetsSubMenu;
	
	//Utility Menu Set Sub Items
	var utilitySubMenu = [];
//	utilitySubMenu[0] = utilityPrint;
//	utilitySubMenu[0] = menuTypeSplit;
	utilitySubMenu[0] = utilityDownloadExcel;
	utilitySubMenu[1] = menuTypeSplit;
	utilitySubMenu[2] = utilityDownloadCSV;
	utilitySubMenu[3] = menuTypeSplit;
	utilitySubMenu[4] = editRefresh;
	
//	utilitySubMenu[4] = menuTypeSplit;
//	utilitySubMenu[6] = utilityDownloadPDF;
	if (g_userRole == "ROLE_SELLER"){
		utilitySubMenu[5] = menuTypeSplit;
		utilitySubMenu[6] = utilityUploadFile;
		utilitySubMenu[7] = menuTypeSplit;
		utilitySubMenu[8] = utilityPMF;
//		utilitySubMenu[11] = menuTypeSplit;
//		utilitySubMenu[12] = utilityRefresh;	
	}
	
	if (g_userRole == "ROLE_SELLER_ADMIN"){
		utilitySubMenu[5] = menuTypeSplit;
		utilitySubMenu[6] = utilityPMF;
	}
	
	utilityMenu.sub = utilitySubMenu;
	
	//Help Menu Set Sub Items
	var helpSubMenu = [];
//	helpSubMenu[0] = helpOnline;
//	helpSubMenu[1] = menuTypeSplit;
	helpSubMenu[0] = helpAboutEon;
	helpMenu.sub = helpSubMenu;
	
	//Set Menubar with Edit items
	var menuItems = [];
	menuItems[0] = fileMenu;
	if(sheet == "home"){
		menuItems[1] = utilityMenu;
	}else{
		menuItems[1] = sheetsMenu;
		menuItems[2] = utilityMenu;
	}
	//menuItems[4] = helpMenu;

	return menuItems;
}


