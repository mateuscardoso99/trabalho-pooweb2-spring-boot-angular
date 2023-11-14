import { EnderecoDto } from "./EnderecoDto";
import { PermissaoDto } from "./PermissaoDto";

export class UsuarioDto{
    id: number;
    nome: string;
    email: string;
    ativo: boolean;
    endereco: EnderecoDto;
    permissoes: PermissaoDto[];

    constructor(){}
}