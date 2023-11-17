import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { FormCriarContaComponent } from "./form-criar-conta.component";

@NgModule({
    declarations:[
        FormCriarContaComponent
    ],
    imports:[
        CommonModule,
        RouterModule,
        ReactiveFormsModule
    ],
    exports:[
        FormCriarContaComponent
    ]
})

export class FormCriarContaModule{}