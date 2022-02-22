package co.edu.unbosque.model.persistence;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
public class Persistencia {
	
	public Persistencia() {}
	
	public static double sumTotalSales() {
		double sum = 0;
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/Archives/data.csv"), Charset.defaultCharset())){
			String content = reader.readLine();
			while(reader.ready()) {
				content = reader.readLine();
				String [] array = content.split("[,]");
				sum += Integer.parseInt(array[3]) * Double.parseDouble(array[5]);
			}
		} catch(Exception e) { e.printStackTrace(); }
		return sum;
	}


	public static List <String> findByInvoiceNo(String invoiceNo) {
		List <String> list = new ArrayList <> ();
		try (BufferedReader read = Files.newBufferedReader(Paths.get("src/main/resources/Archives/data.csv"), Charset.defaultCharset())){
			String content = read.readLine();
			while(read.ready()) {
				content = read.readLine();
				String [] array = content.split("[,]");
				if(invoiceNo.equalsIgnoreCase(String.valueOf(array[0]))) 
					list.add(content);
			}
		} catch(Exception e) { e.printStackTrace(); }
		return list;
	}
	
}
