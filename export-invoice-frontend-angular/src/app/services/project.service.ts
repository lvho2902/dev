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

  private url = "project";
  private url_po = "purchase-order";
  private url_search = "project/search";
  private url_employee = "employee";
  private url_audit = "change-log";
  
  constructor(private http: HttpClient) { }

  public getProject(): Observable<Project[]> {
    return this.http.get<Project[]>(`${environment.apiUrl}/${this.url}`, httpOptions);
  }

  public addProject(addProjectRequest: PostProject): Observable<Project[]> {
    addProjectRequest.id = '00000'
    return this.http.post<Project[]>(`${environment.apiUrl}/${this.url}`, addProjectRequest, httpOptions);
   }

  public getid(id: string): Observable<PostProject> {
    return this.http.get<PostProject>(`${environment.apiUrl}/${this.url}` + "/" + id, httpOptions);
  }

  public updateProject(updateProjectRequest: PostProject): Observable<Project> {
    return this.http.put<Project>(`${environment.apiUrl}/${this.url}`, updateProjectRequest, httpOptions);
  }

  public deleteProject(id: string): Observable<Project> {
    return this.http.delete<Project>(`${environment.apiUrl}/${this.url}` + '/' + id, httpOptions);
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

  public getEmployeeByID(id: string): Observable<Employee[]> {

    return this.http.get<Employee[]>(`${environment.apiUrl}/${this.url_employee}` + id, httpOptions);
  }

  public downloadInvoice(id: string): Observable<Blob> {
    return this.http.get(`${environment.apiUrl}/${this.url}` + '/download/' + id, httpOptionBlob )
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
