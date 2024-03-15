package br.edu.ifpb.ads.easyschool.security.services;

import br.edu.ifpb.ads.easyschool.model.Student;
import br.edu.ifpb.ads.easyschool.model.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public record UserDetailsImpl(Long id, String username, String email, @JsonIgnore String password,
                              Collection<? extends GrantedAuthority> authorities) implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    public static UserDetailsImpl build(User userEntity, List<GrantedAuthority> authorityList) {
        return new UserDetailsImpl(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail(), userEntity.getPassword(), authorityList);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
