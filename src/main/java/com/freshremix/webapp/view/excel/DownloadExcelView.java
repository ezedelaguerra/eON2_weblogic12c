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
 * Apr 20, 2010		nvelasquez
 * Dec 5, 2012      mikes     Redmine 1110		
 */
package com.freshremix.webapp.view.excel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.CellView;
import jxl.HeaderFooter;
import jxl.SheetSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.view.document.AbstractJExcelView;

import com.freshremix.constants.SKUColumnConstantsExcel;
import com.freshremix.dao.UserPreferenceDao;
import com.freshremix.model.Category;
import com.freshremix.model.DownloadExcelSheet;
import com.freshremix.model.ProfitPreference;
import com.freshremix.model.User;
import com.freshremix.service.AppSettingService;
import com.freshremix.ui.model.DownloadExcelSettingForm;
import com.freshremix.util.CategoryUtil;
import com.freshremix.util.DownloadExcelUtil;
import com.freshremix.util.StringUtil;

/**
 * @author nvelasquez
 *
 */
public class DownloadExcelView extends AbstractJExcelView {

	private AppSettingService appSettingService;
	private UserPreferenceDao userPreferenceDao;
	
	public void setUserPreferenceDao(UserPreferenceDao userPreferenceDao) {
		this.userPreferenceDao = userPreferenceDao;
	}

	public void setAppSettingService(AppSettingService appSettingService) {
		this.appSettingService = appSettingService;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map model, WritableWorkbook workBook,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<DownloadExcelSheet> xlSheets = (List<DownloadExcelSheet>) model.get("xlSheets");
		DownloadExcelSettingForm dxls = (DownloadExcelSettingForm) model.get("xlSettings");
		User user = (User) model.get("User");
		String eONSheetName = (String) model.get("eONSheetName");
		String startDate = (String) model.get("startDate");
		String endDate = (String) model.get("endDate");
		Integer categoryId = (Integer) model.get("categoryId");
		Map<String,Category> categoryMap = appSettingService.getCategoryMasterList();
		
		//System.out.println("dxls:[" + dxls.toString() + "]");
		//System.out.println("no. of sheets:[" + xlSheets.size() +"]");
		Map<String, WritableCellFormat> writableCellFormatMap = createWritableCellFormatMap();
		for (int i=0; i<xlSheets.size(); i++) {
			DownloadExcelSheet xlSheet = xlSheets.get(i);
			this.createSheet(workBook, xlSheet, i, Integer.parseInt(dxls.getPriceComputeOpt()), writableCellFormatMap, user);
		}
		if (xlSheets.size() == 0) {
			workBook.createSheet("0", 0);
		}
	    
		String fileName = this.getXLFileName(dxls, user, eONSheetName, categoryId, startDate, endDate, categoryMap);
	    response.setContentType("application/octet-stream.csv");
		//System.out.println("finished constructing excel...:[" + this.getContentType() + "]");
		//System.out.println("test excel...:[" + response.getContentType() + "]");
		response.setHeader ("Pragma", "No-cache");
        response.setDateHeader ("Expires", 0);
        response.setHeader ("Cache-Control", "no-cache");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");

	}
	
	private Map<String,WritableCellFormat> createWritableCellFormatMap() throws Exception {
		// centralize creation of WritableCellFormat to prevent exceeding 441
		// max cell formats in jxl/excel
		int defaultMapSize = 18;
		
		Map<String, WritableCellFormat> writeableCellFormatMap = new HashMap<String, WritableCellFormat>(
				defaultMapSize);
		/* cell formats */
		writeableCellFormatMap.put(DownloadExcelUtil.LBL_CF_HEADER1, this.getLblCFHeader1());
		writeableCellFormatMap.put(DownloadExcelUtil.LBL_CF_HEADER2, this.getLblCFHeader2());
		writeableCellFormatMap.put(DownloadExcelUtil.LBL_CF_HEADER3, this.getLblCFHeader3());
		writeableCellFormatMap.put(DownloadExcelUtil.LBL_CF_DATA1, this.getLblCFData1());
		writeableCellFormatMap.put(DownloadExcelUtil.LBL_CF_DATA2, this.getLblCFData2());
		writeableCellFormatMap.put(DownloadExcelUtil.LBL_CF_DATA3, this.getLblCFData3());
		writeableCellFormatMap.put(DownloadExcelUtil.LBL_CF_DATA4, this.getLblCFData4());
		writeableCellFormatMap.put(DownloadExcelUtil.LBL_CF_DATA5, this.getLblCFData5());
		writeableCellFormatMap.put(DownloadExcelUtil.LBL_CF_DATA6, this.getLblCFData6());
		writeableCellFormatMap.put(DownloadExcelUtil.DBL_CF_DATA1, this.getDblCFData1());
		writeableCellFormatMap.put(DownloadExcelUtil.DBL_CF_DATA2, this.getDblCFData2());
		writeableCellFormatMap.put(DownloadExcelUtil.DBL_CF_DATA3, this.getDblCFData3());
		writeableCellFormatMap.put(DownloadExcelUtil.DBL_CF_DATA4, this.getDblCFData4());
		writeableCellFormatMap.put(DownloadExcelUtil.DBL_CF_DATA5, this.getDblCFData5());
		writeableCellFormatMap.put(DownloadExcelUtil.DBL_CF_INT_QTY1, this.getDblCFIntQty1());
		writeableCellFormatMap.put(DownloadExcelUtil.DBL_CF_INT_QTY2, this.getDblCFIntQty2());
		writeableCellFormatMap.put(DownloadExcelUtil.DBL_CF_INT_QTY3, this.getDblCFIntQty3());
		writeableCellFormatMap.put(DownloadExcelUtil.DBL_CF_DEC_QTY2, this.getDblCFDecQty2());
		writeableCellFormatMap.put(DownloadExcelUtil.DBL_CF_INT_QTY1_LOCKED, this.getDblCFIntQty1Locked());
		return writeableCellFormatMap;
		
	}

