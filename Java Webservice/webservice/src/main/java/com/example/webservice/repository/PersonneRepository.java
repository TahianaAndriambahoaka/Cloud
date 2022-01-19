package com.example.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webservice.model.Personne;
import java.util.Optional;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> 
{
    Optional<Personne> findByUsername(String username);
    Boolean existsByUsername(String username);
 }
