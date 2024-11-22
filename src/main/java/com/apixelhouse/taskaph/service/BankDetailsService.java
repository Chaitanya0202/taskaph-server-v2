package com.apixelhouse.taskaph.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apixelhouse.taskaph.dto.BankDetails;
import com.apixelhouse.taskaph.dto.User;
import com.apixelhouse.taskaph.repo.BankDetailsRepo;
import com.apixelhouse.taskaph.repo.UserRepo;

@Service
public class BankDetailsService {
	
//	@Autowired
//    private BankDetails bankDetailsRepository;
	
	@Autowired
	private BankDetailsRepo bankDetailsRepository;

    @Autowired
    private UserRepo userRepository;

    public BankDetails addBankDetails(int userId, BankDetails bankDetails) {
        // Fetch the User associated with the bank details
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            bankDetails.setUser(user); // Link the user to the bank details
            return bankDetailsRepository.save(bankDetails);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    public BankDetails getBankDetailsByUserId(int userId) {
        return bankDetailsRepository.findByUserUserID(userId);
    }

    public BankDetails updateBankDetails(int userId, BankDetails newBankDetails) {
        BankDetails existingBankDetails = bankDetailsRepository.findByUserUserID(userId);

        if (existingBankDetails != null) {
            existingBankDetails.setBankName(newBankDetails.getBankName());
            existingBankDetails.setAccountHolder(newBankDetails.getAccountHolder());
            existingBankDetails.setAccountNumber(newBankDetails.getAccountNumber());
            existingBankDetails.setIfscCode(newBankDetails.getIfscCode());
            existingBankDetails.setBranch(newBankDetails.getBranch());
            return bankDetailsRepository.save(existingBankDetails);
        } else {
            throw new RuntimeException("No BankDetails found for user with ID: " + userId);
        }
    }

    public void deleteBankDetailsByUserId(int userId) {
        BankDetails bankDetails = bankDetailsRepository.findByUserUserID(userId);
        if (bankDetails != null) {
            bankDetailsRepository.delete(bankDetails);
        } else {
            throw new RuntimeException("No BankDetails found for user with ID: " + userId);
        }
    }

	public List<BankDetails> getAll() {
		
		return bankDetailsRepository.findAll();
	}

}
