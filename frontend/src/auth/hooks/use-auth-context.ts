import { use } from "react";
import { AuthContext } from "../context/AuthContext";

export const useAuthContext = () => {
  const context = use(AuthContext);

  if (!context) {
    throw new Error("useAuthContext must be used within an AuthProvider.");
  }
  return context;
};
