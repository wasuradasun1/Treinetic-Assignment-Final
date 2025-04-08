export interface AuthResponse {
  token: string;
  userId: number;
  username: string;
}

export interface AuthRequest {
  username: string;
  password: string;
}
