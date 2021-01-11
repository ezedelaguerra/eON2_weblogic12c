package com.freshremix.util;

import java.math.BigDecimal;

public final class TaxUtil {

	private  static   BigDecimal TAX_RATE;
	public static final String ROUND = "ROUND";
	public static final String ROUND_UP = "ROUND_UP";
	public static final String ROUND_DOWN = "ROUND_DOWN";

	public static BigDecimal getTAX_RATE() {
		return TAX_RATE;
	}


    
    public static void setTaxRate(String taxRate) {
    	System.out.println(taxRate);
    	TaxUtil.TAX_RATE = new BigDecimal(taxRate);
    }
 

    public static BigDecimal getPriceWithTax(BigDecimal priceWoTax, String roundOpt){
    	return getPriceWithTax(priceWoTax, roundOpt, 0);
    }
    
    public static BigDecimal getPriceWithTax(BigDecimal priceWoTax, String roundOpt, int scale){
    	BigDecimal priceWTax = null;
    	if(priceWoTax==null){
    		return null;
    	}
    	
    	priceWTax = priceWoTax.multiply(TAX_RATE);
    	if(roundOpt.equalsIgnoreCase(ROUND)){
    		priceWTax = priceWTax.setScale(scale, BigDecimal.ROUND_HALF_UP);
    	}else if(roundOpt.equalsIgnoreCase(ROUND_UP)){
    		priceWTax = priceWTax.setScale(scale, BigDecimal.ROUND_UP);
    	}else if(roundOpt.equalsIgnoreCase(ROUND_DOWN)){
    		priceWTax = priceWTax.setScale(scale, BigDecimal.ROUND_DOWN);
    	}
    	return priceWTax;
    }


	
}
