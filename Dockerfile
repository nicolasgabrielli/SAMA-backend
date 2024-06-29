# Usa la imagen base de JDK
FROM openjdk:17-jdk-alpine

# Añadir un volumen apuntando a /tmp
VOLUME /tmp

# Añadir el jar generado por Spring Boot al contenedor
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Exponer el puerto que la aplicación Spring Boot usa
EXPOSE 8080

# El comando para ejecutar la aplicación
ENTRYPOINT ["java","-jar","/app.jar"]