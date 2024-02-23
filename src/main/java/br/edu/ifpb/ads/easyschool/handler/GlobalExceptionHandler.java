package br.edu.ifpb.ads.easyschool.handler;

import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.edu.ifpb.ads.easyschool.exception.StudentAlreadyExistsException;
import br.edu.ifpb.ads.easyschool.exception.StudentNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBadRequestException(BadRequestException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleStudentNotFoundException(StudentNotFoundException ex){
        return new ApiErrors(ex.getMessage());
    }
    
    @ExceptionHandler(StudentAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleStudentAlreadyExistsException(StudentAlreadyExistsException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        return new ApiErrors(errors);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrors handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new ApiErrors(ex.getMessage());
    }

}
