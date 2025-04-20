import { instance } from "@/shared/utils/axios-instance";
import { LoginPayload, RegisterPayload, User } from "@/auth/types";

export const login = (data: LoginPayload) => instance.post<User>("/auth/login", data); //TODO --> confirmar el endpoint

export const register = (data: RegisterPayload) => instance.post<User>("/auth/register", data); //TODO --> confirmar el endpoint
