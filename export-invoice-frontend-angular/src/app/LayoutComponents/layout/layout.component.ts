import { Component, Input } from '@angular/core';
import { Router, NavigationEnd  } from '@angular/router';
import { AuthService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent {
  showHeader = true;
  isLoggedIn: boolean = false;
  isProjectsSelected: boolean = false;
  isPoSelected: boolean = false;
  isAuditSelected: boolean = false;
  isEmployeeSelected: boolean = false;


  constructor(private router: Router, private authService: AuthService) {
    // check if current route is '/login' and hide header if it is
    router.events.subscribe((val) => {
      if (val instanceof NavigationEnd) {
        this.showHeader = val.url !== '/login';
        
      }
    });
    
    
  }

  logout(): void {
    this.authService.logout();
  }

}
