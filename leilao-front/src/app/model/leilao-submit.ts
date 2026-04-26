export interface LeilaoSubmit {
      id?: number,
      nome: string;
      dataHorarioLeilao: Date;
      enderecoLeilaoDTO?: {
        numero: string;
        rua: string;
        cep: {
          cep: string;
        };
        bairro: {
          nomeBairro: string;
        };
        cidade: {
          nome: string;
        };
        estado: {
          nome: string;
          sigla: string;
        };
      };
      categoria: string;
      descricao: string;

}
