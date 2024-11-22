package com.apixelhouse.taskaph.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apixelhouse.taskaph.dto.OfficialDetails;
import com.apixelhouse.taskaph.dto.User;
import com.apixelhouse.taskaph.exception.ContactNotFoundException;
import com.apixelhouse.taskaph.repo.OfficialDetailsRepo;
import com.apixelhouse.taskaph.repo.UserRepo;

@Service
public class OfficialDetailsService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
    private OfficialDetailsRepo officialDetailsRepository;

    public OfficialDetails createOfficialDetails(OfficialDetails details,int id) {
    	 Optional<User> optional = userRepo.findById(id);
		    if (optional.isPresent()) {
		        User user = optional.get();
		        
		        // Set the user ID in the contact
		        details.setUser(user);
		        
		        // Save the contact
		        return officialDetailsRepository.save(details); // Save the contact directly
		    }
		    return null; // Return null if the user is not found
		
    }

    public OfficialDetails getOfficialDetailsById(int id) {
        return officialDetailsRepository.findById(id).orElseThrow(() -> new ContactNotFoundException("ID not found"));
    }

    public List<OfficialDetails> getAllOfficialDetails() {
        return officialDetailsRepository.findAll();
    }

    public OfficialDetails updateOfficialDetails(int id, OfficialDetails details) {
        OfficialDetails existingDetails = getOfficialDetailsById(id);
        existingDetails.setOfficeBranchLocation(details.getOfficeBranchLocation());
        existingDetails.setDesignation(details.getDesignation());
        existingDetails.setEmploymentType(details.getEmploymentType());
        existingDetails.setJoiningDate(details.getJoiningDate());
        existingDetails.setPhoneNumber(details.getPhoneNumber());
        existingDetails.setCurrentSalary(details.getCurrentSalary());
        return officialDetailsRepository.save(existingDetails);
    }

    public void deleteOfficialDetails(int id) {
        OfficialDetails details = getOfficialDetailsById(id);
        officialDetailsRepository.delete(details);
    }

}
