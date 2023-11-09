import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { CadastrarPedidoComponent } from "./cadastrar-pedido.component";
import { CadastrarPedidoRoutingModule } from "./cadastrar-pedido.routing.module";

@NgModule({
    declarations:[
        CadastrarPedidoComponent
    ],
    imports:[
        CommonModule,
        CadastrarPedidoRoutingModule
    ]
})

export class CadastrarPedidoModule{}