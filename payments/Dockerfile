FROM maven:3.8.3-openjdk-17-slim

RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-alpine

RUN mkdir /app
ARG JAR_FILE=/project/target/*.jar
COPY --from=0 ${JAR_FILE} /app/payments.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "payments.jar", "--spring.profiles.active=prod"]

# default HTTP port
EXPOSE 8080
