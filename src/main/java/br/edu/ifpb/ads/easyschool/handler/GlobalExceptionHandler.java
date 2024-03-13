package br.edu.ifpb.ads.easyschool.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NoPermissionException;

import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import br.edu.ifpb.ads.easyschool.exception.PermissionDeniedException;
import br.edu.ifpb.ads.easyschool.exception.StudentAlreadyExistsException;
import br.edu.ifpb.ads.easyschool.exception.StudentNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleBadRequestException(BadRequestException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors handleStudentNotFoundException(StudentNotFoundException ex){
        return new ApiErrors(ex.getMessage());
    }
    
    @ExceptionHandler(StudentAlreadyExistsException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleStudentAlreadyExistsException(StudentAlreadyExistsException ex){
        return new ApiErrors(ex.getMessage());
    }


    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ExceptionFilters dataIntegrationViolation(final DataIntegrityViolationException ex) {
        String result = null;

        final Pattern pattern = Pattern.compile("Key (.*)");
        final Matcher comparator = pattern.matcher(ex.getCause().getCause().getMessage());

        if (comparator.find()) {
            result = comparator.group(1);
        }

        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(result)
                .devMsg(ex.getClass().getName())
                .status(BAD_REQUEST.value())
                .title("Data Violation")
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ExceptionFilters missingServletRequestPartException(final MissingServletRequestPartException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .devMsg(ex.getClass().getName())
                .status(BAD_REQUEST.value())
                .title("Image not found!")
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ExceptionFilters constraintViolationException(ConstraintViolationException ex) {

        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            message.append(violation.getMessage().concat(";"));
        }

        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(message.toString())
                .devMsg(ex.getClass().getName())
                .status(BAD_REQUEST.value())
                .title("Invalid Arguments!")
                .build();
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FileNotFoundException.class)
    public ExceptionFilters fileNotFoundException(FileNotFoundException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .devMsg(ex.getClass().getName())
                .status(BAD_REQUEST.value())
                .title("Data violation")
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(NoPermissionException.class)
    public ExceptionFilters noPermissionException(NoPermissionException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .devMsg(ex.getClass().getName())
                .status(BAD_REQUEST.value())
                .title("NoPermissionException")
                .build();
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(PermissionDeniedException.class)
    public ExceptionFilters permissionDenied(final PermissionDeniedException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .devMsg(ex.getClass().getName())
                .status(UNAUTHORIZED.value())
                .title("Permission Denied!")
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ExceptionFilters methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .devMsg(ex.getClass().getName())
                .status(BAD_REQUEST.value())
                .title("MethodArgumentTypeMismatchException")
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ExceptionFilters emptyResultDataAccessException(EmptyResultDataAccessException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .devMsg(ex.getClass().getName())
                .status(BAD_REQUEST.value())
                .title("EmptyResultDataAccessException")
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ExceptionFilters httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .devMsg(ex.getClass().getName())
                .status(BAD_REQUEST.value())
                .title("HttpRequestMethodNotSupportedException")
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionFilters methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .devMsg(ex.getClass().getName())
                .status(BAD_REQUEST.value())
                .title("MethodArgumentNotValidException")
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ExceptionFilters illegalArgumentException(IllegalArgumentException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .devMsg(ex.getClass().getName())
                .status(BAD_REQUEST.value())
                .title("IllegalArgumentException")
                .build();
    }

}
