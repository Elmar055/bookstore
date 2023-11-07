FROM openjdk:8
ARG JAR_FILE=target/bookstore-1.0.0.jar
COPY ${JAR_FILE} bookstore.jar
ENTRYPOINT ["java", "-jar", "/bookstore.jar"]