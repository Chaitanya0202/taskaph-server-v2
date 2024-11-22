package com.apixelhouse.taskaph.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apixelhouse.taskaph.dto.Client;
import com.apixelhouse.taskaph.dto.Task;
import com.apixelhouse.taskaph.dto.User;
import com.apixelhouse.taskaph.exception.ContactNotFoundException;
import com.apixelhouse.taskaph.repo.ClientRepo;
import com.apixelhouse.taskaph.repo.TaskRepo;

@Service
public class ClientService {
	 @Autowired
	    private ClientRepo clientRepository;
	 
	 @Autowired
	 private TaskRepo taskRepo;

//	    public Client createClient(Client client,int id) {
//	    	Optional<Task> optional= taskRepo.findById(id);
//	    	
//			if (optional.isPresent()) {
//				Task task = optional.get();
//				client.setTask(task);
////				contact.setUser(user); // Link contact with user
//				 return clientRepository.save(client);
//			} else {
//				throw new ContactNotFoundException("User not found with ID: " + id);
//			}
//	        return clientRepository.save(client);
//	    }

	    public Client getClientById(int id) {
	        return clientRepository.findById(id).orElse(null);
	    }

	    public List<Client> getAllClients() {
	        return clientRepository.findAll();
	    }

	    public Client updateClient(int id, Client clientDetails) {
	        return clientRepository.findById(id).map(existingClient -> {
	            existingClient.setCompanyName(clientDetails.getCompanyName());
	            existingClient.setAddress(clientDetails.getAddress());
	            existingClient.setState(clientDetails.getState());
	            existingClient.setCountry(clientDetails.getCountry());
	            existingClient.setPhone(clientDetails.getPhone());
	            existingClient.setEmail(clientDetails.getEmail());
	            existingClient.setDob(clientDetails.getDob());
	            existingClient.setServices(clientDetails.getServices());
	            return clientRepository.save(existingClient);
	        }).orElse(null);
	    }

	    public void deleteClient(int id) {
	        clientRepository.deleteById(id);
	    }

//		public Client saveClient(Client client) {
//			Client client2= clientRepository.save(client);
//			return client2;
//		}


	    public Client saveClient(Client client) {
	        return clientRepository.save(client);
	    }
}

