package com.ajisafe.mystockwebservice;

import java.util.List;
import java.util.Map;

public class AlphavantageData {
	List<Map<String,DayCloseStockPrice>> timeSeriesDaily;

	public List<Map<String, DayCloseStockPrice>> getTimeSeriesDaily() {
		return timeSeriesDaily;
	}

	public void setTimeSeriesDaily(List<Map<String, DayCloseStockPrice>> timeSeriesDaily) {
		this.timeSeriesDaily = timeSeriesDaily;
	}
	
	
}
