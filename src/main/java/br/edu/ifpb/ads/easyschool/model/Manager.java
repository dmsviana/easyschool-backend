package br.edu.ifpb.ads.easyschool.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_managers")
@Getter
@Setter
public class Manager extends User {

    

}
