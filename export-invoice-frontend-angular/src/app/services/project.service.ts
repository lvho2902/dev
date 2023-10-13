import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { environment } from '../environments/environment';
import { ChangeLog, Employee, Po, PostProject, Project } from '../models/invoice';
import { httpOptions, httpOptionBlob } from './httpOptions';


@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private url = "Project";
  private url_po = "Po";
  private url_search = "Project/search";
  private url_employee = "Employee";
  private url_audit = "ChangeLog";
  
  constructor(private http: HttpClient) { }

  public getProject(): Observable<Project[]> {
    return this.http.get<Project[]>(`${environment.apiUrl}/${this.url}`, httpOptions);
  }

  public addProject(addProjectRequest: PostProject): Observable<Project[]> {
    addProjectRequest.projectId = '00000'
    return this.http.post<Project[]>(`${environment.apiUrl}/${this.url}`, addProjectRequest, httpOptions);
   }

  public getProjectId(projectId: string): Observable<PostProject> {
    return this.http.get<PostProject>(`${environment.apiUrl}/${this.url}` + '/GetName/' + projectId, httpOptions);
  }

  public updateProject(updateProjectRequest: PostProject): Observable<Project> {
    return this.http.put<Project>(`${environment.apiUrl}/${this.url}`, updateProjectRequest, httpOptions);
  }

  public deleteProject(projectId: string): Observable<Project> {
    return this.http.delete<Project>(`${environment.apiUrl}/${this.url}` + '/' + projectId + '?id=' + projectId, httpOptions);
  }
  public getPo(): Observable<Po[]> {

    return this.http.get<Po[]>(`${environment.apiUrl}/${this.url_po}`, httpOptions);
  }

  public searchProjects(poName: string): Observable<Project[]> {
    const url = (`${environment.apiUrl}/${this.url_search}`+ '/' + poName);
    return this.http.get<Project[]>(url, httpOptions);
  }

  
  public getEmployee(): Observable<Employee[]> {

    return this.http.get<Employee[]>(`${environment.apiUrl}/${this.url_employee}`, httpOptions);
  }

  public getEmployeeByID(employeeId: string): Observable<Employee[]> {

    return this.http.get<Employee[]>(`${environment.apiUrl}/${this.url_employee}` + '/' + employeeId, httpOptions);
  }

  public downloadInvoice(projectId: string): Observable<Blob> {
    return this.http.get(`${environment.apiUrl}/${this.url}` + '/download/' + projectId, httpOptionBlob )
  }

  public exportAllInvoices(): Observable<Blob> {
    return this.http.get(`${environment.apiUrl}/${this.url}` + '/download/all',httpOptionBlob )
  }

  public getRelatedProjectByPOId(id: number): Observable<Project[]> {
    const url = (`${environment.apiUrl}/${this.url_search}`+ '/' + id);
    return this.http.get<Project[]>(url, httpOptions);
  }

  public getLogs(): Observable<ChangeLog[]> {

    return this.http.get<ChangeLog[]>(`${environment.apiUrl}/${this.url_audit}`, httpOptions);
  }
}
