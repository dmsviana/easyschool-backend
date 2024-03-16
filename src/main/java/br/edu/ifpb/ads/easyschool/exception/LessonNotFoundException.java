package br.edu.ifpb.ads.easyschool.exception;

public class LessonNotFoundException extends RuntimeException {

    public LessonNotFoundException(String message) {
        super(message);
    }

}
