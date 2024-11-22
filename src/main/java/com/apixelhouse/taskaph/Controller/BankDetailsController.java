package com.apixelhouse.taskaph.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apixelhouse.taskaph.dto.BankDetails;
import com.apixelhouse.taskaph.service.BankDetailsService;

@RestController
@RequestMapping("/api/bankDetails")
public class BankDetailsController {
	 @Autowired
	    private BankDetailsService bankDetailsService;

	    // Create new bank details for a user
	    @PostMapping("/add/{userId}")
	    public ResponseEntity<BankDetails> addBankDetails(@PathVariable int userId, @RequestBody BankDetails bankDetails) {
	        BankDetails savedBankDetails = bankDetailsService.addBankDetails(userId, bankDetails);
	        return ResponseEntity.ok(savedBankDetails);
	    }

	    // Get bank details by user ID
	    @GetMapping("/user/{userId}")
	    public ResponseEntity<BankDetails> getBankDetailsByUserId(@PathVariable int userId) {
	        BankDetails bankDetails = bankDetailsService.getBankDetailsByUserId(userId);
	        return ResponseEntity.ok(bankDetails);
	    }

	    // Update bank details for a user
	    @PutMapping("/update/{userId}")
	    public ResponseEntity<BankDetails> updateBankDetails(@PathVariable int userId, @RequestBody BankDetails newBankDetails) {
	        BankDetails updatedBankDetails = bankDetailsService.updateBankDetails(userId, newBankDetails);
	        return ResponseEntity.ok(updatedBankDetails);
	    }

	    // Delete bank details by user ID
	    @DeleteMapping("/delete/{userId}")
	    public ResponseEntity<String> deleteBankDetailsByUserId(@PathVariable int userId) {
	        bankDetailsService.deleteBankDetailsByUserId(userId);
	        return ResponseEntity.ok("Bank details deleted successfully.");
	    }
	    
//	    List Of Bank Details
	    @GetMapping("/getAll")
	    public List<BankDetails>getAll(){
	    	return bankDetailsService.getAll();
	    }
	    

}
