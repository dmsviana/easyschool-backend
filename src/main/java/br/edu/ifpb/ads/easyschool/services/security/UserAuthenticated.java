package br.edu.ifpb.ads.easyschool.services.security;

import java.util.Collection;
import java.util.List;

import br.edu.ifpb.ads.easyschool.model.types.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.ifpb.ads.easyschool.model.Student;

public class UserAuthenticated implements UserDetails {
    private final Student student;

    public UserAuthenticated(Student student) {
        this.student = student;
    }

    @Override
    public String getUsername() {
        return student.getUsername();
    }

    @Override
    public String getPassword() {
        return student.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "read");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
