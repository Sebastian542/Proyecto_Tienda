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
					   "\n 1. " +
			           "\n 0. Salir");
				switch(menu) {
				case 1:
					model.equals(model);
					break;
					
				}
			}while(menu != 0);
		} catch(NumberFormatException formato) {}
	}
}
