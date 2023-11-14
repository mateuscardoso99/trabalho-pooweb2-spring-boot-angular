import { EnderecoDto } from "./EnderecoDto";
import { PermissaoDto } from "./PermissaoDto";

export class UsuarioDto{
    id: number;
    nome: string;
    email: string;
    ativo: boolean;
    endereco: EnderecoDto;
    permissoes: PermissaoDto[];

    constructor(id: number,nome: string,email: string,ativo: boolean,endereco: EnderecoDto,permissoes: PermissaoDto[]){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.ativo = ativo;
        this.endereco = endereco;
        this.permissoes = permissoes;
    }
}