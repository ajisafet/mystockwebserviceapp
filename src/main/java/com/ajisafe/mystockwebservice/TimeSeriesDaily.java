package com.ajisafe.mystockwebservice;

import java.util.Map;
import java.util.TreeMap;

public class TimeSeriesDaily {
	
	private Map<String,DayCloseStockPrice> dayStockPriceRecords;

	public Map<String, DayCloseStockPrice> getDayStockPriceRecords() {
		return dayStockPriceRecords;
	}

	public void setDayStockPriceRecords(Map<String, DayCloseStockPrice> dayStockPriceRecords) {
		this.dayStockPriceRecords = dayStockPriceRecords;
	}
	
	
}
