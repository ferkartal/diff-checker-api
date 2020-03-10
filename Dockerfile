FROM adoptopenjdk/maven-openjdk11:latest
MAINTAINER Nur Erkartal
WORKDIR /temp
COPY . .
RUN mvn verify
COPY temp/target/app.jar diff-checker-api.jar
ENTRYPOINT ["java", "-jar", "diff-checker-api.jar"]