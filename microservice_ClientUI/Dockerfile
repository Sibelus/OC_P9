FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ./target/microservice_ClientUI-0.0.1-SNAPSHOT.jar micro_ClientUI.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/micro_ClientUI.jar"]