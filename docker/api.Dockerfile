FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY . .

RUN chmod +x ./scripts/generate-ssh-keys.sh && \
    ./scripts/generate-ssh-keys.sh

RUN mkdir -p /app/dotenv_files && \
    cp /app/dotenv_files/.env-exemplo /app/dotenv_files/.env

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

COPY --from=build /app/keys /keys

COPY --from=build /app/dotenv_files/.env /app/.env

CMD ["java", "-jar", "app.jar"]