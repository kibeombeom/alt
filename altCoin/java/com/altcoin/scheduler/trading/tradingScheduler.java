package com.altcoin.scheduler.trading;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.altcoin.outside.account.balance.dao.BalanceMapper;
import com.altcoin.outside.account.balance.vo.BalanceVo;

@Component
public class tradingScheduler {
	
	@Autowired
	private BalanceMapper balanceMapper;
	/*
	@Scheduled(cron = "0/2 * * * * *")
	public void trading() {
		try {
			// 1. 주문내역 조회
			BalanceVo balanceVo = balanceMapper.selectBalanace();
			
			System.out.println(balanceVo.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
}
	