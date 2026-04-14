package com.youtan.leilao;

import com.youtan.leilao.model.*;
import com.youtan.leilao.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

//	@Bean
//	CommandLineRunner initDatabase(ImovelRepository repository, EnderecoRepository enderecoRepository
//	, CEPRepository cepRepository, EstadoRepository estadoRepository, CidadeRepository cidadeRepository
//	, BairroRepository bairroRepository, VeiculoRepository veiculoRepository,
//	LeilaoRepository leilaoRepository) {
//		return args -> {
//
//			CEP cep = new CEP();
//			cep.setCep("12231815");
//			cepRepository.save(cep);
//
//			Estado estado = new Estado();
//			estado.setNome("São Paulo");
//			estado.setSigla("SP");
//			estadoRepository.save(estado);
//
//			Cidade cidade = new Cidade();
//			cidade.setNome("São Paulo");
////			cidade.setEstado(estado);
//			cidadeRepository.save(cidade);
//
//			Bairro bairro = new Bairro();
////			bairro.setCidade(cidade);
//			bairro.setNomeBairro("JD Satélite");
//			bairroRepository.save(bairro);
//
//			Endereco endereco = new Endereco();
//			endereco.setNumero("12");
//			endereco.setRua("rua maria");
//			endereco.setComplemento("N/A");
//			endereco.setBairro(bairro);
//			endereco.setCep(cep);
//			endereco.setCidade(cidade);
//			endereco.setEstado(estado);
//			enderecoRepository.save(endereco);
//
//			Imovel imovel = new Imovel();
//			Imovel imovel2 = new Imovel();
//
//			imovel.setEndereco(endereco);
//			imovel.setNome("campos");
//			imovel.setTipoImovel(TipoImovel.RESIDENCIAL);
//			imovel.setPreco(BigDecimal.valueOf(125.20));
//			imovel.setMetragem(50);
//			imovel.setNQuartos(3);
//			imovel.setNBanheiros(1);
//
//			imovel2.setEndereco(endereco);
//			imovel2.setNome("campos2");
//			imovel2.setTipoImovel(TipoImovel.COMERCIAL);
//			imovel2.setPreco(BigDecimal.valueOf(123.20));
//			imovel2.setMetragem(20);
//			imovel2.setNQuartos(0);
//			imovel2.setNBanheiros(2);
//
////			repository.saveAndFlush(imovel);
//			repository.save(imovel);
//			repository.save(imovel2);
//
//			Veiculo veiculo = new Veiculo();
//			veiculo.setAnoFabricacao(1900);
//			veiculo.setCor("amarela");
//			veiculo.setPlaca("hq12312");
//			veiculo.setMarcaModelo("Uno Fiat");
//			veiculo.setNPortas(2);
//			veiculo.setQtdPassageiros(5);
//			veiculo.setValor(BigDecimal.valueOf(12.20));
//			veiculo.setTipoCombustível(TipoCombustivel.GASOLINA);
//			veiculo.setTipoVeículo(TipoVeículo.PASSEIO);
//			veiculoRepository.save(veiculo);
//
//			Leilao leilao = new Leilao();
//			leilao.setItens(Arrays.asList(veiculo));
//			leilao.setEnderecoLeilao(endereco);
//			leilao.setDescricao("leilao teste");
//			leilao.setNome("novo");
//			leilao.setCategoria(TipoCategoria.VEICULO);
//			leilao.setDataHorarioLeilao(LocalDateTime.now());
//			leilaoRepository.save(leilao);
//
//		};
//	}
}
