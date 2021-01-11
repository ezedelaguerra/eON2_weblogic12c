function calculatePriceWithTax(price, roundOpt){
	if(price==null){
		return null;
	}
	var result =0;
	if(roundOpt=='ROUND'){
		result = Math.round(price * TAX_RATE);
	}else if (roundOpt=='ROUND_UP'){
		result = Math.ceil(price * TAX_RATE);
	}else if (roundOpt=='ROUND_UP'){
		result = Math.floor(price * TAX_RATE);
	}
	
	return result;
}