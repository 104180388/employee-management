import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private API = 'http://localhost:8080/api/employees';

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<any[]>(this.API);
  }

  getById(id: number) {
    return this.http.get<any>(`${this.API}/${id}`);
  }

  create(employee: any) {
    return this.http.post(this.API, employee);
  }

  delete(id: number) {
    return this.http.delete(`${this.API}/${id}`);
  }

  update(id: number, employee: any) {
    return this.http.put(`${this.API}/${id}`, employee);
  }
}
