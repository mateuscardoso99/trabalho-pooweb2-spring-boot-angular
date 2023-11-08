import { EnderecoDto } from "./EnderecoDto";

export class UsuarioDto{
    id: number;
    nome: string;
    email: string;
    ativo: boolean;
    endereco: EnderecoDto;
    permissoes: string[];

    constructor(id: number,nome: string,email: string,ativo: boolean,endereco: EnderecoDto,permissoes: string[]){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.ativo = ativo;
        this.endereco = endereco;
        this.permissoes = permissoes;
    }
}