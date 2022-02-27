package com.example.webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.webservice.exception.ResourceNotFoundException;
import com.example.webservice.model.Utilisateur;
import com.example.webservice.repository.UtilisateurRepository;

@RestController
@RequestMapping("/v1")
public class UtilisateurController {
	@Autowired
    private UtilisateurRepository utilisateurRepository;
	
	// Utilisateur par id
	@GetMapping("/utilisateur/{id}")
	public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable(value = "id") long id) throws Exception
	{
		Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Utilisateur non trouvé" ));
        return ResponseEntity.ok().body(utilisateur);
	}
	
	// Utilisateur par idPersonne
	@GetMapping("/utilisateur")
	public Utilisateur getUtilisateurByIdPersonne(@RequestParam(value = "idPersonne") long idPersonne) throws Exception
	{
		Utilisateur utilisateur = utilisateurRepository.findByIdPersonne(idPersonne);
        return utilisateur;
	}
}
