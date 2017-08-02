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
		
		HttpPost http = new HttpPost("http://finance.daum.net/exchange/exchangeMain.daum"); // ������ HTTP �ּ� ����
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
		int startInt = sb.toString().indexOf(start)+20; // +20 ������ startStr �� ���� ��ŭ ���� ����
		int endInt = sb.toString().indexOf(end); //

		// ȯ�� ������ ���� 
		String exchange_all = sb.toString().substring(startInt, endInt).replaceAll("\"",""); // " ����
		exchange_all = exchange_all.replaceAll("(\r\n|\r|\n|\n\r)", " "); // ���๮�� ����
		exchange_all = exchange_all.replaceAll("	", ""); // �� ����
		exchange_all = exchange_all.replaceAll(" ", ""); // ���� ����

		String[] exchange_sentence = exchange_all.split("],");
		for(String exchange_line : exchange_sentence) {
			String[] exchange_word = exchange_line.split(",");
			String country = exchange_word[0].replace("[",""); // ���� ��
			String currency = exchange_word[1]; // ��ȭ �� 
			String value = exchange_word[3]; // ȯ��
			
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
		
    	UserVo userVo = new UserVo(); // ���� vo
    	ExchangeRateVo exchangeVo = new ExchangeRateVo(); // ȯ�� vo
    	TokenVo tokenVo = new TokenVo(); // ��ū vo
    	WalletVo walletVo = new WalletVo(); // ���� vo
    	PremiumVo premiumVo = new PremiumVo(); // �����̾� vo
    	
    	List<CodeVo> exchangeList = new ArrayList<CodeVo>(); // �ŷ��� ����Ʈ
    	List<CodeVo> currencyList = new ArrayList<CodeVo>(); // ȭ�� ����Ʈ
    	
		int isTrade=0; // �ŷ� ���� 0:no / 1:yes
		
		float qty; // ����
		float userMaxDeal; // �ִ� �ŷ� �ݾ�
		float userMinDeal; // �ּ� �ŷ� �ݾ�
		float sellVal; // ���� �Ǹ� �ݾ�
		float marginRate; // ������
		float balance; // �ܾ�
		float exchangeRate; // ȯ��
		
		String userId=""; // session.getUserId();
    	userVo = premiumMapper.selectUserInfo(userId); // get ���� ����
    	
    	String cdCategory = "exchange"; // �ŷ��� ī�װ�
    	exchangeList = premiumMapper.selectCodeCategory(cdCategory);
    	for (int i = 0; i < exchangeList.size()-1; i++) {
    		for (int j = i+1; j < exchangeList.size(); j++) {
        		cdCategory = "currency"; // ����ȭ�� ī�װ�
        		currencyList = premiumMapper.selectCodeCategory(cdCategory);
    			for (int j2 = 0; j2 < currencyList.size(); j2++) {
    				
    				/*
    				 * exchangeList.get(i) - from exchange
    				 * exchangeList.get(j) - to exchange
    				 * currencyList.get(j2) - currency
    				*/
   				 
    				// get ������, ��������ū, ��ũ�� Ű, �⺻ ��� ȭ��
    				tokenVo = premiumMapper.selectToekn(userVo, exchangeList.get(i).getCdKey(), currencyList.get(j2).getCdKey()); // param: userVo, �ŷ��� �ڵ�, ȭ�� �ڵ� 
    				
    				// get �⺻ ��� ȭ�� �ܾ�
    				walletVo = premiumMapper.selectWallet(userVo, exchangeList.get(i).getCdKey(), tokenVo.getKeyCurrencyCd()); // param: userVo, �ŷ��� �ڵ�, ȭ�� �ڵ� 
    				
    				
    				exchangeRate = premiumMapper.selectExchangeRate(tokenVo.getKeyCurrencyCd()); // param: �⺻ ȭ�� �ڵ�
    				balance = walletVo.getQty() * exchangeRate;
					userMaxDeal = userVo.getUserMaxDeal();
					userMinDeal = userVo.getUserMinDeal();
					
					// �ܾ��� �ּҰŷ��ݾ� �̻��̸� �ŷ��� ��. �ִ�ŷ��ݾ� �̻��̸� �ִ�ŷ��ݾ׸�ŭ �ŷ��Ѵ�
					if(balance > userMinDeal) { 
						isTrade = 1;
						if(balance > userMaxDeal) {
							balance = userMaxDeal;
						}
					}
					
					/*
					// param: �ŷ��� �ڵ�, ȭ�� �ڵ�
					JSONObject fromExchangeInfo = getOrderBook(exchangeList.get(i).getCdVal(), currencyList.get(j2)); // from orderbook api ȣ��
					JSONObject toExchangeInfo = getOrderBook(exchangeList.get(j).getCdVal(), currencyList.get(j2)); // to orderbook api ȣ�� 
					
					
					if(isTrade == 1) {
						qty = (balance / fromExchangeInfo.ask) * (1 - tokenVo.getTakerFee()); // ������ �� �ִ� ����
						sellVal = (qty * toExchangeInfo.bid) * (1 - tokenVo.getMarkerFee()); // �Ǹ� �� ���� �Ǹ� �ݾ�
						marginRate = (sellVal - balance) / (balance * 100); // ������ 
						
						
						premiumMapper.insertPremium(premiumVo);
					}
					
					
    				 * �ؾߵǴ� ��. 
    				 * 1. getOderBook �޼��� ���� ( �ŷ��� �� orderbook ���� �� �� �ֵ��� )
    				 * 2. ��� �� ������ premium�� insert
    				 */
    				
				}
			}
		}
    	
	}
}
