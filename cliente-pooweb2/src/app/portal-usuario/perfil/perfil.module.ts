import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ViewWrapperModule } from "../components/view-wrapper.module";
import { PerfilComponent } from "./perfil.component";
import { PerfilRoutingModule } from "./perfil.routing.module";
import { FormCriarContaModule } from "src/app/app-core/components/form-criar-conta/form-criar-conta.module";

@NgModule({
    declarations:[
        PerfilComponent
    ],
    imports:[
        CommonModule,
        PerfilRoutingModule,
        ViewWrapperModule,
        FormCriarContaModule
    ]
})

export class PerfilModule{}