import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ViewWrapperModule } from "../components/view-wrapper.module";
import { DadosEstabelecimentoComponent } from "./dados-estabelecimento.component";
import { DadosEstabelecimentoRoutingModule } from "./dados-estabelecimento.routing.module";
import { ReactiveFormsModule } from "@angular/forms";

@NgModule({
    declarations:[
        DadosEstabelecimentoComponent
    ],
    imports:[
        CommonModule,
        DadosEstabelecimentoRoutingModule,
        ReactiveFormsModule,
        ViewWrapperModule
    ]
})

export class DadosEstabelecimentoModule{}