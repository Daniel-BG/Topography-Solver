package com.toposhort.estructuras;

import java.util.Comparator;

public class ComparadorCoordenadas implements Comparator<Coordenada> {

	@Override
	public int compare(Coordenada arg0, Coordenada arg1) {
		if (Integer.compare(arg0.x, arg1.x) != 0)
			return Integer.compare(arg0.x, arg1.x);
		return Integer.compare(arg0.y, arg1.y);
	}

}
