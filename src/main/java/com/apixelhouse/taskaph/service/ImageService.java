package com.apixelhouse.taskaph.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.apixelhouse.taskaph.dto.ImageUp;
import com.apixelhouse.taskaph.dto.User;
import com.apixelhouse.taskaph.repo.ImageRepo;
import com.apixelhouse.taskaph.repo.UserRepo;
import com.apixelhouse.taskaph.utils.ImageUtils;



@Service
public class ImageService {

	
	 @Autowired
	    private ImageRepo imageRepo;
	    
	    @Autowired
	    private UserRepo userRepo;
	    

	    // Upload a single image with user association
	    public String uploadImage(MultipartFile file, int userId) throws IOException {
	        // Fetch the user from the repository
	        User user = userRepo.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

	        // Save the image with user association
	        ImageUp imageData = imageRepo.save(
	                ImageUp.builder()
	                        .name(file.getOriginalFilename())
	                        .type(file.getContentType())
	                        .imageData(ImageUtils.compressImage(file.getBytes()))
	                        .user(user) // Associate user with the image
	                        .build()
	        );
	        return imageData.getName();
	    }

	    // Upload multiple images with user association
	    public List<String> uploadMultipleImages(MultipartFile[] files, int userId) throws IOException {
	        // Fetch the user from the repository
	        User user = userRepo.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

	        List<String> fileNames = new ArrayList<>();
	        for (MultipartFile file : files) {
	            // Save each image with user association
	            ImageUp imageData = imageRepo.save(
	                    ImageUp.builder()
	                            .name(file.getOriginalFilename())
	                            .type(file.getContentType())
	                            .imageData(ImageUtils.compressImage(file.getBytes()))
	                            .user(user)  // Associate user with the image
	                            .build()
	            );
	            fileNames.add(imageData.getName());
	        }
	        return fileNames;
	    }
//	     Download an image by its file name
	    public byte[] downloadImage(String fileName) {
	        Optional<ImageUp> dbImageData = imageRepo.findByName(fileName);
	        if (dbImageData.isPresent()) {
	        	System.out.println("Name is"+dbImageData.get().getName());
	            return ImageUtils.decompressImage(dbImageData.get().getImageData());
	        } else {
	            throw new RuntimeException("Image not found with name: " + fileName);
	        }
	    }
	    
	    
	    public byte[] downloadSignatureImage(int userId) {
	        // Fetch the user along with their images from the repository
	        Optional<User> dbUserData = userRepo.findById(userId);
	        
	        // Check if the user is present
	        if (dbUserData.isPresent()) {
	            // Get the user's images
	            List<ImageUp> images = dbUserData.get().getImages();
	            
	            // Look for the image with the name "signature.png"
	            for (ImageUp image : images) {
	                if ("signature.png".equals(image.getName())) {
	                    // Decompress and return the image data if found
	                    return ImageUtils.decompressImage(image.getImageData());
	                }
	            }
	            // If the signature image is not found, throw an exception
	            throw new RuntimeException("Signature image not found for userId: " + userId);
	        } else {
	            // If no user is found, throw an exception
	            throw new RuntimeException("User not found for userId: " + userId);
	        }
	    }
	    
	    
	    public List<ImageUp> downloadRemainingImages(int userId) {
	        // Fetch the user along with their images from the repository
	        Optional<User> dbUserData = userRepo.findById(userId);
	        
	        // Check if the user is present
	        if (dbUserData.isPresent()) {
	            // Get the user's images
	            List<ImageUp> images = dbUserData.get().getImages();
	            
	            // Filter out the image with the name "signature.png"
	            return images.stream()
	                    .filter(image -> !"signature.png".equals(image.getName()))
	                    .collect(Collectors.toList());
	        } else {
	            // If no user is found, throw an exception
	            throw new RuntimeException("User not found for userId: " + userId);
	        }
	    }

		    // Service method to list all image names
    public List<String> listAllImages() {
        List<ImageUp> images = imageRepo.findAll();
        List<String> imageNames = new ArrayList<>();
        for (ImageUp image : images) {
            imageNames.add(image.getName());
        }
        return imageNames;
    }

    
    public String updateImage(String fileName, MultipartFile newFile, int userId) throws IOException {
        // Fetch the user
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Find the existing image by name
        Optional<ImageUp> existingImage = imageRepo.findByName(fileName);
        if (existingImage.isPresent()) {
            // Delete the old image from the repository
            imageRepo.delete(existingImage.get());

            // Save the new image with the same user
            ImageUp updatedImage = imageRepo.save(
                    ImageUp.builder()
                            .name(newFile.getOriginalFilename())
                            .type(newFile.getContentType())
                            .imageData(ImageUtils.compressImage(newFile.getBytes()))
                            .user(user)  // Associate the same user
                            .build()
            );
            return updatedImage.getName();
        } else {
            throw new RuntimeException("Image not found with name: " + fileName);
        }
    }

    // Delete an image by its file name
    public String deleteImage(String fileName) {
        Optional<ImageUp> dbImageData = imageRepo.findByName(fileName);
        if (dbImageData.isPresent()) {
            imageRepo.delete(dbImageData.get());
            return "Image deleted successfully!";
        } else {
            throw new RuntimeException("Image not found with name: " + fileName);
        }
    }

    // Fetch all images for a specific user by userId
    public List<String> getImagesByUserId(int userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        List<ImageUp> images = imageRepo.findByUser(user);
        List<String> imageNames = new ArrayList<>();
        for (ImageUp image : images) {
            imageNames.add(image.getName());
        }
        return imageNames;
    }



	
	
    
}
