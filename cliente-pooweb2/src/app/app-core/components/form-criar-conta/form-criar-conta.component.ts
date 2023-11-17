import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { StorageService } from "../../service/storage.service";
import { Router } from "@angular/router";
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { CadastroCliente } from "../../request/CadastroCliente";
import { ClienteDto } from "../../dto/ClienteDto";

@Component({
    selector: 'app-form-criar-conta',
    templateUrl: './form-criar-conta.component.html',
    styleUrls: ['./form-criar-conta.component.css']
})
export class FormCriarContaComponent implements OnInit{
  @Input() url: string;
  @Input() titulo: string;
  @Output() dadosFormularioEmitter = new EventEmitter<CadastroCliente>();

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

  constructor(
    private formBuilder: FormBuilder, 
    private storageService: StorageService, 
    private router: Router
  ) {
    this.cadastroCliente = new CadastroCliente();
  }

  ngOnInit(): void {
    if(this.url.includes("/signup")){
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
    else{
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

      this.dadosFormularioEmitter.emit(this.form.getRawValue() as CadastroCliente)
  }

  onReset(): void {
      this.submitted = false;
      this.form.reset();
  }
}