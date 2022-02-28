package com.example.webservice.message.request;

import java.sql.Date;

public class SignUpForm 
{

    private String email;

    private String password;

    private String nom;

    private String prenom;

    private Date ddn;


    public String getEmail() {
        return email;
    }

    public Date getDdn() {
        return ddn;
    }

    public void setDdn(Date ddn) {
        this.ddn = ddn;
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