	private String getXLFileName(DownloadExcelSettingForm dxls, User user, String eONSheetName,
			Integer categoryId, String startDate, String endDate, Map<String,Category> categoryMap) {
		StringBuffer xlFileName = new StringBuffer();
		
		String priceFormat = "";
		Integer priceComputeOpt = Integer.valueOf(dxls.getPriceComputeOpt());
		switch(priceComputeOpt.intValue()){
			case 0:   priceFormat = "noprice";break;
			case 1:   priceFormat = "withprice_1";break;
			case 2:   priceFormat = "withprice_2";break;
			case 3:   priceFormat = "withprice_3";break;
			case 4:   priceFormat = "withprice_4";		
		}
		
		String hasQtyFlag = "true";
		if (dxls.getHasQty().equals("0")) hasQtyFlag = "false";
		
		xlFileName.append(startDate.substring(0, 4));
		xlFileName.append("-");
		xlFileName.append(startDate.substring(4, 6));
		xlFileName.append("-");
		xlFileName.append(startDate.substring(6));
		xlFileName.append("_");
		xlFileName.append(eONSheetName);
		xlFileName.append("_");
		xlFileName.append(CategoryUtil.getCategoryDesc(categoryId.intValue(), categoryMap));
		xlFileName.append("_");
		xlFileName.append(priceFormat);
		xlFileName.append("_");
		xlFileName.append(hasQtyFlag);
		
		return xlFileName.toString();
	}
	
