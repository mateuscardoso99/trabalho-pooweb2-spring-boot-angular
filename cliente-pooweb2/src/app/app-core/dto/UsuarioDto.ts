import { EnderecoDto } from "./EnderecoDto";

export class UsuarioDto{
    id: number;
    nome: string;
    email: string;
    ativo: boolean;
    endereco: EnderecoDto;
    permissoes: Permissao[];

    constructor(){}
}

export enum Permissao{
    ADMIN_SISTEMA = "ADMIN_SISTEMA",
    ADMIN_EMPRESA = "ADMIN_EMPRESA",
    ADMIN_ESTABELECIMENTO = "ADMIN_ESTABELECIMENTO",
    CLIENTE = "CLIENTE"
}