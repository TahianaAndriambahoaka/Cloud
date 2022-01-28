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
import com.example.webservice.model.Region;
import com.example.webservice.repository.RegionRepository;

@RestController
@RequestMapping("/v1")
public class RegionController {
	@Autowired
    private RegionRepository regionRepository;

	// all
	@GetMapping("/regions")
	public List<Region> getAllRegion() throws Exception
	{
		return regionRepository.findAll();
	}
	
	// get region by id
	@GetMapping("/region/{id}")
	public ResponseEntity<Region> getRegionById(@PathVariable(value = "id") long idRegion) throws Exception
	{
		Region region = regionRepository.findById(idRegion).orElseThrow(()->new ResourceNotFoundException("Region not found for this id :: " ));
        return ResponseEntity.ok().body(region);
	}
	
	// insert
	@PostMapping("/region")
	public Region createRegion(@RequestBody Region region) throws Exception
	{
		return this.regionRepository.save(region);
	}
	
	// update
	@PutMapping("/region/{id}")
	public ResponseEntity<Region> updateRegion(@PathVariable(value = "id") Long idRegion , @Valid @RequestBody Region regionDetails) throws Exception
	{
		Region region = regionRepository.findById(idRegion).orElseThrow(()->new ResourceNotFoundException("Region not found for this id"));
        region.setNom(regionDetails.getNom());
        return ResponseEntity.ok(this.regionRepository.save(region));
	}
	
	// delete
	@DeleteMapping("/region/{id}")
	public Map<String,Boolean> deleteRegion(@PathVariable(value = "id") long idRegion) throws Exception
	{
		Region region = regionRepository.findById(idRegion).orElseThrow(()->new ResourceNotFoundException("Region not found for this id"));
        this.regionRepository.delete(region);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;
	}
}
