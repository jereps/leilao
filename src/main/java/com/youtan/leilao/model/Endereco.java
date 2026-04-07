package com.youtan.leilao.model;

import jakarta.persistence.*;
import lombok.Data;

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

    @ManyToOne
    @JoinColumn(name = "cep_id")
    private CEP cep;

    @ManyToOne
    @JoinColumn(name = "bairro_id")
    private Bairro bairro;

    @ManyToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;

}
