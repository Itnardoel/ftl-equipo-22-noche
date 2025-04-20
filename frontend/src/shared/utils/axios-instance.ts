import axios from "axios";

export const instance = axios.create({
  baseURL: import.meta.env.VITE_API_URL as string, //TODO --> tipar los .env dentro del archivo vite-env.ts
  withCredentials: true,
});
