package com.altcoin.trading.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.altcoin.trading.service.CoinoneAPIService;
import com.altcoin.trading.vo.CoinoneVo;

@Service
public class CoinoneAPIServiceImpl implements CoinoneAPIService {
	
	private static final String COINONE_API_URI = "https://api.coinone.co.kr";
    private static final String COINONE_API_ORDERBOOK_URI = "https://api.coinone.co.kr/orderbook/";
    private static final String COINONE_API_TICKER_URI = "https://api.coinone.co.kr/ticker/";
    private static final String COINONE_API_TRADES_URI = "https://api.coinone.co.kr/trades/";
    private static final String COINONE_API_BALANCE_URI = "https://api.coinone.co.kr/v1/account/balance/";
    
    private static final String ACCESS_TOKEN = "bde9009a-a2dd-471d-af49-160c908d08bf";

	@Override
	public CoinoneVo selectBalance(CoinoneVo vo) throws Exception {
		ResponseEntity<CoinoneVo> coinoneResponseEntity = null;

        RestTemplate restTemplate = new RestTemplate();
        
        String url = COINONE_API_TICKER_URI + "?currency=xrp";
        
        coinoneResponseEntity = restTemplate.getForEntity(url, CoinoneVo.class);
        
        System.out.println(coinoneResponseEntity.getBody().getLast());
        System.out.println(coinoneResponseEntity.getBody().getLast());
        System.out.println(coinoneResponseEntity.getBody().getLast());
        System.out.println(coinoneResponseEntity.getBody().getLast());
        System.out.println(coinoneResponseEntity.getBody().getLast());
        
        return coinoneResponseEntity.getBody();
	}

}