	private void createSheet(WritableWorkbook workBook, DownloadExcelSheet xlSheet,
			int index, int priceOpt, Map<String, WritableCellFormat> writableCellFormatMap, User user)
			throws Exception {
		
		List<String> skuHeaderKeys = xlSheet.getSkuHeaderKeys();
		List<String> qtyHeaderKeys = xlSheet.getQtyHeaderKeys();
		List<String> ttlHeaderKeys = xlSheet.getTtlHeaderKeys();
		Map<String, String> skuHeaderMap = xlSheet.getSkuHeaderMap();
		Map<String, String> qtyHeaderMap = xlSheet.getQtyHeaderMap();
		Map<String, String> ttlHeaderMap = xlSheet.getTtlHeaderMap();
		
		List<Map<String, Object>> skuDataMaps = xlSheet.getSkuDataMaps();
		String wSheetName = xlSheet.getExcelSheetName();
		WritableSheet wSheet = workBook.createSheet(wSheetName, index);
		
		/* sheet settings */
		this.applyPrintSettings(wSheet, xlSheet);
		//
		
		/* cell formats */
		WritableCellFormat lblCFHeader1 = writableCellFormatMap.get(DownloadExcelUtil.LBL_CF_HEADER1);
		WritableCellFormat lblCFHeader2 = writableCellFormatMap.get(DownloadExcelUtil.LBL_CF_HEADER2);
		WritableCellFormat lblCFHeader3 = writableCellFormatMap.get(DownloadExcelUtil.LBL_CF_HEADER3);
		WritableCellFormat lblCFData1 = writableCellFormatMap.get(DownloadExcelUtil.LBL_CF_DATA1);
		WritableCellFormat lblCFData2 = writableCellFormatMap.get(DownloadExcelUtil.LBL_CF_DATA2);
		WritableCellFormat lblCFData3 = writableCellFormatMap.get(DownloadExcelUtil.LBL_CF_DATA3);
		WritableCellFormat lblCFData4 = writableCellFormatMap.get(DownloadExcelUtil.LBL_CF_DATA4);
		WritableCellFormat lblCFData5 = writableCellFormatMap.get(DownloadExcelUtil.LBL_CF_DATA5);
		WritableCellFormat lblCFData6 = writableCellFormatMap.get(DownloadExcelUtil.LBL_CF_DATA6);
		WritableCellFormat dblCFData1 = writableCellFormatMap.get(DownloadExcelUtil.DBL_CF_DATA1);
		WritableCellFormat dblCFData2 = writableCellFormatMap.get(DownloadExcelUtil.DBL_CF_DATA2);
		WritableCellFormat dblCFData3 = writableCellFormatMap.get(DownloadExcelUtil.DBL_CF_DATA3);
		WritableCellFormat dblCFData4 = writableCellFormatMap.get(DownloadExcelUtil.DBL_CF_DATA4);
		WritableCellFormat dblCFData5 = writableCellFormatMap.get(DownloadExcelUtil.DBL_CF_DATA5);
		WritableCellFormat dblCFIntQty1 = writableCellFormatMap.get(DownloadExcelUtil.DBL_CF_INT_QTY1);
		WritableCellFormat dblCFIntQty2 = writableCellFormatMap.get(DownloadExcelUtil.DBL_CF_INT_QTY2);
		WritableCellFormat dblCFIntQty3 = writableCellFormatMap.get(DownloadExcelUtil.DBL_CF_INT_QTY3);
		//WritableCellFormat dblCFDecQty1 = this.getDblCFDecQty1();
		WritableCellFormat dblCFDecQty2 = writableCellFormatMap.get(DownloadExcelUtil.DBL_CF_DEC_QTY2);
		WritableCellFormat dblCFIntQty1Locked = writableCellFormatMap.get(DownloadExcelUtil.DBL_CF_INT_QTY1_LOCKED);
		//WritableCellFormat dblCFDecQty3 = this.getDblCFDecQty3();
		//
		
		/* labels */
		this.addString(wSheet, 0, 0, xlSheet.geteONSheetName(), lblCFData6);
		String dateMDLabel = xlSheet.getDateMDLabel();
		this.addString(wSheet, 0, 1, dateMDLabel, lblCFData6);
		this.setColumnWidth(0, dateMDLabel, wSheet);
		this.addString(wSheet, 0, 2, xlSheet.getCategoryName(), lblCFData6);
		
		wSheet.mergeCells(5, 0, 6, 0);
		wSheet.mergeCells(7, 0, 11, 0);
		wSheet.mergeCells(5, 1, 6, 1);
		wSheet.mergeCells(7, 1, 11, 1);
		wSheet.mergeCells(5, 2, 6, 2);
		wSheet.mergeCells(7, 2, 11, 2);
		wSheet.mergeCells(5, 3, 6, 3);
		wSheet.mergeCells(7, 3, 11, 3);
		
		this.addString(wSheet, 5, 0, DownloadExcelUtil.userLabel, lblCFData2);
		this.addString(wSheet, 5, 1, DownloadExcelUtil.dateLabel, lblCFData2);
		this.addString(wSheet, 5, 2, DownloadExcelUtil.sellerLabel, lblCFData2);
		this.addString(wSheet, 5, 3, DownloadExcelUtil.buyerLabel, lblCFData2);
		
		this.addString(wSheet, 7, 0, xlSheet.getUserName(), lblCFData3);
		this.addString(wSheet, 7, 1, xlSheet.getDateOption(), lblCFData3);
		this.addString(wSheet, 7, 2, xlSheet.getSellerOption(), lblCFData3);
		this.addString(wSheet, 7, 3, xlSheet.getBuyerOption(), lblCFData3);
		//
		
		/* header */
		int headerRow = DownloadExcelUtil.headerRow;
		int headerColumn = 0;
		for (int i=0; i<skuHeaderKeys.size(); i++) {
			String key = skuHeaderKeys.get(i);
			String cellValue = skuHeaderMap.get(key);
			
			/* packagetype, unitorder */
			if (key.equals(SKUColumnConstantsExcel.PACKAGE_TYPE) || 
				key.equals(SKUColumnConstantsExcel.UOM))
				cellValue = this.addLineFeed(cellValue);
			//
			
			this.addString(wSheet, headerColumn, headerRow, cellValue, lblCFHeader1);
			/* auto fit */
			this.setColumnWidth(headerColumn, cellValue, wSheet);
			
			if (key.equals(SKUColumnConstantsExcel.PACKAGE_QTY) ||
				key.equals(SKUColumnConstantsExcel.PACKAGE_TYPE) ||
				key.equals(SKUColumnConstantsExcel.UOM))
					wSheet.setColumnView(headerColumn, 5);
			
			headerColumn++;
		}
		for (int i=0; i<qtyHeaderKeys.size(); i++) {
			String key = qtyHeaderKeys.get(i);
			String cellValue = qtyHeaderMap.get(key);
			
			this.addString(wSheet, headerColumn, headerRow, cellValue, lblCFHeader2);
			/* auto fit */
			this.setColumnWidth(headerColumn, cellValue, wSheet);
			
			headerColumn++;
		}
		for (int i=0; i<ttlHeaderKeys.size(); i++) {
			String key = ttlHeaderKeys.get(i);
			String cellValue = ttlHeaderMap.get(key);
			
			/* price1total, price2total */
			if (key.equals(SKUColumnConstantsExcel.PRICE1_TOTAL) || 
				key.equals(SKUColumnConstantsExcel.PRICE2_TOTAL)) {
				cellValue = this.addLineFeed(cellValue);
				//wSheet.mergeCells(headerColumn, 1, headerColumn, 2);
				this.addString(wSheet, headerColumn, DownloadExcelUtil.grandtotalRow+1, cellValue, lblCFHeader2);
			}
			//
			/* pricetotal */
			if (key.equals(SKUColumnConstantsExcel.PRICE_TOTAL)) {
				cellValue = this.addLineFeed(cellValue);
				//wSheet.mergeCells(headerColumn, 1, headerColumn, 2);
				String cellValue2 = cellValue;
				if (priceOpt == 2 || priceOpt == 4) {
					cellValue = this.addLineFeed(DownloadExcelUtil.priceTotalWithTax);
					cellValue2 = cellValue;
				}
				else if (priceOpt == 3) {
					cellValue2 = this.addLineFeed(DownloadExcelUtil.priceTotalWithTax);
				}
				
				this.addString(wSheet, headerColumn, DownloadExcelUtil.grandtotalRow+1, cellValue2, lblCFHeader2);
			}
			
			if (key.equals(SKUColumnConstantsExcel.TOTAL_SELLING_PRICE)) {
					cellValue = this.addLineFeed(cellValue);
					//wSheet.mergeCells(headerColumn, 1, headerColumn, 2);
					this.addString(wSheet, headerColumn, DownloadExcelUtil.grandtotalRow+1, cellValue, lblCFHeader2);
				}
			//
			
			this.addString(wSheet, headerColumn, headerRow, cellValue, lblCFHeader2);
			/* auto fit */
			this.setColumnWidth(headerColumn, cellValue, wSheet);
			
			headerColumn++;
		}
		//
		
		/* data */
		BigDecimal bdPrice1GrandTotal = new BigDecimal(0);
		BigDecimal bdPrice2GrandTotal = new BigDecimal(0);
		BigDecimal bdPriceGrandTotal = new BigDecimal(0);
		BigDecimal bdPriceGrandTotal_ = new BigDecimal(0);
		BigDecimal bdSellingPriceGrandTotal = new BigDecimal(0);
		int skuRow = headerRow + 1;
		for (int x=0; x<skuDataMaps.size(); x++) {
			Map<String, Object> skuDataMap = skuDataMaps.get(x);
			this.setRowHeight(wSheet, skuRow, 420);
			
			headerColumn = 0;
			BigDecimal bdPrice1 = new BigDecimal(0);
			BigDecimal bdPrice2 = new BigDecimal(0);
			BigDecimal bdPrice = new BigDecimal(0);
			
			// loop for all headers BEFORE the dynamic columns (dates/buyers)
			for (int i=0; i<skuHeaderKeys.size(); i++) {
				String key = skuHeaderKeys.get(i);
				String cellValue = StringUtil.nullToBlank(skuDataMap.get(key));
				WritableCellFormat thisWCF = lblCFData1;
				

				/* price1 */
				if (key.equals(SKUColumnConstantsExcel.PRICE_1)) {
					if (cellValue != null && !cellValue.equals(""))
						bdPrice1 = new BigDecimal(cellValue);
				}				
				/* price2 */
				if (key.equals(SKUColumnConstantsExcel.PRICE_2)) {
					if (cellValue != null && !cellValue.equals(""))
						bdPrice2 = new BigDecimal(cellValue);
				}				
				/* pricewotax or pricewtax*/
				if (key.equals(SKUColumnConstantsExcel.PRICE_WO_TAX) ||
						key.equals(SKUColumnConstantsExcel.PRICE_W_TAX)) {
					if (cellValue != null && !cellValue.equals(""))
						bdPrice = new BigDecimal(cellValue);
				}
								
				if (key.equals(SKUColumnConstantsExcel.PRICE_1) || 
					key.equals(SKUColumnConstantsExcel.PRICE_2) ||
					key.equals(SKUColumnConstantsExcel.PRICE_WO_TAX) || 
					key.equals(SKUColumnConstantsExcel.PRICE_W_TAX) || 
					key.equals(SKUColumnConstantsExcel.PACKAGE_QTY) ||
					key.equals(SKUColumnConstantsExcel.COLUMN_01)) {
					this.addDouble(wSheet, headerColumn, skuRow, cellValue, dblCFData1);
				}
				else if (key.equals(SKUColumnConstantsExcel.PURCHASE_PRICE) || 
					key.equals(SKUColumnConstantsExcel.SELLING_PRICE)) {
					this.addDouble(wSheet, headerColumn, skuRow, cellValue, dblCFData3);
				}
				else {
					if (key.equals(SKUColumnConstantsExcel.SELLING_UOM)) {
						this.addString(wSheet, headerColumn, skuRow, cellValue, dblCFData4);
					}else if (
							key.equals(SKUColumnConstantsExcel.COLUMN_02) ||
							key.equals(SKUColumnConstantsExcel.COLUMN_03) ||
							key.equals(SKUColumnConstantsExcel.COLUMN_04) ||
							key.equals(SKUColumnConstantsExcel.COLUMN_05) ||
							key.equals(SKUColumnConstantsExcel.COLUMN_06) ||
							key.equals(SKUColumnConstantsExcel.COLUMN_07) ||
							key.equals(SKUColumnConstantsExcel.COLUMN_08) ||
							key.equals(SKUColumnConstantsExcel.COLUMN_09) ||
							key.equals(SKUColumnConstantsExcel.COLUMN_10) ||
							key.equals(SKUColumnConstantsExcel.COLUMN_11)
							){
						this.addString(wSheet, headerColumn, skuRow, cellValue, dblCFData5);
					}else {
						
						this.addString(wSheet, headerColumn, skuRow, cellValue, thisWCF);
					}
				}
				
				/* auto fit */
				this.setColumnWidth(headerColumn, cellValue, wSheet);
				
				headerColumn++;
			}
			BigDecimal qtyTotal = new BigDecimal(0);
			// loop for dynamic columns (dates/buyers)
			for (int i=0; i<qtyHeaderKeys.size(); i++) {
				String key = qtyHeaderKeys.get(i);
				String cellValue = StringUtil.nullToBlank(skuDataMap.get(key));
				boolean isCellLocked = ((Boolean) skuDataMap.get(key + "_CellLocked") == null ? false : (Boolean) skuDataMap.get(key + "_CellLocked"));
				WritableCellFormat thisWCF = dblCFIntQty2;
				WritableCellFormat thisWFCLocked = dblCFIntQty1Locked;
				
				//set to blank if cell value is = 0 for quantity columns
				if (cellValue!= null && cellValue.equals("0")) {
					cellValue="";
				}
				
				/* insert empty space if... */
				if (cellValue == null || cellValue.equals("")) {
					if(isCellLocked){
						this.addString(wSheet, headerColumn, skuRow, cellValue, thisWFCLocked);
					} else {
						this.addString(wSheet, headerColumn, skuRow, cellValue, thisWCF);
					}
					headerColumn++;
					continue;
				}
				
				BigDecimal bdValue = new BigDecimal(cellValue);
				int iScale = bdValue.scale();
                if (iScale > 0)
                	thisWCF = dblCFDecQty2;
                
				if(isCellLocked){
					this.addString(wSheet, headerColumn, skuRow, cellValue, thisWFCLocked);
				} else {
					this.addDouble(wSheet, headerColumn, skuRow, cellValue, thisWCF);
				}
				/* auto fit */
				this.setColumnWidth(headerColumn, cellValue, wSheet);
				
				qtyTotal = qtyTotal.add(bdValue);
				headerColumn++;
			}
			BigDecimal bdPrice1Total = this.multiply(bdPrice1, qtyTotal, true);
			BigDecimal bdPrice2Total = this.multiply(bdPrice2, qtyTotal, true);
			BigDecimal bdPriceTotal = this.computeTotalPrice(bdPrice, qtyTotal, priceOpt);
			
			/*
			 * gt.prc : grand total price
			 * t.prc  : total price
			 * t.qty  : total quantity
			 * p.nt   : price no tax
			 * p.t    : price w/ tax
			 * tax    : tax rate
			 * 
			 * 0 = no price computation
			 * 1 = w/ price [t.prc=p.nt * t.qty], [gt.prc=SUM(t.prc)]
			 * 2 = w/ price [t.prc=p.t * t.qty] , [gt.prc=SUM(t.prc)]
			 * 3 = w/ price [t.prc=p.nt * t.qty], [gt.prc=SUM(t.prc) * tax]
			 * 4 = w/ price [t.prc=p.nt * t.qty  * tax], [gt.prc=SUM(t.prc)]
			 */
			bdPrice1GrandTotal = bdPrice1GrandTotal.add(bdPrice1Total);
			bdPrice2GrandTotal = bdPrice2GrandTotal.add(bdPrice2Total);
			bdPriceGrandTotal = bdPriceGrandTotal.add(bdPriceTotal);
			bdPriceGrandTotal_ = bdPriceGrandTotal;
			if (priceOpt == 3) {
				bdPriceGrandTotal_ = this.multiply(bdPriceGrandTotal,
						DownloadExcelUtil.taxRate, true);
			}
			/* end compute total prices */
			
			// loop for all headers AFTER the dynamic columns (dates/buyers)
			for (int i=0; i<ttlHeaderKeys.size(); i++) {
				String key = ttlHeaderKeys.get(i);
				String cellValue = "";
				WritableCellFormat thisWCF = null;
				
				/* total quantity */
				if (key.equals(SKUColumnConstantsExcel.TOTAL_QUANTITY)) {
					cellValue = qtyTotal.toString();
					thisWCF = dblCFIntQty2;
					int iScale = qtyTotal.scale();
                    if (iScale > 0)
                    	thisWCF = dblCFDecQty2;
                    this.addDouble(wSheet, headerColumn, skuRow, cellValue, thisWCF);
				}
				/* price1total */
				if (key.equals(SKUColumnConstantsExcel.PRICE1_TOTAL)) {
					cellValue = bdPrice1Total.toString();
					thisWCF = dblCFIntQty1;
					this.addDouble(wSheet, headerColumn, skuRow, cellValue, thisWCF);
				}
				/* price2total */
				if (key.equals(SKUColumnConstantsExcel.PRICE2_TOTAL)) {
					cellValue = bdPrice2Total.toString();
					thisWCF = dblCFIntQty1;
					this.addDouble(wSheet, headerColumn, skuRow, cellValue, thisWCF);
				}
				/* pricetotal */
				if (key.equals(SKUColumnConstantsExcel.PRICE_TOTAL)) {
					cellValue = bdPriceTotal.toString();
					thisWCF = dblCFIntQty1;
					this.addDouble(wSheet, headerColumn, skuRow, cellValue, thisWCF);
				}
				
				/* totalSellingPrice */
				if (key.equals(SKUColumnConstantsExcel.TOTAL_SELLING_PRICE)) {
					BigDecimal sellingPriceValue = (BigDecimal) (skuDataMap.get(SKUColumnConstantsExcel.SELLING_PRICE) == null ? new BigDecimal(0)
							: skuDataMap.get(SKUColumnConstantsExcel.SELLING_PRICE));
					BigDecimal packageQtyValue = (BigDecimal) (skuDataMap.get(SKUColumnConstantsExcel.PACKAGE_QTY) == null ? new BigDecimal(0)
							: skuDataMap.get(SKUColumnConstantsExcel.PACKAGE_QTY));
					BigDecimal orderQtyValue = new BigDecimal(qtyTotal.toString());
					BigDecimal sellingTotalPrice = this.computeSellingPriceTotal(user, sellingPriceValue, packageQtyValue, orderQtyValue);
					thisWCF = dblCFIntQty2;
					int iScale = qtyTotal.scale();
                    if (iScale > 0)
                    	thisWCF = dblCFDecQty2;
                    if(StringUtils.equals(sellingTotalPrice.toString(), "0")){
                    	this.addString(wSheet, headerColumn, skuRow, sellingTotalPrice.toString(), thisWCF);
                    } else {
                    	this.addDouble(wSheet, headerColumn, skuRow, sellingTotalPrice.toString(), thisWCF);
                    }
                    
                    bdSellingPriceGrandTotal = bdSellingPriceGrandTotal.add(sellingTotalPrice);
				}
				
				/* sku comment */
				if (key.equals(SKUColumnConstantsExcel.SKU_COMMENT)) {
					cellValue = StringUtil.nullToBlank(skuDataMap.get(key));
					this.addString(wSheet, headerColumn, skuRow, cellValue, dblCFData5);
				}
				/** 
				 * TODO: Add 12th-20th new Columns here 
				 * */
				
				if (key.equals(SKUColumnConstantsExcel.COLUMN_12) ||
					key.equals(SKUColumnConstantsExcel.COLUMN_13) ||
					key.equals(SKUColumnConstantsExcel.COLUMN_14) ||
					key.equals(SKUColumnConstantsExcel.COLUMN_15) ||
					key.equals(SKUColumnConstantsExcel.COLUMN_16) ||
					key.equals(SKUColumnConstantsExcel.COLUMN_17) ||
					key.equals(SKUColumnConstantsExcel.COLUMN_18) ||
					key.equals(SKUColumnConstantsExcel.COLUMN_19) ||
					key.equals(SKUColumnConstantsExcel.COLUMN_20) ) {
					cellValue = StringUtil.nullToBlank(skuDataMap.get(key));
					this.addString(wSheet, headerColumn, skuRow, cellValue, dblCFData5);
				}
				
				/* auto fit */
				this.setColumnWidth(headerColumn, cellValue, wSheet);
				
				headerColumn++;
			}
			/* end border */
			this.addString(wSheet, headerColumn, skuRow, "", lblCFData5);
			//
			
			skuRow++;
		}
		//
		/* last row */
		this.setRowHeight(wSheet, skuRow, 420);
		int lastRow = skuRow;
		int totalSkuQtyColumns = skuHeaderKeys.size() + qtyHeaderKeys.size();
		for (int i=0; i<=totalSkuQtyColumns; i++) {
			this.addString(wSheet, i, lastRow, "", lblCFData4);
		}
		/* start grand totals */
		if (priceOpt != 0) {
			for (int i=0; i<ttlHeaderKeys.size(); i++) {
				String key = ttlHeaderKeys.get(i);
				String cellValue = "";
				if (key.equals(SKUColumnConstantsExcel.TOTAL_QUANTITY)) {
					wSheet.mergeCells(totalSkuQtyColumns+i-1, lastRow,
							totalSkuQtyColumns+i, lastRow);
					
					this.addString(wSheet, totalSkuQtyColumns+i-1, lastRow, DownloadExcelUtil.total, lblCFHeader3);
				}
				
				/* price1total */
				if (key.equals(SKUColumnConstantsExcel.PRICE1_TOTAL)) {
					cellValue = bdPrice1GrandTotal.toString();
					this.addDouble(wSheet, totalSkuQtyColumns+i, DownloadExcelUtil.grandtotalRow+2, cellValue, dblCFData2);
				}
				/* price2total */
				if (key.equals(SKUColumnConstantsExcel.PRICE2_TOTAL)) {
					cellValue = bdPrice2GrandTotal.toString();
					this.addDouble(wSheet, totalSkuQtyColumns+i, DownloadExcelUtil.grandtotalRow+2, cellValue, dblCFData2);
				}
				/* pricetotal */
				if (key.equals(SKUColumnConstantsExcel.PRICE_TOTAL)) {
					cellValue = bdPriceGrandTotal.toString();
					this.addDouble(wSheet, totalSkuQtyColumns+i, DownloadExcelUtil.grandtotalRow+2, bdPriceGrandTotal_.toString(), dblCFData2);
				}
				
				/* pricetotal */
				if (key.equals(SKUColumnConstantsExcel.TOTAL_SELLING_PRICE)) {
					cellValue = bdSellingPriceGrandTotal.toString();
					this.addDouble(wSheet, totalSkuQtyColumns+i, DownloadExcelUtil.grandtotalRow+2, bdSellingPriceGrandTotal.toString(), dblCFData2);
				}
				/* sku comments */
				/** 
				 * TODO: Add 6th-20th new Columns here 
				 * */

				if (key.equals(SKUColumnConstantsExcel.COLUMN_20)){
					this.addDouble(wSheet, totalSkuQtyColumns+i, lastRow, "", lblCFData4);
				}else if(key.equals(SKUColumnConstantsExcel.COLUMN_06) ||
						key.equals(SKUColumnConstantsExcel.COLUMN_07) ||
						key.equals(SKUColumnConstantsExcel.COLUMN_08) ||
						key.equals(SKUColumnConstantsExcel.COLUMN_09) ||
						key.equals(SKUColumnConstantsExcel.COLUMN_10) ||
						key.equals(SKUColumnConstantsExcel.COLUMN_11) ||
						key.equals(SKUColumnConstantsExcel.COLUMN_12) ||
						key.equals(SKUColumnConstantsExcel.COLUMN_13) ||
						key.equals(SKUColumnConstantsExcel.COLUMN_14) ||
						key.equals(SKUColumnConstantsExcel.COLUMN_15) ||
						key.equals(SKUColumnConstantsExcel.COLUMN_16) ||
						key.equals(SKUColumnConstantsExcel.COLUMN_17) ||
						key.equals(SKUColumnConstantsExcel.COLUMN_18) ||
						key.equals(SKUColumnConstantsExcel.COLUMN_19) ||
						key.equals(SKUColumnConstantsExcel.SKU_COMMENT) ) {
					//this.addString(wSheet, headerColumn, skuRow, "", lblCFData4);
					this.addDouble(wSheet, totalSkuQtyColumns+i, lastRow, cellValue, lblCFData4);
				}else{
					this.addDouble(wSheet, totalSkuQtyColumns+i, lastRow, cellValue, dblCFIntQty3);
				}
				//this.addString(wSheet, totalSkuQtyColumns+i, lastRow, "", lblCFData4);
			}
		
			this.addString(wSheet, totalSkuQtyColumns+1, DownloadExcelUtil.grandtotalRow, DownloadExcelUtil.allTotals, lblCFData6);
		}
		/* end grand totals */
	}
	
