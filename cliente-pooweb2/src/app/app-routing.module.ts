import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './app-core/middleware/auth.guard';
import { PagenotfoundComponent } from './pagenotfound/pagenotfound.component';
import { PermissaoDto } from './app-core/dto/UsuarioDto';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import("./publico/view-estabelecimentos/view-estabelecimentos.module").then(m=>m.ViewEstabelecimentosModule)
  },
  {
    path: 'login',
    canActivate: [AuthGuard],
    loadChildren: () => import("./login/login.module").then(m=>m.LoginModule)
  },


  {
    path: 'estabelecimento/home',
    data: {permission: PermissaoDto.ADMIN_ESTABELECIMENTO},
    canActivate: [AuthGuard],
    loadChildren: () => import("./portal-estabelecimento/home/home.module").then(m=>m.HomeModule)
  },
  {
    path: 'estabelecimento/usuarios',
    data: {permission: PermissaoDto.ADMIN_ESTABELECIMENTO},
    canActivate: [AuthGuard],
    loadChildren: () => import("./portal-estabelecimento/usuarios/usuarios.module").then(m=>m.UsuariosModule),
  },
  {
    path: 'estabelecimento/perfil-usuario',
    data: {permission: PermissaoDto.ADMIN_ESTABELECIMENTO},
    canActivate: [AuthGuard],
    loadChildren: () => import("./portal-estabelecimento/perfil-usuario/perfil-usuario.module").then(m=>m.PerfilUsuarioModule)
  },
  {
    path: 'estabelecimento/dados-estabelecimento',
    data: {permission: PermissaoDto.ADMIN_ESTABELECIMENTO},
    canActivate: [AuthGuard],
    loadChildren: () => import("./portal-estabelecimento/dados-estabelecimento/dados-estabelecimento.module").then(m=>m.DadosEstabelecimentoModule)
  },


  {
    path: 'usuario/cadastro-pedido',
    data: {permission: PermissaoDto.CLIENTE},
    canActivate: [AuthGuard],
    loadChildren: () => import("./portal-usuario/cadastrar-pedido/cadastrar-pedido.module").then(m=>m.CadastrarPedidoModule)
  },
  {
    path: 'usuario/perfil',
    data: {permission: PermissaoDto.CLIENTE},
    canActivate: [AuthGuard],
    loadChildren: () => import("./portal-usuario/perfil/perfil.module").then(m=>m.PerfilModule)
  },
  {
    path: 'usuario/inicio',
    data: {permission: PermissaoDto.CLIENTE},
    canActivate: [AuthGuard],
    loadChildren: () => import("./portal-usuario/home/home.module").then(m=>m.HomeModule)
  },
  {
    path: 'usuario/signup',
    canActivate: [AuthGuard],
    loadChildren: () => import("./portal-usuario/criar-conta/signup.module").then(m=>m.SignUpModule)
  },
  { path: '**', pathMatch: 'full', component: PagenotfoundComponent }, 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
