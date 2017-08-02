package com.altcoin.trading.vo;

import java.util.ArrayList;

import lombok.Data;

@Data
public class CoinoneVo {

    private String currency; // 媛��긽�솕�룓 �슂�냼
    private String timestamp; // �쁽�옱 �떆媛�
    private ArrayList<CoinoneVo> bid; // 援щℓ�샇媛�
    private ArrayList<CoinoneVo> ask; // �뙋留ㅽ샇媛�
    private ArrayList<CoinoneVo> completeOrders; // 泥닿껐�궡�뿭
    private String result;
    
    private String errorCode; // �긽�깭肄붾뱶
    private String high; // 理쒓퀬媛�
    private String low; // 理쒖�媛�
    private String last; // 留덉�留� 泥닿껐媛�
    private String first; // 泥� 泥닿껐媛�
    private String volume; // 嫄곕옒�웾
    private String price; // 媛�寃�
    private String qty; // 嫄곕옒�웾
    private String balance;
    private String label;
    private String avail;
    private ArrayList<CoinoneVo> normalWallets;
    private CoinoneVo btc;
    private CoinoneVo krw;
    private CoinoneVo xrp;

    private String coin;
    
	public CoinoneVo getXrp() {
		return xrp;
	}

	public void setXrp(CoinoneVo xrp) {
		this.xrp = xrp;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAvail() {
		return avail;
	}

	public void setAvail(String avail) {
		this.avail = avail;
	}

	public ArrayList<CoinoneVo> getNormalWallets() {
		return normalWallets;
	}

	public void setNormalWallets(ArrayList<CoinoneVo> normalWallets) {
		this.normalWallets = normalWallets;
	}

	public CoinoneVo getBtc() {
		return btc;
	}

	public void setBtc(CoinoneVo btc) {
		this.btc = btc;
	}

	public CoinoneVo getKrw() {
		return krw;
	}

	public void setKrw(CoinoneVo krw) {
		this.krw = krw;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public ArrayList<CoinoneVo> getBid() {
		return bid;
	}

	public void setBid(ArrayList<CoinoneVo> bid) {
		this.bid = bid;
	}

	public ArrayList<CoinoneVo> getAsk() {
		return ask;
	}

	public void setAsk(ArrayList<CoinoneVo> ask) {
		this.ask = ask;
	}

	public ArrayList<CoinoneVo> getCompleteOrders() {
		return completeOrders;
	}

	public void setCompleteOrders(ArrayList<CoinoneVo> completeOrders) {
		this.completeOrders = completeOrders;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getCoin() {
		return coin;
	}

	public void setCoin(String coin) {
		this.coin = coin;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
