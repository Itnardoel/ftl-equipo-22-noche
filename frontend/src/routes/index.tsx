import { Routes, Route } from "react-router-dom";
import LoginPage from "@/auth/components/LoginForm";

export const AppRoutes = () => (
  <Routes>
    <Route path="/login" element={<LoginPage />} />
  </Routes>
);
