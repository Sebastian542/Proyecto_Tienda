package co.edu.unbosque.controller;
import co.edu.unbosque.model.Modelo;
import co.edu.unbosque.view.Ventana;

public class Controller {

	private Ventana view;
	private Modelo model;
	
	public Controller() {
		view = new Ventana();
		model = new Modelo();
		funcionar();
	}

	private void funcionar() {
		try {
			int menu = 0;
			do {
				menu = view.leerDato("" + 
			           "\n Selecciona la opcion a realizar" + 
					   "\n 1.Total de ventas " +
					   "\n 2.Detalle de la factura " +
					   "\n 3.Cantidad de unidades vendidas " +
						"\n 4.Promedio de ventas mensuales "+
					   "\n 5.Lista de descripciones " +
						"\n 6.Lista de descripciones por orden de unidades vendidas "
						+ "\n 7.Lista de descripciones por filtrado de meses" +
			           "\n 0. Salir");
				switch(menu) {
				case 1:
					model.summaraizingSales();
					break;
					
				case 2:
					String invoiceNo = view.leerString("Ingrese la factura");
					model.searchData(invoiceNo);
					break;
					
				case 3:
					String stockCode = view.leerString("Ingrese el codigo del stock");
					model.countByStockCode(stockCode);
					break;
					
				case 4:
					int group = view.leerDato("¿Desea agrupar las ventas mensuales por pais? \n1. Si \n2. No");
					if (group == 1) {
						model.avgMonthlySales(true);
					} else if (group == 2) {
						model.avgMonthlySales(false);
					} else
						view.mostrarInformacion("Valor invalido");
					break;
					
				case 5:
					String description = view.leerString("Ingrese un criterio de busqueda");
					model.findPartiallyByDescription(description);
					break;
					
				case 6:
					String descriptionN = view.leerString("Ingrese un criterio de busqueda");
					model.findPartiallyByDescriptionOrder(descriptionN, true);
					break;
					
				case 7:
					String descriptionF = view.leerString("Ingrese un criterio de busqueda");
					int initMonth = view.leerDato("Mes Inicial: ");
					int endMonth = view.leerDato("Mes final: ");
					model.findPartiallyByDescriptionFilter(descriptionF, initMonth, endMonth);
					break;
					
				}
			}while(menu != 0);
		} catch(NumberFormatException formato) {}
	}
}
