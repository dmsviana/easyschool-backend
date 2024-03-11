package br.edu.ifpb.ads.easyschool.controllers;

import java.security.Principal;

import static org.springframework.http.HttpStatus.OK;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import br.edu.ifpb.ads.easyschool.controllers.dtos.response.StudentResponseDTO;
import br.edu.ifpb.ads.easyschool.services.StudentService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("v1/api/me")
@RestController
public class MeController {

    private final StudentService studentService;

    @ResponseStatus(OK)
    @GetMapping
    public StudentResponseDTO findById(Principal principal) {
        return studentService.findByUsername(principal.getName());
    }
}