import { Itens } from './itens';
export interface LeilaoSubmit {
      id?: number,
      nome: string;
      dataHorarioLeilao: Date;
      categoria: string;
      descricao: string;

      enderecoLeilaoDTO?: {
        id?: number,
        numero: string;
        rua: string;
        cep: {
          id?: number,
          cep: string;
        };
        bairro: {
          id?: number,
          nomeBairro: string;
        };
        cidade: {
          id?: number,
          nome: string;
        };
        estado: {
          id?: number,
          nome: string;
          sigla: string;
        };
      };

      financeiraDTO?: {
        id?: number;
        cnpj: number;
        codigoCompensacao: number;
        razaoSocial: string;
      };

      mercadoria?: [{
      tipo: string,
			id: number,
			placa: string,
			marcaModelo: string,
			anoFabricaca: string,
			Cor: string,
			tipoCombustivel: string,
			tipoVeiculo: string,
			nPorta: string,
			qtdPassageiro: string,
			valor: string,
      }];


}
