package com.altcoin.scheduler.premium;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.altcoin.outside.account.balance.service.BalanceService;
import com.altcoin.scheduler.premium.dao.PremiumMapper;
import com.altcoin.scheduler.premium.service.PremiumService;


@Component
public class premiumScheduler {
	
/*
	@Scheduled(cron = "0/10 * * * * *")
	public void cronTest1() {
		try {
			System.out.println("premiumScheduler");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	
}
	