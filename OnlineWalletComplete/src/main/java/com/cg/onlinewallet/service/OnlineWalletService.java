package com.cg.onlinewallet.service;

import java.util.List;

import com.cg.onlinewallet.entities.WalletUser;

public interface OnlineWalletService {
	

	void transactMoney(Integer userId, String beneficiaryLoginName, Double amount);

	Integer login(String loginName, String password);

	
}
