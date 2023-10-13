import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Employee, Po, PostProject, Project } from 'src/app/models/invoice';
import { ProjectService } from 'src/app/services/project.service';
import { SnackbarService } from 'src/app/services/showSnackBar';
import {FormControl} from '@angular/forms';

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css'],
})
export class AddProjectComponent implements OnInit {
  listOfEmployees: any[] = [];

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
  addEmployeeRequest: PostProject = {
    projectId: '',
    name: '',
    desc: '',
    start_date: new Date(),
    due_date: new Date(),
    poId: '',
    reference: '',
    billable: 0,
    rate: 0,
    capex_code: '',
    employeeIds: [],

  };
  selectedEmployeeIds: string[] = [];
  
  showError = false;
  projectErrorMessage = '';
  po_name = '';
  matchingProjects: any[] = [];
  
  constructor(
    private projectService: ProjectService,
    private router: Router,
    private SnackbarService: SnackbarService){}

  nzMaxTagCount: number = 0;

  ngOnInit(): void {
    
    this.selectedEmployeeIds = this.employees.filter(employee => employee.employeeId).map(employee => employee.employeeId);

    this.projectService
      .getProject()
      .subscribe(
        (result: Project[]) => ((this.projects = result), console.log(result))
      );

    this.projectService
      .getPo()
      .subscribe((result: Po[]) => (this.pos = result));

    this.projectService.getEmployee().subscribe((result: Employee[]) => {
      this.employees = result;
    });
  }

  compareDate(): boolean {
    const startDate = new Date(this.addEmployeeRequest.start_date);
    const endDate = new Date(this.addEmployeeRequest.due_date);
    console.log(startDate)
    if (endDate < startDate) {
      this.SnackbarService.showSnackBar("Please ensure that the due date is greater than to start date.", 'mat-snack-bar-container-coral');
      return false
    }
    return true
  }

  addProject() {
        if (this.compareDate()){
          this.projectService.addProject(this.addEmployeeRequest).subscribe({
            next: () => {
                  this.router.navigate(['project']);
                  this.SnackbarService.showSnackBar('Add successfully!!!', 'mat-snack-bar-container-green');
                },
                error: (error) => {
                  console.error('Error occurred:', error);
                  // Handle the error here - for example, you could show an error message to the user
                  this.SnackbarService.showSnackBar(error.error, 'mat-snack-bar-container-red');
                }
              });
              this.projectService.getProject().subscribe((result: Project[]) => (this.projects = result));
        }
      }
  
  getProjectPoName(id: string) {
    return this.pos.find((item) => item.id === id)?.name;
  }
  setNzMaxTagCount() {
    this.nzMaxTagCount = this.addEmployeeRequest.billable;
  }

}
