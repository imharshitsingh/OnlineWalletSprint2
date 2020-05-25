package com.cg.onlinewallet.dao;

import java.util.List;

import com.cg.onlinewallet.entities.WalletAccount;
import com.cg.onlinewallet.entities.WalletAccount.status;
import com.cg.onlinewallet.entities.WalletTransactions;
import com.cg.onlinewallet.entities.WalletUser;

public interface OnlineWalletDao {
	

	WalletUser getUser(Integer userId);

	WalletUser getByEmail(String email);

	boolean checkByEmail(String email);



}
