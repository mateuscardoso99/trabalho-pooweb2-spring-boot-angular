import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { PedidoDto } from "../dto/PedidoDto";
import { Injectable } from "@angular/core";
import { UsuarioAdminEstabelecimentoDto } from "../dto/UsuarioAdminEstabelecimentoDto";

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

    mudarStatusPedido(idEstab: number, idPedido: number, statusPedido: string){
        return this.http.put(EstabelecimentoService.URL + "/" + idEstab + "/pedidos/" +idPedido, {status: statusPedido})
    }

    getUsuarios(idEstab: number){
        return this.http.get<UsuarioAdminEstabelecimentoDto[]>(EstabelecimentoService.URL + "/" + idEstab + "/usuarios")
    }
}