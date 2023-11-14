import { ClienteDto } from "./ClienteDto";
import { UsuarioAdminEstabelecimentoDto } from "./UsuarioAdminEstabelecimentoDto";

export class TokenDto{
    token: string;
    usuario: ClienteDto | UsuarioAdminEstabelecimentoDto;

    constructor(){}
}