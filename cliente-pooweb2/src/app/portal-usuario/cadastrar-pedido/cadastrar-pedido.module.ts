import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { CadastrarPedidoComponent } from "./cadastrar-pedido.component";
import { CadastrarPedidoRoutingModule } from "./cadastrar-pedido.routing.module";
import { ViewWrapperModule } from "../components/view-wrapper.module";
import { FormsModule } from "@angular/forms";

@NgModule({
    declarations:[
        CadastrarPedidoComponent
    ],
    imports:[
        CommonModule,
        FormsModule,
        CadastrarPedidoRoutingModule,
        ViewWrapperModule
    ]
})

export class CadastrarPedidoModule{}