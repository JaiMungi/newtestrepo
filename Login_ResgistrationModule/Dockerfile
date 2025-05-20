FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/Login_ResgistrationModule app.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","app.jar"]