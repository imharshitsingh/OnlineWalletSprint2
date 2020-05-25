package com.cg.onlinewallet.service;


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
	private OnlineWalletDao owDao;

	/*********************************************************************************************************************
	 * Method: login Description: To Validate the user data so that the user can
	 * login
	 * 
	 * @param email:  User's email
	 * 
	 * @param password:  User's password
	 * 
	 * @returns Integer: userId associated with the loginName provided if no exceptions occurs
	 *        
	 * @throws UnauthorizedAccessException: if the account linked with loginName is not an  active user
	 *            
	 * @throws InvalidException: if the account associated with loginName is a admin  type account
	 *            
	 * @throws ValidationException: it is raised if the password dosen't matches with the user's stored password 
	 *          
	 *            Created By - Vinay Kumar Singh
	 ***********************************************************************************************************************/

	@Override
	public Integer login(String email, String password) 
	{
		if(!owDao.checkByEmail(email)) {
			throw new UnauthorizedAccessException("No User exist for this email address. Kindly Register");
		}
			
		WalletUser user = owDao.getByEmail(email);
		WalletAccount account = user.getAccountDetail();
		if (account.getUserStatus() == status.non_active) {
			throw new UnauthorizedAccessException("Your Account is not Activated");
		}
			
		if (user.getUserType() == type.admin) {
			throw new InvalidException("You are not authorized to login from here");
		}
			
		if (user.getPassword().equals(password) == false) {
			throw new ValidationException("The LoginName and password Combination does not match");
		}
			
		return user.getUserID();
	}

	/***************************************************************************************
	 * Method: transactMoney Description: transfers the amount from one wallet account to another
	 * 
	 * @param userId:  user's userId
	 * 
	 * @param payeeEmail: payee's email to transfer money
	 * 
	 * @param amount: amount to be transfered
	 *            
	 * @throws InvalidException: if the payee dosen't exist
	 *              
	 * @throws InvalidException:    if the payee is not an active user
	 *          
	 * @throws WrongValueException:  if the amount is greater then the account balance of the user
	 *            
	 *             Created By - Harshit Singh
	 * 
	 ***********************************************************************************************************************/
	@Override
	public void transactMoney(Integer userId, String payeeEmail, Double amount)
	{
		if (owDao.checkByEmail(payeeEmail) == false)
		{
			throw new InvalidException("The Beneficary doesn't exist");
		}
			
		WalletUser payee = owDao.getByEmail(payeeEmail);
		if (payee.getAccountDetail().getUserStatus() == status.non_active)
		{
			throw new InvalidException("The Beneficiary must be an active user");
		}
			
		WalletUser user = owDao.getUser(userId);
		if (user.getAccountDetail().getAccountBalance() < amount)
		{
			throw new WrongValueException("The Amount cannot be greater then available Balance");
		}
			
		Integer payeeId = payee.getUserID();
		Double payeeBalance = payee.getAccountDetail().getAccountBalance();
		
		payee.getAccountDetail().setAccountBalance(payeeBalance + amount);
		Double userBalance = user.getAccountDetail().getAccountBalance();
		
		user.getAccountDetail().setAccountBalance(userBalance - amount);
		
	}

	
}
