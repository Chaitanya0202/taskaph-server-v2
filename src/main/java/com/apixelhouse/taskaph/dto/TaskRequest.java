package com.apixelhouse.taskaph.dto;

import java.time.LocalDateTime;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {

	 private String title;
	    private String description;
	    private String status;
//	    private LocalDateTime assignedAt;
//	    private LocalDateTime startedAt;
//	    private LocalDateTime completedAt;
	    
	    private LocalDateTime dueDate;
//	    private String companyName;
	    private String departmentName;
	    private String priority;
	    private Set<Integer> userIds;
	    private Set<Integer> clientIds;
}

