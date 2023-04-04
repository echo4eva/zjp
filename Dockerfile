FROM openjdk
COPY build/libs/*.jar test-spring-application.jar
ENTRYPOINT ["java", "-jar", "/test-spring-application.jar"]