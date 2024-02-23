package br.edu.ifpb.ads.easyschool.model.types;

public enum UserRole {

    ADMIN("admin"),
    STUDENT("student"),
    TEACHER("teacher");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

}
