export class PedidoDto{
    id: number;
    descricao: string;
    statusPedido: StatusPedido;
    dataHora: Date;
    estabelecimento: string;
    nomeCliente: string;
    emailCliente: string;

    constructor(){}
}

export enum StatusPedido{
    PENDENTE = 'PENDENTE',
    CANCELADO = 'CANCELADO',
    FINALIZADO = 'FINALIZADO'
}