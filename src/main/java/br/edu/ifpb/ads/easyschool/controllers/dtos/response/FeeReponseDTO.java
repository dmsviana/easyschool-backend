package br.edu.ifpb.ads.easyschool.controllers.dtos.response;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class FeeReponseDTO {

    private Long id;
    private BigDecimal price;
    private LocalDateTime paidAt;
    private LocalDate dueDate;
    private Long studentId;
}
