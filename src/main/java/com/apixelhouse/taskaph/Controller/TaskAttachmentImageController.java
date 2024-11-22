package com.apixelhouse.taskaph.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apixelhouse.taskaph.service.TaskAttachmentImageService;

@RestController
@RequestMapping("/api/AttachmentImage")
public class TaskAttachmentImageController {
	
	@Autowired
	private TaskAttachmentImageService attachmentImageService;

    @PostMapping("/uploadMultiple")
//	@PostMapping(value = "/uploadMultiple/{taskId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadMultipleImages(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("taskId") int taskId){
        try {
            List<String> uploadedFileNames = attachmentImageService.uploadMultipleImages(files, taskId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Files uploaded successfully: " + String.join(", ", uploadedFileNames));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload images: " + e.getMessage());
        }
        
    }

    
    

}
