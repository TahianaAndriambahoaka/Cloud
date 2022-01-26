package com.example.webservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Responsable_region")
public class ResponsableRegion 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="idpersonne")
	private long idPersonne;

	@Column(name="idregion")
	private long idRegion;
	
	public ResponsableRegion(long idPersonne,long idRegion)
	{
		this.idPersonne = idPersonne;
		this.idRegion = idRegion;
	}

	public long getId() 
	{
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdPersonne() {
		return idPersonne;
	}

	public void setIdPersonne(long idPersonne) {
		this.idPersonne = idPersonne;
	}

	public long getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(long idRegion) {
		this.idRegion = idRegion;
	}
}
