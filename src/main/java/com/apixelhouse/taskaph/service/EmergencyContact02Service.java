package com.apixelhouse.taskaph.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.apixelhouse.taskaph.dto.EmergencyContact02;
import com.apixelhouse.taskaph.dto.User;
import com.apixelhouse.taskaph.exception.ContactNotFoundException;
import com.apixelhouse.taskaph.repo.Contact02Repository;

import com.apixelhouse.taskaph.repo.UserRepo;

@Service
public class EmergencyContact02Service {

	@Autowired
	private Contact02Repository emergencyContact02Repository;

	@Autowired
	private UserRepo userRepo;

	public EmergencyContact02 createContact(EmergencyContact02 contact, int id) {
		Optional<User> optional = userRepo.findById(id);
		if (optional.isPresent()) {
			User user = optional.get();

			// Set the user ID in the contact
			contact.setUser(user);

			// Save the contact
			return emergencyContact02Repository.save(contact); // Save the contact directly
		} else {
			throw new ContactNotFoundException("User not found with ID: " + id);
		}
	}

//	     Get contact by ID
	public EmergencyContact02 getContactById(int id) {
		return emergencyContact02Repository.findById(id)
				.orElseThrow(() -> new ContactNotFoundException("Contact not found with ID: " + id));
	}

	// Delete contact by ID
	public EmergencyContact02 deleteContact(int id) {
		Optional<EmergencyContact02> contactOptional = emergencyContact02Repository.findById(id);
		if (contactOptional.isPresent()) {
			EmergencyContact02 contact02 = contactOptional.get();
//	            return emergencyContact02Repository.delete(contactOptional.get());
			emergencyContact02Repository.delete(contact02);
			return null;
		} else {
			throw new RuntimeException("Contact not found with ID: " + id);
//	            return null;
		}
	}

	public EmergencyContact02 updateEc02(int id, EmergencyContact02 updatedContact) {
		EmergencyContact02 contact = getContactById(id);

		contact.setFirstName(updatedContact.getFirstName());
		contact.setLastName(updatedContact.getLastName());
		contact.setEmail(updatedContact.getEmail());
		contact.setPhone(updatedContact.getPhone());
//		contact.setGender(updatedContact.getGender());
		contact.setRelationship(updatedContact.getRelationship());		contact.setAddress(updatedContact.getAddress());
		contact.setCity(updatedContact.getCity());
		contact.setZipPostal(updatedContact.getZipPostal());
		contact.setState(updatedContact.getState());
//		        return updatedContact.save(contact);
		return emergencyContact02Repository.save(contact);
//			 System.out.println(contact);
//			 return null;

	}

	public List<EmergencyContact02> getAll() {
		
		try {
			return emergencyContact02Repository.findAll();
			
		} catch (Exception e) {
			
			throw new RuntimeException("Error while fetching all EmergencyCOntact: " + e.getMessage());
		}
	}

}
