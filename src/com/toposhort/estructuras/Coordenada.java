package com.toposhort.estructuras;

public class Coordenada {
	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Coordenada)
			return this.x == ((Coordenada) obj).x && this.y == ((Coordenada) obj).y;
		return false;
	}

	@Override
	public int hashCode() {
		return ((x << 16) &0xffff0000) | (y &0xffff);
	}

	public int x;
	public int y;
	
	public Coordenada(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean noExistenPuntosEntre(Coordenada otra) {
		return Math.abs(otra.x-this.x) < 2 && Math.abs(otra.y-this.y) < 2;
	}
	
	public boolean entre(Coordenada inicio, Coordenada fin) {
		return this.x >= inicio.x  && this.x <= fin.x && this.y >= inicio.y && this.y <= fin.y;
	}
	
	public Coordenada clone() {
		return new Coordenada(x,y);
	}
	
	public Coordenada derivaX(int factor) {
		return new Coordenada(x+factor,y);
	}
	
	public Coordenada derivaY(int factor) {
		return new Coordenada(x,y+factor);
	}
	
	
}
