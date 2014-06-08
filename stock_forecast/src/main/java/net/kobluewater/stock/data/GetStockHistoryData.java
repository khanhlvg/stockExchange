package net.kobluewater.stock.data;

import java.io.File;
import java.net.URL;
import java.util.List;

import net.kobluewater.stock.util.FileUtil;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class GetStockHistoryData {
	
	static final String INPUT_PATH = "D:\\StockForecast\\InputData\\";
	static final String INPUT_CSV = INPUT_PATH + "ListOfStockToDownload.csv";
	
	static final String OUTPUT_PATH = "D:\\StockForecast\\InputData\\RAWStockHistory\\";

	static Logger log = Logger.getLogger(GetStockHistoryData.class.getName());
	
	static String getURLforStockID(String stockID) {
		return "http://k-db.com/stocks/" + stockID + "-T?download=csv";
	}
	
	static void downloadStockCSVData(String stockID) {
		try {
    		String urlDest = getURLforStockID(stockID);
    		String fileName = OUTPUT_PATH + stockID + ".csv";
 
			URL url = new URL(urlDest);
			File f = new File(fileName);
			
			FileUtils.copyURLToFile(url, f);
			
			log.info("Finished downloading RAW CSV data for stock No." + stockID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getStackTrace());
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		List<String> stockIDList = FileUtil.readListFromFile(INPUT_CSV);
		int count = 0;
		
		for (String stockID : stockIDList) {
			downloadStockCSVData(stockID);
			count++;
			log.debug("Finished " + count + "/" + stockIDList.size() + " stocks");
			Thread.sleep(1000);
		}

	}

}