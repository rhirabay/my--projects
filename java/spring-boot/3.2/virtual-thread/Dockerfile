FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY build/libs/app.jar /app/app.jar
RUN chmod +x /app/app.jar

ENTRYPOINT ["/app/app.jar"]