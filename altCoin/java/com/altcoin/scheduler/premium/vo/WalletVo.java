package com.altcoin.scheduler.premium.vo;

public class WalletVo {
	
	private int walletNo;
	private int currencyCd;
	private int exchangeCd;
	private String tag;
	private String walletAddr;
	private float qty;
	private int isUse;
	
	public int getWalletNo() {
		return walletNo;
	}
	public void setWalletNo(int walletNo) {
		this.walletNo = walletNo;
	}
	public int getCurrencyCd() {
		return currencyCd;
	}
	public void setCurrencyCd(int currencyCd) {
		this.currencyCd = currencyCd;
	}
	public int getExchangeCd() {
		return exchangeCd;
	}
	public void setExchangeCd(int exchangeCd) {
		this.exchangeCd = exchangeCd;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getWalletAddr() {
		return walletAddr;
	}
	public void setWalletAddr(String walletAddr) {
		this.walletAddr = walletAddr;
	}
	public float getQty() {
		return qty;
	}
	public void setQty(float qty) {
		this.qty = qty;
	}
	public int getIsUse() {
		return isUse;
	}
	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}
	
	
	
}
