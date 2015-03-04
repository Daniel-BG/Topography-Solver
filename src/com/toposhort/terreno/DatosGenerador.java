package com.toposhort.terreno;

public class DatosGenerador {
	//int anchura, int altura, long semilla, int alturaMinima, int alturaMaxima, int desplazamientoBase, ReductorAbstracto r
	private int anchuraMapa = ANCHURA_DEFECTO;
	private int alturaMapa = ALTURA_DEFECTO;
	private long semilla = SEMILLA_DEFECTO;
	private boolean semillaAleatoria = false;
	private int alturaMinima = ALTURA_MINIMA_DEFECTO;
	private int alturaMaxima = ALTURA_MAXIMA_DEFECTO;
	private int desplazamientoBase = DESPLAZAMIENTO_BASE_DEFECTO;
	private ReductorAbstracto reductor = REDUCTOR_DEFECTO;
	
	public static final int ANCHURA_DEFECTO = 300;
	public static final int ALTURA_DEFECTO = 200;
	public static final long SEMILLA_DEFECTO = 1404047745880l;
	public static final int ALTURA_MINIMA_DEFECTO = -2000;
	public static final int ALTURA_MAXIMA_DEFECTO = 2000;
	public static final int DESPLAZAMIENTO_BASE_DEFECTO = 1000;
	public static final ReductorAbstracto REDUCTOR_DEFECTO = new ReductorExponencial();
	
	
	public int getAnchuraMapa() {
		return anchuraMapa;
	}
	public void setAnchuraMapa(int anchura) {
		this.anchuraMapa = anchura;
	}
	public int getAlturaMapa() {
		return alturaMapa;
	}
	public void setAlturaMapa(int altura) {
		this.alturaMapa = altura;
	}
	public long getSemilla() {
		long semillita;
		if (this.semillaAleatoria)
			semillita = System.currentTimeMillis();
		else
			semillita = semilla;
		
		System.out.println("La semilla es: " + semillita);
		return semillita;
	}
	public void setSemilla(long semilla) {
		this.semilla = semilla;
	}
	public boolean isSemillaAleatoria() {
		return semillaAleatoria;
	}
	public void setSemillaAleatoria(boolean semillaAleatoria) {
		this.semillaAleatoria = semillaAleatoria;
	}
	public int getAlturaMinima() {
		return alturaMinima;
	}
	public void setAlturaMinima(int alturaMinima) {
		this.alturaMinima = alturaMinima;
	}
	public int getDesplazamientoBase() {
		return desplazamientoBase;
	}
	public void setDesplazamientoBase(int desplazamientoBase) {
		this.desplazamientoBase = desplazamientoBase;
	}
	public int getAlturaMaxima() {
		return alturaMaxima;
	}
	public void setAlturaMaxima(int alturaMaxima) {
		this.alturaMaxima = alturaMaxima;
	}
	public ReductorAbstracto getReductor() {
		return reductor;
	}
	public void setReductor(ReductorAbstracto reductor) {
		this.reductor = reductor;
	}

}
