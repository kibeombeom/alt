package com.altcoin.outside.account.balance.vo;

import com.altcoin.base.vo.BaseVo;

import lombok.Data;

@Data
public class BalanceVo extends BaseVo {
	
	private String userNo;
	private String userId;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
