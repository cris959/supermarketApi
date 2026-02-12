# 1. Imagen base actualizada a Java 21 y corregido el nombre "open"
FROM eclipse-temurin:21-jdk

# 2. Corregido el nombre del archivo JAR incluyendo el -SNAPSHOT
ARG JAR_FILE=target/SupermarketApi-0.0.1-SNAPSHOT.jar

# 3. Copiar el jar al contenedor
COPY ${JAR_FILE} app_technicaltestsuper.jar

# 4. Exponer el puerto configurado en application.properties
EXPOSE 8000

# 5. Comando de ejecución
ENTRYPOINT ["java", "-jar", "app_technicaltestsuper.jar"]