import { Component, OnInit, ChangeDetectorRef, AfterContentChecked   } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { saveAs } from 'file-saver';
import { User, Employee, Po, Project, ChangeLog } from 'src/app/models/invoice';
import { AuthService } from 'src/app/services/authentication.service';
import { PoService } from 'src/app/services/po.service';
import { ProjectService } from 'src/app/services/project.service';
import { SnackbarService } from 'src/app/services/showSnackBar';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})


export class ProjectComponent implements OnInit, AfterContentChecked 
{
  projects: Project[] = [];
  employees: Employee[] = [];
  pos: Po[] = [];
  addProjectRequest: Project = {
    projectId: 'Name',
    name: '',
    desc: '',
    start_date: new Date(),
    due_date: new Date(),
    poId: '',
    reference: '',
    billable: 0,
    rate: 0,
    capex_code: '',
    employeeNames: [],
  };
  po_name = '';
  matchingProjects: any[] = [];
  selectedPo: any;
  showPoTable = false;
  poDetails: Po = {
    id: '',
    name: '',
    email: '',
    phone: 0,
    amount: 0,
    start_date: new Date(),
    due_date: new Date(),
};
result =0;
  user: User[] = [];
  p: number = 1;
  pageSize: number = 10;

  po_id: number = 0;
  logs: ChangeLog[] = [];
 

  constructor(
    private projectService: ProjectService,
    private router: Router,
    private authService: AuthService,
    private poService: PoService,
    private SnackbarService: SnackbarService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/project']);
    }
    else{
      this.router.navigate(['/login']);
    }

    this.projectService
      .getProject()
      .subscribe(
        (result: Project[]) => ((this.projects = result))
      );

    this.projectService
      .getPo()
      .subscribe((result: Po[]) => (this.pos = result));

    this.projectService.getEmployee().subscribe((result: Employee[]) => {
      this.employees = result;
    });

    this.projectService
.getLogs()
.subscribe((result: ChangeLog[]) => (this.logs = result)); 
  }

  ngAfterContentChecked() {
    this.cdr.detectChanges();
  }

  getProjectPoName(id: string) {
    return this.pos.find((item) => item.id === id)?.name;
  }
  getProjectName(id: string) {
    return this.projects.find((item) => item.projectId === id)?.name;
  }

  onSearch(): void {
    this.projectService.searchProjects(this.po_name).subscribe((projects) => {
      this.matchingProjects = projects;
    });
  }


  downloadInvoice(invoiceId: string) {
      this.projectService.downloadInvoice(invoiceId)
      .subscribe(
        blob => {
          var month = ('0' + (new Date(new Date().toLocaleString()).getMonth() + 1)).slice(-2);
          var dateInvoiced = (new Date()).toLocaleString('default', { month: 'short', year: 'numeric' }).toUpperCase().replace(' ', '');
          saveAs(blob, `Invoice ${month}-${this.getProjectName(invoiceId)}-${dateInvoiced}.docx`);
        },
        () =>{
          this.SnackbarService.showSnackBar('The User do not have permission to perform', 'mat-snack-bar-container-red');
        })
  
    };

    exportAllInvoices() {
      this.projectService.exportAllInvoices()
      .subscribe(
        blob => {
          var localeDateTime = (new Date()).toLocaleString().replace(' ', '');
          saveAs(blob, `invoices-${localeDateTime}.zip`);
        },
        () => {
          this.SnackbarService.showSnackBar('The User do not have permission to perform', 'mat-snack-bar-container-red');
        })
      };
    
  
  deleteProject(projectId: string) {
      this.projectService.deleteProject(projectId)
      .subscribe({
        next: () => {
          this.router.navigate(['project']);
          this.SnackbarService.showSnackBar("Delete successfully", 'mat-snack-bar-container-green');
       this.projectService
        .getProject()
        .subscribe((result: Project[]) => (this.projects = result));
    },
  });
}

showPoInfo(id: string): any {
  this.poService.getPoId(id)
              .subscribe({
                next: (response: Po) => {
                  this.poDetails = response;
                  setTimeout(() => {
                    this.cdr.detectChanges();
                  }, 1);
                }
              }); 
              
}

getStatus(dueDate: Date): string {
  const now = new Date();
  const due = new Date(dueDate);
  return due > now? 'Active' : 'Expired';
}

  getProjectbyPoId(): void {
    this.projectService.getRelatedProjectByPOId(this.po_id).subscribe((projects) => {
      this.matchingProjects = projects;
      
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
