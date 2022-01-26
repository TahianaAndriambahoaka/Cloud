package com.example.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webservice.model.ResponsableRegion;

@Repository
public interface ResponsableRegionRepository extends JpaRepository<ResponsableRegion, Long> { }
