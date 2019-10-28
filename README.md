# Stock Web Service App
This is the source code of an application which is a web service that displays the closing stock prices from the [Alphavantage API](https://www.alphavantage.co/documentation/#dailyadj) for the stock of your choice for the last N days as specified by the user and calculates the average closing stock price over the desired period.

To run the web service application, I have created a docker repository at [temitopeajisafe/mystockwebserviceapp](https://hub.docker.com/r/temitopeajisafe/mystockwebserviceapp). To pull the docker repository and run it, execute the command below: 

> docker run --env NDAYS=__$DAYS__ --env TICKER=__$STOCK__ -i -p __$PORT__:8080 temitopeajisafe/mystockwebserviceapp:latest

Replace the following fields with an appropriate value:

__$DAYS__ - Is a place holder for reporting the last N days of the closing price for a stock, $STOCK. The default, if it isn't set, is 3.

__$STOCK__ - The 1 to 4 letter symbol for a company's stock (i.e. MSFT for Microsoft, AMZN for Amazon). The default is MSFT if environment variable isn't set.

__$PORT__ - This is the port number where the web service will be listening to for requests.

An example of running the web service below:
> docker run --env NDAYS=5 --env TICKER=TSLA -i -p 5000:8080 temitopeajisafe/mystockwebserviceapp:latest

The above command will start the web service and on receiving a HTTP request to the port 5000, will report the closing price for Tesla's stock for the last 5 days and average closing stock price over those days.

To run the web service with the default number of days (3) and stock symbol (MSFT) on port 4000, do the following:
> docker run -i -p 4000:8080 temitopeajisafe/mystockwebserviceapp:latest

To interact with the web service, use a web browser or [curl](https://curl.haxx.se/download.html) to call the endpoint: 
> http://localhost:$PORT/mystockwebservice/getclosingstockprice

where $PORT is the port number set when starting the web server.
