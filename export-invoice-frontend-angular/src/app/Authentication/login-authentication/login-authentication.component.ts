import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService, Credentials } from 'src/app/services/authentication.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackbarService } from 'src/app/services/showSnackBar';
@Component({
  selector: 'app-login-authentication',
  templateUrl: './login-authentication.component.html',
  styleUrls: ['./login-authentication.component.css'],
  template: `
    <main>
      <router-outlet></router-outlet>
    </main>`

})
export class LoginComponentTokenService implements OnInit {

  credentials: Credentials = {
    email: '',
    password: ''
  };
  constructor(private authService: AuthService, private router: Router, private snackbarService: SnackbarService) { }

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/project']);
    }
    else{
      this.router.navigate(['/login']);
    }
  }
  isChecked = false;

  onCheckboxChange(event: any) {
  }
  
  onSubmit() {
    if (this.credentials.email === '' || this.credentials.password === '')
    {
      this.snackbarService.showSnackBar("Email is a required field.Password is a required field.", 'mat-snack-bar-container-coral');

    }
    else{
    this.authService.login(this.credentials).subscribe(
      (data) => {
        console.log(data)
        if (this.authService.isAuthenticated()) {
          this.router.navigate(['/home']);
          window.location.reload();
        }
    },
      () => {
        this.snackbarService.showSnackBar("Wrong account or password", 'mat-snack-bar-container-red');
      }
    );
      console.log(this.credentials)
  }
}
}

