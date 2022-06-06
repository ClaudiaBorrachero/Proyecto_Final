import { AnuncioDetalleComponent } from './anuncio/anuncio-detalle/anuncio-detalle.component';
import { AnuncioComponent } from './anuncio/anuncio.component';
import { SearchPageComponent } from './anuncio/searchPage/searchPage.component';
import { SearchComponent } from './shared/search/search.component';
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
{
  path: 'anuncio',
  component: AnuncioComponent
},
{
  path: 'anuncio/:termino',
  component: AnuncioComponent
},
{
  path: 'anuncio-detalle',
  component: AnuncioDetalleComponent
},
{
  path: 'anuncio-detalle/:id',
  component: AnuncioDetalleComponent
},
{
  path: '**',
  component: HomeComponent
}
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
