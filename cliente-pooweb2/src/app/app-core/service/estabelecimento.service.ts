import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { PedidoDto, StatusPedidoDto } from "../dto/PedidoDto";
import { Injectable } from "@angular/core";
import { UsuarioAdminEstabelecimentoDto } from "../dto/UsuarioAdminEstabelecimentoDto";
import { CadastroUsuarioAdmin } from "../request/CadastroUsuarioAdmin";

@Injectable({ providedIn: 'root' })
export class EstabelecimentoService{
    private static readonly URL = environment.urlApi + "/estabelecimento";

    constructor(private http: HttpClient){}

    find(id: number){
        return this.http.get(EstabelecimentoService.URL + "/" +id);
    }

    findAll(){
        return this.http.get(environment.urlApi + "/public/estabelecimentos");
    }

    getPedidos(idEstab: number){
        return this.http.get<PedidoDto[]>(EstabelecimentoService.URL + "/" + idEstab + "/pedidos")
    }

    findPedido(idEstab: number, idPedido: number){
        return this.http.get<PedidoDto[]>(EstabelecimentoService.URL + "/" + idEstab + "/pedidos/" +idPedido)
    }

    mudarStatusPedido(idEstab: number, idPedido: number, statusPedido: StatusPedidoDto){
        return this.http.put(EstabelecimentoService.URL + "/" + idEstab + "/pedidos/" +idPedido, {status: statusPedido})
    }

    findUsuario(idEstab: number, idUsuario: number){
        return this.http.get<UsuarioAdminEstabelecimentoDto>(EstabelecimentoService.URL + "/" + idEstab + "/buscar-usuario/" + idUsuario)
    }

    getUsuarios(idEstab: number){
        return this.http.get<UsuarioAdminEstabelecimentoDto[]>(EstabelecimentoService.URL + "/" + idEstab + "/usuarios")
    }

    cadastrarUsuario(idEstab: number, usuario: CadastroUsuarioAdmin){
        return this.http.post(EstabelecimentoService.URL + "/" + idEstab + "/usuario", usuario);
    }

    updateUsuario(idEstab: number, idUsuario: number, usuario: CadastroUsuarioAdmin){
        return this.http.put(EstabelecimentoService.URL + "/" + idEstab + "/update-usuario/" + idUsuario, usuario);
    }

    desativarUsuario(idEstab: number, idUsuario: number){
        return this.http.delete(EstabelecimentoService.URL + "/" + idEstab + "/desativar-usuario/" + idUsuario);
    }

    ativarUsuario(idEstab: number, idUsuario: number){
        return this.http.put(EstabelecimentoService.URL + "/" + idEstab + "/ativar-usuario/" + idUsuario,{});
    }
}