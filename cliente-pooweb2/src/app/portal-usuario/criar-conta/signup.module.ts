import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { SignUpComponent } from "./signup.component";
import { SignUpRoutingModule } from "./signup.routing.module";

@NgModule({
    declarations:[
        SignUpComponent
    ],
    imports:[
        CommonModule,
        SignUpRoutingModule
    ]
})

export class SignUpModule{}