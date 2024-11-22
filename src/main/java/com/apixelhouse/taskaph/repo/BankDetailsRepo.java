package com.apixelhouse.taskaph.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apixelhouse.taskaph.dto.BankDetails;

public interface BankDetailsRepo extends JpaRepository<BankDetails, Integer>{
	
	BankDetails findByUserUserID(int userID);
}
