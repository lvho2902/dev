import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddEmployeeComponent } from './components/employee/add-employee/add-employee.component';
import { EditEmployeeComponent } from './components/employee/edit-employee/edit-employee.component';
import { AddProjectComponent } from './components/project/add-project/add-project.component';
import { EditProjectComponent } from './components/project/edit-project/edit-project.component';
import { AddPoComponent } from './components/purchaseOrder/add-purchase-order/add-purchase-order.component';
import { EditPoComponent } from './components/purchaseOrder/edit-purchase-order/edit-purchase-order.component';
import { LoginComponentTokenService } from './Authentication/login-authentication/login-authentication.component';
import { LayoutComponent } from './LayoutComponents/layout/layout.component';
import { EmployeeComponent } from './components/employee/employee/employee.component';
import { PoComponent } from './components/purchaseOrder/purchase-order/purchase-order.component';
import { ProjectComponent } from './components/project/project/project.component';
import { GetAuditComponent } from './components/audit/get-audit/get-audit.component';


const routes: Routes = [  
  {
    path: 'employee',
    component: EmployeeComponent
  },
  {
    path: 'employee/add',
    component: AddEmployeeComponent
  },
  {
    path: 'employee/edit/:id',
    component: EditEmployeeComponent
  },
  {
    path: 'project',
    component: ProjectComponent
  },
  {
    path: 'project/add',
    component: AddProjectComponent
  },
  {
    path: 'project/edit/:id',
    component: EditProjectComponent
  },
  {
    path: 'purchase-order',
  component: PoComponent 
  },
  {
    path: 'purchase-order/add',
    component: AddPoComponent
  },
  {
    path: 'purchase-order/edit/:id',
    component: EditPoComponent
  },
  {
    path: 'login',
    component: LoginComponentTokenService
  },
  {
    path: 'audit',
    component: GetAuditComponent
  },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
