import { environment } from "src/environments/environment";

export class EstabelecimentoService{
    private static readonly URL = environment.urlApi + "cliente/";

    constructor(){}
}