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
 * Jun 2, 2010		Pammie		
 */
package com.freshremix.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.freshremix.constants.SheetTypeConstants;
import com.freshremix.constants.UploadCsvMessages;
import com.freshremix.treegrid.CommentsTreeGridItem;

/**
 * @author Pammie
 *
 */
public class UploadCSVUtil {
	private String skuId; //SKU_ID,
    private String skuName; //DESCR,
    private String date; //Date,
    private String quantity; //Quantity,
    private String uom; //UOM,
    private String pQuantity; //Packaging Quantity,
    private String price; //Unit Price,
    private String skuGroupId; //SKU GROUP ID,
    private String skuGroupName; //SKU GROUP NAME,
    private String buyerEntityId; //BUYER ID,
    private String buyerLongName; //BUYER NAME,
    private String sellerId; //SELLER ID,
    private String sellerLongName; //SELLER NAME,
    private String marketCond; //Market condition,
    private String areaProd; //Area Of Production,
    private String clazz; // Class1
    private String grade; // Class2
    private String packingType; //Packing Type,
    private String priceWTax; //Unit Price with Tax,
    private String category; //Category,
    private String skuOrderNum; //SKU Order Number,
    private String buyerOrderNum; //Buyer Order Number,
    private String finFlag; //Finalization Flag,
    private String sheetType; //Sheet Type,
    private String extSkuId; //EXTERNAL_SKU_ID,
    private String opFlag; //Operation Flag
    private String visibility; //DISPLAY Flag
    private String price1;
    private String price2;
    
    private String[] csvSplits;
    private final String quoteReplace = "!x@";
    private final String commaReplace = "!y@";
    private final String pieceUOM = "\u30d4\u30fc\u30b9";
    private final String caseUOM = "C/S";
    private final String kgUOM = "KG";
    public static final String fruitCat = "FRUITS";
    public static final String veggyCat = "VEGETABLES";
    public static final String fishCat = "FISH";
    public static final String ADD = "1";
    public static final String EDIT = "2";
    public static final String DELETE = "3";
    public static final String VISIBILITYFLAG = "4";
    
//    public static Map skuIDToDeleteMap = new HashMap();
//    public static Map skuIDToUpdateMap = new HashMap();
    
    public static final String charSet = "SJIS";
    public static String header[] = {"SKU_ID","DESCR","Date","Quantity","UOM","Packaging Quantity",
                       "Unit Price","SKU GROUP ID","SKU GROUP NAME","BUYER ID",
                       "BUYER NAME","SELLER ID","SELLER NAME","Market condition",
                       "Area Of Production","Class 1","Class 2","Packing Type",
                       "Unit Price with Tax","Category","SKU Order Number",
                       "Buyer Order Number","Finalization Flag","Sheet Type",
                       "EXTERNAL_SKU_ID","Operation Flag","DISPLAY_FLAG","Price1","Price2"};
    
    public static final int EXTERNAL_SKUID_LIMIT = 42;

