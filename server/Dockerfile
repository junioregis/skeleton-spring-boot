FROM openjdk:8-jre-alpine

ARG TIMEZONE=America/Sao_Paulo

RUN ln -snf /usr/share/zoneinfo/$TIMEZONE /etc/localtime && echo $TIMEZONE > /etc/timezone

COPY artifacts/app.jar /app.jar

RUN touch /app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/app.jar" ]