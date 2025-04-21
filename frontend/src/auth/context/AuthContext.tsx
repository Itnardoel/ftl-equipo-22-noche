import { createContext, useState, useEffect, useMemo, PropsWithChildren } from "react";
import { jwtDecode } from "jwt-decode";
import { User } from "../types";

interface AuthContext {
  authToken?: string | null;
  user?: User | null;
  handleLogin: (userToken: string) => void;
  handleLogout: () => void;
}

const AuthContext = createContext<AuthContext | undefined>(undefined);

type AuthProviderProps = PropsWithChildren;

export const AuthProvider = ({ children }: AuthProviderProps) => {
  const [authToken, setAuthToken] = useState<string | null>();
  const [user, setUser] = useState<User | null>();

  const handleLogin = (userToken: string) => {
    setAuthToken(userToken);
    localStorage.setItem("userToken", JSON.stringify(userToken));

    const userData = jwtDecode<User>(userToken);
    setUser(userData);
  };

  const handleLogout = () => {
    setAuthToken(null);
    setUser(null);
    localStorage.removeItem("userToken");
  };

  useEffect(() => {
    const storedToken = localStorage.getItem("userToken");
    if (storedToken) setAuthToken(JSON.parse(storedToken) as string);
  }, []);

  const value = useMemo(() => ({ authToken, user, handleLogin, handleLogout }), [authToken, user]);

  return <AuthContext value={value}>{children}</AuthContext>;
};

export { AuthContext };
