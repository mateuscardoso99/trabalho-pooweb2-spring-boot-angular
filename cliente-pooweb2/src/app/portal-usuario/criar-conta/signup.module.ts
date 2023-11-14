import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { SignUpComponent } from "./signup.component";
import { SignUpRoutingModule } from "./signup.routing.module";
import { ReactiveFormsModule } from "@angular/forms";

@NgModule({
    declarations:[
        SignUpComponent
    ],
    imports:[
        CommonModule,
        ReactiveFormsModule,
        SignUpRoutingModule
    ]
})

export class SignUpModule{}