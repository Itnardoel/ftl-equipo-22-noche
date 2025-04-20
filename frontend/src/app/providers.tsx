import { ReactNode } from "react";
import { AuthProvider } from "@/auth/context/AuthContext";

interface AppProvidersProps {
  children: ReactNode;
}

export const AppProviders = ({ children }: AppProvidersProps) => {
  return <AuthProvider>{children}</AuthProvider>;
};
