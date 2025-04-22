# Imagen base para compilar
FROM maven:3.8-openjdk-17 AS build

# Directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml primero para aprovechar la caché de dependencias
COPY pom.xml .
# Descargar todas las dependencias (se saltará si pom.xml no ha cambiado)
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar la aplicación y omitir tests
RUN mvn package -DskipTests

# Imagen final más ligera
FROM openjdk:17-jre-slim

WORKDIR /app

# Copiar solo el JAR compilado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]