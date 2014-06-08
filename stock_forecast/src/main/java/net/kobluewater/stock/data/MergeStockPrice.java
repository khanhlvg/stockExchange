package net.kobluewater.stock.data;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.kobluewater.stock.util.FileUtil;

public class MergeStockPrice {
	
	static class StockDailyPrice {
		public String stockID;
		public String date;
		public String startPrice;
		public String highestPrice;
		public String lowestPrice;
		public String lastPrice;
		public String transVolume;
		public String transValue;
		
		public StockDailyPrice(String stockID, String infoCSVString) {
			String[] info = infoCSVString.split(",");
			this.stockID = stockID;
			date = info[0];
			startPrice = info[1];
			highestPrice = info[2];
			lowestPrice = info[3];
			lastPrice = info[4];
			transVolume = info[5];
			transValue = info[6];
		}
		
		//this method return the value to merge into file
		public String getValueToMerge() {
			return lastPrice;
		}
	}

	static final String RAW_CSV_PATH = "D:\\StockForecast\\InputData\\RAWStockHistory\\";
	
	static final String OUTPUT_PATH = "D:\\StockForecast\\InputData\\MergedStockHistory\\";
	static final String OUTPUT_CSV = OUTPUT_PATH + "LastPriceOfDay.csv";
	
	static final String COLUMN_NAME_FOR_DATE = "date";
	
	static Logger log = Logger.getLogger(MergeStockPrice.class.getName());
	
	static String getFilenameFromStockID(String stockID) {
		return RAW_CSV_PATH + stockID + ".csv";
	}
	
	static void loadStockInfoToMap(String stockID, LinkedHashMap<String,ArrayList<String>> map) {
		String filename = getFilenameFromStockID(stockID);
		List<String> stockRAWDataList = FileUtil.readListFromFile(filename, 3);
		
		if (!map.containsKey(COLUMN_NAME_FOR_DATE)) {
			ArrayList<String> newTitleList = new ArrayList<String>();
			newTitleList.add(stockID);
			map.put(COLUMN_NAME_FOR_DATE, newTitleList);
		} else {
			map.get(COLUMN_NAME_FOR_DATE).add(stockID);
		}
		
		for (String rawData : stockRAWDataList) {
			StockDailyPrice dailyPrice = new StockDailyPrice(stockID, rawData);
			if (!map.containsKey(dailyPrice.date)) {
				ArrayList<String> newPriceList = new ArrayList<String>();
				newPriceList.add(dailyPrice.getValueToMerge());
				map.put(dailyPrice.date, newPriceList);
			} else {
				map.get(dailyPrice.date).add(dailyPrice.getValueToMerge());
			}
		}
	}
	
	static void exportToCsv(LinkedHashMap<String,ArrayList<String>> map, String filename) {
		
		List<String> ret = new ArrayList<String>();
		
		for (Map.Entry<String,ArrayList<String>> entry : map.entrySet()) {
			StringBuilder sb = new StringBuilder();
			
			sb.append(entry.getKey());
			
			for (String s : entry.getValue()) {
				sb.append(",").append(s);
			}
			
			ret.add(sb.toString());
		}
		FileUtil.writeListToFile(ret, OUTPUT_CSV);
	}
	
	public static void main(String[] args) {
		
		LinkedHashMap<String,ArrayList<String>> map = new LinkedHashMap<String,ArrayList<String>>();
		File[] files = new File(RAW_CSV_PATH).listFiles();
		for (File file : files) {
	        if (!file.isDirectory()) {
	            String stockID = file.getName().replace(".csv", "");
	            loadStockInfoToMap(stockID, map);
	            log.info("Finished loading " + file.getAbsolutePath());
	        }
	    }
		
		exportToCsv(map,"");

	}

}
