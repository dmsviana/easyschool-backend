package br.edu.ifpb.ads.easyschool.controllers.dtos.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDataDTO {

    private Long totalStudents;
    private Long totalCourses;
    private BigDecimal totalFees;
    private Long pendingFeesCount;
    
}
