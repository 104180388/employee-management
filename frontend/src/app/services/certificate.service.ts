import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  private API = 'http://localhost:8080/api/certificates';

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<any[]>(this.API);
  }

  getById(id: number) {
    return this.http.get<any>(`${this.API}/${id}`);
  }

  create(certificate: any) {
    return this.http.post(this.API, certificate);
  }

  delete(id: number) {
    return this.http.delete(`${this.API}/${id}`);
  }
}
