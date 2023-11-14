import { Component, ElementRef, Input, ViewChild } from "@angular/core";
import { StorageService } from "../../service/storage.service";
import { Router } from "@angular/router";
import { UsuarioDto } from "../../dto/UsuarioDto";

@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.css'],
    host: {'class': 'col-12 col-sm-3 col-xl-2 px-sm-2 px-0 bg-dark d-flex sticky-top'}
})
export class SidebarComponent {
  @Input() opcoes: any;
  @ViewChild("dropdown") dropdown: ElementRef;

  usuario: UsuarioDto | undefined;
  constructor(private storageService: StorageService,private router: Router){
    this.usuario = this.storageService.getUser()?.usuario;
  }

  public abrirDropdown(){
    console.log(this.dropdown)
    this.dropdown.nativeElement.classList.toggle("open")
  }

  public logout(){
    this.storageService.signOut();
    this.router.navigate(['/login']);
  }
}