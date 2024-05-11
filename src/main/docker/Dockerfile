FROM amazoncorretto:17
ARG WAR_FILE=*.jar
COPY ${WAR_FILE} application.jar
CMD apt-get update -y
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "application.jar"]
