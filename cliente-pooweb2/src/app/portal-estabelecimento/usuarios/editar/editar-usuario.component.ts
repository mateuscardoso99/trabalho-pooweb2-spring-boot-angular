import { Component, OnInit } from "@angular/core";
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { UsuarioAdminEstabelecimentoDto } from "src/app/app-core/dto/UsuarioAdminEstabelecimentoDto";
import { CadastroUsuarioAdmin } from "src/app/app-core/request/CadastroUsuarioAdmin";
import { EstabelecimentoService } from "src/app/app-core/service/estabelecimento.service";
import { StorageService } from "src/app/app-core/service/storage.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-editar-usuario',
    templateUrl: './editar-usuario.component.html',
    styleUrls: ['./editar-usuario.component.css']
})
export class EditarUsuarioComponent implements OnInit{
    form: FormGroup;
    submitted = false;
    cadastroUsuarioAdmin: CadastroUsuarioAdmin;
    usuarioLogado: UsuarioAdminEstabelecimentoDto;
    idUsuarioEditar: number = 0;

    constructor(
        private formBuilder: FormBuilder, 
        private estabelecimentoService: EstabelecimentoService, 
        private storageService: StorageService, 
        private router: Router,
        private route: ActivatedRoute
    ) {
        this.cadastroUsuarioAdmin = new CadastroUsuarioAdmin();
        this.usuarioLogado = this.storageService.getUser()?.usuario as UsuarioAdminEstabelecimentoDto;
    }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.idUsuarioEditar = params.id
        });

        this.form = new FormGroup({
            nome: new FormControl(''),
            email: new FormControl(''),
            senha: new FormControl('')
        });

        if(this.router.url.includes("/editar")){
            this.estabelecimentoService.findUsuario(this.usuarioLogado.idEstabelecimento, this.idUsuarioEditar).subscribe({
                next: (resposta:any) => {                    
                    this.form = this.formBuilder.group(
                        {
                          nome: [resposta.data.nome, Validators.required],
                          email: [resposta.data.email, [Validators.required, Validators.email]],
                          senha: [
                            '',
                            [
                              Validators.required,
                              Validators.minLength(4),
                              Validators.maxLength(40)
                            ]
                          ]
                        }
                      );
                },
                error: err => {
                    console.log(err)
                }
            })
        }
        else{
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
                ]
              }
            );
        }
    }

    get f(): { [key: string]: AbstractControl } {
        return this.form.controls;
    }

    onSubmit(): void {
        this.submitted = true;

        if (this.form.invalid) {
          return;
        }

        this.cadastroUsuarioAdmin.nome = this.form.get("nome")?.value;
        this.cadastroUsuarioAdmin.email = this.form.get("email")?.value;
        this.cadastroUsuarioAdmin.senha = this.form.get("senha")?.value;

        if(this.router.url.includes("/cadastrar")){
            this.estabelecimentoService.cadastrarUsuario(this.usuarioLogado.idEstabelecimento,this.cadastroUsuarioAdmin).subscribe(
                {
                    next: () => {
                        Swal.fire({
                            title: "Sucesso",
                            text: "Usuário cadastrado com sucesso",
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
        else{
            this.estabelecimentoService.updateUsuario(this.usuarioLogado.idEstabelecimento,this.idUsuarioEditar,this.cadastroUsuarioAdmin).subscribe(
                {
                    next: () => {
                        Swal.fire({
                            title: "Sucesso",
                            text: "Usuário atualizado com sucesso",
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
}