package com.altcoin.outside.account.balance.service;

import com.altcoin.base.vo.UserHeader;

import net.minidev.json.JSONObject;

public interface BalanceService {

	public JSONObject selectCoinoneBalance(UserHeader header) throws Exception;

    public JSONObject selectPoloniexBalance(UserHeader header) throws Exception;

}
