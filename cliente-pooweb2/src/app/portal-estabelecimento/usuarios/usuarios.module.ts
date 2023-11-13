import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { EditarUsuarioComponent } from "./editar/editar-usuario.component";
import { UsuariosRoutingModule } from "./usuarios.routing.module";
import { ViewWrapperModule } from "../components/view-wrapper.module";
import { VisualizarUsuarioComponent } from "./visualizar/visualizar-usuario.component";

@NgModule({
    declarations:[
        VisualizarUsuarioComponent,
        EditarUsuarioComponent
    ],
    imports:[
        CommonModule,
        UsuariosRoutingModule,
        ViewWrapperModule
    ]
})

export class UsuariosModule{}