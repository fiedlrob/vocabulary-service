FROM openjdk:21
WORKDIR /app
COPY build/libs/vocabulary-service-0.0.1-SNAPSHOT.jar vocabulary-service-0.0.1-SNAPSHOT.jar
EXPOSE 5001
CMD ["java", "-jar", "vocabulary-service-0.0.1-SNAPSHOT.jar"]