package com.apixelhouse.taskaph.dto;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int clientID;

	private String companyName;
	private String address;
	private String state;
	private String country;
	private String phone;
	private String email;
	private Date dob;
	private List<String> services;
    @Lob
    private byte[] profile;
	
//	   @OneToOne
//	    @JoinColumn(name = "task_id")
//	   @JsonIgnoreProperties("id")
//	   private Task task;
	
    @ManyToMany(mappedBy = "clients")
    @JsonIgnoreProperties("clients")
    private Set<Task> tasks = new HashSet<>();

	
}
