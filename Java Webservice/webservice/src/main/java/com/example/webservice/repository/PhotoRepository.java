package com.example.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webservice.model.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
