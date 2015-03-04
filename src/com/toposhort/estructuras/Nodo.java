package com.toposhort.estructuras;

import com.toposhort.Colores;

public class Nodo {
	public float altura;
	public Coordenada posicion;

	public Nodo(float altura, Coordenada posicion) {
		this.altura = altura;
		this.posicion = posicion;
	}
	

	
	public int getColor() {
		int[] colores = Colores.coleccionColores();
		int[] alturas = Colores.coleccionAlturas();
		
		if (colores.length <= 1 || alturas.length <= 1 || colores.length != alturas.length)
			return Colores.colorError();
		
		int i = 0;
		while (altura > alturas[i] && i < alturas.length-1) 
			i++;
		
		if (i == 0)
			return colores[0];
		
		float diferencia = alturas[i]-alturas[i-1];
		float bajo = altura-alturas[i-1];
		float alto = alturas[i]-altura;
		assert bajo >= 0 && alto >= 0;
		
		int alpha = (int) (((colores[i-1] >>> 24)*alto+(colores[i] >>> 24)*bajo)/diferencia);
		int rojo = (int) ((((colores[i-1] & 0xff0000) >> 16)*alto+((colores[i] & 0xff0000) >> 16)*bajo)/diferencia);
		int verde = (int) ((((colores[i-1] & 0xff00) >> 8)*alto+((colores[i] & 0xff00) >> 8)*bajo)/diferencia);
		int azul = (int) (((colores[i-1] & 0xff)*alto+(colores[i] & 0xff)*bajo)/diferencia);
		
		return (alpha << 24) + (rojo << 16) + (verde << 8) + azul;
	}


}
