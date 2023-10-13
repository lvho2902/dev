import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee } from 'src/app/models/invoice';
import { AuthService } from 'src/app/services/authentication.service';
import { EmployeeService } from 'src/app/services/employee.service';
import { SnackbarService } from 'src/app/services/showSnackBar';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit
{
  search_name = '';
  matchingEmployee: Employee []= [];

  employees: Employee[] = [];
  EmployeeRequest: Employee = {
    employeeId: "",
    name: '',
    email: '',
    phone: 0,
  };
  p: number = 1;
  pageSize: number = 10;

  constructor(private employeeService: EmployeeService,
     private router: Router, 
     private authService: AuthService,
     private SnackbarService: SnackbarService) {}

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/employee']);
    }
    else{
      this.router.navigate(['/login']);
    }
    // this.router.navigate(['/employee']);
    this.employeeService
  .getEmployee()
  .subscribe((result: Employee[]) => (this.employees = result)); 
  }

  deleteEmployee(employeeId: string) {
    this.employeeService.deleteEmployee(employeeId)
    .subscribe({
      next: () => {
        this.router.navigate(['employee']);
        this.SnackbarService.showSnackBar("Delete successfully!!!", 'mat-snack-bar-container-green');

        this.employeeService
    .getEmployee()
    .subscribe((result: Employee[]) => (this.employees = result));
      }
      
    });
  }
  onSearch(): void {
    this.employeeService.searchByName(this.search_name).subscribe((employees) => {
      this.matchingEmployee = employees;
    });
  }
  
}
