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
        SidebarModule
    ]
})

export class HomeModule{}