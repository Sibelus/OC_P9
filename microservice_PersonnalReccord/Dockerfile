FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ./target/microservice_PersonnalReccord-0.0.1-SNAPSHOT.jar micro_PersonalRecord.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar","/micro_PersonalRecord.jar"]