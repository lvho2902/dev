import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee, Po, PostProject, Project } from 'src/app/models/invoice';
import { ProjectService } from 'src/app/services/project.service';
import { SnackbarService } from 'src/app/services/showSnackBar';


@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.css']
})
export class EditProjectComponent implements OnInit {
  projectDetails: PostProject = {
    id: '',
    name: '',
    description: '',
    startDate: new Date(),
    dueDate: new Date(),
    purchaseOrderId: '',
    reference: '',
    billable: 0,
    rate: 0,
    capexCode: '',
    employeeIds: [],
  };
  employees: Employee[] = [];
  pos: Po[] = [];
  nzMaxTagCount: number = 0;
  maxTagPlaceholder: string = ''
  constructor(private route: ActivatedRoute, private projectService: ProjectService,
     private router: Router, private SnackbarService:SnackbarService) {}

  ngOnInit(): void {
    
    this.route.paramMap.subscribe(
      {
        next: (params) => {
          const id = params.get('id');

          if (id) {
            this.projectService.getid(id)
            .subscribe({
              next: (response: PostProject) => {
                this.projectDetails = response;
                this.nzMaxTagCount = this.projectDetails.billable;
              }
            });

              this.projectService
              .getPo()
              .subscribe((result: Po[]) => (this.pos = result));
        
            this.projectService.getEmployee().subscribe((result: Employee[]) => {
              this.employees = result;
            });
            this.projectService.getEmployeeByID(this.projectDetails.employeeIds.toString()).subscribe((selectedEmployee: any[])=>{
              // this.listOfSelectedEmployee = selectedEmployee
              console.log("Nhat" + selectedEmployee)
              
            })
            }
          }
          
        }
      )

  }

  updateProject() {
    this.projectService.updateProject(this.projectDetails)
    .subscribe({
      next: () => {
        this.router.navigate(['project']);
        this.SnackbarService.showSnackBar('Updated successfully!!!', 'mat-snack-bar-container-green');
      }
    });
   }

setNzMaxTagCount() {
  this.nzMaxTagCount = this.projectDetails.billable;
  this.maxTagPlaceholder = `${this.nzMaxTagCount} items max`;

}
}
