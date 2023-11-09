import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { CreateUsuarioComponent } from "./create/create-usuario.component";
import { EditarUsuarioComponent } from "./editar/editar-usuario.component";
import { UsuariosRoutingModule } from "./usuarios.routing.module";

@NgModule({
    declarations:[
        CreateUsuarioComponent,
        EditarUsuarioComponent
    ],
    imports:[
        CommonModule,
        UsuariosRoutingModule
    ]
})

export class UsuariosModule{}