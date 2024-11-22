package com.apixelhouse.taskaph.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.apixelhouse.taskaph.dto.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	Optional<User>  findByEmail(String email);
	   List<User> findByEnabled(boolean enabled);
	   List<User> findByAprove(boolean aprove);
	   
	   @Query("SELECT u FROM User u WHERE u.enabled = false AND u.aprove = true")
	    List<User> findDisabledApprovedUsers();
	   
	   @Query("SELECT u FROM User u WHERE u.enabled = true AND u.aprove = true")
	    List<User> findEnabledApprovedUsers();
}
