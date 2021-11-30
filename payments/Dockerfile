FROM openjdk:11-jdk-slim

RUN mkdir /project
COPY . /project
WORKDIR /project
RUN ./mvnw clean package -DskipTests


FROM openjdk:11-jre-slim

RUN mkdir /app
ARG JAR_FILE=/project/target/*.jar
COPY --from=0 ${JAR_FILE} /app/payments.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "payments.jar"]

# default HTTP port
EXPOSE 8080
