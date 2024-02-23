package br.edu.ifpb.ads.easyschool.model;

import br.edu.ifpb.ads.easyschool.model.types.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Table(name = "tb_students")
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(length = 100, nullable = false)
    private String username;

    @Column(nullable = false)
    private String registration;

    @Column(nullable = false)
    private String phoneNumber;


}
