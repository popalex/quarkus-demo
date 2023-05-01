## Stage 1 : build with maven builder image with native capabilities
FROM quay.io/quarkus/ubi-quarkus-graalvmce-builder-image:22.3-java17 AS build
COPY --chown=quarkus:quarkus mvnw /code/mvnw
COPY --chown=quarkus:quarkus .mvn /code/.mvn
COPY --chown=quarkus:quarkus pom.xml /code/
USER quarkus
WORKDIR /code
COPY src /code/src
RUN ./mvnw package -Pnative

## Stage 2 : create the docker final image
FROM quay.io/quarkus/quarkus-micro-image:2.0
WORKDIR /app/

# set up permissions for user `1001`
COPY --from=build --chown=1001:root --chmod="g+rwX" /code/target/*-runner /app/application

EXPOSE 8080
USER 1001

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]