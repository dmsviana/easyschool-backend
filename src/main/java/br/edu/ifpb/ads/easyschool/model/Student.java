package br.edu.ifpb.ads.easyschool.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //controlar a visibilidade de serialização e deserialização de objetos para json (nesse caso, ignorar o campo 'senha' ao devolver a resposta no json)
    private String password;

    @Column(length = 100, nullable = false)
    private String username;

    @Column(nullable = false)
    private String registration;

    @Column(length = 11, nullable = false)
    private String phoneNumber;

    private boolean admin;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Fee> fees = new ArrayList<>();

}
