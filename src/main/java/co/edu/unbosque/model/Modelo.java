package co.edu.unbosque.model;

public class Modelo {

	public Modelo() {		
		
	}
	
	public void summaraizingSales() {
		System.out.println(Persistencia.sumTotalSales());
	}

	public void searchData(String search) {
		System.out.println(Persistencia.findByInvoiceNo(search));
	}

	public void countByStockCode(String code) {
		System.out.println(Persistencia.countByStockCode(code));
	}

	public void avgMonthlySales(boolean confirmar) {
		System.out.println(Persistencia.avgMonthlySales(confirmar));
	}

	public void findPartiallyByDescription(String search) {
		Persistencia.findByInvoiceNo(search).forEach(x -> System.out.println(x));
	}

	public void findPartiallyByDescriptionOrder(String search, boolean order) {
		Persistencia.findPartiallyByDescription(search, order).forEach(x -> System.out.println(x));
	}

	public void findPartiallyByDescriptionFilter(String search, int initMonth, int endMonth) {
		System.out.println(Persistencia.finPartiallyByDescription(search, initMonth, endMonth));
	}			
	
	
	
}