    public UploadCSVUtil(String csvLine) {
        /* replace double quotes (reversing DownloadCSVUtil) */
        String doubleToSingleQ = csvLine.replaceAll("\"\"", this.quoteReplace);
        this.csvSplits = doubleToSingleQ.split(",", UploadCSVUtil.header.length+1);

        if (this.csvSplits.length != UploadCSVUtil.header.length)
            this.csvSplits = transformCSVLine(doubleToSingleQ);
    }
    
    
    private String[] transformCSVLine(String csvLine) {
        String temp;
        
        char[] charArr;
        StringBuffer bufferFromStart;
        StringBuffer bufferSplit;
        boolean withQuote;
        boolean didReplace;
        
        /* replace "0???" to 0??? and ",,," to ,,, (reversing DownloadCSVUtil) */
        charArr = csvLine.toCharArray();
        bufferFromStart = new StringBuffer();
        bufferSplit = new StringBuffer();
        withQuote = false;
        didReplace = false;
        for (int i=0; i<charArr.length; i++) {
            char c = charArr[i];
            
            switch( c ) {
                case '"':
                    if (!withQuote) { /* beginning of double quote */
                        String bufferString = bufferSplit.toString();
                        //System.out.println("0bufferString:[" + bufferString + "]");
                        bufferFromStart.append(bufferString);
                        bufferSplit = new StringBuffer();
                        bufferSplit.append(c);
                        withQuote = true;
                    }
                    else { /* end of double quote */
                        bufferSplit.append(c);
                        String bufferString = bufferSplit.toString();
                        //System.out.println("1bufferString:[" + bufferString + "]");
                        
                        if (bufferString.matches("\"0(\\d+?)\"")) {
                            bufferString = bufferString.replaceAll("\"", "");
                        }
                        else if ((bufferString.matches("\"(.*?)(,+?)(.*?)\""))) {
                            bufferString = bufferString.replaceAll("\"", "");
                            bufferString = bufferString.replaceAll(",", this.commaReplace);
                        }
                        else
                            bufferString = bufferString.replaceAll("\"", "");
                            
                        bufferFromStart.append(bufferString);
                        bufferSplit = new StringBuffer();
                        withQuote = false;
                    }
                    break;
                default:
                    bufferSplit.append(c);
                    String bufferString = bufferSplit.toString();
                        
                    if (i == charArr.length-1)
                        bufferFromStart.append(bufferString);
                        
                    break;
            }
        }
        /* end replace "0???" to 0??? and ",,," to ,,, (reversing DownloadCSVUtil) */
        
        String temp2 = bufferFromStart.toString();
        //System.out.println("temp2:\n[" + temp2 + "]");
        
        return temp2.split(",", this.header.length+1);
    }
    
