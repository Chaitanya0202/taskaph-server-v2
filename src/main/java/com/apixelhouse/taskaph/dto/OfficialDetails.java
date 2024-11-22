package com.apixelhouse.taskaph.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class OfficialDetails {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    @OneToOne
	    @JoinColumn(name = "userID", nullable = false)
	    @JsonIgnoreProperties("userID")
	    private User user;

	    private String officeBranchLocation;
	    
	    private String designation;

	    private String employmentType;

	    private String joiningDate;
	    
	    private String phoneNumber;

	    private String currentSalary;
}
