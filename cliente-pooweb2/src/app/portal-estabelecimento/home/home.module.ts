import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { HomeComponent } from "./home.component";
import { HomeRoutingModule } from "./home.routing.module";
import { ViewWrapperModule } from "../components/view-wrapper.module";
import { DdMmYYYYDateModule } from "src/app/app-core/pipe/dd-mm-yyyy-date.pipe.module";

@NgModule({
    declarations:[
        HomeComponent
    ],
    imports:[
        CommonModule,
        HomeRoutingModule,
        ViewWrapperModule,
        DdMmYYYYDateModule
    ]
})

export class HomeModule{}