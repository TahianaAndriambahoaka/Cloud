package com.example.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.webservice.model.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    @Query(value="select u.id, u.idpersonne from utilisateur as u join personne as p on (p.id = u.idpersonne) where p.id = ?1",nativeQuery= true)
	Utilisateur findByIdPersonne(long idPersonne);
}
