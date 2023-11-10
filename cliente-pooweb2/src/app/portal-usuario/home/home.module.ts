import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { HomeComponent } from "./home.component";
import { HomeRoutingModule } from "./home.routing.module";
import { SidebarModule } from "src/app/app-core/components/sidebar/sidebar.module";

@NgModule({
    declarations:[
        HomeComponent
    ],
    imports:[
        CommonModule,
        HomeRoutingModule,
        SidebarModule//Para usar o Componente A dentro do Componente B, precisa tê-los declarados dentro do mesmo Módulo ou o Componente A para ser importado para o Módulo onde o Componente B está sendo declarado
    ]
})

export class HomeModule{}