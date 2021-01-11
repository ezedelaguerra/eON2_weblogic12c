var KG_ORDER_UNIT = "KG";
var ALLOW_DECIMAL_KEY = "*D";

function isDecimalAllowed(uom){
	if(KG_ORDER_UNIT.equalsIgnoreCase(uom) ||  uom.toUpperCase().trim().endsWith(ALLOW_DECIMAL_KEY) ){
		return true;
	}
		
	return false;
}

function validateQuantityScale(uom, value) {
  	if (isDecimalAllowed(uom)) {
  	  if(String(value).indexOf(".") > 0) {
  		  if (String(value).indexOf(".") < String(value).length - 4) {
  			  return false;
  		  }  
  	  }
  	  return true;
  	} else {
  	  if(String(value).indexOf(".") > -1) {
  	  	  return false;
  	  }
  	  return true;
  	}
}	

function validateOrderUnitUI(mygrid, sellerId, skuId, orderUnit, quantityKeyValue, skuMaxLimit) {
	  if (!isDecimalAllowed(orderUnit)) {
		  if (hasDecimal(quantityKeyValue)) {
			  return mygrid.getMsg('UNIT_ORDER_ERROR1');
		  } else if (String(skuMaxLimit).indexOf(".") > -1) {
			  return mygrid.getMsg('UNIT_ORDER_ERROR2');
		  }
	  }
	  return true;
}

function hasDecimal(quantityKeyValue) {
	  for (var i=0;i<quantityKeyValue.length;i++) {
		  var obj = quantityKeyValue[i].split(",");
		  if (String(obj[1]).indexOf(".") > 0) {
			  return true;
		  }
	  }
	  return false;
}

function validateOrderUnitBE(mygrid, sellerId, skuId, orderUnit, quantityKeyValue, url) {
	  var res = "error BE";
	  url = url + "?sellerId=" + sellerId;
	  url = url + "&skuId=" + skuId;
	  url = url + "&orderUnit=" + orderUnit;
	  mygrid.showWaiting();
	  $.ajax({
        url: url,
        data: "",
        dataType: 'json',
        async : false,
        success: function(ret){
		  	if (ret.isValid == "false") {
			  	res = mygrid.getMsg('UNIT_ORDER_ERROR1');
		  	} else {
		  		res = true;
		  	}
        },
        complete:function(XMLHttpRequest, textStatus){
            mygrid.hideWaiting();
        }
    });
    return res;
}

function getOrderUnitById(obj,id) {
	
	var orderUnitMap = obj["unitorderRenderer"];
	var orderUnit;
	for(var i=0;i<orderUnitMap.length;i++) {
		if(id == orderUnitMap[i]["id"]) {
			orderUnit = orderUnitMap[i]["caption"];
		}
	}
	return orderUnit;
}

function toSigmaBoolean(param) {
	return param == "1" ? false : true;
}

function toProfitVisibility(param) {
	return param == "1" ? true : false;
}

