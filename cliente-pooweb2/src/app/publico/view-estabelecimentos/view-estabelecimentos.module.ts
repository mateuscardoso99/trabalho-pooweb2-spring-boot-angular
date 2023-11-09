import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ViewEstabelecimentosComponent } from "./view-estabelecimentos.component";
import { ViewEstabelecimentosRoutingModule } from "./view-estabelecimentos.routing.module";

@NgModule({
    declarations:[
        ViewEstabelecimentosComponent
    ],
    imports:[
        CommonModule,
        ViewEstabelecimentosRoutingModule
    ]
})

export class ViewEstabelecimentosModule{}