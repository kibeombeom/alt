package com.altcoin.scheduler.premium.vo;

import java.util.Date;

public class PremiumVo {
	
	private int premiumNo;
	private int fromExchangeCd;
	private int toExchangeCd;
	private float marginRate;
	private int currencyCd;
	private Date createTime;
	
	
	public int getPremiumNo() {
		return premiumNo;
	}
	public void setPremiumNo(int premiumNo) {
		this.premiumNo = premiumNo;
	}
	public int getFromExchangeCd() {
		return fromExchangeCd;
	}
	public void setFromExchangeCd(int fromExchangeCd) {
		this.fromExchangeCd = fromExchangeCd;
	}
	public int getToExchangeCd() {
		return toExchangeCd;
	}
	public void setToExchangeCd(int toExchangeCd) {
		this.toExchangeCd = toExchangeCd;
	}
	public float getMarginRate() {
		return marginRate;
	}
	public void setMarginRate(float marginRate) {
		this.marginRate = marginRate;
	}
	public int getCurrencyCd() {
		return currencyCd;
	}
	public void setCurrencyCd(int currencyCd) {
		this.currencyCd = currencyCd;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
