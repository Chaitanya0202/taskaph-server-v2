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
import com.apixelhouse.taskaph.dto.EmergencyContact02;
import com.apixelhouse.taskaph.service.EmergencyContact02Service;

@RestController
@RequestMapping("/api/emergencyContact02")
@CrossOrigin("http://localhost:5173/")
public class EmergencyContact02Controller {

	 @Autowired
	    private EmergencyContact02Service emergencyContact02Service;

	 
	  @PostMapping("/saveContact/{id}")
	    public ResponseEntity<EmergencyContact02> createContact(@RequestBody EmergencyContact02 contact , @PathVariable int id) {
	        EmergencyContact02 createdContact = emergencyContact02Service.createContact(contact,id);
	        return ResponseEntity.ok(createdContact);
	    }

	    // Get a contact by ID
	    @GetMapping("/{id}")
	    public ResponseEntity<EmergencyContact02> getContactById(@PathVariable int id) {
	        EmergencyContact02 contact = emergencyContact02Service.getContactById(id);
	        return ResponseEntity.ok(contact);
	    }

	    // Delete a contact by ID
	    @DeleteMapping("/{id}")
	    public ResponseEntity<EmergencyContact02> deleteContact(@PathVariable int id) {
	        emergencyContact02Service.deleteContact(id);
	        return ResponseEntity.noContent().build();
	    }
	    
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<EmergencyContact02>updateEc02(@PathVariable int id,@RequestBody EmergencyContact02 contact02){
	    	EmergencyContact02 updatedContact=emergencyContact02Service.updateEc02(id,contact02);
	    	return ResponseEntity.ok(updatedContact);
	    }
	    
	    @GetMapping("/getAll")
	    public ResponseEntity<List<EmergencyContact02>> getAll(){
	    List<EmergencyContact02> contact02=emergencyContact02Service.getAll();
	    	return ResponseEntity.ok(contact02);
	    }
	    
	    
	    
	    
	    
}
