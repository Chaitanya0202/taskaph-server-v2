package com.apixelhouse.taskaph.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apixelhouse.taskaph.dto.Task;

public interface TaskRepo extends JpaRepository<Task, Integer>{

//	
//	// Find tasks by department
//    List<Task> findByDepartment(String department);
//
//    // Find tasks by company name
//    List<Task> findByCompanyName(String companyName);
//
//    // Find tasks by priority
//    List<Task> findByPriority(String priority);
//
//    // Find tasks by due date
//    List<Task> findByDueDate(LocalDate dueDate);
//
//    // Custom query to find tasks by a combination of fields
//    @Query("SELECT t FROM Task t WHERE (:department IS NULL OR t.department = :department) AND "
//           + "(:companyName IS NULL OR t.companyName = :companyName) AND "
//           + "(:priority IS NULL OR t.priority = :priority) AND "
//           + "(:dueDate IS NULL OR t.dueDate = :dueDate)")
//    List<Task> filterTasks(@Param("department") String department,
//                           @Param("companyName") String companyName,
//                           @Param("priority") String priority,
//                           @Param("dueDate") LocalDate dueDate);
}
