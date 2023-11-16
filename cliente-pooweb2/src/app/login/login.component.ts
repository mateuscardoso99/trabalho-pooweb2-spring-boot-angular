import { Component } from "@angular/core";
import Swal from 'sweetalert2'
import { AuthService } from "../app-core/service/auth.service";
import { LoginRequest } from "../app-core/request/LoginRequest";
import { StorageService } from "../app-core/service/storage.service";
import { TokenDto } from "../app-core/dto/TokenDto";
import { Router } from "@angular/router";
import { Permissao } from "../app-core/dto/UsuarioDto";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent {
    loginRequest: LoginRequest;

    constructor(private authService: AuthService, private storageService: StorageService, private router: Router){
        this.loginRequest = new LoginRequest();
    }

    login(){
        if(!this.loginRequest.email || !this.loginRequest.senha){
            Swal.fire({
                title: 'Erro',
                text: 'Preencha email e senha',
                icon: 'error'
            });
            return;
        }
        this.authService.login(this.loginRequest)
        .subscribe({
            next: (resposta:any) => {
                if(resposta.token){
                    const user = new TokenDto();
                    user.token = resposta.token;
                    user.usuario = resposta.usuario;
                    this.storageService.saveUser(user);
                    if(user.usuario.permissoes.includes(Permissao.CLIENTE)){
                        this.router.navigate(['/usuario/inicio']);
                    }
                    else if(user.usuario.permissoes.includes(Permissao.ADMIN_ESTABELECIMENTO)){
                        this.router.navigate(['/estabelecimento/home']);
                    }
                }
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
        });
    }
}