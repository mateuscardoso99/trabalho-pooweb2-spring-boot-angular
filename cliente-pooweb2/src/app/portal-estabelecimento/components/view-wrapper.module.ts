import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { SidebarModule } from "src/app/app-core/components/sidebar/sidebar.module";
import { ViewWrapperComponent } from "./view-wrapper.component";

@NgModule({
    declarations:[
        ViewWrapperComponent
    ],
    imports:[
        CommonModule,
        SidebarModule//Para usar o Componente A dentro do Componente B, precisa tê-los declarados dentro do mesmo Módulo ou o Componente A para ser importado para o Módulo onde o Componente B está sendo declarado
    ],
    exports:[
        ViewWrapperComponent
    ]
})

export class ViewWrapperModule{}