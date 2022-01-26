package com.example.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webservice.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> { }
