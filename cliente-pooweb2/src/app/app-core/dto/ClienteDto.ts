import { EnderecoDto } from "./EnderecoDto";
import { PedidoDto } from "./PedidoDto";
import { UsuarioDto } from "./UsuarioDto";

export class ClienteDto extends UsuarioDto{
    pedidos: PedidoDto[];

    constructor(id: number,nome: string,email: string,ativo: boolean,endereco: EnderecoDto,permissoes: string[],pedidos: PedidoDto[]){
        super(id,nome,email,ativo,endereco,permissoes);
        this.pedidos = pedidos;
    }
}