package com.cg.onlinewallet.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.onlinewallet.dao.*;
import com.cg.onlinewallet.entities.*;
import com.cg.onlinewallet.entities.WalletAccount.status;
import com.cg.onlinewallet.entities.WalletUser.type;
import com.cg.onlinewallet.exceptions.*;

@Transactional
@Service
public class OnlineWalletServiceImp implements OnlineWalletService {

	public OnlineWalletServiceImp() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private OnlineWalletDao onlineWalletDao;

	/*********************************************************************************************************************
	 * Method: login Description: To Validate the user data so that the user can
	 * login
	 * 
	 * @param email:
	 *            User's email
	 * @param password:
	 *            User's password
	 * @returns Integer: userId associated with the loginName provided if no
	 *          exceptions occurs
	 * @throws UnauthorizedAccessException:it
	 *             is raised if the account associated with loginName is not an
	 *             active user
	 * @throws InvalidException:
	 *             it is raised if the account associated with loginName is a admin
	 *             type account
	 * @throws ValidationException:
	 *             it is raised if the password dosen't matches with the user's
	 *             stored password Created By - Aarushi Bhardwaj
	 * 
	 ***********************************************************************************************************************/

	@Override
	public Integer login(String email, String password) {
		if(!onlineWalletDao.checkUserByEmail(email))
			throw new UnauthorizedAccessException("No User exist for this email address. Kindly Register");
		WalletUser user = onlineWalletDao.getUserByEmail(email);
		WalletAccount account = user.getAccountDetail();
		if (account.getUserStatus() == status.non_active)
			throw new UnauthorizedAccessException("Your Account is not Activated");
		if (user.getUserType() == type.admin)
			throw new InvalidException("You are not authorized to login from here");
		if (user.getPassword().equals(password) == false)
			throw new ValidationException("The LoginName and password Combination does not match");
		return user.getUserID();
	}

	/***************************************************************************************
	 * Method: transactMoney Description: Changes the status of account of user from
	 * active to non-active and otherway around
	 * 
	 * @param userId:
	 *            user's userId
	 * @param beneficiaryEmail:
	 *            beneficiary's email to transfer money
	 * @param amount:
	 *            amount to be transfered
	 * @throws InvalidException:
	 *             it is raised if the beneficiary dosen't exist
	 * @throws InvalidException:
	 *             it is raised if the beneficiary is not an active user
	 * @throws WrongValueException:
	 *             it is raised if the amount is greater then the account balance of
	 *             the user Created By - Kunal Maheshwari
	 * 
	 ***********************************************************************************************************************/
	@Override
	public void transactMoney(Integer userId, String beneficiaryEmail, Double amount) {
		if (onlineWalletDao.checkUserByEmail(beneficiaryEmail) == false)
			throw new InvalidException("The Beneficary doesn't exist");
		WalletUser beneficiary = onlineWalletDao.getUserByEmail(beneficiaryEmail);
		if (beneficiary.getAccountDetail().getUserStatus() == status.non_active)
			throw new InvalidException("The Beneficiary must be an active user");
		WalletUser user = onlineWalletDao.getUser(userId);
		if (user.getAccountDetail().getAccountBalance() < amount)
			throw new WrongValueException("The Amount cannot be greater then available Balance");
		Integer beneficiaryId = beneficiary.getUserID();
		Double beneficiaryBalance = beneficiary.getAccountDetail().getAccountBalance();
		beneficiary.getAccountDetail().setAccountBalance(beneficiaryBalance + amount);
		Double userBalance = user.getAccountDetail().getAccountBalance();
		user.getAccountDetail().setAccountBalance(userBalance - amount);
		
	}

	
}
