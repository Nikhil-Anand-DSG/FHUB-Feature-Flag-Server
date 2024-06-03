# Spring Boot Config Server Vault-Based Feature Flags

A Spring boot application that pulls data from Vault and is able to serve up the values via Mappings.

If you are getting access denied it could be 2 problems.

- Make sure you are setting profile to dev,stage, or prod in run-config or when you start the application

    (ex: mvn spring-boot:run -Dspring-boot.run.profiles=dev)

    OR

    Make sure to enter a valid vault token, try regenerating the token in vault if you still have issues.
