package com.example.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.example.webservice.model.Admin;
import com.example.webservice.model.Signalement;


public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query(value="select * from signalement",nativeQuery=true)
    public List<Signalement> findNewSignalement();
       
}
