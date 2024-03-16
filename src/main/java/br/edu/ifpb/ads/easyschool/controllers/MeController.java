package br.edu.ifpb.ads.easyschool.controllers;

import static org.springframework.http.HttpStatus.OK;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ads.easyschool.security.services.CompositeDetailsServiceImpl;
import br.edu.ifpb.ads.easyschool.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("v1/api/me")
@RestController
public class MeController {

    private final CompositeDetailsServiceImpl compositeDetailsService;

    @ResponseStatus(OK)
    @GetMapping
    public UserDetailsImpl findById(Principal principal) {
        return (UserDetailsImpl) compositeDetailsService.loadUserByUsername(principal.getName());
    }
}