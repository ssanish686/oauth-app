# OAuth Application
***

The application use **Java 17** runtime and **Postgresql** as database.  
**Flywaydb** is used for database migration.  
We have three spring profiles for this application : "**local**", "**dev**"  and "**test**".  

# Run Locally
***

1. #### First set the environment variable  
    `SPRING_PROFILES_ACTIVE=local` 

2. #### Then run below maven command to start up te application.  

    `mvn spring-boot:run`  

>For local, we use embedded H2 database.

# Docker Compose
***
To spin up the application in dev or test environment, use docker compose script.  
Set the spring profile value in **.env** file inside parameter **SPRING_PROFILE**
### The services can be run on the background with command:
`docker compose up -d`

### To check the current working containers:
`docker ps`

### To check the current working images:
`docker images`

### To stop all the running containers:
`docker compose down`

### To stop and remove all containers, networks, and all images used by any service in docker-compose.yml:
`docker compose down --rmi all`

> To recreate the database, delete the postgres folder in parent directory.
