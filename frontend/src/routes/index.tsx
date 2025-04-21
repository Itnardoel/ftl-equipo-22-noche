import { Routes, Route } from "react-router-dom";
import { LoginForm } from "@/auth/components/LoginForm";

export const AppRoutes = () => (
  <Routes>
    <Route path="/login" element={<LoginForm />} />
  </Routes>
);
