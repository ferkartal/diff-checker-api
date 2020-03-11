FROM maven:3.6-jdk-11
MAINTAINER Nur Erkartal
WORKDIR /temp
COPY . .
RUN mvn install
COPY /target/app.jar diff-checker-api.jar
ENTRYPOINT ["java", "-jar", "diff-checker-api.jar"]