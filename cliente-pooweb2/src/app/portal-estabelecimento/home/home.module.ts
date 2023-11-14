import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { HomeComponent } from "./home.component";
import { HomeRoutingModule } from "./home.routing.module";
import { ViewWrapperModule } from "../components/view-wrapper.module";
import { DdMmYYYYDatePipe } from "src/app/app-core/pipe/dd-mm-yyyy-date.pipe";

@NgModule({
    declarations:[
        HomeComponent,
        DdMmYYYYDatePipe
    ],
    imports:[
        CommonModule,
        HomeRoutingModule,
        ViewWrapperModule
    ]
})

export class HomeModule{}