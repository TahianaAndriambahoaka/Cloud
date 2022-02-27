package com.example.webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.webservice.exception.ResourceNotFoundException;
import com.example.webservice.model.Personne;
import com.example.webservice.repository.PersonneRepository;

@RestController
@RequestMapping("/v1")
public class PersonneController 
{
	@Autowired
    private PersonneRepository personneRepository;
	
	// Utilisateur par id
	@GetMapping("/personne/{id}")
	public ResponseEntity<Personne> getPersonneById(@PathVariable(value = "id") long id) throws Exception
	{
		Personne personne = personneRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Personne non trouvé" ));
        return ResponseEntity.ok().body(personne);
	}

	@GetMapping("/personneEmail")
	public ResponseEntity<Personne> getPersonneByEmail(@RequestParam(name = "email") String email) throws Exception
	{
		Personne personne = personneRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("Personne non trouvé" ));
        return ResponseEntity.ok().body(personne);
	}
}
