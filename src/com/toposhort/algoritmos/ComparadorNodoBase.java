package com.toposhort.algoritmos;

import com.toposhort.estructuras.Nodo;

public class ComparadorNodoBase {

	private int limiteAltura = LIMITE_ALTURA_DEFECTO;
	private float limitePendiente = LIMITE_PENDIENTE_DEFECTO;
	private int distanciaEntrePuntos = DISTANCIA_ENTRE_PUNTOS_DEFECTO;
	private float penalizacionBarco = PENALIZACION_BARCO_DEFECTO;
	private boolean barcoPermitido = BARCO_PERMITIDO_DEFECTO;
	
	
	//calculamos el coste entre dos nodos, que suponemos adyacentes
	//será infinito si no cumple los límites de altura o pendiente
	//y por lo demás, viene dado por la diferencia de altura
	public float costeEntreNodos(Nodo a, Nodo b) {
		boolean enAgua = a.altura < 0 || b.altura < 0;
		
		if (a.altura > this.limiteAltura || b.altura > this.limiteAltura)
			return Float.POSITIVE_INFINITY;
		if (enAgua && !this.isBarcoPermitido())
			return Float.POSITIVE_INFINITY;
		if (!enAgua && Math.abs(a.altura-b.altura)/(float)this.distanciaEntrePuntos > this.limitePendiente)
			return Float.POSITIVE_INFINITY;
		
		if (enAgua)
			return this.penalizacionBarco;
		else
			return (Math.abs(a.altura-b.altura)+1);
	}
	
	public int getLimiteAltura() {
		return limiteAltura;
	}
	public void setLimiteAltura(int limiteAltura) {
		this.limiteAltura = limiteAltura;
	}
	public float getLimitePendiente() {
		return limitePendiente;
	}
	public void setLimitePendiente(float limitePendiente) {
		assert limitePendiente >= 0f && limitePendiente <= 1f : "La pendiente debe ser positiva y entre 0 y 1";
		this.limitePendiente = limitePendiente;
	}
	public int getDistanciaEntrePuntos() {
		return distanciaEntrePuntos;
	}
	public void setDistanciaEntrePuntos(int distanciaEntrePuntos) {
		assert distanciaEntrePuntos > 0 : "Distancia entre puntos debe ser positiva y mayor que cero";
		this.distanciaEntrePuntos = distanciaEntrePuntos;
	}
	public float getPenalizacionBarco() {
		return penalizacionBarco;
	}
	public void setPenalizacionBarco(float penalizacionBarco) {
		assert penalizacionBarco >= 0 : "No puede haber costes negativos";
		this.penalizacionBarco = penalizacionBarco;
	}
	public boolean isBarcoPermitido() {
		return barcoPermitido;
	}
	public void setBarcoPermitido(boolean barcoPermitido) {
		this.barcoPermitido = barcoPermitido;
	}

	public static final int LIMITE_ALTURA_DEFECTO = 5000;
	public static final float LIMITE_PENDIENTE_DEFECTO = 0.1f;
	public static final int DISTANCIA_ENTRE_PUNTOS_DEFECTO = 1000;
	public static final float PENALIZACION_BARCO_DEFECTO = 10f;
	public static final boolean BARCO_PERMITIDO_DEFECTO = false;

}
