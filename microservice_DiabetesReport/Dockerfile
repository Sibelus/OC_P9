FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ./target/microservice_DiabetReport-0.0.1-SNAPSHOT.jar micro_DiabetReport.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar","/micro_DiabetReport.jar"]