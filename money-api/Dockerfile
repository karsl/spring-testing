FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/money-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
