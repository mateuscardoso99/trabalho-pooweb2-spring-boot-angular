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
        },
        {
            titulo: "Dados do estabelecimento",
            route: "/estabelecimento/dados-estabelecimento",
            icon: "bi bi-database-fill"
        }
    ];

    public urlPerfil: string = "/estabelecimento/perfil-usuario";
}