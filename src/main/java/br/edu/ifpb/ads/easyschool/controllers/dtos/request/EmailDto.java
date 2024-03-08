package br.edu.ifpb.ads.easyschool.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {

    private Long studentId;
    private String emailTo;
    private String subject;
    private String text;

}
