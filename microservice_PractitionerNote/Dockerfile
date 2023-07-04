FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ./target/microservice_PractitionerNote-0.0.1-SNAPSHOT.jar micro_PractitionerNote.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar","/micro_PractitionerNote.jar"]