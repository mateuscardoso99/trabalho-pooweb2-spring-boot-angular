import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ViewWrapperModule } from "../components/view-wrapper.module";
import { PerfilComponent } from "./perfil.component";
import { PerfilRoutingModule } from "./perfil.routing.module";

@NgModule({
    declarations:[
        PerfilComponent
    ],
    imports:[
        CommonModule,
        PerfilRoutingModule,
        ViewWrapperModule
    ]
})

export class PerfilModule{}