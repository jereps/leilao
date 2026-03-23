package com.youtan.leilao;

import com.youtan.leilao.model.*;
import com.youtan.leilao.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(ImovelRepository repository, EnderecoRepository enderecoRepository
	, CEPRepository cepRepository, EstadoRepository estadoRepository, CidadeRepository cidadeRepository
	, BairroRepository bairroRepository) {
		return args -> {

			CEP cep = new CEP();
			cep.setCep("12231815");
//			cepRepository.save(cep);

			Estado estado = new Estado();
			estado.setNome("São Paulo");
			estado.setSigla("SP");
//			estadoRepository.save(estado);

			Cidade cidade = new Cidade();
			cidade.setNome("São Paulo");
//			cidade.setEstado(estado);
//			cidadeRepository.saveAndFlush(cidade);

			Bairro bairro = new Bairro();
//			bairro.setCidade(cidade);
			bairro.setNomeBairro("JD Satélite");
//			bairroRepository.saveAndFlush(bairro);

			Endereco endereco = new Endereco();
			endereco.setNumero("12");
			endereco.setRua("rua maria");
			endereco.setComplemento("N/A");
			endereco.setBairro(bairro);
			endereco.setCep(cep);
			endereco.setCidade(cidade);
			endereco.setEstado(estado);
//			enderecoRepository.save(endereco);

			Imovel imovel = new Imovel();

			imovel.setEndereco(endereco);
			imovel.setNome("campos");
			imovel.setTipoImovel(TipoImovel.RESIDENCIAL);
			imovel.setPreco(BigDecimal.valueOf(125.20));
			imovel.setMetragem(50);
			imovel.setNQuartos(3);
			imovel.setNBanheiros(1);

//			repository.saveAndFlush(imovel);
			repository.save(imovel);
		};
	}
}
