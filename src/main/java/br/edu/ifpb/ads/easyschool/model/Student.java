package br.edu.ifpb.ads.easyschool.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_students")
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {

        
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    private List<Fee> fees = new ArrayList<>();

}
