FROM postgres:15.3-alpine
LABEL version="1.0"
LABEL description="API usando Node.js e PostgreSQL"
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=root12345
ENV POSTGRES_DB=java
EXPOSE 5432