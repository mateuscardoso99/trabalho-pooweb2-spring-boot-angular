import { Component, OnInit } from "@angular/core";
import { PedidoDto } from "src/app/app-core/dto/PedidoDto";
import { ClienteService } from "src/app/app-core/service/cliente.service";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
    pedidos: PedidoDto[] = [];
    
    constructor(private clienteService: ClienteService){}

    ngOnInit(): void {
        this.clienteService.getPedidos().subscribe({
            next: (response:any) => {
                this.pedidos = response.data as PedidoDto[]
            },
            error: err => {
                console.log(err)
            }
        })
    }    
}