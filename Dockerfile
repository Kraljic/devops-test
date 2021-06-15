FROM maven:3.8.1-jdk-8 as build

WORKDIR /app

COPY ./pom.xml ./pom.xml
RUN mvn -B -f ./pom.xml dependency:resolve
RUN mvn dependency:go-offline

COPY ./ ./
RUN mvn package


FROM java:8-jdk-alpine as app

WORKDIR /app

COPY --from=build /app/target/DockerDemo*.jar ./DockerDemo.jar

CMD ["java", "-jar", "DockerDemo.jar"]