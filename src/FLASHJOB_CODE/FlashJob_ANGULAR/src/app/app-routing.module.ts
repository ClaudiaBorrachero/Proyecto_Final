import { AuthGuard } from './services/auth-guard.service';
import { ProfileComponent } from './profile/profile.component';
// import { PagenotfoundComponent } from './pagenotfound/pagenotfound.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { HomeComponent } from './home/home/home.component';

const routes: Routes = [
  {
    path:'',
    component: HomeComponent,
    pathMatch: 'full'
  },
  {
    path:'home',
    component: HomeComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'profile',
    component: ProfileComponent,
    loadChildren: () => import('./profile/profile.module').then( m => m.ProfileModule ), canLoad:[AuthGuard]
  },
  // {
  //   path: '**',
  //   pathMatch: 'full',
  //   component: PagenotfoundComponent
  // }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
