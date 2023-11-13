import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EditarUsuarioComponent } from './editar/editar-usuario.component';
import { VisualizarUsuarioComponent } from './visualizar/visualizar-usuario.component';

const routes: Routes = [
    {path: '', component: VisualizarUsuarioComponent},
    {path: 'editar', component: EditarUsuarioComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuariosRoutingModule { }