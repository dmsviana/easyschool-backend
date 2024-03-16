package br.edu.ifpb.ads.easyschool.controllers;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ads.easyschool.controllers.dtos.response.DashboardDataDTO;
import br.edu.ifpb.ads.easyschool.services.DashboardService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dashboard")
public class DashboardController {
    

    private final DashboardService dashboardService;

    @ResponseStatus(OK)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public DashboardDataDTO getDashboardData() {
        DashboardDataDTO data = dashboardService.getDashboardData();
        return data;
    }
}
