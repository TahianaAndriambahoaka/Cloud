package com.example.webservice.controller;
import javax.validation.Valid;
import com.example.webservice.message.request.LoginForm;
import com.example.webservice.message.request.SignUpFormResponsable;
import com.example.webservice.message.request.SignUpForm;
import com.example.webservice.message.response.JwtResponse;

import com.example.webservice.model.Personne;
import com.example.webservice.model.Utilisateur;
import com.example.webservice.model.ResponsableRegion;

import com.example.webservice.repository.PersonneRepository;
import com.example.webservice.repository.UtilisateurRepository;
import com.example.webservice.repository.SignalementRepository;
import com.example.webservice.repository.ResponsableRegionRepository;

import com.example.webservice.security.jwt.JwtProvider;
import com.example.webservice.exception.ErrorDetails;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs 
{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PersonneRepository personneRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    ResponsableRegionRepository responsableRegionRepository;

    @Autowired
    SignalementRepository signalementRepository;


    
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) 
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }


    @PostMapping("/signupUtilisateur")
    public ResponseEntity<?> signupUtilisateur(@Valid @RequestBody SignUpForm signUpRequest) 
    {
        if(personneRepository.existsByEmail(signUpRequest.getEmail())) 
        {
            ErrorDetails errorDetails = new ErrorDetails(new Date(),"Signup Failed  !!!", " Username is already taken!");
		    return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
        }
        else 
        {
            if(signUpRequest.getPassword().length()!=8)
            {
                ErrorDetails errorDetails = new ErrorDetails(new Date(),"Signup Failed  !!!", " Password must be 8 character !");
                return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
            }
            else 
            {
                if(signUpRequest.getPassword().contains("é") || signUpRequest.getPassword().contains("è") || signUpRequest.getPassword().contains("ê"))
                {
                    ErrorDetails errorDetails = new ErrorDetails(new Date(),"Signup Failed  !!!", " Password must be without accent !");
                    return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
                }
                else 
                {
                    Personne user = new Personne(signUpRequest.getEmail(),encoder.encode(signUpRequest.getPassword()),signUpRequest.getNom(),signUpRequest.getPrenom());
                    Personne p = personneRepository.save(user);
                    Utilisateur u = new Utilisateur(p.getId());
                    utilisateurRepository.save(u);
                }
            }
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(),signUpRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }


    @PostMapping("/signupResponsable")
    public ResponseEntity<?> registerResponsable(@Valid @RequestBody SignUpFormResponsable signUpRequest) 
    {
        if(personneRepository.existsByEmail(signUpRequest.getEmail())) 
        {
            ErrorDetails errorDetails = new ErrorDetails(new Date(),"Signup Failed  !!!", " Username is already taken!");
		    return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
        }
        else 
        {
            if(signUpRequest.getPassword().length()!=8)
            {
                ErrorDetails errorDetails = new ErrorDetails(new Date(),"Signup Failed  !!!", " Password must be 8 character !");
                return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
            }
            else 
            {
                if(signUpRequest.getPassword().contains("é") || signUpRequest.getPassword().contains("è") || signUpRequest.getPassword().contains("ê"))
                {
                    ErrorDetails errorDetails = new ErrorDetails(new Date(),"Signup Failed  !!!", " Password must be without accent !");
                    return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
                }
                else 
                {
                    Personne user = new Personne(signUpRequest.getEmail(),encoder.encode(signUpRequest.getPassword()),signUpRequest.getNom(),signUpRequest.getPrenom());
                    Personne p = personneRepository.save(user);
                    ResponsableRegion u = new ResponsableRegion(p.getId(),signUpRequest.getIdRegion());
                    responsableRegionRepository.save(u);
                }
            }
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(),signUpRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

}