	private void applyPrintSettings(WritableSheet wSheet, DownloadExcelSheet xlSheet) {
		SheetSettings wsSettings = wSheet.getSettings();
		
		if(xlSheet.getQtyHeaderKeys().size() > 10){
			wsSettings.setPaperSize(PaperSize.A3);
        }else{
        	wsSettings.setPaperSize(PaperSize.A4);
        }
		wsSettings.setFitToPages(true);
		wsSettings.setFitWidth(1);
		wsSettings.setShowGridLines(false);
		wsSettings.setVerticalFreeze(DownloadExcelUtil.headerRow + 1);
		wsSettings.setPrintTitlesRow(0, DownloadExcelUtil.headerRow);
		wsSettings.setTopMargin(0.47244094488189); //1.2 cm
		wsSettings.setBottomMargin(0.47244094488189);
		wsSettings.setLeftMargin(0.393700787401575); //1 cm
		wsSettings.setRightMargin(0.393700787401575);
		wsSettings.setHeaderMargin(0.196850393700787); //0.5 cm
        wsSettings.setFooterMargin(0.196850393700787);
        
        /* footer settings */
        HeaderFooter footer = wsSettings.getFooter();
        footer.getRight().appendPageNumber();
        footer.getRight().append("/");
        footer.getRight().appendTotalPages();    	  

        wSheet.setPageSetup(PageOrientation.LANDSCAPE);
        /**/
	}
	
