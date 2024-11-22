package com.apixelhouse.taskaph.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apixelhouse.taskaph.dto.EmergencyContact01;
import com.apixelhouse.taskaph.dto.User;
import com.apixelhouse.taskaph.exception.ContactNotFoundException;
import com.apixelhouse.taskaph.repo.UserRepo;
import com.apixelhouse.taskaph.repo.contact01Repository;

@Service
public class EmergencyContact01Service {

	@Autowired
	private contact01Repository contact01Repository;

	@Autowired
	private UserRepo userRepo;

	// Create a new emergency contact linked to a user
	public EmergencyContact01 createContact(EmergencyContact01 contact, int userId) {
		Optional<User> optionalUser = userRepo.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			contact.setUser(user); // Link contact with user
			return contact01Repository.save(contact);
		} else {
			throw new ContactNotFoundException("User not found with ID: " + userId);
		}
	}

	// Get contact by ID
	public EmergencyContact01 getContactById(int id) {
		return contact01Repository.findById(id)
				.orElseThrow(() -> new ContactNotFoundException("Contact not found with ID: " + id));
	}

	// Get all contacts
	public List<EmergencyContact01> getAllContacts() {
		return contact01Repository.findAll();
	}

	// Update contact details by ID
	public EmergencyContact01 updateContact(int id, EmergencyContact01 contactDetails) {
		try {

			EmergencyContact01 contact = getContactById(id);
			contact.setFirstName(contactDetails.getFirstName());
			contact.setLastName(contactDetails.getLastName());
			contact.setEmail(contactDetails.getEmail());
			contact.setPhone(contactDetails.getPhone());
//			contact.setGender(contactDetails.getGender());
			contact.setRelationship(contact.getRelationship());
			contact.setAddress(contactDetails.getAddress());
			contact.setCity(contactDetails.getCity());
			contact.setZipPostal(contactDetails.getZipPostal());
			contact.setState(contactDetails.getState());
			return contact01Repository.save(contact);

		} catch (Exception e) {
			throw new RuntimeException("Error while updating user: " + e.getMessage());
		}
	}

	// Delete contact by ID
	public void deleteContact(int id) {
		try {
			EmergencyContact01 contact = getContactById(id);
			contact01Repository.delete(contact); // Delete contact if it exists
			
		} catch (Exception e) {
			throw new RuntimeException("Failed Deletion"+e.getMessage());
		}
	}
}
