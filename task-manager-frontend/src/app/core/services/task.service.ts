import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
//import { Environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { Task, TaskRequest } from '../models/task.model';
import { environment } from '../../../environments/environment.development';

@Injectable({ providedIn: 'root' })
export class TaskService {
  private http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/api/tasks`;

  getAllTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(this.apiUrl);
  }

  getTaskById(id: number): Observable<Task> {
    return this.http.get<Task>(`${this.apiUrl}/${id}`);
  }

  createTask(task: TaskRequest): Observable<Task> {
    return this.http.post<Task>(this.apiUrl, task);
  }

  updateTask(id: number, task: TaskRequest): Observable<Task> {
    return this.http.put<Task>(`${this.apiUrl}/${id}`, task);
  }

  deleteTask(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
