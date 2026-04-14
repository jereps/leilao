package com.youtan.leilao.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyDiscriminatorValue;
import org.hibernate.annotations.AnyKeyJavaClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "lance_historico")
public class LanceHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor", precision = 25, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leilao_id", nullable = false)
    private Leilao leilao;

    @Any
    @Column(name = "item_type", length = 20)
    @AnyKeyJavaClass(Long.class)
    @AnyDiscriminatorValue(discriminator = "IMOVEL", entity = Imovel.class)
    @AnyDiscriminatorValue(discriminator = "VEICULO", entity = Veiculo.class)
    @JoinColumn(name = "item_id")
    private Object item;

    @PrePersist
    protected void onCreate() {
        this.dataHora = LocalDateTime.now();
    }

}
