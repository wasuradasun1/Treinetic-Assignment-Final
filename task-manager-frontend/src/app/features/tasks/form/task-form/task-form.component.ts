// task-form.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { TaskService } from '../../../../core/services/task.service';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.scss'],
})
export class TaskFormComponent implements OnInit {
  taskForm!: ReturnType<FormBuilder['group']>;
  isEditMode = false;
  taskId: number | null = null;
  isLoading = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private taskService: TaskService,
    private route: ActivatedRoute,
    private router: Router,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.taskForm = this.fb.group({
      title: ['', [Validators.required, Validators.maxLength(100)]],
      description: ['', Validators.maxLength(500)],
      status: ['TO_DO', Validators.required],
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.taskId = +id;
      this.loadTask();
    }
  }

  loadTask(): void {
    if (!this.taskId) return;

    this.isLoading = true;
    this.taskService.getTaskById(this.taskId).subscribe({
      next: (task) => {
        this.taskForm.patchValue({
          title: task.title,
          description: task.description,
          status: task.status,
        });
        this.isLoading = false;
      },
      error: () => {
        this.error = 'Failed to load task';
        this.isLoading = false;
      },
    });
  }

  onSubmit(): void {
    if (this.taskForm.invalid) return;

    this.isLoading = true;
    this.error = null;
    const taskData = this.taskForm.value;

    const operation =
      this.isEditMode && this.taskId
        ? this.taskService.updateTask(this.taskId, taskData)
        : this.taskService.createTask(taskData);

    operation.subscribe({
      next: () => this.router.navigate(['/tasks']),
      error: () => {
        this.error = this.isEditMode
          ? 'Failed to update task'
          : 'Failed to create task';
        this.isLoading = false;
      },
    });
  }
}