	/**
	 * Count special ascii-coded characters (eg. Japanese, Kanji Characters)
	 * 
	 * @param str
	 * @param escapeAscii
	 * @return
	 */
	private int sizeUnicodeString(String str, boolean escapeAscii) {
		int count=0;
		int japaneseChar = 2;
		for(int i=0; i<str.length(); i++) {
			char ch = str.charAt(i);

			if (!escapeAscii && ((ch >= 0x0020) && (ch <= 0x007e)))
				count++;
			else
				count+=japaneseChar;
		}
		return count;
	}
	
	private BigDecimal computeTotalPrice(BigDecimal price, BigDecimal qty, int priceOpt) {
		BigDecimal priceTotal = new BigDecimal(0);
		BigDecimal priceWTax = new BigDecimal(0);
		if (priceOpt == 1 || priceOpt == 3) {
			priceTotal = this.multiply(price, qty, true);
		}
		else if (priceOpt == 2) {
			priceTotal = this.multiply(price, qty, true);
		}
		else if (priceOpt == 4) {
			priceWTax = this.multiply(price, DownloadExcelUtil.taxRate, false);
			priceTotal = this.multiply(priceWTax, qty, true);
		}
		
		return priceTotal;
	}
	
	private BigDecimal computeSellingPriceTotal(User user, BigDecimal sellingPrice, BigDecimal packageQty, BigDecimal orderQty){
		ProfitPreference userProfitPreference = userPreferenceDao.getProfitPreference(user.getUserId());
		if(userProfitPreference != null){
			String withPackageQuantity = userProfitPreference.getWithPackageQuantity();
			//Option to compute 
			//Selling Price Total : 1 = With Package Quantity     
			//						0 = Without Package Quantity
			if(StringUtils.equals("1", withPackageQuantity)){
				return sellingPrice.multiply(packageQty).multiply(orderQty);
			} else {
				return sellingPrice.multiply(orderQty);
			}
		} else {
			//Default : With Package Quantity
			return sellingPrice.multiply(packageQty).multiply(orderQty);
		}
		
	}
	
