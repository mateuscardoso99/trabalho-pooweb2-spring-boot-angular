import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { ClienteDto } from "src/app/app-core/dto/ClienteDto";
import { TokenDto } from "src/app/app-core/dto/TokenDto";
import { CadastroCliente } from "src/app/app-core/request/CadastroCliente";
import { ClienteService } from "src/app/app-core/service/cliente.service";
import { StorageService } from "src/app/app-core/service/storage.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-perfil',
    templateUrl: './perfil.component.html',
    styleUrls: ['./perfil.component.css']
})
export class PerfilComponent{
    constructor(private clienteService: ClienteService, private storageService: StorageService, public router: Router) {}

    salvar(dadosFormulario: CadastroCliente): void {
        console.log(dadosFormulario)
        this.clienteService.update(dadosFormulario).subscribe(
            {
                next: (resposta:any) => {
                    Swal.fire({
                        title: "Sucesso",
                        text: "Perfil atualizado com sucesso",
                        icon: "success"
                    })
                    const dadosUsuario: TokenDto = this.storageService.getUser() as TokenDto
                    dadosUsuario.usuario = resposta.data as ClienteDto
                    this.storageService.removeUser();
                    this.storageService.saveUser(dadosUsuario)
                    this.router.navigate(["/usuario/inicio"])
                },
                error: (err) => {
                    let errors:any = [];
                    err?.error?.errors?.errors.forEach((e:any) => {
                        errors.push(e);
                    });
                    Swal.fire({
                        title: err.error.message,
                        text: errors,
                        icon: "error"
                    })
                }
            }
        );
    }
}