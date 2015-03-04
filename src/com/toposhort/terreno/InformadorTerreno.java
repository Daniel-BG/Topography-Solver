package com.toposhort.terreno;


import com.toposhort.estructuras.Coordenada;

public class InformadorTerreno {
	Coordenada comienzo;
	Coordenada termino;
	int iteracion;
	int desplazamientoBase;
	ReductorAbstracto reductor;
	
	public InformadorTerreno(Coordenada comienzo, Coordenada termino, int iteracion, int desplazamientoBase, ReductorAbstracto r) {
		this.comienzo = comienzo;
		this.termino = termino;
		this.iteracion = iteracion;
		this.desplazamientoBase = desplazamientoBase;
		reductor = r;
	}
	
	
	public InformadorTerreno derivaIteracion (int iteracion) {
		InformadorTerreno i = this.clone();
		i.iteracion = iteracion;
		return i;
	}
	
	public InformadorTerreno siguienteIt() {
		return this.derivaIteracion(this.iteracion+1);
	}
	
	public InformadorTerreno derivaComienzo (Coordenada comienzo) {
		InformadorTerreno i = this.clone();
		i.comienzo = comienzo;
		return i;
	}
	
	public InformadorTerreno derivaTermino (Coordenada termino) {
		InformadorTerreno i = this.clone();
		i.termino = termino;
		return i;
	}
	

	
	public InformadorTerreno clone() {
		return new InformadorTerreno(comienzo, termino,iteracion,desplazamientoBase,reductor);
	}
}
