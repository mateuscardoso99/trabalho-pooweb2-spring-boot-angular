import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EditarUsuarioComponent } from './editar/editar-usuario.component';
import { VisualizarUsuarioComponent } from './visualizar/visualizar-usuario.component';
import { UsuariosComponent } from './usuarios.component';

const routes: Routes = [
    {
      path: '',
      children: [
        {
          path: '', component: UsuariosComponent,
          children: [
            {path: '', redirectTo: 'visualizar', pathMatch: 'full'},
            {path: 'visualizar', component: VisualizarUsuarioComponent},
            {path: 'cadastrar', component: EditarUsuarioComponent},
            {path: 'editar/:id', component: EditarUsuarioComponent},
          ]
        },    
      ]
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuariosRoutingModule { }