export interface ImovelSubmit {
      id?: number,
      nome: string;
      tipoImovel: string;
      preco: string;

      endereco?: {
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

      metragem: string;
      nQuartos: string;
      nBanheiros: string;
      tipo: 'IMOVEL';


}
