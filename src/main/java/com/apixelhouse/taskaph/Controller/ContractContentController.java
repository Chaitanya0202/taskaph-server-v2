package com.apixelhouse.taskaph.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apixelhouse.taskaph.dto.ContractContent;
import com.apixelhouse.taskaph.service.ContractContentService;
@RestController
@RequestMapping("/api/contract")
@CrossOrigin(origins = "*")
public class ContractContentController {
	@Autowired
    private ContractContentService service;

    // Save new contract content
    @PostMapping("/save")
    public ResponseEntity<ContractContent> saveContractContent(@RequestBody Map<String, String> payload) {
        String content = payload.get("content");
        ContractContent savedContent = service.saveContent(content);
        return ResponseEntity.ok(savedContent);
    }

    // Get contract content by ID
    @GetMapping("/{id}")
    public ResponseEntity<ContractContent> getContractContent(@PathVariable Integer id) {
        Optional<ContractContent> content = service.getContentById(id);
        return content.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update contract content by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<ContractContent> updateContractContent(@PathVariable Integer id, @RequestBody Map<String, String> payload) {
        String updatedContent = payload.get("content");
        Optional<ContractContent> updated = service.updateContent(id, updatedContent);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete contract content by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContractContent(@PathVariable Integer id) {
        service.deleteContent(id);
        return ResponseEntity.noContent().build();
    }

    // Get all contract contents
    @GetMapping("/all")
    public ResponseEntity<List<ContractContent>> getAllContractContents() {
        List<ContractContent> contents = service.getAllContents();
        return ResponseEntity.ok(contents);
    }
    @GetMapping("/findById")
    public ResponseEntity<List<ContractContent>> findById() {
        List<ContractContent> contents = service.findById();
        return ResponseEntity.ok(contents);
    }

}
