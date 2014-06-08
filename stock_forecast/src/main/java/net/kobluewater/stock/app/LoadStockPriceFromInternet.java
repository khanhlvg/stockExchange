package net.kobluewater.stock.app;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import net.kobluewater.stock.util.Download;

public class LoadStockPriceFromInternet {

	public static void main(String args[]){
    	try {
    		String urlDest = "http://k-db.com/stocks/8306-T?download=csv";
    		String fileName = "8306.csv";
    		
			Download d = new Download(new URL("http://hiphotos.baidu.com/happy_51qq/pic/item/1fec21c589a7108c8326ac9b.jpg"));
			d.run();
			
			URL url = new URL(urlDest);
			File f = new File(fileName);
			
			FileUtils.copyURLToFile(url, f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
