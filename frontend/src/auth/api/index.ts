import { instance } from "@/shared/utils/axios-instance";
import { LoginPayload, RegisterPayload, User } from "@/auth/types";

export const login = (data: LoginPayload) => instance.post<User | null>("/auth/login", data); //TODO --> chequear el tipo de la respuesta <User | null>?

export const register = (data: RegisterPayload) => instance.post<User>("/auth/register", data); //TODO --> chequear el tipo de la respuesta
