import { Component } from "@angular/core";
import Swal from 'sweetalert2'
import { AuthService } from "../app-core/service/auth.service";
import { LoginRequest } from "../app-core/request/LoginRequest";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent {
    loginRequest: LoginRequest;

    constructor(private authService: AuthService){}

    login(){
        if(!this.loginRequest.email || !this.loginRequest.senha){
            Swal.fire({
                title: 'Erro',
                text: 'Preencha email e senha',
                icon: 'error'
            });
            return;
        }
        this.authService.login(this.loginRequest).subscribe({
            next: resposta => {
                console.log(resposta)
            },
            error: erro => {
                console.log(erro)
            }
        })
    }
}