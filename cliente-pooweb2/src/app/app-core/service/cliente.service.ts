import { environment } from "../../../environments/environment"

export class ClienteService{
    private static readonly URL = environment.urlApi + "cliente/";

    constructor(){}
}