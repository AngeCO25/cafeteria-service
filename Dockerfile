FROM eclipse-temurin:21-jdk AS dependencias
WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

FROM eclipse-temurin:21-jdk AS test
WORKDIR /app
COPY . /app
COPY --from=dependencias /root/.m2 /root/.m2
RUN chmod +x mvnw
RUN ./mvnw test -B

FROM eclipse-temurin:21-jdk AS compile
WORKDIR /app
COPY --from=test /app /app
COPY --from=dependencias /root/.m2 /root/.m2
RUN ./mvnw clean package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine AS prod
WORKDIR /app
RUN addgroup -S app && adduser -S app -G app
USER app
COPY --from=compile /app/target/*.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]
