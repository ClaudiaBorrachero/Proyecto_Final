import { Mis_jobsComponent } from './mis_jobs/mis_jobs.component';
import { Mi_perfilComponent } from './mi_perfil/mi_perfil.component';
import { Add_JobComponent } from './add_job/add_job.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../services/auth-guard.service';

const routes: Routes = [

    { path: 'add_job',canActivate:[AuthGuard], component: Add_JobComponent },
    { path: 'mi_perfil',canActivate:[AuthGuard], component: Mi_perfilComponent},
    { path: 'mis_jobs',canActivate:[AuthGuard], component: Mis_jobsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }

