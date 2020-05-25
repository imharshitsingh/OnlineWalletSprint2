package com.cg.onlinewallet.service;


public interface OnlineWalletService {
	

	void transactMoney(Integer userId, String payeeLoginName, Double amount);

	Integer login(String loginName, String password);

	
}
