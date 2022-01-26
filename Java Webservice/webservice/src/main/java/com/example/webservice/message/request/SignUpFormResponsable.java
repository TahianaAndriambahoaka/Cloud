package com.example.webservice.message.request;

public class SignUpFormResponsable 
{
    
    private String email;

    private String password;

    private String nom;

    private String prenom;

    private long idRegion;


    public long getIdRegion() 
    {
        return idRegion;
    }

    public void setIdRegion(long idRegion) 
    {
        this.idRegion = idRegion;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        
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

	public void setPrenom(String prenom) 
    {
		this.prenom = prenom;
	}
}
