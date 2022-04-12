FROM openjdk:8-jre-alpine

EXPOSE 8080

COPY target/metadata-api-test-1.0-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "metadata-api-test-1.0-SNAPSHOT.jar"]
