package com.toposhort.controlador;

import com.toposhort.Colores;
import com.toposhort.algoritmos.ComparadorNodoBase;
import com.toposhort.estructuras.Coordenada;
import com.toposhort.estructuras.MapaTopografico;
import com.toposhort.terreno.DatosGenerador;
import com.toposhort.terreno.Pincel;

public class Controlador {
	
	private MapaTopografico mapa;
	private DatosGenerador dg;
	private ComparadorNodoBase comparador;
	private Pincel pincel;
	
	
	public Controlador(MapaTopografico mapa) {
		this.mapa = mapa;
		this.dg = new DatosGenerador();
		this.comparador = new ComparadorNodoBase();
		this.pincel = new Pincel();
	}
	
	public void regenerar() {
		mapa.generaMapa(dg);
	}
	
	public void generarCaminos() {
		mapa.generarCaminos(comparador);
	}
	
	public void modificaTerreno(Coordenada centro, boolean inverso) {
		if (!inverso)
			mapa.modificarTerreno(pincel, centro);
		else
			mapa.modificarTerreno(pincel.inverso(), centro);
	}
	public void generarMatrizDeAdyacencia() {
		mapa.generaMatrizDeAdyacencia(comparador);
	}
	public void calcularViajante() {
		mapa.resuelveViajante();
	}
	
	public void invierteColor() {
		Colores.CAC = !Colores.CAC;
		mapa.peticionActualizacion();
	}
	public void setCursor(Coordenada coord) {
		mapa.setCursor(coord);
	}

	

	//SETTERS para la generación
	public void setSemilla(long i) {
		dg.setSemilla(i);
	}
	public void setAnchuraMapa(int i) {
		dg.setAnchuraMapa(i);
	}
	public void setAlturaMapa(int i) {
		dg.setAlturaMapa(i);
	}
	public void setAlturaMax(int i) {
		dg.setAlturaMaxima(i);
	}
	public void setSemillaAleatoria(boolean equals) {
		dg.setSemillaAleatoria(equals);
	}
	public void setAlturaMin(int i) {
		dg.setAlturaMinima(i);
	}
	public void setDesviacion(int i) {
		dg.setDesplazamientoBase(i);
	}
	
	//SETTERS para modificacion
	public void setRadio(int i) {
		pincel.radio = i;
	}
	public void setVariacion(Float f) {
		pincel.variacion = f;
	}

	
	//SETTERS para la resolución
	public void setLimiteAltura(int i) {
		comparador.setLimiteAltura(i);
	}
	public void setPendienteMaxima(float i) {
		comparador.setLimitePendiente(i);
	}
	public void setDistanciaEntrePuntos(int i) {
		comparador.setDistanciaEntrePuntos(i);
	}
	public void setPenalizacionBarco(float f) {
		comparador.setPenalizacionBarco(f);
	}
	public void setBarcoDisponieble(boolean selected) {
		comparador.setBarcoPermitido(selected);
	}
	
	//SETTERS PARA EL ALGORITMO
	public void setWayPoint(Coordenada c) {
		mapa.addWayPoint(c, null);
	}
	public void removeWayPoint(Coordenada c) {
		mapa.borraWayPoint(c);
	}
	public void borraCaminos() {
		mapa.borraCaminos();
	}
	public void borraWayPoints() {
		mapa.borraTodosLosWayPoints();
	}

	
	
	








}
