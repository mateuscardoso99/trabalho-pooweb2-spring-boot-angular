import { Component, OnInit } from "@angular/core";
import { UsuarioAdminEstabelecimentoDto } from "src/app/app-core/dto/UsuarioAdminEstabelecimentoDto";
import { EstabelecimentoService } from "src/app/app-core/service/estabelecimento.service";
import { StorageService } from "src/app/app-core/service/storage.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-visualizar-usuario',
    templateUrl: './visualizar-usuario.component.html',
    styleUrls: ['./visualizar-usuario.component.css']
})
export class VisualizarUsuarioComponent implements OnInit{
    usuarios: UsuarioAdminEstabelecimentoDto[];
    usuarioLogado: UsuarioAdminEstabelecimentoDto;

    constructor(private estabelecimentoService: EstabelecimentoService, private storageService: StorageService){
        this.usuarioLogado = storageService.getUser()?.usuario as UsuarioAdminEstabelecimentoDto
    }

    ngOnInit(): void {
        this.getUsuarios();
    }

    getUsuarios(){
        this.estabelecimentoService.getUsuarios(this.usuarioLogado.idEstabelecimento as number).subscribe({
            next: (resposta: any) => {
                this.usuarios = resposta.data as UsuarioAdminEstabelecimentoDto[]
            },error: err => {
                console.log(err)
            }
        })
    }

    desativarUsuario(idUsuario: number){
        Swal.fire({
            title: "Deseja desativar o usuário?",
            showCancelButton: true,
            confirmButtonText: "Sim",
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                this.estabelecimentoService.desativarUsuario(this.usuarioLogado.idEstabelecimento,idUsuario).subscribe({
                    next: () => {
                        Swal.fire({
                            title: 'Sucesso',
                            text: 'Usuário desativado com sucesso',
                            icon: 'success'
                        });
                        this.getUsuarios()
                    },
                    error: () => {
                        Swal.fire({
                            title: 'Erro',
                            text: 'Erro ao desativar usuário',
                            icon: 'error'
                        });
                    }
                })
            }
        });
    }

    ativarUsuario(idUsuario: number){
        this.estabelecimentoService.ativarUsuario(this.usuarioLogado.idEstabelecimento,idUsuario).subscribe({
            next: () => {
                Swal.fire({
                    title: 'Sucesso',
                    text: 'Usuário sativado com sucesso',
                    icon: 'success'
                });
                this.getUsuarios()
            },
            error: () => {
                Swal.fire({
                    title: 'Erro',
                    text: 'Erro ao ativar usuário',
                    icon: 'error'
                });
            }
        })
    }
}