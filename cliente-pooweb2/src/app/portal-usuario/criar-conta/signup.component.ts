import { Component, OnInit } from "@angular/core";
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from "@angular/router";
import { CadastroCliente } from "src/app/app-core/request/CadastroCliente";
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
        senha: new FormControl(''),
        endereco: new FormGroup({
            cidade: new FormControl(''),
            uf: new FormControl(''),
            bairro: new FormControl(''),
            rua: new FormControl(''),
            numero: new FormControl(''),
            complemento: new FormControl('')
        })
    });
    submitted = false;
    cadastroCliente: CadastroCliente;

    constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {
        this.cadastroCliente = new CadastroCliente();
    }

    ngOnInit(): void {
        this.form = this.formBuilder.group(
          {
            nome: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            senha: [
              '',
              [
                Validators.required,
                Validators.minLength(4),
                Validators.maxLength(40)
              ]
            ],
            endereco: this.formBuilder.group({
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
                complemento: ['', Validators.nullValidator]
            })
          }
        );
    }

    get f(): { [key: string]: AbstractControl } {
        return this.form.controls;
    }

    get e(): { [key: string]: AbstractControl } {
        return (this.form.get('endereco') as FormGroup).controls;
    }

    onSubmit(): void {
        this.submitted = true;

        if (this.form.invalid) {
          return;
        }

        this.authService.register(this.form.getRawValue() as CadastroCliente).subscribe(
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
    
    onReset(): void {
        this.submitted = false;
        this.form.reset();
    }
}