import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap} from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from '../environments/environment';
import { Router } from '@angular/router';
import { User } from '../models/invoice';
import { httpOptions } from './httpOptions';

export interface Credentials {
  email: "";
  password: "";
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl = 'token'


  constructor(private http: HttpClient, private jwtHelper: JwtHelperService, private router: Router) { }
  public isAuthenticated(): boolean {
    // const token = localStorage.getItem('token');
    // return !this.jwtHelper.isTokenExpired(token);
    return true;
  }
  login(credentials: Credentials): Observable<any> {
    return this.http.post(`${environment.apiUrl}/${this.loginUrl}`, credentials)
      .pipe(
        tap((response: any) => {
          localStorage.setItem('token', response.token);

           })
      );
  }

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }


}
