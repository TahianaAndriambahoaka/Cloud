package com.example.webservice.security.services;

//import com.example.webservice.model.User;
import com.example.webservice.repository.UserRepository;
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
    UserRepository userRepository;

    @Autowired
    PersonneRepository personneRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException //, LoginNotFoundException
    {
        //User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with -> username  : " + username));
        //return UserPrinciple.build(user);

        Personne personne = personneRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Personne Not Found with -> username " + username));
        return UserPrinciple.build(personne);
    }
}