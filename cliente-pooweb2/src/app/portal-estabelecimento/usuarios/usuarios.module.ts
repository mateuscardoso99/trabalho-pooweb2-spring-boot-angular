import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { EditarUsuarioComponent } from "./editar/editar-usuario.component";
import { UsuariosRoutingModule } from "./usuarios.routing.module";
import { ViewWrapperModule } from "../components/view-wrapper.module";
import { VisualizarUsuarioComponent } from "./visualizar/visualizar-usuario.component";
import { UsuariosComponent } from "./usuarios.component";
import { RouterModule } from "@angular/router";
import { ReactiveFormsModule } from "@angular/forms";

@NgModule({
    declarations:[
        UsuariosComponent,
        VisualizarUsuarioComponent,
        EditarUsuarioComponent
    ],
    imports:[
        CommonModule,
        UsuariosRoutingModule,
        ReactiveFormsModule,
        ViewWrapperModule,
        RouterModule
    ]
})

export class UsuariosModule{}