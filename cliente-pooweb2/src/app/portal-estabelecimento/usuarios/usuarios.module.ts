import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { CreateUsuarioComponent } from "./create/create-usuario.component";
import { EditarUsuarioComponent } from "./editar/editar-usuario.component";

@NgModule({
    declarations:[
        CreateUsuarioComponent,
        EditarUsuarioComponent
    ],
    imports:[
        CommonModule
    ]
})

export class UsuariosModule{}