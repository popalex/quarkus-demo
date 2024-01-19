## Stage 1 : build with maven builder image with native capabilities
FROM docker.io/library/openjdk:17-jdk-slim AS build
COPY mvnw /code/mvnw
COPY .mvn /code/.mvn
COPY pom.xml /code/
WORKDIR /code
COPY src /code/src
RUN ./mvnw clean package --no-transfer-progress

## Stage 2 : create the docker final image
FROM docker.io/library/eclipse-temurin:17-jre-alpine
WORKDIR /app
RUN addgroup --system javauser && adduser -S -s /usr/sbin/nologin -G javauser javauser
COPY --from=build --chown=javauser:javauser /code/target/*-runner.jar /app/app.jar
# if this is NOT a uber jar build, add the next line
# COPY --from=build --chown=javauser:javauser target/lib/ lib/
USER javauser
CMD ["java", "-jar", "app.jar", "-Dquarkus.http.host=0.0.0.0"]