package com.apixelhouse.taskaph.service;

import com.apixelhouse.taskaph.dto.Task;
import com.apixelhouse.taskaph.dto.User;
import com.apixelhouse.taskaph.exception.UserNotFoundException;
import com.apixelhouse.taskaph.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepository;

	public User createUser(User user) {
		try {
			return userRepository.save(user);
		} catch (Exception e) {
			throw new RuntimeException("Error while saving user: " + e.getMessage());
		}
	}

	public User getUserById(int id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User Not Found With ID: " + id));
	}

	public List<User> getAllUsers() {
		try {
			return userRepository.findAll();
		} catch (Exception e) {
			throw new RuntimeException("Error while fetching all users: " + e.getMessage());
		}
	}

	public User updateUser(int id, User userDetails) {
		try {
			User user = getUserById(id);
			user.setFirstName(userDetails.getFirstName());
			user.setLastName(userDetails.getLastName());
			user.setEmail(userDetails.getEmail());
			user.setPhone(userDetails.getPhone());
			user.setGender(userDetails.getGender());
			user.setAddress(userDetails.getAddress());
			user.setCity(userDetails.getCity());
			user.setZipPostal(userDetails.getZipPostal());
			user.setState(userDetails.getState());
			user.setDateOfBirth(userDetails.getDateOfBirth());

			/// isEnabled Used Instead Of Enabled
			user.setEnabled(userDetails.isEnabled());
			user.setPassword(userDetails.getPassword());
			user.setRole(userDetails.getRole());

			return userRepository.save(user);
		} catch (Exception e) {
			throw new RuntimeException("Error while updating user: " + e.getMessage());
		}
	}

	public void deleteUser(int id) {
//		try {
//			User user = getUserById(id);
//			userRepository.delete(user);
//		} catch (Exception e) {
//			throw new RuntimeException("Error while deleting user: " + e.getMessage());
//		}
		User user = userRepository.findById(id)
		        .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

		    // Remove the user from all associated tasks
		    for (Task task : user.getTasksAssignedTo()) {
		        task.getUsers().remove(user);
		    }

		    // Now delete the user
		    userRepository.delete(user);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User Not Found With Email: " + email));
	}

	// pagignation
	public Page<User> getAllUsersPaginated(int pageIndex, int pageSize) {
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		return userRepository.findAll(pageable);
	}

	public List<User> getUsersByStatus(boolean enabled) {
		return userRepository.findByEnabled(enabled);
	}

	public void updateEnabledStatus(int userID, boolean enabled) {
		User user = userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException("User not found"));
		user.setEnabled(enabled);
		userRepository.save(user); // This will update only the changed fields
	}

	public List<User> getUsersByAprove(boolean aprove) {
		// TODO Auto-generated method stub
		return userRepository.findByAprove(aprove);
	}

	public void updateAprovedStatus(int userID, boolean aproved) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException("User not found"));

		user.setAprove(aproved);
		userRepository.save(user);
	}

	// disabled but aproved user
	public List<User> getDisabledApprovedUsers() {
		return userRepository.findDisabledApprovedUsers();
	}

	public List<User> findEnabledApprovedUsers() {
		return userRepository.findEnabledApprovedUsers();
	}

	public User authenticate(String username, String password) {
		Optional<User> userOptional = userRepository.findByEmail(username);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (user.getPassword().equals(password)) {
				return user;
			}
//	            return user.getPassword().equals(password); // Simple password check
		}
		return null;
	}

	public void assignTaskToUser(Integer userId, Integer id) {
		// TODO Auto-generated method stub
		
	}

	public void updateRolePassword(int userID, String role ,String password) {
		User user = userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException("User not found"));
		
		user.setRole(role);
		user.setPassword(password);
		userRepository.save(user);
	}
}
