package net.kobluewater.stock.dto;

public class StockDto {
	String name;
	String market;
	int minTradeValue;
	int code;

	public StockDto(String code, String market, String name, String minTradeValue) {
		this.market = market;
		this.code = Integer.parseInt(code);
		this.name = name;
		this.minTradeValue = Integer.parseInt(minTradeValue);
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
