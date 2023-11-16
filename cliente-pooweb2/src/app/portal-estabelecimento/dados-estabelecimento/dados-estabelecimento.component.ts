import { Component, OnInit } from "@angular/core";
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { EstabelecimentoDto } from "src/app/app-core/dto/EstabelecimentoDto";
import { UsuarioAdminEstabelecimentoDto } from "src/app/app-core/dto/UsuarioAdminEstabelecimentoDto";
import { CadastroEstabelecimento } from "src/app/app-core/request/CadastroEstabelecimento";
import { EstabelecimentoService } from "src/app/app-core/service/estabelecimento.service";
import { StorageService } from "src/app/app-core/service/storage.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-dados-estabelecimento',
    templateUrl: './dados-estabelecimento.component.html',
    styleUrls: ['./dados-estabelecimento.component.css']
})
export class DadosEstabelecimentoComponent implements OnInit{
    estabelecimento: EstabelecimentoDto;
    usuarioLogado: UsuarioAdminEstabelecimentoDto;
    cadastroEstabelecimento: CadastroEstabelecimento = new CadastroEstabelecimento();
    form: FormGroup;
    submitted = false;

    constructor(private formBuilder: FormBuilder,
        private estabelecimentoService: EstabelecimentoService, 
        private storageService: StorageService,
        private router: Router
    ){}

    ngOnInit(): void {
        this.usuarioLogado = this.storageService.getUser()?.usuario as UsuarioAdminEstabelecimentoDto;

        this.form = new FormGroup({
            nome: new FormControl(''),
            razaoSocial: new FormControl(''),
            horarioFuncionamento: new FormControl(''),
            endereco: new FormGroup({
                cidade: new FormControl(''),
                uf: new FormControl(''),
                bairro: new FormControl(''),
                rua: new FormControl(''),
                numero: new FormControl(''),
                complemento: new FormControl(''),
                latitude: new FormControl(''),
                longitude: new FormControl('')
            })
        });

        this.estabelecimentoService.find(this.usuarioLogado.idEstabelecimento).subscribe({
            next: ({data}: any) => {
                this.estabelecimento = data;
                this.form = this.formBuilder.group(
                    {
                        nome: [this.estabelecimento.nome, Validators.required],
                        razaoSocial: [this.estabelecimento.razaoSocial, [Validators.required]],
                        horarioFuncionamento: [this.estabelecimento.horarioFuncionamento,[ Validators.required]],
                        endereco: this.formBuilder.group({
                            cidade: [this.estabelecimento.endereco.cidade, Validators.required],
                            uf: [this.estabelecimento.endereco.uf, 
                                [
                                    Validators.required, 
                                    Validators.minLength(2), 
                                    Validators.maxLength(2)
                                ]
                            ],
                            bairro: [this.estabelecimento.endereco.bairro, Validators.required],
                            rua: [this.estabelecimento.endereco.rua, Validators.required],
                            numero: [this.estabelecimento.endereco.numero, Validators.required],
                            complemento: [this.estabelecimento.endereco.complemento, Validators.nullValidator],
                            latitude: [this.estabelecimento.endereco.latitude, Validators.required],
                            longitude: [this.estabelecimento.endereco.longitude, Validators.required]
                      })
                    }
                  );
            },
            error: err => {
                console.log(err)
            }
        })
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
        this.cadastroEstabelecimento = this.form.getRawValue() as CadastroEstabelecimento;
        this.cadastroEstabelecimento.idEmpresa = this.estabelecimento.empresa.id;

        this.estabelecimentoService.updateEstabelecimento(this.cadastroEstabelecimento, this.usuarioLogado.idEstabelecimento).subscribe(
            {
                next: () => {
                    Swal.fire({
                        title: "Sucesso",
                        text: "Estabelecimento atualizado com sucesso",
                        icon: "success"
                    })
                    this.router.navigate(["/estabelecimento/home"])
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