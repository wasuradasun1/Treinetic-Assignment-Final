export interface Task {
  id: number;
  title: string;
  description: string;
  status: 'TO_DO' | 'IN_PROGRESS' | 'DONE';
  createdAt: string;
  userId: number;
  username?: string;
}

export interface TaskRequest {
  title: string;
  description: string;
  status: string;
}
