package com.example.webservice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.webservice.exception.ResourceNotFoundException;
import com.example.webservice.model.Signalement;
import com.example.webservice.repository.SignalementRepository;

@RestController
@RequestMapping("/v1")
public class SignalementController 
{

	@Autowired
	private SignalementRepository signalementRepository;

	private String uploadLocation = "Backoffice/storage";

	public SignalementController() throws IOException
	{
		var uploadPath = Paths.get(uploadLocation);
        if(!Files.exists(uploadPath)) { Files.createDirectories(uploadPath); }
	}

	// Recherche avancée
	@GetMapping("/rechercherSignalement/{idRegion}/{idType}/{status}")
	public List<Signalement> rechercherSignalement(@PathVariable(value = "idRegion") Integer idRegion,@PathVariable(value = "idType") Long idType, @PathVariable(value = "status") String status) throws Exception 
	{
		return this.signalementRepository.rechercherSignalement(idRegion, idType, status);
	}

	// Notification
	@GetMapping("/listNotification")
	public List<List<Object>> getListNotification() throws Exception 
	{
		return this.signalementRepository.getListNotification();
	}

	// Liste des nouveaux signalements non affectés
	@GetMapping("/listNewSignalement")
	public List<List<Object>> getListNewSignalement() throws Exception 
	{
		return this.signalementRepository.findSignalementNotAffected();
	}

	// Liste des signalements affectés
	@GetMapping("/listAffectedSignalement")
	public List<List<Object>> getListAffectedSignalement() throws Exception 
	{
		return this.signalementRepository.findAffectedSignalement();
	}

	@GetMapping("/listByResponsable/{idPersonne}")
	public List<Signalement> getListByResponsable(@PathVariable(value = "idPersonne") Long idPersonne) throws Exception 
	{
		return this.signalementRepository.getListByResponsable(idPersonne);
	}

	@GetMapping("/listByResponsableStatus/{idPersonne}/{status}")
	public List<Signalement> getListByResponsableStatus(@PathVariable(value = "idPersonne") Long idPersonne,@PathVariable(value = "status") String status) throws Exception 
	{
		return this.signalementRepository.getListByResponsableStatus(idPersonne,status);
	}

	@GetMapping("/listByResponsableDatemax/{idPersonne}/{datemax}")
	public List<Signalement> getListByResponsableDatemax(@PathVariable(value = "idPersonne") Long idPersonne,@PathVariable(value = "datemax") String datemax) throws Exception 
	{
		return this.signalementRepository.getListByResponsableDatemax(idPersonne,new SimpleDateFormat("yyyy-MM-dd").parse(datemax));
	}

	@GetMapping("/listByResponsableDatemaxStatus/{idPersonne}/{datemax}/{status}")
	public List<Signalement> getListByResponsableDatemaxStatus(@PathVariable(value = "idPersonne") Long idPersonne,@PathVariable(value = "datemax") String datemax,@PathVariable(value = "status") String status) throws Exception 
	{
		return this.signalementRepository.getListByResponsableDatemaxStatus(idPersonne,new SimpleDateFormat("yyyy-MM-dd").parse(datemax),status);
	}

	@GetMapping("/listByResponsableDateminStatus/{idPersonne}/{datemin}/{status}")
	public List<Signalement> getListByResponsableDateminStatus(@PathVariable(value = "idPersonne") Long idPersonne,@PathVariable(value = "datemin")  String datemin,@PathVariable(value = "status") String status) throws Exception 
	{
		return this.signalementRepository.getListByResponsableDatemin(idPersonne,new SimpleDateFormat("yyyy-MM-dd").parse(datemin));
	}
                 
	@GetMapping("/listByResponsableDatemin/{idPersonne}/{datemin}")
	public List<Signalement> getListByResponsableDatemin(@PathVariable(value = "idPersonne") Long idPersonne,@PathVariable(value = "datemin")  String datemin) throws Exception 
	{
		return this.signalementRepository.getListByResponsableDatemin(idPersonne,new SimpleDateFormat("yyyy-MM-dd").parse(datemin));
	}

	@GetMapping("/listByResponsableType/{idPersonne}/{type}")
	public List<Signalement> getListByResponsableType(@PathVariable(value = "idPersonne") Long idPersonne,@PathVariable(value = "type") String type) throws Exception 
	{
		return this.signalementRepository.getListByResponsableType(idPersonne,type);
	}

