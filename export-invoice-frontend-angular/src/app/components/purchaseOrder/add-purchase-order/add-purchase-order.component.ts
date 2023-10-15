import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Po } from 'src/app/models/invoice';
import { PoService } from 'src/app/services/purchaseOrder.service';
import { SnackbarService } from 'src/app/services/showSnackBar';

@Component({
  selector: 'app-add-purchase-order',
  templateUrl: './add-purchase-order.component.html',
  styleUrls: ['./add-purchase-order.component.css']
})
export class AddPoComponent implements OnInit 
{
  pos: Po[] = [];
  addPoRequest: Po = {
    id: '',
    name: '',
    email: '',
    phone: 0,
    amount: 1,
    startDate: new Date(),
    dueDate: new Date(),
}

constructor(private poService: PoService, private router: Router, private SnackbarService:SnackbarService) {}

ngOnInit(): void {
  this.poService
.getPo()
.subscribe((result: Po[]) => (this.pos = result)); 
}
compareDate(): boolean {
  const startDate = new Date(this.addPoRequest.startDate);
  const endDate = new Date(this.addPoRequest.dueDate);
  console.log(startDate)
  if (endDate < startDate) {
    this.SnackbarService.showSnackBar("Please ensure that the due date is greater than to start date.", 'mat-snack-bar-container-coral');
    return false
  }
  return true
}

addPo() {
  if (this.compareDate()){
  this.poService.addPo(this.addPoRequest)
  .subscribe({
    next: () => {
      this.router.navigate(['po']);
      this.SnackbarService.showSnackBar('Add successfully!!!', 'mat-snack-bar-container-green');
      this.poService
      .getPo()
      .subscribe((result: Po[]) => (this.pos = result));
    },
    error: (error) => {
      console.error('Error occurred:', error);
      // Handle the error here - for example, you could show an error message to the user
      this.SnackbarService.showSnackBar(error.error, 'mat-snack-bar-container-red');
    }
  })};
}
}
