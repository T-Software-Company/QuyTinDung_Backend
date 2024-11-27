FROM openjdk:21-jdk-oracle

WORKDIR /app

COPY target/qtd-service-0.0.1-SNAPSHOT.jar /app/app.jar
COPY service-account.json /app/service-account.json
COPY tsoftware.store.crt /app/tsoftware.store.crt

RUN keytool -importcert \
    -trustcacerts \
    -file /app/your_certificate.crt \
    -alias qtd \
    -keystore $JAVA_HOME/lib/security/cacerts \
    -storepass changeit \
    -noprompt

EXPOSE 8080

CMD [ "java","-jar","app.jar" ]
