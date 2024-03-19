package br.edu.ifpb.ads.easyschool.controllers;

import static org.springframework.http.HttpStatus.OK;

import java.security.Principal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ads.easyschool.security.services.ManagerDetailsServiceImpl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("v1/api/me")
@RestController
public class MeController {

    private final ManagerDetailsServiceImpl managerDetailsService;

    @ResponseStatus(OK)
    @GetMapping
    public UserDetails findById(Principal principal) {
        return managerDetailsService.loadUserByUsername(principal.getName());
    }
}