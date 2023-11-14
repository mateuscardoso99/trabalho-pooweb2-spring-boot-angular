import { Component, OnInit } from "@angular/core";
import { UsuarioAdminEstabelecimentoDto } from "src/app/app-core/dto/UsuarioAdminEstabelecimentoDto";
import { EstabelecimentoService } from "src/app/app-core/service/estabelecimento.service";
import { StorageService } from "src/app/app-core/service/storage.service";

@Component({
    selector: 'app-visualizar-usuario',
    templateUrl: './visualizar-usuario.component.html',
    styleUrls: ['./visualizar-usuario.component.css']
})
export class VisualizarUsuarioComponent implements OnInit{
    usuarios: UsuarioAdminEstabelecimentoDto[];
    usuario: UsuarioAdminEstabelecimentoDto | undefined;

    constructor(private estabelecimentoService: EstabelecimentoService, private storageService: StorageService){
        this.usuario = storageService.getUser()?.usuario as UsuarioAdminEstabelecimentoDto
    }

    ngOnInit(): void {
        this.estabelecimentoService.getUsuarios(this.usuario?.idEstabelecimento as number).subscribe({
            next: (resposta: any) => {
                this.usuarios = resposta.data as UsuarioAdminEstabelecimentoDto[]
            },error: err => {
                console.log(err)
            }
        })
    }  
}