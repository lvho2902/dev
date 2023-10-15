import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { AddEmployeeComponent } from './components/employee/add-employee/add-employee.component';
import { EditEmployeeComponent } from './components/employee/edit-employee/edit-employee.component';
import { AddProjectComponent } from './components/project/add-project/add-project.component';
import { EditProjectComponent } from './components/project/edit-project/edit-project.component';
import { AddPoComponent } from './components/purchaseOrder/add-purchase-order/add-purchase-order.component';
import { EditPoComponent } from './components/purchaseOrder/edit-purchase-order/edit-purchase-order.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import {MatInputModule} from '@angular/material/input';
import { LoginComponentTokenService } from './Authentication/login-authentication/login-authentication.component';
import { JwtModule } from '@auth0/angular-jwt';
import { LayoutComponent } from './LayoutComponents/layout/layout.component';
import { EmployeeComponent } from './components/employee/employee/employee.component';
import { PoComponent } from './components/purchaseOrder/purchase-order/purchase-order.component';
import { ProjectComponent } from './components/project/project/project.component';;
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';
import { NgxPaginationModule } from 'ngx-pagination';
import { MatButtonModule } from '@angular/material/button';
import {MatCardModule} from '@angular/material/card'
import { FlexLayoutModule } from '@angular/flex-layout';
import {MatFormFieldModule} from '@angular/material/form-field'
import {MatCheckboxModule} from '@angular/material/checkbox'
import { MatSelectModule } from '@angular/material/select';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { GetAuditComponent } from './components/audit/get-audit/get-audit.component';

export function tokenGetter() {
  return localStorage.getItem('token');
}


@NgModule({
  declarations: [
    AppComponent,
    AddEmployeeComponent,
    EditEmployeeComponent,
    AddProjectComponent,
    EditProjectComponent,
    AddPoComponent,
    EditPoComponent,
    LayoutComponent,
    LoginComponentTokenService,
    EmployeeComponent,
    PoComponent,
    ProjectComponent,
    GetAuditComponent,
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    NoopAnimationsModule,
    NgxPaginationModule,
    AppRoutingModule,
    HttpClientModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        allowedDomains: ['localhost:7262'],
        disallowedRoutes: []  }
      }),

    FormsModule,
    MatSnackBarModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatInputModule,
    FlexLayoutModule,
    MatCardModule,
    MatFormFieldModule,
    MatCheckboxModule,
    MatSelectModule,
    NzSelectModule,
    NzIconModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
