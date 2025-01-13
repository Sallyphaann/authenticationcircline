FROM openjdk:17-jdk-slim
WORKDIR /app
COPY ./build/libs/authentication-Circline-0.0.1-SNAPSHOT.jar ./

EXPOSE 8083
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar authentication-Circline-0.0.1-SNAPSHOT.jar"]


