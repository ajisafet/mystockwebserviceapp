FROM eclipse-temurin:latest

RUN mkdir /mystockwebserviceapp
WORKDIR /mystockwebserviceapp

ADD ./target/MyStockWebService-1.0-SNAPSHOT-jar-with-dependencies.jar /mystockwebserviceapp

EXPOSE 8080

ENV NDAYS 3
ENV SYMBOL "MSFT"

CMD ["java", "-jar", "MyStockWebService-1.0-SNAPSHOT-jar-with-dependencies.jar"]
