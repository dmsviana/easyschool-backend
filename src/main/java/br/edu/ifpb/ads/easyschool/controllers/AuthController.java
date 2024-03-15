package br.edu.ifpb.ads.easyschool.controllers;

import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.ResponseEntity.ok;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ads.easyschool.controllers.dtos.request.auth.LoginRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.response.MessageResponse;
import br.edu.ifpb.ads.easyschool.security.jwt.JWTUtils;
import br.edu.ifpb.ads.easyschool.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JWTUtils jwtUtils;


    @PostMapping("/api/auth")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var userDetails = (UserDetailsImpl) authentication.getPrincipal();
        var jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        return ok().header(SET_COOKIE, jwtCookie.toString()).body(null);
    }

    @PostMapping("v1/api/logout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ok().header(SET_COOKIE, cookie.toString()).body(new MessageResponse("You've been signed out!"));
    }

}
