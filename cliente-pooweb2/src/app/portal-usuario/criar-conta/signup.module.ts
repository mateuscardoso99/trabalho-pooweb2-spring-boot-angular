import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { SignUpComponent } from "./signup.component";
import { SignUpRoutingModule } from "./signup.routing.module";
import { FormCriarContaModule } from "src/app/app-core/components/form-criar-conta/form-criar-conta.module";

@NgModule({
    declarations:[
        SignUpComponent
    ],
    imports:[
        CommonModule,
        FormCriarContaModule,
        SignUpRoutingModule
    ]
})

export class SignUpModule{}