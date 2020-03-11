FROM adoptopenjdk/maven-openjdk11 AS build
MAINTAINER Nur Erkartal
WORKDIR /temp
COPY . .
RUN mvn verify

FROM openjdk:11-jre-slim
COPY --from=build /temp/target/app.jar diff-checker-api.jar
ENTRYPOINT ["java", "-jar", "diff-checker-api.jar"]