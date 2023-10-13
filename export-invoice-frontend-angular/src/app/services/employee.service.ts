import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { Employee } from '../models/invoice';
import { environment } from '../environments/environment';
import { httpOptions } from './httpOptions';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private url = "employee";
  
  constructor(private http: HttpClient) { }

  public getEmployee(): Observable<Employee[]> {

    return this.http.get<Employee[]>(`${environment.apiUrl}/${this.url}`, httpOptions);
  }

  public addEmployee(addEmployeeRequest: Employee): Observable<Employee[]> {
    addEmployeeRequest.employeeId = '00000'

    return this.http.post<Employee[]>(`${environment.apiUrl}/${this.url}`, addEmployeeRequest, httpOptions);
  }

  public getEmployeeId(employeeId: string): Observable<Employee> {
    return this.http.get<Employee>(`${environment.apiUrl}/${this.url}` + '/' + employeeId + '?id=' + employeeId, httpOptions);
  }

  public updateEmployee(updateEmployeeRequest: Employee): Observable<Employee> {
    return this.http.put<Employee>(`${environment.apiUrl}/${this.url}`, updateEmployeeRequest, httpOptions);
  }

  public deleteEmployee(employeeId: string): Observable<Employee> {
    return this.http.delete<Employee>(`${environment.apiUrl}/${this.url}` + '/' + employeeId + '?id=' + employeeId, httpOptions);
  }
  
  searchByName(name: string): Observable<Employee[]> {
    const url = (`${environment.apiUrl}/${this.url}` + '/search/' +name);
    return this.http.get<Employee[]>(url, httpOptions);
  }
}
