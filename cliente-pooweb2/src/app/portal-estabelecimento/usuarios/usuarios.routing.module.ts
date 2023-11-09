import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateUsuarioComponent } from './create/create-usuario.component';
import { EditarUsuarioComponent } from './editar/editar-usuario.component';

const routes: Routes = [
    {path: 'create', component: CreateUsuarioComponent},
    {path: 'editar', component: EditarUsuarioComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuariosRoutingModule { }