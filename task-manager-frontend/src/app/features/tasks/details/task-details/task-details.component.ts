// task-details.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
//import { TaskService } from '../../../core/services/task.service';
import { TaskService } from '../../../../core/services/task.service';
//import { AuthService } from '../../../core/services/auth.service';
import { AuthService } from '../../../../core/services/auth.service';
import { Task } from '../../../../core/models/task.model';

@Component({
  selector: 'app-task-details',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.scss']
})
export class TaskDetailsComponent implements OnInit {
  task: Task | null = null;
  isLoading = false;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private taskService: TaskService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadTask(+id);
    }
  }

  loadTask(id: number): void {
    this.isLoading = true;
    this.error = null;
    
    this.taskService.getTaskById(id).subscribe({
      next: task => {
        this.task = task;
        this.isLoading = false;
      },
      error: () => {
        this.error = 'Failed to load task details';
        this.isLoading = false;
      }
    });
  }
}