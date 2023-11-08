import { EmpresaDto } from "./EmpresaDto";
import { EnderecoDto } from "./EnderecoDto";

export class EstabelecimentoDto{
    id: number;
    nome: string;
    razaoSocial: string;
    horarioFuncionamento: string;
    ativo: boolean;
    endereco: EnderecoDto;
    empresa: EmpresaDto;

    constructor(
        id: number,
        nome: string,
        razaoSocial: string,
        horarioFuncionamento: string,
        ativo: boolean,
        endereco: EnderecoDto,
        empresa: EmpresaDto
    ){
        this.id = id;
        this.nome = nome;
        this.razaoSocial = razaoSocial;
        this.horarioFuncionamento = horarioFuncionamento;
        this.ativo = ativo;
        this.endereco = endereco;
        this.empresa = empresa;
    }
}