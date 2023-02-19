# The CRM Service

The purpose of this project is to define a REST API to manage customer data for a small store.

## Requirements

To build the application it is necessary to install the following dependencies:

- [Maven](https://maven.apache.org/)
- [JDK 17](https://openjdk.org/projects/jdk/17/)
- [Docker](https://www.docker.com/) (if you want to build it as a dockerized application)

## Run locally

To deploy the application locally just run:

```bash
mvn clean install
java -jar .\target\the-crm-service-1.0-SNAPSHOT.jar
```
These commands raise the application listening on port 8080. 

If you want to build the application in a dockerized environment:
```bash
mvn clean install
mvn io.fabric8:fabric8-maven-plugin:build
docker run -p 8080:8080 the-crm-service
```
