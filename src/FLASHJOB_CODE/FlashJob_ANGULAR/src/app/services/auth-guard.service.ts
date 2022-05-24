import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, CanLoad, Route, Router, RouterStateSnapshot, UrlSegment, UrlTree } from "@angular/router";
import { catchError, map, Observable, of } from "rxjs";

import { LoginService } from "./login.service";

@Injectable()
export class AuthGuard implements CanActivate, CanLoad {
  constructor( private router: Router, private loginService: LoginService, private http:HttpClient) { }

  /**
   * Método que llama a validarToken del servicio y dependiendo del resultado permitirá o no
   * la entrada a la siguiente página
   * @param route
   * @param state
   * @returns
   */
   canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree| any {

    return this.loginService.validarToken()
    .pipe(
        map( resp => {
          console.log("hola");
          return true
        }),
        catchError( err => {

            this.router.navigateByUrl('/login');
            return of(false)
        })
      )
  }

  canLoad(
    route: Route,
    segments: UrlSegment[]): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {

    return this.loginService.validarToken()
    .pipe(
        map( resp => {
          console.log("hola2");
          return true
        }),
        catchError( err => {

            this.router.navigateByUrl('/login');
            return of(false)
        })
      )
  }

}
