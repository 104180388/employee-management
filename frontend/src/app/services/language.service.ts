import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {

  private API = 'http://localhost:8080/api/languages';

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<any[]>(this.API);
  }

  getById(id: number) {
    return this.http.get<any>(`${this.API}/${id}`);
  }

  create(language: any) {
    return this.http.post(this.API, language);
  }

  delete(id: number) {
    return this.http.delete(`${this.API}/${id}`);
  }
}
