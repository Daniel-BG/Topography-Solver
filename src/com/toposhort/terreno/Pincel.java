package com.toposhort.terreno;

import com.toposhort.estructuras.Coordenada;

public class Pincel {
	public int radio = RADIO_DEFECTO;
	public float variacion = VARIACION_DEFECTO;
	
	public float nuevoValor(Coordenada desplazamiento, Float valorprevio) {
		double x = (double) (desplazamiento.x);
		double y = (double) (desplazamiento.y);
		if (Math.sqrt(x*x+y*y) >= radio)
			return valorprevio;
		return valorprevio + (float) (variacion*Math.sqrt(1-(x*x+y*y)/(double)(radio*radio)));
	}
	
	
	
	
	public static final int RADIO_DEFECTO = 20;
	public static final float VARIACION_DEFECTO = 200;

	public Pincel inverso() {
		Pincel a = new Pincel();
		a.radio = this.radio;
		a.variacion = -this.variacion;
		return a;
	}
}
