package com.altcoin.scheduler.premium.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.altcoin.outside.account.balance.vo.BalanceVo;
import com.altcoin.scheduler.premium.vo.CodeVo;
import com.altcoin.scheduler.premium.vo.ExchangeRateVo;
import com.altcoin.scheduler.premium.vo.TokenVo;
import com.altcoin.scheduler.premium.vo.UserVo;
import com.altcoin.scheduler.premium.vo.WalletVo;

@Mapper
public interface PremiumMapper {


	CodeVo selectCodeValue(String cdVal);

	void insertExchangeRate(ExchangeRateVo exchangeRateVo);

	List<CodeVo> selectCodeCategory(String cdCategory);
	
	TokenVo selectToekn(UserVo userVo, String cdKey, String cdKey2); // ¾È¸¸µë

	WalletVo selectWallet(UserVo userVo, String cdKey, int keyCurrencyCd); // ¾È¸¸µë

	float selectExchangeRate(int keyCurrencyCd); // ¾È¸¸µë

	UserVo selectUserInfo(String userId);



	
	
}
