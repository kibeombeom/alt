package com.altcoin.outside.account.balance.dao;

import org.apache.ibatis.annotations.Mapper;

import com.altcoin.outside.account.balance.vo.BalanceVo;

@Mapper
public interface BalanceMapper {
	
    public BalanceVo selectBalanace();
	
}