	@GetMapping("/listByResponsableTypeStatus/{idPersonne}/{type}/{status}")
	public List<Signalement> getListByResponsableTypeStatus(@PathVariable(value = "idPersonne") Long idPersonne,@PathVariable(value = "type") String type,@PathVariable(value = "status") String status) throws Exception 
	{
		return this.signalementRepository.getListByResponsableTypeStatus(idPersonne,type,status);
	}

	@GetMapping("/listByResponsableTypeDatemax/{idPersonne}/{type}/{datemax}")
	public List<Signalement> getListByResponsableTypeDatemax(@PathVariable(value = "idPersonne") Long idPersonne,@PathVariable(value = "type") String type,@PathVariable(value = "datemax") String datemax) throws Exception 
	{
		return this.signalementRepository.getListByResponsableTypeDatemax(idPersonne,type,new SimpleDateFormat("yyyy-MM-dd").parse(datemax));
	}

	@GetMapping("/listByResponsableTypeDatemin/{idPersonne}/{type}/{datemin}")
	public List<Signalement> getListByResponsableTypeDatemin(@PathVariable(value = "idPersonne") Long idPersonne,@PathVariable(value = "type") String type,@PathVariable(value = "datemin") String datemin) throws Exception 
	{
		return this.signalementRepository.getListByResponsableTypeDatemin(idPersonne,type,new SimpleDateFormat("yyyy-MM-dd").parse(datemin));
	}

	@GetMapping("/listByResponsableDateminDatemax/{idPersonne}/{datemin}/{datemax}")
	public List<Signalement> getListByResponsableDateminDatemax(@PathVariable(value = "idPersonne") Long idPersonne,@PathVariable(value = "datemin")  String datemin,@PathVariable(value = "datemax")  String datemax) throws Exception 
	{
		return this.signalementRepository.getListByResponsableDateminDatemax(idPersonne,new SimpleDateFormat("yyyy-MM-dd").parse(datemin),new SimpleDateFormat("yyyy-MM-dd").parse(datemax));
	}

	@GetMapping("/listByResponsableDateminDatemaxStatus/{idPersonne}/{datemin}/{datemax}/{status}")
	public List<Signalement> getListByResponsableDateminDatemaxStatus(@PathVariable(value = "idPersonne") Long idPersonne,@PathVariable(value = "datemin")  String datemin,@PathVariable(value = "datemax")  String datemax,@PathVariable(value = "status") String status) throws Exception 
	{
		return this.signalementRepository.getListByResponsableDateminDatemaxStatus(idPersonne,new SimpleDateFormat("yyyy-MM-dd").parse(datemin),new SimpleDateFormat("yyyy-MM-dd").parse(datemax),status);
	}

	
	@GetMapping("/listByResponsableTypeDatemaxStatus/{idPersonne}/{type}/{datemax}/{status}")
	public List<Signalement> getListByResponsableTypeDatemaxStatus(@PathVariable(value = "idPersonne") Long idPersonne,@PathVariable(value = "type") String type,@PathVariable(value = "datemax") String datemax,@PathVariable(value = "status") String status) throws Exception 
	{
		return this.signalementRepository.getListByResponsableTypeDatemaxStatus(idPersonne,type,new SimpleDateFormat("yyyy-MM-dd").parse(datemax),status);
	}

	
	@GetMapping("/listByResponsableTypeDateminStatus/{idPersonne}/{type}/{datemin}/{status}")
	public List<Signalement> getListByResponsableTypeDateminStatus(@PathVariable(value = "idPersonne") Long idPersonne,@PathVariable(value = "type") String type,@PathVariable(value = "datemin") String datemin,@PathVariable(value = "status") String status) throws Exception 
	{
		return this.signalementRepository.getListByResponsableTypeDateminStatus(idPersonne,type,new SimpleDateFormat("yyyy-MM-dd").parse(datemin),status);
	}

	@GetMapping("/listByResponsableTypeDateminDatemax/{idPersonne}/{type}/{datemin}/{datemax}")
	public List<Signalement> getListByResponsableTypeDateminDatemax(@PathVariable(value = "idPersonne") Long idPersonne,@PathVariable(value = "type") String type,@PathVariable(value = "datemin") String datemin,@PathVariable(value = "datemax") String datemax) throws Exception 
	{
		return this.signalementRepository.getListByResponsableTypeDateminDatemax(idPersonne,type,new SimpleDateFormat("yyyy-MM-dd").parse(datemin),new SimpleDateFormat("yyyy-MM-dd").parse(datemax));
	}