    /**
	 * Fill up sku variables and validate
	 */
//    public Map validateFields(){//(Map skuMap, Map skuGroupMap, String prodSheetDate, Integer prodSheetEntityId, List buyersDP) {
    public List<CommentsTreeGridItem> validateFields(String validityDate, Integer sellerUserId, Integer rowNum){	
//        Map errorMessageMap = new LinkedHashMap();
//        MessageCode message = null;
    	List<CommentsTreeGridItem> items = new ArrayList<CommentsTreeGridItem>();
    	CommentsTreeGridItem it = new CommentsTreeGridItem();
    	
        int i=0;
        boolean opFlagError = false;
        //int opFlagIndex = this.csvSplits.length-2;
        int opFlagIndex = 25;
        
        for (int j=0; j<this.csvSplits.length; j++) {
            this.csvSplits[j] = ((this.csvSplits[j]).replaceAll(this.quoteReplace, "\"")).
                replaceAll(this.commaReplace, ",").trim();
           // System.out.println("values:[" + this.csvSplits[j] + "]");
        }

        /* 26 Operation Flag */
        this.opFlag = this.csvSplits[opFlagIndex];
        if (!(this.opFlag.equalsIgnoreCase(UploadCSVUtil.ADD) ||
              this.opFlag.equalsIgnoreCase(UploadCSVUtil.EDIT) ||
              this.opFlag.equalsIgnoreCase(UploadCSVUtil.DELETE) ||
              this.opFlag.equalsIgnoreCase(UploadCSVUtil.VISIBILITYFLAG))) {
            opFlagError = true;
        }
        
        /* 1 SKU_ID */
        this.skuId = this.csvSplits[i++];
        if (this.opFlag.equals(UploadCSVUtil.ADD)) {
            if (this.skuId.length()>0) {
            	String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_SKUIDBLANK;
            	it = new CommentsTreeGridItem();
    			it.addCell(rowNum);
    			it.addCell(new Integer(i));
    			it.addCell(errorMessage);
    	        items.add(it);
            }
        }
        else {
            if (this.skuId.length()==0) {
            	String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_REQUIRED;
            	it = new CommentsTreeGridItem();
            	it.addCell(rowNum);
            	it.addCell(new Integer(i));
            	it.addCell(errorMessage);
                items.add(it);
            } else if (!isPositiveNumber(this.skuId) || this.skuId.length() > 9) {
            	String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_NUMBER;
            	it = new CommentsTreeGridItem();
    			it.addCell(rowNum);
    			it.addCell(new Integer(i));
    			it.addCell(errorMessage);
    	        items.add(it);
            }
        }
        
        /* 2 DESCR */
        this.skuName = this.csvSplits[i++];
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (this.skuName.length()==0) {
		  		String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_REQUIRED;
            	it = new CommentsTreeGridItem();
    			it.addCell(rowNum);
    			it.addCell(new Integer(i));
    			it.addCell(errorMessage);
    	        items.add(it);
        	}
        	else if (this.skuName.length() > 42) {
		  		String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_LENGTH;
            	it = new CommentsTreeGridItem();
    			it.addCell(rowNum);
    			it.addCell(new Integer(i));
    			it.addCell(errorMessage);
    	        items.add(it);
        	}
//        }
        
        /* 3 Date */
        this.date = this.csvSplits[i++];
        if (this.date.length()==0) {
  			String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_REQUIRED;
        	it = new CommentsTreeGridItem();
			it.addCell(rowNum);
			it.addCell(new Integer(i));
			it.addCell(errorMessage);
	        items.add(it);
        }
        else if (!isValidDate(this.date) || this.date.length()!=8 || !isPositiveNumber(this.date)) {
        	String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_DATE;
        	it = new CommentsTreeGridItem();
        	it.addCell(rowNum);
        	it.addCell(new Integer(i));
        	it.addCell(errorMessage);
            items.add(it);
        } else if (!this.date.equals(validityDate)) {
        	String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_NOTPRODSHEETDATE;
        	it = new CommentsTreeGridItem();
        	it.addCell(rowNum);
        	it.addCell(new Integer(i));
        	it.addCell(errorMessage);
            items.add(it);
        }
        
        /* 4 Quantity */
        this.quantity = this.csvSplits[i++];
        /* ignore data */
        
        /* 5 UOM */    
        this.uom = this.csvSplits[i++];
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (this.uom.length()==0) {
		  		String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_REQUIRED;
            	it = new CommentsTreeGridItem();
	        	it.addCell(rowNum);
	        	it.addCell(new Integer(i));
	        	it.addCell(errorMessage);
	            items.add(it);
//        	} else {
//        		if (this.csvSplits[19].equalsIgnoreCase(this.fishCat)){
//        			if (this.uom.equalsIgnoreCase(this.pieceUOM)) {
//        				this.uom = "Piece";
//        			}
//        			else if (this.uom.equalsIgnoreCase(this.kgUOM)) {
//        				this.uom = "Kilos";
//        			}
//        			else {
//  					String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_UOM;
//        				MessageCode messageCode = new MessageCode("sku.upload.error.uom", new Object[] {this.pieceUOM, this.kgUOM});
//        				errorMessageMap.put(new Integer(i), getMessage(resourceBundle, messageCode));
//        			}
//        		} else if (this.csvSplits[19].equalsIgnoreCase(this.fruitCat) || this.csvSplits[19].equalsIgnoreCase(this.veggyCat)){
//        			if (this.uom.equalsIgnoreCase(this.pieceUOM)) {
//        				this.uom = "Piece";
//        			}
//        			else if (this.uom.equalsIgnoreCase(this.caseUOM)) {
//        				this.uom = "Case";
//        			}
//        			else {
//						String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_UOM;
//        				MessageCode messageCode = new MessageCode("sku.upload.error.uom", new Object[] {this.pieceUOM, this.caseUOM});
//        				errorMessageMap.put(new Integer(i), getMessage(resourceBundle, messageCode));
//        			}
//        		}
        	}
//        }
        
        /* 6 Packaging Quantity */
        this.pQuantity = this.csvSplits[i++];
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (this.pQuantity.length()==0) {
				String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_REQUIRED;
            	it = new CommentsTreeGridItem();
	        	it.addCell(rowNum);
	        	it.addCell(new Integer(i));
	        	it.addCell(errorMessage);
	            items.add(it);
        	}
        	else if (!isPositiveNumber(this.pQuantity) || this.pQuantity.length() > 9) {
				String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_NUMBER;
            	it = new CommentsTreeGridItem();
	        	it.addCell(rowNum);
	        	it.addCell(new Integer(i));
	        	it.addCell(errorMessage);
	            items.add(it);
        	}
//        }
        
        /* 7 Unit Price */
        this.price = this.csvSplits[i++];
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (!(opFlagError || this.opFlag.equals(UploadCSVUtil.DELETE))) {
        		if (!isPositiveNumber(this.price) || this.price.length() > 9) {
					String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_PRICE;
	            	it = new CommentsTreeGridItem();
		        	it.addCell(rowNum);
		        	it.addCell(new Integer(i));
		        	it.addCell(errorMessage);
		            items.add(it);
        		}
        	}
//        }        
        /* 8 SKU GROUP ID */
        this.skuGroupId = this.csvSplits[i++];
//        int idxGroupId = i;
//       
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (this.skuGroupId.length()==0) {
				String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_REQUIRED;
            	it = new CommentsTreeGridItem();
	        	it.addCell(rowNum);
	        	it.addCell(new Integer(i));
	        	it.addCell(errorMessage);
	            items.add(it);
        	} else if (!isPositiveNumber(this.skuGroupId) || this.skuGroupId.length() > 9) {
				String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_NUMBER;
            	it = new CommentsTreeGridItem();
	        	it.addCell(rowNum);
	        	it.addCell(new Integer(i));
	        	it.addCell(errorMessage);
	            items.add(it);
        	} 
//        	else if (!skuGroupMap.containsKey(new Integer(this.skuGroupId))) {
//				String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_NOSKUGROUP;
//        		MessageCode messageCode = new MessageCode("sku.upload.error.noskugroup");
//        		errorMessageMap.put(new Integer(i), getMessage(resourceBundle, messageCode));
//        	}
//        }        
        //System.out.println("skugroupmap:[" + skuGroupMap + "]");
        
        /* 9 SKU GROUP NAME */
        this.skuGroupName = this.csvSplits[i++];
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (this.skuGroupName.length()==0) {
				String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_REQUIRED;
            	it = new CommentsTreeGridItem();
	        	it.addCell(rowNum);
	        	it.addCell(new Integer(i));
	        	it.addCell(errorMessage);
	            items.add(it);
        	}
//        }        
//        /* 10 BUYER ID */
        this.buyerEntityId = this.csvSplits[i++];
//        if (this.opFlag.equalsIgnoreCase(this.VISIBILITYFLAG)){
        	if (this.buyerEntityId.length() == 0){
				String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_REQUIRED;
            	it = new CommentsTreeGridItem();
	        	it.addCell(rowNum);
	        	it.addCell(new Integer(i));
	        	it.addCell(errorMessage);
	            items.add(it);
        	}
//        }
        
        /* 11 BUYER NAME */
        this.buyerLongName = this.csvSplits[i++];
        /* ignore data */
        
        /* 12 SELLER ID */
        this.sellerId = this.csvSplits[i++];
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (this.sellerId.length()==0) {
				String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_REQUIRED;
            	it = new CommentsTreeGridItem();
	        	it.addCell(rowNum);
	        	it.addCell(new Integer(i));
	        	it.addCell(errorMessage);
	            items.add(it);
        	} else if (!isPositiveNumber(this.sellerId) || this.sellerId.length() > 9) {
				String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_NUMBER;
            	it = new CommentsTreeGridItem();
	        	it.addCell(rowNum);
	        	it.addCell(new Integer(i));
	        	it.addCell(errorMessage);
	            items.add(it);
        	} else if (!this.sellerId.equals(sellerUserId.toString())) {
				String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_NOTPRODSHEETUSER;
            	it = new CommentsTreeGridItem();
	        	it.addCell(rowNum);
	        	it.addCell(new Integer(i));
	        	it.addCell(errorMessage);
	            items.add(it);
        	}
//        }
        
//        if (this.opFlag.equals(this.VISIBILITYFLAG)){
//        	//System.out.println("BUYER ENTITY : " + this.buyerEntityId);
//        		if (!buyersDP.contains(this.buyerEntityId)){
//					String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_BUYERID;
//        			MessageCode messageCode = new MessageCode("sku.upload.error.buyerId");
//        			errorMessageMap.put(new Integer(i), getMessage(resourceBundle, messageCode));
//        		}
//        	
//        }
        
        /* 13 SELLER NAME */
        this.sellerLongName = this.csvSplits[i++];
        /* ignore data */
        
        /* 14 Market condition */
        this.marketCond = this.csvSplits[i++];
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (!(opFlagError || this.opFlag.equals(UploadCSVUtil.DELETE))) {
        		if (this.marketCond.length() > 42) {
					String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_LENGTH;
	            	it = new CommentsTreeGridItem();
		        	it.addCell(rowNum);
		        	it.addCell(new Integer(i));
		        	it.addCell(errorMessage);
		            items.add(it);
        		}
        	}
//        }        
        /* 15 Area Of Production */
        this.areaProd = this.csvSplits[i++];
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (!(opFlagError || this.opFlag.equals(UploadCSVUtil.DELETE))) {
        		if (this.areaProd.length() > 42) {
					String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_LENGTH;
	            	it = new CommentsTreeGridItem();
		        	it.addCell(rowNum);
		        	it.addCell(new Integer(i));
		        	it.addCell(errorMessage);
		            items.add(it);
        		}
        	}
//        }        
        /* 16 Class 1 */
        this.clazz = this.csvSplits[i++];
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (!(opFlagError || this.opFlag.equals(UploadCSVUtil.DELETE))) {
        		if (this.clazz.length() > 42) {
					String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_LENGTH;
	            	it = new CommentsTreeGridItem();
		        	it.addCell(rowNum);
		        	it.addCell(new Integer(i));
		        	it.addCell(errorMessage);
		            items.add(it);
        		}
        	}
//        }
        
        /* 17 Class 2 */
        this.grade = this.csvSplits[i++];
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (!(opFlagError || this.opFlag.equals(UploadCSVUtil.DELETE))) {
        		if (this.grade.length() > 42) {
					String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_LENGTH;
	            	it = new CommentsTreeGridItem();
		        	it.addCell(rowNum);
		        	it.addCell(new Integer(i));
		        	it.addCell(errorMessage);
		            items.add(it);
        		}
        	}
//        }
        
        /* 18 Packing Type */
        this.packingType = this.csvSplits[i++];
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (!(opFlagError || this.opFlag.equals(UploadCSVUtil.DELETE))) {
        		if (this.packingType.length() > 42) {
					String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_LENGTH;
	            	it = new CommentsTreeGridItem();
		        	it.addCell(rowNum);
		        	it.addCell(new Integer(i));
		        	it.addCell(errorMessage);
		            items.add(it);
        		}
        	}
//        }

        /* 19 Unit Price with Tax */
        this.priceWTax = this.csvSplits[i++];
        /* ignore data */
        
        /* 20 Category */
        this.category = this.csvSplits[i++];
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (this.category.length()==0) {
				String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_REQUIRED;
            	it = new CommentsTreeGridItem();
	        	it.addCell(rowNum);
	        	it.addCell(new Integer(i));
	        	it.addCell(errorMessage);
	            items.add(it);
        	} else if (!(this.category.equalsIgnoreCase(UploadCSVUtil.fruitCat) ||
        			this.category.equalsIgnoreCase(UploadCSVUtil.veggyCat) || this.category.equalsIgnoreCase(UploadCSVUtil.fishCat))) {
				String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_CATEGORY;
            	it = new CommentsTreeGridItem();
	        	it.addCell(rowNum);
	        	it.addCell(new Integer(i));
	        	it.addCell(errorMessage);
	            items.add(it);
        	}
//        }
        
        /* 21 SKU Order Number */
        this.skuOrderNum = this.csvSplits[i++];
        /* ignore data */
        
        /* 22 Buyer Order Number */
        this.buyerOrderNum = this.csvSplits[i++];
        /* ignore data */
        
        /* 23 Finalization Flag */
        this.finFlag = this.csvSplits[i++];
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (this.finFlag.length()==0) {
		 		String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_REQUIRED;
            	it = new CommentsTreeGridItem();
	        	it.addCell(rowNum);
	        	it.addCell(new Integer(i));
	        	it.addCell(errorMessage);
	            items.add(it);
        	} else if (!this.finFlag.equals("0")) {
				String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_FINALIZATION;
            	it = new CommentsTreeGridItem();
	        	it.addCell(rowNum);
	        	it.addCell(new Integer(i));
	        	it.addCell(errorMessage);
	            items.add(it);
        	}
//        }
        
        /* 24 Sheet Type */
        this.sheetType = this.csvSplits[i++];
        if (this.sheetType.length()==0) {
        	String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_REQUIRED;
        	it = new CommentsTreeGridItem();
        	it.addCell(rowNum);
        	it.addCell(new Integer(i));
        	it.addCell(errorMessage);
            items.add(it);
        } else if (!this.sheetType.equals(SheetTypeConstants.SELLER_ORDER_SHEET.toString())) {
			  String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_SHEET;
	        	it = new CommentsTreeGridItem();
	        	it.addCell(rowNum);
	        	it.addCell(new Integer(i));
	        	it.addCell(errorMessage);
	            items.add(it);
        }
        
        /* 25 EXTERNAL_SKU_ID */
        this.extSkuId = this.csvSplits[i++];
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (!(opFlagError || this.opFlag.equals(UploadCSVUtil.DELETE))) {
        		if (this.extSkuId.length() > UploadCSVUtil.EXTERNAL_SKUID_LIMIT) {
		  			String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_LENGTH;
	            	it = new CommentsTreeGridItem();
		        	it.addCell(rowNum);
		        	it.addCell(new Integer(i));
		        	it.addCell(errorMessage);
		            items.add(it);
        		}
        	}
//        }
        /*26 OP*/
        String operationFlag = this.csvSplits[i++];
//        this.opFlag = operationFlag;

        
        this.visibility = this.csvSplits[i++];
//        if (this.opFlag.equals(this.VISIBILITYFLAG)){
//            //this.visibility = this.csvSplits[i++];
//        	if (!this.visibility.trim().equalsIgnoreCase("0") && !this.visibility.trim().equalsIgnoreCase("1")){
//				String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_VISIBILITY;
//    			MessageCode messageCode = new MessageCode("sku.upload.error.visibility", new Object[] {String.valueOf(EonSKUUploadCSV.VISIBILITYFLAG)});
//    			errorMessageMap.put(new Integer(i), getMessage(resourceBundle, messageCode));
//        	}
//        }
        /*28 Price1 */
        this.price1 = this.csvSplits[i++];
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (!(opFlagError || this.opFlag.equals(UploadCSVUtil.DELETE))) {
        		if (!isPositiveNumber(this.price1) || this.price1.length() > 9) {
                    if(!this.price1.trim().equals("")){
                    	String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_PRICE1;
                    	it = new CommentsTreeGridItem();
                    	it.addCell(rowNum);
                    	it.addCell(new Integer(i));
                    	it.addCell(errorMessage);
                        items.add(it);
                    }
        		}
        	}
//        }
        /*29 Price2 */
        this.price2 = this.csvSplits[i++];
//        if (!this.opFlag.equals(this.VISIBILITYFLAG)){
        	if (!(opFlagError || this.opFlag.equals(UploadCSVUtil.DELETE))) {
        		if (!isPositiveNumber(this.price2) || this.price2.length() > 9) {
                    if(!this.price2.trim().equals("")){
                    	String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_PRICE2;
                    	it = new CommentsTreeGridItem();
                    	it.addCell(rowNum);
                    	it.addCell(new Integer(i));
                    	it.addCell(errorMessage);
                        items.add(it);
                    }
        		}
        	}
//        }
        
        /* print op flag error */
        if (opFlagError) {
        	String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_OPERATION;
        	it = new CommentsTreeGridItem();
          	it.addCell(rowNum);
        	it.addCell(new Integer(i));
        	it.addCell(errorMessage);
            items.add(it);
        }
        
        /* if there is no validation error, check this SKU's availability in skuMap */
//        if (errorMessageMap.size()==0) {
//            if (!this.opFlag.equals(this.ADD)) {
//                this.checkInSKUMap(resourceBundle, prodSheetDate, errorMessageMap, skuMap);
//            }
//            else { /* check skugroupid integrity */
//            	if (this.skuGroupId.length() > 0
//            			) {
//                SKUGroup skuGroup = (SKUGroup) skuGroupMap.get(new Integer(this.skuGroupId));
//                if (skuGroup != null && skuGroup.getSellerEntityId().equals(new Integer(this.sellerEntityId))) {
//                    if ( (this.category.equalsIgnoreCase(this.fruitCat) &&
//                         !skuGroup.getCategoryId().equals(SKUCategoryTypeCodes.FRUITS)) ||
//                         (this.category.equalsIgnoreCase(this.veggyCat) &&
//                         !skuGroup.getCategoryId().equals(SKUCategoryTypeCodes.VEGETABLES)) ||
//                         (this.category.equalsIgnoreCase(this.fishCat) &&
//                         !skuGroup.getCategoryId().equals(SKUCategoryTypeCodes.FRESHFISH))
//                         ) {
//	  	  				  String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_SKUGROUP_INCONSISTENT;
//                        MessageCode messageCode = new MessageCode("sku.upload.error.skugroup.inconsistent");
//                        errorMessageMap.put(new Integer(idxGroupId), getMessage(resourceBundle, messageCode));
//                    }
//                }
//                else {
//			  		  String errorMessage = UploadCsvMessages.SKU_UPLOAD_ERROR_SKUGROUP_INCONSISTENT;
//                    MessageCode messageCode = new MessageCode("sku.upload.error.skugroup.inconsistent");
//                    errorMessageMap.put(new Integer(idxGroupId), getMessage(resourceBundle, messageCode));
//                }
//            	}
//            }
//        }
        return items;
    }
            
