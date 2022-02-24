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
	
	public static List<String> findPartiallyByDescription(String search) {
		List<String> list = new ArrayList<>();
		search = search.toUpperCase();
		try (BufferedReader read = Files.newBufferedReader(Paths.get("src/main/resources/Archives/data.csv"),
				Charset.defaultCharset())) {
			String content = read.readLine();
			while (read.ready()) {
				content = read.readLine();
				if (content.contains(search))
					list.add(content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> findPartiallyByDescription(String search, boolean order) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		try (BufferedReader read = Files.newBufferedReader(Paths.get("src/main/resources/Archives/data.csv"),
				Charset.defaultCharset())) {
			String content = read.readLine();
			while (read.ready()) {
				content = read.readLine();
				if (content.contains(search)) {
					String line[] = content.split("[,]");
					if (!map.containsKey(line[2])) {
						map.put(line[2], Integer.parseInt(line[3]));
					} else {
						int quantity = Integer.parseInt(line[3]);
						map.replace(line[2], map.get(line[2]) + quantity);
					}
				}
			}
			if (order) {
				return map.entrySet().stream().sorted((x, y) -> y.getValue() - x.getValue())
						.map(x -> x.getKey() + " = " + x.getValue()).collect(Collectors.toList());
			} else {
				return map.entrySet().stream().map(x -> x.getKey() + " = " + x.getValue()).collect(Collectors.toList());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String findPartiallyByDescription(String search, int initMonth, int endMonth) {
		List<String> list = findPartiallyByDescription(search);
		for (String index : list) {
			String line[] = index.split("[,]");
			String date[] = line[4].split("/");
			if (Integer.parseInt(date[0]) >= initMonth && Integer.parseInt(date[0]) <= endMonth)
				list.add(index);
		}
		return list.stream().collect(Collectors.joining("\n"));
	}
	
}
