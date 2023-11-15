import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DadosEstabelecimentoComponent } from './dados-estabelecimento.component';

const routes: Routes = [
    {path: '', component: DadosEstabelecimentoComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DadosEstabelecimentoRoutingModule { }