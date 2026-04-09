package com.youtan.leilao.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyDiscriminatorValue;
import org.hibernate.annotations.AnyKeyJavaClass;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "lance_historico")
public class LanceHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Any
    @Column(name = "item_type")
    @AnyKeyJavaClass(Long.class)
    @AnyDiscriminatorValue(discriminator = "IMOVEL", entity = Imovel.class)
    @AnyDiscriminatorValue(discriminator = "VEICULO", entity = Veiculo.class)
    @JoinColumn(name = "item_id")
    private Object item;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "cliente")
    private String cliente;

}
