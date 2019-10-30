package com.ajisafe.mystockwebservice;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * Root resource (exposed at "getclosingstockprice" path)
 */
@Path("getclosingstockprice")
public class MyResource {
	private static String webService = "https://www.alphavantage.co/query?apikey=C227WD9W3LUVKVV9&function=TIME_SERIES_DAILY_ADJUSTED";
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt() {
    	String symbol = System.getenv("SYMBOL");
        String days = System.getenv("NDAYS");
        Integer intDays = Integer.parseInt(days);
        Client client = ClientBuilder.newClient();
        
        // Contact AlphavantageData to pull stock price records
        WebTarget target = client.target(webService + "&symbol="+symbol);
        String response = null;
        try {
        	response = target.request().get(String.class);
        } catch(Exception e) {
        	System.out.print(e.getMessage());
        }
        
        StringBuilder strBuilderResponse = new StringBuilder(response);
        
        // format JSON response to make it compatible with GSON
        // this is to convert JSON to a Java objects
        int index = strBuilderResponse.indexOf("Time Series (Daily)\": {");
        strBuilderResponse.replace(index, index + "Time Series (Daily)\": {".length(), "timeSeriesDaily\": [\n\t{");
       
        index=strBuilderResponse.lastIndexOf("}");
        strBuilderResponse.deleteCharAt(index);
        index=strBuilderResponse.lastIndexOf("}");
        strBuilderResponse.deleteCharAt(index);
        
        strBuilderResponse.append("}\n\t]\n}");
        int fromIndex = 0;
        
        while (true) {
        	fromIndex = strBuilderResponse.indexOf("4. close", fromIndex);
        	if (fromIndex == -1) break;
        	strBuilderResponse.replace(fromIndex, fromIndex + "4. close".length(), "closingStockPrice");
        	fromIndex = fromIndex + "closingStockPrice".length() - 1;
		}
        
        //Convert formatted JSON response to Java object
		Gson gson = new Gson();
		AlphavantageData alphavantageData = null; 
		
		alphavantageData = gson.fromJson(strBuilderResponse.toString(),AlphavantageData.class);

		StringBuilder tickerData = new StringBuilder(symbol +" data=[");
		int counter = 0;
		DayCloseStockPrice dayCloseStockPrice = null;
		Map<String,DayCloseStockPrice> dayCloseStockPriceRecords = alphavantageData.getTimeSeriesDaily().get(0);
        float closingPriceNdaysSum = 0.0f;
		for (String day :dayCloseStockPriceRecords.keySet()){
        	 dayCloseStockPrice = dayCloseStockPriceRecords.get(day);
        	 tickerData.append(dayCloseStockPrice.getClosingStockPrice());
        	 closingPriceNdaysSum += Float.parseFloat(dayCloseStockPrice.getClosingStockPrice());
        	 counter++;
        	 if (counter < intDays.intValue()) {
        		 tickerData.append(", ");
        	 }
        	 else {
        		 tickerData.append("]");
        		 break;
        	 }
        }
        
		closingPriceNdaysSum /= (float)intDays;
		tickerData.append(", average=").append(closingPriceNdaysSum).append("\n");
		return tickerData.toString();
    }
}
