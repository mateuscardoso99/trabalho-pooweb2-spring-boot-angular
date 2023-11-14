import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { LoginComponent } from "./login.component";
import { LoginRoutingModule } from "./login.routing.module";
import { FormsModule } from "@angular/forms";

@NgModule({
    declarations:[
        LoginComponent
    ],
    imports:[
        CommonModule,
        FormsModule,
        LoginRoutingModule
    ]
})

export class LoginModule{}