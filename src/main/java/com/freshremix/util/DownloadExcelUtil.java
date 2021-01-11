/**
 * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
 * <BR>
 * <B>Description:</B><BR>
 * Describe class or interface.<BR>
 * <BR>
 * <B>Known Bugs:</B>
 * none<BR>
 * <BR>
 * <B>History:</B>
 * <PRE><!-- Do not use tabs in the history table! Do not extend table width! -->
 * date       name            changes
 * ------------------------------------------------------------------------------
 * Apr 30, 2010		nvelasquez
 */
package com.freshremix.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.freshremix.constants.SKUColumnConstantsExcel;
import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.model.EONHeader;
import com.freshremix.model.HideColumn;

/**
 * @author nvelasquez
 *
 */
public class DownloadExcelUtil {
	
	public static String fontFace = MessageUtil.getPropertyMessage(MessageUtil.fontFace);
	public static int headerFontSize = 9;
	public static int dataFontSize = 10;
	public static int qtyFontSize = 13;
	public static int skuRowHeight = 420;
	public static int headerRow = 8;
	public static int maxLenSheetName = 31;
	public static int grandtotalRow = 4;   //start row for the grandtotal group
	public static int buyerDataFontSize = 13;
	public static int skuCommentFontSize = 9;
	
	/* excel labels */
	public static String userLabel = MessageUtil.getPropertyMessage("label.excel.userlbl");
	public static String dateLabel = MessageUtil.getPropertyMessage("label.excel.datelbl");
	public static String sellerLabel = MessageUtil.getPropertyMessage("label.excel.sellerlbl");
	public static String buyerLabel = MessageUtil.getPropertyMessage("label.excel.buyerlbl");
	public static String total = MessageUtil.getPropertyMessage("label.excel.total");
	public static String allTotals = MessageUtil.getPropertyMessage("label.excel.alltotals");
	public static String allSellers = MessageUtil.getPropertyMessage("label.excel.allsellers");
	public static String allBuyers = MessageUtil.getPropertyMessage("label.excel.allbuyers");
	public static String seller = MessageUtil.getPropertyMessage("label.skucolumn.sellername");
	public static String buyer = MessageUtil.getPropertyMessage("label.skucolumn.buyername");
	public static String priceTotalWithTax = MessageUtil.getPropertyMessage("label.excel.pricetotalwithtax");
	public static String priceTotalNoTax = MessageUtil.getPropertyMessage("label.excel.pricetotalnotax");
	public static String price1GrandTotal = MessageUtil.getPropertyMessage("label.excel.price1grandtotal");
	public static String price2GrandTotal = MessageUtil.getPropertyMessage("label.excel.price2grandtotal");
	public static String totalSellingPrice = MessageUtil.getPropertyMessage("label.excel.totalsellingprice");
	
	/* category */
	public static String fruitsLabel = MessageUtil.getPropertyMessage("label.category.fruits");
	public static String veggiesLabel = MessageUtil.getPropertyMessage("label.category.veggies");
	public static String fishLabel = MessageUtil.getPropertyMessage("label.category.fish");
	
	/* sheet names */
	public static String sellerOrder = MessageUtil.getPropertyMessage("label.sheetname.sellerorder");
	public static String sellerAlloc = MessageUtil.getPropertyMessage("label.sheetname.selleralloc");
	public static String sellerAkaden = MessageUtil.getPropertyMessage("label.sheetname.sellerakaden");
	public static String sellerBill = MessageUtil.getPropertyMessage("label.sheetname.sellerbilling");
	public static String buyerOrder = MessageUtil.getPropertyMessage("label.sheetname.buyerorder");
	public static String buyerRec = MessageUtil.getPropertyMessage("label.sheetname.buyerreceived");
	public static String buyerBill = MessageUtil.getPropertyMessage("label.sheetname.buyerbilling");
	public static String sellerAdminOrder = MessageUtil.getPropertyMessage("label.sheetname.selleradminorder");
	public static String sellerAdminAlloc = MessageUtil.getPropertyMessage("label.sheetname.selleradminalloc");
	public static String sellerAdminAkaden = MessageUtil.getPropertyMessage("label.sheetname.selleradminakaden");
	public static String sellerAdminBill = MessageUtil.getPropertyMessage("label.sheetname.selleradminbilling");
	public static String buyerAdminOrder = MessageUtil.getPropertyMessage("label.sheetname.buyeradminorder");
	public static String buyerAdminRec = MessageUtil.getPropertyMessage("label.sheetname.buyeradminreceived");
	public static String buyerAdminBill = MessageUtil.getPropertyMessage("label.sheetname.buyeradminbilling");	
	
