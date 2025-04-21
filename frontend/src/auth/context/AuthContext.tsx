import { createContext, useState, useEffect, useMemo, PropsWithChildren } from "react";
import { User } from "../types";

interface AuthContext {
  authToken?: string | null;
  user?: User | null;
  handleLogin: (userData: User) => void; //TODO --> ver si hace falta pasar informacion del usuario
  handleLogout: () => void;
}

const AuthContext = createContext<AuthContext | undefined>(undefined);

type AuthProviderProps = PropsWithChildren;

export const AuthProvider = ({ children }: AuthProviderProps) => {
  // const [authToken, setAuthToken] = useState<string | null>();
  const [user, setUser] = useState<User | null>();

  const handleLogin = (userData: User) => {
    setUser(userData);
    localStorage.setItem("user", JSON.stringify(userData));
  };

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem("user");
  };

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) setUser(JSON.parse(storedUser) as User);
  }, []);

  const value = useMemo(() => ({ user, handleLogin, handleLogout }), [user]);

  return <AuthContext value={value}>{children}</AuthContext>;
};

export { AuthContext };
