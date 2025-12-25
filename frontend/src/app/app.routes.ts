import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login';
import { EmployeeComponent } from './pages/employee/employee';
import { CertificateComponent } from './pages/certificate/certificate';
import { LanguageComponent } from './pages/language/language';
import { authGuard } from './core/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'employees', component: EmployeeComponent, canActivate: [authGuard] },
  { path: 'certificates', component: CertificateComponent, canActivate: [authGuard] },
  { path: 'languages', component: LanguageComponent, canActivate: [authGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];