	public static String separator = MessageUtil.getPropertyMessage("label.excel.separator");;
	public static BigDecimal taxRate = TaxUtil.getTAX_RATE();
	public static int indexAfterDynamic = 31;
	
	public static String[] skuHeaderKeys = {
		SKUColumnConstantsExcel.SKU_ID,             //0
		SKUColumnConstantsExcel.COMPANY_NAME,       //1
		SKUColumnConstantsExcel.SELLER_NAME,        //2
		SKUColumnConstantsExcel.SKU_GROUP_NAME,     //3
		SKUColumnConstantsExcel.MARKET_CONDITION,	//4
		SKUColumnConstantsExcel.COLUMN_01,          //5
		SKUColumnConstantsExcel.COLUMN_02,          //6
		SKUColumnConstantsExcel.COLUMN_03,          //7
		SKUColumnConstantsExcel.COLUMN_04,          //8
		SKUColumnConstantsExcel.COLUMN_06,          //9
		SKUColumnConstantsExcel.COLUMN_07,          //10
		SKUColumnConstantsExcel.COLUMN_08,          //11
		SKUColumnConstantsExcel.COLUMN_09,          //12
		SKUColumnConstantsExcel.SKU_NAME,			//13
		SKUColumnConstantsExcel.AREA_PRODUCTION,    //14
		SKUColumnConstantsExcel.CLASS1,             //15
		SKUColumnConstantsExcel.CLASS2,             //16
		SKUColumnConstantsExcel.PACKAGE_QTY,        //17
		SKUColumnConstantsExcel.PACKAGE_TYPE,       //18
		SKUColumnConstantsExcel.PRICE_1,            //19
		SKUColumnConstantsExcel.PRICE_2,            //20
		SKUColumnConstantsExcel.PRICE_WO_TAX,       //21
		SKUColumnConstantsExcel.PRICE_W_TAX,        //22 <-- always visible off
		SKUColumnConstantsExcel.UOM,         //23
		SKUColumnConstantsExcel.COMMENT,        	//24 <-- not used refactor to remove
		SKUColumnConstantsExcel.PURCHASE_PRICE,     //25 new columns (SKUBA)
		SKUColumnConstantsExcel.COLUMN_10,			//26
		SKUColumnConstantsExcel.COLUMN_05, 			//27
		SKUColumnConstantsExcel.COLUMN_11,			//28
		SKUColumnConstantsExcel.SELLING_PRICE,      //29 new columns (SKUBA)
		SKUColumnConstantsExcel.SELLING_UOM,        //30 new columns (SKUBA)
		// DYNAMIC COLUMNS HERE (DATES/BUYERS)
		SKUColumnConstantsExcel.TOTAL_QUANTITY,     //31
		SKUColumnConstantsExcel.PRICE1_TOTAL,       //32
		SKUColumnConstantsExcel.PRICE2_TOTAL,       //33
		SKUColumnConstantsExcel.PRICE_TOTAL,        //34
		SKUColumnConstantsExcel.TOTAL_SELLING_PRICE,     //35
		SKUColumnConstantsExcel.SKU_COMMENT,       //36 new columns (SKUBA)
		SKUColumnConstantsExcel.COLUMN_12,          //37
		SKUColumnConstantsExcel.COLUMN_13,          //38
		SKUColumnConstantsExcel.COLUMN_14,          //39
		SKUColumnConstantsExcel.COLUMN_15,          //40
		SKUColumnConstantsExcel.COLUMN_16,          //41
		SKUColumnConstantsExcel.COLUMN_17,          //42
		SKUColumnConstantsExcel.COLUMN_18,          //43
		SKUColumnConstantsExcel.COLUMN_19,          //44
		SKUColumnConstantsExcel.COLUMN_20,          //45
		
	};	
	
