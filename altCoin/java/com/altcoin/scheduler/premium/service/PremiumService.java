package com.altcoin.scheduler.premium.service;

import com.altcoin.scheduler.premium.vo.TokenVo;
import com.altcoin.scheduler.premium.vo.UserVo;
import com.altcoin.scheduler.premium.vo.WalletVo;

public interface PremiumService {

	void setExchangeRate() throws Exception;

	void calPremium();

	//public JSONObject selectCoinoneBalance(UserHeader header) throws Exception;

    //public JSONObject selectPoloniexBalance(UserHeader header) throws Exception;

}
