package com.apixelhouse.taskaph.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apixelhouse.taskaph.dto.EmergencyContact01;

import com.apixelhouse.taskaph.service.EmergencyContact01Service;

@RestController
@RequestMapping("/api/emergencyContact01")
@CrossOrigin("http://localhost:5173/")
public class EmergencyContact01Controller {
    
    @Autowired
    private EmergencyContact01Service emergencyContact01Service;

    @PostMapping("/saveContact/{id}")
    public ResponseEntity<EmergencyContact01> createContact(@RequestBody EmergencyContact01 contact, @PathVariable int id) {
        EmergencyContact01 createdContact = emergencyContact01Service.createContact(contact, id);
        return ResponseEntity.ok(createdContact);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmergencyContact01> getContactById(@PathVariable int id) {
        EmergencyContact01 contact = emergencyContact01Service.getContactById(id);
        return ResponseEntity.ok(contact);
    }

    @GetMapping
    public ResponseEntity<List<EmergencyContact01>> getAllContacts() {
        List<EmergencyContact01> contacts = emergencyContact01Service.getAllContacts();
        return ResponseEntity.ok(contacts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmergencyContact01> updateContact(@PathVariable int id, @RequestBody EmergencyContact01 contactDetails) {
    	
        EmergencyContact01 updatedContact = emergencyContact01Service.updateContact(id, contactDetails);
        return ResponseEntity.ok(updatedContact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable int id) {
        emergencyContact01Service.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
