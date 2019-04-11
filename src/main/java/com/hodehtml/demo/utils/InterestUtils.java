package com.hodehtml.demo.utils;

public class InterestUtils {
	
	public static InterestUtils getInstance(){
		return new InterestUtils();
	}
	
	public double caculate(double loanMoney,int loanDays,double interestRate){
		double result = (loanMoney*loanDays*interestRate)/1000.0;
		return Double.valueOf(CommonUtils.round(result, 2));
	}

}
