package com.example.webservice.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.webservice.exception.ResourceNotFoundException;
import com.example.webservice.model.Photo;
import com.example.webservice.repository.PhotoRepository;



@RestController
@RequestMapping("/v1")
public class PhotoController {

	private String uploadLocation = "Java Webservice/upload";

	// @GetMapping(value = "/signalement/photo/{idSignalement}")
	// public ResponseEntity<?> getCheminImage(@PathVariable(value = "idSignalement") long idSignalement) throws IOException {
	// 	List<Photo> lp = photoRepository.findPhotoByIdSignalement(idSignalement);
	// 	if (lp.isEmpty()) {
	// 		return ResponseEntity.ok().body(null);
	// 	} else {
	// 		Photo p = lp.get(0);
	// 		String imageName = p.getPhoto();
	// 		var path = Paths.get(uploadLocation + "/" + imageName);
	// 		// File file = path.toFile();
	// 		// byte[] fileData = new byte[(int) file.length()];
	// 		// byte[] encoded = Base64.getEncoder().encode(fileData);
	// 		// FileInputStream in = new FileInputStream(file);
	// 		// in.read(fileData);
	// 		// in.close();
	// 		// String hex = DatatypeConverter.printHexBinary(fileData);
	// 		// System.out.println(hex);
	// 		return ResponseEntity.ok().body(imageName);
	// 	}
	// }

	@Autowired
	private PhotoRepository photoRepository;

	// all
	@GetMapping("/photos")
	public List<Photo> getAllPhoto() throws Exception {
		return photoRepository.findAll();
	}

	// get photo by id
	@GetMapping("/photo/{id}")
	public ResponseEntity<Photo> getPhotoById(@PathVariable(value = "id") long idPhoto) throws Exception {
		Photo photo = photoRepository.findById(idPhoto)
				.orElseThrow(() -> new ResourceNotFoundException("Photo not found for this id :: "));
		return ResponseEntity.ok().body(photo);
	}

	// update
	@PutMapping("/photo/{id}")
	public ResponseEntity<Photo> updatePhoto(@PathVariable(value = "id") Long idPhoto,
			@Valid @RequestBody Photo photoDetails) throws Exception {
		Photo photo = photoRepository.findById(idPhoto)
				.orElseThrow(() -> new ResourceNotFoundException("Photo not found for this id"));
		photo.setIdSignalement(photoDetails.getIdSignalement());
		photo.setPhoto(photoDetails.getPhoto());
		return ResponseEntity.ok(this.photoRepository.save(photo));
	}

	// delete
	@DeleteMapping("/photo/{id}")
	public Map<String, Boolean> deletePhoto(@PathVariable(value = "id") long idPhoto) throws Exception {
		Photo photo = photoRepository.findById(idPhoto)
				.orElseThrow(() -> new ResourceNotFoundException("Photo not found for this id"));
		this.photoRepository.delete(photo);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// insert
	@PostMapping("/photo")
	public Photo createPhoto(@RequestBody Photo photo) throws Exception {
		return this.photoRepository.save(photo);
	}

	public PhotoController() throws IOException {
		var uploadPath = Paths.get(uploadLocation);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public Map<String, String> upload(@RequestPart MultipartFile file) {
		var filename = UUID.randomUUID().toString() + file.getOriginalFilename();
		var dest = Paths.get(uploadLocation + "/" + filename);
		try {
			Files.copy(file.getInputStream(), dest);
		} catch (IOException e) {
			e.printStackTrace();
			Map<String, String> response = new HashMap<>();
			response.put("error", "Error");
			return response;
		}
		Map<String, String> response = new HashMap<>();
		response.put("filename", filename);
		return response;
	}
}