	@GetMapping("/listByResponsableTypeDateminDatemaxStatus/{idPersonne}/{type}/{datemin}/{datemax}/{stat}")
	public List<Signalement> getListByResponsableTypeDateminDatemaxStatus(@PathVariable(value = "idPersonne") Long idPersonne,@PathVariable(value = "type") String type,@PathVariable(value = "datemin") String datemin,@PathVariable(value = "datemax") String datemax,@PathVariable(value = "stat") String status) throws Exception 
	{
		return this.signalementRepository.getListByResponsableTypeDateminDatemaxStatus(idPersonne,type,new SimpleDateFormat("yyyy-MM-dd").parse(datemin),new SimpleDateFormat("yyyy-MM-dd").parse(datemax),status);
	}

	// Affecter un signalement
	@PutMapping("/affecterSignalement/{idSignalement}/{idRegion}")
	public void affecterSignalement(@PathVariable(value = "idSignalement") Long idSignalement,@PathVariable(value = "idRegion") Integer idRegion) 
	{
		Signalement signalement = signalementRepository.findById(idSignalement).get();
		signalement.setIdRegion(idRegion);
		signalementRepository.save(signalement);
	}

	// Terminer un signalement
	@PutMapping("/terminerSignalement/{id}")
	public void terminerSignalement(@PathVariable(value = "id") Long signalementId) 
	{
		Signalement signalement = signalementRepository.findById(signalementId).get();
		signalement.setStatus("termine");
		signalementRepository.save(signalement);
	}

	// Supprimer un signalement
	@DeleteMapping("/supprimerSignalement/{id}")
	public void deleteSignalement(@PathVariable(value = "id") Long signalementId) 
	{
		signalementRepository.deleteById(signalementId);
	}

	// Signalements dans une région
	@GetMapping("/signalements/idRegion")
	public List<Signalement> getSignalementByIdRegion(@RequestParam long idRegion) throws Exception 
	{
		return this.signalementRepository.findByIdRegion(idRegion);
	}

	// One
	@GetMapping("/signalement/{id}")
	public List<List<Object>> getSignalementById(@PathVariable(value = "id") Long sId) throws ResourceNotFoundException 
	{
		return this.signalementRepository.findOneSignalement(sId);
	}

	// all
	@GetMapping("/signalements")
	public List<Signalement> getAllSignalement() throws Exception 
	{
		return this.signalementRepository.findAll();
	}

	// get signalement by id
	@GetMapping("/signalementRegion/{id}")
	public ResponseEntity<Signalement> getRegionById(@PathVariable(value = "id") long idSignalement) throws Exception 
	{
		Signalement signalement = signalementRepository.findById(idSignalement).orElseThrow(() -> new ResourceNotFoundException("Signalement not found for this id :: "));
		return ResponseEntity.ok().body(signalement);
	}

	// get signalement by idUtilisateur
	@GetMapping("/signalement")
	public List<Signalement> getByIdUtilisateur(@RequestParam long idUtilisateur) throws Exception 
	{
		return this.signalementRepository.findByIdUtilisateur(idUtilisateur);
	}

