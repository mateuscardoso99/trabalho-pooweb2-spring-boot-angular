import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { environment } from "../../../environments/environment"
import { CadastroCliente } from "../request/CadastroCliente";
import { PedidoDto } from "../dto/PedidoDto";
import { CadastroPedido } from "../request/CadastroPedido";

@Injectable({ providedIn: 'root' })
export class ClienteService{
    private static readonly URL:string = environment.urlApi + "/cliente";

    constructor(private http: HttpClient){}

    find(){
        return this.http.get(ClienteService.URL);
    }

    update(dados: CadastroCliente){
        return this.http.put(ClienteService.URL, dados);
    }

    desativarConta(){
        return this.http.delete(ClienteService.URL + "/desativar");
    }

    ativarConta(){
        return this.http.put(ClienteService.URL + "/ativar",{});
    }

    getPedidos(){
        return this.http.get<PedidoDto[]>(ClienteService.URL + "/pedidos")
    }

    findPedido(id: number){
        return this.http.get(ClienteService.URL + "/pedidos/" +id)
    }

    salvarPedido(pedido: CadastroPedido){
        return this.http.post(ClienteService.URL + "/pedidos", pedido)
    }

    desativarPedido(id: number){
        return this.http.delete(ClienteService.URL + "/pedidos/desativar/" +id);
    }

    ativarPedido(id: number){
        return this.http.put(ClienteService.URL + "/pedidos/ativar/" +id, {});
    }
}