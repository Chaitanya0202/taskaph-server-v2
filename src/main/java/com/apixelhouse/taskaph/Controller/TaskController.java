package com.apixelhouse.taskaph.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.apixelhouse.taskaph.dto.Task;
import com.apixelhouse.taskaph.dto.TaskRequest;
import com.apixelhouse.taskaph.dto.User;
import com.apixelhouse.taskaph.repo.TaskRepo;
import com.apixelhouse.taskaph.service.TaskService;
import com.apixelhouse.taskaph.service.UserService;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("*")
public class TaskController {
	@Autowired
	private TaskService taskService;
	@Autowired
	private UserService userService;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private TaskRepo taskRepo;
//
//	@PostMapping("/create")
//	public ResponseEntity<Task> createTask(@RequestBody TaskRequest taskRequest,@RequestParam("audioFile") MultipartFile audioFile) {
//		Task createdTask = taskService.createTask(taskRequest,audioFile);
//		return ResponseEntity.ok(createdTask);
//	}

	///////// api for Store Task With audio
	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Task> createTask(@RequestPart("taskRequest") String taskRequestJson,
			@RequestPart(value = "audioFile", required = false) MultipartFile audioFile) {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule for LocalDateTime

		TaskRequest taskRequest;
		try {
			taskRequest = objectMapper.readValue(taskRequestJson, TaskRequest.class);
		} catch (IOException e) {
			throw new RuntimeException("Error parsing taskRequest JSON: " + e.getMessage(), e);
		}

		Task createdTask = taskService.createTask(taskRequest, audioFile);

		// Fetch and send email notifications to users
		sendEmailNotifications(taskRequest.getUserIds());

		return ResponseEntity.ok(createdTask);
	}

	private void sendEmailNotifications(Set<Integer> userIds) {

		for (int userId : userIds) {
			// Fetch user details to get the email
			String email = fetchUserEmail(userId);
			if (email != null) {
				sendSimpleMessage(email, "New Task Assigned",
						"You have been assigned a new task. Please check your tasks for details.");
			}
		}

	}
	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	private String fetchUserEmail(int userId) {
		// Call the user API to get user details
		User user = userService.getUserById(userId);
		String email = user.getEmail();
		return email;

	}
	// download audio
	@GetMapping("/download-audio/{taskId}")
	public ResponseEntity<byte[]> downloadAudioFile(@PathVariable Integer taskId) {
		Task task = taskRepo.findById(taskId).get();

		if (task == null || task.getAudioFile() == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		byte[] audioData = task.getAudioFile();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // or set appropriate audio type
		headers.setContentDispositionFormData("attachment", "audioFile.mp3"); // This triggers download as a file

		return new ResponseEntity<>(audioData, headers, HttpStatus.OK);
	}

	// play Audio
	@GetMapping("/play-audio/{taskId}")
	public ResponseEntity<byte[]> playAudioFile(@PathVariable Integer taskId) {
		Task task = taskRepo.findById(taskId).get();

		if (task == null || task.getAudioFile() == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		byte[] audioData = task.getAudioFile();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("audio/mpeg")); // Set appropriate audio type, like "audio/mp3"
		headers.setContentLength(audioData.length);

		return new ResponseEntity<>(audioData, headers, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Task>> getAllTasks() {
		List<Task> tasks = taskService.getAllTasks();
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<Task> getById(@PathVariable int id) {
		Task task = taskService.getById(id);
		return ResponseEntity.ok(task);
	}

	@PutMapping("/{taskId}")
	public ResponseEntity<Task> updateTask(@PathVariable int taskId, @RequestBody TaskRequest taskRequest) {
		try {
			Task updatedTask = taskService.updateTask(taskId, taskRequest);
			return ResponseEntity.ok(updatedTask);
		} catch (Exception e) {
			return ResponseEntity.status(404).body(null); // Handle error if task is not found
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable int id) {
		taskService.deleteTask(id);
		return ResponseEntity.ok("Task deleted successfully.");
	}

}