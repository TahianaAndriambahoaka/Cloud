package com.example.webservice.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Signalement")
public class Signalement 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name="idtype")
	private long idType;
	
	@Column(name="idregion")
	private long idRegion;
	
	@Column(name="status")
	private String status;
	
	@Column(name="dateheure")
	private Date dateHeure;
	
	@Column(name="latitude")
	private double latitude;
	
	@Column(name="longitude")
	private double longitude;
	
	@Column(name="description")
	private String description;
	
	@Column(name="idutilisateur")
	private long idUtilisateur;

	@Column(name="photo", columnDefinition="text", length=10485760)
	private String photo;

/*
	public Signalement(long idType,long idRegion,String status,Date dateHeure,double latitude,double longitude,String description,long idUtilisateur,String photo)
	{
		this.idType = idType;
		this.idRegion = idRegion;
		this.status = status;
		this.dateHeure = dateHeure;
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
		this.idUtilisateur = idUtilisateur;
		this.photo = photo;
	}
*/
	public Signalement(){}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdType() {
		return idType;
	}

	public void setIdType(long idType) {
		this.idType = idType;
	}

	public long getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(long idRegion) {
		this.idRegion = idRegion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateHeure() {
		return dateHeure;
	}

	public void setDateHeure(Date dateHeure) {
		this.dateHeure = dateHeure;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
