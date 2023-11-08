import { EnderecoDto } from "./EnderecoDto";

export class EmpresaDto{
    id: number;
    nome: string;
    razaoSocial: string;
    ativo: boolean;
    endereco: EnderecoDto;

    constructor(
        id: number,
        nome: string,
        razaoSocial: string,
        ativo: boolean,
        endereco: EnderecoDto
    ){
        this.id = id;
        this.nome = nome;
        this.razaoSocial = razaoSocial;
        this.ativo = ativo;
        this.endereco = endereco;
    }
}