# ETAPA 1: Construcción (Maven)
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
# Copia el archivo de configuración de Maven y el código fuente
COPY pom.xml .
COPY src ./src
# Compila el proyecto saltando los tests para ir más rápido
RUN mvn clean package -DskipTests

# ETAPA 2: Ejecución (Imagen ligera)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# AQUÍ ESTÁ EL TRUCO: Copiamos el JAR generado en la etapa anterior (build)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Dserver.port=${PORT:8080}", "-Xmx512m", "-jar", "app.jar"]