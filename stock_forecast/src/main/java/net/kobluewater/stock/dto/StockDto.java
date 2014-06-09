package net.kobluewater.stock.dto;

import java.util.HashMap;
import java.util.Map;

public class StockDto {
	String name;
	String market;
	int minTradeValue;
	int code;
	Map<String,Integer> priceByDay;

	public StockDto(String code, String market, String name, String minTradeValue) {
		this(code);
		this.market = market;
		this.name = name;
		this.minTradeValue = Integer.parseInt(minTradeValue);
	}
	
	public StockDto(String code) {
		this.code = Integer.parseInt(code);
		this.priceByDay = new HashMap<String,Integer>();
	}
	
	public void setStockPrice(String date, int price) {
		priceByDay.put(date, price);
	}
	
	public int getStockPrice(String date) {
		return (priceByDay.get(date) != null) ? priceByDay.get(date) : -1;
	}
	
	@Override
	public String toString() {
		return "Code:" + code +
				"; Market Name:" + market +
				"; Name:" + name + 
				"; Minimum Trade Value:" + minTradeValue + ";";
	}
	
	
	public String toCSVString() {
		return code + "," +
				market + "," +
				name + "," +
				minTradeValue;
	}
	
	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMinTradeValue() {
		return minTradeValue;
	}
	public void setMinTradeValue(int minTradeValue) {
		this.minTradeValue = minTradeValue;
	}
	
	
}
