import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { PedidoDto } from "../dto/PedidoDto";

export class EstabelecimentoService{
    private static readonly URL = environment.urlApi + "/estabelecimento";

    constructor(private http: HttpClient){}

    find(id: number){
        return this.http.get(URL + "/" +id);
    }

    getPedidos(idEstab: number){
        return this.http.get<PedidoDto[]>(URL + "/" + idEstab + "/pedidos")
    }

    findPedido(idEstab: number, idPedido: number){
        return this.http.get<PedidoDto[]>(URL + "/" + idEstab + "/pedidos/" +idPedido)
    }

    mudarStatusPedido(idEstab: number, idPedido: number, statusPedido: string){
        return this.http.put(URL + "/" + idEstab + "/pedidos/" +idPedido, {status: statusPedido})
    }
}