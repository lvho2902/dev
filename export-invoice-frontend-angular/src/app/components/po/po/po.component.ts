import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ChangeLog, Po, Project } from 'src/app/models/invoice';
import { AuthService } from 'src/app/services/authentication.service';
import { PoService } from 'src/app/services/po.service';
import { ProjectService } from 'src/app/services/project.service';
import { SnackbarService } from 'src/app/services/showSnackBar';

@Component({
  selector: 'app-po',
  templateUrl: './po.component.html',
  styleUrls: ['./po.component.css']
})
export class PoComponent implements OnInit
{
  po_name = '';
  matchingPO: Po[]= [];
  pos: Po[] = [];
  addPoRequest: Po = {
    id: '',
    name: '',
    email: '',
    phone: 0,
    amount: 0,
    start_date: new Date(),
    due_date: new Date(),
};
p: number = 1;
pageSize: number = 10;
matchingProjects: Project[] = [];
po_id: number = 0;
projects: Project[] = [];
currentDate = new Date();
result = 0;
logs: ChangeLog[] = [];

constructor(
  private poService: PoService,
  private router: Router,
  private authService: AuthService,
  private SnackbarService: SnackbarService,) {}

ngOnInit(): void {
  if (this.authService.isAuthenticated()) {
    this.router.navigate(['/po']);
  }
  else{
    this.router.navigate(['/login']);
  }

  this.poService
.getPo()
.subscribe((result: Po[]) => (this.pos = result));

this.poService
.getProject()
.subscribe(
  (result: Project[]) => ((this.projects = result))
);

this.poService
.getLogs()
.subscribe((result: ChangeLog[]) => (this.logs = result));

}

deletePo(id: string) {
  this.poService.deletePo(id)
  .subscribe({
    next: () => {
      this.router.navigate(['po']);
      this.SnackbarService.showSnackBar("Delete successfully!!!", 'mat-snack-bar-container-green');
      this.poService
      .getPo()
      .subscribe((result: Po[]) => (this.pos = result));
        }
      });
    }

    onSearch(): void {
      this.poService.searchPo(this.po_name).subscribe((pos) => {
        this.matchingPO = pos;
      });
    }

    getRemainingOfPO(id: String): number {
      const now = new Date();

      const projectsIncludePO = this.projects.filter(item => item.poId === id);

      if (projectsIncludePO.length === 0) {
        const po = this.pos.find(p => p.id === id);
        if (po) {
          return po.amount;
        }
      }

      let remaining = 0;
      let sum = 0;


      for (const project of projectsIncludePO) {
        const auditIncludeProject = this.logs.filter(item => item.primaryKeyValue === project.projectId);
        const startDate = new Date(project.start_date);
        const endDate = new Date(project.due_date);

        if (auditIncludeProject.length === 1) 
        {
          const createdDate = new Date(auditIncludeProject[0].createdDate)
          const diffInMonths_audit = this.getDateDiffInMonths(startDate, createdDate);
          let diffInMonths_new;
          if (now.getDate <= endDate.getDate) {
            diffInMonths_new = this.getDateDiffInMonths(createdDate, now);
          } else {
            diffInMonths_new = this.getDateDiffInMonths(createdDate, endDate);
          }
          sum += project.rate * auditIncludeProject[0].oldValue * diffInMonths_audit
          sum += project.rate * auditIncludeProject[0].newValue * diffInMonths_new
        } 
        else if (auditIncludeProject.length > 1) {
          const createdDate = new Date(auditIncludeProject[0].createdDate)
          const diffInMonths_audit = this.getDateDiffInMonths(startDate, createdDate);
          sum += project.rate * auditIncludeProject[0].oldValue * diffInMonths_audit
          for (let i = 1; i < auditIncludeProject.length; i++) {
            const diffInMonths_created = this.getDateDiffInMonths(new Date(auditIncludeProject[i-1].createdDate), new Date(auditIncludeProject[i].createdDate));
            sum += project.rate * auditIncludeProject[i].oldValue * diffInMonths_created
          }
          let diffInMonths_new;
          if (now.getDate <= endDate.getDate) {
            diffInMonths_new = this.getDateDiffInMonths(new Date(auditIncludeProject[auditIncludeProject.length-1].createdDate), now);
          } else {
            diffInMonths_new = this.getDateDiffInMonths(new Date(auditIncludeProject[auditIncludeProject.length-1].createdDate), endDate);
          }
          sum += project.rate * project.billable * diffInMonths_new
        } 
        else 
        {
          const diffInMonths = this.getDateDiffInMonths(startDate, now);
          sum += project.rate * project.billable *diffInMonths
        }

      }

      const po = this.pos.find(p => p.id === id);
      if (po) {
        remaining = po.amount - sum;
      }

      return remaining;
    }

    getStatusPO(id: String): number{
      const projectsIncludePO = this.projects.filter(item => item.poId === id);
      let sum = 0;
      for (const project of projectsIncludePO) {
        sum += project.rate * project.billable
      }
      return this.getRemainingOfPO(id) - sum
    }

    getDateDiffInMonths(startDate: Date, endDate: Date): number {
      const oneDay = 24 * 60 * 60 * 1000;
      const diffInDays = Math.round((endDate.getTime() - startDate.getTime()) / oneDay); 
      const diffInMonths = Math.floor(diffInDays / 30); 
      return diffInMonths;
    }

}
