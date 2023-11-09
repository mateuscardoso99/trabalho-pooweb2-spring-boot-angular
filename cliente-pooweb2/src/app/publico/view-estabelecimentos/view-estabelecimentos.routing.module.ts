import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ViewEstabelecimentosComponent } from './view-estabelecimentos.component';

const routes: Routes = [
    {path: '', component: ViewEstabelecimentosComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ViewEstabelecimentosRoutingModule { }