# Stock Web Service App
This is the source code of an application which is a web service that displays the closing stock prices from the [Alphavantage API](https://www.alphavantage.co/documentation/#dailyadj) for the stock of your choice for the last N days as specified by the user and calculates the average closing stock price over the desired period.

The source code is located in the folder [src/main/java/com/ajisafe/mystockwebservice](https://github.com/ajisafet/mystockwebserviceapp/tree/master/src/main/java/com/ajisafe/mystockwebservice).

The application can be run in a Docker container or a Kubernetes cluster. Before attempting to run the application, install [Docker Desktop Community Edition](https://www.docker.com/products/docker-desktop). This will give you access to the docker and kubernetes CLI tools & a single-node kubernetes cluster.

Please follow the instructions for your chosen mode of deployment.

### Kubernetes

To run the application, follow the listed instructions below.
1. Clone the repository to your local machine.

2. Then run the command:

> kubectl apply -f mystockwebserviceapp.yaml

This will deploy the application to a locally running kubernetes cluster.

3. Run the following command to see which port number is assigned to the Service object, mystockwebserviceapp-svc. The port number is set to 30001 in the YAML file, which can be changed if needed. Please note that the port number used by the application can be modified in the mystockwebserviceapp.yaml file

> kubectl get service

    NAME                       TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)          AGE
    kubernetes                 ClusterIP   10.96.0.1      <none>        443/TCP          26h
    mystockwebserviceapp-svc   NodePort    10.96.192.58   <none>        8080:30001/TCP   48m


4. Call the end point http://localhost:$PORT/mystockwebservice/getclosingstockprice to interact with the web service.

> curl http://localhost:30001/mystockwebservice/getclosingstockprice

    MSFT data=[144.6223, 142.8300, 144.1900, 140.7300, 139.9400, 137.2400, 136.3700], average=140.84604

5. To customise the stock (SYMBOL), number of days (NDAYS) and API key (APIKEY), change the ConfigMap and Secret resources in the mystockwebserviceapp.yaml YAML file.

6. To stop the application, run the following command:

> kubectl delete -f mystockwebserviceapp.yaml
 
### Docker

To run the web service application, I have created a docker repository at [temitopeajisafe/mystockwebserviceapp](https://hub.docker.com/r/temitopeajisafe/mystockwebserviceapp). To pull the docker repository and run it, execute the command below: 

> docker run --env NDAYS=__$DAYS__ --env SYMBOL=__$STOCK__ -i -p __$PORT__:8080 temitopeajisafe/mystockwebserviceapp:latest

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

where $PORT is the port number set when starting the web service.
