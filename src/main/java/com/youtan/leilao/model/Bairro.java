package com.youtan.leilao.model;

import jakarta.persistence.*;
import lombok.Data;

//@Data
@Entity
@Table(name = "bairro")
public class Bairro {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeBairro() {
        return nomeBairro;
    }

    public void setNomeBairro(String nomeBairro) {
        this.nomeBairro = nomeBairro;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_bairro", length = 200, nullable = false)
    private String nomeBairro;

}
