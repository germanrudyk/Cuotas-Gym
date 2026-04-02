# 1. Usa una imagen que coincida con tu versión de Java (JDK 17 es la recomendada para Spring Boot 3)
FROM eclipse-temurin:17-jdk-alpine

# 2. Crear un directorio de trabajo
WORKDIR /app

# 3. Copia el JAR (Asegúrate de que el nombre coincida con el generado por Maven)
COPY target/CuotasGym-0.0.1-SNAPSHOT.jar app.jar

# 4. Render asigna un puerto dinámicamente mediante la variable de entorno $PORT
# No es estrictamente necesario EXPOSE en Render, pero ayuda a la documentación
EXPOSE 8080

# 5. Comando optimizado para producción
ENTRYPOINT ["java", "-Xmx512m", "-Dserver.port=${PORT:8080}", "-jar", "app.jar"]