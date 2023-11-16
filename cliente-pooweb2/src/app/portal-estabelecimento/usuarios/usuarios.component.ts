import { Component } from "@angular/core";
import { Router } from "@angular/router";

@Component({
    selector: 'app-usuarios',
    templateUrl: './usuarios.component.html',
    styleUrls: ['./usuarios.component.css']
})
export class UsuariosComponent{
    constructor(public router: Router){}//public pra ser acessível dentro do componente
}