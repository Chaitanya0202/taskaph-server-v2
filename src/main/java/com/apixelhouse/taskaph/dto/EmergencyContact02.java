package com.apixelhouse.taskaph.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyContact02 {
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    @OneToOne
	    @JoinColumn(name = "userID", nullable = false)
	    @JsonIgnoreProperties("userID")
	    private User user;

//	    @Column(nullable = false)
	    private String firstName;

//	    @Column(nullable = false)
	    private String lastName;

//	    @Column(nullable = false, unique = true)
	    private String email;

//	    @Column(nullable = true)
	    private String phone;

//	    @Column(nullable = true)
	    private String relationship;

//	    @Column(nullable = false)
	    private String address;

//	    @Column(nullable = false)
	    private String city;

//	    @Column(nullable = false)
	    private String zipPostal;

//	    @Column(nullable = false)
	    private String state;
	
	    
	    

}
