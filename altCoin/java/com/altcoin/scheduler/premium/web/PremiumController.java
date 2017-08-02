package com.altcoin.scheduler.premium.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.altcoin.scheduler.premium.service.PremiumService;
import com.altcoin.scheduler.premium.vo.CodeVo;
import com.altcoin.scheduler.premium.vo.ExchangeRateVo;
import com.altcoin.scheduler.premium.vo.TokenVo;
import com.altcoin.scheduler.premium.vo.UserVo;
import com.altcoin.scheduler.premium.vo.WalletVo;

@Controller
public class PremiumController {
	
    @Resource(name = "PremiumService")
    private PremiumService premiumService;
    

    @RequestMapping(value="/tester")
	public String calPremium() throws Exception { 
    	
    	premiumService.setExchangeRate(); // set 환율
    	
    	premiumService.calPremium(); // set 프리미엄
    	
    	

    
		
		return null;
	}
}
