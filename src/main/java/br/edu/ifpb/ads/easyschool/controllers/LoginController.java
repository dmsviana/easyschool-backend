package br.edu.ifpb.ads.easyschool.controllers;

import br.edu.ifpb.ads.easyschool.dtos.request.StudentRequestDTO;
import br.edu.ifpb.ads.easyschool.security.jwt.JWTUtils;
import br.edu.ifpb.ads.easyschool.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.ResponseEntity.ok;


@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final JWTUtils jwtUtils;




    @PostMapping("/api/auth")
    public ResponseEntity<?> authenticate(@Valid @RequestBody StudentRequestDTO loginRequestDTO) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var userDetails = (UserDetailsImpl) authentication.getPrincipal();
        var jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        return ok().header(SET_COOKIE, jwtCookie.toString()).body(null);
    }

}
