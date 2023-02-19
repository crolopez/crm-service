# The CRM Service

The purpose of this project is to define a REST API to manage customer data for a small store.

You can check the available endpoints from the OpenAPI specification that you can find in [api-spec.yaml](./src/main/resources/api-spec.yaml) file, or import the Insomnia collection from [insomnia-collection.json](./insomnia-collection.json).

## Requirements

To build the application it is necessary to install the following dependencies:

- [Maven](https://maven.apache.org/)
- [JDK 17](https://openjdk.org/projects/jdk/17/)
- [Docker](https://www.docker.com/) (if you want to build it as a dockerized application)

## Configuration

In order to launch the application, it is necessary to configure the following environment variables:

| Variable    | Description                                                                             |
|-------------|-----------------------------------------------------------------------------------------|
| DATASOURCE  | Datasource of a PostgreSQL database. Example: jdbc:postgresql://localhost:5432/postgres |
| DB_USERNAME | Database username                                                                       |
| DB_PASSWORD | Database password                                                                       |


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
docker run -p 8080:8080 --env DATASOURCE="<DATASOURCE>" --env DB_USERNAME="<DB_USERNAME>" --env DB_PASSWORD="<DB_PASSWORD>" the-crm-service
```

## How to deploy

In [this file](./.github/workflows/cicd.yml) you can find an example of how to deploy this application in a dockerized environment on AWS. 

The recipe shows the steps to run the corresponding CI/CD, and you would only need to configure the secrets based on your personal AWS and Amazon ECS credentials.