import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Employee } from 'src/app/models/invoice';
import { EmployeeService } from 'src/app/services/employee.service';
import { Router } from '@angular/router';
import { SnackbarService } from 'src/app/services/showSnackBar';

@Component({
  selector: 'app-edit-employee',
  templateUrl: './edit-employee.component.html',
  styleUrls: ['./edit-employee.component.css']
})
export class EditEmployeeComponent implements OnInit {
  employeeDetails: Employee = {
    id: '',
    name: '',
    email: '',
    phone: 0,
  }


  constructor(private route: ActivatedRoute, private employeeService: EmployeeService, 
    private router: Router, private SnackbarService:SnackbarService) {}

  ngOnInit(): void {
      this.route.paramMap.subscribe(
        {
          next: (params) => {
            const id = params.get('id');

            if (id) {
              this.employeeService.getid(id)
              .subscribe({
                next: (response: Employee) => {
                  console.log(response)
                  this.employeeDetails = response;
                }
              });

            }
          }
        }
      )
  }

  updateEmployee() {
    this.employeeService.updateEmployee(this.employeeDetails)
    .subscribe({
      next: () => {
        this.router.navigate(['employee']);
        this.SnackbarService.showSnackBar("Updated successfully!!!", 'mat-snack-bar-container-green');
      }
    });
  }

}
