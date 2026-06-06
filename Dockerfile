
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jdk-alpine
COPY --from=build /target/agrotech-0.0.1-SNAPSHOT.jar agrotech.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "agrotech.jar"]