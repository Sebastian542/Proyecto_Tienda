package co.edu.unbosque.model.persistence;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
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
	
	public static String findPartiallyByDescription(String search, boolean order) {
		try (BufferedReader scan = Files.newBufferedReader(Paths.get("src/main/resources/Archives/data.csv"),
				Charset.defaultCharset())) {
			HashMap<Integer, String> partialDescription = new HashMap<>();
			String content = scan.readLine();
			while (scan.ready()) {
				content = scan.readLine();
				String line[] = content.split(",");
				if (!search.equalsIgnoreCase(String.valueOf(line[2]))) {
					if (order) {
						TreeMap<Integer, String> quantity = new TreeMap<Integer, String>();
						quantity.put(Integer.parseInt(line[3]), line[2]);
						return quantity.descendingMap().entrySet().stream().map(x -> String.valueOf(x))
								.collect(Collectors.joining("\n"));
					} else {
						partialDescription.put(Integer.parseInt(line[3]), line[2]);
						return partialDescription.entrySet().stream().map(x -> String.valueOf(x))
								.collect(Collectors.joining("\n"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