// Auto Calculation of price w/ and w/o tax for Seller/SA only and with profit info for Buyer/BA only
function autoCalculatePrice(value, oldValue, record, row, col, grid) {

	/*
	1) Get the orginal price (sku level)
	2) Subtract the orig from the GT
	3) Add the new computed price (sku level) to GT
	4) Assign result in 3) to priceInfo column
	*/
	var curProfitInfoT = grid.getTotals();
	var curProfitInfoGT = grid.getGrandTotals();
	var oldProfitInfo = _.unserialize(record.profitInfo);
	var newProfitInfo = new Object();
	var totalQty = grid.getTotalQuantity(record);

	if (record.profitInfo) {
		curProfitInfoT.priceWithoutTax = 
			Number(curProfitInfoT.priceWithoutTax) - Number(oldProfitInfo.priceWithoutTax);
		curProfitInfoT.priceWithTax = 
			Number(curProfitInfoT.priceWithTax) - Number(oldProfitInfo.priceWithTax);
		curProfitInfoGT.priceWithoutTax = 
			Number(curProfitInfoGT.priceWithoutTax) - Number(oldProfitInfo.priceWithoutTax);
		curProfitInfoGT.priceWithTax = 
			Number(curProfitInfoGT.priceWithTax) - Number(oldProfitInfo.priceWithTax);
		
		//only Buyer/BA has priceTaxOpt
		if(oldProfitInfo.priceTaxOpt != null){
			curProfitInfoT.sellingPrice = 
				Number(curProfitInfoT.sellingPrice) - Number(oldProfitInfo.sellingPrice);
			curProfitInfoT.profit = 
				Number(curProfitInfoT.profit) - Number(oldProfitInfo.profit);		
			curProfitInfoGT.sellingPrice = 
				Number(curProfitInfoGT.sellingPrice) - Number(oldProfitInfo.sellingPrice);
			curProfitInfoGT.profit = 
				Number(curProfitInfoGT.profit) - Number(oldProfitInfo.profit);		
			
		}
	}

	newProfitInfo.priceWithoutTax = Number(record.pricewotax) * Number(totalQty);
	newProfitInfo.priceWithTax = Number(record.pricewtax) * Number(totalQty);

	if(oldProfitInfo.priceTaxOpt != null)
		newProfitInfo =
			calculateProfitPercentage(record, totalQty, oldProfitInfo.priceTaxOpt, grid, newProfitInfo);

	curProfitInfoT.priceWithoutTax = 
		Number(curProfitInfoT.priceWithoutTax) + Number(newProfitInfo.priceWithoutTax);
	curProfitInfoT.priceWithTax = 
		Number(curProfitInfoT.priceWithTax) + Number(newProfitInfo.priceWithTax);
	curProfitInfoGT.priceWithoutTax = 
		Number(curProfitInfoGT.priceWithoutTax) + Number(newProfitInfo.priceWithoutTax);
	curProfitInfoGT.priceWithTax = 
		Number(curProfitInfoGT.priceWithTax) + Number(newProfitInfo.priceWithTax);

	if(oldProfitInfo.priceTaxOpt != null){
		curProfitInfoT.sellingPrice = 
			Number(curProfitInfoT.sellingPrice) + Number(newProfitInfo.sellingPrice);
		curProfitInfoT.profit = 
			Number(curProfitInfoT.profit) + Number(newProfitInfo.profit);		
		curProfitInfoGT.sellingPrice = 
			Number(curProfitInfoGT.sellingPrice) + Number(newProfitInfo.sellingPrice);
		curProfitInfoGT.profit = 
			Number(curProfitInfoGT.profit) + Number(newProfitInfo.profit);	

		if(curProfitInfoT.sellingPrice > 0)
			curProfitInfoT.profitPercentage = 
				round(round(curProfitInfoT.profit/curProfitInfoT.sellingPrice,4)*100,1);

		if(curProfitInfoGT.sellingPrice > 0)
			curProfitInfoGT.profitPercentage = 
				round(round(curProfitInfoGT.profit/curProfitInfoGT.sellingPrice,4)*100,1);
	}

	grid.setTotals(curProfitInfoT);	
	grid.setGrandTotals(curProfitInfoGT);

	// update Total Qty
	grid.setCellValue("rowqty", record, totalQty);
	// update hidden price info (sku level)
	grid.setCellValue("profitInfo", record, _.serialize(newProfitInfo));
}

//Compute Profit Percentage
function calculateProfitPercentage(record, totalQty, priceTaxOpt, grid, newProfitInfo) {
	
	/*
	1) Profit = (Selling Price - Price / Packing Quantity) * Packing Quantity * Row Quantity)
	2) Selling Price (per SKU)= (selling price * packing quantity * Row Quantity)
	3) Profit Percentage = Profit/Selling Price(per SKU)*100
	*/
	
	var price = 0;
	var sellPrice = 0;
	var pckQty = 0;
	var profit = 0;
	var sellingPrice = 0;
	var profitPercentage = 0;
	
	if (record.profitInfo) {
		if(priceTaxOpt == "1")
			price = Number(record.pricewtax);
		else
			price = Number(record.pricewotax);
		
		sellPrice = Number(record.B_sellingPrice);
		pckQty = Number(record.packageqty);
		profit = round(round((sellPrice - round((price/pckQty),1))*pckQty,1)*totalQty,1);
		sellingPrice = round(round(sellPrice*pckQty,1)*totalQty,1);
		
		if(sellingPrice > 0){
			profitPercentage = round(round(profit/sellingPrice,4)*100,1);
			grid.setCellValue("B_profitPercentage", record, profitPercentage);
		}

		newProfitInfo.sellingPrice = sellingPrice;
		newProfitInfo.profit = profit;
		newProfitInfo.profitPercentage = profitPercentage;
	}

	return newProfitInfo;
}

function round(num, dec) {
	var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
	return result;
}