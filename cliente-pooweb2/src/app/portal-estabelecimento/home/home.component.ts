import { Component, OnInit } from "@angular/core";
import { PedidoDto, StatusPedidoDto } from "src/app/app-core/dto/PedidoDto";
import { UsuarioAdminEstabelecimentoDto } from "src/app/app-core/dto/UsuarioAdminEstabelecimentoDto";
import { EstabelecimentoService } from "src/app/app-core/service/estabelecimento.service";
import { StorageService } from "src/app/app-core/service/storage.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
    pedidos: PedidoDto[] = [];
    usuario: UsuarioAdminEstabelecimentoDto;

    constructor(private estabelecimentoService: EstabelecimentoService, private storageService: StorageService){
        this.usuario = storageService.getUser()?.usuario as UsuarioAdminEstabelecimentoDto
    }

    ngOnInit(): void {
        this.getPedidos();
    }

    getPedidos(){
        this.estabelecimentoService.getPedidos(this.usuario?.idEstabelecimento as number).subscribe({
            next: (resposta:any) => {
                this.pedidos = resposta.data as PedidoDto[]
            },
            error: err => {
                console.log(err)
            }
        })
    }

    mudarStatusPedido(pedido: PedidoDto, statusPedido: string){
        this.estabelecimentoService.mudarStatusPedido(this.usuario.idEstabelecimento, pedido.id, statusPedido as StatusPedidoDto).subscribe({
            next: () => {
                Swal.fire({
                    title: "Sucesso",
                    text: "Pedido atualizado com sucesso",
                    icon: "success"
                });
                pedido.statusPedido = statusPedido as StatusPedidoDto
            },
            error: () => {
                Swal.fire({
                    title: "Erro",
                    text: "Erro ao atualizar pedido",
                    icon: "error"
                });
            }
        })
    }
}