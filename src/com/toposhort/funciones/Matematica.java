package com.toposhort.funciones;

import java.awt.event.MouseEvent;

import com.toposhort.estructuras.Coordenada;

public class Matematica {
	
	public static int convertirAEntero(String str) {
		int resultado;
		try {resultado = Integer.parseInt(str);}
		catch (NumberFormatException e) {return Integer.MIN_VALUE;}
		
		return resultado;
	}
	
	public static float convertirAComaFlotante(String str) {
		float resultado;
		try {resultado = Float.parseFloat(str);}
		catch (NumberFormatException e) {return Float.MIN_VALUE;}
		
		return resultado;
	}

	public static long convertirALong(String str) {
		long resultado;
		try {resultado = Long.parseLong(str);}
		catch (NumberFormatException e) {return Integer.MIN_VALUE;}
		
		return resultado;
	}
	
	public static Coordenada getCoordenadaDesdeEventoDeRaton(MouseEvent arg0, int anchura, int altura, int tamanox, int tamanoy) {
		int x = arg0.getX();
		int y = arg0.getY();
		return new Coordenada(x*anchura/tamanox,y*altura/tamanoy);
	}
}
