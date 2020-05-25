package com.cg.onlinewallet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import org.springframework.stereotype.Repository;

import com.cg.onlinewallet.entities.*;
import com.cg.onlinewallet.entities.WalletAccount.status;
import com.cg.onlinewallet.exceptions.UnauthorizedAccessException;

@Repository
public class OnlineWalletDaoImp implements OnlineWalletDao {
	@PersistenceContext
	private EntityManager entityManager;

	public OnlineWalletDaoImp() {
		// TODO Auto-generated constructor stub
	}


	/***************************************
	* Method: checkUserByEmail
	* 
	* Description: To check that the email of the user is present or not
	* 
	* @param email:User's email
	* 
	* @returns Boolean: if email exists then true, if not then false
	* 
	* Created By - Harshit Singh
	*****************************************/	
	@Override
	public boolean checkByEmail(String email)
	{ 
		String Qr = "SELECT user.email FROM WalletUser user WHERE user.email= :email";
		TypedQuery<String> query = entityManager.createQuery(Qr, String.class).setParameter("email", email);
		try {
			query.getSingleResult();
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
	/*********************************************************************************************************************
	* Method: getUserByEmail
	* 
	* Description: To fetch the user with the email
	* 
	* @param email:User's email
	* 
	* @returns user: It will return the user present with given email
	* 
	* Created By - Harshit Singh
	***********************************************************************************************************************/
	@Override
	public WalletUser getByEmail(String email) 
	{
		String Qr = "SELECT user FROM WalletUser user WHERE user.email= :email";
		TypedQuery<WalletUser> query = entityManager.createQuery(Qr, WalletUser.class).setParameter("email",
				email);
		return query.getSingleResult();
	}

	

	@Override
	public WalletUser getUser(Integer userId)
	{
		WalletUser user = entityManager.find(WalletUser.class, userId);
		return user;
	}

	
}
