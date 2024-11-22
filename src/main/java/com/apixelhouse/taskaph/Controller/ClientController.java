package com.apixelhouse.taskaph.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apixelhouse.taskaph.dto.Client;
import com.apixelhouse.taskaph.dto.Task;
import com.apixelhouse.taskaph.repo.ClientRepo;
import com.apixelhouse.taskaph.repo.TaskRepo;
import com.apixelhouse.taskaph.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin("*")
public class ClientController {

	
	@Autowired
    private ClientService clientService;
	
    @Autowired
    private ObjectMapper objectMapper;
    
	@Autowired
	private ClientRepo clientRepo;
	@Autowired
	private TaskRepo taskRepo;

//    @PostMapping("/save/{id}")
//    public ResponseEntity<Client> createClient(@RequestBody Client Client, @PathVariable int id) {
//    	
//        Client newClient = clientService.createClient(Client,id);
//        return ResponseEntity.ok(newClient);
//    }
	
    
//	@PostMapping("/saveClienturl")
//	public ResponseEntity<Client> saveClient(
//	        @RequestPart("client") String clientJson,
//	        @RequestPart(value = "profile", required = false) MultipartFile profile) {
//	    try {
//	        // Convert the JSON string to a Client object
//	        ObjectMapper objectMapper = new ObjectMapper();
//	        Client client = objectMapper.readValue(clientJson, Client.class);
//
//	        // Set the profile image as bytes if it exists
//	        if (profile != null && !profile.isEmpty()) {
//	            client.setProfile(profile.getBytes());
//	        }
//
//	        Client savedClient = clientService.saveClient(client);
//	        return ResponseEntity.ok(savedClient);
//	    } catch (IOException e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//	    }
//	}

	  @PostMapping("/saveClienturl")
	    public ResponseEntity<?> saveClient(
	            @RequestPart("client") String clientJson,
	            @RequestPart(value = "profile", required = false) MultipartFile profile) {
	        try {
	            // Parse JSON to Client object
	            Client client = objectMapper.readValue(clientJson, Client.class);

	            // If profile image exists, set it as bytes in the client
	            if (profile != null && !profile.isEmpty()) {
	                if (!profile.getContentType().startsWith("image/")) {
	                    return ResponseEntity.badRequest().body("Profile must be an image.");
	                }
	                client.setProfile(profile.getBytes());
	            }
	            

	            Client savedClient = clientService.saveClient(client);
	            return ResponseEntity.ok(savedClient);
	        } catch (JsonProcessingException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid client data: " + e.getMessage());
	        } catch (IOException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing profile image.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
	        }
	    }

//	    @PostMapping("/saveClient")
//	    public ResponseEntity<Client> saveClient(
//	            @RequestPart("client") Client client,
//	            @RequestPart(value = "profile", required = false) MultipartFile profile) {
//	        try {
//	            if (profile != null && !profile.isEmpty()) {
//	                client.setProfile(profile.getBytes());
//	            }
//	        } catch (IOException e) {
//	            return ResponseEntity.status(500).build();
//	        }
//
//	        Client savedClient = clientService.saveClient(client);
//	        return ResponseEntity.ok(savedClient);
//	    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable int id) {
        Client client = clientService.getClientById(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }
    

    @GetMapping("/getAll")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable int id, @RequestBody Client Client) {
        Client updatedClient = clientService.updateClient(id, Client);
        if (updatedClient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedClient);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteClient(@PathVariable int id) {
//        clientService.deleteClient(id);
//        return ResponseEntity.noContent().build();
//    }
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable int id) {
        Optional<Client> client = clientRepo.findById(id);
        
        if (client.isPresent()) {
            // Clear relationships with tasks to prevent cascading delete
            for (Task task : client.get().getTasks()) {
                task.getClients().remove(client.get());
                taskRepo.save(task);
            }
            clientRepo.delete(client.get());
            return ResponseEntity.ok("Client deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found.");
        }
    }

    
}
