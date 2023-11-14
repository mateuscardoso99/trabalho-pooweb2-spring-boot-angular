import { Component, OnInit } from "@angular/core";
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from "@angular/router";
import { CadastroCliente } from "src/app/app-core/request/CadastroCliente";
import { CadastroEndereco } from "src/app/app-core/request/CadastroEndereco";
import { AuthService } from "src/app/app-core/service/auth.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.css']
})
export class SignUpComponent implements OnInit{
    form: FormGroup = new FormGroup({
        nome: new FormControl(''),
        email: new FormControl(''),
        password: new FormControl(''),
        cidade: new FormControl(''),
        uf: new FormControl(false),
        bairro: new FormControl(''),
        rua: new FormControl(''),
        numero: new FormControl(''),
        complemento: new FormControl(''),
    });
    submitted = false;
    cadastroCliente: CadastroCliente;

    constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {}

    ngOnInit(): void {
        this.form = this.formBuilder.group(
          {
            nome: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            password: [
              '',
              [
                Validators.required,
                Validators.minLength(4),
                Validators.maxLength(40)
              ]
            ],
            cidade: ['', Validators.required],
            uf: ['', 
                [
                    Validators.required, 
                    Validators.minLength(2), 
                    Validators.maxLength(2)
                ]
            ],
            bairro: ['', Validators.required],
            rua: ['', Validators.required],
            numero: ['', Validators.required],
            complemento: ['', Validators.nullValidator],
          }
        );
    }

    get f(): { [key: string]: AbstractControl } {
        return this.form.controls;
    }

    onSubmit(): void {
        this.submitted = true;

        if (this.form.invalid) {
          return;
        }

        const dados: any = JSON.stringify(this.form.value, null, 2);

        this.cadastroCliente = new CadastroCliente(
            dados.nome,
            dados.email,
            dados.password,
            new CadastroEndereco(
                dados.cidade,
                dados.email,
                dados.password,
                dados.nome,
                dados.email,
                dados.password,
                "",
                ""
            )
        )
        this.authService.register(this.cadastroCliente).subscribe(
            {
                next: () => {
                    this.router.navigate(["/login"])
                },
                error: (err) => {
                    console.log(err)
                    Swal.fire({
                        title: "Erro",
                        text: err.message
                    })
                }
            }
        );
    }
    
    onReset(): void {
        this.submitted = false;
        this.form.reset();
    }
}