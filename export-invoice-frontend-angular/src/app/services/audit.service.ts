import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ChangeLog } from '../models/invoice';
import { environment } from '../environments/environment';
import { httpOptions } from './httpOptions';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class AuditService {

  private url = "ChangeLog";
  
  constructor(private http: HttpClient) { }

  public getLogs(): Observable<ChangeLog[]> {

    return this.http.get<ChangeLog[]>(`${environment.apiUrl}/${this.url}`, httpOptions);
  }

  public deleteLogs(auditId: number): Observable<ChangeLog> {
    return this.http.delete<ChangeLog>(`${environment.apiUrl}/${this.url}` + '/' + auditId, httpOptions);
  }
}
