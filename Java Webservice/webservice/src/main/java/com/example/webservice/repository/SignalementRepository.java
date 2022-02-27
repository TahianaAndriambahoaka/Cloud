package com.example.webservice.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.webservice.model.Signalement;

public interface SignalementRepository extends JpaRepository<Signalement, Long> 
{
	@Query(value="select signalement.id as id,type.nom as nomType,status,dateHeure,description,idUtilisateur,latitude,longitude, photo from Signalement join type on type.id = Signalement.idType where signalement.id= ?1",nativeQuery= true)
	List<List<Object>> findOneSignalement(Long id);

	@Query(value="select * from Signalement where idRegion = ?1 and idType = ?2 and status = ?3",nativeQuery= true)
	List<Signalement> rechercherSignalement(Integer idRegion,Long idType,String status);
	
	@Query(value="select signalement.id as id,type.nom as nomType,status,dateHeure,description,idUtilisateur from Signalement join type on type.id = Signalement.idType where  signalement.id  in (select id from signalement where idRegion = 0)",nativeQuery= true)
	List<List<Object>> findSignalementNotAffected();

	@Query(value="select signalement.id as id,region.nom as nomRegion,type.nom as nomType,status,dateHeure,description,idUtilisateur from Signalement join region on region.id = Signalement.idRegion join type on type.id = Signalement.idType where signalement.idRegion != 0",nativeQuery= true)
	List<List<Object>> findAffectedSignalement();

	@Query(value="select signalement.id as id,region.nom as nomRegion,type.nom as nomType,status,dateHeure,description,idUtilisateur from Signalement join region on region.id = Signalement.idRegion join type on type.id = Signalement.idType where status = 'termine'",nativeQuery= true)
	List<List<Object>> getListNotification();

	@Query(value="select signalement.id,type.id as idtype,region.id as idRegion,signalement.dateheure,signalement.description,signalement.latitude,signalement.longitude,signalement.photo,signalement.status,signalement.idUtilisateur from signalement join type on type.id = signalement.idType join region on region.id = signalement.idregion join responsable_region on region.id = responsable_region.idregion join personne on personne.id = responsable_region.idPersonne where idPersonne = ?1 ",nativeQuery= true)
	List<Signalement> getListByResponsable(Long id);

	@Query(value="select signalement.id,type.id as idtype,region.id as idRegion,signalement.dateheure,signalement.description,signalement.latitude,signalement.longitude,signalement.photo,signalement.status,signalement.idUtilisateur from signalement join type on type.id = signalement.idType join region on region.id = signalement.idregion join responsable_region on region.id = responsable_region.idregion join personne on personne.id = responsable_region.idPersonne where idPersonne = ?1  and status = ?2 ",nativeQuery= true)
	List<Signalement> getListByResponsableStatus(Long id,String status);

	@Query(value="select signalement.id,type.id as idtype,region.id as idRegion,signalement.dateheure,signalement.description,signalement.latitude,signalement.longitude,signalement.photo,signalement.status,signalement.idUtilisateur from signalement join type on type.id = signalement.idType join region on region.id = signalement.idregion join responsable_region on region.id = responsable_region.idregion join personne on personne.id = responsable_region.idPersonne where idPersonne = ?1  and dateheure <= ?2 ",nativeQuery= true)
	List<Signalement> getListByResponsableDatemax(Long id,Date datemax);

	@Query(value="select signalement.id,type.id as idtype,region.id as idRegion,signalement.dateheure,signalement.description,signalement.latitude,signalement.longitude,signalement.photo,signalement.status,signalement.idUtilisateur from signalement join type on type.id = signalement.idType join region on region.id = signalement.idregion join responsable_region on region.id = responsable_region.idregion join personne on personne.id = responsable_region.idPersonne where idPersonne = ?1  and dateheure <= ?2 and status = ?3 ",nativeQuery= true)
	List<Signalement> getListByResponsableDatemaxStatus(Long id,Date datemax,String status);

