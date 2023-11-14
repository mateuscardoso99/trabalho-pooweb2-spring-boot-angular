import { UsuarioDto } from "./UsuarioDto";

export class UsuarioAdminEstabelecimentoDto extends UsuarioDto{
    idEstabelecimento: number;
    estabelecimento: string
}