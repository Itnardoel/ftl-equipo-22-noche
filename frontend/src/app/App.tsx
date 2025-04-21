import { BrowserRouter } from "react-router-dom";
import { AppRoutes } from "@/routes/index";

const App = () => {
  return (
    <BrowserRouter>
      <AppRoutes />
    </BrowserRouter>
  );
};

export default App;
