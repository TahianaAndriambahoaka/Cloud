package com.example.webservice.security.services;
import com.example.webservice.model.Personne;
import com.example.webservice.repository.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService 
{

    @Autowired
    PersonneRepository personneRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException 
    {
        Personne personne = personneRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Personne Not Found with -> email " + email));
        return UserPrinciple.build(personne);
    }
}