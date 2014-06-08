package net.kobluewater.stock.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import net.kobluewater.stock.dto.StockDto;
 
public class GetSmallStockList {
	
	static final String YAHOO_FINANCE_URL = "http://info.finance.yahoo.co.jp/ranking/?kd=7&tm=d&vl=a&mk=3&p=";
	static final int LAST_PAGE = 10;
	
	static final String PATH = "D:\\StockForecast\\InputData\\";
	static final String OUTPUT_CSV = PATH + "SmallScaleStock.csv";
	
	static final Pattern STOCK_INFO_ROW = Pattern.compile("<tr class=\"rankingTabledata yjM\">.*?<\\/tr>");
	static final Pattern STOCK_CONTENT = Pattern.compile(".*?>(\\d*)<\\/a>.*?yjSt\">(.*?)<\\/td>.*?yjSt\">(.*?)<\\/td>.*?bgyellow01\">(.*?)<\\/td>");
	
	static Logger log = Logger.getLogger(GetSmallStockList.class.getName());
	
	static StockDto htmlToStockDto(String html) {
		
		StockDto ret = null;
		Matcher matcher = STOCK_CONTENT.matcher(html);
		
		if (matcher.find()) {
			ret = new StockDto(matcher.group(1),
					matcher.group(2),
					matcher.group(3),
					matcher.group(4).replace(",",""));
		}
		
		return ret;
	}
	
	static void addStockInfoToList(String uri, List<StockDto> stockList) {
		URL url;
		Matcher matcher;
		 
		try {
			// get URL content
			url = new URL(uri);
			URLConnection conn = url.openConnection();
 
			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream(), "UTF-8"));
 
			String inputLine;
   
			while ((inputLine = br.readLine()) != null) {
				matcher = STOCK_INFO_ROW.matcher(inputLine);
				while (matcher.find()) {
					stockList.add(htmlToStockDto(matcher.group()));
				}
			}
 
			br.close();
 
			log.info("Finish parsing URL:" + uri);
 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void exportStockList(List<StockDto> stockList, String filename) {
		if (stockList == null) {
			return;
		}
		
		try {
			
			File fileDir = new File(filename);
			 
			Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(fileDir), "UTF8"));
			
			for (StockDto stock : stockList) {
				log.debug(stock.toString());
				out.append(stock.toCSVString()).append("\n");
			}

			out.flush();
			out.close();
 
			log.info("Finish exporting stock info to " + filename);
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		/*String testHtml = "<tr class=\"rankingTabledata yjM\"><td class=\"txtcenter\">251</td><td class=\"txtcenter\"><a href=\"http://stocks.finance.yahoo.co.jp/stocks/detail/?code=6966.t\">6966</a></td><td class=\"txtcenter yjSt\">\u6771\u8A3C1\u90E8</td><td class=\"normal yjSt\">(\u682A)\u4E09\u4E95\u30CF\u30A4\u30C6\u30C3\u30AF</td><td class=\"txtcenter grey yjSt\">15:00</td><td class=\"txtright bold\">665</td><td class=\"txtright\">28,240</td><td class=\"txtright\">100</td><td class=\"txtright bgyellow01\">66,500</td><td class=\"txtcenter yjSt\"><a href=\"http://textream.yahoo.co.jp/rd/finance/6966\">\u63B2\u793A\u677F</a></td></tr>";
		System.out.print(htmlToStockDto(testHtml));*/
		
		List<StockDto> stockList = new ArrayList<StockDto>();
		for (int p=1; p<= LAST_PAGE; p++) {
			addStockInfoToList(YAHOO_FINANCE_URL + p, stockList);
		}
		exportStockList(stockList, OUTPUT_CSV);
		
	}
}