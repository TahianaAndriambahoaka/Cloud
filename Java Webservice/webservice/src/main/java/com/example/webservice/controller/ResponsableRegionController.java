package com.example.webservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webservice.exception.ResourceNotFoundException;
import com.example.webservice.model.ResponsableRegion;
import com.example.webservice.repository.ResponsableRegionRepository;

@RestController
@RequestMapping("/v1")
public class ResponsableRegionController {
	@Autowired
    private ResponsableRegionRepository responsableRegionRepository;
	
	// all
	@GetMapping("/responsableRegions")
	public List<ResponsableRegion> getAllResponsableRegion() throws Exception
	{
		return responsableRegionRepository.findAll();
	}

	// ResponsableRegion par id
	@GetMapping("/responsableRegion/{id}")
	public ResponseEntity<ResponsableRegion> getResponsableRegionById(@PathVariable(value = "id") long id) throws Exception
	{
		ResponsableRegion responsableRegion = responsableRegionRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Responsable de Région non trouvé" ));
        return ResponseEntity.ok().body(responsableRegion);
	}
}
