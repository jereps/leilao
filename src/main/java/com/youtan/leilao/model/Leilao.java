package com.youtan.leilao.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.AnyDiscriminatorValue;
import org.hibernate.annotations.AnyKeyJavaClass;
import org.hibernate.annotations.ManyToAny;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "leilao")
public class Leilao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 200, nullable = false)
	private String nome;

    @Column( nullable = false)
    private LocalDateTime dataHorarioLeilao;

    @OneToOne()
    @JoinColumn(name = "endereco_id")
    private Endereco enderecoLeilao;

    @ManyToAny
    @Column(name = "item_type")
    @AnyKeyJavaClass(Long.class)
    @AnyDiscriminatorValue(discriminator = "IMOVEL", entity = Imovel.class)
    @AnyDiscriminatorValue(discriminator = "VEICULO", entity = Veiculo.class)
    @JoinTable(
            name = "leilao_itens",
            joinColumns = @JoinColumn(name = "leilao_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Object> itens;

    @Column(length = 10, nullable = false)
    private TipoCategoria categoria;

    @Column(name = "descricao")
    private String descricao;

}
