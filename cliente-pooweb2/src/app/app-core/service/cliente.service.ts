import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { environment } from "../../../environments/environment"
import { CadastroCliente } from "../request/CadastroCliente";
import { PedidoDto } from "../dto/PedidoDto";
import { CadastroPedido } from "../request/CadastroPedido";

@Injectable({ providedIn: 'root' })
export class ClienteService{
    private static readonly URL = environment.urlApi + "/cliente";

    constructor(private http: HttpClient){}

    find(){
        return this.http.get(URL.toString());
    }

    update(dados: CadastroCliente){
        return this.http.put(URL.toString(), dados);
    }

    desativarConta(){
        return this.http.delete(URL + "/desativar");
    }

    ativarConta(){
        return this.http.put(URL + "/ativar",{});
    }

    getPedidos(){
        return this.http.get<PedidoDto[]>(URL + "/pedidos")
    }

    findPedido(id: number){
        return this.http.get(URL + "/pedidos/" +id)
    }

    salvarPedido(pedido: CadastroPedido){
        return this.http.post(URL + "/pedidos", pedido)
    }

    desativarPedido(id: number){
        return this.http.delete(URL + "/pedidos/desativar/" +id);
    }

    ativarPedido(id: number){
        return this.http.put(URL + "/pedidos/ativar/" +id, {});
    }
}