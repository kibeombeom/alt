package com.altcoin.scheduler.premium.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altcoin.outside.account.balance.vo.BalanceVo;
import com.altcoin.scheduler.premium.dao.PremiumMapper;
import com.altcoin.scheduler.premium.service.PremiumService;
import com.altcoin.scheduler.premium.vo.CodeVo;
import com.altcoin.scheduler.premium.vo.ExchangeRateVo;
import com.altcoin.scheduler.premium.vo.PremiumVo;
import com.altcoin.scheduler.premium.vo.TokenVo;
import com.altcoin.scheduler.premium.vo.UserVo;
import com.altcoin.scheduler.premium.vo.WalletVo;

import net.minidev.json.JSONObject;


@Service("PremiumService")
public class PremiumServiceImpl implements PremiumService {

	@Autowired
	private PremiumMapper premiumMapper;


	@Override
	public void setExchangeRate() throws Exception {
		ExchangeRateVo exchangeRateVo = new ExchangeRateVo(); 
		
		HttpPost http = new HttpPost("http://finance.daum.net/exchange/exchangeMain.daum"); // 가져올 HTTP 주소 세팅
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = httpClient.execute(http);
		HttpEntity entity = response.getEntity();
		ContentType contentType = ContentType.getOrDefault(entity);
		Charset charset = contentType.getCharset();
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), charset));
		StringBuffer sb = new StringBuffer();
		String line = "";
		
		while((line=br.readLine()) != null){
			sb.append(line+"\n");
		}

		//System.out.println(sb.toString());
		String start= "var exchangeData = ["; 
		String end = "Array.prototype.sortMulti";
		int startInt = sb.toString().indexOf(start)+20; // +20 이유는 startStr 의 길이 만큼 빼기 위함
		int endInt = sb.toString().indexOf(end); //

		// 환율 데이터 정제 
		String exchange_all = sb.toString().substring(startInt, endInt).replaceAll("\"",""); // " 제거
		exchange_all = exchange_all.replaceAll("(\r\n|\r|\n|\n\r)", " "); // 개행문자 제거
		exchange_all = exchange_all.replaceAll("	", ""); // 탭 제거
		exchange_all = exchange_all.replaceAll(" ", ""); // 공백 제거

		String[] exchange_sentence = exchange_all.split("],");
		for(String exchange_line : exchange_sentence) {
			String[] exchange_word = exchange_line.split(",");
			String country = exchange_word[0].replace("[",""); // 국가 명
			String currency = exchange_word[1]; // 통화 명 
			String value = exchange_word[3]; // 환율
			
			// System.out.println(country+", "+currency+", "+value);
			CodeVo codeVo = premiumMapper.selectCodeValue(currency); // code table select  
			
			exchangeRateVo.setCountryCd(Integer.parseInt(codeVo.getCdKey()));
			exchangeRateVo.setCurrencyCd(Integer.parseInt(codeVo.getCdKey()));
			exchangeRateVo.setExchangeValue(Float.parseFloat(value));
						
			premiumMapper.insertExchangeRate(exchangeRateVo); // exchange table insert
			
		}
	}


	@Override
	public void calPremium() {
		
    	UserVo userVo = new UserVo(); // 유저 vo
    	ExchangeRateVo exchangeVo = new ExchangeRateVo(); // 환율 vo
    	TokenVo tokenVo = new TokenVo(); // 토큰 vo
    	WalletVo walletVo = new WalletVo(); // 지갑 vo
    	PremiumVo premiumVo = new PremiumVo(); // 프리미엄 vo
    	
    	List<CodeVo> exchangeList = new ArrayList<CodeVo>(); // 거래소 리스트
    	List<CodeVo> currencyList = new ArrayList<CodeVo>(); // 화폐 리스트
    	
		int isTrade=0; // 거래 여부 0:no / 1:yes
		
		float qty; // 수량
		float userMaxDeal; // 최대 거래 금액
		float userMinDeal; // 최소 거래 금액
		float sellVal; // 예상 판매 금액
		float marginRate; // 마진률
		float balance; // 잔액
		float exchangeRate; // 환율
		
		String userId=""; // session.getUserId();
    	userVo = premiumMapper.selectUserInfo(userId); // get 유저 정보
    	
    	String cdCategory = "exchange"; // 거래소 카테고리
    	exchangeList = premiumMapper.selectCodeCategory(cdCategory);
    	for (int i = 0; i < exchangeList.size()-1; i++) {
    		for (int j = i+1; j < exchangeList.size(); j++) {
        		cdCategory = "currency"; // 가상화폐 카테고리
        		currencyList = premiumMapper.selectCodeCategory(cdCategory);
    			for (int j2 = 0; j2 < currencyList.size(); j2++) {
    				
    				/*
    				 * exchangeList.get(i) - from exchange
    				 * exchangeList.get(j) - to exchange
    				 * currencyList.get(j2) - currency
    				*/
   				 
    				// get 수수료, 엑세스토큰, 시크릿 키, 기본 사용 화폐
    				tokenVo = premiumMapper.selectToekn(userVo, exchangeList.get(i).getCdKey(), currencyList.get(j2).getCdKey()); // param: userVo, 거래소 코드, 화폐 코드 
    				
    				// get 기본 사용 화폐 잔액
    				walletVo = premiumMapper.selectWallet(userVo, exchangeList.get(i).getCdKey(), tokenVo.getKeyCurrencyCd()); // param: userVo, 거래소 코드, 화폐 코드 
    				
    				
    				exchangeRate = premiumMapper.selectExchangeRate(tokenVo.getKeyCurrencyCd()); // param: 기본 화폐 코드
    				balance = walletVo.getQty() * exchangeRate;
					userMaxDeal = userVo.getUserMaxDeal();
					userMinDeal = userVo.getUserMinDeal();
					
					// 잔액이 최소거래금액 이상이면 거래를 함. 최대거래금액 이상이면 최대거래금액만큼 거래한다
					if(balance > userMinDeal) { 
						isTrade = 1;
						if(balance > userMaxDeal) {
							balance = userMaxDeal;
						}
					}
					
					/*
					// param: 거래소 코드, 화폐 코드
					JSONObject fromExchangeInfo = getOrderBook(exchangeList.get(i).getCdVal(), currencyList.get(j2)); // from orderbook api 호출
					JSONObject toExchangeInfo = getOrderBook(exchangeList.get(j).getCdVal(), currencyList.get(j2)); // to orderbook api 호출 
					
					
					if(isTrade == 1) {
						qty = (balance / fromExchangeInfo.ask) * (1 - tokenVo.getTakerFee()); // 구매할 수 있는 수량
						sellVal = (qty * toExchangeInfo.bid) * (1 - tokenVo.getMarkerFee()); // 판매 시 예상 판매 금액
						marginRate = (sellVal - balance) / (balance * 100); // 마진률 
						
						
						premiumMapper.insertPremium(premiumVo);
					}
					
					
    				 * 해야되는 것. 
    				 * 1. getOderBook 메서드 생성 ( 거래소 별 orderbook 갖고 올 수 있도록 )
    				 * 2. 계산 후 마진률 premium에 insert
    				 */
    				
				}
			}
		}
    	
	}
}