	private void setColumnWidth(int colIndex, String cellValue, WritableSheet ws) {
		int size = this.sizeUnicodeString(cellValue, false);
		int currSize = ((CellView)ws.getColumnView(colIndex)).getSize();
		if(size >= currSize/256){
			ws.setColumnView(colIndex, size+2);
		}
	}
	
	private void setRowHeight(WritableSheet ws, int rowIndex, int height)
			throws Exception {
		ws.setRowView(rowIndex, height);
	}
	
	private String addLineFeed(String origString) {
		if (origString == null || origString.trim().equals(""))
			return "";
        String tmpString = new String(origString);
        StringBuffer sBuff = new StringBuffer();
        
        sBuff.append(tmpString.substring(0,tmpString.length()-2));
        sBuff.append("\n");
        sBuff.append(tmpString.substring(tmpString.length()-2));
        
        return sBuff.toString();
    }
	
	private void addString(WritableSheet ws, int col, int row, String value, WritableCellFormat wcf)
			throws Exception {
		ws.addCell(new Label(col, row, value, wcf));
	}
	
	private void addDouble(WritableSheet ws, int col, int row, String value,
			WritableCellFormat wcf)
			throws Exception {
		if (value == null || value.equals("") || Double.parseDouble(value) == 0d)
			ws.addCell(new Label(col, row, "", wcf));
		else {
			ws.addCell(new Number(col, row, Double.parseDouble(value), wcf));
		}
	}

