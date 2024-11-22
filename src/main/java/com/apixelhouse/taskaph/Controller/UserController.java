package com.apixelhouse.taskaph.Controller;

import com.apixelhouse.taskaph.dto.SignInRequest;
import com.apixelhouse.taskaph.dto.User;
import com.apixelhouse.taskaph.repo.UserRepo;
import com.apixelhouse.taskaph.service.UserService;
import com.apixelhouse.taskaph.utils.JwtUtils;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtUtils jwtUtil;

    @PostMapping("/saveUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }
    // New endpoint for paginated list of users
    
    @GetMapping("/paginated")
    public ResponseEntity<Page<User>> getAllUsersPaginated(
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<User> usersPage = userService.getAllUsersPaginated(pageIndex, pageSize);
        return ResponseEntity.ok(usersPage);
    }
    
    /// Enabled Or Disabled API`s
    @GetMapping("/filterEnabled/{enabled}")
    public ResponseEntity<List<User>> filterUsersByStatus(@PathVariable boolean enabled) {
        List<User> users = userService.getUsersByStatus(enabled);
        return ResponseEntity.ok(users);
    }
    
    
    @PutMapping("filterEnabled/{userID}/{enabled}")
    public ResponseEntity<String> updateEnabledStatus(
            @PathVariable int userID,
            @PathVariable boolean enabled) {
        userService.updateEnabledStatus(userID, enabled);
        return ResponseEntity.ok("User enabled status updated successfully");
    }
        
    // aproved Api`s
    
    @PutMapping("filterAproved/{userID}/{aproved}")
    public ResponseEntity<String> updateAprovedStatus(
            @PathVariable int userID,
            @PathVariable boolean aproved) {
        userService.updateAprovedStatus(userID, aproved);
        return ResponseEntity.ok("User aproved status updated successfully");
    }
    
    
    @GetMapping("/filterAprove/{aprove}")
    public ResponseEntity<List<User>> filterUsersByAprove(@PathVariable boolean aprove) {
        List<User> users = userService.getUsersByAprove(aprove);
        return ResponseEntity.ok(users);
    }
    
    //disabled but aproved
    @GetMapping("/filter/disabled-approved")
    public List<User> getDisabledApprovedUsers() {
        return userService.getDisabledApprovedUsers();
    }
    
    //active - enabled with Aproved
    @GetMapping("/filter/enabled-approved")
    public List<User> findEnabledApprovedUsers() {
        return userService.findEnabledApprovedUsers();
    }    
    
    //sign In Api`s
//    //Returing Object
//    @PostMapping("/sign-in")
//    public ResponseEntity<User> signIn(@RequestBody SignInRequest signInRequest) {
//        User isAuthenticated = userService.authenticate(signInRequest.getEmail(), signInRequest.getPassword());
//        if (isAuthenticated != null) {
//            return ResponseEntity.ok(isAuthenticated);
//        } else {
//            return null;
//        }
//    }
//    @PostMapping("/signin")
//    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
//        Optional<User> userOptional = userRepo.findByEmail(request.getEmail());
//
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            if (user.getPassword().equals(request.getPassword())) {
//            	System.out.println("user"+user);
//                String token = jwtUtil.generateToken(user.getEmail());
//                System.out.println("TOken is"+token);
//                return ResponseEntity.ok(Collections.singletonMap("token", token));
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//    }
//    
//    @PostMapping("/signIn")
//    public ResponseEntity<String> signIn(@RequestBody SignInRequest inRequest) {
//        Optional<User> optionalUser = userRepo.findByEmail(inRequest.getEmail());
//
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//
//            // Here you should ideally use a password encoder to compare hashed passwords
//            if (user.getPassword().equals(inRequest.getPassword())) {
//                // You may want to generate a JWT token here
//                // String token = jwtUtil.generateToken(user.getEmail());
//
//                return ResponseEntity.ok("Success");
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//    }
//sign In With JWT
    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest inRequest) {
        Optional<User> optionalUser = userRepo.findByEmail(inRequest.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Here you should ideally use a password encoder to compare hashed passwords
            if (user.getPassword().equals(inRequest.getPassword())) {
                // Generate a JWT token
                String token = jwtUtil.generateToken(user.getEmail());

                return ResponseEntity.ok(token); // Return the token as a response
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    
    // role Update
    @PutMapping("updateRoleAndPassword/{userID}/{role}/{password}")
    public ResponseEntity<String> updateRole(
            @PathVariable int userID,
            @PathVariable  String role,
            @PathVariable String password) {
        userService.updateRolePassword(userID, role,password);
        return ResponseEntity.ok("User Role And Password  updated successfully");
    }
    
}


