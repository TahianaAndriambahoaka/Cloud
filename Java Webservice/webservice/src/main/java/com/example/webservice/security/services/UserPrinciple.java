package com.example.webservice.security.services;
import com.example.webservice.model.Personne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Objects;

public class UserPrinciple implements UserDetails 
{
	private static final long serialVersionUID = 1L;

	private Long id;
    private String username;

    @JsonIgnore
    private String password;

    public UserPrinciple(Long id ,String username,  String password) 
    {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    
    public static UserPrinciple build(Personne user) 
    {
        return new UserPrinciple(user.getId(),user.getEmail(),user.getPassword());
    }

    public Long getId() {
        return id;
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() 
    {
        return null;
    }
}