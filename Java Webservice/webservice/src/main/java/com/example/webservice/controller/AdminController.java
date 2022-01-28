package com.example.webservice.controller;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webservice.exception.ResourceNotFoundException;
import com.example.webservice.model.*;
import com.example.webservice.repository.AdminRepository;

@RestController
@RequestMapping("/v1")
public class AdminController 
{
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("helloWorld")
    public String helloWorld()
    {
        return "helloWorld";
    }
    
    // getAll
    @GetMapping("admins")
    public List<Admin> getAllAdmin()
    {
        return this.adminRepository.findAll();
    }

    // get by id
    @GetMapping("/admin/{id}")
    public ResponseEntity<Admin> getAdminId(@PathVariable(value = "id") long adminId) throws ResourceNotFoundException 
    {
        Admin admin = adminRepository.findById(adminId).orElseThrow(()->new ResourceNotFoundException("Employee not found for this id :: " ));
        return ResponseEntity.ok().body(admin);
    }

    @GetMapping("newSignalement")
    public List<Signalement> getNewSignalement(){
        return this.adminRepository.findNewSignalement();
    }
}
