import { CadastroEndereco } from "./CadastroEndereco";

export class CadastroCliente{
    nome: string;
    email: string;
    senha: string;
    endereco: CadastroEndereco;

    constructor(nome: string, email: string, senha: string, endereco: CadastroEndereco){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }
}