package com.example.webservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.example.webservice.model.Signalement;


public interface SignalementRepository extends JpaRepository<Signalement, Long> {
	@Query(value="select signalement.id as id,region.nom as nomRegion,type.nom as nomType,status,dateHeure,description,idUtilisateur from Signalement join region on region.id = Signalement.idRegion join type on type.id = Signalement.idType  where signalement.id= ?1",nativeQuery= true)
	List<List<Object>> findOneSignalement(Long id);

	@Query(value="select * from Signalement where idRegion = ?1 and idType = ?2 and status = ?3",nativeQuery= true)
	List<Signalement> rechercherSignalement(Integer idRegion,Long idType,String status);
	
	@Query(value="select signalement.id as id,type.nom as nomType,status,dateHeure,description,idUtilisateur from Signalement join type on type.id = Signalement.idType where  signalement.id  in (select id from signalement where idRegion IS NULL)",nativeQuery= true)
	List<List<Object>> findSignalementNotAffected();

	@Query(value="select signalement.id as id,region.nom as nomRegion,type.nom as nomType,status,dateHeure,description,idUtilisateur from Signalement join region on region.id = Signalement.idRegion join type on type.id = Signalement.idType where signalement.idRegion IS NOT NULL",nativeQuery= true)
	List<List<Object>> findAffectedSignalement();

	@Query(value="select signalement.id as id,region.nom as nomRegion,type.nom as nomType,status,dateHeure,description,idUtilisateur from Signalement join region on region.id = Signalement.idRegion join type on type.id = Signalement.idType where status = 'termine'",nativeQuery= true)
	List<List<Object>> getListNotification();


	void deleteById(long id);

	List<Signalement> findByIdRegion(long idRegion);

    void save(Optional<Signalement> signalement);
	
	// Recherche avancée BackOffice
	@Query(value="select * from Signalement where EXTRACT(YEAR FROM dateheure) = ?1 and (EXTRACT(MONTH FROM dateheure) between ?2 and ?3)  and idType = ?4",nativeQuery= true)
	List<Signalement> rechercherSignalementBackOffice(Integer annee, Integer moisDebut, Integer moisFin, Long idType);
	
	// Recherche avancée BackOffice
	@Query(value="select * from Signalement where EXTRACT(YEAR FROM dateheure) = ?1 and idType = ?2",nativeQuery= true)
	List<Signalement> rechercherSignalementBackOffice(Integer annee, Long idType);
	
	// Recherche avancée BackOffice
	@Query(value="select * from Signalement where EXTRACT(YEAR FROM dateheure) = ?1 and (EXTRACT(MONTH FROM dateheure) between ?2 and ?3)",nativeQuery= true)
	List<Signalement> rechercherSignalementBackOffice(Integer annee, Integer moisDebut, Integer moisFin);
	
	// Recherche avancée BackOffice
	@Query(value="select * from Signalement where EXTRACT(YEAR FROM dateheure) = ?1",nativeQuery= true)
	List<Signalement> rechercherSignalementBackOffice(Integer annee);
	
	// Recherche avancée BackOffice
	@Query(value="select * from Signalement where EXTRACT(MONTH FROM dateheure) between ?1 and ?2",nativeQuery= true)
	List<Signalement> rechercherSignalementBackOffice(Integer moisDebut, Integer moisFin);
	
	// Recherche avancée BackOffice
	@Query(value="select * from Signalement where idType = ?1",nativeQuery= true)
	List<Signalement> rechercherSignalementBackOffice(Long idType);
	
	// getNbSignalementParType BackOffice
	@Query(value="select Type.id, Type.nom, count(Signalement.id) nb from Signalement join Type on (Type.id=Signalement.idType) group by Type.id, Type.nom",nativeQuery= true)
	List<Object> getNbSignalementParType();
	
}
