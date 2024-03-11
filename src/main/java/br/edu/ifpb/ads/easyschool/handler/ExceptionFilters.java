package br.edu.ifpb.ads.easyschool.handler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ExceptionFilters {
    
    private String title;
    private int status;
    private String details;
    private LocalDateTime timestamp;
    private String devMsg;


}