	/*SKU Worksheet Cell Format Constants*/
	public static final String LBL_CF_HEADER1 = "lblCFHeader1";
	public static final String LBL_CF_HEADER2 = "lblCFHeader2";
	public static final String LBL_CF_HEADER3 = "lblCFHeader3";
	public static final String LBL_CF_DATA1 = "lblCFData1";
	public static final String LBL_CF_DATA2 = "lblCFData2";
	public static final String LBL_CF_DATA3 = "lblCFData3";
	public static final String LBL_CF_DATA4 = "lblCFData4";
	public static final String LBL_CF_DATA5 = "lblCFData5";
	public static final String LBL_CF_DATA6 = "lblCFData6";
	public static final String DBL_CF_DATA1 = "dblCFData1";
	public static final String DBL_CF_DATA2 = "dblCFData2";
	public static final String DBL_CF_DATA3 = "dblCFData3";
	public static final String DBL_CF_DATA4 = "dblCFData4";
	public static final String DBL_CF_DATA5 = "dblCFData5";
	public static final String DBL_CF_INT_QTY1 = "dblCFIntQty1";
	public static final String DBL_CF_INT_QTY2 = "dblCFIntQty2";
	public static final String DBL_CF_INT_QTY3 = "dblCFIntQty3";
	public static final String DBL_CF_DEC_QTY2 = "dblCFDecQty2";
	public static final String DBL_CF_INT_QTY1_LOCKED = "dblCFDecQty2Locked";

	
	public static String[] getSkuHeaderNames(EONHeader eONHeader) {
		
		String[] skuHeaderNames = new String[46];   
		for(int i=0; i<skuHeaderKeys.length; i++){
			skuHeaderNames[i] = eONHeader.getSheetName(skuHeaderKeys[i]);
		}
		
		// other labels are in the properties file
		skuHeaderNames[24] = MessageUtil.getPropertyMessage("label.skucolumn.comment"); // NOT USED
		skuHeaderNames[31] = MessageUtil.getPropertyMessage("label.skucolumn.totalqty");
		skuHeaderNames[32] = MessageUtil.getPropertyMessage("label.skucolumn.price1total");
		skuHeaderNames[33] = MessageUtil.getPropertyMessage("label.skucolumn.price2total");
		skuHeaderNames[34] = MessageUtil.getPropertyMessage("label.skucolumn.pricetotal");
		skuHeaderNames[35] = MessageUtil.getPropertyMessage("label.excel.totalsellingprice");
		
		return skuHeaderNames;
	}
	
	public static String[] getWeekDayNames() {
		String[] weekDayNames = new String[7];
		weekDayNames[0] = MessageUtil.getPropertyMessage("label.day.sunday");
		weekDayNames[1] = MessageUtil.getPropertyMessage("label.day.monday");
		weekDayNames[2] = MessageUtil.getPropertyMessage("label.day.tuesday");
		weekDayNames[3] = MessageUtil.getPropertyMessage("label.day.wednesday");
		weekDayNames[4] = MessageUtil.getPropertyMessage("label.day.thursday");
		weekDayNames[5] = MessageUtil.getPropertyMessage("label.day.friday");
		weekDayNames[6] = MessageUtil.getPropertyMessage("label.day.saturday");
		return weekDayNames;
	}
	
	public static String getMDLbl(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("M/d");
		Calendar cal = Calendar.getInstance();
		long millis = DateFormatter.toDateObj(date).getTime();
		StringBuffer buff = new StringBuffer(sdf.format(new Date(millis)));
		cal.setTimeInMillis(millis);

		Integer weekDay = Integer.valueOf(cal.get(Calendar.DAY_OF_WEEK) - 1);
		/* 0:sun, 1:mon: 2:tue 3:wed 4:thu 5:fri 6:sat */

		buff.append("(");
		buff.append(getWeekDayNames()[weekDay.intValue()]);
		buff.append(")");

		return buff.toString();
	}
	
