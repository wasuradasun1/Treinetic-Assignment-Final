import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { AuthResponse, AuthRequest } from '../models/auth.model';
import { environment } from '../../../environments/environment.development';
import { Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/api/auth`;
  private router = inject(Router);

  login(credentials: AuthRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.apiUrl}/authenticate`, credentials)
      .pipe(tap((response) => this.storeAuthData(response)));
  }

  register(credentials: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, credentials);
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('username');
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getCurrentUserId(): number | null {
    const userId = localStorage.getItem('userId');
    return userId ? parseInt(userId, 10) : null;
  }

  getCurrentUsername(): string | null {
    return localStorage.getItem('username');
  }

  private storeAuthData(response: AuthResponse): void {
    localStorage.setItem('token', response.token);
    localStorage.setItem('userId', response.userId.toString());
    localStorage.setItem('username', response.username);
  }
}