	private BigDecimal multiply(BigDecimal m1, BigDecimal m2, boolean roundOf) {
		BigDecimal product = new BigDecimal(0);
		product = m1.multiply(m2);
		if (roundOf)
			product = product.setScale(0, BigDecimal.ROUND_HALF_UP);
		return product;
	}
	 
	/* cell formats */
	private WritableCellFormat getLblCFHeader1() throws Exception {
		WritableFont wf = new WritableFont(WritableFont.createFont(DownloadExcelUtil.fontFace),
				DownloadExcelUtil.headerFontSize, WritableFont.NO_BOLD);
		WritableCellFormat cf = new WritableCellFormat(wf);
		cf.setAlignment(Alignment.CENTRE);
        cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		cf.setBackground(Colour.IVORY);
		cf.setBorder(Border.ALL, BorderLineStyle.HAIR);
		cf.setBorder(Border.TOP, BorderLineStyle.THIN);
		cf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
        cf.setWrap(true);
		
		return cf;
	}
	
	private WritableCellFormat getLblCFHeader2() throws Exception {
		WritableCellFormat cf = this.getLblCFHeader1();
		cf.setBorder(Border.ALL, BorderLineStyle.THIN);
		
		return cf;
	}
	
	private WritableCellFormat getLblCFHeader3() throws Exception {
		WritableCellFormat cf = this.getLblCFHeader2();
		cf.setVerticalAlignment(VerticalAlignment.BOTTOM);
		
		return cf;
	}
	
	private WritableCellFormat getLblCFData1() throws Exception {
		WritableFont wf = new WritableFont(WritableFont.createFont(DownloadExcelUtil.fontFace),
				DownloadExcelUtil.dataFontSize, WritableFont.NO_BOLD);
		WritableCellFormat cf = new WritableCellFormat(wf);
		cf.setAlignment(Alignment.LEFT);
        cf.setBorder(Border.ALL, BorderLineStyle.HAIR);
		cf.setWrap(true);
		
		return cf;
	}
	
	private WritableCellFormat getLblCFData2() throws Exception {
		WritableCellFormat cf = this.getLblCFData1();
		cf.setAlignment(Alignment.RIGHT);
        cf.setBorder(Border.ALL, BorderLineStyle.THIN);
		
		return cf;
	}
	
	private WritableCellFormat getLblCFData3() throws Exception {
		WritableCellFormat cf = this.getLblCFData2();
		cf.setAlignment(Alignment.LEFT);
        
		return cf;
	}
	
	private WritableCellFormat getLblCFData4() throws Exception {
		WritableCellFormat cf = this.getLblCFData1();
		cf.setBorder(Border.ALL, BorderLineStyle.NONE);
		cf.setBorder(Border.TOP, BorderLineStyle.THIN);
        
		return cf;
	}
	
	private WritableCellFormat getLblCFData5() throws Exception {
		WritableCellFormat cf = this.getLblCFData1();
		cf.setBorder(Border.ALL, BorderLineStyle.NONE);
		cf.setBorder(Border.LEFT, BorderLineStyle.THIN);
        
		return cf;
	}
	
