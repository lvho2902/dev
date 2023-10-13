import { Component, ViewEncapsulation } from '@angular/core';
import { AuthService } from './services/authentication.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  encapsulation: ViewEncapsulation.None

})
export class AppComponent {
  title = 'project';

  constructor( private authService: AuthService, private router: Router) {}

  ngOnInit(): void{
    
  }
  
  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  
}
