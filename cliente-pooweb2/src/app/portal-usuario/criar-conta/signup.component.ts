import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { CadastroCliente } from "src/app/app-core/request/CadastroCliente";
import { AuthService } from "src/app/app-core/service/auth.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.css']
})
export class SignUpComponent{
    constructor(private authService: AuthService, public router: Router) {}    

    salvar(dadosFormulario: CadastroCliente): void {
        this.authService.register(dadosFormulario).subscribe(
            {
                next: () => {
                    Swal.fire({
                        title: "Sucesso",
                        text: "Conta criada com sucesso",
                        icon: "success"
                    })
                    this.router.navigate(["/login"])
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