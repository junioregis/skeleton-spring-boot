FROM gradle:4.9-jdk8-alpine

ENV GRADLE_USER_HOME=/src/.gradle

USER root

RUN mkdir /src

WORKDIR /src

COPY src/ .

VOLUME /src/.gradle

CMD [ "gradle", "clean", "api:test", "api:bootJar", "--stacktrace" ]