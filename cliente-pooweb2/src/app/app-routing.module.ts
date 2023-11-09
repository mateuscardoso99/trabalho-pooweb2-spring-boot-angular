import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {path: '', redirectTo: 'login',pathMatch: 'full'},
  {
    path: 'login',
    loadChildren: () => import("./login/login.module").then(m=>m.LoginModule)
  },
  {
    path: 'estabelecimento/home',
    loadChildren: () => import("./portal-estabelecimento/home/home.module").then(m=>m.HomeModule)
  },
  {
    path: 'estabelecimento/usuarios',
    loadChildren: () => import("./portal-estabelecimento/usuarios/usuarios.module").then(m=>m.UsuariosModule)
  },
  {
    path: 'usuario/pedidos',
    loadChildren: () => import("./portal-usuario/cadastrar-pedido/cadastrar-pedido.module").then(m=>m.CadastrarPedidoModule)
  },
  {
    path: 'usuario/signup',
    loadChildren: () => import("./portal-usuario/criar-conta/signup.module").then(m=>m.SignUpModule)
  },
  {
    path: 'usuario/inicio',
    loadChildren: () => import("./portal-usuario/home/home.module").then(m=>m.HomeModule)
  },
  {
    path: 'mapa-estabs',
    loadChildren: () => import("./publico/view-estabelecimentos/view-estabelecimentos.module").then(m=>m.ViewEstabelecimentosModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
