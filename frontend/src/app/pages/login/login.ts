import { Component, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {

  username = '';
  password = '';
  errorMessage = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  login() {
    this.errorMessage = '';

    this.authService.login(this.username, this.password)
      .subscribe({
        next: res => {
          this.authService.saveToken(res.token);
          this.router.navigate(['/employees']);
        },
        error: err => {
          if (err.status === 401) {
            this.errorMessage = 'Wrong username or password';
          } else {
            this.errorMessage = 'Error';
          }

          this.cdr.detectChanges();
        }
      });
  }
}
