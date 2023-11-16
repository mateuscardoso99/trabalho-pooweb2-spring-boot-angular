export class PedidoDto{
    id: number;
    descricao: string;
    statusPedido: StatusPedidoDto;
    dataHora: Date;
    estabelecimento: string;
    nomeCliente: string;
    emailCliente: string;

    constructor(){}
}

export enum StatusPedidoDto{
    PENDENTE = 'PENDENTE',
    CANCELADO = 'CANCELADO',
    FINALIZADO = 'FINALIZADO'
}