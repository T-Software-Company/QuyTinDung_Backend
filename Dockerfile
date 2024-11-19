FROM openjdk:21-jdk-oracle

WORKDIR /app

COPY target/qtd-service-0.0.1-SNAPSHOT.jar /app/app.jar
COPY service-account.json /app/service-account.json

EXPOSE 8080

CMD [ "java","-jar","app.jar" ]
