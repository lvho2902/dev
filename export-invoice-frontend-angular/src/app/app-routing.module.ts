import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddEmployeeComponent } from './components/employee/add-employee/add-employee.component';
import { EditEmployeeComponent } from './components/employee/edit-employee/edit-employee.component';
import { AddProjectComponent } from './components/project/add-project/add-project.component';
import { EditProjectComponent } from './components/project/edit-project/edit-project.component';
import { AddPoComponent } from './components/po/add-po/add-po.component';
import { EditPoComponent } from './components/po/edit-po/edit-po.component';
import { LoginComponentTokenService } from './Authentication/login-authentication/login-authentication.component';
import { LayoutComponent } from './LayoutComponents/layout/layout.component';
import { EmployeeComponent } from './components/employee/employee/employee.component';
import { PoComponent } from './components/po/po/po.component';
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
    path: 'po',
  component: PoComponent 
  },
  {
    path: 'po/add',
    component: AddPoComponent
  },
  {
    path: 'po/edit/:id',
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
