package com.youtan.leilao.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 20, nullable = false)
    private String numero;

    @Column(length = 200, nullable = false)
    private String rua;

    @Column(length = 200)
    private String complemento;

    @OneToMany(mappedBy = "endereco",fetch = FetchType.LAZY)
//    @JoinColumn(name = "imovel_id")
    private List<Imovel> imovel;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "cep_id")
    private CEP cep;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "bairro_id")
    private Bairro bairro;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "estado_id")
    private Estado estado;

}
