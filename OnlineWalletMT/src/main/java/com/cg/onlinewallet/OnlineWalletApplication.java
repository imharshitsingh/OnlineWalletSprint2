package com.cg.onlinewallet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cg.onlinewallet.entities.*;
import com.cg.onlinewallet.entities.WalletAccount.status;
import com.cg.onlinewallet.entities.WalletUser.type;

@Transactional
@SpringBootApplication
public class OnlineWalletApplication implements CommandLineRunner {
	@Autowired
	EntityManager em;

	public static void main(String[] args) {
		SpringApplication.run(OnlineWalletApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		

		WalletAccount wa1 = new WalletAccount(1000.00, status.active);
		WalletAccount wa2 = new WalletAccount(1000.00, status.active);
		WalletAccount wa3 = new WalletAccount(0.0, status.active);
	

		WalletUser wu1 = new WalletUser("Harshit Singh", "Harshit@123", "9897446350", "harshit@gmail.com", type.user, wa1);
		WalletUser wu2 = new WalletUser("Vinay Singh Parmar", "Parmar&123", "9876543210", "parmar@gmail.com",type.user, wa2);
		WalletUser wu3 = new WalletUser("Admin", "Admin&123", "9999999999", "admin@admin.com", type.admin, wa3);
		
		

		em.persist(wu1);
		em.persist(wu2);
		em.persist(wu3);
	

		em.persist(wa1);
		em.persist(wa2);
		em.persist(wa3);
		

		
		
	}
    
}
