import { UsuarioDto } from "./UsuarioDto";

export class TokenDto{
    token: string;
    usuario: UsuarioDto;

    constructor(token: string,usuario: UsuarioDto){
        this.token = token;
        this.usuario = usuario;
    }
}