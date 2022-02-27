package com.example.webservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webservice.exception.ResourceNotFoundException;
import com.example.webservice.model.Type;
import com.example.webservice.repository.TypeRepository;

@RestController
@RequestMapping("/v1")
public class TypeController 
{
	@Autowired
    private TypeRepository typeRepository;
	
    // all
 	@GetMapping("/types")
 	public List<Type> getAllTypes() throws Exception
 	{
 		return this.typeRepository.findAll();
 	};

    // Type par id
 	@GetMapping("/type/{id}")
 	public Type getTypeById(@PathVariable(value = "id") long idType) throws Exception
 	{
 		Type type = typeRepository.findById(idType).orElseThrow(()->new ResourceNotFoundException("Type not found for this id :: " ));
        return type;
 	};

	// insert
	@PostMapping("/type")
	public Type createType(@RequestBody Type type) throws Exception
	{
		return this.typeRepository.save(type);
	}
	
	// update
	@PutMapping("/type/{id}")
	public ResponseEntity<Type> updateType(@PathVariable(value = "id") Long idType , @Valid @RequestBody Type typeDetails) throws Exception
	{
		Type type = typeRepository.findById(idType).orElseThrow(()->new ResourceNotFoundException("Type not found for this id"));
        type.setNom(typeDetails.getNom());
        return ResponseEntity.ok(this.typeRepository.save(type));
	}
	
	// delete
	@DeleteMapping("/type/{id}")
	public Map<String,Boolean> deleteType(@PathVariable(value = "id") long idType) throws Exception
	{
		Type type = typeRepository.findById(idType).orElseThrow(()->new ResourceNotFoundException("Type not found for this id"));
        this.typeRepository.delete(type);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;
	}
}
