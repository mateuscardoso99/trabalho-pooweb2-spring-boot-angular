import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { CadastrarPedidoComponent } from "./cadastrar-pedido.component";
import { CadastrarPedidoRoutingModule } from "./cadastrar-pedido.routing.module";
import { ViewWrapperModule } from "../components/view-wrapper.module";

@NgModule({
    declarations:[
        CadastrarPedidoComponent
    ],
    imports:[
        CommonModule,
        CadastrarPedidoRoutingModule,
        ViewWrapperModule
    ]
})

export class CadastrarPedidoModule{}