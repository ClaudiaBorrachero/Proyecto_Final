import { Add_JobComponent } from './add_job/add_job.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../services/auth-guard.service';

const routes: Routes = [

    { path: 'add_job',canActivate:[AuthGuard], component: Add_JobComponent },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }

