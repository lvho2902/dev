import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ChangeLog } from 'src/app/models/invoice';
import { AuditService } from 'src/app/services/audit.service';
import { AuthService } from 'src/app/services/authentication.service';
import { SnackbarService } from 'src/app/services/showSnackBar';

@Component({
  selector: 'app-get-audit',
  templateUrl: './get-audit.component.html',
  styleUrls: ['./get-audit.component.css']
})
export class GetAuditComponent implements OnInit{
  logs: ChangeLog[] = [];
  p: number = 1;
  pageSize: number = 10;

  constructor(private auditService: AuditService,
    private router: Router, 
    private authService: AuthService,
    private SnackbarService: SnackbarService,) {}

    ngOnInit(): void {
      if (this.authService.isAuthenticated()) {
        this.router.navigate(['/audit']);
      }
      else{
        this.router.navigate(['/login']);
      }
  
      this.auditService
    .getLogs()
    .subscribe((result: ChangeLog[]) => (this.logs = result)); 
    }

    deleteAudit(id: number) {
      this.auditService.deleteLogs(id)
      .subscribe({
        next: () => {
          this.router.navigate(['audit']);
          this.SnackbarService.showSnackBar("Delete successfully!!!", 'mat-snack-bar-container-green');
          this.auditService
          .getLogs()
          .subscribe((result: ChangeLog[]) => (this.logs = result));
            }
          });
        }

}
