export class PedidoDto{
    id: number;
    descricao: string;
    statusPedido: string;
    dataHora: Date;
    estabelecimento: string;
    nomeCliente: string;
    emailCliente: string;

    constructor(
        id: number,
        descricao: string,
        statusPedido: string,
        dataHora: Date,
        estabelecimento: string,
        nomeCliente: string,
        emailCliente: string
    ){
        this.id = id;
        this.descricao = descricao;
        this.statusPedido = statusPedido;
        this.dataHora = dataHora;
        this.estabelecimento = estabelecimento;
        this.nomeCliente = nomeCliente;
        this.emailCliente = emailCliente;
    }
}