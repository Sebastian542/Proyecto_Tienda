package co.edu.unbosque.model.persistence;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
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
	
	public static int countByStockCode(String stockCode) {
		int sum = 0;
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/Archives/data.csv"), Charset.defaultCharset())){
			String content = reader.readLine();
			while(reader.ready()) {
				content = reader.readLine();
				String line [] = content.split(",");
				if(stockCode.equalsIgnoreCase(String.valueOf(line[1]))) 
					sum += Integer.parseInt(line[3]);
			}
		} catch(Exception e) { e.printStackTrace(); }
		return sum;
	}
	
	
	public static String avgMonthlySales(boolean groupByCountry) {
		HashMap <String, Double> averageCountry = new HashMap <> ();
		HashMap <String, Integer> countCountrys = new HashMap <> ();
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/Archives/data.csv"), Charset.defaultCharset())){
			String content = reader.readLine();
			while(reader.ready()) {
				content = reader.readLine();
				String line [] = content.split(",");
				String country = line[line.length -1];
				if(groupByCountry) {
					double sum = Double.parseDouble(line[3]) * Double.parseDouble(line[5]);
					if(!averageCountry.containsKey(country)) {
						countCountrys.put(country, 1);
						averageCountry.put(country, sum);
					}else {
						double current = averageCountry.get(country);
						averageCountry.replace(country, (current + sum));
						countCountrys.replace(country, countCountrys.get(country) +1);
					}
					for(String index : averageCountry.keySet()) 
						averageCountry.replace(index, averageCountry.get(index) / countCountrys.get(index));
					return averageCountry.entrySet().stream().map(x -> String.valueOf(x)).collect(Collectors.joining("\n"));
				}else {
					HashMap <Integer, Double> month = new HashMap <Integer, Double> ();
					HashMap <Integer, Integer> quantity = new HashMap <Integer, Integer> ();
					String split [] = line[4].split("/");
					int mes = Integer.parseInt(split[2]);
					if(!month.containsKey(Integer.parseInt(line[4]))) {
						month.put(mes, Integer.parseInt(line[3]) * Double.parseDouble(line[5]));
						quantity.put(mes, 1);
					}else {
						double current = month.get(mes);
						month.replace(mes, (current + (Integer.parseInt(line[3]) * Double.parseDouble(line[5]))));
						quantity.replace(mes, quantity.get(mes));
					}
					for(Integer index : month.keySet())
						month.replace(index, (month.get(index) / quantity.get(index)));
					return month.entrySet().stream().map(x -> String.valueOf(x)).collect(Collectors.joining("\n"));
				}
			}
		} catch(Exception e) { e.printStackTrace(); }
		return null;
	}
	
	public static List <String> findPartiallyByDescription(String search) {
		List <String> list = new ArrayList <> ();
		search = search.toUpperCase();
		try (BufferedReader read = Files.newBufferedReader(Paths.get("src/main/resources/Archives/data.csv"), Charset.defaultCharset())){
			String content = read.readLine();
			while(read.ready()) {
				content = read.readLine();
				if(content.matches(".*(" + search + ").*"))
					list.add(content);
			}
		} catch(Exception e) { e.printStackTrace(); }
		return list;
	}
	
	
	public static List <String> findPartiallyByDescription(String search, boolean order) {
		HashMap <String, Integer> quantity = new HashMap <String, Integer> ();
		List <String> list = findPartiallyByDescription(search);
		if(order) {
			for(String index : list) {
				String array [] = index.split("[,]");
				System.out.println(array[2]);
				if(!quantity.containsKey(array[2])){
					quantity.put(array[2], Integer.parseInt(array[3]));
				}else {
					int current = quantity.get(array[2]);
					quantity.replace(array[2], (current + Integer.parseInt(array[3])));
				}
			}
			//Falto ordenar los datos.
			return Collections.emptyList();
		}else {
			return list.stream().toList();
		}
	}
	
}
