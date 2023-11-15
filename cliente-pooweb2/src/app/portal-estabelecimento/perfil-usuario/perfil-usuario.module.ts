import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ViewWrapperModule } from "../components/view-wrapper.module";
import { PerfilUsuarioComponent } from "./perfil-usuario.component";
import { PerfilUsuarioRoutingModule } from "./perfil-usuario.routing.module";

@NgModule({
    declarations:[
        PerfilUsuarioComponent
    ],
    imports:[
        CommonModule,
        PerfilUsuarioRoutingModule,
        ViewWrapperModule
    ]
})

export class PerfilUsuarioModule{}