package com.example.webservice.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Personne")
public class Personne {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="nom")
	private String nom;

	@Column(name="prenom")
	private String prenom;

	@Column(name="username")
	private String username;

	@Column(name="password")
	private String password;

	@Column(name="ddn")
	private Date ddn;

	@Column(name="pdp")
	private String pdp;

	public Personne()
	{

	}
	
	/*public Personne(String username,String password)
	{
		this.username=username;
		this.password=password;
	}*/

	public Personne(String username,String password,String nom,String prenom)
	{
		this.username=username;
		this.password= password;
		this.nom = nom;
		this.prenom=prenom;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDdn() {
		return ddn;
	}

	public void setDdn(Date ddn) {
		this.ddn = ddn;
	}

	public String getPdp() {
		return pdp;
	}

	public void setPdp(String pdp) {
		this.pdp = pdp;
	}
	
}
