import { Component, OnInit } from "@angular/core";
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
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
export class PerfilComponent implements OnInit{
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
    usuarioLogado: ClienteDto;

    constructor(private formBuilder: FormBuilder, private clienteService: ClienteService, private storageService: StorageService, private router: Router) {
        this.cadastroCliente = new CadastroCliente();
    }

    ngOnInit(): void {
        this.usuarioLogado = this.storageService.getUser()?.usuario as ClienteDto;

        this.form = this.formBuilder.group(
          {
            nome: [this.usuarioLogado.nome, Validators.required],
            email: [this.usuarioLogado.email, [Validators.required, Validators.email]],
            senha: [
              '',
              [
                Validators.required,
                Validators.minLength(4),
                Validators.maxLength(40)
              ]
            ],
            endereco: this.formBuilder.group({
                cidade: [this.usuarioLogado.endereco.cidade, Validators.required],
                uf: [this.usuarioLogado.endereco.uf, 
                    [
                        Validators.required, 
                        Validators.minLength(2), 
                        Validators.maxLength(2)
                    ]
                ],
                bairro: [this.usuarioLogado.endereco.bairro, Validators.required],
                rua: [this.usuarioLogado.endereco.rua, Validators.required],
                numero: [this.usuarioLogado.endereco.numero, Validators.required],
                complemento: [this.usuarioLogado.endereco.complemento, Validators.nullValidator]
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

        this.clienteService.update(this.form.getRawValue() as CadastroCliente).subscribe(
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
    
    onReset(): void {
        this.submitted = false;
        this.form.reset();
    }
}