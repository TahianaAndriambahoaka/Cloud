package com.example.webservice.message.request;
import javax.validation.constraints.*;
import java.util.Date;

public class SignUpForm 
{

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;


    private String nom;

    private String prenom;

    private Date ddn;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) 
    {
        this.username = username;
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

    
	public Date getDdn() 
    {
		return ddn;
	}

	public void setDdn(Date ddn) {
		this.ddn = ddn;
	}

}