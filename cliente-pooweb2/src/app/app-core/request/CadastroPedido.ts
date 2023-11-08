export class CadastroPedido{
    descricao: string;
    idEstabelecimento: number;

    constructor(descricao: string, idEstabelecimento: number){
        this.descricao = descricao;
        this.idEstabelecimento = idEstabelecimento;
    }
}