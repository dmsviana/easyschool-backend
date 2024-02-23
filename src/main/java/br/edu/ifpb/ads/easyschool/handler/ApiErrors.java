package br.edu.ifpb.ads.easyschool.handler;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class ApiErrors {
    private List<String> errors;

    public ApiErrors(String error) {
        this.errors = Arrays.asList(error);
    }

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

}