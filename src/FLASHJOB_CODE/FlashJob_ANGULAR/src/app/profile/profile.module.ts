
import { Mis_jobsComponent } from './mis_jobs/mis_jobs.component';
import { Mi_perfilComponent } from './mi_perfil/mi_perfil.component';
import { ProfileRoutingModule } from './profile-routing.module';
import { Add_JobComponent } from './add_job/add_job.component';
import { SharedModule } from './../shared/shared.module';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './profile.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {TableModule} from 'primeng/table';

@NgModule({
  declarations: [
    ProfileComponent,
    Add_JobComponent,
    Mi_perfilComponent,
    Mis_jobsComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule,
    FormsModule,
    ProfileRoutingModule,
    ReactiveFormsModule,
    TableModule
  ]
})
export class ProfileModule { }
