package com.apixelhouse.taskaph.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
public class Task {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer id;

    private String title;
    private String description;
    private String status;
//    private LocalDateTime assignedAt;
//    private LocalDateTime startedAt;
//    private LocalDateTime completedAt;
    private LocalDateTime dueDate; 
    private String departmentName;
    private String priority;
    
    @Lob
    private byte[] audioFile; 

//
//    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL)
//    @JsonIgnoreProperties("task")
//    private Client client;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "task_client",  // Join table for Task-Client relationship
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    @JsonIgnoreProperties("tasks")
    private Set<Client> clients = new HashSet<>();
    
    
    @ManyToMany
    @JoinTable(
        name = "task_user",  // Join table name
        joinColumns = @JoinColumn(name = "task_id"),  // Foreign key column for Task
        inverseJoinColumns = @JoinColumn(name = "assignedTo_id", referencedColumnName = "userID", table = "\"user\"",nullable = true)  // Escaped foreign key column for User
    )
    @JsonIgnoreProperties("tasksAssignedTo")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("task")
    private Set<TaskAttachmentImage> taskAttachments = new HashSet<>();
    
}
