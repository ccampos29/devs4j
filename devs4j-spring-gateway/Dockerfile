FROM openjdk:11-slim

LABEL maintainer="Carlos Campos"

ARG JAR_FILE

RUN echo ${JAR_FILE}

COPY ${JAR_FILE} /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]