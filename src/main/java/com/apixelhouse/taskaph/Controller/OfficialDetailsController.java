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

import com.apixelhouse.taskaph.dto.OfficialDetails;
import com.apixelhouse.taskaph.service.OfficialDetailsService;

@RestController
@RequestMapping("/api/officialDetails")
@CrossOrigin("http://localhost:5173/")
public class OfficialDetailsController {
	 @Autowired
	    private OfficialDetailsService officialDetailsService;

	    @PostMapping("/{id}")
	    public ResponseEntity<OfficialDetails> createOfficialDetails(@RequestBody OfficialDetails details ,@PathVariable int id) {
	        OfficialDetails createdDetails = officialDetailsService.createOfficialDetails(details,id);
	        return ResponseEntity.ok(createdDetails);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<OfficialDetails> getOfficialDetailsById(@PathVariable int id) {
	        OfficialDetails details = officialDetailsService.getOfficialDetailsById(id);
	        return ResponseEntity.ok(details);
	    }

	    @GetMapping
	    public ResponseEntity<List<OfficialDetails>> getAllOfficialDetails() {
	        List<OfficialDetails> details = officialDetailsService.getAllOfficialDetails();
	        return ResponseEntity.ok(details);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<OfficialDetails> updateOfficialDetails(@PathVariable int id, @RequestBody OfficialDetails details) {
	        OfficialDetails updatedDetails = officialDetailsService.updateOfficialDetails(id, details);
	        return ResponseEntity.ok(updatedDetails);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteOfficialDetails(@PathVariable int id) {
	        officialDetailsService.deleteOfficialDetails(id);
	        return ResponseEntity.noContent().build();
	    }

}
