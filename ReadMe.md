# OAuth Application
***

The application use **Java 17** runtime, **Springboot 2.5.1** and **Postgresql** as database.  
**Flywaydb** is used for database migration.  
We have three spring profiles for this application : "**local**", "**dev**" and "**prod**".  

## Run Locally
***

1. #### First set the environment variable  
    `SPRING_PROFILES_ACTIVE=local` 

2. #### Then run below maven command to start up te application.  

    `mvn spring-boot:run`  

>For local, we use embedded H2 database.

## Docker Compose
***
To spin up the application with actual postgre database, use docker compose script.  
Currently **dev** profile is used for docker compose.  
Execute below commands inside **docker** folder where **docker-compose.yml** is present.

### The services can be run on the background with command:
`docker compose up -d`

### To stop all the running containers:
`docker compose down`

### To stop and remove all containers, networks, and all images used by any service in docker-compose.yml:
`docker compose down --rmi all`

> While spinning up initially, the postgres datafiles are created inside the docker folder. This data files will be reused on the subsequent docker compose. If we want to recreate the database, delete the postgres folder in docker directory manually, before every compose.

## Actuator 
***
The actuator can be accessed via below link.  

> http://<*hostname*>:9001/actuator
 
## Deployment Steps
***

> Make sure kubeconfig is configured with the correct cluster name.  

#### For eks run below command to configure the kubeconfig.  
`aws eks update-kubeconfig --region <regionName> --name <clusterName>`

### Postgres Deployment
***

1. #### To deploy the postgres in kubernetes cluster, run below command in **k8s** folder.  

   `kubectl apply -f database-deployment.yml`

2. #### After deployment, run below command to check if you are able to log in to postgres database.  

   `kubectl exec -it <postgres pod name> -- psql -h localhost -U <db user name> --password -p 5432 <db name>`

### Application Deployment
***
1. #### Build the application 
   `mvn clean install`

2. #### Build docker Image  
   `docker build -t ssanish686/oauth-app:latest .`  

3. #### Push the docker Image to docker hub 
   `docker push ssanish686/oauth-app:latest`

4. #### To deploy the application in kubernetes cluster, run below command in **K8S** folder.

   `kubectl apply -f app-deployment.yml`

5. #### Use below command to check the logs 
   `kubectl logs <app pod name>`

6. #### We are using LoadBalancer service for the app. Run below command to see the LoadBalancer details.
   ` kubectl describe svc <app service name>`  
     Hostname is present in the field : ***LoadBalancer Ingress***

7. #### Check actuator to see if the app is up and running
   `http://{{oauth-app-host}}:{{oauth-management-port}}/actuator/health`

## Deployment Steps Using Helm
***

Helm provide more convenient way to deploy the application to different environment. 
In the above case since we are deploying only to PROD, It's ok to use deployment.yml and apply kubectl. 
But in case if we have quite a lot of environment (DEV, QA, UAT, PROD), it will become cumbersome activity to change 
values in deployment.yml file. Here helm can be really helpful.

1. #### To deploy the postgres in kubernetes cluster, run below command in ***helm*** folder.
   `helm install -f prod-postgres-values.yml postgres ./postgres`

2. #### To deploy the application in kubernetes cluster, run below command in ***helm*** folder.
   `helm install -f prod-app-values.yml app ./app`

3. #### To check te status of deployment.
   `helm list`

## Commands to remember

#### To show all service

`kubectl get svc`

#### To delete a service

`kubectl delete svc <service name>`

#### To show all deployment

`kubectl get deployments`

#### To delete a deployment

`kubectl delete deployments <deployment name>`

#### To show all pods

`kubectl get pods`

#### To delete a pod

`kubectl delete pods <pod name>`

#### To show all config map

`kubectl get cm`

#### To delete a config map

`kubectl delete cm <config map name>`

#### To see values in a config map

`kubectl describe cm <configmap name>`

#### To create a helm chart
`helm create <chart name>`

#### To show the charts installed
`helm list`

#### To uninstall a chart which is installed
`helm uninstall <chart name>`
> This will delete all the corresponding service, deployments, pods everything which are created in the kubernetes cluster as part of helm install.