import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { CadastroPedido } from "src/app/app-core/request/CadastroPedido";
import { ClienteService } from "src/app/app-core/service/cliente.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-cadastrar-pedido',
    templateUrl: './cadastrar-pedido.component.html',
    styleUrls: ['./cadastrar-pedido.component.css']
})
export class CadastrarPedidoComponent implements OnInit{
    cadastroPedido: CadastroPedido;
    idEstab: number;

    constructor(private route: ActivatedRoute, private clienteService: ClienteService, private router: Router) {
        this.cadastroPedido = new CadastroPedido();
    }

    ngOnInit(): void {
        this.route.queryParams.subscribe(params => {
            this.cadastroPedido.idEstabelecimento = params.idEstab;
        });
        if(!this.cadastroPedido.idEstabelecimento){
            this.router.navigate(['/usuario/inicio'])
        }
    }

    salvarPedido(){
        this.clienteService.salvarPedido(this.cadastroPedido).subscribe({
            next: () => {
                Swal.fire({
                    title: "Sucesso",
                    text: "Pedido cadastrado com sucesso",
                    icon: "success"
                });
                this.router.navigate(['/usuario/inicio'])
            },
            error: err => {
                Swal.fire({
                    title: err?.error?.message,
                    text: "Houve um erro ao cadastrar o pedido",
                    icon: "error"
                });
            }
        })
    }
}