import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { ChangeLog, Po, Project } from '../models/invoice';
import { environment } from '../environments/environment';
import { httpOptions } from './httpOptions';

@Injectable({
  providedIn: 'root'
})
export class PoService {

  private url = "purchase-order";
  private url_search = "Project/search";
  private url_getproject = "project"
  private url_audit = "ChangeLog";
  
  constructor(private http: HttpClient) { }

  public getPo(): Observable<Po[]> {

    return this.http.get<Po[]>(`${environment.apiUrl}/${this.url}`, httpOptions);
  }

  public addPo(addPoRequest: Po): Observable<Po[]> {
    // addPoRequest.id = ''

    return this.http.post<Po[]>(`${environment.apiUrl}/${this.url}`, addPoRequest, httpOptions);
  }

  public getPoId(id: string): Observable<Po> {
    return this.http.get<Po>(`${environment.apiUrl}/${this.url}` + '/' + id, httpOptions);
  }

  public updatePo(updatePoRequest: Po): Observable<Po> {
    return this.http.put<Po>(`${environment.apiUrl}/${this.url}`, updatePoRequest, httpOptions);
  }

  public deletePo(id: string): Observable<Po> {
    return this.http.delete<Po>(`${environment.apiUrl}/${this.url}` + '/' + id, httpOptions);
  }

  searchPo(poName: string): Observable<Po[]> {
    const url = (`${environment.apiUrl}/${this.url}` + '/search/' +poName);
    return this.http.get<Po[]>(url, httpOptions);
  }

  public getRelatedProjectByPOId(id?: number): Observable<Project[]> {
    const url = (`${environment.apiUrl}/${this.url_search}`+ '/' + id);
    return this.http.get<Project[]>(url, httpOptions);
  }

  public getProject(): Observable<Project[]> {
    return this.http.get<Project[]>(`${environment.apiUrl}/${this.url_getproject}`, httpOptions);
  }

  public getLogs(): Observable<ChangeLog[]> {

    return this.http.get<ChangeLog[]>(`${environment.apiUrl}/${this.url_audit}`, httpOptions);
  }
  
}
