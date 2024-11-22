package com.apixelhouse.taskaph.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apixelhouse.taskaph.dto.Client;

public interface ClientRepo extends JpaRepository<Client, Integer>{

}
