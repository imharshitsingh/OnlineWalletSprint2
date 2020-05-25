package com.cg.onlinewallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.onlinewallet.entities.WalletUser;
import com.cg.onlinewallet.service.*;
@CrossOrigin(origins="*")
@RestController
public class OnlineWalletController {

	@Autowired
	private OnlineWalletService onlineWalletService;

	public OnlineWalletController() {
		// TODO Auto-generated constructor stub
	}
	/*********************************************************************************************************************
	* Method:login
	* Description:To map the request of user for login into the application
	* @param email:User's email
	* @param password:User's password
	* @returns Entity:After login,it will return the userId for the developer use only.
	* Created By-Arushi Bhardwaj
	***********************************************************************************************************************/

	@GetMapping("/login")
	public ResponseEntity<Integer> login(String email, String password) {
		Integer userId = onlineWalletService.login(email, password);
		return new ResponseEntity<Integer>(userId, HttpStatus.OK);
	}
	 
	
	/*********************************************************************************************************************
	* Method:transactMoney
	* Description:To map the request of user for transferring the amount from one user to another user account
	* @param userId:User's Id
	* @param amount:amount to be transferred
	* @returns Entity:After transferring,it will give the message that transaction is completed
	* Created By-Kunal Maheshwari
	***********************************************************************************************************************/

	@GetMapping("/transactmoney/{userId}")
	public ResponseEntity<String> transactMoney(@PathVariable("userId") Integer userId, Double amount,
			String email) {
		onlineWalletService.transactMoney(userId, email, amount);
		return new ResponseEntity<String>("Transaction Completed", HttpStatus.OK);
	}
	
	
}
