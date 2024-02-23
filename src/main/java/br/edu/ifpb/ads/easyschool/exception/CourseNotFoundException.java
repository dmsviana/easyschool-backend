package br.edu.ifpb.ads.easyschool.exception;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(String message) {
        super(message);
    }
    
}
