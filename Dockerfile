FROM maven:3.8.5-openjdk-17
ADD target/oauth-app.jar oauth-app.jar
EXPOSE 8080 9001
ENTRYPOINT ["java", "-jar", "oauth-app.jar"]