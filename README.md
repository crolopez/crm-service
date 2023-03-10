# CRM Service

The purpose of this project is to define a REST API to manage customer data for a small store.

You can check the available endpoints from the OpenAPI specification that you can find in [api-spec.yaml](./src/main/resources/api-spec.yaml) file, or import the Insomnia collection from [insomnia-collection.json](./insomnia-collection.json).

## Requirements

To build the application it is necessary to install the following dependencies:

- [Maven](https://maven.apache.org/)
- [JDK 17](https://openjdk.org/projects/jdk/17/)
- [Docker](https://www.docker.com/) (if you want to build it as a dockerized application)

## Configuration

In order to launch the application, it is necessary to configure the following environment variables:

| Variable               | Description                                                                             |
|------------------------|-----------------------------------------------------------------------------------------|
| DATASOURCE             | Datasource of a PostgreSQL database. Example: jdbc:postgresql://localhost:5432/postgres |
| DB_USERNAME            | Database username                                                                       |
| DB_PASSWORD            | Database password                                                                       |
| OAUTH2_CLIENT_ID       | OAuth2 client ID generated by your authentication provider                              |
| OAUTH2_CLIENT_SECRET   | OAuth2 client secret generated by your authentication provider                          |
| FIRST_USER_IS_ADMIN    | Make admin the first user logged into the application. Accepted values: `true`/`false`. |
| CACHE_EXPIRATION_TIME  | Internal cache expiration time in minutes                                               | 

The `OAUTH2_*` parameters refer to Github parameters as authentication provider.

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
docker run -p 8080:8080 \
  --env DATASOURCE="<DATASOURCE>" \
  --env DB_USERNAME="<DB_USERNAME>" \
  --env DB_PASSWORD="<DB_PASSWORD>" \
  --env OAUTH2_CLIENT_ID="<OAUTH2_CLIENT_ID>" \
  --env OAUTH2_CLIENT_SECRET="<OAUTH2_CLIENT_SECRET>" \
  --env CACHE_EXPIRATION_TIME="2" \
  --env FIRST_USER_IS_ADMIN=true \
  the-crm-service
```

You can see a complete example of how to configure and run the API in a local environment [here](./.github/docs/EXAMPLE.md).

## How to deploy

In [this file](./.github/workflows/cicd.yml) you can find an example of how to deploy this application in a dockerized environment on AWS. 

The recipe shows the steps to run the corresponding CI/CD, and you would only need to configure the secrets based on your personal AWS and Amazon ECS credentials.

## How to use 

This API uses OAuth2 with Github as authentication provider. Therefore, before using it you must access this endpoint:

``` bash
http://localhost:8080/v1/login/oauth2/github
```

This will take you to an authentication screen where you can enter your Github credentials. 

After that, a response will be returned to you, in whose headers you can find the bearer token to use in the API.

## About the API

- Any user with a Github account can login to the application adopting the `USER` role.
- Any change in the role of a user will be reflected when refreshing cache (option `CACHE_EXPIRATION_TIME`).
- If the `FIRST_USER_IS_ADMIN` option is enabled, the first user to log in will adopt the `ADMIN` role.
- You can pre-register users using the endpoint for this, but their identifier must match the Github one (not username).
