package com.altcoin.outside.account.balance.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.altcoin.base.vo.UserHeader;
import com.altcoin.outside.account.balance.service.BalanceService;
import com.altcoin.util.Encoding;
import com.altcoin.util.HttpUtil;

import net.minidev.json.JSONObject;

@Service("balanceService")
public class BalanceServiceImpl implements BalanceService {
	
	private static final String COINONE_API_URI = "https://api.coinone.co.kr";
    private static final String COINONE_API_BALANCE_URI = "/v2/account/balance/";

    private static final String POLONIEX_API_URI = "https://poloniex.com/";
    private static final String POLONIEX_API_TICKER_URI = "public?command=returnTicker";
    private static final String POLONIEX_API_BALANCE_URI = "&command=returnBalances";
    private static final String POLONIEX_API_TRADING_URI = "tradingApi";
    
    @Override
    public JSONObject selectCoinoneBalance(UserHeader header) throws Exception {
        JSONObject params = HttpUtil.settingApiKeys(header.getCoinoneApikeys());

        Map<String, Object> data = Encoding.coinoneEncoding(params, header);

        JSONObject result = HttpUtil.getJSONfromPost(COINONE_API_URI + COINONE_API_BALANCE_URI, (Map<String, String>) data.get("map"), (String)data.get("payload"));
        
        return result;
    }

    @Override
    public JSONObject selectPoloniexBalance(UserHeader header) throws Exception {
        JSONObject params = HttpUtil.settingApiKeys(header.getPoloniexApikeys());

        Map<String, Object> data = Encoding.poloniexEncoding(params, header, POLONIEX_API_BALANCE_URI);

        JSONObject result = HttpUtil.getJSONfromPost(POLONIEX_API_URI + POLONIEX_API_TRADING_URI, (Map<String, String>) data.get("map"), (String)data.get("param"));

        return result;
    }

}
