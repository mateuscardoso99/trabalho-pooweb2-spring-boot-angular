import { Component } from "@angular/core";

@Component({
    selector: 'app-view-wrapper',
    templateUrl: './view-wrapper.component.html',
    styleUrls: ['./view-wrapper.component.css']
})
export class ViewWrapperComponent {
    /*
    route:
    Se o primeiro segmento começar com /, o roteador procurará a rota na raiz do aplicativo.
    Se o primeiro segmento começar com ./ ou não começar com uma barra, o roteador procurará nos filhos da rota atualmente ativada.
    E se o primeiro segmento começar com ../, o roteador subirá um nível.
     */
    public opcoes = [
        {
            titulo: "Pedidos",
            route: "../inicio",
            icon: "bi bi-bag-fill"
        },
        {
            titulo: "Novo pedido",
            route: "../cadastro-pedido",
            icon: "bi bi-plus"
        }
    ];
}