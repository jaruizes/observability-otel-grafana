#FROM eclipse-temurin:17-jdk-alpine as build
#WORKDIR /workspace/app
#
#COPY mvnw .
#COPY .mvn .mvn
#COPY pom.xml .
#COPY src src
#
#RUN ./mvnw install -DskipTests
#RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jdk-alpine

RUN apk --no-cache add curl
COPY target/dependency/BOOT-INF/lib /app/lib
COPY target/dependency/META-INF /app/META-INF
COPY target/dependency/BOOT-INF/classes /app

EXPOSE 8081
ENTRYPOINT ["java","-cp","app:app/lib/*","com.jaruiz.examples.observability.bservicespring.BServiceSpringApplication"]