	private static int getColumnVisibility(String headerKey, HideColumn hideColumn){
		if(headerKey.equals(SKUColumnConstantsExcel.SKU_ID)){
			return 0;
		}else if(headerKey.equals(SKUColumnConstantsExcel.COMPANY_NAME)){
			return Integer.parseInt(hideColumn.getCompanyName());
		}else if(headerKey.equals(SKUColumnConstantsExcel.SELLER_NAME)){
			return Integer.parseInt(hideColumn.getSellerName());
		}else if(headerKey.equals(SKUColumnConstantsExcel.SKU_GROUP_NAME)){
			return Integer.parseInt(hideColumn.getGroupName());
		}else if(headerKey.equals(SKUColumnConstantsExcel.MARKET_CONDITION)){
			return Integer.parseInt(hideColumn.getMarketCondition());
		}else if(headerKey.equals(SKUColumnConstantsExcel.SKU_NAME)){
			return Integer.parseInt(hideColumn.getSkuName());
		}else if(headerKey.equals(SKUColumnConstantsExcel.AREA_PRODUCTION)){
			return Integer.parseInt(hideColumn.getAreaProduction());
		}else if(headerKey.equals(SKUColumnConstantsExcel.CLASS1)){
			return Integer.parseInt(hideColumn.getClazz1());
		}else if(headerKey.equals(SKUColumnConstantsExcel.CLASS2)){
			return Integer.parseInt(hideColumn.getClazz2());
		}else if(headerKey.equals(SKUColumnConstantsExcel.PACKAGE_QTY)){
			return Integer.parseInt(hideColumn.getPackageQty());
		}else if(headerKey.equals(SKUColumnConstantsExcel.PACKAGE_TYPE)){
			return Integer.parseInt(hideColumn.getPackageType());
		}else if(headerKey.equals(SKUColumnConstantsExcel.PRICE_1)){
			return Integer.parseInt(hideColumn.getPrice1());
		}else if(headerKey.equals(SKUColumnConstantsExcel.PRICE_2)){
			return Integer.parseInt(hideColumn.getPrice2());
		}else if(headerKey.equals(SKUColumnConstantsExcel.PRICE_WO_TAX)){
			return Integer.parseInt(hideColumn.getPriceWOTax());
		}else if(headerKey.equals(SKUColumnConstantsExcel.PRICE_W_TAX)){
			return Integer.parseInt(hideColumn.getPriceWTax());
		}else if(headerKey.equals(SKUColumnConstantsExcel.UOM)){
			return Integer.parseInt(hideColumn.getUom());		
		}else if(headerKey.equals(SKUColumnConstantsExcel.COMMENT)){
			return 0;			
		}else if(headerKey.equals(SKUColumnConstantsExcel.PURCHASE_PRICE)){
			return Integer.parseInt(hideColumn.getPurchasePrice());
		}else if(headerKey.equals(SKUColumnConstantsExcel.SELLING_PRICE)){
			return Integer.parseInt(hideColumn.getSellingPrice());
		}else if(headerKey.equals(SKUColumnConstantsExcel.SELLING_UOM)){
			return Integer.parseInt(hideColumn.getSellingUom());
		}else if(headerKey.equals(SKUColumnConstantsExcel.TOTAL_QUANTITY)){
			return 1;
		}else if(headerKey.equals(SKUColumnConstantsExcel.PRICE1_TOTAL)){
			return 1;
		}else if(headerKey.equals(SKUColumnConstantsExcel.PRICE2_TOTAL)){
			return 1;
		}else if(headerKey.equals(SKUColumnConstantsExcel.PRICE_TOTAL)){
			return 1;
		}else if(headerKey.equals(SKUColumnConstantsExcel.TOTAL_SELLING_PRICE)){
			//IF Selling Price is showing, Total Selling Price must also show
			return Integer.parseInt(hideColumn.getSellingPrice()); 
		}else if(headerKey.equals(SKUColumnConstantsExcel.SKU_COMMENT)){
			return Integer.parseInt(hideColumn.getSkuComment());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_01)){
			return Integer.parseInt(hideColumn.getColumn01());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_02)){
			return Integer.parseInt(hideColumn.getColumn02());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_03)){
			return Integer.parseInt(hideColumn.getColumn03());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_04)){
			return Integer.parseInt(hideColumn.getColumn04());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_05)){
			return Integer.parseInt(hideColumn.getColumn05());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_06)){
			return Integer.parseInt(hideColumn.getColumn06());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_07)){
			return Integer.parseInt(hideColumn.getColumn07());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_08)){
			return Integer.parseInt(hideColumn.getColumn08());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_09)){
			return Integer.parseInt(hideColumn.getColumn09());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_10)){
			return Integer.parseInt(hideColumn.getColumn10());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_11)){
			return Integer.parseInt(hideColumn.getColumn11());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_12)){
			return Integer.parseInt(hideColumn.getColumn12());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_13)){
			return Integer.parseInt(hideColumn.getColumn13());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_14)){
			return Integer.parseInt(hideColumn.getColumn14());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_15)){
			return Integer.parseInt(hideColumn.getColumn15());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_16)){
			return Integer.parseInt(hideColumn.getColumn16());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_17)){
			return Integer.parseInt(hideColumn.getColumn17());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_18)){
			return Integer.parseInt(hideColumn.getColumn18());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_19)){
			return Integer.parseInt(hideColumn.getColumn19());
		}else if(headerKey.equals(SKUColumnConstantsExcel.COLUMN_20)){
			return Integer.parseInt(hideColumn.getColumn20());
		}
		return 0;
	}
	
	/**
	 * Columns         Seller   BuyerAdmin    Buyer       
	 * price1            o
	 * price1total       o
	 * price2            o          o
	 * price2total       o          o
	 * purchasePrice                o           o
	 * sellingPrice                 o           o
	 * sellingUom                   o           o
	 * skuComments                  o           o
	 * 
	 */
	
	public static int[] setColumnVisibility(HideColumn hideColumn, Integer sheetTypeId){
		int[] headerFlag = new int[skuHeaderKeys.length];
		
		// SELLER / SELLER ADMIN
		if (sheetTypeId.equals(SheetTypeConstants.SELLER_ORDER_SHEET) ||	
			sheetTypeId.equals(SheetTypeConstants.SELLER_ALLOCATION_SHEET) ||
			sheetTypeId.equals(SheetTypeConstants.SELLER_AKADEN_SHEET) ||
			sheetTypeId.equals(SheetTypeConstants.SELLER_BILLING_SHEET) ||
			sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ORDER_SHEET) ||
			sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_ALLOCATION_SHEET) ||
			sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_AKADEN_SHEET) ||
			sheetTypeId.equals(SheetTypeConstants.SELLER_ADMIN_BILLING_SHEET)){
			
			//loop for all headers
			for(int i=0; i<skuHeaderKeys.length; i++){
				if(skuHeaderKeys[i].equals(SKUColumnConstantsExcel.PURCHASE_PRICE) ||
				   skuHeaderKeys[i].equals(SKUColumnConstantsExcel.SELLING_PRICE) ||
				   skuHeaderKeys[i].equals(SKUColumnConstantsExcel.SELLING_UOM) ||
				   skuHeaderKeys[i].equals(SKUColumnConstantsExcel.SKU_COMMENT)){
					headerFlag[i] = 0;
				}else{
					headerFlag[i] = getColumnVisibility(skuHeaderKeys[i], hideColumn);
				}
			}
		
		// BUYER ADMIN
		}else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_ORDER_SHEET) ||	
			sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_RECEIVED_SHEET) ||
			sheetTypeId.equals(SheetTypeConstants.BUYER_ADMIN_BILLING_SHEET)){
			
			//loop for all headers
			for(int i=0; i<skuHeaderKeys.length; i++){
				if(skuHeaderKeys[i].equals(SKUColumnConstantsExcel.PRICE_1) ||
				   skuHeaderKeys[i].equals(SKUColumnConstantsExcel.PRICE1_TOTAL)){
					headerFlag[i] = 0;
				}else{
					headerFlag[i] = getColumnVisibility(skuHeaderKeys[i], hideColumn);
				}
			}			
		
		// BUYER
		}else if (sheetTypeId.equals(SheetTypeConstants.BUYER_ORDER_SHEET) ||	
			sheetTypeId.equals(SheetTypeConstants.BUYER_RECEIVED_SHEET) ||
			sheetTypeId.equals(SheetTypeConstants.BUYER_BILLING_SHEET)){
			
			//loop for all headers
			for(int i=0; i<skuHeaderKeys.length; i++){
				if(skuHeaderKeys[i].equals(SKUColumnConstantsExcel.PRICE_1) ||
				   skuHeaderKeys[i].equals(SKUColumnConstantsExcel.PRICE1_TOTAL) ||
				   skuHeaderKeys[i].equals(SKUColumnConstantsExcel.PRICE_2) ||
				   skuHeaderKeys[i].equals(SKUColumnConstantsExcel.PRICE2_TOTAL)){
					headerFlag[i] = 0;
				}else{
					headerFlag[i] = getColumnVisibility(skuHeaderKeys[i], hideColumn);
				}
			}			
		}
		
		return headerFlag;
	}
}
