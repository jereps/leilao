package com.youtan.leilao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "veiculos")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 7, nullable = false)
    private String placa;

    @Column(length = 200, nullable = false)
    private String marcaModelo;

    @Column(length = 4, nullable = false)
    private Integer anoFabricacao;

    @Column(length = 20, nullable = false)
    private String Cor;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCombustivel tipoCombustivel;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoVeículo tipoVeiculo;

    @Column(length = 10, nullable = false)
    private Integer nPortas;

    @Column(length = 10, nullable = false)
    private Integer qtdPassageiros;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00",locale = "pt-BR")
    @Column(precision = 25, scale = 2)
    private BigDecimal valor;

    public void incrementarValor(BigDecimal valor){
        this.valor = this.valor.add(valor);
    }

}
