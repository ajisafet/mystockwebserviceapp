FROM adoptopenjdk:12-jre-hotspot

RUN mkdir /mystockwebserviceapp
RUN mkdir /app
WORKDIR /mystockwebserviceapp

ADD MyStockWebService-1.0-SNAPSHOT-jar-with-dependencies.jar /mystockwebserviceapp

EXPOSE 8080

ENV NDAYS 3
ENV TICKER "MSFT"

CMD ["java", "-jar", "MyStockWebService-1.0-SNAPSHOT-jar-with-dependencies.jar"]