    /** Checks if string can be converted to Integer */
    private boolean isPositiveNumber(String sNum) {
    	try {
    		int iNum = Integer.parseInt(sNum);
    		if (iNum < 0) return false;
    	} catch (NumberFormatException nfe) {
    		return false;
    	}
    	return true;
    }
    
    /**
	 * Checks if string can be converted to Date
	 */
    private boolean isValidDate(String sDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date dDate = dateFormat.parse(sDate);
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getpQuantity() {
		return pQuantity;
	}

	public void setpQuantity(String pQuantity) {
		this.pQuantity = pQuantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSkuGroupId() {
		return skuGroupId;
	}

	public void setSkuGroupId(String skuGroupId) {
		this.skuGroupId = skuGroupId;
	}

	public String getSkuGroupName() {
		return skuGroupName;
	}

	public void setSkuGroupName(String skuGroupName) {
		this.skuGroupName = skuGroupName;
	}

	public String getBuyerEntityId() {
		return buyerEntityId;
	}

	public void setBuyerEntityId(String buyerEntityId) {
		this.buyerEntityId = buyerEntityId;
	}

	public String getBuyerLongName() {
		return buyerLongName;
	}

	public void setBuyerLongName(String buyerLongName) {
		this.buyerLongName = buyerLongName;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerLongName() {
		return sellerLongName;
	}

	public void setSellerLongName(String sellerLongName) {
		this.sellerLongName = sellerLongName;
	}

	public String getMarketCond() {
		return marketCond;
	}

	public void setMarketCond(String marketCond) {
		this.marketCond = marketCond;
	}

	public String getAreaProd() {
		return areaProd;
	}

	public void setAreaProd(String areaProd) {
		this.areaProd = areaProd;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	
	public String getGrade(){
		return grade;
	}
	
	public void setGrade(String grade){
		this.grade = grade;
	}
	
	public String getPackingType() {
		return packingType;
	}

	public void setPackingType(String packingType) {
		this.packingType = packingType;
	}

	public String getPriceWTax() {
		return priceWTax;
	}

	public void setPriceWTax(String priceWTax) {
		this.priceWTax = priceWTax;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSkuOrderNum() {
		return skuOrderNum;
	}

	public void setSkuOrderNum(String skuOrderNum) {
		this.skuOrderNum = skuOrderNum;
	}

	public String getBuyerOrderNum() {
		return buyerOrderNum;
	}

	public void setBuyerOrderNum(String buyerOrderNum) {
		this.buyerOrderNum = buyerOrderNum;
	}

	public String getFinFlag() {
		return finFlag;
	}

	public void setFinFlag(String finFlag) {
		this.finFlag = finFlag;
	}

	public String getSheetType() {
		return sheetType;
	}

	public void setSheetType(String sheetType) {
		this.sheetType = sheetType;
	}

	public String getExtSkuId() {
		return extSkuId;
	}

	public void setExtSkuId(String extSkuId) {
		this.extSkuId = extSkuId;
	}

	public String getOpFlag() {
		return opFlag;
	}

	public void setOpFlag(String opFlag) {
		this.opFlag = opFlag;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getPrice1() {
		return price1;
	}

	public void setPrice1(String price1) {
		this.price1 = price1;
	}

	public String getPrice2() {
		return price2;
	}

	public void setPrice2(String price2) {
		this.price2 = price2;
	}

	public String[] getCsvSplits() {
		return csvSplits;
	}

	public void setCsvSplits(String[] csvSplits) {
		this.csvSplits = csvSplits;
	}
}
