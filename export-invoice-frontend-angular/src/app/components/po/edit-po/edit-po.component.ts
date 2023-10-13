import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Po } from 'src/app/models/invoice';
import { PoService } from 'src/app/services/po.service';
import { SnackbarService } from 'src/app/services/showSnackBar';

@Component({
  selector: 'app-edit-po',
  templateUrl: './edit-po.component.html',
  styleUrls: ['./edit-po.component.css']
})
export class EditPoComponent implements OnInit{
  poDetails: Po = {
    id: '',
    name: '',
    email: '',
    phone: 0,
    amount: 0,
    start_date: new Date(),
    due_date: new Date(),
}

constructor(private route: ActivatedRoute, private poService: PoService, private router: Router, private SnackbarService:SnackbarService) {}
compareDate(): boolean {
  const startDate = new Date(this.poDetails.start_date);
  const endDate = new Date(this.poDetails.due_date);
  console.log(startDate)
  if (endDate < startDate) {
    this.SnackbarService.showSnackBar("Please ensure that the due date is greater than to start date.", 'mat-snack-bar-container-coral');
    return false
  }
  return true
}
  ngOnInit(): void {
      this.route.paramMap.subscribe(
        {
          next: (params) => {
            const id = params.get('id');

            if (id) {
              this.poService.getPoId(id)
              .subscribe({
                next: (response: Po) => {
                  console.log(response)
                  this.poDetails = response;
                }
              });

            }
          }
        }
      )
  }

  updatePo() {
    if (this.compareDate()){

    this.poService.updatePo(this.poDetails)
    .subscribe({
      next: () => {
        this.router.navigate(['po']);
        this.SnackbarService.showSnackBar("Update successfully!!!", 'mat-snack-bar-container-green');
      }
    })};
  }
}
