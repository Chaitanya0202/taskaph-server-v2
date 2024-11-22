package com.apixelhouse.taskaph.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apixelhouse.taskaph.dto.ImageUp;
import com.apixelhouse.taskaph.dto.User;

public interface ImageRepo extends JpaRepository<ImageUp, Integer>{

	   Optional<ImageUp> findByName(String fileName);
	   List<ImageUp> findByUser(User user); 
}
