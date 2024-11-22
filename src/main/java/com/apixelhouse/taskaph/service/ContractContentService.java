package com.apixelhouse.taskaph.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apixelhouse.taskaph.dto.ContractContent;
import com.apixelhouse.taskaph.repo.ContractContentRepository;

@Service
public class ContractContentService {
	@Autowired
    private ContractContentRepository repository;

    // Save new content
    public ContractContent saveContent(String content) {
        ContractContent contractContent = new ContractContent();
        contractContent.setContent(content);
        return repository.save(contractContent);
    }

    // Update existing content by ID
    public Optional<ContractContent> updateContent(Integer id, String newContent) {
        Optional<ContractContent> existingContent = repository.findById(id);
        if (existingContent.isPresent()) {
            ContractContent content = existingContent.get();
            content.setContent(newContent);
            return Optional.of(repository.save(content));
        }
        return Optional.empty();
    }

    // Get content by ID
    public Optional<ContractContent> getContentById(Integer id) {
        return repository.findById(id);
    }

    // Get all contract contents
    public List<ContractContent> getAllContents() {
        return repository.findAll();
    }

    // Delete content by ID
    public void deleteContent(Integer id) {
        repository.deleteById(id);
    }

	public List<ContractContent> findById() {
		// TODO Auto-generated method stub
		return null;
	}

}