	@Query(value="select signalement.id,type.id as idtype,region.id as idRegion,signalement.dateheure,signalement.description,signalement.latitude,signalement.longitude,signalement.photo,signalement.status,signalement.idUtilisateur from signalement join type on type.id = signalement.idType join region on region.id = signalement.idregion join responsable_region on region.id = responsable_region.idregion join personne on personne.id = responsable_region.idPersonne where idPersonne = ?1  and dateheure >= ?2 ",nativeQuery= true)
	List<Signalement> getListByResponsableDatemin(Long id,Date datemin);

	@Query(value="select signalement.id,type.id as idtype,region.id as idRegion,signalement.dateheure,signalement.description,signalement.latitude,signalement.longitude,signalement.photo,signalement.status,signalement.idUtilisateur from signalement join type on type.id = signalement.idType join region on region.id = signalement.idregion join responsable_region on region.id = responsable_region.idregion join personne on personne.id = responsable_region.idPersonne where idPersonne = ?1  and dateheure >= ?2 and status = ?3",nativeQuery= true)
	List<Signalement> getListByResponsableDateminStatus(Long id,Date datemin,String status);

	@Query(value="select signalement.id,type.id as idtype,region.id as idRegion,signalement.dateheure,signalement.description,signalement.latitude,signalement.longitude,signalement.photo,signalement.status,signalement.idUtilisateur from signalement join type on type.id = signalement.idType join region on region.id = signalement.idregion join responsable_region on region.id = responsable_region.idregion join personne on personne.id = responsable_region.idPersonne where idPersonne = ?1  and type.nom = ?2 ",nativeQuery= true)
	List<Signalement> getListByResponsableType(Long id,String type);

	@Query(value="select signalement.id,type.id as idtype,region.id as idRegion,signalement.dateheure,signalement.description,signalement.latitude,signalement.longitude,signalement.photo,signalement.status,signalement.idUtilisateur from signalement join type on type.id = signalement.idType join region on region.id = signalement.idregion join responsable_region on region.id = responsable_region.idregion join personne on personne.id = responsable_region.idPersonne where idPersonne = ?1  and type.nom = ?2 and status = ?3",nativeQuery= true)
	List<Signalement> getListByResponsableTypeStatus(Long id,String type,String status);

	@Query(value="select signalement.id,type.id as idtype,region.id as idRegion,signalement.dateheure,signalement.description,signalement.latitude,signalement.longitude,signalement.photo,signalement.status,signalement.idUtilisateur from signalement join type on type.id = signalement.idType join region on region.id = signalement.idregion join responsable_region on region.id = responsable_region.idregion join personne on personne.id = responsable_region.idPersonne where idPersonne = ?1  and type.nom = ?2 and dateheure <= ?3 ",nativeQuery= true)
	List<Signalement> getListByResponsableTypeDatemax(Long id,String type,Date datemax);

	@Query(value="select signalement.id,type.id as idtype,region.id as idRegion,signalement.dateheure,signalement.description,signalement.latitude,signalement.longitude,signalement.photo,signalement.status,signalement.idUtilisateur from signalement join type on type.id = signalement.idType join region on region.id = signalement.idregion join responsable_region on region.id = responsable_region.idregion join personne on personne.id = responsable_region.idPersonne where idPersonne = ?1  and type.nom = ?2 and dateheure >= ?3 ",nativeQuery= true)
	List<Signalement> getListByResponsableTypeDatemin(Long id,String type,Date datemin);

	@Query(value="select signalement.id,type.id as idtype,region.id as idRegion,signalement.dateheure,signalement.description,signalement.latitude,signalement.longitude,signalement.photo,signalement.status,signalement.idUtilisateur from signalement join type on type.id = signalement.idType join region on region.id = signalement.idregion join responsable_region on region.id = responsable_region.idregion join personne on personne.id = responsable_region.idPersonne where idPersonne = ?1  and dateheure >= ?2 and dateheure <= ?3",nativeQuery= true)
	List<Signalement> getListByResponsableDateminDatemax(Long id,Date datemin,Date datemax);

