import { Component } from "@angular/core";

@Component({
    selector: 'app-view-wrapper',
    templateUrl: './view-wrapper.component.html',
    styleUrls: ['./view-wrapper.component.css']
})
export class ViewWrapperComponent {
    public opcoes = [
        {
            titulo: "Início",
            route: "/estabelecimento/home",
            icon: "bi bi-house"
        },
        {
            titulo: "Usuários",
            route: "/estabelecimento/usuarios",
            icon: "bi bi-people-fill"
        }
    ];
}