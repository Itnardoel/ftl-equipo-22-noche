# Imagen base de Java
FROM openjdk:17-jdk-slim

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el .jar al contenedor
COPY target/*.jar app.jar

# Exponer el puerto que usa Spring Boot
EXPOSE 8080

# Comando para correr la app
ENTRYPOINT ["java", "-jar", "app.jar"]