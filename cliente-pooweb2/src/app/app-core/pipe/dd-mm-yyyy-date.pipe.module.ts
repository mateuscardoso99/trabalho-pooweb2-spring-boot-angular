import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { DdMmYYYYDatePipe } from "./dd-mm-yyyy-date.pipe";

@NgModule({
    declarations:[
        DdMmYYYYDatePipe
    ],
    exports: [DdMmYYYYDatePipe]
})

export class DdMmYYYYDateModule{}