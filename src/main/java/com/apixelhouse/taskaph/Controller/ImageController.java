package com.apixelhouse.taskaph.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apixelhouse.taskaph.dto.ImageUp;
import com.apixelhouse.taskaph.service.ImageService;



@RestController
@RequestMapping("/api/images")
@CrossOrigin("http://localhost:5173/")
public class ImageController {

	
	@Autowired
    private ImageService imageService;


    // API to download an image by file name
    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData = imageService.downloadImage(fileName);
        if (imageData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Image not found with name: " + fileName);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(imageData);
    }
	
	
    @GetMapping("/downloadSign/{userId}")
    public ResponseEntity<?> downloadImage(@PathVariable int userId ) {
        byte[] imageData = imageService.downloadSignatureImage(userId);
        if (imageData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Image not found with name: " );
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(imageData);
    }
    
    @GetMapping("/remainingImages/{userId}")
    public ResponseEntity<List<ImageUp>> getRemainingImages(@PathVariable int userId) {
        List<ImageUp> remainingImages = imageService.downloadRemainingImages(userId);
        return ResponseEntity.ok(remainingImages);
    }

    // API to upload multiple images
    @PostMapping("/upload/{userId}")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file, 
                                              @PathVariable("userId") int userId) {
        try {
            String uploadImage = imageService.uploadImage(file, userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("File uploaded successfully: " + uploadImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        }
        
    }

    @PostMapping(value = "/uploadMultiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadMultipleImages(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("userId") int userId) { // Use @RequestParam for userId
        try {
            List<String> uploadedFileNames = imageService.uploadMultipleImages(files, userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Files uploaded successfully: " + String.join(", ", uploadedFileNames));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload images: " + e.getMessage());
        }
    }

    

//    // API to list all uploaded image names
    @GetMapping("/list")
    public ResponseEntity<List<String>> listAllImages() {
        List<String> imageNames = imageService.listAllImages();
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageNames);
    }
    
    
    @PostMapping("/update/{fileName}/{userId}")
    public ResponseEntity<String> updateImage(
            @PathVariable("fileName") String fileName,
            @RequestParam("image") MultipartFile newFile,
            @PathVariable("userId") int userId) {
        try {
            String updatedImage = imageService.updateImage(fileName, newFile, userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Image updated successfully: " + updatedImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update image: " + e.getMessage());
        }
    }

    // API to delete an image by its name
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteImage(@PathVariable("fileName") String fileName) {
        try {
            String message = imageService.deleteImage(fileName);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete image: " + e.getMessage());
        }
    }

    // API to get all images for a specific user by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<String>> getImagesByUserId(@PathVariable("userId") int userId) {
        try {
            List<String> imageNames = imageService.getImagesByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(imageNames);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
