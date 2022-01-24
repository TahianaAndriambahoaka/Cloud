package com.example.webservice.controller;
import javax.validation.Valid;
import com.example.webservice.message.request.LoginForm;
import com.example.webservice.message.request.SignUpForm;
import com.example.webservice.message.response.JwtResponse;
import com.example.webservice.model.Personne;
import com.example.webservice.repository.PersonneRepository;
import com.example.webservice.repository.SignalementRepository;
import com.example.webservice.security.jwt.JwtProvider;
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
    SignalementRepository signalementRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) 
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) 
    {
        if(personneRepository.existsByUsername(signUpRequest.getUsername())) 
        {
            return new ResponseEntity<String>("Fail -> Username is already taken!",HttpStatus.BAD_REQUEST);
        }

        Personne user = new Personne(signUpRequest.getUsername(),encoder.encode(signUpRequest.getPassword()),signUpRequest.getNom(),signUpRequest.getPrenom());
        personneRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(),signUpRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

}