	@Query(value="select signalement.id,type.id as idtype,region.id as idRegion,signalement.dateheure,signalement.description,signalement.latitude,signalement.longitude,signalement.photo,signalement.status,signalement.idUtilisateur from signalement join type on type.id = signalement.idType join region on region.id = signalement.idregion join responsable_region on region.id = responsable_region.idregion join personne on personne.id = responsable_region.idPersonne where idPersonne = ?1  and dateheure >= ?2 and dateheure <= ?3 and status = ?4",nativeQuery= true)
	List<Signalement> getListByResponsableDateminDatemaxStatus(Long id,Date datemin,Date datemax,String status);

	@Query(value="select signalement.id,type.id as idtype,region.id as idRegion,signalement.dateheure,signalement.description,signalement.latitude,signalement.longitude,signalement.photo,signalement.status,signalement.idUtilisateur from signalement join type on type.id = signalement.idType join region on region.id = signalement.idregion join responsable_region on region.id = responsable_region.idregion join personne on personne.id = responsable_region.idPersonne where idPersonne = ?1   and type.nom = ?2 and dateheure <= ?3 and status = ?4",nativeQuery= true)
	List<Signalement> getListByResponsableTypeDatemaxStatus(Long id,String type,Date datemax,String status);

	@Query(value="select signalement.id,type.id as idtype,region.id as idRegion,signalement.dateheure,signalement.description,signalement.latitude,signalement.longitude,signalement.photo,signalement.status,signalement.idUtilisateur from signalement join type on type.id = signalement.idType join region on region.id = signalement.idregion join responsable_region on region.id = responsable_region.idregion join personne on personne.id = responsable_region.idPersonne where idPersonne = ?1   and type.nom = ?2 and dateheure >= ?3 and status = ?4",nativeQuery= true)
	List<Signalement> getListByResponsableTypeDateminStatus(Long id,String type,Date datemin,String status);

	@Query(value="select signalement.id,type.id as idtype,region.id as idRegion,signalement.dateheure,signalement.description,signalement.latitude,signalement.longitude,signalement.photo,signalement.status,signalement.idUtilisateur from signalement join type on type.id = signalement.idType join region on region.id = signalement.idregion join responsable_region on region.id = responsable_region.idregion join personne on personne.id = responsable_region.idPersonne where idPersonne = ?1   and type.nom = ?2 and dateheure >= ?3 and dateheure <= ?4",nativeQuery= true)
	List<Signalement> getListByResponsableTypeDateminDatemax(Long id,String type,Date datemin,Date datemax);

	@Query(value="select signalement.id,type.id as idtype,region.id as idRegion,signalement.dateheure,signalement.description,signalement.latitude,signalement.longitude,signalement.photo,signalement.status,signalement.idUtilisateur from signalement join type on type.id = signalement.idType join region on region.id = signalement.idregion join responsable_region on region.id = responsable_region.idregion join personne on personne.id = responsable_region.idPersonne where idPersonne = ?1   and type.nom = ?2 and dateheure >= ?3 and dateheure <= ?4 and status = ?5 ",nativeQuery= true)
	List<Signalement> getListByResponsableTypeDateminDatemaxStatus(Long id,String type,Date datemin,Date datemax,String Status);


	void deleteById(long id);

	List<Signalement> findByIdRegion(long idRegion);

    void save(Optional<Signalement> signalement);
	
	// find by idUtilisateur
	@Query(value="select * from Signalement where idUtilisateur = ?1",nativeQuery= true)
	List<Signalement> findByIdUtilisateur(Long idUtilisateur);
	
	// Recherche avancée BackOffice
	@Query(value="select * from Signalement where EXTRACT(YEAR FROM dateheure) = ?1 and (EXTRACT(MONTH FROM dateheure) between ?2 and ?3)  and idType = ?4",nativeQuery= true)
	List<Signalement> rechercherSignalementBackOffice(Integer annee, Integer moisDebut, Integer moisFin, Long idType);
	
	// Recherche avancée BackOffice
	@Query(value="select * from Signalement where idType = ?1 and (EXTRACT(MONTH FROM dateheure) between ?2 and ?3)",nativeQuery= true)
	List<Signalement> rechercherSignalementBackOffice(Long idType, Integer moisDebut, Integer moisFin);
	
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
