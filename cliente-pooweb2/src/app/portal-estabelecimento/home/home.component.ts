import { Component, OnInit } from "@angular/core";
import { PedidoDto } from "src/app/app-core/dto/PedidoDto";
import { UsuarioAdminEstabelecimentoDto } from "src/app/app-core/dto/UsuarioAdminEstabelecimentoDto";
import { EstabelecimentoService } from "src/app/app-core/service/estabelecimento.service";
import { StorageService } from "src/app/app-core/service/storage.service";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
    pedidos: PedidoDto[] = [];
    usuario: UsuarioAdminEstabelecimentoDto | undefined;

    constructor(private estabelecimentoService: EstabelecimentoService, private storageService: StorageService){
        this.usuario = storageService.getUser()?.usuario as UsuarioAdminEstabelecimentoDto
    }

    ngOnInit(): void {
        this.estabelecimentoService.getPedidos(this.usuario?.idEstabelecimento as number).subscribe({
            next: (resposta:any) => {
                this.pedidos = resposta.data as PedidoDto[]
            },
            error: err => {
                console.log(err)
            }
        })
    }

}