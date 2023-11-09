import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadastrarPedidoComponent } from './cadastrar-pedido.component';

const routes: Routes = [
    {path: 'create', component: CadastrarPedidoComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CadastrarPedidoRoutingModule { }