	// insert
	@PostMapping("/signalement")
	public Signalement createSignalement(@RequestPart Signalement signalement,@Nullable @RequestPart MultipartFile file) throws Exception 
	{
		if(file!=null)
		{
			// signalement.setPhoto(saveFile(file));
			try {
				String sary = encodeFileToBase64Binary(multipartToFile(file, saveFile(file)));
				// System.out.println(sary);
				signalement.setPhoto(sary);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else 
		{
			signalement.setPhoto("default.jpg");
		}
		
		return this.signalementRepository.save(signalement);
	}

	private static String encodeFileToBase64Binary(File file) throws Exception {
        FileInputStream f = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        f.read(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
	public  static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
		File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
		multipart.transferTo(convFile);
		return convFile;
	}

	private String saveFile(MultipartFile file)
    {
        var filename = UUID.randomUUID().toString()+file.getOriginalFilename();
        // var dest = Paths.get(uploadLocation + "/" + filename);
        // try 
        // {
        //     Files.copy(file.getInputStream(),dest);
        // } 
        // catch (IOException e) 
        // {
        //     e.printStackTrace();
        //     return "Error";
        // }
        return filename;
    }


	// update
	@PutMapping("/signalement/{id}")
	public ResponseEntity<Signalement> updateSignalement(@PathVariable(value = "id") Long idSignalement,@Valid @RequestBody Signalement signalementDetails) throws Exception {
		Signalement signalement = signalementRepository.findById(idSignalement).orElseThrow(() -> new ResourceNotFoundException("Signalement not found for this id"));
		signalement.setIdRegion(signalementDetails.getIdRegion());
		signalement.setIdType(signalementDetails.getIdType());
		signalement.setStatus(signalementDetails.getStatus());
		return ResponseEntity.ok(this.signalementRepository.save(signalement));
	}

	// delete
	@DeleteMapping("/signalement/{id}")
	public Map<String, Boolean> deleteSignalement(@PathVariable(value = "id") long idSignalement) throws Exception 
	{
		Signalement signalement = signalementRepository.findById(idSignalement).orElseThrow(() -> new ResourceNotFoundException("Signalement not found for this id"));
		this.signalementRepository.delete(signalement);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// Recherche avancée BackOffice
	@GetMapping("/rechercherSignalement/annee={annee}/moisDebut={moisDebut}/moisFin={moisFin}/idType={idType}")
	public List<Signalement> rechercherSignalementBackOffice(@PathVariable(value = "annee") Integer annee,@PathVariable(value = "moisDebut") Integer moisDebut, @PathVariable(value = "moisFin") Integer moisFin,@PathVariable(value = "idType") Long idType) throws Exception 
	{
		return this.signalementRepository.rechercherSignalementBackOffice(annee, moisDebut, moisFin, idType);
	}

	// Recherche avancée BackOffice
	@GetMapping("/rechercherSignalement/idType={idType}/moisDebut={moisDebut}/moisFin={moisFin}")
	public List<Signalement> rechercherSignalementBackOffice(@PathVariable(value = "idType") Long idType,@PathVariable(value = "moisDebut") Integer moisDebut, @PathVariable(value = "moisFin") Integer moisFin) throws Exception 
	{
		return this.signalementRepository.rechercherSignalementBackOffice(idType, moisDebut, moisFin);
	}

	// Recherche avancée BackOffice
	@GetMapping("/rechercherSignalement/annee={annee}/idType={idType}")
	public List<Signalement> rechercherSignalementBackOffice(@PathVariable(value = "annee") Integer annee,@PathVariable(value = "idType") Long idType) throws Exception 
	{
		return this.signalementRepository.rechercherSignalementBackOffice(annee, idType);
	}

	// Recherche avancée BackOffice
	@GetMapping("/rechercherSignalement/annee{annee}/moisDebut={moisDebut}/moisFin={moisFin}")
	public List<Signalement> rechercherSignalementBackOffice(@PathVariable(value = "annee") Integer annee,@PathVariable(value = "moisDebut") Integer moisDebut, @PathVariable(value = "moisFin") Integer moisFin) throws Exception 
	{
		return this.signalementRepository.rechercherSignalementBackOffice(annee, moisDebut, moisFin);
	}

	// Recherche avancée BackOffice
	@GetMapping("/rechercherSignalement/annee={annee}")
	public List<Signalement> rechercherSignalementBackOffice(@PathVariable(value = "annee") Integer annee) throws Exception 
	{
		return this.signalementRepository.rechercherSignalementBackOffice(annee);
	}

	// Recherche avancée BackOffice
	@GetMapping("/rechercherSignalement/moisDebut={moisDebut}/moisFin={moisFin}")
	public List<Signalement> rechercherSignalementBackOffice(@PathVariable(value = "moisDebut") Integer moisDebut,@PathVariable(value = "moisFin") Integer moisFin) throws Exception 
	{
		return this.signalementRepository.rechercherSignalementBackOffice(moisDebut, moisFin);
	}

	// Recherche avancée BackOffice
	@GetMapping("/rechercherSignalement/idType={idType}")
	public List<Signalement> rechercherSignalementBackOffice(@PathVariable(value = "idType") Long idType) throws Exception 
	{
		return this.signalementRepository.rechercherSignalementBackOffice(idType);
	}

	// getNbSignalementParType BackOffice
	// @CrossOrigin(origins = "http://localhost/")
	@GetMapping("/rechercherSignalement/getNbSignalementParType")
	List<Object> getNbSignalementParType() {
		return this.signalementRepository.getNbSignalementParType();
	}
}
