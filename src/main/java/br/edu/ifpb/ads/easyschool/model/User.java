package br.edu.ifpb.ads.easyschool.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class User {
    

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



}
