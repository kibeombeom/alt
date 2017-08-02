package com.altcoin.base.vo;

import java.util.Map;

import lombok.Data;

@Data
public class BaseVo {

    private Map<String, Object> coinoneApikeys;
    private Map<String, Object> poloniexApikeys;
    private String coinoneSecert;
    private String poloniexSecert;
    private String coin;
    
	public Map<String, Object> getCoinoneApikeys() {
		return coinoneApikeys;
	}
	public void setCoinoneApikeys(Map<String, Object> coinoneApikeys) {
		this.coinoneApikeys = coinoneApikeys;
	}
	public Map<String, Object> getPoloniexApikeys() {
		return poloniexApikeys;
	}
	public void setPoloniexApikeys(Map<String, Object> poloniexApikeys) {
		this.poloniexApikeys = poloniexApikeys;
	}
	public String getCoinoneSecert() {
		return coinoneSecert;
	}
	public void setCoinoneSecert(String coinoneSecert) {
		this.coinoneSecert = coinoneSecert;
	}
	public String getPoloniexSecert() {
		return poloniexSecert;
	}
	public void setPoloniexSecert(String poloniexSecert) {
		this.poloniexSecert = poloniexSecert;
	}
	public String getCoin() {
		return coin;
	}
	public void setCoin(String coin) {
		this.coin = coin;
	}

}
