# OAuth Application
***

The application use **Java 17** runtime and **Postgresql** as database.  
**Flywaydb** is used for database migration.  
We have two spring profiles for this application : "**local**" and "**dev**".  

# Run Locally
***

1. #### First set the environment variable  
    `SPRING_PROFILES_ACTIVE=local` 

2. #### Then run below maven command to start up te application.  

    `mvn spring-boot:run`  

>For local, we use embedded H2 database.

# Docker Compose
***
To spin up the application with actual postgre database, use docker compose script.  
Set the spring profile value as **dev** inside **docker/.env** file for the parameter **SPRING_PROFILE**.  
Execute below commands inside **docker** folder where **docker-compose.yml** is present.

### The services can be run on the background with command:
`docker compose up -d`

### To stop all the running containers:
`docker compose down`

### To stop and remove all containers, networks, and all images used by any service in docker-compose.yml:
`docker compose down --rmi all`

> While spinning up initially, the postgres datafiles are created inside the docker folder. This data files will be reused on the subsequent docker compose. If we want to recreate the database, delete the postgres folder in docker directory manually, before every compose.

