
About the application

    Mediscreen is a Spring Boot application focused on preventive care, it allows doctors to better monitor 
    their patients and assess the risks of them developing type 2 diabetes.
    To facilitate it's evolution and scalability, the application is built with a microservice's architecture. 
    
    Each microservice is store in a docker container and is responsible of it's own functionality :
    - microservice_ClientUI => display information
    - microservice_PersonalRecord => manage patient info (CRUD)
    - microservice_PractitionerNote => manage patient notes (CRUD)
    - microservice_DiabetesReport => assesses diabetes risks based on patient data

spring-boot
Technical:

    Framework: Spring Boot v3.1.0
    Java 17
    Maven
    Feign client
    Sping data JPA
    Spring data MongoDb
    Thymeleaf
    Bootstrap v.4.0.0-2

Setup the docker containers

    Use the docker-compose.yml file to build the docker containers

Database

    The patient infos are persisted in SQL database.
    -> To create the database use the file microservice_PersonalRecord/data/PersonalRecord_data.sql

    The practitioner's notes are persisted in noSQL database
