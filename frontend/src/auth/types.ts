type Role = "ROLE_USER" | "ROLE_ADMIN";

export interface LoginPayload {
  email: string;
  password: string;
}

export interface RegisterPayload {
  name: string;
  lastname: string;
  email: string;
  password: string;
  role: Role;
}

export interface User {
  id: string;
  name: string;
  email: string;
  avatarUrl?: string;
  createdAt: string;
  updatedAt: string;
}

//TODO --> chequear los campos que tiene register y user
