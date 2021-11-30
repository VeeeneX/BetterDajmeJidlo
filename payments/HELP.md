# Payments service

To build and run this app only Java11 suitable environment is required. This repository contains
maven wrapper used to manage this Java project. There are also docker image and docker-compose
configurations which can be used to build, test and deploy application on systems without
Java11

#### Build to JVM: ####
`./mvnw clean install`

#### Docker image for JVM build  ####
`docker build . -t payments-service:latest`

#### Build spring native image for docker - preferred ####
`./mvnw -P native clean spring-boot:build-image`
