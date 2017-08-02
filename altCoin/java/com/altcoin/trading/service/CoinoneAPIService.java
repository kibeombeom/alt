package com.altcoin.trading.service;

import org.springframework.stereotype.Service;

import com.altcoin.trading.vo.CoinoneVo;

@Service
public interface CoinoneAPIService {
	
	public CoinoneVo selectBalance(CoinoneVo vo) throws Exception;

}
