package com.apixelhouse.taskaph.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.apixelhouse.taskaph.dto.ImageUp;
import com.apixelhouse.taskaph.dto.Task;
import com.apixelhouse.taskaph.dto.TaskAttachmentImage;
import com.apixelhouse.taskaph.dto.User;
import com.apixelhouse.taskaph.repo.TaskAttachmentImageRepo;
import com.apixelhouse.taskaph.repo.TaskRepo;
import com.apixelhouse.taskaph.utils.ImageUtils;

@Service
public class TaskAttachmentImageService {
	
	@Autowired
	private TaskAttachmentImageRepo imageRepo;
	
	@Autowired
	private TaskRepo taskRepo;
	   // Upload multiple images with user association
	public List<String> uploadMultipleImages(MultipartFile[] files, int taskId) throws IOException {
	    // Fetch the task from the repository
	    Task task = taskRepo.findById(taskId)
	            .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

	    List<String> fileNames = new ArrayList<>();
	    for (MultipartFile file : files) {
	        // Save each image with task association
	        TaskAttachmentImage taskAttachment = imageRepo.save(
	                TaskAttachmentImage.builder()
	                        .name(file.getOriginalFilename())
	                        .type(file.getContentType())
	                        .imageData(ImageUtils.compressImage(file.getBytes()))
	                        .task(task)  // Associate task with the image
	                        .build()
	        );
	        fileNames.add(taskAttachment.getName());
	    }
	    return fileNames;
	}



}
