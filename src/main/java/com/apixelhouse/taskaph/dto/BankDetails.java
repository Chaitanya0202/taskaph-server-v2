package com.apixelhouse.taskaph.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDetails {
	  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String bankName;
    
    private String accountHolder;
    
    private String accountNumber;
    
    private String ifscCode;
    
    private String branch;
    
    @OneToOne
    @JoinColumn(name = "userID", nullable = false)
    @JsonIgnoreProperties("userID")
    private User user;

}
