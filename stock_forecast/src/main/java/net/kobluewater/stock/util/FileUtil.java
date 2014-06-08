package net.kobluewater.stock.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class FileUtil {
	
	static Logger log = Logger.getLogger(FileUtil.class.getName());

	public static List<String> readListFromFile(String filename) {
		return readListFromFile(filename, 1);
	}
	
	public static List<String> readListFromFile(String filename, int startFromLine) {
		
		List<String> ret = null;
		BufferedReader br = null;
		 
		try {
			
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader(filename));
 
			ret = new ArrayList<String>();
			
			for (int i=1; i<startFromLine; i++) {
				br.readLine();
			}
			
			while ((sCurrentLine = br.readLine()) != null) {
				ret.add(sCurrentLine);
			}
			
			log.info("Finished reading " + filename + " to list");
 
		} catch (IOException e) {
			log.error(e.toString());
			
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				log.error(ex.toString());
			}
		}
		
		return ret;
	}
	
	public static void writeListToFile(List<String> destList, String filename) {
		try {			
 
			File file = new File(filename);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (String content : destList) {
				bw.append(content).append("\n");
			}
			bw.close();
 
			log.info("Finished writing list to " + filename);
 
		} catch (IOException e) {
			log.error(e.toString());
		}
	}
	
}
