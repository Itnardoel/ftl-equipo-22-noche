import { SubmitHandler, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { login } from "../api/index";
import { useAuth } from "../hooks/use-auth-context";

const schema = z.object({
  email: z.string().email(),
  password: z.string().min(8),
});

type FormFields = z.infer<typeof schema>;

export const LoginForm = () => {
  const {
    register,
    handleSubmit,
    setError,
    formState: { errors, isSubmitting },
  } = useForm<FormFields>({
    resolver: zodResolver(schema),
  });

  const { handleLogin: setToken } = useAuth();

  const onSubmit: SubmitHandler<FormFields> = async (formData: FormFields) => {
    try {
      const { data } = await login(formData);
      console.log("RESPUESTA DEL SERVIDOR LOGINFORM --> ", data);

      if (!data) {
        throw new Error("Error al intentar ingresar");
      }

      setToken(data);
    } catch (error) {
      console.error(error);
      setError("root", {
        message: error as string,
      });
    }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <input {...register("email")} type="text" placeholder="Email" />
      {errors.email && <div className="text-red-500">{errors.email.message}</div>}
      <input {...register("password")} type="password" placeholder="Password" />
      {errors.password && <div className="text-red-500">{errors.password.message}</div>}
      <button disabled={isSubmitting} type="submit">
        {isSubmitting ? "Ingresando..." : "Ingresar"}
      </button>
      {errors.root && <div className="text-red-500">{errors.root.message}</div>}
    </form>
  );
};
