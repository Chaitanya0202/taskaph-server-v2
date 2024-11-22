package com.apixelhouse.taskaph.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.apixelhouse.taskaph.dto.Client;
import com.apixelhouse.taskaph.dto.Task;
import com.apixelhouse.taskaph.dto.TaskRequest;
import com.apixelhouse.taskaph.dto.User;
import com.apixelhouse.taskaph.exception.UserNotFoundException;
import com.apixelhouse.taskaph.repo.ClientRepo;
import com.apixelhouse.taskaph.repo.TaskRepo;
import com.apixelhouse.taskaph.repo.UserRepo;

@Service
public class TaskService {
//	
	@Autowired
	private TaskRepo taskRepository;

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ClientRepo clientRepo;

//	 public Task createTask(TaskRequest taskRequest , MultipartFile audioFile) {
//		    Task task = new Task();
//		    task.setTitle(taskRequest.getTitle());
//		    task.setDescription(taskRequest.getDescription());
//		    task.setStatus(taskRequest.getStatus());
//		    task.setDueDate(taskRequest.getDueDate());
////		    task.setCompanyName(taskRequest.getCompanyName());		    
//		    task.setDepartmentName(taskRequest.getDepartmentName());		    
//		    task.setPriority(taskRequest.getPriority());
////
//		    try {
//	            if (audioFile != null && !audioFile.isEmpty()) {
//	                byte[] audioBytes = audioFile.getBytes();
//	                task.setAudioFile(audioBytes);
//	            }
//	        } catch (Exception e) {
//	            throw new RuntimeException("Error saving audio file", e);
//	        }
//
//		    
//		    
//		    
//		    Set<Client>clients =new HashSet<Client>(clientRepo.findAllById(taskRequest.getClientIds()));
////		  
//		    // Fetch the users based on userIds and set them in the task
//		    Set<User> users = new HashSet<>(userRepo.findAllById(taskRequest.getUserIds()));
//
//		    if (users.isEmpty()) {
//		        throw new RuntimeException("No users found for the given IDs");
//		    }
//		    if (clients.isEmpty()) {
//		        throw new RuntimeException("No Clients found for the given IDs");
//		    }
//		    task.setClients(clients);		   
//		    task.setUsers(users);
//
//		    // Save the task
//		    return taskRepository.save(task);
//		}
//	 
	public Task createTask(TaskRequest taskRequest, MultipartFile audioFile) {
		Task task = new Task();
		task.setTitle(taskRequest.getTitle());
		task.setDescription(taskRequest.getDescription());
		task.setStatus(taskRequest.getStatus());
		task.setDueDate(taskRequest.getDueDate());
		task.setDepartmentName(taskRequest.getDepartmentName());
		task.setPriority(taskRequest.getPriority());

		// Handle audio file
		if (audioFile != null && !audioFile.isEmpty()) {
            try {
                byte[] audioBytes = audioFile.getBytes();
                task.setAudioFile(audioBytes);
            } catch (IOException e) {
                throw new RuntimeException("Error saving audio file", e);
            }
        }

		
		// Fetch and set clients and users
		Set<Client> clients = new HashSet<>(clientRepo.findAllById(taskRequest.getClientIds()));
		Set<User> users = new HashSet<>(userRepo.findAllById(taskRequest.getUserIds()));

		if (clients.isEmpty()) {
			throw new RuntimeException("No clients found for the given IDs");
		}
		if (users.isEmpty()) {
			throw new RuntimeException("No users found for the given IDs");
		}

		task.setClients(clients);
		task.setUsers(users);

		return taskRepository.save(task);
	}

	public Task getById(int id) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Task Not Found with id" + id));
		return task;
	}

	public List<Task> getAllTasks() {
		List<Task> tasks = taskRepository.findAll();
		return tasks;
	}

	public Task updateTask(TaskRequest request, int id) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Task Not Found WIth Id " + id));

		task.setId(id);

		return null;
	}

	public Task updateTask(Integer taskId, TaskRequest taskRequest) throws Exception {
		Optional<Task> taskOptional = taskRepository.findById(taskId);
		if (taskOptional.isEmpty()) {
			throw new Exception("Task not found with ID: " + taskId);
		}
		Task task = taskOptional.get();
		task.setTitle(taskRequest.getTitle());
		task.setDescription(taskRequest.getDescription());
		task.setStatus(taskRequest.getStatus());

		task.setDueDate(taskRequest.getDueDate());
//	        task.setCompanyName(taskRequest.getCompanyName());
		task.setDepartmentName(taskRequest.getDepartmentName());
		task.setPriority(taskRequest.getPriority());
		List<Client> clientList = clientRepo.findAllById(taskRequest.getClientIds());
		Set<Client> clientSet = new HashSet<Client>(clientList);
		task.setClients(clientSet);

		List<User> userList = userRepo.findAllById(taskRequest.getUserIds());
		Set<User> userSet = new HashSet<>(userList); // Convert List<User> to Set<User>
		task.setUsers(userSet);

		return taskRepository.save(task);
	}

	public void deleteTask(int id) {
		// Find task by id, throw exception if not found
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Task not found with ID " + id));

		// Perform the delete operation
		taskRepository.deleteById(id);
	}

}
