package com.apixelhouse.taskaph.dto;

import jakarta.persistence.Entity;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
@Entity
@Data
public class EmergencyContact01 {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Column(nullable = false)
    private String firstName;

//    @Column(nullable = false)
    private String lastName;

//    @Column(nullable = false, unique = true)
    private String email;

//    @Column(nullable = true)
    private String phone;

//    @Column(nullable = true)
    private String relationship;

//    @Column(nullable = false)
    private String address;

//    @Column(nullable = false)
    private String city;

//    @Column(nullable = false)
    private String zipPostal;

//    @Column(nullable = false)
    private String state;
    
    
    
    @OneToOne
    @JoinColumn(name = "userID", nullable = false)
    @JsonIgnoreProperties("userID")
    private User user;


}
