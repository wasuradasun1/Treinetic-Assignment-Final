// task-list.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaskService } from '../../../../core/services/task.service';
import { AuthService } from '../../../../core/services/auth.service';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Task } from '../../../../core/models/task.model';
@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']
})
export class TaskListComponent implements OnInit {
  tasks: Task[] = [];
  filteredTasks: Task[] = [];
  statusFilter = 'ALL';
  isLoading = false;
  error: string | null = null;

  constructor(
    public authService: AuthService,
    private taskService: TaskService
  ) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void {
    this.isLoading = true;
    this.error = null;
    
    this.taskService.getAllTasks().subscribe({
      next: tasks => {
        this.tasks = tasks;
        this.applyFilter();
        this.isLoading = false;
      },
      error: () => {
        this.error = 'Failed to load tasks. Please try again later.';
        this.isLoading = false;
      }
    });
  }

  applyFilter(): void {
    this.filteredTasks = this.statusFilter === 'ALL'
      ? [...this.tasks]
      : this.tasks.filter(task => task.status === this.statusFilter);
  }

  onFilterChange(): void {
    this.applyFilter();
  }

  deleteTask(id: number): void {
    if (confirm('Are you sure you want to delete this task?')) {
      this.taskService.deleteTask(id).subscribe({
        next: () => {
          this.tasks = this.tasks.filter(task => task.id !== id);
          this.applyFilter();
        },
        error: () => alert('Failed to delete task')
      });
    }
  }
}