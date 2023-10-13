  import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
  import { Employee } from 'src/app/models/invoice';
import { EmployeeService } from 'src/app/services/employee.service';
import { SnackbarService } from 'src/app/services/showSnackBar';
import { ActivatedRoute } from '@angular/router';

  @Component({
    selector: 'app-add-employee',
    templateUrl: './add-employee.component.html',
    styleUrls: ['./add-employee.component.css']
  })
  export class AddEmployeeComponent implements OnInit 
  {

    employees: Employee[] = [];
    EmployeeRequest: Employee = {
      employeeId: "",
      name: '',
      email: '',
      phone: 0,
    };

    constructor(
      private employeeService: EmployeeService, 
      private router: Router, 
      private route: ActivatedRoute, 
      private SnackbarService: SnackbarService,
      ) {}

    ngOnInit(): void {

      this.employeeService
    .getEmployee()
    .subscribe((result: Employee[]) => (this.employees = result));

        
    }


    addEmployee() {
      this.employeeService.addEmployee(this.EmployeeRequest)
      .subscribe({
        next: () => {
          this.router.navigate(['employee']);
          this.SnackbarService.showSnackBar("Add successfully!!!", 'mat-snack-bar-container-green');

      this.employeeService
      .getEmployee()
      .subscribe((result: Employee[]) => (this.employees = result));
        }
        
      });
    
    }
  }
