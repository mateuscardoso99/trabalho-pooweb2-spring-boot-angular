import { EnderecoDto } from "./EnderecoDto";
import { PedidoDto } from "./PedidoDto";
import { UsuarioDto } from "./UsuarioDto";

export class ClienteDto extends UsuarioDto{
    pedidos: PedidoDto[];
}