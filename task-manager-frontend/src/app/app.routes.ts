import { Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { TaskListComponent } from './features/tasks/list/task-list/task-list.component';
import { TaskFormComponent } from './features/tasks/form/task-form/task-form.component';
import { TaskDetailsComponent } from './features/tasks/details/task-details/task-details.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'tasks',
    canActivate: [AuthGuard],
    children: [
      { path: '', component: TaskListComponent },
      { path: 'new', component: TaskFormComponent },
      { path: 'edit/:id', component: TaskFormComponent },
      { path: ':id', component: TaskDetailsComponent },
    ],
  },
  { path: '', redirectTo: '/tasks', pathMatch: 'full' },
  { path: '**', redirectTo: '/tasks' },
];
