FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline
COPY src ./src

EXPOSE 8080
CMD ["./mvnw","spring-boot:run"]

#CMD ["java", "-jar", "target/service1-0.0.1-SNAPSHOT.jar"]
