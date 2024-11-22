package com.apixelhouse.taskaph.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class User {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;
    
    private String password;
    
    private String role;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = true)
    private String gender;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zipPostal;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private LocalDate dateOfBirth;
    
    
    @Column(nullable = false)
    private boolean enabled =true;  

    @Column(nullable = false)
    private boolean aprove ;
    
//    
//    @Column(name = "profile_image", columnDefinition = "LONGBLOB",nullable = true)
//    private byte[] profileImage;

    
    
    // One-to-One relationship with EmergencyContact01
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private EmergencyContact01 emergencyContact01;

    // One-to-One relationship with EmergencyContact02
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private EmergencyContact02 emergencyContact02;
    
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private OfficialDetails officialDetails;
    
    
    // One user can have many images
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageUp> images;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private BankDetails bankDetails;
    
    // Mapping With Task
    @ManyToMany(mappedBy = "users",cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonIgnoreProperties("users")
    private Set<Task> tasksAssignedTo = new HashSet<>();

  

}
