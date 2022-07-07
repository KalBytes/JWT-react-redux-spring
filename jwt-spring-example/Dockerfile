FROM maven:3.6.3-openjdk-11-slim as builder

WORKDIR /app
COPY pom.xml .

COPY src/ /app/src/
RUN mvn install -DskipTests=true

FROM adoptopenjdk/openjdk11:jre-11.0.8_10-alpine

COPY --from=builder /app/target/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]