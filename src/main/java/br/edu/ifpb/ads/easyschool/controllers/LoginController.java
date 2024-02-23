package br.edu.ifpb.ads.easyschool.controllers;

import br.edu.ifpb.ads.easyschool.model.Student;
import br.edu.ifpb.ads.easyschool.repositories.StudentRepository;
import br.edu.ifpb.ads.easyschool.services.security.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("login")
    public String authenticate(Authentication authentication){
        return authenticationService.authenticate(authentication);
    }

}
