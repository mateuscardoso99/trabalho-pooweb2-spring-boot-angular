import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ViewWrapperModule } from "../components/view-wrapper.module";
import { DadosEstabelecimentoComponent } from "./dados-estabelecimento.component";
import { DadosEstabelecimentoRoutingModule } from "./dados-estabelecimento.routing.module";

@NgModule({
    declarations:[
        DadosEstabelecimentoComponent
    ],
    imports:[
        CommonModule,
        DadosEstabelecimentoRoutingModule,
        ViewWrapperModule
    ]
})

export class DadosEstabelecimentoModule{}