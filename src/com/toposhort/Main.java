package com.toposhort;

import com.toposhort.controlador.Controlador;
import com.toposhort.estructuras.MapaTopografico;
import com.toposhort.visor.Ventana;
import com.toposhort.visor.VentanaOpciones;
import com.toposhort.visor.VentanaViajante;
import com.toposhort.visor.VisorMapa;

public class Main {
	public static void main(String[] args) {
		MapaTopografico g = new MapaTopografico();
		Controlador c = new Controlador(g);
		VisorMapa vm = new VisorMapa(c);
	
		new Ventana(vm);
		new VentanaOpciones(c);
		VentanaViajante vv = new VentanaViajante(c);
		
		g.addObserver(vm);
		g.addObserver(vv);
		

		
		c.regenerar();
	}
	
}
