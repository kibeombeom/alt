package com.altcoin.scheduler.premium.vo;

public class TokenVo {
	
	private int tokenNo;
	private int exchangeCd;
	private String accessToken;
	private String seretKey; 
	private float markerFee;
	private float takerFee;
	private int keyCurrencyCd;
	private int CurrencyCd;
	private int isUse;
	
	
	public int getKeyCurrencyCd() {
		return keyCurrencyCd;
	}
	public void setKeyCurrencyCd(int keyCurrencyCd) {
		this.keyCurrencyCd = keyCurrencyCd;
	}
	public int getTokenNo() {
		return tokenNo;
	}
	public void setTokenNo(int tokenNo) {
		this.tokenNo = tokenNo;
	}
	public int getExchangeCd() {
		return exchangeCd;
	}
	public void setExchangeCd(int exchangeCd) {
		this.exchangeCd = exchangeCd;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getSeretKey() {
		return seretKey;
	}
	public void setSeretKey(String seretKey) {
		this.seretKey = seretKey;
	}
	public float getMarkerFee() {
		return markerFee;
	}
	public void setMarkerFee(float markerFee) {
		this.markerFee = markerFee;
	}
	public float getTakerFee() {
		return takerFee;
	}
	public void setTakerFee(float takerFee) {
		this.takerFee = takerFee;
	}
	public int getCurrencyCd() {
		return CurrencyCd;
	}
	public void setCurrencyCd(int currencyCd) {
		CurrencyCd = currencyCd;
	}
	public int getIsUse() {
		return isUse;
	}
	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}
	
	
}