	private WritableCellFormat getLblCFData6() throws Exception {
		WritableCellFormat cf = this.getLblCFData3();
		cf.setBorder(Border.ALL, BorderLineStyle.NONE);
		
		return cf;
	}

	//WCF for 20 new columns data

//	private WritableCellFormat getLblCFData7() throws Exception {
//		WritableCellFormat cf = this.getLblCFData4();
//		cf.setBorder(Border.ALL, BorderLineStyle.NONE);
//		cf.setBorder(Border.TOP, BorderLineStyle.THIN);
//        
//		return cf;
//	}
	
	
	private WritableCellFormat getDblCFData1() throws Exception {
		NumberFormat nFormatInt = new NumberFormat("#,##0");
		WritableFont wf = new WritableFont(WritableFont.createFont(DownloadExcelUtil.fontFace),
				DownloadExcelUtil.dataFontSize, WritableFont.NO_BOLD);
		WritableCellFormat cf = new WritableCellFormat(wf, nFormatInt);
		cf.setAlignment(Alignment.RIGHT);
        cf.setBorder(Border.ALL, BorderLineStyle.HAIR);
		cf.setWrap(true);
		
		return cf;
	}
	
	private WritableCellFormat getDblCFData2() throws Exception {
		WritableCellFormat cf = this.getDblCFData1();
        cf.setBorder(Border.ALL, BorderLineStyle.THIN);
        
		return cf;
	}
	
	//WCF for buyer purchase and selling price data
	private WritableCellFormat getDblCFData3() throws Exception {
		NumberFormat nFormatInt = new NumberFormat("#,##0");
		WritableFont wf = new WritableFont(WritableFont.createFont(DownloadExcelUtil.fontFace),
				DownloadExcelUtil.buyerDataFontSize, WritableFont.NO_BOLD);
		WritableCellFormat cf = new WritableCellFormat(wf, nFormatInt);
		cf.setAlignment(Alignment.RIGHT);
        cf.setBorder(Border.ALL, BorderLineStyle.HAIR);
		cf.setWrap(true);
		
		return cf;
	}
	
	//WCF for buyer Selling UOM data
	private WritableCellFormat getDblCFData4() throws Exception {
		WritableFont wf = new WritableFont(WritableFont.createFont(DownloadExcelUtil.fontFace),
				DownloadExcelUtil.buyerDataFontSize, WritableFont.NO_BOLD);
		WritableCellFormat cf = new WritableCellFormat(wf);
		cf.setAlignment(Alignment.RIGHT);
        cf.setBorder(Border.ALL, BorderLineStyle.HAIR);
		cf.setWrap(true);
		
		return cf;
	}

	//WCF for buyer sku comment data
	private WritableCellFormat getDblCFData5() throws Exception {
		WritableFont wf = new WritableFont(WritableFont.createFont(DownloadExcelUtil.fontFace),
				DownloadExcelUtil.skuCommentFontSize, WritableFont.NO_BOLD);
		WritableCellFormat cf = new WritableCellFormat(wf);
		cf.setAlignment(Alignment.LEFT);
        cf.setBorder(Border.ALL, BorderLineStyle.HAIR);
		cf.setWrap(true);
		
		return cf;
	}
	
	private WritableCellFormat getDblCFIntQty1() throws Exception {
		NumberFormat nFormatInt = new NumberFormat("#,##0");
		WritableFont wf = new WritableFont(WritableFont.createFont(DownloadExcelUtil.fontFace),
				DownloadExcelUtil.qtyFontSize, WritableFont.NO_BOLD);
		WritableCellFormat cf = new WritableCellFormat(wf, nFormatInt);
		cf.setAlignment(Alignment.RIGHT);
        cf.setBorder(Border.ALL, BorderLineStyle.HAIR);
		cf.setWrap(true);
		
		return cf;
	}
	
	private WritableCellFormat getDblCFIntQty1Locked() throws Exception {
		WritableCellFormat cf = this.getDblCFIntQty1();
        cf.setBorder(Border.LEFT, BorderLineStyle.THIN);
        cf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
		cf.setBackground(Colour.GREY_40_PERCENT);
		
		return cf;
	}
	
	private WritableCellFormat getDblCFIntQty2() throws Exception {
		WritableCellFormat cf = this.getDblCFIntQty1();
        cf.setBorder(Border.LEFT, BorderLineStyle.THIN);
        cf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
		
		return cf;
	}
	
	private WritableCellFormat getDblCFIntQty3() throws Exception {
		WritableCellFormat cf = this.getDblCFIntQty2();
        cf.setBorder(Border.TOP, BorderLineStyle.THIN);
        cf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
		
		return cf;
	}
	
	private WritableCellFormat getDblCFDecQty1() throws Exception {
		NumberFormat nFormatDec = new NumberFormat("#,##0.0##");
		WritableFont wf = new WritableFont(WritableFont.createFont(DownloadExcelUtil.fontFace),
				DownloadExcelUtil.qtyFontSize, WritableFont.NO_BOLD);
		WritableCellFormat cf = new WritableCellFormat(wf, nFormatDec);
		cf.setAlignment(Alignment.RIGHT);
        cf.setBorder(Border.ALL, BorderLineStyle.HAIR);
		cf.setWrap(true);
		
		return cf;
	}
	
	private WritableCellFormat getDblCFDecQty2() throws Exception {
		WritableCellFormat cf = this.getDblCFDecQty1();
        cf.setBorder(Border.LEFT, BorderLineStyle.THIN);
        cf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
		
		return cf;
	}
	
//	private WritableCellFormat getDblCFDecQty3() throws Exception {
//		WritableCellFormat cf = this.getDblCFDecQty2();
//        cf.setBorder(Border.TOP, BorderLineStyle.THIN);
//        cf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
//		
//		return cf;
//	}
	//
	
}
